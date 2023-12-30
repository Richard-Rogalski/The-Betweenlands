package thebetweenlands.blocks.container;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thebetweenlands.client.particle.BLParticle;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.herblore.aspects.AspectManager;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.tileentities.TileEntityInfuser;

public class BlockInfuser extends BlockContainer {

	public BlockInfuser() {
		super(Material.iron);
		setHardness(2.0F);
		setResistance(5.0F);
		setBlockName("thebetweenlands.infuser");
		setCreativeTab(BLCreativeTabs.herbLore);
		setBlockTextureName("thebetweenlands:octineBlock");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is) {
		int rot = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, rot == 0 ? 2 : rot == 1 ? 5 : rot == 2 ? 3 : 4, 3);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityInfuser) {
			TileEntityInfuser tile = (TileEntityInfuser) world.getTileEntity(x, y, z);

			if (!player.isSneaking()) {
				if (tile != null && player.getCurrentEquippedItem() == null && tile.getStirProgress() >= 90) {
					tile.setStirProgress(0);
					return true;
				}
				if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == BLItemRegistry.weedwoodBucketWater && !tile.hasInfusion()) {
					ItemStack oldItem = player.getCurrentEquippedItem();
					ItemStack newItem = tile.fillTankWithBucket(player.inventory.getStackInSlot(player.inventory.currentItem));
					world.markBlockForUpdate(x, y, z);
					if (!player.capabilities.isCreativeMode) player.inventory.setInventorySlotContents(player.inventory.currentItem, newItem);
					if (!ItemStack.areItemStacksEqual(oldItem, newItem)) {
						return true;
					}
				}
				if (player.getCurrentEquippedItem() != null && AspectManager.get(world).getDiscoveredAspects(player.getCurrentEquippedItem(), null).size() > 0 && !tile.hasInfusion()) {
					ItemStack ingredient = player.getCurrentEquippedItem();
					for (int i = 0; i < TileEntityInfuser.MAX_INGREDIENTS; i++) {
						if(tile.getStackInSlot(i) == null) {
							ItemStack singleIngredient = ingredient.copy();
							singleIngredient.stackSize = 1;
							tile.setInventorySlotContents(i, singleIngredient);
							tile.updateInfusingRecipe();
							if (!player.capabilities.isCreativeMode) player.getCurrentEquippedItem().stackSize--;
							world.markBlockForUpdate(x, y, z);
							return true;
						}
					}
				}
				if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == BLItemRegistry.lifeCrystal) {
					if(tile.getStackInSlot(TileEntityInfuser.MAX_INGREDIENTS + 1) == null) {
						tile.setInventorySlotContents(TileEntityInfuser.MAX_INGREDIENTS + 1, player.getCurrentEquippedItem());
						tile.updateInfusingRecipe();
						if (!player.capabilities.isCreativeMode) player.setCurrentItemOrArmor(0, null);
					}
					return true;
				}
			}

			if(player.isSneaking() && !tile.hasInfusion()) {
				for (int i = TileEntityInfuser.MAX_INGREDIENTS; i >= 0; i--) {
					if(tile.getStackInSlot(i) != null) {
						EntityItem itemEntity = player.dropPlayerItemWithRandomChoice(tile.getStackInSlot(i).copy(), false);
						if(itemEntity != null) itemEntity.delayBeforeCanPickup = 0;
						tile.setInventorySlotContents(i, null);
						tile.updateInfusingRecipe();
						world.markBlockForUpdate(x, y, z);
						return true;
					}
				}
			}

			if(player.isSneaking()) {
				if(tile.getStackInSlot(TileEntityInfuser.MAX_INGREDIENTS + 1) != null) {
					EntityItem itemEntity = player.dropPlayerItemWithRandomChoice(tile.getStackInSlot(TileEntityInfuser.MAX_INGREDIENTS + 1).copy(), false);
					if(itemEntity != null) itemEntity.delayBeforeCanPickup = 0;
					tile.setInventorySlotContents(TileEntityInfuser.MAX_INGREDIENTS + 1, null);
					tile.updateInfusingRecipe();
					world.markBlockForUpdate(x, y, z);
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(world.isRemote)
			return;
		IInventory tileInventory = (IInventory) world.getTileEntity(x, y, z);
		TileEntityInfuser tile = (TileEntityInfuser) world.getTileEntity(x, y, z);
		if (tileInventory != null && !tile.hasInfusion()) {
			for (int i = 0; i <= TileEntityInfuser.MAX_INGREDIENTS + 1; i++) {
				ItemStack stack = tileInventory.getStackInSlot(i);
				if (stack != null) {
					if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
						float f = 0.7F;
						double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
						double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
						double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
						EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
						entityitem.delayBeforeCanPickup = 10;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
		} else if (tileInventory != null && tile.hasInfusion()) {
			ItemStack stack = tileInventory.getStackInSlot(TileEntityInfuser.MAX_INGREDIENTS + 1);
			if (stack != null) {
				if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
					float f = 0.7F;
					double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (world.getTileEntity(x, y, z) instanceof TileEntityInfuser) {
			TileEntityInfuser infuser = (TileEntityInfuser) world.getTileEntity(x, y, z);
			if (infuser.getWaterAmount() > 0  && infuser.getTemperature() > 0) {
				int amount = infuser.waterTank.getFluidAmount();
				int capacity = infuser.waterTank.getCapacity();
				float size = 1F / capacity * amount;
				float xx = (float) x + 0.5F;
				float yy = (float) (y + 0.35F + size * 0.5F);
				float zz = (float) z + 0.5F;
				float fixedOffset = 0.25F;
				float randomOffset = rand.nextFloat() * 0.6F - 0.3F;
				if(rand.nextInt((101 - infuser.getTemperature()))/4 == 0) {
					if(!infuser.hasInfusion())
						BLParticle.BUBBLE_INFUSION.spawn(world, xx, yy, zz, 0.1D, 0.0D, 0.1D, 0);
					else
						BLParticle.BUBBLE_INFUSION.spawn(world, xx, yy, zz, 0.1D, 0.0D, 0.1D, 0);
					if (rand.nextInt(10) == 0 && infuser.getTemperature() > 70)
						world.playSound(xx, yy, zz, "liquid.lava", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.5F, false);
				}
				if (infuser.getTemperature() >= 100) {
					BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx - fixedOffset), (double) y + 0.75D, (double) (zz + randomOffset), 0.0D, 0.0D, 0.0D, 0);
					BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx + fixedOffset), (double) y + 0.75D, (double) (zz + randomOffset), 0.0D, 0.0D, 0.0D, 0);
					BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx + randomOffset), (double) y + 0.75D, (double) (zz - fixedOffset), 0.0D, 0.0D, 0.0D, 0);
					BLParticle.STEAM_PURIFIER.spawn(world, (double) (xx + randomOffset), (double) y + 0.75D, (double) (zz + fixedOffset), 0.0D, 0.0D, 0.0D, 0);
				}
			}
		}
	}

	@Override
	public int getRenderType() {
		return - 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityInfuser();
	}
}