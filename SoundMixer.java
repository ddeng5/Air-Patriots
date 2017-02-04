//I got this code from the internet.
//It sets the volume for the volume slider
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
public class SoundMixer {
	double vol = 0.5;
	public SoundMixer() {
		changeVolume(0.5);
	}
	public void changeVolume(double f){
		//it gets the line, and changes the volume using getControl();
		javax.sound.sampled.Mixer.Info[] mixers = AudioSystem.getMixerInfo();
	    for(int i=0;i<mixers.length;i++){
	        Mixer mixer = AudioSystem.getMixer(mixers[i]);
	        Line.Info[] lineinfos = mixer.getTargetLineInfo();
	        for(Line.Info lineinfo : lineinfos){
	            try {
	                Line line = mixer.getLine(lineinfo);
	                line.open();
	                if(line.isControlSupported(FloatControl.Type.VOLUME)){
	                    ((FloatControl)line.getControl(FloatControl.Type.VOLUME)).setValue((float) f);
	                }
	            }catch(LineUnavailableException e){}
	        }
	    }
	    vol = f;
	}
	public double getVolume(){
		return vol;
	}
}