package task1_5;
import function.Function;


public class DahlquistFunction extends Function {

	private double k;
	
	public DahlquistFunction(double pk) {
		this.k = pk;
	}

	public void setK(double k) {
		this.k = k;
	}
	
	public double getK() {
		return this.k;
	}

	@Override
	public double valueAt(double x) {
		return -k*x;
	}

}
