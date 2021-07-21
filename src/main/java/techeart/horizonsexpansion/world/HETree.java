package techeart.horizonsexpansion.world;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class HETree extends Tree
{
    private final ConfiguredFeature<BaseTreeFeatureConfig, ?> feature;

    public HETree(ConfiguredFeature<BaseTreeFeatureConfig, ?> feature) { this.feature = feature; }

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random p_225546_1_, boolean p_225546_2_)
    {
        return feature;
    }
}
