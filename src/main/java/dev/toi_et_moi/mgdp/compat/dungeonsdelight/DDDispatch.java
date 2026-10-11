package dev.toi_et_moi.mgdp.compat.dungeonsdelight;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.toi_et_moi.mgdp.init.MGDP;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import dev.xkmc.mob_weapon_api.api.simple.IHoldWeaponBehavior;
import dev.xkmc.mob_weapon_api.registry.RangedBehaviorFactory;
import dev.xkmc.mob_weapon_api.registry.RangedStatusPredicate;
import dev.xkmc.mob_weapon_api.registry.WeaponRegistry;
import dev.xkmc.mob_weapon_api.registry.WeaponStatus;
import dev.xkmc.modulargolems.compat.materials.common.ModDispatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DDDispatch extends ModDispatch {

    public static final String MODID = "dungeonsdelight";

    public static void init(){
        TagKey<Item> tagDD = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(DDDispatch.MODID, "cleavers"));
        RangedStatusPredicate pred = (ItemStack s) -> s.is(tagDD) ? Optional.of(WeaponStatus.RANGED) : Optional.empty();
        RangedBehaviorFactory<IHoldWeaponBehavior> factory = (LivingEntity user, ItemStack stack) -> new CleaverThrowBehavior();
        WeaponRegistry.HOLD.register(MGDP.loc("cleaver"), pred, factory, 5);
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
