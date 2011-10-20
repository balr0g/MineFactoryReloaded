package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityPig;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.buildcraft.api.API;
import net.minecraft.src.buildcraft.api.LiquidData;
import net.minecraft.src.forge.Configuration;
import net.minecraft.src.forge.Property;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.HarvestType;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IMFRProxy;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableGiantMushroom;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableSapling;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableStemPlants;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableWheat;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableStemPlant;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableVine;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableWheat;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.PlantableStandard;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.PlantableWheat;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.RanchableChicken;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.RanchableCow;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.RanchableStandard;

public class MineFactoryReloadedCore
{
	public static PowerSystem powerSystem;
	
	public static String terrainTexture = "/MineFactorySprites/terrain.png";
	public static String itemTexture = "/MineFactorySprites/items.png";
	
	public static Block machineBlock;
	
	public static Block conveyorBlock;
	
	public static Block passengerRailDropoffBlock;
	public static Block passengerRailPickupBlock;
	public static Block cargoRailDropoffBlock;
	public static Block cargoRailPickupBlock;
	
	public static Item steelIngotItem;
	public static Item factoryHammerItem;
	public static Item milkItem;
	
	public static Item machineItem;
	
	public static int conveyorTexture;
	public static int conveyorOffTexture;
	public static int conveyorStillOffTexture;
	public static int harvesterAnimatedTexture;
	public static int rancherAnimatedTexture;
	public static int steelSideTexture;
	public static int planterSaplingTexture;
	public static int planterCactusTexture;
	public static int planterSugarTexture;
	public static int planterMushroomTexture;
	public static int steelHoleTexture;
	public static int passengerRailPickupTexture;
	public static int passengerRailDropoffTexture;
	public static int cargoRailPickupTexture;
	public static int cargoRailDropoffTexture;
	public static int fisherBucketTexture;
	public static int fisherFishTexture;
	public static int harvesterSideTexture;
	public static int rancherSideTexture;
	public static int fertilizerBackTexture;
	public static int fertilizerSideTexture;
	public static int vetSideTexture;
	public static int collectorSideTexture;
	public static int weatherTopTexture;
	public static int weatherSnowSideTexture;
	public static int blockBreakerAnimatedTexture;
	public static int fertilizerAnimatedTexture;
	public static int vetAnimatedTexture;
	public static int blockBreakerSideTexture;
	
	public static int factoryHammerTexture;
	public static int steelIngotTexture;
	public static int milkTexture;
	
	public static IMFRProxy proxy;
	
	public static Map<MineFactoryReloadedCore.Machine, Integer> machineMetadataMappings;

	// Config
	public static Property machineBlockId;
	public static Property conveyorBlockId;
	public static Property passengerPickupRailBlockId;
	public static Property passengerDropoffRailBlockId;
	public static Property cargoPickupRailBlockId;
	public static Property cargoDropoffRailBlockId;
	
	public static Property steelIngotItemId;
	public static Property hammerItemId;
	public static Property milkItemId;
	
	public static Property animateBlockFaces;
	public static Property animationTileSize;
	public static Property treeSearchMaxVertical;
	public static Property treeSearchMaxHorizontal;
	public static Property passengerRailSearchMaxVertical;
	public static Property passengerRailSearchMaxHorizontal;
	public static Property verticalHarvestSearchMaxVertical;
	public static Property playSounds;
	public static Property machinesCanDropInChests;
	public static Property rancherInjuresAnimals;
	public static Property harvesterHarvestsSmallMushrooms;
	
	public static Property powerSystemProperty;
	
