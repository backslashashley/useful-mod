package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

	@Inject(
		method = "m_5724150",
		at = @At(
			value= "HEAD"
		),
		cancellable = true
	)
	private void onClickRealmsButton(CallbackInfo ci) {
		ci.cancel();
	}
}
