package main;

import java.io.File;

import org.ejml.data.DenseMatrix64F;

import DataBean.Util;
import DataBean.XYMatrix;
import ErrorCalculator.ErrorCalculator;
import FileReader.BasicMatrixBuilder;
import ModelBuilder.ModelCalculator;

public class _15 {
	static double[] Eout_a = new double[13];
	
	
	
	public static void main(String args[]){
		BasicMatrixBuilder basicMatrixbuilder = new BasicMatrixBuilder();
		ModelCalculator modelCalculator = new ModelCalculator();
		ErrorCalculator errorCalculator = new ErrorCalculator();
		
		
		basicMatrixbuilder.build(Util.train);
		XYMatrix train = basicMatrixbuilder.getData();
		basicMatrixbuilder.build(Util.test);
		XYMatrix test = basicMatrixbuilder.getData();
		
		
		for(int i=0;i<Util.lambdas.length;i++){
			DenseMatrix64F w = modelCalculator.calculate(Util.lambdas[i], train);
			double Eout = errorCalculator.calculateError(w, test);
			Eout_a[i] = Eout;
			System.out.println(Eout);
			
		}
		int minIndex = Util.getMinIndex(Eout_a);
		System.out.println("Min Index:"+minIndex);
		System.out.println("Lambda with min Eout:"+Util.lambdas[minIndex]);
		
		DenseMatrix64F opw = modelCalculator.calculate(Util.lambdas[minIndex], train);
		double Eout = errorCalculator.calculateError(opw, test);
		System.out.println("Eout with Lambda"+Util.lambdas[minIndex]+": "+Eout);
	}
	
	
}
