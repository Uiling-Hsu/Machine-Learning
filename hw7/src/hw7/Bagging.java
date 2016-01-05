package hw7;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import dataBean.Data;

public class Bagging {
	public static String train_inputPath = "resource" + File.separator + "hw7_train.txt";//"hw3_train.dat"
	private ArrayList<Data> trainData ;
	private Random random ;
	
	public void init(){
		DataReader dataReader = new DataReader();
		DecisionTree decisionTree = new DecisionTree();
		trainData = dataReader.read(train_inputPath);
	}
	
	public ArrayList<Data> bagging(){
		ArrayList<Data> bag = new ArrayList<Data>();
		random = new Random(System.currentTimeMillis());
		for(int i =0 ;i<trainData.size();i++){
			shuffle(trainData);
			random = ThreadLocalRandom.current();//new Random(System.currentTimeMillis())
			int index = random.nextInt(trainData.size()-1 - 0 + 1);
//			System.out.println(index);
			bag.add(trainData.get(index));
		}
		return bag;
	}
	
	
	private void shuffle(ArrayList<Data> a) {

		Random rnd = ThreadLocalRandom.current();
		for (int i = a.size() - 1; i >0; i--) {
			int index = rnd.nextInt(i + 1);
//			System.out.println(index);
			Data s = a.get(index);
			a.set(index,a.get(i));
			a.set(i, s);
		}

	}
	public static void main(String[] args){
		Bagging b = new Bagging();
		b.init();
		ArrayList<Data> bag = b.bagging();
		for(int i =0;i<bag.size();i++){
			System.out.println(""+bag.get(i).getX().get(0)+" "+bag.get(i).getX().get(1)+" "+bag.get(i).getY());
		}
		
		
	}
	
}
