package dev.toi_et_moi.mgdp.compat.revelation;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.Nullable;

public class RevelationDispatch extends ModDispatch {

    public static final String MODID = "goety_revelation";

    public RevelationDispatch(){
        RevelationCompatRegistry.register();
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
