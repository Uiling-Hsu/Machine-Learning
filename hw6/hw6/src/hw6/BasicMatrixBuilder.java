package hw6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;



public class BasicMatrixBuilder implements MLMatrixBuilder{
	private XYMatrix data=null;
	
	
	
	public void readFile(String path){
		data = new XYMatrix();
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
	           
	           data.getA_X().add(item);
	           data.getA_Y().add(Double.parseDouble(sep[2]));
	           
	        }   
	        br.close();
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	}
	
	private void buildMatrix(){
		double[][] _x= new double[data.getA_X().size()][];
		data.setY( new DenseMatrix64F(data.getA_Y().size(),1));
		
		for(int i=0;i<data.getA_X().size();i++){
			ArrayList<Double> row = data.getA_X().get(i);
			double[] copy = new double[row.size()];
		    for (int j = 0; j < row.size(); j++) {
		        // Manually loop and set individually
		        copy[j] = row.get(j);
		    }
		    _x[i] = copy;
		    data.getY().set(i, 0,data.getA_Y().get(i) );
		}
		
		data.setX(new DenseMatrix64F(_x));
		
	}
	
	public void  build(String filePath){
		readFile(filePath);
		buildMatrix();
	}
	
	public XYMatrix getData(){
		return data;
	}
	

	
}