	public static void Init(IMFRProxy proxyParam)
	{
		proxy = proxyParam;
		
		loadConfig();
		
		machineMetadataMappings = new HashMap<Machine, Integer>();
		machineMetadataMappings.put(Machine.Planter, 0);
		machineMetadataMappings.put(Machine.Fisher, 1);
		machineMetadataMappings.put(Machine.Harvester, 2);
		machineMetadataMappings.put(Machine.Rancher, 3);
		machineMetadataMappings.put(Machine.Fertilizer, 4);
		machineMetadataMappings.put(Machine.Vet, 5);
		machineMetadataMappings.put(Machine.Collector, 6);
		machineMetadataMappings.put(Machine.Breaker, 7);
		machineMetadataMappings.put(Machine.Weather, 8);

		setupTextures();
		
		passengerRailPickupBlock = new BlockRailPassengerPickup(Util.getInt(passengerPickupRailBlockId), passengerRailPickupTexture);
		passengerRailDropoffBlock = new BlockRailPassengerDropoff(Util.getInt(passengerDropoffRailBlockId), passengerRailDropoffTexture);
		cargoRailDropoffBlock = new BlockRailCargoDropoff(Util.getInt(cargoDropoffRailBlockId), cargoRailDropoffTexture);
		cargoRailPickupBlock = new BlockRailCargoPickup(Util.getInt(cargoPickupRailBlockId), cargoRailPickupTexture);
		
		conveyorBlock = new BlockConveyor(Util.getInt(conveyorBlockId), conveyorTexture);
		
		machineBlock = new BlockFactoryMachine(Util.getInt(machineBlockId), 0);
		
		steelIngotItem = (new ItemFactory(Util.getInt(steelIngotItemId)))
			.setIconIndex(steelIngotTexture)
			.setItemName("steelIngot");
		factoryHammerItem = (new ItemFactory(Util.getInt(hammerItemId)))
			.setIconIndex(factoryHammerTexture)
			.setItemName("factoryWrench")
			.setMaxStackSize(1);
		milkItem = (new ItemFactory(Util.getInt(milkItemId)))
			.setIconIndex(milkTexture)
			.setItemName("milkItem");

		ModLoader.RegisterBlock(machineBlock, ItemFactoryMachine.class);
		ModLoader.RegisterBlock(conveyorBlock);
		ModLoader.RegisterBlock(passengerRailPickupBlock);
		ModLoader.RegisterBlock(passengerRailDropoffBlock);
		ModLoader.RegisterBlock(cargoRailDropoffBlock);
		ModLoader.RegisterBlock(cargoRailPickupBlock);
		
		ModLoader.RegisterTileEntity(TileEntityFisher.class, "factoryFisher");
		ModLoader.RegisterTileEntity(TileEntityPlanter.class, "factoryPlanter");
		ModLoader.RegisterTileEntity(TileEntityHarvester.class, "factoryHarvester");
		ModLoader.RegisterTileEntity(TileEntityRancher.class, "factoryRancher");
		ModLoader.RegisterTileEntity(TileEntityFertilizer.class, "factoryFertilizer");
		ModLoader.RegisterTileEntity(TileEntityConveyor.class, "factoryConveyor");
		ModLoader.RegisterTileEntity(TileEntityVet.class, "factoryVet");
		ModLoader.RegisterTileEntity(TileEntityCollector.class, "factoryItemCollector");
		ModLoader.RegisterTileEntity(TileEntityBlockBreaker.class, "factoryBlockBreaker");
		ModLoader.RegisterTileEntity(TileEntityWeather.class, "factoryWeather");

		/*

		ModLoader.AddRecipe(new ItemStack(steelIngotItem, 5), new Object[]
			{
				" C ", "CIC", " C ",
				Character.valueOf('C'), Item.coal,
				Character.valueOf('I'), Item.ingotIron
		    }
		);
		ModLoader.AddRecipe(new ItemStack(conveyorBlock, 1), new Object[]
			{
				"R", "S",
				Character.valueOf('R'), Item.redstone,
				Character.valueOf('S'), steelIngotItem
		    }
		);
		ModLoader.AddRecipe(new ItemStack(harvesterBlock, 1), new Object[]
		    {
				"SSS", "SXS", "SSS",
				Character.valueOf('X'), Item.axeSteel,
				Character.valueOf('S'), steelIngotItem
		    }
		);
		ModLoader.AddRecipe(new ItemStack(harvesterBlock, 1), new Object[]
 		    {
 				"SSS", "SWS", "SSS",
 				Character.valueOf('W'), Item.swordSteel,
 				Character.valueOf('S'), steelIngotItem
 		    }
 		);
		ModLoader.AddRecipe(new ItemStack(planterBlock, 1), new Object[]
		    {
				"SSS", "SHS", "SSS",
				Character.valueOf('H'), Item.hoeSteel,
				Character.valueOf('S'), steelIngotItem
		    }
		);
		ModLoader.AddRecipe(new ItemStack(rancherBlock, 1), new Object[]
			{
				"SSS", "SHS", "SSS",
				Character.valueOf('H'), Item.shears,
				Character.valueOf('S'), steelIngotItem
		    }
		);
		
		ModLoader.AddRecipe(new ItemStack(fisherBlock, 1), new Object[]
 			{
 				"SSS", "SFS", "SSS",
 				Character.valueOf('F'), Item.fishingRod,
 				Character.valueOf('S'), steelIngotItem
 			}
 		);
		ModLoader.AddRecipe(new ItemStack(cargoRailPickupBlock, 2), new Object[]
 			{
 				" C ", "SDS", "SSS",
 				Character.valueOf('C'), Block.chest,
				Character.valueOf('S'), steelIngotItem,
 				Character.valueOf('D'), Block.railDetector
 			}
 		);
		ModLoader.AddRecipe(new ItemStack(cargoRailDropoffBlock, 2), new Object[]
  			{
				"SSS", "SDS", " C ",
				Character.valueOf('C'), Block.chest,
				Character.valueOf('S'), steelIngotItem,
				Character.valueOf('D'), Block.railDetector
			}
		);
		ModLoader.AddRecipe(new ItemStack(passengerRailPickupBlock, 3), new Object[]
 			{
 				" L ", "SDS", "SSS",
				Character.valueOf('L'), Block.blockLapis,
				Character.valueOf('S'), steelIngotItem,
				Character.valueOf('D'), Block.railDetector
 			}
 		);
		ModLoader.AddRecipe(new ItemStack(passengerRailDropoffBlock, 3), new Object[]
			{
 				"SSS", "SDS", " L ",
				Character.valueOf('L'), Block.blockLapis,
				Character.valueOf('S'), steelIngotItem,
				Character.valueOf('D'), Block.railDetector
			}
		);
		
		
		
		ModLoader.AddRecipe(new ItemStack(factoryHammerItem, 1), new Object[]
            {
				"SSS", " T ", " T ",
				Character.valueOf('S'), steelIngotItem,
				Character.valueOf('T'), Item.stick
            }
		);
		ModLoader.AddRecipe(new ItemStack(fertilizerItem, 1), new Object[]
 			{
 				"SSS", "SPS", "SSS",
 				Character.valueOf('P'), Block.sapling,
 				Character.valueOf('S'), steelIngotItem
 			}
 		);
		ModLoader.AddRecipe(new ItemStack(fertilizerItem, 1), new Object[]
   			{
   				"SSS", "SPS", "SSS",
   				Character.valueOf('P'), new ItemStack(Block.sapling, 1, 1),
   				Character.valueOf('S'), steelIngotItem
   			}
   		);
 		ModLoader.AddRecipe(new ItemStack(fertilizerItem, 1), new Object[]
 			{
 				"SSS", "SPS", "SSS",
 				Character.valueOf('P'), new ItemStack(Block.sapling, 1, 2),
 				Character.valueOf('S'), steelIngotItem
 			}
 		);*/
		
		registerPlantable(new PlantableStandard(Block.sapling.blockID, Block.sapling.blockID));
		registerPlantable(new PlantableStandard(Item.reed.shiftedIndex, Block.reed.blockID));
		registerPlantable(new PlantableStandard(Block.cactus.blockID, Block.cactus.blockID));
		registerPlantable(new PlantableStandard(Item.pumpkinSeeds.shiftedIndex, Block.pumpkinStem.blockID));
		registerPlantable(new PlantableStandard(Item.melonSeeds.shiftedIndex, Block.melonStem.blockID));
		registerPlantable(new PlantableStandard(Block.mushroomBrown.blockID, Block.mushroomBrown.blockID));
		registerPlantable(new PlantableStandard(Block.mushroomRed.blockID, Block.mushroomRed.blockID));
		registerPlantable(new PlantableWheat());
		
		registerHarvestable(new HarvestableStandard(Block.wood.blockID, HarvestType.Tree));
		registerHarvestable(new HarvestableStandard(Block.leaves.blockID, HarvestType.TreeLeaf));
		registerHarvestable(new HarvestableStandard(Block.reed.blockID, HarvestType.LeaveBottom));
		registerHarvestable(new HarvestableStandard(Block.cactus.blockID, HarvestType.LeaveBottom));
		registerHarvestable(new HarvestableStandard(Block.plantRed.blockID, HarvestType.Normal));
		registerHarvestable(new HarvestableStandard(Block.plantYellow.blockID, HarvestType.Normal));
		registerHarvestable(new HarvestableStandard(Block.tallGrass.blockID, HarvestType.Normal));
		registerHarvestable(new HarvestableStandard(Block.mushroomCapBrown.blockID, HarvestType.Tree));
		registerHarvestable(new HarvestableStandard(Block.mushroomCapRed.blockID, HarvestType.Tree));
		registerHarvestable(new HarvestableStemPlant(Block.pumpkin.blockID, HarvestType.Normal));
		registerHarvestable(new HarvestableStemPlant(Block.melon.blockID, HarvestType.Normal));
		registerHarvestable(new HarvestableWheat());
		registerHarvestable(new HarvestableVine());
		if(Util.getBool(harvesterHarvestsSmallMushrooms))
		{
			registerHarvestable(new HarvestableStandard(Block.mushroomBrown.blockID, HarvestType.Normal));
			registerHarvestable(new HarvestableStandard(Block.mushroomRed.blockID, HarvestType.Normal));
		}
		
		registerFertilizable(new FertilizableSapling());
		registerFertilizable(new FertilizableWheat());
		registerFertilizable(new FertilizableGiantMushroom(Block.mushroomBrown.blockID));
		registerFertilizable(new FertilizableGiantMushroom(Block.mushroomRed.blockID));
		registerFertilizable(new FertilizableStemPlants(Block.pumpkinStem.blockID));
		registerFertilizable(new FertilizableStemPlants(Block.melonStem.blockID));
		
		registerFertilizerItem(Item.dyePowder.shiftedIndex);
		
		registerRanchable(new RanchableChicken());
		registerRanchable(new RanchableCow());
		registerRanchable(new RanchableStandard(EntityPig.class, new ItemStack(Item.porkRaw), 45, 1, 40));
		registerRanchable(new RanchableStandard(EntitySheep.class, new ItemStack(Block.cloth), 30, 1, 40));
		registerRanchable(new RanchableStandard(EntitySlime.class, new ItemStack(Item.slimeBall), 25, 1, 30));
		registerRanchable(new RanchableStandard(EntitySquid.class, new ItemStack(Item.dyePowder), 10, 1, 40));
	}
	
