package nessiesson.usefulmod.mixins;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

@Mixin(targets = {"net/minecraft/client/world/color/BlockColors$4", "net/minecraft/client/world/color/BlockColors$5"})
public class BlockColorsMixin {

	@Inject(
		method = "m_4761142(Lnet/minecraft/block/state/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;I)I",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void rainbowLeaves(BlockState state, WorldView world, BlockPos pos, int tint, CallbackInfoReturnable<Integer> cir) {
		if (!Config.SHOW_RAINBOW_LEAVES.get() || pos == null) {
			return;
		}

		final int sc = 1024;
		final float hue = this.usefulmodDist(pos.getX(), 32 * pos.getY(), pos.getX() + pos.getZ()) % sc / sc;
		cir.setReturnValue(Color.HSBtoRGB(hue, 0.7F, 1F));
	}

	@Unique
	private float usefulmodDist(int x, int y, int z) {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
}
