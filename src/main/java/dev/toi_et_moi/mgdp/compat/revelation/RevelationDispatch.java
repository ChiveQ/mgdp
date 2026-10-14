package dev.toi_et_moi.mgdp.compat.revelation;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.toi_et_moi.mgdp.compat.revelation.handlers.ApocalypseConversionHandler;
import dev.toi_et_moi.mgdp.compat.revelation.handlers.AtrociousArrowRainHandler;
import dev.toi_et_moi.mgdp.compat.revelation.handlers.ProfaneStealHandler;
import dev.toi_et_moi.mgdp.compat.revelation.handlers.RevelationArrowHandler;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;

public class RevelationDispatch extends ModDispatch {

    public static final String MODID = "goety_revelation";

    public RevelationDispatch(){
        RevelationCompatRegistry.register();
        MinecraftForge.EVENT_BUS.register(ApocalypseConversionHandler.class);
        MinecraftForge.EVENT_BUS.register(AtrociousArrowRainHandler.class);
        MinecraftForge.EVENT_BUS.register(ProfaneStealHandler.class);
        MinecraftForge.EVENT_BUS.register(RevelationArrowHandler.class);
    }

    @Override
    protected void genLang(RegistrateLangProvider registrateLangProvider) {

    }

    @Override
    public void genRecipe(RegistrateRecipeProvider registrateRecipeProvider) {

    }

    @Override
    public @Nullable ConfigDataProvider getDataGen(DataGenerator dataGenerator) {
        return null;
    }
}
