import java.awt.Color;
import java.awt.Graphics;

//TODO add movement
public class LeftPaddle {

	private int x, y, ticks;
	private KeyManager km;

	public LeftPaddle(int width, int height, KeyManager km){
		this.x = 40;
		this.y = height/2 - 50;
		this.ticks = 0;
		this.km = km;
	}

	public void tick(){
		ticks++;
		if(ticks >= 30){
			if(km.up){
				y--;
			}
			if(km.down){
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

}
