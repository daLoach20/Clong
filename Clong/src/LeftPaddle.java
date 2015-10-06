import java.awt.Color;
import java.awt.Graphics;

//TODO add movement
public class LeftPaddle {

	private double x, y, speed;
	private int height, ticks;
	private KeyManager km;

	public LeftPaddle(int width, int height, KeyManager km){
		this.x = 40;
		this.y = height/2 - 50;
		this.height = height;
		this.ticks = 0;
		this.speed = 1.2;
		this.km = km;
	}

	public void tick(){
		ticks++;
		if(ticks >= 1){
			if(km.up && canMoveUp()){
				y -= speed;
			}
			if(km.down && canMoveDown()){
				y += speed;
			}
			ticks = 0;
		}
	}

	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect((int)x, (int)y, 20, 100);
	}

	public int getX() {
		return (int)x;
	}

	public int getY(){
		return (int)y;
	}
	
private boolean canMoveDown() {
		
		if((y+100) >= height) return false;
		
		return true;
	}

	private boolean canMoveUp(){
		if(y <= 0) return false;
		
		return true;
	}

}
