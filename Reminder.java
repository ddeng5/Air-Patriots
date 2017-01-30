//This class is pretty much a timer. Its used for the music thing when you hover your button over
//the nuke button
import  java.io.*;
import java.util.*;
public class Reminder {
    Timer timer;
	GamePanel game;
	
    public Reminder(int seconds,GamePanel g) {
    	game = g;
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
	}
	
    class RemindTask extends TimerTask {
        public void run() {
            game.wonderButton = true; //changes the nuke button to true so if you hover over it. it should play
            timer.cancel(); //Terminate the timer thread
        }
    }
} 