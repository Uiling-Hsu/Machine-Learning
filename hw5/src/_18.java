import java.io.IOException;

import libsvm.svm;
import libsvm.svm_node;

public class _18 {
	
	public static double D[] = { 0, 0, 0, 0, 0 };
	private static double C[]={Math.pow(10, -3),Math.pow(10, -2),Math.pow(10, -1),Math.pow(10, 0),Math.pow(10, 1)};
	
	public static void main(String argv[]) throws IOException
	{
		for(int c = 0; c<C.length; c++){
		
			String a[] = {"-t", "2","-g ",""+100,"-c ",""+C[c], "data/15feature.train"};
			svm_train t = new svm_train();
			t.run(a);
	
			double sumOfAlpha =0;
			svm_node[] freeX=null;
			double[] decisionValue = new double[1];
			
			for(int i =0;i<t.model.sv_indices.length;i++){
				double alpha =  t.model.sv_coef[0][i]*t.prob.y[t.model.sv_indices[i]-1];
				sumOfAlpha+=alpha ;
				if(0<alpha&&alpha<C[c]){
					freeX=t.model.SV[i];
				}	
			}
			svm.svm_predict_values(t.model, freeX, decisionValue);
			
			double obj = svm.objValue;
			double absolut_w = Math.sqrt(2*(obj+sumOfAlpha));
			double distance = Math.abs(decisionValue[0]/absolut_w);
			
			System.out.println(distance);
			D[c] = distance;
		}
		for(double d: D){
			System.out.print(d+" ");
		}
	
	}
}
