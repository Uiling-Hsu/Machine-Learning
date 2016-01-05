package hw7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import dataBean.Data;





public class DataReader {
	private  ArrayList<Data> datas;
	
	
	public DataReader(){
		
	}
	
	public ArrayList<Data> read(String input_file_name) {
		 datas = new ArrayList<Data>();
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
				datas.add(data);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	
	
}

