package dev.toi_et_moi.mgdp.compat.youkai;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.modulargolems.content.item.upgrade.SimpleUpgradeItem;
import dev.xkmc.modulargolems.content.modifier.base.PotionDefenseModifier;
import dev.xkmc.youkaishomecoming.init.registrate.YHEffects;

import static dev.xkmc.modulargolems.init.registrate.GolemItems.regModUpgrade;
import static dev.xkmc.modulargolems.init.registrate.GolemModifiers.reg;

public class YoukaiCompatRegistry {

    public static final RegistryEntry<PotionDefenseModifier> PHANTOM;
    public static final RegistryEntry<SimpleUpgradeItem> UPGRADE_PHANTOM;

    static{
        PHANTOM = reg("phantom", () -> new PotionDefenseModifier(2, YHEffects.UDUMBARA), // 即"phantom"
                "Phantom",
                "Golem gains Phantom effect every second. Level = effect level.");
        UPGRADE_PHANTOM = regModUpgrade("phantom",() -> PHANTOM,YoukaiDispatch.MODID).lang("").register();// TODO 等级校验？
    }

    public static void register() {}


}
