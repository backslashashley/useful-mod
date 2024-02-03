package nessiesson.usefulmod.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.Block;
import net.minecraft.block.SnowLayerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.particle.ParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin extends Block {

	private SnowLayerBlockMixin(Material material) {
		super(material);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (!Config.SHOW_SNOW_DRIP_PARTICLES.get()) {
			return;
		}

		if (rand.nextInt(10) == 0 && world.getBlockState(pos.down()).isFullBlock()) {
			final Material material = world.getBlockState(pos.down(2)).getMaterial();
			if (!material.blocksMovement() && !material.isLiquid()) {
				final double x = pos.getX() + rand.nextDouble();
				final double y = pos.getY() - 1.05;
				final double z = pos.getZ() + rand.nextDouble();
				world.addParticle(ParticleType.END_ROD, x, y, z, 0D, -0.06, 0D);
			}
		}
	}
}