package main;

import java.io.PrintWriter;

public class Test {
	public static void main(String[] args) {
		try {
			PrintWriter writer = new PrintWriter("out.csv","UTF-8");
			writer.println("t;f ;; t;y ;; t;g ;; t;z ;; t;fx;fy ;; t;cx;cy ;; t;Cx;Cy");
			for(int i=0; i<1000; i++){
				writer.println(";;;;;;;;;;;;;;;;;;;;;;;;;");
			}
			writer.flush();
		} catch(Exception e){
			System.out.println(e);
		}
	}
}
