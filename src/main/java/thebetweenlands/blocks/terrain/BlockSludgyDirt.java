package thebetweenlands.blocks.terrain;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.creativetabs.BLCreativeTabs;
import thebetweenlands.entities.mobs.IEntityBL;
import thebetweenlands.world.events.impl.EventSpoopy;

public class BlockSludgyDirt
        extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon topIcon, sideIcon, spoopyTopIcon, spoopySideIcon;

	public BlockSludgyDirt() {
		super(Material.grass);
		setHardness(0.5F);
		setStepSound(soundTypeGravel);
		setHarvestLevel("shovel", 0);
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockName("thebetweenlands.sludgyDirt");
		setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(EventSpoopy.isSpoopy(Minecraft.getMinecraft().theWorld)) {
			if( side == 2 || side == 3 || side == 4 || side == 5 ) {
				return this.spoopySideIcon;
			} else if( side == 1 ) {
				return this.spoopyTopIcon;
			}
		} else {
			if( side == 2 || side == 3 || side == 4 || side == 5 ) {
				return this.sideIcon;
			} else if( side == 1 ) {
				return this.topIcon;
			}
		}

		return this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon("thebetweenlands:swampDirt");
		this.sideIcon = reg.registerIcon("thebetweenlands:sludgyDirtSide");
		this.topIcon = reg.registerIcon("thebetweenlands:sludge");
		this.spoopySideIcon = reg.registerIcon("thebetweenlands:sludgyDirtSideSpoopy");
		this.spoopyTopIcon = reg.registerIcon("thebetweenlands:sludgeSpoopy");
	}

	@Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return BLBlockRegistry.swampDirt.getItemDropped(0, rand, fortune);
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)((float)(y + 1) - f), (double)(z + 1));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof IEntityBL == false) entity.setInWeb();
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
    public boolean shouldSideBeRendered (IBlockAccess iblockaccess, int x, int y, int z, int side) {
        Block block = iblockaccess.getBlock(x, y, z);
        return block != BLBlockRegistry.sludgyDirt;
    }
}
