package thebetweenlands.entities;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import thebetweenlands.TheBetweenlands;
import thebetweenlands.entities.mobs.EntityAngler;
import thebetweenlands.entities.mobs.EntityBlindCaveFish;
import thebetweenlands.entities.mobs.EntityBloodSnail;
import thebetweenlands.entities.mobs.EntityChiromaw;
import thebetweenlands.entities.mobs.EntityDarkDruid;
import thebetweenlands.entities.mobs.EntityDragonFly;
import thebetweenlands.entities.mobs.EntityDreadfulMummy;
import thebetweenlands.entities.mobs.EntityFirefly;
import thebetweenlands.entities.mobs.EntityFrog;
import thebetweenlands.entities.mobs.EntityGasCloud;
import thebetweenlands.entities.mobs.EntityGecko;
import thebetweenlands.entities.mobs.EntityGiantToad;
import thebetweenlands.entities.mobs.EntityLeech;
import thebetweenlands.entities.mobs.EntityLurker;
import thebetweenlands.entities.mobs.EntityMireSnail;
import thebetweenlands.entities.mobs.EntityMireSnailEgg;
import thebetweenlands.entities.mobs.EntityMummyArm;
import thebetweenlands.entities.mobs.EntityPeatMummy;
import thebetweenlands.entities.mobs.EntityPyrad;
import thebetweenlands.entities.mobs.EntityShallowBreath;
import thebetweenlands.entities.mobs.EntitySiltCrab;
import thebetweenlands.entities.mobs.EntitySludge;
import thebetweenlands.entities.mobs.EntitySludgeBall;
import thebetweenlands.entities.mobs.EntitySporeling;
import thebetweenlands.entities.mobs.EntitySwampHag;
import thebetweenlands.entities.mobs.EntityTarBeast;
import thebetweenlands.entities.mobs.EntityTarminion;
import thebetweenlands.entities.mobs.EntityTermite;
import thebetweenlands.entities.mobs.EntityWight;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBoss;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBossBlockade;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBossProjectile;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBossSpawner;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBossTeleporter;
import thebetweenlands.entities.mobs.boss.fortress.EntityFortressBossTurret;
import thebetweenlands.entities.projectiles.EntityAngryPebble;
import thebetweenlands.entities.projectiles.EntityBLArrow;
import thebetweenlands.entities.projectiles.EntityElixir;
import thebetweenlands.entities.projectiles.EntityPyradFlame;
import thebetweenlands.entities.projectiles.EntitySnailPoisonJet;
import thebetweenlands.entities.projectiles.EntityThrownTarminion;
import thebetweenlands.entities.projectiles.EntityVolatileSoul;
import thebetweenlands.entities.rowboat.EntityWeedwoodRowboat;
import thebetweenlands.items.misc.ItemSpawnEggs;

