package nessiesson.usefulmod;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;

import nessiesson.usefulmod.config.Config;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShulkerBoxItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.Identifier;
import net.minecraft.util.DefaultedList;

// Modified version of the shulkerbox display from Vazkii's Quark Forgemod.
public class ShulkerBoxDisplay {

	private static final Identifier WIDGET_RESOURCE = new Identifier("usefulmod", "textures/shulker_widget.png");

	public static void render(ItemStack stack, int x, int y, GuiElement gui) {
		if (!Config.SHULKER_BOX_DISPLAY.get()) {
			return;
		}

		if (stack != null && stack.getItem() instanceof ShulkerBoxItem && stack.hasNbt() && Screen.isShiftDown()) {
			NbtCompound cmp = getCompoundOrNull(stack);
			if (cmp != null && cmp.contains("Items", 9)) {
				final int texWidth = 172;
				final int texHeight = 64;

				final int dy = y - texHeight - 18;

				GlStateManager.pushMatrix();
				Lighting.turnOnGui();
				GlStateManager.enableRescaleNormal();
				GlStateManager.translatef(0F, 0F, 700F);

				final Minecraft mc = Minecraft.getInstance();
				mc.getTextureManager().bind(WIDGET_RESOURCE);

				final DyeColor dye = ((ShulkerBoxBlock) ((BlockItem) stack.getItem()).getBlock()).getColor();
				final float[] colours = dye.getTextureDiffuseColors();
				GlStateManager.color3f(colours[0], colours[1], colours[2]);

				gui.drawTexture(x, dy, 0, 0, texWidth, texHeight);

				DefaultedList<ItemStack> itemList = DefaultedList.of(27, ItemStack.EMPTY);
				InventoryHelper.fromNbt(cmp, itemList);

				ItemRenderer render = mc.getItemRenderer();

				GlStateManager.enableDepthTest();
				int i = 0;
				for (ItemStack itemstack : itemList) {
					if (!itemstack.isEmpty()) {
						final int xp = x + 6 + (i % 9) * 18;
						final int yp = dy + 6 + (i / 9) * 18;

						render.renderGuiItem(itemstack, xp, yp);
						render.renderGuiItemDecorations(mc.textRenderer, itemstack, xp, yp);
					}

					i++;
				}

				GlStateManager.disableDepthTest();
				GlStateManager.popMatrix();
			}
		}
	}

	public static void addShulkerBoxTooltip(ItemStack stack, List<String> tooltip) {
		if (!Config.SHULKER_BOX_DISPLAY.get()) {
			return;
		}

		if (!stack.isEmpty() && stack.getItem() instanceof ShulkerBoxItem && stack.hasNbt()) {
			final NbtCompound cmp = getCompoundOrNull(stack);
			if (cmp != null && cmp.contains("Items", 9)) {

				if (!Screen.isShiftDown()) {
					tooltip.add(1, I18n.translate("usefulmod.ui.mention_shift"));
				}
			}
		}
	}

	private static NbtCompound getCompoundOrNull(ItemStack stack) {
		final NbtCompound compound = stack.getNbt();
		if (compound != null && compound.contains("BlockEntityTag")) {
			return compound.getCompound("BlockEntityTag");
		} else {
			return null;
		}
	}
}
