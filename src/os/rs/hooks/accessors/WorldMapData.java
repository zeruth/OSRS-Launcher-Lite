package os.rs.hooks.accessors;

import os.rs.hooks.Hooks;
import os.rs.hooks.model.RSClass;

public class WorldMapData extends RSClass {

	public WorldMapData(Object reference) {
		this.reference = reference;
		if (Hooks.worldMapData != null) {
			this.fields = Hooks.worldMapData.fields;
			this.name = Hooks.worldMapData.name;
			this.obfuscatedName = Hooks.worldMapData.obfuscatedName;
		}
	}
}
