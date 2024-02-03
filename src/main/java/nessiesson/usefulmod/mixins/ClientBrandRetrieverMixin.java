package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.ClientBrandRetriever;

@Mixin(value = ClientBrandRetriever.class, priority = 1001, remap = false)
public class ClientBrandRetrieverMixin {

	@Inject(
		method = "getClientModName",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private static void pureVanilla(CallbackInfoReturnable<String> cir) {
		// cir.setReturnValue("vanilla++");
	}
}
