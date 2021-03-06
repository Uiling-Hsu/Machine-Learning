package ErrorCalculator;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

import DataBean.XYMatrix;

public class ErrorCalculator {
	
	public double calculateError(DenseMatrix64F w, XYMatrix data){
		DenseMatrix64F result = new DenseMatrix64F(data.getA_X().size(),1);
		CommonOps.mult(data.getX() , w , result);
		double error = 0;
		
		for(int i=0;i<data.getA_X().size();i++){
			if(data.getY().get(i, 0)*result.get(i, 0)<0){
				error+=1;
			}						
		}
		
		
		return error/data.getA_X().size();
		
	}
	
}
