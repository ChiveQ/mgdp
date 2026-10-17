package dev.toi_et_moi.mgdp.init.registrate;

import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.compat.CompatManager;
import dev.toi_et_moi.mgdp.init.MGDP;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.modulargolems.content.item.equipments.MetalGolemWeaponItem;
import dev.xkmc.modulargolems.content.item.upgrade.AddSlotTemplate;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.base.GolemModifier;
import dev.xkmc.modulargolems.init.ModularGolems;
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
import src.toi_et_moi.mgdp.init.IronCurtainItem;

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
 */
public class MGDPItems {

    // 本模组专属创造物品栏（mgdp 升级物品同时进入本体的 upgrades 栏与本栏）
    public static final RegistryEntry<CreativeModeTab> MGDP_TAB;

    // 独立物品（不依赖其它模组）
    public static final ItemEntry<IronCurtainItem> IRON_CURTAIN;
    public static final ItemEntry<BlackMourningItem> BLACK_MOURNING;
    public static final ItemEntry<MetalGolemWeaponItem>
            SIMPLE_GOLEM_SPEAR, SIMPLE_IRON_GOLEM_SPEAR, SIMPLE_DIAMOND_GOLEM_SPEAR, SIMPLE_NETHERITE_GOLEM_SPEAR;

    // 升级物品（不依赖其它模组） 从初序
    public static final ItemEntry<SimpleUpgradeItem>
            HARVEST_CROP, FLIGHT, POTION_AURA, REBIRTH, UNSTOPPABLE, SPIRIT,
            NETHERITE_GOLD, ENCHANTED_NETHERITE_GOLD, BELL_OF_AVICI,
            DIAMOND_ATTACK, ENCHANTED_DIAMOND_ATTACK, CRIMSON_ATTACK, ENCHANTED_CRIMSON_ATTACK,
            LIGHTNING_STORM, ROCKET_FLIGHT, DRAGON_BREATH, WITHER_EXTINCTION, CHARGED_SHIELD,
            HYPOTHERMIA,CONDUIT,OVERWORLD, NETHER,SUNLIGHT,CORONA,MOON_SHADOW, TIME_AXIS,
            UPSIDE_DOWN, REVERSE, GHOST, SPYGLASS, SHRINK,SELF_REPAIR,SONIC_BOOM,
            FOCUSED_DEFENSE,EXECUTIONER,INVISIBILITY,GUARDIAN_LASER,INDOMITABLE,
            ARMOR_PIERCE, MAGIC_RESISTANCE,VERSATILITY, DAMAGE_CAP, TOTEMIC, ENCHANTED_TOTEMIC,
            ENCHANT,SELF_DESTRUCT, FIREBALL, HERO, FLARE, UNBREAKABLE, INFINITE_AMMO,
            QUICK_STRIKE, ANGLER,ANVIL_SLAM,TRIDENT_FESTIVAL,IRON_UPGRADE,DISARM,
            RIPTIDE,END_VOID,END_OF_BEGINNING,DEATH_KNELL, ECHO_TRIO, MIND_CONTROL,
            CREATIVE_SLOT,CREATIVE_SLOT_100,BRUSH, BOMB_DISPOSAL,PROJECTILE_DODGE,
            SHIELD_BLOCK,CONQUEROR,BACKSTEP, PROSPERITY,LIQUID_CLEAR,
            BLAST_FURNACE, FURNACE, MINER, SCAV_BOX, LUMBERJACK,MAGIC_IMMUNE,
            LORD, SNOW_TRAIL,SWAP, LUNA,BACKFLIP, WINDMILL,WITCH;


