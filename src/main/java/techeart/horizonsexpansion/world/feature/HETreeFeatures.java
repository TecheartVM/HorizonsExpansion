package techeart.horizonsexpansion.world.feature;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import techeart.horizonsexpansion.MainClass;
import techeart.horizonsexpansion.util.RegistryHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class HETreeFeatures
{
    public static final List<ConfiguredFeature<?,?>> LIST = new ArrayList<>();

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> POPLAR = registerFeature(
            "tree_poplar",
            Feature.TREE.configured(
                    new BaseTreeFeatureConfig.Builder(
                            new SimpleBlockStateProvider(RegistryHandler.WOOD_POPLAR.getLog().getBlock().defaultBlockState()),
                            new SimpleBlockStateProvider(RegistryHandler.WOOD_POPLAR.getLeaves().getBlock().defaultBlockState()),
                            new FancyFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(1), 1),
                            new StraightTrunkPlacer(5, 5, 5),
                            new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))
                    ).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()
            )
    );


    protected static <FC extends IFeatureConfig, F extends Feature<FC>>
                     ConfiguredFeature<FC, F> registerFeature(String name, ConfiguredFeature<FC, F> feature)
    {
        return Registry.register(
                WorldGenRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(MainClass.MODID, name),
                feature
        );
    }
}
