package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.spectator.TeleportToPlayerCategory;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.world.GameMode;

@Mixin(TeleportToPlayerCategory.class)
public class TeleportToPlayerCategoryMixin {

	@Redirect(
		method = "<init>(Ljava/util/Collection;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/PlayerInfo;getGameMode()Lnet/minecraft/world/GameMode;"
		)
	)
	private GameMode mc125157(PlayerInfo info) {
		return info.getProfile().getName().equals(Minecraft.getInstance().getSession().getUsername()) ? info.getGameMode() : GameMode.NOT_SET;
	}
}
