package os.rs.game;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import os.rs.discord.DiscordManager;
import os.rs.hooks.Hooks;
import os.rs.hooks.accessors.Client;
import os.rs.hooks.accessors.DecorativeObject;
import os.rs.hooks.accessors.GameObject;
import os.rs.hooks.accessors.GroundObject;
import os.rs.hooks.accessors.Region;
import os.rs.hooks.accessors.Tile;
import os.rs.paint.Console;
import os.rs.paint.agility.AgilityObjects;
import os.rs.paint.agility.AgilityOverlay;
import os.rs.paint.listeners.ActorNames;
import os.rs.paint.listeners.DecorativeObjects;
import os.rs.paint.listeners.FpsPaintListener;
import os.rs.paint.listeners.GameObjects;
import os.rs.paint.listeners.GroundObjects;
import os.rs.paint.listeners.InputListeners;
import os.rs.paint.listeners.PaintListener;
import os.rs.paint.listeners.TextPaintListener;
import os.rs.paint.listeners.WallObjects;
import os.rs.reflection.JarLoader;

public class Game extends Canvas implements Runnable {

	public static Applet applet;
	public static JarLoader jarLoader;
	private static final long serialVersionUID = 1L;
	public boolean debug;
	private BufferedImage gameImage;
	private InputListeners inputListeners;
	public boolean loading = true;
	public BufferedImage paintImage;
	private List<PaintListener> paintListeners;
	private Thread paintThread;
	private List<TextPaintListener> textPaintListeners;
	public Region oldRegion = new Region(null);
	public int basex = 0, basey = 0;
	public static HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
	public static HashMap<Integer, DecorativeObject> decorativeObjects = new HashMap<Integer, DecorativeObject>();
	public static HashMap<Integer, GroundObject> groundObjects = new HashMap<Integer, GroundObject>();
	public ThreadGroup threadGroup;
	public AgilityObjects agilityObjects = new AgilityObjects();
	int[] i = new int[30000];

	public Game() {
		threadGroup = new ThreadGroup("RSGame");
		gameImage = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);
		paintImage = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					jarLoader = new JarLoader();

					Class<?> c = jarLoader.loadClass("client");
					applet = (Applet) c.newInstance();
					applet.setStub(jarLoader.getAppletStub());
					applet.init();
					applet.setSize(OSRSLauncher.loaderWindow.getContentPane().getSize());
					applet.start();

					// Sleeping to let the game load
					Thread.sleep(1000);
					Hooks.client = new Client(applet);
					new Hooks();
					while (!Client.isLoaded()) {
						Thread.sleep(10);
					}

					DiscordManager.run();

					inputListeners = new InputListeners(true, applet);
					requestFocus();
					addMouseListener(inputListeners);
					addMouseMotionListener(inputListeners);
					addMouseWheelListener(inputListeners);
					addKeyListener(inputListeners);
					addFocusListener(inputListeners);

					paintListeners.add(new FpsPaintListener(Hooks.client));
					paintListeners.add(new ActorNames(Hooks.client));
					paintListeners.add(new GroundObjects(Hooks.client));
					paintListeners.add(new GameObjects(Hooks.client));
					paintListeners.add(new DecorativeObjects(Hooks.client));
					paintListeners.add(new WallObjects(Hooks.client));

