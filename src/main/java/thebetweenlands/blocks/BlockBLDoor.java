package thebetweenlands.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import thebetweenlands.manual.IManualEntryItem;
import thebetweenlands.proxy.ClientProxy.BlockRenderIDs;

import java.util.Random;

public class BlockBLDoor extends BlockDoor implements IManualEntryItem {
	public final String name;
	private Item item;
	private int renderPass = 0;

	public BlockBLDoor(String name, Material material) {
		super(material);
		disableStats();
		this.name = name;
		setHardness(3.0F);
		setStepSound(soundTypeWood);
		setBlockName("thebetweenlands.door" + name);
		setBlockTextureName("thebetweenlands:door_" + name);
	}

	public void setItem(Item item) {
		this.item = item;
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
    public int getRenderBlockPass ()
    {
        return 1;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return item;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return (meta & 8) != 0 ? null : item;
	}

	@Override
	public int getRenderType() {
		return BlockRenderIDs.DOOR.id();
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if (this.blockMaterial == Material.rock) {
			return false; //Allow items to interact with the door
		} else {
			int i1 = this.func_150012_g(world, x, y, z);
			int j1 = i1 & 7;
			j1 ^= 4;

			if ((i1 & 8) == 0) {
				world.setBlockMetadataWithNotify(x, y, z, j1, 2);
				world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
			} else {
				world.setBlockMetadataWithNotify(x, y - 1, z, j1, 2);
				world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y, z);
			}

			world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
			return true;
		}
	}

	@Override
	public String manualName(int meta) {
		return name + "Door";
	}

	@Override
	public Item getItem() {
		return item;
	}

	@Override
	public int[] recipeType(int meta) {
		return new int[]{2};
	}

	@Override
	public int metas() {
		return 0;
	}
}