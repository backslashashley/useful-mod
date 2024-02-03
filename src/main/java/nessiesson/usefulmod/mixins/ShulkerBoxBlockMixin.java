package nessiesson.usefulmod.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import nessiesson.usefulmod.ShulkerBoxDisplay;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TooltipFlag;
import net.minecraft.world.World;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {

	@Inject(
		method = "addHoverText",
		at = @At(
			value = "HEAD"
		)
	)
	private void postGetTooltip(ItemStack stack, World world, List<String> tooltip, TooltipFlag flag, CallbackInfo ci) {
		ShulkerBoxDisplay.addShulkerBoxTooltip(stack, tooltip);
	}
}
