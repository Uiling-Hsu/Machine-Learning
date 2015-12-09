import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class _15 {
	
	public static double absolute_w[] = { 0, 0, 0, 0, 0 };
	private static double C[] = { Math.pow(10, -6), Math.pow(10, -4), Math.pow(10, -2), Math.pow(10, 0), Math.pow(10, 2) };

	public static void main(String argv[]) throws IOException
	{
		double dd=0;
		
		for(int c = 0; c<C.length; c++){
			double w[] = { 0, 0 };
			String a[] = {"-t", "0", "-c ",""+C[c], "data/15feature.train"};
			svm_train t = new svm_train();
			t.run(a);
			for(int i =0;i<t.model.sv_indices.length;i++){
//				System.out.println(t.model.sv_indices[i]);
				w[0]+=(t.model.sv_coef[0][i])*(t.model.SV[i][0].value);
				w[1]+=(t.model.sv_coef[0][i])*(t.model.SV[i][1].value);
				dd+=t.model.sv_coef[0][i];
			}
			System.out.println("sum_coeff"+dd);
			System.out.println("t.model.SV[0]:"+t.model.SV[0].length);
			System.out.println("t.model.sv_indices.length:"+t.model.sv_indices.length);
			System.out.println("t.model.sv_coef[0]:"+t.model.sv_coef[0].length);
			System.out.println("w:"+w[0]+" "+w[1]);
			System.out.println("absolute(w):"+Math.sqrt(w[0]*w[0]+w[1]*w[1]));
			absolute_w[c] = Math.sqrt(w[0]*w[0]+w[1]*w[1]);//+t.model.rho[0]*t.model.rho[0];
		}
		
		
		for(double aw: absolute_w){
			System.out.print(aw+" ");
		}

		
	}

}
