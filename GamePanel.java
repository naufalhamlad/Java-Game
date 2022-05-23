import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.6));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 15;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 25;
	static final int GOAL_HEIGHT = 50;
	static final int GOAL_WIDTH = 50;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	Goal goal1;
	Goal goal2;
	
	
	
	GamePanel(){
		newPaddles();
		newBall();
		newGoal();
		
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	//creating goals
	public void newGoal() {
		
		goal1 = new Goal(50,(GAME_HEIGHT/2)-(GOAL_HEIGHT/2),GOAL_WIDTH,GOAL_HEIGHT);
		goal2 = new Goal(GAME_WIDTH-100,(GAME_HEIGHT/2)-(GOAL_HEIGHT/2),GOAL_WIDTH,GOAL_HEIGHT);
		
		
		
	}
	//creating ball
	public void newBall() {
		random = new Random();
		int spawn = random.nextInt(4);
		switch(spawn) {
		case 0:
			ball = new Ball(60, 60, BALL_DIAMETER, BALL_DIAMETER);
			break;
		case 1:
			ball = new Ball(60, 540, BALL_DIAMETER, BALL_DIAMETER);
		case 2:
			ball = new Ball(940, 60, BALL_DIAMETER, BALL_DIAMETER);
		case 3:
			ball = new Ball(940, 540, BALL_DIAMETER, BALL_DIAMETER);
			
			
		}
	}
	//creating paddles
	public void newPaddles() {
		paddle1 = new Paddle(150,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle(GAME_WIDTH-175,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		goal1.draw(g);
		goal2.draw(g);
		score.draw(g);
Toolkit.getDefaultToolkit().sync(); 

	}
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void checkCollision() {
		
		
		if(ball.y <=0) { //deflect ball from the top frame
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) { 
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.x <=0) {
			ball.setXDirection(-ball.xVelocity);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			ball.setXDirection(-ball.xVelocity);
		}
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; 
			if(ball.yVelocity>0)
				ball.yVelocity++; 
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; 
			if(ball.yVelocity>0)
				ball.yVelocity++; 
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		if(paddle1.y<=0) //make sure paddle wont go out of the frame
			paddle1.y=0;
		if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
		
		if(paddle1.x<=0)
			paddle1.x=0;
		if(paddle1.x >= ((GAME_WIDTH/2)-PADDLE_WIDTH))
			paddle1.x = (GAME_WIDTH/2)-PADDLE_WIDTH;
		
		if(paddle2.y<=0)
			paddle2.y=0;
		if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
		if(paddle2.x>= 1000-35)
			paddle2.x= 1000-35;
		if(paddle2.x <= (GAME_WIDTH/2)-1)
			paddle2.x = (GAME_WIDTH/2)-1;
		
		
		
		if(ball.x >=25 && // if ball go p1 into the goal x axis
			ball.x <=100 &&
			ball.y >=(GAME_HEIGHT/2)-(GOAL_WIDTH/2) && //if ball go into the p1 goal y axis 
			ball.y <=(GAME_HEIGHT/2)+(GOAL_WIDTH/2)) {
			score.player2++;
			newPaddles();
			newBall(); 
			}
			
		if(paddle1.x >=25 && // if paddle1 go p1 into the goal x axis
			paddle1.x <=100 &&
			paddle1.y >=(GAME_HEIGHT/2)-(GOAL_WIDTH/2) && //if paddle1 go into the p1 goal y axis 
			paddle1.y <=(GAME_HEIGHT/2)+(GOAL_WIDTH/2)) {
			score.player2++;
			newPaddles();
			newBall();
		
		}
		if(ball.x <=1000-50 && // if paddle2 go p2 into the goal x axis
		    ball.x >=1000-125 &&
			ball.y >=(GAME_HEIGHT/2)-(GOAL_WIDTH/2) && //if ball go into the p2 goal y axis
			ball.y <=(GAME_HEIGHT/2)+(GOAL_WIDTH/2)) {
			score.player1++;
			newPaddles();
			newBall();
			
		}
		if(paddle2.x <=1000-50 && // if paddle2 go p2 into the goal x axis
			paddle2.x >=1000-125 &&
			paddle2.y >=(GAME_HEIGHT/2)-(GOAL_WIDTH/2) && //if paddle2 go into the p2 goal y axis
			paddle2.y <=(GAME_HEIGHT/2)+(GOAL_WIDTH/2)) {
			score.player1++;
			newPaddles();
			newBall();
				
			}
	}
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}