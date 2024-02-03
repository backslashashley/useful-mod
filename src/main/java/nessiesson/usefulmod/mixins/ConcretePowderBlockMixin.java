package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.property.EnumProperty;
import net.minecraft.item.DyeColor;

@Mixin(ConcretePowderBlock.class)
public class ConcretePowderBlockMixin extends FallingBlock {

	@Shadow
	@Final
	public static EnumProperty<DyeColor> COLOR;

	@Override
	public int getParticleColor(BlockState state) {
		return state.get(COLOR).getTextureDiffuseColor();
	}
}
