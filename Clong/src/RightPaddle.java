import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
//TODO AI for paddle
public class RightPaddle {
	
	private int x, y, center;
	private int height;
	private int ticks;
	private final int randomFactor = 1;
	private Board board;
	private boolean debug = false;
	private KeyManager km;
	
	public RightPaddle(int width, int height, Board board, KeyManager km){
		this.height = height;
		this.board = board;
		this.x = width - 40 - 20;
		this.y = height / 2 - 50;
		this.center = y+50;
		this.km = km;
		this.ticks = 0;
	}
	
	public void tick(Ball ball){
		center = y+50;
		Random rand = new Random();
		if(!debug){
			ticks++;
			if(ticks >= 30){
				if(ball.getY() < center){
					if(canMoveUp()) y--;
				}
				if(ball.getY() > center){
					if(canMoveDown()) y++;
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
		
		//upOrDown = 0;
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
		g.drawRect(x, y, 20, 100);
		g.setColor(Color.RED);
		if(board.ball != null){
			//System.out.println("Not Null");
			//g.drawLine(board.ball.getX(), board.ball.getY(), x, center);
		}
	}

	public int getX() {
		return x;
	}
	
	public int getY(){
		return y;
	}

}
