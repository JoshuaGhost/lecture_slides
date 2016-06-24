package task1_2;
import java.util.ArrayList;
import java.util.List;


public class PQFormulaSolver {
	static List<Double> solve(double p, double q){
		List<Double> results = new ArrayList<Double>();
		
		double frontTerm = (-1.0/2.0)*p;
		double backTerm = Math.sqrt(Math.pow(p/2.0,2.0)-q);
		
		double result1 = frontTerm + backTerm;
		double result2 = frontTerm - backTerm;
		
		if(result2 != result1){
			results.add(result2);
		}
		
		results.add(result1);
		return results;
	}
}
