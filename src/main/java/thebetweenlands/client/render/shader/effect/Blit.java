package thebetweenlands.client.render.shader.effect;

import net.minecraft.util.ResourceLocation;

public class Blit extends PostProcessingEffect {
	@Override
	protected ResourceLocation[] getShaders() {
		return new ResourceLocation[] {new ResourceLocation("thebetweenlands:shaders/mc/program/blit.vsh"), new ResourceLocation("thebetweenlands:shaders/mc/program/blit.fsh")};
	}
}
