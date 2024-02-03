package nessiesson.usefulmod.mixins;

import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MovingBlockEntity;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PlayerCombatS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

@Mixin(ClientPlayNetworkHandler.class)
public  class ClientPlayNetworkHandlerMixin {

	@Shadow
	private ClientWorld world;

	@Inject(
		method = "handlePlayerCombat",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V"
		)
	)
	private void sendDeathLocation(PlayerCombatS2CPacket packetIn, CallbackInfo ci) {
		if (Config.RESPAWN_ON_DEATH.get()) {
			Minecraft.getInstance().player.tryRespawn();
		}

		if (Config.DEATH_LOCATION.get()) {
			final Minecraft mc = Minecraft.getInstance();
			final BlockPos pos = mc.player.getSourceBlockPos();
			final String formatted = String.format("You died @ %d %d %d", pos.getX(), pos.getY(), pos.getZ());
			final Text message = new LiteralText(formatted);
			message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, formatted));
			mc.gui.getChat().addMessage(message);
		}
	}

	@Redirect(
		method = "handleWorldTime",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/network/packet/s2c/play/WorldTimeS2CPacket;getTimeOfDay()J"
		)
	)
	private long alwaysDay(WorldTimeS2CPacket packet) {
		final long time = packet.getTimeOfDay();
		if (Config.ALWAYS_DAY.get()) {
			return time >= 0 ? -(time - time % 24000L + 6000L) : time;
		}

		return time;
	}

	@Redirect(
		method = "handleVehiclePassengers",
		at = @At(
			value = "INVOKE",
			target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V",
			remap = false
		)
	)
	private void noopWarn(Logger logger, String message) {
		// noop
	}

	@Redirect(
		method = "handleWorldChunk",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/Iterator;hasNext()Z",
			remap = false
		)
	)
	private boolean replaceBlockEntityLoop(Iterator<NbtCompound> it) {
		while (it.hasNext()) {
			final NbtCompound compound = it.next();
			final BlockPos pos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
			final boolean isPiston = compound.getString("id").equals("minecraft:piston");
			if (isPiston) {
				compound.putFloat("progress", Math.min(compound.getFloat("progress") + 0.5F, 1F));
			}

			BlockEntity be = this.world.getBlockEntity(pos);
			if (be != null) {
				be.readNbt(compound);
			} else {
				if (!isPiston) {
					continue;
				}

				final BlockState state = this.world.getBlockState(pos);
				if (state.getBlock() != Blocks.MOVING_BLOCK) {
					continue;
				}

				be = new MovingBlockEntity();
				be.readNbt(compound);
				this.world.setBlockEntity(pos, be);
				be.clearBlockCache();
			}
		}

		return false;
	}
}