	public static void afterModsLoaded()
	{
		API.liquids.add(new LiquidData(milkItem.shiftedIndex, Item.bucketMilk.shiftedIndex));
	}
	
	private static void setupTextures()
	{
		factoryHammerTexture = 0;
		steelIngotTexture = 2;
		milkTexture = 3;
		
		// 0 bottom 1 top 2 east 3 west 4 north 5 south
		cargoRailDropoffTexture = 0;
		cargoRailPickupTexture = 1;
		passengerRailDropoffTexture = 2;
		passengerRailPickupTexture = 3;
		steelSideTexture = 4;
		steelHoleTexture = 5;
		planterCactusTexture = 6;
		planterMushroomTexture = 7;
		planterSaplingTexture = 8;
		planterSugarTexture = 9;
		conveyorTexture = 10;
		conveyorOffTexture = 11;
		harvesterAnimatedTexture = 12;
		rancherAnimatedTexture = 13;
		fisherBucketTexture = 14;
		fisherFishTexture = 15;
		harvesterSideTexture = 16;
		rancherSideTexture = 17;
		fertilizerBackTexture = 18;
		fertilizerSideTexture = 19;
		conveyorStillOffTexture = 20;
		vetSideTexture = 21;
		collectorSideTexture = 22;
		weatherTopTexture = 23;
		weatherSnowSideTexture = 24;
		blockBreakerAnimatedTexture = 25;
		fertilizerAnimatedTexture = 26;
		vetAnimatedTexture = 27;
		blockBreakerSideTexture = 28;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][0] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][2] = planterCactusTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][3] = planterMushroomTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][4] = planterSaplingTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][5] = planterSugarTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][0] = rancherAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][1] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][2] = fisherBucketTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][3] = fisherBucketTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][4] = fisherFishTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][5] = fisherFishTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][5] = harvesterAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][4] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][2] = harvesterSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][3] = harvesterSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][5] = rancherAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][4] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][2] = rancherSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][3] = rancherSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][5] = fertilizerAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][4] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][2] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][3] = fertilizerBackTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Vet)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Vet)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Vet)][5] = vetAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Vet)][4] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Vet)][2] = vetSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Vet)][3] = vetSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Collector)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Collector)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Collector)][5] = collectorSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Collector)][4] = collectorSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Collector)][2] = collectorSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Collector)][3] = collectorSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Breaker)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Breaker)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Breaker)][5] = blockBreakerAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Breaker)][4] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Breaker)][2] = blockBreakerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Breaker)][3] = blockBreakerSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Weather)][0] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Weather)][1] = weatherTopTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Weather)][2] = fisherBucketTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Weather)][3] = fisherBucketTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Weather)][4] = weatherSnowSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Weather)][5] = weatherSnowSideTexture;
	}
	
	private static void loadConfig()
	{
		File configFile = new File(proxy.getConfigPath());
		Configuration c = new Configuration(configFile);
		c.load();
		machineBlockId = c.getOrCreateBlockIdProperty("ID.MachineBlock", 124);
		conveyorBlockId = c.getOrCreateBlockIdProperty("ID.ConveyorBlock", 125);
		passengerPickupRailBlockId = c.getOrCreateBlockIdProperty("ID.PassengerRailPickupBlock", 129);
		passengerDropoffRailBlockId = c.getOrCreateBlockIdProperty("ID.PassengerRailDropoffBlock", 130);
		cargoPickupRailBlockId = c.getOrCreateBlockIdProperty("ID.CargoRailPickupBlock", 131);
		cargoDropoffRailBlockId = c.getOrCreateBlockIdProperty("ID.CargoRailDropoffBlock", 132);
		
		steelIngotItemId = c.getOrCreateIntProperty("ID.SteelIngot", Configuration.ITEM_PROPERTY, 124);
		hammerItemId = c.getOrCreateIntProperty("ID.Hammer", Configuration.ITEM_PROPERTY, 988);
		milkItemId = c.getOrCreateIntProperty("ID.Milk", Configuration.ITEM_PROPERTY, 987);
		
		animateBlockFaces = c.getOrCreateBooleanProperty("AnimateBlockFaces", Configuration.GENERAL_PROPERTY, true);
		animateBlockFaces.comment = "Set to false to disable animation of harvester, rancher, conveyor, etc. This may be required if using certain mods that affect rendering.";
		animationTileSize = c.getOrCreateIntProperty("AnimationTileSize", Configuration.GENERAL_PROPERTY, 16);
		animationTileSize.comment = "Set this to match the size of your texture pack to allow animations to work with HD texture packs. Setting this incorrectly may cause unreliable behavior.";
		playSounds = c.getOrCreateBooleanProperty("PlaySounds", Configuration.GENERAL_PROPERTY, true);
		playSounds.comment = "Set to false to disable the harvester's sound when a block is harvested.";
		harvesterHarvestsSmallMushrooms = c.getOrCreateBooleanProperty("HarvesterHarvestsSmallMushrooms", Configuration.GENERAL_PROPERTY, false);
		harvesterHarvestsSmallMushrooms.comment = "Set to true to enable old-style mushroom farms (but will prevent giant mushrooms from working correctly as the small ones will be harvested immediately)";
		rancherInjuresAnimals = c.getOrCreateBooleanProperty("RancherInjuresAnimals", Configuration.GENERAL_PROPERTY, true);
		rancherInjuresAnimals.comment = "If false, the rancher will never injure animals. Intended for those who want to play in a (pseudo-)creative style.";
		machinesCanDropInChests = c.getOrCreateBooleanProperty("MachinesCanDropInChests", Configuration.GENERAL_PROPERTY, true);
		machinesCanDropInChests.comment = "Set to false to disable machines placing items into chests adjacent to them";
	
		treeSearchMaxHorizontal = c.getOrCreateIntProperty("SearchDistance.TreeMaxHoriztonal", Configuration.GENERAL_PROPERTY, 5);
		treeSearchMaxHorizontal.comment = "When searching for parts of a tree, how far out to the sides (radius) to search";
		treeSearchMaxVertical = c.getOrCreateIntProperty("SearchDistance.TreeMaxVertical", Configuration.GENERAL_PROPERTY, 15);
		treeSearchMaxVertical.comment = "When searching for parts of a tree, how far up to search";
		verticalHarvestSearchMaxVertical = c.getOrCreateIntProperty("SearchDistance.StackingBlockMaxVertical", Configuration.GENERAL_PROPERTY, 3);
		verticalHarvestSearchMaxVertical.comment = "How far upward to search for members of \"stacking\" blocks, like cactus and sugarcane";
		passengerRailSearchMaxVertical = c.getOrCreateIntProperty("SearchDistance.PassengerRailMaxVertical", Configuration.GENERAL_PROPERTY, 2);
		passengerRailSearchMaxVertical.comment = "When searching for players or dropoff locations, how far up to search";
		passengerRailSearchMaxHorizontal = c.getOrCreateIntProperty("SearchDistance.PassengerRailMaxHorizontal", Configuration.GENERAL_PROPERTY, 3);
		passengerRailSearchMaxHorizontal.comment = "When searching for players or dropoff locations, how far out to the sides (radius) to search";
		
		powerSystemProperty = c.getOrCreateProperty("PowerSystem", Configuration.GENERAL_PROPERTY, "redstone");
		powerSystemProperty.comment = "Set whether MFR will run off classic alternating redstone of BuildCraft's power system. Values other than \"redstone\" or \"buildcraft\" will cause the system to revert to redstone mode";
		if(powerSystemProperty.value.toLowerCase().equals("buildcraft"))
		{
			powerSystem = PowerSystem.BuildCraft;
		}
		else
		{
			powerSystem = PowerSystem.Redstone;
			powerSystemProperty.value = "redstone";
		}
		
		c.save();
	}
	
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		TileEntityPlanter.registerPlantable(plantable);
	}
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		TileEntityHarvester.registerHarvestable(harvestable);
	}
	
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		TileEntityFertilizer.registerFertilizable(fertilizable);
	}
	
	public static void registerFertilizerItem(int itemId)
	{
		TileEntityFertilizer.registerFertilizerItem(itemId);
	}
	
	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		TileEntityRancher.registerRanchable(ranchable);
	}

	public enum PowerSystem
	{
		Redstone,
		BuildCraft
	}
	
	public enum Machine
	{
		Planter,
		Fisher,
		Harvester,
		Fertilizer,
		Rancher,
		Vet,
		Collector,
		Breaker,
		Weather
	}
}
