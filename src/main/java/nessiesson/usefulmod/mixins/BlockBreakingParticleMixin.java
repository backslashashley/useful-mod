package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MovingBlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.particle.BlockBreakingParticle;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.render.block.BlockModelShaper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockBreakingParticle.class)
public class BlockBreakingParticleMixin extends Particle {

	@Shadow
	@Final
	private BlockState state;

	private BlockBreakingParticleMixin(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Inject(
		method = "<init>",
		at = @At(
			value = "TAIL"
		)
	)
	private void removeRandomParticleMotion(CallbackInfo ci) {
		if (Config.SHOW_INSANE_BLOCK_BREAKING_PARTICLES.get()) {
			final double multiplier = this.random.nextFloat() * 5;
			this.velocityX *= multiplier;
			this.velocityY *= multiplier;
			this.velocityZ *= multiplier;
			this.lifetime *= multiplier;
			this.gravity = 0F;
		}

		if (Config.FIX_BLOCK_36_PARTICLES.get() && this.state.getBlock() == Blocks.MOVING_BLOCK) {
			final BlockEntity te = this.world.getBlockEntity(new BlockPos(this.x, this.y, this.z));
			final BlockModelShaper bms = Minecraft.getInstance().getBlockRenderDispatcher().getModelShaper();
			if (te instanceof MovingBlockEntity) {
				final MovingBlockEntity piston = (MovingBlockEntity) te;
				this.setTexture(bms.getParticleIcon(piston.getMovedState()));
			} else {
				this.setTexture(bms.getParticleIcon(Blocks.STONE.defaultState()));
			}
		}
	}
}
