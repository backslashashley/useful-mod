package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.PistonBaseBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.block.PistonBaseBlock.EXTENDED;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlockMixin {

	@Inject(
		method = "checkExtended", 
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;addBlockEvent(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;II)V",
			ordinal = 1,
			shift = At.Shift.BEFORE
		)
	)
	private void reintroduceInstantDoubleRetraction(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (Config.INSTANT_DOUBLE_RETRACTION.get()) {
			world.setBlockState(pos, state.set(EXTENDED, false), 2);
		}
	}
}
