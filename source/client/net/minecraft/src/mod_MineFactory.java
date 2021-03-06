package net.minecraft.src;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

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
	
	public void load()
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
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.Weather)), "Weather Collector");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.AutoEnchanter)), "Auto-Enchanter");
		ModLoader.AddName(new ItemStack(MineFactoryReloadedCore.machineBlock, 1, MineFactoryReloadedCore.machineMetadataMappings.get(Machine.AutoBreeder)), "Auto-Breeder");
		
		ModLoader.AddName(MineFactoryReloadedCore.conveyorBlock, "Conveyor Belt");
		
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
	public String getVersion()
	{
		return MineFactoryReloadedCore.version;
	}
	
	/** Force MFR to load after all other mods (since it uses BC API)
	  */
	@Override
	public String getPriorities() {
		return "after:*";
	}

	@Override
	public void RegisterAnimation(Minecraft minecraft)
	{
		if(Util.getBool(MineFactoryReloadedCore.animateBlockFaces))
		{
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.conveyorTexture, "/MineFactorySprites/animations/Conveyor.png"));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.harvesterAnimatedTexture, "/MineFactorySprites/animations/Harvester.png"));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.rancherAnimatedTexture, "/MineFactorySprites/animations/Rancher.png"));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.blockBreakerAnimatedTexture, "/MineFactorySprites/animations/BlockBreaker.png"));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.fertilizerAnimatedTexture, "/MineFactorySprites/animations/Fertilizer.png"));
			ModLoader.addAnimation(new TextureFrameAnimFX(MineFactoryReloadedCore.vetAnimatedTexture, "/MineFactorySprites/animations/Vet.png"));
			ModLoader.addAnimation(new TextureLiquidFX(MineFactoryReloadedCore.milkTexture, MineFactoryReloadedCore.itemTexture, 240, 255, 240, 255, 230, 245));
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
			e.setPosition(x, y + 2.5, z);
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
			return ((BlockMushroom)Block.blocksList[blockId]).fertilizeMushroom(world, x, y, z, world.rand);
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

		@Override
		public void sendPacketToAll(Packet230ModLoader p)
		{	
		}

		@Override
		public int calcItemStackEnchantability(Random random, int i, int j, ItemStack itemstack)
		{
			return EnchantmentHelper.calcItemStackEnchantability(random, i, j, itemstack);
		}

		@Override
		public List<?> buildEnchantmentList(Random random, ItemStack itemstack,	int i)
		{
			return EnchantmentHelper.buildEnchantmentList(random, itemstack, i);
		}

		@Override
		public Enchantment getEnchantment(EnchantmentData ed)
		{
			return ed.field_40264_a;
		}

		@Override
		public int getLevel(EnchantmentData ed)
		{
			return ed.field_40263_b;
		}

		@Override
		public void applyEnchantment(EnchantmentData ed, ItemStack stack)
		{
			stack.addEnchantment(getEnchantment(ed), getLevel(ed));
		}

		@Override
		public void setInLove(EntityAnimal animal, int value)
		{
			try
			{
				Field f = Class.forName("fx").getDeclaredFields()[0];
				f.setAccessible(true);
				f.set(animal, value);
			}
			catch(SecurityException e)
			{
				e.printStackTrace();
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}	
		}

		@Override
		public void setEntityToAttack(EntityCreature entity, Entity target)
		{
			entity.setEntityToAttack(target);
		}
	}
}
