/* Coded by: Raymond Lam
 * Assignment 11 Breakout game
 * Due: December 9, 2013
 * 
 * Assignment guidelines:
 * 	1. You must start with the Assignment11_BreakoutGame.zip View in a new windowfile and add to it.
 * 	2. Add a Brick class for bricks the ball will crash into.
 * 	3. Make a 2 dimensional Array or ArrayList of those Bricks.
 * 	4. Display the bricks in the window.
 * 	5. Check and see if the ball hits a brick and if it does, make the brick go away and add to the score displayed on the top left.
 * For extra credit:
 * 	1. Make the bricks change colors a few times before dying.
 * 	2. Make a special brick that if the ball hits it will reduce the speed of the ball.
 * 	3. Make a special brick that if the ball hits it the width of the paddle is lengthened.
 * 	4. Make a special brick that if the ball hits the brick there is another ball generated, so now you have 2 balls in play.
 * 	5. Add sound.
 * 	6. Come up with your own cool extra feature.
 * 
 * Game details:
 * 	1.	hitting each brick will add 200 points to the total
 *	2.	hitting the bottom of the screen will result in losing a life
 * 		when player is out of lives, the game is over
 * 	3.	hitting cyan brick will reduce the ball's speed
 * 	4.	hitting green brick will enlarge the paddle's width
 * 	5.	hitting green brick will give the player an extra life
 * 	6.	hitting black brick will place the ball at the top of the screen
 */

//package JavaSimpleGamePKG_v07;

