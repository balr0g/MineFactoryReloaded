package net.minecraft.src.powercrystals.minefactoryreloaded;

import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Area;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.BlockPosition;

public abstract class TileEntityFactoryRotateable extends TileEntityFactory
{
	private Orientations forwardDirection;
	
	public TileEntityFactoryRotateable(int bcEnergyNeededToWork, int bcEnergyNeededToActivate)
	{
		super(bcEnergyNeededToWork, bcEnergyNeededToActivate);
		forwardDirection = Orientations.XPos;
	}
	
	protected int getHarvestRadius()
	{
		return 1;
	}
	
	protected Area getHarvestArea()
	{
		BlockPosition ourpos = new BlockPosition(this);
		ourpos.moveForwards(getHarvestRadius() + 1);
		return new Area(ourpos, getHarvestRadius());
	}
	
	public Orientations getDirectionFacing()
	{
		return forwardDirection;
	}
	
	public void rotate()
	{
		if(forwardDirection == Orientations.XPos)
		{
			forwardDirection = Orientations.ZPos;
		}
		else if(forwardDirection == Orientations.ZPos)
		{
			forwardDirection = Orientations.XNeg;
		}
		else if(forwardDirection == Orientations.XNeg)
		{
			forwardDirection = Orientations.ZNeg;
		}
		else if(forwardDirection == Orientations.ZNeg)
		{
			forwardDirection = Orientations.XPos;
		}
		else
		{
			forwardDirection = Orientations.XPos;
		}
	}
	
	public int getRotatedSide(int side)
	{
		if(side < 2)
		{
			return side;
		}
		else if(forwardDirection == Orientations.ZPos)
		{
			return addToSide(side, 1);
		}
		else if(forwardDirection == Orientations.XNeg)
		{
			return addToSide(side, 2);
		}
		else if(forwardDirection == Orientations.ZNeg)
		{
			return addToSide(side, 3);
		}
		return side;
	}
	
	private int addToSide(int side, int shift)
	{
		// 0 bottom 1 top 2 east 3 west 4 north 5 south
		int shiftsRemaining = shift;
		int out = side;
		while(shiftsRemaining > 0)
		{
			if(out == 2) out = 5;
			else if(out == 3) out = 4;
			else if(out == 4) out = 2;
			else if(out == 5) out = 3;
			shiftsRemaining--;
		}
		return out;
	}
}
