package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;

// Totally not stolen from hackerman masa
// https://github.com/maruohon/tweakeroo/blob/521e641e34c709eaeb683176f89c9952e5c355f9/src/main/java/fi/dy/masa/tweakeroo/mixin/MixinEntityRenderer_Predicate.java
@Mixin(targets = "net/minecraft/client/render/GameRenderer$1")
public class GameRenderer$1Mixin {

	@Inject(
		method = "apply(Lnet/minecraft/entity/Entity;)Z",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void ignoreDeadEntities(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof LivingEntity && ((LivingEntity) entity).getHealth() <= 0F) {
			cir.setReturnValue(false);
		}
	}
}
