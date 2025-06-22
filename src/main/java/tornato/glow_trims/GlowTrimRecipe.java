package tornato.glow_trims;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.Optional;

public class GlowTrimRecipe implements SmithingRecipe {
    @Override
    public ItemStack craft(SmithingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        var out = input.base().copy();
        out.set(GlowTrims.GLOW_TRIM_COMPONENT, this.addition.acceptsItem(Items.GLOW_INK_SAC.getRegistryEntry()));
        return out;
    }

    private final Ingredient base;
    private final Ingredient addition;
    private IngredientPlacement placement = null;

    public GlowTrimRecipe(Ingredient base, Ingredient addition) {
        this.base = base;
        this.addition = addition;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return GlowTrims.GLOW_TRIM_RECIPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.placement == null) {
            this.placement = IngredientPlacement.forMultipleSlots(List.of(Optional.empty(), Optional.of(this.base()), this.addition()));
        }
        return this.placement;
    }

    @Override
    public Optional<Ingredient> template() {
        return Optional.empty();
    }

    @Override
    public Ingredient base() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> addition() {
        return Optional.of(addition);
    }

//    @Override
//    public List<RecipeDisplay> getDisplays() {
//        return List.of(
//                new SmithingRecipeDisplay(
//                        Ingredient.toDisplay(Optional.empty()),
//                        this.base.toDisplay(),
//                        Ingredient.toDisplay(Optional.of(this.addition)),
//                        Ingredient.toDisplay(Optional.empty()),
//                        new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE)
//                )
//        );
//    }

    public static class Serializer implements RecipeSerializer<GlowTrimRecipe> {
        private static final MapCodec<GlowTrimRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(o -> o.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(o -> o.addition)
                        )
                        .apply(instance, GlowTrimRecipe::new)
        );
        @Override
        public MapCodec<GlowTrimRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, GlowTrimRecipe> packetCodec() {
            return null;
        }
    }
}























