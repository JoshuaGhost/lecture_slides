package output;

public class Point2D {
	private double x;
	private double y;
	
	public Point2D(double px, double py){
		this.x = px;
		this.y = py;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	@Override 
	public String toString(){
		return x + ", " + y;
	}
}
