package main;

import java.io.File;

import org.ejml.data.DenseMatrix64F;

import DataBean.Util;
import DataBean.XYMatrix;
import ErrorCalculator.ErrorCalculator;
import FileReader.BasicMatrixBuilder;
import FileReader.ThreeTwoSplitMatrixBuilder;
import ModelBuilder.ModelCalculator;

public class _16 {
	static double[] Etrain_a = new double[13];
	
	
	
	public static void main(String args[]){
		ThreeTwoSplitMatrixBuilder threeTwoSplitMatrixBuilder= new ThreeTwoSplitMatrixBuilder();
		BasicMatrixBuilder basicMatrixbuilder = new BasicMatrixBuilder();
		ModelCalculator modelCalculator = new ModelCalculator();
		ErrorCalculator errorCalculator = new ErrorCalculator();
		
		
		threeTwoSplitMatrixBuilder.build(Util.train);
		XYMatrix train = threeTwoSplitMatrixBuilder.getTrain();
		XYMatrix val = threeTwoSplitMatrixBuilder.getVal();
		basicMatrixbuilder.build(Util.test);
		XYMatrix test = basicMatrixbuilder.getData();
		
		
		for(int i=0;i<Util.lambdas.length;i++){
			DenseMatrix64F w = modelCalculator.calculate(Util.lambdas[i], train);
			double Etrain = errorCalculator.calculateError(w, train);
			Etrain_a[i] = Etrain;
			System.out.println(Etrain);
			
		}
		int minIndex = Util.getMinIndex(Etrain_a);
		System.out.println("Min Index:"+minIndex);
		System.out.println("Lambda with min Etrain:"+Util.lambdas[minIndex]);
		
		DenseMatrix64F opw = modelCalculator.calculate(Util.lambdas[minIndex], train);
		double Eout = errorCalculator.calculateError(opw, test);
		System.out.println("Eout with Lambda"+Util.lambdas[minIndex]+": "+Eout);
	}
	
	
}
