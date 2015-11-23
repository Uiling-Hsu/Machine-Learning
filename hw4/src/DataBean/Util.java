package DataBean;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Util {
	public final static String train = "data"+File.separator+"hw4_train.txt";
	public final static String test = "data"+File.separator+"hw4_test.txt";
	public final static double[] lambdas = {Math.pow(10, 2),Math.pow(10, 1),Math.pow(10, 0)
		,Math.pow(10, -1),Math.pow(10, -2),Math.pow(10, -3)
		,Math.pow(10, -4),Math.pow(10, -5),Math.pow(10, -6)
		,Math.pow(10, -7),Math.pow(10, -8),Math.pow(10, -9),Math.pow(10, -10)};

	public static int getMinIndex(double[] err){
		int min=0;
		for(int i=0;i<err.length;i++){
			if(err[i]<err[min]){
				min =i;
			}
		}
		return min;
	}

}
