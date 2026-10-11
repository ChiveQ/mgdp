package dev.toi_et_moi.mgdp.compat;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import dev.toi_et_moi.mgdp.compat.cataclysm.CataDispatch;
import dev.toi_et_moi.mgdp.compat.cataclysm.CataSlotTemplateRegistry;
import dev.toi_et_moi.mgdp.compat.create.CreateDispatch;
import dev.toi_et_moi.mgdp.compat.dungeonsdelight.DDDispatch;
import dev.toi_et_moi.mgdp.compat.goety.GoetyDispatch;
import dev.toi_et_moi.mgdp.compat.golemdungeons.GDDispatch;
import dev.toi_et_moi.mgdp.compat.golemmagicka.GolemMagickaDispatch;
import dev.toi_et_moi.mgdp.compat.ironsspellbooks.ISSDispatch;
import dev.toi_et_moi.mgdp.compat.ironsspellbooks.ISSSlotTemplateRegistry;
import dev.toi_et_moi.mgdp.compat.jerotes.JerotesDispatch;
import dev.toi_et_moi.mgdp.compat.l2complements.LCDispatch;
import dev.toi_et_moi.mgdp.compat.l2hostility.HostilityDispatch;
import dev.toi_et_moi.mgdp.compat.pandora.PandoraDispatch;
import dev.toi_et_moi.mgdp.compat.revelation.RevelationDispatch;
import dev.toi_et_moi.mgdp.compat.smc.SMCDispatch;
import dev.toi_et_moi.mgdp.compat.twilightforest.TFDispatch;
import dev.toi_et_moi.mgdp.compat.youkai.YoukaiDispatch;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraftforge.fml.ModList;


import java.util.ArrayList;
import java.util.List;

public class CompatManager {

    public static final List<ModDispatch> LIST = new ArrayList<>();

    public static void registerModifiers(){
        if(ModList.get().isLoaded(TFDispatch.MODID)) LIST.add(new TFDispatch());
        if(ModList.get().isLoaded(CreateDispatch.MODID)) LIST.add(new CreateDispatch());
        if(ModList.get().isLoaded(HostilityDispatch.MODID)) LIST.add(new HostilityDispatch());
        if(ModList.get().isLoaded(PandoraDispatch.MODID)) LIST.add(new PandoraDispatch());
        if(ModList.get().isLoaded(CataDispatch.MODID)) LIST.add(new CataDispatch());
        if(ModList.get().isLoaded(ISSDispatch.MODID)) LIST.add(new ISSDispatch());
        if(ModList.get().isLoaded(GolemMagickaDispatch.MODID)) LIST.add(new GolemMagickaDispatch());
        if(ModList.get().isLoaded(GoetyDispatch.MODID)) LIST.add(new GoetyDispatch());
        if(ModList.get().isLoaded(RevelationDispatch.MODID)) LIST.add(new RevelationDispatch());
        if(ModList.get().isLoaded(SMCDispatch.MODID)) LIST.add(new SMCDispatch());
        if(ModList.get().isLoaded(YoukaiDispatch.MODID)) LIST.add(new YoukaiDispatch());
    }

    public static void registerSummoners(){
        if(ModList.get().isLoaded(GDDispatch.MODID)) LIST.add(new GDDispatch());
    }

    public static void registerSlotTemplates(){
        if(ModList.get().isLoaded(LCDispatch.MODID)) LIST.add(new LCDispatch());
        if(ModList.get().isLoaded(CataDispatch.MODID)) CataSlotTemplateRegistry.register();
        if(ModList.get().isLoaded(GoetyDispatch.MODID)) ISSSlotTemplateRegistry.register();
        if(ModList.get().isLoaded(ISSDispatch.MODID)) ISSSlotTemplateRegistry.register();
        if(ModList.get().isLoaded(JerotesDispatch.MODID)) LIST.add(new JerotesDispatch());
    }

    public static void commonSetup(){
        for (ModDispatch dispatch : LIST) {
            dispatch.commonSetup();
        }
        if(ModList.get().isLoaded(DDDispatch.MODID)) DDDispatch.init();
        // TODO netmusic
    }

    public static void dispatchGenLang(RegistrateLangProvider pvd) {
        for (ModDispatch dispatch : LIST) {
            //dispatch.genLang(pvd);
        }
    }

}
