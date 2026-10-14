package dev.toi_et_moi.mgdp.util;

import dev.xkmc.mob_weapon_api.registry.WeaponRegistry;
import dev.xkmc.mob_weapon_api.registry.WeaponStatus;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Unique;

public final class FlightUtil {

    public static final double AI_HOVER_BAND = 0.15;
    public static final double AI_DEFAULT_RANGED = 10.0;
    public static final double AI_RANGED_SAFE_BUFFER = 4.0;
    public static final double AI_VERTICAL_GAIN = 0.25;
    public static final double AI_VERTICAL_MAX = 0.6;
    public static final double AI_VERTICAL_GAIN_RANGED = 0.15; // ranged: gentle, anti-wither
    public static final double AI_VERTICAL_MAX_RANGED = 0.3;

    public static WeaponStatus getWeaponStatus(ItemStack stack) {
        if (stack.isEmpty()) return null;
        var s = WeaponRegistry.BOW.getProperties(stack);
        if (s.isPresent()) return s.get();
        s = WeaponRegistry.CROSSBOW.getProperties(stack);
        if (s.isPresent()) return s.get();
        s = WeaponRegistry.INSTANT.getProperties(stack);
        if (s.isPresent()) return s.get();
        s = WeaponRegistry.HOLD.getProperties(stack);
        if (s.isPresent()) return s.get();
        return null;
    }

    public static double getRangedRange(AbstractGolemEntity<?, ?> golem, ItemStack stack) {
        // HOLD weapons expose range(LivingEntity, ItemStack) directly
        var hold = WeaponRegistry.HOLD.get(golem, stack);
        if (hold.isPresent()) return hold.get().range(golem, stack);
        // BOW / CROSSBOW / INSTANT: fall back to default
        return AI_DEFAULT_RANGED;
    }

