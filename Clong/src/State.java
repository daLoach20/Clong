import java.awt.Graphics;
import java.util.ArrayList;


public abstract class State {
	
	ArrayList<State> states;
	
	public State(ArrayList<State> states){
		this.states = states;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
