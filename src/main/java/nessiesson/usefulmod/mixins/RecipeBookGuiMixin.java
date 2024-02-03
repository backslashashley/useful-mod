package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.menu.ActionType;

@Mixin(RecipeBookGui.class)
public class RecipeBookGuiMixin {

	@Inject(method = "updateContents", at = @At("TAIL"))
	private void craftingHax(CallbackInfo ci) {
		if (!Config.CRAFTING_HAX.get()) {
			return;
		}

		final Minecraft mc = Minecraft.getInstance();
		final ClientPlayerInteractionManager controller = mc.interactionManager;
		final LocalClientPlayerEntity player = mc.player;
		final int id = player.menu.networkId;
		if (Screen.isShiftDown() && Screen.isControlDown()) {
			controller.clickSlot(id, 0, 1, Screen.isAltDown() ? ActionType.THROW : ActionType.QUICK_MOVE, player);
		}
	}
}
