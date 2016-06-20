package run;
import integrator.Integrator;
import integrator.Integrator2D;
import function.Function;
import function.Function2D;
import output.CSVExporter;
import output.PointList2D;
import output.PointList3D;
import task1_1.ExplicitEuler;
import task1_1.TestFunction1;
import task1_1.TestFunction2;
import task1_2.ImplicitEuler;
import task1_2.ImplicitEulerFunction1;
import task1_2.ImplicitEulerFunction2;
import task1_3.ExplicitEuler2D;
import task1_3.TestFunction2D1X;
import task1_3.TestFunction2D1Y;
import task1_3.TestFunction2D2X;
import task1_3.TestFunction2D2Y;
import task1_4.HeunIntegrator2D;
import task1_5.AStabilityTest;
import task1_5.CheatingIntegrator;
import task1_5.DahlquistImplicitEuler;
import testing.HeunIntegrator;

public class Main {
	public static void main(String... args){
		
		//Die einzelnen Aufgaben lassen sich auskopieren.
		
		//Task 1_1
		Function function1_1_1 = new TestFunction1();
		Function function1_1_2 = new TestFunction2();
		
		Integrator integrator = new ExplicitEuler();
		
		PointList2D result = new PointList2D();
		
		result = integrator.integrate(function1_1_1, 1, 10000, 0.01);
		CSVExporter.export("", "task1_1_function1_initial1", result);
		
		result = integrator.integrate(function1_1_1, 0, 10000, 0.01);
		CSVExporter.export("", "task1_1_function1_initial0", result);
		
		result = integrator.integrate(function1_1_2, 1, 10000, 0.01);
		CSVExporter.export("", "task1_1_function2_initial1", result);
		
		result = integrator.integrate(function1_1_2, 0, 10000, 0.01);
		CSVExporter.export("", "task1_1_function2_initial0", result);
		
		//Task 1_2
		Integrator integrator1_2 = new ImplicitEuler();
		Function function1_2_1 = new TestFunction1();
		Function function1_2_2 = new TestFunction2();
		
		PointList2D result2 = integrator1_2.integrate(function1_2_1, 1, 500, 0.001);
		CSVExporter.export("", "task1_2_function1_general_euler_initial1", result2);
		
		result2 = integrator1_2.integrate(function1_2_1, 0, 500, 0.001);
		CSVExporter.export("", "task1_2_function1_general_euler_initial0", result2);
		
		result2 = integrator1_2.integrate(function1_2_2, 1, 500, 0.001);
		CSVExporter.export("", "task1_2_function2_general_euler_initial1", result2);
		
		result2 = integrator1_2.integrate(function1_2_2, 0, 500, 0.001);
		CSVExporter.export("", "task1_2_function2_general_euler_initial0", result2);
		
		integrator1_2 = new ImplicitEulerFunction1();
		
		result2 = integrator1_2.integrate(function1_2_1, 1, 20000, 0.001);
		CSVExporter.export("", "task1_2_function1_specialized_euler_initial1", result2);
		
		result2 = integrator1_2.integrate(function1_2_1, 0, 20000, 0.001);
		CSVExporter.export("", "task1_2_function1_specialized_euler_initial0", result2);
		
		integrator1_2 = new ImplicitEulerFunction2();
		
		result2 = integrator1_2.integrate(function1_2_2, 1, 20000, 0.001);
		CSVExporter.export("", "task1_2_function2_specialized_euler_initial1", result2);
		
		result2 = integrator1_2.integrate(function1_2_2, 0, 20000, 0.001);
		CSVExporter.export("", "task1_2_function2_specialized_euler_initial0", result2);
		
		//Task 1_3
		
		Function2D function1X = new TestFunction2D1X();
		Function2D function1Y = new TestFunction2D1Y();
		Integrator2D integrator2D = new ExplicitEuler2D();
		
		PointList3D result3 = integrator2D.integrate(function1X, function1Y, 1, 1, 20000, 0.001);
		CSVExporter.export("", "task1_3_function1_explicit_euler_initial1_1", result3);
		
		Function2D function2X = new TestFunction2D2X();
		Function2D function2Y = new TestFunction2D2Y();
		
		result3 = integrator2D.integrate(function2X, function2Y, 1, 1, 20000, 0.001);
		CSVExporter.export("", "task1_3_function2_explicit_euler_initial1_1", result3);
		
		
		//Task 1_4
		
		Function2D function1XHeun = new TestFunction2D1X();
		Function2D function1YHeun = new TestFunction2D1Y();
		Integrator2D integrator2DHeun = new HeunIntegrator2D();
		
		PointList3D result4 = integrator2DHeun.integrate(function1XHeun, function1YHeun, 1, 1, 20000, 0.001);
		CSVExporter.export("", "task1_4_function1_heun_initial1_1", result4);
		
		Function2D function2XHeun = new TestFunction2D2X();
		Function2D function2YHeun = new TestFunction2D2Y();
		
		result4 = integrator2DHeun.integrate(function2XHeun, function2YHeun, 1, 1, 20000, 0.001);
		CSVExporter.export("", "task1_4_function2_heun_initial1_1", result4);
		
		//Task 1_5

		Integrator task1_5_1 = new ExplicitEuler();
		Integrator task1_5_2 = new DahlquistImplicitEuler();
		Integrator task1_5_3 = new HeunIntegrator();
		Integrator task1_5_4 = new CheatingIntegrator();
		
		AStabilityTest astab = new AStabilityTest();
		System.out.println("Expliziter Euler:" + astab.runTest(task1_5_1));
		System.out.println("Impliziter Euler:" + astab.runTest(task1_5_2));
		System.out.println("Heun:" + astab.runTest(task1_5_3));
		System.out.println("Cheating:" + astab.runTest(task1_5_4));
	}
}
