/**
 * @(#)AirPatriots.java
 *
 *
 * @author 
 * @version 1.00 2013/4/24
 */

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AirPatriots extends JFrame implements ActionListener{
	static MainScreen game; //game panel
	javax.swing.Timer myTimer;// timer
	javax.swing.Timer spawnTimer;

	
	
	
    
    
	
    public AirPatriots(){
    	super("AirPatriots!");
    	game = new MainScreen(); 
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    	setSize(1280,750);
    	setLayout(new BorderLayout());
    	setResizable(false);
    	myTimer = new javax.swing.Timer(1000/70,this);
    	myTimer.start();
    	spawnTimer = new javax.swing.Timer(1000,this);
    	spawnTimer.start(); 
    	add(game);
    	setVisible(true);
    }
    public void actionPerformed(ActionEvent e){ //called with timer
    	Object source=e.getSource();
    	if(source==myTimer){
    		game.repaint();
    	}


    }
    public static void main (String [] args)throws IOException {
    	AirPatriots a = new AirPatriots();
    	
    }
    
    
    
}