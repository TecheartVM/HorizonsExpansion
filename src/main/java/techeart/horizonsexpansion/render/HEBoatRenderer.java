package techeart.horizonsexpansion.render;

import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;
import techeart.horizonsexpansion.MainClass;

import java.util.Objects;

public class HEBoatRenderer extends BoatRenderer
{
    public HEBoatRenderer(EntityRendererManager rendererManager) { super(rendererManager); }

    @Override
    public ResourceLocation getTextureLocation(BoatEntity entity)
    {
        String[] name = Objects.requireNonNull(entity.getType().getRegistryName()).getPath().split("_");
        return new ResourceLocation(MainClass.MODID, "textures/entity/boats/" + name[1] + ".png");
    }
}
