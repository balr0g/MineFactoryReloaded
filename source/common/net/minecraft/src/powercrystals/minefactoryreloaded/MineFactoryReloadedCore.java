package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityPig;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MLProp;
import net.minecraft.src.Material;
import net.minecraft.src.ModLoader;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableGiantMushroom;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableSapling;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableStemPlants;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.FertilizableWheat;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestType;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableStandard;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableStemPlant;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.HarvestableWheat;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.PlantableStandard;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.PlantableWheat;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.RanchableChicken;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.RanchableCow;
import net.minecraft.src.powercrystals.minefactoryreloaded.farmables.RanchableStandard;

public class MineFactoryReloadedCore
{
	public static PowerSystem powerSystem = PowerSystem.BuildCraft;
	
	public static String terrainTexture = "/MineFactorySprites/terrain.png";
	public static String itemTexture = "/MineFactorySprites/items.png";
	
	public static Block machineBlock;
	
	public static Block passengerRailDropoffBlock;
	public static Block passengerRailPickupBlock;
	public static Block cargoRailDropoffBlock;
	public static Block cargoRailPickupBlock;
	
	public static Item steelIngotItem;
	public static Item factoryHammerItem;
	
	public static Item machineItem;
	
	public static int conveyorNormalTexture;
	public static int conveyorReverseTexture;
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
	
	public static int factoryWrenchTexture;
	public static int steelIngotTexture;
	public static int fertilizerItemTexture;
	
	public static IMFRProxy proxy;
	
	public static Map<MineFactoryReloadedCore.Machine, Integer> machineMetadataMappings;
	
	// TODO
	@MLProp(min=1, max=1023)
	public static int PassengerPickupRailBlockID = 129;
	@MLProp(min=1, max=1023)
	public static int PassengerDropoffRailBlockID = 130;
	@MLProp(min=1, max=1023)
	public static int CargoPickupRailBlockID = 131;
	@MLProp(min=1, max=1023)
	public static int CargoDropoffRailBlockID = 132;
	
	@MLProp(min=256, max=32767)
	public static int SteelIngotItemID = 124;
	@MLProp(min=256, max=32767)
	public static int FertilizerItemID = 989;
	@MLProp(min=256, max=32767)
	public static int FactoryWrenchItemID = 988;
	
	@MLProp(info="If true, conveyor belts and harvesters will be animated. If you are using HD textures or Minecraft Extended, you may need to disable this for blocks to render properly..")
	public static boolean AnimateBlockFaces = true;
	@MLProp(min=0, max=128, info="The maximum number of squares to search upward for trees when harvesting.")
	public static int TreeSearchMaxVertical = 15;
	@MLProp(min=0, max=128, info="The maximum number of squares to search on the X and Z axis for trees when harvesting. This is the distance from the center; not the entire width of the search area.")
	public static int TreeSearchMaxHorizontal = 5;
	@MLProp(min=0, max=128, info="The maximum number of squares to search on the X and Z axis for players to pick up or places to put them on. This is the distance from the center; not the entire width of the search area.")
	public static int PassengerRailMaxHorizontal = 3;
	@MLProp(min=0, max=128, info="The maximum number of squares to search on the Y axis for players to pick up or places to put them on. This is the distance from the center; not the entire width of the search area.")
	public static int PassengerRailMaxVertical = 2;
	@MLProp(min=0, max=128, info="The maximum number of squares to search upward for cactus and sugarcane blocks when harvesting.")
	public static int SugarAndCactusSearchMaxVertical = 3;
	@MLProp(info="If true, play sounds when a block is harvested.")
	public static boolean PlaySounds = true;
	@MLProp(info="If true, harvesters will attempt to drop harvested items into chests located behind them.")
	public static boolean HarvesterCanDropInChests = true;
	@MLProp(info="If true, the rancher will sometimes cause damage to animals it is harvesting from.")
	public static boolean RancherInjuresAnimals = true;
	@MLProp(info="If true, the harvester will harvest small (old-style) mushrooms. False by default so that they can be fertilized into big mushrooms.")
	public static boolean HarvesterHarvestsSmallMushrooms = false;
   
