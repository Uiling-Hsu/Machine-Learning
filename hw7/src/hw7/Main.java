package hw7;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dataBean.Data;
import dataBean.Function;


public class Main {
	
	public static String train_inputPath = "resource" + File.separator + "hw7_train.txt";//"hw3_train.dat"
	public static String test_inputPath = "resource" + File.separator + "hw7_test.txt";
	public static String _16_outputPath = "report" + File.separator + "_16.txt";//
	public static String _17_outputPath = "report" + File.separator + "_17.txt";//
	public static String _17_test_outputPath = "resource" + File.separator + "_17_test.txt";//
	public static String _18_outputPath = "report" + File.separator + "_18.txt";//
	public static String _18_test_outputPath = "resource" + File.separator + "_18_test.txt";//
	public static String _19_outputPath = "report" + File.separator + "_19.txt";//
	public static String _19_test_outputPath = "resource" + File.separator + "_19_test.txt";//
	public static String _20_outputPath = "report" + File.separator + "_20.txt";//
	public static String _20_test_outputPath = "resource" + File.separator + "_20_test.txt";//
	
	public static void main(String[] args){
		ArrayList<Function[]> forest = new ArrayList<Function[]>();
		ArrayList<Data> trainData, testData ;
		DataReader dataReader = new DataReader();
		DecisionTree decisionTree = new DecisionTree();
		trainData = dataReader.read(train_inputPath);
		testData = dataReader.read(test_inputPath);
//		for(int i =0;i<trainData.size();i++){
//		System.out.println(""+trainData.get(i).getX().get(0)+" "+trainData.get(i).getX().get(1)+" "+trainData.get(i).getY());
//	}

		
		

		_13(trainData);
		
		
//		_14(trainData); 
//		_15(trainData,testData);
//		
//		_16(trainData);
//		_17(trainData);
//		_18(trainData,testData);
//		_19(trainData);
//		_20(trainData,testData);
	}
	
	
	private static Function[] initTree(ArrayList<Data> trainData){

		Function[] tree = new Function[5000];
		
		for(int i=0;i<5000;i++){
			tree[i]=null;
		}
		return tree;
	}
	
