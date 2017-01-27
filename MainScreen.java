//Class for the menu screen. Does everything for the menu screen

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.sound.sampled.AudioSystem;
import java.applet.*;


public class MainScreen extends JPanel implements KeyListener,MouseListener, MouseMotionListener{
	Image main = new ImageIcon("menuImages/main.jpg").getImage();			//Loading Screen Picture
	Image blue = new ImageIcon("menuImages/blue.jpg").getImage();			//Blue BackGround for Main Menu
	Image cloud1 = new ImageIcon("menuImages/cloud/3.png").getImage();		//Cloud Picture for Main Menu
	Image [] settings = new Image[7];									//List for All Images for Settings Pop-up Menu
	Image [] levels = new Image[4];										//List for Menu Level Preview
	Image settingspic = new ImageIcon("menuImages/setting.png").getImage();	//Pop-up Image for Settings
	boolean settingEnabled = false;										//If enabled Opens settings
	private int loc=0;													//Cloud Location
	private boolean[] keys;
	Font loadingFont,settingFont; 										//Calibri
	private int loading,count,loadingCounter,cloudmove1;
    boolean isButtonPressed,soundSlide = false;
    int mx,loaded=0; int my = 0;
    int sound=600;														//Sound Value Pixels
    boolean soundEnabled = true;
    										//If true, sound is Enabled
    Rectangle soundSlideRect2 = new Rectangle(600,240,226,40);			//Sound slider container
    Rectangle soundSlideRect = new Rectangle(650,240,32,40);
    Rectangle soundBox = new Rectangle(389,235,40,40);
    Rectangle settingBox = new Rectangle(330,120,620,480);				//Setting pop-up image container
    Rectangle settingBtn = new Rectangle(600,600,132,104);				//Settings Btns image container
    static GamePanel game;

    Image rightArrowNormal = new ImageIcon("images/rightArrowNormal.png").getImage();
    Image rightArrowHover = new ImageIcon("images/rightArrowHover.png").getImage();
    Image rightArrowPushed = new ImageIcon("images/rightArrowPushed.png").getImage();
    Image leftArrowNormal = new ImageIcon("images/leftArrowNormal.png").getImage();
    Image leftArrowHover = new ImageIcon("images/leftArrowHover.png").getImage();
    Image leftArrowPushed = new ImageIcon("images/leftArrowPushed.png").getImage();

    boolean playCheck=false;

    Font coinFont,coinFontBig;

    //Rectangle mapRightRect = new Rectangle(1100,460,rightArrowNormal.getWidth(null),rightArrowNormal.getHeight(null));
   //Rectangle mapLeftRect = new Rectangle(180-rightArrowNormal.getWidth(null),460,rightArrowNormal.getWidth(null),rightArrowNormal.getHeight(null));

    SoundMixer sm = new SoundMixer();

    //all the maps and the paths for each of the levels, then we just pass it into the gamepanel when we
    //choose a map
  static Image grass1 = new ImageIcon("images/grass.png").getImage();
	static Image grass2 = new ImageIcon("images/grass2.png").getImage();

	static Image nuke1 = new ImageIcon("images/nuke.png").getImage();
	static Image nuke2 = new ImageIcon("images/nuke2.png").getImage();

	static Image desert = new ImageIcon("images/desert.jpg").getImage();
	static Image desert2 = new ImageIcon("images/desert2.png").getImage();

	static Image ice = new ImageIcon("images/ice.png").getImage();
	static Image ice2 = new ImageIcon("images/ice2.png").getImage();

	static ArrayList<Point> Level1a = new ArrayList();
	static ArrayList<Point> Level1b = new ArrayList();
	static ArrayList<Point> Level1c = new ArrayList();
	//END MAP STUFF

