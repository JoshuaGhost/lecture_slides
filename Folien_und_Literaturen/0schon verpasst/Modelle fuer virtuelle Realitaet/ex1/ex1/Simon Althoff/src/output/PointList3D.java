package output;
import java.util.ArrayList;
import java.util.List;


public class PointList3D {
	private List<Point3D> content;
	
	public PointList3D(){
		content = new ArrayList<Point3D>();
	}
	
	public void add(Point3D p){
		content.add(p);
	}
	
	public List<Point3D> getPoints(){
		List<Point3D> returnValue = new ArrayList<Point3D>();
		returnValue.addAll(content);
		return returnValue;
	}
}