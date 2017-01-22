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
												public Point(int px, int py) {
									    	x=(double)px;
									    	y=(double)py;
									    	z=0;//if you dont enter a z value deafult it to 0
									    }
									    public Point(double px,double py){
									    	x = px;
									    	y = py;
									    	z=0;
										}
										public Point(int px, int py, int pz) {
									    	x=(double)px;
									    	y=(double)py;
									    	z = (double)pz;
									    }
									    public Point(double px,double py, double pz){
									    	x = px;
									    	y = py;
									    	z=pz;
										}
}
