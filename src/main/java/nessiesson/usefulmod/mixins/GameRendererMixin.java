package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Unique
	private float usefulmodEyeHeight;
	@Unique
	private float usefulmodLastEyeHeight;

	@Inject(
		method = "renderItemInHand",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void hideHand(float partialTicks, int pass, CallbackInfo ci) {
		if (!Config.SHOW_HAND.get()) {
			ci.cancel();
		}
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/client/world/ClientWorld;getBrightness(Lnet/minecraft/util/math/BlockPos;)F"
		)
	)
	private void updateEyeHeights(CallbackInfo ci) {
		this.usefulmodLastEyeHeight = this.usefulmodEyeHeight;
		this.usefulmodEyeHeight += (Minecraft.getInstance().getCamera().getEyeHeight() - this.usefulmodEyeHeight) * 0.5F;
	}

	@Redirect(
		method = "transformCamera",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/blaze3d/platform/GlStateManager;translatef(FFF)V",
			ordinal = 4
		)
	)
	private void eyeHeightTranslate(float x, float y, float z, float partialTicks) {
		if (Config.SHOW_SNEAK_EYEHEIGHT.get()) {
			y += Minecraft.getInstance().getCamera().getEyeHeight() - this.usefulmodLastEyeHeight - (this.usefulmodEyeHeight - this.usefulmodLastEyeHeight) * partialTicks;
		}

		GlStateManager.translatef(x, y, z);
	}

	@ModifyVariable(
		method = "getFov",
		ordinal = 1,
		index = 4,
		name = "f",
		at = @At(
			value = "STORE",
			ordinal = 2
		)
	)
	private float fixedStaticFoV(float value) {
		return Config.TEMP_MASA_FOV_FIX.get() ? Minecraft.getInstance().options.fov : value;
	}
}
