//this is just a modified verion of javas Point object
//we needed a Z value to know where to draw the balloons and we also didnt like a few things about the deafult Point class
//so this was born to make things easier

public class Point {
	double x,y,z; 
	
	//our point takes an int or a double, take that java
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
	
	//all of these methods are the same as javas point except with the incorporated z value
	public double getX(){return x;}
	public double getY(){return y;}
	public double getZ(){return z;}
	
	public void translate(int dx, int dy){
		x+=dx;
		y+=dy;
	}
	public void translate(int dx, int dy, int dz){
		x+=dx;
		y+=dy;
		z+=dz;
	}
	public void setLocation(int px, int py, int pz) {
    	x=(double)px;
    	y=(double)py;
    	z = (double)pz;
    }
    public void setLocation(int px, int py) {
    	x=(double)px;
    	y=(double)py;
    }
    public void setLocation(double px, double py, double pz) {
    	x=px;
    	y=py;
    	z=pz;
    }
    public void setLocation(double px, double py) {
    	x=px;
    	y=py;
    }
    public void move(int px, int py, int pz) {
    	x=(double)px;
    	y=(double)py;
    	z = (double)pz;
    }
    public void move(int px, int py) {
    	x=(double)px;
    	y=(double)py;
    }
    public void move(double px, double py, double pz) {
    	x=px;
    	y=py;
    	z=pz;
    }
    public void move(double px, double py) {
    	x=px;
    	y=py;
    }
    public String toString(){
    	return ""+x+" "+y+" "+z;
    }

}