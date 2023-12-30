package thebetweenlands.manual;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import thebetweenlands.items.BLItemRegistry;

/**
 * Created by Bart on 06/12/2015.
 */
public class GuiManualHerblore extends GuiManualBase {
    private static ResourceLocation book = new ResourceLocation("thebetweenlands:textures/gui/manual/manualHL.png");

    public GuiManualHerblore(EntityPlayer player) {
        super(player);
    }


    @Override
    public void initGui() {
        manualType = BLItemRegistry.manualHL;
        xStart = width / 2 - 146;
        xStartRightPage = xStart + 146;
        yStart = (height - HEIGHT) / 2;
        untilUpdate = 0;
        changeCategory(ManualManager.getCurrentCategory(manualType, player), ManualManager.getCurrentPageNumber(manualType, player));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void drawScreen(int mouseX, int mouseY, float renderPartials) {
        mc.renderEngine.bindTexture(book);

        drawTexture(xStart, yStart, WIDTH, HEIGHT, 1024.0D, 1024.0D, 0.0D, 292.0D, 0.0D, 180.0D);
        if (currentCategory != null) {
            if (currentCategory.getCurrentPage() - 2 >= 1) {
                if (mouseX >= xStart + 15 && mouseX <= xStart + 15 + 19 && mouseY >= yStart + 160 && mouseY <= yStart + 160 + 8)
                    drawTexture(xStart + 15, yStart + 160, 19, 8, 1024.0D, 1024.0D, 292.0D, 311.0D, 9.0D, 18.0D);
                else
                    drawTexture(xStart + 15, yStart + 160, 19, 8, 1024.0D, 1024.0D, 292.0D, 311.0D, 0.0D, 9.0D);
            }
            if (currentCategory.getCurrentPage() + 2 <= currentCategory.getVisiblePages().size()) {
                if (mouseX >= xStart + 256 && mouseX <= xStart + 256 + 19 && mouseY >= yStart + 160 && mouseY <= yStart + 160 + 8)
                    drawTexture(xStart + 256, yStart + 160, 19, 8, 1024.0D, 1024.0D, 311.0D, 330.0D, 9.0D, 18.0D);
                else
                    drawTexture(xStart + 256, yStart + 160, 19, 8, 1024.0D, 1024.0D, 311.0D, 330.0D, 0.0D, 9.0D);
            }
            drawTexture(xStart, yStart + 10, 14, 22 * currentCategory.getCategoryNumber(), 1024.0D, 1024.0D, 293.0D, 306.0D, 18.0D, 18.0D + 22.0D * currentCategory.getCategoryNumber());
            drawTexture(xStart + 279, yStart + 10 + 22 * currentCategory.getCategoryNumber(), 14, 44 - 22 * currentCategory.getCategoryNumber(), 1024.0D, 1024.0D, 306.0D, 293.0D, 18.0D + 22.0D * currentCategory.getCategoryNumber(), 62.0D);
            if (currentCategory.getCategoryNumber() == 2)
                drawTexture(xStart, yStart + 33, 14, 20, 1024.0D, 1024.0D, 312.0D, 325.0D, 42.0D, 62.0D);
            else
                drawTexture(xStart + 278, yStart + 33, 14, 20, 1024.0D, 1024.0D, 315.0D, 329.0D, 42.0D, 62.0D);
            drawTexture(xStart, yStart + 11, 14, 20, 1024.0D, 1024.0D, 312.0D, 325.0D, 20.0D, 39.0D);
            currentCategory.draw(mouseX, mouseY);
        }
        
        this.renderPageNumbers(mouseX, mouseY, renderPartials);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (currentCategory != null) {
            if (mouseX >= xStart + 15 && mouseX <= xStart + 15 + 19 && mouseY >= yStart + 160 && mouseY <= yStart + 160 + 8 && button == 0) {
                currentCategory.previousPage(this);
            }
            if (mouseX >= xStart + 256 && mouseX <= xStart + 256 + 19 && mouseY >= yStart + 160 && mouseY <= yStart + 160 + 8 && button == 0) {
                currentCategory.nextPage(this);
            }
            if (mouseX >= xStart + (currentCategory.getCategoryNumber() >= 1 ? 0 : 279) && mouseX <= xStart + (currentCategory.getCategoryNumber() >= 1 ? 0 : 279) + 14 && mouseY >= yStart + 11 && mouseY <= yStart + 10 + 20 && button == 0)
                changeCategory(HLEntryRegistry.aspectCategory);
            if (mouseX >= xStart + (currentCategory.getCategoryNumber() >= 2 ? 0 : 279) && mouseX <= xStart + (currentCategory.getCategoryNumber() >= 2 ? 0 : 279) + 14 && mouseY >= yStart + 33 && mouseY <= yStart + 32 + 20 && button == 0)
                changeCategory(HLEntryRegistry.elixirCategory);
            currentCategory.mouseClicked(mouseX, mouseY, button);
        }
    }

}
