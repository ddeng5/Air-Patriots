//Importing stuff
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AirPatriots extends JFrame implements ActionListener{//main class with the main method
	static GamePanel game; //game panel
	static MainScreen main;
	javax.swing.Timer myTimer;// timer
	javax.swing.Timer spawnTimer; //timer controls when balloons are spawned
	static ArrayList<Point> Level1a = new ArrayList(); //these are the lists for the paths for the balloons
	static ArrayList<Point> Level1b = new ArrayList(); //we just need these to start the game off
	
	static Image i1 = new ImageIcon("images/Level1.png").getImage();  //the images for the first map
	static Image i2 = new ImageIcon("images/grass_obj.png").getImage();
	
	boolean quit = false; //if the game is quit and you're in menu screen
	
	public static void textFile(ArrayList l, String a) throws IOException {  //reads coordinates makes them into custom point classes
    	Scanner inFile = new Scanner (new BufferedReader (new FileReader(a)));
    	while(inFile.hasNextLine()){
			String [] per = inFile.nextLine().split(" ");
			l.add(new Point(Double.parseDouble(per[0]),Double.parseDouble(per[1])));
		}
		inFile.close(); 
    }
    
    
	
    public AirPatriots(ArrayList<Point> Path1, ArrayList<Point> Path2, Image bg1,Image bg2){
    	super("BalloonKiller!!");
    	game = new GamePanel(Level1a,Level1b,i1,i2); //make the game and all
    	game.quit=true; //start off so that it starts in menu
    	main = new MainScreen(game); //makes the mainscreen
    	main.setVisible(true); //main is visible
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	setSize(1280,750);
    	setLayout(new BorderLayout());
    	setResizable(false);
    	myTimer = new javax.swing.Timer(1000/70,this);
    	myTimer.start();
    	spawnTimer = new javax.swing.Timer(1000,this);
    	spawnTimer.start(); 
    	add(game);//adds the game
    	setVisible(true);//makes sure the user can see it
    }
    public void actionPerformed(ActionEvent e){ //called with timer
    	Object source=e.getSource(); 
    	if(source==myTimer){
    		quit = game.quit;//checks if the user quit the game
    		if (quit==false){ //if he didn't
    			game.requestFocus();
    			game.checkKeys(); //whatever keys are presed
    			game.checkPlaneShoot();//checks if plane shoots
    			game.checkBalloonShoot(); //checks if balloonshoots
    			game.cleanseLists(); //cleans up everything that died
    			game.repaint();
    			main.setVisible(false); //makes sure only the game is on the screen
    			game.setVisible(true);
    		}else{
    			main.requestFocus();
    			add(main); //add it so that its on the screen
    			main.playMusic(); //play the music
    			main.repaint();
    		}
    		
    		
    	}
    	if(source==spawnTimer && game.paused==false && quit==false){
    		game.spawnState();//updates the spawn state
    		if(game.balSpawn){ //spawns more balloons if the wave is done
    			game.spawnBalloon();
    		}
    		
    	}
    	if (quit){ //if you quit then you switch what frame you are viewing
    		game.setVisible(false);
    		main.setVisible(true);
    	}
    }
    
    public static void main (String [] args)throws IOException {
    	textFile(Level1a,"level1a.txt");
    	textFile(Level1b,"level1b.txt");
    	AirPatriots a = new AirPatriots(Level1a,Level1b,i1,i2);
    	
    }
    
    
}