    static {
        // 标签页
        MGDP_TAB = MGDP.REGISTRATE.buildModCreativeTab("mgdp_tab", "MG: Decisive Plan - Main",
                    b -> b.icon(MGDPItems.FLIGHT::asStack).withSearchBar());
        // 手杖
        {
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
        }
        // 武器
        {
            SIMPLE_GOLEM_SPEAR = regSpear("simple_golem_spear", 10, 0, 10.0F, 2.0F, Rarity.EPIC, false).register();
            SIMPLE_IRON_GOLEM_SPEAR = regSpear("simple_iron_golem_spear", 6, 0.3, 4.0F, 2.0F, Rarity.COMMON, false).register();
            SIMPLE_DIAMOND_GOLEM_SPEAR = regSpear("simple_diamond_golem_spear", 8, 0.4, 4.0F, 2.0F, Rarity.COMMON, false).register();
            SIMPLE_NETHERITE_GOLEM_SPEAR = regSpear("simple_netherite_golem_spear", 10, 0.5, 4.0F, 2.0F, Rarity.COMMON, true).register();
        }
        // 扩充模板
        CompatManager.registerSlotTemplates();
        // 召唤器
        CompatManager.registerSummoners();
        // 升级
        {
            HARVEST_CROP = regUpgrade("harvest_crop", () -> MGDPModifiers.HARVEST_CROP).register();
            FLIGHT = regUpgrade("flight", () -> MGDPModifiers.FLIGHT, Rarity.EPIC).register();
            ROCKET_FLIGHT = regUpgrade("rocket_flight", () -> MGDPModifiers.ROCKET_FLIGHT).register();
            SPIRIT = regUpgrade("spirit", () -> MGDPModifiers.SPIRIT, 1, true).register();
            POTION_AURA = regUpgrade("potion_aura", () -> MGDPModifiers.POTION_AURA, Rarity.EPIC).register();
            REBIRTH = regUpgrade("rebirth", () -> MGDPModifiers.REBIRTH, Rarity.EPIC).register();
            UNSTOPPABLE = regUpgrade("unstoppable", () -> MGDPModifiers.UNSTOPPABLE, 1, true).register();
            NETHERITE_GOLD = regUpgrade("netherite_gold", () -> MGDPModifiers.NETHERITE_GOLD).lang("Netherite Gold Apple Upgrade").register();
            ENCHANTED_NETHERITE_GOLD = regUpgrade("enchanted_netherite_gold", () -> MGDPModifiers.ENCHANTED_NETHERITE_GOLD, 1, true).lang("Enchanted Netherite Gold Apple Upgrade").register();
            BELL_OF_AVICI = regUpgrade("bell_of_avici", () -> MGDPModifiers.BELL_OF_AVICI, Rarity.UNCOMMON).lang("Bell of Avici Upgrade").register();
            DIAMOND_ATTACK = regUpgrade("diamond_attack", () -> MGDPModifiers.DIAMOND_ATTACK).register();
            ENCHANTED_DIAMOND_ATTACK = regUpgrade("enchanted_diamond_attack", () -> MGDPModifiers.ENCHANTED_DIAMOND_ATTACK, 1, true).register();
            CRIMSON_ATTACK = regUpgrade("crimson_attack", () -> MGDPModifiers.CRIMSON_ATTACK).register();
            ENCHANTED_CRIMSON_ATTACK = regUpgrade("enchanted_crimson_attack", () -> MGDPModifiers.ENCHANTED_CRIMSON_ATTACK, 1, true).register();
            LIGHTNING_STORM = regUpgrade("lighting_storm", () -> MGDPModifiers.LIGHTNING_STORM, 1, true).lang("Weather Control Device Upgrade").register();
            DRAGON_BREATH = regUpgrade("dragon_breath", () -> MGDPModifiers.DRAGON_BREATH).register();
            WITHER_EXTINCTION = regUpgrade("wither_extinction", () -> MGDPModifiers.WITHER_EXTINCTION, Rarity.EPIC).register();
            CHARGED_SHIELD = regUpgrade("charged_shield", () -> MGDPModifiers.CHARGED_SHIELD).register();
            HYPOTHERMIA = regUpgrade("hypothermia", () -> MGDPModifiers.HYPOTHERMIA).register();
            CONDUIT = regUpgrade("heart_of_the_sea", () -> MGDPModifiers.CONDUIT).lang("Conduit Upgrade").register();
            OVERWORLD = regUpgrade("overworld", () -> MGDPModifiers.OVERWORLD).lang("Primordial Earth Upgrade").register();
            NETHER = regUpgrade("nether", () -> MGDPModifiers.NETHER).lang("Blazing Inferno Upgrade").register();
            SUNLIGHT = regUpgrade("sunlight", () -> MGDPModifiers.SUNLIGHT).lang("Radiance Upgrade").register();
            CORONA = regUpgrade("corona", () -> MGDPModifiers.CORONA).register();
            MOON_SHADOW = regUpgrade("moon_shadow", () -> MGDPModifiers.MOON_SHADOW).register();
            TIME_AXIS = regUpgrade("time_axis", () -> MGDPModifiers.TIME_AXIS, Rarity.EPIC).lang("§6Time Axis Upgrade").register();
            UPSIDE_DOWN = regUpgrade("upside_down", () -> MGDPModifiers.UPSIDE_DOWN).register();
            REVERSE = regUpgrade("reverse", () -> MGDPModifiers.REVERSE).register();
            GHOST = regUpgrade("ghost", () -> MGDPModifiers.GHOST).register();
            SPYGLASS = regUpgrade("spyglass", () -> MGDPModifiers.SPYGLASS).register();
            SHRINK = regUpgrade("shrink", () -> MGDPModifiers.SHRINK).register();
            SELF_REPAIR = regUpgrade("self_repair", () -> MGDPModifiers.SELF_REPAIR).register();
            SONIC_BOOM = regUpgrade("sonic_boom", () -> MGDPModifiers.SONIC_BOOM).register();
            FOCUSED_DEFENSE = regUpgrade("focused_defense", () -> MGDPModifiers.FOCUSED_DEFENSE).register();
            EXECUTIONER = regUpgrade("executioner", () -> MGDPModifiers.EXECUTIONER, Rarity.EPIC).register();
            INVISIBILITY = regUpgrade("invisibility", () -> MGDPModifiers.INVISIBILITY).register();
            GUARDIAN_LASER = regUpgrade("guardian_laser", () -> MGDPModifiers.GUARDIAN_LASER, 1,false,Rarity.UNCOMMON).lang("Guardian Upgrade").register();
            INDOMITABLE = regUpgrade("indomitable", () -> MGDPModifiers.INDOMITABLE, Rarity.RARE).register();
            ARMOR_PIERCE = regUpgrade("armor_pierce", () -> GolemModifiers.ARMOR_BYPASS).register();
            MAGIC_RESISTANCE = regUpgrade("magic_resistance", () -> GolemModifiers.MAGIC_RES).register();
            VERSATILITY = regUpgrade("versatility", () -> MGDPModifiers.VERSATILITY, Rarity.EPIC).register();
            DAMAGE_CAP = regUpgrade("damage_cap", () -> GolemModifiers.DAMAGE_CAP, Rarity.EPIC).register();
            TOTEMIC = regUpgrade("totemic", () -> MGDPModifiers.TOTEMIC).lang("Totemic Apple Upgrade").register();
            ENCHANTED_TOTEMIC = regUpgrade("enchanted_totemic", () -> MGDPModifiers.TOTEMIC, 2, true).lang("Enchanted Totemic Apple Upgrade").register();
            ENCHANT = regUpgrade("enchant", () -> MGDPModifiers.ENCHANT).lang("Auto-Enchant Upgrade").register();
            SELF_DESTRUCT = regUpgrade("self_destruct", () -> MGDPModifiers.SELF_DESTRUCT).register();
            FIREBALL = regUpgrade("fireball", () -> MGDPModifiers.FIREBALL).lang("Fireball Attack Upgrade").register();
            HERO = regUpgrade("hero", () -> MGDPModifiers.HERO).lang("Hero of the Village Upgrade").register();
            FLARE = regUpgrade("flare", () -> MGDPModifiers.FLARE).lang("Illumination Upgrade").register();
            UNBREAKABLE = regUpgrade("unbreakable", () -> MGDPModifiers.UNBREAKABLE).register();
            INFINITE_AMMO = regUpgrade("infinite_ammo", () -> MGDPModifiers.INFINITE_AMMO).register();
            QUICK_STRIKE = regUpgrade("quick_strike", () -> MGDPModifiers.QUICK_STRIKE).register();
            ANGLER = regUpgrade("angler", () -> MGDPModifiers.ANGLER).register();
            ANVIL_SLAM = regUpgrade("anvil_slam", () -> MGDPModifiers.ANVIL_SLAM).register();
            TRIDENT_FESTIVAL = regUpgrade("trident_festival", () -> MGDPModifiers.TRIDENT_FESTIVAL, Rarity.EPIC).register();
            IRON_UPGRADE = regUpgrade("iron_upgrade", () -> MGDPModifiers.IRON_UPGRADE).lang("Iron Upgrade").register();
            DISARM = regUpgrade("disarm", () -> MGDPModifiers.DISARM).register();
            RIPTIDE = regUpgrade("riptide", () -> MGDPModifiers.RIPTIDE).register();
            END_VOID = regUpgrade("end_void", () -> MGDPModifiers.END_VOID).register();
            END_OF_BEGINNING = regUpgrade("end_of_beginning", () -> MGDPModifiers.END_OF_BEGINNING, Rarity.EPIC).lang("§6End of Beginning").register();
            DEATH_KNELL = regUpgrade("death_knell", () -> MGDPModifiers.DEATH_KNELL, Rarity.EPIC).register();
            ECHO_TRIO = regUpgrade("echo_trio", () -> MGDPModifiers.ECHO_TRIO, Rarity.EPIC).register();
            MIND_CONTROL = regUpgrade("mind_control", () -> MGDPModifiers.MIND_CONTROL, 1,false,Rarity.EPIC).register();
            CREATIVE_SLOT = regUpgrade("creative_slot", () -> MGDPModifiers.CREATIVE_SLOT).lang("Creative +1 Slot").register();
            CREATIVE_SLOT_100 = regUpgrade("creative_slot_100", () -> MGDPModifiers.CREATIVE_SLOT_100).lang("Creative +100 Slots").register();
            BRUSH = regUpgrade("brush", () -> MGDPModifiers.BRUSH).lang("Archaeology Upgrade").register();
            BOMB_DISPOSAL = regUpgrade("bomb_disposal", () -> MGDPModifiers.BOMB_DISPOSAL).lang("Bomb Disposal Expert Upgrade").register();
            PROJECTILE_DODGE = regUpgrade("projectile_dodge", () -> MGDPModifiers.PROJECTILE_DODGE, 1, true)
                    .properties(p -> p.rarity(Rarity.RARE).fireResistant()).register();
            SHIELD_BLOCK = regUpgrade("shield_block", () -> MGDPModifiers.SHIELD_BLOCK).register();
            CONQUEROR = regUpgrade("conqueror", () -> MGDPModifiers.CONQUEROR).register();
            BACKSTEP = regUpgrade("backstep", () -> MGDPModifiers.BACKSTEP).register();
            PROSPERITY = regUpgrade("prosperity", () -> MGDPModifiers.PROSPERITY).register();
            LIQUID_CLEAR = regUpgrade("liquid_clear", () -> MGDPModifiers.LIQUID_CLEAR).register();
            BLAST_FURNACE = regUpgrade("blast_furnace", () -> MGDPModifiers.BLAST_FURNACE).register();
            FURNACE = regUpgrade("furnace", () -> MGDPModifiers.FURNACE).register();
            MINER = regUpgrade("mine", () -> MGDPModifiers.MINER).lang("Mining Upgrade").register();
            SCAV_BOX = regUpgrade("scav_box", () -> MGDPModifiers.SCAV_BOX).register();
            LUMBERJACK = regUpgrade("lumberjack", () -> MGDPModifiers.LUMBERJACK).register();
            MAGIC_IMMUNE = regUpgrade("magic_immune", () -> GolemModifiers.MAGIC_IMMUNE, 1, true).lang("Magic Immunity Upgrade").register();
            LORD = regUpgrade("lord", () -> MGDPModifiers.LORD).register();
            SNOW_TRAIL = regUpgrade("snow_trail", () -> MGDPModifiers.SNOW_TRAIL).register();
            SWAP = regUpgrade("swap", () -> MGDPModifiers.SWAP, 1,false,Rarity.EPIC).register();
            LUNA = regUpgrade("luna", () -> MGDPModifiers.LUNA, Rarity.EPIC).register();
            BACKFLIP = regUpgrade("backflip", () -> MGDPModifiers.BACKFLIP).register();
            WINDMILL = regUpgrade("windmill", () -> MGDPModifiers.WINDMILL).register();
            WITCH = regUpgrade("witch", () -> MGDPModifiers.WITCH).register();
        }
        // 联动升级
        CompatManager.registerModifiers();
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

    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod,Rarity rarity) {
        return regUpgrade(id,mod,1,true,rarity);
    }

    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod,int lv, boolean foil,Rarity rarity) {
        return regUpgrade(id, mod, lv,foil).properties(p -> p.rarity(rarity));
    }

    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod, int lv, boolean foil) {
        return regUpgradeImpl(id, mod, lv, foil, MGDP.MODID).tag(MGTagGen.GOLEM_UPGRADES);
    }

    public static ItemBuilder<SimpleUpgradeItem, L2Registrate> regModUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod, int lv, boolean foil, String modid) {
        var reg = regUpgradeImpl(id, mod, lv, foil, modid);
        reg.setData(ProviderType.ITEM_TAGS, (a, b) -> b.addTag(MGTagGen.GOLEM_UPGRADES).addOptional(reg.get().getId()));
        return reg;
    }

    public static ItemBuilder<SimpleUpgradeItem, L2Registrate> regModUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod, String modid) {
        return regModUpgrade(id, mod, 1, false, modid);
    }

    public static ItemBuilder<SimpleUpgradeItem, L2Registrate> regModUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod,int lv, boolean foil,Rarity rarity,String modid) {
        return regModUpgrade(id, mod, lv, foil,modid).properties(p -> p.rarity(rarity));
    }

    public static ItemBuilder<SimpleUpgradeItem, L2Registrate> regModUpgrade(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod,Rarity rarity,String modid) {
        return regModUpgrade(id,mod,1,true,rarity,modid);
    }

    /**
     * 对齐本体 {@code GolemItems.regUpgradeImpl}：以本模组的 REGISTRATE 注册 SimpleUpgradeItem，
     * 生成带 {@code modulargolems:blue_arrow} predicate 的蓝/紫箭头覆盖模型，
     * 并同时放入本体的 upgrades 栏与本模组的 {@link #MGDP_TAB} 栏。
     */
    private static ItemBuilder<SimpleUpgradeItem, L2Registrate> regUpgradeImpl(String id, Supplier<RegistryEntry<? extends GolemModifier>> mod, int level, boolean foil,String modid) {
        return MGDP.REGISTRATE.item(id, p -> new SimpleUpgradeItem(p, mod.get()::get, level, foil))
                .model((ctx, pvd) -> pvd.generated(ctx, ResourceLocation.fromNamespaceAndPath(modid, "item/upgrades/" + id))
                        .override().predicate(ModularGolems.loc( "blue_arrow"), 0.5f)
                        .model(pvd.getBuilder(pvd.name(ctx) + "_purple")
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid,"item/upgrades/" + id))
                                .texture("layer1", ModularGolems.loc("item/purple_arrow")))
                        .end()
                        .override().predicate(ResourceLocation.fromNamespaceAndPath(ModularGolems.MODID, "blue_arrow"), 1)
                        .model(pvd.getBuilder(pvd.name(ctx) + "_blue")
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", ResourceLocation.fromNamespaceAndPath(modid,"item/upgrades/" + id))
                                .texture("layer1", ModularGolems.loc("item/blue_arrow")))
                        .end())
                .lang(RegistrateLangProvider.toEnglishName(id) + " Upgrade") // 此処異MG
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

    public static ItemBuilder<AddSlotTemplate, L2Registrate> regAddSlotTemplate(String id,Supplier<RegistryEntry<? extends GolemModifier>> mod){
        return MGDP.REGISTRATE.item(id,p -> new AddSlotTemplate(p, mod.get()))
                .model((ctx,pvd) -> pvd.generated(ctx,pvd.modLoc("item/expansion/"+id)))
                .defaultLang()
                .tab(GolemItems.ITEMS.getKey())
                .tab(MGDP_TAB.getKey());
    }

}