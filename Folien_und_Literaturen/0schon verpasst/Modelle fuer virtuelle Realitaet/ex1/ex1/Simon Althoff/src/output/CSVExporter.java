package output;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class CSVExporter {
	public static void export(String path, String name, PointList2D content){
		PrintWriter writer;
		List<Point2D> pointList = content.getPoints();
		try {
			writer = new PrintWriter(path + name + ".csv", "UTF-8");
			for(Point2D point: pointList){
				writer.println(point);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void export(String path, String name, PointList3D content){
		PrintWriter writer;
		List<Point3D> pointList = content.getPoints();
		try {
			writer = new PrintWriter(path + name + ".csv", "UTF-8");
			for(Point3D point: pointList){
				writer.println(point);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
