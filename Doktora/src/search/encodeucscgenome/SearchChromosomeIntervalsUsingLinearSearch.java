/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

/*
 * 
 * This program divides the input data into chromosome based files.
 * 
 * Then for each input line in chromosome based files it searches for the dnase, tfbs, histone and ucsc refseq genes 
 * whether the input line overlaps with any dnase, tfbs, histone and ucsc refseq genes.
 * 
 * This program uses sorted chromosome based dnase, tfbs, histone and refseq genes files.
 * It accomplishes linear search.
 * 
 * It takes 30 minutes for 1822 rows tcga data.
 * It takes 29 minutes for Ocd gwas significant snps data.
 */

package search.encodeucscgenome;

import intervaltree.Interval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import create.ChromosomeBasedFilesandOperations;
import enumtypes.ChromosomeName;

public class SearchChromosomeIntervalsUsingLinearSearch {
	
	
	public void createChromBaseSeachInputFilesBufferedWriters(List<BufferedWriter> bufList){
		
		try {
			FileWriter fileWriter1 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt" );
			FileWriter fileWriter2 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt" );
			FileWriter fileWriter3 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt" );
			FileWriter fileWriter4 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt" );
			FileWriter fileWriter5 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt" );
			FileWriter fileWriter6 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt" );
			FileWriter fileWriter7 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt" );
			FileWriter fileWriter8 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt" );
			FileWriter fileWriter9 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt" );
			FileWriter fileWriter10 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt" );
			FileWriter fileWriter11 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt" );
			FileWriter fileWriter12 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt" );
			FileWriter fileWriter13 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt" );
			FileWriter fileWriter14 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt" );
			FileWriter fileWriter15 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt" );
			FileWriter fileWriter16 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt" );
			FileWriter fileWriter17 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt" );
			FileWriter fileWriter18 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt" );
			FileWriter fileWriter19 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt" );
			FileWriter fileWriter20 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt" );
			FileWriter fileWriter21 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt" );
			FileWriter fileWriter22 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt" );
			FileWriter fileWriter23 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt" );
			FileWriter fileWriter24 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt" );
			
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
			
			bufList.add(bufferedWriter1);
			bufList.add(bufferedWriter2);
			bufList.add(bufferedWriter3);
			bufList.add(bufferedWriter4);
			bufList.add(bufferedWriter5);
			bufList.add(bufferedWriter6);
			bufList.add(bufferedWriter7);
			bufList.add(bufferedWriter8);
			bufList.add(bufferedWriter9);
			bufList.add(bufferedWriter10);
			bufList.add(bufferedWriter11);
			bufList.add(bufferedWriter12);
			bufList.add(bufferedWriter13);
			bufList.add(bufferedWriter14);
			bufList.add(bufferedWriter15);
			bufList.add(bufferedWriter16);
			bufList.add(bufferedWriter17);
			bufList.add(bufferedWriter18);
			bufList.add(bufferedWriter19);
			bufList.add(bufferedWriter20);
			bufList.add(bufferedWriter21);
			bufList.add(bufferedWriter22);
			bufList.add(bufferedWriter23);
			bufList.add(bufferedWriter24);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeChromBaseSearchInputFile(ChromosomeName chromName, String strLine, List<BufferedWriter> bufList){
		try {
			
			if (chromName.isCHROMOSOME1()){
					bufList.get(0).write(strLine + "\n");
					bufList.get(0).flush();		
			} else 	if (chromName.isCHROMOSOME2()){
				bufList.get(1).write(strLine + "\n");
				bufList.get(1).flush();		
			}else 	if (chromName.isCHROMOSOME3()){
				bufList.get(2).write(strLine + "\n");
				bufList.get(2).flush();		
			}else 	if (chromName.isCHROMOSOME4()){
				bufList.get(3).write(strLine + "\n");
				bufList.get(3).flush();		
			}else 	if (chromName.isCHROMOSOME5()){
				bufList.get(4).write(strLine + "\n");
				bufList.get(4).flush();		
			}else 	if (chromName.isCHROMOSOME6()){
				bufList.get(5).write(strLine + "\n");
				bufList.get(5).flush();		
			}else 	if (chromName.isCHROMOSOME7()){
				bufList.get(6).write(strLine + "\n");
				bufList.get(6).flush();		
			}else 	if (chromName.isCHROMOSOME8()){
				bufList.get(7).write(strLine + "\n");
				bufList.get(7).flush();		
			}else 	if (chromName.isCHROMOSOME9()){
				bufList.get(8).write(strLine + "\n");
				bufList.get(8).flush();		
			}else 	if (chromName.isCHROMOSOME10()){
				bufList.get(9).write(strLine + "\n");
				bufList.get(9).flush();		
			}else 	if (chromName.isCHROMOSOME11()){
				bufList.get(10).write(strLine + "\n");
				bufList.get(10).flush();		
			}else 	if (chromName.isCHROMOSOME12()){
				bufList.get(11).write(strLine + "\n");
				bufList.get(11).flush();		
			}else 	if (chromName.isCHROMOSOME13()){
				bufList.get(12).write(strLine + "\n");
				bufList.get(12).flush();		
			}else 	if (chromName.isCHROMOSOME14()){
				bufList.get(13).write(strLine + "\n");
				bufList.get(13).flush();		
			}else 	if (chromName.isCHROMOSOME15()){
				bufList.get(14).write(strLine + "\n");
				bufList.get(14).flush();		
			}else 	if (chromName.isCHROMOSOME16()){
				bufList.get(15).write(strLine + "\n");
				bufList.get(15).flush();		
			}else 	if (chromName.isCHROMOSOME17()){
				bufList.get(16).write(strLine + "\n");
				bufList.get(16).flush();		
			}else 	if (chromName.isCHROMOSOME18()){
				bufList.get(17).write(strLine + "\n");
				bufList.get(17).flush();		
			}else 	if (chromName.isCHROMOSOME19()){
				bufList.get(18).write(strLine + "\n");
				bufList.get(18).flush();		
			}else 	if (chromName.isCHROMOSOME20()){
				bufList.get(19).write(strLine + "\n");
				bufList.get(19).flush();		
			}else 	if (chromName.isCHROMOSOME21()){
				bufList.get(20).write(strLine + "\n");
				bufList.get(20).flush();		
			}else 	if (chromName.isCHROMOSOME22()){
				bufList.get(21).write(strLine + "\n");
				bufList.get(21).flush();		
			}else 	if (chromName.isCHROMOSOMEX()){
				bufList.get(22).write(strLine + "\n");
				bufList.get(22).flush();		
			}else 	if (chromName.isCHROMOSOMEY()){
				bufList.get(23).write(strLine + "\n");
				bufList.get(23).flush();		
			}else{
				GlanetRunner.appendLog("Unknown chromosome");
			}

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void partitionSearchInputFilePerChromName(String inputFileName, List<BufferedWriter> bufferedWriterList){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		int indexofFirstTab;
		ChromosomeName chromName;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
				writeChromBaseSearchInputFile(chromName,strLine,bufferedWriterList);
				
			} // End of While
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
	}
	
	public BufferedReader createBufferedReader(String fileName){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bufferedReader;
	}
	
//	Does the given region of interest intersect with the any tfbs
	public boolean intersect(int startPosition,int endPosition,int startPositionofRegionofInterest, int endPositionofRegionofInterest){
		
		int startPositionofIntersection = Math.max(startPosition, startPositionofRegionofInterest);
		int endPositionofIntersection = Math.min(endPosition, endPositionofRegionofInterest);
		
		if (startPositionofIntersection <= endPositionofIntersection)
			return true;
		else 
			return false;
	}

	
	public boolean overlaps(int low_x, int high_x, int low_y, int high_y ){
		if (( low_x <= high_y) && (low_y <= high_x))
			return true;
		else 
			return false;
	}
	
	public void searchforUcscRefSeqGenesintheGivenRegionofInterest(ChromosomeName chromName, Interval interval, BufferedWriter bw){
		
		int startPositionofRegionofInterest = interval.getLow();
		int endPositionofRegionofInterest = interval.getHigh();
		
		FileReader fileReader =null;
		BufferedReader br = null;
		
		String strLine;		
		
		int startPosition = 0;
		int endPosition = 0;
		String  ucscRefSeqGeneName;
		String  geneHugoSymbol;
		String intervalName;
		Integer geneEntrezId;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		
		fileReader = ChromosomeBasedFilesandOperations.getSortedRefSeqGenesFileReader(chromName);
				
		br = new BufferedReader(fileReader);
		 
		 
			try {
				
//				chrY	131384	221383	NR_027231	283981	5p3	+	LINC00685

				while ((strLine = br.readLine()) != null)   {
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab = strLine.indexOf('\t', indexofSixthTab+1);
					
					
					
					startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
//					Since the data is sorted with respect to start position field in ascending order
//					if start position of the region of interest is greater than the end position in the  Sorted_Chromosome_Base_Dnase_File
//					They can not intersect
//					Also there can not be any intersection for the rest of the data
					if (endPositionofRegionofInterest < startPosition)
						break;
					
//					if (intersect(startPosition,endPosition,startPositionofRegionofInterest, endPositionofRegionofInterest)){
					if (overlaps(startPosition,endPosition,startPositionofRegionofInterest, endPositionofRegionofInterest)){
						
//						write this dnase to output file

//						get the data
						
						ucscRefSeqGeneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						geneEntrezId = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
						intervalName = strLine.substring(indexofFifthTab+1, indexofSixthTab);
						geneHugoSymbol = strLine.substring(indexofSeventhTab+1);

						bw.write("ucscRefSeqGene" + "\t" + chromName.convertEnumtoString()+ "\t" +  startPosition + "\t" + endPosition + "\t" + ucscRefSeqGeneName+ "\t" + intervalName + "\t" + geneHugoSymbol+ "\t"+ geneEntrezId +"\n");
						bw.flush();

					}
					
				}
				
//				close the buffered reader
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	}

	
	public void searchforHistoneintheGivenRegionofInterest(ChromosomeName chromName, Interval interval, BufferedWriter bw){
		
		int startPositionofRegionofInterest = interval.getLow();
		int endPositionofRegionofInterest = interval.getHigh();
		
		FileReader fileReader =null;
		BufferedReader br = null;
		
		String strLine;		
		
		int startPosition = 0;
		int endPosition = 0;
		String histoneName;
		String  cellLineName;
		String fileName;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		
		
		try {
			if (chromName.isCHROMOSOME1()){
				//open sorted dnase file of Chromosome1
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR1_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME2()){
				//open sorted dnase file of Chromosome2
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR2_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME3()){
				//open sorted dnase file of Chromosome3
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR3_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME4()){
				//open sorted dnase file of Chromosome4
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR4_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME5()){
				//open sorted dnase file of Chromosome5
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR5_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME6()){
				//open sorted dnase file of Chromosome6
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR6_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME7()){
				//open sorted dnase file of Chromosome7
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR7_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME8()){
				//open sorted dnase file of Chromosome8
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR8_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME9()){
				//open sorted dnase file of Chromosome9
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR9_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME10()){
				//open sorted dnase file of Chromosome10
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR10_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME11()){
				//open sorted dnase file of Chromosome11
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR11_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME12()){
				//open sorted dnase file of Chromosome12
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR12_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME13()){
				//open sorted dnase file of Chromosome13
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR13_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME14()){
				//open sorted dnase file of Chromosome14
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR14_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME15()){
				//open sorted dnase file of Chromosome15
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR15_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME16()){
				//open sorted dnase file of Chromosome16
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR16_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME17()){
				//open sorted dnase file of Chromosome17
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR17_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME18()){
				//open sorted dnase file of Chromosome18
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR18_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME19()){
				//open sorted dnase file of Chromosome19
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR19_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME20()){
				//open sorted dnase file of Chromosome20
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR20_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME21()){
				//open sorted dnase file of Chromosome21
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR21_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOME22()){
				//open sorted dnase file of Chromosome22
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHR22_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOMEX()){
				//open sorted dnase file of ChromosomeX
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHRX_HISTONE_FILENAME);
				
			}else if (chromName.isCHROMOSOMEY()){
				//open sorted dnase file of ChromosomeY
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY,Commons.SORTED_CHRY_HISTONE_FILENAME);
				
			}	
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 br = new BufferedReader(fileReader);
		 
		 
			try {
				
//				chrY	2649445	2650904	H3k27ac	Hepg2	wgEncodeBroadHistoneHepg2H3k27acStdAln.narrowPeak

				while ((strLine = br.readLine()) != null)   {
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
															
					startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
//					Since the data is sorted with respect to start position field in ascending order
//					if start position of the region of interest is greater than the end position in the  Sorted_Chromosome_Base_Dnase_File
//					They can not intersect
//					Also there can not be any intersection for the rest of the data
					if (endPositionofRegionofInterest < startPosition)
						break;
					
					if (overlaps(startPosition,endPosition,startPositionofRegionofInterest, endPositionofRegionofInterest)){
//						write this dnase to output file

//						get the data
						histoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
						fileName = strLine.substring(indexofFifthTab+1);

						bw.write("histone" + "\t" + chromName+ "\t"  + startPosition + "\t" + endPosition + "\t" + histoneName+ "\t" + cellLineName + "\t" + fileName +"\n");
						bw.flush();

					}
					
				}
				
//				close the buffered reader
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	}

	
	public void searchforTfbsintheGivenRegionofInterest(ChromosomeName chromName, Interval interval, BufferedWriter bw){
		
		int startPositionofRegionofInterest = interval.getLow();
		int endPositionofRegionofInterest = interval.getHigh();
		
		FileReader fileReader =null;
		BufferedReader br = null;
		
		String strLine;		
		
		int startPosition = 0;
		int endPosition = 0;
		String tfbsName;
		String  cellLineName;
		String fileName;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		
		try {
			if (chromName.isCHROMOSOME1()){
				//open sorted dnase file of Chromosome1
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR1_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME2()){
				//open sorted dnase file of Chromosome2
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR2_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME3()){
				//open sorted dnase file of Chromosome3
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR3_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME4()){
				//open sorted dnase file of Chromosome4
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR4_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME5()){
				//open sorted dnase file of Chromosome5
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR5_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME6()){
				//open sorted dnase file of Chromosome6
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR6_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME7()){
				//open sorted dnase file of Chromosome7
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR7_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME8()){
				//open sorted dnase file of Chromosome8
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR8_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME9()){
				//open sorted dnase file of Chromosome9
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR9_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME10()){
				//open sorted dnase file of Chromosome10
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR10_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME11()){
				//open sorted dnase file of Chromosome11
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR11_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME12()){
				//open sorted dnase file of Chromosome12
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR12_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME13()){
				//open sorted dnase file of Chromosome13
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR13_TFBS_FILENAME);
			
			}else if (chromName.isCHROMOSOME14()){
				//open sorted dnase file of Chromosome14
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR14_TFBS_FILENAME);
			
			}else if (chromName.isCHROMOSOME15()){
				//open sorted dnase file of Chromosome15
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR15_TFBS_FILENAME);
			
			}else if (chromName.isCHROMOSOME16()){
				//open sorted dnase file of Chromosome16
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR16_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME17()){
				//open sorted dnase file of Chromosome17
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR17_TFBS_FILENAME);
			
			}else if (chromName.isCHROMOSOME18()){
				//open sorted dnase file of Chromosome18
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR18_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME19()){
				//open sorted dnase file of Chromosome19
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR19_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME20()){
				//open sorted dnase file of Chromosome20
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR20_TFBS_FILENAME);
			
			}else if (chromName.isCHROMOSOME21()){
				//open sorted dnase file of Chromosome21
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR21_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOME22()){
				//open sorted dnase file of Chromosome22
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHR22_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOMEX()){
				//open sorted dnase file of ChromosomeX
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHRX_TFBS_FILENAME);
				
			}else if (chromName.isCHROMOSOMEY()){
				//open sorted dnase file of ChromosomeY
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.SORTED_CHRY_TFBS_FILENAME);
				
			}	
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 br = new BufferedReader(fileReader);
		 
		 
			try {
				
//				chrY	2649586	2649842	Pol2	Hepg2	spp.optimal.wgEncodeHaibTfbsHepg2Pol2Pcr2xAlnRep0_VS_wgEncodeHaibTfbsHepg2ControlPcr2xAlnRep0.narrowPeak

				while ((strLine = br.readLine()) != null)   {
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);					
					indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
					
					
					startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
//					Since the data is sorted with respect to start position field in ascending order
//					if start position of the region of interest is greater than the end position in the  Sorted_Chromosome_Base_Dnase_File
//					They can not intersect
//					Also there can not be any intersection for the rest of the data
					if (endPositionofRegionofInterest < startPosition)
						break;
					
					if (overlaps(startPosition,endPosition,startPositionofRegionofInterest, endPositionofRegionofInterest)){
//						write this dnase to output file

//						get the data
						tfbsName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
						fileName = strLine.substring(indexofFifthTab+1);

						bw.write("tfbs" + "\t" + chromName+ "\t"  + startPosition + "\t" + endPosition + "\t" + tfbsName+ "\t" + cellLineName + "\t" + fileName +"\n");
						bw.flush();

					}
					
				}
				
