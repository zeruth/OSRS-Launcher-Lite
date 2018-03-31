package os.rs.paint.listeners;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import os.rs.game.OSRSLauncher;
import os.rs.game.Settings;
import os.rs.hooks.Hooks;
import os.rs.hooks.accessors.Client;

public class FpsPaintListener implements PaintListener {

	public FpsPaintListener(Client game) {
	}

	@Override
	public void onRepaint(Graphics g) {
		if (Settings.SHOW_FPS) {
			if (Client.isLoaded()) {
				FontMetrics fm = g.getFontMetrics();
				String fps = "" + Hooks.gameEngine.getFps();
				int x = OSRSLauncher.loaderWindow.getWidth() - fm.stringWidth(fps) - 17;

				int j = Hooks.gameEngine.getFps();
				if (j >= 50) {
					g.setColor(Color.cyan);
				} else if (j >= 40 && j < 50) {
					g.setColor(Color.green);
				} else if (j >= 30 && j < 40) {
					g.setColor(Color.yellow);
				} else {
					g.setColor(Color.red);
				}
				g.drawString(fps, x, 15);
			}
		}
	}

}
