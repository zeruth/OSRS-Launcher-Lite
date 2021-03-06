package hooks.accessors;

import java.util.ArrayList;

import hooks.Hooks;
import hooks.model.RSClass;

public class Region extends RSClass {

	GameObject[] objects;
	Tile[] tiles;
	Object[][][] rsTiles;

	Object[] tempObjects;
	Tile tempTile;

	public Region(Object reference) {
		this.reference = reference;
		if (Hooks.region != null) {
			this.fields = Hooks.region.fields;
			this.name = Hooks.region.name;
			this.obfuscatedName = Hooks.region.obfuscatedName;
		}
	}

	public GameObject[] getObjects() {
		this.tempObjects = (Object[]) getValue(getField("objects"));
		int i = 0;
		this.objects = new GameObject[this.tempObjects.length];
		for (Object o : this.tempObjects) {
			if (o != null) {
				this.objects[i] = new GameObject(o);
				i++;
			}
		}
		return this.objects;
	}

	public Tile getTile(int x, int y) {
		if (Hooks.client != null) {
			int bx = Client.getBaseX();
			int by = Client.getBaseY();

			int ax = x - bx;
			int ay = y - by;

			System.out.println(bx + " " + by);
			this.rsTiles = (Object[][][]) getValue(getField("tiles"));

			if (this.rsTiles[Client.getPlane()][ax][ay] != null) {
				this.tempTile = new Tile(this.rsTiles[Client.getPlane()][ax][ay]);
			}
			return this.tempTile;
		}

		return null;

	}

	public ArrayList<Tile> getTiles() {
		ArrayList<Tile> tiles = new ArrayList<>();
		int REGION_SIZE = 104;
		this.rsTiles = (Object[][][]) getValue(getField("tiles"));
		int z = Client.getPlane();
		for (int x = 0; x < REGION_SIZE; ++x) {
			for (int y = 0; y < REGION_SIZE; ++y) {
				Object tile = this.rsTiles[z][x][y];
				if (tile == null) {
					continue;
				}
				tiles.add(new Tile(tile));
			}
		}
		return tiles;
	}

}
