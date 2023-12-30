package thebetweenlands.world.teleporter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.utils.confighandler.ConfigHandler;
import thebetweenlands.world.feature.trees.WorldGenWeedWoodPortalTree;

public final class TeleporterBetweenlands extends Teleporter {
	private final WorldServer worldServerInstance;
	private final LongHashMap destinationCoordinateCache = new LongHashMap();
	private final List<Long> destinationCoordinateKeys = new ArrayList<Long>();

	public TeleporterBetweenlands(WorldServer worldServer) {
		super(worldServer);
		worldServerInstance = worldServer;
	}

	@Override
	public void placeInPortal(Entity entity, double x, double y, double z, float rotationYaw) {
		if (!placeInExistingPortal(entity, x, y, z, rotationYaw)) {
			makePortal(entity);
			placeInExistingPortal(entity, x, y, z, rotationYaw);
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, double x, double y, double z, float rotationYaw) {
		int checkRadius = 128;
		double distToPortal = -1.0;
		int posX = 0;
		int posY = 0;
		int posZ = 0;
		int roundX = MathHelper.floor_double(x);
		int roundZ = MathHelper.floor_double(z);
		long coordPair = ChunkCoordIntPair.chunkXZ2Int(roundX, roundZ);
		boolean portalNotSaved = true;


		if (destinationCoordinateCache.containsItem(coordPair)) {
			PortalPosition pos = (PortalPosition) destinationCoordinateCache.getValueByKey(coordPair);
			distToPortal = 0.0;
			posX = pos.posX;
			posY = pos.posY;
			posZ = pos.posZ;
			pos.lastUpdateTime = worldServerInstance.getTotalWorldTime();
			portalNotSaved = false;
		} else {
			for (int i = roundX - checkRadius; i <= roundX + checkRadius; i++) {
				for (int j = roundZ - checkRadius; j <= roundZ + checkRadius; j++) {
					for (int h = worldServerInstance.getHeightValue(i, j); h >= 0; h--) {
						Block block = worldServerInstance.getBlock(i, h, j);
						if (block == BLBlockRegistry.treePortalBlock) {
							while(worldServerInstance.getBlock(i, --h, j) == BLBlockRegistry.treePortalBlock);
							double X = i + 0.5 - x;
							double Z = j + 0.5 - z;
							double Y = h + 2 + 0.5 - y;
							double dist = X * X + Y * Y + Z * Z;

							if (distToPortal < 0.0 || dist < distToPortal) {
								distToPortal = dist;
								posX = i;
								posY = h + 2;
								posZ = j;
							}
						}
					}
				}
			}
		}
		if (distToPortal >= 0.0) {
			if (portalNotSaved) {
				destinationCoordinateCache.add(coordPair, new PortalPosition(posX, posY, posZ, worldServerInstance.getTotalWorldTime()));
				destinationCoordinateKeys.add(Long.valueOf(coordPair));
			}

			entity.motionX = entity.motionY = entity.motionZ = 0.0;
			entity.setLocationAndAngles(posX + 0.5D, posY, posZ + 0.5D, entity.rotationYaw, entity.rotationPitch);
			if(entity.worldObj.provider.dimensionId != ConfigHandler.DIMENSION_ID)
				setDefaultPlayerSpawnLocation(entity);
			return true;
		}

		return false;
	}

	@Override
	public boolean makePortal(Entity entity) {
		int posX = MathHelper.floor_double(entity.posX);
		int posZ = MathHelper.floor_double(entity.posZ);
		int maxPortalSpawnHeight;
		int minSpawnHeight;
		//System.out.println(entity.dimension);
		if (entity.dimension == 0) {
			maxPortalSpawnHeight = 100;
			minSpawnHeight = 64;
		} else {
			maxPortalSpawnHeight = 85;
			minSpawnHeight = 80;
		}
		//System.out.println(maxPortalSpawnHeight + "," + minSpawnHeight);
		for (int x = posX - 127; x < posX + 127; x++) {
			for (int z = posZ - 127; z < posZ + 127; z++) {
				for (int y = maxPortalSpawnHeight; y >= minSpawnHeight; y--) {
					Block block = worldServerInstance.getBlock(x, y, z);
					if (block != Blocks.air) {
						if (canGenerate(worldServerInstance, x, y, z)) {
							new WorldGenWeedWoodPortalTree().generate(worldServerInstance, worldServerInstance.rand, x, y, z);
							entity.setLocationAndAngles(x, y + 2, z, entity.rotationYaw, entity.rotationPitch);
							return true;
						} else {
							for (int yy = y; yy <= maxPortalSpawnHeight; yy++) {
								if (canGenerate(worldServerInstance, x, yy, z)) {
									new WorldGenWeedWoodPortalTree().generate(worldServerInstance, worldServerInstance.rand, x, yy, z);
									entity.setLocationAndAngles(x, yy + 2, z, entity.rotationYaw, entity.rotationPitch);
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean canGenerate(World world, int posX, int posY, int posZ){
		int height = 16;
		int maxRadius = 9;
		for (int xx = posX - maxRadius; xx <= posX + maxRadius; xx++)
			for (int zz = posZ - maxRadius; zz <= posZ + maxRadius; zz++)
				for (int yy = posY + 2; yy < posY + height; yy++) {
					Block block = world.getBlock(xx, yy, zz);
					if ((!world.isAirBlock(xx, yy, zz) && block.isNormalCube()) || block instanceof BlockLeaves)
						return false;
				}
		return true;
	}

	@Override
	public void removeStalePortalLocations(long timer) {
		if (timer % 100L == 0L) {
			Iterator<Long> iterator = destinationCoordinateKeys.iterator();
			while (iterator.hasNext()) {
				Long hashedPortalPos = iterator.next();
				PortalPosition position = (PortalPosition) destinationCoordinateCache.getValueByKey(hashedPortalPos.longValue());

				if (position == null || position.lastUpdateTime < timer - 600L) {
					iterator.remove();
					destinationCoordinateCache.remove(hashedPortalPos.longValue());
				}
			}
		}
	}

	public void setDefaultPlayerSpawnLocation(Entity entity) {
		if (!(entity instanceof EntityPlayerMP))
			return;

		EntityPlayerMP player = (EntityPlayerMP) entity;
		ChunkCoordinates coords = player.getBedLocation(ConfigHandler.DIMENSION_ID);

		if (coords == null) {
			coords = player.getPlayerCoordinates();
			int spawnFuzz = 64;
			int spawnFuzzHalf = spawnFuzz / 2;
			coords.posX += worldServerInstance.rand.nextInt(spawnFuzz) - spawnFuzzHalf;
			coords.posZ += worldServerInstance.rand.nextInt(spawnFuzz) - spawnFuzzHalf;
			coords.posY = worldServerInstance.getTopSolidOrLiquidBlock(coords.posX, coords.posZ) + 1;
			player.setSpawnChunk(coords, true, ConfigHandler.DIMENSION_ID);
		}
	}

}