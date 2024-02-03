package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;
import net.minecraft.world.biome.Biome;

@Mixin(Biome.class)
public class BiomeMixin {

	@Inject(
		method = "getSkyColor",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void blackSky(float temperature, CallbackInfoReturnable<Integer> cir) {
		if (Config.TEMP_BLACK_SKY.get()) {
			cir.setReturnValue(0);
		}
	}
}
