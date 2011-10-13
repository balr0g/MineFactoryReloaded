package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityPig;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MLProp;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.Item;

public class MineFactoryReloadedCore
{
	public static PowerSystem powerSystem = PowerSystem.IndustrialCraft;
	
	public static String terrainTexture = "/MineFactorySprites/terrain.png";
	public static String itemTexture = "/MineFactorySprites/items.png";
	
	public static Block conveyorBlock;
	public static Block fertilizerBlock;
	public static Block harvesterBlock;
	public static Block rancherBlock;
	public static Block planterBlock;
	public static Block fisherBlock;
	public static Block passengerRailDropoffBlock;
	public static Block passengerRailPickupBlock;
	public static Block cargoRailDropoffBlock;
	public static Block cargoRailPickupBlock;
	
	public static Item fertilizerItem;
	public static Item steelIngotItem;
	public static Item factoryHammerItem;
	
	public static int conveyorNormalTexture;
	public static int conveyorReverseTexture;
	public static int harvesterAnimatedTexture;
	public static int rancherAnimatedTexture;
	public static int steelSideTexture;
	public static int planterSaplingTexture;
	public static int planterCactusTexture;
	public static int planterSugarTexture;
	public static int planterMushroomTexture;
	public static int steelHoleSideTexture;
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
	
	// TODO
	@MLProp(min=1, max=1023)
	public static int ConveyorBlockID = 125;
	@MLProp(min=1, max=1023)
	public static int FertilizerBlockID = 126;
	@MLProp(min=1, max=1023)
	public static int HarvesterBlockID = 127;
	@MLProp(min=1, max=1023)
	public static int PlanterBlockID = 128;
	@MLProp(min=1, max=1023)
	public static int PassengerPickupRailBlockID = 129;
	@MLProp(min=1, max=1023)
	public static int PassengerDropoffRailBlockID = 130;
	@MLProp(min=1, max=1023)
	public static int CargoPickupRailBlockID = 131;
	@MLProp(min=1, max=1023)
	public static int CargoDropoffRailBlockID = 132;
	@MLProp(min=1, max=1023)
	public static int RancherBlockID = 133;
	@MLProp(min=1, max=1023)
	public static int FisherBlockID = 134;
	
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
		
		cargoRailDropoffTexture = 0;
		cargoRailPickupTexture = 1;
		passengerRailDropoffTexture = 2;
		passengerRailPickupTexture = 3;
		steelSideTexture = 4;
		steelHoleSideTexture = 5;
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

		factoryWrenchTexture = 0;
		fertilizerItemTexture = 1;
		steelIngotTexture = 2;
		
		conveyorBlock = new BlockFactoryConveyor(ConveyorBlockID, conveyorNormalTexture);
		fertilizerBlock = new BlockFactoryFertilizer(FertilizerBlockID, fertilizerSideTexture);
		harvesterBlock = new BlockFactoryHarvester(HarvesterBlockID, steelSideTexture);
		planterBlock = new BlockFactoryPlanter(PlanterBlockID, steelSideTexture);
		passengerRailPickupBlock = new BlockRailPassengerPickup(PassengerPickupRailBlockID, passengerRailPickupTexture);
		passengerRailDropoffBlock = new BlockRailPassengerDropoff(PassengerDropoffRailBlockID, passengerRailDropoffTexture);
		cargoRailDropoffBlock = new BlockRailCargoDropoff(CargoDropoffRailBlockID, cargoRailDropoffTexture);
		cargoRailPickupBlock = new BlockRailCargoPickup(CargoPickupRailBlockID, cargoRailPickupTexture);
		rancherBlock = new BlockFactoryRancher(RancherBlockID, steelSideTexture);
		fisherBlock = new BlockFactoryFisher(FisherBlockID, steelHoleSideTexture);
		
		fertilizerItem = (new ItemFactoryFertilizer(FertilizerItemID))
			.setIconIndex(fertilizerItemTexture)
			.setItemName("itemFertilizer");
		steelIngotItem = (new ItemFactory(SteelIngotItemID))
			.setIconIndex(steelIngotTexture)
			.setItemName("steelIngot");
		factoryHammerItem = (new ItemFactory(FactoryWrenchItemID))
			.setIconIndex(factoryWrenchTexture)
			.setItemName("factoryWrench")
			.setMaxStackSize(1);
		
		Item.itemsList[rancherBlock.blockID] = null;
		Item.itemsList[rancherBlock.blockID] = new ItemRancher(rancherBlock.blockID - 256).setItemName("rancherItem");
		
		ModLoader.RegisterBlock(conveyorBlock);
		ModLoader.RegisterBlock(fertilizerBlock);
		ModLoader.RegisterBlock(harvesterBlock);
		ModLoader.RegisterBlock(rancherBlock);
		ModLoader.RegisterBlock(planterBlock);
		ModLoader.RegisterBlock(fisherBlock);
		ModLoader.RegisterBlock(passengerRailPickupBlock);
		ModLoader.RegisterBlock(passengerRailDropoffBlock);
		ModLoader.RegisterBlock(cargoRailDropoffBlock);
		ModLoader.RegisterBlock(cargoRailPickupBlock);

