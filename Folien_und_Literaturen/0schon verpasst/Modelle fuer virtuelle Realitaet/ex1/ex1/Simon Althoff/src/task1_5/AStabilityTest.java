package task1_5;
import integrator.Integrator;

import java.util.ArrayList;
import java.util.List;


public class AStabilityTest {
	private List<Double> toTest;
	
	public AStabilityTest(){
		toTest = new ArrayList<Double>();
		toTest.add(0.0001);
		toTest.add(0.001);
		toTest.add(0.01);
		toTest.add(0.1);
		toTest.add(0.5);
		toTest.add(0.99);
		toTest.add(1.0);
		toTest.add(2.0);
		toTest.add(3.0);
		toTest.add(4.0);
		toTest.add(5.0);
		toTest.add(6.0);
		toTest.add(7.0);
		toTest.add(8.0);
		toTest.add(9.0);
		toTest.add(10.0);
		toTest.add(100.0);
		toTest.add(1000.0);
		toTest.add(10000.0);
		toTest.add(100000.0);
		toTest.add(1000000.0);
	}
	
	public boolean runTest(Integrator integrator){
		if(toTest.isEmpty()){
			return true;
		}
		
		DahlquistFunction f = new DahlquistFunction(0.0);
		int n = 2000000;
		double dx = 0.1;
		
		
		
		for(double k : toTest){
			f.setK(k);

			double curValue = 1;
			double lastValue = 1;
			
			for(int i = 0; i < n; i++){
				curValue = integrator.integrationFunction(curValue,f,dx);
				if(curValue > lastValue || curValue < 0.0){
					System.out.println("Failed because not monotonic with k=" + k + " last=" + lastValue + "current=" + curValue);
					return false;
				}
				
				lastValue = curValue;

			}
			
			if(curValue > 0.00000001){
				System.out.println("Nicht gegen 0 konvergiert");
				return false;
			}
		}
		
		
		return true;
	}
}
