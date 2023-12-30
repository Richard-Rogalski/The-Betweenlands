package thebetweenlands.proxy;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;

import com.google.common.base.Throwables;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import thebetweenlands.TheBetweenlands;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.blocks.container.BlockWeedWoodChest;
import thebetweenlands.client.audio.SuperbSoundRegistry;
import thebetweenlands.client.audio.ambience.AmbienceRegistry;
import thebetweenlands.client.event.AmbienceSoundPlayHandler;
import thebetweenlands.client.event.BLMusicHandler;
import thebetweenlands.client.event.CorrosionTextureStitchHandler;
import thebetweenlands.client.gui.GuiLorePage;
import thebetweenlands.client.gui.GuiOverlay;
import thebetweenlands.client.input.KeyBindingsBL;
import thebetweenlands.client.input.WeedwoodRowboatHandler;
import thebetweenlands.client.model.block.crops.ModelCropFungus1;
import thebetweenlands.client.model.block.crops.ModelCropFungus2;
import thebetweenlands.client.model.block.crops.ModelCropFungus3;
import thebetweenlands.client.model.block.crops.ModelCropFungus4;
import thebetweenlands.client.model.block.crops.ModelCropFungus5;
import thebetweenlands.client.model.item.ModelExplorersHat;
import thebetweenlands.client.render.block.BlockBLHopperRenderer;
import thebetweenlands.client.render.block.BlockBLLeverRenderer;
import thebetweenlands.client.render.block.BlockDoorRenderer;
import thebetweenlands.client.render.block.BlockDoublePlantRenderer;
import thebetweenlands.client.render.block.BlockFarmedDirtRenderer;
import thebetweenlands.client.render.block.BlockHollowLogRenderer;
import thebetweenlands.client.render.block.BlockModelPlantRenderer;
import thebetweenlands.client.render.block.BlockMossBedRenderer;
import thebetweenlands.client.render.block.BlockMudFlowerPotRenderer;
import thebetweenlands.client.render.block.BlockRootRenderer;
import thebetweenlands.client.render.block.BlockRubberLogRenderer;
import thebetweenlands.client.render.block.BlockRubberTapRenderer;
import thebetweenlands.client.render.block.BlockSlopeRenderer;
import thebetweenlands.client.render.block.BlockStalactiteRenderer;
import thebetweenlands.client.render.block.BlockSwampReedRenderer;
import thebetweenlands.client.render.block.BlockSwampWaterRenderer;
import thebetweenlands.client.render.block.BlockWalkwayRenderer;
import thebetweenlands.client.render.block.BlockWeedWoodBushRenderer;
import thebetweenlands.client.render.block.crops.BlockBLGenericCropRenderer;
import thebetweenlands.client.render.entity.RenderAngler;
import thebetweenlands.client.render.entity.RenderBLItemFrame;
import thebetweenlands.client.render.entity.RenderBerserkerGuardian;
import thebetweenlands.client.render.entity.RenderBlindCaveFish;
import thebetweenlands.client.render.entity.RenderBloodSnail;
import thebetweenlands.client.render.entity.RenderChiromaw;
import thebetweenlands.client.render.entity.RenderDarkDruid;
import thebetweenlands.client.render.entity.RenderDragonFly;
import thebetweenlands.client.render.entity.RenderDreadfulMummy;
import thebetweenlands.client.render.entity.RenderEntityVolarkite;
import thebetweenlands.client.render.entity.RenderFirefly;
import thebetweenlands.client.render.entity.RenderFrog;
import thebetweenlands.client.render.entity.RenderGasCloud;
import thebetweenlands.client.render.entity.RenderGecko;
import thebetweenlands.client.render.entity.RenderGiantToad;
import thebetweenlands.client.render.entity.RenderLeech;
import thebetweenlands.client.render.entity.RenderLurker;
import thebetweenlands.client.render.entity.RenderMeleeGuardian;
import thebetweenlands.client.render.entity.RenderMireSnail;
import thebetweenlands.client.render.entity.RenderMireSnailEgg;
import thebetweenlands.client.render.entity.RenderMummyArm;
import thebetweenlands.client.render.entity.RenderPeatMummy;
import thebetweenlands.client.render.entity.RenderPyrad;
import thebetweenlands.client.render.entity.RenderRopeNode;
import thebetweenlands.client.render.entity.RenderShockwaveBlock;
import thebetweenlands.client.render.entity.RenderSiltCrab;
import thebetweenlands.client.render.entity.RenderSludge;
import thebetweenlands.client.render.entity.RenderSludgeBall;
import thebetweenlands.client.render.entity.RenderSporeling;
import thebetweenlands.client.render.entity.RenderSwampHag;
import thebetweenlands.client.render.entity.RenderSwordEnergy;
import thebetweenlands.client.render.entity.RenderTarBeast;
import thebetweenlands.client.render.entity.RenderTarminion;
import thebetweenlands.client.render.entity.RenderTermite;
import thebetweenlands.client.render.entity.RenderVolatileSoul;
import thebetweenlands.client.render.entity.RenderWeedwoodRowboat;
import thebetweenlands.client.render.entity.RenderWight;
import thebetweenlands.client.render.entity.boss.fortress.RenderFortressBoss;
import thebetweenlands.client.render.entity.boss.fortress.RenderFortressBossBlockade;
import thebetweenlands.client.render.entity.boss.fortress.RenderFortressBossProjectile;
import thebetweenlands.client.render.entity.boss.fortress.RenderFortressBossSpawner;
import thebetweenlands.client.render.entity.boss.fortress.RenderFortressBossTeleporter;
import thebetweenlands.client.render.entity.boss.fortress.RenderFortressBossTurret;
import thebetweenlands.client.render.entity.projectile.RenderAngryPebble;
import thebetweenlands.client.render.entity.projectile.RenderBLArrow;
import thebetweenlands.client.render.entity.projectile.RenderElixir;
import thebetweenlands.client.render.entity.projectile.RenderPyradFlame;
import thebetweenlands.client.render.entity.projectile.RenderSnailPoisonJet;
import thebetweenlands.client.render.item.ItemAlembicRenderer;
import thebetweenlands.client.render.item.ItemAnimatorRenderer;
import thebetweenlands.client.render.item.ItemCompostBinRenderer;
import thebetweenlands.client.render.item.ItemDruidAltarRenderer;
import thebetweenlands.client.render.item.ItemGeckoCageRenderer;
import thebetweenlands.client.render.item.ItemInfuserRenderer;
import thebetweenlands.client.render.item.ItemItemCageRenderer;
import thebetweenlands.client.render.item.ItemItemShelfRenderer;
import thebetweenlands.client.render.item.ItemLootPot1Renderer;
import thebetweenlands.client.render.item.ItemLootPot2Renderer;
import thebetweenlands.client.render.item.ItemLootPot3Renderer;
import thebetweenlands.client.render.item.ItemPestleAndMortarRenderer;
import thebetweenlands.client.render.item.ItemPurifierRenderer;
import thebetweenlands.client.render.item.ItemRepellerRenderer;
import thebetweenlands.client.render.item.ItemSpikeTrapRenderer;
import thebetweenlands.client.render.item.ItemTarLootPot1Renderer;
import thebetweenlands.client.render.item.ItemTarLootPot2Renderer;
import thebetweenlands.client.render.item.ItemTarLootPot3Renderer;
import thebetweenlands.client.render.item.ItemTarminionRenderer;
import thebetweenlands.client.render.item.ItemVolarKiteRenderer;
import thebetweenlands.client.render.item.ItemWeedWoodChestRenderer;
import thebetweenlands.client.render.shader.ShaderHelper;
import thebetweenlands.client.render.tileentity.TileEntityAlembicRenderer;
import thebetweenlands.client.render.tileentity.TileEntityAnimatorRenderer;
import thebetweenlands.client.render.tileentity.TileEntityAspectVialRenderer;
import thebetweenlands.client.render.tileentity.TileEntityAspectrusCropRenderer;
import thebetweenlands.client.render.tileentity.TileEntityBLSignRenderer;
import thebetweenlands.client.render.tileentity.TileEntityBLSpawnerRenderer;
import thebetweenlands.client.render.tileentity.TileEntityBLWorkbenchRenderer;
import thebetweenlands.client.render.tileentity.TileEntityCompostBinRenderer;
import thebetweenlands.client.render.tileentity.TileEntityDruidAltarRenderer;
import thebetweenlands.client.render.tileentity.TileEntityGeckoCageRenderer;
import thebetweenlands.client.render.tileentity.TileEntityInfuserRenderer;
import thebetweenlands.client.render.tileentity.TileEntityItemCageRenderer;
import thebetweenlands.client.render.tileentity.TileEntityItemShelfRenderer;
import thebetweenlands.client.render.tileentity.TileEntityLootPot1Renderer;
import thebetweenlands.client.render.tileentity.TileEntityLootPot2Renderer;
import thebetweenlands.client.render.tileentity.TileEntityLootPot3Renderer;
import thebetweenlands.client.render.tileentity.TileEntityPestleAndMortarRenderer;
import thebetweenlands.client.render.tileentity.TileEntityPossessedBlockRenderer;
import thebetweenlands.client.render.tileentity.TileEntityPurifierRenderer;
import thebetweenlands.client.render.tileentity.TileEntityRepellerRenderer;
import thebetweenlands.client.render.tileentity.TileEntitySpikeTrapRenderer;
import thebetweenlands.client.render.tileentity.TileEntityTarBeastSpawnerRenderer;
import thebetweenlands.client.render.tileentity.TileEntityTarLootPot1Renderer;
import thebetweenlands.client.render.tileentity.TileEntityTarLootPot2Renderer;
import thebetweenlands.client.render.tileentity.TileEntityTarLootPot3Renderer;
import thebetweenlands.client.render.tileentity.TileEntityWeedWoodChestRenderer;
import thebetweenlands.client.render.tileentity.TileEntityWispRenderer;
import thebetweenlands.entities.EntityBLItemFrame;
import thebetweenlands.entities.EntityRopeNode;
import thebetweenlands.entities.EntityShockwaveBlock;
import thebetweenlands.entities.EntitySwordEnergy;
import thebetweenlands.entities.EntityVolarkite;
import thebetweenlands.entities.mobs.EntityAngler;
import thebetweenlands.entities.mobs.EntityBerserkerGuardian;
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
import thebetweenlands.entities.mobs.EntityMeleeGuardian;
import thebetweenlands.entities.mobs.EntityMireSnail;
import thebetweenlands.entities.mobs.EntityMireSnailEgg;
import thebetweenlands.entities.mobs.EntityMummyArm;
import thebetweenlands.entities.mobs.EntityPeatMummy;
import thebetweenlands.entities.mobs.EntityPyrad;
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
import thebetweenlands.entities.particles.EntityThemFX;
import thebetweenlands.entities.particles.EntityWispFX;
import thebetweenlands.entities.projectiles.EntityAngryPebble;
import thebetweenlands.entities.projectiles.EntityBLArrow;
import thebetweenlands.entities.projectiles.EntityElixir;
import thebetweenlands.entities.projectiles.EntityPyradFlame;
import thebetweenlands.entities.projectiles.EntitySnailPoisonJet;
import thebetweenlands.entities.projectiles.EntityThrownTarminion;
import thebetweenlands.entities.projectiles.EntityVolatileSoul;
import thebetweenlands.entities.rowboat.EntityWeedwoodRowboat;
import thebetweenlands.event.debugging.DebugHandlerClient;
import thebetweenlands.event.debugging.DebugHandlerCommon;
import thebetweenlands.event.elixirs.ElixirClientHandler;
import thebetweenlands.event.entity.AttackDamageHandler;
import thebetweenlands.event.entity.PowerRingHandler;
import thebetweenlands.event.gui.GuiOpenedHandler;
import thebetweenlands.event.input.KeyInputHandler;
import thebetweenlands.event.input.RingInputHandler;
import thebetweenlands.event.input.radialmenu.RadialMenuHandler;
import thebetweenlands.event.item.ItemNBTExclusionHandler;
import thebetweenlands.event.item.ItemTooltipHandler;
import thebetweenlands.event.render.AspectItemOverlayHandler;
import thebetweenlands.event.render.BrightnessHandler;
import thebetweenlands.event.render.CameraPositionHandler;
import thebetweenlands.event.render.DecayRenderHandler;
import thebetweenlands.event.render.FogHandler;
import thebetweenlands.event.render.FovHandler;
import thebetweenlands.event.render.GLUProjectionHandler;
import thebetweenlands.event.render.ItemTextureTicker;
import thebetweenlands.event.render.OverlayHandler;
import thebetweenlands.event.render.ShaderHandler;
import thebetweenlands.event.render.WorldRenderHandler;
import thebetweenlands.event.world.ThemHandler;
import thebetweenlands.inventory.container.ContainerLurkerSkinPouch;
import thebetweenlands.inventory.gui.GuiAnimator;
import thebetweenlands.inventory.gui.GuiBLCrafting;
import thebetweenlands.inventory.gui.GuiBLDualFurnace;
import thebetweenlands.inventory.gui.GuiBLFurnace;
import thebetweenlands.inventory.gui.GuiDruidAltar;
import thebetweenlands.inventory.gui.GuiLurkerSkinPouch;
import thebetweenlands.inventory.gui.GuiPestleAndMortar;
import thebetweenlands.inventory.gui.GuiPouchNaming;
import thebetweenlands.inventory.gui.GuiPurifier;
import thebetweenlands.inventory.gui.GuiWeedWoodChest;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.equipment.ItemBasicInventory;
import thebetweenlands.items.equipment.ItemLurkerSkinPouch;
import thebetweenlands.lib.ModInfo;
import thebetweenlands.manual.GuiManualBase;
import thebetweenlands.manual.GuiManualHerblore;
import thebetweenlands.manual.GuideBookEntryRegistry;
import thebetweenlands.manual.HLEntryRegistry;
import thebetweenlands.network.handlers.ClientPacketHandler;
import thebetweenlands.tileentities.TileEntityAlembic;
import thebetweenlands.tileentities.TileEntityAnimator;
import thebetweenlands.tileentities.TileEntityAspectVial;
import thebetweenlands.tileentities.TileEntityAspectrusCrop;
import thebetweenlands.tileentities.TileEntityBLCraftingTable;
import thebetweenlands.tileentities.TileEntityBLDualFurnace;
import thebetweenlands.tileentities.TileEntityBLFurnace;
import thebetweenlands.tileentities.TileEntityBLSign;
import thebetweenlands.tileentities.TileEntityCompostBin;
import thebetweenlands.tileentities.TileEntityDruidAltar;
import thebetweenlands.tileentities.TileEntityGeckoCage;
import thebetweenlands.tileentities.TileEntityInfuser;
import thebetweenlands.tileentities.TileEntityItemCage;
import thebetweenlands.tileentities.TileEntityItemShelf;
import thebetweenlands.tileentities.TileEntityLootPot1;
import thebetweenlands.tileentities.TileEntityLootPot2;
import thebetweenlands.tileentities.TileEntityLootPot3;
import thebetweenlands.tileentities.TileEntityPestleAndMortar;
import thebetweenlands.tileentities.TileEntityPossessedBlock;
import thebetweenlands.tileentities.TileEntityPurifier;
import thebetweenlands.tileentities.TileEntityRepeller;
import thebetweenlands.tileentities.TileEntitySpikeTrap;
import thebetweenlands.tileentities.TileEntityTarBeastSpawner;
import thebetweenlands.tileentities.TileEntityTarLootPot1;
import thebetweenlands.tileentities.TileEntityTarLootPot2;
import thebetweenlands.tileentities.TileEntityTarLootPot3;
import thebetweenlands.tileentities.TileEntityWeedWoodChest;
import thebetweenlands.tileentities.TileEntityWisp;
import thebetweenlands.tileentities.spawner.TileEntityBLSpawner;
import thebetweenlands.utils.TimerDebug;
import thebetweenlands.utils.confighandler.ConfigHandler;

