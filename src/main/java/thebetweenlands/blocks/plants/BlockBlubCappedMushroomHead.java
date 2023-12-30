package thebetweenlands.blocks.plants;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.client.particle.BLParticle;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.items.BLItemRegistry;

public class BlockBlubCappedMushroomHead extends Block {
	public BlockBlubCappedMushroomHead() {
		super(Material.wood);
		setStepSound(Block.soundTypeCloth);
		setHardness(0.2F);
		setBlockName("thebetweenlands.hugeMushroomTop");
		setBlockTextureName("thebetweenlands:bulbCappedShroomCap");
		setCreativeTab(BLCreativeTabs.plants);
		setLightLevel(1.0F);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side) {
		Block block = iblockaccess.getBlock(x, y, z);
		return block != BLBlockRegistry.bulbCappedMushroomTop;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(BLBlockRegistry.bulbCappedMushroom);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {
		if (!world.isRemote) {
			int dropChance = 6;

			if (fortune > 0){
				dropChance -= 2*fortune;
			}
			if(world.rand.nextInt(dropChance) <= 0) {
				this.dropBlockAsItem(world, x, y, z, new ItemStack(BLItemRegistry.bulbCappedMushroomItem, 1));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if(world.rand.nextInt(150) == 0) {
			int particle = rand.nextInt(3);
			if(particle == 0) {
				BLParticle.MOSQUITO.spawn(world, x, y, z);
			} else if(particle == 1) {
				BLParticle.FLY.spawn(world, x, y, z);
			} else {
				BLParticle.MOTH.spawn(world, x, y, z);
			}
		}
	}
}
