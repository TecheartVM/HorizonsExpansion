package techeart.horizonsexpansion.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockWoodenSlab extends SlabBlock
{
    private final int flammability;
    private final int fireSpreadSpeed;

    public BlockWoodenSlab(BlockState defaultState) { this(defaultState, 5, 5); }

    public BlockWoodenSlab(BlockState defaultState, int flammability, int fireSpreadSpeed)
    {
        super(Properties.copy(defaultState.getBlock()));
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }
}
