package dev.toi_et_moi.mgdp.init.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.toi_et_moi.mgdp.compat.CompatManager;
import dev.xkmc.modulargolems.init.ModularGolems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * 语言文件生成
 * 例同MGLangData
 * */
public enum MGDPLangData {

    ;

    private final String key, def;
    private final int arg;
    private final ChatFormatting format;

    MGDPLangData(String key, String def, int arg, @Nullable ChatFormatting format) {
        this.key = ModularGolems.MODID + "." + key;
        this.def = def;
        this.arg = arg;
        this.format = format;
    }

    public String key() {
        return key;
    }

    public static String asId(String name) {
        return name.toLowerCase(Locale.ROOT);
    }

    public static MutableComponent getTranslate(String s) {
        return Component.translatable(ModularGolems.MODID + "." + s);
    }

    public MutableComponent get(Object... args) {
        if (args.length != arg)
            throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
        MutableComponent ans = Component.translatable(key, args);
        if (format != null) {
            return ans.withStyle(format);
        }
        return ans;
    }

    public static void genLang(RegistrateLangProvider pvd) {
        for (MGDPLangData lang : MGDPLangData.values()) {
            pvd.add(lang.key, lang.def);
        }
        CompatManager.dispatchGenLang(pvd);
    }

}
