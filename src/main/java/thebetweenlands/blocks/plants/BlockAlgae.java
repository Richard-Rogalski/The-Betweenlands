package thebetweenlands.blocks.plants;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.entities.rowboat.EntityWeedwoodRowboat;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.herblore.ItemGenericPlantDrop;
import thebetweenlands.items.herblore.ItemGenericPlantDrop.EnumItemPlantDrop;
import thebetweenlands.items.tools.ISickleHarvestable;
import thebetweenlands.items.tools.ISyrmoriteShearable;

public class BlockAlgae extends BlockBush implements ISickleHarvestable, ISyrmoriteShearable {
	public BlockAlgae() {
		setHardness(0.1F);
		setStepSound(soundTypeGrass);
		setCreativeTab(BLCreativeTabs.plants);
		setBlockName("thebetweenlands.algae");
		setBlockTextureName("thebetweenlands:algae");
		setBlockBounds(0, 0.0F, 0, 1.0F, 0.125F, 1.0F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityWeedwoodRowboat == false) {
			entity.motionX *= 0.8D;
			entity.motionZ *= 0.8D;
		} else {
			entity.motionX *= 0.995D;
			entity.motionZ *= 0.995D;
		}
	}

	@Override
	protected boolean canPlaceBlockOn(Block block) {
		return block == BLBlockRegistry.swampWater;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return y >= 0 && y < 256 ? world.getBlock(x, y - 1, z) == BLBlockRegistry.swampWater && world.getBlockMetadata(x, y - 1, z) == 0 : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if(blockAccess.getBlock(x, y, z) == this) {
			return false;
		}
		return super.shouldSideBeRendered(blockAccess, x, y, z, side);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		return drops;
	}

	@Override
	public boolean isHarvestable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return item.getItem() == BLItemRegistry.sickle;
	}

	@Override
	public ArrayList<ItemStack> getHarvestableDrops(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> dropList = new ArrayList<ItemStack>();
		dropList.add(ItemGenericPlantDrop.createStack(EnumItemPlantDrop.ALGAE));
		return dropList;
	}

	@Override
	public ItemStack getSyrmoriteShearableSpecialDrops(Block block, int x, int y, int z, int meta) {
		return null;
	}

	@Override
	public boolean isSyrmoriteShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}
}