//				close the buffered reader
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	}

	
//	search whether there is any dnase in the region of interest
	public void	searchforDnaseintheGivenRegionofInterest(ChromosomeName chromName, Interval interval, BufferedWriter bw){
		
		int startPositionofRegionofInterest = interval.getLow();
		int endPositionofRegionofInterest = interval.getHigh();
		
		FileReader fileReader =null;
		BufferedReader br = null;
		
		String strLine;		
		
		int startPosition = 0;
		int endPosition = 0;
		String  cellLineName;
		String fileName;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		
		try {
			if (chromName.isCHROMOSOME1()){
				//open sorted dnase file of Chromosome1
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR1_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME2()){
				//open sorted dnase file of Chromosome2
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR2_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME3()){
				//open sorted dnase file of Chromosome3
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR3_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME4()){
				//open sorted dnase file of Chromosome4
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR4_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME5()){
				//open sorted dnase file of Chromosome5
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR5_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME6()){
				//open sorted dnase file of Chromosome6
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR6_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME7()){
				//open sorted dnase file of Chromosome7
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR7_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME8()){
				//open sorted dnase file of Chromosome8
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR8_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME9()){
				//open sorted dnase file of Chromosome9
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR9_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME10()){
				//open sorted dnase file of Chromosome10
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR10_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME11()){
				//open sorted dnase file of Chromosome11
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR11_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME12()){
				//open sorted dnase file of Chromosome12
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR12_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME13()){
				//open sorted dnase file of Chromosome13
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR13_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME14()){
				//open sorted dnase file of Chromosome14
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR14_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME15()){
				//open sorted dnase file of Chromosome15
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR15_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME16()){
				//open sorted dnase file of Chromosome16
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR16_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME17()){
				//open sorted dnase file of Chromosome17
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR17_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME18()){
				//open sorted dnase file of Chromosome18
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR18_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME19()){
				//open sorted dnase file of Chromosome19
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR19_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME20()){
				//open sorted dnase file of Chromosome20
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR20_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME21()){
				//open sorted dnase file of Chromosome21
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR21_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOME22()){
				//open sorted dnase file of Chromosome22
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHR22_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOMEX()){
				//open sorted dnase file of ChromosomeX
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHRX_DNASE_FILENAME);
				
			}else if (chromName.isCHROMOSOMEY()){
				//open sorted dnase file of ChromosomeY
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.SORTED_CHRY_DNASE_FILENAME);
				
			}	
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 br = new BufferedReader(fileReader);
		 
		 
			try {
				
//				Data in the Sorted_Chromosome_Base_Dnase_File is in ascending order for start position field
//				a custom line from Sorted Chromosome Base Dnase File
//				chr4	10000	10150	AG04450	AG04450-DS12255.peaks.fdr0.01.hg19.bed
//				chrY	2649600	2649750	CACO2	CACO2-DS8416.peaks.fdr0.01.hg19.bed

				while ((strLine = br.readLine()) != null)   {
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
					
					
					startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
//					Since the data is sorted with respect to start position field in ascending order
//					if start position of the region of interest is greater than the end position in the  Sorted_Chromosome_Base_Dnase_File
//					They can not intersect
//					Also there can not be any intersection for the rest of the data
					if (endPositionofRegionofInterest < startPosition)
						break;
					
					if (overlaps(startPosition,endPosition,startPositionofRegionofInterest, endPositionofRegionofInterest)){
//						write this dnase to output file

//						get the data
						cellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
						fileName = strLine.substring(indexofFourthTab+1);

						bw.write("dnase" + "\t" + chromName.convertEnumtoString()+ "\t"  + startPosition + "\t" + endPosition + "\t" + cellLineName + "\t" + fileName +"\n");
						bw.flush();

					}
					
				}
				
//				close the buffered reader
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	}
	
	
	public void searchUcscRefSeqGenes(BufferedReader bufferedReader, ChromosomeName chromName, BufferedWriter bufferedWriter){
		
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
				
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();				
				searchforUcscRefSeqGenesintheGivenRegionofInterest(chromName, interval,bufferedWriter);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
		
	}


	public void searchHistone(BufferedReader bufferedReader, ChromosomeName chromName, BufferedWriter bufferedWriter){
		
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		 
		int low;
		int high;
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
				
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();				
				searchforHistoneintheGivenRegionofInterest(chromName, interval,bufferedWriter);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
		
	}

	
	public void searchTfbs(BufferedReader bufferedReader, ChromosomeName chromName, BufferedWriter bufferedWriter){
		
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
								
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();												
				searchforTfbsintheGivenRegionofInterest(chromName, interval,bufferedWriter);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
		
	}
	
	public void searchDnase(BufferedReader bufferedReader, ChromosomeName chromName, BufferedWriter bufferedWriter){
		
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
				
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();								
				searchforDnaseintheGivenRegionofInterest(chromName, interval, bufferedWriter);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
		
	}
	
	public void searchEachChromBaseInputFile(String outputFileName){
		
		BufferedReader bufferedReader  =null;
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 1; i<=24 ; i++ ){
			
			switch(i){
			case 1: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME1, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME1, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME1, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME1, bufferedWriter);
				break;

			case 2: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME2, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME2, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME2, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME2, bufferedWriter);
				break;

			case 3: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME3, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME3, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME3, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME3, bufferedWriter);
				break;

			case 4: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME4, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME4, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME4, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME4, bufferedWriter);
				break;
				
			case 5: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME5, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME5, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME5, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME5, bufferedWriter);
				break;
				
			case 6: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME6, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME6, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME6, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME6, bufferedWriter);
				break;		
				
			case 7: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME7, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME7, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME7, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME7, bufferedWriter);
				break;
				
			case 8: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME8, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME8, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME8, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME8, bufferedWriter);
				break;		
				
			case 9: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME9, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME9, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME9, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME9, bufferedWriter);
				break;
			
			case 10: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME10, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME10, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME10, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME10, bufferedWriter);
				break;
			
			case 11: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME11, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME11, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME11, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME11, bufferedWriter);
				break;
				
			case 12: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME12, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME12, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME12, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME12, bufferedWriter);
				break;
				
			case 13: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME13, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME13, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME13, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME13, bufferedWriter);
				break;
				
			case 14: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME14, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME14, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME14, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME14, bufferedWriter);
				break;
				
			case 15: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME15, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME15, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME15, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME15, bufferedWriter);
				break;
				
			case 16: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME16, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME16, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME16, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME16, bufferedWriter);
				break;
				
			case 17: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME17, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME17, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME17, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME17, bufferedWriter);
				break;
				
			case 18: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME18, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME18, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME18, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME18, bufferedWriter);
				break;
				
			case 19: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME19, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME19, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME19, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME19, bufferedWriter);
				break;
				
			case 20: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME20, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME20, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME20, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME20, bufferedWriter);
				break;
				
			case 21: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME21, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME21, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME21, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME21, bufferedWriter);
				break;
				
			case 22: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOME22, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOME22, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOME22, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOME22, bufferedWriter);
				break;
				
				
			case 23: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOMEX, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOMEX, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOMEX, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOMEX, bufferedWriter);
				break;
				

			case 24: 			
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
				searchDnase(bufferedReader, ChromosomeName.CHROMOSOMEY, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
				searchTfbs(bufferedReader, ChromosomeName.CHROMOSOMEY, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
				searchHistone(bufferedReader, ChromosomeName.CHROMOSOMEY, bufferedWriter);
				
				bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
				searchUcscRefSeqGenes(bufferedReader,  ChromosomeName.CHROMOSOMEY, bufferedWriter);
				break;
		}// End of Switch 						
	}// End of For
		
		try {
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void closeBufferedWriterList(List<BufferedWriter> bufList){
		for(int i = 0; i<bufList.size(); i++){
			try {
				bufList.get(i).close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public static void main(String[] args){
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		SearchChromosomeIntervalsUsingLinearSearch searchInterval = new SearchChromosomeIntervalsUsingLinearSearch();		
		
		searchInterval.createChromBaseSeachInputFilesBufferedWriters(bufferedWriterList);		
//		searchInterval.partitionSearchInputFilePerChromName(Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS,bufferedWriterList);
//		searchInterval.partitionSearchInputFilePerChromName(Commons.POSITIVE_CONTROL_OUTPUT_FILE_NAME_WITHOUT_OVERLAPS,bufferedWriterList);
		searchInterval.closeBufferedWriterList(bufferedWriterList);
		
		searchInterval.searchEachChromBaseInputFile(Commons.SEARCH_USING_LINEAR_SEARCH_OUTPUT_FILE);
	
	}
	
	
}
