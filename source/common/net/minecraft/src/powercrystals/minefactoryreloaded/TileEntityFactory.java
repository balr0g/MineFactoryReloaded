package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.buildcraft.api.IPowerReceptor;
import net.minecraft.src.buildcraft.api.PowerFramework;
import net.minecraft.src.buildcraft.api.PowerProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.PowerSystem;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.InventoryUtil;

public abstract class TileEntityFactory extends TileEntity implements IInventory, IPowerReceptor
{
	private boolean lastRedstonePowerState = false;
	private boolean redstonePowerAvailable = false;
	
	private PowerProvider powerProvider;
	private int powerNeeded;
	
	protected TileEntityFactory(int bcEnergyNeededToWork, int bcEnergyNeededToActivate)
	{
		powerProvider = PowerFramework.currentFramework.createPowerProvider();
		powerNeeded = bcEnergyNeededToWork;
		powerProvider.configure(25, powerNeeded, powerNeeded, bcEnergyNeededToActivate, powerNeeded);
		inventory = new ItemStack[getSizeInventory()];
	}
	
	protected void dropStack(World world, ItemStack s, float dropX, float dropY, float dropZ, int harvesterX, int harvesterY, int harvesterZ)
	{
		if(MineFactoryReloadedCore.HarvesterCanDropInChests)
		{
			for(IInventory chest : InventoryUtil.findChests(world, harvesterX, harvesterY, harvesterZ))
			{
				s.stackSize = InventoryUtil.addToInventory(chest, s);
				if(s.stackSize == 0)
				{
					return;
				}
			}
		}
		if(s.stackSize > 0)
		{
			EntityItem entityitem = new EntityItem(world, dropX, dropY, dropZ, s);
			entityitem.motionX = 0.0D;
			entityitem.motionY = 0.3D;
			entityitem.motionZ = 0.0D;
			world.entityJoinedWorld(entityitem);
		}
	}
	
	public void neighborBlockChanged()
	{
		boolean isPowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		if(isPowered && !lastRedstonePowerState && MineFactoryReloadedCore.powerSystem == MineFactoryReloadedCore.PowerSystem.Redstone)
		{
			lastRedstonePowerState = isPowered;
			redstonePowerAvailable = true;
			doWork();
			redstonePowerAvailable = false;
		}
		else
		{
			lastRedstonePowerState = isPowered;
		}
	}
	
	protected boolean powerAvailable()
	{
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.Redstone)
		{
			return redstonePowerAvailable;
		}
		else if(MineFactoryReloadedCore.powerSystem == PowerSystem.BuildCraft)
		{
			if(powerProvider.useEnergy(powerNeeded, powerNeeded, false) >= powerNeeded)
			{
				powerProvider.useEnergy(powerNeeded, powerNeeded, true);
				return true;
			}
			return false;
		}
		return false;
	}
	
	// base methods
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.BuildCraft)
		{
			getPowerProvider().update(this);
		}
	}
	
	// IPowerReceptor methods

	@Override
	public void setPowerProvider(PowerProvider provider)
	{
		powerProvider = provider;
	}

	@Override
	public PowerProvider getPowerProvider()
	{
		return powerProvider;
	}

	@Override
	public int powerRequest()
	{
		return powerNeeded;
	}
	
	// IInventory methods
	
	protected ItemStack[] inventory;
	
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
        inventory[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
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
        if(getSizeInventory() > 0)
        {
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

    }

	@Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        if(getSizeInventory() > 0)
        {
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
	
	public int findFirstStack(int itemId, int itemDamage)
	{
		for(int i = 0; i < getSizeInventory(); i++)
		{
			ItemStack s = getStackInSlot(i);
			if(s != null && s.itemID == itemId && s.getItemDamage() == itemDamage)
			{
				return i;
			}
		}
		return -1;
	}
}
