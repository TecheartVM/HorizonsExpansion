package techeart.horizonsexpansion.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockSapling extends SaplingBlock
{
    private final int flammability;
    private final int fireSpreadSpeed;

    public BlockSapling(Tree tree) { this(tree, 60, 30); }

    public BlockSapling(Tree tree, int flammability, int fireSpreadSpeed)
    {
        super(
                tree,
                Properties.of(Material.PLANT)
                        .instabreak()
                        .sound(SoundType.GRASS)
                        .randomTicks()
                        .noCollission()
        );
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }
}
