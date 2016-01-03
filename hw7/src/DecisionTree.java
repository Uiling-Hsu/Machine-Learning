import java.util.ArrayList;

import dataBean.Data;
import dataBean.Function;

public class DecisionTree {

	public void train(int nodeIndex, ArrayList<Data> trainData, Function[] tree) {
		Function nodeFunction;
		ArrayList<Data> leftTrainSet = new ArrayList<Data>();// Positive
		ArrayList<Data> rightTrainSet = new ArrayList<Data>();// Negative

		if (zeroImpurity(trainData) || allSameX(trainData)) {
			return;
		} else {
			sortInputbyFeature(0, trainData);
			ArrayList<Double> thresholds_x0 = calThresholdbyFeature(0,
					trainData);
			// Test
			// System.out.println("thresholds_x0:");
			// for(int i=0;i<thresholds_x0.size();i++){
			//
			// System.out.println(thresholds_x0.get(i));
			// }

			Function x0_target = findMinErrbyFeature(0, trainData,
					thresholds_x0);

			System.out.println("x0_target.impurity:" + x0_target.getErrRate());

			sortInputbyFeature(1, trainData);
			ArrayList<Double> thresholds_x1 = calThresholdbyFeature(1,
					trainData);
			// Test
			// System.out.println("thresholds_x1:");
			// for(int i=0;i<thresholds_x1.size();i++){
			//
			// System.out.println(thresholds_x1.get(i));
			// }
			Function x1_target = findMinErrbyFeature(1, trainData,
					thresholds_x1);

			System.out.println("x1_target.impurity:" + x1_target.getErrRate());

			if (x1_target.getErrRate() < x0_target.getErrRate()) {
				nodeFunction = x1_target;
			} else {
				nodeFunction = x0_target;
			}

			if (nodeFunction.getMinArgIndex() == 0
					|| nodeFunction.getMinArgIndex() == trainData.size()) {// Thresholds.size-1==trainData.size
				
				tree[nodeIndex]= null;
				return;
			}

			tree[nodeIndex] = nodeFunction;

			for (Data d : trainData) {
				if (seperateTrainingData(nodeFunction, d) > 0) {
					leftTrainSet.add(d);
				} else {
					rightTrainSet.add(d);
				}
			}

			// Test
			// for(int i =0;i<leftTrainSet.size();i++){
			// System.out.println(""+leftTrainSet.get(i).getX().get(0)+" "+leftTrainSet.get(i).getX().get(1)+" "+leftTrainSet.get(i).getY());
			// }
			//
			// for(int i =0;i<rightTrainSet.size();i++){
			// System.out.println(""+rightTrainSet.get(i).getX().get(0)+" "+rightTrainSet.get(i).getX().get(1)+" "+rightTrainSet.get(i).getY());
			// }
			//
			// System.out.println(leftTrainSet.size()+" "+rightTrainSet.size());

			// //left
			train(nodeIndex * 2, leftTrainSet, tree);
			// //right
			train(nodeIndex * 2 + 1, rightTrainSet, tree);
		}
	}

	private double seperateTrainingData(Function g, Data d) {
		double sign = Math.signum(d.getX().get(g.getFeature_index())
				- g.getThreshold());
		return g.getS() * sign;
	}

	public void sortInputbyFeature(int f_index, ArrayList<Data> trainData) {

		int length = trainData.size();
		while (length > 0) {
			int max_index = 0;
			Data max = trainData.get(0);
			for (int i = 0; i < length; i++) {
				if (trainData.get(i).getX().get(f_index) > max.getX().get(
						f_index)) {
					max = trainData.get(i);
					max_index = i;
				}
			}
			length--;
			Data temp = trainData.get(length);
			trainData.set(length, max);
			trainData.set(max_index, temp);
		}
	}

	public ArrayList<Double> calThresholdbyFeature(int f_index,
			ArrayList<Data> trainData) {
		ArrayList<Double> thresholds = new ArrayList<Double>();

		thresholds.add(trainData.get(0).getX().get(f_index) - 1);
		for (int i = 0; i < trainData.size(); i++) {
			if ((i + 1) != trainData.size()) {
				thresholds
						.add((trainData.get(i).getX().get(f_index) + trainData
								.get(i + 1).getX().get(f_index)) / 2);
			}

		}
		thresholds
				.add(trainData.get(trainData.size() - 1).getX().get(f_index) + 1);

		return thresholds;
	}

