package techeart.horizonsexpansion.registries;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.fml.RegistryObject;
import techeart.horizonsexpansion.MainClass;
import techeart.horizonsexpansion.blocks.*;
import techeart.horizonsexpansion.render.HEBoatRenderer;
import techeart.horizonsexpansion.util.HEBoatDispenserBehavior;
import techeart.horizonsexpansion.util.RegistryHandler;
import techeart.horizonsexpansion.world.HETree;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistryWood
{
    private final WoodType type;

    private final RegistryBlock log;
    private final RegistryBlock wood;
    private final RegistryBlock strippedLog;
    private final RegistryBlock strippedWood;
    private final RegistryBlock planks;
    private final RegistryBlock stairs;
    private final RegistryBlock slab;
    private final RegistryBlock fence;
    private final RegistryBlock gate;
    private final RegistryBlock door;
    private final RegistryBlock trapdoor;
    private final RegistryBlock button;
    private final RegistryBlock plate;
    private final RegistryBlock leaves;
    private final RegistryBlock sapling;
    private final RegistryObject<Block> saplingPotted;

    private final RegistrySign sign;
    private final RegistryBoat boat;

    private final Tree tree;

    public RegistryWood(String name, int flammability, int fireSpreadSpeed, boolean isNatural, @Nullable Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> treeFeature)
    {
        type = WoodType.register(WoodType.create(MainClass.MODID + ":" + name));

        strippedLog = new RegistryBlock("stripped_log_" + name, () -> new BlockLog(flammability, fireSpreadSpeed));
        strippedWood = new RegistryBlock("stripped_wood_" + name, () -> new BlockLog(flammability, fireSpreadSpeed));
        log = new RegistryBlock("log_" + name, () -> new BlockLog(flammability, fireSpreadSpeed, strippedLog.getBlock()));
        wood = new RegistryBlock("wood_" + name, () -> new BlockLog(flammability, fireSpreadSpeed, strippedWood.getBlock()));

        planks = new RegistryBlock("planks_" + name, () -> new BlockPlanks(flammability, fireSpreadSpeed));
        stairs = new RegistryBlock("stairs_" + name, () -> new BlockWoodenStairs(planks.getBlock().defaultBlockState(), flammability, fireSpreadSpeed));
        slab = new RegistryBlock("slab_" + name, () -> new BlockWoodenSlab(planks.getBlock().defaultBlockState(), flammability, fireSpreadSpeed));
        fence = new RegistryBlock("fence_" + name, () -> new BlockWoodenFence(planks.getBlock().defaultBlockState(), flammability, fireSpreadSpeed), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS));
        gate = new RegistryBlock("gate_" + name, () -> new BlockWoodenGate(planks.getBlock().defaultBlockState(), flammability, fireSpreadSpeed), new Item.Properties().tab(ItemGroup.TAB_REDSTONE));
        door = new RegistryBlock("door_" + name, () -> new DoorBlock(AbstractBlock.Properties.copy(planks.getBlock())), new Item.Properties().tab(ItemGroup.TAB_REDSTONE));
        trapdoor = new RegistryBlock("trapdoor_" + name, () -> new TrapDoorBlock(AbstractBlock.Properties.copy(planks.getBlock())), new Item.Properties().tab(ItemGroup.TAB_REDSTONE));
        button = new RegistryBlock("button_" + name, () -> new WoodButtonBlock(AbstractBlock.Properties.copy(planks.getBlock()).noCollission()), new Item.Properties().tab(ItemGroup.TAB_REDSTONE));
        plate = new RegistryBlock("plate_" + name, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.copy(planks.getBlock()).noCollission()), new Item.Properties().tab(ItemGroup.TAB_REDSTONE));

        sign = new RegistrySign(name, type, AbstractBlock.Properties.of(Material.WOOD).strength(2).sound(SoundType.WOOD).noCollission());

        if (treeFeature != null) tree = new HETree(treeFeature);
        else tree = null;

        if (isNatural)
        {
            leaves = new RegistryBlock("leaves_" + name, BlockLeaves::new, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS));

            RegistryHandler.addCompostableItem(leaves::getItem, 0.3f);

            if (tree != null)
            {
                sapling = new RegistryBlock("sapling_" + name, () -> new BlockSapling(tree),
                        new Item.Properties().tab(ItemGroup.TAB_DECORATIONS));
                saplingPotted = RegistryHandler.BLOCKS.register("potted_" + name, () -> new FlowerPotBlock(
                        () -> (FlowerPotBlock) Blocks.FLOWER_POT,
                        sapling::getBlock,
                        AbstractBlock.Properties.copy(Blocks.FLOWER_POT)
                ));

                ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(new ResourceLocation(MainClass.MODID, "sapling_" + name), saplingPotted);
                RegistryHandler.addCompostableItem(sapling::getItem, 0.3f);
            }
            else
            {
                sapling = null;
                saplingPotted = null;
            }
        }
        else
        {
            leaves = null;
            sapling = null;
            saplingPotted = null;
        }

        boat = new RegistryBoat(name);
        DispenserBlock.registerBehavior(boat::getItem, new HEBoatDispenserBehavior(boat));

        initRenderers();
    }

    private void initRenderers()
    {
        MainClass.RENDER_HANDLER.setBlockRenderType(door, RenderType.cutout());
        MainClass.RENDER_HANDLER.setBlockRenderType(trapdoor, RenderType.cutout());
        if (sapling != null)
        {
            MainClass.RENDER_HANDLER.setBlockRenderType(sapling, RenderType.cutout());
            MainClass.RENDER_HANDLER.setBlockRenderType(saplingPotted, RenderType.cutout());
        }
        MainClass.RENDER_HANDLER.setTileRenderer(sign.getTile(), SignTileEntityRenderer::new);
        MainClass.RENDER_HANDLER.setEntityRenderer(boat.getType(), HEBoatRenderer::new);
    }

    public WoodType getType() {
        return type;
    }

    public RegistryBlock getLog() {
        return log;
    }

    public RegistryBlock getWood() {
        return wood;
    }

    public RegistryBlock getStrippedLog() {
        return strippedLog;
    }

    public RegistryBlock getStrippedWood() {
        return strippedWood;
    }

    public RegistryBlock getPlanks() {
        return planks;
    }

    public RegistryBlock getStairs() {
        return stairs;
    }

    public RegistryBlock getSlab() {
        return slab;
    }

    public RegistryBlock getFence() {
        return fence;
    }

    public RegistryBlock getGate() {
        return gate;
    }

    public RegistryBlock getDoor() {
        return door;
    }

    public RegistryBlock getTrapdoor() {
        return trapdoor;
    }

    public RegistryBlock getButton() {
        return button;
    }

    public RegistryBlock getPlate() {
        return plate;
    }

    public RegistryBlock getLeaves() {
        return leaves;
    }

    public RegistryBlock getSapling() {
        return sapling;
    }

    public RegistryObject<Block> getSaplingPotted() {
        return saplingPotted;
    }

    public RegistrySign getSign() {
        return sign;
    }

    public RegistryBoat getBoat() { return boat; }

    public Tree getTree() {
        return tree;
    }
}
