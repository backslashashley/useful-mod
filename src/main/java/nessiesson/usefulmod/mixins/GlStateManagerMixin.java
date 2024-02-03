package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.config.Config;

@Mixin(GlStateManager.class)
public class GlStateManagerMixin {

	@ModifyVariable(
		method = "fogDensity",
		at = @At(
			value = "HEAD"
		),
		argsOnly = true
	)
	private static float adjustFogDensity(float fogDensity) {
		// In vanilla code, this method is only called with fogdensity = 2F when in lava.
		return fogDensity == 2F && Config.SHOW_CLEAR_LAVA.get() ? 0F : fogDensity;
	}
}
