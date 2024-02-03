package nessiesson.usefulmod.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.client.gui.screen.inventory.menu.PlayerInventoryScreen;
import net.minecraft.inventory.menu.InventoryMenu;

@Mixin(PlayerInventoryScreen.class)
public abstract class PlayerInventoryScreenMixin extends InventoryMenuScreen {

	private PlayerInventoryScreenMixin(InventoryMenu menu) {
		super(menu);
	}

	@Inject(
		method = "checkStatusEffects",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/gui/screen/inventory/menu/PlayerInventoryScreen;x:I",
			opcode = Opcodes.PUTFIELD,
			shift = At.Shift.AFTER
		)
	)
	private void noPotionShift(CallbackInfo ci) {
		if (!Config.SHOW_POTION_SHIFT.get()) {
			this.x = (this.width - this.backgroundWidth) / 2;
		}
	}
}
