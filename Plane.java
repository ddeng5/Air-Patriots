//This class keeps track of all the information for the plane.
//it also controls rotating and drawing the plane and editing the path
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;

public class Plane {
	//Initializing all the variables we will need to store
	private String type;
	Image flag = new ImageIcon("images/redflag.png").getImage();
	private int HP;
	private int maxHP;
	private int Power;
	private int Speed;
	private int Radius;
	private int coinCost;
	private int fireRate;
	private String [] upgradeStrings = new String[]{"Speed","Power","Radius","Fire Rate"};
	private int [] upgradeCount = new int[]{0,0,0,0};//this keeps track of how many times the user has upgraded each stat

	private ArrayList<Point> Pos = new ArrayList();
	private Point p = new Point(400,300);
	private double Heading = 48;//the direction in which the plane will be moving
	private Image sprite; //this is what the image will be depending on the type of plane
	private String moveType = "straight"; //or it could be moving from the array so the moveType = "array"
	private Rectangle planeRect;
	private GamePanel panel;
	private int planeHeight,planeWidth;
	private int counter=0;
	private int shotCount = 0;
	private int glowx,glowy;
	private boolean loop = false;
	private Image[] animation;//used to store any animations needed for the given plane

	private int fireRateM;
	private int powerM;
	private int speedM;
	private int radiusM;

	public String[] getUpgradeStrings(){return upgradeStrings;}
	public void setUpgradeStrings(String [] l){upgradeStrings = l;}
	public int[] getUpgradeCount(){return upgradeCount;}
	public void setUpgradeCount(int [] l){upgradeCount = l;}


    public Plane(String t, GamePanel pan, Point p1,double heading ) {
    	panel = pan;
    	p=p1;
    	Heading=heading;
    	if (t.equals("heli")){ //there are three types of planes with all their stats below
    		makeHeli();
    	}else if (t.equals("bomber")){
    		makeBomber();
    	}else if (t.equals("jet")){
    		makeJet();
    	}
    }
    public void makeHeli(){//setting all of the stats for the helicopter
    	glowx=25;
    	glowy=25;

    	fireRateM=75;
    	powerM = 60;
    	speedM = 90;
    	radiusM = 85;


    	type = "heli";
    	HP = 250;
    	maxHP=HP;
    	Power = 15;
    	Speed = 3;
    	Radius = 800;
    	coinCost=70;
    	fireRate=30;
    	sprite = new ImageIcon("images/heli.png").getImage();
    	animation=new Image[4];
    	for(int i=0;i<4;i++){
    		animation[i]=new ImageIcon("images/blade"+(i+1)+".png").getImage();//the helicopter has an animation where the blade spins
    	}
    	planeHeight = sprite.getHeight(null);
    	planeWidth= sprite.getWidth(null);
    }
    public void makeBomber(){//setting the stats for the bomber
    	glowx=25;
    	glowy=25;
    	type = "bomber";
    	HP = 225;
    	maxHP=HP;
    	Power = 10;
    	Speed = 1;
    	Radius = 100;
    	coinCost=50;

    	fireRateM=100;
    	powerM = 150;
    	speedM = 130;
    	radiusM = 95;

    	sprite = new ImageIcon("images/bomber.png").getImage();
    	fireRate=50;
    	planeHeight = sprite.getHeight(null);
    	planeWidth= sprite.getWidth(null);
    }
    
}
