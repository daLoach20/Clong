import java.awt.Color;
import java.awt.Graphics;

//TODO add movement
public class LeftPaddle {

	private int x, y, ticks;
	private int height;
	private KeyManager km;

	public LeftPaddle(int width, int height, KeyManager km){
		this.x = 40;
		this.y = height/2 - 50;
		this.height = height;
		this.ticks = 0;
		this.km = km;
	}

	public void tick(){
		ticks++;
		if(ticks >= 30){
			if(km.up && canMoveUp()){
				y--;
			}
			if(km.down && canMoveDown()){
				y++;
			}
			ticks = 0;
		}
	}

	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(x, y, 20, 100);
	}

	public int getX() {
		return x;
	}

	public int getY(){
		return y;
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
