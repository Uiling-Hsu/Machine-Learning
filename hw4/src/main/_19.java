package main;

import org.ejml.data.DenseMatrix64F;

import DataBean.Util;
import DataBean.XYMatrix;
import ErrorCalculator.ErrorCalculator;
import FileReader.FiveFoldMatrixBuilder;
import ModelBuilder.ModelCalculator;

public class _19 {
	
	static double[] Ecv_a = new double[13];
	
	public static void main(String args[]){
		
		FiveFoldMatrixBuilder fiveFoldMatrixBuilder = new FiveFoldMatrixBuilder();
		ModelCalculator modelCalculator = new ModelCalculator();
		ErrorCalculator errorCalculator = new ErrorCalculator();
		
		fiveFoldMatrixBuilder.build(Util.train);
		
		int excludeIndex=0;
		for(int i=0;i<Util.lambdas.length;i++){
			double[] Ecv_js = new double[5];
			for(int j=0;j<5;j++){
				XYMatrix exclude_j = fiveFoldMatrixBuilder.buildMatrixExclude(j);
				DenseMatrix64F w = modelCalculator.calculate(Util.lambdas[i], exclude_j);
				double Ecv_j = errorCalculator.calculateError(w, fiveFoldMatrixBuilder.getFold(j));
				Ecv_js[j] = Ecv_j;			
			}
			double Ecv_l=0;
			for(double d:Ecv_js){
				Ecv_l+=d;
			}
			Ecv_a[i]=Ecv_l/Ecv_js.length;
			System.out.println(Ecv_a[i]);
		}
		int minIndex = Util.getMinIndex(Ecv_a);
		System.out.println("Min Index:"+minIndex);
		System.out.println("Lambda with min Eval:"+Util.lambdas[minIndex]);
		
	}
}
