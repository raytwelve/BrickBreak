//package JavaSimpleGamePKG_v07;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Sprite {
	private int x, y; // x and y location
	private int width, height;
	private Rectangle bounds;
	private Rectangle window_bounds;
	
	public Sprite (int _x, int _y, int _width, int _height, Rectangle _window_bounds) {
		x = _x;
		y = _y;
		width = _width;
		height = _height;
		bounds = new Rectangle(x, y, width, height);
		window_bounds = new Rectangle();
		window_bounds = _window_bounds;
	} //Sprite constructor
	
	public int getX() {
		return x;
	}
	public void setX(int _x) {
		x = _x;
        bounds.x = x;
	}
	public int getY() {
		return y;
        
	}
	public void setY(int _y) {
		y = _y;
        bounds.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int _width) {
		width = _width;
        bounds.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int _height) {
		height = _height;
        bounds.height = height;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle _bounds) {
		bounds = _bounds;
        setX(bounds.x);
        setY(bounds.y);
        setWidth(bounds.width);
        setHeight(bounds.height);
	}
	public Rectangle getWindow_bounds() {
		return window_bounds;
	}
	public void setWindow_bounds(Rectangle _window_bounds) {
		window_bounds = _window_bounds;
	}
	
	public void Draw(Graphics2D g2d, Rectangle WindowBounds) {
		//blank, since it will be implemented by child classes/subclasses
	}// Draw method
	
	public void Move() {
		// blank since it will be implemented by child classes/subclasses
	}// Move method
} //Sprite class
