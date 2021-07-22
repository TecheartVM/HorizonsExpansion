package techeart.horizonsexpansion.registries;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import techeart.horizonsexpansion.MainClass;
import techeart.horizonsexpansion.util.RegistryHandler;

import java.util.List;
import java.util.function.Predicate;

public class RegistryBoat
{
    private final EntityType<BoatEntity> entityType;
    private final Item item;

    public RegistryBoat(String name)
    {
        entityType = EntityType.Builder.<BoatEntity>
                of(EntityBoat::new, EntityClassification.MISC)
                .sized(1.375f, 0.5625f)
                .clientTrackingRange(10)
                .build(name);
        item = new ItemBoat(entityType);

        RegistryHandler.ITEMS.register("boat_" + name, () -> item);
        RegistryHandler.ENTITIES.register("boat_" + name, () -> entityType);
    }

    public EntityType<BoatEntity> getType() { return entityType; }

    public Item getItem() { return item; }

    public static class EntityBoat extends BoatEntity
    {
        public EntityBoat(EntityType<? extends BoatEntity> type, World world) { super(type, world); }

        public EntityBoat(EntityType<? extends BoatEntity> type, World world, double x, double y, double z)
        {
            super(type, world);
            setPos(x, y, z);
            xo = x;
            yo = y;
            zo = z;
            setDeltaMovement(Vector3d.ZERO);
        }

        @Override
        public Item getDropItem()
        {
            String[] s = getType().toString().split("\\.");
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(MainClass.MODID, s[2]));
        }

        @Override
        public IPacket<?> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
    }

    public static class ItemBoat extends Item
    {
        private final EntityType<? extends BoatEntity> type;

        private final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);

        public ItemBoat(EntityType<? extends BoatEntity> type)
        {
            super(new Properties().stacksTo(1).tab(ItemGroup.TAB_TRANSPORTATION));
            this.type = type;
        }

        @Override
        public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
        {
            ItemStack itemstack = player.getItemInHand(hand);
            RayTraceResult raytraceresult = getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.ANY);
            if (raytraceresult.getType() == RayTraceResult.Type.MISS)
            {
                return ActionResult.pass(itemstack);
            }
            else
            {
                Vector3d vector3d = player.getViewVector(1.0F);
                List<Entity> list = world.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
                if (!list.isEmpty())
                {
                    Vector3d vector3d1 = player.getEyePosition(1.0F);

                    for(Entity entity : list)
                    {
                        AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                        if (axisalignedbb.contains(vector3d1))
                            return ActionResult.pass(itemstack);
                    }
                }

                if (raytraceresult.getType() == RayTraceResult.Type.BLOCK)
                {
                    EntityBoat boat = new EntityBoat(type, world, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                    boat.yRot = player.yRot;
                    if (!world.noCollision(boat, boat.getBoundingBox().inflate(-0.1D)))
                    {
                        return ActionResult.fail(itemstack);
                    }
                    else
                    {
                        if (!world.isClientSide)
                        {
                            world.addFreshEntity(boat);
                            if (!player.abilities.instabuild) itemstack.shrink(1);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
                    }
                }
                else return ActionResult.pass(itemstack);
            }
        }
    }
}