public class BLEntityRegistry
{
	public static void init() {
		// Entity registrations can be with or without spawn egg for non mob entites eg. projectiles etc.
		registerEntity(0, EntityDarkDruid.class, "darkDruid", 0x000000, 0xFF0000);
		registerEntity(1, EntityAngler.class, "angler", 0x243B0B, 0x00FFFF);
		registerEntity(2, EntitySludge.class, "sludge", 0x3A2F0B, 0x5F4C0B);
		registerEntity(3, EntitySwampHag.class, "swampHag", 0x0B3B0B, 0xDBA901);
		registerEntity(4, EntityTarBeast.class, "tarBeast", 0x000000, 0x2E2E2E, 64, 1, true);
		registerEntity(5, EntityWight.class, "wight", 0xECF8E0, 0x243B0B);
		registerEntity(6, EntityFirefly.class, "firefly", 0xFFB300, 0xFFD000);
		registerEntity(7, EntitySporeling.class, "sporeling", 0x696144, 0xFFFB00, 64, 1, true);
		registerEntity(8, EntityLeech.class, "leech", 0x804E3D, 0x635940);
		registerEntity(9, EntityDragonFly.class, "dragonfly", 0x31B53C, 0x779E3C);
		registerEntity(10, EntityBloodSnail.class, "bloodSnail", 0x8E9456, 0xB3261E);
		registerEntity(11, EntityMireSnail.class, "mireSnail", 0x8E9456, 0xF2FA96);
		registerEntity(12, EntityMireSnailEgg.class, "mireSnailEgg");
		registerEntity(13, EntityAngryPebble.class, "angryPebble");
		registerEntity(14, EntityBLArrow.class, "blArrow", 64, 20, true);
		registerEntity(15, EntitySiltCrab.class, "siltCrab", 0x086A87, 0xB43104);
		registerEntity(16, EntitySnailPoisonJet.class, "snailPoisonJet");
		registerEntity(17, EntityLurker.class, "lurker", 0x283320, 0x827856);
		registerEntity(18, EntityBLItemFrame.class, "itemFrameBL");
		registerEntity(19, EntityGecko.class, "gecko", 0xFF8000, 0x22E0B1, 64, 1, true);
		registerEntity(20, EntityTermite.class, "termite", 0xD9D7A7, 0xD99830);
		registerEntity(21, EntityGiantToad.class, "toad", 0x405C3B, 0x7ABA45);
		//registerEntity(22, EntityMeleeGuardian.class, "meleeGuardian", /*0x283320, 0x827856,*/ 64, 1, true);
		//registerEntity(23, EntityBerserkerGuardian.class, "berserkerGuardian", /*0x283320, 0x827856,*/ 64, 1, true);
		registerEntity(24, EntityBlindCaveFish.class, "blindCaveFish", 0xD0D1C2, 0xECEDDF);
		registerEntity(25, EntityTarminion.class, "tarminion", 0x000000, 0x2E2E2E, 64, 1, true);
		registerEntity(26, EntityThrownTarminion.class, "thrownTarminion", 64, 10, true);
		registerEntity(27, EntityWeedwoodRowboat.class, "weedwoodBoat", 64, 1, true);
		registerEntity(28, EntityPeatMummy.class, "peatMummy", 0x524D3A, 0x69463F, 64, 1, true);
		registerEntity(29, EntityElixir.class, "thrownElixir", 64, 10, true);
		registerEntity(30, EntityRopeNode.class, "ropeNode", 64, 1, true);
		registerEntity(31, EntityDreadfulMummy.class, "dreadfulMummy", 0x000000, 0x591E08, 64, 1, true);
		registerEntity(32, EntityShallowBreath.class, "shallowBreath", /*0x005C3D, 0x2D4231,*/ 10, 1, true);
		registerEntity(33, EntityVolatileSoul.class, "volatileSoul", 64, 1, true);
		registerEntity(34, EntitySludgeBall.class, "sludgeBall", 64, 20, true);
		registerEntity(35, EntitySwordEnergy.class, "swordEnergy", 64, 20, true);
		registerEntity(36, EntityFortressBoss.class, "fortressBoss", 0x000000, 0x00FFFA, 64, 1, true);
		registerEntity(37, EntityFortressBossTurret.class, "fortressBossTurret", 64, 20, false);
		registerEntity(38, EntityFortressBossProjectile.class, "fortressBossProjectile", 64, 5, true);
		registerEntity(39, EntityFortressBossSpawner.class, "fortressBossSpawner", 64, 20, false);
		registerEntity(40, EntityFortressBossBlockade.class, "fortressBossBlockade", 64, 20, false);
		registerEntity(41, EntityChiromaw.class, "chiromaw", 0x3F5A69, 0xA16A77);
		registerEntity(42, EntityPyrad.class, "pyrad", 0x5E4726, 0x2D4231, 64, 3, true);
		registerEntity(43, EntityPyradFlame.class, "flammeBall");
		registerEntity(44, EntityFortressBossTeleporter.class, "fortressBossTeleporter", 64, 5, false);
		registerEntity(45, EntityMummyArm.class, "mummyArm", /*0x804E3D, 0x3D6F80,*/ 64, 20, false);
		registerEntity(46, EntityShockwaveBlock.class, "shockwaveBlock", 64, 20, true);
		registerEntity(47, EntityVolarkite.class, "volarkite", 64, 20, true);
		registerEntity(48, EntityTargetDistraction.class, "targetDistraction", 0, Integer.MAX_VALUE, false);
		registerEntity(49, EntityFrog.class, "frog", 0x559653, 0xC72C2C, 64, 20, true);
		registerEntity(50, EntityGasCloud.class, "gasCloud", 0xFFB300, 0xFFD000);
	}

	private static final void registerEntity(int id, Class<? extends Entity> entityClass, String name, int trackingRange, int trackingFrequency, boolean velocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, name, id, TheBetweenlands.instance, trackingRange, trackingFrequency, velocityUpdates);
	}

	private static final void registerEntity(int id, Class<? extends Entity> entityClass, String name) {
		EntityRegistry.registerModEntity(entityClass, name, id, TheBetweenlands.instance, 64, 3, true);
	}

	private static final void registerEntity(int id, Class<? extends EntityLiving> entityClass, String name, int eggBackgroundColor, int eggForegroundColor, int trackingRange, int trackingFrequency, boolean velocityUpdates) {
		registerEntity(id, entityClass, name, trackingRange, trackingFrequency, velocityUpdates);
		ItemSpawnEggs.registerSpawnEgg(entityClass, name, id, eggBackgroundColor, eggForegroundColor);
	}

	private static final void registerEntity(int id, Class<? extends EntityLiving> entityClass, String name, int eggBackgroundColor, int eggForegroundColor) {
		registerEntity(id, entityClass, name);
		ItemSpawnEggs.registerSpawnEgg(entityClass, name, id, eggBackgroundColor, eggForegroundColor);
	}
}
