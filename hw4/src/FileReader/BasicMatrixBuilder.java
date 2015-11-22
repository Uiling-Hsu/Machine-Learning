package FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;


public class BasicMatrixBuilder implements MLMatrixBuilder{
	
	public static DenseMatrix64F X;
	public static SimpleMatrix Y;
	public static ArrayList<ArrayList<Double>> A_X = new ArrayList<ArrayList<Double>>();
	public static ArrayList<Double> A_Y = new ArrayList<Double>();
	
	
	public void readFile(String path){		
		String  thisLine = null;		
		try{
	        // open input stream test.txt for reading purpose.
	        BufferedReader br = new BufferedReader(new FileReader(path));   
	        while ((thisLine = br.readLine()) != null) {
	        
	           String sep[] = thisLine.split(" |\t");
	           ArrayList<Double> item = new ArrayList<Double>();
	           item.add((double) 1);
	           item.add(Double.parseDouble(sep[0]));
	           item.add(Double.parseDouble(sep[1]));
	           
	           A_X.add(item);
	           A_Y.add(Double.parseDouble(sep[2]));
	           
	        }       
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	}
	
	private void buildMatrix(){
		double[][] _x= new double[A_X.size()][];
		Y= new SimpleMatrix(A_Y.size(),1);
		
		for(int i=0;i<A_X.size();i++){
			ArrayList<Double> row = A_X.get(i);
			double[] copy = new double[row.size()];
		    for (int j = 0; j < row.size(); j++) {
		        // Manually loop and set individually
		        copy[j] = row.get(j);
		    }
		    _x[i] = copy;
		    Y.set(i, 0,A_Y.get(i) );
		}
		
		X= new DenseMatrix64F(_x);
		
	}
	
	public void  build(String filePath){
		readFile(filePath);
		buildMatrix();
	}
	
	public DenseMatrix64F getX(){
		return X;
	}
	
	public SimpleMatrix getY(){
		return Y;
	}
	
}
