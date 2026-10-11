package dev.toi_et_moi.mgdp.compat.goety;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;

public class GoetyDispatch extends ModDispatch {

    public static final String MODID = "goety";

    public GoetyDispatch(){
        GoetyCompatRegistry.register();
        MinecraftForge.EVENT_BUS.register(ApostleDefenseHandler.class);
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
