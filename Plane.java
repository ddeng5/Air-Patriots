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

	
}
