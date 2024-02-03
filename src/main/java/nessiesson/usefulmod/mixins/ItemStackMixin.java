package nessiesson.usefulmod.mixins;

import java.util.Comparator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.google.common.collect.Multimap;

import nessiesson.usefulmod.config.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.living.attribute.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	private static final Comparator comp = Comparator.<NbtCompound>comparingInt(t -> t.getShort("lvl"))
		.reversed()
		.thenComparingInt(t -> t.getShort("id"));

	@Shadow
	private NbtList getEnchantments() {
		throw new UnsupportedOperationException();
	}

	@Redirect(
		method = "getTooltip",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/collect/Multimap;isEmpty()Z",
			remap = false
		)
	)
	private boolean noAttributes(Multimap<String, AttributeModifier> map) {
		return !Config.SHOW_ITEM_ATTRIBUTES.get() || map.isEmpty() || !Minecraft.getInstance().options.advancedItemTooltips;
	}

	@Redirect(
		method = "getTooltip",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;getEnchantments()Lnet/minecraft/nbt/NbtList;"
		)
	)
	private NbtList sortEnchantments(ItemStack stack) {
		final NbtList enchantments = this.getEnchantments();
		if (Config.SORT_ENCHANTMENT_TOOLTIP.get()) {
			((NbtListAccessor) enchantments).getElements().sort(comp);
		}
		return enchantments;
	}
}
