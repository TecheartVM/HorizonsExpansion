package techeart.horizonsexpansion.util;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import techeart.horizonsexpansion.MainClass;
import techeart.horizonsexpansion.registries.RegistryWood;
import techeart.horizonsexpansion.world.feature.HETreeFeatures;

public class RegistryHandler
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainClass.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MainClass.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MainClass.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MainClass.MODID);

//    public static final RegistryObject<Block> BEER_BARREL = BLOCKS.register("beer_barrel", BlockBeerBarrel::new);
//
//    public static final RegistryObject<Item> BEER_BARREL_ITEM = ITEMS.register("beer_barrel", () ->
//            new BlockItem(BEER_BARREL.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
//    public static final RegistryObject<Item> WOODEN_MUG = ITEMS.register("wooden_mug", ItemMug::new);

    public static final RegistryWood WOOD_POPLAR = new RegistryWood("poplar", 5, 5, true, null);

    public static void register()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        ENTITIES.register(eventBus);
    }

}