	//these are the pictures for the buttons
	Image greyBarNormal = new ImageIcon("images/greyBarNormal.png").getImage();
	Image greyBarHover = new ImageIcon("images/greyBarHover.png").getImage();
	Image greyBarPushed = new ImageIcon("images/greyBarPushed.png").getImage();
	Image yellowBarNormal = new ImageIcon("images/yellowBarNormal.png").getImage();
	Image yellowBarHover = new ImageIcon("images/yellowBarHover.png").getImage();
	Image yellowBarPushed = new ImageIcon("images/yellowBarPressed.png").getImage();
	Image greenBarNormal = new ImageIcon("images/greenBar1.png").getImage();
	Image greenBarHover = new ImageIcon("images/greenBar2.png").getImage();
	Image greenBarPushed = new ImageIcon("images/greenBar3.png").getImage();
	//the cursors and the picture
	Cursor normalCur;
	Image imgCursor;

	//the music for each of the level. we pass this into the gamepanel too
	String [] themeMusic = new String[]{"sounds/Isaac Shepard - Load and Explode2.wav",
										"sounds/Isaac Shepard - Slick Shooter.wav",
										"sounds/Isaac Shepard - Engage.wav",
										"sounds/Isaac Shepard - Holes in the Ground.wav"
										};
	//if the help menu is up
	boolean helpEnabled = false;

	Image[] maps1=new Image[4];
	//the help pictures for the help screen
	Image [] helpPics = new Image[]{new ImageIcon("images/helpPic1.png").getImage(),//FONT IS BODONI MT BOLDED
									new ImageIcon("images/helpPic2.png").getImage(),//500 X 292 IMAGES
									new ImageIcon("images/helpPic3.png").getImage(),
									new ImageIcon("images/helpPic4.png").getImage(),
									new ImageIcon("images/helpPic5.png").getImage(),
									new ImageIcon("images/helpPic6.png").getImage(),
									new ImageIcon("images/helpPic7.png").getImage()};
									/*new ImageIcon("images/helpPic8.png").getImage(),
									new ImageIcon("images/helpPic9.png").getImage(),
									new ImageIcon("images/helpPic10.png").getImage(),
									new ImageIcon("images/helpPic11.png").getImage(),
									new ImageIcon("images/helpPic12.png").getImage(),
									new ImageIcon("images/helpPic13.png").getImage()}*/
   	int helpPicCounter = 10000000*helpPics.length;//which help page you're on...multiplied by this much
   	//so we dont have to worry about negatives
   	Image[]maps2=new Image[4];
   	//paths
   	String [] paths1=new String[4];
   	String [] paths2=new String[4];
   	String [] paths3=new String[4];
	//the theme music that plays when you pen the game
	AudioClip mainMusic = Applet.newAudioClip(getClass().getResource("sounds/Isaac Shepard - Bombard.wav"));
	//the rectangles for the help menu
	Rectangle helpBarRect = new Rectangle(1000,610,greyBarNormal.getWidth(null),greyBarNormal.getHeight(null));
	Rectangle helpNextRect = new Rectangle(685,475,greenBarNormal.getWidth(null),greenBarNormal.getHeight(null));
	Rectangle helpPrevRect = new Rectangle(375,475,greenBarNormal.getWidth(null),greenBarNormal.getHeight(null));
	//the rectangles to select a map on the main menu
	Rectangle nextSelect = new Rectangle(850,290,1017-850,439-290);
	Rectangle prevSelect= new Rectangle(267,295,1017-850,439-290);
	Rectangle currentSelect=new Rectangle(479,219,807-479,503-219);
	//if music is played.
	boolean musicPlayed=false;


