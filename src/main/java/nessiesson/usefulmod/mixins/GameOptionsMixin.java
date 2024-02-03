package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.options.GameOptions;
import net.minecraft.client.resource.language.I18n;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

	@Shadow
	public float gamma;

	@Shadow
	private float getValueFloat(GameOptions.Option option) {
		throw new UnsupportedOperationException();
	}

	@Inject(
		method = "setValue(Lnet/minecraft/client/options/GameOptions$Option;F)V",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void overrideGammaValue(GameOptions.Option option, float value, CallbackInfo ci) {
		if (option != GameOptions.Option.GAMMA) {
			return;
		}

		if (value >= 0.95F) {
			value = 1000F;
		} else if (value >= 0.9F) {
			value = 1F;
		} else {
			value = Math.min(1F, value / 0.9F);
		}

		ci.cancel();
		this.gamma = value;
	}

	@Inject(
		method = "getValueAsString",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void overrideGammaText(GameOptions.Option option, CallbackInfoReturnable<String> cir) {
		if (option != GameOptions.Option.GAMMA) {
			return;
		}

		cir.cancel();
		final float f = this.getValueFloat(option);
		String s = I18n.translate(option.getName()) + ": ";
		if (f > 1F) {
			s += I18n.translate("usefulmod.options.gamma.fullbright");
		} else if (f > 0.95F) {
			s += I18n.translate("options.gamma.max");
		} else if (f > 0F) {
			s += "+" + (int) (f * 100F) + "%";
		} else {
			s += I18n.translate("options.gamma.min");
		}

		cir.setReturnValue(s);
	}
}
