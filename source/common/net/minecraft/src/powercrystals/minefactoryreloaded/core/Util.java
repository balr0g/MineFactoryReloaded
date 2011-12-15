package net.minecraft.src.powercrystals.minefactoryreloaded.core;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.api.API;
import net.minecraft.src.buildcraft.api.IPipeEntry;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.forge.ISidedInventory;
import net.minecraft.src.forge.Property;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class Util
{
	public static int addToInventory(InventoryAndSide targetInventory, ItemStack stackToAdd)
	{
		int amountLeftToAdd = stackToAdd.stackSize;
		int stackSizeLimit;
		if(stackToAdd.itemID >= Block.blocksList.length)
		{
			stackSizeLimit = Math.min(targetInventory.getInventory().getInventoryStackLimit(), Item.itemsList[stackToAdd.itemID].getItemStackLimit());
		}
		else
		{
			stackSizeLimit = targetInventory.getInventory().getInventoryStackLimit();
		}
		int slotIndex;
		
		while(amountLeftToAdd > 0)
		{
			slotIndex = getAvailableSlot(targetInventory.getInventory(), stackToAdd, targetInventory.getSide());
			if(slotIndex < 0)
			{
				break;
			}
			ItemStack targetStack = targetInventory.getInventory().getStackInSlot(slotIndex);
			if(targetStack == null)
			{
				if(stackToAdd.stackSize <= stackSizeLimit)
				{
					ItemStack s = stackToAdd.copy();
					s.stackSize = amountLeftToAdd;
					targetInventory.getInventory().setInventorySlotContents(slotIndex, s);
					amountLeftToAdd = 0;
					break;
				}
				else
				{
					ItemStack s = stackToAdd.copy();
					s.stackSize = stackSizeLimit;
					targetInventory.getInventory().setInventorySlotContents(slotIndex, stackToAdd);
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
	
	private static int getAvailableSlot(IInventory inventory, ItemStack stack, int side)
	{
		int firstslot;
		int lastslot;
		if(inventory instanceof ISidedInventory)
		{
			firstslot = ((ISidedInventory)inventory).getStartInventorySide(side);
			lastslot = firstslot + ((ISidedInventory)inventory).getSizeInventorySide(side);
		}
		else
		{
			firstslot = 0;
			lastslot = inventory.getSizeInventory() - 1;
		}
		
		int stackSizeLimit;
		if(stack.itemID >= Block.blocksList.length)
		{
			stackSizeLimit = Math.min(inventory.getInventoryStackLimit(), Item.itemsList[stack.itemID].getItemStackLimit());
		}
		else
		{
			stackSizeLimit = inventory.getInventoryStackLimit();
		}
		for(int i = firstslot; i <= lastslot; i++)
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

	public static List<Orientations> findPipes(World world, int x, int y, int z)
	{
		List<Orientations> pipes = new LinkedList<Orientations>();
		BlockPosition ourpos = new BlockPosition(x, y, z);
		for(Orientations o : Orientations.values())
		{
			BlockPosition bp = new BlockPosition(ourpos);
			bp.orientation = o;
			bp.moveForwards(1);
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te instanceof IPipeEntry)
			{
				pipes.add(o);
			}
		}
		
		return pipes;
	}
	
	public static List<InventoryAndSide> findChests(World world, int x, int y, int z)
	{
		List<InventoryAndSide> chests = new LinkedList<InventoryAndSide>();
		BlockPosition ourpos = new BlockPosition(x, y, z);
		
		for(BlockPosition bp : ourpos.getAdjacent(true))
		{
			TileEntity te = world.getBlockTileEntity(bp.x, bp.y, bp.z);
			if(te != null && te instanceof IInventory)
			{
				chests.add(new InventoryAndSide(checkForDoubleChest(world, te, bp), getDestinationSide(x, y, z, bp.x, bp.y, bp.z)));
			}
		}
		return chests;
	}
	
	public static int getDestinationSide(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		if(y2 > y1) // destination above us, bottom
		{
			return 0;
		}
		if(y2 < y1) // destination below us, top
		{
			return 1;
		}
		if(x2 > x1) // destination is X+ from us, X-
		{
			return 2;
		}
		if(x2 < x1) // destination is X- from us, X+
		{
			return 4;
		}
		if(z2 > z1) // destination is Z+ from us, Z-
		{
			return 3;
		}
		if(z2 < z1) // destination is Z- from us, Z+
		{
			return 3;
		}
		return -1; // equal
	}
	
	private static IInventory checkForDoubleChest(World world, TileEntity te, BlockPosition chestloc)
	{
		if(world.getBlockId(chestloc.x, chestloc.y, chestloc.z) == Block.chest.blockID)
		{
			for(BlockPosition bp : chestloc.getAdjacent(false))
			{
				if(world.getBlockId(bp.x, bp.y, bp.z) == Block.chest.blockID)
				{
					return new InventoryLargeChest("Large Chest", ((IInventory)te), ((IInventory)world.getBlockTileEntity(bp.x, bp.y, bp.z)));
				}
			}
		}
		return ((IInventory)te);
	}
	
	public static boolean isRedstonePowered(TileEntity te)
	{
		if(te.worldObj.isBlockIndirectlyGettingPowered(te.xCoord, te.yCoord, te.zCoord))
		{
			return true;
		}
		for(BlockPosition bp : new BlockPosition(te).getAdjacent(false))
		{
			int blockId = te.worldObj.getBlockId(bp.x, bp.y, bp.z);
			if(blockId == Block.redstoneWire.blockID && Block.blocksList[blockId].isPoweringTo(te.worldObj, bp.x, bp.y, bp.z, 1))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoldingWrench(EntityPlayer player)
	{
		return player.inventory.getCurrentItem() != null && 
			player.inventory.getCurrentItem().itemID == MineFactoryReloadedCore.factoryHammerItem.shiftedIndex;
	}
	
	public static boolean getBool(Property property)
	{
		return Boolean.parseBoolean(property.value);
	}
	
	public static int getInt(Property property)
	{
		return Integer.parseInt(property.value);
	}
	
	public static boolean isBlockUnbreakable(Block b)
	{
		return API.unbreakableBlock(b.blockID) || b.getHardness() < 0 || b instanceof BlockFluid;
	}
}
