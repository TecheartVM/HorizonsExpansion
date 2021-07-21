package techeart.horizonsexpansion.util;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import techeart.horizonsexpansion.MainClass;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MainClass.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler
{
    @SubscribeEvent
    public static void registerBlockColors(ColorHandlerEvent.Block event)
    {
        BlockColors colors = event.getBlockColors();
        colors.register((staet, blockDisplayReader, pos, index) -> {
            if(index > 15) return 0xFFFFFF;
            //if can't determine biome color, return some nice green
            if(blockDisplayReader == null || pos == null) return 0x54ad2a;
            return BiomeColors.getAverageFoliageColor(blockDisplayReader, pos);
        }, RegistryHandler.WOOD_POPLAR.getLeaves().getBlock());
    }

    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event)
    {
        ItemColors itemColors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();
        itemColors.register(
                ((stack, index) -> blockColors.getColor(
                        ((BlockItem)stack.getItem()).getBlock().defaultBlockState(),
                        null,
                        null,
                        index
                )),
                RegistryHandler.WOOD_POPLAR.getLeaves().getBlock()
        );
    }
}
