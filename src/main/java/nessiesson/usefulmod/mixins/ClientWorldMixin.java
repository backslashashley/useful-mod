package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FireworksEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldData;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldStorage;

// Priority changed to override Tweakeroo.
@Mixin(value = ClientWorld.class, priority = 1001)
public abstract class ClientWorldMixin extends World {

	private ClientWorldMixin(WorldStorage storage, WorldData data, Dimension dimension, Profiler profiler, boolean isClient) {
		super(storage, data, dimension, profiler, isClient);
	}

	@Override
	public void updateEntity(Entity entity) {
		if (Config.CLIENT_ENTITY_UPDATES.get() || entity instanceof PlayerEntity || entity instanceof FireworksEntity) {
			super.updateEntity(entity);
		}
	}

	@Override
	public float getRain(float delta) {
		return Config.SHOW_RAIN.get() ? this.prevRain + (this.rain - this.prevRain) * delta : 0F;
	}
}
