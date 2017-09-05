//package JavaSimpleGamePKG_v07;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Paddle extends Sprite {
	private int half_paddle_width;
	
	public Paddle (int _width, int _height, Rectangle _Window_bounds) {
		super((_Window_bounds.width / 2) - (_width / 2), 
				_Window_bounds.height - 40, _width, _height, _Window_bounds);
		half_paddle_width = _width / 2; // pre-calculate half the paddles length, since this value is used over and over.
	} // Paddle constructor
	
	public void Move(int x) {
		// x is the mouse x location.
		if ((x - half_paddle_width) <= 0) // is the mouse too far to the left of the window?
			setX(0); // set the paddle location to the left most side of the window.
		else if ((x + half_paddle_width) >= getWindow_bounds().width) // is the paddle too far to the right?
			setX(getWindow_bounds().width - getWidth()); // set the paddle location to the right side of the window
		else
			setX(x - half_paddle_width); // we're somewhere in the middle, so draw the paddle centered at the mouse X location
		
		// update paddle boundaries Rectangle with the new x,y location of paddle
		// the bounds are used for collision checking later on.
		getBounds().x = getX();
		getBounds().y = getY();
	} // Move method
	
	public void Draw(Graphics2D g2d, Rectangle WindowBounds) {
		setWindow_bounds(WindowBounds);
		setY(WindowBounds.height - (getHeight() * 2)); // fix the y location of the paddle in case the user has resized the window.
		g2d.setColor(Color.ORANGE); 
		g2d.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
		g2d.setColor(Color.MAGENTA);
		g2d.fillRoundRect(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4, 10, 10);
	} // Draw method
} // Paddle class
