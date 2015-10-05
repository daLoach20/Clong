import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;


public class Sound {
	
	private String name;
	public AudioClip sound;
	
	
	public Sound(String name, URL url){
		this.name = name;
		try{
			sound = Applet.newAudioClip(url);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void play(){
		new Thread(new Runnable(){
			public void run(){
				if(sound != null){
					sound.play();
				}
			}
		}).start();
	}
	
	public void loop(){
		new Thread(new Runnable(){
			public void run(){
				if(sound != null){
					sound.loop();
				}
			}
		}).start();
	}
	
	public void stop(){
		if(sound != null){
			sound.stop();
		}
	}
	
	public static URL getUrl(String filename){
		
		
		
		return Sound.class.getResource("/res/" + filename + ".wav");
	}

}
