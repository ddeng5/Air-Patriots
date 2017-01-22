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


    public void makeJet(){//setting the stats for the jet
    	type = "jet";
    	HP = 200;
    	maxHP=HP;
    	glowx=25;
    	glowy=25;
    	Power = 3;
    	Speed = 2;
    	Radius  = 600;
    	coinCost=20;
    	fireRate=5;

    	fireRateM=50;
    	powerM = 45;
    	speedM = 75;
    	radiusM = 60;

    	sprite = new ImageIcon("images/jet.png").getImage();
    	planeHeight = 100;
    	planeWidth=100;
    }
    public void setMoveType(String n){//to change the moveType of the plane from straight to array
    	moveType = n;
    }

    public void draw (Graphics g){//this function handles all of the drawing of the planes
    	//drawing the planes hp bar
    	g.setColor(Color.RED);
    	g.fillRect(getX()-(maxHP/2),getY()-50,maxHP,3);
    	g.setColor(Color.GREEN);
    	g.fillRect(getX()-(maxHP/2),getY()-50,HP,3);
    	//


    	//rotating the screen, then drawing the sprite on the rotated screen, and then rotating the screen back to its original state
    	Graphics2D g2D = (Graphics2D)g;
		AffineTransform saveXform = g2D.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(Math.toRadians(Heading),(p.x+sprite.getWidth(null)/2),(p.y+sprite.getHeight(null)/2));
		g2D.transform(at);
		g2D.drawImage(sprite,(int)p.x,(int)p.y,panel);
		if(type.equals("heli")){//if its a helicopter draw the blade animations
			g2D.drawImage(animation[counter/10],getX()-50,getY()-50,panel);
			counter=(counter+1)%30;
		}
		g2D.setTransform(saveXform);
		g.setColor(Color.WHITE);//set the color back to white so that the other drawing colors dont get messed up
    }

    //just accessor functions
	public int getX(){return (int)p.x + (int)sprite.getWidth(null)/2;}
	public int getY(){return (int)p.y + (int)sprite.getHeight(null)/2;}
	public int getX2(){return (int)p.x ;}
	public int getY2(){return (int)p.y ;}
    public String getType(){return type;}
    public int getHP(){return HP;}
    public int getPower(){return Power;}
    public int getSpeed(){return Speed;}
    public void setHP(int l){HP = l;}

    //we only use the set functions when the user is upgrading so we add the cost of the upgrade so that we can later calculate the sale price
    public void setPower(int l){

    	coinCost+=getPowerCost();
    	Power = l;
    }
    public void setSpeed(int l){
    	coinCost+=getSpeedCost();
    	Speed = l;
    }
    //
    public Rectangle getRect(){return planeRect;}
    public String getMoveType(){return moveType;}
    public int getRange(){return Radius;}
    public void setRange(int l){
    	coinCost+=getRadiusCost();
    	Radius=l;
    }
    public int getCost(){return coinCost;}
    public int getGlowX(){return -glowx;}
    public int getGlowY(){return -glowy;}
    public int getFireRate(){return fireRate;}
    public void setFireRate(int l){
    	coinCost+=getFireRateCost();
    	fireRate=l;
    }
    public void setHeading(double h){Heading = h;}
    public int Attack(){
    	return Power;
    }
    public int getFireRateCost(){return fireRateM*(upgradeCount[3]+1);}
    public int getPowerCost(){return powerM*(upgradeCount[1]+1);}
    public int getSpeedCost(){return speedM*(upgradeCount[0]+1);}
    public int getRadiusCost(){return radiusM*(upgradeCount[2]+1);}

    public int getSalePrice(){
    	return (coinCost)/2;
    }

    public void removeLast(){//just removes the last point from the list, used for when the user presses backspace
    	if (Pos.size()>0){
    		Pos.remove(Pos.size()-1);
    	}
    }

    public void move(Graphics g,Plane pl,GamePanel game){//this function handles moving the plane and setting the directions
    	shotCount++;
    	if (moveType.equals("straight")){
    		if (Pos.size()==0){//if you dont have any points in the path and your plane is off screen
    			if (p.x+sprite.getHeight(null)>1280 || p.x<0){
	    			Heading+=5*greaterAngle(Heading,Heading+180);//then we have to turn the plane around to get it back
	    		}
	    		if (p.y+sprite.getWidth(null)>750 || p.y<0){
	    			Heading +=5*greaterAngle(Heading,Heading+180);//same thing just checking the top and bottom
	    		}
    		}

    		p.setLocation(p.getX()+Math.cos(Math.toRadians(Heading))*Speed,p.getY()+Math.sin(Math.toRadians(Heading))*Speed);//move the plane in the direction of the heading and the distance is speed
    		planeRect = new Rectangle((int)p.getX(),(int)p.getY(),planeHeight,planeWidth);//update the rect surrounding the plane each time its moved

    	}
    	if (Pos.size()!=0){//othewise if there are points in the path of the plane
    		if (this==pl){//if this plane is the selcted plane
    			for (int i=0; i<Pos.size();i++){//draw the current path that the plane is traveling along
	    			g.drawImage(flag,(int)Pos.get(i).x,(int)Pos.get(i).y,game);
	    		}

	    		g.setColor(Color.RED);
	    		if (loop){//if the plane is in a loop then change the line colour from red to green
	    			g.setColor(Color.GREEN);
	    		}
	    		for (int i=0; i<Pos.size()-1;i++){
	    			g.drawLine((int)Pos.get(i).x+10,(int)Pos.get(i).y+10,(int)Pos.get(i+1).x+10,(int)Pos.get(i+1).y+10);
	    		}
    		}

    		double h1 = ((180+Math.toDegrees(Math.atan2((getY()-Pos.get(0).y),(getX()-Pos.get(0).x)))));//calculate the heading from where your plane is to the next point in the path
    		if(isOffScreen(getX(),getY())==false || Math.abs(Math.abs(Heading%360)-h1)>=5){//if your plane is not off the screen and your heading is not within 5 degress of the desired heading
    			Heading+=2*greaterAngle(Heading,h1);//then turn the heading until we are going in the right direction
    		}
    		if(planeRect.contains((int)Pos.get(0).x,(int)Pos.get(0).y)){//once we reach the next point in the path
    			if (loop && Pos.size()>0){//if the plane is on a loop re add the point to the beginning
    				Pos.add(Pos.get(0));
    			}
    			Pos.remove(0);//other wise remove it
    		}
    	}
    	Heading=Heading%360;//just so that the heading does not get huge, not nessesary just so its easier to debug
    }


    public double distance(Point p, Point p2){//finds the distance from point a to point b, overloaded for convinence
    	return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
    }
    public double distance(int x,int y, Point p2){
    	return distance(new Point(x,y),p2);
    }

    public void addPoint(int x, int y){//adds the point the the planes current path
    	if (Pos.size()>0&&distance(Pos.get(0),new Point(x,y))<=50){//if the path is within 50 pixels from the first point in the path
    		loop = true;//put the plane in a loop
    	}else{
    		Pos.add(new Point (x,y));//other wise just add it
    	}

    }
    public String getStats(){//just getting the stats for debugging purposes
    	return ("Type: "+type+"\n HP: "+HP+"\nPower: "+"\nSpeed: "+Speed+"\nRadius: "+Radius);
    }
    public String toString(){
    	return getStats();
    }
    public ArrayList<Point> getPath(){//gets the current path that the plane is on
    	return Pos;
    }
    public void clearPath(){//clears the current path that the plane is on, if its looped undo it
    	Pos.clear();
    	loop = false;
    }

    public Bullet shoot(ArrayList<Balloon> balloons){//checks wether or not the plane can shoot
    	//depending on the type of plane they will shoot different so we farm off to different methods
		if(type.equals("jet")){
			return jetShoot(balloons);
		}
		if(type.equals("bomber")){
			return bomberShoot(balloons);
		}
		if(type.equals("heli")){
			return heliShoot(balloons);
		}
	return null;
    }
	public Bullet jetShoot(ArrayList<Balloon> balloons){//shoot method for the jet
		for(Balloon b:balloons){
			double h = Math.toDegrees(Math.atan2(p.y-b.getY(),p.x-b.getX()))+180;//find the angle from the plane to the balloon
			if(distance(getX(),getY(),b.getPoint())<=Radius && Math.abs(Heading%360-h%360)<=30 && shotCount>fireRate){//if the distance from the plane to the balloon is within the radius and the angle is within 30 degrees from the front of the plane AND the shoot cooldown hads been reached
				b.getHit(Attack());//hit the guy right away, this is more efficent than bullet tracking and for this game it works better because the balloons should not be able to dodge bullets
				shotCount=0;//reset the shoot cooldown
				return new Bullet(new Point((int)getX(),(int)getY()),b.getX(),b.getY(),panel,"jet");//return the bullet which will be added to a list in GamePanel
			}
		}
		return null;//otherwise if you cant shoot then just return null and the method in gamePanel will filter it
	}
	public Bullet bomberShoot(ArrayList<Balloon> balloons){//shoot for the bomber
		boolean shot=false;
		for(Balloon b:balloons){
			if(distance(getX(),getY(),b.getPoint())<=Radius && shotCount>fireRate){//checks the same thing as above but ignores the heading difference because it hits an area
				b.getHit(Attack());//hit the balloon if its within but dont return right after so that it hits all balloons in one shot instead of just one
				shot=true;//let the method know they we did find a balloon
			}
		}
		if(shot){//if a balloon was found then draw the animation so that the bomber isnt wasting ammo, so to speak
			shotCount=0;
			return new Bullet(new Point((int)getX(),(int)getY()),0,0,panel,"bomber");
		}
		return null;
	}

	public Bullet heliShoot(ArrayList<Balloon> balloons){//heli shoot is exactly the same as jet shoot except it returns a helit type bullet
		for(Balloon b:balloons){//i realize this method could be eliminated quite easily but at this point its not worth it
			double h = Math.toDegrees(Math.atan2(p.y-b.getY(),p.x-b.getX()))+180;
			if(distance(getX(),getY(),b.getPoint())<=Radius && Math.abs(Heading%360-h%360)<=30 && shotCount>fireRate){
				b.getHit(Attack());
				shotCount=0;
				return new Bullet(new Point((int)getX(),(int)getY()),b.getX(),b.getY(),panel,"heli");
			}
		}
		return null;
	}


	public boolean isOffScreen(int x,int y){//checks if the given point is off the screen, useful for checking the planes on screen state
		if(x+50<0 || x-50>1280){
			return true;
		}
		if(y+50<0 || y-50>750){
			return true;
		}
	return false;
	}
	public boolean isOffScreen(Point p){//overloaded for ease
		return isOffScreen((int)p.x,(int)p.y);
	}
	private int greaterAngle(double a1,double a2)//this method finds wether its faster to go clock wise or counter clockwise to get from angle a1 to a2
    {//courtesy of Ron McKenzie, thanks sir
		int ang1 = ((int)(a1)+360)%360;//get the angles positive and within 0-360
		int ang2 = ((int)(a2)+360)%360;

		if(ang2 == ang1)  //if the angles are equal then return 0
		    return 0;
		if(ang2 > ang1 && ang2 - ang1 <= 180) //if angle 2 is greater than angle one and the difference between the 2 is less than 180
		    return 1;//its faster to go right
		if(ang2 < ang1 && (ang2+360) - ang1 <= 180)//aswell as if ang2 is less than ang1 and the difference is less than 180
		    return 1;
		return -1;//if nothing gets returned up to here then its fastest to go left
    }

    public void getHit(int x){//this is a tricky one, dont really remeber what we did here.
    	HP-=x;
    }
}
