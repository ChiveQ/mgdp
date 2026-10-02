package dev.toi_et_moi.mgdp.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.compat.CompatManager;
import dev.toi_et_moi.mgdp.init.MGDP;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.modulargolems.content.item.equipments.MetalGolemWeaponItem;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import dev.xkmc.modulargolems.init.data.MGTagGen;
import dev.xkmc.modulargolems.init.registrate.GolemItems;
import dev.xkmc.modulargolems.init.registrate.GolemModifiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.model.generators.ModelFile;
import src.toi_et_moi.mgdp.init.BlackMourningItem;
import src.toi_et_moi.mgdp.item.IronCurtainItem;

import java.util.function.Supplier;

/**
 * mgdp 的物品注册（L2 Registrate 版本）。
 *
 * <p>仿照 {@link GolemItems} 的结构：字段声明 + static 块赋值。
 * 升级物品统一走 {@link #regUpgrade} 系列 helper，自动生成蓝/紫箭头 predicate 模型、
 * 打入 {@code modulargolems:upgrades} 标签，并同时放入本体的 upgrades 栏与本模组的
 * {@link #MGDP_TAB} 栏。</p>
 *
 * <p>本次仅注册<b>不依赖其它模组</b>的物品；所有需要 {@code ModList.get().isLoaded(...)}
 * 判定的条件注册项（l2hostility / twilightforest / create / cataclysm / goety / ...）
 * 均暂不迁移，留待后续在专门的 compat dispatch 中处理。</p>
 *
 * <p>modifier 的 supplier 暂引用旧代码 {@code src.toi_et_moi.mgdp.init.MGDPModifiers}，
 * 待 dev 侧 MGDPModifiers 完成后替换。</p>
 */
public class MGDPItems {

    // 本模组专属创造物品栏（mgdp 升级物品同时进入本体的 upgrades 栏与本栏）
    public static final RegistryEntry<CreativeModeTab> MGDP_TAB;

    // 升级物品（不依赖其它模组）
    public static final ItemEntry<SimpleUpgradeItem>
            HARVEST_CROP, FLIGHT, POTION_AURA, REBIRTH, UNSTOPPABLE, SPIRIT,
            NETHERITE_GOLD, ENCHANTED_NETHERITE_GOLD, BELL_OF_AVICI,
            DIAMOND_ATTACK, ENCHANTED_DIAMOND_ATTACK, CRIMSON_ATTACK, ENCHANTED_CRIMSON_ATTACK,
            LIGHTNING_STORM, ROCKET_FLIGHT, DRAGON_BREATH, WITHER_EXTINCTION, CHARGED_SHIELD,
            ARMOR_PIERCE, MAGIC_RESISTANCE, DAMAGE_CAP, TOTEMIC, ENCHANTED_TOTEMIC,
            HERO, FLARE, UNBREAKABLE, INFINITE_AMMO, PROSPERITY, LORD, SNOW_TRAIL,
            WINDMILL, BACKFLIP, SWAP, LUNA, WITCH, LIQUID_CLEAR, MAGIC_IMMUNE,
            QUICK_STRIKE, ANGLER, DEATH_KNELL, ECHO_TRIO, END_VOID, RIPTIDE,
            IRON_UPGRADE, TRIDENT_FESTIVAL, ANVIL_SLAM, SELF_DESTRUCT, FIREBALL,
            BRUSH, BOMB_DISPOSAL, BACKSTEP, CONQUEROR, PROJECTILE_DODGE,
            BLAST_FURNACE, FURNACE, MINER, SCAV_BOX, LUMBERJACK,
            CREATIVE_SLOT_100, CREATIVE_SLOT, MIND_CONTROL,
            GUARDIAN_LASER, INDOMITABLE, INVISIBILITY, EXECUTIONER, FOCUSED_DEFENSE,
            SONIC_BOOM, SELF_REPAIR, SUNLIGHT, OVERWORLD, NETHER, CONDUIT, HYPOTHERMIA,
            VERSATILITY, DISARM, END_OF_BEGINNING, CORONA, MOON_SHADOW, TIME_AXIS,
            UPSIDE_DOWN, REVERSE, GHOST, SPYGLASS, SHRINK, ENCHANT, SHIELD_BLOCK;

    // 独立物品（不依赖其它模组）
    public static final ItemEntry<IronCurtainItem> IRON_CURTAIN;
    public static final ItemEntry<BlackMourningItem> BLACK_MOURNING;
    public static final ItemEntry<MetalGolemWeaponItem>
            SIMPLE_GOLEM_SPEAR, SIMPLE_IRON_GOLEM_SPEAR, SIMPLE_DIAMOND_GOLEM_SPEAR, SIMPLE_NETHERITE_GOLEM_SPEAR;

