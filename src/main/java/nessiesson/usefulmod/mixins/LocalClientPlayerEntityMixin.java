package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.menu.ActionType;
import net.minecraft.inventory.menu.InventoryMenu;
import net.minecraft.inventory.menu.TraderMenu;
import net.minecraft.world.World;

@Mixin(LocalClientPlayerEntity.class)
public class LocalClientPlayerEntityMixin extends ClientPlayerEntity {

	private LocalClientPlayerEntityMixin(World world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(
		method = "canUseCommand",
		at = @At(
			value = "HEAD"
		),
		cancellable = true
	)
	private void overrideCommandPermissions(int permissions, String commandName, CallbackInfoReturnable<Boolean> cir) {
		if (Config.ALWAYS_SINGLE_PLAYER_CHEATS.get()) {
			cir.setReturnValue(true);
		}
	}

	@Inject(
		method = "closeMenu",
		at = @At(
			value = "HEAD"
		)
	)
	private void onCloseMenu(CallbackInfo ci) {
		final InventoryMenu container = this.menu;
		if (container instanceof TraderMenu) {
			final Minecraft mc = Minecraft.getInstance();
			mc.interactionManager.clickSlot(container.networkId, 0, 1, ActionType.QUICK_MOVE, mc.player);
			mc.interactionManager.clickSlot(container.networkId, 1, 1, ActionType.QUICK_MOVE, mc.player);
		}
	}

	@Redirect(
		method = "tickAi",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screen/Screen;shouldPauseGame()Z"
		)
	)
	private boolean mc2071(Screen screen) {
		return true;
	}
}
