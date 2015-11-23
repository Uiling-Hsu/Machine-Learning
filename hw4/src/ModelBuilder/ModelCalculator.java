package ModelBuilder;

import org.ejml.data.DenseMatrix64F;
import org.ejml.factory.LinearSolver;
import org.ejml.factory.LinearSolverFactory;
import org.ejml.ops.CommonOps;
import org.ejml.simple.SimpleMatrix;

import DataBean.XYMatrix;

public class ModelCalculator {
	
	public DenseMatrix64F calculate(double lambda,XYMatrix data){
		// X_Trans
		DenseMatrix64F x_trans = CommonOps.transpose(data.getX(),null);
		// Lambda_I
		DenseMatrix64F lambda_I = new DenseMatrix64F(3,3);
		lambda_I.set(0, 0, lambda);
		lambda_I.set(1, 1, lambda);
		lambda_I.set(2, 2, lambda);
		// XTX
		DenseMatrix64F xTx = new DenseMatrix64F(3,3);
		CommonOps.mult(x_trans , data.getX() , xTx);
		DenseMatrix64F lambda_IPlusxTx =  new DenseMatrix64F(3,3);
		CommonOps.add(lambda_I, xTx, lambda_IPlusxTx);
		
		LinearSolver<DenseMatrix64F> pinv = LinearSolverFactory.pseudoInverse(true);
		pinv.setA(lambda_IPlusxTx);
		DenseMatrix64F invert =  new DenseMatrix64F(3,3);
		pinv.invert(invert);
		DenseMatrix64F xTy =  new DenseMatrix64F(3,1);
		CommonOps.mult(x_trans , data.getY() , xTy);
		DenseMatrix64F w =  new DenseMatrix64F(3,1);
		CommonOps.mult(invert,xTy,w);
		return w;
		
	}
	
	
	
}
