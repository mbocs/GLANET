/**
 * @author Burcak Otlu
 * Jul 26, 2013
 * 2:03:10 PM
 * 2013
 *
 * 
 */
package deprecated;

import hg19.GRCh37Hg19Chromosome;
import intervaltree.IntervalTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

import keggpathway.ncbigenes.KeggPathwayUtility;
import mapabilityandgc.ChromosomeBasedGCArray;
import mapabilityandgc.ChromosomeBasedMapabilityArray;
import ui.GlanetRunner;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;
import auxiliary.NumberofComparisons;
import auxiliary.NumberofComparisonsforBonferroniCorrectionCalculation;

import common.Commons;

import empiricalpvalues.AllMaps;
import empiricalpvalues.AnnotationTask;
import empiricalpvalues.GCCharArray;
import empiricalpvalues.InputLine;
import empiricalpvalues.MapabilityFloatArray;
import enumtypes.AnnotationType;
import enumtypes.ChromosomeName;
import enumtypes.EnrichmentType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.MultipleTestingType;
import enumtypes.WriteGeneratedRandomDataMode;
import enumtypes.WritePermutationBasedAnnotationResultMode;
import enumtypes.WritePermutationBasedandParametricBasedAnnotationResultMode;
import generate.randomdata.RandomDataGenerator;


public class AnnotatePermutationsUsingForkJoin_withEnrichmentChoices {
	
	static class GenerateRandomData extends RecursiveTask<Map<Integer,List<InputLine>>>{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5508399455444935122L;
		private final int chromSize;
		private final ChromosomeName chromName;
		private final List<InputLine> chromosomeBasedOriginalInputLines;
			
		private final GenerateRandomDataMode generateRandomDataMode;
		private final WriteGeneratedRandomDataMode  writeGeneratedRandomDataMode;
		
		private final int lowIndex;
		private final int highIndex;
		
		private final List<AnnotationTask> listofAnnotationTasks;

		private final GCCharArray gcCharArray;
		private final MapabilityFloatArray mapabilityFloatArray;
		private final String outputFolder;
				
