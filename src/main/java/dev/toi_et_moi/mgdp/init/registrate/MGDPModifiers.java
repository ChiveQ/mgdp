package dev.toi_et_moi.mgdp.init.registrate;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.toi_et_moi.mgdp.content.modifier.base.AttributeOperationModifier;
import dev.toi_et_moi.mgdp.content.modifier.base.DescAttributeGolemModifier;
import dev.toi_et_moi.mgdp.content.modifier.buff.EnchantedNetheriteGoldModifier;
import dev.toi_et_moi.mgdp.content.modifier.buff.NetheriteGoldModifier;
import dev.toi_et_moi.mgdp.content.modifier.combat.EnchantedCrimsonAttackModifier;
import dev.toi_et_moi.mgdp.content.modifier.combat.RiptideModifier;
import dev.xkmc.l2library.base.NamedEntry;
import dev.xkmc.modulargolems.content.core.StatFilterType;
import dev.xkmc.modulargolems.content.entity.common.GolemFlags;
import dev.xkmc.modulargolems.content.modifier.base.AttributeGolemModifier;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import dev.xkmc.modulargolems.content.modifier.base.PotionDefenseModifier;
import dev.xkmc.modulargolems.content.modifier.base.SimpleFlagModifier;
import dev.xkmc.modulargolems.init.registrate.GolemTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import src.toi_et_moi.mgdp.modifier.MGDPAddSlotModifier;
import src.toi_et_moi.mgdp.modifier.buff.*;
import src.toi_et_moi.mgdp.modifier.combat.*;
import src.toi_et_moi.mgdp.modifier.common.CoronaModifier;
import src.toi_et_moi.mgdp.modifier.common.GhostModifier;
import src.toi_et_moi.mgdp.modifier.common.MoonShadowModifier;
import src.toi_et_moi.mgdp.modifier.common.SpyglassModifier;
import src.toi_et_moi.mgdp.modifier.conqueror.ConquerorModifier;
import src.toi_et_moi.mgdp.modifier.defense.*;
import src.toi_et_moi.mgdp.modifier.farming.*;
import src.toi_et_moi.mgdp.modifier.special.*;

import javax.annotation.Nullable;

import static dev.toi_et_moi.mgdp.init.MGDP.REGISTRATE;

