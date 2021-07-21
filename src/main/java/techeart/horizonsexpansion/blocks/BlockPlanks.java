package techeart.horizonsexpansion.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockPlanks extends Block
{
    private final int flammability;
    private final int fireSpreadSpeed;

    public BlockPlanks() { this(5, 5); }

    public BlockPlanks(int flammability, int fireSpreadSpeed)
    {
        super(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD));
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }
}
