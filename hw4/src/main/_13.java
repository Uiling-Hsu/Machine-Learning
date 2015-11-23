package main;

import org.ejml.data.DenseMatrix64F;

import DataBean.Util;
import DataBean.XYMatrix;
import ErrorCalculator.ErrorCalculator;
import FileReader.BasicMatrixBuilder;
import ModelBuilder.ModelCalculator;

public class _13 {
	public static void main(String args[]){
		BasicMatrixBuilder basicMatrixbuilder = new BasicMatrixBuilder();
		ModelCalculator modelCalculator = new ModelCalculator();
		ErrorCalculator errorCalculator = new ErrorCalculator();
		
		
		basicMatrixbuilder.build(Util.train);
		XYMatrix train = basicMatrixbuilder.getData();
		basicMatrixbuilder.build(Util.test);
		XYMatrix test = basicMatrixbuilder.getData();
		
		DenseMatrix64F w = modelCalculator.calculate(11.26, train);
		
		double Ein = errorCalculator.calculateError(w, train);
		double Eout = errorCalculator.calculateError(w, test);
		
		System.out.println("Ein:"+Ein);
		System.out.println("Eout:"+Eout);
	}
}