    public MainScreen(GamePanel game){ //constuctor take sin the game panel to keep both of them in sync
    	super();
    	//for the cursor on the main screen
    	imgCursor=new ImageIcon("images/cursor.png").getImage();
    	Toolkit tk = Toolkit.getDefaultToolkit();
    	normalCur = tk.createCustomCursor( imgCursor, new java.awt.Point( 5, 5 ), "cursor" );
    	setCursor(normalCur);
    	//for the coin font that we use
    	try{
			coinFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,12);
			coinFontBig = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,25);
		}
		catch(IOException ioe){
			System.out.println("error loading AgentOrange.tff");
		}
		catch(FontFormatException e){
		}
    	//adds the pictures to the list...these are for the layers
    	maps1[0]=grass1;
   		maps1[1]=nuke1;
   		maps1[2]=desert;
   		maps1[3]=ice;

   		maps2[0]=grass2;
   		maps2[1]=nuke2;
   		maps2[2]=desert2;
   		maps2[3]=ice2;

   		for(int i=0;i<4;i++){   ///making text files
   			paths1[i]="level"+(i+1)+"a.txt";
   			paths2[i]="level"+(i+1)+"b.txt";
   			if(i>1){
   				paths3[i]="level"+(i+1)+"c.txt";
   			}
   			else{
   				paths3[i]=null;
   			}
   		}
    	this.game=game;
    	for(int i=0;i<4;i++){											//Loading Menu Level Preview
			Image img = Toolkit.getDefaultToolkit().getImage("menuImages/"+(i+1)+".png");
			levels[i]=img;
		}

		for(int i=0;i<7;i++){											//Loading All Images for Settings Pop-up Menu
			Image img = Toolkit.getDefaultToolkit().getImage("menuImages/btns/"+(i+1)+".png");
			settings[i]=img;
		}

		keys = new boolean[KeyEvent.KEY_LAST+1];

		try{															//Loading Font
			loadingFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,15);
			settingFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,20);
		}
		catch(IOException ioe){
			System.out.println("error loading AgentOrange.tff");
		}
		catch(FontFormatException e){
		}
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

    }
    public void addNotify(){
		super.addNotify();
		requestFocus();
	}



	public static void textFile(ArrayList l, String a) throws IOException {
    	Scanner inFile = new Scanner (new BufferedReader (new FileReader(a)));
    	while(inFile.hasNextLine()){
			String [] per = inFile.nextLine().split(" ");
			l.add(new Point(Double.parseDouble(per[0]),Double.parseDouble(per[1]),Double.parseDouble(per[2])));
		}
		inFile.close();
    }


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(loaded<50){													//Loading Screen Display
			g.drawImage(main,0,0,this);									//Draws images that will be used later on
			g.setFont(loadingFont);
			g.setColor(Color.WHITE);
			g.drawString("Loading",600,680);
			g.drawImage(levels[0],5000,5000,this);
			g.drawImage(levels[1],5000,5000,this);
			g.drawImage(levels[2],5000,5000,this);
			g.drawImage(levels[3],5000,5000,this);
			g.drawImage(blue,5000,5000,this);
			g.drawImage(settings[0],5000,5000,null);
			g.drawImage(settings[1],5000,5000,null);
			g.drawImage(settings[2],5000,5000,null);
			g.drawImage(cloud1,cloudmove1%(1280+512) - 512,400,this);
			loaded+=1;
		}
		if(loaded>=50){													//Once Loading is Done, Goes to Main Menu
			cloudmove1++;
			for(int i=0;i<4;i++){
				if(loc==i){
					g.drawImage(blue,0,0,this);							//Draws Blue Background for Main Menu
					g.drawImage(cloud1,cloudmove1%(1280+512) - 512,400,this);//Draws cloud, then adds it back to front once reaches end
					g.drawImage(levels[i],0,0,this);					//Draws Level Previews
				}
			}
			if(settingBtn.contains(mx,my)){								//Settings Pop-up
				if(isButtonPressed==true && !settingEnabled){
					g.drawImage(settings[2],600,600,null);
				}
				else{
					g.drawImage(settings[1],600,600,null);
				}
			}
			else{
				g.drawImage(settings[0],600,600,null);
			}
			setFont(coinFontBig);
			g.setColor(Color.WHITE);
			g.drawImage(greyBarNormal,helpBarRect.x,helpBarRect.y,this);


			if (helpBarRect.contains(mx,my)){
				g.drawImage(greyBarHover,helpBarRect.x,helpBarRect.y,this);
			} //the help bar rectangles
			if (helpBarRect.contains(mx,my)&&isButtonPressed){
				g.drawImage(greyBarPushed,helpBarRect.x,helpBarRect.y,this);
			}

			g.drawString("HELP",helpBarRect.x+73,helpBarRect.y+50);


			if (helpEnabled){//if the help menu is enabled
				g.drawImage(settingspic,330,120,null); //draws the rectangle that pops up
				g.drawImage(greenBarNormal,helpNextRect.x,helpNextRect.y,this); //this is the button code - normal button
				if (helpNextRect.contains(mx,my)){ //if you're over the button then its the button that is lit up
					g.drawImage(greenBarHover,helpNextRect.x,helpNextRect.y,this);
				}
				if (helpNextRect.contains(mx,my)&&isButtonPressed){//if you pressed it then its the pushed button
					g.drawImage(greenBarPushed,helpNextRect.x,helpNextRect.y,this);
				}
				g.drawString("NEXT",helpNextRect.x+65,helpNextRect.y+42);

				g.drawImage(greenBarNormal,helpPrevRect.x,helpPrevRect.y,this);
				if (helpPrevRect.contains(mx,my)){//same stuff for the previous button
					g.drawImage(greenBarHover,helpPrevRect.x,helpPrevRect.y,this);
				}
				if (helpPrevRect.contains(mx,my)&&isButtonPressed){
					g.drawImage(greenBarPushed,helpPrevRect.x,helpPrevRect.y,this);
				}
				g.drawString("PREV",helpPrevRect.x+65,helpPrevRect.y+42);

				g.drawImage(helpPics[helpPicCounter%helpPics.length],settingBox.x+50,settingBox.y+100,this);
			}

			if(settingEnabled==true){ //if the setting menu is turned
				g.drawImage(settingspic,330,120,null);//draws the setting pictures
				g.drawImage(settings[5],389,235,null);
				g.drawImage(settings[6],600,248,null);
				if (soundEnabled){ //if the sound is enabled
					g.drawImage(settings[4],soundSlideRect.x,soundSlideRect.y,null);
				}else{
					g.drawImage(settings[4],600,248,null);
				}

				g.setFont(settingFont);
				g.setColor(Color.WHITE);
				g.drawString("Sound",456,260);

				if(soundEnabled){										//Sound Settings
					g.drawImage(settings[3],389,235,null);
				}
				if(soundSlideRect.x<=600){//moves the slider left and right
					mainMusic.stop();
					soundEnabled=false;
					playCheck=true;
				}
				else if(soundSlideRect.x>600){
					soundEnabled=true;
					if(playCheck){
						mainMusic.loop();
						playCheck=false;
					}

				}
			}

			checkKeys();	//checks what keys are pressed
		}
		//g.drawRect(currentSelect.x,currentSelect.y,currentSelect.width,currentSelect.height);
	}

	public void checkKeys(){ //checks what keys are presed
		if((keys[KeyEvent.VK_ESCAPE] && settingEnabled) || (keys[KeyEvent.VK_ESCAPE] && helpEnabled)){				//If Escape is pressed, closes settings
			settingEnabled=false;
			helpEnabled=false;
			helpPicCounter = 10000000*helpPics.length;
		}
	}
	public void playMusic(){ //plays the music
		if(!musicPlayed && soundEnabled){
			mainMusic.loop();
			musicPlayed=true;
		}
	}
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){

		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e){							//Goes Next/Prev. if Right or Left arrows is pressed
		keys[e.getKeyCode()]=false;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && settingEnabled==false && helpEnabled==false){ //if you move left or right
			loc=(loc+1)%4;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT && helpEnabled){ //move the help menu pictures left or right
			helpPicCounter++;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT && helpEnabled){
			helpPicCounter--;
		}
		if((e.getKeyCode()==KeyEvent.VK_ENTER && settingEnabled==false && helpEnabled==false)){ //if you choose a map
			try{
				Level1a.clear(); //clear everything and add the new points
				Level1b.clear();
				Level1c.clear();
				textFile(Level1a,paths1[loc]);
    			textFile(Level1b,paths2[loc]);
    			if(paths3[loc]!=null){
    				textFile(Level1c,paths3[loc]);
    			}
    			mainMusic.stop(); //stop the music
				game.resetEverything(Level1a,Level1b,Level1c,maps1[loc],maps2[loc],themeMusic[loc]);//this loads the new data
				//and refreshes data
				musicPlayed=false;

			}
			catch(IOException ex){
			}
		}

		if(e.getKeyCode()==KeyEvent.VK_LEFT&& settingEnabled==false && helpEnabled==false){
			if (loc!=0){ //moves the map choices left or right
				loc=Math.abs((loc-1)%4);
			}else{
				loc = 3;
			}

		}
	}


