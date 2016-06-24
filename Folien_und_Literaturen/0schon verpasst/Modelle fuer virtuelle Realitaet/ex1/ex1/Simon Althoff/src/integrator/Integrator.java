package integrator;

import function.Function;
import output.Point2D;
import output.PointList2D;


public abstract class Integrator {
	public PointList2D integrate(Function fd, double initialValue, int n, double dx){
		PointList2D result = new PointList2D();
		
		double curValue = initialValue;
		result.add(new Point2D(0, curValue));
		
		for(int i = 0; i < n; i++){
			curValue = integrationFunction(curValue,fd,dx);
			result.add(new Point2D(i*dx, curValue));
		}
		
		return result;
	}
	
	public abstract double integrationFunction(double currentValue, Function fd, double dx);
}
