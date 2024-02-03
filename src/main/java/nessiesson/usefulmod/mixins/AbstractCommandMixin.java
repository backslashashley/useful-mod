package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;

import net.minecraft.server.command.AbstractCommand;

@Mixin(AbstractCommand.class)
public class AbstractCommandMixin {

	@Inject(
		method = "canUse",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void overrideCommandPermissions(CallbackInfoReturnable<Boolean> cir) {
		if (Config.ALWAYS_SINGLE_PLAYER_CHEATS.get()) {
			cir.setReturnValue(true);
		}
	}
}
