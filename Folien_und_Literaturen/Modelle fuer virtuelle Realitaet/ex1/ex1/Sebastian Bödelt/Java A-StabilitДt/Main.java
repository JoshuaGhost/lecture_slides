
public class Main {

	public static void main(String[] args) {
		double deltaT = 0.001;
		
		System.out.println("Explizit Euler: " + new AStabilityTest(new Explizit_Euler(deltaT)).check());
		System.out.println("Implizit Euler: " + new AStabilityTest(new Implizit_Euler(deltaT)).check());
		System.out.println("Besteht Test: " + new AStabilityTest(new Besteht_Test(deltaT)).check());
	}

}
