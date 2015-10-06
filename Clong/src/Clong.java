//TODO Sound, AI, Player Movement, Pause
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Clong extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7498060950409770193L;
	private JFrame frame;
	private Thread thread;
	private final int width = 300, height = (int)(width / 16.0f*9), scale = 2;
	private Board board;
	private MainMenu mMenu;
	private MainMenu winScreen;
	private MainMenu loseScreen;
	//fun
	private boolean first;
	private ArrayList<State> stateList;
	public static State currentState;
	private KeyManager km;
	
	public Clong(){
		first = true;
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		km = new KeyManager();
		addKeyListener(km);
		stateList = new ArrayList<State>();
		board = new Board(width, height, scale, stateList, km);
		mMenu = new MainMenu(width, height, scale, stateList, "Start Game!", km, true);
		winScreen = new MainMenu(width, height, scale, stateList, "You win!", km, false);
		loseScreen = new MainMenu(width, height, scale, stateList, "You Lose.", km, false);
		
		stateList.add(mMenu);
		stateList.add(board);
		stateList.add(loseScreen);
		stateList.add(winScreen);
		
		currentState = stateList.get(0);
		frame = new JFrame();
	}
	
	public static void main(String[] args){
		Clong game = new Clong();
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}

	private void start() {
		thread = new Thread(this, "Display");
		thread.start();
		
	}

	public void run() {
		
		long startTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000 / 60d; //
		double delta = 0;
		int ticks = 0;
		int frames = 0;
		requestFocus();
		
		while (true) {
			long now = System.nanoTime();
			delta += (now - startTime) / ns;
			startTime = now;
			// System.out.println(delta/60);//this will run every second
			while (delta >= 1) {// delta runs 60 times per seconds
				// System.out.println(tick);
				ticks++;
				tick();
				delta--;
			}

			frames++;
			render();

			// if(tick % 60 == 0)System.out.println(tick);
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames + ", Ticks: " + ticks);
				frame.setTitle("Rain" + "     " + "FPS: " + frames
						+ ", Ticks: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
		
	}
	
	private void tick(){
		km.tick();
		currentState.tick();
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		if(first){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width * scale, height * scale);
		}
		
		currentState.render(g);
		
		g.dispose();
		bs.show();
		
	}
	
}