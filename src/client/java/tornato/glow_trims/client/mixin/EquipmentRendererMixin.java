package tornato.glow_trims.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import tornato.glow_trims.GlowTrims;

@Mixin(EquipmentRenderer.class)
public class EquipmentRendererMixin {
    @ModifyArg(
            method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"),
            index = 2
    )
    private int awd(int light, @Local(argsOnly = true) ItemStack stack) {
        return stack.getOrDefault(GlowTrims.GLOW_TRIM_COMPONENT, false) ? 255 : light;
    }
}
