package techeart.horizonsexpansion.registries;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import techeart.horizonsexpansion.util.RegistryHandler;

public class RegistrySign
{
    private final Block blockStanding;
    private final Block blockWall;
    private final Item item;

    private final TileEntityType<TileEntitySign> tile;

    private final AbstractBlock.Properties blockProps;

    public RegistrySign(String name, WoodType woodType, AbstractBlock.Properties props) {
        blockProps = AbstractBlock.Properties
                .of(Material.WOOD)
                .noCollission()
                .harvestTool(ToolType.AXE)
                .harvestLevel(0)
                .sound(SoundType.WOOD);

        blockStanding = new BlockSignStanding(woodType, props, this);
        blockWall = new BlockSignWall(woodType, props, this);

        item = new SignItem(
                new Item.Properties().tab(ItemGroup.TAB_DECORATIONS),
                blockStanding,
                blockWall
        );

        tile = TileEntityType.Builder.of(() -> new TileEntitySign(this), blockStanding, blockWall).build(null);

        RegistryHandler.BLOCKS.register("sign_" + name, () -> blockStanding);
        RegistryHandler.BLOCKS.register("sign_wall_" + name, () -> blockWall);
        RegistryHandler.ITEMS.register("sign_" + name, () -> item);
        RegistryHandler.TILE_ENTITIES.register("sign_" + name, () -> tile);
    }

    public Block getBlockStanding() { return blockStanding; }

    public Block getBlockWall() { return blockWall; }

    public Item getItem() { return item; }

    public TileEntityType<TileEntitySign> getTile() { return tile; }

    public static class TileEntitySign extends SignTileEntity
    {
        private final TileEntityType<?> type;

        public TileEntitySign(RegistrySign signObj) { type = signObj.getTile(); }

        public TileEntitySign(TileEntityType<?> type) { this.type = type; }

        @Override
        public TileEntityType<?> getType() { return type; }
    }

    public static class BlockSignStanding extends StandingSignBlock
    {
        private final RegistrySign signObj;

        public BlockSignStanding(WoodType woodType, Properties props, RegistrySign signObj)
        {
            super(props, woodType);
            this.signObj = signObj;
        }

        @Override
        public TileEntity newBlockEntity(IBlockReader p_196283_1_)
        {
            return new TileEntitySign(signObj.getTile());
        }
    }

    public static class BlockSignWall extends WallSignBlock
    {
        private final RegistrySign signObj;

        public BlockSignWall(WoodType woodType, Properties props, RegistrySign signObj)
        {
            super(props, woodType);
            this.signObj = signObj;
        }

        @Override
        public TileEntity newBlockEntity(IBlockReader p_196283_1_)
        {
            return new TileEntitySign(signObj.getTile());
        }
    }
}
