package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.render.entity.ItemFrameRenderer;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(ItemFrameRenderer.class)
public class ItemFrameRendererMixin {

	@Shadow
	private void renderItem(ItemFrameEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Shadow
	private void renderNameTag(ItemFrameEntity entity, double x, double y, double z) {
		throw new UnsupportedOperationException();
	}

	@Inject(
		method = "render",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void onRender(ItemFrameEntity entity, double x, double y, double z, float yaw, float tickDelta, CallbackInfo ci) {
		if (entity.direction != null && !Config.SHOW_ITEM_fRAME_FRAME.get()) {
			ci.cancel();
			final BlockPos blockpos = entity.getBlockPos();
			final double dX = (double) blockpos.getX() - entity.x + x + 0.5D;
			final double dY = (double) blockpos.getY() - entity.y + y + 0.5D;
			final double dZ = (double) blockpos.getZ() - entity.z + z + 0.5D;
			GlStateManager.pushMatrix();
			GlStateManager.translated(dX, dY, dZ);
			GlStateManager.rotatef(180F - entity.yaw, 0F, 1F, 0F);
			GlStateManager.translatef(0F, 0F, 0.4375F);
			this.renderItem(entity);
			GlStateManager.popMatrix();
			this.renderNameTag(entity, x + entity.direction.getOffsetX() * 0.3D, y - 0.25D, z + entity.direction.getOffsetZ() * 0.3D);
		}
	}
}
