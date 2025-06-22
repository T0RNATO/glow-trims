package tornato.glow_trims;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Optional;

public class GlowTrimRecipe implements SmithingRecipe {
    @Override
    public boolean matches(SmithingRecipeInput input, World world) {
        return input.template().isEmpty() && this.base.test(input.base()) && this.addition.test(input.addition());
    }

    @Override
    public ItemStack craft(SmithingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        var out = input.base().copy();
        out.set(GlowTrims.GLOW_TRIM_COMPONENT, this.addition.test(new ItemStack(Items.GLOW_INK_SAC)));
        return out;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        // Copied from SmithingTrimRecipe#getResult
        ItemStack itemStack = new ItemStack(Items.IRON_CHESTPLATE);
        Optional<RegistryEntry.Reference<ArmorTrimPattern>> optional = registriesLookup.getWrapperOrThrow(RegistryKeys.TRIM_PATTERN).streamEntries().findFirst();
        Optional<RegistryEntry.Reference<ArmorTrimMaterial>> optional2 = registriesLookup.getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL)
                .getOptional(ArmorTrimMaterials.REDSTONE);
        if (optional.isPresent() && optional2.isPresent()) {
            itemStack.set(DataComponentTypes.TRIM, new ArmorTrim(optional2.get(), optional.get()));
        }

        return itemStack;
    }

    private final Ingredient base;
    private final Ingredient addition;

    public GlowTrimRecipe(Ingredient base, Ingredient addition) {
        this.base = base;
        this.addition = addition;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return GlowTrims.GLOW_TRIM_RECIPE;
    }

    @Override
    public boolean testTemplate(ItemStack stack) {
        return stack.isEmpty();
    }

    @Override
    public boolean testBase(ItemStack stack) {
        return base.test(stack);
    }

    @Override
    public boolean testAddition(ItemStack stack) {
        return addition.test(stack);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.GLOW_INK_SAC);
    }

    public static class Serializer implements RecipeSerializer<GlowTrimRecipe> {
        private static final MapCodec<GlowTrimRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter(o -> o.base),
                                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(o -> o.addition)
                        )
                        .apply(instance, GlowTrimRecipe::new)
        );
        @Override
        public MapCodec<GlowTrimRecipe> codec() {
            return CODEC;
        }

        public static final PacketCodec<RegistryByteBuf, GlowTrimRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                GlowTrimRecipe.Serializer::write, GlowTrimRecipe.Serializer::read
        );

        @Override
        public PacketCodec<RegistryByteBuf, GlowTrimRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static GlowTrimRecipe read(RegistryByteBuf buf) {
            Ingredient base = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient addition = Ingredient.PACKET_CODEC.decode(buf);
            return new GlowTrimRecipe(base, addition);
        }

        private static void write(RegistryByteBuf buf, GlowTrimRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.base);
            Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
        }
    }
}























