package FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

import DataBean.XYMatrix;


public class ThreeTwoSplitMatrixBuilder implements MLMatrixBuilder{
	private XYMatrix train;
	private XYMatrix val;
	
	
	public void readFile(String path){		
		train = new XYMatrix();
		val = new XYMatrix();
		String  thisLine = null;
		int seperateLine=0;
		try{
	        // open input stream test.txt for reading purpose.
	        BufferedReader br = new BufferedReader(new FileReader(path));   
	        while ((thisLine = br.readLine()) != null) {
	        	seperateLine++;
	           String sep[] = thisLine.split(" |\t");
	           ArrayList<Double> item = new ArrayList<Double>();
	           item.add((double) 1);
	           item.add(Double.parseDouble(sep[0]));
	           item.add(Double.parseDouble(sep[1]));
	           
	           if(seperateLine<=120){
	        	   train.getA_X().add(item);
	        	   train.getA_Y().add(Double.parseDouble(sep[2]));
	           }else{
	        	   val.getA_X().add(item);
	        	   val.getA_Y().add(Double.parseDouble(sep[2]));
	        	   
	           }
	        }       
	        br.close();
	     }catch(Exception e){
	        e.printStackTrace();
	     }
		
	}
	
	private void buildTrainMatrix(){
		double[][] _x= new double[train.getA_X().size()][];
		train.setY(new DenseMatrix64F(train.getA_Y().size(),1));
		
		for(int i=0;i<train.getA_X().size();i++){
			ArrayList<Double> row = train.getA_X().get(i);
			double[] copy = new double[row.size()];
		    for (int j = 0; j < row.size(); j++) {
		        // Manually loop and set individually
		        copy[j] = row.get(j);
		    }
		    _x[i] = copy;
		    train.getY().set(i, 0,train.getA_Y().get(i) );
		}
		
		train.setX(new DenseMatrix64F(_x));
		
	}
	private void buildValMatrix(){
		double[][] _x= new double[val.getA_X().size()][];
		val.setY(new DenseMatrix64F(val.getA_Y().size(),1));
		
		for(int i=0;i<val.getA_X().size();i++){
			ArrayList<Double> row = val.getA_X().get(i);
			double[] copy = new double[row.size()];
		    for (int j = 0; j < row.size(); j++) {
		        // Manually loop and set individually
		        copy[j] = row.get(j);
		    }
		    _x[i] = copy;
		    val.getY().set(i, 0,val.getA_Y().get(i) );
		}
		
		val.setX(new DenseMatrix64F(_x));
		
	}
	public void  build(String filePath){
		readFile(filePath);
		buildTrainMatrix();
		buildValMatrix();
	}
	
	public XYMatrix getTrain(){
		return train;
	}
	

	public XYMatrix getVal(){
		return val;
	}
	

}
