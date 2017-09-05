//package JavaSimpleGamePKG_v07;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Ball extends Sprite {
    private int ball_x_vel, ball_y_vel;  // x velocity and y velocity of the ball.
    private boolean down, right;         // which way is the ball going.
    private int lives;
    private int bricks_destroyed;
    
    public Ball(int _x, int _y, int _width, int _height, Rectangle _Window_bounds) {
    	super(_x,  _y, _width, _height, _Window_bounds);
		ball_x_vel = _width / 4;
		ball_y_vel = _height / 3;
		down  = false;
		right = false;
		lives = 3;
        bricks_destroyed = 0;
	} // Ball constructor
    
    public int Move(int score, Paddle my_paddle, ArrayList<ArrayList<Brick>> my_bricks, int brick_rows, int brick_columns, 
    		int ran_row, int ran_col, int ran_row2, int ran_col2, int ran_row3, int ran_col3, int ran_row4, int ran_col4) {
    	getBounds().x = getX();
		getBounds().y = getY();
		
		// if right is true move ball right, otherwise (else) move it left.
		if (right) {
			setX(getX() + ball_x_vel);
		}
		else {
			setX(getX() - ball_x_vel);  // else move left.
		}
		
		// if down is true move ball down, otherwise (else) move it up.
		if (down)  setY(getY() + ball_y_vel); 
		else setY(getY() - ball_y_vel);  // else up.
	
		// is ball hitting the right side?
		if (getX() > (getWindow_bounds().width - getWidth())) { 
			right = false; // we're not going to go right any more.
			setX(getWindow_bounds().width - getWidth()); 
		}
		
		// is ball hitting the bottom side? Notice the score is decremented.
		if (getY() + getHeight() > getWindow_bounds().height) {
            GameOver();
			down  = false; // we're not going to go down any more.
//			setY(getWindow_bounds().height - getHeight()); 
		
			score -= 200; // since we hit the bottom we lose 200 point.
			lives--;
		}
		
		// is ball hitting the left side?
		if (getX() <= 0) { 
			right = true; // we won't move left any more, we're going right. 
			setX(0);
		}
		
		// is ball hitting the top side?
		if (getY() <= 0) { 
			down  = true; // we're not going to go up any more, set down to true.
			setY(0);
		}
		
		// is ball hitting the paddle?
		if (getBounds().intersects(my_paddle.getBounds())) {
			down  = false; // if it hit the paddle then we're not going to go down any more so set down to false.
			setY(my_paddle.getBounds().y - getHeight()); // set he ball y position to be well above the paddle so it won't immediately hit it again.
		}
		// is ball hitting the bricks?
		for (int i = 0; i < brick_rows; i++) {
			for (int j = 0; j < brick_columns; j++) {
				//first check if it hits from the bottom or top
				if ( getX() <= (my_bricks.get(i).get(j).getBounds().x + my_bricks.get(i).get(j).getWidth()) 
						&& getX() >= my_bricks.get(i).get(j).getBounds().x ) {
					// hit bottom
					if ( getY() <= (my_bricks.get(i).get(j).getBounds().y + my_bricks.get(i).get(j).getHeight())
							&& getY() >= (my_bricks.get(i).get(j).getBounds().y + (my_bricks.get(i).get(j).getHeight() / 2)) ) {
						down = true;
						DeleteBrick(my_bricks, i, j, ran_row, ran_col, ran_row2, ran_col2, ran_row3, ran_col3, ran_row4, ran_col4, my_paddle);
						score += 100;
					}
					// hit top
					else if ( getY() >= (my_bricks.get(i).get(j).getBounds().y - getHeight()) 
							&& getY() < (my_bricks.get(i).get(j).getBounds().y + ( getHeight() / 3)) ) {
						down = false;
						DeleteBrick(my_bricks, i, j, ran_row, ran_col, ran_row2, ran_col2, ran_row3, ran_col3, ran_row4, ran_col4, my_paddle);
						score += 100;
					}
					//determines if hit from a side
					else if ( getY() <= (my_bricks.get(i).get(j).getBounds().y + my_bricks.get(i).get(j).getHeight()) 
							&& getY() >= my_bricks.get(i).get(j).getBounds().y ) {
						//hit right
						if ( getX() <= ( my_bricks.get(i).get(j).getBounds().x + my_bricks.get(i).get(j).getWidth() ) 
								&& getX() > ( my_bricks.get(i).get(j).getBounds().x + (my_bricks.get(i).get(j).getWidth() - (getWidth() / 2)) ) ) {
							right = true;
							DeleteBrick(my_bricks, i, j, ran_row, ran_col, ran_row2, ran_col2, ran_row3, ran_col3, ran_row4, ran_col4, my_paddle);
							score += 100;
						}
						//hit left
						else if ( getX() >= ( my_bricks.get(i).get(j).getBounds().x - getWidth() )
								&& getX() < ( my_bricks.get(i).get(j).getBounds().x + (getWidth() / 2) ) ) {
							right = false;
							DeleteBrick(my_bricks, i, j, ran_row, ran_col, ran_row2, ran_col2, ran_row3, ran_col3, ran_row4, ran_col4, my_paddle);
							score += 100;
						}
					}
				}
			}
		}
		return(score);
	} // MoveBall
    
    public void Draw(Graphics2D g2d, Rectangle WindowBounds) {
    	setWindow_bounds(WindowBounds);
    	
    	g2d.setColor(Color.WHITE); // set the ball color to blue.
    	// Now draw the ball
    	g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    } // Draw
    
    public int getLives() {
    	return (lives);
    }
    public int getBall_x_vel() {
    	return (ball_x_vel);
    }
    public void setBall_x_vel(int _ball_x_vel) {
    	ball_x_vel = _ball_x_vel;
	}
    public int getBall_y_vel() {
    	return (ball_y_vel);
	}
    public void setBall_y_vel(int _ball_y_vel) {
    	ball_y_vel = _ball_y_vel;
	}
    public void setBricksDestroyed(int count)
    {
        bricks_destroyed = count;
    }
    public int getBricksDestroyed()
    {
        return bricks_destroyed;
    }
    public void GameOver() {
    	//Graphics2D g2d = new Graphics2D;
		ball_x_vel = 0;
		ball_y_vel = 0;
	}
    
    
    private void DeleteBrick(ArrayList<ArrayList<Brick>> my_bricks,int row, int col, 
    		int ran_row, int ran_col, int ran_row2, int ran_col2, int ran_row3, int ran_col3, int ran_row4, int ran_col4, Paddle my_paddle) {
    	my_bricks.get(row).get(col).setWidth(0);
		my_bricks.get(row).get(col).setHeight(0);
		Rectangle temp = new Rectangle(0, 0, 0, 0);
		my_bricks.get(row).get(col).setBounds(temp);
        bricks_destroyed++;
		
		//is it special brick?
		if(row == ran_row && col == ran_col) {	//reduce speed
			ball_x_vel -= 1;
			ball_y_vel -= 1;
//			System.out.println("hit brick at row: " + row + " col: " + col);
//			System.out.println("ball slowed");
		}
		if (row == ran_row2 && col == ran_col2) {	// double sized paddle
			Rectangle tempor = new Rectangle(my_paddle.getBounds().x, my_paddle.getBounds().y, (my_paddle.getBounds().width * 2), my_paddle.getBounds().height);
			my_paddle.setBounds(tempor);
			my_paddle.setWidth(my_paddle.getWidth() * 2);
//			System.out.println("hit brick at row: " + row + " col: " + col);
//			System.out.println("paddle enlarged");
		}
		if(row == ran_row3 && col == ran_col3) {	// an extra life
			lives++;
//			System.out.println("hit brick at row: " + row + " col: " + col);
//			System.out.println("extra life");
		}
		if(row == ran_row4 && col == ran_col4) {	// ball will be placed at top of the screen
			setX(0);
			setY(0);
			down = true;
			right = true;
//			System.out.println("hit brick at row: " + row + " col: " + col);
//			System.out.println("ball at top");
		}
    }
} // Ball class
