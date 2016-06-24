package integrator;

import function.Function2D;
import output.Point3D;
import output.PointList3D;


public abstract class Integrator2D {
	public PointList3D integrate(Function2D fx, Function2D fy, double initialValueX, double initialValueY , int n, double dx){
		PointList3D result = new PointList3D();
		
		double curValueX = initialValueX;
		double curValueY = initialValueY;
		
		result.add(new Point3D(0, curValueX, curValueY));
		
		for(int i = 0; i < n; i++){
			double tempX = curValueX;
			double tempY = curValueY;
			
			curValueX = integrationFunction(tempX,fx,tempY,fy,dx);
			curValueY = integrationFunction(tempY,fy,tempX,fx,dx);
			result.add(new Point3D(i*dx, curValueX, curValueY));
		}
		
		return result;
	}
	
	protected abstract double integrationFunction(double currentValue, Function2D toIntegrate, double additionalValue, Function2D additionalFunction, double dt);
}
