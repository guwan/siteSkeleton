//package com.guwan.dataFile;
// 
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
// 
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
// 
///**
// * @author ashraf
// * 
// */
//public class CsvFileWriter {
//	
//	//CSV鏂囦欢鍒嗛殧绗�
//	private static final String NEW_LINE_SEPARATOR = "\n";
//	
//	//CSV鏂囦欢澶�
//	private static final Object [] FILE_HEADER = {"\u624B\u673A\u53F7\u7801"};
//
//    private static int fileTotal=50;//鐢熸垚鏂囦欢鎬婚噺
//    private static int singleTotal=300000;//鍗曚釜鏂囦欢涓婇檺
//    private static long startNumber=13500000000l;
//	
//	/**
//	 * 鍐機SV鏂囦欢
//	 * 
//	 * @param fileName
//	 */
//	public static void writeCsvFile(String fileName) {
//		FileWriter fileWriter = null;
//		CSVPrinter csvFilePrinter = null;
//		// 鍒涘缓 CSVFormat
//		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
//		for (int i = 0; i < fileTotal; i++) {
//			try {
//				// 鍒濆鍖朏ileWriter
//				fileWriter = new FileWriter(newFile(fileName.replace(".csv",i + ".csv")));
//				// 鍒濆鍖� CSVPrinter
//				csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
//				// 鍒涘缓CSV鏂囦欢澶�
//				//csvFilePrinter.printRecord(FILE_HEADER);
//				for (int j = 0; j < singleTotal; j++) {
//					csvFilePrinter.print(startNumber+(i*singleTotal+j));
//					csvFilePrinter.println();
//				}
//				System.out.println(fileName.replace(".csv",i + ".csv") + " CSV鏂囦欢鍒涘缓鎴愬姛.");
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					fileWriter.flush();
//					fileWriter.close();
//					csvFilePrinter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		// 閬嶅巻List鍐欏叆CSV
//		// for (User user : userList) {
//		// List<String> userDataRecord = new ArrayList<String>();
//		// userDataRecord.add(user.getUsername());
//		// userDataRecord.add(user.getPassword());
//		// userDataRecord.add(user.getName());
//		// userDataRecord.add(String.valueOf(user.getAge()));
//		// csvFilePrinter.printRecord(userDataRecord);
//		// }
//	}
//	
//	private static File newFile(String fileName) {
//		File f = new File(fileName);
//		// 鍒涘缓鏂囦欢
//		if (!f.exists()) {
//			try {
//				f.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return f;
//	}
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args){
//		writeCsvFile("D://chenna//phone.csv");
//	}
//	
//}