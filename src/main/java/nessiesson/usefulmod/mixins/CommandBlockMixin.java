package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.CommandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.CreativeModeTab;

@Mixin(CommandBlock.class)
public class CommandBlockMixin extends Block {

	private CommandBlockMixin(Material material) {
		super(material);
	}

	@Inject(
		method = "<init>",
		at = @At(
			value = "TAIL"
		)
	)
	private void init(CallbackInfo ci) {
		this.setCreativeModeTab(CreativeModeTab.REDSTONE);
	}
}
