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
	public void makeBoss(){
		fireRate=50;
		Radius=300;
		damage=3;
		HP=2000;
		maxHP=HP;
		Speed=20;
		coinValue=30;
		hpScale=10;
		Sprite = new ImageIcon("images/nukeBoss.png").getImage();
	}
	public boolean move(){//just moves to the next position in the list of positions it has to go
		shotCount+=1;
		if (HP>0){
			//updates point
			p=path.get(count);
			//updates rectangle
			balloonRect = new Rectangle ((int)p.getX()- Sprite.getWidth(null)/2,(int)p.getY()- Sprite.getWidth(null)/2,rectLength,rectWidth);
			count++;
			if (count>=path.size()-1){
				return false;
			}
			
			return true;
		}
	return true;	
	}
	public void draw(Graphics g,Balloon selectedBalloon){ //draws the balloon
		//if this is selectedballoon, draw the circle around it to see its range
		if(this==selectedBalloon){
			g.drawOval((int)p.x-(Radius/2),(int)p.y-(Radius/2),Radius,Radius);
		}
		if(HP>0){ //as long as the balloon is alive
			//the health bar
			g.setColor(Color.RED); 
			g.fillRect((int)p.x-maxHP/(2*hpScale),(int)p.y-30,maxHP/hpScale,3);
			g.setColor(Color.GREEN);
			g.fillRect((int)p.x-maxHP/(2*hpScale),(int)p.y-30,HP/hpScale,3);
			g.setColor(Color.RED);
			//draws the balloon
			g.drawImage(Sprite,(int)p.x - Sprite.getWidth(null)/2,(int)p.y - Sprite.getHeight(null)/2,panel);
		}
		else{ //if balloons dead, it runs the blow up animation
			if (anCount<animation.length-1){
				g.drawImage(animation[anCount],(int)p.x-19,(int)p.y-22,panel);
				//draws the coins and makes it go up
				g.drawImage(coin,(int)p.x,(int)p.y-anCount,panel);
				g.setColor(Color.WHITE);
				g.drawString("+"+coinValue,(int)p.x+22,(int)p.y-anCount+17);
				anCount++;
			}
		}
	}
	//accessors
	public void getHit(int power){
		HP-=power;
	}
	public Point getPoint(){
		return p;
	}
	public int getX(){
		return (int)p.x;
	}
	public int getY(){
		return (int)p.y;
	}
	public int getZ(){
		return (int)p.z;
	}
	public int getHP(){
		return HP;
	}
	public int getMaxHP(){
		return maxHP;
	}
	public Rectangle getRect(){
		return balloonRect;
	}
	public int getCoinValue(){
		return coinValue;
	}
	public int getAnimationCounter(){
		return anCount;
	}
	public double distance(Point p, Point p2){ //makes finding the distance a little cleaner
    	return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
    }
    public double distance(int a,int b,int c,int d){
    	return distance(new Point(a,b),new Point(c,d));
    }
    public String getType(){
    	return type;
    }
    public Image getSprite(){
    	return Sprite;
    }
    public int getDamage(){
    	return damage;
    }
    //end of accessors
    public Bullet shoot(ArrayList<Plane> planes){ //finds a plane close to the balloon and shoots it down
    	for(Plane p:planes){
	    	if(distance(getX(),getY(),p.getX(),p.getY())<=Radius && shotCount%fireRate==0){
					p.getHit(damage);
					return new Bullet(new Point((int)getX(),(int)getY()),p.getX(),p.getY(),panel,"balloon");
			}
    	}
    return null;
    }
}

