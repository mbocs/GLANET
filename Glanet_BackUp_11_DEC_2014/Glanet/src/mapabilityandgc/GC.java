/**
 * @author Burcak Otlu
 * Aug 8, 2013
 * 11:06:24 PM
 * 2013
 *
 *
 *	This class will calculate the gc of an given interval
 * 
 */
package mapabilityandgc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ui.GlanetRunner;

import common.Commons;

import enrichment.GCCharArray;
import enrichment.InputLine;
import enumtypes.ChromosomeName;

public class GC {
	
	static GCCharArray gcCharArray; 
	
	
	
	public static GCCharArray getGcCharArray() {
		return gcCharArray;
	}

	public static void setGcCharArray(GCCharArray gcCharArray) {
		GC.gcCharArray = gcCharArray;
	}

	public GC() {
		super();
		// TODO Auto-generated constructor stub
	}

	//todo
    //for variance calculation among functional elements' gc values
	public static float calculateGC(int low, int high, GCCharArray gcCharArray){
			
		float gcContent = 0;
		
		int value;
		int length = high-low+1;
		
		for(int i = low ; i<=high; i++){
			//ascii 0 = 48 decimal
			//ascii 1 = 49 decimal
			value = gcCharArray.getGcArray()[i]-48;
			gcContent = gcContent + value;
		}
		gcContent = gcContent/length;
		
		return gcContent;
	}
	
	
	public static  void calculateGCofInterval(InputLine givenInputLine,GCCharArray gcArray){
		int low = givenInputLine.getLow();
		int high = givenInputLine.getHigh();
		int length = givenInputLine.getLength();
		int value;
		float gcContent =0;

		if (high<gcArray.getGcArray().length){
			for(int i = low ; i<=high; i++){
				//ascii 0 = 48 decimal
				//ascii 1 = 49 decimal
				value = gcArray.getGcArray()[i]-48;
				gcContent = gcContent + value;
			}
			gcContent = gcContent/length;
		}else{
			GlanetRunner.appendLog("input line's high exceeds hg19 chromsome size");
		}
		
		givenInputLine.setGcContent(gcContent);
		
	}
	
	public static float differenceofGCs(InputLine inputLine1, InputLine inputLine2){
		return Math.abs(inputLine1.getGcContent() - inputLine2.getGcContent());
	}
	
	
	public static void fillChromBasedGCArrayfromFastaFile(String dataFolder,String gcFastaFileName, GCCharArray gcArray){
		FileReader fileReader;
		BufferedReader bufferedReader;
		int numberofCharactersRead;
		
		char[] cbuf = new char[10000];
		char ch;
		int nthBase =0;
		String strLine;
		
		try {
			fileReader = new FileReader(dataFolder +gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//skip first informative line of fasta file
			strLine=bufferedReader.readLine();
			
			//check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")){
				GlanetRunner.appendLog("Fasta file does not start with > character.");
			}
			
			while((numberofCharactersRead = bufferedReader.read(cbuf))!=-1){
				
//				GlanetRunner.appendLog("number of characters read: " + numberofCharactersRead);
				
				for(int i =0; i<numberofCharactersRead ; i++){
					ch = cbuf[i];
			
					if(		((ch == Commons.NUCLEIC_ACID_UPPER_CASE_A) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_A)) 	||
						    ((ch == Commons.NUCLEIC_ACID_UPPER_CASE_T) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_T))	||
						    ((ch == Commons.NUCLEIC_ACID_UPPER_CASE_N) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_N))) {
						
								gcArray.getGcArray()[nthBase++] = '0';
//						 		gcArray[nthBase++] = '0';
						 		
					}else if (	((ch == Commons.NUCLEIC_ACID_UPPER_CASE_G) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_G)) ||
					    		((ch == Commons.NUCLEIC_ACID_UPPER_CASE_C) || (ch==Commons.NUCLEIC_ACID_LOWER_CASE_C))){
								
								gcArray.getGcArray()[nthBase++] = '1';
//						 		gcArray[nthBase++] = '1';
					}
						
				}//end of for
			}//end of while
			
			
			GlanetRunner.appendLog("nthBase must be written once: " + nthBase + " gcCharArray construction has ended.");
			
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		
		
		
	}
		
	public static  void fillChromBasedGCArray(String dataFolder,ChromosomeName chromName, GCCharArray gcArray){
		
		switch(chromName){
			case CHROMOSOME1:   fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR1_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME2:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR2_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME3:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR3_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME4:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR4_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME5:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR5_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME6:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR6_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME7:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR7_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME8:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR8_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME9:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR9_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME10:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR10_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME11:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR11_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME12:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR12_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME13:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR13_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME14:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR14_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME15:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR15_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME16:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR16_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME17:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR17_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME18:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR18_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME19:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR19_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME20:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR20_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME21:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR21_FASTA_FILE,gcArray);
										break;
			case CHROMOSOME22:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHR22_FASTA_FILE,gcArray);
										break;
			case CHROMOSOMEX:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHRX_FASTA_FILE,gcArray);
										break;
			case CHROMOSOMEY:  fillChromBasedGCArrayfromFastaFile(dataFolder,Commons.GC_HG19_CHRY_FASTA_FILE,gcArray);
										break;
							
		}
		
		
	}
	
	//args[0] must have input file name with folder
	//args[1] must have GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2] must have Input File Format		
	//args[3] must have Number of Permutations	
	//args[4] must have False Discovery Rate (ex: 0.05)
	//args[5] must have Generate Random Data Mode (with GC and Mapability/without GC and Mapability)
	//args[6] must have writeGeneratedRandomDataMode checkBox
	//args[7] must have writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//args[8] must have writePermutationBasedAnnotationResultMode checkBox
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
	
		InputLine givenInputLine= new InputLine(ChromosomeName.CHROMOSOME1, 3500000, 4000000);
		ChromosomeName chromName = ChromosomeName.CHROMOSOME1;
				
		GCCharArray gcArray= new GCCharArray(250000000);
		
		
		fillChromBasedGCArray(dataFolder,chromName,gcArray);
		calculateGCofInterval(givenInputLine,gcArray);
	
		GlanetRunner.appendLog("Given input line's gc content: " + givenInputLine.getGcContent());
	}

}
