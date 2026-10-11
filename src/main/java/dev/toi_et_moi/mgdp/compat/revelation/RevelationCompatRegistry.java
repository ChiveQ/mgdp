package dev.toi_et_moi.mgdp.compat.revelation;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.toi_et_moi.mgdp.compat.revelation.modifiers.*;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import net.minecraft.world.item.Rarity;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class RevelationCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<PyreLordModifier> THE_PYRE_LORD;
    public static final RegistryEntry<CruelModifier> THE_CRUEL;
    public static final RegistryEntry<WitchKingModifier> THE_WITCH_KING;
    public static final RegistryEntry<GreatShadowModifier> THE_GREAT_SHADOW;
    public static final RegistryEntry<DefilerModifier> THE_DEFILER;
    public static final RegistryEntry<DarkModifier> THE_DARK;
    public static final RegistryEntry<GloriousModifier> THE_GLORIOUS;
    public static final RegistryEntry<GenesisModifier> THE_GENESIS;
    public static final RegistryEntry<ApocalypseModifier> THE_APOCALYPSE;

    public static final ItemEntry<SimpleUpgradeItem>
            UP_PYRE_LORD,UP_CRUEL,UP_WITCH_KING,UP_GREAT_SHADOW,
            UP_DEFILER, UP_DARK,UP_GLORIOUS,UP_GENESIS,UP_APOCALYPSE;

    static{
        THE_PYRE_LORD = reg("the_pyre_lord", PyreLordModifier::new,
                "Blast Lord",
                "Apostle title: summons nether meteors, 4x fire/explosion damage.");
        THE_WITCH_KING = reg("the_witch_king", WitchKingModifier::new,
                "§cThe Witch King",
                "§5Apostle Title: applies all potion effects to allies/enemies, cleanses allies.");
        THE_CRUEL = reg("the_cruel", CruelModifier::new,
                "§cThe Cruel",
                "Frost aura, ice spear/storm/chunk attacks, +300% frost damage, freeze immunity.");
        THE_GREAT_SHADOW = reg("the_great_shadow", GreatShadowModifier::new,
                "§cThe Great Shadow",
                "Darkness/blindness aura, magic bolt barrages, +300% damage vs magic/undead/debuffed.");
        THE_DEFILER = reg("the_defiler", DefilerModifier::new,
                "§cThe Defiler",
                "Venom aura, acid pools, entangle vines, piercing poison quills, +300% damage vs debuffed/arthropods.");
        THE_DARK = reg("the_dark", DarkModifier::new,
                "§cThe Pitch-Black Shadow",
                "Wane aura, giant scythe slashes, +300% damage vs waned/dark targets, shadow walk in darkness.");
        THE_GLORIOUS = reg("the_glorious", GloriousModifier::new,
                "§cThe Glorious",
                "Resistance aura for allies, +300% damage vs bosses.");
        THE_GENESIS = reg("the_genesis", GenesisModifier::new,
                "§cThe Genesis",
                "Empowers all other apostle titles, x1.5 damage, immune while not targeting.");
        THE_APOCALYPSE = reg("the_apocalypse", ApocalypseModifier::new,
                "§cThe Apocalypse",
                "Void frame damage, doom/erosion/curse aura, strips target buffs, -50% damage taken.");

        UP_PYRE_LORD = regModUpgrade("the_pyre_lord",() -> THE_PYRE_LORD,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_WITCH_KING = regModUpgrade("the_witch_king",() -> THE_WITCH_KING,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_CRUEL = regModUpgrade("the_cruel",() -> THE_CRUEL,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_GREAT_SHADOW = regModUpgrade("the_great_shadow",() -> THE_GREAT_SHADOW,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_DEFILER = regModUpgrade("the_defiler",() -> THE_DEFILER,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_DARK = regModUpgrade("the_dark",() -> THE_DARK,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_GLORIOUS = regModUpgrade("the_glorious",() -> THE_DARK,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_GENESIS= regModUpgrade("the_genesis",() -> THE_DARK,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
        UP_APOCALYPSE = regModUpgrade("the_apocalypse",() -> THE_DARK,1,false,Rarity.EPIC,RevelationDispatch.MODID).register();
    }

}
