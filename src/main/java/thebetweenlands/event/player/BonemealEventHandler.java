package thebetweenlands.event.player;

import net.minecraftforge.event.entity.player.BonemealEvent;
import thebetweenlands.blocks.plants.crops.BlockBLGenericCrop;
import thebetweenlands.blocks.tree.BlockBLSapling;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BonemealEventHandler {
	@SubscribeEvent
	 public void onUseBonemeal(BonemealEvent event) {
		if (event.block instanceof BlockBLGenericCrop || event.block instanceof BlockBLSapling)
			if (!event.world.isRemote)
				event.setResult(Result.DENY);
	}
}