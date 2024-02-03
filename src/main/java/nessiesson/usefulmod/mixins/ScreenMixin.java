package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Splitter;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public class ScreenMixin {

	@Shadow
	private void sendChatMessage(String msg, boolean addToChat) {
		throw new UnsupportedOperationException();
	}

	@Inject(
		method = "handleInputs",
		at = {
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/gui/screen/Screen;handleKeyboard()V"
			),
			@At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/gui/screen/Screen;handleMouse()V"
			)
		},
		cancellable = true
	)
	private void mc31222(CallbackInfo ci) {
		if ((Screen) (Object) this != Minecraft.getInstance().screen) {
			ci.cancel();
		}
	}

	/**
	 * @author nessie
	 * @reason Simplest way to inject
	 */
	@Overwrite
	public void sendChatMessage(String msg) {
		for (String message : Splitter.fixedLength(256).split(msg)) {
			this.sendChatMessage(message, true);
		}
	}

	@Inject(
		method = "renderBackground(I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/Screen;fillGradient(IIIIII)V"
		),
		cancellable = true
	)
	private void onDrawBackground(int offset, CallbackInfo ci) {
		if (!Config.SHOW_GUI_BACKGROUND.get()) {
			ci.cancel();
		}
	}
}
