import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
public class Ball {
	
	public int x, y, screenWidth, screenHeight, futureY, futureX;
	private final int scale = 4, width = 2*scale, height = 2*scale;
	public int xDir, yDir, speed, defaultSpeed;
	private int ticks;
	private boolean realBall;
	private Random rand = new Random();
	private LeftPaddle leftPad;
	private RightPaddle rightPad;
	private ArrayList<State> states;
	private boolean up, left;
	private KeyManager km;
	private Sound hit;
	private Sound win;
	private Sound lose;
	
	//test code
	public int collisions = 0;
	private int first = 0;
	
	public Ball(int width, int height, LeftPaddle leftPad, RightPaddle rightPad, ArrayList<State> states, KeyManager km, boolean realBall){
		//sets original position of ball, and original direction (this should be randomized)
		this.states = states;
		this.screenWidth = width;
		this.screenHeight = height;
		getStartPositions();
		this.futureX = x;
		this.futureY = y;
		this.leftPad = leftPad;
		this.rightPad = rightPad;
		this.km = km;
		this.realBall = realBall;
		this.defaultSpeed = 20;
		ticks = 0;
		hit = new Sound("Collision", Sound.getUrl("coll"));
		win = new Sound("Collision", Sound.getUrl("win"));
		lose = new Sound("Collision", Sound.getUrl("lose"));
	}
	
	private void getStartPositions() {
		
		if(rand.nextBoolean())x = screenWidth/2 + rand.nextInt(20);
		else x = screenWidth/2 - rand.nextInt(20);
		
		if(rand.nextBoolean())y = screenHeight/2 + rand.nextInt(20);
		else y = screenHeight/2 - rand.nextInt(20);
		
		if(rand.nextBoolean()) xDir = 1;
		else xDir = -1;
		
		if(rand.nextBoolean()) yDir = 1;
		else yDir = -1;
	}

	public void tick(){
		//figures out position and direction of ball
		if(km.space) speed = 1000;
		else speed = defaultSpeed;
		checkCollisions();
		resetFuture();
		
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
	
	private void resetFuture() {
		
		if(x > screenWidth/2 -10 && !left){
			futureX = x;
			futureY = y;
		}
		
	}

	private void checkCollisions() {
		
		if(realBall && collisionLeft()) {
			xDir = 1;
			left = false;
			hit.play();
			getFuturePosition();
		}
		if(realBall && collisionRight()) {
			xDir = -1;
			left = true;
			hit.play();
		}
		if(collisionWall()) xDir = 0;
		
		if(realBall && collisionUp()) {
			yDir = 1;
			up = false;
		}
		if(realBall && collisionDown()){
			up = true;
			yDir = -1;
		}
		if(y < 1) {
			yDir = 1;
			up = false;
		}
		if(y+height+2 > screenHeight) {
			yDir = -1;
			up = true;
		}
		
	}

	private void getFuturePosition() {
		Ball futureBall = new Ball(screenWidth, screenHeight, leftPad, rightPad, states, km, false);
		futureBall.x = x + 5;
		futureBall.y = y;
		futureBall.left = left;
		futureBall.up = up;
		
		futureBall.xDir = 1;
		futureBall.yDir = yDir;
		
		futureBall.speed = 1;
		first = 0;
		while(futureBall.x <= rightPad.getX()){
			futureX = futureBall.x + rand.nextInt(10);
			futureY = futureBall.y;
			futureBall.tick();
		}
	}

	private boolean collisionDown() {
		
		if(!collisionLeft() && !collisionRight()){
			if(!up){
				if(x < leftPad.getX()+20 && x+width > leftPad.getX()){
					if(y+height > leftPad.getY()-2 && y < leftPad.getY() - 5) return true;
				}
				//if rightpad
				if(x > rightPad.getX() && x < rightPad.getX()+20){
					if(y+height > rightPad.getY()-2 && y < rightPad.getY() - 5) return true;
				}
			}
		}
		
		return false;
	}
	private boolean collisionUp(){
		
		if(!collisionLeft() && !collisionRight()){
			if(up){
				if(x < leftPad.getX()+20 && x+width > leftPad.getX()){
					if(y < leftPad.getY()+102 && y > leftPad.getY() + 90) {
						return true;
					}
				}
				//if rightpad
				if(x+width > rightPad.getX() && x < rightPad.getX()+20){
					if(y < rightPad.getY()+102 && y > rightPad.getY() + 90) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private boolean collisionRight() {
		
		if(!left){
			if(x+width == rightPad.getX()){
				if(y+height >= rightPad.getY() && y <= rightPad.getY()+100) return true;
			}
		}
		
		return false;
	}

	private boolean collisionLeft(){
		
		if(left){
			if(x == leftPad.getX()+20){
				if(y+height >= leftPad.getY() && y <= leftPad.getY()+100) return true;
			}
		}
		
		return false;
		
	}
	
	
	private boolean collisionWall(){
		if(realBall){
			if(x <= 0) {
				Clong.currentState = states.get(2);
				lose.play();
			}
			if(x+width >= screenWidth){
				Clong.currentState = states.get(3);
				win.play();
			}			
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
		return futureY;
	}

	public int getX(){
		return futureX;
	}

}
