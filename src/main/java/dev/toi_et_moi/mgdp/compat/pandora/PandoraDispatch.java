package dev.toi_et_moi.mgdp.compat.pandora;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.curseofpandora.init.CurseOfPandora;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.Nullable;

public class PandoraDispatch extends ModDispatch {

    public static final String MODID = CurseOfPandora.MODID;

    public PandoraDispatch(){
        PandoraCompatRegistry.register();
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
