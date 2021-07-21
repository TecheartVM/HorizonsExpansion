package techeart.horizonsexpansion.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockWoodenStairs extends StairsBlock
{
    private final int flammability;
    private final int fireSpreadSpeed;

    public BlockWoodenStairs(BlockState defaultState) { this(defaultState, 5, 5); }

    public BlockWoodenStairs(BlockState defaultState, int flammability, int fireSpreadSpeed)
    {
        super(defaultState, Properties.copy(defaultState.getBlock()));
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }
}
