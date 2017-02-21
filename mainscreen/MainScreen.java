import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class MainScreen extends JPanel implements KeyListener{ 
	Image main = new ImageIcon("images/main.jpg").getImage(); 
	Image load = new ImageIcon("images/load.png").getImage(); 
	Image load2 = new ImageIcon("images/load2.png").getImage();
	Image blue = new ImageIcon("images/blue.jpg").getImage();
	Image cloud1 = new ImageIcon("images/cloud/3.png").getImage();
	Image [] levels = new Image[4];
	boolean loaded = false;
	private int loc=0;
	private boolean[] keys;
	Font loadingFont; //Calibri
	private int loading,count,loadingCounter,cloudmove1;
    public MainScreen(){ 
    	super();
    	for(int i=0;i<4;i++){
			Image img = Toolkit.getDefaultToolkit().getImage("images/"+(i+1)+".png");
			levels[i]=img;
		}
		keys = new boolean[KeyEvent.KEY_LAST+1];
		
		try{
			loadingFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("fonts/AgentOrange.ttf"))).deriveFont(0,15);
		}
		catch(IOException ioe){
			System.out.println("error loading AgentOrange.tff");
		}
		catch(FontFormatException e){
		}
		addKeyListener(this);

    }
    public void addNotify(){ 
		super.addNotify();
		requestFocus();
	}
	public void paintComponent(Graphics g){
		if(loaded==false){
			g.drawImage(levels[0],0,0,this);
			g.drawImage(levels[1],0,0,this);
			g.drawImage(levels[2],0,0,this);
			g.drawImage(levels[3],0,0,this);
			g.drawImage(blue,0,0,this);
			g.drawImage(main,0,0,this);
			loaded=true;
		}
			
		/*if(loadingCounter<400){
			
			g.drawImage(main,0,0,this);
			if(count>=10){
				loading=1;
			}
			if(count>=20){
				loading=0;
				count=0;
			}
			count++;
			if(loading==1){
				g.drawImage(load,550,650,this);
			}
			if(loading==0){
				g.drawImage(load2,550,650,this);
			}
			g.setFont(loadingFont);
			g.setColor(Color.WHITE);
			g.drawString("Loading",600,680);
			loadingCounter++;
		}*/
		if(loadingCounter>=0){
			cloudmove1++;
			for(int i=0;i<4;i++){
				if(loc==i){
					g.drawImage(blue,0,0,this);
					g.drawImage(cloud1,cloudmove1%(1280+512) - 512,400,this);
					g.drawImage(levels[i],0,0,this);
					
				}
					
			}
			
			
		}
		//System.out.println(loadingCounter);
		
		
	
		
	}

	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()]=false;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(loc+1<4){
				loc+=1;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(loc-1>=0){
				loc-=1;
			}
		}
	}

 

}