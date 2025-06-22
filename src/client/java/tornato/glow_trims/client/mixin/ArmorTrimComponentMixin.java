package tornato.glow_trims.client.mixin;

import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tornato.glow_trims.GlowTrims;

import java.util.function.Consumer;

@Mixin(ArmorTrim.class)
public abstract class ArmorTrimComponentMixin {
    @Shadow @Final private RegistryEntry<ArmorTrimMaterial> material;

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    private void awd(Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type, ComponentsAccess components, CallbackInfo ci) {
        if (components.getOrDefault(GlowTrims.GLOW_TRIM_COMPONENT, false)) {
            textConsumer.accept(ScreenTexts.space().append(
                    Text.literal("Glowing").formatted(Formatting.BOLD).withColor(
                            this.material.value().description().getStyle().getColor().getRgb()
                    )
            ));
        }
    }
}
