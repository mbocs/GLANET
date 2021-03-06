/**
 * @author burcakotlu
 * @date Apr 2, 2014 
 * @time 5:01:25 PM
 */
package jaxbxjctool;

import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.EnrichmentType;

/**
 * 
 */
public class GenerationofSequencesandMatricesforGivenIntervals {
	
	final static Logger logger = Logger.getLogger(GenerationofSequencesandMatricesforGivenIntervals.class);
	
	
	
	public static void constructLogoMatricesfromEncodeMotifs(String dataFolder,String encodeMotifsInputFileName,Map<String,String>  tfName2LogoMatrices){
		
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
					
		//Attention order is important!
		//Order is ACGT
		String tfName = null;
		
		
		try {
				fileReader = new FileReader(dataFolder +  encodeMotifsInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
//					Encode-motif matrix
//					Order is ACGT					
//					>NFKB_disc1 NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
//					K 0.000000 0.000000 0.737500 0.262500
//					G 0.000000 0.000000 1.000000 0.000000
//					G 0.000000 0.000000 1.000000 0.000000
//					R 0.570833 0.000000 0.429167 0.000000
//					A 0.837500 0.158333 0.004167 0.000000
//					W 0.395833 0.000000 0.000000 0.604167
//					T 0.000000 0.004167 0.000000 0.995833
//					Y 0.000000 0.383333 0.000000 0.616667
//					C 0.000000 1.000000 0.000000 0.000000
//					C 0.000000 1.000000 0.000000 0.000000
					
					
					if (strLine.startsWith(">")){
						
						//start reading from first character, skip '>' character
						tfName = strLine.substring(1);
						
						if (tfName2LogoMatrices.get(tfName)==null){
							tfName2LogoMatrices.put(tfName, strLine+ System.getProperty("line.separator"));
							
						}else{
							tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName)+ strLine + System.getProperty("line.separator"));	
						}
							
					}//End of if
						
						
						
					else{
						tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName)+ strLine+ System.getProperty("line.separator"));
					}
						
				}//end of while
				

				
				bufferedReader.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void constructPfmMatricesfromEncodeMotifs(String dataFolder,String encodeMotifsInputFileName,Map<String,String> tfName2PfmMatrices){
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
		int indexofFirstBlank;
		int indexofSecondBlank;
		int indexofThirdBlank;
		int indexofFourthBlank;
		
		float _AFrequency;
		float _CFrequency;
		float _GFrequency;
		float _TFrequency;
		
		
		List<PositionFrequency> positionfrequencyList = new ArrayList<PositionFrequency>();;
		
		//Attention
		//Order is ACGT
		String ALine = "";
		String CLine = "";
		String GLine = "";
		String TLine = "";
		
		
		String tfName = null;
		String formerTfName = null;
		
		Iterator<PositionFrequency> iterator;
		
		try {
				fileReader = new FileReader(dataFolder + encodeMotifsInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
//					Encode-motif matrix
//					Order is ACGT					
//					>NFKB_disc1 NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
//					K 0.000000 0.000000 0.737500 0.262500
//					G 0.000000 0.000000 1.000000 0.000000
//					G 0.000000 0.000000 1.000000 0.000000
//					R 0.570833 0.000000 0.429167 0.000000
//					A 0.837500 0.158333 0.004167 0.000000
//					W 0.395833 0.000000 0.000000 0.604167
//					T 0.000000 0.004167 0.000000 0.995833
//					Y 0.000000 0.383333 0.000000 0.616667
//					C 0.000000 1.000000 0.000000 0.000000
//					C 0.000000 1.000000 0.000000 0.000000
					
					
					if (strLine.startsWith(">")){
						
						
						//start reading from first character, skip '>' character
						tfName = strLine.substring(1);
							
						if(formerTfName!=null){
							//Write former positionfrequencyList to the output file starts
							//if it is full
							ALine ="A |\t";
							CLine ="C |\t";
							GLine ="G |\t";
							TLine ="T |\t";
									
							iterator = positionfrequencyList.iterator();
							
							while(iterator.hasNext()){
								PositionFrequency positionFrequency = (PositionFrequency) iterator.next();
								ALine = ALine + positionFrequency.get_AFrequency() + "\t";
								CLine = CLine + positionFrequency.get_CFrequency() + "\t";
								GLine = GLine + positionFrequency.get_GFrequency() + "\t";
								TLine = TLine + positionFrequency.get_TFrequency() + "\t";
							}
							
							
							//We must have the former tfName
							//We must have inserted the header line
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + ALine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + CLine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + GLine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + TLine +System.getProperty("line.separator"));
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + "//"  +System.getProperty("line.separator"));
							//Write former full pfList to the output file ends
						}//End of if
						
						
						//if tfName is inserted for the first time
						if (tfName2PfmMatrices.get(tfName)==null){
							tfName2PfmMatrices.put(tfName, "; " + strLine.substring(1) + System.getProperty("line.separator"));
						}
						//else start appending the new coming matrix to the already existing matrices for this tfName
						else{
							tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "; " + strLine.substring(1) + System.getProperty("line.separator"));
						}
						
						//Initialize positionfrequencyList
						positionfrequencyList = new ArrayList<PositionFrequency>();
						
					}else{
						
						indexofFirstBlank 	= strLine.indexOf(' ');
						indexofSecondBlank 	= strLine.indexOf(' ',indexofFirstBlank+1);
						indexofThirdBlank 	= strLine.indexOf(' ',indexofSecondBlank+1);
						indexofFourthBlank 	= strLine.indexOf(' ',indexofThirdBlank+1);
						
						_AFrequency = Float.parseFloat(strLine.substring(indexofFirstBlank+1, indexofSecondBlank));
						_CFrequency = Float.parseFloat(strLine.substring(indexofSecondBlank+1, indexofThirdBlank));
						_GFrequency = Float.parseFloat(strLine.substring(indexofThirdBlank+1, indexofFourthBlank));
						_TFrequency	= Float.parseFloat(strLine.substring(indexofFourthBlank+1));
						
						PositionFrequency positionFrequency = new PositionFrequency(_AFrequency,_CFrequency,_GFrequency,_TFrequency);
						positionfrequencyList.add(positionFrequency);
						formerTfName = tfName;
					}
						
				}//end of while
				
				//Write the last positionFrequencyList starts
				//Order is ACGT
				ALine ="A |\t";
				CLine ="C |\t";
				GLine ="G |\t";
				TLine ="T |\t";
			
				iterator = positionfrequencyList.iterator();
				
				while(iterator.hasNext()){
					PositionFrequency positionFrequency = (PositionFrequency) iterator.next();
					ALine = ALine + positionFrequency.get_AFrequency() + "\t";
					CLine = CLine + positionFrequency.get_CFrequency() + "\t";
					GLine = GLine + positionFrequency.get_GFrequency() + "\t";
					TLine = TLine + positionFrequency.get_TFrequency() + "\t";
				}
				
				
				//We must use former tfName
				//We must have inserted the header line
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + ALine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + CLine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + GLine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + TLine +System.getProperty("line.separator"));
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + "//"  +System.getProperty("line.separator"));
				//Write the last positionFrequencyList ends
				
				bufferedReader.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public static void fillFrequencyListUsingCountList(List<Float> frequencyList, List<Integer> countList,Integer totalCount){
		
		Iterator<Integer> iterator = countList.iterator();
		
		Integer count;
		Float frequency;
		
		
		while(iterator.hasNext()){
			
			count = iterator.next();
			frequency = (count*1.0f)/totalCount;
			
			frequencyList.add(frequency);
			
			
		}
		
		
	}
	
	
	//Fill CountList using CountLine
		public static void fillCountList(String countLine,List<Integer> countList){
			//example Count line
			//4	19	0	0	0	0		
			int indexofFormerTab =0;
			int indexofLatterTab =0;
			
			int count =0;
			
			indexofFormerTab = 0;
			indexofLatterTab = countLine.indexOf('\t');
			
			
			//Insert the first count
			if (indexofLatterTab>=0){
				count = Integer.parseInt(countLine.substring(indexofFormerTab, indexofLatterTab));
				countList.add(count);			
			}
			
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = countLine.indexOf('\t',indexofFormerTab+1);

			
			//Insert the rest of the counts
			while(indexofLatterTab>=0){
				
				count = Integer.parseInt(countLine.substring(indexofFormerTab+1, indexofLatterTab));
				
				countList.add(count);
				
				indexofFormerTab = indexofLatterTab;
				indexofLatterTab = countLine.indexOf('\t',indexofFormerTab+1);
				
			}
			
			//Insert the last count
			if(indexofFormerTab>=0){
				count = Integer.parseInt(countLine.substring(indexofFormerTab+1));
				countList.add(count);
				
			}
		
		}
		
		public static int getTotalCount(List<Integer> ACountList,List<Integer> CCountList,List<Integer> GCountList,List<Integer>TCountList){
			
			Iterator<Integer> iteratorA = ACountList.iterator();
			Integer countA;
			
			
			Iterator<Integer> iteratorC = CCountList.iterator();
			Integer countC;
			
			Iterator<Integer> iteratorG = GCountList.iterator();
			Integer countG;
			
			Iterator<Integer> iteratorT = TCountList.iterator();
			Integer countT;
		
			int totalCount = 0;;
			
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()  ){
				
				countA = iteratorA.next();
				countC = iteratorC.next();
				countG = iteratorG.next();
				countT = iteratorT.next();
				
				totalCount = countA + countC + countG + countT;
				return totalCount;
				
			
				
			}
			
			return totalCount;
			
			
		}
	
		public static void putPFM(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,Map<String,String> tfName2PfmMatrices){
			
			//example matrix in tab format
			//		; NFKB_known3	NFKB_1	NF-kappaB_transfac_M00054														
			//		A |	0	0	0.025	0.675	0.525	0.2	0.025	0.05	0.075	0						
			//		C |	0	0	0	0	0.325	0.025	0.05	0.45	0.9	0.95						
			//		G |	1	1	0.975	0.325	0.025	0.075	0.05	0	0	0						
			//		T |	0	0	0	0	0.125	0.7	0.875	0.5	0.025	0.05						
			//		//			
			
			Iterator<Float> iteratorA = AFrequencyList.iterator();
			Iterator<Float> iteratorC = CFrequencyList.iterator();
			Iterator<Float> iteratorG = GFrequencyList.iterator();
			Iterator<Float> iteratorT = TFrequencyList.iterator();
			
			Float frequencyA;
			Float frequencyC;
			Float frequencyG;
			Float frequencyT;
			
			String strLineA = "";
			String strLineC = "";
			String strLineG = "";
			String strLineT = "";
			
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
				
				frequencyA = iteratorA.next();
				frequencyC = iteratorC.next();
				frequencyG = iteratorG.next();
				frequencyT = iteratorT.next();
				
				strLineA = strLineA  + "\t"  + frequencyA;
				strLineC = strLineC  + "\t"  + frequencyC;
				strLineG = strLineG  + "\t"  + frequencyG;
				strLineT = strLineT  + "\t"  + frequencyT;
				
			}//end of while

			
			//this tfName has no previous position frequency matrix inserted
			if(tfName2PfmMatrices.get(tfName)==null){
				tfName2PfmMatrices.put(tfName, "; " + tfName + System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "A|"+ strLineA+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "C|"+ strLineC+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "G|"+ strLineG+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "T|"+ strLineT+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "//"+  System.getProperty("line.separator"));
				
			}
			//this tfName already has position frequency matrices
			//append the new position frequency matrix
			else{
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "; " + tfName + System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "A|"+ strLineA+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "C|"+ strLineC+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "G|"+ strLineG+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "T|"+ strLineT+  System.getProperty("line.separator"));
				tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "//"+  System.getProperty("line.separator"));
					
			}

			
			
			
		}
		
		public static void putLogoMatrix(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,Map<String,String> tfName2LogoMatrices){
			
//			Example logo matrix
//			G 0.008511 0.004255 0.987234 0.000000		
//			A 0.902127 0.012766 0.038298 0.046809		
//			R 0.455319 0.072340 0.344681 0.127660		
//			W 0.251064 0.085106 0.085106 0.578724		
//			T 0.000000 0.046809 0.012766 0.940425		
//			G 0.000000 0.000000 1.000000 0.000000		
//			T 0.038298 0.021277 0.029787 0.910638		
//			A 0.944681 0.004255 0.051064 0.000000		
//			G 0.000000 0.000000 1.000000 0.000000		
//			T 0.000000 0.000000 0.012766 0.987234		

			Iterator<Float> iteratorA = AFrequencyList.iterator();
			Iterator<Float> iteratorC = CFrequencyList.iterator();
			Iterator<Float> iteratorG = GFrequencyList.iterator();
			Iterator<Float> iteratorT = TFrequencyList.iterator();
			
			Float frequencyA;
			Float frequencyC;
			Float frequencyG;
			Float frequencyT;
		
			String strLine = null;
			
			if (tfName2LogoMatrices.get(tfName) == null){
				tfName2LogoMatrices.put(tfName, tfName + System.getProperty("line.separator"));		
			}
			
			else{
				tfName2LogoMatrices.put(tfName,tfName2LogoMatrices.get(tfName)+ tfName + System.getProperty("line.separator"));			
			}
				
				
				
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
				
				frequencyA = iteratorA.next();
				frequencyC = iteratorC.next();
				frequencyG = iteratorG.next();
				frequencyT = iteratorT.next();
				
				strLine = "X" + "\t" + frequencyA + "\t" + frequencyC + "\t" + frequencyG + "\t" + frequencyT +System.getProperty("line.separator");
				tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName) + strLine);
				
				
			}//end of while

		
		}


	public static void constructPfmMatricesandLogoMatricesfromJasparCore(String dataFolder,String jasparCoreInputFileName,Map<String,String> tfName2PfmMatrices,Map<String,String>  tfName2LogoMatrices){
		//Attention
		//Order is ACGT
				
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
		
		String tfName = null;
		
		int whichLine = 0;
		
		final int headerLine= 0;
		final int ALine = 1;
		final int CLine = 2;
		final int GLine = 3;
		final int TLine = 4;
		
		List<Integer> ACountList = null;
		List<Float>	AFrequencyList = null;
		
		List<Integer> CCountList = null;
		List<Float>	CFrequencyList = null;
		
		List<Integer> GCountList = null;
		List<Float>	GFrequencyList = null;
	
		List<Integer> TCountList = null;
		List<Float>	TFrequencyList = null;
		
		int totalCount;
	
		try {
			fileReader = new FileReader(dataFolder + jasparCoreInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
//			Example matrix from jaspar core pfm_all.txt
//			>MA0004.1 Arnt																													
//			4	19	0	0	0	0																								
//			16	0	20	0	0	0																								
//			0	1	0	20	0	20																								
//			0	0	0	0	20	0																								

			
			while((strLine = bufferedReader.readLine())!=null){
				if (strLine.startsWith(">")){
					tfName = strLine.substring(1);
					
					//Initialize array lists
					//for the new coming position count matrix and position frequency matrix
					ACountList = new ArrayList<Integer>();
					AFrequencyList = new ArrayList<Float>();
					
					CCountList = new ArrayList<Integer>();
					CFrequencyList = new ArrayList<Float>();
					
					GCountList = new ArrayList<Integer>();
					GFrequencyList = new ArrayList<Float>();
				
					TCountList = new ArrayList<Integer>();
					TFrequencyList = new ArrayList<Float>();
					
					whichLine = ALine;
					continue;
				}
				
				switch(whichLine){			
					case ALine:	{	
									fillCountList(strLine,ACountList);
									whichLine = CLine;
									break;
								}
											
					case CLine:	{
									fillCountList(strLine,CCountList);							
									whichLine = GLine;
									break;
								}
										
					case GLine: {		
									fillCountList(strLine,GCountList);					
									whichLine = TLine;
									break;
								}
						
					case TLine:{
									fillCountList(strLine,TCountList);		
									whichLine = headerLine;
									
									//Since count lists are available
									//Then compute frequency lists
									totalCount = getTotalCount(ACountList,CCountList,GCountList,TCountList);
									fillFrequencyListUsingCountList(AFrequencyList,ACountList,totalCount);
									fillFrequencyListUsingCountList(CFrequencyList,CCountList,totalCount);
									fillFrequencyListUsingCountList(GFrequencyList,GCountList,totalCount);
									fillFrequencyListUsingCountList(TFrequencyList,TCountList,totalCount);
									
									//Now put the new matrix to the hashmap in tab format
									putPFM(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,tfName2PfmMatrices);
									
									//Put the transpose of the matrix for logo generation
									putLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,tfName2LogoMatrices);
//									writeLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,bufferedWriter);
	
									break;
									
								}
										
				}//End of switch
							
			}//End of while
			
			bufferedReader.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static String getTfNamewithoutNumber(String tfName){
		
		int n = tfName.length();
		char c;
		int i;
		
		for (i = 0; i < n; i++) {
		    c = tfName.charAt(i);
		    if (Character.isDigit(c)){
		    	break;
		    }
		}
		
		return tfName.substring(0, i);
	}
	
	public static String getTfNameWithFirstNumberWithNextCharacter(String tfName){
		
		int n = tfName.length();
		char c;
		int i;
		
		for (i = 0; i < n; i++) {
		    c = tfName.charAt(i);
		    if (Character.isDigit(c)){
		    	if ((i+1)<n){
		    	 	return tfName.substring(0, i+2);
		  		  
		    	}else{
		    	 	return tfName.substring(0, i+1);
		  		  
		    	}
		     }
		}
		
	
		return tfName.substring(0, i);
	}
	
	public static String removeLastCharacter(String tfName){
		
		int n = tfName.length();
		
		if (n>=6){
			return tfName.substring(0, n-1);
			
		}else{
			return tfName;
			
		}
	}
	
	
	public static void createTfIntervalsFile(String outputFolder,String directoryBase,String snpDirectory,String fileName, List<String> snpBasedTfIntervalKeyList, Map<String,TfKeggPathwayTfInterval> tfKeggPathwayBasedTfIntervalMap){
		
		TfKeggPathwayTfInterval tfInterval;
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
	
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + snpDirectory + System.getProperty("file.separator") + fileName + ".txt");	
			bufferedWriter = new BufferedWriter(fileWriter);
						
			for(String tfIntervalKey : snpBasedTfIntervalKeyList ){
	
				//get tfInterval
				tfInterval = tfKeggPathwayBasedTfIntervalMap.get(tfIntervalKey);
				
				tfIntervalStartOneBased = tfInterval.getStartOneBased();
				tfIntervalEndOneBased = tfInterval.getEndOneBased();
								
				bufferedWriter.write(tfInterval.getTfNameCellLineName() +  "\t" + "chr" + tfInterval.getChrNamewithoutPreceedingChr() + " \t" + tfIntervalStartOneBased + "\t" +tfIntervalEndOneBased + System.getProperty("line.separator"));				
						
			}//End of file for each tf interval overlapping with this snp
		
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//for tf
	public static void createTfIntervalsFile_forTf(String outputFolder,String directoryBase,String snpDirectory,String fileName, List<String> snpBasedTfIntervalKeyList, Map<String,TfCellLineTfInterval> tfCellLineBasedTfIntervalMap){
		
		TfCellLineTfInterval tfInterval;
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
	
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + snpDirectory + System.getProperty("file.separator") + fileName + ".txt");	
			bufferedWriter = new BufferedWriter(fileWriter);
						
			for(String tfIntervalKey : snpBasedTfIntervalKeyList ){
	
				//get tfInterval
				tfInterval = tfCellLineBasedTfIntervalMap.get(tfIntervalKey);
				
				tfIntervalStartOneBased = tfInterval.getStartOneBased();
				tfIntervalEndOneBased = tfInterval.getEndOneBased();
								
				bufferedWriter.write(tfInterval.getTfNameCellLineName() +  "\t" + "chr" + tfInterval.getChrNamewithoutPreceedingChr() + " \t" + tfIntervalStartOneBased + "\t" +tfIntervalEndOneBased + System.getProperty("line.separator"));				
						
			}//End of file for each tf interval overlapping with this snp
		
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static void createSequenceFile(String outputFolder,String directoryBase, String sequenceFileDirectory, String fileName,String sequence){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		int indexofLineSeparator;
		String firstLineofFastaFile;
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + sequenceFileDirectory + System.getProperty("file.separator") + fileName + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			indexofLineSeparator = sequence.indexOf(System.getProperty("line.separator"));
			
			//fastaFile is sent
			if(indexofLineSeparator!=-1){
				firstLineofFastaFile = sequence.substring(0, indexofLineSeparator);
				
				bufferedWriter.write(firstLineofFastaFile + "\t" +fileName + System.getProperty("line.separator"));
				bufferedWriter.write(sequence.substring(indexofLineSeparator+1).trim());
			
			}
			//only sequence is sent
			//so add '>' character to make it fasta format
			else{
				bufferedWriter.write(">" + fileName + System.getProperty("line.separator"));
				bufferedWriter.write(sequence);
	
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getAlteredSequence(String precedingSNP,String allele,String followingSNP){
		
		if(!allele.equals(Commons.STRING_HYPHEN)){
			return precedingSNP + allele + followingSNP;
		}else{
			return precedingSNP + followingSNP;

		}
		
		
	}
	
public static String takeComplementforeachAllele(String allele){
		
		String complementedAllele = "";
		
		for(char nucleotide: allele.toCharArray()){
			switch(nucleotide) {
				case 'A':
				case 'a': 	complementedAllele = complementedAllele + "T";
							break;
							
				case 'C':
				case 'c': 	complementedAllele = complementedAllele + "G";
							break;
							
				case 'G':
				case 'g': 	complementedAllele = complementedAllele + "C";
							break;
							
				case 'T':
				case 't': 	complementedAllele = complementedAllele + "A";
							break;
							
				case '-': 	complementedAllele = complementedAllele + "-";
							break;
							
				default : return null;			
							
			}//End of switch
		}//End of for
		
		return complementedAllele;
	}

	public static String takeComplement(String alleles){
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		String complementedAllele = null;
		String complementedAlleles =  "";
		
		indexofFormerTab = alleles.indexOf('\t');
		
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
		
		//take the complement of this allele
		complementedAllele = takeComplementforeachAllele(allele);
		
		if(complementedAllele!=null){
			complementedAlleles = complementedAlleles + complementedAllele + "\t";
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//take the complement of this allele
			complementedAllele = takeComplementforeachAllele(allele);
			
			if(complementedAllele!=null){
				complementedAlleles = complementedAlleles + complementedAllele + "\t";
			}
			
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		//get the last allele
		allele = alleles.substring(indexofFormerTab+1);
		//take the complement of this allele
		complementedAllele = takeComplementforeachAllele(allele);
		
		if(complementedAllele!=null){
			complementedAlleles = complementedAlleles + complementedAllele;
		}
	
				
		
		
				
		return complementedAlleles;
		
	}
	
	public static List<String> findOtherObservedAllelesandGetAltereSequences(String snp, String alleles,String precedingSNP,String followingSNP){
		
		List<String> alteredSnpSequences;
		
		String complementedAlleles;
		
			
		//We must decide whether we can use alleles 
		//or we must use the complement of the alleles
		//if snp is equal to the one of these alleles then use alleles
		//else use the complement of alleles
		if (useAlleles(snp,alleles)){
			alteredSnpSequences = getAlteredSnpSequences(snp,alleles,precedingSNP,followingSNP);
		}else {
			complementedAlleles = takeComplement(alleles);
			alteredSnpSequences = getAlteredSnpSequences(snp,complementedAlleles,precedingSNP,followingSNP);	
		}
			
		
		return alteredSnpSequences;
	}
	

	
	public static List<String> getAlteredSNPSequences(String referenceSequence, List<String> observedAlleles,int oneBasedStartSnpPosition, int oneBasedEndSnpPosition){
		
		String precedingSNP;
		String followingSNP;
		String snp;
		
		
		List<String> alteredSnpSequences;
		List<String> allAlteredSnpSequences = new ArrayList<String>();
				
		//snpPosition is at Commons.SNP_POSITION; (one-based)
				
		//precedingSNP is 14 characters long
		precedingSNP = referenceSequence.substring(0, oneBasedStartSnpPosition-1);
		
		//followingSNP is 14 characters long
		followingSNP = referenceSequence.substring(oneBasedEndSnpPosition);
		
		//snp
		snp = referenceSequence.substring(oneBasedStartSnpPosition-1,oneBasedEndSnpPosition);
				
		//take each possible observed alleles
		//C\tT\t
		
		for(String alleles: observedAlleles){
			
			//Find the other alleles other than normal nucleotide
			alteredSnpSequences = findOtherObservedAllelesandGetAltereSequences(snp,alleles,precedingSNP,followingSNP);
			
			//check for whether every altered sequence in alteredSnpSequences exists in allAlteredSnpSequences
			//if allAlteredSnpSequences does not contain it 
			//then add it
			for(String alteredSequence:alteredSnpSequences){
				if (!allAlteredSnpSequences.contains(alteredSequence)){
					allAlteredSnpSequences.add(alteredSequence);
					
				}
			}
			
		}
	
		return allAlteredSnpSequences;
	}
	
	
	public static List<String>  getAlteredSnpSequences(String snp, String alleles,String precedingSNP,String followingSNP){
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		String alteredSnpSequence;
		List<String> alteredSnpSequences = new ArrayList<String>();
		
		indexofFormerTab = alleles.indexOf('\t');
				
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
		
		//check for this first allele
		if (!snp.equals(allele)){
			alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
			
			if (alteredSnpSequence!=null && !(alteredSnpSequences.contains(alteredSnpSequence))){
				alteredSnpSequences.add(alteredSnpSequence);
			}
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//check for this allele
			if (!snp.equals(allele)){
				alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
				
				if (alteredSnpSequence!=null && !(alteredSnpSequences.contains(alteredSnpSequence))){
					alteredSnpSequences.add(alteredSnpSequence);
				}
			}
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		//get the last allele
		allele = alleles.substring(indexofFormerTab+1);
		
		//check for this last allele
		if (!snp.equals(allele)){
			alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
			
			if (alteredSnpSequence!=null && !(alteredSnpSequences.contains(alteredSnpSequence))){
				alteredSnpSequences.add(alteredSnpSequence);
			}
		}
		
		return alteredSnpSequences;
	}


	
	//if snp exists in any of tab delimited alleles useAlleles return true
	//else useAlleles return false
	public static boolean useAlleles(String snp,String alleles){
		//alleles is composed by allele each is seperated by tab
		//A\tC\tG\t
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		
		indexofFormerTab = alleles.indexOf('\t');
		
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
				
		//check for this first allele
		if (snp.equals(allele)){
			return true;
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//check for this allele
			if (snp.equals(allele)){
				return true;
			}
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		//get the last allele
		allele = alleles.substring(indexofFormerTab+1);
		
		//check for this last allele
		if (snp.equals(allele)){
			return true;
		}
		
				
		return false;
		
	}
	
	public static void createObservedAllelesFile(String outputFolder,String directoryBase, String observedAllelesFileDirectory, String fileName,List<String> observedAlleles){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + observedAllelesFileDirectory + System.getProperty("file.separator") + fileName + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(String observedAllele: observedAlleles){
				bufferedWriter.write(observedAllele + System.getProperty("line.separator"));	
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createPeakSequencesFile(String outputFolder, String directoryBase,String sequenceFileDirectory, String fileName, String peakName, String peakSequence){

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		int indexofFirstLineSeparator;
		String firstLineofFastaFile;
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + sequenceFileDirectory + System.getProperty("file.separator") + fileName + ".txt",true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			indexofFirstLineSeparator = peakSequence.indexOf(System.getProperty("line.separator"));
			firstLineofFastaFile = peakSequence.substring(0,indexofFirstLineSeparator);
					
			bufferedWriter.write(firstLineofFastaFile + "\t" +peakName + System.getProperty("line.separator"));
			bufferedWriter.write(peakSequence.substring(indexofFirstLineSeparator+1).trim());
		
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void createMatrixFile(String outputFolder,String directoryBase, String tfNameCellLineNameorKeggPathwayName, String matrixName,String matrix){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + directoryBase + tfNameCellLineNameorKeggPathwayName + System.getProperty("file.separator") +matrixName + ".txt",true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(matrix);
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	//Requires chrName without preceeding "chr" string 
	//Requires oneBased coordinates
	public static String  getDNASequence(String chrNamewithoutPreceedingChr,int startOneBased, int endOneBased,Map<String,String> chrName2RefSeqIdforGrch38Map){
		
		String sourceHTML = null;
		String refSeqId;
		
		refSeqId = chrName2RefSeqIdforGrch38Map.get(chrNamewithoutPreceedingChr);
				
	  
	  //GlanetRunner.appendLog("EFETCH RESULT:");
	  // Read from the URL
	  try
	  { 
		  	String eFetchString="http://www.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id="+ refSeqId +"&strand=1" +  "&seq_start="+ startOneBased + "&seq_stop=" + endOneBased + "&rettype=fasta&retmode=text";
		  	URL url= new URL(eFetchString);
		
	   	 	BufferedReader in= new BufferedReader(new InputStreamReader(url.openStream()));
	        String inputLine;       // one line of the result, as it is read line by line
	        sourceHTML= "";  // will eventually contain the whole result
	        // Continue to read lines while there are still some left to read
	        
	        //Pay attention
	        //Each line including last line has new line character at the end.
	        while ((inputLine= in.readLine()) != null)  // read one line of the input stream
	            { sourceHTML+= inputLine + System.getProperty("line.separator");            // add this line to end of the whole shebang
	//	              ++lineCount;                              // count the number of lines read
	            }
	        
	        // Close the connection
	        in.close();
	  }catch (Exception e){ 
		  GlanetRunner.appendLog("Error reading from the URL:");
		  System.out.println(e);
	  }
	  
	
	  return sourceHTML;
	}

	
	//without orient starts
public static String convertSlashSeparatedAllelestoTabSeparatedAlleles(String observedAllelesSeparatedwithSlash){
		
		int indexofFormerSlash = observedAllelesSeparatedwithSlash.indexOf('/');
		int indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash +1);
		
		String allele;
		String observedAllelesSeparatedwithTabs = "";
		
		//for the first allele
		allele = observedAllelesSeparatedwithSlash.substring(0,indexofFormerSlash);
		
		//update
		observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
	
		
				
		
		while (indexofFormerSlash>=0 && indexofLatterSlash >=0){
			
			allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1, indexofLatterSlash);	
			
			//update
			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
				
			indexofFormerSlash = indexofLatterSlash ;			
			indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash+1);
			
		}
		
		//for the last allele
		allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1);	
		
		//update
		observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele;		
		
			
		return observedAllelesSeparatedwithTabs;
			
	}
	//without orient ends
	
	
//	//With Orient starts
//	public static String convertSlashSeparatedAllelestoTabSeparatedAlleles(String observedAllelesSeparatedwithSlash, String orient){
//		
//		int indexofFormerSlash = observedAllelesSeparatedwithSlash.indexOf('/');
//		int indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash +1);
//		
//		String allele;
//		String complementedAllele;
//		String observedAllelesSeparatedwithTabs = "";
//		
//		//for the first allele
//		allele = observedAllelesSeparatedwithSlash.substring(0,indexofFormerSlash);
//		
//		if (orient.equals(Commons.REVERSE)){
//			complementedAllele = takeComplementforeachAllele(allele);			
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + complementedAllele + "\t";			
//		}else{
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
//		}
//		
//		
//				
//		
//		while (indexofFormerSlash>=0 && indexofLatterSlash >=0){
//			
//			allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1, indexofLatterSlash);	
//			
//			if (orient.equals(Commons.REVERSE)){
//				complementedAllele = takeComplementforeachAllele(allele);				
//				//update
//				observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + complementedAllele + "\t";				
//			}else{
//				//update
//				observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele + "\t";		
//			}
//			
//			
//			indexofFormerSlash = indexofLatterSlash ;			
//			indexofLatterSlash = observedAllelesSeparatedwithSlash.indexOf('/',indexofFormerSlash+1);
//			
//		}
//		
//		//for the last allele
//		allele = observedAllelesSeparatedwithSlash.substring(indexofFormerSlash+1);	
//		
//		if (orient.equals(Commons.REVERSE)){
//			complementedAllele = takeComplementforeachAllele(allele);				
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + complementedAllele;				
//		}else{
//			//update
//			observedAllelesSeparatedwithTabs = observedAllelesSeparatedwithTabs + allele;		
//		}
//		
//		
//		return observedAllelesSeparatedwithTabs;
//			
//	}
//	//With Orient ends

	
	public static boolean alreadyExists(String observedAllelesSeparatedbyTabs,List<String> observedAlleles){
		
		Boolean exists = false;
		
		
		for(String allele:observedAlleles){
			if (observedAllelesSeparatedbyTabs.equals(allele)){
				exists = true;
				break;
			}
		}
		
		return exists;
		
	}
		
	
	public static void add(String chrNamewithoutChr,int zeroBasedCoordinate, String observedAllelesSeparatedbyTabs,Map<String, List<String>> chrNamewithoutChrandZeroBasedCoordinate2ObservedAlleles){
		String key = chrNamewithoutChr + "_"  + zeroBasedCoordinate;
		
		List<String> observedAlleles = chrNamewithoutChrandZeroBasedCoordinate2ObservedAlleles.get(key);
		
		if (observedAlleles==null){
			observedAlleles = new ArrayList<String>();			
			observedAlleles.add(observedAllelesSeparatedbyTabs);
			chrNamewithoutChrandZeroBasedCoordinate2ObservedAlleles.put(key, observedAlleles);
		} else {
			
			if (!observedAlleles.contains(observedAllelesSeparatedbyTabs)){
				observedAlleles.add(observedAllelesSeparatedbyTabs);			
			}
//			if (!alreadyExists(observedAllelesSeparatedbyTabs,observedAlleles)){
//				observedAlleles.add(observedAllelesSeparatedbyTabs);
//			}
		}
		
	}
	
	
	public static String getDirectoryBase(String enrichmentType){
		
		String directoryBase = null;
		
			switch(enrichmentType){
			
				case Commons.TF:{
					directoryBase = Commons.TF_RESULTS_DIRECTORY_BASE;	
					break;
				}
			
				case Commons.TF_EXON_BASED_KEGG_PATHWAY:{			
					directoryBase = Commons.TF_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;	
					break;
				}
				
				case Commons.TF_REGULATION_BASED_KEGG_PATHWAY:{				
					directoryBase = Commons.TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;			
					break;
				}
					
				case Commons.TF_ALL_BASED_KEGG_PATHWAY:{			
					directoryBase = Commons.TF_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;			
					break;
				}	
				
				case Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY:{
					directoryBase = Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;		
					break;
				}
				
				case Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY:{
					directoryBase = Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;					
					break;
				}
				
				case Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY:{
					directoryBase = Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;					
					break;
				}
			
			} // End of switch

		return directoryBase;
	}
	
	public static String getDNASequenceFromFastaFile(String fastaFile){
		String referenceSequence;
		int indexofFirstLineSeparator;
		
		
		indexofFirstLineSeparator = fastaFile.indexOf(System.getProperty("line.separator"));
		referenceSequence = fastaFile.substring(indexofFirstLineSeparator+1).trim();
		
		return referenceSequence;
		
		
		
	}
	
	
	public static void getSNPBasedPeakSequence(List<String> snpBasedTfIntervalKeyList,Map<String,TfKeggPathwayTfInterval> tfKeggPathwayBasedTfIntervalMap,String chrNamewithoutPreceedingChr,Map<String,String> chrName2RefSeqIdforGrch38Map,String outputFolder, String directoryBase,String tfNameKeggPathwayNameBased_SnpDirectory,String snpKeyString){
		
		//initialize  min and max tf intervals
		int minTfIntervalStartOneBased = Integer.MAX_VALUE;
		int maxTfIntervalEndOneBased = Integer.MIN_VALUE;
		
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		String tfExtendedPeakSequence;
		TfKeggPathwayTfInterval tfInterval;
					
		for(String tfIntervalKey : snpBasedTfIntervalKeyList ){

			//get tfInterval
			tfInterval = tfKeggPathwayBasedTfIntervalMap.get(tfIntervalKey);
			
			tfIntervalStartOneBased = tfInterval.getStartOneBased();
			tfIntervalEndOneBased = tfInterval.getEndOneBased();
			
			if (tfIntervalStartOneBased < minTfIntervalStartOneBased){
				minTfIntervalStartOneBased = tfIntervalStartOneBased;
			}
			
			if (tfIntervalEndOneBased > maxTfIntervalEndOneBased){
				maxTfIntervalEndOneBased = tfIntervalEndOneBased;
			}		
					
		}//End of file for each tf interval overlapping with this snp
		
		
		tfExtendedPeakSequence = getDNASequence(chrNamewithoutPreceedingChr,minTfIntervalStartOneBased, maxTfIntervalEndOneBased,chrName2RefSeqIdforGrch38Map);
		
		//write snp based extended peak sequence file
		createPeakSequencesFile(outputFolder,directoryBase,tfNameKeggPathwayNameBased_SnpDirectory,"extendedPeakSequence","extendedPeak",tfExtendedPeakSequence);

	}
	
	
	//for tf
	public static void getSNPBasedPeakSequence_forTf(List<String> snpBasedTfIntervalKeyList,Map<String,TfCellLineTfInterval> tfCellLineBasedTfIntervalMap,String chrNamewithoutPreceedingChr,Map<String,String> chrName2RefSeqIdforGrch38Map,String outputFolder, String directoryBase,String tfNameCellLineNameBased_SnpDirectory,String snpKeyString){
		
		//initialize  min and max tf intervals
		int minTfIntervalStartOneBased = Integer.MAX_VALUE;
		int maxTfIntervalEndOneBased = Integer.MIN_VALUE;
		
		int tfIntervalStartOneBased;
		int tfIntervalEndOneBased;
		
		String tfExtendedPeakSequence;
		TfCellLineTfInterval tfInterval;
					
		for(String tfIntervalKey : snpBasedTfIntervalKeyList ){

			//get tfInterval
			tfInterval = tfCellLineBasedTfIntervalMap.get(tfIntervalKey);
			
			tfIntervalStartOneBased = tfInterval.getStartOneBased();
			tfIntervalEndOneBased = tfInterval.getEndOneBased();
			
			if (tfIntervalStartOneBased < minTfIntervalStartOneBased){
				minTfIntervalStartOneBased = tfIntervalStartOneBased;
			}
			
			if (tfIntervalEndOneBased > maxTfIntervalEndOneBased){
				maxTfIntervalEndOneBased = tfIntervalEndOneBased;
			}		
					
		}//End of file for each tf interval overlapping with this snp
		
		
		tfExtendedPeakSequence = getDNASequence(chrNamewithoutPreceedingChr,minTfIntervalStartOneBased, maxTfIntervalEndOneBased,chrName2RefSeqIdforGrch38Map);
		
		//write snp based extended peak sequence file
		createPeakSequencesFile(outputFolder,directoryBase,tfNameCellLineNameBased_SnpDirectory,"extendedPeakSequence","extendedPeak",tfExtendedPeakSequence);

	}
	
	public static List<String> findTfIntervalsOverlappingWithThisSNP(List<String> tfIntervalKeyList, Map<String,TfKeggPathwayTfInterval> tfIntervalMap,int snpZeroBasedStartCoordinate, int snpZeroBasedEndCoordinate){
		
		List<String> snpBasedTfIntervalKeyList = new ArrayList<String>();
		TfKeggPathwayTfInterval tfInterval;
		
		for(String tfIntervalKey: tfIntervalKeyList ){
			tfInterval = tfIntervalMap.get(tfIntervalKey);
					
			//Interval Tree works with 0 Based coordinates
			if (IntervalTree.overlaps((tfInterval.getStartOneBased()-1), (tfInterval.getEndOneBased()-1), snpZeroBasedStartCoordinate, snpZeroBasedEndCoordinate)){
				snpBasedTfIntervalKeyList.add(tfIntervalKey);
			}
		}
		
		return snpBasedTfIntervalKeyList;
		
	}
	
	//for tf
	public static List<String> findTfIntervalsOverlappingWithThisSNP_forTf(List<String> tfIntervalKeyList, Map<String,TfCellLineTfInterval> tfIntervalMap,int snpZeroBasedStartCoordinate, int snpZeroBasedEndCoordinate){
		
		List<String> snpBasedTfIntervalKeyList = new ArrayList<String>();
		TfCellLineTfInterval tfInterval;
		
		for(String tfIntervalKey: tfIntervalKeyList ){
			tfInterval = tfIntervalMap.get(tfIntervalKey);
						
			//IntervalTree works with 0Based coordinates
			if (IntervalTree.overlaps((tfInterval.getStartOneBased()-1), (tfInterval.getEndOneBased()-1), snpZeroBasedStartCoordinate, snpZeroBasedEndCoordinate)){
				snpBasedTfIntervalKeyList.add(tfIntervalKey);
			}
		}
		
		return snpBasedTfIntervalKeyList;
		
	}
	
	//TF starts
	public static void readAugmentedDataWriteSequencesandMatrices_forTf(
			AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs, 
			AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation,
			Map<String,String> chrName2RefSeqIdforGrch38Map, 
			String outputFolder,
			String augmentedInputFileName, 
			Map<String,String> tfName2PfmMatrices, 
			Map<String,String> tfName2LogoMatrices,
			String enrichmentType){
		
		FileReader augmentedFileReader;
		BufferedReader augmentedBufferedReader;
				
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
			
		int indexofUnderscore;
		
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
		int indexofThirdUnderscore;
			
		String chrNamewithPreceedingChr = null;
		String chrNamewithoutPreceedingChr = null;
						
		int givenIntervalStartOneBased;
		int givenIntervalEndOneBased;

		int tfStartOneBased;
		int tfEndOneBased;
						
		String tfNameCellLineName = null;
		
		String tfName;
		String tfNameRemovedLastCharacter;
		String previousTfNameRemovedLastCharacter;
		
		boolean thereExistsPFMMatrix = false;
		boolean thereExistsLOGOMatrix = false;
		
		
		String fastaFile;
		String referenceSequence;
		String directoryBase = null;
					
		Boolean isThereAnExactTfNamePfmMatrix = false;
		
		//4 April 2014
		List<String> rsIdList;
		RsInformation rsInformation;
		String observedAllelesSeparatedwithSlash;
		
				
		
		/*******************************************************************************/
		/***************************MAP1 starts*****************************************/
		/*******************************************************************************/
		//This map is used whether this pfm matrix file is already created and written for a certain TF.
		//Right know key is tfCellLine 
		//However key can be only TF, is not it?  @todo test it.
		//If once pfm and logo matrices are found then there is no need to search and write pfm matrix files twice or more
		Map<String,Boolean> tfCellLine2PFMMatriceAlreadyExistsTrueorFalseMap 	= new HashMap<String,Boolean>();
		/*******************************************************************************/
		/***************************MAP1 ends*******************************************/
		/*******************************************************************************/
			
		
		/*******************************************************************************/
		/***************************MAP2 starts******************************************/
		/*******************************************************************************/
		//This map contains the list of snp keys for a given interval
		//Key must be GivenIntervalName which is  chrNamewithPreceedingChr + givenIntervalStartZeroBased + givenIntervalEndZeroBased
		String givenIntervalKey;
		Map<String,List<String>> givenInterval2SnpListMap = new HashMap<String,List<String>>();
		List<String> snpKeyListInAGivenInterval;
		/*******************************************************************************/
		/***************************MAP2 ends********************************************/
		/*******************************************************************************/
	

		/*******************************************************************************/
		/***************************MAP3 starts******************************************/
		/*******************************************************************************/
		//Key must contain rsID_chrNumber_startOneBased
		String snpKey;	
		Map<String,SNP> snpMap =  new HashMap<String,SNP>();
		SNP snp;
		/*******************************************************************************/
		/***************************MAP3 ends********************************************/
		/*******************************************************************************/
	
				
		/*******************************************************************************/
		/***************************MAP4 starts*****************************************/
		/*******************************************************************************/
		//7 April 2014 starts		
		//Key must contain TFCellLine givenIntervalName (chrNumber startZeroBased endZeroBased)
		Map<String,TfCellLineGivenInterval> tfCellLineGivenInterval2Map = new HashMap<String,TfCellLineGivenInterval>();
		String tfCellLineGivenIntervalKey;
		TfCellLineGivenInterval tfCellLineGivenInterval;
		/*******************************************************************************/
		/***************************MAP4 ends*******************************************/
		/*******************************************************************************/
	
		
		/*******************************************************************************/
		/***************************MAP5 starts*****************************************/
		/*******************************************************************************/
		//Key must contain TFCellLine givenIntervalName (chrNumber startZeroBased endZeroBased) TFInterval (chrNumber startZeroBased endZeroBased)		
		Map<String,TfCellLineTfInterval> tfCellLineGivenIntervalTfInterval2Map = new HashMap<String,TfCellLineTfInterval>();
		String tfCellLineGivenIntervalTfIntervalKey;
		TfCellLineTfInterval tfCellLineTfInterval;
		/*******************************************************************************/
		/***************************MAP5 ends*******************************************/
		/*******************************************************************************/
	
		
	
		//7 April 2014  ends
					
				
		//10 March 2014
		//Each observedAlleles String contains observed alleles which are separated by tabs, pay attention, there can be more than two observed alleles such as A\tG\tT\t-\tACG
		//Pay attention, for the same chrName and ChrPosition there can be more than one observedAlleles String. It is rare but possible.
		List<String> alteredSequences;
		String observedAllelesSeparatedwithTabs;
					
//		**************	POL2_HEPG2	**************
//		POL2_HEPG2	Searched for chr	interval Low	interval High	tfbs node Chrom Name	node Low	node High	node Tfbs Name	node CellLineName	node FileName
//		POL2_HEPG2	chr4	186266940	186266940	chr4	186266748	186267003	POL2	HEPG2	spp.optimal.wgEncodeHaibTfbsHepg2Pol2Pcr2xAlnRep0_VS_wgEncodeHaibTfbsHepg2ControlPcr2xAlnRep0.narrowPeak
//		POL2_HEPG2	chr4	186266940	186266940	chr4	186266771	186266986	POL2	HEPG2	spp.optimal.wgEncodeSydhTfbsHepg2Pol2ForsklnStdAlnRep0_VS_wgEncodeSydhTfbsHepg2InputForsklnStdAlnRep0.narrowPeak
//		POL2_HEPG2	chr4	186266940	186266940	chr4	186266803	186267066	POL2	HEPG2	spp.optimal.wgEncodeSydhTfbsHepg2Pol2IggrabAlnRep0_VS_wgEncodeSydhTfbsHepg2InputIggrabAln.narrowPeak
//		POL2_HEPG2	Searched for chr	interval Low	interval High	tfbs node Chrom Name	node Low	node High	node Tfbs Name	node CellLineName	node FileName
//		POL2_HEPG2	chr6	31951083	31951083	chr6	31950996	31951211	POL2	HEPG2	spp.optimal.wgEncodeSydhTfbsHepg2Pol2ForsklnStdAlnRep0_VS_wgEncodeSydhTfbsHepg2InputForsklnStdAlnRep0.narrowPeak

		try {
			augmentedFileReader = new FileReader(outputFolder + augmentedInputFileName);
			augmentedBufferedReader = new BufferedReader(augmentedFileReader);
			
			directoryBase = getDirectoryBase(enrichmentType);
			
			/****************************************************************************************/
			/*********************Reading Augmented TF File Starts***********************************/
			/****************************************************************************************/										
			while((strLine = augmentedBufferedReader.readLine())!=null){
							
				//skip strLine starts with * or contains "Search for" which means it is a header line
				if (!(strLine.startsWith("*")) && !(strLine.contains("Searched for"))){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
					indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
					
					tfNameCellLineName = strLine.substring(0, indexofFirstTab);
					chrNamewithPreceedingChr =  strLine.substring(indexofFirstTab+1, indexofSecondTab);
					chrNamewithoutPreceedingChr = chrNamewithPreceedingChr.substring(3);
					
					//Used in finding list of rsIds in this given interval
					givenIntervalStartOneBased = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					givenIntervalEndOneBased =  Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
											
					tfStartOneBased = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					tfEndOneBased =  Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
					//Get tfName and CellLineName
					indexofUnderscore = tfNameCellLineName.indexOf('_');
					tfName = tfNameCellLineName.substring(0, indexofUnderscore);
					
					//Initialize tfNameRemovedLastCharacter to tfName
					tfNameRemovedLastCharacter = tfName;
					
					/*************************************************************************/
					/**********Create Files for pfm Matrices and logo Matrices starts*********/
					/*************************************************************************/
					if(tfCellLine2PFMMatriceAlreadyExistsTrueorFalseMap.get(tfNameCellLineName) == null){
												
						isThereAnExactTfNamePfmMatrix = false;
						
						//Find PFM entry							
						for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
							if (pfmEntry.getKey().contains(tfName)){
								isThereAnExactTfNamePfmMatrix = true;
								createMatrixFile(outputFolder,directoryBase, tfNameCellLineName,  "pfmMatrices_" + tfName,pfmEntry.getValue());
									
							}
						}//End of for
						
						
						//Find LOGO entry
						for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
							if(logoEntry.getKey().contains(tfName)){
								createMatrixFile(outputFolder,directoryBase, tfNameCellLineName, "logoMatrices_" +tfName,logoEntry.getValue());

							}
						}

						
						if (!isThereAnExactTfNamePfmMatrix){
							
							thereExistsPFMMatrix= false;
							thereExistsLOGOMatrix = false;
							
							while (!thereExistsPFMMatrix && !thereExistsLOGOMatrix){
								previousTfNameRemovedLastCharacter = tfNameRemovedLastCharacter;
								//@todo removeLastCharacter may need further check
								tfNameRemovedLastCharacter = removeLastCharacter(tfNameRemovedLastCharacter);
								
								//If no  change
								if(previousTfNameRemovedLastCharacter.equals(tfNameRemovedLastCharacter)){
									break;
								}
								
								//find pfm entry							
								for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
									if (pfmEntry.getKey().contains(tfNameRemovedLastCharacter)){
										thereExistsPFMMatrix = true;
										createMatrixFile(outputFolder,directoryBase, tfNameCellLineName, "pfmMatrices_" + tfName,pfmEntry.getValue());									
												
									}
								}//End of for PFM
								
								//find logo entry
								for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
									if(logoEntry.getKey().contains(tfNameRemovedLastCharacter)){
										thereExistsLOGOMatrix= true;
										createMatrixFile(outputFolder,directoryBase, tfNameCellLineName, "logoMatrices_" +tfName,logoEntry.getValue());
	
									}
								}//End of for LOGO
								
							}//End of while
							
														
						}//End of IF there is no exact TF NAME PFM Matrix Match
						
						tfCellLine2PFMMatriceAlreadyExistsTrueorFalseMap.put(tfNameCellLineName, true);
					} //End of if
					/*************************************************************************/
					/**********Create Files for pfm Matrices and logo Matrices ends***********/
					/*************************************************************************/

					
					/*************************************************************************/
					/*************************SET KEYS starts*********************************/
					/*************************************************************************/
					//Set Given Interval Name
					givenIntervalKey ="givenInterval" + "_" + chrNamewithPreceedingChr + "_" + givenIntervalStartOneBased +  "_" + givenIntervalEndOneBased;
					
					//Create key using tfNameCellLineName and GivenIntervalName					
					tfCellLineGivenIntervalKey = tfNameCellLineName + "_" + givenIntervalKey;
		 					
					//Create key using tfNameCellLineName and givenIntervalName and tfInterval
					tfCellLineGivenIntervalTfIntervalKey = tfNameCellLineName + "_" + givenIntervalKey + "_" + "tfInterval" + "_" + chrNamewithPreceedingChr + "_" + tfStartOneBased + "_" + tfEndOneBased;
						
					//Get the given interval
					tfCellLineGivenInterval = tfCellLineGivenInterval2Map.get(tfCellLineGivenIntervalKey);
					/*************************************************************************/
					/*************************SET KEYS ends***********************************/
					/*************************************************************************/
					
					
					/*****************************************************************************************************/
					/*******For this tfCellLine, this given interval is read for the first time starts********************/
					/*****************************************************************************************************/					
					if (tfCellLineGivenInterval==null){
						
						tfCellLineGivenInterval = new TfCellLineGivenInterval();
						
						tfCellLineGivenInterval2Map.put(tfCellLineGivenIntervalKey, tfCellLineGivenInterval);
						
						tfCellLineGivenInterval.setTfNameCellLineName(tfNameCellLineName);
						tfCellLineGivenInterval.setGivenIntervalName(givenIntervalKey);
						
						tfCellLineGivenInterval.setChromNamewithoutPreceedingChr(chrNamewithoutPreceedingChr);
						tfCellLineGivenInterval.setGivenIntervalStartOneBased(givenIntervalStartOneBased);
						tfCellLineGivenInterval.setGivenIntervalEndOneBased(givenIntervalEndOneBased);
						
						tfCellLineGivenInterval.setSnpKeyList(new ArrayList<String>());
						tfCellLineGivenInterval.setTfCellLineBasedTfIntervalKeyList(new ArrayList<String>());	
						
						
						/*******************************************************************************************/
						/***************************************PART1 starts****************************************/
						/**************************Getting snpKeyList in a given interval***************************/
						/*************Check whether snps in this given interval are already found or not************/
						/*******************************************************************************************/
						snpKeyListInAGivenInterval = givenInterval2SnpListMap.get(givenIntervalKey);
						
						if (snpKeyListInAGivenInterval==null){
							
							snpKeyListInAGivenInterval = new ArrayList<String>();
							givenInterval2SnpListMap.put(givenIntervalKey,snpKeyListInAGivenInterval);
							
							//Get all the rsIDs in this given interval				
							//We have to provide 1-based coordinates as arguments
							rsIdList = augmentationOfAGivenIntervalWithRsIDs.getRsIdsInAGivenInterval(chrNamewithoutPreceedingChr,givenIntervalStartOneBased,givenIntervalEndOneBased);
							
													
							for(String rsId: rsIdList){
																	
								//For each rsId get rs Information
								rsInformation = augmentationOfAGivenRsIdWithInformation.getInformationforGivenRsId(rsId);
								
								if(rsInformation!=null){
									if (!rsInformation.isMerged()){
										//rsInformation has slash separated observed alleles
										observedAllelesSeparatedwithSlash = rsInformation.getObservedAlleles();								
										observedAllelesSeparatedwithTabs = convertSlashSeparatedAllelestoTabSeparatedAlleles(observedAllelesSeparatedwithSlash);									
										
										snpKey = Commons.RS +rsId + "_" +"chr" + rsInformation.getChrNamewithoutChr() + "_" + (rsInformation.getStartZeroBased()+1);
										
										snp = snpMap.get(snpKey);
										
										if(snp==null){
											
											snp = new SNP();
											snp.setChrNamewithoutPreceedingChr(rsInformation.getChrNamewithoutChr());
											
											snp.setSnpZeroBasedStartCoordinate(rsInformation.getStartZeroBased());									
											snp.setSnpOneBasedStartCoordinate(rsInformation.getStartZeroBased()+1);
											
											snp.setSnpZeroBasedEndCoordinate(rsInformation.getEndZeroBased());									
											snp.setSnpOneBasedEndCoordinate(rsInformation.getEndZeroBased()+1);
											
											snp.setLength(rsInformation.getEndZeroBased()-rsInformation.getStartZeroBased()+1);
											
											/*****************************************************************/
											/***Get fasta file and reference sequence for this snp starts*****/
											/*****************************************************************/
											//Get fasta file and reference sequence for this snp
											fastaFile = getDNASequence(snp.getChrNamewithoutPreceedingChr(),snp.getSnpOneBasedStartCoordinate()- Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION,snp.getSnpOneBasedEndCoordinate() + Commons.NUMBER_OF_BASES_AFTER_SNP_POSITION,chrName2RefSeqIdforGrch38Map);
											referenceSequence = getDNASequenceFromFastaFile(fastaFile);
											/*****************************************************************/
											/***Get fasta file and reference sequence for this snp ends*******/
											/*****************************************************************/
											
											snp.setReferenceSequence(referenceSequence);
											snp.setFastaFile(fastaFile);
											
											List<String> observedAlleles = new ArrayList<String>();
											snp.setObservedAlleles(observedAlleles);
											snp.getObservedAlleles().add(observedAllelesSeparatedwithTabs);
												
											snpMap.put(snpKey, snp);
											tfCellLineGivenInterval.getSnpKeyList().add(snpKey);
											snpKeyListInAGivenInterval.add(snpKey);
											
										}//End of IF snp is null
										else{
											if (!snp.getObservedAlleles().contains(observedAllelesSeparatedwithTabs)){
												snp.getObservedAlleles().add(observedAllelesSeparatedwithTabs);			
											}
											
											//Note that snpKey is already added to
											//tfKeggPathwayGivenInterval.getSnpKeyList() and
											//snpKeyList
											
										}//end of ELSE snp is not null		

									}
								}//End of IF rsInformation is not null																								
							}//End of for each rsId in a given interval
							
							
						}else{
							tfCellLineGivenInterval.setSnpKeyList(snpKeyListInAGivenInterval);
						}
						/*******************************************************************************************/
						/******************************PART1 snpKeyList ends****************************************/
						/**************************Getting snpKeyList in a given Interval***************************/
						/*************Check whether snps in this given interval are already found or not************/
						/*******************************************************************************************/

														
						/*******************************************************************************************/
						/***************************************PART2 starts****************************************/
						/***********************Add TfCellLineGivenIntervalTFIntervalKey****************************/
						/*******************************************************************************************/
						tfCellLineTfInterval= tfCellLineGivenIntervalTfInterval2Map.get(tfCellLineGivenIntervalTfIntervalKey);
						
						if(tfCellLineTfInterval==null){
							tfCellLineTfInterval = new TfCellLineTfInterval();
														
							//set attributes of tfCellLineTfInterval
							tfCellLineTfInterval.setStartOneBased(tfStartOneBased);
							tfCellLineTfInterval.setEndOneBased(tfEndOneBased);
							tfCellLineTfInterval.setChrNamewithoutPreceedingChr(chrNamewithoutPreceedingChr);						
							tfCellLineTfInterval.setTfNameCellLineName(tfNameCellLineName);
							
							tfCellLineGivenIntervalTfInterval2Map.put(tfCellLineGivenIntervalTfIntervalKey,tfCellLineTfInterval);
							tfCellLineGivenInterval.getTfCellLineBasedTfIntervalKeyList().add(tfCellLineGivenIntervalTfIntervalKey);
						}else{
							//do nothing
							//this tfInterval is already added to the map and list
						}
						/*******************************************************************************************/
						/***************************************PART2 ends******************************************/
						/***********************Add TfCellLineGivenIntervalTFIntervalKey****************************/
						/*******************************************************************************************/
						
							
					}
					/*****************************************************************************************************/
					/*******For this tfCellLine, this given interval is read for the first time ends**********************/
					/*****************************************************************************************************/					

					
					/*****************************************************************************************************/
					/*******For this tfCellLine, this given interval is read before***************************************/
					/********We already know the snps in this given interval**********************************************/
					/***************However new TF Intervals might  be added**********************************************/
					/*******************************************Starts****************************************************/
					/*****************************************************************************************************/
					else{
						
						tfCellLineTfInterval = tfCellLineGivenIntervalTfInterval2Map.get(tfCellLineGivenIntervalTfIntervalKey);
								
						if(tfCellLineTfInterval==null){
							tfCellLineTfInterval = new TfCellLineTfInterval();
							
							
							//set attributes of tfKeggPathwayTfInterval
							tfCellLineTfInterval.setStartOneBased(tfStartOneBased);
							tfCellLineTfInterval.setEndOneBased(tfEndOneBased);
							tfCellLineTfInterval.setChrNamewithoutPreceedingChr(chrNamewithoutPreceedingChr);						
							tfCellLineTfInterval.setTfNameCellLineName(tfNameCellLineName);
							
							tfCellLineGivenIntervalTfInterval2Map.put(tfCellLineGivenIntervalTfIntervalKey,tfCellLineTfInterval);
							tfCellLineGivenInterval.getTfCellLineBasedTfIntervalKeyList().add(tfCellLineGivenIntervalTfIntervalKey);
						}else{
							//do nothing
							//this tfInterval is already in the list
						}
						
					}
					/*****************************************************************************************************/
					/*******For this tfCellLine, this given interval is read before***************************************/
					/********We already know the snps in this given interval**********************************************/
					/***************However new TF Intervals might  be added**********************************************/
					/*******************************************Ends******************************************************/
					/*****************************************************************************************************/

																		
				}//End of if: strLine does not start with "*" and strLine does not contain "Search for"	
				
				
			}//End of while 
			/****************************************************************************************/
			/*********************Reading Augmented TF File Ends*************************************/
			/****************************************************************************************/
			
			
			//Close BufferedReader
			augmentedBufferedReader.close();
			
			//Write snp reference sequence
			//write snp observed alleles 
			//write snp altered sequences
			//write tf intervals overlapping with this snp
			//write extended peak sequence 
			List<String>  tfIntervalKeyList;
			
			List<String> snpBasedTfIntervalKeyList ;
						
			TfCellLineGivenInterval givenInterval;
			String snpDirectory;
			
			
			//Augmented file has been read
			//Now for each given interval
			//get the reference sequence, observed alleles and altered sequences of snps in this given interval and write them to files
			//get the tf intervals in this given interval
			//for each snp in this given interval
			//get the list of tf intervals overlapping with this snp
			//write these snp based tf intervals to a file
			//get the minStartZeroBased and maxEndZeroBased coordinates from these list of overlapping tf intervals
			//get the extended peak sequence starting at minStartZeroBased and ending at maxEndZeroBased for this snp Based tf intervals 
			for(Map.Entry<String, TfCellLineGivenInterval> entry: tfCellLineGivenInterval2Map.entrySet()){
//				givenIntervalKey = entry.getKey();
				givenInterval = entry.getValue();
				
				givenIntervalKey = givenInterval.getGivenIntervalName();
				
				//test
				tfNameCellLineName = givenInterval.getTfNameCellLineName();
				
				snpKeyListInAGivenInterval = givenInterval.getSnpKeyList();
				tfIntervalKeyList = givenInterval.getTfCellLineBasedTfIntervalKeyList();
				
				chrNamewithoutPreceedingChr = givenInterval.getChromNamewithoutPreceedingChr();
											
				for(String snpKeyString : snpKeyListInAGivenInterval ){
					snp = snpMap.get(snpKeyString);
					
					indexofFirstUnderscore = snpKeyString.indexOf('_');
					indexofSecondUnderscore = snpKeyString.indexOf('_',indexofFirstUnderscore+1);
					indexofThirdUnderscore = snpKeyString.indexOf('_',indexofSecondUnderscore+1);
						
									
					String snpKeyStringWithoutRsId = snpKeyString.substring(0,indexofThirdUnderscore);
								
					snpDirectory = tfNameCellLineName+  System.getProperty("file.separator") + givenIntervalKey + System.getProperty("file.separator") + snpKeyString;
										
					//write reference sequence
					createSequenceFile(outputFolder ,directoryBase,snpDirectory, "reference" + "_" + snpKeyStringWithoutRsId,snp.getFastaFile());
					
					//write observedAlleles
					createObservedAllelesFile(outputFolder,directoryBase, snpDirectory, "observedAlleles" + "_" + snpKeyStringWithoutRsId ,snp.getObservedAlleles());
					
					
				
					alteredSequences = getAlteredSNPSequences(snp.getReferenceSequence(),snp.getObservedAlleles(),Commons.ONE_BASED_SNP_POSITION, Commons.ONE_BASED_SNP_POSITION + snp.getLength()-1);
								
					//create Altered Sequences
					int alteredSNPSequenceCount = 0;
					for(String alteredSequence : alteredSequences){
						alteredSNPSequenceCount++;
						createSequenceFile(outputFolder,directoryBase, snpDirectory, "altered" + alteredSNPSequenceCount + "_" + snpKeyStringWithoutRsId,alteredSequence);	
					}
									
					//find overlapping tfIntervalkeys with this snp
					snpBasedTfIntervalKeyList = findTfIntervalsOverlappingWithThisSNP_forTf(tfIntervalKeyList, tfCellLineGivenIntervalTfInterval2Map,  snp.getSnpZeroBasedStartCoordinate(),snp.getSnpZeroBasedEndCoordinate());
					
					//get snp based extended peak sequence
					getSNPBasedPeakSequence_forTf(snpBasedTfIntervalKeyList,tfCellLineGivenIntervalTfInterval2Map,chrNamewithoutPreceedingChr,chrName2RefSeqIdforGrch38Map,outputFolder,directoryBase,snpDirectory,snpKeyString);

					//write snp based tf Intervals file
					createTfIntervalsFile_forTf(outputFolder,directoryBase,snpDirectory, "tfIntervals" + "_" + snpKeyStringWithoutRsId, snpBasedTfIntervalKeyList,tfCellLineGivenIntervalTfInterval2Map);
								
				}//End of for each snp in this given interval
							
				logger.info(entry.getKey());
				
			}//End of each given interval
			
						
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	//end ends
		
	
	public static void readAugmentedDataWriteSequencesandMatrices(
			AugmentationofGivenIntervalwithRsIds augofGivenInterval, 
			AugmentationofGivenRsIdwithInformation augofGivenRsId,
			Map<String,String> chrName2RefSeqIdforGrch38Map, 
			String outputFolder,
			String augmentedInputFileName, 
			Map<String,String> tfName2PfmMatrices, 
			Map<String,String> tfName2LogoMatrices,
			String enrichmentType){
		
		FileReader augmentedFileReader;
		BufferedReader augmentedBufferedReader;
				
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
			
		int indexofUnderscore;
		
		int indexofFirstUnderscore;
		int indexofSecondUnderscore;
		int indexofThirdUnderscore;
	
			
		String chrNamewithPreceedingChr = null;
		String chrNamewithoutPreceedingChr = null;
						
		int givenIntervalStartOneBased;
		int givenIntervalEndOneBased;

		int tfStartOneBased;
		int tfEndOneBased;
						
		String tfNameCellLineName;
		
		String tfName;
		String tfNameRemovedLastCharacter;
		String previousTfNameRemovedLastCharacter;
		
		boolean thereExistsPFMMatrix = false;
		boolean thereExistsLOGOMatrix = false;
		
		
		//These variables will be used jointly 
		//for Tf CellLine KeggPathway and
		//for Tf KeggPathway 
		String tfNameKeggPathwayName;
						
		//used for this pfm matrix file is already created and written.
		//No need for twice
		Map<String,Boolean> pfmMatrices2FalseorTrueMap 	= new HashMap<String,Boolean>();
		
		String fastaFile;
		String referenceSequence;
		String directoryBase = null;
					
		Boolean isThereAnExactTfNamePfmMatrix = false;
		
		//4 April 2014
		List<String> rsIdList;
		RsInformation rsInformation;
		String observedAllelesSeparatedwithSlash ;
				
		//7 April 2014 starts		
		//key must contain tf  KeggPathway chr startZeroBased endZeroBased
		Map<String,TfKeggPathwayGivenInterval> tfKeggPathwayBasedGivenIntervalMap = new HashMap<String,TfKeggPathwayGivenInterval>();
		
		//key must contain tf KeggPathway  tf CellLine chr startZeroBased endZeroBased		
		Map<String,TfKeggPathwayTfInterval> tfKeggPathwayBasedTfIntervalMap = new HashMap<String,TfKeggPathwayTfInterval>();
		
		//key must be chrNamewithPreceedingChr + givenIntervalStartZeroBased + givenIntervalEndZeroBased
		//this map contains the list of snp keys in a given interval
		Map<String,List<String>> givenIntervalBasedSnpMap = new HashMap<String,List<String>>();
		
		//key must contain chr startZeroBased endZeroBased
		Map<String,SNP> snpMap =  new HashMap<String,SNP>();
		
		TfKeggPathwayGivenInterval tfKeggPathwayGivenInterval = null;
		
		String tfKeggPathwayGivenIntervalKey;
		String tfKeggPathwayTfIntervalKey;
		String snpKey;	
//		String givenIntervalKey;
		
		String givenIntervalName;
		
		List<String> snpKeyList;
		//7 April 2014  ends
					
				
		//10 March 2014
		//Each observedAlleles String contains observed alleles which are separated by tabs, pay attention, there can be more than two observed alleles such as A\tG\tT\t-\tACG
		//Pay attention, for the same chrName and ChrPosition there can be more than one observedAlleles String. It is rare but possible.
		List<String> alteredSequences;
		String observedAllelesSeparatedwithTabs;
					
//		**************	hsa00380 Tryptophan metabolism - Homo sapiens (human)	**************											
//		NFKB_hsa00380	Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName
//		NFKB_hsa00380	chr1	89546803	89546803	NFKB_GM12878	89546683	89546992	NM_001008661	89468644	89558643	5D	CCBL2	56267	hsa00380

		try {
			augmentedFileReader = new FileReader(outputFolder + augmentedInputFileName);
			augmentedBufferedReader = new BufferedReader(augmentedFileReader);
			
			directoryBase = getDirectoryBase(enrichmentType);
													
			while((strLine = augmentedBufferedReader.readLine())!=null){
							
				//skip strLine starts with * or contains "Search for" which means it is a header line
				if (!(strLine.startsWith("*")) && !(strLine.contains("Search for"))){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
					indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
					
					tfNameKeggPathwayName = strLine.substring(0, indexofFirstTab);
					chrNamewithPreceedingChr =  strLine.substring(indexofFirstTab+1, indexofSecondTab);
					chrNamewithoutPreceedingChr = chrNamewithPreceedingChr.substring(3);
					
					//Used in finding list of rsIds in this given interval
					givenIntervalStartOneBased = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					givenIntervalEndOneBased =  Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
					
						
					tfNameCellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					tfStartOneBased = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					tfEndOneBased =  Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
					//Get tfName and keggPathwayName
					indexofUnderscore = tfNameKeggPathwayName.indexOf('_');
					tfName = tfNameKeggPathwayName.substring(0, indexofUnderscore);
										
					
					tfNameRemovedLastCharacter = tfName;
														
					//create pfmMatrices and logo Matrices for this tfNameKeggPathwayName
					//create pfm matrices and logo matrices files under tfNameKeggPathwayName starts
					//and do this only for once for an unique tfNameKeggPathwayName
					if(pfmMatrices2FalseorTrueMap.get(tfNameKeggPathwayName) == null){
												
						isThereAnExactTfNamePfmMatrix = false;
						
						//find pfm entry							
						for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
							if (pfmEntry.getKey().contains(tfName)){
								isThereAnExactTfNamePfmMatrix = true;
								createMatrixFile(outputFolder,directoryBase, tfNameKeggPathwayName,  "pfmMatrices_" + tfName,pfmEntry.getValue());
									
							}
						}//End of for
						
						
						//find logo entry
						for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
							if(logoEntry.getKey().contains(tfName)){
								createMatrixFile(outputFolder,directoryBase, tfNameKeggPathwayName, "logoMatrices_" +tfName,logoEntry.getValue());

							}
						}
					
						
						if (!isThereAnExactTfNamePfmMatrix){
							
							thereExistsPFMMatrix= false;
							thereExistsLOGOMatrix = false;
							
							while (!thereExistsPFMMatrix && !thereExistsLOGOMatrix){
								previousTfNameRemovedLastCharacter = tfNameRemovedLastCharacter;
								tfNameRemovedLastCharacter = removeLastCharacter(tfNameRemovedLastCharacter);
								
								//If no  change
								if(previousTfNameRemovedLastCharacter.equals(tfNameRemovedLastCharacter)){
									break;
								}
								
								//find pfm entry							
								for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
									if (pfmEntry.getKey().contains(tfNameRemovedLastCharacter)){
										thereExistsPFMMatrix = true;
										createMatrixFile(outputFolder,directoryBase, tfNameKeggPathwayName, "pfmMatrices_" + tfName,pfmEntry.getValue());																			
									}
								}//End of for PFM
								
								//find logo entry
								for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
									if(logoEntry.getKey().contains(tfNameRemovedLastCharacter)){
										thereExistsLOGOMatrix= true;
										createMatrixFile(outputFolder,directoryBase, tfNameKeggPathwayName, "logoMatrices_" +tfName,logoEntry.getValue());
									}
								}//End of for LOGO
								
							}//End of while

													
						}//End of If
						
						pfmMatrices2FalseorTrueMap.put(tfNameKeggPathwayName, true);
					} //End of if
					//create pfm matrices and logo matrices files ends

					//Set given interval name
					givenIntervalName ="givenInterval" + "_" + chrNamewithPreceedingChr + "_" + givenIntervalStartOneBased +  "_" + givenIntervalEndOneBased;
					
					//Create tfNameKeggPathwayName based given interval key					
					tfKeggPathwayGivenIntervalKey = tfNameKeggPathwayName + "_" + givenIntervalName;
		 					
					//Create tf interval key
					tfKeggPathwayTfIntervalKey = tfNameKeggPathwayName + "_" + givenIntervalName + "_" + tfNameCellLineName +  "_"  + "tfInterval" + "_" + chrNamewithPreceedingChr + "_" + tfStartOneBased + "_" + tfEndOneBased;
						
					//get the given interval
					tfKeggPathwayGivenInterval = tfKeggPathwayBasedGivenIntervalMap.get(tfKeggPathwayGivenIntervalKey);
					
					//For this tfKeggPathway, this given interval is read for the first time.					
					if (tfKeggPathwayGivenInterval==null){
						
						tfKeggPathwayGivenInterval = new TfKeggPathwayGivenInterval();
						
						tfKeggPathwayBasedGivenIntervalMap.put(tfKeggPathwayGivenIntervalKey, tfKeggPathwayGivenInterval);
						
						tfKeggPathwayGivenInterval.setChromNamewithoutPreceedingChr(chrNamewithoutPreceedingChr);
						tfKeggPathwayGivenInterval.setStartOneBased(givenIntervalStartOneBased);
						tfKeggPathwayGivenInterval.setEndOneBased(givenIntervalEndOneBased);
						
						tfKeggPathwayGivenInterval.setSnpKeyList(new ArrayList<String>());
						tfKeggPathwayGivenInterval.setTfKeggPathwayBasedTfIntervalKeyList(new ArrayList<String>());
						
						tfKeggPathwayGivenInterval.setGivenIntervalName(givenIntervalName);
						tfKeggPathwayGivenInterval.setTfNameKeggPathwayName(tfNameKeggPathwayName);
																								
						
						//part1
						//snpKeyList						
						//check whether for this given interval snps are already found
						snpKeyList = givenIntervalBasedSnpMap.get(givenIntervalName);
						
						if (snpKeyList==null){
							
							snpKeyList = new ArrayList<String>();
							givenIntervalBasedSnpMap.put(givenIntervalName,snpKeyList);
							
							//get all the rsIds in this given interval				
							//we have to provide one based coordinates for method arguments
							rsIdList = augofGivenInterval.getRsIdsInAGivenInterval(chrNamewithoutPreceedingChr, givenIntervalStartOneBased,givenIntervalEndOneBased);
													
							for(String rsId: rsIdList){
																	
								//For each rsId get rs Information
								rsInformation = augofGivenRsId.getInformationforGivenRsId(rsId);
								
								if(rsInformation!=null){
									if (!rsInformation.isMerged()){
										//rsInformation has slash separated observed alleles
										observedAllelesSeparatedwithSlash = rsInformation.getObservedAlleles();								
										observedAllelesSeparatedwithTabs = convertSlashSeparatedAllelestoTabSeparatedAlleles(observedAllelesSeparatedwithSlash);									
										
										snpKey = "snp" + "_" +"chr" + rsInformation.getChrNamewithoutChr() + "_" + (rsInformation.getStartZeroBased()+1) + "_" + Commons.RS +rsId;

										SNP snp = snpMap.get(snpKey);
										
										if(snp==null){
											
											snp = new SNP();
											snp.setChrNamewithoutPreceedingChr(rsInformation.getChrNamewithoutChr());
											
											snp.setSnpZeroBasedStartCoordinate(rsInformation.getStartZeroBased());									
											snp.setSnpOneBasedStartCoordinate(rsInformation.getStartZeroBased()+1);
											
											snp.setSnpZeroBasedEndCoordinate(rsInformation.getEndZeroBased());									
											snp.setSnpOneBasedEndCoordinate(rsInformation.getEndZeroBased()+1);
											
											snp.setLength(rsInformation.getEndZeroBased()-rsInformation.getStartZeroBased()+1);
											
											
											//get fasta file and reference sequence for this snp
											fastaFile = getDNASequence(snp.getChrNamewithoutPreceedingChr(),snp.getSnpOneBasedStartCoordinate()- Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION,snp.getSnpOneBasedEndCoordinate() + Commons.NUMBER_OF_BASES_AFTER_SNP_POSITION,chrName2RefSeqIdforGrch38Map);
											referenceSequence = getDNASequenceFromFastaFile(fastaFile);
											
											snp.setReferenceSequence(referenceSequence);
											snp.setFastaFile(fastaFile);
											
											List<String> observedAlleles = new ArrayList<String>();
											snp.setObservedAlleles(observedAlleles);
											snp.getObservedAlleles().add(observedAllelesSeparatedwithTabs);
												
											snpMap.put(snpKey, snp);
											tfKeggPathwayGivenInterval.getSnpKeyList().add(snpKey);
											snpKeyList.add(snpKey);
											
										}else{
											if (!snp.getObservedAlleles().contains(observedAllelesSeparatedwithTabs)){
												snp.getObservedAlleles().add(observedAllelesSeparatedwithTabs);			
											}
											
											//Note that snpKey is already added to
											//tfKeggPathwayGivenInterval.getSnpKeyList() and
											//snpKeyList
											
										}//end of else snp is not null	

									}//This rsId is not merged		
								}//End of IF rsInformation is not null
								//rsInformation is null for this rsId
								else{
									logger.debug("rsInformation is null for this rsId: " + rsId + " check is it merged or not!" );
								}
											
								
							}//End of for each rsId in a given interval
							
							
						}else{
							tfKeggPathwayGivenInterval.setSnpKeyList(snpKeyList);
						}
						
							
														
						//part2
						//tfNameandKeggPathway based tfIntervalKeyList
						
						if(tfKeggPathwayBasedTfIntervalMap.get(tfKeggPathwayTfIntervalKey)==null){
							TfKeggPathwayTfInterval tfKeggPathwayTfInterval = new TfKeggPathwayTfInterval();
														
							//set attributes of tfKeggPathwayTfInterval
							tfKeggPathwayTfInterval.setStartOneBased(tfStartOneBased);
							tfKeggPathwayTfInterval.setEndOneBased(tfEndOneBased);
							tfKeggPathwayTfInterval.setChrNamewithoutPreceedingChr(chrNamewithoutPreceedingChr);						
							tfKeggPathwayTfInterval.setTfNameCellLineName(tfNameCellLineName);
							tfKeggPathwayTfInterval.setTfNameKeggPathwayName(tfNameKeggPathwayName);
							
							tfKeggPathwayBasedTfIntervalMap.put(tfKeggPathwayTfIntervalKey,tfKeggPathwayTfInterval);
							tfKeggPathwayGivenInterval.getTfKeggPathwayBasedTfIntervalKeyList().add(tfKeggPathwayTfIntervalKey);
						}else{
							//do nothing
							//this tfInterval is already added to the map and list
						}
												
							
					}
					// this given interval is read before
					//we already know the snps in this given interval
					//however new tf Intervals might  be added.
					else{
						
						if(tfKeggPathwayBasedTfIntervalMap.get(tfKeggPathwayTfIntervalKey)==null){
							TfKeggPathwayTfInterval tfKeggPathwayTfInterval = new TfKeggPathwayTfInterval();
							
							
							//set attributes of tfKeggPathwayTfInterval
							tfKeggPathwayTfInterval.setStartOneBased(tfStartOneBased);
							tfKeggPathwayTfInterval.setEndOneBased(tfEndOneBased);
							tfKeggPathwayTfInterval.setChrNamewithoutPreceedingChr(chrNamewithoutPreceedingChr);						
							tfKeggPathwayTfInterval.setTfNameCellLineName(tfNameCellLineName);
							tfKeggPathwayTfInterval.setTfNameKeggPathwayName(tfNameKeggPathwayName);
							
							tfKeggPathwayBasedTfIntervalMap.put(tfKeggPathwayTfIntervalKey,tfKeggPathwayTfInterval);
							tfKeggPathwayGivenInterval.getTfKeggPathwayBasedTfIntervalKeyList().add(tfKeggPathwayTfIntervalKey);
						}else{
							//do nothing
							//this tfInterval is already in the list
						}
					
						
					}
																		
				}//End of if: strLine does not start with "*"	and contains "Search for"	
				
				
			}//End of while 
			
			//Write snp reference sequence
			//write snp observed alleles 
			//write snp altered sequences
			//write tf intervals overlapping with this snp
			//write extended peak sequence 
			List<String>  tfIntervalKeyList;
			
			List<String> snpBasedTfIntervalKeyList ;
						
			TfKeggPathwayGivenInterval givenInterval;
			String snpDirectory;
			
			
			//Augmented file has been read
			//Now for each given interval
			//get the reference sequence, observed alleles and altered sequences of snps in this given interval and write them to files
			//get the tf intervals in this given interval
			//for each snp in this given interval
			//get the list of tf intervals overlapping with this snp
			//write these snp based tf intervals to a file
			//get the minStartZeroBased and maxEndZeroBased coordinates from these list of overlapping tf intervals
			//get the extended peak sequence starting at minStartZeroBased and ending at maxEndZeroBased for this snp Based tf intervals 
			for(Map.Entry<String, TfKeggPathwayGivenInterval> entry: tfKeggPathwayBasedGivenIntervalMap.entrySet()){
//				givenIntervalKey = entry.getKey();
				givenInterval = entry.getValue();
				
				givenIntervalName = givenInterval.getGivenIntervalName();
				tfNameKeggPathwayName = givenInterval.getTfNameKeggPathwayName();
				
				snpKeyList = givenInterval.getSnpKeyList();
				tfIntervalKeyList = givenInterval.getTfKeggPathwayBasedTfIntervalKeyList();
				
				chrNamewithoutPreceedingChr = givenInterval.getChromNamewithoutPreceedingChr();
											
				for(String snpKeyString : snpKeyList ){
					SNP snp = snpMap.get(snpKeyString);
					
					indexofFirstUnderscore = snpKeyString.indexOf('_');
					indexofSecondUnderscore = snpKeyString.indexOf('_',indexofFirstUnderscore+1);
					indexofThirdUnderscore = snpKeyString.indexOf('_',indexofSecondUnderscore+1);
						
									
					String snpKeyStringWithoutRsId = snpKeyString.substring(0,indexofThirdUnderscore);
					
					
					snpDirectory = tfNameKeggPathwayName+  System.getProperty("file.separator") + givenIntervalName + System.getProperty("file.separator") + snpKeyString;
										
					//write reference sequence
					createSequenceFile(outputFolder ,directoryBase,snpDirectory, "reference" + "_" + snpKeyStringWithoutRsId,snp.getFastaFile());
					
					//write observedAlleles
					createObservedAllelesFile(outputFolder,directoryBase, snpDirectory, "observedAlleles" + "_" + snpKeyStringWithoutRsId ,snp.getObservedAlleles());
				
					
					alteredSequences = getAlteredSNPSequences(snp.getReferenceSequence(),snp.getObservedAlleles(),Commons.ONE_BASED_SNP_POSITION, Commons.ONE_BASED_SNP_POSITION + snp.getLength()-1);
								
					//create Altered Sequences
					int alteredSNPSequenceCount = 0;
					for(String alteredSequence : alteredSequences){
						alteredSNPSequenceCount++;
						createSequenceFile(outputFolder,directoryBase, snpDirectory, "altered" + alteredSNPSequenceCount + "_" + snpKeyStringWithoutRsId,alteredSequence);	
					}
						
									
					//find overlapping tfIntervalkeys with this snp
					snpBasedTfIntervalKeyList = findTfIntervalsOverlappingWithThisSNP(tfIntervalKeyList, tfKeggPathwayBasedTfIntervalMap,  snp.getSnpZeroBasedStartCoordinate(), snp.getSnpZeroBasedEndCoordinate());
					
					//get snp based extended peak sequence
					getSNPBasedPeakSequence(snpBasedTfIntervalKeyList,tfKeggPathwayBasedTfIntervalMap,chrNamewithoutPreceedingChr,chrName2RefSeqIdforGrch38Map,outputFolder,directoryBase,snpDirectory,snpKeyString);

					//write snp based tf Intervals file
					createTfIntervalsFile(outputFolder,directoryBase,snpDirectory, "tfIntervals" + "_" + snpKeyStringWithoutRsId, snpBasedTfIntervalKeyList,tfKeggPathwayBasedTfIntervalMap);
								
				}//End of for each snp in this given interval
							
				logger.info(entry.getKey());
				
			}//End of each given interval
			
						
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	//TF KEGGPATHWAY ends

	
	
	//Pay Attention
	//Contains X for chrX
	//Contains 1 for chr1
	
	//# Sequence-Name	Sequence-Role	Assigned-Molecule	Assigned-Molecule-Location/Type	GenBank-Accn	Relationship	RefSeq-Accn	Assembly-Unit
	//1	assembled-molecule	1	Chromosome	CM000663.1	=	NC_000001.10	Primary Assembly
	//X	assembled-molecule	X	Chromosome	CM000685.1	=	NC_000023.10	Primary Assembly
	public static void fillMap(String dataFolder, String refSeqIdsforGRChXXInputFile,Map<String,String> chrName2RefSeqIdforGrchXXMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String chrName;
		String refSeqId;
		
		try {
			fileReader = new FileReader(dataFolder + refSeqIdsforGRChXXInputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(strLine.startsWith("#")){
					continue;
				}else{
					if (count<numberofChromosomesinHomoSapiens){
						count++;
						
						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t', indexofSixthTab+1);
						
						chrName = strLine.substring(0, indexofFirstTab);
						refSeqId = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
						
						chrName2RefSeqIdforGrchXXMap.put(chrName, refSeqId);
						continue;
						
					}
				}
					
				break;				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Pay Attention
	//Contains X for chrX
	//Contains 1 for chr1	
	//# Sequence-Name	Sequence-Role	Assigned-Molecule	Assigned-Molecule-Location/Type	GenBank-Accn	Relationship	RefSeq-Accn	Assembly-Unit
	//1	assembled-molecule	1	Chromosome	CM000663.1	=	NC_000001.10	Primary Assembly
	//X	assembled-molecule	X	Chromosome	CM000685.1	=	NC_000023.10	Primary Assembly
	public Map<String,String> fillMap(String refSeqIdsforGRCh37InputFile){
		
		Map<String,String> chrName2RefSeqIdforGrch37Map = new HashMap<String,String>();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine = null;
		int numberofChromosomesinHomoSapiens = 24;
		int count = 0;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String chrName;
		String refSeqId;
		
		try {
			fileReader = new FileReader(refSeqIdsforGRCh37InputFile);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(strLine.startsWith("#")){
					continue;
				}else{
					if (count<numberofChromosomesinHomoSapiens){
						count++;
						
						indexofFirstTab 	= strLine.indexOf('\t');
						indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
						indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
						indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
						indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
						indexofSeventhTab 	= strLine.indexOf('\t', indexofSixthTab+1);
						
						chrName = strLine.substring(0, indexofFirstTab);
						refSeqId = strLine.substring(indexofSixthTab+1, indexofSeventhTab);
						
						chrName2RefSeqIdforGrch37Map.put(chrName, refSeqId);
						continue;
						
					}
				}
					
				break;
				
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chrName2RefSeqIdforGrch37Map;
	}
	
		
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Input File Format	
	//			--->	default	Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, 
	//					default 1
	//args[4]	--->	Perform Enrichment parameter
	//			--->	default	Commons.DO_ENRICH
	//			--->			Commons.DO_NOT_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Level, default 0.05
	//args[8]	--->	Bonferroni Correction Significance Criteria, default 0.05
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
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	UserDefinedGeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	UserDefinedGeneSet InputFile 
	//args[24]	--->	UserDefinedGeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	UserDefinedGeneSet	Name
	//args[26]	--->	UserDefinedGeneSet 	Optional GeneSet Description InputFile
	//args[27]  --->	UserDefinedLibrary Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	UserDefinedLibrary InputFile
	//args[29] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.
	public static void main(String[] args) {
		
		
		String glanetFolder = args[1];
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = Commons.NO_NAME;
		}
		//jobName ends
				
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
				
		//TfEnrichment, DO or DO_NOT
		EnrichmentType tfEnrichment = EnrichmentType.convertStringtoEnum(args[12]);
			
		//TfKeggPathway Enrichment, DO or DO_NOT
		EnrichmentType tfKeggPathwayEnrichment = EnrichmentType.convertStringtoEnum(args[14]);
		
		//TfCellLineKeggPathway Enrichment, DO or DO_NOT
		EnrichmentType tfCellLineKeggPathwayEnrichment = EnrichmentType.convertStringtoEnum(args[15]);

		//pfm matrices
		String encodeMotifsInputFileName 	= Commons.ENCODE_MOTIFS ;		
		String jasparCoreInputFileName 		= Commons.JASPAR_CORE;
		
		
		//TF
		String augmentedTfInputFileName = Commons.AUGMENTED_TF_RESULTS_1BASED_START_END_GRCH38_COORDINATES;
	
		//TFKEGGPathway
		String augmentedTfExonBasedKeggPathwayInputFileName 		= Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES;
		String augmentedTfRegulationBasedKeggPathwayInputFileName 	= Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES ;
		String augmentedTfAllBasedKeggPathwayInputFileName 			= Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES ;		
				
		//TFCellLineKEGGPathway
		String augmentedTfCellLineExonBasedKeggPathwayInputFileName 		= Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES ;
		String augmentedTfCellLineRegulationBasedKeggPathwayInputFileName 	= Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES ;
		String augmentedTfCellLineAllBasedKeggPathwayInputFileName 			= Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_1BASED_START_END_GRCH38_COORDINATES ;
		
		//Example Data
		//7 NC_000007.13  GRCh37
//		Chromosome 7	CM000669.2	=	NC_000007.14	0 GRCh37
		Map<String,String> chrName2RefSeqIdforGrch38Map = new HashMap<String,String>();
		
				
		//@todo We have to update this file regularly
		//Construct map for refSeq Ids of homo sapiens chromosomes for GRCh37
		String refSeqIdsforGRCh38InputFile = Commons.REFSEQ_IDS_FOR_GRCH38_INPUT_FILE;
		fillMap(dataFolder,refSeqIdsforGRCh38InputFile,chrName2RefSeqIdforGrch38Map);
							
					
//		//Before each run
//		//delete directories and files under base directories
		FileOperations.deleteOldFiles(outputFolder + Commons.GENERATION_OF_REFERENCE_AND_ALTERED_SEQUENCES_OUTPUT_FOLDER);
				
		//Construct pfm matrices from encode-motif.txt file
		//A tf can have more than one pfm matrices
		//Take the transpose of given matrices in encode-motif.txt
		//Write the matrices in tab format for RSAT tool
		Map<String,String> tfName2PfmMatrices = new HashMap<String,String>();
		
		Map<String,String> tfName2LogoMatrices = new HashMap<String,String>();
		
			
		//Construct position frequency matrices from Encode Motifs
		constructPfmMatricesfromEncodeMotifs(dataFolder,encodeMotifsInputFileName,tfName2PfmMatrices);
		
		//Construct logo matrices from Encode Motifs
		constructLogoMatricesfromEncodeMotifs(dataFolder,encodeMotifsInputFileName,tfName2LogoMatrices);
		
		//Construct position frequency matrices from Jaspar Core 
		//Construct logo matrices from Jaspar Core
		constructPfmMatricesandLogoMatricesfromJasparCore(dataFolder,jasparCoreInputFileName,tfName2PfmMatrices,tfName2LogoMatrices);
		
		
		AugmentationofGivenIntervalwithRsIds augmentationOfAGivenIntervalWithRsIDs;
		AugmentationofGivenRsIdwithInformation augmentationOfAGivenRsIdWithInformation ;
		
		try {
			augmentationOfAGivenIntervalWithRsIDs = new AugmentationofGivenIntervalwithRsIds();
			augmentationOfAGivenRsIdWithInformation = new AugmentationofGivenRsIdwithInformation();
			
			
			//ONLY TF ENRICHMENT CASE
			if (tfEnrichment.isTfEnrichment() && 
				!tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() &&
				!tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
				
				//TF
				//generate sequences and matrices for enriched tf elements
				readAugmentedDataWriteSequencesandMatrices_forTf(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF);
			}
			
			//ONLY TFKEGGPATHWAY ENRICHMENT CASE					
			if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() &&
				!tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
				
				//TF
				//generate sequences and matrices for enriched tf elements
				readAugmentedDataWriteSequencesandMatrices_forTf(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF);

				//TFKEGGPATHWAY
				//Using tfName2PfmMatrices
				//Using snps for Enriched TfandKeggPathway
				//Output dnaSequences for TfandKeggPathway
				//Output pfmMatrices for TfandKeggPathway
				readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfExonBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_EXON_BASED_KEGG_PATHWAY);
				readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfRegulationBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
				readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfAllBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_ALL_BASED_KEGG_PATHWAY);	
			}
			
			//ONLY TFCELLLINEKEGGPATHWAY ENRICHMENT CASE					
			if(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment() &&
					!tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){
				
				//TF
				//generate sequences and matrices for enriched tf elements
				readAugmentedDataWriteSequencesandMatrices_forTf(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF);

				//TFCELLLINEKEGGPATHWAY
				//Using tfName2PfmMatrices
				//Using snps for Enriched Tf CellLine KeggPathway
				//Output dnaSequences for Tf CellLine KeggPathway
				//Output pfmMatrices for Tf CellLine KeggPathway
				readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfCellLineExonBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfCellLineRegulationBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfCellLineAllBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
	
			}
			
			//BOTH TFKEGGPATHWAY and TFCELLLINEKEGGPATHWAY
			if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() &&
					tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
					
					//TF
					//generate sequences and matrices for enriched tf elements
					readAugmentedDataWriteSequencesandMatrices_forTf(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF);

					//TFKEGGPATHWAY
					//Using tfName2PfmMatrices
					//Using snps for Enriched TfandKeggPathway
					//Output dnaSequences for TfandKeggPathway
					//Output pfmMatrices for TfandKeggPathway
					readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfExonBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_EXON_BASED_KEGG_PATHWAY);
					readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfRegulationBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
					readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfAllBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_ALL_BASED_KEGG_PATHWAY);
					
					//TFCELLLINEKEGGPATHWAY
					//Using tfName2PfmMatrices
					//Using snps for Enriched Tf CellLine KeggPathway
					//Output dnaSequences for Tf CellLine KeggPathway
					//Output pfmMatrices for Tf CellLine KeggPathway
					readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfCellLineExonBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
					readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfCellLineRegulationBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
					readAugmentedDataWriteSequencesandMatrices(augmentationOfAGivenIntervalWithRsIDs,augmentationOfAGivenRsIdWithInformation,chrName2RefSeqIdforGrch38Map,outputFolder,augmentedTfCellLineAllBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	



}
