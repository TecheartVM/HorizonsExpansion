package techeart.horizonsexpansion.registries;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import techeart.horizonsexpansion.util.RegistryHandler;

import java.util.function.Supplier;

public class RegistryBlock {
    private final RegistryObject<Block> block;
    private final RegistryObject<Item> item;

    public RegistryBlock(String name, Supplier<? extends Block> blockFactory) {
        block = RegistryHandler.BLOCKS.register(name, blockFactory);
        item = RegistryHandler.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
    }

    public RegistryBlock(String name, Supplier<? extends Block> blockFactory, Item.Properties itemProps) {
        block = RegistryHandler.BLOCKS.register(name, blockFactory);
        item = RegistryHandler.ITEMS.register(name, () -> new BlockItem(block.get(), itemProps));
    }

    public RegistryBlock(String name, Supplier<? extends Block> blockFactory, Supplier<? extends Item> itemFactory) {
        block = RegistryHandler.BLOCKS.register(name, blockFactory);
        item = RegistryHandler.ITEMS.register(name, itemFactory);
    }

    public Block getBlock() {
        return block.get();
    }

    public Item getItem() {
        return item.get();
    }

    public RegistryObject<Block> getBlockRO() {
        return block;
    }

    public RegistryObject<Item> getItemRO() {
        return item;
    }
}
