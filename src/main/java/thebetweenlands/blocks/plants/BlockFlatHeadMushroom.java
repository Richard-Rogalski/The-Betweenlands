package thebetweenlands.blocks.plants;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.misc.ItemGeneric;
import thebetweenlands.items.misc.ItemGeneric.EnumItemGeneric;
import thebetweenlands.proxy.ClientProxy.BlockRenderIDs;

import java.util.ArrayList;
import java.util.Random;

public class BlockFlatHeadMushroom extends BlockBLSmallPlants {
	public IIcon modelTexture1, modelTexture2;
	
	public BlockFlatHeadMushroom() {
		super("flatHeadMushroom");
	}

	@Override
	public int getRenderType() {
		return BlockRenderIDs.MODEL_PLANT.id();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		modelTexture1 = reg.registerIcon("thebetweenlands:flatHeadMushroom");
		modelTexture2 = reg.registerIcon("thebetweenlands:flatHeadMushroom2");
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(BLItemRegistry.flatheadMushroomItem, 1 + fortune));
		return drops;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return modelTexture1;
	}
	
	@Override
	public boolean isHarvestable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
	@Override	
	protected boolean canPlaceBlockOn(Block block) {
		return super.canPlaceBlockOn(block) || block.isNormalCube();
	}
}