		ModLoader.RegisterTileEntity(net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityFertilizer.class, "Fertilizer");
		ModLoader.RegisterTileEntity(net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityPlanter.class, "Planter");
		ModLoader.RegisterTileEntity(net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityHarvester.class, "Harvester");
		ModLoader.RegisterTileEntity(net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityRancher.class, "Rancher");
		ModLoader.RegisterTileEntity(net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityVeterinary.class, "Veterinary");
		ModLoader.RegisterTileEntity(net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityFisher.class, "Fisher");

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
 		);
		
		registerPlantable(new FactoryPlantableStandard(Block.sapling.blockID, Block.sapling.blockID));
		registerPlantable(new FactoryPlantableStandard(Item.reed.shiftedIndex, Block.reed.blockID));
		registerPlantable(new FactoryPlantableStandard(Block.cactus.blockID, Block.cactus.blockID));
		registerPlantable(new FactoryPlantableStandard(Item.pumpkinSeeds.shiftedIndex, Block.pumpkinStem.blockID));
		registerPlantable(new FactoryPlantableStandard(Item.melonSeeds.shiftedIndex, Block.melonStem.blockID));
		registerPlantable(new FactoryPlantableStandard(Block.mushroomBrown.blockID, Block.mushroomBrown.blockID));
		registerPlantable(new FactoryPlantableStandard(Block.mushroomRed.blockID, Block.mushroomRed.blockID));
		registerPlantable(new FactoryPlantableWheat());
		
		registerHarvestable(new FactoryHarvestableStandard(Block.wood.blockID, FactoryHarvestType.Tree));
		registerHarvestable(new FactoryHarvestableStandard(Block.leaves.blockID, FactoryHarvestType.Tree));
		registerHarvestable(new FactoryHarvestableStandard(Block.reed.blockID, FactoryHarvestType.LeaveBottom));
		registerHarvestable(new FactoryHarvestableStandard(Block.cactus.blockID, FactoryHarvestType.LeaveBottom));
		registerHarvestable(new FactoryHarvestableStandard(Block.plantRed.blockID, FactoryHarvestType.Normal));
		registerHarvestable(new FactoryHarvestableStandard(Block.plantYellow.blockID, FactoryHarvestType.Normal));
		registerHarvestable(new FactoryHarvestableStandard(Block.tallGrass.blockID, FactoryHarvestType.Normal));
		registerHarvestable(new FactoryHarvestableStandard(Block.mushroomCapBrown.blockID, FactoryHarvestType.Tree));
		registerHarvestable(new FactoryHarvestableStandard(Block.mushroomCapRed.blockID, FactoryHarvestType.Tree));
		registerHarvestable(new FactoryHarvestableStemPlant(Block.pumpkin.blockID, FactoryHarvestType.Normal));
		registerHarvestable(new FactoryHarvestableStemPlant(Block.melon.blockID, FactoryHarvestType.Normal));
		registerHarvestable(new FactoryHarvestableWheat());
		if(HarvesterHarvestsSmallMushrooms)
		{
			registerHarvestable(new FactoryHarvestableStandard(Block.mushroomBrown.blockID, FactoryHarvestType.Normal));
			registerHarvestable(new FactoryHarvestableStandard(Block.mushroomRed.blockID, FactoryHarvestType.Normal));
		}
		
		registerFertilizable(new FactoryFertilizableSapling());
		registerFertilizable(new FactoryFertilizableWheat());
		registerFertilizable(new FactoryFertilizableGiantMushroom(Block.mushroomBrown.blockID));
		registerFertilizable(new FactoryFertilizableGiantMushroom(Block.mushroomRed.blockID));
		registerFertilizable(new FactoryFertilizableStemPlants(Block.pumpkinStem.blockID));
		registerFertilizable(new FactoryFertilizableStemPlants(Block.melonStem.blockID));
		
		registerFertilizerItem(Item.dyePowder.shiftedIndex);
		
		registerRanchable(new FactoryRanchableChicken());
		registerRanchable(new FactoryRanchableCow());
		registerRanchable(new FactoryRanchableStandard(EntityPig.class, new ItemStack(Item.porkRaw), 45, 1, 40));
		registerRanchable(new FactoryRanchableStandard(EntitySheep.class, new ItemStack(Block.cloth), 30, 1, 40));
		registerRanchable(new FactoryRanchableStandard(EntitySlime.class, new ItemStack(Item.slimeBall), 25, 1, 30));
		registerRanchable(new FactoryRanchableStandard(EntitySquid.class, new ItemStack(Item.dyePowder), 10, 1, 40));
	}
	
	
	public static int addToInventory(IInventory targetInventory, ItemStack stackToAdd)
	{
		int amountLeftToAdd = stackToAdd.stackSize;
		int stackSizeLimit;
		if(stackToAdd.itemID >= Block.blocksList.length)
		{
			stackSizeLimit = Math.min(targetInventory.getInventoryStackLimit(), Item.itemsList[stackToAdd.itemID].getItemStackLimit());
		}
		else
		{
			stackSizeLimit = targetInventory.getInventoryStackLimit();
		}
		int slotIndex;
		
		while(amountLeftToAdd > 0)
		{
			slotIndex = getAvailableSlot(targetInventory, stackToAdd);
			if(slotIndex < 0)
			{
				break;
			}
			ItemStack targetStack = targetInventory.getStackInSlot(slotIndex);
			if(targetStack == null)
			{
				if(stackToAdd.stackSize <= stackSizeLimit)
				{
					ItemStack s = stackToAdd.copy();
					s.stackSize = amountLeftToAdd;
					targetInventory.setInventorySlotContents(slotIndex, s);
					amountLeftToAdd = 0;
					break;
				}
				else
				{
					ItemStack s = stackToAdd.copy();
					s.stackSize = stackSizeLimit;
					targetInventory.setInventorySlotContents(slotIndex, stackToAdd);
					amountLeftToAdd -= s.stackSize;
				}
			}
			else
			{
				int amountToAdd = Math.min(amountLeftToAdd, stackSizeLimit - targetStack.stackSize);
				targetStack.stackSize += amountToAdd;
				amountLeftToAdd -= amountToAdd;
			}
		}
		
		return amountLeftToAdd;
	}
	
	private static int getAvailableSlot(IInventory inventory, ItemStack stack)
	{
		int stackSizeLimit;
		if(stack.itemID >= Block.blocksList.length)
		{
			stackSizeLimit = Math.min(inventory.getInventoryStackLimit(), Item.itemsList[stack.itemID].getItemStackLimit());
		}
		else
		{
			stackSizeLimit = inventory.getInventoryStackLimit();
		}
		for(int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack targetStack = inventory.getStackInSlot(i);
			if(targetStack == null)
			{
				return i;
			}
			if(targetStack.itemID == stack.itemID && targetStack.getItemDamage() == stack.getItemDamage()
					&& targetStack.stackSize < stackSizeLimit)
			{
				return i;
			}
		}
		
		return -1;
	}

	public static List<IInventory> findChests(World world, int x, int y, int z)
	{
		List<IInventory> chests = new LinkedList<IInventory>();
		TileEntity te;
		
		te = world.getBlockTileEntity(x + 1, y, z);
		if(te != null && te instanceof IInventory)
		{
			chests.add(checkForDoubleChest(world, te, x + 1, y, z));
		}
		te = world.getBlockTileEntity(x - 1, y, z);
		if(te != null && te instanceof IInventory)
		{
			chests.add(checkForDoubleChest(world, te, x - 1, y, z));
		}
		te = world.getBlockTileEntity(x, y, z + 1);
		if(te != null && te instanceof IInventory)
		{
			chests.add(checkForDoubleChest(world, te, x, y, z + 1));
		}
		te = world.getBlockTileEntity(x, y, z - 1);
		if(te != null && te instanceof IInventory)
		{
			chests.add(checkForDoubleChest(world, te, x, y, z - 1));
		}
		te = world.getBlockTileEntity(x, y + 1, z);
		if(te != null && te instanceof IInventory)
		{
			chests.add(checkForDoubleChest(world, te, x, y + 1, z));
		}
		te = world.getBlockTileEntity(x, y - 1, z);
		if(te != null && te instanceof IInventory)
		{
			chests.add(checkForDoubleChest(world, te, x, y - 1, z));
		}
		
		return chests;
	}
	
	private static IInventory checkForDoubleChest(World world, TileEntity te, int x, int y, int z)
	{
		int blockId = world.getBlockId(x, y, z);
		if(blockId == Block.chest.blockID)
		{
			if(world.getBlockId(x + 1, y, z) == Block.chest.blockID)
			{
				return new InventoryLargeChest("Large Chest", ((IInventory)te), ((IInventory)world.getBlockTileEntity(x + 1, y, z)));
			}
			if(world.getBlockId(x - 1, y, z) == Block.chest.blockID)
			{
				return new InventoryLargeChest("Large Chest", ((IInventory)te), ((IInventory)world.getBlockTileEntity(x - 1, y, z)));
			}
			if(world.getBlockId(x, y, z + 1) == Block.chest.blockID)
			{
				return new InventoryLargeChest("Large Chest", ((IInventory)te), ((IInventory)world.getBlockTileEntity(x, y, z + 1)));
			}
			if(world.getBlockId(x, y, z - 1) == Block.chest.blockID)
			{
				return new InventoryLargeChest("Large Chest", ((IInventory)te), ((IInventory)world.getBlockTileEntity(x, y, z - 1)));
			}
		}
		return ((IInventory)te);
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
		BuildCraft,
		IndustrialCraft
	}
}
