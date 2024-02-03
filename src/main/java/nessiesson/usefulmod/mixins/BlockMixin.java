package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;

@Mixin(Block.class)
public class BlockMixin {

	@Inject(
		method = "getOffset",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void onGetOffset(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir) {
		if (Config.SHOW_CENTERED_PLANTS.get()) {
			cir.setReturnValue(Vec3d.ZERO);
		}
	}
}
