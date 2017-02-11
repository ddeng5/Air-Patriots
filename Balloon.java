import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.geom.*;
class Balloon{ //takes care of the balloon moving, drawing and all the info associated with it
//the fields are pretty self explanatory for the most part
	private int Speed,HP,maxHP,count;
	private String type; //green (Easy), yellow (Medium), red(HARDD!)
	private Point p; //where teh balloon is
	private Image Sprite,coin; //the picture for the coin animation
	private ArrayList<Point> path; //the path the balloon is gonna take
	private Rectangle balloonRect; //the rectangle for collision checking
	private int rectLength = 38; //how big the rectangle is suposed to be
	private int rectWidth = 44;
	private int coinValue; //how much the balloon is worth
	private int damage;
	private boolean balFlag = true;
	private GamePanel panel;
	private int anCount =0; //how far in the blowup animation
	private int hpScale=1; //just to scale the HP so that the hpbar doesnt get too big
	private Image [] animation;
	private int fireRate; //how fast balloons shoot
	private int shotCount=0; //framecounter to make sure they dont shoot too fast
	private int Radius;
	public Balloon(String type,ArrayList<Point> path, GamePanel pan, Image [] an){
		panel = pan;
		this.type=type;
		this.path=path;
		count=0;
		p=path.get(count);
		coin = new ImageIcon("images/coin.png").getImage();
		balloonRect = new Rectangle ((int)p.getX(),(int)p.getY(),rectLength,rectWidth);
		animation = an;
		if(type.equals("green")){
			makeGreen();
		}
		if(type.equals("yellow")){
			makeYellow();
		}
		if(type.equals("red")){
			makeRed();
		}
		if(type.equals("boss")){
			makeBoss();
		}
	}
	public void makeGreen(){//just updates the fields so that they correspond to the type of balloon
		fireRate=50;
		Radius=100;
		damage=3;
		HP=30;
		maxHP=HP;
		Speed=10;
		coinValue=2;
		Sprite = new ImageIcon("images/greenBalloon.png").getImage();
	}
	public void makeYellow(){
		fireRate=50;
		Radius=100;
		damage=3;
		HP=45;
		maxHP=HP;
		Speed=15;
		coinValue=4;
		Sprite = new ImageIcon("images/yellowBalloon.png").getImage();
	}
	public void makeRed(){
		fireRate=50;
		Radius=100;
		damage=3;
		HP=60;
		maxHP=HP;
		Speed=20;
		coinValue=6;
		Sprite = new ImageIcon("images/redBalloon.png").getImage();
	}

}
