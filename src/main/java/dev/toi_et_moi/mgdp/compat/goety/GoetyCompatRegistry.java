package dev.toi_et_moi.mgdp.compat.goety;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.toi_et_moi.mgdp.compat.goety.modifiers.BottlingModifier;
import dev.toi_et_moi.mgdp.compat.goety.modifiers.CroneModifier;
import dev.toi_et_moi.mgdp.compat.goety.modifiers.NecromancerModifier;
import dev.toi_et_moi.mgdp.compat.goety.modifiers.VoidEchoModifier;

import static dev.toi_et_moi.mgdp.init.registrate.MGDPItems.regModUpgrade;
import static dev.toi_et_moi.mgdp.init.registrate.MGDPModifiers.reg;

public class GoetyCompatRegistry {

    public static void register(){}

    public static final RegistryEntry<CroneModifier> CRONE;
    public static final RegistryEntry<BottlingModifier> BOTTLING;
    public static final RegistryEntry<NecromancerModifier> NECROMANCER;
    public static final RegistryEntry<VoidEchoModifier> VOID_ECHO;

    public static final ItemEntry<SimpleUpgradeItem> UP_CRONE, UP_BOTTLING,UP_NECROMANCER,UP_VOID_ECHO;

    static{
        // 升级
        CRONE = reg("crone", CroneModifier::new,
                "Crone",
                "Golem attacks with Goety brew effects and buffs allies.");
        BOTTLING = reg("bottling", BottlingModifier::new,
                "Bottling",
                "Golem applies bottling effect to its owner. Effect level equals upgrade level.");
        NECROMANCER = reg("necromancer", NecromancerModifier::new,
                "Necromancer",
                "Golem periodically summons zombie/skeleton minions to fight alongside it.");
        VOID_ECHO = reg("void_echo", VoidEchoModifier::new,
                "Void Echo",
                "Attacks deal void damage and apply Void Touched. Life steal, teleport dodge, and damage cap.");
        UP_CRONE = regModUpgrade("crone",() -> CRONE, GoetyDispatch.MODID).register();
        UP_BOTTLING = regModUpgrade("bottling", () -> BOTTLING, GoetyDispatch.MODID).register();
        UP_NECROMANCER = regModUpgrade("necromancer", () -> NECROMANCER, GoetyDispatch.MODID).register();
        UP_VOID_ECHO = regModUpgrade("void_echo",() -> VOID_ECHO, GoetyDispatch.MODID).register();
    }

}
