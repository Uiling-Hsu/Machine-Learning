import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

public class _20 {
	public static double T[] = { 0, 0, 0, 0, 0 };
	private static double G[] = { Math.pow(10, 0), Math.pow(10, 1), Math.pow(10, 2), Math.pow(10, 3), Math.pow(10, 4) };
	public static int A[] = new int[7291];
	public static int times = 0;
	
	
	public static void main(String argv[]) throws IOException {
		for (int index = 0; index < A.length; index++) {
			A[index] = index;
		}
		
		
		for(int num = 0; num<100; num++){
			times++;
			double[] E = {0,0,0,0,0};
			HashSet collection = shuffle(A, 1000);
			System.out.println("size:"+collection.size());
			RandShuffleInputConvertor(collection);
	
			for (int g = 0; g < G.length; g++) {
	
				String a[] = { "-t", "2", "-g ", "" + G[g], "-c ", "" + 0.1, "data/15feature.train" };
				svm_train t = new svm_train();
				t.run(a);
				String p[] = { "data/20feature.validation", "15feature.train.model", "20predict" };
				svm_predict.main(p);
				E[g] = 1 - svm_predict.accuracy;
//				System.out.println("e:" + (1 - svm_predict.accuracy));
			}
			int min_index = findMin(E);		
			System.out.println("Min_Index:"+min_index);
			T[min_index]++;
			for (double e : E) {
				System.out.print(e + " ");
			}
			for (double t : T) {
				System.out.print(t + " ");
			}
			System.out.println("Times:"+times);
		}

	}

	private static void RandShuffleInputConvertor(HashSet collection){
			int index = 0;
			String thisLine = null;
				try {
					// open input stream test.txt for reading purpose.
					BufferedReader br = new BufferedReader(new FileReader("resource" + File.separator + "feature.train"));
					PrintWriter writer = new PrintWriter("data" + File.separator + "20feature.train", "UTF-8");
					PrintWriter valWriter = new PrintWriter("data" + File.separator + "20feature.validation", "UTF-8");
					while ((thisLine = br.readLine()) != null) {
						String o_line = null;
						StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
						//System.out.println(st.countTokens());
						double d = Double.valueOf(st.nextToken()).doubleValue();
						if(d!=0){
							o_line = "-1"+ " ";
						}else{
							o_line = "1"+ " ";
						}
						
						int m = st.countTokens();
//						System.out.println(st.countTokens());
						for (int i = 1; i <= m; i++) {
							o_line += i + ":" + st.nextToken() + " ";
						}
						
						if(collection.contains(index)){
							valWriter.println(o_line);
						}else{
							writer.println(o_line);
						}
						index++;
					}
					valWriter.close();
					writer.close();
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}				
	}

	private static HashSet shuffle(int a[], int limit) {
		HashSet<Integer> set = new HashSet();
		Random rnd = ThreadLocalRandom.current();
		for (int i = a.length - 1; i > a.length - limit - 1; i--) {
			int index = rnd.nextInt(i + 1);
			int s = a[index];
			a[index] = a[i];
			a[i] = s;
			set.add(s);
		}
//		int[] dest = new int[limit];
//		System.arraycopy(a, a.length - 1 - limit + 1, dest, 0, limit);
		
		return set;
	}

	private static int findMin(double[] e_v) {
		double min = e_v[0];
		int index=0;
		for (int i = 0; i < e_v.length; i++) {
			if (min > e_v[i]) {
				min = e_v[i];
				index = i;
			}
		}
		return index;
	}

}
