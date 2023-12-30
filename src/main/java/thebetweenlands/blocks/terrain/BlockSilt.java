package thebetweenlands.blocks.terrain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.entities.mobs.EntitySiltCrab;
import thebetweenlands.items.armor.ItemRubberBoots;

public class BlockSilt
        extends Block
{
	public BlockSilt() {
		super(Material.sand);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setHarvestLevel("shovel", 0);
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockName("thebetweenlands.silt");
		setBlockTextureName("thebetweenlands:silt");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        float yOffset = 0.125F;
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, (y + 1) - yOffset, z + 1);
    }

	@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		boolean canWalk = entity instanceof EntityPlayer && ((EntityPlayer)entity).inventory.armorInventory[0] != null && 
				((EntityPlayer)entity).inventory.armorInventory[0].getItem() instanceof ItemRubberBoots;
		if(!(entity instanceof EntitySiltCrab) && !canWalk) {
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
    }
}
