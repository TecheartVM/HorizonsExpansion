package techeart.horizonsexpansion.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BlockLog extends RotatedPillarBlock
{
    private final int flammability;
    private final int fireSpreadSpeed;
    private final Block strippedBlock;

    public BlockLog() { this(5, 5); }

    public BlockLog(int flammability, int fireSpreadSpeed) { this(flammability, fireSpreadSpeed, null); }

    public BlockLog(int flammability, int fireSpreadSpeed, Block stripped)
    {
        super(Properties.of(Material.WOOD
                //,(state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.WOOD : MaterialColor.PODZOL
        ).strength(2.0F).sound(SoundType.WOOD));

        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
        this.strippedBlock = stripped;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType)
    {
        if(strippedBlock == null) return null;
        if(toolType != ToolType.AXE) return null;
        return strippedBlock.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }
}
