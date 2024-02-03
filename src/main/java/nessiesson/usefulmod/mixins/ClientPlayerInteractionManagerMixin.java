package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.PlayerUseBlockC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.World;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

	@Unique
	float usefulmodBlockHardness;

	@Inject(
		method = "startMiningBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/ClientPlayerInteractionManager;isMiningBlock(Lnet/minecraft/util/math/BlockPos;)Z",
			shift = At.Shift.BEFORE
		)
	)
	private void preInstantMine(BlockPos pos, Direction face, CallbackInfoReturnable<Boolean> cir) {
		final World world = Minecraft.getInstance().world;
		this.usefulmodBlockHardness = world.getBlockState(pos).getMiningSpeed(world, pos);
	}

	@Inject(
		method = "startMiningBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/ClientPlayerInteractionManager;isMiningBlock(Lnet/minecraft/util/math/BlockPos;)Z",
			shift = At.Shift.AFTER
		)
	)
	private void postInstantMine(BlockPos pos, Direction face, CallbackInfoReturnable<Boolean> cir) {
		if (Config.MINING_GHOST_BLOCK_FIX.get() && this.usefulmodBlockHardness > 0F) {
			final ClientPlayNetworkHandler connection = Minecraft.getInstance().getNetworkHandler();
			if (connection != null) {
				connection.sendPacket(new PlayerUseBlockC2SPacket(pos, face, InteractionHand.MAIN_HAND, 0F, 0F, 0F));
			}
		}
	}

	@ModifyConstant(
		method = "updateBlockMining",
		constant = @Constant(
			intValue = 5
		)
	)
	private int postBlockMine(int blockHitDelay) {
		return Config.CLICK_BLOCK_MINING.get() ? 0 : 5;
	}
}
