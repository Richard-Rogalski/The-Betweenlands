package thebetweenlands.blocks;

import java.util.Random;

import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.tileentities.spawner.MobSpawnerBaseLogicBL;
import thebetweenlands.tileentities.spawner.TileEntityBLSpawner;

public class BlockBLSpawner extends BlockMobSpawner {

	public static String[] entityList = new String[]{
			"thebetweenlands.swampHag",
	"thebetweenlands.wight"};

	public BlockBLSpawner() {
		super();
		setLightLevel(0.9375F);
		setHardness(10.0F);
		setHarvestLevel("pickaxe", 0);
		setBlockName("thebetweenlands.blSpawner");
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockTextureName("thebetweenlands:betweenstone");
	}

	public static void setMob(World world, int x, int y, int z, String mobName) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityBLSpawner) {
			MobSpawnerBaseLogicBL spawnerLogic = ((TileEntityBLSpawner) te).getSpawnerLogic();
			String prevMob = spawnerLogic.getEntityNameToSpawn();
			spawnerLogic.setEntityName(mobName);
			//resets the rendered entity
			if (world.isRemote && prevMob != mobName) {
				NBTTagCompound nbt = new NBTTagCompound();
				spawnerLogic.writeToNBT(nbt);
				spawnerLogic.readFromNBT(nbt);
			}
		}
	}

	public static void setRandomMob(World world, int x, int y, int z, Random random) {
		setMob(world, x, y, z, entityList[random.nextInt(entityList.length)]);
	}

	public static MobSpawnerBaseLogicBL getLogic(World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityBLSpawner)
			return ((TileEntityBLSpawner) te).getSpawnerLogic();
		return null;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, entity, itemStack);
		Random random = new Random();
		setRandomMob(world, x, y, z, random);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBLSpawner();
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
	public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {
	}
}
