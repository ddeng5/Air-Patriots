import java.awt.*;
import java.util.*;
import javax.swing.*;
class Button{//this class is used for when we make buttons where we need to
	private Image normal,hover,pushed;
	private Rectangle rect;
	private boolean mousePushed; //if its button is pushed or not
	private int mx,my;
	private int x,y;
	private GamePanel panel; 
	public Button(int x,int y,Image normal,Image hover,Image pushed, GamePanel pan){
		panel=pan;
		mx=0;
		my=0;
		this.x=x;
		this.y=y;
		this.normal=normal;
		this.hover=hover;
		this.pushed=pushed;
		rect=new Rectangle(x,y,normal.getWidth(null),normal.getHeight(null));
	}
	public void update(){//updates the mx,my so that the button knows exactly where the mouse is
		this.mx=panel.mx;
		this.my=panel.my;
		this.mousePushed=panel.isButtonPressed;
	}
	public void draw(Graphics g){ //just draws it
		update();
		g.drawImage(normal,x,y,panel); //normal
		if(rect.contains(mx,my)){
			g.drawImage(hover,x,y,panel);//if you're on the rect then hover
			if(mousePushed){ //if youre pressing it then pushed
				g.drawImage(pushed,x,y,panel);
			}
		}
	}
	public boolean isHover(){ //if its over
		update();
		if(rect.contains(mx,my)){
			return true;
		}
		return false;
	}
	public boolean isPushed(){ //if its pushed
		update();
		if(rect.contains(mx,my) && mousePushed){
			return true;
		}
		return false;
	}
	public Button clone(int x1,int y1){ //if you wanna have another button
		return new Button(x1,y1,normal,hover,pushed,panel);
	}
}