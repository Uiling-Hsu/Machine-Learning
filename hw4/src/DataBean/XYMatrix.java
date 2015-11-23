package DataBean;

import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;

public class XYMatrix {
	private DenseMatrix64F X;
	private DenseMatrix64F Y;
	
	private ArrayList<ArrayList<Double>> A_X = new ArrayList<ArrayList<Double>>();
	private ArrayList<Double> A_Y = new ArrayList<Double>();
	public DenseMatrix64F getX() {
		return X;
	}
	public void setX(DenseMatrix64F x) {
		X = x;
	}
	public DenseMatrix64F getY() {
		return Y;
	}
	public void setY(DenseMatrix64F y) {
		Y = y;
	}
	public ArrayList<ArrayList<Double>> getA_X() {
		return A_X;
	}
	public void setA_X(ArrayList<ArrayList<Double>> a_X) {
		A_X = a_X;
	}
	public ArrayList<Double> getA_Y() {
		return A_Y;
	}
	public void setA_Y(ArrayList<Double> a_Y) {
		A_Y = a_Y;
	}
}
