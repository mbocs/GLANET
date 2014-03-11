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

import common.Commons;

import empiricalpvalues.GCCharArray;
import empiricalpvalues.InputLine;

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
			System.out.println("input line's high exceeds hg19 chromsome size");
		}
		
		givenInputLine.setGcContent(gcContent);
		
	}
	
	public static float differenceofGCs(InputLine inputLine1, InputLine inputLine2){
		return Math.abs(inputLine1.getGcContent() - inputLine2.getGcContent());
	}
	
	
	public static void fillChromBasedGCArrayfromFastaFile(String gcFastaFileName, GCCharArray gcArray){
		FileReader fileReader;
		BufferedReader bufferedReader;
		int numberofCharactersRead;
		
		char[] cbuf = new char[10000];
		char ch;
		int nthBase =0;
		String strLine;
		
		try {
			fileReader = new FileReader(gcFastaFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//skip first informative line of fasta file
			strLine=bufferedReader.readLine();
			
			//check whether fasta file starts with > greater character
			if (!strLine.startsWith(">")){
				System.out.println("Fasta file does not start with > character.");
			}
			
			while((numberofCharactersRead = bufferedReader.read(cbuf))!=-1){
				
//				System.out.println("number of characters read: " + numberofCharactersRead);
				
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
			
			
			System.out.println("nthBase must be written once: " + nthBase + " gcCharArray construction has ended.");
			
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
				e.printStackTrace();
		}
		
		
		
		
	}
		
	public static  void fillChromBasedGCArray(String chromName, GCCharArray gcArray){
		
		switch(chromName){
			case Commons.CHROMOSOME1:   fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR1_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME2:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR2_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME3:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR3_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME4:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR4_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME5:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR5_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME6:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR6_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME7:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR7_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME8:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR8_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME9:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR9_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME10:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR10_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME11:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR11_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME12:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR12_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME13:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR13_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME14:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR14_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME15:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR15_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME16:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR16_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME17:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR17_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME18:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR18_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME19:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR19_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME20:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR20_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME21:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR21_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOME22:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHR22_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOMEX:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHRX_FASTA_FILE,gcArray);
										break;
			case Commons.CHROMOSOMEY:  fillChromBasedGCArrayfromFastaFile(Commons.GC_HG19_CHRY_FASTA_FILE,gcArray);
										break;
							
		}
		
		
	}
	
	public static void main(String[] args) {
		InputLine givenInputLine= new InputLine(Commons.CHROMOSOME1, 3500000, 4000000);
		String chromName = Commons.CHROMOSOME1;
				
		GCCharArray gcArray= new GCCharArray(250000000);
		
		
		fillChromBasedGCArray(chromName,gcArray);
		calculateGCofInterval(givenInputLine,gcArray);
	
		System.out.println("Given input line's gc content: " + givenInputLine.getGcContent());
	}

}
