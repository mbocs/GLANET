/**
 * @author burcakotlu
 * @date Sep 28, 2014 
 * @time 6:16:48 PM
 */
package userdefined;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;

/**
 * This code generates GO annotations input file
 * for testing purposes (testing data for user defined geneset facility)
 * 
 */
public class GOAnnotationsInputFileGeneration {

	
	public static void readGOAnnotatiosInputFileAndWriteGOAnnotationsOutputFile(String inputFileName, String outputFileName){
		
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		
		String GO_ID;
		String geneSymbol;
		int numberofLinesRead =0;
		int numberofLinesWritten =0;
		int numberofCommentLines = 0;
		
		
		try {
			FileReader fileReader = FileOperations.createFileReader(inputFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			FileWriter fileWriter = FileOperations.createFileWriter(outputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine= bufferedReader.readLine())!=null){
				
				//Skip comment lines
				if (!(strLine.startsWith("!"))){
					
					indexofFirstTab =  strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
					
					geneSymbol = strLine.substring(indexofSecondTab+1,indexofThirdTab);
					GO_ID = strLine.substring(indexofFourthTab+1,indexofFifthTab);
					
					bufferedWriter.write(GO_ID + "\t" + geneSymbol + System.getProperty("line.separator"));
					
					numberofLinesWritten++;		
				}//End of IF
				else{
					numberofCommentLines++;
				}
				
				numberofLinesRead++;
				
			}//End of WHILE
			
			System.out.println("Number of lines written: " + numberofLinesWritten);
			System.out.println("Number of comment lines: " + numberofCommentLines);
			System.out.println("Number of lines read: " + numberofLinesRead);
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String GOAnnotationsInputFileName = "E:\\DOKTORA_DATA\\GO\\gene_association.goa_ref_human";
		String GOAnnotationsOutputFileName =  "E:\\DOKTORA_DATA\\GO\\GO_gene_associations_human_ref.txt";
		
		
		readGOAnnotatiosInputFileAndWriteGOAnnotationsOutputFile(GOAnnotationsInputFileName,GOAnnotationsOutputFileName);

	}

}
