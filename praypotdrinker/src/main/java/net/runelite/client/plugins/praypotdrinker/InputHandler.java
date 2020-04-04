package net.runelite.client.plugins.praypotdrinker;

import net.runelite.api.Client;
import net.runelite.api.Point;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputHandler {
	public static void leftClick(Client client, int x, int y)
	{
		assert !client.isClientThread();

		Point pos = new Point(x, y);

		if (client.isStretchedEnabled()) {
			final Dimension stretched = client.getStretchedDimensions();
			final Dimension real = client.getRealDimensions();
			final double width = (stretched.width / real.getWidth());
			final double height = (stretched.height / real.getHeight());
			final Point point = new Point((int) (pos.getX() * width), (int) (pos.getY() * height));
			moveMouse(client, point);
			clickMouse(client, point, 1);
			return;
		}

		moveMouse(client, pos);
		clickMouse(client, pos, 1);
	}

	public static void leftClick(Client client, Point pos)
	{
		assert !client.isClientThread();

		if (client.isStretchedEnabled()) {
			final Dimension stretched = client.getStretchedDimensions();
			final Dimension real = client.getRealDimensions();
			final double width = (stretched.width / real.getWidth());
			final double height = (stretched.height / real.getHeight());
			final Point point = new Point((int) (pos.getX() * width), (int) (pos.getY() * height));
			moveMouse(client, point);
			clickMouse(client, point, 1);
			return;
		}

		moveMouse(client, pos);
		clickMouse(client, pos, 1);
	}
	
	private static MouseEvent createEvent(Client client, Point p, int id)
	{
		return new MouseEvent(client.getCanvas(), id, System.currentTimeMillis(), 0, p.getX(), p.getY(), 0, false);
	}
	
	private static MouseEvent createEvent(Client client, Point p, int id, int button)
	{
		return new MouseEvent(client.getCanvas(), id, System.currentTimeMillis(), 0, p.getX(), p.getY(), 1, false, button);
	}
	
	private static void clickMouse(Client client, Point p, int button)
	{
		client.getCanvas().dispatchEvent(createEvent(client, p, 501, button));
		client.getCanvas().dispatchEvent(createEvent(client, p, 502, button));
		client.getCanvas().dispatchEvent(createEvent(client, p, 500, button));
	}
	
	private static void moveMouse(Client client, Point p)
	{
		client.getCanvas().dispatchEvent(createEvent(client, p, 504));
		client.getCanvas().dispatchEvent(createEvent(client, p, 505));
		client.getCanvas().dispatchEvent(createEvent(client, p, 503));

		try
		{
			//sleep for 2 frames (just to be sure we get the menu option loaded)
			Thread.sleep(client.getFPS() / 5);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	public static Point getClickPoint(Rectangle rect)
	{
		int rand = (Math.random() <= 0.5) ? 1 : 2;
		int x = (int) (rect.getX() + (rand * 3) + rect.getWidth() / 2);
		int y = (int) (rect.getY() + (rand * 3) + rect.getHeight() / 2);
		return new Point(x, y);
	}
	
	public static void sendKey(Component target, int key)
	{
		target.dispatchEvent(new KeyEvent(target, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, key, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD));
		target.dispatchEvent(new KeyEvent(target, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, key, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD));
	}
	
	public static void pressKey(Component target, int key)
	{
		target.dispatchEvent(new KeyEvent(target, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, key, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD));
	}
	
	public static void releaseKey(Component target, int key)
	{
		target.dispatchEvent(new KeyEvent(target, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, key, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD));
	}
}