	public static void _20(ArrayList<Data> trainData,ArrayList<Data> testData){
		ArrayList<Function[]> forest =new ArrayList<Function[]>();
		DecisionTree decisionTree = new DecisionTree();
		Function[] tree = initTree(trainData);
		decisionTree.train(1, trainData, tree, 1);
		forest.add(tree);
		train29999gt(trainData,forest,1);
		
			int first =0;
			try {
				PrintWriter r = new PrintWriter(_20_outputPath);
//				PrintWriter r = new PrintWriter(_20_test_outputPath);
//				while(first<forest.size()){//G first<forest.size()
//					if(first%1000==0){
//						System.out.println("Frist:"+first);
//					}
//					double errRate=0;
//					for(int i=0;i<testData.size();i++){
//						double result = 0;
//						for(int j=0;j<=first;j++){//uniform blending
//							result+=calculate_g(testData.get(i),forest.get(j),1);
//						}
////						System.out.println("result:"+result);
////						System.out.println("Math.signum(result):"+Math.signum(result));
//						if(Math.signum(result)!=testData.get(i).getY()){
//							errRate++;
//						}	
//					}
//					errRate=errRate/testData.size();
//					r.println(errRate);
//					first++;	
//				}
				double[] errRates = dpCalculate(forest,testData);
				
				for(double e:errRates){
					r.println(e);
				}
				r.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void _19(ArrayList<Data> trainData){
		ArrayList<Function[]> forest =new ArrayList<Function[]>();
		DecisionTree decisionTree = new DecisionTree();
		Function[] tree = initTree(trainData);
		decisionTree.train(1, trainData, tree, 1);
		forest.add(tree);
		train29999gt(trainData,forest,1);	
			int first =0;
			try {
				PrintWriter r = new PrintWriter(_19_outputPath);
//				PrintWriter r = new PrintWriter(_19_test_outputPath);
//				while(first<forest.size()){//G first<forest.size()
//					if(first%1000==0){
//						System.out.println("Frist:"+first);
//					}
//					double errRate=0;
//					for(int i=0;i<trainData.size();i++){
//						double result = 0;
//						for(int j=0;j<=first;j++){//uniform blending
//							result+=calculate_g(trainData.get(i),forest.get(j),1);
//						}
////						System.out.println("result:"+result);
////						System.out.println("Math.signum(result):"+Math.signum(result));
//						if(Math.signum(result)!=trainData.get(i).getY()){
//							errRate++;
//						}	
//					}
//					errRate=errRate/trainData.size();
//					r.println(errRate);
//					first++;	
//				}
				double[] errRates = dpCalculate(forest,trainData);
				
				for(double e:errRates){
					r.println(e);
				}
				r.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void _18( ArrayList<Data> trainData,ArrayList<Data> testData){
		ArrayList<Function[]> forest =new ArrayList<Function[]>();
		Function[] tree = _13(trainData);
		forest.add(tree);
		train29999gt(trainData,forest,0);
		
		
			int first =0;
			try {
				PrintWriter r = new PrintWriter(_18_outputPath);
//				PrintWriter r = new PrintWriter(_18_test_outputPath);
//				while(first<1000){//G first<forest.size()
//					if(first%1000==0){
//						System.out.println("Frist:"+first);
//					}
//					double errRate=0;
//					for(int i=0;i<testData.size();i++){
//						double result = 0;
//						for(int j=0;j<=first;j++){//uniform blending
//							result+=calculate_g(testData.get(i),forest.get(j),1);
//						}
//						if(Math.signum(result)!=testData.get(i).getY()){
//							errRate++;
//						}	
//					}
//					errRate=errRate/testData.size();
//					r.println(errRate);
//					first++;	
//				}
				
				double[] errRates = dpCalculate(forest,testData);
				
				for(double e:errRates){
					r.println(e);
				}
				
				r.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	
	public static void _17(ArrayList<Data> trainData){
		ArrayList<Function[]> forest =new ArrayList<Function[]>();
		Function[] tree = _13(trainData);
		forest.add(tree);
		train29999gt(trainData,forest,0);
		
			int first =0;
			try {
				PrintWriter r = new PrintWriter(_17_outputPath);
//				PrintWriter r = new PrintWriter(_17_test_outputPath);
//				while(first<100){//G first<forest.size()
//					if(first%1000==0){
//						System.out.println("Frist:"+first);
//					}
//					double errRate=0;
//					for(int i=0;i<trainData.size();i++){
//						double result = 0;
//						for(int j=0;j<=first;j++){//uniform blending
//							result+=calculate_g(trainData.get(i),forest.get(j),1);
//						}
////						System.out.println("result:"+result);
////						System.out.println("Math.signum(result):"+Math.signum(result));
//						if(Math.signum(result)!=trainData.get(i).getY()){
//							errRate++;
//						}	
//					}
//					errRate=errRate/trainData.size();
//					r.println(errRate);
//					first++;	
//				}
				double[] errRates = dpCalculate(forest,trainData);
				
				for(double e:errRates){
					r.println(e);
				}
				
				
				r.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public static void _16(ArrayList<Data> trainData){
		ArrayList<Function[]> forest =new ArrayList<Function[]>();
		Function[] tree = _13(trainData);
		forest.add(tree);
		train29999gt(trainData,forest,0);
		System.out.println("forest size:"+forest.size());
		try {
			PrintWriter r = new PrintWriter(_16_outputPath);
			for(int j=0;j<forest.size();j++){
	//			_13(forest.get(i));
				double errRate=0;
				for(int i=0;i<trainData.size();i++){
					if(calculate_g(trainData.get(i),forest.get(j),1)!=trainData.get(i).getY()){
						errRate++;
					}			
				}
				
				errRate=errRate/trainData.size();
				r.println(errRate);
			}
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Function[]> train29999gt(ArrayList<Data> trainData,ArrayList<Function[]> forest,int restriction){
		DecisionTree decisionTree = new DecisionTree();
		Bagging b = new Bagging();
		b.init();
		for(int i=0;i<29999;i++){
			System.out.println("-----------------Iteration: "+i+"------------");
			Function[] tree = initTree(trainData);
			ArrayList<Data> bag = b.bagging();
			decisionTree.train(1, bag, tree,restriction);
			forest.add(tree);
		}
		return forest;
		
	}
	
	public static void _15(ArrayList<Data> trainData, ArrayList<Data> testData){
		Function[] tree = _13(trainData);
		double errRate=0;
		for(int i=0;i<testData.size();i++){
			if(calculate_g(testData.get(i),tree,1)!=testData.get(i).getY()){
				errRate++;
			}
//			System.out.println(calculate_g(testData.get(i),tree,1));
		}
		errRate=errRate/testData.size();
		errRateoutputPrinter("15_Output",errRate);
		System.out.println("Eout:"+errRate);
	}
	
	public static void errRateoutputPrinter(String fileName, double errRate){
		try {
			PrintWriter r = new PrintWriter(fileName);
			r.println(errRate);
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void _14(ArrayList<Data> trainData){
		Function[] tree = _13(trainData);
		double errRate=0;
		for(int i=0;i<trainData.size();i++){
			if(calculate_g(trainData.get(i),tree,1)!=trainData.get(i).getY()){
				errRate++;
			}
//			System.out.println(calculate_g(trainData.get(i),tree,1));
		}
		errRate=errRate/trainData.size();
		errRateoutputPrinter("14_Output",errRate);
		System.out.println("Ein:"+errRate);
	}
	
	public static Function[] _13(ArrayList<Data> trainData){
		DecisionTree decisionTree = new DecisionTree();
		Function[] tree = initTree(trainData);
		decisionTree.train(1, trainData, tree, 0);
		int layer=0;
		for(int i=1;i<tree.length;i++){
			if(layer != log(i,2)){
				System.out.println();
				if(tree[i]!=null){
					if(tree[i].isLeaves()){
						System.out.print(" g"+"("+i+")"+" {leaves:"+tree[i].getValue()+"}");
					}else{
						System.out.print(" g"+"("+i+")"+ "f_index: " + tree[i].getFeature_index() + " Threshold: " + tree[i].getThreshold() );
					}
				}
				
				layer = log(i,2);
			}else{
				if(tree[i]!=null){
					if(tree[i].isLeaves()){
						System.out.print(" g"+"("+i+")"+" {leaves:"+tree[i].getValue()+"}");
					}else{
						System.out.print(" g"+"("+i+")"+ "f_index: " + tree[i].getFeature_index() + " Threshold: " + tree[i].getThreshold() );
					}
				}
			}
		}
		
		return tree;
	}
	

	public static int log(int x, int base)
	{
	    return (int) (Math.log(x) / Math.log(base));
	}
	
	public static double[] dpCalculate(ArrayList<Function[]> forest, ArrayList<Data> datas){
		double[] firstErrRate = new double[forest.size()];
		double[] result = new double[datas.size()];
		
		for(int i =0;i<datas.size();i++){
			result[i]=0;
		}
		for(int i =0;i<firstErrRate.length;i++){
			firstErrRate[i]=0;
		}
		
		
		for(int t=0;t<forest.size();t++){
			for(int i =0;i<datas.size();i++){
				result[i]+=calculate_g(datas.get(i),forest.get(t),1);
				if(Math.signum(result[i])!=datas.get(i).getY()){
					firstErrRate[t]++;
				}
			}
			firstErrRate[t]=firstErrRate[t]/datas.size();
		}
		
		return firstErrRate;
	}
	
	public static double calculate_g(Data d,Function[] tree, int index){
//		System.out.println("index:"+index);
		Function g = tree[index];
		double sign = Math.signum(d.getX().get(g.getFeature_index())- g.getThreshold());
//		System.out.println(g.getS() *sign);
		
		if(sign>0){
			
			if(!tree[index].isLeaves()){
//				System.out.println("calculate_g(d,tree, 2*index);");
				return calculate_g(d,tree, 2*index);
			}else{
//				System.out.println("result = 1");
				return tree[index].getValue();
			}
		}else{
			
			if(!tree[index].isLeaves()){
//				System.out.println("calculate_g(d,tree, 2*index+1);");
				return calculate_g(d,tree, 2*index+1);
			}else{
//				System.out.println("result = -1");
				return tree[index].getValue();
			}
		}
		

	}
                      
}