//MOUSE METHODS
	public void mouseEntered( MouseEvent e ) {}
  	public void mouseExited( MouseEvent e ) {}
  	public void mouseClicked( MouseEvent e ) {}
  	public void mousePressed( MouseEvent e ) {
      	isButtonPressed = true;
      	if((settingBox.contains(mx,my))==false && isButtonPressed == true){	//Settings is closed if mouse is clicked outside the settings pop-up
			settingEnabled=false;
		}
   }
   	public void mouseReleased( MouseEvent e ) {
   		if (currentSelect.contains(mx,my)&&settingEnabled == false && helpEnabled == false){ //if you select the map
   		//pretty much does the same thing as if you press enter, look at that code
   			try{
				Level1a.clear();
				Level1b.clear();
				Level1c.clear();
				textFile(Level1a,paths1[loc]);
    			textFile(Level1b,paths2[loc]);
    			if(paths3[loc]!=null){
    				textFile(Level1c,paths3[loc]);
    			}
    			mainMusic.stop();
				game.resetEverything(Level1a,Level1b,Level1c,maps1[loc],maps2[loc],themeMusic[loc]);
				musicPlayed=false;

			}
			catch(IOException ex){
			}
   		}
   		//This comment is for moving map left and right. does the same thing pressing left arrow key and right arrow key
   		if(nextSelect.contains(mx,my)&&settingEnabled == false && helpEnabled == false){
   			loc=(loc+1)%4;
   		}
   		if(prevSelect.contains(mx,my)&&settingEnabled == false && helpEnabled == false){
   			if(loc==0){
   				loc=3;
   			}
   			else{
   				loc-=1;
   			}
   		}
   		//if the help bar is enabled, it just de-enables (?) it.
   		if(helpBarRect.contains(mx,my)){
   			helpEnabled = !helpEnabled;
   		}
   		if(settingBtn.contains(mx,my)){
			settingEnabled=!settingEnabled;
		}
   		if (helpEnabled&&helpNextRect.contains(mx,my)){ //if you press the left button or the right button on the
   		//move the help pictures
   			helpPicCounter++;
   		}
   		if (helpEnabled&&helpPrevRect.contains(mx,my)){
   			helpPicCounter--;
   		}
   		if (!settingBox.contains(mx,my)&&!helpBarRect.contains(mx,my)){//for the sound box
   			helpEnabled = false;
   			helpPicCounter = 10000000*helpPics.length;
   		}

   		if(soundBox.contains(mx,my)){										//Toggles Sound on/off
					soundEnabled = !soundEnabled;

					if(soundEnabled){ //for moving the slider for the volume to off when sound is not playing
						soundSlideRect.x=650;
						mainMusic.loop();
					}
					if(!soundEnabled){
						playCheck=true;
						soundSlideRect.x=591;
						mainMusic.stop();
					}
		}

      isButtonPressed = false;
   }
   	public void mouseMoved( MouseEvent e ) {
      	mx = e.getX();
      	my = e.getY();
   }
   	public void mouseDragged( MouseEvent e ) {
   		mx = e.getX();
      	my = e.getY();
   		if (soundSlideRect2.contains(mx,my)&&isButtonPressed&&mx>600&&mx<600+226){	//Moves the sound slider
      			soundSlideRect.x=mx-10;
      			sm.changeVolume((soundSlideRect.x-600.0)/226.0);//sets the volume
      		}
      	}
}
