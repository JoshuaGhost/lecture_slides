package output;

public class Point3D {
	
	private double t;
	private double x;
	private double y;
	
	
	public Point3D(double pt, double px, double py){
		this.t = pt;
		this.x = px;
		this.y = py;
	}
	
	public double getT() {
		return t;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	@Override 
	public String toString(){
		return t + ", " + x + ", " + y;
	}
}