package output;
import java.util.ArrayList;
import java.util.List;


public class PointList2D {
	private List<Point2D> content;
	
	public PointList2D(){
		content = new ArrayList<Point2D>();
	}
	
	public void add(Point2D p){
		content.add(p);
	}
	
	public List<Point2D> getPoints(){
		List<Point2D> returnValue = new ArrayList<Point2D>();
		returnValue.addAll(content);
		return returnValue;
	}
}
