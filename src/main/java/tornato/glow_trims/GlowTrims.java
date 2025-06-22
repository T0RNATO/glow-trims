package tornato.glow_trims;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.minecraft.component.ComponentType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class GlowTrims implements ModInitializer {
    public static final ComponentType<Boolean> GLOW_TRIM_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            idOf("glow_trim"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static final RecipeSerializer<GlowTrimRecipe> GLOW_TRIM_RECIPE = Registry.register(Registries.RECIPE_SERIALIZER, idOf("glow_trim"), new GlowTrimRecipe.Serializer());

    public static Identifier idOf(String path) {
        return Identifier.of("glow_trims", path);
    }

    @Override
    public void onInitialize() {

    }
}