					paintListeners.add(new AgilityOverlay(Hooks.client));
				} catch (Exception e) {
					e.printStackTrace();
				}

				loading = false;
				System.out.println("[OSRS] Init Complete.");

			}
		}).start();

		paintListeners = new ArrayList<PaintListener>();
		textPaintListeners = new ArrayList<TextPaintListener>();

		paintThread = new Thread(threadGroup, this, "paint");
		paintThread.start();

		new Thread(threadGroup, new Runnable() {

			@Override
			public void run() {
				while (true != false) {
					if (Hooks.client != null) {
						if (Client.isLoaded())
							if (Hooks.client.isLoggedIn()) {
								if (basex != Hooks.client.getBaseX() || basey != Hooks.client.getBaseY()) {
									resetObjects();
									basex = Hooks.client.getBaseX();
									basey = Hooks.client.getBaseY();
								}
								if (Hooks.client.getRegion() != null) {
									if (Hooks.client.getRegion().getTiles() != null) {
										for (Tile t : Hooks.client.getRegion().getTiles()) {
											for (GameObject go : t.getObjects()) {
												if (go != null) {
													if (gameObjects.containsKey(go.getID())) {
														continue;
													}
													switch (go.getID()) {

													// Al Kharid course
													case 10355:
														agilityObjects.AL_KHARID_3 = go;
														addGameObject(agilityObjects.AL_KHARID_3);
														break;
													case 10357:
														agilityObjects.AL_KHARID_5 = go;
														addGameObject(agilityObjects.AL_KHARID_3);
														break;
													case 10352:
														agilityObjects.AL_KHARID_8 = go;
														addGameObject(agilityObjects.AL_KHARID_8);
														break;

													// Varrock

													case 10587:
														agilityObjects.VARROCK_2 = go;
														addGameObject(agilityObjects.VARROCK_2);
														break;
													case 10642:
														agilityObjects.VARROCK_3 = go;
														addGameObject(agilityObjects.VARROCK_3);
														break;
													case 10777:
														agilityObjects.VARROCK_4 = go;
														addGameObject(agilityObjects.VARROCK_4);
														break;
													case 10778:
														agilityObjects.VARROCK_5 = go;
														addGameObject(agilityObjects.VARROCK_5);
														break;
													case 10779:
														agilityObjects.VARROCK_6 = go;
														addGameObject(agilityObjects.VARROCK_6);
														break;
													case 10780:
														agilityObjects.VARROCK_7 = go;
														addGameObject(agilityObjects.VARROCK_7);
														break;
													case 10781:
														agilityObjects.VARROCK_8 = go;
														addGameObject(agilityObjects.VARROCK_8);
														break;
													case 10817:
														agilityObjects.VARROCK_9 = go;
														addGameObject(agilityObjects.VARROCK_9);
														break;

													// Canifis
													case 10819:
														agilityObjects.CANIFIS_1 = go;
														addGameObject(agilityObjects.CANIFIS_1);
														break;
													case 10820:
														agilityObjects.CANIFIS_2 = go;
														addGameObject(agilityObjects.CANIFIS_2);
														break;
													case 10821:
														agilityObjects.CANIFIS_3 = go;
														addGameObject(agilityObjects.CANIFIS_3);
														break;
													case 10828:
														agilityObjects.CANIFIS_4 = go;
														addGameObject(agilityObjects.CANIFIS_4);
														break;
													case 10822:
														agilityObjects.CANIFIS_5 = go;
														addGameObject(agilityObjects.CANIFIS_5);
														break;
													case 10831:
														agilityObjects.CANIFIS_6 = go;
														addGameObject(agilityObjects.CANIFIS_6);
														break;
													case 10823:
														agilityObjects.CANIFIS_7 = go;
														addGameObject(agilityObjects.CANIFIS_7);
														break;
													case 10832:
														agilityObjects.CANIFIS_8 = go;
														addGameObject(agilityObjects.CANIFIS_8);
														break;
													}
												}
											}

											if (t != null) {
												if (t.getDecorativeObject() != null) {
													if (decorativeObjects
															.containsKey(t.getDecorativeObject().getID())) {
														continue;
													}
													switch (t.getDecorativeObject().getID()) {

													// Al Kharid course
													case 10093:
														agilityObjects.AL_KHARID_1 = t.getDecorativeObject();
														addDecorativeObject(agilityObjects.AL_KHARID_1);
														break;
													case 10094:
														agilityObjects.AL_KHARID_6 = t.getDecorativeObject();
														agilityObjects.AL_KHARID_6.plane = 2;
														addDecorativeObject(agilityObjects.AL_KHARID_6);
														break;

													// Varrock course
													case 10586:
														agilityObjects.VARROCK_1 = t.getDecorativeObject();
														addDecorativeObject(agilityObjects.VARROCK_1);
														break;
													}
												}
											}

											if (t != null) {
												if (t.getGroundObject() != null) {
													if (groundObjects.containsKey(t.getGroundObject().getID())) {
														continue;
													}
													if (t.getGroundObject() != null)
														switch (t.getGroundObject().getID()) {
														// Al Kharid Course
														case 10284:
															agilityObjects.AL_KHARID_2 = t.getGroundObject();
															agilityObjects.AL_KHARID_2.plane = 3;
															addGroundObject(agilityObjects.AL_KHARID_2);
															break;
														case 10527:
															agilityObjects.AL_KHARID_4 = t.getGroundObject();
															agilityObjects.AL_KHARID_4.plane = 3;
															addGroundObject(agilityObjects.AL_KHARID_4);
															break;
														case 10583:
															agilityObjects.AL_KHARID_7 = t.getGroundObject();
															agilityObjects.AL_KHARID_7.plane = 3;
															addGroundObject(agilityObjects.AL_KHARID_7);
															break;

														}
												}
											}

										}
									}
								}
							} else {
								resetObjects();
							}

					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();

		this.setSize(765, 503);
	}

	public void addGameObject(GameObject d) {
		if (i[d.getID()] != 1) {
			gameObjects.put(d.getID(), d);
			i[d.getID()] = 1;
		}
	}

	public void addDecorativeObject(DecorativeObject d) {
		if (i[d.getID()] != 1) {
			decorativeObjects.put(d.getID(), d);
			i[d.getID()] = 1;
		}
	}

	public void addGroundObject(GroundObject d) {
		if (i[d.getID()] != 1) {
			groundObjects.put(d.getID(), d);
			i[d.getID()] = 1;
		}
	}

	public void focus() {
		requestFocus();
	}

	public void resetObjects() {
		i = new int[30000];
		gameObjects = new HashMap<Integer, GameObject>();
		decorativeObjects = new HashMap<Integer, DecorativeObject>();
	}

	public Graphics gamePaint() {
		return gameImage.getGraphics();
	}

	public Applet getApplet() {
		return applet;
	}

	@Override
	public void paint(Graphics g) {
		try {
			if (!this.isVisible())
				return;

			if (OSRSLauncher.loaderWindow.getHeight() != gameImage.getHeight()
					|| OSRSLauncher.loaderWindow.getWidth() != gameImage.getWidth()) {
				gameImage = new BufferedImage(OSRSLauncher.loaderWindow.getWidth(),
						OSRSLauncher.loaderWindow.getHeight(), BufferedImage.TYPE_INT_RGB);
				paintImage = new BufferedImage(OSRSLauncher.loaderWindow.getWidth(),
						OSRSLauncher.loaderWindow.getHeight(), BufferedImage.TYPE_INT_RGB);
				if (applet != null)
					applet.setSize(OSRSLauncher.loaderWindow.getContentPane().getSize());
			}

			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Rectangle r = g2d.getClipBounds();
			if (loading) {
				g2d.drawImage(gameImage, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width,
						r.y + r.height, null);
				return;
			}

			if (gameImage != null) {
				paintImage.flush();

				Graphics paintGraphics = paintImage.getGraphics();
				paintGraphics.drawImage(gameImage, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width,
						r.y + r.height, null);

				for (PaintListener pl : paintListeners) {
					pl.onRepaint(paintGraphics);
				}

				for (TextPaintListener tpl : textPaintListeners) {
					int y = 40;
					paintGraphics.setColor(Color.cyan);
					String[] lines = tpl.onTextRepaint();
					if (lines != null) {
						for (String line : lines) {
							if (line == null)
								continue;
							paintGraphics.drawString(line, 20, y);
							y += 15;
						}
					}
				}

				g2d.drawImage(paintImage, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width,
						r.y + r.height, null);
				paintGraphics.dispose();
			}
		} catch (RasterFormatException ignored) {
		}
	}

	@Override
	public void paintAll(Graphics g) {
		paint(g);
	}

	@Override
	public void repaint(int x, int y, int width, int height) {
		super.repaint(0, 0, OSRSLauncher.loaderWindow.getWidth(), OSRSLauncher.loaderWindow.getHeight());
	}

	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		super.repaint(tm, 0, 0, OSRSLauncher.loaderWindow.getWidth(), getHeight());
	}

	@Override
	public void run() {
		while (true)
			try {
				if (this.isShowing()) {
					repaint();
					Thread.sleep(1000 / 50);
					if (!hasFocus() && !Console.isOpen())
						requestFocus();
				} else {
					Thread.sleep(300);
				}
			} catch (InterruptedException ignored) {
			}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

}
