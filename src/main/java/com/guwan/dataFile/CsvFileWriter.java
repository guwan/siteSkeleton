package com.guwan.dataFile;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
 
/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {
	
	//CSV文件分隔符
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV文件头
	private static final Object [] FILE_HEADER = {"\u624B\u673A\u53F7\u7801"};

    private static int fileTotal=50;//生成文件总量
    private static int singleTotal=300000;//单个文件上限
    private static long startNumber=13500000000l;
	
	/**
	 * 写CSV文件
	 * 
	 * @param fileName
	 */
	public static void writeCsvFile(String fileName) {
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		// 创建 CSVFormat
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		for (int i = 0; i < fileTotal; i++) {
			try {
				// 初始化FileWriter
				fileWriter = new FileWriter(newFile(fileName.replace(".csv",i + ".csv")));
				// 初始化 CSVPrinter
				csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
				// 创建CSV文件头
				//csvFilePrinter.printRecord(FILE_HEADER);
				for (int j = 0; j < singleTotal; j++) {
					csvFilePrinter.print(startNumber+(i*singleTotal+j));
					csvFilePrinter.println();
				}
				System.out.println(fileName.replace(".csv",i + ".csv") + " CSV文件创建成功.");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
					csvFilePrinter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 遍历List写入CSV
		// for (User user : userList) {
		// List<String> userDataRecord = new ArrayList<String>();
		// userDataRecord.add(user.getUsername());
		// userDataRecord.add(user.getPassword());
		// userDataRecord.add(user.getName());
		// userDataRecord.add(String.valueOf(user.getAge()));
		// csvFilePrinter.printRecord(userDataRecord);
		// }
	}
	
	private static File newFile(String fileName) {
		File f = new File(fileName);
		// 创建文件
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		writeCsvFile("D://chenna//phone.csv");
	}
	
}