	public Function findMinErrbyFeature(int f_index, ArrayList<Data> trainData,
			ArrayList<Double> thresholds) {
		ArrayList<double[]> rightPositivePurity = new ArrayList<double[]>();
		ArrayList<double[]> leftPositivePurity = new ArrayList<double[]>();
		Function best = new Function();
		double rpp_0 = 0;// represent -1 number
		double rpp_1 = 0;// represent +1 number
		double lpp_0 = 0;
		double lpp_1 = 0;
		for (int i = 0; i < trainData.size(); i++) {
			// System.out.println(trainData.get(i).getY());
			if (trainData.get(i).getY() > 0) {
				rpp_1++;
			} else {
				lpp_0++;
			}
		}

		// System.out.println("rpp_1:"+rpp_1);
		// System.out.println("lpp_0:"+lpp_0);
		double[] rppa = new double[2];
		rppa[0] = rpp_0;
		rppa[1] = rpp_1;
		rightPositivePurity.add(rppa);
		double[] lppa = new double[2];
		lppa[0] = lpp_0;
		lppa[1] = lpp_1;
		leftPositivePurity.add(lppa);
		// System.out.println("index: "+0+" rpp_0: "+rpp_0+" rpp_1: "+rpp_1);
		// System.out.println("index: "+0+" lpp_0: "+lpp_0+" lpp_1: "+lpp_1);

		for (int i = 1; i < thresholds.size(); i++) {
			rpp_0 = 0;
			lpp_0 = 0;
			rpp_1 = 0;
			lpp_1 = 0;
			// System.out.println("trainData.get(i-1).getY(): "+trainData.get(i-1).getY());
			if (trainData.get(i - 1).getY() > 0) {
				rpp_1 = rightPositivePurity.get(i - 1)[1] - 1;
				rpp_0 = rightPositivePurity.get(i - 1)[0];
				lpp_1 = leftPositivePurity.get(i - 1)[1] + 1;
				lpp_0 = leftPositivePurity.get(i - 1)[0];
			} else {
				rpp_0 = rightPositivePurity.get(i - 1)[0] + 1;
				rpp_1 = rightPositivePurity.get(i - 1)[1];
				lpp_0 = leftPositivePurity.get(i - 1)[0] - 1;
				lpp_1 = leftPositivePurity.get(i - 1)[1];
			}
			// System.out.println("index: "+i+" rpp_0: "+rpp_0+" rpp_1: "+rpp_1);
			// System.out.println("index: "+i+" lpp_0: "+lpp_0+" lpp_1: "+lpp_1);
			rppa = new double[2];
			rppa[0] = rpp_0;
			rppa[1] = rpp_1;
			rightPositivePurity.add(rppa);
			lppa = new double[2];
			lppa[0] = lpp_0;
			lppa[1] = lpp_1;
			leftPositivePurity.add(lppa);
		}
		for (int i = 0; i < rightPositivePurity.size(); i++) {
			if (rightPositivePurity.get(i)[0] < 0
					|| rightPositivePurity.get(i)[1] < 0) {
				System.out.println("rpp " + f_index + ": "
						+ rightPositivePurity.get(i)[0] + " "
						+ rightPositivePurity.get(i)[1]);
			}
		}
		for (int i = 0; i < leftPositivePurity.size(); i++) {
			if (leftPositivePurity.get(i)[0] < 0
					|| leftPositivePurity.get(i)[1] < 0) {
				System.out.println("lpp :" + f_index + ": "
						+ leftPositivePurity.get(i)[0] + " "
						+ leftPositivePurity.get(i)[1]);
			}
		}

		int min_index = 0;
		double impurity = 0;
		double s = 0;

		int rpp_min_index = 0;
		double rpp_min_impurity = 1
				- Math.pow(
						rightPositivePurity.get(0)[0]
								/ (double) trainData.size(), 2)
				- Math.pow(
						rightPositivePurity.get(0)[1]
								/ (double) trainData.size(), 2);
		int lpp_min_index = 0;
		double lpp_min_impurity = 1
				- Math.pow(leftPositivePurity.get(0)[0] / trainData.size(), 2)
				- Math.pow(leftPositivePurity.get(0)[1] / trainData.size(), 2);

		for (int i = 0; i < rightPositivePurity.size(); i++) {
			double rpp_error = 1
					- Math.pow(
							rightPositivePurity.get(i)[0] / trainData.size(), 2)
					- Math.pow(
							rightPositivePurity.get(i)[1] / trainData.size(), 2);
			if (rpp_error < rpp_min_impurity) {
				rpp_min_impurity = rpp_error;
				rpp_min_index = i;
			}
		}

		for (int i = 0; i < leftPositivePurity.size(); i++) {
			double lpp_error = 1
					- Math.pow(leftPositivePurity.get(i)[0] / trainData.size(),
							2)
					- Math.pow(leftPositivePurity.get(i)[1] / trainData.size(),
							2);
			if (lpp_error < lpp_min_impurity) {
				lpp_min_impurity = lpp_error;
				lpp_min_index = i;
			}
		}

		if (lpp_min_impurity < rpp_min_impurity) {
			s = -1;
			impurity = lpp_min_impurity;
			min_index = lpp_min_index;
		} else {
			s = 1;
			impurity = rpp_min_impurity;
			min_index = rpp_min_index;
		}

		Function f = new Function();
		f.setS(s);
		f.setErrRate(impurity);
		f.setThreshold(thresholds.get(min_index));
		f.setMinArgIndex(min_index);
		f.setFeature_index(f_index);
		System.out.println("f_index: " + f_index + " s:" + f.getS()
				+ " Threshold: " + f.getThreshold() + " Impurity:" + impurity);
		return f;

	}

	private boolean zeroImpurity(ArrayList<Data> trainData) {

		Data temp = trainData.get(0);
		for (Data d : trainData) {
			if (d.getY() != temp.getY()) {
				return false;
			}
		}

		return true;
	}

	private boolean allSameX(ArrayList<Data> trainData) {
		Data temp = trainData.get(0);
		for (Data d : trainData) {
			for (int i = 0; i < d.getX().size(); i++) {
				if (!d.getX().get(i).equals(temp.getX().get(i))) {
					return false;
				}
			}
		}
		return true;
	}

}