	@MLProp(info="The size of each tile for the animated textures, in pixels. Assumes square tiles.")
	public static int AnimationTileSize = 16;
	
	public static void Init(IMFRProxy proxyParam)
	{
		proxy = proxyParam;
		
		factoryWrenchTexture = 0;
		fertilizerItemTexture = 1;
		steelIngotTexture = 2;
		
		machineMetadataMappings = new HashMap<Machine, Integer>();
		machineMetadataMappings.put(Machine.Planter, 0);
		machineMetadataMappings.put(Machine.Fisher, 1);
		machineMetadataMappings.put(Machine.Harvester, 2);
		machineMetadataMappings.put(Machine.Rancher, 3);
		machineMetadataMappings.put(Machine.Fertilizer, 4);

		setupTextures();
		
		passengerRailPickupBlock = new BlockRailPassengerPickup(PassengerPickupRailBlockID, passengerRailPickupTexture);
		passengerRailDropoffBlock = new BlockRailPassengerDropoff(PassengerDropoffRailBlockID, passengerRailDropoffTexture);
		cargoRailDropoffBlock = new BlockRailCargoDropoff(CargoDropoffRailBlockID, cargoRailDropoffTexture);
		cargoRailPickupBlock = new BlockRailCargoPickup(CargoPickupRailBlockID, cargoRailPickupTexture);
		
		machineBlock = new BlockFactoryMachine(137, 0, Material.ice);
		
		steelIngotItem = (new ItemFactory(SteelIngotItemID))
			.setIconIndex(steelIngotTexture)
			.setItemName("steelIngot");
		factoryHammerItem = (new ItemFactory(FactoryWrenchItemID))
			.setIconIndex(factoryWrenchTexture)
			.setItemName("factoryWrench")
			.setMaxStackSize(1);

		ModLoader.RegisterBlock(machineBlock, ItemFactoryMachine.class);
		ModLoader.RegisterBlock(passengerRailPickupBlock);
		ModLoader.RegisterBlock(passengerRailDropoffBlock);
		ModLoader.RegisterBlock(cargoRailDropoffBlock);
		ModLoader.RegisterBlock(cargoRailPickupBlock);
		
		ModLoader.RegisterTileEntity(TileEntityFisher.class, "factoryFisher");
		ModLoader.RegisterTileEntity(TileEntityPlanter.class, "factoryPlanter");
		ModLoader.RegisterTileEntity(TileEntityHarvester.class, "factoryHarvester");
		ModLoader.RegisterTileEntity(TileEntityRancher.class, "factoryRancher");
		ModLoader.RegisterTileEntity(TileEntityFertilizer.class, "factoryFertilizer");

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
		registerHarvestable(new HarvestableStandard(Block.leaves.blockID, HarvestType.Tree));
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
		if(HarvesterHarvestsSmallMushrooms)
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
	
	private static void setupTextures()
	{
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
		conveyorNormalTexture = 10;
		conveyorReverseTexture = 11;
		harvesterAnimatedTexture = 12;
		rancherAnimatedTexture = 13;
		fisherBucketTexture = 14;
		fisherFishTexture = 15;
		harvesterSideTexture = 16;
		rancherSideTexture = 17;
		fertilizerBackTexture = 18;
		fertilizerSideTexture = 19;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][0] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][2] = planterCactusTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][3] = planterMushroomTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][4] = planterSaplingTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Planter)][5] = planterSugarTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][0] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][2] = fisherBucketTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][3] = fisherBucketTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][4] = fisherFishTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fisher)][5] = fisherFishTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][2] = harvesterAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][3] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][4] = harvesterSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Harvester)][5] = harvesterSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][0] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][1] = steelSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][2] = rancherAnimatedTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][3] = steelHoleTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][4] = rancherSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Rancher)][5] = rancherSideTexture;
		
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][0] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][1] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][2] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][3] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][4] = fertilizerSideTexture;
		BlockFactoryMachine.textures[machineMetadataMappings.get(Machine.Fertilizer)][5] = fertilizerBackTexture;
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
		Rancher
	}
}
