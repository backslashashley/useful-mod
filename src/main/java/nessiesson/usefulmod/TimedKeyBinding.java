package nessiesson.usefulmod;

import net.minecraft.client.options.KeyBinding;

public class TimedKeyBinding extends KeyBinding {

	public int tickDelay = 10;
	public int currentTick = 0;

	public TimedKeyBinding(String name, int keyCode, String category) {
		super(name, keyCode, category);
	}

	public void tick() {
		if (this.currentTick++ >= this.tickDelay) {
			this.currentTick = 0;
			if (this.tickDelay == 0) {
				KeyBinding.set(this.getKeyCode(), true);
			}

			KeyBinding.click(this.getKeyCode());
		}
	}
}
