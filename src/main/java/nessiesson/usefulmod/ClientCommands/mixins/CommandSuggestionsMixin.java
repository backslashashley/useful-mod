package nessiesson.usefulmod.ClientCommands.mixins;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.ObjectArrays;

import nessiesson.usefulmod.ClientCommands.ClientCommandManager;

import net.minecraft.client.command.CommandSuggestions;
import net.minecraft.text.Formatting;

@Mixin(CommandSuggestions.class)
public class CommandSuggestionsMixin {

	@ModifyArg(
		method = "useSuggestion",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;write(Ljava/lang/String;)V"
		)
	)
	private String stripFormattingCodes(String text) {
		return Formatting.strip(text);
	}

	@Inject(
		method = "requestSuggestions",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/handler/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"
		)
	)
	private void addAutoComplete(String prefix, CallbackInfo ci) {
		ClientCommandManager.INSTANCE.autoComplete(prefix);
	}

	@ModifyVariable(
		method = "acceptSuggestions",
		at = @At(
			value = "INVOKE",
			target = "Ljava/util/List;clear()V",
			remap = false
		)
	)
	private String[] setLatestAutoComplete(String... completions) {
		final String[] complete = ClientCommandManager.INSTANCE.latestAutoComplete;
		if (complete != null) {
			return ObjectArrays.concat(complete, completions, String.class);
		}

		return completions;
	}

	@Redirect(
		method = "acceptSuggestions",
		at = @At(
			value = "INVOKE",
			target = "Lorg/apache/commons/lang3/StringUtils;getCommonPrefix([Ljava/lang/String;)Ljava/lang/String;",
			remap = false
		)
	)
	private String adjustedGetCommonPrefix(String... strs) {
		final String string = StringUtils.getCommonPrefix(strs);
		return Formatting.strip(string);
	}
}
