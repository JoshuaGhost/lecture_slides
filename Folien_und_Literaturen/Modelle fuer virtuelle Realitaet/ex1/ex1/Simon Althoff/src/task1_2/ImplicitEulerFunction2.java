package task1_2;
import integrator.Integrator;
import function.Function;


public class ImplicitEulerFunction2 extends Integrator {

	//f(t1) = dt*f(t1) + f(t0)
	@Override
	public double integrationFunction(double currentValue, Function fd,
			double dx) {
		return PQFormulaSolver.solve(-1/dx, currentValue/dx).get(0);
	}

}
