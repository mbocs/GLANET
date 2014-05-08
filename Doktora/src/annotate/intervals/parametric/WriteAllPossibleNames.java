/**
 * @author Burcak Otlu
 *
 * 
 */

package annotate.intervals.parametric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;
import common.Commons;



public class WriteAllPossibleNames {
	
	public static void closeBufferedReaders(List<BufferedReader> bufferedReaderList){
		try {
			for(int i = 0; i<bufferedReaderList.size(); i++){			
					bufferedReaderList.get(i).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void closeBufferedWriters(List<BufferedWriter> bufferedWriterList){
		try {
			for(int i = 0; i<bufferedWriterList.size(); i++){			
					bufferedWriterList.get(i).close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public static void createChromBaseDnaseBufferedWriters(String dataFolder,List<BufferedWriter> bufferedWriterList){
		
		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR1_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR2_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR3_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR4_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR5_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR6_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR7_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR8_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR9_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR10_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR11_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter12 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR12_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter13 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR13_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter14 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR14_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter15 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR15_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter16 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR16_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter17 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR17_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter18 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR18_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter19 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR19_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter20 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR20_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter21 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR21_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter22 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR22_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter23 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRX_DNASE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter24 = FileOperations.createFileWriter(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRY_DNASE_FILENAME_WITH_NUMBERS);
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void createChromBaseDnaseBufferedReaders(String dataFolder,List<BufferedReader> bufferedReaderList){
		
		try {
			FileReader fileReader1 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR1_DNASE_FILENAME);
			FileReader fileReader2 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR2_DNASE_FILENAME);
			FileReader fileReader3 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR3_DNASE_FILENAME);
			FileReader fileReader4 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR4_DNASE_FILENAME);
			FileReader fileReader5 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR5_DNASE_FILENAME);
			FileReader fileReader6 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR6_DNASE_FILENAME);
			FileReader fileReader7 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR7_DNASE_FILENAME);
			FileReader fileReader8 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR8_DNASE_FILENAME);
			FileReader fileReader9 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR9_DNASE_FILENAME);
			FileReader fileReader10 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR10_DNASE_FILENAME);
			FileReader fileReader11 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR11_DNASE_FILENAME);
			FileReader fileReader12 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR12_DNASE_FILENAME);
			FileReader fileReader13 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR13_DNASE_FILENAME);
			FileReader fileReader14 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR14_DNASE_FILENAME);
			FileReader fileReader15 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR15_DNASE_FILENAME);
			FileReader fileReader16 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR16_DNASE_FILENAME);
			FileReader fileReader17 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR17_DNASE_FILENAME);
			FileReader fileReader18 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR18_DNASE_FILENAME);
			FileReader fileReader19 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR19_DNASE_FILENAME);
			FileReader fileReader20 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR20_DNASE_FILENAME);
			FileReader fileReader21 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR21_DNASE_FILENAME);
			FileReader fileReader22 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR22_DNASE_FILENAME);
			FileReader fileReader23 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRX_DNASE_FILENAME);
			FileReader fileReader24 = FileOperations.createFileReader(dataFolder + Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRY_DNASE_FILENAME);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public static void createChromBaseTfbsBufferedWriters(String outputFolder,List<BufferedWriter> bufferedWriterList){

		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR1_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR2_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR3_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR4_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR5_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR6_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR7_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR8_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR9_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR10_TFBS_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR11_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR12_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR13_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR14_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR15_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR16_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR17_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR18_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR19_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR20_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR21_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR22_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter23 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRX_TFBS_FILENAME_WITH_NUMBERS);;
			FileWriter fileWriter24 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRY_TFBS_FILENAME_WITH_NUMBERS);;
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public static void createChromBaseTfbsBufferedReaders(String outputFolder,List<BufferedReader> bufferedReaderList){

		try {
			FileReader fileReader1 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR1_TFBS_FILENAME);
			FileReader fileReader2 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR2_TFBS_FILENAME);
			FileReader fileReader3 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR3_TFBS_FILENAME);
			FileReader fileReader4 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR4_TFBS_FILENAME);
			FileReader fileReader5 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR5_TFBS_FILENAME);
			FileReader fileReader6 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR6_TFBS_FILENAME);
			FileReader fileReader7 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR7_TFBS_FILENAME);
			FileReader fileReader8 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR8_TFBS_FILENAME);
			FileReader fileReader9 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR9_TFBS_FILENAME);
			FileReader fileReader10 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR10_TFBS_FILENAME);;
			FileReader fileReader11 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR11_TFBS_FILENAME);;
			FileReader fileReader12 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR12_TFBS_FILENAME);;
			FileReader fileReader13 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR13_TFBS_FILENAME);;
			FileReader fileReader14 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR14_TFBS_FILENAME);;
			FileReader fileReader15 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR15_TFBS_FILENAME);;
			FileReader fileReader16 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR16_TFBS_FILENAME);;
			FileReader fileReader17 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR17_TFBS_FILENAME);;
			FileReader fileReader18 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR18_TFBS_FILENAME);;
			FileReader fileReader19 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR19_TFBS_FILENAME);;
			FileReader fileReader20 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR20_TFBS_FILENAME);;
			FileReader fileReader21 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR21_TFBS_FILENAME);;
			FileReader fileReader22 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR22_TFBS_FILENAME);;
			FileReader fileReader23 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRX_TFBS_FILENAME);;
			FileReader fileReader24 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRY_TFBS_FILENAME);;
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//todo
	
	public static void createChromBaseHistoneBufferedWriters(String outputFolder, List<BufferedWriter> bufferedWriterList){

		try {
			FileWriter fileWriter1 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR1_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter2 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR2_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter3 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR3_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter4 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR4_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter5 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR5_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter6 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR6_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter7 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR7_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter8 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR8_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter9 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR9_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter10 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR10_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter11 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR11_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter12 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR12_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter13 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR13_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter14 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR14_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter15 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR15_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter16 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR16_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter17 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR17_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter18 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR18_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter19 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR19_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter20 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR20_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter21 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR21_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter22 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR22_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter23 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRX_HISTONE_FILENAME_WITH_NUMBERS);
			FileWriter fileWriter24 = FileOperations.createFileWriter(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRY_HISTONE_FILENAME_WITH_NUMBERS);
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufferedWriterList.add(bufferedWriter1);
			bufferedWriterList.add(bufferedWriter2);
			bufferedWriterList.add(bufferedWriter3);
			bufferedWriterList.add(bufferedWriter4);
			bufferedWriterList.add(bufferedWriter5);
			bufferedWriterList.add(bufferedWriter6);
			bufferedWriterList.add(bufferedWriter7);
			bufferedWriterList.add(bufferedWriter8);
			bufferedWriterList.add(bufferedWriter9);
			bufferedWriterList.add(bufferedWriter10);
			bufferedWriterList.add(bufferedWriter11);
			bufferedWriterList.add(bufferedWriter12);
			bufferedWriterList.add(bufferedWriter13);
			bufferedWriterList.add(bufferedWriter14);
			bufferedWriterList.add(bufferedWriter15);
			bufferedWriterList.add(bufferedWriter16);
			bufferedWriterList.add(bufferedWriter17);
			bufferedWriterList.add(bufferedWriter18);
			bufferedWriterList.add(bufferedWriter19);
			bufferedWriterList.add(bufferedWriter20);
			bufferedWriterList.add(bufferedWriter21);
			bufferedWriterList.add(bufferedWriter22);
			bufferedWriterList.add(bufferedWriter23);
			bufferedWriterList.add(bufferedWriter24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//todo
	
	public static void createChromBaseHistoneBufferedReaders(String outputFolder, List<BufferedReader> bufferedReaderList){

		try {
			FileReader fileReader1 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR1_HISTONE_FILENAME);
			FileReader fileReader2 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR2_HISTONE_FILENAME);
			FileReader fileReader3 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR3_HISTONE_FILENAME);
			FileReader fileReader4 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR4_HISTONE_FILENAME);
			FileReader fileReader5 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR5_HISTONE_FILENAME);
			FileReader fileReader6 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR6_HISTONE_FILENAME);
			FileReader fileReader7 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR7_HISTONE_FILENAME);
			FileReader fileReader8 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR8_HISTONE_FILENAME);
			FileReader fileReader9 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR9_HISTONE_FILENAME);
			FileReader fileReader10 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR10_HISTONE_FILENAME);
			FileReader fileReader11 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR11_HISTONE_FILENAME);
			FileReader fileReader12 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR12_HISTONE_FILENAME);
			FileReader fileReader13 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR13_HISTONE_FILENAME);
			FileReader fileReader14 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR14_HISTONE_FILENAME);
			FileReader fileReader15 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR15_HISTONE_FILENAME);
			FileReader fileReader16 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR16_HISTONE_FILENAME);
			FileReader fileReader17 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR17_HISTONE_FILENAME);
			FileReader fileReader18 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR18_HISTONE_FILENAME);
			FileReader fileReader19 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR19_HISTONE_FILENAME);
			FileReader fileReader20 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR20_HISTONE_FILENAME);
			FileReader fileReader21 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR21_HISTONE_FILENAME);
			FileReader fileReader22 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHR22_HISTONE_FILENAME);
			FileReader fileReader23 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRX_HISTONE_FILENAME);
			FileReader fileReader24 = FileOperations.createFileReader(outputFolder + Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.UNSORTED_CHRY_HISTONE_FILENAME);
			
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
			BufferedReader bufferedReader4 = new BufferedReader(fileReader4);
			BufferedReader bufferedReader5 = new BufferedReader(fileReader5);
			BufferedReader bufferedReader6 = new BufferedReader(fileReader6);
			BufferedReader bufferedReader7 = new BufferedReader(fileReader7);
			BufferedReader bufferedReader8 = new BufferedReader(fileReader8);
			BufferedReader bufferedReader9 = new BufferedReader(fileReader9);
			BufferedReader bufferedReader10 = new BufferedReader(fileReader10);
			BufferedReader bufferedReader11 = new BufferedReader(fileReader11);
			BufferedReader bufferedReader12 = new BufferedReader(fileReader12);
			BufferedReader bufferedReader13 = new BufferedReader(fileReader13);
			BufferedReader bufferedReader14 = new BufferedReader(fileReader14);
			BufferedReader bufferedReader15 = new BufferedReader(fileReader15);
			BufferedReader bufferedReader16 = new BufferedReader(fileReader16);
			BufferedReader bufferedReader17 = new BufferedReader(fileReader17);
			BufferedReader bufferedReader18 = new BufferedReader(fileReader18);
			BufferedReader bufferedReader19 = new BufferedReader(fileReader19);
			BufferedReader bufferedReader20 = new BufferedReader(fileReader20);
			BufferedReader bufferedReader21 = new BufferedReader(fileReader21);
			BufferedReader bufferedReader22 = new BufferedReader(fileReader22);
			BufferedReader bufferedReader23 = new BufferedReader(fileReader23);
			BufferedReader bufferedReader24 = new BufferedReader(fileReader24);
			
			bufferedReaderList.add(bufferedReader1);
			bufferedReaderList.add(bufferedReader2);
			bufferedReaderList.add(bufferedReader3);
			bufferedReaderList.add(bufferedReader4);
			bufferedReaderList.add(bufferedReader5);
			bufferedReaderList.add(bufferedReader6);
			bufferedReaderList.add(bufferedReader7);
			bufferedReaderList.add(bufferedReader8);
			bufferedReaderList.add(bufferedReader9);
			bufferedReaderList.add(bufferedReader10);
			bufferedReaderList.add(bufferedReader11);
			bufferedReaderList.add(bufferedReader12);
			bufferedReaderList.add(bufferedReader13);
			bufferedReaderList.add(bufferedReader14);
			bufferedReaderList.add(bufferedReader15);
			bufferedReaderList.add(bufferedReader16);
			bufferedReaderList.add(bufferedReader17);
			bufferedReaderList.add(bufferedReader18);
			bufferedReaderList.add(bufferedReader19);
			bufferedReaderList.add(bufferedReader20);
			bufferedReaderList.add(bufferedReader21);
			bufferedReaderList.add(bufferedReader22);
			bufferedReaderList.add(bufferedReader23);
			bufferedReaderList.add(bufferedReader24);				
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void readCellLineNames(String dataFolder,List<String> cellLineNames,Map<String,Integer> cellLineName2CellLineNumberMap,Map<Integer,String> cellLineNumber2CellLineNameMap,FileNameNumber fileNameNumber,List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		createChromBaseDnaseBufferedReaders(dataFolder,bufferedReaderList);
		createChromBaseDnaseBufferedWriters(dataFolder,bufferedWriterList);
		
		String strLine;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		
		String chrNameLowHigh;
		String cellLineName;
		String fileName;
		int cellLineNameNumber = 1;
		
		
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 bufferedWriter = bufferedWriterList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted dnase line
//						chrY	10036738	10039094	H1_ES	idrPool.H1_ES_DNaseHS_BP_TP_P_peaks_OV_DukeDNase_H1_ES_B1_peaks_VS_DukeDNase_H1_ES_B2_peaks.npk2.narrowPeak

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						
						chrNameLowHigh =  strLine.substring(0, indexofThirdTab);
						cellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						fileName = strLine.substring(indexofFourthTab+1);
						
						
						if(!(cellLineNames.contains(cellLineName))){
							cellLineNames.add(cellLineName);
							
							cellLineName2CellLineNumberMap.put(cellLineName, cellLineNameNumber);
							cellLineNumber2CellLineNameMap.put(cellLineNameNumber, cellLineName);
							
							cellLineNameNumber++;
						}
						
						
						if (!(fileNames.contains(fileName))){
							
							fileNames.add(fileName);
							fileName2FileNumberMap.put(fileName, fileNameNumber.getFileNameNumber());
							fileNumber2FileNameMap.put(fileNameNumber.getFileNameNumber(), fileName);
							
							fileNameNumber.setFileNameNumber(fileNameNumber.getFileNameNumber()+1);;
						}
						
						
						bufferedWriter.write(chrNameLowHigh + "\t" + cellLineName2CellLineNumberMap.get(cellLineName) + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator"));
						
						
						
					 }// End of While			
			}// End of For						
			
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		
		closeBufferedReaders(bufferedReaderList);
		closeBufferedWriters(bufferedWriterList);
	}
	
	
	public static void readTforHistoneNames(String dataFolder,List<String> tfbsorHistoneNames,Map<String,Integer> elementName2ElementNumberMap,Map<Integer,String> elementNumber2ElementNameMap,String tfbsorHistone,FileNameNumber fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		List<BufferedReader> bufferedReaderList = new ArrayList<BufferedReader>();
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		
		if (tfbsorHistone.equals(Commons.TFBS)){
			createChromBaseTfbsBufferedReaders(dataFolder,bufferedReaderList);
			createChromBaseTfbsBufferedWriters(dataFolder,bufferedWriterList);
						
		}else if (tfbsorHistone.equals(Commons.HISTONE)) {
			createChromBaseHistoneBufferedReaders(dataFolder,bufferedReaderList);	
			createChromBaseHistoneBufferedWriters(dataFolder,bufferedWriterList);				
			
		}
		
		
		String strLine;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstTab 	= 0;
		int indexofSecondTab 	= 0;
		int indexofThirdTab 	= 0;
		int indexofFourthTab	= 0;
		int indexofFifthTab	= 0;
			
		String chrNameLowHigh;
		String tfbsorHistoneName;
		String cellLineName;
		String fileName;
		int elementNumber = 1;
		
		
		try {
			for(int i = 0; i< bufferedReaderList.size() ; i++){
				 bufferedReader = bufferedReaderList.get(i);
				 bufferedWriter = bufferedWriterList.get(i);
				 				
					while((strLine = bufferedReader.readLine())!=null){
//						example unsorted tfbs line
//						chrY	2804079	2804213	Ctcf	H1hesc	spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak
						
//						example unsorted histone line
//						chrY	15589743	15592520	H3k27ac	H1hesc	wgEncodeBroadHistoneH1hescH3k27acStdAln.narrowPeak

						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
						
						chrNameLowHigh = strLine.substring(0, indexofThirdTab);
						tfbsorHistoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
						fileName = strLine.substring(indexofFifthTab+1);
						
						if(!(tfbsorHistoneNames.contains(tfbsorHistoneName))){
							tfbsorHistoneNames.add(tfbsorHistoneName);
							
							elementName2ElementNumberMap.put(tfbsorHistoneName, elementNumber);
							elementNumber2ElementNameMap.put(elementNumber, tfbsorHistoneName);
							
							elementNumber++;
			
						}
						
						if (!(fileNames.contains(fileName))){
							
							fileNames.add(fileName);
							fileName2FileNumberMap.put(fileName, fileNameNumber.getFileNameNumber());
							fileNumber2FileNameMap.put(fileNameNumber.getFileNameNumber(), fileName);
							
							fileNameNumber.setFileNameNumber(fileNameNumber.getFileNameNumber()+1);;
						}
						
						
						bufferedWriter.write(chrNameLowHigh + "\t" + elementName2ElementNumberMap.get(tfbsorHistoneName) + "\t" + "todo cellline number is expected" + "\t" + fileName2FileNumberMap.get(fileName) + System.getProperty("line.separator"));
					 }// End of While			
			}// End of For
									
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		closeBufferedReaders(bufferedReaderList);		
		closeBufferedWriters(bufferedWriterList);
		
	}

	

	public static void readGeneIds(String dataFolder, List<String> geneIds, String inputFileName){
		
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		String geneId;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//			example line 
//9606	1	REVIEWED	NM_130786.3	161377438	NP_570602.2	21071030	AC_000151.1	157718668	55167315	55174019	-	Alternate HuRef
				

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				geneId = strLine.substring(indexofFirstTab+1,indexofSecondTab);				
				
				if(!(geneIds.contains(geneId))){					
					geneIds.add(geneId);								
				}													
			} // End of While
						
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void readRNAAccessionVersions(String dataFolder, List<String> rnaNucleotideAccessionVersions,String inputFileName){
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofDot = 0;
		
		String rnaNucleotideAccessionVersion;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line 
//9606	1	REVIEWED	NM_130786.3	161377438	NP_570602.2	21071030	AC_000151.1	157718668	55167315	55174019	-	Alternate HuRef

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				
				rnaNucleotideAccessionVersion = strLine.substring(indexofThirdTab+1,indexofFourthTab);				
				indexofDot =rnaNucleotideAccessionVersion.indexOf('.');
				
				if(indexofDot>=0){
					rnaNucleotideAccessionVersion = rnaNucleotideAccessionVersion.substring(0, indexofDot);
				}
				
				
				if(!(rnaNucleotideAccessionVersions.contains(rnaNucleotideAccessionVersion))){					
					rnaNucleotideAccessionVersions.add(rnaNucleotideAccessionVersion);								
				}													
			} // End of While
			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void readUcscRefSeqGeneName2s(String dataFolder,List<String>  ucscRefSeqGeneName2s,String inputFileName){
		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;
		int indexofNinethTab = 0;
		int indexofTenthTab = 0;
		int indexofEleventhTab = 0;
		int indexofTwelfthTab = 0;
		int indexofThirteenthTab = 0;
		
		String ucscRefSeqGeneName2;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
//			consume first line
			strLine = bufferedReader.readLine();
			
			while((strLine = bufferedReader.readLine())!=null){
//				Example line
//				#bin	name	chrom	strand	txStart	txEnd	cdsStart	cdsEnd	exonCount	exonStarts	exonEnds	score	name2	cdsStartStat	cdsEndStat	exonFrames
//				1	NM_032785	chr1	-	48998526	50489626	48999844	50489468	14	48998526,49000561,49005313,49052675,49056504,49100164,49119008,49128823,49332862,49511255,49711441,50162984,50317067,50489434,	48999965,49000588,49005410,49052838,49056657,49100276,49119123,49128913,49332902,49511472,49711536,50163109,50317190,50489626,	0	AGBL4	cmpl	cmpl	2,2,1,0,0,2,1,1,0,2,0,1,1,0,				

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
				indexofEighthTab = strLine.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab = strLine.indexOf('\t',indexofEighthTab+1);
				indexofTenthTab = strLine.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab = strLine.indexOf('\t',indexofTenthTab+1);
				indexofTwelfthTab = strLine.indexOf('\t',indexofEleventhTab+1);
				indexofThirteenthTab = strLine.indexOf('\t',indexofTwelfthTab+1);
				
				
				ucscRefSeqGeneName2 = strLine.substring(indexofTwelfthTab+1,indexofThirteenthTab);				
				
				if(!(ucscRefSeqGeneName2s.contains(ucscRefSeqGeneName2))){					
					ucscRefSeqGeneName2s.add(ucscRefSeqGeneName2);								
				}													
			} // End of While
			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static void readKeggPathwayNames(String dataFolder,List<String> keggPathwayNameList, Map<String,Integer> keggPathwayName2KeggPathwayNumberMap,Map<Integer,String> keggPathwayNumber2KeggPathwayNameMap, String inputFileName){

		String strLine;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofTab 	= 0;
		int indexofColon = 0;
		
		String keggPathwayName;
		int keggPathwayNumber = 1;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
//				example line
//				path:hsa00010	hsa:10327	reverse

				indexofTab = strLine.indexOf('\t');				
				keggPathwayName = strLine.substring(0,indexofTab);
				
				indexofColon = keggPathwayName.indexOf(':');				
				keggPathwayName = keggPathwayName.substring(indexofColon+1);				
				
				if(!(keggPathwayNameList.contains(keggPathwayName))){					
					keggPathwayNameList.add(keggPathwayName);	
					
					keggPathwayName2KeggPathwayNumberMap.put(keggPathwayName, keggPathwayNumber);
					keggPathwayNumber2KeggPathwayNameMap.put(keggPathwayNumber, keggPathwayName);
					keggPathwayNumber++;
										
				}													
			} // End of While			
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
							
	}

	
	public static void writeNames(String dataFolder,List<String> nameList, String outputDirectoryName, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		
		try {
			
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
		
			for(int i = 0; i<nameList.size() ;i++){
								
				bufferedWriter.write(nameList.get(i)+ System.getProperty("line.separator"));
				bufferedWriter.flush();				
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
					
	
	}
	
	
	public static void writeMapsString2Integer(String dataFolder,Map<String,Integer> cellLineName2CellLineNumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<String,Integer> entry :cellLineName2CellLineNumberMap.entrySet()){
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() + System.getProperty("line.separator"));
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	
	public static void writeMapsInteger2String(String dataFolder,Map<Integer,String> cellLineName2CellLineNumberMap, String outputDirectoryName, String outputFileName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;		
		
		try {
			fileWriter = FileOperations.createFileWriter(dataFolder + outputDirectoryName,outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<Integer,String> entry :cellLineName2CellLineNumberMap.entrySet()){
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() + System.getProperty("line.separator"));
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
	
	
	public static void writeAllPossibleEncodeCellLineNames(String dataFolder,FileNameNumber fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<String> cellLineNames = new ArrayList<String>();
		Map<String,Integer> cellLineName2CellLineNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> cellLineNumber2CellLineNameMap = new HashMap<Integer,String>();

		readCellLineNames(dataFolder,cellLineNames,cellLineName2CellLineNumberMap,cellLineNumber2CellLineNameMap,fileNameNumber,fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		writeNames(dataFolder,cellLineNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_CELL_LINE_NAMES_OUTPUT_FILENAME);
		writeMapsString2Integer(dataFolder,cellLineName2CellLineNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_CELLLINENAME_2_CELLLINENUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,cellLineNumber2CellLineNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_CELLLINENUMBER_2_CELLLINENAME_OUTPUT_FILENAME);
			
	}
	
	
	public static void writeAllPossibleEncodeTfNames(String dataFolder,FileNameNumber fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<String> tfNames = new ArrayList<String>();
		Map<String,Integer> tfName2TfNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> tfNumber2TfNameMap = new HashMap<Integer,String>();

		readTforHistoneNames(dataFolder,tfNames, tfName2TfNumberMap, tfNumber2TfNameMap,Commons.TFBS,fileNameNumber, fileNames,fileName2FileNumberMap, fileNumber2FileNameMap);
		writeNames(dataFolder,tfNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ENCODE_TF_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,tfName2TfNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_TFNAME_2_TFNUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,tfNumber2TfNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_TFNUMBER_2_TFNAME_OUTPUT_FILENAME);
	
	}

	public static void writeAllPossibleEncodeHistoneNames(String dataFolder,FileNameNumber fileNameNumber, List<String> fileNames,Map<String,Integer> fileName2FileNumberMap, Map<Integer,String> fileNumber2FileNameMap){
		
		List<String> histoneNames = new ArrayList<String>();
		Map<String,Integer> histoneName2HistoneNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> histoneNumber2HistoneNameMap = new HashMap<Integer,String>();

		readTforHistoneNames(dataFolder,histoneNames,histoneName2HistoneNumberMap,histoneNumber2HistoneNameMap,Commons.HISTONE,fileNameNumber, fileNames,fileName2FileNumberMap, fileNumber2FileNameMap);
		writeNames(dataFolder,histoneNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONE_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,histoneName2HistoneNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONENAME_2_HISTONENUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,histoneNumber2HistoneNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_HISTONENUMBER_2_HISTONENAME_OUTPUT_FILENAME);

	}
	
	public static void writeAllPossibleEncodeFileNames(String dataFolder,List<String> fileNames,Map<String,Integer> fileName2FileNumberMap,Map<Integer,String> fileNumber2FileNameMap){
		writeNames(dataFolder,fileNames,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_ENCODE_FILE_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,fileName2FileNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_FILENAME_2_FILENUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,fileNumber2FileNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_ENCODE_FILENUMBER_2_FILENAME_OUTPUT_FILENAME);

		
	}
	
	
	public static void writeAllPossibleGeneIds(String dataFolder){

		List<String> geneIds = new ArrayList<String>();
		readGeneIds(dataFolder, geneIds,Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME +Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME );
		writeNames(dataFolder,geneIds,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_GENE_IDS_OUTPUT_FILENAME);		
		
	}
	
	public static void writeAllPossibleRNAAccessionVersions(String dataFolder){

		List<String> rnaNucleotideAccessionVersions = new ArrayList<String>();
		readRNAAccessionVersions(dataFolder,rnaNucleotideAccessionVersions,Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_FILENAME);
		writeNames(dataFolder,rnaNucleotideAccessionVersions,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_RNA_NUCLEUOTIDE_ACCESSION_VERSIONS_OUTPUT_FILENAME);		
		
	}

	
	public static void writeAllPossibleUcscRefSeqGeneName2s(String dataFolder){

		List<String> ucscRefSeqGeneName2s = new ArrayList<String>();
		readUcscRefSeqGeneName2s(dataFolder,ucscRefSeqGeneName2s,Commons.FTP_HG19_REFSEQ_GENES);
		writeNames(dataFolder,ucscRefSeqGeneName2s,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME, Commons.WRITE_ALL_POSSIBLE_ALTERNATE_GENE_NAMES_OUTPUT_FILENAME);		
		
	}
	
	public static void writeAllPossibleKeggPathwayNames(String dataFolder){
		
		List<String> keggPathwayNameList = new ArrayList<String>();	
		Map<String,Integer> keggPathwayName2KeggPathwayNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> keggPathwayNumber2KeggPathwayNameMap = new HashMap<Integer,String>();

		
		readKeggPathwayNames(dataFolder,keggPathwayNameList,keggPathwayName2KeggPathwayNumberMap,keggPathwayNumber2KeggPathwayNameMap,Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE);
		writeNames(dataFolder,keggPathwayNameList,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILENAME);		
		writeMapsString2Integer(dataFolder,keggPathwayName2KeggPathwayNumberMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNAME_2_KEGGPATHWAYNUMBER_OUTPUT_FILENAME);
		writeMapsInteger2String(dataFolder,keggPathwayNumber2KeggPathwayNameMap,Commons.WRITE_ALL_POSSIBLE_NAMES_OUTPUT_DIRECTORYNAME,Commons.WRITE_ALL_POSSIBLE_KEGGPATHWAYNUMBER_2_KEGGPATHWAYNAME_OUTPUT_FILENAME);

	}
	
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, default 1
	//args[4]	--->	Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respest to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE
	//			--->			Commons.BONFERRONI_CORRECTED_P_VALUE
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Benjamini Hochberg FDR, default 0.05
	//args[9]	--->	Number of permutations, default 5000
	//args[10]	--->	Dnase Enrichment
	//			--->	default Commons.DO_NOT_DNASE_ENRICHMENT
	//			--->	Commons.DO_DNASE_ENRICHMENT
	//args[11]	--->	Histone Enrichment
	//			--->	default	Commons.DO_NOT_HISTONE_ENRICHMENT
	//			--->			Commons.DO_HISTONE_ENRICHMENT
	//args[12]	--->	Transcription Factor(TF) Enrichment 
	//			--->	default	Commons.DO_NOT_TF_ENRICHMENT
	//			--->			Commons.DO_TF_ENRICHMENT
	//args[13]	--->	KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_KEGGPATHWAY_ENRICHMENT
	//args[14]	--->	TF and KEGG Pathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_KEGGPATHWAY_ENRICHMENT
	//args[15]	--->	TF and CellLine and KeggPathway Enrichment
	//			--->	default	Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 
	//			--->			Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[16]	--->	RSAT parameter
	//			--->	default Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//			--->			Commons.DO_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT
	//args[17]	--->	job name example: ocd_gwas_snps 
	//args[18]	--->	writeGeneratedRandomDataMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA
	//			--->			Commons.WRITE_GENERATED_RANDOM_DATA
	//args[19]	--->	writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//			--->	default Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT
	//args[20]	--->	writePermutationBasedAnnotationResultMode checkBox
	//			---> 	default	Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT
	//			--->			Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT			
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder = glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
	
		//All Possible Encode File Names 
		List<String> fileNames = new ArrayList<String>();
		Map<String,Integer> fileName2FileNumberMap = new HashMap<String,Integer>();
		Map<Integer,String> fileNumber2FileNameMap = new HashMap<Integer,String>();
		
		FileNameNumber fileNameNumber = new FileNameNumber(1);
		
//todo
		//cell line name mapleri hepsine gönderilecek
		
		//Write all possible ENCODE cell line names	
		//Using unsorted dnase txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\dnase 
		WriteAllPossibleNames.writeAllPossibleEncodeCellLineNames(dataFolder,fileNameNumber, fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		
		//Write all possible ENCODE tfbs names
		//Using unsorted tfbs txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\tfbs 
		WriteAllPossibleNames.writeAllPossibleEncodeTfNames(dataFolder,fileNameNumber, fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		
		//Write all possible ENCODE histone names
		//Using unsorted tfbs txt files under C:\eclipse_ganymede\workspace\Doktora1\src\annotate\encode\input_output\\histone
		WriteAllPossibleNames.writeAllPossibleEncodeHistoneNames(dataFolder,fileNameNumber, fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);
		
		//Write all possible ENCODE file names	
		WriteAllPossibleNames.writeAllPossibleEncodeFileNames(dataFolder,fileNames,fileName2FileNumberMap,fileNumber2FileNameMap);


		//Write all possible gene ids
//		Using human_gene2refseq.txt under C:\eclipse_ganymede\workspace\Doktora1\src\ncbi\input_output
		WriteAllPossibleNames.writeAllPossibleGeneIds(dataFolder);

		//Write all possible RNA nucleotide accession version, in other words ucsc refseq gene name
//		Using human_gene2refseq.txt under C:\eclipse_ganymede\workspace\Doktora1\src\ncbi\input_output
		WriteAllPossibleNames.writeAllPossibleRNAAccessionVersions(dataFolder);

		//Write all possible ucsc refseq gene name2
//		Using hg19_refseq_genes.txt under C:\\eclipse_ganymede\\workspace\\Doktora1\\src\\annotate\\ucscgenome\\input_output	
		WriteAllPossibleNames.writeAllPossibleUcscRefSeqGeneName2s(dataFolder);

		//Write all possible kegg pathway names		
		//Using pathway_hsa.list under C:\eclipse_ganymede\workspace\Doktora1\src\keggpathway\ncbigenes\input_output
		WriteAllPossibleNames.writeAllPossibleKeggPathwayNames(dataFolder);
		
		
	}

}
