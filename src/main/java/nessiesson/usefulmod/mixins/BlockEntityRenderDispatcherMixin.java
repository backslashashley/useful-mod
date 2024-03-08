package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

	@Redirect(
		method = "render(Lnet/minecraft/block/entity/BlockEntity;FI)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/block/entity/BlockEntity;squaredDistanceTo(DDD)D"
		)
	)
	private double alwaysRenderBlockEntities(BlockEntity blockEntity, double x, double y, double z) {
		return Config.ALWAYS_RENDER_BLOCK_ENTITIES.get() ? 0D : blockEntity.squaredDistanceTo(x, y, z);
	}
}
