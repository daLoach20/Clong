import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyManager implements KeyListener{
	
	private boolean keys[];
	public boolean up, down, enter, space;
	
	public KeyManager(){
		this.keys = new boolean[255];
		
	}
	
	public void tick(){
		
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		enter = keys[KeyEvent.VK_ENTER];
		//space = keys[KeyEvent.VK_SPACE];
		space = false;
		
		/*for(int i = 0; i < keys.length;i++){
			if(keys[i]) System.out.println(i);
		}*/		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
