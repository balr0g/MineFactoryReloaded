package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.buildcraft.api.APIProxy;
import net.minecraft.src.forge.MinecraftForgeClient;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import net.minecraft.src.powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.IMFRProxy;
import net.minecraft.src.powercrystals.minefactoryreloaded.core.Util;
import net.minecraft.src.powercrystals.minefactoryreloaded.FactoryRenderer;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import net.minecraft.src.powercrystals.minefactoryreloaded.MineFactoryReloadedCore.Machine;
import net.minecraft.src.powercrystals.minefactoryreloaded.TextureFrameAnimFX;
import net.minecraft.src.powercrystals.minefactoryreloaded.TextureLiquidFX;
import net.minecraft.src.powercrystals.minefactoryreloaded.TileEntityFactory;

public class mod_MineFactory extends BaseModMp
{
	public static int renderId = 1000;
	
	public mod_MineFactory()
	{
		MineFactoryReloadedCore.Init(new ClientProxy());
		
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Planter)), "Planter");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fisher)), "Fisher");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Harvester)), "Harvester");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Rancher)), "Rancher");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Fertilizer)), "Fertilizer");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Vet)), "Veterinary Station");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Collector)), "Item Collector");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Breaker)), "Block Breaker");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Weather)), "Weather Harvester");
		
		ModLoader.AddName(MineFactoryReloadedCore.passengerRailPickupBlock, "Passenger Pickup Rail");
		ModLoader.AddName(MineFactoryReloadedCore.passengerRailDropoffBlock, "Passenger Dropoff Rail");
		ModLoader.AddName(MineFactoryReloadedCore.cargoRailDropoffBlock, "Cargo Dropoff Rail");
		ModLoader.AddName(MineFactoryReloadedCore.cargoRailPickupBlock, "Cargo Pickup Rail");
		
		ModLoader.AddName(MineFactoryReloadedCore.steelIngotItem, "Steel Ingot");
		ModLoader.AddName(MineFactoryReloadedCore.factoryHammerItem, "Factory Hammer");
		
		renderId = ModLoader.getUniqueBlockModelID(this, false);
	}
	
	@Override
	public void ModsLoaded()
	{
		ModLoaderMp.Init();
		MinecraftForgeClient.preloadTexture(MineFactoryReloadedCore.terrainTexture);
		MinecraftForgeClient.preloadTexture(MineFactoryReloadedCore.itemTexture);
		MineFactoryReloadedCore.afterModsLoaded();
	}

	@Override
	public String Version()
	{
		return "1.8.1R1.3.0B";
	}

	@Override
	public void RegisterAnimation(Minecraft minecraft)
	{
		if(Util.getBool(MineFactoryReloadedCore.animateBlockFaces))
		{
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.conveyorTexture, "/MineFactorySprites/animations/Conveyor.png", Util.getInt(MineFactoryReloadedCore.animationTileSize)));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.harvesterAnimatedTexture, "/MineFactorySprites/animations/Harvester.png", Util.getInt(MineFactoryReloadedCore.animationTileSize)));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.rancherAnimatedTexture, "/MineFactorySprites/animations/Rancher.png", Util.getInt(MineFactoryReloadedCore.animationTileSize)));
			ModLoader.addAnimation(new TextureLiquidFX(MineFactoryReloadedCore.milkTexture, MineFactoryReloadedCore.itemTexture, 240, 255, 240, 255, 230, 245, Util.getInt(MineFactoryReloadedCore.animationTileSize)));
		}
	}

	@Override
	public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int renderId)
	{
		if(renderId == mod_MineFactory.renderId)
		{
			FactoryRenderer.renderConveyorWorld(renderblocks, iblockaccess, i, j, k, block, renderId);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
    public void HandleTileEntityPacket(int i, int j, int k, int l, int ai[], float af[], String as[])
    {
		World w = APIProxy.getWorld();
		TileEntity te = w.getBlockTileEntity(i, j, k);
		if(te != null && te instanceof TileEntityFactory)
		{
			((TileEntityFactory)te).rotateDirectlyTo(ai[0]);
		}
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
	
	public class ClientProxy implements IMFRProxy
	{

		@Override
		public boolean isClient(World world)
		{
			return world.multiplayerWorld;
		}

		@Override
		public boolean isServer()
		{
			return false;
		}

		@Override
		public void movePlayerToCoordinates(EntityPlayer e, double x, double y,	double z)
		{
			e.setPosition(x, y, z);
		}

		@Override
		public int getBlockDamageDropped(Block block, int metadata)
		{
			return block.damageDropped(metadata);
		}

		@Override
		public int getRenderId()
		{
			return renderId;
		}

		@Override
		public boolean fertilizeGiantMushroom(World world, int x, int y, int z)
		{
			int blockId = world.getBlockId(x, y, z);
			return ((BlockMushroom)Block.blocksList[blockId]).func_35293_c(world, x, y, z, world.rand);
		}

		@Override
		public void fertilizeStemPlant(World world, int x, int y, int z)
		{
			int blockId = world.getBlockId(x, y, z);
			((BlockStem)Block.blocksList[blockId]).func_35294_i(world, x, y, z);
		}

		@Override
		public String getConfigPath()
		{
			return Minecraft.getMinecraftDir() + "/config/MineFactoryReloaded.cfg";
		}

		@Override
		public Packet getTileEntityPacket(TileEntity te, int[] dataInt, float[] dataFloat, String[] dataString)
		{
			return null;
		}
	}
}
