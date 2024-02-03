package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.render.Culler;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FireworksEntity;
import net.minecraft.entity.living.LivingEntity;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@Inject(
		method = "render(Lnet/minecraft/entity/Entity;FZ)V",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void hideDeathAnimation(Entity entity, float tickDelta, boolean hitbox, CallbackInfo ci) {
		if (Config.SHOW_DEATH_ANIMATION.get() || !(entity instanceof LivingEntity)) {
			return;
		}

		final LivingEntity mob = (LivingEntity) entity;
		if (mob.deathTime > 0) {
			ci.cancel();
		}
	}

	@Inject(
		method = "shouldRender",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void hideFireworksWhenRidden(Entity entity, Culler camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof FireworksEntity && ((FireworksEntity) entity).m_9626788()) {
			cir.setReturnValue(false);
		}
	}
}
