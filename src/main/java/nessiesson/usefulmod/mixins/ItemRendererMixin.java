package nessiesson.usefulmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FireworksItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

	private boolean isPerfectBasicToolBase(ItemStack stack) {
		return EnchantmentHelper.getLevel(Enchantments.UNBREAKING, stack) == 3 && EnchantmentHelper.getLevel(Enchantments.MENDING, stack) > 0;
	}

	private boolean isPerfectToolBase(ItemStack stack) {
		return this.isPerfectBasicToolBase(stack) && EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack) == 5;
	}

	private boolean isPerfectSilk(ItemStack stack) {
		return this.isPerfectToolBase(stack) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0;
	}

	private boolean isPerfectFortune(ItemStack stack) {
		return this.isPerfectToolBase(stack) && EnchantmentHelper.getLevel(Enchantments.FORTUNE, stack) == 3;
	}

	private boolean isPerfectSilkAxe(ItemStack stack) {
		return this.isPerfectSilk(stack) && EnchantmentHelper.getLevel(Enchantments.SHARPNESS, stack) == 5;
	}

	private boolean isPerfectFortuneAxe(ItemStack stack) {
		return this.isPerfectFortune(stack) && EnchantmentHelper.getLevel(Enchantments.SHARPNESS, stack) == 5;
	}

	private boolean isPerfectHoe(ItemStack stack) {
		return this.isPerfectBasicToolBase(stack);
	}

	private boolean isPerfectSword(ItemStack stack) {
		return this.isPerfectBasicToolBase(stack)
				&& EnchantmentHelper.getLevel(Enchantments.SHARPNESS, stack) == 5
				&& EnchantmentHelper.getLevel(Enchantments.LOOTING, stack) == 3
				&& EnchantmentHelper.getLevel(Enchantments.SWEEPING, stack) == 3
				&& EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, stack) == 2;
	}

	@Inject(
		method = "renderGuiItemDecorations",
		at = @At(
			value = "TAIL"
		)
	)
	private void postRenderGuiItemDecorations(TextRenderer textRenderer, ItemStack stack, int x, int y, String stackSizeText, CallbackInfo ci) {
		if (!Config.SHOW_IDEAL_TOOL_MARKER.get() || stack.isEmpty()) {
			return;
		}

		final Item item = stack.getItem();
		String marker = "";
		if (item instanceof AxeItem) {
			if (this.isPerfectSilkAxe(stack)) {
				marker = "S";
			} else if (this.isPerfectFortuneAxe(stack)) {
				marker = "F";
			}
		} else if (item instanceof ElytraItem && this.isPerfectBasicToolBase(stack)) {
			marker = "P";
		} else if (item instanceof FireworksItem && stack.hasNbt()) {
			final NbtCompound itemData = stack.getNbt();
			if (itemData != null) {
				final NbtCompound fireworks = itemData.getCompound("Fireworks");
				marker = String.valueOf(fireworks.getByte("Flight"));
			}
		} else if (item instanceof FlintAndSteelItem && this.isPerfectBasicToolBase(stack)) {
			marker = "P";
		} else if (item instanceof HoeItem && this.isPerfectHoe(stack)) {
			marker = "P";
		} else if (item instanceof PickaxeItem) {
			if (this.isPerfectSilk(stack)) {
				marker = "S";
			} else if (this.isPerfectFortune(stack)) {
				marker = "F";
			}
		} else if (item instanceof ShearsItem && this.isPerfectToolBase(stack)) {
			marker = "P";
		} else if (item instanceof ShovelItem) {
			if (this.isPerfectSilk(stack)) {
				marker = "S";
			} else if (this.isPerfectFortune(stack)) {
				marker = "F";
			}
		} else if (item instanceof SwordItem && this.isPerfectSword(stack)) {
			marker = "P";
		} else if (item instanceof FireworksItem) {
			final NbtCompound compound = stack.getNbt("Fireworks");
			if (compound != null && compound.contains("Flight", 99)) {
				marker = String.valueOf(compound.getByte("Flight"));
			}
		}

		if (!marker.equals("")) {
			GlStateManager.disableLighting();
			GlStateManager.disableDepthTest();
			GlStateManager.disableBlend();
			GlStateManager.pushMatrix();
			final float f = 0.5F;
			GlStateManager.scalef(f, f, f);
			textRenderer.drawWithShadow(marker, (x) / f, (y + 12) / f, 0xFFFFFF);
			GlStateManager.popMatrix();
			GlStateManager.enableLighting();
			GlStateManager.enableDepthTest();
			GlStateManager.enableBlend();
		}
	}
}
