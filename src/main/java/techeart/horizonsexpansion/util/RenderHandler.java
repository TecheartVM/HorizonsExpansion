package techeart.horizonsexpansion.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import techeart.horizonsexpansion.MainClass;
import techeart.horizonsexpansion.registries.RegistryBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RenderHandler
{
    private final Map<RegistryObject<Block>, RenderType> BLOCK_RENDERERS = new HashMap<>();
    private final Map<TileEntityType<? extends TileEntity>, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super TileEntity>>> TILEENTITY_RENDERERS
            = new HashMap<>();
    private final Map<EntityType<? extends Entity>, IRenderFactory<? super Entity>> ENTITY_RENDERERS = new HashMap<>();

    public void init()
    {
        for (RegistryObject<Block> block : BLOCK_RENDERERS.keySet())
            RenderTypeLookup.setRenderLayer(block.get(), BLOCK_RENDERERS.get(block));

        for (TileEntityType<? extends TileEntity> type : TILEENTITY_RENDERERS.keySet())
            ClientRegistry.bindTileEntityRenderer(type, TILEENTITY_RENDERERS.get(type));

        for (EntityType<?> type : ENTITY_RENDERERS.keySet())
            RenderingRegistry.registerEntityRenderingHandler(type, ENTITY_RENDERERS.get(type));
    }

    public void setBlockRenderType(RegistryObject<Block> block, RenderType type) { BLOCK_RENDERERS.put(block, type); }

    public void setBlockRenderType(RegistryBlock block, RenderType type) { BLOCK_RENDERERS.put(block.getBlockRO(), type); }

    public <T extends TileEntity> void setTileRenderer(TileEntityType<? extends T> type, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> renderer)
    {
        TILEENTITY_RENDERERS.put(type, (Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super TileEntity>>) renderer);
    }

    public <T extends Entity> void setEntityRenderer(EntityType<T> type, IRenderFactory<? super T> renderer)
    {
        ENTITY_RENDERERS.put(type, (IRenderFactory<? super Entity>) renderer);
    }
}
