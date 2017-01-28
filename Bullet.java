import java.util.*;
import java.awt.*;
//import java.io.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.*;

public class Bullet {//this is the bullet class, it takes care of what type of bullet it is
//the animations, and the hitting and damage 
//fields are prety self explanatory
	private Point p; //where the bullet is
	private double Heading;
	private Image Sprite;
	private Image[] animation; //for the bomber since it has a blowup bullet thing
	private GamePanel panel;
	private int endx,endy,counter;
	private String type;
    public Bullet(Point p,int endx,int endy,GamePanel pan,String type) {
    	//gives each field its correct values
    	counter=0;
    	this.p=p;
    	this.endx=endx;
    	this.endy=endy;
    	this.type=type;
    	if(type.equals("jet")){ //just adds the images for each type of bullet you get
    		Sprite = new ImageIcon("images/bullet.png").getImage();
    	}
    	if(type.equals("bomber")){
    		animation= new Image[3];
    		animation[0]=new ImageIcon("images/fire1.png").getImage();
    		animation[1]=new ImageIcon("images/fire2.png").getImage();
    		animation[2]=new ImageIcon("images/fire3.png").getImage();
    	}
    	if(type.equals("heli")){
    		Sprite=new ImageIcon("images/heliMiss.png").getImage();
    	}
    	if(type.equals("balloon")){
    		Sprite=new ImageIcon("images/balloonMiss.png").getImage();
    	}
    }
    public void move(){ //sets the location moving to the heading 
    	if (type.equals("heli") || type.equals("jet") || type.equals("balloon")){
    		Heading = Math.toDegrees(Math.atan2(p.y-endy,p.x-endx))+180;
    		p.setLocation(p.getX()+Math.cos(Math.toRadians(Heading))*10,p.getY()+Math.sin(Math.toRadians(Heading))*10);
    	}
    }
    //accessors
    public int getX(){
    	return (int)p.x;
    }
    public int getY(){
    	return (int)p.y;
    }
    public void draw(Graphics g){ //draws the bullet 
    	if(type.equals("jet")){ //if its the jet its th yellow
    		g.drawImage(Sprite,(int)p.x - Sprite.getWidth(null)/2,(int)p.y - Sprite.getHeight(null)/2,panel);
    	}
		if(type.equals("bomber")){ //if its the bomber then you have the bing bomb sprite
			if (counter/15<animation.length-1){
				g.drawImage(animation[counter/15],(int)p.x - animation[counter/15].getWidth(null)/2,(int)p.y - animation[counter/15].getHeight(null)/2,panel);
				counter++;
			}
			
		}
		if(type.equals("heli")||type.equals("balloon")){ //if its the heli then you have the missile
		//this makes the missile rotate according to the heading
			Graphics2D g2D = (Graphics2D)g;
			AffineTransform saveXform = g2D.getTransform();
			AffineTransform at = new AffineTransform();
			at.rotate(Math.toRadians(Heading),(p.x+Sprite.getWidth(null)/2),(p.y+Sprite.getHeight(null)/2));
			g2D.transform(at);
			g2D.drawImage(Sprite,(int)p.x,(int)p.y,panel);
			g2D.setTransform(saveXform);
		}
	}
	public String toString(){
		return (p.x+"    "+p.y);
	}
	public double distance(Point p, Point p2){ //to make it easier
    	return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
    }
	public boolean reachedEnd(){ //checks if it hits something or if it reached the end of the range from the plane
		if(type.equals("jet")|| type.equals("heli")||type.equals("balloon")){
			if(distance(p,new Point(endx,endy))<=15){
				return true;
			}
		}
		if(type.equals("bomber")){ //or if the bomber's counter is up
			if(counter==30){
				return true;
			}
		}
	return false;
	}
}