package thebetweenlands.world.feature.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.tileentities.TileEntityLootPot1;
import thebetweenlands.world.WorldProviderBetweenlands;
import thebetweenlands.world.loot.LootTables;
import thebetweenlands.world.loot.LootUtil;

import java.util.Random;

/**
 * Created by Bart on 19/02/2016.
 */
public class WorldGenUnderGroundStructures extends WorldGenerator {
    private static final boolean markReplaceableCheck = false;
    private static final int[] stairSequence = new int[]{0, 3, 1, 2};
    private static final int[] upsideDownStairSequence = new int[]{4, 7, 5, 6};
    private int width = -1;
    private int depth = -1;

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        boolean shouldStop = true;
        while (y > WorldProviderBetweenlands.CAVE_WATER_HEIGHT) {
            if (world.getBlock(x, y - 1, z) == BLBlockRegistry.pitstone && world.getBlock(x, y, z) == Blocks.air) {
                shouldStop = false;
                break;
            }
            y--;
        }
        if (shouldStop)
            return false;

        int randomInt = random.nextInt(6);
        switch (randomInt) {
            case 0:
                return structure1(world, random, x, y, z);
            case 1:
                return structure2(world, random, x, y, z);
            case 2:
                return structure3(world, random, x, y, z);
            case 3:
                return structure4(world, random, x, y, z);
            case 4:
                return structure5(world, random, x, y, z);
            case 5:
                return structure6(world, random, x, y, z);
            default:
                return false;
        }
    }


    private boolean structure1(World world, Random random, int x, int y, int z) {
        width = 7;
        depth = 6;
        int direction = random.nextInt(4);
        if (rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, 1, 4, depth, direction)
                || rotatedCubeCantReplace(world, x, y, z, 6, 0, 0, 1, 4, depth, direction)
                || rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, width, 4, 1, direction)
                || rotatedCubeCantReplace(world, x, y, z, 0, 0, 5, width, 4, 1, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, false);

        rotatedCubeVolume(world, x, y, z, 0, 0, 0, BLBlockRegistry.pitstoneTiles, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 6, 0, 0, BLBlockRegistry.pitstoneTiles, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 5, 0, 0, BLBlockRegistry.pitstoneTiles, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 0, BLBlockRegistry.pitstoneTiles, 0, 2, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 5, BLBlockRegistry.pitstoneTiles, 0, 5, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 1, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 5, BLBlockRegistry.pitstoneBricks, 0, 5, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1 + random.nextInt(5), 1, 5, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 6, 1, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 6, 1, 2 + random.nextInt(2), Blocks.air, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 6, 2, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 5, BLBlockRegistry.pitstoneBricks, 0, 7, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2 + random.nextInt(4), 2, 5, Blocks.air, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 3, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 2, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 3, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 2, direction);

        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 1, 0, 1, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 5, 0, 1, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 1, 0, 4, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 5, 0, 4, direction);
        return true;
    }


    private boolean structure2(World world, Random random, int x, int y, int z) {
        width = 9;
        depth = 11;
        int direction = random.nextInt(4);
        int height = 6 + random.nextInt(2);
        if (rotatedCubeCantReplace(world, x, y, z, 4, height - 3, 0, 5, height - 3, depth, direction) || rotatedCubeCantReplace(world, x, y, z, 0, height - 3, 6, 4, height - 3, 5, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 6, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 0, -1, 10, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 4, -1, 6, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 4, -1, 10, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 4, -1, 2, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 8, -1, 6, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 8, -1, 10, 1, 1, direction, true)
                || !makePitstoneSupport(world, x, y, z, 8, -1, 2, 1, 1, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 6, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 0, -1, 10, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 4, -1, 6, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 4, -1, 10, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 4, -1, 2, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 8, -1, 6, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 8, -1, 10, 1, 1, direction, false);
        makePitstoneSupport(world, x, y, z, 8, -1, 2, 1, 1, direction, false);

        rotatedCubeVolume(world, x, y, z, 0, 0, 6, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 0, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 0, 6, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 0, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 0, 2, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, 0, 6, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, 0, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, 0, 2, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 1, 6, BLBlockRegistry.pitstonePillar, 0, 1, 2 + random.nextInt(3), 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 1, 10, BLBlockRegistry.pitstonePillar, 0, 1, random.nextInt(3), 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 1, 6, BLBlockRegistry.pitstonePillar, 0, 1, height - 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 1, 10, BLBlockRegistry.pitstonePillar, 0, 1, height - 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 1, 2, BLBlockRegistry.pitstonePillar, 0, 1, height - 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, 1, 6, BLBlockRegistry.pitstonePillar, 0, 1, height - 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, 1, 10, BLBlockRegistry.pitstonePillar, 0, 1, height - 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, 1, 2, BLBlockRegistry.pitstonePillar, 0, 1, height - 3, 1, direction);

        rotatedCubeVolume(world, x, y, z, 1, height - 2, 10, BLBlockRegistry.pitstoneBricks, 0, 8, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2, height - 2, 9, BLBlockRegistry.pitstoneTiles, 0, 6, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3, height - 2, 8, BLBlockRegistry.pitstoneTiles, 0, 5, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2, height - 2, 7, BLBlockRegistry.pitstoneTiles, 0, 6, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, height - 2, 6, BLBlockRegistry.pitstoneBricks, 0, 4, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 8, height - 2, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 10, direction);
        rotatedCubeVolume(world, x, y, z, 7, height - 2, 1, BLBlockRegistry.pitstoneTiles, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 6, height - 2, 2, BLBlockRegistry.pitstoneTiles, 0, 1, 1, 5, direction);
        rotatedCubeVolume(world, x, y, z, 5, height - 2, 3, BLBlockRegistry.pitstoneTiles, 0, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 4, height - 2, 2, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 4, direction);

        rotatedCubeVolume(world, x, y, z, 8, height - 1, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 8, height - 1, 1, BLBlockRegistry.pitstoneBrickWall, 0, 1, 1, 9, direction);
        rotatedCubeVolume(world, x, y, z, 8, height - 1, 2 + random.nextInt(7), Blocks.air, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, height - 1, 2, BLBlockRegistry.pitstoneBrickWall, 0, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 4, height - 1, 3 + random.nextInt(2), Blocks.air, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, height - 1, 6, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2, height - 1, 10, BLBlockRegistry.pitstoneBrickWall, 0, 6, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3 + random.nextInt(4), height - 1, 10, Blocks.air, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, height - 1, 6, BLBlockRegistry.pitstoneBrickWall, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3, height - 1, 6, BLBlockRegistry.pitstoneBrickWall, 0, 1, 1, 1, direction);
        if (random.nextBoolean())
            rotatedCubeVolume(world, x, y, z, 8, height, 10, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);


        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 7, height - 1, 9, direction);
        if (random.nextInt(4) == 0)
            rotatedLoot(world, random, x, y, z, 5, height - 1, 4, direction);
        if (random.nextInt(4) == 0)
            rotatedLoot(world, random, x, y, z, 1, 0, 7, direction);
        if (random.nextInt(4) == 0)
            rotatedLoot(world, random, x, y, z, 7, 0, 9, direction);
        return true;
    }

    private boolean structure3(World world, Random random, int x, int y, int z) {
        width = 6;
        depth = 6;
        int direction = 0;
        if (rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, width, 7, depth, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, false);

        rotatedCubeVolume(world, x, y, z, 1, 0, 1, BLBlockRegistry.pitstoneTiles, 0, 4, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 0, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 0, 5, BLBlockRegistry.pitstoneBricks, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 0, 5, BLBlockRegistry.pitstoneBricks, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 0, BLBlockRegistry.pitstoneBrickStairs, 2, 4, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 0, 1, BLBlockRegistry.pitstoneBrickStairs, 0, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 5, BLBlockRegistry.pitstoneBrickStairs, 3, 4, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 0, 1, BLBlockRegistry.pitstoneBrickStairs, 1, 1, 1, 4, direction);

        rotatedCubeVolume(world, x, y, z, 0, 2, 0, BLBlockRegistry.pitstonePillar, 0, 1, 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 5, BLBlockRegistry.pitstonePillar, 0, 1, 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 2, 0, BLBlockRegistry.pitstonePillar, 0, 1, 3, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 2, 5, BLBlockRegistry.pitstonePillar, 0, 1, 3, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 5, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 5, 5, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 5, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 5, 5, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 5, 0, BLBlockRegistry.pitstoneBrickSlab, 8, 4, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 5, 1, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 1, 5, 5, BLBlockRegistry.pitstoneBrickSlab, 8, 4, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 5, 1, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 4, direction);

        rotatedCubeVolume(world, x, y, z, 1, 6, 0, BLBlockRegistry.pitstoneBrickSlab, 0, 4, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 6, 1, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 1, 6, 5, BLBlockRegistry.pitstoneBrickSlab, 0, 4, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 5, 6, 1, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 4, direction);

        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 1, 1, 1, 0);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 4, 1, 1, 0);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 1, 1, 4, 0);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 4, 1, 4, 0);
        return true;
    }

    private boolean structure4(World world, Random random, int x, int y, int z) {
        depth = 11;
        width = 5;
        int direction = random.nextInt(4);
        if (rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, width, 4, depth, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, false);

        rotatedCubeVolume(world, x, y, z, 0, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 11, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3, 0, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3, 0, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 0, 2, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 9, direction);
        rotatedCubeVolume(world, x, y, z, 2, 0, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 1, 2, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 9, direction);
        rotatedCubeVolume(world, x, y, z, 4, 1, 3, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 8, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 5, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 5, direction);
        rotatedCubeVolume(world, x, y, z, 3, 1, 5, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 5, direction);
        rotatedCubeVolume(world, x, y, z, 2, 1, 10, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 1, 2, 5, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 5, direction);
        rotatedCubeVolume(world, x, y, z, 3, 2, 5, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 5, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 3, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 8, direction);
        rotatedCubeVolume(world, x, y, z, 4, 2, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 7, direction);
        rotatedCubeVolume(world, x, y, z, 2, 2, 10, BLBlockRegistry.pitstoneBricks, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2, 2, 4, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(3, direction, upsideDownStairSequence), 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 1, 3, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 2, 3, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 3, 3, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 6, direction);

        for (int i = 0; i <= 4; i++) {
            if (random.nextInt(4) == 0)
                rotatedLoot(world, random, x, y, z, 1, 0, 5 + i, direction);
            if (random.nextInt(4) == 0)
                rotatedLoot(world, random, x, y, z, 3, 0, 5 + i, direction);
        }
        return true;
    }


    private boolean structure5(World world, Random random, int x, int y, int z) {
        depth = 8;
        width = 7;
        int direction = random.nextInt(4);

        if (rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, width, 5, depth, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, false);

        rotatedCubeVolume(world, x, y, z, 1, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 7, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 6, BLBlockRegistry.pitstoneBricks, 0, 7, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 7, direction);
        rotatedCubeVolume(world, x, y, z, 7, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 7, direction);
        rotatedCubeVolume(world, x, y, z, 0, 0, 2, BLBlockRegistry.pitstoneBrickWall, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 0, 4, BLBlockRegistry.pitstoneBrickWall, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2, 0, 1, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3, 0, 1, BLBlockRegistry.pitstoneBrickSlab, 8, 3, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 6, 0, 1, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 2, 0, 5, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 3, 0, 5, BLBlockRegistry.pitstoneBrickSlab, 8, 3, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 6, 0, 5, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 1, 1, 0, BLBlockRegistry.pitstoneTiles, 0, 7, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 6, BLBlockRegistry.pitstoneTiles, 0, 7, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 0, BLBlockRegistry.pitstoneTiles, 0, 1, 2, 7, direction);
        rotatedCubeVolume(world, x, y, z, 7, 1, 0, BLBlockRegistry.pitstoneTiles, 0, 1, 2, 7, direction);
        rotatedCubeVolume(world, x, y, z, 4, 1, 0, BLBlockRegistry.pitstoneBrickWall, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 4, 1, 6, BLBlockRegistry.pitstoneBrickWall, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 7, 1, 3, BLBlockRegistry.pitstoneBrickWall, 0, 1, 2, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 1, BLBlockRegistry.pitstoneBrickWall, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 1, 5, BLBlockRegistry.pitstoneBrickWall, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 3, Blocks.air, 0, 1, 2, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 2, 2, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(3, direction, stairSequence), 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 3, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(0, direction, stairSequence), 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 0, 2, 4, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(1, direction, stairSequence), 1, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 1, 3, 0, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(3, direction, stairSequence), 7, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 1, 3, 1, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(0, direction, stairSequence), 1, 1, 6, direction);
        rotatedCubeVolume(world, x, y, z, 2, 3, 6, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(1, direction, stairSequence), 6, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 7, 3, 1, BLBlockRegistry.pitstoneBrickStairs, getMetaFromDirection(2, direction, stairSequence), 1, 1, 5, direction);

        rotatedCubeVolume(world, x, y, z, 2, 4, 1, BLBlockRegistry.pitstoneBrickSlab, 0, 5, 1, 5, direction);

        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 2, 1, 1, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 2, 1, 5, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 6, 1, 1, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 6, 1, 5, direction);
        return true;
    }

    private boolean structure6(World world, Random random, int x, int y, int z) {
        width = 7;
        depth = 7;
        int direction = random.nextInt(4);
        if (rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, 3, 2, 1, direction)
                || rotatedCubeCantReplace(world, x, y, z, 1, 0, 0, 1, 1, 1, direction)
                || rotatedCubeCantReplace(world, x, y, z, 6, 0, 3, 1, 2, 3, direction)
                || rotatedCubeCantReplace(world, x, y, z, 2, 0, 6, 4, 3, 1, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, false);

        rotatedCubeVolume(world, x, y, z, 0, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 3, direction);
        rotatedCubeVolume(world, x, y, z, 1, 0, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, x, y, z, 6, 0, 3, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 2, 0, 6, BLBlockRegistry.pitstoneBricks, 0, 4, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 0, 1, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 2, direction);
        rotatedCubeVolume(world, x, y, z, 6, 1, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 4, direction);
        rotatedCubeVolume(world, x, y, z, 3, 1, 6, BLBlockRegistry.pitstoneBricks, 0, 3, 1, 1, direction);

        rotatedCubeVolume(world, x, y, z, 5, 2, 6, BLBlockRegistry.pitstoneBricks, 0, 2, 1, 1, direction);

        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 5, 0, 5, direction);
        if (random.nextInt(3) == 0)
            rotatedLoot(world, random, x, y, z, 1, 0, 1, direction);
        return true;
    }

    /*public boolean structure5(World world, Random random, int x, int y, int z) {
        depth = 13;
        width = 8;
        int direction = 0;//random.nextInt(4);
        if (!rotatedCubeCantReplace(world, x, y, z, 0, 0, 0, width, 1, depth, direction)
                || !rotatedCubeCantReplace(world, x, y, z, 0, 5, 0, width, 1, depth, direction)
                || !rotatedCubeCantReplace(world, x, y, z, 2, 6, 0, 6, 3, depth, direction))
            return false;
        if (!makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, true))
            return false;
        makePitstoneSupport(world, x, y, z, 0, -1, 0, width, depth, direction, false);

        rotatedCubeVolume(world, random, x, y, z, 1, 0, 1, BLBlockRegistry.pitstoneBrickStairs, 0, 1, 1, 12, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 0, 0, BLBlockRegistry.pitstoneBrickStairs, 2, 4, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 0, 12, BLBlockRegistry.pitstoneBrickStairs, 3, 4, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 0, 1, BLBlockRegistry.pitstoneTiles, 0, 4, 1, 11, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 0, 1, BLBlockRegistry.pitstoneBrickStairs, 1, 1, 1, 12, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 0, 0, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 0, 4, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 0, 8, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 0, 12, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 0, 0, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 0, 4, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 0, 8, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 0, 12, BLBlockRegistry.chiseledPitstone, 0, 1, 1, 1, direction);

        rotatedCubeVolume(world, random, x, y, z, 1, 1, 0, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 1, 4, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 1, 8, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 1, 12, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 1, 0, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 1, 4, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 1, 8, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 1, 12, BLBlockRegistry.pitstonePillar, 0, 1, 4, 1, direction);

        rotatedCubeVolume(world, random, x, y, z, 6, 5, 0, BLBlockRegistry.pitstoneBrickSlab, 8, 2, 1, 13, direction);
        rotatedCubeVolume(world, random, x, y, z, 0, 5, 0, BLBlockRegistry.pitstoneBrickSlab, 8, 2, 1, 13, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 5, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 5, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 5, 8, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 1, 5, 12, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 5, 0, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 5, 4, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 5, 8, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 5, 12, BLBlockRegistry.pitstoneBricks, 0, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 5, 0, BLBlockRegistry.pitstoneBrickSlab, 8, 4, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 5, 4, BLBlockRegistry.pitstoneBrickSlab, 8, 4, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 5, 8, BLBlockRegistry.pitstoneBrickSlab, 8, 4, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 5, 12, BLBlockRegistry.pitstoneBrickSlab, 8, 4, 1, 1, direction);

        rotatedCubeVolume(world, random, x, y, z, 1, 6, 0, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 13, direction);
        rotatedCubeVolume(world, random, x, y, z, 6, 6, 0, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 13, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 6, 1, BLBlockRegistry.pitstoneBricks, 0, 4, 1, 11, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 6, 0, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 5, 6, 0, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 2, 6, 12, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 1, direction);
        rotatedCubeVolume(world, random, x, y, z, 5, 6, 12, BLBlockRegistry.pitstoneBrickSlab, 8, 1, 1, 1, direction);

        rotatedCubeVolume(world, random, x, y, z, 3, 7, 0, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 13, direction);
        rotatedCubeVolume(world, random, x, y, z, 4, 7, 0, BLBlockRegistry.pitstoneBrickSlab, 0, 1, 1, 13, direction);

        if (random.nextInt(2) == 0)
            rotatedLoot(world, random, x, y, z, 5, 1, 2, 0);
        if (random.nextInt(2) == 0)
            rotatedLoot(world, random, x, y, z, 2, 1, 6, 0);
        if (random.nextInt(2) == 0)
            rotatedLoot(world, random, x, y, z, 5, 1, 10, 0);
        System.out.println("generated at: " + x + " " + y + " " + z);
        return true;
    }*/


    private void rotatedCubeVolume(World world, int x, int y, int z, int offsetA, int offsetB, int offsetC, Block blockType, int blockMeta, int sizeWidth, int sizeHeight, int sizeDepth, int direction) {
        x -= width / 2;
        z -= depth / 2;
        switch (direction) {
            case 0:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int xx = x + offsetA; xx < x + offsetA + sizeWidth; xx++)
                        for (int zz = z + offsetC; zz < z + offsetC + sizeDepth; zz++)
                            world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
            case 1:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int zz = z + depth - offsetA - 1; zz > z + depth - offsetA - sizeWidth - 1; zz--)
                        for (int xx = x + offsetC; xx < x + offsetC + sizeDepth; xx++)
                            world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
            case 2:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int xx = x + width - offsetA - 1; xx > x + width - offsetA - sizeWidth - 1; xx--)
                        for (int zz = z + depth - offsetC - 1; zz > z + depth - offsetC - sizeDepth - 1; zz--)
                            world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
            case 3:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int zz = z + offsetA; zz < z + offsetA + sizeWidth; zz++)
                        for (int xx = x + width - offsetC - 1; xx > x + width - offsetC - sizeDepth - 1; xx--)
                            world.setBlock(xx, yy, zz, blockType, blockMeta, 2);
                break;
        }
    }

    private void rotatedLoot(World world, Random rand, int x, int y, int z, int offsetA, int offsetB, int offsetC, int direction) {
        x -= width / 2;
        z -= depth / 2;
        switch (direction) {
            case 0:
                generateLoot(world, rand, x + offsetA, y + offsetB, z + offsetC);
                break;
            case 1:
                generateLoot(world, rand, x + offsetC, y + offsetB, z + depth - offsetA - 1);
                break;
            case 2:
                generateLoot(world, rand, x + width - offsetA - 1, y + offsetB, z + depth - offsetC - 1);
                break;
            case 3:
                generateLoot(world, rand, x + width - offsetC - 1, y + offsetB, z + offsetA);
                break;
        }
    }


    private boolean makePitstoneSupport(World world, int x, int y, int z, int offsetA, int offsetB, int offsetC, int sizeWidth, int sizeDepth, int direction, boolean simulate) {
        x -= width / 2;
        z -= depth / 2;
        switch (direction) {
            case 0:
                for (int xx = x + offsetA; xx < x + offsetA + sizeWidth; xx++)
                    for (int zz = z + offsetC; zz < z + offsetC + sizeDepth; zz++) {
                        int yy = y + offsetB;
                        int times = 0;
                        while (world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz)) {
                            if (!simulate)
                                world.setBlock(xx, yy, zz, BLBlockRegistry.pitstone, 0, 2);
                            yy--;
                            times++;
                            if (times > 4)
                                return false;
                        }
                    }
                break;
            case 1:
                for (int zz = z + depth - offsetA - 1; zz > z + depth - offsetA - sizeWidth - 1; zz--)
                    for (int xx = x + offsetC; xx < x + offsetC + sizeDepth; xx++) {
                        int yy = y + offsetB;
                        int times = 0;
                        while (world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz)) {
                            if (!simulate)
                                world.setBlock(xx, yy, zz, BLBlockRegistry.pitstone, 0, 2);
                            yy--;
                            times++;
                            if (times > 4)
                                return false;
                        }
                    }
                break;
            case 2:
                for (int xx = x + width - offsetA - 1; xx > x + width - offsetA - sizeWidth - 1; xx--)
                    for (int zz = z + depth - offsetC - 1; zz > z + depth - offsetC - sizeDepth - 1; zz--) {
                        int yy = y + offsetB;
                        int times = 0;
                        while (world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz)) {
                            if (!simulate)
                                world.setBlock(xx, yy, zz, BLBlockRegistry.pitstone, 0, 2);
                            yy--;
                            times++;
                            if (times > 4)
                                return false;
                        }
                    }
                break;
            case 3:
                for (int zz = z + offsetA; zz < z + offsetA + sizeWidth; zz++)
                    for (int xx = x + width - offsetC - 1; xx > x + width - offsetC - sizeDepth - 1; xx--) {
                        int yy = y + offsetB;
                        int times = 0;
                        while (world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz)) {
                            if (!simulate)
                                world.setBlock(xx, yy, zz, BLBlockRegistry.pitstone, 0, 2);
                            yy--;
                            times++;
                            if (times > 4)
                                return false;
                        }
                    }
                break;
        }
        return true;
    }

    private boolean rotatedCubeCantReplace(World world, int x, int y, int z, int offsetA, int offsetB, int offsetC, int sizeWidth, int sizeHeight, int sizeDepth, int direction) {
        x -= width / 2;
        z -= depth / 2;
        boolean replaceable = true;
        switch (direction) {
            case 0:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int xx = x + offsetA; xx < x + offsetA + sizeWidth; xx++)
                        for (int zz = z + offsetC; zz < z + offsetC + sizeDepth; zz++) {
                            if (!world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz))
                                replaceable = false;
                            if (markReplaceableCheck)
                                world.setBlock(xx, yy, zz, Blocks.wool);
                        }
                break;
            case 1:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int zz = z + depth - offsetA - 1; zz > z + depth - offsetA - sizeWidth - 1; zz--)
                        for (int xx = x + offsetC; xx < x + offsetC + sizeDepth; xx++) {
                            if (!world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz))
                                replaceable = false;
                            if (markReplaceableCheck)
                                world.setBlock(xx, yy, zz, Blocks.wool);
                        }
                break;
            case 2:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int xx = x + width - offsetA - 1; xx > x + width - offsetA - sizeWidth - 1; xx--)
                        for (int zz = z + depth - offsetC - 1; zz > z + depth - offsetC - sizeDepth - 1; zz--) {
                            if (!world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz))
                                replaceable = false;
                            if (markReplaceableCheck)
                                world.setBlock(xx, yy, zz, Blocks.wool);
                        }
                break;
            case 3:
                for (int yy = y + offsetB; yy < y + offsetB + sizeHeight; yy++)
                    for (int zz = z + offsetA; zz < z + offsetA + sizeWidth; zz++)
                        for (int xx = x + width - offsetC - 1; xx > x + width - offsetC - sizeDepth - 1; xx--) {
                            if (!world.getBlock(xx, yy, zz).isReplaceable(world, xx, yy, zz))
                                replaceable = false;
                            if (markReplaceableCheck)
                                world.setBlock(xx, yy, zz, Blocks.wool);
                        }
                break;
        }
        return !replaceable;
    }

    private void generateLoot(World world, Random random, int x, int y, int z) {
        int randDirection = random.nextInt(4) + 2;
        world.setBlock(x, y, z, getRandomBlock(random), randDirection, 3);
        TileEntityLootPot1 lootPot = (TileEntityLootPot1) world.getTileEntity(x, y, z);
        if (lootPot != null)
            LootUtil.generateLoot(lootPot, random, LootTables.COMMON_POT_LOOT, 1, 2);

    }

    private Block getRandomBlock(Random rand) {
        switch (rand.nextInt(3)) {
            case 0:
                return BLBlockRegistry.lootPot1;
            case 1:
                return BLBlockRegistry.lootPot2;
            case 2:
                return BLBlockRegistry.lootPot3;
            default:
                return BLBlockRegistry.lootPot1;
        }
    }

    private int getMetaFromDirection(int start, int direction, int[] sequence) {
        return sequence[(direction + start) % sequence.length];
    }
}
