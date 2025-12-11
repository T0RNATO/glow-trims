package tornato.glow_trims;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class GlowTrims implements ModInitializer {
    public static final DataComponentType<Boolean> GLOW_TRIM_COMPONENT = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            idOf("glow_trim"),
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build()
    );

    public static final RecipeSerializer<GlowTrimRecipe> GLOW_TRIM_RECIPE = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, idOf("glow_trim"), new GlowTrimRecipe.Serializer());

    public static ResourceLocation idOf(String path) {
        return ResourceLocation.fromNamespaceAndPath("glow_trims", path);
    }

    @Override
    public void onInitialize() {

    }
}