public class MGDPModifiers {
    public static void register() {}
    // 顺序同创造物品栏
    public static final RegistryEntry<HarvestCropModifier> HARVEST_CROP;
    public static final RegistryEntry<GolemModifier> FLIGHT;
    public static final RegistryEntry<PotionAuraModifier> POTION_AURA;
    public static final RegistryEntry<RebirthModifier> REBIRTH;
    public static final RegistryEntry<SimpleFlagModifier> UNSTOPPABLE;
    public static final RegistryEntry<GolemModifier> SPIRIT;
    public static final RegistryEntry<NetheriteGoldModifier> NETHERITE_GOLD;
    public static final RegistryEntry<EnchantedNetheriteGoldModifier> ENCHANTED_NETHERITE_GOLD;
    public static final RegistryEntry<BellOfAviciModifier> BELL_OF_AVICI;
    public static final RegistryEntry<AttributeOperationModifier> DIAMOND_ATTACK;
    public static final RegistryEntry<AttributeOperationModifier> ENCHANTED_DIAMOND_ATTACK;
    public static final RegistryEntry<AttributeOperationModifier> CRIMSON_ATTACK;
    public static final RegistryEntry<EnchantedCrimsonAttackModifier> ENCHANTED_CRIMSON_ATTACK;
    public static final RegistryEntry<LightningStormModifier> LIGHTNING_STORM;
    public static final RegistryEntry<GolemModifier> ROCKET_FLIGHT;
    public static final RegistryEntry<DragonBreathModifier> DRAGON_BREATH;
    public static final RegistryEntry<WitherExtinctionModifier> WITHER_EXTINCTION;
    public static final RegistryEntry<ChargedShieldModifier> CHARGED_SHIELD;
    public static final RegistryEntry<HypothermiaModifier> HYPOTHERMIA;
    public static final RegistryEntry<ConduitModifier> CONDUIT;
    public static final RegistryEntry<OverworldModifier> OVERWORLD;
    public static final RegistryEntry<NetherModifier> NETHER;
    public static final RegistryEntry<SunlightModifier> SUNLIGHT;
    public static final RegistryEntry<CoronaModifier> CORONA;
    public static final RegistryEntry<MoonShadowModifier> MOON_SHADOW;
    public static final RegistryEntry<TimeAxisModifier> TIME_AXIS;
    public static final RegistryEntry<GolemModifier> UPSIDE_DOWN,REVERSE;
    public static final RegistryEntry<GhostModifier> GHOST;
    public static final RegistryEntry<SpyglassModifier> SPYGLASS;
    public static final RegistryEntry<DescAttributeGolemModifier> SHRINK;
    public static final RegistryEntry<SelfRepairModifier> SELF_REPAIR;
    public static final RegistryEntry<SonicBoomModifier> SONIC_BOOM;
    public static final RegistryEntry<FocusedDefenseModifier> FOCUSED_DEFENSE;
    public static final RegistryEntry<ExecutionerModifier> EXECUTIONER;
    public static final RegistryEntry<PotionDefenseModifier> INVISIBILITY;
    public static final RegistryEntry<GuardianLaserModifier> GUARDIAN_LASER;
    public static final RegistryEntry<IndomitableModifier> INDOMITABLE;
    // ARMOR_PIERCE MAGIC_RESISTANCE无独立Modifier
    public static final RegistryEntry<VersatilityModifier> VERSATILITY;
    // DAMAGE_CAP无独立Modifier
    public static final RegistryEntry<TotemicModifier> TOTEMIC;  // 兼ENCHANTED_TOTEMIC
    public static final RegistryEntry<EnchantModifier> ENCHANT;
    public static final RegistryEntry<SelfDestructModifier> SELF_DESTRUCT;
    public static final RegistryEntry<FireballModifier> FIREBALL;
    public static final RegistryEntry<HeroModifier> HERO;
    public static final RegistryEntry<FlareModifier> FLARE;
    public static final RegistryEntry<UnbreakableModifier> UNBREAKABLE;
    public static final RegistryEntry<InfiniteAmmoModifier> INFINITE_AMMO;
    public static final RegistryEntry<QuickStrikeModifier> QUICK_STRIKE;
    public static final RegistryEntry<AnglerModifier> ANGLER;
    public static final RegistryEntry<AnvilSlamModifier> ANVIL_SLAM;
    public static final RegistryEntry<TridentFestivalModifier> TRIDENT_FESTIVAL;
    public static final RegistryEntry<DescAttributeGolemModifier> IRON_UPGRADE;
    public static final RegistryEntry<DisarmModifier> DISARM;
    public static final RegistryEntry<RiptideModifier> RIPTIDE;
    public static final RegistryEntry<EndVoidModifier> END_VOID;
    public static final RegistryEntry<EndOfBeginningModifier> END_OF_BEGINNING;
    public static final RegistryEntry<DeathKnellModifier> DEATH_KNELL;
    public static final RegistryEntry<EchoTrioModifier> ECHO_TRIO;
    public static final RegistryEntry<MindControlModifier> MIND_CONTROL;
    public static final RegistryEntry<MGDPAddSlotModifier> CREATIVE_SLOT;
    public static final RegistryEntry<MGDPAddSlotModifier> CREATIVE_SLOT_100;
    public static final RegistryEntry<BrushModifier> BRUSH;
    public static final RegistryEntry<BombDisposalModifier> BOMB_DISPOSAL;
    public static final RegistryEntry<ProjectileDodgeModifier> PROJECTILE_DODGE;
    public static final RegistryEntry<ShieldBlockModifier> SHIELD_BLOCK;
    public static final RegistryEntry<ConquerorModifier> CONQUEROR;
    public static final RegistryEntry<BackstepModifier> BACKSTEP;
    public static final RegistryEntry<ProsperityModifier> PROSPERITY;
    public static final RegistryEntry<LiquidClearModifier> LIQUID_CLEAR;
    public static final RegistryEntry<BlastFurnaceModifier> BLAST_FURNACE;
    public static final RegistryEntry<FurnaceModifier> FURNACE;
    public static final RegistryEntry<MinerModifier> MINER;
    public static final RegistryEntry<ScavBoxModifier> SCAV_BOX;
    public static final RegistryEntry<LumberjackModifier> LUMBERJACK;
    // MAGIC_IMMUNE无独立Modifier
    public static final RegistryEntry<LordModifier> LORD;
    public static final RegistryEntry<SnowTrailModifier> SNOW_TRAIL;
    public static final RegistryEntry<SwapModifier> SWAP;
    public static final RegistryEntry<LunaModifier> LUNA;
    public static final RegistryEntry<BackflipModifier> BACKFLIP;
    public static final RegistryEntry<WindmillModifier> WINDMILL;
    public static final RegistryEntry<WitchModifier> WITCH;

