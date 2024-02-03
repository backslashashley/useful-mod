package nessiesson.usefulmod;

import java.util.stream.Stream;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.mixins.ChatScreenAccessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.render.world.WorldRenderer;
import net.minecraft.server.command.AbstractCommand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class AreaSelectionRenderer {

	public static void render(float partialTicks) {
		final Minecraft mc = Minecraft.getInstance();
		if (!(mc.screen instanceof ChatScreen)) {
			return;
		}

		final ChatScreen chat = (ChatScreen) mc.screen;
		final String msg = ((ChatScreenAccessor) chat).getChatField().getText().trim();
		final String[] args = msg.split(" ");
		if (args.length == 0) {
			return;
		}

		final LocalClientPlayerEntity player = mc.player;
		final double d0 = player.prevTickX + (player.x - player.prevTickX) * partialTicks;
		final double d1 = player.prevTickY + (player.y - player.prevTickY) * partialTicks;
		final double d2 = player.prevTickZ + (player.z - player.prevTickZ) * partialTicks;

		BlockPos p0 = null;
		BlockPos p1 = null;
		BlockPos p2 = null;

		// @formatter:off
		try { p0 = AbstractCommand.parseBlockPos(player, args, 1, false); } catch (Exception ignored) { /*noop*/ }
		try { p1 = AbstractCommand.parseBlockPos(player, args, 4, false); } catch (Exception ignored) { /*noop*/ }
		try { p2 = AbstractCommand.parseBlockPos(player, args, 7, false); } catch (Exception ignored) { /*noop*/ }
		// @formatter:on

		GlStateManager.depthMask(false);
		GlStateManager.disableTexture();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.lineWidth(3F);

		if (Stream.of("/clone", "/fill", "/setblock").anyMatch(s -> args[0].equals(s))) {
			if (args[0].equals("/setblock")) {
				p1 = p0;
			}

			Box origin = null;
			if (p0 != null && p1 != null) {
				origin = new Box(p0, p1);
				WorldRenderer.renderOutlineShape(origin.grow(1F, 1F, 1F).move(-d0, -d1, -d2), 0.9F, 0.9F, 0.9F, 1F);
			}

			if (args[0].equals("/clone")) {
				if (p2 != null && origin != null) {
					final Box target = new Box(p2, p2.add(origin.maxX - origin.minX + 1, origin.maxY - origin.minY + 1, origin.maxZ - origin.minZ + 1));
					WorldRenderer.renderOutlineShape(target.expand(0.005).move(-d0, -d1, -d2), 0.99F, 0.99F, 0.99F, 1F);
				}
			}
		}

		GlStateManager.lineWidth(1F);
		GlStateManager.enableTexture();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
	}
}
