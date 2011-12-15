package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.buildcraft.api.ILiquidContainer;
import net.minecraft.src.buildcraft.api.IPowerReceptor;
import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.buildcraft.api.PowerFramework;
import net.minecraft.src.buildcraft.api.PowerProvider;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.PowerSystem;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IMachine;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;

public class TileEntityFactoryMachine extends TileEntity implements IPowerReceptor, ILiquidContainer, IInventory
{
	private IMachine machine;
	
	private boolean lastRedstonePowerState = false;
	private boolean redstonePowerAvailable = false;
	
	private PowerProvider powerProvider;
	private int powerNeeded;
	
	private ItemStack[] inventory;
	
	public TileEntityFactoryMachine()
	{
		inventory = new ItemStack[getSizeInventory()];
	}
	
	public void neighboorBlockChanged()
	{
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.Redstone)
		{
			boolean isPowered = Util.isRedstonePowered(this);
			if(isPowered && !lastRedstonePowerState)
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
		
		if(machine != null)
		{
			machine.neighborBlockChanged(this);
		}
	}
	
	public void setMachine(IMachine machine)
	{
		setMachine(machine, null);
	}
	
	private void setMachine(IMachine machine, PowerProvider powerProvider)
	{
		this.machine = machine;
		if(this.machine.getRequiresPower())
		{
			if(powerProvider == null)
			{
				this.machine.setupBCPower(PowerFramework.currentFramework.createPowerProvider());
			}
			else
			{
				this.powerProvider = powerProvider;
			}
			this.powerNeeded = this.machine.getBCPowerNeeded();
		}
	}
	
	public IMachine getMachine()
	{
		return machine;
	}
	
	private boolean powerAvailable()
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

	// **********************************
	// Other base methods
	// **********************************

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(MineFactoryReloadedCore.powerSystem == PowerSystem.BuildCraft)
		{
			getPowerProvider().update(this);
		}
	}
	
	// **********************************
	// IInventory methods
	// **********************************
	
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
	}

	@Override
	public String getInvName()
	{
		return this.machine.getInventoryName();
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
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}
	
	// **********************************
	// ILiquidSource methods
	// **********************************

	@Override
	public int fill(Orientations from, int quantity, int id, boolean doFill)
	{
		return 0;
	}

	@Override
	public int empty(int quantityMax, boolean doEmpty)
	{
		return 0;
	}

	@Override
	public int getLiquidQuantity()
	{
		return 0;
	}

	@Override
	public int getCapacity()
	{
		return 0;
	}

	@Override
	public int getLiquidId()
	{
		return 0;
	}
	
	// **********************************
	// IPowerReceptor methods
	// **********************************

	@Override
	public void setPowerProvider(PowerProvider provider)
	{
		this.powerProvider = provider;
	}

	@Override
	public PowerProvider getPowerProvider()
	{
		return powerProvider;
	}

	@Override
	public void doWork()
	{
		if(powerAvailable() && machine != null)
		{
			machine.doWork();
		}
	}

	@Override
	public int powerRequest()
	{
		return 0;
	}
}
