//package JavaSimpleGamePKG_v07;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Brick extends Sprite {
	boolean speed_reduce = false;
	boolean paddle_enlarge = false;
	boolean extra_life = false;
	boolean ball_at_top = false;
	
	public Brick (int _x, int _y, int _width, int _height, boolean _speed_reduce, 
			boolean _paddle_enlarge, boolean _extra_life, boolean _ball_at_top, Rectangle _Window_bounds) {
		super(_x, _y, _width, _height, _Window_bounds);
		speed_reduce = _speed_reduce;
		paddle_enlarge = _paddle_enlarge;
		extra_life = _extra_life;
		ball_at_top = _ball_at_top;
	} //Brick constructor
	
	public void Draw(Graphics2D g2d, Rectangle WindowBounds) {
		setWindow_bounds(WindowBounds);
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 5, 5);
		if (speed_reduce) {
			g2d.setColor(Color.CYAN); // hitting cyan brick will reduce the ball's speed
		}
		else if (paddle_enlarge) {
			g2d.setColor(Color.GREEN); // hitting green brick will enlarge the paddle's width
		}
		else if(extra_life) {
			g2d.setColor(Color.WHITE); // hitting white brick will give the player an extra life
		}
		else if(ball_at_top) {
			g2d.setColor(Color.BLACK);	// hitting black brick will place the ball at the top of the screen
		}
		else {
			g2d.setColor(Color.RED);
		}
		g2d.fillRoundRect(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4, 5, 5);
	} //Draw method
} //Brick class
