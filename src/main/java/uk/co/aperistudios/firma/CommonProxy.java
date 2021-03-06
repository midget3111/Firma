package uk.co.aperistudios.firma;

import java.util.ArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.aperistudios.firma.blocks.boring.BaseBlock;
import uk.co.aperistudios.firma.blocks.boring.BrickBlock;
import uk.co.aperistudios.firma.blocks.boring.BrickBlock2;
import uk.co.aperistudios.firma.blocks.boring.ClayBlock;
import uk.co.aperistudios.firma.blocks.boring.ClayBlock2;
import uk.co.aperistudios.firma.blocks.boring.CobbleBlock;
import uk.co.aperistudios.firma.blocks.boring.CobbleBlock2;
import uk.co.aperistudios.firma.blocks.boring.DirtBlock;
import uk.co.aperistudios.firma.blocks.boring.DirtBlock2;
import uk.co.aperistudios.firma.blocks.boring.FarmBlock;
import uk.co.aperistudios.firma.blocks.boring.FarmBlock2;
import uk.co.aperistudios.firma.blocks.boring.GravelBlock;
import uk.co.aperistudios.firma.blocks.boring.GravelBlock2;
import uk.co.aperistudios.firma.blocks.boring.IceBlock;
import uk.co.aperistudios.firma.blocks.boring.PlankBlock;
import uk.co.aperistudios.firma.blocks.boring.PlankBlock2;
import uk.co.aperistudios.firma.blocks.boring.RockBlock;
import uk.co.aperistudios.firma.blocks.boring.RockBlock2;
import uk.co.aperistudios.firma.blocks.boring.SandBlock;
import uk.co.aperistudios.firma.blocks.boring.SandBlock2;
import uk.co.aperistudios.firma.blocks.boring.SmoothBlock;
import uk.co.aperistudios.firma.blocks.boring.SmoothBlock2;
import uk.co.aperistudios.firma.blocks.lessboring.DoorBlock;
import uk.co.aperistudios.firma.blocks.lessboring.FloorStorage;
import uk.co.aperistudios.firma.blocks.lessboring.MiniBlock;
import uk.co.aperistudios.firma.blocks.lessboring.OreBlock;
import uk.co.aperistudios.firma.blocks.lessboring.ShitOnFloor;
import uk.co.aperistudios.firma.blocks.liquids.BaseLiquid;
import uk.co.aperistudios.firma.blocks.living.CropBlock;
import uk.co.aperistudios.firma.blocks.living.GrassBlock;
import uk.co.aperistudios.firma.blocks.living.GrassBlock2;
import uk.co.aperistudios.firma.blocks.living.LeafBlock;
import uk.co.aperistudios.firma.blocks.living.LeafBlock2;
import uk.co.aperistudios.firma.blocks.living.LogBlock;
import uk.co.aperistudios.firma.blocks.living.LogBlock2;
import uk.co.aperistudios.firma.blocks.living.SaplingBlock;
import uk.co.aperistudios.firma.blocks.living.SaplingBlock2;
import uk.co.aperistudios.firma.blocks.living.SparseGrassBlock;
import uk.co.aperistudios.firma.blocks.living.SparseGrassBlock2;
import uk.co.aperistudios.firma.blocks.machine.AnvilBlock;
import uk.co.aperistudios.firma.blocks.machine.CrucibleBlock;
import uk.co.aperistudios.firma.blocks.machine.FurnaceBlock;
import uk.co.aperistudios.firma.blocks.tileentity.AnvilTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.CropTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.CrucibleTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.DoorTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.FloorStorageTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.MiniBlockTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.OreTileEntity;
import uk.co.aperistudios.firma.blocks.tileentity.SoFTileEntity;
import uk.co.aperistudios.firma.container.HandlerGui;
import uk.co.aperistudios.firma.crafting.CraftingManager;
import uk.co.aperistudios.firma.generation.FirmaBiome;
import uk.co.aperistudios.firma.generation.FirmaOreVeinGen;
import uk.co.aperistudios.firma.generation.FirmaPathGen;
import uk.co.aperistudios.firma.generation.FirmaTreeGen;
import uk.co.aperistudios.firma.generation.FirmaVillageGen;
import uk.co.aperistudios.firma.generation.FirmaWorld;
import uk.co.aperistudios.firma.generation.FirmaWorldProvider;
import uk.co.aperistudios.firma.generation.OreGenReplacer;
import uk.co.aperistudios.firma.generation.ShitOnFloorGen;
import uk.co.aperistudios.firma.generation.layers.Layer;
import uk.co.aperistudios.firma.generation.structures.Plan;
import uk.co.aperistudios.firma.generation.tree.FirmaTree;
import uk.co.aperistudios.firma.handler.FirmaHandler;
import uk.co.aperistudios.firma.items.BrickItem;
import uk.co.aperistudios.firma.items.ClayItem;
import uk.co.aperistudios.firma.items.DoubleIngotItem;
import uk.co.aperistudios.firma.items.FirmaDoorItem;
import uk.co.aperistudios.firma.items.FirmaItem;
import uk.co.aperistudios.firma.items.FoodIngredientItem;
import uk.co.aperistudios.firma.items.GemItem;
import uk.co.aperistudios.firma.items.HideItem;
import uk.co.aperistudios.firma.items.IngotItem;
import uk.co.aperistudios.firma.items.MetaBlockItem;
import uk.co.aperistudios.firma.items.MetalSheetItem;
import uk.co.aperistudios.firma.items.MiniBlockItem;
import uk.co.aperistudios.firma.items.OreItem;
import uk.co.aperistudios.firma.items.PebbleItem;
import uk.co.aperistudios.firma.items.ScrapMetalItem;
import uk.co.aperistudios.firma.items.SeedItem;
import uk.co.aperistudios.firma.items.StorageItem;
import uk.co.aperistudios.firma.items.ToolHeads;
import uk.co.aperistudios.firma.items.ToolItem;
import uk.co.aperistudios.firma.items.UnfiredClay;
import uk.co.aperistudios.firma.packet.KnapToServer;
import uk.co.aperistudios.firma.packet.SetDayPacket;
import uk.co.aperistudios.firma.types.AlcoholType;
import uk.co.aperistudios.firma.types.ItemSize;
import uk.co.aperistudios.firma.types.MetalLiquid;
import uk.co.aperistudios.firma.types.OresEnum;
import uk.co.aperistudios.firma.types.RockEnum;
import uk.co.aperistudios.firma.types.RockEnum2;
import uk.co.aperistudios.firma.types.ToolMaterials;
import uk.co.aperistudios.firma.types.ToolType;

