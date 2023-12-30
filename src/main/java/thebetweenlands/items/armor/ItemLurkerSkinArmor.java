package thebetweenlands.items.armor;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thebetweenlands.gemcircle.CircleGem;
import thebetweenlands.items.BLItemRegistry;
import thebetweenlands.items.BLMaterial;
import thebetweenlands.items.misc.ItemGeneric.EnumItemGeneric;
import thebetweenlands.manual.IManualEntryItem;

public class ItemLurkerSkinArmor extends ItemArmorBL implements IManualEntryItem {

	public ItemLurkerSkinArmor(int armorType) {
		super(BLMaterial.armorLurkerSkin, 2, armorType, "thebetweenlands:textures/armour/lurker1.png", "thebetweenlands:textures/armour/lurker2.png");
		String itemTexture;
		switch(armorType) {
		default:
		case 0:
			itemTexture = "thebetweenlands:lurkerSkinHelmet";
			break;
		case 1:
			itemTexture = "thebetweenlands:lurkerSkinChestplate";
			break;
		case 2:
			itemTexture = "thebetweenlands:lurkerSkinLeggings";
			break;
		case 3:
			itemTexture = "thebetweenlands:lurkerSkinBoots";
			break;
		}
		this.setTextureName(itemTexture);
		this.setGemTextures(CircleGem.AQUA, itemTexture + "AquaGem", "thebetweenlands:textures/armour/lurker1AquaGem.png", "thebetweenlands:textures/armour/lurker2AquaGem.png");
		this.setGemTextures(CircleGem.CRIMSON, itemTexture + "CrimsonGem", "thebetweenlands:textures/armour/lurker1CrimsonGem.png", "thebetweenlands:textures/armour/lurker2CrimsonGem.png");
		this.setGemTextures(CircleGem.GREEN, itemTexture + "GreenGem", "thebetweenlands:textures/armour/lurker1GreenGem.png", "thebetweenlands:textures/armour/lurker2GreenGem.png");
	}

	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == BLItemRegistry.itemsGeneric && material.getItemDamage() == EnumItemGeneric.LURKER_SKIN.id;
	}
	
	@Override
	protected boolean isLeggings(ItemStack stack) {
		return stack.getItem() == BLItemRegistry.lurkerSkinLeggings;
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemStack[] armor = player.inventory.armorInventory;
		int armorPieces = 0;
		for (ItemStack anArmor : armor) {
			if (anArmor != null && anArmor.getItem() instanceof ItemLurkerSkinArmor) {
				armorPieces += 1;
			}
		}
		if (itemStack.getItem() == BLItemRegistry.lurkerSkinBoots && player.isInWater()) {
			boolean fullyInWater = player.worldObj.getBlock((int)player.posX, (int)(player.boundingBox.maxY + 0.1D), (int)player.posZ).getMaterial().isLiquid();
			if(fullyInWater) {
				if(!player.isSneaking() && player.moveForward == 0) {
					player.motionY = Math.sin(player.ticksExisted / 5.0F) * 0.016D;
				}
				if(player.moveForward != 0) {
					if(player.moveForward > 0) {
						Vec3 lookVec = player.getLookVec().normalize();
						double speed = 0.01D + 0.05D / 4.0D * armorPieces;
						player.motionX += lookVec.xCoord * player.moveForward * speed;
						player.motionZ += lookVec.zCoord * player.moveForward * speed;
						player.motionY += lookVec.yCoord * player.moveForward * speed;
						player.getFoodStats().addExhaustion(0.003F);
					}
					player.motionY += 0.02D;
				}
			}
			if(armorPieces >= 4) {
				if (!player.isPotionActive(Potion.waterBreathing)) {
					player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 0));
				}
				if(player.ticksExisted % 3 == 0) player.setAir(player.getAir() - 1);
				if(player.getAir() <= -20) {
					player.setAir(0);

					for (int i = 0; i < 8; ++i) {
						Random rand = world.rand;
						float f = rand.nextFloat() - rand.nextFloat();
						float f1 = rand.nextFloat() - rand.nextFloat();
						float f2 = rand.nextFloat() - rand.nextFloat();
						player.worldObj.spawnParticle("bubble", player.posX + (double)f, player.posY + (double)f1, player.posZ + (double)f2, player.motionX, player.motionY, player.motionZ);
					}

					player.attackEntityFrom(DamageSource.drown, 2.0F);
				}
			}
		}
	}

	@Override
	public String manualName(int meta) {
		return "lurkerArmor";
	}

	@Override
	public Item getItem() {
		return this;
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