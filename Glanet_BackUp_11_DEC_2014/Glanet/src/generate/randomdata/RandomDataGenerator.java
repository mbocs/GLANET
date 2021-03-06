/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 11:26:12 PM
 * 2013
 *
 * 
 */
package generate.randomdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import mapabilityandgc.GC;
import mapabilityandgc.Mapability;

import common.Commons;

import enrichment.GCCharArray;
import enrichment.InputLine;
import enrichment.MapabilityFloatArray;
import enumtypes.ChromosomeName;
import enumtypes.GenerateRandomDataMode;

public class RandomDataGenerator {
	
	//todo
	public static List<InputLine>  generateRandomData(GCCharArray gcCharArray, MapabilityFloatArray mapabilityDoubleArray,int chromSize, ChromosomeName chromName, List<InputLine> chromosomeBasedOriginalInputLines, ThreadLocalRandom threadLocalRandom,GenerateRandomDataMode generateRandomDataMode){
		
		List<InputLine> randomlyGeneratedInputLines = null;
		
		InputLine originalInputLine;
		InputLine randomlyGeneratedLine;
		int low;
		int high;
		int length;
		
		
		float differencebetweenGCs;
		float differencebetweenMapabilities;
		
		float dynamicGCThreshold;
		float dynamicMapabilityThreshold;
		
		int count;
		int counterThreshold;
		
		//for logging purposes
//		FileWriter fileWriter;
//		BufferedWriter bufferedWriter =null;
		
//		DecimalFormat df = new DecimalFormat("#.######");
	      
		
		if (generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
			
					
					randomlyGeneratedInputLines = new ArrayList<InputLine>();
					
					for(int j=0; j< chromosomeBasedOriginalInputLines.size(); j++){
						
						originalInputLine = chromosomeBasedOriginalInputLines.get(j);
						length = originalInputLine.getLength();
						
						//low must be greater than or equal to zero
						//high must be less than chromSize
						low = threadLocalRandom.nextInt(chromSize-length+1);
						high = low + length - 1 ;
								
						randomlyGeneratedLine =new InputLine(chromName, low, high);
						randomlyGeneratedInputLines.add(randomlyGeneratedLine);		
					}//End of for: each original input line
					
				
			
			
			
		}else if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
			
			//for logging purposes
//			try {
//				fileWriter = new FileWriter(Commons.RANDOM_DATA_GENERATION_LOG_FILE,true);
//				bufferedWriter = new BufferedWriter(fileWriter);
				
				randomlyGeneratedInputLines = new ArrayList<InputLine>();
				
				for(int j=0; j< chromosomeBasedOriginalInputLines.size(); j++){
					
					//ORIGINAL INPUT DATA
					originalInputLine = chromosomeBasedOriginalInputLines.get(j);
					
					GC.calculateGCofInterval(originalInputLine, gcCharArray);				
					Mapability.calculateMapabilityofIntervalUsingArray(originalInputLine,mapabilityDoubleArray);					
					length = originalInputLine.getLength();
					
					//RANDOM INPUT DATA
					//generate random data
					//low must be greater than or equal to zero
					//high must be less than chromSize
					low = threadLocalRandom.nextInt(chromSize-length+1);
					high = low + length - 1 ;
					
					randomlyGeneratedLine =new InputLine(chromName, low, high);
					
					GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
					differencebetweenGCs = GC.differenceofGCs(randomlyGeneratedLine, originalInputLine);
					
					Mapability.calculateMapabilityofIntervalUsingArray(randomlyGeneratedLine, mapabilityDoubleArray);
					differencebetweenMapabilities = Mapability.differenceofMapabilities(randomlyGeneratedLine, originalInputLine);
					
					count =0;
					counterThreshold = Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
					
					dynamicGCThreshold = Commons.GC_THRESHOLD_LOWER_VALUE;
					dynamicMapabilityThreshold = Commons.MAPABILITY_THRESHOLD_LOWER_VALUE;
					
					while(differencebetweenGCs>dynamicGCThreshold || differencebetweenMapabilities > dynamicMapabilityThreshold){
						count++;
						
						if (count>counterThreshold){
							
							   if (differencebetweenGCs>dynamicGCThreshold){
								   if (!(dynamicGCThreshold>=Commons.GC_THRESHOLD_UPPER_VALUE)){
									   
									   if (count>Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL){
											dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_004;	
									   }else if (count>Commons.NUMBER_OF_TRIAL_THIRD_LEVEL){
											dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_003;	
									   }else if (count>Commons.NUMBER_OF_TRIAL_SECOND_LEVEL){
											dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_002;
									   }else{
											dynamicGCThreshold = dynamicGCThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_001;	 
									   }
																	   }
							   }
							   
							   if (differencebetweenMapabilities>dynamicMapabilityThreshold){ 
								   if(!(dynamicMapabilityThreshold>=Commons.MAPABILITY_THRESHOLD_UPPER_VALUE)){
									   
									   if (count>Commons.NUMBER_OF_TRIAL_FOURTH_LEVEL){
											dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_004;	
									   }else if (count>Commons.NUMBER_OF_TRIAL_THIRD_LEVEL){
										   dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_003;	
									   }else if (count>Commons.NUMBER_OF_TRIAL_SECOND_LEVEL){
										   dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_002;
									   }else{
										   dynamicMapabilityThreshold = dynamicMapabilityThreshold + Commons.THRESHOLD_INCREASE_VALUE_0_POINT_001;	 
									   }
									   
									   
								   }
							   }
								
							  counterThreshold = counterThreshold + Commons.NUMBER_OF_TRIAL_FIRST_LEVEL;
						}
		
						
						//low must be greater than or equal to zero
						//high must be less than chromSize
						low = threadLocalRandom.nextInt(chromSize-length+1);
						high = low + length - 1 ;
						
						randomlyGeneratedLine =new InputLine(chromName, low, high);
						
						GC.calculateGCofInterval(randomlyGeneratedLine, gcCharArray);
						differencebetweenGCs = GC.differenceofGCs(randomlyGeneratedLine, originalInputLine);
						
						Mapability.calculateMapabilityofIntervalUsingArray(randomlyGeneratedLine, mapabilityDoubleArray);
						differencebetweenMapabilities =  Mapability.differenceofMapabilities(randomlyGeneratedLine, originalInputLine);
						
					}//End of While
						
					if (count>Commons.NUMBER_OF_TRIAL_FIRST_LEVEL){
//						bufferedWriter.write("Numberof Trial" + "\t" + count +"\t" + "dynamicGCThreshold"+ "\t" + df.format(dynamicGCThreshold) + "\t" +"dynamicMapabilityThreshold" + "\t" + df.format(dynamicMapabilityThreshold) + "\t"  +"original input line gc" + "\t" + df.format(originalInputLine.getGcContent()) + "\t" + "original input line mapability" + "\t" + df.format(originalInputLine.getMapability())+ "\t" + "chromName" + "\t" +chromName +"\n" );
					}
					
					randomlyGeneratedInputLines.add(randomlyGeneratedLine);		
				}//End of for: each original input line
				
			
//				bufferedWriter.close();
			
			
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		}//End of IF
		
		return randomlyGeneratedInputLines;
	}		
	//todo 
	
	
	
	
	//generate random data
	public static void generateRandomData(List<InputLine>  randomlyGeneratedData, List<InputLine> originalInputData,Random myRandom,Integer chromSize,ChromosomeName chromName){
		
		InputLine originalLine;
		InputLine randomlyGeneratedLine;
		int low;
		int high;
		int length;
		
		for(int i= 0;i<originalInputData.size(); i++){
			originalLine = originalInputData.get(i);
			length = originalLine.getLength();
			
			//low must be greater than or equal to zero
			//high must be less than chromSize
			
			low = myRandom.nextInt(chromSize-length+1);
			high = low + length - 1 ;
		
			
			randomlyGeneratedLine =new InputLine(chromName, low, high);
			randomlyGeneratedData.add(randomlyGeneratedLine);
		}
		
	}

	

}
