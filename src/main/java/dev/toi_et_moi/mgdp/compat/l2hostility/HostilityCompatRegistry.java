package dev.toi_et_moi.mgdp.compat.l2hostility;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.compat.l2hostility.modifiers.*;
import dev.xkmc.modulargolems.compat.materials.l2hostility.LHCompatRegistry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class HostilityCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<UndyingModifier> UNDYING;
    public static final RegistryEntry<GrenadeModifier> GRENADE;
    public static final RegistryEntry<KillerAuraModifier> KILLER_AURA;
    public static final RegistryEntry<PullingModifier> PULLING;
    public static final RegistryEntry<RepellingModifier> REPELLING;
    public static final RegistryEntry<DementorModifier> DEMENTOR;
    public static final RegistryEntry<DrainModifier> DRAIN;
    public static final RegistryEntry<ReprintModifier> REPRINT;

    public static final ItemEntry<SimpleUpgradeItem> UP_ADAPTIVE,UP_DISPELL,UP_PULLING,UP_REPELLING,UP_DEMENTOR,
            UP_DRAIN,UP_REPRINT,UP_GRENADE,UP_KILLER_AURA,UP_UNDYING;

    static{
        DEMENTOR = reg("hostility_dementor", DementorModifier::new,
                "Hostility Upgrade: Dementor",
                "Reduces incoming damage with a nonlinear formula.");

        DRAIN = reg("hostility_drain", DrainModifier::new,
                "Hostility Upgrade: Drain",
                "Deals extra damage per negative effect on target and steals beneficial effects.");
        REPRINT = reg("hostility_reprint", ReprintModifier::new,
                "Hostility Upgrade: Reprint",
                "Deals bonus damage based on target's total enchantment levels.");
        UNDYING = reg("hostility_undying", UndyingModifier::new,
                "Hostility Upgrade: Undying",
                "When the golem would die, it instead revives with full health. Cannot bypass creative/kill damage.");

        GRENADE = reg("hostility_grenade", GrenadeModifier::new,
                "Hostility Upgrade: Grenade",
                "Shoots fast homing explosive grenades at targets within 40 blocks.");

        KILLER_AURA = reg("hostility_killer_aura", KillerAuraModifier::new,
                "Hostility Upgrade: Killer Aura",
                "+3 range and +3 sweep attack per level. Ranged hits splash the same damage to other enemies within 6/9/12 blocks.");

        PULLING = reg("hostility_pulling", PullingModifier::new,
                "Hostility Upgrade: Pulling",
                "Golem pulls nearby enemies toward itself like a magnet.");

        REPELLING = reg("hostility_repelling", RepellingModifier::new,
                "Hostility Upgrade: Repelling",
                "Golem pushes nearby enemies away and is immune to projectiles.");

        UP_ADAPTIVE = regModUpgrade("hostility_adaptive",() -> LHCompatRegistry.LH_ADAPTIVE,HostilityDispatch.MODID).register();
        UP_DISPELL = regModUpgrade("hostility_dispell",() -> LHCompatRegistry.LH_DISPELL,HostilityDispatch.MODID).register();
        UP_PULLING = regModUpgrade("hostility_pulling",() -> PULLING,HostilityDispatch.MODID).register();
        UP_REPELLING = regModUpgrade("hostility_repelling",() -> REPELLING,HostilityDispatch.MODID).register();
        UP_DEMENTOR = regModUpgrade("hostility_dementor",() -> DEMENTOR,HostilityDispatch.MODID).register();
        UP_DRAIN = regModUpgrade("hostility_drain",() -> DRAIN,HostilityDispatch.MODID).register();
        UP_REPRINT = regModUpgrade("hostility_reprint",() -> REPRINT,HostilityDispatch.MODID).register();
        UP_GRENADE = regModUpgrade("hostility_grenade",() -> GRENADE,HostilityDispatch.MODID).register();
        UP_KILLER_AURA = regModUpgrade("hostility_killer_aura",() -> KILLER_AURA,HostilityDispatch.MODID).register();
        UP_UNDYING = regModUpgrade("hostility_undying",() -> UNDYING,HostilityDispatch.MODID).register();
    }

}
