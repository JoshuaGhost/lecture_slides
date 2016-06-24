
public class AStabilityTest {
	
	double[] kWerte = {0.00000001, 1,2000};
	iFunction function;
	
	public AStabilityTest(iFunction function) {
		this.function = function;
	}
	
	public boolean check() {
		double current;
		for(double k : kWerte) {
			current = 1;
			function.reset();
			function.setK(k);
			for(int i = 0; i<500;i++) {
				if(current < (current = function.calculateNext())) {
					return false;
				};
			}
		}
		return true;
	}

}
