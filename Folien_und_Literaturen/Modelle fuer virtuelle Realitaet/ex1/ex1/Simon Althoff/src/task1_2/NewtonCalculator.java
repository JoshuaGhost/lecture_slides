package task1_2;
import function.Function;


public class NewtonCalculator {
	
	//Gleichung des Impliziten Eulers: f(t1) = dt*g(f(t1)) + f(t0)
	//Erstelle Funktion mit Nullstelle bei f(t1):  p(x) = dt*g(x) - x + f(0)
	//Finde diese Nullstelle mit dem Newtonverfahren
	//Als Ableitung von p(x) ergibt sich p(x)' = dt*g'(x) - 1
	
	public static double newton(double curValue, Function f, double dt){
		
		double currentX = curValue;
		double fc = dt*f.valueAt(currentX)-currentX+curValue;
		
		while(fc > 0.0000001){
			double fdc = dt*deriveFunction(f,currentX) - 1;
			currentX = curValue - fc/fdc;
			fc = dt*f.valueAt(currentX)-currentX+curValue;
		}
		
		return currentX;
	}
	
	
	//Bestimmt die Ableitung über den Differenzquotienten
	public static double deriveFunction(Function f, double x){
		double h = 0.0000000001;
		return (f.valueAt(x+h)-f.valueAt(x-h))/(2*h);
	}
}
