package thebetweenlands.world.feature.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.blocks.BlockGenericPlanks;
import thebetweenlands.blocks.tree.BlockBLLog;

import java.util.Random;

public class WorldGenSwampHut extends WorldGenerator {

    Block fence = BLBlockRegistry.weedwoodPlankFence;
    Block door = BLBlockRegistry.doorWeedwood;
    private BlockBLLog log = (BlockBLLog) BLBlockRegistry.weedwoodLog;
    private Block plank = (BlockGenericPlanks) BLBlockRegistry.weedwoodPlanks;
    private Block stairs = BLBlockRegistry.weedwoodPlankStairs;
    private Block roof = BLBlockRegistry.thatchSlope;
    private Block bricks = BLBlockRegistry.betweenstoneBricks;
    private Block thatchBlock = BLBlockRegistry.thatch;
    private int length = -1;
    private int width = -1;
    private int height = -1;
    private int direction = -1;

    public WorldGenSwampHut() {
        length = 16;
        width = 16;
        height = 10;
        direction = 0;
    }


    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        BiomeGenBase biomeBase = world.getBiomeGenForCoords(x, z);
        //int newY = 80;
        /*if (biomeBase == BLBiomeRegistry.marsh1 || biomeBase == BLBiomeRegistry.marsh2) {
            for (int newX = x; newX <= x + length; ++newX) {
                for (int newZ = z; newZ <= z + width; ++newZ) {
                    Block block = world.getBlock(newX, newY, newZ);
                    if (block != null && block == biomeBase.topBlock) {
                        //  if(random.nextInt(ConfigHandler.SWAMP_HUT_FREQUENCY) == 0) */
        generateStructure(world, random, x, y, z);
                    /*}
                }
            }
        }*/
        return true;
    }

    public boolean generateStructure(World world, Random rand, int x, int y, int z) {
        // air check
      /*  for(int newX = x; newX <= x + length; ++newX) {
            for(int newZ = z; newZ <= z + width; ++newZ) {
                for(int newY = y + 1; newY < y + height; ++newY ) {
                    if(!world.isAirBlock(newX, newY, newZ)) {
                        return false;
                    }
                }
            }
        } */
        //hut generation starts here ;)
        verticalBeam(world, rand, x + 5, y, z + 5, log, 0, 4, 0);
        verticalBeam(world, rand, x + 10, y, z + 5, log, 0, 4, 0);
        verticalBeam(world, rand, x + 5, y, z + 10, log, 0, 4, 0);
        verticalBeam(world, rand, x + 10, y, z + 10, log, 0, 4, 0);

        verticalBeam(world, rand, x + 4, y + 4, z + 4, log, 0, 3, 0);
        verticalBeam(world, rand, x + 11, y + 4, z + 4, log, 0, 3, 0);
        verticalBeam(world, rand, x + 4, y + 4, z + 11, log, 0, 3, 0);
        verticalBeam(world, rand, x + 11, y + 4, z + 11, log, 0, 3, 0);

        verticalBeam(world, rand, x + 4, y + 5, z + 5, log, 0, 4, 0);
        verticalBeam(world, rand, x + 11, y + 5, z + 5, log, 0, 4, 0);
        verticalBeam(world, rand, x + 4, y + 5, z + 10, log, 0, 4, 0);
        verticalBeam(world, rand, x + 11, y + 5, z + 10, log, 0, 4, 0);

        verticalBeam(world, rand, x + 5, y + 5, z + 4, log, 0, 4, 0);
        verticalBeam(world, rand, x + 10, y + 5, z + 4, log, 0, 4, 0);
        verticalBeam(world, rand, x + 5, y + 5, z + 11, log, 0, 4, 0);
        verticalBeam(world, rand, x + 10, y + 5, z + 11, log, 0, 4, 0);

        verticalBeam(world, rand, x + 4, y + 8, z + 6, log, 0, 2, 0);
        verticalBeam(world, rand, x + 11, y + 8, z + 6, log, 0, 2, 0);
        verticalBeam(world, rand, x + 4, y + 8, z + 9, log, 0, 2, 0);
        verticalBeam(world, rand, x + 11, y + 8, z + 9, log, 0, 2, 0);

        verticalBeam(world, rand, x + 6, y + 8, z + 4, log, 0, 2, 0);
        verticalBeam(world, rand, x + 9, y + 8, z + 4, log, 0, 2, 0);
        verticalBeam(world, rand, x + 6, y + 8, z + 11, log, 0, 2, 0);
        verticalBeam(world, rand, x + 9, y + 8, z + 11, log, 0, 2, 0);

        verticalBeam(world, rand, x + 4, y + 9, z + 7, log, 0, 2, 0);
        verticalBeam(world, rand, x + 11, y + 9, z + 7, log, 0, 2, 0);
        verticalBeam(world, rand, x + 4, y + 9, z + 8, log, 0, 2, 0);
        verticalBeam(world, rand, x + 11, y + 9, z + 8, log, 0, 2, 0);

        verticalBeam(world, rand, x + 7, y + 9, z + 4, log, 0, 2, 0);
        verticalBeam(world, rand, x + 7, y + 9, z + 11, log, 0, 2, 0);
        verticalBeam(world, rand, x + 8, y + 9, z + 4, log, 0, 2, 0);
        verticalBeam(world, rand, x + 8, y + 9, z + 11, log, 0, 2, 0);

        //house duplicate walls and roof
        for (int direction = 0; direction < 4; direction++) {
            rotatedBeam(world, rand, x, y, z, 6, 5, bricks, direction == 0 || direction == 2 ? 4 : 8, 4, direction);

            // bottom window
            rotatedBeam(world, rand, x, y + 1, z, 6, 5, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
            rotatedBeam(world, rand, x, y + 1, z, 7, 5, fence, direction == 0 || direction == 2 ? 4 : 8, 2, direction);
            rotatedBeam(world, rand, x, y + 1, z, 9, 5, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);

            rotatedBeam(world, rand, x, y + 2, z, 6, 5, plank, direction == 0 || direction == 2 ? 4 : 8, 4, direction);
            rotatedBeam(world, rand, x, y + 3, z, 6, 5, plank, direction == 0 || direction == 2 ? 4 : 8, 4, direction);
            rotatedBeam(world, rand, x, y + 4, z, 5, 5, plank, direction == 0 || direction == 2 ? 4 : 8, 6, direction);
            rotatedBeam(world, rand, x, y + 4, z, 5, 4, plank, direction == 0 || direction == 2 ? 4 : 8, 6, direction);
            rotatedBeam(world, rand, x, y + 3, z, 4, 4, stairs, direction == 0 ? 6 : direction == 1 ? 4 : direction == 2 ? 7 : 5, 8, direction);

            // mid window
            rotatedBeam(world, rand, x, y + 5, z, 6, 4, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
            rotatedBeam(world, rand, x, y + 5, z, 7, 4, fence, direction == 0 || direction == 2 ? 4 : 8, 2, direction);
            rotatedBeam(world, rand, x, y + 5, z, 9, 4, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
            rotatedBeam(world, rand, x, y + 6, z, 6, 4, fence, direction == 0 || direction == 2 ? 4 : 8, 4, direction);
            rotatedBeam(world, rand, x, y + 7, z, 6, 4, plank, direction == 0 || direction == 2 ? 4 : 8, 4, direction);

            // top window
            rotatedBeam(world, rand, x, y + 8, z, 7, 4, fence, direction == 0 || direction == 2 ? 4 : 8, 2, direction);

            rotatedBeam(world, rand, x, y + 7, z, 4, 4, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
            rotatedBeam(world, rand, x, y + 7, z, 11, 4, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);

            // roof left
            rotatedBeam(world, rand, x, y + 11, z, 3, 7, roof, direction == 0 ? 2 : direction == 1 ? 0 : direction == 2 ? 3 : 1, 4, direction);
            rotatedBeam(world, rand, x, y + 10, z, 3, 6, roof, direction == 0 ? 2 : direction == 1 ? 0 : direction == 2 ? 3 : 1, 4, direction);
            rotatedBeam(world, rand, x, y + 9, z, 3, 5, roof, direction == 0 ? 2 : direction == 1 ? 0 : direction == 2 ? 3 : 1, 3, direction);
            rotatedBeam(world, rand, x, y + 8, z, 3, 4, roof, direction == 0 ? 2 : direction == 1 ? 0 : direction == 2 ? 3 : 1, 2, direction);
            rotatedBeam(world, rand, x, y + 10, z, 3, 7, roof, direction == 0 ? 7 : direction == 1 ? 5 : direction == 2 ? 6 : 4, 1, direction);
            rotatedBeam(world, rand, x, y + 9, z, 3, 6, roof, direction == 0 ? 7 : direction == 1 ? 5 : direction == 2 ? 6 : 4, 1, direction);
            rotatedBeam(world, rand, x, y + 8, z, 3, 5, roof, direction == 0 ? 7 : direction == 1 ? 5 : direction == 2 ? 6 : 4, 1, direction);

            // roof right
            rotatedBeam(world, rand, x, y + 11, z, 3, 8, roof, direction == 0 ? 3 : direction == 1 ? 1 : direction == 2 ? 2 : 0, 4, direction);
            rotatedBeam(world, rand, x, y + 10, z, 3, 9, roof, direction == 0 ? 3 : direction == 1 ? 1 : direction == 2 ? 2 : 0, 4, direction);
            rotatedBeam(world, rand, x, y + 9, z, 3, 10, roof, direction == 0 ? 3 : direction == 1 ? 1 : direction == 2 ? 2 : 0, 3, direction);
            rotatedBeam(world, rand, x, y + 8, z, 3, 11, roof, direction == 0 ? 3 : direction == 1 ? 1 : direction == 2 ? 2 : 0, 2, direction);
            rotatedBeam(world, rand, x, y + 10, z, 3, 8, roof, direction == 0 ? 6 : direction == 1 ? 4 : direction == 2 ? 7 : 5, 1, direction);
            rotatedBeam(world, rand, x, y + 9, z, 3, 9, roof, direction == 0 ? 6 : direction == 1 ? 4 : direction == 2 ? 7 : 5, 1, direction);
            rotatedBeam(world, rand, x, y + 8, z, 3, 10, roof, direction == 0 ? 6 : direction == 1 ? 4 : direction == 2 ? 7 : 5, 1, direction);

            //roof apex
            rotatedBeam(world, rand, x, y + 11, z, 7, 7, thatchBlock, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
            rotatedBeam(world, rand, x, y + 11, z, 7, 8, thatchBlock, direction == 0 || direction == 2 ? 4 : 8, 1, direction);

        }

        System.out.println("Added Hut at: " + x + " " + z);
        // door/extension(s)
        boolean leftExtension = rand.nextBoolean();
        boolean rightExtension = rand.nextBoolean();
        boolean frontExtension = rand.nextBoolean();
        boolean backExtension = rand.nextBoolean();

        if (!leftExtension && !rightExtension && !frontExtension && !backExtension) {
            System.out.println("Door can only be on main building");
        }

        //   if(frontExtension) {
        //	   addExtention(world, rand,  x, y, z, 0);
        //	   System.out.println("Has Front Wing");
        //   }

        if (leftExtension) {
            addExtention(world, rand, x, y, z, 1);
            System.out.println("Has Left Wing");
        }

        if (backExtension) {
            addExtention(world, rand, x, y, z, 2);
            System.out.println("Has Back Wing");
        }

        if (rightExtension) {
            addExtention(world, rand, x, y, z, 3);
            System.out.println("Has Right Wing");
        }

        System.out.println("Added Hut at: " + x + " " + z);
        return true;
    }

    private void addExtention(World world, Random rand, int x, int y, int z, int direction) {
        //frame
        rotatedBeam(world, rand, x, y + 2, z, 2, 6, log, direction == 0 || direction == 2 ? 4 : 8, 3, direction);
        rotatedBeam(world, rand, x, y + 2, z, 2, 9, log, direction == 0 || direction == 2 ? 4 : 8, 3, direction);

        for (int beamHeight = 0; beamHeight < 2; beamHeight++) {
            rotatedBeam(world, rand, x, y + beamHeight, z, 1, 6, log, 0, 1, direction);
            rotatedBeam(world, rand, x, y + beamHeight, z, 1, 9, log, 0, 1, direction);
            rotatedBeam(world, rand, x, y + beamHeight, z, 4, 6, log, 0, 1, direction);
            rotatedBeam(world, rand, x, y + beamHeight, z, 4, 9, log, 0, 1, direction);
        }

        for (int beamLength = 0; beamLength < 4; beamLength++)
            rotatedBeam(world, rand, x, y + 2, z, 1, 6 + beamLength, log, direction == 0 || direction == 2 ? 8 : 4, 1, direction);

        // air gap
        for (int beamLength = 0; beamLength < 2; beamLength++)
            for (int beamHeight = 0; beamHeight < 3; beamHeight++)
                rotatedBeam(world, rand, x, y + beamHeight, z, 5, 7 + beamLength, Blocks.air, 0, 1, direction);

        //windows side
        for (int beamLength = 0; beamLength < 2; beamLength++) {
            rotatedBeam(world, rand, x, y, z, 1, 7 + beamLength, bricks, direction == 0 || direction == 2 ? 8 : 4, 1, direction);
            rotatedBeam(world, rand, x, y + 1, z, 1, 7 + beamLength, fence, direction == 0 || direction == 2 ? 8 : 4, 1, direction);
        }
        //windows back
        rotatedBeam(world, rand, x, y, z, 2, 6, bricks, direction == 0 || direction == 2 ? 4 : 8, 2, direction);
        rotatedBeam(world, rand, x, y + 1, z, 2, 6, fence, direction == 0 || direction == 2 ? 4 : 8, 2, direction);

        //front door
        rotatedBeam(world, rand, x, y, z, 2, 9, bricks, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
        rotatedBeam(world, rand, x, y + 1, z, 2, 9, plank, direction == 0 || direction == 2 ? 4 : 8, 1, direction);
        //rotatedBeam(world, rand, x, y, z, 3, 9, door, direction == 1 ? 3 : direction == 3 ? 1 : 2, 1, direction);
        //rotatedBeam(world, rand, x, y +1, z, 3, 9, door, direction == 1 ? 8 : 9, 1, direction);
    }

    public void rotatedBeam(World world, Random rand, int x, int y, int z, int a, int b, Block blockType, int blockMeta, int size, int direction) {
        switch (direction) {
            case 0:
                for (int xx = x + a; xx < x + a + size; xx++)
                    world.setBlock(xx, y, z + b, blockType, blockMeta, 2);
                break;
            case 1:
                for (int zz = z + a; zz < z + a + size; zz++)
                    world.setBlock(x + b, y, zz, blockType, blockMeta, 2);
                break;
            case 2:
                for (int xx = x + length - a - 1; xx > x + length - a - size - 1; xx--)
                    world.setBlock(xx, y, z + length - b - 1, blockType, blockMeta, 2);
                break;
            case 3:
                for (int zz = z + length - a - 1; zz > z + length - a - size - 1; zz--)
                    world.setBlock(x + length - b - 1, y, zz, blockType, blockMeta, 2);
                break;
        }
    }

    public void verticalBeam(World world, Random rand, int x, int y, int z, Block blockType, int blockMeta, int size, int direction) {
        for (int yy = y; yy < y + size; yy++)
            world.setBlock(x, yy, z, blockType, blockMeta, 2);
    }

    public void horizontalBeam(World world, Random rand, int x, int y, int z, Block blockType, int blockMeta, int size, int direction) {
        switch (direction) {
            case 0:
                for (int xx = x; xx < x + size; xx++)
                    world.setBlock(xx, y, z, blockType, blockMeta, 2);
                break;
            case 1:
                for (int zz = z; zz < z + size; zz++)
                    world.setBlock(x, y, zz, blockType, blockMeta, 2);
                break;
        }
    }
}