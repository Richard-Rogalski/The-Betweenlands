package thebetweenlands.world.biomes;

import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.entities.mobs.EntityBlindCaveFish;
import thebetweenlands.entities.mobs.EntityFirefly;
import thebetweenlands.entities.mobs.EntityLeech;
import thebetweenlands.entities.mobs.EntityPeatMummy;
import thebetweenlands.entities.mobs.EntitySludge;
import thebetweenlands.entities.mobs.EntityTarBeast;
import thebetweenlands.entities.mobs.EntityWight;
import thebetweenlands.world.WorldProviderBetweenlands;
import thebetweenlands.world.biomes.base.BiomeGenBaseBetweenlands;
import thebetweenlands.world.biomes.decorators.BiomeDecoratorSludgePlains;
import thebetweenlands.world.biomes.decorators.base.BiomeDecoratorBaseBetweenlands;
import thebetweenlands.world.biomes.feature.FlatLandNoiseFeature;
import thebetweenlands.world.biomes.feature.PatchNoiseFeature;
import thebetweenlands.world.biomes.spawning.spawners.CaveSpawnEntry;
import thebetweenlands.world.biomes.spawning.spawners.SurfaceSpawnEntry;
import thebetweenlands.world.biomes.spawning.spawners.TarSpawnEntry;

/**
 * Created by Bart on 9-10-2015.
 */
public class BiomeSludgePlains extends BiomeGenBaseBetweenlands {

	public BiomeSludgePlains(int biomeID) {
		this(biomeID, new BiomeDecoratorSludgePlains());
	}

	public BiomeSludgePlains(int biomeID, BiomeDecoratorBaseBetweenlands decorator) {
		super(biomeID, decorator);
		this.setFogColor((byte) 10, (byte) 30, (byte) 12);
		setColors(0x314D31, 0x314D31);
		setWeight(20);
		this.setHeightAndVariation(WorldProviderBetweenlands.LAYER_HEIGHT + 8, 2);
		this.setBiomeName("Sludge Plains");
		this.setBlocks(BLBlockRegistry.betweenstone, BLBlockRegistry.swampDirt, BLBlockRegistry.mud, BLBlockRegistry.mud, BLBlockRegistry.betweenlandsBedrock);
		this.setFillerBlockHeight((byte) 1);
		this.addFeature(new FlatLandNoiseFeature())
		.addFeature(new PatchNoiseFeature(0.03125D * 5.75D, 0.03125D * 5.75D, BLBlockRegistry.sludgyDirt))
		.addFeature(new PatchNoiseFeature(0.74D, 0.74D, BLBlockRegistry.swampDirt))
		.addFeature(new PatchNoiseFeature(0.65D, 0.65D, BLBlockRegistry.mud, 1.0D / 1.35D, 1.72D));
		this.waterColorMultiplier = 0x3A2F0B;

		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityFirefly.class, (short) 20).setGroupSize(1, 3));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityBlindCaveFish.class, (short) 30).setGroupSize(3, 5));

		this.blSpawnEntries.add(new TarSpawnEntry(EntityTarBeast.class, (short) 80).setHostile(true).setGroupSize(1, 2).setGroupRadius(15.0D));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityLeech.class, (short) 50).setHostile(true));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntitySludge.class, (short) 60).setHostile(true));
		this.blSpawnEntries.add(new SurfaceSpawnEntry(EntityPeatMummy.class, (short) 10).setHostile(true).setGroupRadius(20.0D));
		this.blSpawnEntries.add(new CaveSpawnEntry(EntityWight.class, (short) 20).setHostile(true).setGroupRadius(30.0D));
	}
}
