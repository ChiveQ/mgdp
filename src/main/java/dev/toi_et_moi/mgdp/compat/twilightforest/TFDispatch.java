package dev.toi_et_moi.mgdp.compat.twilightforest;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.Nullable;

public class TFDispatch extends ModDispatch {

    public static final String MODID = dev.xkmc.modulargolems.compat.materials.twilightforest.TFDispatch.MODID;

    public TFDispatch(){
        TFCompatRegistry.register();
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
