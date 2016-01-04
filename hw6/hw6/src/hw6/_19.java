package hw6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.LinearSolver;
import org.ejml.factory.LinearSolverFactory;


public class _19 {
	
	public static String train_inputPath = "resource" + File.separator + "lssvm_all";
	private static XYMatrix train = new XYMatrix();
	private static XYMatrix test = new XYMatrix();
	private static double[] r = {32, 2, 0.125};
	private static double[] lambda = {0.001,1,1000};
	private static XYMatrix[] G_K = new XYMatrix[3];

	public static void read(String input_file_name) {
		String thisLine = null;
		int t=0;
		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(input_file_name));
		
			
			
			while ((thisLine = br.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
				Data data = new Data();
				//System.out.println(st.countTokens());
//				o_line = st.nextToken() + " ";
				int m = st.countTokens();
//				System.out.println(st.countTokens());
				for (int i = 0; i < m-1; i++) {
					data.getX().add(i, Double.valueOf(st.nextToken()));
				}
				data.setY(Double.valueOf(st.nextToken()));	
				
				
				if(t<300){
					train.getA_X().add(data.getX());
					train.getA_Y().add(data.getY());
				}else{
					test.getA_X().add(data.getX());
					test.getA_Y().add(data.getY());
				}
				t++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static XYMatrix calculateGaussianKernal(double r){
		 XYMatrix result = new XYMatrix();
		for(int i=0;i<train.getA_X().size();i++){
			ArrayList<Double> X = new ArrayList<Double>();
			for(int j=0;j<train.getA_X().size();j++){
				ArrayList<Double> Xm = train.getA_X().get(i);
				ArrayList<Double> Xn = train.getA_X().get(j);
				double sum=0;
				for(int k=0;k<Xm.size();k++){
					sum += Xm.get(k)*Xn.get(k);
				}
				X.add(Math.exp((double)-1*r*sum));
			}
			result.getA_X().add(X);
		}
		return result;
	}
	
	
//	public static ArrayList<Double> calculateBate(XYMatrix K, double lambda){
//		BasicMatrixBuilder basicMatrixbuilder = new BasicMatrixBuilder();
//		
//		LinearSolver<DenseMatrix64F> pinv = LinearSolverFactory.pseudoInverse(true);
//		
//		pinv.setA(lambda_IPlusxTx);
//		DenseMatrix64F invert =  new DenseMatrix64F(3,3);
//		pinv.invert(invert);
//	}
	
	public static void main(String[] args){
		read(train_inputPath);
		System.out.println("train size:"+train.getA_X().size());
//		for(int i=0;i<train.getA_X().size();i++){
//			
//			System.out.println(train.getA_X().get(i).toString());
//		}
		System.out.println("test size:"+test.getA_X().size());
//		for(int i=0;i<test.getA_X().size();i++){
//			
//			System.out.println(test.getA_X().get(i).toString());
//		}
		
		for(int i=0; i<r.length; i++){
			G_K[i]=calculateGaussianKernal(r[i]);
		}
		
		System.out.println(G_K[0].getA_X().size());
		System.out.println(G_K[0].getA_X().get(0).size());
		
		
		
//		for(int i=0;i<G_K.length;i++){
//			for(int j=0;j<G_K[i].getA_X().size();j++){
//				System.out.println(G_K[i].getA_X().get(j).toString());
//			}
//		}
		
//		System.out.println("--------------------------------");
		
	}

}