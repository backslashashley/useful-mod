package nessiesson.usefulmod.mixins;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.block.entity.BeaconRenderer;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

@Mixin(BeaconRenderer.class)
public class BeaconRendererMixin {

	@Inject(method = "render", at = @At("RETURN"))
	private void render(BeaconBlockEntity te, double x, double y, double z, float tickDelta, int overlay, float opacity, CallbackInfo ci) {
		if (!Config.SHOW_BEACON_RANGE.get()) {
			return;
		}

		if (((BeaconBlockEntityAccessor) te).isActive() && te.getLevel() > 0) {
			final PlayerEntity player = Minecraft.getInstance().player;
			final double d0 = te.getLevel() * 10 + 10;
			final double d1 = player.prevTickX + (player.x - player.prevTickX) * tickDelta;
			final double d2 = player.prevTickY + (player.y - player.prevTickY) * tickDelta;
			final double d3 = player.prevTickZ + (player.z - player.prevTickZ) * tickDelta;
			final BlockPos pos = te.getPos();
			// It's not good, but it seems reasonably random.
			final int colour = pos.getX() + 101 * pos.getZ() + 41942 * pos.getY();
			final float[] colours = new Color((int) (colour * (16777215.0 / 2611456.0))).getRGBColorComponents(null);
			final Box axisalignedbb = (new Box(pos)).move(-d1, -d2, -d3).expand(d0).grow(0.0, te.getWorld().getHeight(), 0.0);
			GlStateManager.depthFunc(GL11.GL_LESS);
			GlStateManager.depthMask(false);
			GlStateManager.disableFog();
			GlStateManager.disableLighting();
			GlStateManager.disableTexture();
			WorldRenderer.renderShape(axisalignedbb, colours[0], colours[1], colours[2], 0.2F);
			WorldRenderer.renderOutlineShape(axisalignedbb, colours[0], colours[1], colours[2], 1F);
			GlStateManager.enableTexture();
			GlStateManager.enableLighting();
			GlStateManager.enableFog();
			GlStateManager.depthMask(true);
			GlStateManager.depthFunc(GL11.GL_EQUAL);
		}
	}
}