import java.awt.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Game extends JPanel implements MouseMotionListener {
	public Rectangle my_window; // window info
	public Rectangle bounds;    // borders of the window.
	public JFrame frame;        // graphics go into this JFrame.
	public MyGameTimer my_game_timer; // the Timer that runs the game.
	public Ball my_ball;
	private int ball_diameter = 18;
	public Paddle my_paddle;
	public ArrayList<ArrayList<Brick>> my_bricks;
	private int brick_rows = 8;
	private int brick_col = 12;
	int brick_width = 75;
	int brick_height = 25;
	private int Score = 0;
	private int Lives = 3;
	private int ran_row, ran_row2, ran_row3, ran_row4;
	private int ran_col, ran_col2, ran_col3, ran_col4;
	Random random = new Random();
	private static final Font scoreFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	// constructor method that initializes things
    public Game() {
        super(); // call the constructor method (if any) of a superclass. When used with an empty argument (), it's not really needed.
        my_window = new Rectangle(0, 0, (brick_width*brick_col), 566); // set the initial size of the window.
        bounds 	  = new Rectangle(0, 0, (brick_width*brick_col), 566); // Give some starter values for the ball boundary, in this case the same as our window size.
        frame 	  = new JFrame("Java Breakout Game");// Window title.
        frame.addMouseMotionListener(this); // add a MouseMotionListener to this frame so we can get the mouse coordinates.
        // Create a new Paddle(width, height, window_bounds)
        my_paddle = new Paddle(85, 12, bounds);
        // Create a new Ball (x, y , width, height, window_bounds)
        my_ball = new Ball( (bounds.width - ball_diameter)/2 , my_paddle.getY() - ball_diameter, ball_diameter, ball_diameter, bounds);
        
        
        my_ball.GameOver();
        
//        //my_paddle.setY(my_paddle.getY() - 6);
//        
//        System.out.println("constructor");
//        
//        System.out.println("window");
//        System.out.println(my_window);
//        System.out.println("bounds");
//        System.out.println(bounds);
//        
//        System.out.println("paddle");
//        System.out.println(my_paddle.getBounds());
//        System.out.println(my_paddle.getWindow_bounds());
//        System.out.println(my_paddle.getX());
//        System.out.println(my_paddle.getY());
//        System.out.println(my_paddle.getWidth());
//        System.out.println(my_paddle.getHeight());
//        
//        
//        //                System.out.println(my_paddle.getBounds().y);
//        System.out.println("ball");
//        System.out.println(my_ball.getBounds());
//        System.out.println(my_ball.getWindow_bounds());
//        System.out.println(my_ball.getX());
//        System.out.println(my_ball.getY());
//        System.out.println(my_ball.getWidth());
//        System.out.println(my_ball.getHeight());
        
        
        
        
        
        /*
         my_ball   = new Ball(my_paddle.getX() + ((my_paddle.getWidth() / 2) - (ball_diameter / 2)), 	//align center of ball with center of paddle
         (my_paddle.getY() - (ball_diameter + 38) ), ball_diameter, ball_diameter, bounds);	// start ball on top of paddle.
         */
        
        // generate 4 unique random brick locations to mark as the special bricks
        ran_row = random.nextInt(brick_rows);
        ran_col = random.nextInt(brick_col);
        do {
            ran_row2 = random.nextInt(brick_rows);
            ran_col2 = random.nextInt(brick_col);
            ran_row3 = random.nextInt(brick_rows);
            ran_col3 = random.nextInt(brick_col);
            ran_row4 = random.nextInt(brick_rows);
            ran_col4 = random.nextInt(brick_col);
        }
        while ( (ran_row == ran_row2) && (ran_col == ran_col2)
               || (ran_row == ran_row3) && (ran_col == ran_col3)
               || (ran_row == ran_row4) && (ran_col == ran_col4)
               || (ran_row2 == ran_row3) && (ran_col2 == ran_col3)
               || (ran_row2 == ran_row4) && (ran_col2 == ran_col4)
               || (ran_row3 == ran_row4) && (ran_col3 == ran_col4) );
        
        // create array of bricks
        my_bricks = new ArrayList<ArrayList<Brick>>();
        for (int r = 0; r < brick_rows; r++)
        {
            ArrayList<Brick> tempRow = new ArrayList<Brick>();
            for (int c = 0; c < brick_col; c++)
            {
                Brick tempBrick;
                // special bricks
                if (r == ran_row && c == ran_col) {
                    tempBrick = new Brick((c * brick_width), ((r + 2) * brick_height), brick_width, brick_height, true, false, false, false, bounds);
                }
                else if (r == ran_row2 && c == ran_col2) {
                    tempBrick = new Brick((c * brick_width), ((r + 2) * brick_height), brick_width, brick_height, false, true, false, false, bounds);
                }
                else if (r == ran_row3 && c == ran_col3) {
                    tempBrick = new Brick((c * brick_width), ((r + 2) * brick_height), brick_width, brick_height, false, false, true, false, bounds);
                }
                else if (r == ran_row4 && c == ran_col4) {
                    tempBrick = new Brick((c * brick_width), ((r + 2) * brick_height), brick_width, brick_height, false, false, false, true, bounds);
                }
                // regular brick
                else {
                    tempBrick = new Brick((c * brick_width), ((r + 2) * brick_height), brick_width, brick_height, false, false, false, false, bounds);
                }
                tempRow.add(tempBrick);
            }
            my_bricks.add(tempRow);
        }
        my_game_timer = new MyGameTimer(); // Create a timer for this class.
    } // Game Constructor.
	
	// Create a Timer class (an --inner class--) inside Game class that has access to the Game class methods.
	class MyGameTimer extends TimerTask {
		// the run method is the basic game loop. It will update the position of things (in our case the ball) then repaint the screen.
		public void run() {
			moveItems();     // move all items in the game;
			frame.repaint(); // repaint the window, which means paintComponent will automatically get called.
			}
		}
	
	//this method get's automatically called when there is a need to redraw the window. Like when the user moved it or resized it.
	// remember the name of this method "paintComponent" is special, it has to be called this for Java to know about it.
	public void paintComponent(Graphics g) {
		// Get the drawing area bounds for game logic, in case they have changed because the user
		// resized the window.

		bounds = g.getClipBounds();
		my_window = bounds; // copy the boundaries to my_window, in case the user has resized 
							// the window this will make sure the entire window is cleaned below
							// when the clearRect call is made with my_window.width and my_window.height
        for (int r = 0; r < brick_rows; r++)
        {
            for (int c = 0; c < brick_col; c++)
            {
                my_bricks.get(r).get(c).setWindow_bounds(bounds);
            }
        }
        my_paddle.setWindow_bounds(bounds);
        my_ball.setWindow_bounds(bounds);
        
        
		Graphics2D g2d = (Graphics2D) g;
		// Clear the drawing area to a gradient from orange to blue
		GradientPaint gp = new GradientPaint(0, my_window.height / 4, Color.ORANGE, 0, my_window.height, Color.BLUE);
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, my_window.width, my_window.height);
		
		// Display the score.
		g.setColor(Color.WHITE);
	    g.setFont(scoreFont);
		g.drawString("Score:", my_window.x + 30 , my_window.y + 30);
		g.drawString(Integer.toString(Score), my_window.x + 100, my_window.y + 30);
		g.drawString("Lives:", my_window.x + 770 , my_window.y + 30);
		g.drawString(Integer.toString(Lives), my_window.x + 840, my_window.y + 30);
		my_paddle.Draw(g2d,bounds); // draw the paddle. pass the bounds in case the window size has changed.
        
        
        if (my_ball.getY() + my_ball.getHeight() > bounds.height)
        {
            my_ball.setY(my_paddle.getY() - ball_diameter);
            my_ball.setX(my_paddle.getX() + my_paddle.getWidth()/2 - ball_diameter/2);
            my_ball.Draw(g2d,bounds); // draw the ball, pass the bounds in case the window size has changed.
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {}
        }
        else
        {
            my_ball.Draw(g2d,bounds); // draw the ball, pass the bounds in case the window size has changed.
        }
		// draw the bricks
		for (int r = 0; r < brick_rows; r++) {
			for (int c = 0; c < brick_col; c++) {
				my_bricks.get(r).get(c).Draw(g2d, bounds);
				}
			}
        if (Lives < 1) {
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER!", (my_window.width / 2) - 60, (my_window.height / 2) + 30);
        }
        if(my_ball.getBricksDestroyed() == brick_rows*brick_col)
        {
            my_ball.GameOver();
            g.setColor(Color.BLACK);
            g.drawString("YOU WIN!", (my_window.width / 2) - 60, (my_window.height / 2) + 30);
        }

		} // paintComponent
	
	public void moveItems() {
		Score = my_ball.Move(Score, my_paddle, my_bricks, brick_rows, brick_col,
				ran_row, ran_col, ran_row2, ran_col2, ran_row3, ran_col3, ran_row4, ran_col4);
        
        Lives = my_ball.getLives();
		} // moveItems
	
	public static void main(String arg[]) {
		Timer simple_game_timer = new Timer();  // make a timer object.
		Game panel = new Game(); // Create and instance of our simple game class

		// Set up our JFrame
		panel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.frame.setSize(panel.my_window.width, panel.my_window.height);
		panel.frame.setContentPane(panel); 
		panel.requestFocusInWindow();
		panel.frame.setVisible(true);
		// Set up a timer to call the run method in the my_game_timer class regularly.
		simple_game_timer.schedule(panel.my_game_timer, 0, 10);
		} // main
	
	@Override // this method were generated by Eclipse, I added the my_paddle.Move call.
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("mouse dragged "+ e.getX() + "," + e.getY() + "\n");
		my_paddle.Move(e.getX());
	}

	@Override // this method were generated by Eclipse, I added the my_paddle.Move call.
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("mouse moved "+ e.getX() + "," + e.getY() + "\n");
        if(Lives == 0 || my_ball.getBricksDestroyed() == brick_rows*brick_col)
            return;
        if(my_ball.getBall_x_vel() == 0)
        {
            my_ball.setBall_x_vel(my_ball.getWidth()/4);
            my_ball.setBall_y_vel(my_ball.getHeight()/3);
        }
        my_paddle.Move(e.getX());
	}
} // Game Class
