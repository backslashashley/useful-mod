package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.AreaSelectionRenderer;
import nessiesson.usefulmod.UsefulKeybinds;
import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.render.Culler;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@Inject(
		method = "shouldRenderEntity",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void highlightAllEntitites(Entity entity, Entity camera, Culler culler, CallbackInfoReturnable<Boolean> cir) {
		final LocalClientPlayerEntity player = Minecraft.getInstance().player;
		if (player.isSpectator() && UsefulKeybinds.HIGHLIGHT_ENTITIES.isPressed()) {
			cir.setReturnValue((entity instanceof LivingEntity || entity instanceof MinecartEntity) && (entity.ignoreCameraFrustum || culler.isVisible(entity.getShape()) || entity.m_1411174(player)));
		}
	}

	@Redirect(
		method = "doEvent",
		at = @At(
			ordinal = 0,
			value = "INVOKE",
			target = "Lnet/minecraft/client/world/ClientWorld;playSound(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/block/Block;getSounds()Lnet/minecraft/block/sound/BlockSounds;"
			)
		)
	)
	private void noBreakSound(ClientWorld client, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean distanceDelay) {
		if (!Config.SHOW_EXPLOSION.get()) {
			client.playSound(pos, sound, category, volume, pitch, distanceDelay);
		}
	}

	@Inject(
		method = "renderEntities",
		at = @At(
			value = "TAIL"
		)
	)
	private void postRenderEntities(Entity camera, Culler culler, float tickDelta, CallbackInfo ci) {
		AreaSelectionRenderer.render(tickDelta);
	}
}
