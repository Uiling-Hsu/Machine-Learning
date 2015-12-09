import java.io.IOException;

import libsvm.svm;
import libsvm.svm_node;

public class _19 {
	public static double E[] = { 0, 0, 0, 0, 0 };
	private static double G[]={Math.pow(10, 0),Math.pow(10, 1),Math.pow(10, 2),Math.pow(10, 3),Math.pow(10, 4)};
	
	public static void main(String argv[]) throws IOException
	{
		for(int g = 0; g<G.length; g++){
		
			String a[] = {"-t", "2","-g ",""+G[g],"-c ",""+0.1, "data/15feature.train"};
			svm_train t = new svm_train();
			t.run(a);
			String p[] = {"data/15feature.test","15feature.train.model","19predict"};
			svm_predict.main(p);
			E[g] = 1-svm_predict.accuracy;
			System.out.println("e:"+(1-svm_predict.accuracy));
		}
		for(double e: E){
			System.out.print(e+" ");
		}
	
	}
}