public class ClientProxy extends CommonProxy {
	public enum BlockRenderIDs {
		DOUBLE_PLANTS,
		RUBBER_LOG,
		WEEDWOOD_BUSH,
		SWAMP_WATER,
		SWAMP_REED,
		STALACTITE,
		ROOT,
		MODEL_PLANT,
		GOLDEN_CLUB,
		BOG_BEAN,
		MARSH_MARIGOLD,
		WATER_WEEDS,
		DOOR,
		WALKWAY,
		RUBBER_TAP,
		LEVER,
		MUDFLOWERPOT,
		SLOPE,
		HOLLOW_LOG,
		MOSS_BED,
		CROP,
		FARMED_DIRT,
		HOPPER;

		private final int ID;

		BlockRenderIDs() {
			ID = RenderingRegistry.getNextAvailableRenderId();
		}

		public int id() {
			return ID;
		}
	}

	private static final ResourceLocation PLAYER_CORRUPTION_TEXTURE = new ResourceLocation(ModInfo.ID + ":textures/player/playerCorruption.png");

	private static final ModelExplorersHat EXPLORERS_HAT_MODEL = new ModelExplorersHat();

	private BufferedImage playerCorruptionImg = null;

	public static RenderDragonFly dragonFlyRenderer;

	public static TimerDebug debugTimer;

