package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.CompactChat;

import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.text.Text;

@Mixin(ChatGui.class)
public class ChatGuiMixin {

	private static final CompactChat compactChat = new CompactChat();

	@Inject(
		method = "addMessage(Lnet/minecraft/text/Text;)V",
		at = @At(
			value = "HEAD"
		),
	cancellable = true)
	private void onChat(Text text, CallbackInfo ci) {
		if (compactChat.onChat(text)) {
			ci.cancel();
		}
	}
}
