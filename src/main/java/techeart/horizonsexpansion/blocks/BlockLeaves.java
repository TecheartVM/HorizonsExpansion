package techeart.horizonsexpansion.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import techeart.horizonsexpansion.util.RegistryHandler;

public class BlockLeaves extends LeavesBlock
{
    private final int flammability;
    private final int fireSpreadSpeed;

    public BlockLeaves() { this(60, 30); }

    public BlockLeaves(int flammability, int fireSpreadSpeed)
    {
        super(Properties.of(Material.LEAVES)
                .strength(2.0F)
                .sound(SoundType.GRASS)
                .noOcclusion()
                .randomTicks()
                .isValidSpawn((state, world, pos, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
                .isSuffocating((state, world, pos) -> false)
                .isViewBlocking((state, world, pos) -> false)
        );
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return flammability; }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) { return fireSpreadSpeed; }
}
