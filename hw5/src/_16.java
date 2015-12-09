import java.io.IOException;

public class _16 {
	public static double E[] = { 0, 0, 0, 0, 0 };
	private static double C[] = { Math.pow(10, -6), Math.pow(10, -4), Math.pow(10, -2), Math.pow(10, 0), Math.pow(10, 2) };

	public static void main(String argv[]) throws IOException
	{
		
		for(int c = 0; c<C.length; c++){
		
			String a[] = {"-t", "1", "-d","2","-c ",""+C[c], "data/16feature.train"};
			svm_train t = new svm_train();
			t.run(a);
			String p[] = {"data/16feature.train","16feature.train.model","16predict"};
			svm_predict.main(p);
			E[c] = 1-svm_predict.accuracy;
		}
		
		for(double e: E){
			System.out.print(e+" ");
		}
		
	}
}
