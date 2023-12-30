package thebetweenlands.client.render.shader.effect;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class WarpEffect extends PostProcessingEffect {
	private float scale = 1.0F;
	private float timeScale = 1.0F;
	private float multipier = 1.0F;
	private float xOffset = 0.0F;
	private float yOffset = 0.0F;
	private float warpX = 1.0F;
	private float warpY = 1.0F;

	private int timeUniformID = -1;
	private int scaleUniformID = -1;
	private int timeScaleUniformID = -1;
	private int multiplierUniformID = -1;
	private int xOffsetUniformID = -1;
	private int yOffsetUniformID = -1;
	private int warpXUniformID = -1;
	private int warpYUniformID = -1;

	public WarpEffect setScale(float scale) {
		this.scale = scale;
		return this;
	}

	public WarpEffect setTimeScale(float timeScale) {
		this.timeScale = timeScale;
		return this;
	}

	public WarpEffect setMultiplier(float multiplier) {
		this.multipier = multiplier;
		return this;
	}

	public WarpEffect setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		return this;
	}

	public WarpEffect setWarpDir(float warpX, float warpY) {
		this.warpX = warpX;
		this.warpY = warpY;
		return this;
	}

	@Override
	protected boolean initEffect() {
		this.timeUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_msTime");
		this.scaleUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_scale");
		this.timeScaleUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_timeScale");
		this.multiplierUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_multiplier");
		this.xOffsetUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_xOffset");
		this.yOffsetUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_yOffset");
		this.warpXUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_warpX");
		this.warpYUniformID = OpenGlHelper.func_153194_a(this.getShaderProgram(), "u_warpY");
		return /*this.timeUniformID >= 0 && this.scaleUniformID >= 0 && this.timeScaleUniformID >= 0 && this.multiplierUniformID >= 0 && this.xOffsetUniformID >= 0 && this.yOffsetUniformID >= 0*/true;
	}

	@Override
	protected ResourceLocation[] getShaders() {
		return new ResourceLocation[] {new ResourceLocation("thebetweenlands:shaders/postprocessing/warp/warp.vsh"), new ResourceLocation("thebetweenlands:shaders/postprocessing/warp/warp.fsh")};
	}

	@Override
	protected void uploadUniforms() {
		OpenGlHelper.func_153168_a(this.timeUniformID, this.getSingleFloatBuffer(System.nanoTime() / 1000000.0F));
		OpenGlHelper.func_153168_a(this.scaleUniformID, this.getSingleFloatBuffer(this.scale));
		OpenGlHelper.func_153168_a(this.timeScaleUniformID, this.getSingleFloatBuffer(this.timeScale));
		OpenGlHelper.func_153168_a(this.multiplierUniformID, this.getSingleFloatBuffer(this.multipier));
		OpenGlHelper.func_153168_a(this.xOffsetUniformID, this.getSingleFloatBuffer(this.xOffset));
		OpenGlHelper.func_153168_a(this.yOffsetUniformID, this.getSingleFloatBuffer(this.yOffset));
		OpenGlHelper.func_153168_a(this.warpXUniformID, this.getSingleFloatBuffer(this.warpX));
		OpenGlHelper.func_153168_a(this.warpYUniformID, this.getSingleFloatBuffer(this.warpY));
	}
}