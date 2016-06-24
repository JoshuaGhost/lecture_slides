
public class Implizit_Euler implements iFunction {

	double deltaT;
	double current;
	double k;
	
	public Implizit_Euler(double deltaT) {
		this.deltaT = deltaT;
		this.current = 1;
		this.k = 1;
	}
	
	@Override
	public double calculateNext() {
		current = current / ( 1 + k*deltaT);
		return current;
	}

	@Override
	public void reset() {
		this.current = 1;
	}

	@Override
	public void setK(double k) {
		this.k = k;
	}
	
}
