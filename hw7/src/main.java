import java.io.File;
import java.util.ArrayList;

import dataBean.Data;
import dataBean.Function;


public class main {
	
	public static String train_inputPath = "resource" + File.separator + "hw7_train.txt";
	public static String test_inputPath = "resource" + File.separator + "hw7_test.txt";
	
	
	public static void main(String[] args){
		ArrayList<Data> trainData ;
		DataReader dataReader = new DataReader();
		DecisionTree decisionTree = new DecisionTree();
		trainData = dataReader.read(train_inputPath);
//		for(int i =0;i<trainData.size();i++){
//		System.out.println(""+trainData.get(i).getX().get(0)+" "+trainData.get(i).getX().get(1)+" "+trainData.get(i).getY());
//	}
		
		
		Function[] tree = new Function[trainData.size()];
		
		for(int i=0;i<trainData.size();i++){
			tree[i]=null;
		}
		
		
		decisionTree.train(1, trainData, tree);
		
		int layer=0;
		for(int i=1;i<tree.length;i++){
			if(layer != log(i,2)){
				System.out.println();
				if(tree[i]!=null){
					System.out.print(" g"+"("+i+")");
				}
				
				layer = log(i,2);
			}else{
				if(tree[i]!=null){
					System.out.print(" g"+"("+i+")");
				}
			}
		}
		
	}
	

	static int log(int x, int base)
	{
	    return (int) (Math.log(x) / Math.log(base));
	}
                      
}
