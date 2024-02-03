package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.TimedKeyBinding;
import nessiesson.usefulmod.UsefulKeybinds;
import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.snooper.SnooperPopulator;
import net.minecraft.util.BlockableEventLoop;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements BlockableEventLoop, SnooperPopulator {

	@Shadow
	public Screen screen;

	@Shadow
	public ClientWorld world;

	@Inject(
		method = "handleGuiKeyBindings",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/options/GameOptions;setValue(Lnet/minecraft/client/options/GameOptions$Option;I)V"
		),
		cancellable = true
	)
	private void onNarratorKeypress(CallbackInfo ci) {
		ci.cancel();
	}

	@ModifyVariable(
		method = "doPick",
		ordinal = 0,
		index = 3,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/ClientPlayerInteractionManager;addStackToCreativeMenu(Lnet/minecraft/item/ItemStack;I)V"
		)
	)
	private ItemStack maxStackSize(ItemStack stack) {
		if (Config.ALWAYS_PICK_BLOCK_MAX_STACK.get()) {
			stack.setSize(stack.getMaxSize());
		}

		return stack;
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "TAIL"
		)
	)
	private void onPreClientTick(CallbackInfo ci) {
		if (UsefulKeybinds.TAPE_MOUSEABLE.isEmpty() || this.screen instanceof GameMenuScreen) {
			return;
		}

		for (TimedKeyBinding key : UsefulKeybinds.TAPE_MOUSEABLE) {
			key.tick();
		}
	}
}
