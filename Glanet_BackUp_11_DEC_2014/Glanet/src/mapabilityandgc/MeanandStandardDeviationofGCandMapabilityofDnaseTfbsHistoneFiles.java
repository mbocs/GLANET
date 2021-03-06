/**
 * @author Burcak Otlu
 * Sep 9, 2013
 * 4:58:05 PM
 * 2013
 *
 * It takes 35 minutes.
 */
package mapabilityandgc;

import hg19.GRCh37Hg19Chromosome;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ui.GlanetRunner;

import common.Commons;

import create.encode.CellLineHistone;
import create.encode.CellLineTranscriptionFactor;
import create.encode.CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles;
import enrichment.GCCharArray;
import enumtypes.ChromosomeName;

public class MeanandStandardDeviationofGCandMapabilityofDnaseTfbsHistoneFiles {

	/**
	 * 
	 */
	public MeanandStandardDeviationofGCandMapabilityofDnaseTfbsHistoneFiles() {
		// TODO Auto-generated constructor stub
	}
	
	
	//todo
	public static void writeTenDifferentMeanFiles(String fileName, Map<String,MeanandStandardDeviation> treeMap){
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		String name;			
		MeanandStandardDeviation values;
		
		//We must get the ten files key and value data
		//First file with lowest mean
		//and second file with highest mean must be included.
		//Rest of the eight files must be choses.
		
		int count =1;
		int index = 1;
		int size = treeMap.entrySet().size();
		int incrementValue = ((size-1)/8);
		
	
		try {
			fileWriter = new FileWriter(fileName);
			bufferedWriter = new BufferedWriter(fileWriter);
					
			
			for (Map.Entry<String, MeanandStandardDeviation> entry: treeMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();
				
				//get the first file with mean mean and last file width max mean
				if((count == 1) || (count ==size)){
					bufferedWriter.write(name + "\t" + values.getName()+ "\t" + "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" +"mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");
					index = index + incrementValue;
				
				}else if (count == index){
						bufferedWriter.write(name + "\t" + values.getName()+ "\t" + "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" +"mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");
						index = index + incrementValue;
					
				}else{
					//Skip this file
				}
							
				count++;
								
				if (count>size){
					break;
				}
				
			}//End of FOR
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
    
	
  	public static void writeTopTenMostVaryingStdDevResultstoFiles(String fileName, Map<String,MeanandStandardDeviation> treeMap){
  		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		
		String name;			
		MeanandStandardDeviation values;
		
		int count =0;
	
		try {
			fileWriter = new FileWriter(fileName);
			bufferedWriter = new BufferedWriter(fileWriter);
					
			for (Map.Entry<String, MeanandStandardDeviation> entry: treeMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();
				count++;
				
				if (count>10)
					break;
				
				bufferedWriter.write(name + "\t" + values.getName()+ "\t" + "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" +"mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");
			}//End of FOR
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
  	}
    
	
	public static void writeMeanandStdDevResultstoFiles(String fileName,Map<String,MeanandStandardDeviation> hashMap){
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		
		String name;			
		MeanandStandardDeviation values;
	
		try {
			fileWriter = new FileWriter(fileName);
			bufferedWriter = new BufferedWriter(fileWriter);
					
			for (Map.Entry<String, MeanandStandardDeviation> entry: hashMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();
				
				bufferedWriter.write(name + "\t" + values.getName()+ "\t" + "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" +"mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");
			}//End of FOR
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void writeMeanandStdDevResultstoFiles(String fileName,Map<String,MeanandStandardDeviation> dnaseHashMap, Map<String,MeanandStandardDeviation> tfbsHashMap, Map<String,MeanandStandardDeviation> histoneHashMap){
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		try {
			fileWriter = new FileWriter(fileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			String name;
			MeanandStandardDeviation values;
			
			//Dnase
			for (Map.Entry<String, MeanandStandardDeviation> entry: dnaseHashMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();

				bufferedWriter.write(name + "\t" + values.getName()+ "\t" +    "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" + "mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");	
			}
			
			//Tfbs
			for (Map.Entry<String, MeanandStandardDeviation> entry: tfbsHashMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();

				bufferedWriter.write(name + "\t" + values.getName()+ "\t" +    "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" + "mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");	
			}
			
			//Histone
			for (Map.Entry<String, MeanandStandardDeviation> entry: histoneHashMap.entrySet()){
				name = entry.getKey();
				values = entry.getValue();

				bufferedWriter.write(name + "\t" + values.getName()+ "\t" +    "number of intervals" + "\t" + values.getNumberofIntervals() + "\t" + "mean" + "\t" + values.getMean() + "\t" + "std dev" + "\t" +values.getStandardDeviation() + "\n");	
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	
	//todo
	//GC
	public static void calculateStandardDeviationGC(String outputFolder,String functionalElementType, Map<String,MeanandStandardDeviation> gcHashMap){
		String name;
		String functionalElementName;
		
		MeanandStandardDeviation meanandStandardDeviation;
		
		String fileName;
		String gcFileName=null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		float gc;
		float mean;
		float difference;
		float squareofDifference;
		float standardDeviation;
		
		float sumofSquaresofDifferences;
		
		
		for(Map.Entry<String, MeanandStandardDeviation> entry: gcHashMap.entrySet()){
			//Commons.GC+"\t"+ functionalElementType + "\t" + inputFileName
			name = entry.getKey();
			
			indexofFirstTab = name.indexOf('\t');
			indexofSecondTab = name.indexOf('\t',indexofFirstTab+1);
			
			functionalElementName = name.substring(indexofFirstTab+1, indexofSecondTab);
			
			if (functionalElementName.contains(functionalElementType)) {
				
				meanandStandardDeviation = entry.getValue();
				
				//gcFileName = Commons.DOKTORA_ECLIPSE_WORKSPACE +"src\\mapabilityandgc\\output\\Augmentation\\FunctionalElementFileBased\\Dnase\\Gc\\" + Commons.GC + "_" + fileName;
	 			
				fileName = name.substring(indexofSecondTab+1);
				
				if (Commons.DNASE.equals(functionalElementType)){
					gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\Gc\\" + Commons.GC + "_" + fileName;					
				}else if (Commons.TF.equals(functionalElementType)){
					gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\Gc\\" + Commons.GC + "_" + fileName;					
				}else if (Commons.HISTONE.equals(functionalElementType)){
					gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\Gc\\" + Commons.GC + "_" + fileName;					
				}
				
				
				try {
					fileReader = new FileReader(gcFileName);
					bufferedReader = new BufferedReader(fileReader);
					mean = meanandStandardDeviation.getMean();
					
					//important
					//initialize to zero
					sumofSquaresofDifferences =0;
					
					while((strLine= bufferedReader.readLine())!=null){
						gc = Float.parseFloat(strLine); 
						difference = gc-mean;
						squareofDifference= difference * difference;
						sumofSquaresofDifferences = sumofSquaresofDifferences + squareofDifference;
						 
					}
					
					standardDeviation = (float) Math.sqrt(sumofSquaresofDifferences/meanandStandardDeviation.getNumberofIntervals());
					meanandStandardDeviation.setStandardDeviation(standardDeviation);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//End of if
			
			
		}//End of for each entry in the gcHashMap
	
	}
	
	
	//Mapability
	//mean has been calculated for each file
	public static void calculateStandardDeviationMapability(String outputFolder,String functionalElementType, Map<String,MeanandStandardDeviation> mapabilityHashMap){
		String name;
			
		MeanandStandardDeviation meanandStandardDeviation;
		
		String fileName;
		String mapabilityFileName=null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		float mapability;
		float mean;
		float difference;
		float squareofDifference;
		float standardDeviation;
		
		float sumofSquaresofDifferences;
		
		
		for(Map.Entry<String, MeanandStandardDeviation> entry: mapabilityHashMap.entrySet()){
			//Commons.MAPABILITY +"\t"+ functionalElementType + "\t" + inputFileName
			name = entry.getKey();
						
			indexofFirstTab = name.indexOf('\t');
			indexofSecondTab = name.indexOf('\t',indexofFirstTab+1);
						
			meanandStandardDeviation = entry.getValue();
				
			//mapabilityFileName = Commons.DOKTORA_ECLIPSE_WORKSPACE +"src\\mapabilityandgc\\output\\Augmentation\\FunctionalElementFileBased\\Dnase\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
 			
			fileName = name.substring(indexofSecondTab+1);
			
			if (Commons.DNASE.equals(functionalElementType)){
				mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;					
			}else if (Commons.TF.equals(functionalElementType)){
				mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;					
			}else if (Commons.HISTONE.equals(functionalElementType)){
				mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;					
			}
			
			
			try {
				fileReader = new FileReader(mapabilityFileName);
				bufferedReader = new BufferedReader(fileReader);
				mean = meanandStandardDeviation.getMean();
				
				//important
				//initialize to zero
				sumofSquaresofDifferences =0;
				
				
				while((strLine= bufferedReader.readLine())!=null){
					mapability = Float.parseFloat(strLine); 
					difference = mapability-mean;
					squareofDifference= difference * difference;
					sumofSquaresofDifferences = sumofSquaresofDifferences + squareofDifference;
					 
				}
				
				standardDeviation = (float) Math.sqrt(sumofSquaresofDifferences/meanandStandardDeviation.getNumberofIntervals());
				meanandStandardDeviation.setStandardDeviation(standardDeviation);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of FOR
	}
	
	
	
	
	//todo
	//GC
	public static void calculateMeanGC(String inputFileName, String inputFilePath, String gcFileName,Map<String, MeanandStandardDeviation> gcHashMap,GCCharArray gcCharArray,String functionalElementType){
		FileReader fileReader =null;
    	BufferedReader bufferedReader = null;
    	String strLine;
    	
    	int indexofFirstTab;
    	int indexofSecondTab;
    	int indexofThirdTab;
    	String chromName;
    	
    	int low;
    	int high;
    	float gc;
    	
    	FileWriter fileWriter = null;
    	BufferedWriter bufferedWriter = null;
    	
		 // Open the file that is the first 		    			  		    			  
		try {
			   fileReader = new FileReader(inputFilePath);
			   bufferedReader = new BufferedReader(fileReader);
			   
			   //Write in append mode
			   fileWriter = new FileWriter(gcFileName,true); 
			   bufferedWriter = new BufferedWriter(fileWriter);
			   
			   //Commons.GC +	"\t" + functionalElementType + "\t" + fileName
			   MeanandStandardDeviation meanandStdDev = gcHashMap.get(Commons.GC + "\t" + functionalElementType + "\t" + inputFileName);
			    
			   if(Commons.DNASE.equals(functionalElementType)){
//	        		Get the cell line name from file name
					String cellLineDnase = null;
					cellLineDnase = CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineName(inputFileName);
					meanandStdDev.setName(cellLineDnase);
					cellLineDnase = null;

			   }else if(Commons.TF.equals(functionalElementType)){
//	       			Get the cell line name and transcription factor name from file name		
				   CellLineTranscriptionFactor cellLineandTranscriptionFactorName  = new CellLineTranscriptionFactor();
				   CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineNameandTranscriptionFactorName(cellLineandTranscriptionFactorName,inputFileName);
				   meanandStdDev.setName(cellLineandTranscriptionFactorName.getCellLineName() + "_" +cellLineandTranscriptionFactorName.getTranscriptionFactorName());
			   
			   }else if(Commons.HISTONE.equals(functionalElementType)){
//	       			Get the cell line name and histone name from file name
				   CellLineHistone cellLineNameHistoneName  = new CellLineHistone();
				   CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineNameandHistoneName(cellLineNameHistoneName,inputFileName);
				   meanandStdDev.setName(cellLineNameHistoneName.getCellLineName() + "_" + cellLineNameHistoneName.getHistoneName());
			   }
				
				  
				while ((strLine = bufferedReader.readLine()) != null)   {
					//chr7	151332800	151332950	.	0	.	13	5.89646	-1	-1
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					
					chromName = strLine.substring(0, indexofFirstTab);
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
					gc = GC.calculateGC(low, high, gcCharArray);
					
					meanandStdDev.setNumberofIntervals(meanandStdDev.getNumberofIntervals()+1);
					meanandStdDev.setSumofGCs(meanandStdDev.getSumofGCs()+ gc);
					
					bufferedWriter.write(gc + "\n");		
				}
						
				
				//Close the Buffered Reader
				bufferedReader.close();
				bufferedWriter.close();				
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 	

	}
	
	
	
	
	//Mapability
	public static void calculateMeanMapability(String inputFileName, String inputFilePath, String mapabilityFileName, Map<String,MeanandStandardDeviation> mapabilityHashMap,IntervalTree mapabilityIntervalTree,String functionalElementType){
		FileReader fileReader =null;
    	BufferedReader bufferedReader = null;
    	String strLine;
    	
    	int indexofFirstTab;
    	int indexofSecondTab;
    	int indexofThirdTab;
    	String chromName;
    	
    	int low;
    	int high;
    	float mapability;
    	
    	FileWriter fileWriter = null;
    	BufferedWriter bufferedWriter = null;
    	
		 // Open the file that is the first 		    			  		    			  
		try {
			   fileReader = new FileReader(inputFilePath);
			   bufferedReader = new BufferedReader(fileReader);
			   
			   //Write in append mode
			   fileWriter = new FileWriter(mapabilityFileName,true); 
			   bufferedWriter = new BufferedWriter(fileWriter);
			   
			   MeanandStandardDeviation meanandStdDev = mapabilityHashMap.get(Commons.MAPABILITY + "\t" + functionalElementType + "\t" + inputFileName);
			    
			   if(Commons.DNASE.equals(functionalElementType)){
//	        		Get the cell line name from file name
					String cellLineDnase = null;
					cellLineDnase = CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineName(inputFileName);
					meanandStdDev.setName(cellLineDnase);
					cellLineDnase = null;

			   }else if(Commons.TF.equals(functionalElementType)){
				   
//	       			Get the cell line name and transcription factor name from file name		
				   CellLineTranscriptionFactor cellLineandTranscriptionFactorName  = new CellLineTranscriptionFactor();
				   CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineNameandTranscriptionFactorName(cellLineandTranscriptionFactorName,inputFileName);
				   meanandStdDev.setName(cellLineandTranscriptionFactorName.getCellLineName() + "_" +cellLineandTranscriptionFactorName.getTranscriptionFactorName());
				   cellLineandTranscriptionFactorName = null;
				   
			   }else if(Commons.HISTONE.equals(functionalElementType)){
//	       			Get the cell line name and histone name from file name
				   CellLineHistone cellLineNameHistoneName  = new CellLineHistone();
				   CreationOfUnsortedChromosomeBasedWithNumbersENCODEFiles.getCellLineNameandHistoneName(cellLineNameHistoneName,inputFileName);
				   meanandStdDev.setName(cellLineNameHistoneName.getCellLineName() + "_" + cellLineNameHistoneName.getHistoneName());
				   cellLineNameHistoneName = null;
			   }
				
				  
				while ((strLine = bufferedReader.readLine()) != null)   {
					//chr7	151332800	151332950	.	0	.	13	5.89646	-1	-1
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					
					chromName = strLine.substring(0, indexofFirstTab);
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					
					mapability = Mapability.calculateMapability(low, high, mapabilityIntervalTree);
					
					meanandStdDev.setNumberofIntervals(meanandStdDev.getNumberofIntervals()+1);
					meanandStdDev.setSumofMapabilities(meanandStdDev.getSumofMapabilities()+ mapability);
					
					bufferedWriter.write(mapability + "\n");		
				}
						
				
				//Close the Buffered Reader
				bufferedReader.close();
				bufferedWriter.close();				
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 	

	}
	
	//todo
	//GC
	public static void calculateMeanGC(String outputFolder,File directory,String functionalElementType, Map<String,MeanandStandardDeviation> gcHashMap, GCCharArray gcCharArray){
		File[] files;
    	File file;
    	int numberofFiles;
    	
    	String fileName;
    	String filePath;
    	
    	String gcFileName=null; 
    	
		if(!directory.exists()){
			 GlanetRunner.appendLog("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    files = directory.listFiles();
			    numberofFiles= files.length;
			    
			    if (Commons.DNASE.equals(functionalElementType)){
			    	System.out.printf("Number of Dnase Files %d in %s\n", files.length, directory.getAbsolutePath());
				}else  if (Commons.TF.equals(functionalElementType)){
					System.out.printf("Number of Tfbs Files %d in %s\n", files.length, directory.getAbsolutePath());
				} else if (Commons.HISTONE.equals(functionalElementType)){
					System.out.printf("Number of Histone Files %d in %s\n", files.length, directory.getAbsolutePath());
				}
			    
		        for (int i = 0; i < numberofFiles; i++) {
		        		
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		file = files[i];		        		
		        		
		        		fileName = file.getName();		
		    			filePath = file.getPath();
		    			
		    			 if (Commons.DNASE.equals(functionalElementType)){
		    				 gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\Gc\\" + Commons.GC + "_" + fileName;
		 			    }else  if (Commons.TF.equals(functionalElementType)){
		 			    	gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\Gc\\" + Commons.GC + "_" + fileName;
		 			    } else if (Commons.HISTONE.equals(functionalElementType)){
		 			    	gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\Gc\\" + Commons.GC + "_" + fileName;
		 			    }
		    			
		    			calculateMeanGC(fileName,filePath,gcFileName,gcHashMap,gcCharArray,functionalElementType);
		    			
		        	}//Check for each file and read each file		        	
//		            GlanetRunner.appendLog(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
			
	        } //End of if: For all files in this directory

	}
	
	
	//Mapability
	public static void 	calculateMeanMapability(String outputFolder,File directory,String functionalElementType, Map<String,MeanandStandardDeviation> mapabilityHashMap,IntervalTree mapabilityIntervalTree){
		File[] files;
    	File file;
    	int numberofFiles;
    	
    	String fileName;
    	String filePath;
    	
    	String mapabilityFileName=null; 
    	
		if(!directory.exists()){
			 GlanetRunner.appendLog("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    files = directory.listFiles();
			    numberofFiles= files.length;
			    
			    if (Commons.DNASE.equals(functionalElementType)){
			    	System.out.printf("Number of Dnase Files %d in %s\n", files.length, directory.getAbsolutePath());
				}else  if (Commons.TF.equals(functionalElementType)){
					System.out.printf("Number of Tfbs Files %d in %s\n", files.length, directory.getAbsolutePath());
				} else if (Commons.HISTONE.equals(functionalElementType)){
					System.out.printf("Number of Histone Files %d in %s\n", files.length, directory.getAbsolutePath());
				}
			    
		        for (int i = 0; i < numberofFiles; i++) {
		        		
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		file = files[i];		        		
		        		
		        		fileName = file.getName();		
		    			filePath = file.getPath();
		    			
		    			 if (Commons.DNASE.equals(functionalElementType)){
		    				 mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
		 			    }else  if (Commons.TF.equals(functionalElementType)){
		 			    	mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
		 			    } else if (Commons.HISTONE.equals(functionalElementType)){
		 			    	mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
		 			    }
		    			
		    			calculateMeanMapability(fileName,filePath,mapabilityFileName,mapabilityHashMap,mapabilityIntervalTree,functionalElementType);
		    			
		        	}//Check for each file and read each file		        	
//		            GlanetRunner.appendLog(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
			
	        } //End of if: For all files in this directory
	}
	
	
	//todo
	//GC
	public static void calculateMeanGC(Map<String,MeanandStandardDeviation> gcHashMap){
		MeanandStandardDeviation meanandStandardDeviation;
		
		for(Map.Entry<String, MeanandStandardDeviation> entry: gcHashMap.entrySet()){
			//name
			//Commons.GC +	"\t" + functionalElementType + "\t" + fileName
			
			meanandStandardDeviation = entry.getValue();
			meanandStandardDeviation.setMean(meanandStandardDeviation.getSumofGCs()/meanandStandardDeviation.getNumberofIntervals());
			
		}//End of FOR
	}
	
	
	//Mapability
	public static void calculateMeanMapability(Map<String,MeanandStandardDeviation> mapabilityHashMap){
			
		MeanandStandardDeviation meanandStandardDeviation;
		
		for(Map.Entry<String, MeanandStandardDeviation> entry: mapabilityHashMap.entrySet()){
			//key
			//Commons.MAPABILITY +	"\t" + functionalElementType + "\t" + fileName
				
				meanandStandardDeviation = entry.getValue();
				meanandStandardDeviation.setMean(meanandStandardDeviation.getSumofMapabilities()/meanandStandardDeviation.getNumberofIntervals());
			
		}//End of FOR
	}
	
	//todo
	//GC
	public static void initializeGCHashMap(String outputFolder,File directory,Map<String, MeanandStandardDeviation>  gcHashMap,String functionalElementType){
		File[] files;
    	File file;
    	int numberofFiles;
    	
    	String fileName;
    	String filePath;
    	
    	String gcFileName=null; 
    	
		if(!directory.exists()){
			 GlanetRunner.appendLog("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    files = directory.listFiles();
			    numberofFiles= files.length;
			    
			    if (Commons.DNASE.equals(functionalElementType)){
			    	System.out.printf("Number of Dnase Files %d in %s\n", files.length, directory.getAbsolutePath());
				}else  if (Commons.TF.equals(functionalElementType)){
					System.out.printf("Number of Tfbs Files %d in %s\n", files.length, directory.getAbsolutePath());
				} else if (Commons.HISTONE.equals(functionalElementType)){
					System.out.printf("Number of Histone Files %d in %s\n", files.length, directory.getAbsolutePath());
				}
			    
		        for (int i = 0; i < numberofFiles; i++) {
		        		
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		file = files[i];		        		
		        		
		        		fileName = file.getName();		
		    			filePath = file.getPath();
		    			
		    			 if (Commons.DNASE.equals(functionalElementType)){
		    				 gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\Gc\\" + Commons.GC + "_" + fileName;
		 			    }else  if (Commons.TF.equals(functionalElementType)){
		 			    	gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\Gc\\" + Commons.GC + "_" + fileName;
		 			    } else if (Commons.HISTONE.equals(functionalElementType)){
		 			    	gcFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\Gc\\" + Commons.GC + "_" + fileName;
		 			    }
		    			 
		    			 MeanandStandardDeviation meanandStandardDeviation = new MeanandStandardDeviation();
		    			 gcHashMap.put(Commons.GC +	"\t" + functionalElementType + "\t" + fileName, meanandStandardDeviation);
		    			
		    		}//Check for each file and read each file		        	
//		            GlanetRunner.appendLog(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
			
	        } //End of if: For all files in this directory

	}
	
	
	//Mapability
	public static void initializeMapabilityHashMap(String outputFolder,File directory, Map<String, MeanandStandardDeviation> mapabilityHashMap, String functionalElementType){
		File[] files;
    	File file;
    	int numberofFiles;
    	
    	String fileName;
    	String filePath;
    	
    	String mapabilityFileName=null; 
    	
		if(!directory.exists()){
			 GlanetRunner.appendLog("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    files = directory.listFiles();
			    numberofFiles= files.length;
			    
			    if (Commons.DNASE.equals(functionalElementType)){
			    	System.out.printf("Number of Dnase Files %d in %s\n", files.length, directory.getAbsolutePath());
				}else  if (Commons.TF.equals(functionalElementType)){
					System.out.printf("Number of Tfbs Files %d in %s\n", files.length, directory.getAbsolutePath());
				} else if (Commons.HISTONE.equals(functionalElementType)){
					System.out.printf("Number of Histone Files %d in %s\n", files.length, directory.getAbsolutePath());
				}
			    
		        for (int i = 0; i < numberofFiles; i++) {
		        		
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		file = files[i];		        		
		        		
		        		fileName = file.getName();		
		    			filePath = file.getPath();
		    			
		    			 if (Commons.DNASE.equals(functionalElementType)){
		    				 mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
		 			    }else  if (Commons.TF.equals(functionalElementType)){
		 			    	mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
		 			    } else if (Commons.HISTONE.equals(functionalElementType)){
		 			    	mapabilityFileName = outputFolder +"Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\Mapability\\" + Commons.MAPABILITY + "_" + fileName;
		 			    }
		    			 
		    			 MeanandStandardDeviation meanandStandardDeviation = new MeanandStandardDeviation();
		    			 mapabilityHashMap.put(Commons.MAPABILITY +	"\t" + functionalElementType + "\t" + fileName, meanandStandardDeviation);
		    			
		    		}//Check for each file and read each file		        	
//		            GlanetRunner.appendLog(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
			
	        } //End of if: For all files in this directory

	}
	
	
	//todo
	//GC
	public static void calculateMeanandStandardDeviationofGCofEachFunctionalElementFile(String outputFolder,String dataFolder,Map<String, MeanandStandardDeviation>  gcDnaseHashMap, Map<String, MeanandStandardDeviation>  gcTfbsHashMap, Map<String, MeanandStandardDeviation>  gcHistoneHashMap, List<Integer>  hg19ChromosomeSizes){
		ChromosomeName chromName;
		int chromSize;
		GCCharArray gcCharAray;
		String  directory=null;
		String mainDirectory=null;
		String initializeDirectory;
		File file;
		
		//DNASE
		mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\";
		
		//create MeanandStandardDeviation objects for each file in the directory
		//put MeanandStandardDeviation objects in the gcHashMap
		//take any chromosome
		//for example chr1
		chromName = ChromosomeName.CHROMOSOME1;
		initializeDirectory = mainDirectory + chromName + "\\";
		file = new File(initializeDirectory);
		
		GlanetRunner.appendLog("-------------------------");
		GlanetRunner.appendLog("Initialize hash map for DNASE has started.");
		initializeGCHashMap(outputFolder,file,gcDnaseHashMap,Commons.DNASE);
		GlanetRunner.appendLog("Initialize hash map for DNASE has ended.");
		
		
		//TFBS
		mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\";
		
		//create MeanandStandardDeviation objects for each file in the directory
		//put MeanandStandardDeviation objects in the mapabilityHashMap
		//take any chromosome
		//for example chr1
		chromName = ChromosomeName.CHROMOSOME1;
		initializeDirectory = mainDirectory + chromName + "\\";
		file = new File(initializeDirectory);
		
		GlanetRunner.appendLog("-------------------------");
		GlanetRunner.appendLog("Initialize hash map for TFBS has started.");
		initializeGCHashMap(outputFolder,file,gcTfbsHashMap,Commons.TF);
		GlanetRunner.appendLog("Initialize hash map for TFBS has ended.");
		
		
		//HISTONE
		mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\";
		
		//create MeanandStandardDeviation objects for each file in the directory
		//put MeanandStandardDeviation objects in the mapabilityHashMap
		//take any chromosome
		//for example chr1
		chromName = ChromosomeName.CHROMOSOME1;
		initializeDirectory = mainDirectory + chromName + "\\";
		file = new File(initializeDirectory);
		
		GlanetRunner.appendLog("-------------------------");
		GlanetRunner.appendLog("Initialize hash map for HISTONE  has started.");
		initializeGCHashMap(outputFolder,file,gcHistoneHashMap,Commons.HISTONE);
		GlanetRunner.appendLog("Initialize hash map for HISTONE has ended.");
		GlanetRunner.appendLog("-------------------------");
	    
		for(int i=1; i <= Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
					
			chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			chromSize = hg19ChromosomeSizes.get(i-1);
			//use the same char array
			gcCharAray = ChromosomeBasedGCArray.getChromosomeGCArray(dataFolder,chromName, chromSize);
			
			//DNASE
			mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\";
			directory = mainDirectory + chromName + "\\";
			file = new File(directory);
			//calculate mapability for all files of Dnase
			calculateMeanGC(outputFolder,file,Commons.DNASE, gcDnaseHashMap,gcCharAray);
			
			
			//TFBS
			mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\";
			directory = mainDirectory + chromName + "\\";
			file = new File(directory);
			//calculate mapability for all files of Dnase
			calculateMeanGC(outputFolder,file,Commons.TF, gcTfbsHashMap,gcCharAray);
			
			//HISTONE
			mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\";
			directory = mainDirectory + chromName + "\\";
			file = new File(directory);
			//calculate mapability for all files of Dnase
			calculateMeanGC(outputFolder,file,Commons.HISTONE, gcHistoneHashMap,gcCharAray);
			
				
		}
		
		//DNASE
		//After mapability has been calculated for all chromosomes 
		calculateMeanGC(gcDnaseHashMap);
		calculateStandardDeviationGC(outputFolder,Commons.DNASE,gcDnaseHashMap);
		
		//TFBS
		//After mapability has been calculated for all chromosomes 
		calculateMeanGC(gcTfbsHashMap);
		calculateStandardDeviationGC(outputFolder,Commons.TF,gcTfbsHashMap);
	
		//HISTONE
		//After mapability has been calculated for all chromosomes 
		calculateMeanGC(gcHistoneHashMap);
		calculateStandardDeviationGC(outputFolder,Commons.HISTONE,gcHistoneHashMap);
	
	}
	
	
	//Mapability
	public static void 	calculateMeanandStandardDeviationofMapabilityofEachFunctionalElementFile(String outputFolder,Map<String, MeanandStandardDeviation> mapabilityDnaseHashMap,Map<String, MeanandStandardDeviation> mapabilityTfbsHashMap,Map<String, MeanandStandardDeviation> mapabilityHistoneHashMap,List<Integer> hg19ChromosomeSizes){
		
		ChromosomeName chromName;
		int chromSize;
		IntervalTree mapabilityIntervalTree;
		String  directory=null;
		String mainDirectory=null;
		String initializeDirectory;
		File file;
		
		//DNASE
		mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\";
		
		//create MeanandStandardDeviation objects for each file in the directory
		//put MeanandStandardDeviation objects in the mapabilityHashMap
		//take any chromosome
		//for example chr1
		chromName = ChromosomeName.CHROMOSOME1;
		initializeDirectory = mainDirectory + chromName + "\\";
		file = new File(initializeDirectory);
		
		GlanetRunner.appendLog("-------------------------");
		GlanetRunner.appendLog("Initialize hash map for DNASE has started.");
		initializeMapabilityHashMap(outputFolder,file,mapabilityDnaseHashMap,Commons.DNASE);
		GlanetRunner.appendLog("Initialize hash map for DNASE has ended.");
		
		
		//TFBS
		mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\";
		
		//create MeanandStandardDeviation objects for each file in the directory
		//put MeanandStandardDeviation objects in the mapabilityHashMap
		//take any chromosome
		//for example chr1
		chromName = ChromosomeName.CHROMOSOME1;
		initializeDirectory = mainDirectory + chromName + "\\";
		file = new File(initializeDirectory);
		
		GlanetRunner.appendLog("-------------------------");
		GlanetRunner.appendLog("Initialize hash map for TFBS has started.");
		initializeMapabilityHashMap(outputFolder,file,mapabilityTfbsHashMap,Commons.TF);
		GlanetRunner.appendLog("Initialize hash map for TFBS has ended.");
		
		
		//HISTONE
		mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\";
		
		//create MeanandStandardDeviation objects for each file in the directory
		//put MeanandStandardDeviation objects in the mapabilityHashMap
		//take any chromosome
		//for example chr1
		chromName = ChromosomeName.CHROMOSOME1;
		initializeDirectory = mainDirectory + chromName + "\\";
		file = new File(initializeDirectory);
		
		GlanetRunner.appendLog("-------------------------");
		GlanetRunner.appendLog("Initialize hash map for HISTONE  has started.");
		initializeMapabilityHashMap(outputFolder,file,mapabilityHistoneHashMap,Commons.HISTONE);
		GlanetRunner.appendLog("Initialize hash map for HISTONE has ended.");
		GlanetRunner.appendLog("-------------------------");
	    
		for(int i=1; i <= Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
					
			chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			chromSize = hg19ChromosomeSizes.get(i-1);
			//use the same interval tree
			mapabilityIntervalTree = ChromosomeBasedMapabilityIntervalTree.getChromosomeBasedMapabilityIntervalTree(chromName, chromSize);
			
			//DNASE
			mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\";
			directory = mainDirectory + chromName + "\\";
			file = new File(directory);
			//calculate mapability for all files of Dnase
			calculateMeanMapability(outputFolder,file,Commons.DNASE, mapabilityDnaseHashMap,mapabilityIntervalTree);
			
			
			//TFBS
			mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\";
			directory = mainDirectory + chromName + "\\";
			file = new File(directory);
			//calculate mapability for all files of Dnase
			calculateMeanMapability(outputFolder,file,Commons.TF, mapabilityTfbsHashMap,mapabilityIntervalTree);
			
			//HISTONE
			mainDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\";
			directory = mainDirectory + chromName + "\\";
			file = new File(directory);
			//calculate mapability for all files of Dnase
			calculateMeanMapability(outputFolder,file,Commons.HISTONE, mapabilityHistoneHashMap,mapabilityIntervalTree);
			
				
		}
		
		//DNASE
		//After mapability has been calculated for all chromosomes 
		calculateMeanMapability(mapabilityDnaseHashMap);
		calculateStandardDeviationMapability(outputFolder,Commons.DNASE,mapabilityDnaseHashMap);
		
		//TFBS
		//After mapability has been calculated for all chromosomes 
		calculateMeanMapability(mapabilityTfbsHashMap);
		calculateStandardDeviationMapability(outputFolder,Commons.TF,mapabilityTfbsHashMap);
	
		//HISTONE
		//After mapability has been calculated for all chromosomes 
		calculateMeanMapability(mapabilityHistoneHashMap);
		calculateStandardDeviationMapability(outputFolder,Commons.HISTONE,mapabilityHistoneHashMap);
	
	}
    
	
	
	
	
	public static void closeBufferedWriters(Map<ChromosomeName,BufferedWriter> bufferedWriterHashMap){
		
		for (Map.Entry<ChromosomeName,BufferedWriter> entry: bufferedWriterHashMap.entrySet()){
			BufferedWriter bufferedWriter = entry.getValue();
			
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	 
	public static void partitionFileinaChromosomeBasedManner(String filePath, Map<ChromosomeName,BufferedWriter> bufferedWriterHashMap){
		FileReader fileReader= null;
		BufferedReader bufferedReader = null;
		
		BufferedWriter bufferedWriter = null;
		
		String strLine;
		ChromosomeName chromName;
		
		int indexofFirstTab;
		
		
		try {
			
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine= bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
				bufferedWriter = bufferedWriterHashMap.get(chromName);
				bufferedWriter.write(strLine + "\n");
			}
			
			bufferedReader.close();
			closeBufferedWriters(bufferedWriterHashMap);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static void fillBufferedWriterHashMap(String chromosomeBasedDirectory, String fileName, Map<ChromosomeName,BufferedWriter> bufferedWriterHashMap){
		ChromosomeName chromName;
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		
		for(int i = 1; i<= Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			try {
				fileWriter = new FileWriter(chromosomeBasedDirectory + chromName + "\\" + fileName );
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriterHashMap.put(chromName, bufferedWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public static void createChromosomeBasedFunctionalElementFiles(String outputFolder,File directory, String functionalElementType){
		File[] files;
    	File file;
    	int numberofFiles;
    	
    	String fileName;
    	String filePath;
    	String chromosomeBasedDirectory = null;
    	
		if(!directory.exists()){
			 GlanetRunner.appendLog("No File/Dir: " + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    files = directory.listFiles();
			    numberofFiles= files.length;
			    
			    if (Commons.DNASE.equals(functionalElementType)){
			    	System.out.printf("Number of Dnase Files %d in %s\n", files.length, directory.getAbsolutePath());
				}else  if (Commons.TF.equals(functionalElementType)){
					System.out.printf("Number of Tfbs Files %d in %s\n", files.length, directory.getAbsolutePath());
				} else if (Commons.HISTONE.equals(functionalElementType)){
					System.out.printf("Number of Histone Files %d in %s\n", files.length, directory.getAbsolutePath());
				}
			    
		        for (int i = 0; i < numberofFiles; i++) {
		        		
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		file = files[i];		        		
		        		
		        		fileName = file.getName();		
		    			filePath = file.getPath();
		    			
		    			 if (Commons.DNASE.equals(functionalElementType)){
		    				 chromosomeBasedDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Dnase\\";
		 			    }else  if (Commons.TF.equals(functionalElementType)){
		 			    	chromosomeBasedDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Tfbs\\";
		 			    } else if (Commons.HISTONE.equals(functionalElementType)){
		 			    	chromosomeBasedDirectory = outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\Histone\\";
		 			    }
		    			
		    			 
		    			 
		    			Map<ChromosomeName,BufferedWriter> bufferedWriterHashMap = new HashMap<ChromosomeName,BufferedWriter>();
		    			fillBufferedWriterHashMap(chromosomeBasedDirectory,fileName,bufferedWriterHashMap); 
		    			partitionFileinaChromosomeBasedManner(filePath,bufferedWriterHashMap);
		    			    			
		    			
		        	}//Check for each file and read each file		        	
//		            GlanetRunner.appendLog(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
			
	        } //End of if: For all files in this directory

	}
	
	
	
	public static void deleteOldFiles(File folder){
		File[] files = folder.listFiles();
		 
	    for(File file: files){
	        if(file.isFile()){
	            file.delete();
	        }else if(file.isDirectory()) {
	        	//GlanetRunner.appendLog("Folder Name: "+ file.getName() + " Absolute Path: " + file.getAbsolutePath());
	        	deleteOldFiles(file);
	        }
	    }     
	}
	
	
	//Mapability
	public static void calculateMapability(String outputFolder,List<Integer> hg19ChromosomeSizes){
		
		String allFunctionalElementMapabilityFiles 		= Commons.ALL_MAPABILITY_FILES;
		
		String allDnaseMapabilityFiles 					= Commons.ALL_DNASE_MAPABILITY_FILES;
		String topTenDnaseMapabilityFiles 				= Commons.TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES;;
		String tenDifferentMeanDnaseMapabilityFiles 	= Commons.TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES;
			
    	String allTfbsMapabilityFiles 					= Commons.ALL_TFBS_MAPABILITY_FILES;
    	String topTenTfbsMapabilityFiles 				= Commons.TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES;
    	String tenDifferentMeanTfbsMapabilityFiles 		= Commons.TEN_DIFFERENT_MEAN_TFBS_MAPABILITY_FILES;
		
    	String allHistoneMapabilityFiles 				=Commons.ALL_HISTONE_MAPABILITY_FILES;
    	String topTenHistoneMapabilityFiles				= Commons.TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES;
    	String tenDifferentMeanHistoneMapabilityFiles 	= Commons.TEN_DIFFERENT_MEAN_HISTONE_MAPABILITY_FILES;
    	
    	Map<String,MeanandStandardDeviation> dnaseMapabilityHashMap = new HashMap<String,MeanandStandardDeviation>();
    	Map<String,MeanandStandardDeviation> tfbsMapabilityHashMap = new HashMap<String,MeanandStandardDeviation>();
    	Map<String,MeanandStandardDeviation> histoneMapabilityHashMap = new HashMap<String,MeanandStandardDeviation>();
  	
    	GlanetRunner.appendLog("-------------------------");
    	GlanetRunner.appendLog("Calculate mean and standard deviation for MAPABILITY has started.");   
    	//For all functional elements
    	//DNASE 
    	//TFBS
    	//HISTONE
    	calculateMeanandStandardDeviationofMapabilityofEachFunctionalElementFile(outputFolder,dnaseMapabilityHashMap,tfbsMapabilityHashMap,histoneMapabilityHashMap,hg19ChromosomeSizes);
    	GlanetRunner.appendLog("Calculate mean and standard deviation for MAPABILITY has ended.");      
    	
    	//sort by standard deviation descending order
    	//Dnase
    	StandardDeviationComparator compMapabilityDnase = new StandardDeviationComparator(dnaseMapabilityHashMap);
    	TreeMap dnaseMapabilityTreeMapStdDevDescendingOrder = new TreeMap(compMapabilityDnase);
    	dnaseMapabilityTreeMapStdDevDescendingOrder.putAll(dnaseMapabilityHashMap);
    	
    	//Tfbs
    	StandardDeviationComparator compMapabilityTfbs = new StandardDeviationComparator(tfbsMapabilityHashMap);
    	TreeMap tfbsMapabilityTreeMapStdDevDescendingOrder = new TreeMap(compMapabilityTfbs);
    	tfbsMapabilityTreeMapStdDevDescendingOrder.putAll(tfbsMapabilityHashMap);
    	
    	//Histone
    	StandardDeviationComparator compMapabilityHistone = new StandardDeviationComparator(histoneMapabilityHashMap);
    	TreeMap histoneMapabilityTreeMapStdDevDescendingOrder = new TreeMap(compMapabilityHistone);
    	histoneMapabilityTreeMapStdDevDescendingOrder.putAll(histoneMapabilityHashMap);
    	
    	//sort by mean ascending order
    	//Dnase
    	MeanComparator dnaseMapabilityMeanComparator = new MeanComparator(dnaseMapabilityHashMap);
    	TreeMap dnaseMapabilityTreeMapMeanAscendingOrder = new TreeMap(dnaseMapabilityMeanComparator);
    	dnaseMapabilityTreeMapMeanAscendingOrder.putAll(dnaseMapabilityHashMap);
    	
    	//Tfbs
    	MeanComparator tfbsMapabilityMeanComparator = new MeanComparator(tfbsMapabilityHashMap);
    	TreeMap tfbsMapabilityTreeMapMeanAscendingOrder = new TreeMap(tfbsMapabilityMeanComparator);
    	tfbsMapabilityTreeMapMeanAscendingOrder.putAll(tfbsMapabilityHashMap);
    
    	//Histone
    	MeanComparator histoneMapabilityMeanComparator = new MeanComparator(histoneMapabilityHashMap);
    	TreeMap histoneMapabilityTreeMapMeanAscendingOrder = new TreeMap(histoneMapabilityMeanComparator);
    	histoneMapabilityTreeMapMeanAscendingOrder.putAll(histoneMapabilityHashMap);
    
    	
    	GlanetRunner.appendLog("-------------------------");
    	//DNASE
    	GlanetRunner.appendLog("Write results for ALL DNASE MAPABILITY has started.");        
    	writeMeanandStdDevResultstoFiles(allDnaseMapabilityFiles, dnaseMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for TOP TEN DNASE MAPABILITY has started.");        
    	writeTopTenMostVaryingStdDevResultstoFiles(topTenDnaseMapabilityFiles, dnaseMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for TEN DIFFERENT MEAN DNASE MAPABILITY has started.");        
    	writeTenDifferentMeanFiles(tenDifferentMeanDnaseMapabilityFiles, dnaseMapabilityTreeMapMeanAscendingOrder);
       	
    	
    	//TFBS
    	GlanetRunner.appendLog("Write results for ALL TFBS MAPABILITY has started.");        
    	writeMeanandStdDevResultstoFiles(allTfbsMapabilityFiles, tfbsMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for TOP TEN TFBS MAPABILITY has started.");        
    	writeTopTenMostVaryingStdDevResultstoFiles(topTenTfbsMapabilityFiles, tfbsMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for TEN DIFFERENT MEAN TFBS MAPABILITY has started.");        
    	writeTenDifferentMeanFiles(tenDifferentMeanTfbsMapabilityFiles, tfbsMapabilityTreeMapMeanAscendingOrder);
      
    	//HISTONE
    	GlanetRunner.appendLog("Write results for ALL HISTONE MAPABILITY has started.");        
    	writeMeanandStdDevResultstoFiles(allHistoneMapabilityFiles, histoneMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for TOP TEN HISTONE MAPABILITY has started.");        
    	writeTopTenMostVaryingStdDevResultstoFiles(topTenHistoneMapabilityFiles, histoneMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for TEN DIFFERENT MEAN HISTONE MAPABILITY has started.");        
    	writeTenDifferentMeanFiles(tenDifferentMeanHistoneMapabilityFiles, histoneMapabilityTreeMapMeanAscendingOrder);
      
    	writeMeanandStdDevResultstoFiles(allFunctionalElementMapabilityFiles, dnaseMapabilityTreeMapStdDevDescendingOrder,tfbsMapabilityTreeMapStdDevDescendingOrder,histoneMapabilityTreeMapStdDevDescendingOrder);
    	GlanetRunner.appendLog("Write results for MAPABILITY has ended.");        
    
	}
	
	
	//todo
	//GC
	public static void calculateGC(String outputFolder,String dataFolder,List<Integer> hg19ChromosomeSizes){
		
    	String allFunctionalElementGCFiles 	= Commons.ALL_GC_FILES;
    	
    	String allDnaseGCFiles 				= Commons.ALL_DNASE_GC_FILES;
    	String topTenDnaseGCFiles 			= Commons.TOP_TEN_MOST_VARYING_DNASE_GC_FILES;
    	String tenDifferentMeanDnaseGCFiles = Commons.TEN_DIFFERENT_MEAN_DNASE_GC_FILES;
		
    	
    	String allTfbsGCFiles 				= Commons.ALL_TFBS_GC_FILES;
    	String topTenTfbsGCFiles 			= Commons.TOP_TEN_MOST_VARYING_TFBS_GC_FILES;
    	String tenDifferentMeanTfbsGCFiles = Commons.TEN_DIFFERENT_MEAN_TFBS_GC_FILES;
		
    	String allHistoneGCFiles 			= Commons.ALL_HISTONE_GC_FILES;
    	String topTenHistoneGCFiles 		= Commons.TOP_TEN_MOST_VARYING_HISTONE_GC_FILES;
    	String tenDifferentMeanHistoneGCFiles = Commons.TEN_DIFFERENT_MEAN_HISTONE_GC_FILES;
		
    	
    	Map<String,MeanandStandardDeviation> dnaseGCHashMap = new HashMap<String,MeanandStandardDeviation>();
    	Map<String,MeanandStandardDeviation> tfbsGCHashMap = new HashMap<String,MeanandStandardDeviation>();
    	Map<String,MeanandStandardDeviation> histoneGCHashMap = new HashMap<String,MeanandStandardDeviation>();
    	
    	GlanetRunner.appendLog("-------------------------");
    	GlanetRunner.appendLog("Calculate mean and standard deviation for GC has started.");   
    	//For all functional elements
    	//DNASE 
    	//TFBS
    	//HISTONE
    	calculateMeanandStandardDeviationofGCofEachFunctionalElementFile(outputFolder,dataFolder,dnaseGCHashMap,tfbsGCHashMap,histoneGCHashMap,hg19ChromosomeSizes);
    	GlanetRunner.appendLog("Calculate mean and standard deviation for GC has ended.");        
    	
    	
    	
    	//sort by standard deviation descending order
    	//Dnase
    	StandardDeviationComparator compDnaseGC = new StandardDeviationComparator(dnaseGCHashMap);
    	TreeMap dnaseGCTreeMap = new TreeMap(compDnaseGC);
    	dnaseGCTreeMap.putAll(dnaseGCHashMap);
    	
    	//Tfbs
    	StandardDeviationComparator compTfbsGC = new StandardDeviationComparator(tfbsGCHashMap);
    	TreeMap tfbsGCTreeMap = new TreeMap(compTfbsGC);
    	tfbsGCTreeMap.putAll(tfbsGCHashMap);
    	
    	//Histone
    	StandardDeviationComparator compHistoneGC = new StandardDeviationComparator(histoneGCHashMap);
    	TreeMap histoneGCTreeMap = new TreeMap(compHistoneGC);
    	histoneGCTreeMap.putAll(histoneGCHashMap);
    	
    	//sort by mean ascending order
    	//Dnase
    	MeanComparator dnaseGCMeanComparator = new MeanComparator(dnaseGCHashMap);
    	TreeMap dnaseGCTreeMapMeanAscendingOrder = new TreeMap(dnaseGCMeanComparator);
    	dnaseGCTreeMapMeanAscendingOrder.putAll(dnaseGCHashMap);
    	
    	//Tfbs
    	MeanComparator tfbsGCMeanComparator = new MeanComparator(tfbsGCHashMap);
    	TreeMap tfbsGCTreeMapMeanAscendingOrder = new TreeMap(tfbsGCMeanComparator);
    	tfbsGCTreeMapMeanAscendingOrder.putAll(tfbsGCHashMap);
    
    	//Histone
    	MeanComparator histoneGCMeanComparator = new MeanComparator(histoneGCHashMap);
    	TreeMap histoneGCTreeMapMeanAscendingOrder = new TreeMap(histoneGCMeanComparator);
    	histoneGCTreeMapMeanAscendingOrder.putAll(histoneGCHashMap);
    
    	
    	GlanetRunner.appendLog("-------------------------");
    	//DNASE
    	GlanetRunner.appendLog("Write results for ALL DNASE GC has started.");        
    	writeMeanandStdDevResultstoFiles(allDnaseGCFiles, dnaseGCTreeMap);
    	GlanetRunner.appendLog("Write results for TOP TEN DNASE GC has started.");        
    	writeTopTenMostVaryingStdDevResultstoFiles(topTenDnaseGCFiles, dnaseGCTreeMap);
    	GlanetRunner.appendLog("Write results for TEN DIFFERENT MEAN DNASE GC has started.");        
    	writeTenDifferentMeanFiles(tenDifferentMeanDnaseGCFiles, dnaseGCTreeMapMeanAscendingOrder);
       
    	//TFBS
    	GlanetRunner.appendLog("Write results for ALL TFBS GC has started.");        
    	writeMeanandStdDevResultstoFiles(allTfbsGCFiles, tfbsGCTreeMap);
    	GlanetRunner.appendLog("Write results for TOP TEN TFBS GC has started.");        
    	writeTopTenMostVaryingStdDevResultstoFiles(topTenTfbsGCFiles, tfbsGCTreeMap);
    	GlanetRunner.appendLog("Write results for TEN DIFFERENT MEAN TFBS GC has started.");        
    	writeTenDifferentMeanFiles(tenDifferentMeanTfbsGCFiles, tfbsGCTreeMapMeanAscendingOrder);
     
    	//HISTONE
    	GlanetRunner.appendLog("Write results for ALL HISTONE GC has started.");        
    	writeMeanandStdDevResultstoFiles(allHistoneGCFiles, histoneGCTreeMap);
    	GlanetRunner.appendLog("Write results for TOP TEN HISTONE GC has started.");        
    	writeTopTenMostVaryingStdDevResultstoFiles(topTenHistoneGCFiles, histoneGCTreeMap);
    	GlanetRunner.appendLog("Write results for TEN DIFFERENT MEAN HISTONE GC has started.");        
    	writeTenDifferentMeanFiles(tenDifferentMeanHistoneGCFiles, histoneGCTreeMapMeanAscendingOrder);
     
    	writeMeanandStdDevResultstoFiles(allFunctionalElementGCFiles, dnaseGCTreeMap,tfbsGCTreeMap,histoneGCTreeMap);
    	GlanetRunner.appendLog("Write results for GC has ended.");        
    
    	
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
	public static void main(String[] args){
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
	
		List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();
		GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
    	//get the hg19 chromosome sizes
    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes,dataFolder, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
    	
    	//DNASE ORIGINAL ENCODE FILES DIRECTORY
    	File dnaseDir1 	= new File(dataFolder + common.Commons.ENCODE_DNASE_DIRECTORY1);
    	File dnaseDir2 	= new File(dataFolder + common.Commons.ENCODE_DNASE_DIRECTORY2);
    	//TFBS ORIGINAL ENCODE FILES DIRECTORY
    	File tfbsDir 	= new File(dataFolder + common.Commons.ENCODE_TFBS_DIRECTORY);
    	//HISTONE ORIGINAL ENCODE FILES DIRECTORY
    	File histoneDir = new File(dataFolder + common.Commons.ENCODE_HISTONE_DIRECTORY);
		
    	
    	//Delete old files
    	GlanetRunner.appendLog("-------------------------");
    	GlanetRunner.appendLog("Start deleting old files...");
    	File folder = new File(outputFolder + "Doktora\\mapabilityandgc\\Augmentation\\FunctionalElementFileBased\\");
    	deleteOldFiles(folder);
    	GlanetRunner.appendLog("Deleting old files has ended.");
    	
    	//some files may be empty
    	GlanetRunner.appendLog("-------------------------");
    	GlanetRunner.appendLog("Create chromosome based functional element files has started.");
    	createChromosomeBasedFunctionalElementFiles(outputFolder,dnaseDir1,Commons.DNASE);
    	createChromosomeBasedFunctionalElementFiles(outputFolder,dnaseDir2,Commons.DNASE);
    	createChromosomeBasedFunctionalElementFiles(outputFolder,tfbsDir,Commons.TF);
    	createChromosomeBasedFunctionalElementFiles(outputFolder,histoneDir,Commons.HISTONE);
    	GlanetRunner.appendLog("Create chromosome based functional element files has ended.");
    	
    	
    	//Mapability
    	calculateMapability(outputFolder,hg19ChromosomeSizes);
    	
    	//GC
    	calculateGC(outputFolder,dataFolder,hg19ChromosomeSizes);
  	
    	
	}

}
