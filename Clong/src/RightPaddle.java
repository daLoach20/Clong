import java.awt.Color;
import java.awt.Graphics;
//TODO AI for paddle
public class RightPaddle {
	
	private double x, y, speed;
	private int height, center;
	private int ticks;
	private Board board;
	private boolean debug = false;
	private KeyManager km;
	
	public RightPaddle(int width, int height, Board board, KeyManager km){
		this.height = height;
		this.board = board;
		this.x = width - 40 - 20;
		this.y = height / 2 - 50;
		this.center = (int)y+50;
		this.km = km;
		this.speed = 1.08;
		this.ticks = 0;
	}
	
	public void tick(Ball ball){
		center = (int)y+50;
		if(!debug){
			ticks++;
			if(ticks >= 1){
				if(ball.getY() < center){
					if(canMoveUp()) y -= speed;
				}
				if(ball.getY() > center){
					if(canMoveDown()) y += speed;
				}
				ticks = 0;
			}
		}
		else{
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
	}
	
	private boolean canMoveDown() {
		
		if((y+100) >= height) return false;
		
		return true;
	}

	private boolean canMoveUp(){
		if(y <= 0) return false;
		
		return true;
	}
	
	public void render(Graphics g){
		g.drawRect((int)x, (int)y, 20, 100);
		g.setColor(Color.RED);
		if(board.ball != null && debug){
			g.drawLine(board.ball.getX(), board.ball.getY(), (int)x, center);
		}
	}

	public int getX() {
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}

}
