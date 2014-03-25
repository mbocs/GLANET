package processinputdata;

import java.awt.peer.SystemTrayPeer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jaxbxjctool.AugmentationofGivenRsIds;
import auxiliary.FileOperations;

import common.Commons;
/**
 * @author burcakotlu
 * @date Mar 24, 2014 
 * @time 1:58:07 PM
 */

/**
 * 
 */
public class InputDataProcess {
	
	static Collection nonOverLap(Collection bigger, Collection smaller) {
		   Collection result = bigger;
		   result.removeAll(smaller);
		   return result;
	}
	
	//eutil efetch returns 0-based coordinates for given dbSNP ids 
	public static void 	readDBSNPIDs(String inputFileName, String outputFileName){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
					
		String rsId = null;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);			
		
			AugmentationofGivenRsIds app = new AugmentationofGivenRsIds();
						
			//for debug
			List<String> searched = new ArrayList<String>();
			List<String> found = new ArrayList<String>();
			//for debug
			
			while((rsId = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(rsId.startsWith("#"))){
					app.run(rsId, bufferedWriter,searched,found);
				}//End of if not comment line							
			}
			
			//for debug start
			System.out.println("searched size:" + "\t" + searched.size() + "\t" + "found size:" + "\t" +  found.size());
			List<String> difference = (List<String>) nonOverLap(searched,found);
			//for debug end
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//@todo remove overlaps if any exists
	
		
	}

	//BED format is 0-based, end is exclusive
	//In BED format, bed lines have three required fileds and nine additional optional fields.
	//chrom chr3 chrY chr2_random
	//chromStart first base in a chromosome is numbered 0.
	//chromEnd is exclusive, fisrt 100 bases of a chromosome are defined as chromStart=0 chromEnd=100, and 
	//span the bases numbered 0-99.
	public static void 	readBEDFile(String inputFileName, String outputFileName){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String chrName;
		int start,exclusiveEnd, inclusiveEnd;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(strLine.startsWith("#"))){
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					
					chrName = strLine.substring(0, indexofFirstTab);
					start = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
					exclusiveEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
					
					//get the inclusive end
					inclusiveEnd = exclusiveEnd-1;
					
					bufferedWriter.write(chrName + "\t" + start +  "\t" + inclusiveEnd + "\n");
				}//End of if not comment line	
			}
			
			bufferedReader.close();
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//@todo remove overlaps if any exists
		
			
	}
	
	//GFF3 format is 1-based, end is inclusive
	//example GFF3 input line
	//chrX	experiment	SNP	146993388	146993388	.	-	0	cellType=HeLA
	public static void 	readGFF3File(String inputFileName, String outputFileName){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		
		String chrName;
		int oneBasedStart, oneBasedInclusiveEnd;
		int zeroBasedStart, zeroBasedInclusiveEnd;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if(!(strLine.startsWith("#"))){
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
					
					chrName = strLine.substring(0, indexofFirstTab);
					oneBasedStart = Integer.parseInt(strLine.substring(indexofThirdTab+1,indexofFourthTab));
					oneBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofFourthTab+1,indexofFifthTab));
					
					//get the 0-based start
					zeroBasedStart = oneBasedStart-1;
					
					//get the 0-based inclusive end
					zeroBasedInclusiveEnd = oneBasedInclusiveEnd -1;
					
					bufferedWriter.write(chrName + "\t" + zeroBasedStart +  "\t" + zeroBasedInclusiveEnd + "\n");
				}//End of if not comment line
				
			}
			
			bufferedReader.close();
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//@todo remove overlaps if any exists
	

	}
	
	//0-based coordinates, start and end are inclusive.
	//1 100 200
	//1 100
	//chr1 100
	//chr1 100 200
	public static void 	readZeroBasedCoordinates(String inputFileName, String outputFileName){
		
		//read the file line by line
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//write to output file line by line 
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		int indexofColon;
		int indexofDot;
		int indexofHyphen;
		
		String chrName = null;
		int zeroBasedStart = 0;
		int zeroBasedInclusiveEnd = 0;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//skip comment lines
				if(!(strLine.startsWith("#"))){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					
					indexofColon = strLine.indexOf(':');
					indexofDot = strLine.indexOf('.');
					indexofHyphen = strLine.indexOf('-');
				
					//19:12995239..12998702
					//19:12995239-12998702
					//chr19:12995239-12998702				
					//chr11:524690..5247000
					//there is no tab
					if(indexofColon>=0){
						chrName = strLine.substring(0, indexofColon);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						
						if (indexofHyphen>=0){
							zeroBasedStart = Integer.parseInt(strLine.substring(indexofColon+1, indexofHyphen));
							zeroBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofHyphen+1).trim());
						}else if (indexofDot>=0){
							zeroBasedStart = Integer.parseInt(strLine.substring(indexofColon+1, indexofDot));
							zeroBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofDot+2).trim());
						}
						//19:12995239
						//chr19:12995239				
						else {
							zeroBasedStart = Integer.parseInt(strLine.substring(indexofColon+1).trim());
							zeroBasedInclusiveEnd = zeroBasedStart;
						}
					}
									
					//chrX 100 200
					//X 100 200
					else if (indexofSecondTab>=0 && indexofFirstTab>=0){
						
						chrName = strLine.substring(0, indexofFirstTab);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						zeroBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
						zeroBasedInclusiveEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1).trim());
					}
					//chrX 100
					//X 100
					else if (indexofFirstTab>=0){
						
						chrName = strLine.substring(0, indexofFirstTab);
						
						if(!(chrName.startsWith("chr"))){
							//add "chr"
							chrName = "chr" + chrName;
						}
						
						zeroBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1).trim());
						zeroBasedInclusiveEnd = zeroBasedStart;
					
					}
					
					
					
					bufferedWriter.write(chrName + "\t" + zeroBasedStart +  "\t" + zeroBasedInclusiveEnd + "\n");
				}//End of if not comment line
								
			}
			
			bufferedReader.close();
			bufferedWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//@todo remove overlaps if any exists


	}
	
	public static void processInputData(String inputFileName,String inputFileFormat, String outputFileName){
		
		if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readDBSNPIDs(inputFileName,outputFileName);	
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE)){
			readBEDFile(inputFileName,outputFileName);
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readGFF3File(inputFileName,outputFileName);
		}else if (inputFileFormat.equals(Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE)){
			readZeroBasedCoordinates(inputFileName,outputFileName);
		}
	}
	
	public static void main(String[] args) {
		//Get the input file
		String inputFileName = args[0];
//		inputFileName = "C:\\Users\\burcakotlu\\GLANET\\AnnotationData\\OCD_GWAS_SNP\\OCD_GWAS_SIGNIFICANT_SNP_RSIDs.txt";
//		inputFileName = "C:\\Users\\burcakotlu\\GLANET\\AnnotationData\\OCD_GWAS_SNP\\OCD_GWAS_SIGNIFICANT_SNP_RSIDs_TEST.txt";
		

		inputFileName = "C:\\Users\\burcakotlu\\GLANET\\TEST_INPUT_DATA\\Test_dbSNP_ids.txt";
//		inputFileName = "C:\\Users\\burcakotlu\\GLANET\\TEST_INPUT_DATA\\Test_BED_format.txt";
//		inputFileName = "C:\\Users\\burcakotlu\\GLANET\\TEST_INPUT_DATA\\Test_GFF3_format.txt";
//		inputFileName = "C:\\Users\\burcakotlu\\GLANET\\TEST_INPUT_DATA\\Test_0_based_coordinates.txt";
		
		//Get the input file format
		String inputFileFormat = args[1];
		
		inputFileFormat = Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
//		inputFileFormat = Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE;
//		inputFileFormat = Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
//		inputFileFormat = Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE;
				
		String outputFileName = Commons.PROCESSED_INPUT_FILE; 
		
		//Read input data 
		//Process input data
		//Write output data
		processInputData(inputFileName,inputFileFormat,outputFileName);
				

	}

}