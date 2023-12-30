package thebetweenlands.blocks.terrain;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.entities.mobs.IEntityBL;
import thebetweenlands.herblore.elixirs.ElixirEffectRegistry;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.BLMaterial;
import thebetweenlands.items.armor.ItemRubberBoots;

public class BlockMud extends Block {
	public BlockMud() {
		super(BLMaterial.mud);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setHarvestLevel("shovel", 0);
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockName("thebetweenlands.mud");
		setBlockTextureName("thebetweenlands:mud");
	}

	public boolean canEntityWalkOnMud(Entity entity) {
		if(entity instanceof EntityLivingBase && ElixirEffectRegistry.EFFECT_HEAVYWEIGHT.isActive((EntityLivingBase)entity)) return false;
		boolean canWalk = entity instanceof EntityPlayer && ((EntityPlayer)entity).inventory.armorInventory[0] != null && ((EntityPlayer)entity).inventory.armorInventory[0].getItem() instanceof ItemRubberBoots;
		boolean hasLurkerArmor = entity instanceof EntityPlayer && entity.isInWater() && ((EntityPlayer)entity).inventory.armorInventory[0] != null && ((EntityPlayer)entity).inventory.armorInventory[0].getItem() == BLItemRegistry.lurkerSkinBoots;
		return entity instanceof IEntityBL || entity instanceof EntityItem || canWalk || hasLurkerArmor || (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode && ((EntityPlayer)entity).capabilities.isFlying);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List aabblist, Entity entity) {
		AxisAlignedBB blockAABB = this.getCollisionBoundingBoxFromPool(world, x, y, z);
		if (blockAABB != null && aabb.intersectsWith(blockAABB) && this.canEntityWalkOnMud(entity)) {
			if(entity instanceof IEntityBL || entity instanceof EntityItem) {
				aabblist.add(blockAABB);
			} else {
				if(world.isRemote) aabblist.add(AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+1-0.125, z+1));
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (!this.canEntityWalkOnMud(entity)) {
			entity.motionX *= 0.08D;
			if(!entity.isInWater() && entity.motionY < 0 && entity.onGround) entity.motionY = -0.1D;
			entity.motionZ *= 0.08D;
			if(!entity.isInWater()) {
				entity.setInWeb();
			} else {
				entity.motionY *= 0.02D;
			}
			entity.onGround = true;
			if(entity instanceof EntityLivingBase && entity.isInsideOfMaterial(BLMaterial.mud)) {
				((EntityLivingBase) entity).attackEntityFrom(DamageSource.inWall, 2.0F);
			}
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return true;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		Block block = blockAccess.getBlock(x, y, z);
		if(block == this) return false;
		return side == 0 && this.minY > 0.0D ? true : (side == 1 && this.maxY < 1.0D ? true : (side == 2 && this.minZ > 0.0D ? true : (side == 3 && this.maxZ < 1.0D ? true : (side == 4 && this.minX > 0.0D ? true : (side == 5 && this.maxX < 1.0D ? true : !blockAccess.getBlock(x, y, z).isOpaqueCube())))));
	}
}
