package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.gui.screen.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

	@ModifyConstant(
		method = "init",
		constant = @Constant(
			intValue = 256
		)
	)
	private int increaseLimit(int orig) {
		return Config.EXTENDED_CHAT.get() ? 2048 : orig;
	}
}
