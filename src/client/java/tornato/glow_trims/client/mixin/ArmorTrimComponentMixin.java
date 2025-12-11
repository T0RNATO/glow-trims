package tornato.glow_trims.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tornato.glow_trims.GlowTrims;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

@Mixin(ArmorTrim.class)
public abstract class ArmorTrimComponentMixin {
    @Shadow @Final private Holder<TrimMaterial> material;

    @Inject(method = "addToTooltip", at = @At("TAIL"))
    private void awd(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components, CallbackInfo ci) {
        if (components.getOrDefault(GlowTrims.GLOW_TRIM_COMPONENT, false)) {
            textConsumer.accept(CommonComponents.space().append(
                    Component.literal("Glowing").withStyle(ChatFormatting.BOLD).withColor(
                            this.material.value().description().getStyle().getColor().getValue()
                    )
            ));
        }
    }
}
