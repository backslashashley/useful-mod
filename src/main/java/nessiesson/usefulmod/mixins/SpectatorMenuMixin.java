package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenuListener;

@Mixin(SpectatorMenu.class)
public class SpectatorMenuMixin {

	@Shadow
	private SpectatorMenuItem getItem(int index) {
		throw new UnsupportedOperationException();
	}

	@Inject(
		method = "<init>",
		at = @At(
			value = "RETURN"
		)
	)
	private void onlyShowPlayers(SpectatorMenuListener menu, CallbackInfo ci) {
		if (!Config.SHOW_TEAM_MENU.get()) {
			this.getItem(0).select((SpectatorMenu) (Object) this);
		}
	}
}
