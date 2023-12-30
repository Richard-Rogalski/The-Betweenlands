package thebetweenlands.blocks.container;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.tileentities.TileEntityTarLootPot1;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTarLootPot1 extends BlockContainer {

	public BlockTarLootPot1() {
		super(Material.rock);
		setHardness(15.0F);
		setResistance(20.0F);
		setStepSound(Block.soundTypeStone);
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockName("thebetweenlands.tarLootPot1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return BLBlockRegistry.solidTar.getIcon(side, 1);
	}

	@Override
	public int getRenderType() {
		return -1;
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
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityTarLootPot1();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack is) {
		byte rotationMeta = 0;
		int rotation = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if (rotation == 0)
			rotationMeta = 2;
		if (rotation == 1)
			rotationMeta = 5;
		if (rotation == 2)
			rotationMeta = 3;
		if (rotation == 3)
			rotationMeta = 4;
		world.setBlockMetadataWithNotify(x, y, z, rotationMeta, 3);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(x, y, z) instanceof TileEntityTarLootPot1) {
			TileEntityTarLootPot1 tile = (TileEntityTarLootPot1) world.getTileEntity(x, y, z);
			if (player.getCurrentEquippedItem() != null) {
				int meta = world.getBlockMetadata(x, y, z);
				ItemStack item = player.getCurrentEquippedItem();
				for (int i = 0; i < 3; i++) {
					if (tile.getStackInSlot(i) == null) {
						tile.setInventorySlotContents(i, new ItemStack(item.getItem(), 1, item.getItemDamage()));
						if (!player.capabilities.isCreativeMode)
							player.getCurrentEquippedItem().stackSize--;
						world.markBlockForUpdate(x, y, z);
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		IInventory tile = (IInventory) world.getTileEntity(x, y, z);
		if (tile != null)
			for (int i = 0; i < tile.getSizeInventory(); i++) {
				ItemStack stack = tile.getStackInSlot(i);
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
}