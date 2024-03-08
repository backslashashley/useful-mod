package nessiesson.usefulmod.mixins;

import nessiesson.usefulmod.config.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.widget.ServerListEntryWidget;
import net.minecraft.client.render.TextRenderer;

@Mixin(ServerListEntryWidget.class)
public class ServerListEntryWidgetMixin {

	@Redirect(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)I",
			ordinal = 0
		)
	)
	private int hideServerNames(TextRenderer textRenderer, String text, int x, int y, int color) {
		return Config.SHOW_SERVER_NAMES.get() ? textRenderer.draw(text, x, y, color) : 0;
	}
}
