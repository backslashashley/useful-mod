package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.gui.overlay.BossEventOverlay;
import net.minecraft.client.render.Window;

@Mixin(BossEventOverlay.class)
public class BossEventOverlayMixin {

	@Redirect(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/Window;getHeight()I"
		)
	)
	private int onlyOneBossBar(Window window) {
		return Config.SHOW_ONE_BOSS_BAR.get() ? 36 : window.getHeight();
	}
}
