import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
public class Ball {
	
	private int x, y, screenWidth, screenHeight;
	private final int scale = 4, width = 2*scale, height = 2*scale;
	private int xDir, yDir, speed = 20;
	private int ticks;
	private LeftPaddle leftPad;
	private RightPaddle rightPad;
	private ArrayList<State> states;
	private boolean up, left;
	private KeyManager km;
	private Sound hit;
	private Sound win;
	private Sound lose;
	
	public Ball(int width, int height, LeftPaddle leftPad, RightPaddle rightPad, ArrayList<State> states, KeyManager km){
		//sets original position of ball, and original direction (this should be randomized)
		this.states = states;
		this.screenWidth = width;
		this.screenHeight = height;
		this.x = width/2;
		this.y = height/2;
		this.xDir = -1;
		this.left = true;
		this.yDir = 1;
		this.up = false;
		this.leftPad = leftPad;
		this.rightPad = rightPad;
		this.km = km;
		ticks = 0;
		hit = new Sound("Collision", Sound.getUrl("coll"));
		win = new Sound("Collision", Sound.getUrl("win"));
		lose = new Sound("Collision", Sound.getUrl("lose"));
	}
	
	public void tick(){
		//figures out position and direction of ball
		speed = 20;
		if(km.space) speed = 1000;
		checkCollisions();
		
		//if(x <= 0) xDir = 1;
		//if(x+width >= screenWidth) xDir = -1;
		
		//y = leftPad.getY()+50;
		if(ticks >= speed){
			x += xDir;
			y += yDir;
			ticks = 0;
		}
		ticks++;
	}
	
	private void checkCollisions() {
		
		if(collisionLeft()) {
			xDir = 1;
			left = false;
			hit.play();
		}
		if(collisionRight()) {
			xDir = -1;
			left = true;
			hit.play();
		}
		if(collisionWall()) xDir = 0;
		if(collisionUp()) {
			//System.out.println("CollisionUp");
			yDir = 1;
		}
		if(collisionDown()){
			
			yDir = -1;
		}
		if(y <= 0) {
			yDir = 1;
			up = false;
		}
		if(y+height+2 >= screenHeight) {
			yDir = -1;
			up = true;
		}
		
	}

	private boolean collisionDown() {
		
		if(!collisionLeft() && !collisionRight()){
			if(!up){
				if(x < leftPad.getX()+20 && x+width > leftPad.getX()){
					//System.out.println("lpx: " + leftPad.getX() + ", bx: " + x);
					//System.out.println("lpy: " + (leftPad.getY()+100) + ", by: " + y);
					if(y+height >= leftPad.getY()-2) return true;
				}
				//if rightpad
				if(x > rightPad.getX() && x < rightPad.getX()+20){
					if(y+height == rightPad.getY()-2) return true;
				}
			}
		}
		
		return false;
	}

	private boolean collisionRight() {
		
		if(!left){
			if(x+width == rightPad.getX()){
				//System.out.println("right x true");
				if(y >= rightPad.getY() && y+height <= rightPad.getY()+100) return true;
			}
		}
		
		return false;
	}

	private boolean collisionLeft(){
		
		if(left){
			if(x == leftPad.getX()+20){
				//System.out.println("right x true");
				if(y >= leftPad.getY() && y+height <= leftPad.getY()+100) return true;
			}
		}
		
		return false;
		
	}
	
	private boolean collisionUp(){
		
		if(!collisionLeft() && !collisionRight()){
			if(up){
				if(x < leftPad.getX()+20 && x+width > leftPad.getX()){
					//System.out.println("lpx: " + leftPad.getX() + ", bx: " + x);
					//System.out.println("lpy: " + (leftPad.getY()+100) + ", by: " + y);
					if(y <= leftPad.getY()+102) return true;
				}
				//if rightpad
				if(x >= rightPad.getX() && x <= rightPad.getX()+20){
					if(y <= rightPad.getY()+102) return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean collisionWall(){
		if(x <= 0) {
			Clong.currentState = states.get(2);
			lose.play();
		}
		if(x+width >= screenWidth){
			Clong.currentState = states.get(3);
			win.play();
		}
		
		return false;
	}
	
	public void render(Graphics g){
		//render the ball to the screen
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		g.fillRect(x, y, width, height);
		g.setColor(Color.RED);
		String msg = "X: " + x + ", Y: " + y;
		String msg2 = "PX: " + leftPad.getX() + ", PY: " + (leftPad.getY()+102);
		//g.drawString(msg, 20, 20);
		//g.drawString(msg2, 20, 30);
	}

	public int getY() {
		return y;
	}

	public int getX(){
		return x;
	}

}