    static {
        HARVEST_CROP = reg("harvest_crop", HarvestCropModifier::new,
                "Harvest Crop",
                "Auto harvest and replant crops within %s block(s) per Pickup level. Requires Pickup upgrade to work.");

        FLIGHT = reg("flight", () -> new GolemModifier(StatFilterType.MOVEMENT, 1),
                "Flight",
                "Golem gains creative-style flight. Can move freely in all three dimensions.");

        POTION_AURA = reg("potion_aura", PotionAuraModifier::new,
                "Potion Aura",
                "Shares positive effects with allies and inflicts negative effects on enemies within 48 blocks.");

        REBIRTH = reg("rebirth", RebirthModifier::new,
                "Rebirth",
                "Golem gains the ability to revive after death and recycle itself.");

        UNSTOPPABLE = reg("unstoppable", () -> new SimpleFlagModifier(StatFilterType.MOVEMENT, GolemFlags.FREE_MOVE),
                "Unstoppable",
                "Golem ignores terrain slowdown, knockback, entity collision, and cramming damage.");

        SPIRIT = reg("spirit", () -> new GolemModifier(StatFilterType.MOVEMENT, 1),
                "Spirit",
                "Requires Flight. Golem phases through all blocks like a Vex, becoming completely intangible.");

        NETHERITE_GOLD = reg("netherite_gold", NetheriteGoldModifier::new,
                "Netherite Gold Apple",
                "Regeneration V and fire immunity in one upgrade.");

        ENCHANTED_NETHERITE_GOLD = reg("enchanted_netherite_gold", EnchantedNetheriteGoldModifier::new,
                "Enchanted Netherite Gold Apple",
                "Regeneration V, fire immune, explosion resistant, lava walking, and +10% healing.");

        BELL_OF_AVICI = reg("bell_of_avici", BellOfAviciModifier::new,
                "Bell of Avici",
                "Enhanced Bell: 64-block radius, enemies that target the golem are teleported nearby every 2s.");

        DIAMOND_ATTACK = reg("diamond_attack", () -> new AttributeOperationModifier(
                new AttributeOperationModifier.AttrEntry(() -> Attributes.ATTACK_DAMAGE, "diamond_attack",
                        AttributeModifier.Operation.MULTIPLY_BASE, 0.3)),
                "Diamond Attack",
                "+30% attack damage.");

        ENCHANTED_DIAMOND_ATTACK = reg("enchanted_diamond_attack", () -> new AttributeOperationModifier(
                new AttributeOperationModifier.AttrEntry(() -> Attributes.ATTACK_DAMAGE, "enchanted_diamond_attack",
                        AttributeModifier.Operation.MULTIPLY_BASE, 0.6)),
                "Enchanted Diamond Attack",
                "+60% attack damage, all attacks are critical hits.");

        CRIMSON_ATTACK = reg("crimson_attack", () -> new AttributeOperationModifier(
                        new AttributeOperationModifier.AttrEntry(() -> Attributes.ATTACK_DAMAGE, "crimson_attack",
                                AttributeModifier.Operation.MULTIPLY_TOTAL, 0.5),
                        new AttributeOperationModifier.AttrEntry(() -> Attributes.ARMOR, "crimson_armor",
                                AttributeModifier.Operation.MULTIPLY_BASE, -0.5),
                        new AttributeOperationModifier.AttrEntry(() -> Attributes.ARMOR_TOUGHNESS, "crimson_tough",
                                AttributeModifier.Operation.MULTIPLY_BASE, -0.5)),
                "Crimson Attack",
                "-5% current HP per hit, +50% attack damage, min attack speed 1.0.");

        ENCHANTED_CRIMSON_ATTACK = reg("enchanted_crimson_attack", EnchantedCrimsonAttackModifier::new,
                "Enchanted Crimson Attack",
                "-10% current HP per hit, +100% attack damage, 50% lifesteal, min attack speed 1.0.");

        LIGHTNING_STORM = reg("lighting_storm", LightningStormModifier::new,
                "Lightning Storm",
                "Periodically strikes lightning on enemies. Damage = golem attack. 3x in thunderstorms.");

        ROCKET_FLIGHT = reg("rocket_flight", () -> new GolemModifier(StatFilterType.MOVEMENT, 1),
                "Rocket Flight",
                "Same as Flight, but -100% armor and toughness.");

        DRAGON_BREATH = reg("dragon_breath", DragonBreathModifier::new,
                "Dragon Breath",
                "Fires homing dragon fireballs at enemies. Explosion + breath cloud on impact.");

        WITHER_EXTINCTION = reg("wither_extinction", WitherExtinctionModifier::new,
                "Wither Extinction",
                "Charges 10s, then deals 100% max HP as wither + explosion damage to all enemies in 64 blocks. 1min cooldown.");

        CHARGED_SHIELD = reg("charged_shield", ChargedShieldModifier::new,
                "Charged Shield",
                "5 damage-absorbing shields. Each blocks 1 hit. Recharges every 15/10/5 seconds per level.");

        HYPOTHERMIA = reg("hypothermia", HypothermiaModifier::new,
                "Hypothermia",
                "Freezes enemies on hit and extinguishes fire in a 48-block radius.");

        CONDUIT = reg("heart_of_the_sea", ConduitModifier::new,
                "Conduit",
                "Golem grants Conduit Power to allies, gains attack and dodge in water/rain. Synergizes with Lightning Storm.");

        OVERWORLD = reg("overworld", OverworldModifier::new,
                "Primordial Earth",
                "Golem receives double healing and ignores knockback in the Overworld, underground (Y≤0) deals double damage and takes 30% less damage.");

        NETHER = reg("nether", NetherModifier::new,
                "Blazing Inferno",
                "Golem repairs once per second when on fire, in lava, or above Y=128 in the Nether, and deals double damage.");

        SUNLIGHT = reg("sunlight", SunlightModifier::new,
                "Radiance",
                "Golem regenerates in bright areas and deals bonus damage in darkness.");

        CORONA = reg("corona", CoronaModifier::new,
                "Corona",
                "During the day, the golem ignores all negative potion effects.");

        MOON_SHADOW = reg("moon_shadow", MoonShadowModifier::new,
                "Moon Shadow",
                "During the night, prevents hostile mob spawning within 32 blocks.");

        TIME_AXIS = reg("time_axis", TimeAxisModifier::new,
                "§6Time Axis",
                "§5Combines Radiance, Corona, and Moon Shadow. Effects ignore light/time restrictions. Accelerates block growth and random ticks nearby.");

        UPSIDE_DOWN = reg("upside_down",() -> new GolemModifier(StatFilterType.HEALTH, 1),
                "Upside Down",
                "Flips the golem upside down.");

        REVERSE = reg("reverse", () -> new GolemModifier(StatFilterType.HEALTH, 1),
                "Reverse",
                "Reverses the golem's front and back.");

        GHOST = reg("ghost", GhostModifier::new,
                "Ghost",
                "Makes the golem's model and shadow invisible.");
        SPYGLASS = reg("spyglass", SpyglassModifier::new,
                "Vision Range",
                "Doubles the golem's follow/target range.");

        SHRINK = reg("shrink", () -> new DescAttributeGolemModifier(4,
                        new AttributeGolemModifier.AttrEntry(GolemTypes.STAT_SIZE_P, () -> -0.2)),
                "Shrink",
                "Shrink golem size by 20%% per level. Max level 4.");

        SELF_REPAIR = reg("self_repair", SelfRepairModifier::new,
                "Self Repair",
                "When idle for 10s, regenerates 1 HP per second and reduces forge count.");

        SONIC_BOOM = reg("sonic_boom", SonicBoomModifier::new,
                "Sonic Boom",
                "Golem can use Sonic Boom Attack. Deals 10 damage per level and knocks back enemies.");

        FOCUSED_DEFENSE = reg("focused_defense", FocusedDefenseModifier::new,
                "Focused Defense",
                "Only takes damage from the golem's own target.");

        EXECUTIONER = reg("executioner", ExecutionerModifier::new,
                "Executioner",
                "Stronger against weakened enemies. Takes reduced/no damage from low-health attackers.");

        INVISIBILITY = reg("invisibility", () -> new PotionDefenseModifier(1, () -> MobEffects.INVISIBILITY),
                "Invisibility",
                "Golem becomes invisible.");

        GUARDIAN_LASER = reg("guardian_laser", GuardianLaserModifier::new,
                "Guardian Laser",
                "Charge a laser beam dealing heavy damage to all enemies in its path.");

        INDOMITABLE = reg("indomitable", IndomitableModifier::new,
                "Indomitable",
                "Leaps high and fires a volley of scattered arrows. Arrows inherit bow enchantments, bypass armor and i-frames. +10% ATK per level, one extra volley per level.");

        VERSATILITY = reg("versatility", VersatilityModifier::new,
                "Versatility",
                "The first 5 non-blue MGDP upgrades don't consume upgrade slots.");

        TOTEMIC = reg("totemic", TotemicModifier::new,
                "Totemic Apple",
                "Every %ss, grants absorption hearts equal to 10%% of max health per level.");

        ENCHANT = reg("enchant", EnchantModifier::new,
                "Auto-Enchant",
                "Every 10s, randomly enchants unenchanted items in the golem's gear and nearby containers. Each level adds 10 levels of enchantment-table power. Level 3 also maxes out any existing enchantments.");

        SELF_DESTRUCT = reg("self_destruct", SelfDestructModifier::new,
                "Self Destruct",
                "Golem explodes upon death or retrieval, dealing damage equal to max HP.");

        FIREBALL = reg("fireball", FireballModifier::new,
                "Fireball Attack",
                "Golem shoots fireballs at enemies within range.");

        HERO = reg("hero", HeroModifier::new,
                "Hero of the Village",
                "Grants the owner 1 minute of Hero of the Village per raider slain. Stacks duration. Compatible with Emerald upgrade.");

        FLARE = reg("flare", FlareModifier::new,
                "Illumination",
                "Golem automatically places torches in nearby dark areas. Makes cave exploration safer.");

        UNBREAKABLE = reg("unbreakable", UnbreakableModifier::new,
                "Unbreakable",
                "Golem equipment takes no durability damage.");

        INFINITE_AMMO = reg("infinite_ammo", InfiniteAmmoModifier::new,
                "Infinite Ammo",
                "Golem has unlimited ammunition for ranged weapons.");

        QUICK_STRIKE = reg("quick_strike", QuickStrikeModifier::new,
                "Quick Strike",
                "Golem attacks instantly with no cooldown for both melee and ranged.");

        ANGLER = reg("angler", AnglerModifier::new,
                "Angler",
                "Golem automatically fishes when holding a fishing rod near water.");

        ANVIL_SLAM = reg("anvil_slam", AnvilSlamModifier::new,
                "Anvil Slam",
                "Golem leaps toward the target and slams an anvil, dealing AOE damage to nearby enemies.");

        TRIDENT_FESTIVAL = reg("trident_festival", TridentFestivalModifier::new,
                "Trident Festival",
                "Golem summons tridents that explode and strike lightning on impact.");

        IRON_UPGRADE = reg("iron_upgrade", () -> new DescAttributeGolemModifier(2,
                        new AttributeGolemModifier.AttrEntry(GolemTypes.STAT_ARMOR, () -> 5.0),
                        new AttributeGolemModifier.AttrEntry(GolemTypes.STAT_ATTACK, () -> 2.0),
                        new AttributeGolemModifier.AttrEntry(GolemTypes.STAT_RANGE, () -> 0.5),
                        new AttributeGolemModifier.AttrEntry(GolemTypes.STAT_SWEEP, () -> 0.5)),
                "Iron Upgrade",
                "Pure stat upgrade: +5 armor, +2 damage, +0.5 range, +0.5 sweep per level. Max level 2.");

        DISARM = reg("disarm", DisarmModifier::new,
                "Disarm",
                "Golem strips random equipment from targets on hit. Level = items per hit.");

        RIPTIDE = reg("riptide", RiptideModifier::new,
                "Riptide",
                "Golem with a Riptide trident launches toward targets in water or rain.");

        END_VOID = reg("end_void", EndVoidModifier::new,
                "End Void",
                "Golem deals void damage in The End and is healed by end crystals like the Ender Dragon.");

        END_OF_BEGINNING = reg("end_of_beginning", EndOfBeginningModifier::new,
                "§6End of Beginning",
                "§5Combines all 3 dimension upgrades. Effects work everywhere. Nearby End Crystals make the golem invulnerable and need 13 hits to destroy.");

        DEATH_KNELL = reg("death_knell", DeathKnellModifier::new,
                "Death Knell",
                "Deals bonus damage equal to 2%% of target's max health.");

        ECHO_TRIO = reg("echo_trio", EchoTrioModifier::new,
                "Echo Trio",
                "Locks onto 3 targets within 35 blocks, fires sonic booms at them, and counter-attacks when damaged.");

        MIND_CONTROL = reg("mind_control", MindControlModifier::new,
                "Mind Control",
                "On hit, may redirect the target to attack your current target instead.");

        CREATIVE_SLOT = reg("creative_slot", () -> new src.toi_et_moi.mgdp.modifier.MGDPAddSlotModifier(999, 1),
                "Creative +1 Slot",
                "Add 1 upgrade slot. Creative only.");

        CREATIVE_SLOT_100 = reg("creative_slot_100", () -> new src.toi_et_moi.mgdp.modifier.MGDPAddSlotModifier(999, 100),
                "Creative +100 Slots",
                "Add 100 upgrade slots. Creative only.");

        BRUSH = reg("brush", BrushModifier::new,
                "Archaeology",
                "Requires Pickup. Golem with Brush automatically extracts items from suspicious blocks.");

        BOMB_DISPOSAL = reg("bomb_disposal", BombDisposalModifier::new,
                "Bomb Disposal Expert",
                "Nullifies non-friendly explosions and defuses primed TNT.");

        PROJECTILE_DODGE = reg("projectile_dodge", ProjectileDodgeModifier::new,
                "Projectile Dodge",
                "Dodges incoming projectiles and fast-moving threats.");

        SHIELD_BLOCK = reg("shield_block", ShieldBlockModifier::new,
                "Shield Block",
                "Block 5 shield-blockable hits. Fully refreshes every %ss.");

        CONQUEROR = reg("conqueror", ConquerorModifier::new,
                "Veterancy",
                "Golem gains conqueror XP by killing mobs. Higher star rating unlocks more bonuses.");

        BACKSTEP = reg("backstep", BackstepModifier::new,
                "Backstep",
                "Golem backsteps away when a target gets too close. If Backflip is also installed, the backstep is stylish.");

        PROSPERITY = reg("prosperity", ProsperityModifier::new,
                "Prosperity",
                "Crop range +%s/level (current %s). Loot x(level+1).");

        LIQUID_CLEAR = reg("liquid_clear", LiquidClearModifier::new,
                "Liquid Clear",
                "Clear range +%s/level (current %s).");

        BLAST_FURNACE = reg("blast_furnace", BlastFurnaceModifier::new,
                "Blast Furnace",
                "Golem smelts ores in hand like a blast furnace, dropping experience.");

        FURNACE = reg("furnace", FurnaceModifier::new,
                "Furnace",
                "Upgraded blast furnace: smelts items in hand and nearby containers using all furnace recipes (smelting, blasting, smoking), dropping experience.");

        MINER = reg("mine", MinerModifier::new,
                "Mining",
                "Holding a pickaxe, the golem automatically mines ores within the Pickup range. Requires Pickup. Chain-mines up to 8 adjacent same-type ores for free; chain drops land at the chain origin.");

        SCAV_BOX = reg("scav_box", ScavBoxModifier::new,
                "Scav Box",
                "After a cooldown, the golem scavenges structure chest loot and finds special items from installed linked mods.");

        LUMBERJACK = reg("lumberjack", LumberjackModifier::new,
                "Lumberjack",
                "Holding an axe, the golem automatically fells trees within the Pickup range and clears leaves. Requires Pickup. Replants a matching sapling at the tree base unless dual-wielding axes; Auto Smelt applies.");

        LORD = reg("lord", LordModifier::new,
                "Lord",
                "Golem displays a personal boss bar showing its name and health.");

        SNOW_TRAIL = reg("snow_trail", SnowTrailModifier::new,
                "Snow Trail",
                "Golem leaves a trail of snow and freezes water like Frost Walker as it moves.");

        SWAP = reg("swap", SwapModifier::new,
                "Swap",
                "Press the swap key (default: R) to switch places with the golem. When taking fatal damage, passively swaps with a random golem with this upgrade. 10s cooldown.");

        LUNA = reg("luna", LunaModifier::new,
                "Luna",
                "Press the reappear key (default: Alt+L) to duplicate the golem you are looking at. Materials, upgrades, owner, health and custom name are all preserved. With Scav Box, scavenging rolls an extra Luna loot table. 30s cooldown.");

        BACKFLIP = reg("backflip", BackflipModifier::new,
                "Backflip",
                "Golem does a stylish backflip while idling.");

        WINDMILL = reg("windmill", WindmillModifier::new,
                "Windmill",
                "Golem spins constantly like a windmill. Pure cosmetic.");

        WITCH = reg("witch", WitchModifier::new,
                "Witch",
                "Golem buffs itself and throws potions at enemies and allies like a witch.");

    }

    /**
     * 倣GolemModifiers、僅改REGISTRATE
     * */
    public static <T extends GolemModifier> RegistryEntry<T> reg(String id, NonNullSupplier<T> sup, String name, @Nullable String def) {
        // 创建一个可变对象来持有注册结果
        Mutable<RegistryEntry<T>> holder = new MutableObject<>();
        // 使用REGISTRATE创建并配置一个泛型注册项
        var ans = REGISTRATE.generic(GolemTypes.MODIFIERS, id, sup).defaultLang();
        // 设置名称的语言本地化
        ans.lang(NamedEntry::getDescriptionId, name);
        // 如果提供了描述文本，则添加描述的语言本地化
        if (def != null) {
            ans.addMiscData(ProviderType.LANG, pvd -> pvd.add(holder.getValue().get().getDescriptionId() + ".desc", def));
        }
        // 完成注册并获取结果
        var result = ans.register();
        // 将注册结果设置到可变对象 holder 中
        holder.setValue(result);
        return result;
    }

}
