package hw6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	public static String train_inputPath = "resource" + File.separator + "adaboost_train";
	public static String test_inputPath = "resource" + File.separator + "adaboost_test";
	private static ArrayList<Data> train = new ArrayList<Data>();
	private static ArrayList<Double> thresholds_x0 = new ArrayList<Double>();
	private static ArrayList<Double> thresholds_x1 = new ArrayList<Double>();
	private static ArrayList<Function> candidates_G = new ArrayList<Function>();
	private static ArrayList<Data> test = new ArrayList<Data>();

	
	
	
	public static void sortInputbyFeature(int index){	
		
		int length = train.size();
		while(length>0){
			int max_index = 0;
			Data max =train.get(0);
			for(int i=0;i<length;i++){	
				if(train.get(i).getX().get(index)>max.getX().get(index)){
					max = train.get(i);
					max_index = i;
				}
			}	
			length--;
			Data temp = train.get(length);
			train.set(length, max);
			train.set(max_index, temp);
		}
	}
	
	
	
	public static void calThresholdbyFeature_x0(){
		thresholds_x0.add(train.get(0).getX().get(0)-1);
		for(int i=0;i<train.size();i++){
			if((i+1)!=train.size()){
				thresholds_x0.add((train.get(i).getX().get(0)+train.get(i+1).getX().get(0))/2);
			}
			
		}
		thresholds_x0.add(train.get(train.size()-1).getX().get(0)+1);
	}
	
	public static void calThresholdbyFeature_x1(){
		thresholds_x1.add(train.get(0).getX().get(1)-1);
		for(int i=0;i<train.size();i++){
			if((i+1)!=train.size()){
				thresholds_x1.add((train.get(i).getX().get(1)+train.get(i+1).getX().get(1))/2);
			}
			
		}
		thresholds_x1.add(train.get(train.size()-1).getX().get(0)+1);
	}
	

	
	public static Function findMinErrbyFeature_x0(int iteration){
		ArrayList<Double> plus_error_x0 = new ArrayList<Double>();
		ArrayList<Double> minus_error_x0 = new ArrayList<Double>();
		Function best = new Function();
		double mu_sum = 0;
		double s_plus_error = 0;
		double s_minus_error = 0;
		for(int i=0;i<train.size();i++){
//			System.out.println(train.get(i).getMus().get(iteration));
			mu_sum+=train.get(i).getMus().get(iteration);
			if(train.get(i).getY()<0){
				s_plus_error+=train.get(i).getMus().get(iteration);
			}else{
				s_minus_error+=train.get(i).getMus().get(iteration);
			}
		}
		plus_error_x0.add(s_plus_error);
		minus_error_x0.add(s_minus_error);
//		System.out.println(plus_error_x0.get(0));
//		System.out.println(minus_error_x0.get(0));
//		
//		System.out.println(mu_sum);
		
		for(int i =1;i<thresholds_x0.size();i++){
			s_plus_error = 0;
			s_minus_error = 0;
			if(train.get(i-1).getY()>0){
				s_plus_error = plus_error_x0.get(i-1)+train.get(i-1).getMus().get(iteration);
				s_minus_error = minus_error_x0.get(i-1)-train.get(i-1).getMus().get(iteration);
			}else{
				s_plus_error = plus_error_x0.get(i-1)-train.get(i-1).getMus().get(iteration);
				s_minus_error = minus_error_x0.get(i-1)+train.get(i-1).getMus().get(iteration);
			}
//			System.out.println("s_plus_error:"+s_plus_error);
//			System.out.println("s_minus_error:"+s_minus_error);
			plus_error_x0.add(s_plus_error);
			minus_error_x0.add(s_minus_error);
		}
		for(int i=0;i<plus_error_x0.size();i++){
			if(plus_error_x0.get(i)<0){
			System.out.print("plus_error_x0:"+plus_error_x0.get(i)+" ");
			}
		}
		for(int i=0;i<minus_error_x0.size();i++){
			if(minus_error_x0.get(i)<0){
			System.out.print("minus_error_x0:"+minus_error_x0.get(i)+" ");
			}
		}
		
		
		int min_index=0;
		double min_error =0;
		double s =0;
		
		
		int positive_min_index=0;
		double positive_min_error =plus_error_x0.get(0);
		int negative_min_index=0;
		double negative_min_error =minus_error_x0.get(0);
		
		
		for(int i=0;i<plus_error_x0.size();i++){
			if(plus_error_x0.get(i)<positive_min_error){
				positive_min_error = plus_error_x0.get(i);
				positive_min_index = i;
			}		
		}
		
		
		for(int i=0;i<minus_error_x0.size();i++){
			if(minus_error_x0.get(i)<negative_min_error){
				negative_min_error = minus_error_x0.get(i);
				negative_min_index = i;
			}		
		}
		
		if(negative_min_error<positive_min_error){
			s=-1;
			min_error = negative_min_error;
			min_index = negative_min_index;
		}else{
			s=1;
			min_error = positive_min_error;
			min_index = positive_min_index;
		}
		
		
		Function f = new Function();
		f.setS(s);
		f.setErrRate((min_error/mu_sum));
		f.setThreshold(thresholds_x0.get(min_index));
		f.setFeature_index(0);
//		System.out.println(f.getS()+" " + f.getThreshold()+ " "+min_error+" "+mu_sum+" "+f.getErrRate());
		
		return f;
		
	}
	

	
	public static Function findMinErrbyFeature_x1(int iteration){
		ArrayList<Double> plus_error_x1 = new ArrayList<Double>();
		ArrayList<Double> minus_error_x1 = new ArrayList<Double>();
		Function best = new Function();
		double mu_sum = 0;
		double s_plus_error = 0;
		double s_minus_error = 0;
		for(int i=0;i<train.size();i++){
			mu_sum+=train.get(i).getMus().get(iteration);
			if(train.get(i).getY()<0){
				s_plus_error+=train.get(i).getMus().get(iteration);
			}else{
				s_minus_error+=train.get(i).getMus().get(iteration);
			}
		}
		plus_error_x1.add(s_plus_error);
		minus_error_x1.add(s_minus_error);
		
		
		for(int i =1;i<thresholds_x1.size();i++){
			s_plus_error = 0;
			s_minus_error = 0;
			if(train.get(i-1).getY()>0){
				s_plus_error = plus_error_x1.get(i-1)+train.get(i-1).getMus().get(iteration);
				s_minus_error = minus_error_x1.get(i-1)-train.get(i-1).getMus().get(iteration);
			}else{
				s_plus_error = plus_error_x1.get(i-1)-train.get(i-1).getMus().get(iteration);
				s_minus_error = minus_error_x1.get(i-1)+train.get(i-1).getMus().get(iteration);
			}
			plus_error_x1.add(s_plus_error);
			minus_error_x1.add(s_minus_error);
		}
		
		for(int i=0;i<plus_error_x1.size();i++){
			if(plus_error_x1.get(i)<0){
				System.out.print("plus_error_x1:"+plus_error_x1.get(i)+" ");
			}
		}
		for(int i=0;i<minus_error_x1.size();i++){
			if(minus_error_x1.get(i)<0){
			System.out.print("minus_error_x1:"+minus_error_x1.get(i)+" ");
			}
		}
		
		
		
		int min_index=0;
		double min_error =0;
		double s =0;
		
		
		int positive_min_index=0;
		double positive_min_error =plus_error_x1.get(0);
		int negative_min_index=0;
		double negative_min_error =minus_error_x1.get(0);
		
		
		for(int i=0;i<plus_error_x1.size();i++){
			if(plus_error_x1.get(i)<positive_min_error){
				positive_min_error = plus_error_x1.get(i);
				positive_min_index = i;
			}		
		}
		
		
		for(int i=0;i<minus_error_x1.size();i++){
			if(minus_error_x1.get(i)<negative_min_error){
				negative_min_error = minus_error_x1.get(i);
				negative_min_index = i;
			}		
		}
		
		if(negative_min_error<positive_min_error){
			s=-1;
			min_error = negative_min_error;
			min_index = negative_min_index;
		}else{
			s=1;
			min_error = positive_min_error;
			min_index = positive_min_index;
		}
		
		
		Function f = new Function();
		f.setS(s);
		f.setErrRate(min_error/mu_sum);
		f.setThreshold(thresholds_x1.get(min_index));
		f.setFeature_index(1);
		
//		System.out.println(f.getS()+" " + f.getThreshold()+ " "+min_error+" "+mu_sum+" "+f.getErrRate());
		
		
		return f;
		
	}
	
	public static double calUpdateCoefficient( double errRate){
		
		return Math.sqrt(((1-errRate)/errRate));
		
	}
	
	public static void updateMu(double updateCoefficient, int iteration){
		for(int i=0;i<train.size();i++){
			int feature_i = candidates_G.get(iteration-1).getFeature_index();
			if(train.get(i).getX().get(feature_i)>=candidates_G.get(iteration-1).getThreshold()){
				if((double)1*(train.get(i).getY())*candidates_G.get(iteration-1).getS()<0){
					train.get(i).getMus().add(train.get(i).getMus().get(iteration-1)*updateCoefficient);
				}else{
					train.get(i).getMus().add(train.get(i).getMus().get(iteration-1)/updateCoefficient);
				}
			}else{
				if((double)-1*(train.get(i).getY())*candidates_G.get(iteration-1).getS()<0){
					train.get(i).getMus().add(train.get(i).getMus().get(iteration-1)*updateCoefficient);
				}else{
					train.get(i).getMus().add(train.get(i).getMus().get(iteration-1)/updateCoefficient);
				}
			}
		}
		
		
	}
	
	public double findMinError(){
		return 0;
		
	}
	
	public static void _12(){
		double[] gt_errorRates=new double[candidates_G.size()];
		for(int i=0;i<candidates_G.size();i++){
			double gt_errRate=0;
			for(int j=0;j<train.size();j++){	
					double gt = 0;
					int feature_i = candidates_G.get(i).getFeature_index();
					if(train.get(j).getX().get(feature_i)>=candidates_G.get(i).getThreshold()){
						gt=(double)1*candidates_G.get(i).getS();
						
					}else{
						gt=(double)(-1)*candidates_G.get(i).getS();
					}
			
				if(gt*train.get(j).getY()<0){
					gt_errRate++;
				}
			}
			gt_errRate = gt_errRate/train.size();
			gt_errorRates[i]=gt_errRate;
		}
		for(int t=0;t<gt_errorRates.length;t++){
			System.out.println(gt_errorRates[t]);
		}
		
		
	}
	
	public static void _14(){
		double[] gt_errorRates=new double[candidates_G.size()];
		for(int i=0;i<candidates_G.size();i++){
			
			double gt_errRate=0;
			for(int j=0;j<train.size();j++){
				double single_errRate=0;
				double gt_sum=0;
				for(int k=0;k<=i;k++){
					
					double gt = 0;
					int feature_i = candidates_G.get(k).getFeature_index();
					if(train.get(j).getX().get(feature_i)>=candidates_G.get(k).getThreshold()){
						gt=(double)1*candidates_G.get(k).getS();
						
					}else{
						gt=(double)(-1)*candidates_G.get(k).getS();
					}
					gt=gt*candidates_G.get(k).getAlpha();
					gt_sum+=gt;
				}
				if(gt_sum*train.get(j).getY()<0){
					gt_errRate++;
				}
			}
			gt_errRate = gt_errRate/train.size();
			gt_errorRates[i]=gt_errRate;
		}
		
		for(int t=0;t<gt_errorRates.length;t++){
			System.out.println(gt_errorRates[t]);
		}
		
	}
	
	public static void _15(){
		double[] Mut = new double[candidates_G.size()];
		for(int i=0;i<candidates_G.size();i++){
			double mu_sum = 0;
			for(int j=0;j<train.size();j++){
				mu_sum += train.get(j).getMus().get(i);
			}
			Mut[i] = mu_sum;
		}
		
		for(int i=0;i<candidates_G.size();i++){
			System.out.println(Mut[i]);
		}
	}
	
	public static void _16(){
		
		double min =candidates_G.get(0).getErrRate();
		for(int i=0;i<candidates_G.size();i++){
			if(candidates_G.get(i).getErrRate()<min){
				min = candidates_G.get(i).getErrRate();
			}
			System.out.println(candidates_G.get(i).getErrRate());
		}
		
		System.out.println("Minimun e:"+min);
		
		
		
	}
	
	
	public static void _17(){
		double[] gt_errorRates=new double[candidates_G.size()];
		for(int i=0;i<candidates_G.size();i++){
			double gt_errRate=0;
			for(int j=0;j<test.size();j++){	
					double gt = 0;
					int feature_i = candidates_G.get(i).getFeature_index();
					if(test.get(j).getX().get(feature_i)>=candidates_G.get(i).getThreshold()){
						gt=(double)1*candidates_G.get(i).getS();
						
					}else{
						gt=(double)(-1)*candidates_G.get(i).getS();
					}
			
				if(gt*test.get(j).getY()<0){
					gt_errRate++;
				}
			}
			gt_errRate = gt_errRate/test.size();
			gt_errorRates[i]=gt_errRate;
		}
		for(int t=0;t<gt_errorRates.length;t++){
			System.out.println(gt_errorRates[t]);
		}
		
	}
	
	public static void _18(){
		double[] gt_errorRates=new double[candidates_G.size()];
		for(int i=0;i<candidates_G.size();i++){
			
			double gt_errRate=0;
			for(int j=0;j<test.size();j++){
				double single_errRate=0;
				double gt_sum=0;
				for(int k=0;k<=i;k++){
					
					double gt = 0;
					int feature_i = candidates_G.get(k).getFeature_index();
					if(test.get(j).getX().get(feature_i)>=candidates_G.get(k).getThreshold()){
						gt=(double)1*candidates_G.get(k).getS();
						
					}else{
						gt=(double)(-1)*candidates_G.get(k).getS();
					}
					gt=gt*candidates_G.get(k).getAlpha();
					gt_sum+=gt;
				}
				if(gt_sum*test.get(j).getY()<0){
					gt_errRate++;
				}
			}
			gt_errRate = gt_errRate/test.size();
			gt_errorRates[i]=gt_errRate;
		}
		
		for(int t=0;t<gt_errorRates.length;t++){
			System.out.println(gt_errorRates[t]);
		}
	}
	
	
	public static void main(String args[]){
		readTrain(train_inputPath);
		readTest(test_inputPath);
//		for(int i =0;i<train.size();i++){
//			System.out.println(""+train.get(i).getX().get(0)+" "+train.get(i).getX().get(1)+" "+train.get(i).getY());
//		}
		// init u0
		for(int i =0;i<train.size();i++){
			train.get(i).getMus().add((double) (1/(double)train.size()));
		}
		
		for(int i =0;i<test.size();i++){
			test.get(i).getMus().add((double) (1/(double)test.size()));
		}
		
//		for(int i =0;i<train .size();i++){
//			System.out.println(""+train.get(i).getMus().get(0));
//		}
		int t = 0;
		//Train
		do{
			
			thresholds_x0 = new ArrayList<Double>();
			thresholds_x1 = new ArrayList<Double>();
			
			sortInputbyFeature(0);
			calThresholdbyFeature_x0();
			Function x0_target = findMinErrbyFeature_x0(t);
//			System.out.println(x0_target.getErrRate());
			
			
			sortInputbyFeature(1);
			calThresholdbyFeature_x1();
			Function x1_target = findMinErrbyFeature_x1(t);
//			System.out.println(x1_target.getErrRate());
			
			
			if(x1_target.getErrRate()<x0_target.getErrRate()){
				candidates_G.add(x1_target);
			}else{
				candidates_G.add(x0_target);
			}
			double updateCoefficient = calUpdateCoefficient(candidates_G.get(t).getErrRate());
	
			updateMu(updateCoefficient,++t);
			
		}while(t<300);
		
		for(int i=0;i<candidates_G.size();i++){
			candidates_G.get(i).setAlpha(Math.log(calUpdateCoefficient(candidates_G.get(i).getErrRate())));
		}
		System.out.println(candidates_G.size());
		for(int i=0;i<candidates_G.size();i++){
			System.out.println("Ein(g"+i+")"+":"+candidates_G.get(i).getErrRate()+" Threshold("+i+"):"+candidates_G.get(i).getThreshold()
					+" Alpha:"+candidates_G.get(i).getAlpha()
					);
		}
		
		
		

//		_12();
//		_14();
//		_15();
//		_16();
//		_17();
//		_18();
		
			
		
	}
	public static void readTrain(String input_file_name) {
		String thisLine = null;
		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(input_file_name));

			while ((thisLine = br.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
				Data data = new Data();
				//System.out.println(st.countTokens());
//				o_line = st.nextToken() + " ";
				int m = st.countTokens();
//				System.out.println(st.countTokens());
				for (int i = 0; i < m-1; i++) {
					data.getX().add(i, Double.valueOf(st.nextToken()));
				}
				data.setY(Double.valueOf(st.nextToken()));	
				train.add(data);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void readTest(String input_file_name) {
		String thisLine = null;
		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(input_file_name));

			while ((thisLine = br.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
				Data data = new Data();
				//System.out.println(st.countTokens());
//				o_line = st.nextToken() + " ";
				int m = st.countTokens();
//				System.out.println(st.countTokens());
				for (int i = 0; i < m-1; i++) {
					data.getX().add(i, Double.valueOf(st.nextToken()));
				}
				data.setY(Double.valueOf(st.nextToken()));	
				test.add(data);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
