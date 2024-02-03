package nessiesson.usefulmod.ClientCommands.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.ClientCommands.ClientCommandManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

@Mixin(Screen.class)
public class ScreenMixin {

	@Inject(
		method = "sendChatMessage(Ljava/lang/String;Z)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;sendChat(Ljava/lang/String;)V"
		),
		cancellable = true
	)
	private void runClientCommand(String msg, boolean addToChat, CallbackInfo ci) {
		if (ClientCommandManager.INSTANCE.run(Minecraft.getInstance().player, msg) != 0) {
			ci.cancel();
		}
	}
}
