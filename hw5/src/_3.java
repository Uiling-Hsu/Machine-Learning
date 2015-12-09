import java.io.IOException;

public class _3 {
	
	public static void main(String argv[]) throws IOException
	{
		
		
		double w[] = { 0, 0 };
			double x[] = { 0, 0 };
			String a[] = {"-c","2","-t", "1", "-g ","1","-r","1","-d","2", "data/3Test"};
			svm_train t = new svm_train();
			t.run(a);
//			for(int i =0;i<t.model.sv_indices.length;i++){
////				System.out.println(t.model.sv_indices[i]);
//				w[0]+=(t.model.sv_coef[0][i])*(t.model.SV[i][0].value);
//				w[1]+=(t.model.sv_coef[0][i])*(t.model.SV[i][1].value);
//				
//			}
//			System.out.println("w:"+w[0]+" "+w[1]);
			for(int i =0;i<t.model.sv_indices.length;i++){
				System.out.println("alpha:"+t.model.sv_indices[i]);
				x[0]=(t.model.SV[i][0].value);
				x[1]=(t.model.SV[i][1].value);
				System.out.println("x:"+x[0]+" "+x[1]);
			}
			System.out.println(t.model.SV.length);
			
		
		
		

		
	}

}
