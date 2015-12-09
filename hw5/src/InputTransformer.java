import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class InputTransformer {
	public static String train_inputPath = "resource" + File.separator + "feature.train";
	public static String train_outputPath = "data" + File.separator + "feature.train";
	public static String test_inputPath = "resource" + File.separator + "feature.test";
	public static String test_outputPath = "data" + File.separator + "feature.test";
	
	
	
	public static void transfor(String input_file_name, String output_file_name) {
		String thisLine = null;

		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(input_file_name));
			PrintWriter writer = new PrintWriter(output_file_name, "UTF-8");

			while ((thisLine = br.readLine()) != null) {
				String o_line = null;
				StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
				//System.out.println(st.countTokens());
				o_line = st.nextToken() + " ";
				int m = st.countTokens();
				System.out.println(st.countTokens());
				for (int i = 1; i <= m; i++) {
					o_line += i + ":" + st.nextToken() + " ";
				}
				writer.println(o_line);
			}

			writer.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void transfor15(String input_file_name, String output_file_name) {
		String thisLine = null;

		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(input_file_name));
			PrintWriter writer = new PrintWriter(output_file_name, "UTF-8");

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
				System.out.println(st.countTokens());
				for (int i = 1; i <= m; i++) {
					o_line += i + ":" + st.nextToken() + " ";
				}
				writer.println(o_line);
			}

			writer.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void transfor16(String input_file_name, String output_file_name) {
		String thisLine = null;

		try {
			// open input stream test.txt for reading purpose.
			BufferedReader br = new BufferedReader(new FileReader(input_file_name));
			PrintWriter writer = new PrintWriter(output_file_name, "UTF-8");

			while ((thisLine = br.readLine()) != null) {
				String o_line = null;
				StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
				//System.out.println(st.countTokens());
				double d = Double.valueOf(st.nextToken()).doubleValue();
				if(d!=8){
					o_line = "-1"+ " ";
				}else{
					o_line = "1"+ " ";
				}
				
				int m = st.countTokens();
				System.out.println(st.countTokens());
				for (int i = 1; i <= m; i++) {
					o_line += i + ":" + st.nextToken() + " ";
				}
				writer.println(o_line);
			}

			writer.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String argv[]) throws IOException {
		InputTransformer.transfor("resource" + File.separator + "test","data"+File.separator+"3Test");
		InputTransformer.transfor15(train_inputPath, "data" + File.separator + "15feature.train");
		InputTransformer.transfor15(test_inputPath, "data" + File.separator + "15feature.test");
		InputTransformer.transfor16(train_inputPath, "data" + File.separator + "16feature.train");
		InputTransformer.transfor15(test_inputPath, "data" + File.separator + "16feature.test");
		InputTransformer.transfor(train_inputPath, train_outputPath);
		InputTransformer.transfor(test_inputPath, test_outputPath);
	}

}
