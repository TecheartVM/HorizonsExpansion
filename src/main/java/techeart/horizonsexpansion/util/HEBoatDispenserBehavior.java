package techeart.horizonsexpansion.util;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techeart.horizonsexpansion.registries.RegistryBoat;

public class HEBoatDispenserBehavior extends DefaultDispenseItemBehavior
{
    private final RegistryBoat boatObj;
    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    public HEBoatDispenserBehavior(RegistryBoat boatObj) { this.boatObj = boatObj; }

    @Override
    public ItemStack execute(IBlockSource source, ItemStack stack)
    {
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        World world = source.getLevel();
        double d0 = source.x() + (double)((float)direction.getStepX() * 1.125F);
        double d1 = source.y() + (double)((float)direction.getStepY() * 1.125F);
        double d2 = source.z() + (double)((float)direction.getStepZ() * 1.125F);
        BlockPos blockpos = source.getPos().relative(direction);
        double d3;
        if (world.getFluidState(blockpos).is(FluidTags.WATER))
        {
            d3 = 1.0D;
        }
        else
        {
            if (!world.getBlockState(blockpos).isAir() || !world.getFluidState(blockpos.below()).is(FluidTags.WATER))
            {
                return this.defaultDispenseItemBehavior.dispense(source, stack);
            }
            d3 = 0.0D;
        }

        RegistryBoat.EntityBoat boatentity = new RegistryBoat.EntityBoat(boatObj.getType(), world, d0, d1 + d3, d2);
        boatentity.yRot = direction.toYRot();
        world.addFreshEntity(boatentity);
        stack.shrink(1);
        return stack;
    }

    @Override
    protected void playSound(IBlockSource source) { source.getLevel().levelEvent(1000, source.getPos(), 0); }
}
