package thebetweenlands.blocks.terrain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.items.tools.ItemSpadeBL;

public class BlockSwampDirt
extends Block
{
	public BlockSwampDirt() {
		super(Material.ground);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setHarvestLevel("shovel", 0);
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockName("thebetweenlands.swampDirt");
		setBlockTextureName("thebetweenlands:swampDirt");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (player.getCurrentEquippedItem() !=null && player.getCurrentEquippedItem().getItem() instanceof ItemSpadeBL) {
			if(!world.isRemote) {
				world.setBlock(x, y, z, BLBlockRegistry.farmedDirt, 1, 3);
				world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), stepSound.getStepResourcePath(), (stepSound.getVolume() + 1.0F) / 2.0F, stepSound.getPitch() * 0.8F);
				world.playAuxSFXAtEntity(null, 2001, x, y + 1, z, Block.getIdFromBlock(world.getBlock(x, y, z)));
				player.getCurrentEquippedItem().damageItem(1, player);
			}
			return true;
		}
		return false;
	}
}
