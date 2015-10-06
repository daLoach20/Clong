import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;


public class MainMenu extends State{
	
	private int cursor;
	private int width, height, scale;
	private int cursorX, cursorY;
	private int ticks;
	private boolean canStart = true;
	private String text;
	private KeyManager km;
	
	public MainMenu(int width, int height, int scale, ArrayList<State> states, String text, KeyManager km, boolean b){
		super(states);
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.cursor = 0;
		this.ticks = 0;
		this.text = text;
		this.km = km;
		this.canStart = b;
	}

	@Override
	public void tick() {
		if(km.enter && canStart){
			Clong.currentState = states.get(1);
		}
		if(cursor == 0){
			cursorX = width*scale/2-175;
			cursorY = height*scale/2-22;
		}
		if(ticks < 0){
			Clong.currentState = states.get(1);
		}
		ticks++;
		
	}
	
	public void setText(String text){
		this.text = text;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width*scale, height*scale);
		
		g.setColor(Color.RED);
		g.drawString("Press Enter", width*scale - 140, height*scale - 30);
		g.drawString("UP/DOWN keys to move", width*scale - 140, height*scale - 15);
		//Font currentFont = g.getFont();
		Font newFont = new Font("TimesRoman", Font.BOLD, 50);
		g.setFont(newFont);
		g.drawString(text, width*scale/2-100, height/2*scale);
		g.fillRect(cursorX, cursorY, 50, 10);
		
		
	}

}
