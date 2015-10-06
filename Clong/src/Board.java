import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Board extends State{
	
	private int width, height;
	private int scale;
	private LeftPaddle leftPad;
	private RightPaddle rightPad;
	public Ball ball;
	
	public Board(int boardWidth, int boardHeight, int scale, ArrayList<State> states, KeyManager km){
		super(states);
		this.scale = scale;
		this.width = boardWidth;
		this.height = boardHeight;
		this.leftPad = new LeftPaddle(width * scale, height * scale, km);
		this.rightPad = new RightPaddle(width * scale, height * scale, this, km);
		this.ball = new Ball(width * scale, height * scale, leftPad, rightPad, states, km, true);
	}
	
	public void tick(){
		ball.tick();
		leftPad.tick();
		rightPad.tick(ball);		
	}
	
	public void render(Graphics g){
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width * scale, height * scale);
		
		ball.render(g);
		leftPad.render(g);
		rightPad.render(g);
		
	}

}
