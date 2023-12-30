package thebetweenlands.blocks.terrain;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.creativetabs.BLCreativeTabs;

import java.util.Random;

public class BlockSlimyGrass
        extends Block
{
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;

	public BlockSlimyGrass() {
		super(Material.grass);
		setHardness(0.5F);
		setStepSound(soundTypeGrass);
		setHarvestLevel("shovel", 0);
		setCreativeTab(BLCreativeTabs.blocks);
		setBlockName("thebetweenlands.slimyGrass");
		setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if( side == 2 || side == 3 || side == 4 || side == 5 ) {
			return this.sideIcon;
		} else if( side == 1 ) {
			return this.topIcon;
		}

        return this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon("thebetweenlands:slimyDirt");
        this.sideIcon = reg.registerIcon("thebetweenlands:slimyGrassSide");
        this.topIcon = reg.registerIcon("thebetweenlands:slimyGrassTop");
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
        if( !world.isRemote ) {
            if( world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2 ) {
                world.setBlock(x, y, z, BLBlockRegistry.slimyDirt);
            } else if( world.getBlockLightValue(x, y + 1, z) >= 9 ) {
                for( int l = 0; l < 4; ++l ) {
                    int xTarget = x + rand.nextInt(3) - 1;
                    int yTarget = y + rand.nextInt(5) - 3;
                    int zTarget = z + rand.nextInt(3) - 1;
                    Block block = world.getBlock(xTarget, yTarget + 1, zTarget);
                    
                    if( world.getBlock(xTarget, yTarget, zTarget) == Blocks.dirt
                        && world.getBlockMetadata(xTarget, yTarget, zTarget) == 0
                        && world.getBlockLightValue(xTarget, yTarget + 1, zTarget) >= 4
                        && world.getBlockLightOpacity(xTarget, yTarget + 1, zTarget) <= 2 )
                    {
                        world.setBlock(xTarget, yTarget, zTarget, BLBlockRegistry.slimyGrass);
                    }
                }
            }
        }
    }

	@Override
    public Item getItemDropped(int meta, Random rand, int fortune) {
        return BLBlockRegistry.slimyDirt.getItemDropped(0, rand, fortune);
    }
}
