package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;

import net.minecraft.entity.decoration.ItemFrameEntity;

@Mixin(ItemFrameEntity.class)
public class ItemFrameEntityMixin {

	@Inject(
		method = {
			"getWidth",
			"getHeight"
		},
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void adjustHitBox(CallbackInfoReturnable<Integer> cir) {
		if (!Config.SHOW_ITEM_fRAME_FRAME.get()) {
			cir.setReturnValue(5);
		}
	}
}