	@Override
	public void init() {
		super.init();
		// Register packet handlers
		try {
			TheBetweenlands.sidedPacketHandler.registerPacketHandler(ClientPacketHandler.class, Side.CLIENT);
			TheBetweenlands.sidedPacketHandler.registerPacketHandler(TileEntityAnimator.class, Side.CLIENT);
			TheBetweenlands.sidedPacketHandler.registerPacketHandler(TileEntityDruidAltar.class, Side.CLIENT);
			TheBetweenlands.sidedPacketHandler.registerPacketHandler(AttackDamageHandler.class, Side.CLIENT);
			TheBetweenlands.sidedPacketHandler.registerPacketHandler(PowerRingHandler.class, Side.CLIENT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Key bindings
		KeyBindingsBL.init();

		// Mob Entity Renderer
		RenderingRegistry.registerEntityRenderingHandler(EntityDarkDruid.class, new RenderDarkDruid());
		RenderingRegistry.registerEntityRenderingHandler(EntityAngler.class, new RenderAngler());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlindCaveFish.class, new RenderBlindCaveFish());
		RenderingRegistry.registerEntityRenderingHandler(EntitySludge.class, new RenderSludge());
		RenderingRegistry.registerEntityRenderingHandler(EntitySwampHag.class, new RenderSwampHag());
		RenderingRegistry.registerEntityRenderingHandler(EntityTarBeast.class, new RenderTarBeast());
		RenderingRegistry.registerEntityRenderingHandler(EntityWight.class, new RenderWight());
		RenderingRegistry.registerEntityRenderingHandler(EntityFirefly.class, new RenderFirefly());
		RenderingRegistry.registerEntityRenderingHandler(EntityLeech.class, new RenderLeech());
		RenderingRegistry.registerEntityRenderingHandler(EntitySporeling.class, new RenderSporeling());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonFly.class, dragonFlyRenderer = new RenderDragonFly());
		RenderingRegistry.registerEntityRenderingHandler(EntityBloodSnail.class, new RenderBloodSnail());
		RenderingRegistry.registerEntityRenderingHandler(EntityMireSnail.class, new RenderMireSnail());
		RenderingRegistry.registerEntityRenderingHandler(EntityMireSnailEgg.class, new RenderMireSnailEgg());
		RenderingRegistry.registerEntityRenderingHandler(EntityAngryPebble.class, new RenderAngryPebble());
		RenderingRegistry.registerEntityRenderingHandler(EntityBLArrow.class, new RenderBLArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntitySiltCrab.class, new RenderSiltCrab());
		RenderingRegistry.registerEntityRenderingHandler(EntitySnailPoisonJet.class, new RenderSnailPoisonJet());
		RenderingRegistry.registerEntityRenderingHandler(EntityLurker.class, new RenderLurker());
		RenderingRegistry.registerEntityRenderingHandler(EntityGecko.class, new RenderGecko());
		RenderingRegistry.registerEntityRenderingHandler(EntityTermite.class, new RenderTermite());
		RenderingRegistry.registerEntityRenderingHandler(EntityGiantToad.class, new RenderGiantToad());
		RenderingRegistry.registerEntityRenderingHandler(EntityMeleeGuardian.class, new RenderMeleeGuardian());
		RenderingRegistry.registerEntityRenderingHandler(EntityBerserkerGuardian.class, new RenderBerserkerGuardian());
		RenderingRegistry.registerEntityRenderingHandler(EntityBLItemFrame.class, new RenderBLItemFrame());
		RenderingRegistry.registerEntityRenderingHandler(EntityTarminion.class, new RenderTarminion());
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownTarminion.class, new ItemTarminionRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityWeedwoodRowboat.class, new RenderWeedwoodRowboat());
		RenderingRegistry.registerEntityRenderingHandler(EntityPeatMummy.class, new RenderPeatMummy());
		RenderingRegistry.registerEntityRenderingHandler(EntityElixir.class, new RenderElixir());
		RenderingRegistry.registerEntityRenderingHandler(EntityRopeNode.class, new RenderRopeNode());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreadfulMummy.class, new RenderDreadfulMummy());
		RenderingRegistry.registerEntityRenderingHandler(EntityVolatileSoul.class, new RenderVolatileSoul());
		RenderingRegistry.registerEntityRenderingHandler(EntitySludgeBall.class, new RenderSludgeBall());
		RenderingRegistry.registerEntityRenderingHandler(EntitySwordEnergy.class, new RenderSwordEnergy());
		RenderingRegistry.registerEntityRenderingHandler(EntityFortressBoss.class, new RenderFortressBoss());
		RenderingRegistry.registerEntityRenderingHandler(EntityFortressBossTurret.class, new RenderFortressBossTurret());
		RenderingRegistry.registerEntityRenderingHandler(EntityFortressBossProjectile.class, new RenderFortressBossProjectile());	
		RenderingRegistry.registerEntityRenderingHandler(EntityFortressBossSpawner.class, new RenderFortressBossSpawner());	
		RenderingRegistry.registerEntityRenderingHandler(EntityFortressBossBlockade.class, new RenderFortressBossBlockade());
		RenderingRegistry.registerEntityRenderingHandler(EntityFortressBossTeleporter.class, new RenderFortressBossTeleporter());
		RenderingRegistry.registerEntityRenderingHandler(EntityChiromaw.class, new RenderChiromaw());
		RenderingRegistry.registerEntityRenderingHandler(EntityPyrad.class, new RenderPyrad());
		RenderingRegistry.registerEntityRenderingHandler(EntityPyradFlame.class, new RenderPyradFlame(1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMummyArm.class, new RenderMummyArm());
		RenderingRegistry.registerEntityRenderingHandler(EntityShockwaveBlock.class, new RenderShockwaveBlock());
		RenderingRegistry.registerEntityRenderingHandler(EntityVolarkite.class, new RenderEntityVolarkite());
		RenderingRegistry.registerEntityRenderingHandler(EntityFrog.class, new RenderFrog());
		RenderingRegistry.registerEntityRenderingHandler(EntityGasCloud.class, new RenderGasCloud());
		
		// Tile Entity Renderer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDruidAltar.class, new TileEntityDruidAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWeedWoodChest.class, new TileEntityWeedWoodChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBLCraftingTable.class, new TileEntityBLWorkbenchRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWisp.class, new TileEntityWispRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimator.class, new TileEntityAnimatorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPurifier.class, new TileEntityPurifierRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompostBin.class, new TileEntityCompostBinRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlembic.class, new TileEntityAlembicRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfuser.class, new TileEntityInfuserRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPestleAndMortar.class, new TileEntityPestleAndMortarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLootPot1.class, new TileEntityLootPot1Renderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLootPot2.class, new TileEntityLootPot2Renderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLootPot3.class, new TileEntityLootPot3Renderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBLSpawner.class, new TileEntityBLSpawnerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTarBeastSpawner.class, new TileEntityTarBeastSpawnerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTarLootPot1.class, new TileEntityTarLootPot1Renderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTarLootPot2.class, new TileEntityTarLootPot2Renderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTarLootPot3.class, new TileEntityTarLootPot3Renderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemShelf.class, new TileEntityItemShelfRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGeckoCage.class, new TileEntityGeckoCageRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAspectrusCrop.class, new TileEntityAspectrusCropRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBLSign.class, new TileEntityBLSignRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRepeller.class, new TileEntityRepellerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPossessedBlock.class, new TileEntityPossessedBlockRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpikeTrap.class, new TileEntitySpikeTrapRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemCage.class, new TileEntityItemCageRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAspectVial.class, new TileEntityAspectVialRenderer());
		
		// Item Entity Renderer
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.druidAltar), new ItemDruidAltarRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.animator), new ItemAnimatorRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.weedwoodChest), new ItemWeedWoodChestRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.purifier), new ItemPurifierRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.compostBin), new ItemCompostBinRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.alembic), new ItemAlembicRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.infuser), new ItemInfuserRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.pestleAndMortar), new ItemPestleAndMortarRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.lootPot1), new ItemLootPot1Renderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.lootPot2), new ItemLootPot2Renderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.lootPot3), new ItemLootPot3Renderer());
		MinecraftForgeClient.registerItemRenderer(BLItemRegistry.tarminion, new ItemTarminionRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.tarLootPot1), new ItemTarLootPot1Renderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.tarLootPot2), new ItemTarLootPot2Renderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.tarLootPot3), new ItemTarLootPot3Renderer());
		MinecraftForgeClient.registerItemRenderer(BLItemRegistry.volarkite, new ItemVolarKiteRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.geckoCage), new ItemGeckoCageRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.repeller), new ItemRepellerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.itemCage), new ItemItemCageRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.spikeTrap), new ItemSpikeTrapRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BLBlockRegistry.itemShelf), new ItemItemShelfRenderer());

		//Register custom item renderer for aspect overlays
		for(Item item : BLItemRegistry.ITEMS) {
			if(MinecraftForgeClient.getItemRenderer(new ItemStack(item), ItemRenderType.INVENTORY) == null) {
				this.registerItemRenderer(item);
			}
		}
		for(Block block : BLBlockRegistry.BLOCKS) {
			Item blockItem = Item.getItemFromBlock(block);
			if(MinecraftForgeClient.getItemRenderer(new ItemStack(blockItem), ItemRenderType.INVENTORY) == null) {
				this.registerItemRenderer(blockItem);
			}
		}

		// Block Renderer
		RenderingRegistry.registerBlockHandler(new BlockDoublePlantRenderer());
		RenderingRegistry.registerBlockHandler(new BlockRubberLogRenderer());
		RenderingRegistry.registerBlockHandler(new BlockWeedWoodBushRenderer());
		RenderingRegistry.registerBlockHandler(new BlockSwampWaterRenderer());
		RenderingRegistry.registerBlockHandler(new BlockSwampReedRenderer());
		RenderingRegistry.registerBlockHandler(new BlockStalactiteRenderer());
		RenderingRegistry.registerBlockHandler(new BlockRootRenderer());
		RenderingRegistry.registerBlockHandler(new BlockModelPlantRenderer());
		RenderingRegistry.registerBlockHandler(new BlockDoorRenderer());
		RenderingRegistry.registerBlockHandler(new BlockWalkwayRenderer());
		RenderingRegistry.registerBlockHandler(new BlockRubberTapRenderer());
		RenderingRegistry.registerBlockHandler(new BlockBLLeverRenderer());
		RenderingRegistry.registerBlockHandler(new BlockMudFlowerPotRenderer());
		RenderingRegistry.registerBlockHandler(new BlockMossBedRenderer());
		RenderingRegistry.registerBlockHandler(new BlockSlopeRenderer());
		RenderingRegistry.registerBlockHandler(new BlockHollowLogRenderer());
		RenderingRegistry.registerBlockHandler(new BlockBLGenericCropRenderer());
		RenderingRegistry.registerBlockHandler(new BlockFarmedDirtRenderer());
		RenderingRegistry.registerBlockHandler(new BlockBLHopperRenderer());

		// Events
		MinecraftForge.EVENT_BUS.register(GuiOverlay.INSTANCE);
		FMLCommonHandler.instance().bus().register(GuiOverlay.INSTANCE);
		AmbienceSoundPlayHandler ambientHandler = new AmbienceSoundPlayHandler();
		FMLCommonHandler.instance().bus().register(ambientHandler);
		MinecraftForge.EVENT_BUS.register(ambientHandler);
		FMLCommonHandler.instance().bus().register(new BLMusicHandler());
		FMLCommonHandler.instance().bus().register(BrightnessHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(FogHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(FogHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ShaderHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ShaderHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(WorldRenderHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ThemHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(new CorrosionTextureStitchHandler());
		FMLCommonHandler.instance().bus().register(ItemTooltipHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ItemTooltipHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(GLUProjectionHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(OverlayHandler.INSTANCE);
		WeedwoodRowboatHandler.INSTANCE.init();
		FMLCommonHandler.instance().bus().register(CameraPositionHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ItemTextureTicker.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ElixirClientHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ElixirClientHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(FovHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(DecayRenderHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(AspectItemOverlayHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ItemNBTExclusionHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ItemNBTExclusionHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(RadialMenuHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(RadialMenuHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(GuiOpenedHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(RingInputHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(KeyInputHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(BLItemRegistry.amulet);
		MinecraftForge.EVENT_BUS.register(BLItemRegistry.lurkerSkinPouch);

		// Crop renderers
		BLBlockRegistry.fungusCrop.setCropModels(
				new ModelBase[]{
						new ModelCropFungus1(), 
						new ModelCropFungus2(), 
						new ModelCropFungus3(), 
						new ModelCropFungus4(), 
						new ModelCropFungus5()}, 
				new int[] {
						32, 32,
						32, 32,
						64, 64,
						64, 64,
						64, 64
				});

		replaceSoundRegistry();

		//Ambience
		AmbienceRegistry.register();

		//Add listener to reload shader resources
		IResourceManager resourceMgr = Minecraft.getMinecraft().getResourceManager();
		if(resourceMgr instanceof IReloadableResourceManager) {
			((IReloadableResourceManager)resourceMgr).registerReloadListener(ShaderHelper.INSTANCE);
		}

		if (ConfigHandler.DEBUG) {
			FMLCommonHandler.instance().bus().register(DebugHandlerClient.INSTANCE);
			MinecraftForge.EVENT_BUS.register(DebugHandlerClient.INSTANCE);
			Field tessellatorInstanceField = ReflectionHelper.findField(Tessellator.class, "instance", "field_78398_a", "a");
			unfinalize(tessellatorInstanceField);
			ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), debugTimer = new TimerDebug(20), "timer", "field_71428_T", "Q");
		}
	}

	private void replaceSoundRegistry() {
		SoundHandler mcSoundHandler = Minecraft.getMinecraft().getSoundHandler();
		Field sndRegistry = ReflectionHelper.findField(SoundHandler.class, "sndRegistry", "field_147697_e", "e");
		unfinalize(sndRegistry);
		try {
			sndRegistry.set(mcSoundHandler, new SuperbSoundRegistry());
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}

	private void unfinalize(Field field) {
		try {
			ReflectionHelper.findField(Field.class, "modifiers").setInt(field, field.getModifiers() & ~Modifier.FINAL);
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}

	@Override
	public void postInit() {
		//Font
		pixelLove = new FontRenderer(Minecraft.getMinecraft().gameSettings, new ResourceLocation("thebetweenlands:textures/gui/manual/fontAtlas.png"), Minecraft.getMinecraft().renderEngine, false);
		if (Minecraft.getMinecraft().gameSettings.language != null) {
			pixelLove.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());
		}
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(pixelLove);

		// Init manual
		GuideBookEntryRegistry.init();
		HLEntryRegistry.init();
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case GUI_DRUID_ALTAR: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityDruidAltar) {
				return new GuiDruidAltar(player.inventory, (TileEntityDruidAltar) tileentity);
			}
			break;
		}
		case GUI_WEEDWOOD_CRAFT: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityBLCraftingTable) {
				return new GuiBLCrafting(player.inventory, (TileEntityBLCraftingTable) tileentity);
			}
			break;
		}
		case GUI_WEEDWOOD_CHEST:
			IInventory inventory = BlockWeedWoodChest.getInventory(world, x, y, z);
			return new GuiWeedWoodChest(player.inventory, inventory);
		case GUI_BL_FURNACE: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityBLFurnace) {
				return new GuiBLFurnace(player.inventory, (TileEntityBLFurnace) tileentity);
			}
			break;
		}
		case GUI_BL_DUAL_FURNACE: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityBLDualFurnace) {
				return new GuiBLDualFurnace(player.inventory, (TileEntityBLDualFurnace) tileentity);
			}
			break;
		}
		case GUI_ANIMATOR: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityAnimator) {
				return new GuiAnimator(player, (TileEntityAnimator) tileentity);
			}
			break;
		}
		case GUI_PURIFIER: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityPurifier) {
				return new GuiPurifier(player.inventory, (TileEntityPurifier) tileentity);
			}
			break;
		}
		case GUI_PESTLE_AND_MORTAR: {
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityPestleAndMortar) {
				return new GuiPestleAndMortar(player.inventory, (TileEntityPestleAndMortar) tileentity);
			}
			break;
		}
		case GUI_MANUAL:
			return new GuiManualBase(player);
		case GUI_HL:
			return new GuiManualHerblore(player);
		case GUI_LORE:
			return new GuiLorePage(player.getCurrentEquippedItem());
		case GUI_LURKER_POUCH:
			return new GuiLurkerSkinPouch((ContainerLurkerSkinPouch) new ContainerLurkerSkinPouch(player, player.inventory, new ItemBasicInventory(player.getHeldItem(), 9 + (x * 9), StatCollector.translateToLocal("container.lurkerSkinPouch"))));
		case GUI_LURKER_POUCH_KEYBIND:
			ItemStack pouch = ItemLurkerSkinPouch.getFirstPouch(player);
			if(pouch != null) {
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				ContainerLurkerSkinPouch container = new ContainerLurkerSkinPouch(player, player.inventory, new ItemBasicInventory(pouch, 9 + (x * 9), StatCollector.translateToLocal("container.lurkerSkinPouch")));
				return new GuiLurkerSkinPouch(container);
			}
			break;
		case GUI_LURKER_POUCH_NAMING:
			return new GuiPouchNaming(player);
		}
		return null;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public void updateWispParticles(TileEntityWisp te) {
		Iterator<Object> i = te.particleList.iterator();
		while (i.hasNext()) {
			if (((EntityWispFX) i.next()).isDead) {
				i.remove();
			}
		}
		for (Object particle : te.particleList) {
			((EntityWispFX) particle).onUpdate();
		}
	}

	@Override
	public void spawnThem() {
		if (Minecraft.getMinecraft().thePlayer.dimension != ConfigHandler.DIMENSION_ID) {
			return;
		}
		if (FogHandler.INSTANCE.hasDenseFog() && FogHandler.INSTANCE.getCurrentFogEnd() < 80.0f) {
			int probability = Math.max((int) FogHandler.INSTANCE.getCurrentFogEnd() / 2 + 16, 10);
			if (Minecraft.getMinecraft().theWorld.rand.nextInt(probability) == 0) {
				double xOff = Minecraft.getMinecraft().theWorld.rand.nextInt(50) - 25;
				double zOff = Minecraft.getMinecraft().theWorld.rand.nextInt(50) - 25;
				double sx = Minecraft.getMinecraft().renderViewEntity.posX + xOff;
				double sz = Minecraft.getMinecraft().renderViewEntity.posZ + zOff;
				double sy = Minecraft.getMinecraft().theWorld.getHeightValue((int) sx, (int) sz) + 1.0f + Minecraft.getMinecraft().theWorld.rand.nextFloat() * 2.5f;
				Minecraft.getMinecraft().effectRenderer.addEffect(new EntityThemFX(Minecraft.getMinecraft().theWorld, sx, sy, sz));
			}
		}
	}

	@Override
	public void playPortalSounds(Entity entity, int timer) {
		if (entity instanceof EntityPlayerSP) {
			EntityPlayerSP player = (EntityPlayerSP) entity;
			if (timer < 120) {
				player.closeScreen();
				if (timer == 119) {
					player.playSound("thebetweenlands:portalTrigger", 1.0F, 0.8F);
				}
				if (timer == 2) {
					player.playSound("thebetweenlands:portalTravel", 1.25f, 0.8f);
				}
			}
		}
	}

	@Override
	public DebugHandlerCommon getDebugHandler() {
		return DebugHandlerClient.INSTANCE;
	}

	@Override
	public void onPlayerEnterWeedwoodRowboat() {
		if (ConfigHandler.rowboatView) {
			WeedwoodRowboatHandler.THIRD_PERSON_PERSPECTIVE.switchTo();
		} else {
			WeedwoodRowboatHandler.FIRST_PERSON_PERSPECTIVE.switchTo();
		}
	}

	@Override
	public void updateRiderYawInWeedwoodRowboat(EntityWeedwoodRowboat rowboat, EntityLivingBase rider) {
		if (rowboat.worldObj.isRemote && WeedwoodRowboatHandler.THIRD_PERSON_PERSPECTIVE.isCurrentPerspective()) {
			rider.rotationYaw += ((rowboat.rotationYaw - rider.rotationYaw) % 180 - 90) * 0.2F;
			rider.rotationPitch *= 0.8F;
		}
	}

	private static FontRenderer pixelLove;

	@Override
	public FontRenderer getCustomFontRenderer() {
		return pixelLove; 
	}

	@Override
	public ModelBiped getExplorersHatModel() {
		return EXPLORERS_HAT_MODEL;
	}
}
