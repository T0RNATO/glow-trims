package tornato.glow_trims;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import java.util.List;
import java.util.Optional;

@MethodsReturnNonnullByDefault
public class GlowTrimRecipe implements SmithingRecipe {
    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider registries) {
        var out = input.base().copy();
        out.set(GlowTrims.GLOW_TRIM_COMPONENT, this.addition.acceptsItem(Items.GLOW_INK_SAC.builtInRegistryHolder()));
        return out;
    }

    private final Ingredient base;
    private final Ingredient addition;
    private PlacementInfo placement = null;

    public GlowTrimRecipe(Ingredient base, Ingredient addition) {
        this.base = base;
        this.addition = addition;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return GlowTrims.GLOW_TRIM_RECIPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placement == null) {
            this.placement = PlacementInfo.createFromOptionals(List.of(Optional.empty(), Optional.of(this.baseIngredient()), this.additionIngredient()));
        }
        return this.placement;
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.empty();
    }

    @Override
    public Ingredient baseIngredient() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
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
        public StreamCodec<RegistryFriendlyByteBuf, GlowTrimRecipe> streamCodec() {
            return null;
        }
    }
}























