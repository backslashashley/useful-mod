package nessiesson.usefulmod.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

@Mixin(NbtList.class)
public interface NbtListAccessor {

	@Accessor("elements")
	List<NbtElement> getElements();

}
