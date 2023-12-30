package thebetweenlands.manual.widgets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import thebetweenlands.blocks.BLBlockRegistry;
import thebetweenlands.items.BLItemRegistry;

import java.util.ArrayList;

/**
 * Created by Bart on 17/11/2015.
 */
public class RubberTabWidget extends ManualWidgetsBase {
    public static int width = 62;
    public static int height = 38;

    public RubberTabWidget(int xStart, int yStart) {
        super(xStart, yStart);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawForeGround() {
        manual.mc.renderEngine.bindTexture(icons);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        manual.drawTexturedModalRect(xStart + 18, yStart + 4, 0, 17, 16, 16);
        manual.drawTexturedModalRect(xStart + 38, yStart + 22, 0, 0, 22, 16);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        renderItem(xStart, yStart, new ItemStack(BLItemRegistry.weedwoodBucket), false, true, manual.manualType);
        renderItem(xStart + 18, yStart + 22, new ItemStack(Item.getItemFromBlock(BLBlockRegistry.rubberTreeLog)), false, false, manual.manualType);
        renderItem(xStart + 62, yStart + 22, new ItemStack(BLItemRegistry.weedwoodBucketRubber), false, false, manual.manualType);
        if (mouseX >= xStart + 18 && mouseX <= xStart + 34 && mouseY >= yStart + 4 && mouseY <= yStart + 20) {
            ArrayList<String> processTooltip = new ArrayList<>();
            processTooltip.add(StatCollector.translateToLocal("manual.widget.rubberTab.recipe"));
            renderTooltip(mouseX, mouseY, processTooltip, 0xffffff, 0xf0100010);
        }
    }
}