    /**
     * Self-contained AI flight control. Replaces what the parent mod's SWIM
     * upgrade would have given via the nav system: it only ever "chases" the
     * target and never backs off, so it has no ranged strafing and no
     * intelligent melee "stop on reach". This method does both, based on
     * the weapon type reported by mob_weapon_api's registries.
     *
     * Ranged: wither-style hover directly above the target at a distance of
     *         weapon-range blocks; horizontal position aligns to target XZ,
     *         vertical chases up gently but never chases down.
     * Melee:  fly to within reach, hover, never retreat; vertical chases up
     *         gently but never chases down (so finding a target on the
     *         ground does not pull a flying golem down to it).
     */
    @Unique
    public static Vec3 aiFlightControl(AbstractGolemEntity<?, ?> golem) {
        LivingEntity target = golem.getTarget();
        if (target == null || !target.isAlive()) return Vec3.ZERO;

        ItemStack mainHand = golem.getMainHandItem();
        WeaponStatus status = FlightUtil.getWeaponStatus(mainHand);
        if (status == null) return Vec3.ZERO;

        double reach = golem.getAttributeValue(ForgeMod.ENTITY_REACH.get());
        double meleeDist = reach + target.getBbWidth() * 0.5;

        double dx = target.getX() - golem.getX();
        double dy = target.getY() - golem.getY();
        double dz = target.getZ() - golem.getZ();
        double horizDist = Math.sqrt(dx * dx + dz * dz);

        Vec3 horizDir;
        double vertical;
        int strafeTime = 0;
        boolean strafeClockwise = false, strafeBackwards = false;

        if (status.isRanged()) {
            // Ranged kiting, anti-wither-style.
            //  - Keep horizontal distance at idealH (well past melee reach).
            //  - Never chase vertical — vertical chase + a flying target (e.g.
            //    wither) makes both ascend forever.
            //  - If we ever end up directly above/below the target (horizDist ≈
            //    0), break the alignment by picking a random horizontal
            //    direction. Hovering directly above is exactly the wither
            //    behaviour we are trying to avoid.
            //  - Inside the kite band, do the SmartRangedAttackGoal-style
            //    strafe: side-step + occasional forward/back, with a 30%
            //    chance per 20-tick cycle to flip clockwise / backwards.
            //    This is safe for seeTime accumulation because the golem
            //    keeps looking at the target the whole time (lookAt is
            //    set below in the method), so canSee never flips to false
            //    and seeTime keeps ticking up.
            //
            //  v0.4.0 change: back up ONLY when the target is inside our own
            //  melee reach. Earlier the back-up fired at idealH * 0.85
            //  (~12 blocks), which reset seeTime in the parent
            //  SmartRangedAttackGoal on every step (canSee toggling sets
            //  seeTime = 0) and the bow/crossbow could never finish its pull.
            //  The whole band (idealH * 0.85 .. idealH * 1.15) now HOLDS
            //  position so seeTime accumulates. We only step back if the
            //  target has closed into our own melee range — otherwise we
            //  would just stand still while a wither walks up and slaps us.
            double idealH = Math.max(FlightUtil.getRangedRange(golem, mainHand), meleeDist + AI_RANGED_SAFE_BUFFER);

            if (horizDist > idealH * (1.0 + AI_HOVER_BAND)) {
                // Too far — approach on the XZ plane.
                horizDir = new Vec3(dx, 0, dz).normalize();
            } else if (horizDist < meleeDist) {
                // Target has walked into our own melee reach — back up so we
                // don't eat a hit. We are still well within the band
                // geometry, but meleeDist << idealH so seeTime will still
                // reset and the bow will re-pull. That is the intended
                // trade-off: prefer being in range to losing a fight.
                horizDir = new Vec3(-dx, 0, -dz).normalize();
            } else if (horizDist < 0.5D) {
                // Directly above/below the target. dx/dz is zero, so a
                // normalise would NaN — break the vertical alignment with
                // a random horizontal direction so the bow can actually aim
                // (seeTime needs a non-trivial horizDist to stay > 0).
                double angle = golem.getRandom().nextDouble() * Math.PI * 2.0D;
                horizDir = new Vec3(Math.cos(angle), 0, Math.sin(angle));
            } else {
                // Inside the kite band — strafe like vanilla
                // SmartRangedAttackGoal.strafing(). Every 20 ticks there is
                // a 30% chance to flip clockwise and a 30% chance to flip
                // backwards, so the golem's strafe pattern wanders
                // realistically instead of being a fixed circle.
                strafeTime++;
                if (strafeTime >= 20) {
                    strafeTime = 0;
                    if (golem.getRandom().nextFloat() < 0.3F) strafeClockwise = !strafeClockwise;
                    if (golem.getRandom().nextFloat() < 0.3F) strafeBackwards = !strafeBackwards;
                }
                float yawRad = golem.getYRot() * (float) (Math.PI / 180.0);
                double fx = -Mth.sin(yawRad);
                double fz =  Mth.cos(yawRad);
                // Right vector = yaw - 90 degrees
                double sx = -Mth.cos(yawRad);
                double sz = -Mth.sin(yawRad);
                float fwd = strafeBackwards ? -0.5F : 0.5F;
                float side = strafeClockwise ? 0.5F : -0.5F;
                horizDir = new Vec3(fx * fwd + sx * side, 0, fz * fwd + sz * side);
                // Normalise so the speed component fed to moveRelative is
                // unit-length regardless of how fwd/side combine.
                if (horizDir.lengthSqr() > 1e-6) horizDir = horizDir.normalize();
            }
            // No vertical control. Vertical chase is the bug that lets a
            // flying golem and a wither race upward forever.
            vertical = 0;
        } else {
            // Melee: fly to within reach, hover, do not retreat, do not chase down
            double idealDist = meleeDist;
            if (horizDist > idealDist * (1.0 + AI_HOVER_BAND)) {
                horizDir = new Vec3(dx, 0, dz).normalize();
            } else {
                horizDir = Vec3.ZERO; // in or below reach: hold position
            }
            if (dy > 0) {
                vertical = Mth.clamp(dy * AI_VERTICAL_GAIN, 0.1, AI_VERTICAL_MAX);
            } else {
                vertical = 0;
            }
        }

        // Face target
        golem.getLookControl().setLookAt(target, 30.0F, 30.0F);

        return new Vec3(horizDir.x, vertical, horizDir.z);
    }

}
