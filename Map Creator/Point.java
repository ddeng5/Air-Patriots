public class Point {
	double x,y,z; 
    public Point(int px, int py) {
    	x=(double)px;
    	y=(double)py;
    	z=0;
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