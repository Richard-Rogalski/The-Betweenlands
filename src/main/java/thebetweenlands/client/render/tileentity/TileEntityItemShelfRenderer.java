package thebetweenlands.client.render.tileentity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import thebetweenlands.blocks.plants.BlockBLSmallPlants;
import thebetweenlands.blocks.terrain.BlockWisp;
import thebetweenlands.client.model.block.ModelItemShelf;
import thebetweenlands.items.throwable.ItemTarminion;
import thebetweenlands.tileentities.TileEntityItemShelf;
import thebetweenlands.utils.ItemRenderHelper;

@SideOnly(Side.CLIENT)
public class TileEntityItemShelfRenderer extends TileEntitySpecialRenderer {
	private final ModelItemShelf model = new ModelItemShelf();
	public static ResourceLocation TEXTURE = new ResourceLocation("thebetweenlands:textures/tiles/itemShelf.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTickTime) {
		TileEntityItemShelf shelf = (TileEntityItemShelf) tile;
		int meta = shelf.getBlockMetadata();
		double unitPixel = 0.0625D;
		double offSetX = 0;
		double offSetZ = 0;
		double offSetXX = 0;
		double offSetZZ = 0;
		float rotation = 0;
		bindTexture(TEXTURE);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		switch (meta) {
		case 2:
			GL11.glRotatef(180F, 0.0F, 1F, 0F);
			rotation = 0F;
			offSetX = unitPixel * 12;
			offSetZ = unitPixel * 12;
			offSetXX = unitPixel * 3.75;
			offSetZZ = unitPixel * 12;
			break;
		case 3:
			GL11.glRotatef(0F, 0.0F, 1F, 0F);
			rotation = 180;
			offSetX = unitPixel * 3.75;
			offSetZ = unitPixel * 3.75;
			offSetXX = unitPixel * 12;
			offSetZZ = unitPixel * 3.75;
			break;
		case 4:
			GL11.glRotatef(90F, 0.0F, 1F, 0F);
			rotation = 90F;
			offSetX = unitPixel * 12;
			offSetZ = unitPixel * 3.75;
			offSetXX = unitPixel * 12;
			offSetZZ = unitPixel * 12;
			break;
		case 5:
			GL11.glRotatef(-90F, 0.0F, 1F, 0F);
			rotation = -90F;
			offSetX = unitPixel * 3.75;
			offSetZ = unitPixel * 12;
			offSetXX = unitPixel * 3.75;
			offSetZZ = unitPixel * 3.75;
			break;
		}
		model.render();
		GL11.glPopMatrix();
		renderItemInSlot(shelf, 0, x + offSetX, y + unitPixel * 11, z + offSetZ, rotation);
		renderItemInSlot(shelf, 1, x + offSetXX, y + unitPixel * 11, z + offSetZZ, rotation);
		renderItemInSlot(shelf, 2, x + offSetX, y + unitPixel * 3, z + offSetZ, rotation);
		renderItemInSlot(shelf, 3, x + offSetXX, y + unitPixel * 3, z + offSetZZ, rotation);
	}

	private void renderItemInSlot(TileEntityItemShelf shelf, int slotIndex, double x, double y, double z, float rotation) {
		ItemStack stack = shelf.getStackInSlot(slotIndex);
		if (stack != null) {
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslated(x, y, z);
			GL11.glRotatef(rotation, 0.0F, 1F, 0F);
			GL11.glPushMatrix();

			if (stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType()) && !(Block.getBlockFromItem(stack.getItem()) instanceof BlockContainer)) {
				GL11.glTranslated(0, -0.1, 0);
				GL11.glRotatef(-90, 0.0F, 1F, 0F);

				if(Block.getBlockFromItem(stack.getItem()) instanceof BlockWall)
					GL11.glRotatef(90, 0.0F, 1F, 0F);

				if(Block.getBlockFromItem(stack.getItem()) instanceof BlockBLSmallPlants && Block.getBlockFromItem(stack.getItem()).getRenderType() != 1)
					GL11.glTranslated(-0.125, -0.1, -0.125);

				if(Block.getBlockFromItem(stack.getItem()) instanceof BlockDoublePlant&& Block.getBlockFromItem(stack.getItem()).getRenderType() != 1)
					GL11.glTranslated(-0.125, -0.05, -0.125);

				EntityItem entityitem = new EntityItem(shelf.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
				entityitem.getEntityItem().stackSize = 1;
				entityitem.hoverStart = 0.0F;
				RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, rotation, 0.0F);
			}
			else if (stack.getItem() instanceof ItemBlock && Block.getBlockFromItem(stack.getItem()) instanceof BlockContainer || stack.getItem() instanceof ItemTarminion) {
				GL11.glTranslated(0, -0.1, 0);
				GL11.glRotatef(90, 0.0F, 1F, 0F);

				if(stack.getItem() instanceof ItemTarminion) {
					GL11.glTranslated(0, -0.22, 0);
					GL11.glRotatef(90, 0.0F, 1F, 0F);
				}

				if(Block.getBlockFromItem(stack.getItem()) instanceof BlockWisp)
					GL11.glRotatef(-rotation -90, 0.0F, 1F, 0F);

				EntityItem entityitem = new EntityItem(shelf.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
				entityitem.getEntityItem().stackSize = 1;
				entityitem.hoverStart = 0.0F;
				RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, rotation, 0.0F);
			} else {
				GL11.glTranslated(-0.16D, -0.125, 0);
				GL11.glScaled(0.33D, 0.33D, 0.33D);
				/*for(int i = 0; i < stack.getItem().getRenderPasses(stack.getItemDamage()); i++) {
					ItemRenderHelper.renderItem(stack, i);
				}*/
				ItemRenderHelper.renderItemIn3D(stack);
			}
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}
}
