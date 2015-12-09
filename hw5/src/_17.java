import java.io.IOException;

public class _17 {
	public static double Alphas[] = { 0, 0, 0, 0, 0 };
	private static double C[]={Math.pow(10, -6),Math.pow(10, -4),Math.pow(10, -2),Math.pow(10, 0),Math.pow(10, 2)};
	
	
	public static void main(String argv[]) throws IOException
	{
		for(int c = 0; c<C.length; c++){
		
			String a[] = {"-t", "1", "-d","2","-c ",""+C[c], "data/16feature.train"};
			svm_train t = new svm_train();
			t.run(a);
	
			double sumOfAlpha =0;
			
			for(int i =0;i<t.model.sv_indices.length;i++){
//				System.out.println(t.model.sv_indices[i]);
				sumOfAlpha+=t.model.sv_coef[0][i]*t.prob.y[t.model.sv_indices[i]-1];		
				
			}
			System.out.println("t.model.sv_indices.length:"+t.model.sv_indices.length);
			System.out.println("t.model.sv_coef[0]:"+t.model.sv_coef[0].length);
			System.out.println(sumOfAlpha);
			Alphas[c] = sumOfAlpha;
		}
		for(double a: Alphas){
			System.out.print(a+" ");
		}
	
	}
}
