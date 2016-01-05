package hw7;
import java.util.ArrayList;

import dataBean.Data;
import dataBean.Function;

public class DecisionTree {

	public void train(int nodeIndex, ArrayList<Data> trainData, Function[] tree, int restriction) {
		Function nodeFunction;
		ArrayList<Data> leftTrainSet = new ArrayList<Data>();// Positive
		ArrayList<Data> rightTrainSet = new ArrayList<Data>();// Negative

//		System.out.println("NodeIndex: "+nodeIndex);
//		System.out.println("TrainSize: "+trainData.size());
		
		
		if (zeroImpurity(trainData,nodeIndex,tree) || allSameX(trainData,nodeIndex,tree)) {//Leaves
			return;
		} else {//Branch
			if(restriction!=0){
				if(Main.log(nodeIndex, 2)==restriction){//Restriction
					tree[nodeIndex]=majority(trainData);
					return;
				}
			}
			
			
			sortInputbyFeature(0, trainData);
			ArrayList<Double> thresholds_x0 = calThresholdbyFeature(0,
					trainData);
//			 Test
//			 System.out.println("thresholds_x0:");
//			 for(int i=0;i<thresholds_x0.size();i++){
//			
//			 System.out.println(thresholds_x0.get(i));
//			 }

			Function x0_target = findMinErrbyFeature(0, trainData,
					thresholds_x0);

//			System.out.println("x0_target.impurity:" + x0_target.getErrRate());

			sortInputbyFeature(1, trainData);
			ArrayList<Double> thresholds_x1 = calThresholdbyFeature(1,
					trainData);
//			 Test
//			 System.out.println("thresholds_x1:");
//			 for(int i=0;i<thresholds_x1.size();i++){
//			
//			 System.out.println(thresholds_x1.get(i));
//			 }
			Function x1_target = findMinErrbyFeature(1, trainData,
					thresholds_x1);

//			System.out.println("x1_target.impurity:" + x1_target.getErrRate());

			if (x1_target.getErrRate() < x0_target.getErrRate()) {
				nodeFunction = x1_target;
			} else {
				nodeFunction = x0_target;
			}

//			System.out.println("nodeFunction:" + "s: "+nodeFunction.getS()+" Threshold: "+nodeFunction.getThreshold()+" impurity:"+nodeFunction.getErrRate());
					

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
			train(nodeIndex * 2, leftTrainSet, tree, restriction);
			// //right
			train(nodeIndex * 2 + 1, rightTrainSet, tree, restriction);
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
				rpp_0++;
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
//		 System.out.println("index: "+0+" rpp_0: "+rpp_0+" rpp_1: "+rpp_1);
//		 System.out.println("index: "+0+" lpp_0: "+lpp_0+" lpp_1: "+lpp_1);

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
				rpp_0 = rightPositivePurity.get(i - 1)[0] - 1;
				rpp_1 = rightPositivePurity.get(i - 1)[1];
				lpp_0 = leftPositivePurity.get(i - 1)[0] + 1;
				lpp_1 = leftPositivePurity.get(i - 1)[1];
			}
//			 System.out.println("index: "+i+" rpp_0: "+rpp_0+" rpp_1: "+rpp_1);
//			 System.out.println("index: "+i+" lpp_0: "+lpp_0+" lpp_1: "+lpp_1);
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
		double s = 1;


		double rpp_min_impurity = 1
				- Math.pow(rightPositivePurity.get(0)[0]/ (double) trainData.size(), 2)
				- Math.pow(rightPositivePurity.get(0)[1]/ (double) trainData.size(), 2);

		double lpp_min_impurity = 0;
		
		impurity = 0*lpp_min_impurity+trainData.size()*rpp_min_impurity;
		
		for(int i=1;i<thresholds.size();i++){
			if((trainData.size()-i)!=0){
				rpp_min_impurity = 1
					- Math.pow(rightPositivePurity.get(i)[0]/ (double) (trainData.size()-i), 2)
					- Math.pow(rightPositivePurity.get(i)[1]/ (double) (trainData.size()-i), 2);
//				System.out.println("rpp_min_impurity:"+rpp_min_impurity);
			}else{
				rpp_min_impurity = 0;
			}
			
			lpp_min_impurity = 1
					- Math.pow(leftPositivePurity.get(i)[0] / i, 2)
					- Math.pow(leftPositivePurity.get(i)[1] / i, 2);
//			System.out.println("lpp_min_impurity:"+lpp_min_impurity);
			
			double tempImpurity = i*lpp_min_impurity+(trainData.size()-i)*rpp_min_impurity;
//			System.out.println("TempImpurity:"+tempImpurity);
			
			if(tempImpurity<impurity){
				impurity = tempImpurity;
				min_index = i;
			}
		}
		

		Function f = new Function();
		f.setS(s);
		f.setErrRate(impurity);
		f.setThreshold(thresholds.get(min_index));
		f.setMinArgIndex(min_index);
		f.setFeature_index(f_index);
//		System.out.println("f_index: " + f_index + " s:" + f.getS()
//				+ " Threshold: " + f.getThreshold() + " Impurity:" + impurity);
		return f;

	}

	private boolean zeroImpurity(ArrayList<Data> trainData, int nodeIndex, Function[] tree) {
		if(trainData.size()==0){
			System.out.println("trainData.size()==0");
			return true;
		}
		Data temp = trainData.get(0);
		for (Data d : trainData) {
			if (d.getY() != temp.getY()) {
				return false;
			}
		}
		
		Function f = new Function();
		f.setLeaves(true);
		f.setValue(temp.getY());
		tree[nodeIndex] = f;
		System.out.println("zeroImpurity, nodeSize="+trainData.size());
		return true;
	}

	private boolean allSameX(ArrayList<Data> trainData, int nodeIndex, Function[] tree) {
		if(trainData.size()==0){
			System.out.println("trainData.size()==0");
			return true;
		}
		int positive =0;
		int negative =0;
		Data temp = trainData.get(0);
		for (Data d : trainData) {
			if(d.getY()>0){
				positive++;
			}else{
				negative++;
			}
			
			for (int i = 0; i < d.getX().size(); i++) {
				if (!d.getX().get(i).equals(temp.getX().get(i))) {
					return false;
				}
			}
			
		}
		if(positive>negative){
			Function f = new Function();
			f.setLeaves(true);
			f.setValue(1);
			tree[nodeIndex] = f;
		}else{
			Function f = new Function();
			f.setLeaves(true);
			f.setValue(-1);
			tree[nodeIndex] = f;
		}
		
		System.out.println("allSameX, nodeSize="+trainData.size());
		return true;
	}
	
	
	private Function majority(ArrayList<Data> trainData){
		int positive =0;
		int negative =0;
		for (Data d : trainData) {
			if(d.getY()>0){
				positive++;
			}else{
				negative++;
			}
		}
		if(positive>negative){
			Function f = new Function();
			f.setLeaves(true);
			f.setValue(1);
			return f;
		}else{
			Function f = new Function();
			f.setLeaves(true);
			f.setValue(-1);
			return f;
		}
		
	}

}