    static {
        MGDP_TAB = MGDP.REGISTRATE.buildL2CreativeTab("mgdp_tab", "MG: Decisive Plan - Main",
                b -> b.icon(MGDPItems.FLIGHT::asStack));
    }

    static {
        HARVEST_CROP = regUpgrade("harvest_crop", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.HARVEST_CROP).register();
        FLIGHT = regUpgrade("flight", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.FLIGHT, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        POTION_AURA = regUpgrade("potion_aura", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.POTION_AURA, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        REBIRTH = regUpgrade("rebirth", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.REBIRTH, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        UNSTOPPABLE = regUpgrade("unstoppable", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.UNSTOPPABLE, 1, true).register();
        SPIRIT = regUpgrade("spirit", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SPIRIT, 1, true).register();
        NETHERITE_GOLD = regUpgrade("netherite_gold", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.NETHERITE_GOLD).register();
        ENCHANTED_NETHERITE_GOLD = regUpgrade("enchanted_netherite_gold", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ENCHANTED_NETHERITE_GOLD, 1, true).register();
        BELL_OF_AVICI = regUpgrade("bell_of_avici", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.BELL_OF_AVICI, 1, true)
                .properties(p -> p.rarity(Rarity.UNCOMMON)).register();
        DIAMOND_ATTACK = regUpgrade("diamond_attack", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.DIAMOND_ATTACK).register();
        ENCHANTED_DIAMOND_ATTACK = regUpgrade("enchanted_diamond_attack", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ENCHANTED_DIAMOND_ATTACK, 1, true).register();
        CRIMSON_ATTACK = regUpgrade("crimson_attack", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CRIMSON_ATTACK).register();
        ENCHANTED_CRIMSON_ATTACK = regUpgrade("enchanted_crimson_attack", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ENCHANTED_CRIMSON_ATTACK, 1, true).register();
        LIGHTNING_STORM = regUpgrade("lighting_storm", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.LIGHTNING_STORM, 1, true).register();
        ROCKET_FLIGHT = regUpgrade("rocket_flight", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ROCKET_FLIGHT).register();
        DRAGON_BREATH = regUpgrade("dragon_breath", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.DRAGON_BREATH).register();
        WITHER_EXTINCTION = regUpgrade("wither_extinction", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.WITHER_EXTINCTION, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        CHARGED_SHIELD = regUpgrade("charged_shield", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CHARGED_SHIELD).register();

        ARMOR_PIERCE = regUpgrade("armor_pierce", () -> GolemModifiers.ARMOR_BYPASS).register();
        MAGIC_RESISTANCE = regUpgrade("magic_resistance", () -> GolemModifiers.MAGIC_RES).register();
        DAMAGE_CAP = regUpgrade("damage_cap", () -> GolemModifiers.DAMAGE_CAP, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        MAGIC_IMMUNE = regUpgrade("magic_immune", () -> GolemModifiers.MAGIC_IMMUNE, 1, true).register();

        TOTEMIC = regUpgrade("totemic", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.TOTEMIC).register();
        ENCHANTED_TOTEMIC = regUpgrade("enchanted_totemic", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.TOTEMIC, 2, true).register();
        HERO = regUpgrade("hero", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.HERO).register();
        FLARE = regUpgrade("flare", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.FLARE).register();
        UNBREAKABLE = regUpgrade("unbreakable", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.UNBREAKABLE).register();
        INFINITE_AMMO = regUpgrade("infinite_ammo", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.INFINITE_AMMO).register();
        PROSPERITY = regUpgrade("prosperity", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.PROSPERITY).register();
        LORD = regUpgrade("lord", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.LORD).register();
        SNOW_TRAIL = regUpgrade("snow_trail", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SNOW_TRAIL).register();

        WINDMILL = regUpgrade("windmill", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.WINDMILL).register();
        BACKFLIP = regUpgrade("backflip", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.BACKFLIP).register();
        SWAP = regUpgrade("swap", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SWAP)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        LUNA = regUpgrade("luna", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.LUNA, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        WITCH = regUpgrade("witch", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.WITCH).register();

        LIQUID_CLEAR = regUpgrade("liquid_clear", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.LIQUID_CLEAR).register();

        QUICK_STRIKE = regUpgrade("quick_strike", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.QUICK_STRIKE).register();
        ANGLER = regUpgrade("angler", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ANGLER).register();
        DEATH_KNELL = regUpgrade("death_knell", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.DEATH_KNELL, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        ECHO_TRIO = regUpgrade("echo_trio", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ECHO_TRIO, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        END_VOID = regUpgrade("end_void", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.END_VOID).register();
        RIPTIDE = regUpgrade("riptide", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.RIPTIDE).register();
        IRON_UPGRADE = regUpgrade("iron_upgrade", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.IRON_UPGRADE).register();
        TRIDENT_FESTIVAL = regUpgrade("trident_festival", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.TRIDENT_FESTIVAL, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        ANVIL_SLAM = regUpgrade("anvil_slam", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ANVIL_SLAM).register();

        SELF_DESTRUCT = regUpgrade("self_destruct", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SELF_DESTRUCT).register();

        FIREBALL = regUpgrade("fireball", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.FIREBALL).register();
        BRUSH = regUpgrade("brush", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.BRUSH).register();
        BOMB_DISPOSAL = regUpgrade("bomb_disposal", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.BOMB_DISPOSAL).register();
        BACKSTEP = regUpgrade("backstep", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.BACKSTEP).register();
        CONQUEROR = regUpgrade("conqueror", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CONQUEROR).register();
        PROJECTILE_DODGE = regUpgrade("projectile_dodge", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.PROJECTILE_DODGE, 1, true)
                .properties(p -> p.rarity(Rarity.RARE).fireResistant()).register();

        BLAST_FURNACE = regUpgrade("blast_furnace", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.BLAST_FURNACE).register();
        FURNACE = regUpgrade("furnace", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.FURNACE).register();
        MINER = regUpgrade("mine", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.MINER).register();
        SCAV_BOX = regUpgrade("scav_box", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SCAV_BOX).register();
        LUMBERJACK = regUpgrade("lumberjack", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.LUMBERJACK).register();

        CREATIVE_SLOT_100 = regUpgrade("creative_slot_100", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CREATIVE_SLOT_100)
                .properties(p -> p.stacksTo(64)).register();
        CREATIVE_SLOT = regUpgrade("creative_slot", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CREATIVE_SLOT)
                .properties(p -> p.stacksTo(64)).register();
        MIND_CONTROL = regUpgrade("mind_control", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.MIND_CONTROL)
                .properties(p -> p.rarity(Rarity.EPIC)).register();

        GUARDIAN_LASER = regUpgrade("guardian_laser", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.GUARDIAN_LASER)
                .properties(p -> p.rarity(Rarity.UNCOMMON)).register();
        INDOMITABLE = regUpgrade("indomitable", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.INDOMITABLE, 1, true)
                .properties(p -> p.rarity(Rarity.RARE)).register();
        INVISIBILITY = regUpgrade("invisibility", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.INVISIBILITY).register();
        EXECUTIONER = regUpgrade("executioner", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.EXECUTIONER, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        FOCUSED_DEFENSE = regUpgrade("focused_defense", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.FOCUSED_DEFENSE).register();
        SONIC_BOOM = regUpgrade("sonic_boom", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SONIC_BOOM).register();
        SELF_REPAIR = regUpgrade("self_repair", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SELF_REPAIR).register();

        SUNLIGHT = regUpgrade("sunlight", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SUNLIGHT).register();
        OVERWORLD = regUpgrade("overworld", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.OVERWORLD).register();
        NETHER = regUpgrade("nether", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.NETHER).register();
        CONDUIT = regUpgrade("heart_of_the_sea", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CONDUIT).register();
        HYPOTHERMIA = regUpgrade("hypothermia", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.HYPOTHERMIA).register();
        VERSATILITY = regUpgrade("versatility", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.VERSATILITY, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        DISARM = regUpgrade("disarm", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.DISARM).register();
        END_OF_BEGINNING = regUpgrade("end_of_beginning", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.END_OF_BEGINNING, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        CORONA = regUpgrade("corona", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.CORONA).register();
        MOON_SHADOW = regUpgrade("moon_shadow", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.MOON_SHADOW).register();
        TIME_AXIS = regUpgrade("time_axis", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.TIME_AXIS, 1, true)
                .properties(p -> p.rarity(Rarity.EPIC)).register();
        UPSIDE_DOWN = regUpgrade("upside_down", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.UPSIDE_DOWN).register();
        REVERSE = regUpgrade("reverse", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.REVERSE).register();
        GHOST = regUpgrade("ghost", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.GHOST).register();
        SPYGLASS = regUpgrade("spyglass", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SPYGLASS).register();
        SHRINK = regUpgrade("shrink", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SHRINK).register();
        ENCHANT = regUpgrade("enchant", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.ENCHANT).register();
        SHIELD_BLOCK = regUpgrade("shield_block", () -> src.toi_et_moi.mgdp.init.MGDPModifiers.SHIELD_BLOCK).register();

        IRON_CURTAIN = MGDP.REGISTRATE.item("iron_curtain", p -> new IronCurtainItem(p.stacksTo(1).rarity(Rarity.EPIC)))
                .model((ctx, pvd) -> pvd.handheld(ctx, MGDP.loc("item/iron_curtain")))
                .defaultLang()
                .tab(MGDP_TAB.getKey())
                .register();
        BLACK_MOURNING = MGDP.REGISTRATE.item("black_mourning", p -> new BlackMourningItem(p.stacksTo(1).rarity(Rarity.EPIC)))
                .model((ctx, pvd) -> pvd.generated(ctx, MGDP.loc("item/equipments/black_mourning")))
                .defaultLang()
                .tab(MGDP_TAB.getKey())
                .register();

        SIMPLE_GOLEM_SPEAR = regSpear("simple_golem_spear", 10, 0, 10.0F, 2.0F, Rarity.EPIC, false).register();
        SIMPLE_IRON_GOLEM_SPEAR = regSpear("simple_iron_golem_spear", 6, 0.3, 4.0F, 2.0F, Rarity.COMMON, false).register();
        SIMPLE_DIAMOND_GOLEM_SPEAR = regSpear("simple_diamond_golem_spear", 8, 0.4, 4.0F, 2.0F, Rarity.COMMON, false).register();
        SIMPLE_NETHERITE_GOLEM_SPEAR = regSpear("simple_netherite_golem_spear", 10, 0.5, 4.0F, 2.0F, Rarity.COMMON, true).register();
    }

    /** 空方法，仅用于唤起本类加载（对齐 {@link GolemItems#register()} 的结构）。 */
    public static void register() {
    }

    /**
     * 注册升级物品（默认等级 1、无附魔光效）。
     * 对齐本体 {@code GolemItems.regUpgrade(id, mod)} 的语义。
     */
    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod) {
        return regUpgrade(id, mod, 1, false);
    }

    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod, int level, boolean foil) {
        return regUpgradeImpl(id, mod, level, foil).tag(MGTagGen.GOLEM_UPGRADES);
    }

    /**
     * 对齐本体 {@code GolemItems.regUpgradeImpl}：以本模组的 REGISTRATE 注册 SimpleUpgradeItem，
     * 生成带 {@code modulargolems:blue_arrow} predicate 的蓝/紫箭头覆盖模型，
     * 并同时放入本体的 upgrades 栏与本模组的 {@link #MGDP_TAB} 栏。
     */
    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgradeImpl(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod, int level, boolean foil) {
        return MGDP.REGISTRATE.item(id, p -> new SimpleUpgradeItem(p, mod.get()::get, level, foil))
                .model((ctx, pvd) -> pvd.generated(ctx, MGDP.loc("item/upgrades/" + id))
                        .override().predicate(ResourceLocation.fromNamespaceAndPath("modulargolems", "blue_arrow"), 0.5f)
                        .model(pvd.getBuilder(pvd.name(ctx) + "_purple")
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", MGDP.loc("item/upgrades/" + id))
                                .texture("layer1", ResourceLocation.fromNamespaceAndPath("modulargolems", "item/purple_arrow")))
                        .end()
                        .override().predicate(ResourceLocation.fromNamespaceAndPath("modulargolems", "blue_arrow"), 1)
                        .model(pvd.getBuilder(pvd.name(ctx) + "_blue")
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", MGDP.loc("item/upgrades/" + id))
                                .texture("layer1", ResourceLocation.fromNamespaceAndPath("modulargolems", "item/blue_arrow")))
                        .end())
                .removeTab(GolemItems.ITEMS.getKey())
                .tab(GolemItems.UPGRADES.getKey())
                .tab(MGDP_TAB.getKey());
    }

    /**
     * 注册简易傀儡长矛（{@link MetalGolemWeaponItem}）。
     * 对齐本体 {@code GolemWeaponType.SPEAR} 的注册方式：以 {@code long_weapon} 为底座模型，
     * GUI 视角使用扁平图标贴图，并复用原手写模型的第三/第一人称缩放（scale ×2）。
     */
    private static ItemBuilder<MetalGolemWeaponItem, L2Registrate> regSpear(String id, int attackDamage,
                                                                            double percentAttack, float range, float sweep,
                                                                            Rarity rarity, boolean fireResistant) {
        return MGDP.REGISTRATE.item(id, p -> {
                    Item.Properties props = p.stacksTo(1).rarity(rarity);
                    if (fireResistant) props = props.fireResistant();
                    return new MetalGolemWeaponItem(props, attackDamage, percentAttack, range, sweep);
                })
                .model((ctx, pvd) -> {
                    var base = pvd.getBuilder(ctx.getName())
                            .parent(new ModelFile.UncheckedModelFile("modulargolems:item/long_weapon"))
                            .texture("layer0", MGDP.loc("item/equipments/" + ctx.getName()));
                    // 复刻 src 手写模型的第三/第一人称显示缩放（相对 long_weapon 底座放大 ×2）
                    base.transforms()
                            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(2.0F).end()
                            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(2.0F).end()
                            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(2.0F).end()
                            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(2.0F).end();
                    //return base;
                })
                .defaultLang()
                .tab(MGDP_TAB.getKey());
    }

}