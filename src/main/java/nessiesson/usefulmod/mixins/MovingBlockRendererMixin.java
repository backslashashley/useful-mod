package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.entity.MovingBlockEntity;
import net.minecraft.client.render.block.entity.MovingBlockRenderer;

@Mixin(MovingBlockRenderer.class)
public class MovingBlockRendererMixin {

	@Redirect(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/platform/GlStateManager;disableCull()V"
		)
	)
	private void cullMovingBlocks(MovingBlockEntity blockEntity, double x, double y, double z, float tickDelta, int overlay, float opacity) {
		GlStateManager.enableCull();
		GlStateManager.enablePolygonOffset();
		GlStateManager.polygonOffset(-blockEntity.getFacing().getId() * 0.01F, 0.01F);
	}

	@Inject(
		method = "render",
		at = @At(
			value = "RETURN"
		)
	)
	private void uncullMovingBlocks(MovingBlockEntity blockEntity, double x, double y, double z, float tickDelta, int overlay, float opacity, CallbackInfo ci) {
		GlStateManager.disableCull();
		GlStateManager.disablePolygonOffset();
	}

	@ModifyConstant(
		method = "render",
		constant = @Constant(
			floatValue = 0.25F
		)
	)
	private float fixShortArm(float value) {
		return 0.5F;
	}

	@ModifyConstant(
		method = "render",
		constant = @Constant(
			floatValue = 1F
		)
	)
	private float fixPistonBlink(float value) {
		return Float.MAX_VALUE;
	}
}
