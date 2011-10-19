package net.minecraft.src;

import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IMFRProxy;

public class mod_MineFactory extends BaseModMp
{	
	private static mod_MineFactory instance;
	
	public mod_MineFactory()
	{
		MineFactoryReloadedCore.Init(new ServerProxy());
		ModLoaderMp.InitModLoaderMp();
		instance = this;
	}
	
	public String Version()
	{
		return "1.8.1R1.2.2";
	}
	
	public static void registerPlantable(IFactoryPlantable plantable)
	{
		MineFactoryReloadedCore.registerPlantable(plantable);
	}
	
	public static void registerHarvestable(IFactoryHarvestable harvestable)
	{
		MineFactoryReloadedCore.registerHarvestable(harvestable);
	}
	
	public static void registerFertilizable(IFactoryFertilizable fertilizable)
	{
		MineFactoryReloadedCore.registerFertilizable(fertilizable);
	}
	
	public static void registerFertilizerItem(int itemId)
	{
		MineFactoryReloadedCore.registerFertilizerItem(itemId);
	}
	
	public static void registerRanchable(IFactoryRanchable ranchable)
	{
		MineFactoryReloadedCore.registerRanchable(ranchable);
	}
	
	public class ServerProxy implements IMFRProxy
	{
		@Override
		public boolean isClient(World world)
		{
			return world.singleplayerWorld;
		}

		@Override
		public boolean isServer()
		{
			return true;
		}

		@Override
		public void movePlayerToCoordinates(EntityPlayer e, double x, double y,	double z)
		{
			if(!(e instanceof EntityPlayerMP))
			{
				return;
			}
			((EntityPlayerMP)e).playerNetServerHandler.teleportTo(x, y, z, e.rotationYaw, e.rotationPitch);
		}

		@Override
		public int getBlockDamageDropped(Block block, int metadata)
		{
			return block.damageDropped(metadata);
		}

		@Override
		public int getRenderId()
		{
			return 0;
		}

		@Override
		public boolean fertilizeGiantMushroom(World world, int x, int y, int z)
		{
			int blockId = world.getBlockId(x, y, z);
			return ((BlockMushroom)Block.blocksList[blockId]).fertilizeMushrom(world, x, y, z, world.rand);
		}

		@Override
		public void fertilizeStemPlant(World world, int x, int y, int z)
		{
			int blockId = world.getBlockId(x, y, z);
			((BlockStem)Block.blocksList[blockId]).func_35066_f_(world, x, y, z);
		}

		@Override
		public String getConfigPath()
		{
			return "config/MineFactoryReloaded.cfg";
		}
		@Override
		public Packet getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString)
		{
			return ModLoaderMp.GetTileEntityPacket(instance, te.xCoord, te.yCoord, te.zCoord, 0, dataInt, dataFloat, dataString);
		}
	}
}
