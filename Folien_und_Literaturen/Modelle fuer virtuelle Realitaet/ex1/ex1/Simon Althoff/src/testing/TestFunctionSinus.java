package testing;
import function.Function;



public class TestFunctionSinus extends Function{

	@Override
	public double valueAt(double x) {
		return 2*Math.sqrt(x);
	}

}
