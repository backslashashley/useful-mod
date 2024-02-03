package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;

@Mixin(targets = "net/minecraft/client/gui/screen/options/LanguageOptionsScreen$LanguageSelectionListWidget")
public class LangguageSelectionListWidgetMixin {

	@Redirect(
		method = "entryClicked(IZII)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/Minecraft;reloadResources()V"
		)
	)
	private void onlyRefreshLanguage(Minecraft minecraft) {
		minecraft.getLanguageManager().reload(minecraft.getResourceManager());
	}
}
