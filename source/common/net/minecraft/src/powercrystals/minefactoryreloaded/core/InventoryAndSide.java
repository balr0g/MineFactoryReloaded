package net.minecraft.src.powercrystals.minefactoryreloaded.core;

import net.minecraft.src.IInventory;

public class InventoryAndSide
{
	private IInventory inventory;
	int side;
	
	public InventoryAndSide(IInventory inventory, int side)
	{
		this.inventory = inventory;
		this.side = side;
	}
	
	public IInventory getInventory()
	{
		return inventory;
	}
	
	public int getSide()
	{
		return side;
	}
}
