package nessiesson.usefulmod.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

	private ItemEntityMixin(World worldIn) {
		super(worldIn);
	}

	@Redirect(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/World;isClient:Z",
			opcode = Opcodes.GETFIELD,
			ordinal = 0
		)
	)
	private boolean clientPushOutOfBlocks(World world) {
		return !Config.SMOOTH_ITEM_MOVEMENT.get() && !Minecraft.getInstance().isInSingleplayer();
	}
}