public abstract class CommonProxy {

	public static DimensionType firmaDimension;
	public static IBlockState[] rockLayerTop, rockLayerMid, rockLayerBot, saplingLayer;
	public static int d = 0;
	public static FirmaTreeGen treeGen;
	public static FirmaPathGen pathGen;

	FirmaWorld fw = new FirmaWorld();

	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new FirmaHandler());

		FirmaMod.rock = new RockBlock();
		FirmaMod.rock2 = new RockBlock2();
		FirmaMod.rockb = new BrickBlock();
		FirmaMod.rockb2 = new BrickBlock2();
		FirmaMod.rockc = new CobbleBlock();
		FirmaMod.rockc2 = new CobbleBlock2();
		FirmaMod.rocks = new SmoothBlock();
		FirmaMod.rocks2 = new SmoothBlock2();
		FirmaMod.dirt = new DirtBlock();
		FirmaMod.dirt2 = new DirtBlock2();
		FirmaMod.grass = new GrassBlock();
		FirmaMod.grass2 = new GrassBlock2();
		FirmaMod.grasss = new SparseGrassBlock();
		FirmaMod.grasss2 = new SparseGrassBlock2();
		FirmaMod.gravel = new GravelBlock();
		FirmaMod.gravel2 = new GravelBlock2();
		FirmaMod.plank = new PlankBlock();
		FirmaMod.plank2 = new PlankBlock2();
		FirmaMod.sand = new SandBlock();
		FirmaMod.sand2 = new SandBlock2();
		FirmaMod.leaf = new LeafBlock();
		FirmaMod.leaf2 = new LeafBlock2();
		FirmaMod.ice = new IceBlock();
		FirmaMod.clayBlock = new ClayBlock();
		FirmaMod.clayBlock2 = new ClayBlock2();
		FirmaMod.sapling = new SaplingBlock();
		FirmaMod.sapling2 = new SaplingBlock2();
		FirmaMod.log = new LogBlock();
		FirmaMod.log2 = new LogBlock2();
		FirmaMod.farm = new FarmBlock();
		FirmaMod.farm2 = new FarmBlock2();

		FirmaMod.shitOnFloor = new ShitOnFloor();
		FirmaMod.floorStorage = new FloorStorage();
		FirmaMod.crucible = new CrucibleBlock();
		FirmaMod.anvil = new AnvilBlock();
		FirmaMod.furnace = new FurnaceBlock();
		FirmaMod.ore = new OreBlock();
		FirmaMod.crops = new CropBlock();

		FirmaMod.pebble = new PebbleItem("pebble");
		FirmaMod.oreItem = new OreItem("oreitem");
		FirmaMod.brick = new BrickItem("brickitem");
		FirmaMod.gem = new GemItem("gem");
		FirmaMod.ingot = new IngotItem("ingot");
		FirmaMod.doubleingot = new DoubleIngotItem("doubleingot");
		FirmaMod.metalsheet = new MetalSheetItem("metalsheet");
		FirmaMod.scrapmetal = new ScrapMetalItem("scrapmetal");
		FirmaMod.unfiredClayBits = new UnfiredClay("unfiredclay");
		FirmaMod.toolHeads = new ToolHeads("toolheads");
		FirmaMod.clay = new ClayItem("clay");
		FirmaMod.hide = new HideItem("hide");
		FirmaMod.doorItem = new FirmaDoorItem();
		FirmaMod.seedItem = new SeedItem();
		FirmaMod.foodItem = new FoodIngredientItem();

		FirmaMod.vesselItem = new StorageItem("vesselitem", ItemSize.SMALL, 4);

		FirmaMod.miniBlocks = new MiniBlock();
		FirmaMod.miniBlockItem = new MiniBlockItem(FirmaMod.miniBlocks);
		FirmaMod.door = new DoorBlock();

		rockLayerTop = new IBlockState[] { FirmaMod.rock2.getStateFromMeta(RockEnum2.Shale.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Claystone.getMeta()), FirmaMod.rock2.getStateFromMeta(RockEnum2.RockSalt.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Limestone.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Conglomerate.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Dolomite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Chert.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Chalk.getMeta()), FirmaMod.rock2.getStateFromMeta(RockEnum2.Rhyolite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Basalt.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Andesite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Dacite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Quartzite.getMeta()),
				FirmaMod.rock2.getStateFromMeta(RockEnum2.Slate.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Phyllite.getMeta()),
				FirmaMod.rock2.getStateFromMeta(RockEnum2.Schist.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Gneiss.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Marble.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Granite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Diorite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Gabbro.getMeta()) };
		rockLayerMid = new IBlockState[] { FirmaMod.rock2.getStateFromMeta(RockEnum2.Rhyolite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Basalt.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Andesite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Dacite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Quartzite.getMeta()),
				FirmaMod.rock2.getStateFromMeta(RockEnum2.Slate.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Phyllite.getMeta()),
				FirmaMod.rock2.getStateFromMeta(RockEnum2.Schist.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Gneiss.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Marble.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Granite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Diorite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Gabbro.getMeta()), };
		rockLayerBot = new IBlockState[] { FirmaMod.rock2.getStateFromMeta(RockEnum2.Rhyolite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Basalt.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Andesite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Dacite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Granite.getMeta()),
				FirmaMod.rock.getStateFromMeta(RockEnum.Diorite.getMeta()), FirmaMod.rock.getStateFromMeta(RockEnum.Gabbro.getMeta()), };

		ToolItem thisTool = null;
		for (ToolMaterials tm : ToolMaterials.values()) {
			for (ToolType tt : ToolType.values()) {
				thisTool = new ToolItem(tm, tt);
				FirmaMod.bunchOfTools.add(thisTool);
				thisTool.addRecipe();
			}
		}

		FirmaMod.lava = BaseLiquid.create("lava", fluid -> fluid.setLuminosity(100).setDensity(800).setViscosity(1500), 0xffff0000, true);
		FirmaMod.saltwater = BaseLiquid.create("saltwater", fluid -> fluid.setLuminosity(0).setDensity(800).setViscosity(1500), 0xff0022ff, false);
		FirmaMod.freshwater = BaseLiquid.create("freshwater", fluid -> fluid.setLuminosity(0).setDensity(800).setViscosity(1500), 0xff0022ff, false);
		FirmaMod.milk = BaseLiquid.create("milk", fluid -> fluid.setLuminosity(0).setDensity(800).setViscosity(1500), 0xffffffff, false);
		for (AlcoholType at : AlcoholType.values()) {
			BaseLiquid.create(at.getName(), fluid -> fluid.setLuminosity(0).setDensity(800).setViscosity(1500), at.getCol(), false);
		}
		for (MetalLiquid ml : MetalLiquid.values()) {
			BaseLiquid.create(ml.getName(), fluid -> fluid.setLuminosity(15).setDensity(1000).setViscosity(10), ml.getCol(), true);
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(FirmaMod.instance, new HandlerGui());

		KnapToServer.init();
		SetDayPacket.init();

		CraftingManager.addKnappingRecipes();

		for (BaseBlock b : FirmaMod.allBlocks) {
			GameRegistry.register(b);
			Item i = new MetaBlockItem(b);
			GameRegistry.register(i);
		}

		for (FirmaItem i : FirmaMod.allItems) {
			GameRegistry.register(i);
		}

		for (Fluid f : FirmaMod.allFluids) {

			FluidRegistry.addBucketForFluid(f);
		}

		FirmaBiome.init();
		// Dirty hack to put Firma as first world to gen before default

		int i = fw.getWorldTypeID();
		WorldType.WORLD_TYPES[i] = WorldType.WORLD_TYPES[0];
		WorldType.WORLD_TYPES[0] = fw;

		// d = DimensionManager.getNextFreeDimId();
		DimensionManager.unregisterDimension(0);
		firmaDimension = DimensionType.register("Firma", "-" + d, d, FirmaWorldProvider.class, true);
		DimensionManager.registerDimension(d, firmaDimension);

		for (FirmaBiome b : FirmaBiome.biomes) {
			b.reg();
		}

		for (FirmaTree a : FirmaBiome.getAllTreeGen()) {
			GameRegistry.registerWorldGenerator(a, 0);
		}

		Layer.prep();

		GameRegistry.registerTileEntity(OreTileEntity.class, "firmaorete");
		GameRegistry.registerTileEntity(SoFTileEntity.class, "firmasof");
		GameRegistry.registerTileEntity(AnvilTileEntity.class, "firmaanvil");
		GameRegistry.registerTileEntity(FloorStorageTileEntity.class, "firmafloor");
		GameRegistry.registerTileEntity(DoorTileEntity.class, "firmadoor");
		GameRegistry.registerTileEntity(CrucibleTileEntity.class, "firmacrucible");
		GameRegistry.registerTileEntity(CropTileEntity.class, "firmacrop");
		GameRegistry.registerTileEntity(MiniBlockTileEntity.class, "firmamini");
		// TODO Non-vein ores.

		ArrayList<FirmaOreVeinGen> topLayers = new ArrayList<FirmaOreVeinGen>();
		int prio = 0;

		// Top layer pinned to top stone block. Veins will break off/be visible
		// in mountain edges. Harder to follow vein but easier to find more.
		// Grade 0, shit tier
		FirmaOreVeinGen l = new FirmaOreVeinGen(100, 256, 1, new OreGenReplacer(OresEnum.NATIVECOPPER, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 2, new OreGenReplacer(OresEnum.BISMUTHINITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 3, new OreGenReplacer(OresEnum.CASSITERITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 4, new OreGenReplacer(OresEnum.GARNIERITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 5, new OreGenReplacer(OresEnum.MALACHITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 6, new OreGenReplacer(OresEnum.TETRAHEDRITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 7, new OreGenReplacer(OresEnum.HEMATITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 8, new OreGenReplacer(OresEnum.NATIVESILVER, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 9, new OreGenReplacer(OresEnum.NATIVEGOLD, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 10, new OreGenReplacer(OresEnum.SPHALERITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);
		l = new FirmaOreVeinGen(100, 256, 11, new OreGenReplacer(OresEnum.LIMONITE, 0));
		topLayers.add(l);
		GameRegistry.registerWorldGenerator(l, prio++);

		// Middle layer, between 50 and 100 y, Grade 1
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 12, new OreGenReplacer(OresEnum.NATIVECOPPER, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 13, new OreGenReplacer(OresEnum.NATIVEGOLD, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 14, new OreGenReplacer(OresEnum.NATIVESILVER, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 15, new OreGenReplacer(OresEnum.BISMUTHINITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 16, new OreGenReplacer(OresEnum.CASSITERITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 17, new OreGenReplacer(OresEnum.GARNIERITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 18, new OreGenReplacer(OresEnum.HEMATITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 19, new OreGenReplacer(OresEnum.LIMONITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 20, new OreGenReplacer(OresEnum.MALACHITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 21, new OreGenReplacer(OresEnum.SPHALERITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(50, 100, 22, new OreGenReplacer(OresEnum.TETRAHEDRITE, 0)), prio++);

		// Bottom layer, between 30 and 70, Grade 2
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 23, new OreGenReplacer(OresEnum.NATIVECOPPER, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 24, new OreGenReplacer(OresEnum.NATIVEGOLD, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 25, new OreGenReplacer(OresEnum.NATIVESILVER, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 26, new OreGenReplacer(OresEnum.BISMUTHINITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 27, new OreGenReplacer(OresEnum.CASSITERITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 28, new OreGenReplacer(OresEnum.GARNIERITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 29, new OreGenReplacer(OresEnum.HEMATITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 30, new OreGenReplacer(OresEnum.LIMONITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 31, new OreGenReplacer(OresEnum.MALACHITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 32, new OreGenReplacer(OresEnum.SPHALERITE, 0)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(30, 70, 33, new OreGenReplacer(OresEnum.TETRAHEDRITE, 0)), prio++);

		// Lava layer, between 1 and 40. Dangerous, Grade 3. Less severe for
		// lower tier ores
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 40, 34, new OreGenReplacer(OresEnum.NATIVECOPPER, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 40, 35, new OreGenReplacer(OresEnum.NATIVESILVER, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 40, 36, new OreGenReplacer(OresEnum.MALACHITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 40, 37, new OreGenReplacer(OresEnum.TETRAHEDRITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 25, 38, new OreGenReplacer(OresEnum.CASSITERITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 10, 39, new OreGenReplacer(OresEnum.HEMATITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 10, 40, new OreGenReplacer(OresEnum.LIMONITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 50, 41, new OreGenReplacer(OresEnum.SPHALERITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 40, 42, new OreGenReplacer(OresEnum.BISMUTHINITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 40, 43, new OreGenReplacer(OresEnum.GARNIERITE, 3)), prio++);
		GameRegistry.registerWorldGenerator(new FirmaOreVeinGen(3, 60, 44, new OreGenReplacer(OresEnum.NATIVEGOLD, 3)), prio++);

		CommonProxy.pathGen = new FirmaPathGen(45);
		GameRegistry.registerWorldGenerator(pathGen, prio++);
		GameRegistry.registerWorldGenerator(new FirmaVillageGen(), prio++);
		CommonProxy.treeGen = new FirmaTreeGen();
		GameRegistry.registerWorldGenerator(treeGen, prio++);
		GameRegistry.registerWorldGenerator(new ShitOnFloorGen(topLayers), prio++);

		// GameRegistry.registerWorldGenerator(new LavaLayerGen(-4), 40);

		// Use during debug to remove Rock from world to expose ore veins
		// GameRegistry.registerWorldGenerator(new FirmaDebugOres(), 200);

		Plan.init(); // Prepare world gen structures

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	public abstract void setDate(TimeData data);

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (event.world.isRemote) {
			return;
		} // Not called on client
		long time = event.world.getWorldTime();
		if (time > Util.ticksInDay) {
			MapStorage storage = event.world.getPerWorldStorage();
			TimeData td = (TimeData) storage.getOrLoadData(TimeData.class, "firmatime");
			if (td == null) {
				td = new TimeData("");
				storage.setData("firmatime", td);
			}
			td.addDay();
			event.world.setWorldTime(time - Util.ticksInDay);
			td.setDirty(true);
			SetDayPacket sdp = new SetDayPacket(td);
			FirmaMod.dispatcher.sendToDimension(sdp, event.world.provider.getDimension());
			// new Exception().printStackTrace();
			System.out.println("Day inceremented on Server " + td.toString());
		}
	}

	public TimeData getTimeData(World world) {
		if (world.isRemote) { // Client
			return ClientProxy.staticDate;
		}
		MapStorage storage = world.getPerWorldStorage();
		TimeData td = (TimeData) storage.getOrLoadData(TimeData.class, "firmatime");
		if (td == null) {
			td = new TimeData("");
		}
		return td;
	}

	public TimeData getTimeData(IBlockAccess worldIn) {
		if (worldIn instanceof World) {
			return getTimeData((World) worldIn);
		}
		return new TimeData("");
	}
}
