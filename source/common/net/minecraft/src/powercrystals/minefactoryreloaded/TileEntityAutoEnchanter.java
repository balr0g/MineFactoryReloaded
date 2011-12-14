package net.minecraft.src.powercrystals.minefactoryreloaded;

import java.util.List;
import java.util.Random;

import net.minecraft.src.EnchantmentData;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class TileEntityAutoEnchanter extends TileEntityFactory implements IInventory
{
	private ItemStack[] inventory;
	private static int booksPerLevel = 1;
	private int level = 0;
	private int books = 0;
	private boolean lastRedstonePowerState;
	private Random enchantingRandom;
	
	public TileEntityAutoEnchanter()
	{
		inventory = new ItemStack[getSizeInventory()];
		enchantingRandom = new Random();
	}
	
	public void neighborBlockChanged()
	{
		boolean isPowered = Util.isRedstonePowered(this);
		if(isPowered && !lastRedstonePowerState)
		{
			lastRedstonePowerState = isPowered;
			doWork();
		}
		else
		{
			lastRedstonePowerState = isPowered;
		}
	}
	
	private void doWork()
	{
		float dropOffsetX = 0.0F;
		float dropOffsetZ = 0.0F;
		
		if(getDirectionFacing() == Orientations.XPos)
		{
			dropOffsetX = -0.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZPos)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = -0.5F;
		}
		else if(getDirectionFacing() == Orientations.XNeg)
		{
			dropOffsetX = 1.5F;
			dropOffsetZ = 0.5F;
		}
		else if(getDirectionFacing() == Orientations.ZNeg)
		{
			dropOffsetX = 0.5F;
			dropOffsetZ = 1.5F;
		}
		
		System.out.println("Starting enchanting; we are level " + level);
		for(int i = 0; i < getSizeInventory(); i++)
		{
			ItemStack stack = getStackInSlot(i);
			if(stack == null)
			{
				continue;
			}
			System.out.println("Found a " + stack.itemID + " in slot " + i);
			int enchantability = MineFactoryReloadedCore.proxy.calcItemStackEnchantability(enchantingRandom, -1, level, stack);
			System.out.println("Enchantability: " + enchantability);
			List<?> enchantmentDataList = MineFactoryReloadedCore.proxy.buildEnchantmentList(enchantingRandom, stack, enchantability);
			if(enchantmentDataList == null) System.out.println("No valid enchantments");
			else System.out.println(enchantmentDataList.size() + " valid enchantments");
			if(enchantmentDataList != null)
			{
				EnchantmentData ed = (EnchantmentData)enchantmentDataList.get(0);
				level -= MineFactoryReloadedCore.proxy.getLevel(ed);
				System.out.println("Enchantment: Level " + MineFactoryReloadedCore.proxy.getLevel(ed) + " " + MineFactoryReloadedCore.proxy.getEnchantment(ed).effectId);
				MineFactoryReloadedCore.proxy.applyEnchantment(ed, stack);
				dropStack(stack, dropOffsetX, 0, dropOffsetZ);
				inventory[i] = null;
				return;
			}
		}
	}
	
	@Override
	public String getInvName()
	{
		return "Auto Enchanter";
	}

	@Override
	public int getSizeInventory()
	{
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return inventory[i];
	}
	
	@Override
	public void openChest()
	{
	}
	
	@Override
	public void closeChest()
	{
	}

	@Override
    public ItemStack decrStackSize(int i, int j)
    {
        if(inventory[i] != null)
        {
            if(inventory[i].stackSize <= j)
            {
                ItemStack itemstack = inventory[i];
                inventory[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = inventory[i].splitStack(j);
            if(inventory[i].stackSize == 0)
            {
                inventory[i] = null;
            }
            return itemstack1;
        }
        else
        {
            return null;
        }
    }

	@Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
		if(itemstack != null && itemstack.itemID == Item.book.shiftedIndex)
		{
			System.out.println("Found a book");
			int booksToAdd = itemstack.stackSize; 
			while(books + booksToAdd >= booksPerLevel)
			{
				booksToAdd -= (booksPerLevel - books);
				books = 0;
				level++;
			}
			books += booksToAdd;
			System.out.println("Now level " + level);
		}
		else
		{
	        inventory[i] = itemstack;
	        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
	        {
	            itemstack.stackSize = getInventoryStackLimit();
	        }
		}
    }

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}
	
	@Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        inventory = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j >= 0 && j < inventory.length)
            {
            	ItemStack s = new ItemStack(0, 0, 0);
            	s.readFromNBT(nbttagcompound1);
                inventory[j] = s;
            }
        }
    }

	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < inventory.length; i++)
        {
            if(inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.setTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }
}
