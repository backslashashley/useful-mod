package nessiesson.usefulmod;

import org.lwjgl.opengl.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.Formatting;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;
import net.ornithemc.osl.networking.api.client.ClientConnectionEvents;

public class UsefulMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Useful Mod");

	private static final StepAssistHelper STEP_ASSIST_HELPER = new StepAssistHelper();

	private static String originalTitle;

	@Override
	public void init() {
		MinecraftClientEvents.READY.register(minecraft -> {
			originalTitle = Display.getTitle();
			updateTitle();
		});
		MinecraftClientEvents.TICK_END.register(minecraft -> {
			if (minecraft.world == null) {
				return;
			}

			final LocalClientPlayerEntity player = minecraft.player;
			if (Config.NO_FALL.get() && player.fallDistance > 2F && !player.isFallFlying()) {
				player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
			}

			if (Config.FLIGHT_INERTIA_CANCELLATION.get() && player.abilities.flying) {
				final GameOptions settings = minecraft.options;
				if (!(GameOptions.isPressed(settings.forwardKey) || GameOptions.isPressed(settings.backKey) || GameOptions.isPressed(settings.leftKey) || GameOptions.isPressed(settings.rightKey))) {
					player.velocityX = player.velocityZ = 0;
				}
			}

			STEP_ASSIST_HELPER.update(player);
			if (!UsefulKeybinds.TAPE_MOUSEABLE.isEmpty()) {
				if (minecraft.screen instanceof TitleScreen) {
					drawStringInCorner("TM paused");
				} else {
					for (TimedKeyBinding key : UsefulKeybinds.TAPE_MOUSEABLE) {
						drawStringInCorner(String.format("%s %d / %d\n", key.getName().replaceFirst("^key\\.", ""), key.currentTick, key.tickDelay));
					}
				}
			}
		});
		ClientConnectionEvents.LOGIN.register(minecraft -> {
			updateTitle();
		});
		Config.register();
		UsefulKeybinds.init();
	}

	private static void updateTitle() {
		Display.setTitle(originalTitle + " - " + Minecraft.getInstance().getSession().getUsername());
	}

	static void debugFeedBack() {
		final Text tag = new TranslatableText("debug.prefix");
		final Text text = new TranslatableText("key.usefulmod.reload_audio");
		tag.setStyle(new Style().setColor(Formatting.YELLOW).setBold(true));
		final Text message = new LiteralText("").append(tag).append(" ").append(text);
		Minecraft.getInstance().gui.getChat().addMessage(message);
	}

	private static void drawStringInCorner(String msg) {
		final TextRenderer font = Minecraft.getInstance().textRenderer;
		GlStateManager.pushMatrix();
		GlStateManager.scaled(0.5, 0.5, 0.5);
		font.draw(msg, 5, 5, 0xFFFFFF, true);
		GlStateManager.popMatrix();
	}
}
