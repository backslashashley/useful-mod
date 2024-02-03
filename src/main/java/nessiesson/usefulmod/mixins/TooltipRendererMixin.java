package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.ShulkerBoxDisplay;

import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.menu.CreativeInventoryScreen;
import net.minecraft.item.ItemStack;

@Mixin({CreativeInventoryScreen.class, Screen.class})
public class TooltipRendererMixin extends GuiElement {

	@Inject(
		method = "renderTooltip",
		at = @At(
			value = "RETURN"
		)
	)
	private void postRenderToolTip(ItemStack stack, int x, int y, CallbackInfo ci) {
		ShulkerBoxDisplay.render(stack, x, y, this);
	}
}
