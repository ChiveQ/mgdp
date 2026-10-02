package dev.toi_et_moi.mgdp.compat.youkai;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import dev.xkmc.youkaishomecoming.init.YoukaisHomecoming;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.Nullable;

public class YoukaiDispatch extends ModDispatch {

    public static final String MODID = YoukaisHomecoming.MODID;

    public YoukaiDispatch(){
        YoukaiCompatRegistry.register();
    }

    @Override
    protected void genLang(RegistrateLangProvider pvd) {
//        pvd.add("");
    }

    @Override
    public void genRecipe(RegistrateRecipeProvider pvd) {

    }

    @Override
    public @Nullable ConfigDataProvider getDataGen(DataGenerator gen) {
        return null;
    }
}
