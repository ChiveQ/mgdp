package dev.toi_et_moi.mgdp.content.modifier.combat;

import dev.toi_et_moi.mgdp.content.modifier.base.AttributeOperationModifier;
import dev.xkmc.modulargolems.content.entity.common.AbstractGolemEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantedCrimsonAttackModifier extends AttributeOperationModifier {

    public EnchantedCrimsonAttackModifier() {
        super(
                new AttrEntry(() -> Attributes.ATTACK_DAMAGE, "enchanted_crimson_attack",
                        AttributeModifier.Operation.MULTIPLY_TOTAL, 1.0),
                new AttrEntry(() -> Attributes.ARMOR, "crimson_armor",
                        AttributeModifier.Operation.MULTIPLY_BASE, -1.0),
                new AttrEntry(() -> Attributes.ARMOR_TOUGHNESS, "crimson_tough",
                        AttributeModifier.Operation.MULTIPLY_BASE, -1.0)
        );
    }

    @Override
    public void onHurtTarget(AbstractGolemEntity<?, ?> golem, LivingHurtEvent event, int level) {
        event.getEntity().invulnerableTime = 0; // 无视无敌帧
        golem.heal(event.getAmount() * 0.5F); // 吸血效果
    }

}
