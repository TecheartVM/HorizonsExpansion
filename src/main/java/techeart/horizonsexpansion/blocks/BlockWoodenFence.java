package techeart.horizonsexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockWoodenFence extends FenceBlock
{
    private final int flammability;
    private final int fireSpreadSpeed;

    public BlockWoodenFence(BlockState defaultState) { this(defaultState, 5, 5); }

    public BlockWoodenFence(BlockState defaultState, int flammability, int fireSpreadSpeed)
    {
        super(Properties.copy(defaultState.getBlock()));
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }

    @Override
    public boolean connectsTo(BlockState state, boolean flag, Direction direction)
    {
        Block block = state.getBlock();
        return block instanceof BlockWoodenFence
                || block instanceof BlockWoodenGate && BlockWoodenGate.connectsToDirection(state, direction)
                || super.connectsTo(state, flag, direction);
    }
}
