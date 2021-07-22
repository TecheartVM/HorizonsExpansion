package techeart.horizonsexpansion.world;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class HETree extends Tree
{
    private final Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> feature;

    public HETree(Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> feature) { this.feature = feature; }

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random p_225546_1_, boolean p_225546_2_)
    {
        return feature.get();
    }
}
