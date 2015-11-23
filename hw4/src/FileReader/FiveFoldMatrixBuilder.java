package FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

import DataBean.XYMatrix;


public class FiveFoldMatrixBuilder implements MLMatrixBuilder{
	private XYMatrix[] fiveFold = new XYMatrix[5];
	
	public FiveFoldMatrixBuilder(){
		for(int i=0;i<5;i++){
			fiveFold[i]=new XYMatrix();
		}
	}
	
	
	public void readFile(String path){		
		String  thisLine = null;
		int seperateLine=0;
		int fold=0;
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
	           
	           if(seperateLine<=40){
	        	   fiveFold[fold].getA_X().add(item);
	        	   fiveFold[fold].getA_Y().add(Double.parseDouble(sep[2]));
	           }else{
	        	   seperateLine=1;
	        	   fold++;
	        	   fiveFold[fold].getA_X().add(item);
	        	   fiveFold[fold].getA_Y().add(Double.parseDouble(sep[2]));
	           }
	        }  
	        br.close();
	     }catch(Exception e){
	        e.printStackTrace();
	     }
	}
	
	private void buildMatrix(){
		
		for(XYMatrix xy:fiveFold){
			double[][] _x= new double[40][];
			xy.setY(new DenseMatrix64F(40,1));
			
			for(int i=0;i<40;i++){
				ArrayList<Double> row = xy.getA_X().get(i);
				double[] copy = new double[row.size()];
			    for (int j = 0; j < row.size(); j++) {
			        // Manually loop and set individually
			        copy[j] = row.get(j);
			    }
			    _x[i] = copy;
			    xy.getY().set(i, 0,xy.getA_Y().get(i) );
			}
			
			xy.setX(new DenseMatrix64F(_x));
		}
		
	}
	
	public XYMatrix buildMatrixExclude(int foldIndex){
		XYMatrix collection = new XYMatrix();
		for(int i=0;i<5;i++){
			if(i==foldIndex){
				continue;
			}else{				
				collection.getA_X().addAll(fiveFold[i].getA_X());
				collection.getA_Y().addAll(fiveFold[i].getA_Y());	
			}
		}
		double[][] _x= new double[collection.getA_X().size()][];
		collection.setY( new DenseMatrix64F(collection.getA_Y().size(),1));
		
		for(int i=0;i<collection.getA_X().size();i++){
			ArrayList<Double> row = collection.getA_X().get(i);
			double[] copy = new double[row.size()];
		    for (int j = 0; j < row.size(); j++) {
		        // Manually loop and set individually
		        copy[j] = row.get(j);
		    }
		    _x[i] = copy;
		    collection.getY().set(i, 0,collection.getA_Y().get(i) );
		}
		
		collection.setX(new DenseMatrix64F(_x));
		return collection;
	}
	
	public void  build(String filePath){
		readFile(filePath);
		buildMatrix();
	}
	
	
	
	public XYMatrix getFold(int index){
		return fiveFold[index];
	}
	

}
