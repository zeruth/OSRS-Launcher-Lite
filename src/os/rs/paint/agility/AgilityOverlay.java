package os.rs.paint.agility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.util.Map;

import os.rs.game.Game;
import os.rs.game.Settings;
import os.rs.hooks.Hooks;
import os.rs.hooks.accessors.Client;
import os.rs.hooks.accessors.DecorativeObject;
import os.rs.hooks.accessors.GameObject;
import os.rs.hooks.accessors.GroundObject;
import os.rs.paint.listeners.PaintListener;

public class AgilityOverlay implements PaintListener {

	private Map<RenderingHints.Key, ?> hints;

	public AgilityOverlay(Client game) {
	}

	@Override
	public void onRepaint(Graphics g) {
		g.setColor(Color.YELLOW);
		Color fillColor = new Color(Color.YELLOW.getBlue(), Color.YELLOW.getGreen(), Color.YELLOW.getRed(), 50);
		if (Settings.SHOW_AGILITY_OVERLAY == true) {
			if (Game.gameObjects != null)
				for (GameObject go : Game.gameObjects.values()) {
					if (go.getPlane() != Hooks.client.getPlane())
						continue;
					if (go != null) {
						switch (go.getID()) {

						// Al Kharid course
						case 10355:
						case 10357:
						case 10352:
							// Varrock course
						case 10587:
						case 10642:
						case 10777:
						case 10778:
						case 10779:
						case 10780:
						case 10781:
						case 10817:
							// Canifis course;
						case 10819:
						case 10820:
						case 10821:
						case 10822:
						case 10823:
						case 10828:
						case 10831:
						case 10832:
							g.setColor(Color.YELLOW);
							;
							Graphics2D g2 = (Graphics2D) g;
							Area a = go.getClickbox();
							if (a != null) {
								g2.draw(a);
								g2.setPaint(fillColor);
								g2.fill(a);
							}
						}
					}
				}

			for (DecorativeObject d : Game.decorativeObjects.values()) {

				if (d != null) {
					switch (d.getID()) {

					// Al Kharid course
					case 10093:
					case 10094:
						// Varrock course
					case 10586:
						if (d.getPlane() != Hooks.client.getPlane()) {
							continue;
						} else {

							System.out.println("" + d.getPlane() + " " + Hooks.client.getPlane());
						}
						g.setColor(Color.YELLOW);
						;
						Graphics2D g2 = (Graphics2D) g;
						Area a = d.getClickbox();
						if (a != null) {

							g2.draw(a);
							g2.setPaint(fillColor);
							g2.fill(a);
						}
					}

				}

			}

			for (GroundObject go : Game.groundObjects.values()) {
				if (go != null) {
					switch (go.getID()) {

					// Al Kharid Course
					case 10284:
					case 10527:
					case 10583:
					case 10584:
						if (go.getPlane() != Hooks.client.getPlane()) {
							System.out.println("" + go.getPlane() + " " + Hooks.client.getPlane());
							continue;
						}
						g.setColor(Color.YELLOW);
						;
						Graphics2D g2 = (Graphics2D) g;
						Area a = go.getClickbox();
						if (a != null) {
							g2.draw(a);
							g2.setPaint(fillColor);
							g2.fill(a);
						}
					}
				}
			}

			/*
			 * if (OSRSLauncher.loaderWindow.getWidth()<=850) { RenderingHints rh = new
			 * RenderingHints( RenderingHints.KEY_TEXT_ANTIALIASING,
			 * RenderingHints.VALUE_TEXT_ANTIALIAS_ON); int radiusX = 50; int centerX =
			 * ((OSRSLauncher.loaderWindow.getWidth()-(radiusX))/2)-50; int centerY = 5; int
			 * radiusYY = 50; int startAngle = 90; int length = -359; Circle c = new
			 * Circle(centerX, centerY, radiusYY); Graphics2D g22 = (Graphics2D) g;
			 * g.setColor(Color.black); g22.setRenderingHints(rh); g22.setStroke(new
			 * BasicStroke(3)); g22.setFont(new Font(g.getFont().getFontName(), Font.PLAIN,
			 * 15)); g22.fillOval(centerX, centerY, radiusYY, radiusX);
			 * g.setColor(Color.red); int lvl = Hooks.client.getRealSkillLevels()[16];
			 * g22.drawString(Integer.toString(lvl), centerX+16, 20); Color fillColor2 = new
			 * Color(Color.RED.getRed(), Color.red.getGreen(), Color.red.getBlue(), 50);
			 * g22.drawArc(centerX, centerY, radiusX, radiusYY, startAngle, -300);
			 * g.setColor(fillColor2); g22.setStroke(new BasicStroke()); } else { int
			 * radiusX = 50; int centerX =
			 * ((OSRSLauncher.loaderWindow.getWidth()-(radiusX))/2); int centerY = 5; int
			 * radiusYY = 50; int startAngle = 90; int length = -359; Circle c = new
			 * Circle(centerX, centerY, radiusYY); Graphics2D g22 = (Graphics2D) g;
			 * g.setColor(Color.black); g22.fillOval(centerX, centerY, radiusYY, radiusX);
			 * g.setColor(Color.red); int lvl = Hooks.client.getRealSkillLevels()[16];
			 * g22.drawString(Integer.toString(lvl), centerX, 20); Color fillColor2 = new
			 * Color(Color.RED.getRed(), Color.red.getGreen(), Color.red.getBlue(), 50);
			 * g.drawArc(centerX, centerY, radiusX, radiusYY, startAngle, length);
			 * g.setColor(fillColor2); }
			 * 
			 * } }
			 */

		}

	}

}