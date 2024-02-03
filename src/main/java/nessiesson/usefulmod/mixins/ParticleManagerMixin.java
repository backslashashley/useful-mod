package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.particle.ParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

	@Shadow
	private World world;

	@Inject(
		method = "addBlockMiningParticles",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void onAddBlockMiningParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (!Config.SHOW_BLOCK_BREAKING_PARTICLES.get()) {
			ci.cancel();
			return;
		}

		if (Config.SHOW_EXPLOSION.get() && this.world instanceof ClientWorld) {
			((ClientWorld) this.world).playSound(pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4F, (1F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
			final float f = this.world.random.nextFloat();
			if (f <= 0.5F) {
				this.world.addParticle(ParticleType.EXPLOSION_NORMAL, pos.getX(), pos.getY(), pos.getZ(), 1D, 0D, 0D);
			} else if (f <= 0.85F) {
				this.world.addParticle(ParticleType.EXPLOSION_LARGE, pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
			} else {
				this.world.addParticle(ParticleType.EXPLOSION_HUGE, pos.getX(), pos.getY(), pos.getZ(), 0D, 0D, 0D);
			}
		}
	}
}
