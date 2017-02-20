import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.Rectangle;
//import java.awt.Point;
import java.awt.Font;

public class mapCreator extends JFrame implements ActionListener{
	mapPanel map;
	javax.swing.Timer redrawTimer;
    public mapCreator() {
    	super("Map Creator");
		setLayout(null);
		setSize(1280,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		map = new mapPanel();
		add(map);
		redrawTimer = new javax.swing.Timer(20, this);
		redrawTimer.start();
		setVisible(true);
		setResizable(false);
    }
    public void actionPerformed(ActionEvent e){
		Object source=e.getSource();
		if(source==redrawTimer){//if the redraw timer ticks repaint the screen
			map.repaint();
			map.checkKeys();
		}
	}
	public static void main(String[]args){
		mapCreator frame=new mapCreator();
	}
}


class mapPanel extends JPanel implements MouseListener,MouseMotionListener,KeyListener{
	private boolean clicked;
	private int mx,my;
	Image imgMap;
	ArrayList<Point> pathPoints=new ArrayList<Point>();
	ArrayList<Point> undo=new ArrayList<Point>();
	Point mouse;
	private boolean []keys;
	BufferedWriter mapData;
	int z=0;
	public mapPanel(){
		setSize(1280,750);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		imgMap=new ImageIcon("ice.png").getImage();//load the map image
		keys=new boolean[KeyEvent.KEY_LAST+1];//setting the size of the keylist
	}
	public void mousePressed(MouseEvent e) {// if the user clicks set the clicked variable to true
		clicked=true;
    }
    public void mouseReleased(MouseEvent e) {//if the mouse is released set clicked to false
    	clicked=false;
    	if (pathPoints.size()==0){
			pathPoints.add(new Point(mx,my,z));
		}
		else{
			double d = distance(pathPoints.get(pathPoints.size()-1),new Point(mx,my,z));
			double h1 = (((Math.PI+Math.atan2((pathPoints.get(pathPoints.size()-1).y-my),(pathPoints.get(pathPoints.size()-1).x-mx)))));
			Point lastPoint=pathPoints.get(pathPoints.size()-1);
			for (int i=1; i<d;i++){
				pathPoints.add(new Point(lastPoint.x+(Math.cos(h1)*i),lastPoint.y+(Math.sin(h1)*i),z));
			}
		}
    }

    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseDragged(MouseEvent e){//if the user drags the mouse update the x and y to move the circle with their mouse 
    	mx=e.getX();
    	my=e.getY();
    }
    public void mouseMoved(MouseEvent e) {//if the user moves the mouse update the x and y to prevent from the location being thrown off
    	mx=e.getX();
    	my=e.getY();
    }
    public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e){//if a key gets pressed set that index of the list to true
		keys[e.getKeyCode()]=true;
	}
	public void keyReleased(KeyEvent e){//if the key is released set it to false
		keys[e.getKeyCode()]=false;
	}
	public void checkKeys(){
		if(keys[KeyEvent.VK_LEFT]){
			if(pathPoints.size()>0){
				undo.add(pathPoints.get(pathPoints.size()-1));
				pathPoints.remove(pathPoints.size()-1);
			}
		}
		if(keys[KeyEvent.VK_RIGHT]){
			if(undo.size()>0){
				pathPoints.add(undo.get(undo.size()-1));
				undo.remove(undo.size()-1);
			}
		}
		if(keys[KeyEvent.VK_ENTER]){
			saveToFile();
		}
		if(keys[KeyEvent.VK_UP]){
			z=1;
		}
		if(keys[KeyEvent.VK_DOWN]){
			z=0;
		}
	}
	public double distance(Point p, Point p2){
    	return Math.pow((Math.pow((p2.x - p.x),2) + Math.pow((p2.y - p.y),2)),0.5);
    }
    public double distance(int x,int y, Point p2){
    	return distance(new Point(x,y),p2);
    }
    public void paintComponent(Graphics g){
    	//System.out.println(pathPoints.size());
		g.drawImage(imgMap,0,0,this);
		g.setColor(Color.RED);
		/*if(clicked){
			if(pathPoints.indexOf(new Point(mx,my))==-1){
				if (pathPoints.size()==0){
					pathPoints.add(new Point(mx,my,z));
				}else{
					
					System.out.println(d+"");
					for (int i=1; i<(int)d;i++){
						if (pathPoints.get(pathPoints.size()-1).x!=mx&&pathPoints.get(pathPoints.size()-1).y!=my){
							pathPoints.add(new Point(pathPoints.get(pathPoints.size()-1).x+(Math.cos(h1)*i),pathPoints.get(pathPoints.size()-1).y+(Math.sin(h1)*i),z));
						}
						
					}
				}
			}
		}*/
		for(Point a:pathPoints){
			if(a.z==1){
				g.setColor(Color.GREEN);
			}
			else{
				g.setColor(Color.RED);
			}
			g.fillOval((int)a.x,(int)a.y,10,10);
		}
	}
	public void addNotify(){//making this window focused
		super.addNotify();
		requestFocus();
	}
	public void saveToFile(){
		try{
			mapData = new BufferedWriter(new FileWriter("mapData.txt"));//opening the file to write
			for(Point a:pathPoints){
				mapData.write(a.x+" "+a.y+" "+a.z);
				mapData.newLine();
			}
			mapData.flush();
			mapData.close();
			System.out.println("The Map has been saved to mapData.txt");
			System.exit(1);
		}
		catch(IOException e){
			System.out.println("There is no highscores.txt file!");
			System.exit(1);
		}
	}
}