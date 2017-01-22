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
}
