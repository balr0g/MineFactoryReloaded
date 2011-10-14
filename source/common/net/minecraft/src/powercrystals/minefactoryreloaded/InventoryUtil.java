package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryLargeChest;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class InventoryUtil
{
	
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

}