		public GenerateRandomData(String outputFolder,int chromSize, ChromosomeName chromName, List<InputLine> chromosomeBasedOriginalInputLines, GenerateRandomDataMode generateRandomDataMode, WriteGeneratedRandomDataMode writeGeneratedRandomDataMode,int lowIndex, int highIndex, List<AnnotationTask> listofAnnotationTasks,GCCharArray gcCharArray, MapabilityFloatArray mapabilityFloatArray) {
			
			this.outputFolder = outputFolder;
			
			this.chromSize = chromSize;
			this.chromName = chromName;
			this.chromosomeBasedOriginalInputLines = chromosomeBasedOriginalInputLines;

			this.generateRandomDataMode = generateRandomDataMode;
			this.writeGeneratedRandomDataMode = writeGeneratedRandomDataMode;
			
			this.lowIndex = lowIndex;
			this.highIndex = highIndex;
			
			this.listofAnnotationTasks = listofAnnotationTasks;
					
			this.gcCharArray = gcCharArray;
			this.mapabilityFloatArray = mapabilityFloatArray;
		}

		
		protected Map<Integer,List<InputLine>> compute() {
			
			int middleIndex;
			Map<Integer,List<InputLine>> rightRandomlyGeneratedData;
			Map<Integer,List<InputLine>> leftRandomlyGeneratedData;
	        
	    	Integer permutationNumber;
			AnnotationTask annotationTask;
					
			//DIVIDE
			if (highIndex-lowIndex>8){
			 	middleIndex = lowIndex + (highIndex - lowIndex) / 2;
			 	GenerateRandomData left  = new GenerateRandomData(outputFolder,chromSize, chromName, chromosomeBasedOriginalInputLines, generateRandomDataMode,writeGeneratedRandomDataMode,lowIndex,middleIndex,listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
			 	GenerateRandomData right = new GenerateRandomData(outputFolder,chromSize, chromName, chromosomeBasedOriginalInputLines, generateRandomDataMode,writeGeneratedRandomDataMode,middleIndex,highIndex,listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
	            left.fork();
	            rightRandomlyGeneratedData = right.compute();
	            leftRandomlyGeneratedData  = left.join();
	            
	            //Add the contents of leftRandomlyGeneratedData into rightRandomlyGeneratedData
	            mergeMaps(rightRandomlyGeneratedData,leftRandomlyGeneratedData);
	            
	            leftRandomlyGeneratedData = null;
	            return rightRandomlyGeneratedData;
	 		}
			//CONQUER
			else {
				
				Map<Integer,List<InputLine>> randomlyGeneratedDataMap = new HashMap<Integer,List<InputLine>>();
				 	
				for(int i=lowIndex; i<highIndex; i++){
					 annotationTask = listofAnnotationTasks.get(i);					 					
					 
					 permutationNumber = annotationTask.getPermutationNumber();
					 					      
				     GlanetRunner.appendLog("Generate Random Data For Permutation: " + permutationNumber + "\t" +chromName);	
				     
				     randomlyGeneratedDataMap.put(permutationNumber, RandomDataGenerator.generateRandomData(gcCharArray,mapabilityFloatArray,chromSize, chromName,chromosomeBasedOriginalInputLines, ThreadLocalRandom.current(), generateRandomDataMode));
				      
				     //Write Generated Random Data
				     if(writeGeneratedRandomDataMode.isWriteGeneratedRandomDataMode()){
				    	writeGeneratedRandomData(outputFolder,randomlyGeneratedDataMap.get(permutationNumber),permutationNumber);
				     }
						
				}//End of FOR
					
				return randomlyGeneratedDataMap;
			}

		}//End of compute method
		
		//Add the content of leftMap to rightMap
		//Clear and null leftMap
		protected void mergeMaps(Map<Integer,List<InputLine>> rightRandomlyGeneratedDataMap,Map<Integer,List<InputLine>> leftRandomlyGeneratedDataMap){
			
			for(Map.Entry<Integer, List<InputLine>> entry: leftRandomlyGeneratedDataMap.entrySet()){
				Integer permutationNumber = entry.getKey();
				
				if (!rightRandomlyGeneratedDataMap.containsKey(permutationNumber)){
					rightRandomlyGeneratedDataMap.put(permutationNumber, entry.getValue());
				}
			}//End of for
			
			leftRandomlyGeneratedDataMap.clear();
			leftRandomlyGeneratedDataMap = null;
				
		}
	       
		
		protected void writeGeneratedRandomData(String outputFolder,List<InputLine> randomlyGeneratedData,int permutationNumber){
			 FileWriter fileWriter;
		     BufferedWriter bufferedWriter;
		     InputLine randomlyGeneratedInputLine;
		        
		 	try {
		 		
				fileWriter = FileOperations.createFileWriter(outputFolder + Commons.RANDOMLY_GENERATED_DATA_FOLDER  + Commons.PERMUTATION + permutationNumber  + "_" + Commons.RANDOMLY_GENERATED_DATA  +".txt",true);
				bufferedWriter = new BufferedWriter(fileWriter);
				
				for(int i=0;i<randomlyGeneratedData.size(); i++){
					randomlyGeneratedInputLine = randomlyGeneratedData.get(i);
					bufferedWriter.write(ChromosomeName.convertEnumtoString(randomlyGeneratedInputLine.getChrName()) +"\t" + randomlyGeneratedInputLine.getLow() + "\t" + randomlyGeneratedInputLine.getHigh() +System.getProperty("line.separator"));
					bufferedWriter.flush();
				}//End of FOR
				
				bufferedWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}//End of GenerateRandomData Class
	
		

	static class Annotate extends RecursiveTask<AllMaps>{
		
		
	private static final long serialVersionUID = 2919115895116169524L;
	private final int chromSize;
	private final ChromosomeName chromName;
	private final Map<Integer,List<InputLine>> randomlyGeneratedDataMap;
	private final int runNumber;
	private final int numberofPermutations;
	
	private final WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode;
	
	private final List<AnnotationTask> listofAnnotationTasks;
	private final IntervalTree intervalTree;
	private final IntervalTree ucscRefSeqGenesIntervalTree;
	
	private final AnnotationType annotationType;
	private final EnrichmentType tfandKeggPathwayEnrichmentType;
	
	private final int lowIndex;
	private final int highIndex;
	
	private final Map<String,List<String>> geneId2KeggPathwayMap;
	
	private final String outputFolder;
	
	private final int overlapDefinition;
	
	
	
	
	public Annotate(String outputFolder,int chromSize, ChromosomeName chromName, Map<Integer,List<InputLine>> randomlyGeneratedDataMap, int runNumber,int numberofPermutations, WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,int lowIndex, int highIndex, List<AnnotationTask> listofAnnotationTasks, IntervalTree intervalTree, IntervalTree ucscRefSeqGenesIntervalTree,AnnotationType annotationType, EnrichmentType tfandKeggPathwayEnrichmentType,Map<String, List<String>> geneId2KeggPathwayMap,int overlapDefinition) {
		this.outputFolder  = outputFolder;
		
		this.chromSize = chromSize;
		this.chromName = chromName;
		this.randomlyGeneratedDataMap = randomlyGeneratedDataMap;
		this.runNumber = runNumber;
		this.numberofPermutations = numberofPermutations;
		
		this.writePermutationBasedandParametricBasedAnnotationResultMode = writePermutationBasedandParametricBasedAnnotationResultMode;
		
		this.lowIndex = lowIndex;
		this.highIndex = highIndex;
		
		this.listofAnnotationTasks = listofAnnotationTasks;
		this.intervalTree = intervalTree;
	
		//sent full when annotation for tf and ucsc refseq genes will be done
		//otherwise sent null
		this.ucscRefSeqGenesIntervalTree = ucscRefSeqGenesIntervalTree;
		
		this.annotationType = annotationType;	
		this.tfandKeggPathwayEnrichmentType = tfandKeggPathwayEnrichmentType;
		
		//geneId2KeggPathwayMap
		//sent full when annotation for tf and ucsc refseq genes will be done
		//otherwise sent null
		this.geneId2KeggPathwayMap = geneId2KeggPathwayMap;
		
		this.overlapDefinition = overlapDefinition;
		
	}
	
	
		
		protected AllMaps compute() {
			
			int middleIndex;
			AllMaps rightAllMaps;
	        AllMaps leftAllMaps;
	        
	    	AnnotationTask annotationTask;
			Integer permutationNumber;
			List<AllMaps> listofAllMaps;
			AllMaps allMaps;
				
			//DIVIDE
			if (highIndex-lowIndex>9){
			 	middleIndex = lowIndex + (highIndex - lowIndex) / 2;
	            Annotate left  = new Annotate(outputFolder,chromSize, chromName, randomlyGeneratedDataMap, runNumber,numberofPermutations,writePermutationBasedandParametricBasedAnnotationResultMode,lowIndex,middleIndex,listofAnnotationTasks,intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap,overlapDefinition);
	            Annotate right = new Annotate(outputFolder,chromSize, chromName, randomlyGeneratedDataMap, runNumber,numberofPermutations,writePermutationBasedandParametricBasedAnnotationResultMode,middleIndex,highIndex,listofAnnotationTasks,intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap,overlapDefinition);
	            left.fork();
	            rightAllMaps = right.compute();
	            leftAllMaps  = left.join();
	            combineAllMaps(leftAllMaps,rightAllMaps);
	            leftAllMaps= null;
	            return rightAllMaps;
			}
			//CONQUER
			else {
				
				listofAllMaps = new ArrayList<AllMaps>();
				allMaps = new AllMaps();
				 	
				for(int i=lowIndex; i<highIndex; i++){
					 annotationTask = listofAnnotationTasks.get(i);
					 permutationNumber = annotationTask.getPermutationNumber();
					      
				     GlanetRunner.appendLog("Annotate Random Data For Permutation: " + permutationNumber + "\t" +chromName + "\t" + annotationType);	
				     
				     //NEW FUNCTIONALITY HAS BEEN ADDED
				     if(writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()){
				    	 listofAllMaps.add(AnnotateGivenIntervalsWithGivenParameters.annotatePermutationwithoutIO(permutationNumber,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap,overlapDefinition));
				     }
				     
				     //NEW FUNCTIONALITY HAS BEEN ADDED
				     else if (writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){
				     	 listofAllMaps.add(AnnotateGivenIntervalsWithGivenParameters.annotatePermutationwithIO(outputFolder,permutationNumber,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap,overlapDefinition));
				     }						
				}//End of FOR
					
				combineListofAllMaps(listofAllMaps,allMaps);
				listofAllMaps = null;
				return allMaps;
				
	
			}
		}		
		
		//Accumulate the allMaps in the left into allMaps in the right
		protected void combineListofAllMaps(List<AllMaps> listofAllMaps,AllMaps allMaps){
			for(int i =0; i<listofAllMaps.size(); i++){
				combineAllMaps(listofAllMaps.get(i), allMaps);
			}
		}
		
		
		//Accumulate leftMap in the rightMap
		//Accumulate number of overlaps 
		//based on permutationNumber and ElementName
		protected void  combineMaps(Map<String,Integer> leftMap, Map<String,Integer> rightMap){
			
			for(Map.Entry<String, Integer> entry: leftMap.entrySet()){
				String permutationNumberElementName = entry.getKey();
				Integer numberofOverlaps = entry.getValue();
				
				if (rightMap.get(permutationNumberElementName)==null){
					rightMap.put(permutationNumberElementName, numberofOverlaps);
				}else{
					rightMap.put(permutationNumberElementName, rightMap.get(permutationNumberElementName)+numberofOverlaps);
				}
			}
			
			leftMap.clear();
			leftMap = null;
			//deleteMap(leftMap);
	
		}
		
		
		protected void combineAllMaps(AllMaps leftAllMaps, AllMaps rightAllMaps) {
				
				//LEFT ALL MAPS
				Map<String,Integer> leftPermutationNumberDnaseCellLineName2KMap = leftAllMaps.getPermutationNumberDnaseCellLineName2KMap();
				Map<String,Integer> leftPermutationNumberTfbsNameCellLineName2KMap = leftAllMaps.getPermutationNumberTfNameCellLineName2KMap();
				Map<String,Integer> leftPermutationNumberHistoneNameCellLineName2KMap = leftAllMaps.getPermutationNumberHistoneNameCellLineName2KMap();
				
				Map<String,Integer> leftPermutationNumberExonBasedKeggPathway2KMap = leftAllMaps.getPermutationNumberExonBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberRegulationBasedKeggPathway2KMap = leftAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberAllBasedKeggPathway2KMap = leftAllMaps.getPermutationNumberAllBasedKeggPathway2KMap();
				
				Map<String,Integer> leftPermutationNumberTfExonBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfRegulationBasedKeggPathway2KMap 	= leftAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfAllBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap();

				Map<String,Integer> leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap 	= leftAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap();
				Map<String,Integer> leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap 		= leftAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap();
				
			
				//RIGHT ALL MAPS
				Map<String,Integer> rightPermutationNumberDnaseCellLineName2KMap = rightAllMaps.getPermutationNumberDnaseCellLineName2KMap();
				Map<String,Integer> rightPermutationNumberTfbsNameCellLineName2KMap = rightAllMaps.getPermutationNumberTfNameCellLineName2KMap();
				Map<String,Integer> rightPermutationNumberHistoneNameCellLineName2KMap = rightAllMaps.getPermutationNumberHistoneNameCellLineName2KMap();
				
				Map<String,Integer> rightPermutationNumberExonBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberExonBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberRegulationBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberAllBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberAllBasedKeggPathway2KMap();
				
				Map<String,Integer> rightPermutationNumberTfExonBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfRegulationBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfAllBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap();

				Map<String,Integer> rightPermutationNumberTfCellLineExonBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap = rightAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap();
				Map<String,Integer> rightPermutationNumberTfCellLineAllBasedKeggPathway2KMap 		= rightAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap();
								
				
				if (leftPermutationNumberDnaseCellLineName2KMap!=null){
					combineMaps(leftPermutationNumberDnaseCellLineName2KMap,rightPermutationNumberDnaseCellLineName2KMap);
					leftPermutationNumberDnaseCellLineName2KMap = null;
				}
				
				if(leftPermutationNumberTfbsNameCellLineName2KMap!=null){
					combineMaps(leftPermutationNumberTfbsNameCellLineName2KMap,rightPermutationNumberTfbsNameCellLineName2KMap);
					leftPermutationNumberTfbsNameCellLineName2KMap = null;
				}
				
				if(leftPermutationNumberHistoneNameCellLineName2KMap!=null){
					combineMaps(leftPermutationNumberHistoneNameCellLineName2KMap,rightPermutationNumberHistoneNameCellLineName2KMap);
					leftPermutationNumberHistoneNameCellLineName2KMap = null;					
				}
				
				if(leftPermutationNumberExonBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberExonBasedKeggPathway2KMap,rightPermutationNumberExonBasedKeggPathway2KMap);
					leftPermutationNumberExonBasedKeggPathway2KMap = null;					
				}
				
				if (leftPermutationNumberRegulationBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberRegulationBasedKeggPathway2KMap,rightPermutationNumberRegulationBasedKeggPathway2KMap);
					leftPermutationNumberRegulationBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberAllBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberAllBasedKeggPathway2KMap,rightPermutationNumberAllBasedKeggPathway2KMap);
					leftPermutationNumberAllBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap,rightPermutationNumberTfCellLineExonBasedKeggPathway2KMap);
					leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap,rightPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap);
					leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap,rightPermutationNumberTfCellLineAllBasedKeggPathway2KMap);
					leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap = null;
				}
			
				if (leftPermutationNumberTfExonBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfExonBasedKeggPathway2KMap,rightPermutationNumberTfExonBasedKeggPathway2KMap);
					leftPermutationNumberTfExonBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfRegulationBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfRegulationBasedKeggPathway2KMap,rightPermutationNumberTfRegulationBasedKeggPathway2KMap);
					leftPermutationNumberTfRegulationBasedKeggPathway2KMap = null;
				}
				
				if (leftPermutationNumberTfAllBasedKeggPathway2KMap!=null){
					combineMaps(leftPermutationNumberTfAllBasedKeggPathway2KMap,rightPermutationNumberTfAllBasedKeggPathway2KMap);
					leftPermutationNumberTfAllBasedKeggPathway2KMap = null;
				}
				
				
				//delete AllMaps
				//deleteAllMaps(leftAllMaps);
				leftAllMaps = null;
						
		}//End of combineAllMaps
		
		
		  
		
		protected void  deleteRandomlyGeneratedData(List<InputLine>randomlyGeneratedData){
			for(InputLine inputLine: randomlyGeneratedData){
				inputLine.setChrName(null);
				inputLine= null;
			}
			
			randomlyGeneratedData.clear();
		}
			
		
		protected void deleteMap(Map<String,Integer> map){
			if (map!=null){
				for(Map.Entry<String, Integer> entry: map.entrySet()){
					entry.setValue(null);
					entry= null;			
				}
				map= null;
			}
			
		}
		
		protected void deleteAllMaps(AllMaps allMaps){
			Map<String,Integer> map = allMaps.getPermutationNumberDnaseCellLineName2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberTfNameCellLineName2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberHistoneNameCellLineName2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberExonBasedKeggPathway2KMap();
			deleteMap(map);
			map = allMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
			deleteMap(map);
			allMaps = null;
		}
		
		
	}

	
	public void readOriginalInputDataLines(List<InputLine> originalInputLines, String inputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		
		ChromosomeName chrName;
		int low;
		int high;
	
		GlanetRunner.appendLog("Input data file name is: " + inputFileName);
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while( (strLine= bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				
				chrName = ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab));
				
				if (indexofSecondTab>0){
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				}else{
					low = Integer.parseInt(strLine.substring(indexofFirstTab+1));
					high = low;
				}
				
				InputLine originalInputLine = new InputLine(chrName, low, high);
				originalInputLines.add(originalInputLine);
			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public  void partitionDataChromosomeBased(List<InputLine> originalInputLines, Map<ChromosomeName,List<InputLine>> chromosomeBasedOriginalInputLines){
		InputLine line;
		ChromosomeName chrName;
		List<InputLine> list;
		
		
		for(int i = 0; i<originalInputLines.size(); i++){
			
			line = originalInputLines.get(i);
			chrName = line.getChrName();
			list = chromosomeBasedOriginalInputLines.get(chrName);
			
			if (list == null){
				list = new ArrayList<InputLine>();
				list.add(line);
				chromosomeBasedOriginalInputLines.put(chrName, list);
			}else{
				list.add(line);
				chromosomeBasedOriginalInputLines.put(chrName,list);
			}
		}
	}
	
	//convert permutation augmented name to only name
	//Fill elementName2ALLMap and originalElementName2KMap in convert methods	
	public void convert(Map<String,Integer> permutationDnase2KMap,Map<String,List<Integer>> elementName2AllKMap, Map<String,Integer> test_originalElementName2KMap){
		String permutationAugmentedName;
		Integer numberofOverlaps;
		int indexofFirstUnderscore;
		String name;
		String permutationNumber;
		
		List<Integer> list;
		
		for(Map.Entry<String, Integer> entry: permutationDnase2KMap.entrySet()){
			
			//example permutationAugmentedName PERMUTATION0_K562
			permutationAugmentedName = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			indexofFirstUnderscore = permutationAugmentedName.indexOf('_');
			name = permutationAugmentedName.substring(indexofFirstUnderscore+1);

			//example permutationNumber PERMUTATION0
			permutationNumber = permutationAugmentedName.substring(0, indexofFirstUnderscore);
			
			if (Commons.PERMUTATION0.equals(permutationNumber)){
				test_originalElementName2KMap.put(name, numberofOverlaps);
			}else{
				list =elementName2AllKMap.get(name);
				
				if(list ==null){
					list = new ArrayList<Integer>();
					list.add(numberofOverlaps);
					elementName2AllKMap.put(name, list);
				}else{
					list.add(numberofOverlaps);
					elementName2AllKMap.put(name, list);
					
				}
			}
			
			
			
		}
	}
	
	public void fillMapfromMap(Map<String,Integer> toMap, Map<String,Integer> fromMap){
		String name;
		Integer numberofOverlaps;
		
		for(Map.Entry<String, Integer> entry: fromMap.entrySet()){
			name = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			toMap.put(name, numberofOverlaps);
			
			
		}
	}
	
//	public void annotateOriginalInputData(String inputDataFileName,Map<String,Integer> originalDnase2KMap, Map<String,Integer> originalTfbs2KMap, Map<String,Integer> originalHistone2KMap, Map<String,Integer> originalExonBasedKeggPathway2KMap, Map<String,Integer> originalRegulationBasedKeggPathway2KMap){
//		AnnotateGivenIntervalsWithGivenParameters annotateIntervals = new AnnotateGivenIntervalsWithGivenParameters();
//		
//		AllName2KMaps name2KMap = annotateIntervals.annotateOriginalData(inputDataFileName);
//		
//		fillMapfromMap(originalDnase2KMap, name2KMap.getDnaseCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalTfbs2KMap, name2KMap.getTfbsNameandCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalHistone2KMap, name2KMap.getHistoneNameandCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalExonBasedKeggPathway2KMap, name2KMap.getExonBasedKeggPathway2NumberofOverlapsMap());
//		fillMapfromMap(originalRegulationBasedKeggPathway2KMap, name2KMap.getRegulationBasedKeggPathway2NumberofOverlapsMap());
//		
//		
//	}
	


	//Empirical P Value and Bonferroni Corrected Empirical P Value
	//List<FunctionalElement> list is filled in this method
	//Using name2AccumulatedKMap and originalName2KMap
	public void calculateEmpricalPValues(Integer numberofComparisons, int numberofRepeats, int numberofPermutations,Map<String, List<Integer>> name2AccumulatedKMap,Map<String, Integer> originalName2KMap, List<FunctionalElement> list){
		
			
		String  originalName;
		Integer originalNumberofOverlaps;
		List<Integer> listofNumberofOverlaps;
		Integer numberofOverlaps;
		int  numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
		Float empiricalPValue;
		Float bonferroniCorrectedEmpiricalPValue;
		
		FunctionalElement functionalElement;
					
		
		//only consider the names in the original name 2 k map
		for(Map.Entry<String, Integer> entry: originalName2KMap.entrySet()){
			originalName = entry.getKey();
			originalNumberofOverlaps = entry.getValue();
			
			listofNumberofOverlaps = name2AccumulatedKMap.get(originalName);

			//Initialise numberofPermutationsHavingOverlapsGreaterThanorEqualto to zero for each original name 
			numberofPermutationsHavingOverlapsGreaterThanorEqualto = 0;
			
			if (listofNumberofOverlaps!=null){
				for(int i =0; i<listofNumberofOverlaps.size(); i++){
					
					numberofOverlaps =listofNumberofOverlaps.get(i);
					
					if(numberofOverlaps >= originalNumberofOverlaps){
						numberofPermutationsHavingOverlapsGreaterThanorEqualto++;
					}
				}//end of for
			}//end of if
			
			empiricalPValue = (numberofPermutationsHavingOverlapsGreaterThanorEqualto * 1.0f)/(numberofRepeats * numberofPermutations);
			bonferroniCorrectedEmpiricalPValue = ((numberofPermutationsHavingOverlapsGreaterThanorEqualto* 1.0f)/(numberofRepeats * numberofPermutations)) * numberofComparisons;
			
			if(bonferroniCorrectedEmpiricalPValue>=1){
				bonferroniCorrectedEmpiricalPValue = 1.0f;
			}
			
			functionalElement = new FunctionalElement();
			functionalElement.setName(originalName);
			functionalElement.setEmpiricalPValue(empiricalPValue);
			functionalElement.setBonferroniCorrectedEmpiricalPValue(bonferroniCorrectedEmpiricalPValue);
			
			//18 FEB 2014
			functionalElement.setOriginalNumberofOverlaps(originalNumberofOverlaps);
			functionalElement.setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(numberofPermutationsHavingOverlapsGreaterThanorEqualto);
			functionalElement.setNumberofPermutations(numberofRepeats * numberofPermutations);
			functionalElement.setNumberofComparisons(numberofComparisons);
			
			list.add(functionalElement);
				
		}//end of for
			
		
	}
		
		
		
	
	public void generateAnnotationTasks(ChromosomeName chromName, List<AnnotationTask> listofAnnotationTasks,int runNumber,int numberofPermutationsInThisRun,int numberofPermutationsInEachRun){
		
		
			for(int permutationNumber = 1; permutationNumber<= numberofPermutationsInThisRun; permutationNumber++){
				listofAnnotationTasks.add(new AnnotationTask(chromName, (runNumber-1)* numberofPermutationsInEachRun + permutationNumber));
			}
	}
	
	
	public void generateAnnotationTaskforOriginalData(ChromosomeName chromName, List<AnnotationTask> listofAnnotationTasks,Integer originalDataPermutationNumber){
		listofAnnotationTasks.add(new AnnotationTask(chromName, 0));
	}
	
	public IntervalTree generateDnaseIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createDnaseIntervalTree(dataFolder,chromName);	
	}
	
	public IntervalTree generateTfbsIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createTfbsIntervalTree(dataFolder,chromName);	
	}
	
	public IntervalTree generateHistoneIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createHistoneIntervalTree(dataFolder,chromName);	
	}
	
	public IntervalTree generateUcscRefSeqGeneIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithGivenParameters.createUcscRefSeqGenesIntervalTree(dataFolder,chromName);	
	}
	
	public void generateIntervalTrees(String outputFolder,ChromosomeName chromName, List<IntervalTree> listofIntervalTrees){
		IntervalTree dnaseIntervalTree;
		IntervalTree tfbsIntervalTree ;
		IntervalTree histoneIntervalTree;
		IntervalTree ucscRefSeqGeneIntervalTree;
		
				
		dnaseIntervalTree			= AnnotateGivenIntervalsWithGivenParameters.createDnaseIntervalTree(outputFolder,chromName);
		tfbsIntervalTree 			= AnnotateGivenIntervalsWithGivenParameters.createTfbsIntervalTree(outputFolder,chromName);
		histoneIntervalTree  		= AnnotateGivenIntervalsWithGivenParameters.createHistoneIntervalTree(outputFolder,chromName);
		ucscRefSeqGeneIntervalTree 	= AnnotateGivenIntervalsWithGivenParameters.createUcscRefSeqGenesIntervalTree(outputFolder,chromName);
		
		//order is important
		listofIntervalTrees.add(dnaseIntervalTree);
		listofIntervalTrees.add(tfbsIntervalTree);
		listofIntervalTrees.add(histoneIntervalTree);
		listofIntervalTrees.add(ucscRefSeqGeneIntervalTree);
		
	}

	public void closeBufferedWriters(Map<String,BufferedWriter> permutationNumber2BufferedWriterHashMap){
		
		BufferedWriter bufferedWriter = null;
		try {
			
			for(Map.Entry<String,BufferedWriter> entry: permutationNumber2BufferedWriterHashMap.entrySet() ){
				bufferedWriter = entry.getValue();
				bufferedWriter.close();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeBuferedWriterswithIntegerKey(Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap){
		
		BufferedWriter bufferedWriter = null;
		try {
			
			for(Map.Entry<Integer,BufferedWriter> entry: permutationNumber2BufferedWriterHashMap.entrySet() ){
				bufferedWriter = entry.getValue();
				bufferedWriter.close();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void writeAnnotationstoFiles(String outputFolder,Map<String,Integer> name2KMap, Map<String,BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName){
		
		String permutationNumberName;
		String permutationNumber;
		String name;
		
		int indexofFirstUnderscore;
		Integer numberofOverlaps;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		for(Map.Entry<String, Integer> entry: name2KMap.entrySet()){
			permutationNumberName = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			indexofFirstUnderscore = permutationNumberName.indexOf('_');
			permutationNumber = permutationNumberName.substring(0, indexofFirstUnderscore);
			name =  permutationNumberName.substring(indexofFirstUnderscore+1);
			
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber) ;
			
			try {
				
				if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATION + folderName + permutationNumber +  "_" + extraFileName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);							
				}
				bufferedWriter.write(name +"\t" + numberofOverlaps +System.getProperty("line.separator"));
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of for
		
	}
	
	//Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	//Accumulate number of overlaps coming from each chromosome
	//based on permutationNumber and ElementName
	public static void accumulate(Map<String,Integer> chromosomeBasedName2KMap, Map<String,Integer> accumulatedName2KMap){
		String permutationNumberElementName;
		Integer numberofOverlaps;
		
		for(Map.Entry<String, Integer> entry: chromosomeBasedName2KMap.entrySet()){
			permutationNumberElementName = entry.getKey();
			numberofOverlaps = entry.getValue();
			
			
			if (accumulatedName2KMap.get(permutationNumberElementName)==null){
				accumulatedName2KMap.put(permutationNumberElementName, numberofOverlaps);
			}else{
				accumulatedName2KMap.put(permutationNumberElementName, accumulatedName2KMap.get(permutationNumberElementName) + numberofOverlaps);
				
			}
		}
		
	}
	
	
	
	public static void accumulate(AllMaps chromosomeBasedAllMaps, AllMaps accumulatedAllMaps){
		
		//Dnase
		accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap());
		
		//Tfbs
		accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap());
		
		//Histone
		accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap());
		
		//Exon Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap());
		
		//Regulation Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap());
				
	}
	
	//Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	//Coming from each chromosome
	public static void accumulate(AllMaps chromosomeBasedAllMaps, AllMaps accumulatedAllMaps, AnnotationType annotationType){
		
		if (annotationType.isDnaseAnnotation()){
			//Dnase
			accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap());
		}else if (annotationType.isTfAnnotation()){
			//Tfbs
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap());
		}else if (annotationType.isHistoneAnnotation()){
			//Histone
			accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap());
		}else if (annotationType.isKeggPathwayAnnotation()){
			//Exon Based Kegg Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap());
			
			//Regulation Based Kegg Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap());
			
			//All Based Kegg Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap());
	
		}else if (annotationType.isTfCellLineKeggPathwayAnnotation()){
			//TF and CellLine and Kegg Pathway Annotation
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap());
		}else if (annotationType.isTfKeggPathwayAnnotation()){
			//TF and Kegg Pathway Annotation
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap());
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap());
			//NEW FUNCIONALITY
		}
		
						
	}
	
	
	
	public void deleteIntervalTrees(List<IntervalTree> listofIntervalTrees){
		IntervalTree dnaseIntervalTree = listofIntervalTrees.get(0);
		IntervalTree tfbsIntervalTree = listofIntervalTrees.get(1);
		IntervalTree histoneIntervalTree = listofIntervalTrees.get(2);
		IntervalTree ucscRefSeqGenesIntervalTree = listofIntervalTrees.get(3);
		
		IntervalTree.deleteNodesofIntervalTree(dnaseIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(tfbsIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(histoneIntervalTree.getRoot());
		IntervalTree.deleteNodesofIntervalTree(ucscRefSeqGenesIntervalTree.getRoot());
		
//		dnaseIntervalTree = new IntervalTree();
		dnaseIntervalTree 	= null;
		tfbsIntervalTree 	= null;
		histoneIntervalTree = null;
		ucscRefSeqGenesIntervalTree	= null;
	}
	
	
	public void deleteIntervalTree(IntervalTree intervalTree){
		
		IntervalTree.deleteNodesofIntervalTree(intervalTree.getRoot());
		intervalTree 	= null;
	}
	
	
	public void deleteAnnotationTasks(List<AnnotationTask> listofAnnotationTasks){
		for(AnnotationTask annotationTask : listofAnnotationTasks){
			
			annotationTask = null;
		}
	}
	
	public void deleteGCCharArray(char[] gcCharArray){
		gcCharArray = null;
	}
	
	public void deleteMapabilityFloatArray(float[] mapabilityFloatArray){
		mapabilityFloatArray = null;
	}
	
	
	
	//NEW FUNCIONALITY ADDED
	//First Generate random data concurrently
	//then annotate permutations concurrently
	//the tasks are executed
	//after all the parallel work is done
	//results are written to files
	public void annotateAllPermutationsInThreads(
			String outputFolder,
			String dataFolder,
			int NUMBER_OF_AVAILABLE_PROCESSORS,
			int runNumber, 
			int numberofPermutationsInThisRun,
			int numberofPermutationsInEachRun,			
			List<InputLine> allOriginalInputLines, Map<String,List<Integer>> dnase2AllKMap,Map<String,List<Integer>> tfbs2AllKMap,Map<String,List<Integer>> histone2AllKMap,Map<String,List<Integer>> exonBasedKeggPathway2AllKMap,Map<String,List<Integer>> regulationBasedKeggPathway2AllKMap,Map<String,List<Integer>> allBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfExonBasedKeggPathway2AllKMap, Map<String,List<Integer>> tfRegulationBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfAllBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfCellLineExonBasedKeggPathway2AllKMap, Map<String,List<Integer>> tfCellLineRegulationBasedKeggPathway2AllKMap,Map<String,List<Integer>> tfCellLineAllBasedKeggPathway2AllKMap, GenerateRandomDataMode generateRandomDataMode, WriteGeneratedRandomDataMode writeGeneratedRandomDataMode,WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode,Map<String,Integer> originalDnase2KMap,Map<String,Integer> originalTfbs2KMap,Map<String,Integer> originalHistone2KMap,Map<String,Integer> originalExonBasedKeggPathway2KMap,Map<String,Integer> originalRegulationBasedKeggPathway2KMap,Map<String,Integer> originalAllBasedKeggPathway2KMap, Map<String,Integer> originalTfExonBasedKeggPathway2KMap,Map<String,Integer> originalTfRegulationBasedKeggPathway2KMap,Map<String,Integer> originalTfAllBasedKeggPathway2KMap,Map<String,Integer> originalTfCellLineExonBasedKeggPathway2KMap,Map<String,Integer> originalTfCellLineRegulationBasedKeggPathway2KMap,Map<String,Integer> originalTfCellLineAllBasedKeggPathway2KMap, EnrichmentType dnaseEnrichment, EnrichmentType histoneEnrichment, EnrichmentType tfEnrichment, EnrichmentType keggPathwayEnrichment, EnrichmentType tfKeggPathwayEnrichment, EnrichmentType tfCellLineKeggPathwayEnrichment,int overlapDefinition){
		
		AllMaps allMaps = new AllMaps();
		AllMaps accumulatedAllMaps = new AllMaps();
		
		accumulatedAllMaps.setPermutationNumberDnaseCellLineName2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfNameCellLineName2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberHistoneNameCellLineName2KMap(new HashMap<String,Integer>());
		
		accumulatedAllMaps.setPermutationNumberExonBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberRegulationBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberAllBasedKeggPathway2KMap(new HashMap<String,Integer>());
		
		//Will be used 	for Tf and KeggPathway enrichment or
		accumulatedAllMaps.setPermutationNumberTfExonBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfRegulationBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfAllBasedKeggPathway2KMap(new HashMap<String,Integer>());
			
		//Will be used 	for Tf and CellLine and KeggPathway enrichment
		accumulatedAllMaps.setPermutationNumberTfCellLineExonBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(new HashMap<String,Integer>());
		accumulatedAllMaps.setPermutationNumberTfCellLineAllBasedKeggPathway2KMap(new HashMap<String,Integer>());
						
		Map<String,BufferedWriter> permutationNumber2DnaseBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2HistoneBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		Map<String,BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		//Will be used 	for Tf and KeggPathway enrichment or
		Map<String,BufferedWriter> permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		//Will be used 	for Tf and CellLine and KeggPathway enrichment
		Map<String,BufferedWriter> permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		Map<String,BufferedWriter> permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<String,BufferedWriter>();
		
		Map<ChromosomeName,List<InputLine>> originalInputLinesMap = new HashMap<ChromosomeName,List<InputLine>>();
		
		//todo test it 
		//SecureRandom myrandom = new SecureRandom();

		List<AnnotationTask> listofAnnotationTasks 	= null;
		IntervalTree intervalTree 					= null;
		
		//For NEW FUNCTIONALITY
		IntervalTree tfIntervalTree 				= null;
		IntervalTree ucscRefSeqGenesIntervalTree 	= null;
		
		//For efficiency
		//Fill this map only once.
		Map<String,List<String>> geneId2KeggPathwayMap = new HashMap<String, List<String>>();
		KeggPathwayUtility.createNcbiGeneId2KeggPathwayMap(dataFolder,Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayMap);
	
		GCCharArray gcCharArray						= null;
    	MapabilityFloatArray mapabilityFloatArray 	= null;
    	List<Integer> hg19ChromosomeSizes 			= new ArrayList<Integer>();
    	
		//Partition the original input data lines in a chromosome based manner
		partitionDataChromosomeBased(allOriginalInputLines,originalInputLinesMap);
				
    	hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
    	//get the hg19 chromosome sizes
    	hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		
		ChromosomeName chromName;
    	int chromSize;
    	List<InputLine> chromosomeBaseOriginalInputLines;
    	Map<Integer,List<InputLine>> permutationNumber2RandomlyGeneratedDataHashMap = new HashMap<Integer,List<InputLine>>();
    	
    	Annotate annotate;
    	GenerateRandomData generateRandomData;
    	ForkJoinPool pool = new ForkJoinPool(NUMBER_OF_AVAILABLE_PROCESSORS);
    	
    	long startTimeAllPermutations = System.currentTimeMillis();
    		       		
		GlanetRunner.appendLog("Run Number: " + runNumber);

		//for each chromosome
		for(int i= 1 ; i<=Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
			
    		chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
			chromSize = hg19ChromosomeSizes.get(i-1);
			
			GlanetRunner.appendLog("chromosome name:" + chromName + " chromosome size: " + chromSize);
			chromosomeBaseOriginalInputLines 	= originalInputLinesMap.get(chromName);
							
			if (chromosomeBaseOriginalInputLines!=null){
										
				//initialize list of annotation tasks
				listofAnnotationTasks = new ArrayList<AnnotationTask>();
				
				gcCharArray = new GCCharArray();
				mapabilityFloatArray = new MapabilityFloatArray();
			
				//generate tasks
				GlanetRunner.appendLog("Generate annotation tasks has started.");
				generateAnnotationTasks(chromName,listofAnnotationTasks,runNumber,numberofPermutationsInThisRun,numberofPermutationsInEachRun);
				GlanetRunner.appendLog("Generate annotation tasks has ended.");
				
				   				
				if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
					gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(dataFolder,chromName,chromSize);
					mapabilityFloatArray = ChromosomeBasedMapabilityArray.getChromosomeMapabilityArray(dataFolder,chromName,chromSize);
				}
				
				GlanetRunner.appendLog("Generate Random Data and Annotate has started.");	
			    long startTime = System.currentTimeMillis();
			    
			    GlanetRunner.appendLog("First Generate Random Data");
			    GlanetRunner.appendLog("Generate Random Data has started.");
 			    //First generate Random Data
			    generateRandomData = new GenerateRandomData(outputFolder,chromSize,chromName,chromosomeBaseOriginalInputLines,generateRandomDataMode,writeGeneratedRandomDataMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
			    permutationNumber2RandomlyGeneratedDataHashMap = pool.invoke(generateRandomData);
			    GlanetRunner.appendLog("Generate Random Data has ended.");
			    
			    //In the first run
			    //generate task for original data
		     	//After Random Data Generation has been ended
				//generate task for User Given Original Data(Genomic Variants)
			    //Since we do not need random data, there is  original data is given
				generateAnnotationTaskforOriginalData(chromName,listofAnnotationTasks,Commons.ORIGINAL_DATA_PERMUTATION_NUMBER);
				
				//Add the original data to permutationNumber2RandomlyGeneratedDataHashMap
				permutationNumber2RandomlyGeneratedDataHashMap.put(Commons.ORIGINAL_DATA_PERMUTATION_NUMBER, chromosomeBaseOriginalInputLines);
			 	    
				GlanetRunner.appendLog("Deletion of the gcCharArray has started.");
				deleteGCCharArray(gcCharArray.getGcArray());
				GlanetRunner.appendLog("Deletion of the gcCharArray has ended.");
				gcCharArray = null;
				
				GlanetRunner.appendLog("Deletion of the mapabilityFloatArray has started.");
				deleteMapabilityFloatArray(mapabilityFloatArray.getMapabilityArray());
				GlanetRunner.appendLog("Deletion of the mapabilityFloatArray has ended.");
				mapabilityFloatArray = null;
				
				GlanetRunner.appendLog("Annotate has started.");
				
				if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){
					
					//New Functionality START
    				//tfbs 
    				//Kegg Pathway (exon Based, regulation Based, all Based)
    				//tfbs and Kegg Pathway (exon Based, regulation Based, all Based)
    				//generate tf interval tree and ucsc refseq genes interval tree
    				tfIntervalTree = generateTfbsIntervalTree(dataFolder,chromName);
    				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTree(dataFolder,chromName);
      			    annotate = new Annotate(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsInThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,AnnotationType.TF_KEGG_PATHWAY_ANNOTATION,tfKeggPathwayEnrichment,geneId2KeggPathwayMap,overlapDefinition);
      				allMaps = pool.invoke(annotate);    
      				//Will be used 	for Tf and KeggPathway Enrichment or
      				//				for Tf and CellLine and KeggPathway Enrichment
					accumulate(allMaps, accumulatedAllMaps,AnnotationType.TF_KEGG_PATHWAY_ANNOTATION);	
      			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.TF_ANNOTATION);
      			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.KEGG_PATHWAY_ANNOTATION);
      			  
      			    allMaps = null;
      			    deleteIntervalTree(tfIntervalTree);
      			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
      			    tfIntervalTree = null;
      			    ucscRefSeqGenesIntervalTree = null;	
      				//New Functionality END
    			
				}else if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
    					
    					//New Functionality START
        				//tfbs 
        				//Kegg Pathway (exon Based, regulation Based, all Based)
        				//tfbs and Kegg Pathway (exon Based, regulation Based, all Based)
        				//generate tf interval tree and ucsc refseq genes interval tree
        				tfIntervalTree = generateTfbsIntervalTree(dataFolder,chromName);
        				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTree(dataFolder,chromName);
          			    annotate = new Annotate(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsInThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION,tfCellLineKeggPathwayEnrichment,geneId2KeggPathwayMap,overlapDefinition);
          				allMaps = pool.invoke(annotate);    
          				//Will be used 	for Tf and KeggPathway Enrichment or
          				//				for Tf and CellLine and KeggPathway Enrichment
    					accumulate(allMaps, accumulatedAllMaps,AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION);	
          			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.TF_ANNOTATION);
          			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.KEGG_PATHWAY_ANNOTATION);
          			  
          			    allMaps = null;
          			    deleteIntervalTree(tfIntervalTree);
          			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
          			    tfIntervalTree = null;
          			    ucscRefSeqGenesIntervalTree = null;	
          				//New Functionality END
        			
    			}
    				
				if (dnaseEnrichment.isDnaseEnrichment()){
					
					//dnase
    			    //generate dnase interval tree
    			    intervalTree = generateDnaseIntervalTree(dataFolder,chromName);
    			    annotate = new Annotate(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsInThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.DNASE_ANNOTATION,null,null,overlapDefinition);
    				allMaps = pool.invoke(annotate);    			    
    			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.DNASE_ANNOTATION);
    			    allMaps = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;
        	
				}
			 		
				if (histoneEnrichment.isHistoneEnrichment()){
				    //histone
    			    //generate histone interval tree
    			    intervalTree = generateHistoneIntervalTree(dataFolder,chromName);
    			    annotate = new Annotate(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsInThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.HISTONE_ANNOTATION,null,null,overlapDefinition);
    				allMaps = pool.invoke(annotate);    			    
    			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.HISTONE_ANNOTATION);
    			    allMaps = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;

				}
	    			    
				if ((tfEnrichment.isTfEnrichment()) && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){
    			    //tf
    			    //generate tf interval tree
    			    intervalTree = generateTfbsIntervalTree(dataFolder,chromName);
    			    annotate = new Annotate(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsInThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.TF_ANNOTATION,null,null,overlapDefinition);
    				allMaps = pool.invoke(annotate);    			    
    			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.TF_ANNOTATION);
    			    allMaps = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;

				}
				
				
				if (keggPathwayEnrichment.isKeggPathwayEnrichment() && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){
   			    //ucsc RefSeq Genes
    			    //generate UCSC RefSeq Genes interval tree
    			    intervalTree = generateUcscRefSeqGeneIntervalTree(dataFolder,chromName);
    			    annotate = new Annotate(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsInThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.KEGG_PATHWAY_ANNOTATION,null,geneId2KeggPathwayMap,overlapDefinition);
    				allMaps = pool.invoke(annotate);    			    
    			    accumulate(allMaps, accumulatedAllMaps,AnnotationType.KEGG_PATHWAY_ANNOTATION);
    			    allMaps = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;	

				}
	
       		
				GlanetRunner.appendLog("Annotate has ended.");
				
			    long endTime = System.currentTimeMillis();
				GlanetRunner.appendLog("RunNumber: " + runNumber  + " For Chromosome: " + chromName + " Annotation of " + numberofPermutationsInThisRun + " permutations took  " + (endTime - startTime) + " milliseconds.");
				GlanetRunner.appendLog("Generate Random Data and Annotate has ended.");
			
				GlanetRunner.appendLog("Deletion of the tasks has started.");
				deleteAnnotationTasks(listofAnnotationTasks);
				GlanetRunner.appendLog("Deletion of the tasks has ended.");
		
			    permutationNumber2RandomlyGeneratedDataHashMap.clear();
			    permutationNumber2RandomlyGeneratedDataHashMap= null;
				listofAnnotationTasks = null;
				annotate = null;
				generateRandomData = null;
				chromosomeBaseOriginalInputLines =null;
				
				
			}//end of if: chromosome based input lines is not null
			
    	}//End of for: each chromosome
    	
    			
    	pool.shutdown();
		
		if (pool.isTerminated()){
			GlanetRunner.appendLog("ForkJoinPool is terminated ");
			
		}   	
		
		long endTimeAllPermutations = System.currentTimeMillis();
	
		GlanetRunner.appendLog("RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  "+ numberofPermutationsInThisRun  + " took "  + (endTimeAllPermutations - startTimeAllPermutations) + " milliseconds.");
	
		//convert permutation augmented name to only name
		//Fill elementName2ALLMap and originalElementName2KMap in convert methods
		convert(accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap(),dnase2AllKMap,originalDnase2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),tfbs2AllKMap,originalTfbs2KMap);
		convert(accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(),histone2AllKMap,originalHistone2KMap);
		
		convert(accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),exonBasedKeggPathway2AllKMap,originalExonBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),regulationBasedKeggPathway2AllKMap,originalRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),allBasedKeggPathway2AllKMap,originalAllBasedKeggPathway2KMap);
			
		convert(accumulatedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap(),tfExonBasedKeggPathway2AllKMap,originalTfExonBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),tfRegulationBasedKeggPathway2AllKMap,originalTfRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap(),tfAllBasedKeggPathway2AllKMap,originalTfAllBasedKeggPathway2KMap);
		
		convert(accumulatedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),tfCellLineExonBasedKeggPathway2AllKMap,originalTfCellLineExonBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),tfCellLineRegulationBasedKeggPathway2AllKMap,originalTfCellLineRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),tfCellLineAllBasedKeggPathway2AllKMap,originalTfCellLineAllBasedKeggPathway2KMap);
		
				
		//Permutation Based Results
		if (writePermutationBasedAnnotationResultMode.isWritePermutationBasedAnnotationResultMode()){
			
			if(dnaseEnrichment.isDnaseEnrichment()){
				//Dnase
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberDnaseCellLineName2KMap(),permutationNumber2DnaseBufferedWriterHashMap, "dnase" + System.getProperty("file.separator")  , Commons.DNASE);
				closeBufferedWriters(permutationNumber2DnaseBufferedWriterHashMap);
			
			}
			
			if(histoneEnrichment.isHistoneEnrichment()){
				//Histone
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberHistoneNameCellLineName2KMap(),permutationNumber2HistoneBufferedWriterHashMap,"histone" + System.getProperty("file.separator") , Commons.HISTONE);
				closeBufferedWriters(permutationNumber2HistoneBufferedWriterHashMap);
		
			}
			
			
			if(tfEnrichment.isTfEnrichment()  && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){					
				//Transcription Factor 
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),permutationNumber2TfbsBufferedWriterHashMap, "tfbs" + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);					
			}
			
	
			if(keggPathwayEnrichment.isKeggPathwayEnrichment()  && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){					
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,"keggPathway" + System.getProperty("file.separator") + "exonBased" +System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, "keggPathway" + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator") , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, "keggPathway" + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator") , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
			}
			

			
			if(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){
				
				//Tf and Exon Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfExonBasedKeggPathway2KMap(),permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap, "tfKeggPathwayNumberofOverlaps" + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator") , Commons.TF_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap, "tfKeggPathwayNumberofOverlaps"+ System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator") , Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and All Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfAllBasedKeggPathway2KMap(),permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap, "tfKeggPathwayNumberofOverlaps"+ System.getProperty("file.separator")+ "tfAllBased" + System.getProperty("file.separator") , Commons.TF_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);
				
				//Tfbs
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),permutationNumber2TfbsBufferedWriterHashMap, "tfbs" + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);
				
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,"keggPathway" + System.getProperty("file.separator") + "exonBased" +System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, "keggPathway" + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator") , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, "keggPathway" + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator") , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
			
			}else if(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
				
							
				//Tfbs
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfNameCellLineName2KMap(),permutationNumber2TfbsBufferedWriterHashMap, "tfbs" + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);
				
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,"keggPathway" + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, "keggPathway" + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, "keggPathway" + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);			
				
				//Tf and Cell Line and Exon Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap, "tfCellLineKeggPathwayNumberofOverlaps" + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Cell Line and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap, "tfCellLineKeggPathwayNumberofOverlaps" + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Cell Line and All Based Kegg Pathway
				writeAnnotationstoFiles(outputFolder,accumulatedAllMaps.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap, "tfCellLineKeggPathwayNumberofOverlaps" + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);
				
		
			}
			
			
		}//End of if: write permutation based results
			
	}
	//NEW FUNCIONALITY ADDED
	
	
	public void writetoFile(List<FunctionalElement> list, String fileName, MultipleTestingType empiricalPValueType, int NUMBER_OF_REPEATS, int NUMBER_OF_PERMUTATIONS, GenerateRandomDataMode generateRandomDataMode, String inputDataFileName,float FDR){
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		int i;
		try {
			
			//Set the file name
			if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
				
				if (inputDataFileName.indexOf("ocd")>=0){
					fileWriter = new FileWriter(fileName + "_OCD_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if (inputDataFileName.indexOf("HIV1")>=0){
					fileWriter = new FileWriter(fileName + "_HIV1_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");			
				}else if(inputDataFileName.indexOf("positive_control")>=0){
					fileWriter = new FileWriter(fileName + "_K562_GATA1_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}else if(generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
				if (inputDataFileName.indexOf("ocd")>=0){
						fileWriter = new FileWriter(fileName + "_OCD_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if (inputDataFileName.indexOf("HIV1")>=0){
					fileWriter = new FileWriter(fileName + "_HIV1_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");			
				}else if(inputDataFileName.indexOf("positive_control")>=0){
						fileWriter = new FileWriter(fileName + "_K562_GATA1_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}
			
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write header line
			//If BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE first BonfCorrPValue then Empirical P Value
			//Else If EMPIRICAL_P_VALUE first EmpiricalPValue then BonfCorrPValue
			if (empiricalPValueType.isBonferroniCorrection()){	
				bufferedWriter.write("Name" + "\t"+ "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualto" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "BonfCorrEmpiricalPValue" + "\t"+ "EmpiricalPValue" + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of "+ FDR + System.getProperty("line.separator"));	
			} else if(empiricalPValueType.isEmpiricalPValue()){
				bufferedWriter.write("Name" + "\t"+ "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualto" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "EmpiricalPValue" + "\t"+ "BonfCorrEmpiricalPValue" + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of "+ FDR + System.getProperty("line.separator"));	
			} else if (empiricalPValueType.isBenjaminiHochbergFDR()){
				bufferedWriter.write("Name" + "\t"+ "OriginalNumberofOverlaps" + "\t" + "NumberofPermutationsHavingOverlapsGreaterThanorEqualto" + "\t" + "NumberofPermutations" + "\t" + "NumberofComparisons" + "\t" + "BonfCorrEmpiricalPValue" + "\t"+ "EmpiricalPValue" + "\t" + "BH FDR Adjusted P Value" + "\t" + "Reject Null Hypothesis for an FDR of "+ FDR + System.getProperty("line.separator"));	
			}
			
			
			//For each element in the list
			for(FunctionalElement element : list){
				
				//In case of Functional Element is a kegg pathway
				if(element.getKeggPathwayName()!=null){
					
					if (empiricalPValueType.isBonferroniCorrection()){	
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() +"\t");	
					} else if(empiricalPValueType.isEmpiricalPValue()){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" +df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() +"\t");	
					} else if(empiricalPValueType.isBenjaminiHochbergFDR()){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() +"\t");	
						
					}
					
					bufferedWriter.write(element.getKeggPathwayName()+"\t");
					
					
					if (element.getKeggPathwayGeneIdList().size()>=1){
						//Write the gene ids of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayGeneIdList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
					}
					
					if(element.getKeggPathwayAlternateGeneNameList().size()>=1){
						//Write the alternate gene names of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayAlternateGeneNameList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + System.getProperty("line.separator"));
					
					}					
				}else{
					if (empiricalPValueType.isBonferroniCorrection()){	
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() + System.getProperty("line.separator"));	
					} else if(empiricalPValueType.isEmpiricalPValue()){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getEmpiricalPValue()) + "\t" +df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() + System.getProperty("line.separator"));	
					} else if(empiricalPValueType.isBenjaminiHochbergFDR()){
						bufferedWriter.write(element.getName() + "\t"+ element.getOriginalNumberofOverlaps() + "\t" + element.getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() + "\t" + element.getNumberofPermutations() + "\t" + element.getNumberofComparisons() + "\t" + df.format(element.getBonferroniCorrectedEmpiricalPValue())+ "\t"+ df.format(element.getEmpiricalPValue())+ "\t" + df.format(element.getBH_FDR_adjustedPValue()) +"\t" + element.isRejectNullHypothesis() + System.getProperty("line.separator"));	
								
					}
				}
							
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void writetoFileSmallValueInsteadofZero(Random random, List<FunctionalElement> list, String fileName, MultipleTestingType empiricalPValueType, int NUMBER_OF_REPEATS, int NUMBER_OF_PERMUTATIONS, GenerateRandomDataMode generateRandomDataMode, String inputDataFileName){
		FileWriter fileWriter=null;
		BufferedWriter bufferedWriter;
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		Float empiricalPValue;
		Float bonfCorrEmpiricalPValue;
		
		int i;
		try {
			
			if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
				
				if (inputDataFileName.indexOf("ocd")>=0){
					fileWriter = new FileWriter(fileName + "_OCD_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if(inputDataFileName.indexOf("positive_control")>=0){
					fileWriter = new FileWriter(fileName + "_K562_GATA1_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}else if(generateRandomDataMode.isGenerateRandomDataModeWithoutMapabilityandGc()){
				if (inputDataFileName.indexOf("ocd")>=0){
						fileWriter = new FileWriter(fileName + "_OCD_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else if(inputDataFileName.indexOf("positive_control")>=0){
						fileWriter = new FileWriter(fileName + "_K562_GATA1_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}else{
					fileWriter = new FileWriter(fileName + "_withoutGCMap_"  + NUMBER_OF_REPEATS+ "Rep_" + NUMBER_OF_PERMUTATIONS + "Perm.txt");	
				}
			}
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(FunctionalElement element : list){
				
				//Write the name of the functional element
				bufferedWriter.write(element.getName() + "\t");
				
				
				//In case of Functional Element is a kegg pathway
				if(element.getKeggPathwayName()!=null){
					
					bufferedWriter.write(element.getKeggPathwayName()+"\t");
					
					
					if (element.getKeggPathwayGeneIdList().size()>=1){
						//Write the gene ids of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayGeneIdList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayGeneIdList().get(i) + "\t");
					}
					
					if(element.getKeggPathwayAlternateGeneNameList().size()>=1){
						//Write the alternate gene names of the kegg pathway
						for(i =0 ;i < element.getKeggPathwayAlternateGeneNameList().size()-1; i++){
							bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + ", ");
						}
						bufferedWriter.write(element.getKeggPathwayAlternateGeneNameList().get(i) + "\t");
					
					}					
				}
				
				
				
				if (empiricalPValueType.isBonferroniCorrection()){	
					bonfCorrEmpiricalPValue = element.getBonferroniCorrectedEmpiricalPValue();
					if(bonfCorrEmpiricalPValue.equals(Commons.FLOAT_ZERO)){
						element.setBonferroniCorrectedEmpiricalPValue(random.nextFloat()/Commons.FLOAT_TEN_QUADRILLION);
					}
					bufferedWriter.write(df.format(element.getBonferroniCorrectedEmpiricalPValue())+ System.getProperty("line.separator"));	
				} else if(empiricalPValueType.isEmpiricalPValue()){
					empiricalPValue = element.getEmpiricalPValue();
					if (empiricalPValue.equals(Commons.FLOAT_ZERO)){
						element.setEmpiricalPValue(random.nextFloat()/Commons.FLOAT_TEN_QUADRILLION);
					}
					bufferedWriter.write(df.format(element.getEmpiricalPValue())+ System.getProperty("line.separator"));	
				}
				
				
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void writeToBeCollectedNumberofOverlaps(String outputFolder,Map<String,Integer> originalElement2KMap, Map<String,List<Integer>> element2AllKMap,String toBePolledDirectoryName, String runNumber){
		String elementName;
		Integer originalNumberofOverlaps;
		
		List<Integer> permutationNumberofOverlapsList;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + toBePolledDirectoryName + "_" + runNumber +".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(Map.Entry<String,Integer> entry: originalElement2KMap.entrySet()){
				
				elementName = entry.getKey();
				originalNumberofOverlaps = entry.getValue();
				
				bufferedWriter.write(elementName + "\t" + originalNumberofOverlaps + "|" );
				
				permutationNumberofOverlapsList = element2AllKMap.get(elementName);
				
				if (permutationNumberofOverlapsList!=null){
					for (Integer permutationNumberofOverlaps : permutationNumberofOverlapsList){
						bufferedWriter.write(permutationNumberofOverlaps + "," );
					}//End of inner loop
					
				}

				bufferedWriter.write(System.getProperty("line.separator"));
				
				//if permutationNumberofOverlapsList is null 
				//do nothing
				
				
				
			}//End of outer loop
		
		
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public static void writeInformation(){
		GlanetRunner.appendLog("Java runtime max memory: " + java.lang.Runtime.getRuntime().maxMemory());
        GlanetRunner.appendLog("Java runtime total memory: " + java.lang.Runtime.getRuntime().totalMemory());	
		GlanetRunner.appendLog("Java runtime available processors: " + java.lang.Runtime.getRuntime().availableProcessors()); 
	
	}
	
	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with System.getProperty("file.separator") at the end. 
	//			--->	This folder will be used for outputFolder and dataFolder.
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
	//args[21]	--->	number of permutations in each run
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
		
		
		int overlapDefinition = Integer.parseInt(args[3]);

		//Number of processors can be used in deciding on paralellism level
		int NUMBER_OF_AVAILABLE_PROCESSORS =  java.lang.Runtime.getRuntime().availableProcessors();
			
		//Set the number of total permutations
		int numberofTotalPermutations = Integer.parseInt(args[9]);
		
		//set the number of permutations in each run
		int numberofPermutationsInEachRun = Integer.parseInt(args[21]);				
		
		//SET the Input Data File
//		String inputDataFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS;
//		String inputDataFileName = Commons.POSITIVE_CONTROL_OUTPUT_FILE_NAME_WITHOUT_OVERLAPS;
//		String inputDataFileName = Commons.TCGA_INPUT_DATA_WITH_NON_BLANKS_SNP_IDS_WITHOUT_OVERLAPS;
		String inputDataFileName = outputFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE;
				
		//Set the Generate Random Data Mode
//		String generateRandomDataMode = Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT;
//		String generateRandomDataMode = args[5];
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(args[5]);
		
		//Set the Write Mode of Generated Random Data
//		String writeGeneratedRandomDataMode = Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA;
		WriteGeneratedRandomDataMode writeGeneratedRandomDataMode = WriteGeneratedRandomDataMode.convertStringtoEnum(args[18]);
				
		//Set the Write Mode of Permutation Based and Parametric Based Annotation Result
//		String writePermutationBasedandParametricBasedAnnotationResultMode = Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT;
		WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode = WritePermutationBasedandParametricBasedAnnotationResultMode.convertStringtoEnum(args[19]);
		
		//Set the Write Mode of the Permutation Based Annotation Result
//		String writePermutationBasedAnnotationResultMode = Commons.WRITE_PERMUTATION_BASED_ANNOTATION_RESULT;
		WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode = WritePermutationBasedAnnotationResultMode.convertStringtoEnum(args[20]);
		
		//ENRICHMENT
		//Dnase Enrichment, DO or DO_NOT
		EnrichmentType dnaseEnrichment = EnrichmentType.convertStringtoEnum(args[10]);
		
		//Histone Enrichment, DO or DO_NOT
		EnrichmentType histoneEnrichment = EnrichmentType.convertStringtoEnum(args[11]);
		
		//Transcription Factor Enrichment, DO or DO_NOT
		EnrichmentType tfEnrichment = EnrichmentType.convertStringtoEnum(args[12]);
			
		//KEGG Pathway Enrichment, DO or DO_NOT
		EnrichmentType keggPathwayEnrichment = EnrichmentType.convertStringtoEnum(args[13]);
						
		//TfKeggPathway Enrichment, DO or DO_NOT
		EnrichmentType tfKeggPathwayEnrichment = EnrichmentType.convertStringtoEnum(args[14]);
		
		//TfCellLineKeggPathway Enrichment, DO or DO_NOT
		EnrichmentType tfCellLineKeggPathwayEnrichment = EnrichmentType.convertStringtoEnum(args[15]);
		
		//Run Name
		String jobName = args[17] ;
		
		writeInformation();
			
		//Random Class for generating small values instead of zero valued p values
		//Random random = new Random();
		
		AnnotatePermutationsUsingForkJoin_withEnrichmentChoices annotatePermutationsUsingForkJoin = new AnnotatePermutationsUsingForkJoin_withEnrichmentChoices();

		List<InputLine> originalInputLines = new ArrayList<InputLine>();
		
		//Read original input data lines in to a list
		annotatePermutationsUsingForkJoin.readOriginalInputDataLines(originalInputLines, inputDataFileName);
	
		//For Bonferroni Correction 
		//Set the number of comparisons for DNase, Tfbs, Histone
		//Set the number of comparisons for ExonBasedKeggPathway, RegulationBasedKeggPathway, AllBasedKeggPathway
		//Set the number of comparisons for TfCellLineExonBasedKeggPathway, TfCellLineRegulationBasedKeggPathway, TfCellLineAllBasedKeggPathway
		//Set the number of comparisons for TfExonBasedKeggPathway, TfRegulationBasedKeggPathway, TfAllBasedKeggPathway
		NumberofComparisons  numberofComparisons = new NumberofComparisons();
		NumberofComparisonsforBonferroniCorrectionCalculation.getNumberofComparisonsforBonferroniCorrection(dataFolder,numberofComparisons);
			
		
		/*********************************************/
		//delete old files
		String annotateOutputBaseDirectoryName = outputFolder + Commons.ANNOTATION;
		List<String> notToBeDeleted = new ArrayList<String>();
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(annotateOutputBaseDirectoryName,notToBeDeleted);
		
		//delete old files
		String toBeCollectedOutputBaseDirectoryName = outputFolder + Commons.ENRICHMENT_DIRECTORY;
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(toBeCollectedOutputBaseDirectoryName);			
		/*********************************************/

		
		//for loop starts
		//NUMBER_OF_PERMUTATIONS has to be multiple of 1000 like 1000, 5000, 10000, 50000, 100000
		
		int numberofRuns = 0;
		int numberofRemainedPermutations = 0;
		String runName;
		
		numberofRuns = numberofTotalPermutations / numberofPermutationsInEachRun;
		numberofRemainedPermutations = numberofTotalPermutations % numberofPermutationsInEachRun;
		
		//Increase numberofRuns by 1 for remainder permutations less than Commons.NUMBER_OF_PERMUTATIONS_IN_EACH_RUN
		if (numberofRemainedPermutations> 0){
			numberofRuns += 1;
		}
		
		
		if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment() && tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
			GlanetRunner.appendLog("Both Tf_KEGG_Pathway_enrichment and  Tf_Cellline_Kegg_Pathway_enrichment can not be selected");
		}
		else{
			
			for(int runNumber=1; runNumber<=numberofRuns;runNumber++){
				
				GlanetRunner.appendLog("**************	" + runNumber + ". Run" + "	******************	starts");
				
				runName = jobName + runNumber;
				
				//annotation of original data with permutations	
				//annotation of original data has permutation number zero
				//number of overlaps for the original data: k out of n for the original data
				Map<String,Integer> originalDnase2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalTfbs2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalHistone2KMap = new HashMap<String,Integer>();
				
				Map<String,Integer> originalExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalAllBasedKeggPathway2KMap = new HashMap<String,Integer>();
							
				//Tf and KeggPathway Enrichment
				Map<String,Integer> originalTfExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalTfRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalTfAllBasedKeggPathway2KMap = new HashMap<String,Integer>();
			
				//Tf and CellLine and KeggPathway Enrichment 
				Map<String,Integer> originalTfCellLineExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalTfCellLineRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
				Map<String,Integer> originalTfCellLineAllBasedKeggPathway2KMap = new HashMap<String,Integer>();
									
				//functionalElementName based
				//number of overlaps: k out of n for all permutations
				Map<String,List<Integer>> dnase2AllKMap = new HashMap<String,List<Integer>>();
				Map<String,List<Integer>> histone2AllKMap = new HashMap<String,List<Integer>>();
				Map<String,List<Integer>> tfbs2AllKMap = new HashMap<String,List<Integer>>();
				
				Map<String,List<Integer>> exonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
				Map<String,List<Integer>> regulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
				Map<String,List<Integer>> allBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
				
				//Tf and KeggPathway Enrichment
				Map<String,List<Integer>> tfExonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
				Map<String,List<Integer>> tfRegulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>() ;
				Map<String,List<Integer>> tfAllBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
			
				//Tf and CellLine and KeggPathway Enrichment 
				Map<String,List<Integer>> tfCellLineExonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
				Map<String,List<Integer>> tfCellLineRegulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>() ;
				Map<String,List<Integer>> tfCellLineAllBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
				
							
				GlanetRunner.appendLog("Concurrent programming has been started.");				
				//concurrent programming
				//generate random data
				//then annotate permutations concurrently
				//elementName2AllKMap and originalElementName2KMap will be filled here
				if ((runNumber == numberofRuns) && (numberofRemainedPermutations >0)){
					annotatePermutationsUsingForkJoin.annotateAllPermutationsInThreads(outputFolder,dataFolder,NUMBER_OF_AVAILABLE_PROCESSORS,runNumber,numberofRemainedPermutations,numberofPermutationsInEachRun,originalInputLines,dnase2AllKMap, tfbs2AllKMap, histone2AllKMap, exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap,allBasedKeggPathway2AllKMap,tfExonBasedKeggPathway2AllKMap,tfRegulationBasedKeggPathway2AllKMap,tfAllBasedKeggPathway2AllKMap,tfCellLineExonBasedKeggPathway2AllKMap,tfCellLineRegulationBasedKeggPathway2AllKMap,tfCellLineAllBasedKeggPathway2AllKMap,generateRandomDataMode,writeGeneratedRandomDataMode,writePermutationBasedandParametricBasedAnnotationResultMode,writePermutationBasedAnnotationResultMode,originalDnase2KMap,originalTfbs2KMap,originalHistone2KMap,originalExonBasedKeggPathway2KMap,originalRegulationBasedKeggPathway2KMap,originalAllBasedKeggPathway2KMap,originalTfExonBasedKeggPathway2KMap,originalTfRegulationBasedKeggPathway2KMap,originalTfAllBasedKeggPathway2KMap,originalTfCellLineExonBasedKeggPathway2KMap,originalTfCellLineRegulationBasedKeggPathway2KMap,originalTfCellLineAllBasedKeggPathway2KMap,dnaseEnrichment,histoneEnrichment,tfEnrichment,keggPathwayEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment,overlapDefinition);						
				}else {
					annotatePermutationsUsingForkJoin.annotateAllPermutationsInThreads(outputFolder,dataFolder,NUMBER_OF_AVAILABLE_PROCESSORS,runNumber,numberofPermutationsInEachRun,numberofPermutationsInEachRun,originalInputLines,dnase2AllKMap, tfbs2AllKMap, histone2AllKMap, exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap,allBasedKeggPathway2AllKMap,tfExonBasedKeggPathway2AllKMap,tfRegulationBasedKeggPathway2AllKMap,tfAllBasedKeggPathway2AllKMap,tfCellLineExonBasedKeggPathway2AllKMap,tfCellLineRegulationBasedKeggPathway2AllKMap,tfCellLineAllBasedKeggPathway2AllKMap,generateRandomDataMode,writeGeneratedRandomDataMode,writePermutationBasedandParametricBasedAnnotationResultMode,writePermutationBasedAnnotationResultMode,originalDnase2KMap,originalTfbs2KMap,originalHistone2KMap,originalExonBasedKeggPathway2KMap,originalRegulationBasedKeggPathway2KMap,originalAllBasedKeggPathway2KMap,originalTfExonBasedKeggPathway2KMap,originalTfRegulationBasedKeggPathway2KMap,originalTfAllBasedKeggPathway2KMap,originalTfCellLineExonBasedKeggPathway2KMap,originalTfCellLineRegulationBasedKeggPathway2KMap,originalTfCellLineAllBasedKeggPathway2KMap,dnaseEnrichment,histoneEnrichment,tfEnrichment, keggPathwayEnrichment, tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment,overlapDefinition);		
					
				}
				GlanetRunner.appendLog("Concurrent programming has been ended.");				
					
				
				if(dnaseEnrichment.isDnaseEnrichment()){
				
					//Write to be collected files
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalDnase2KMap,dnase2AllKMap,Commons.TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS,runName);
				}
				
				if (histoneEnrichment.isHistoneEnrichment()){
					
					//Write to be collected files
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalHistone2KMap,histone2AllKMap,Commons.TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS,runName);
				}
				
				if (tfEnrichment.isTfEnrichment() && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){
					
					//Write to be collected files
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
				}
					
				if (keggPathwayEnrichment.isKeggPathwayEnrichment() && !(tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment())){
					
					//Write to be collected files
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				}

				if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){
								
					//Write to be collected files
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
					
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfExonBasedKeggPathway2KMap,tfExonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfRegulationBasedKeggPathway2KMap,tfRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfAllBasedKeggPathway2KMap,tfAllBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				
				}else if(tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
					
					//Write to be collected files
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
					
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);		
													
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineExonBasedKeggPathway2KMap,tfCellLineExonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineRegulationBasedKeggPathway2KMap,tfCellLineRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
					annotatePermutationsUsingForkJoin.writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineAllBasedKeggPathway2KMap,tfCellLineAllBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				}
				//Calculate Empirical P Values and Bonferroni Corrected Empirical P Values ends

				
				originalDnase2KMap = null;
				originalTfbs2KMap = null;
				originalHistone2KMap = null;
				
				originalExonBasedKeggPathway2KMap = null;
				originalRegulationBasedKeggPathway2KMap = null;
				originalAllBasedKeggPathway2KMap = null;
							
				originalTfExonBasedKeggPathway2KMap = null;
				originalTfRegulationBasedKeggPathway2KMap = null;
				originalTfAllBasedKeggPathway2KMap = null;
			
				originalTfCellLineExonBasedKeggPathway2KMap = null;
				originalTfCellLineRegulationBasedKeggPathway2KMap = null;
				originalTfCellLineAllBasedKeggPathway2KMap = null;
									
				//functionalElementName based
				//number of overlaps: k out of n for all permutations
				dnase2AllKMap = null;
				histone2AllKMap = null;
				tfbs2AllKMap = null;
				
				exonBasedKeggPathway2AllKMap = null;
				regulationBasedKeggPathway2AllKMap = null;
				allBasedKeggPathway2AllKMap = null;
				
				//Tf and KeggPathway Enrichment
				tfExonBasedKeggPathway2AllKMap = null;
				tfRegulationBasedKeggPathway2AllKMap = null ;
				tfAllBasedKeggPathway2AllKMap = null;
			
				//Tf and CellLine and KeggPathway Enrichment 
				tfCellLineExonBasedKeggPathway2AllKMap = null;
				tfCellLineRegulationBasedKeggPathway2AllKMap = null ;
				tfCellLineAllBasedKeggPathway2AllKMap = null;
		
				GlanetRunner.appendLog("**************	" + runNumber + ". Run" + "	******************	ends");
				
			}
			//end of for each run number						

		}//end of else
								
	
	}//End of main function

}
