package os.rs.hooks.accessors;

import os.rs.hooks.Hooks;
import os.rs.hooks.model.RSClass;

public class Area extends RSClass {

	public Area(Object reference) {
		this.reference = reference;
		if (Hooks.sequence != null) {
			this.fields = Hooks.sequence.fields;
			this.name = Hooks.sequence.name;
			this.obfuscatedName = Hooks.sequence.obfuscatedName;
		}
	}

	public int getCameraZ() {
		return (int) getValue(getField("cameraZ"));
	}
}
