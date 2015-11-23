package main;

import java.io.File;

import org.ejml.data.DenseMatrix64F;

import DataBean.Util;
import DataBean.XYMatrix;
import ErrorCalculator.ErrorCalculator;
import FileReader.BasicMatrixBuilder;
import FileReader.ThreeTwoSplitMatrixBuilder;
import ModelBuilder.ModelCalculator;

public class _18 {
	static double[] Eval_a = new double[13];
	
	
	
	public static void main(String args[]){
		ThreeTwoSplitMatrixBuilder threeTwoSplitMatrixBuilder= new ThreeTwoSplitMatrixBuilder();
		BasicMatrixBuilder basicMatrixbuilder = new BasicMatrixBuilder();
		ModelCalculator modelCalculator = new ModelCalculator();
		ErrorCalculator errorCalculator = new ErrorCalculator();
		
		
		threeTwoSplitMatrixBuilder.build(Util.train);
		XYMatrix train = threeTwoSplitMatrixBuilder.getTrain();
		XYMatrix val = threeTwoSplitMatrixBuilder.getVal();
		basicMatrixbuilder.build(Util.train);
		XYMatrix train_all = basicMatrixbuilder.getData();
		basicMatrixbuilder.build(Util.test);
		XYMatrix test = basicMatrixbuilder.getData();
		
		
		for(int i=0;i<Util.lambdas.length;i++){
			DenseMatrix64F w = modelCalculator.calculate(Util.lambdas[i], train);
			double Eval = errorCalculator.calculateError(w, val);
			Eval_a[i] = Eval;
			System.out.println("Eval:"+Eval);
			
		}
		int minIndex = Util.getMinIndex(Eval_a);
		System.out.println("Min Index:"+minIndex);
		System.out.println("Lambda with min Eval:"+Util.lambdas[minIndex]);
		
		
		
		
		DenseMatrix64F w = modelCalculator.calculate(Util.lambdas[minIndex], train_all);
		double Ein = errorCalculator.calculateError(w, train_all);
		System.out.println("Ein:"+Ein);
		double Eout = errorCalculator.calculateError(w, test);
		System.out.println("Eout:"+Eout);
		
	}
	
	
}
