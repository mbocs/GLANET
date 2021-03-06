/**
 * @author burcakotlu
 * @date May 9, 2014 
 * @time 10:45:02 AM
 */
package empiricalpvalues;

import hg19.GRCh37Hg19Chromosome;
import intervaltree.IntervalTree;

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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

import keggpathway.ncbigenes.KeggPathwayUtility;
import mapabilityandgc.ChromosomeBasedGCArray;
import mapabilityandgc.ChromosomeBasedMapabilityArray;
import ui.GlanetRunner;
import userdefined.geneset.UserDefinedGeneSetUtility;
import annotate.intervals.parametric.AnnotateGivenIntervalsWithNumbersWithChoices;
import auxiliary.FileOperations;
import auxiliary.FunctionalElement;
import auxiliary.NumberofComparisons;
import auxiliary.NumberofComparisonsforBonferroniCorrectionCalculation;

import common.Commons;

import enumtypes.AnnotationType;
import enumtypes.ChromosomeName;
import enumtypes.EnrichmentType;
import enumtypes.GenerateRandomDataMode;
import enumtypes.UserDefinedGeneSetInputType;
import enumtypes.WriteGeneratedRandomDataMode;
import enumtypes.WritePermutationBasedAnnotationResultMode;
import enumtypes.WritePermutationBasedandParametricBasedAnnotationResultMode;
import generate.randomdata.RandomDataGenerator;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TLongIntIterator;
import gnu.trove.list.TIntList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * 
 */
public class AnnotatePermutationsWithNumbersWithChoices {


	
	static class GenerateRandomData extends RecursiveTask<Map<Integer,List<InputLine>>>{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5508399455444935122L;
		private final int chromSize;
		private final ChromosomeName chromName;
		private final List<InputLine> chromosomeBasedOriginalInputLines;
			
		private final GenerateRandomDataMode generateRandomDataMode;
		private final WriteGeneratedRandomDataMode writeGeneratedRandomDataMode;
		
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
					 					      
				     GlanetRunner.appendLog("Generate Random Data For Permutation: " + permutationNumber.toString() + "\t" +chromName.toString());	
				     
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
				    	 listofAllMaps.add(AnnotateGivenIntervalsWithNumbersWithChoices.annotatePermutationwithoutIO(permutationNumber,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap,overlapDefinition));
				     }
				     
				     //NEW FUNCTIONALITY HAS BEEN ADDED
				     else if (writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){
				     	 listofAllMaps.add(AnnotateGivenIntervalsWithNumbersWithChoices.annotatePermutationwithIO(outputFolder,permutationNumber,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2KeggPathwayMap,overlapDefinition));
				     }						
				}//End of FOR
					
				combineListofAllMaps(listofAllMaps,allMaps);
				listofAllMaps = null;
				return allMaps;
				
	
			}
		}		
		
		//Why are we doing this?
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

	
	
	static class AnnotateWithNumbers extends RecursiveTask<AllMapsWithNumbers>{
		
		
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
	
	private final TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap;
	
	private final String outputFolder;
	
	private final int overlapDefinition;
		
		
	public AnnotateWithNumbers(String outputFolder,int chromSize, ChromosomeName chromName, Map<Integer,List<InputLine>> randomlyGeneratedDataMap, int runNumber,int numberofPermutations, WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,int lowIndex, int highIndex, List<AnnotationTask> listofAnnotationTasks, IntervalTree intervalTree, IntervalTree ucscRefSeqGenesIntervalTree,AnnotationType annotationType, EnrichmentType tfandKeggPathwayEnrichmentType, TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap,int overlapDefinition) {
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
		this.geneId2ListofGeneSetNumberMap = geneId2ListofGeneSetNumberMap;
		
		this.overlapDefinition = overlapDefinition;		
	}
	
	
		
		protected AllMapsWithNumbers compute() {
			
			int middleIndex;
			AllMapsWithNumbers rightAllMapsWithNumbers;
	        AllMapsWithNumbers leftAllMapsWithNumbers;
	        
	    	AnnotationTask annotationTask;
			Integer permutationNumber;
			List<AllMapsWithNumbers> listofAllMapsWithNumbers;
			AllMapsWithNumbers allMapsWithNumbers;
				
			//DIVIDE
			if (highIndex-lowIndex>9){
			 	middleIndex = lowIndex + (highIndex - lowIndex) / 2;
			 	AnnotateWithNumbers left  = new AnnotateWithNumbers(outputFolder,chromSize, chromName, randomlyGeneratedDataMap, runNumber,numberofPermutations,writePermutationBasedandParametricBasedAnnotationResultMode,lowIndex,middleIndex,listofAnnotationTasks,intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2ListofGeneSetNumberMap,overlapDefinition);
			 	AnnotateWithNumbers right = new AnnotateWithNumbers(outputFolder,chromSize, chromName, randomlyGeneratedDataMap, runNumber,numberofPermutations,writePermutationBasedandParametricBasedAnnotationResultMode,middleIndex,highIndex,listofAnnotationTasks,intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2ListofGeneSetNumberMap,overlapDefinition);
	            left.fork();
	            rightAllMapsWithNumbers = right.compute();
	            leftAllMapsWithNumbers  = left.join();
	            combineLeftAllMapsandRightAllMaps(leftAllMapsWithNumbers, rightAllMapsWithNumbers);
	            leftAllMapsWithNumbers= null;
	            return rightAllMapsWithNumbers;
			}
			//CONQUER
			else {
				
				listofAllMapsWithNumbers = new ArrayList<AllMapsWithNumbers>();
				allMapsWithNumbers = new AllMapsWithNumbers();
				 	
				for(int i=lowIndex; i<highIndex; i++){
					 annotationTask = listofAnnotationTasks.get(i);
					 permutationNumber = annotationTask.getPermutationNumber();
					      
				     GlanetRunner.appendLog("Annotate Random Data For Permutation: " + permutationNumber + "\t" +chromName.toString() + "\t" + annotationType);	
				     
				     //WITHOUT IO
				     if(writePermutationBasedandParametricBasedAnnotationResultMode.isDoNotWritePermutationBasedandParametricBasedAnnotationResultMode()){
				    	 listofAllMapsWithNumbers.add(AnnotateGivenIntervalsWithNumbersWithChoices.annotatePermutationwithoutIOwithNumbers(permutationNumber,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2ListofGeneSetNumberMap,overlapDefinition));
				     }
				     
				     //WITH IO
				     else if (writePermutationBasedandParametricBasedAnnotationResultMode.isWritePermutationBasedandParametricBasedAnnotationResultMode()){
				     	 listofAllMapsWithNumbers.add(AnnotateGivenIntervalsWithNumbersWithChoices.annotatePermutationwithIOwithNumbers(outputFolder,permutationNumber,chromName,randomlyGeneratedDataMap.get(permutationNumber), intervalTree,ucscRefSeqGenesIntervalTree,annotationType,tfandKeggPathwayEnrichmentType,geneId2ListofGeneSetNumberMap,overlapDefinition));
				     }						
				}//End of FOR
					
				combineListofAllMapsWithNumbers(listofAllMapsWithNumbers,allMapsWithNumbers);
				listofAllMapsWithNumbers = null;
				return allMapsWithNumbers;
				
	
			}
		}		
		
		//Accumulate the allMaps in the left into allMaps in the right
		protected void combineListofAllMapsWithNumbers(List<AllMapsWithNumbers> listofAllMaps,AllMapsWithNumbers allMaps){
			for(int i =0; i<listofAllMaps.size(); i++){
				combineLeftAllMapsandRightAllMaps(listofAllMaps.get(i), allMaps);
			}
		}	
		
		
		
		//Combine leftAllMapsWithNumbers and rightAllMapsWithNumbers in rightAllMapsWithNumbers
		protected void combineLeftAllMapsandRightAllMaps(AllMapsWithNumbers leftAllMapsWithNumbers, AllMapsWithNumbers rightAllMapsWithNumbers) {
			
			//LEFT ALL MAPS WITH NUMBERS
			TIntIntMap leftPermutationNumberDnaseCellLineNumber2KMap 			= leftAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();
			TLongIntMap leftPermutationNumberTfNumberCellLineNumber2KMap 		= leftAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();
			TLongIntMap leftPermutationNumberHistoneNumberCellLineNumber2KMap 	= leftAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();
			
			TIntIntMap leftPermutationNumberExonBasedUserDefinedGeneSet2KMap 			= leftAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSet2KMap();
			TIntIntMap leftPermutationNumberRegulationBasedUserDefinedGeneSet2KMap 		= leftAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSet2KMap();
			TIntIntMap leftPermutationNumberAllBasedUserDefinedGeneSet2KMap 			= leftAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSet2KMap();
			
			TIntIntMap leftPermutationNumberExonBasedKeggPathway2KMap 			= leftAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap();
			TIntIntMap leftPermutationNumberRegulationBasedKeggPathway2KMap 	= leftAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap();
			TIntIntMap leftPermutationNumberAllBasedKeggPathway2KMap 			= leftAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap();
			
			TLongIntMap leftPermutationNumberTfExonBasedKeggPathway2KMap 		= leftAllMapsWithNumbers.getPermutationNumberTfExonBasedKeggPathway2KMap();
			TLongIntMap leftPermutationNumberTfRegulationBasedKeggPathway2KMap 	= leftAllMapsWithNumbers.getPermutationNumberTfRegulationBasedKeggPathway2KMap();
			TLongIntMap leftPermutationNumberTfAllBasedKeggPathway2KMap 		= leftAllMapsWithNumbers.getPermutationNumberTfAllBasedKeggPathway2KMap();

			TLongIntMap leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap 		= leftAllMapsWithNumbers.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap();
			TLongIntMap leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap 	= leftAllMapsWithNumbers.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap();
			TLongIntMap leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap 		= leftAllMapsWithNumbers.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap();
			
		
			//RIGHT ALL MAPS WITH NUMBERS
			TIntIntMap  rightPermutationNumberDnaseCellLineNumber2KMap 			= rightAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap();
			TLongIntMap rightPermutationNumberTfNumberCellLineNumber2KMap 		= rightAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap();
			TLongIntMap rightPermutationNumberHistoneNumberCellLineNumber2KMap 	= rightAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap();
			
			TIntIntMap rightPermutationNumberExonBasedUserDefinedGeneSet2KMap 			= rightAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSet2KMap();
			TIntIntMap rightPermutationNumberRegulationBasedUserDefinedGeneSet2KMap 	= rightAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSet2KMap();
			TIntIntMap rightPermutationNumberAllBasedUserDefinedGeneSet2KMap 			= rightAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSet2KMap();
			
			TIntIntMap rightPermutationNumberExonBasedKeggPathway2KMap 			= rightAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap();
			TIntIntMap rightPermutationNumberRegulationBasedKeggPathway2KMap 	= rightAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap();
			TIntIntMap rightPermutationNumberAllBasedKeggPathway2KMap 			= rightAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap();
			
			TLongIntMap rightPermutationNumberTfExonBasedKeggPathway2KMap 		= rightAllMapsWithNumbers.getPermutationNumberTfExonBasedKeggPathway2KMap();
			TLongIntMap rightPermutationNumberTfRegulationBasedKeggPathway2KMap = rightAllMapsWithNumbers.getPermutationNumberTfRegulationBasedKeggPathway2KMap();
			TLongIntMap rightPermutationNumberTfAllBasedKeggPathway2KMap 		= rightAllMapsWithNumbers.getPermutationNumberTfAllBasedKeggPathway2KMap();

			TLongIntMap rightPermutationNumberTfCellLineExonBasedKeggPathway2KMap 		= rightAllMapsWithNumbers.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap();
			TLongIntMap rightPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap = rightAllMapsWithNumbers.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap();
			TLongIntMap rightPermutationNumberTfCellLineAllBasedKeggPathway2KMap 		= rightAllMapsWithNumbers.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap();
							
			//DNASE
			if (leftPermutationNumberDnaseCellLineNumber2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberDnaseCellLineNumber2KMap,rightPermutationNumberDnaseCellLineNumber2KMap);
				leftPermutationNumberDnaseCellLineNumber2KMap = null;
			}
			
			//TF
			if(leftPermutationNumberTfNumberCellLineNumber2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfNumberCellLineNumber2KMap,rightPermutationNumberTfNumberCellLineNumber2KMap);
				leftPermutationNumberTfNumberCellLineNumber2KMap = null;
			}
			
			//HISTONE
			if(leftPermutationNumberHistoneNumberCellLineNumber2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberHistoneNumberCellLineNumber2KMap,rightPermutationNumberHistoneNumberCellLineNumber2KMap);
				leftPermutationNumberHistoneNumberCellLineNumber2KMap = null;					
			}
			
		
			//EXON BASED USERDEFINED GENESET  
			if(leftPermutationNumberExonBasedUserDefinedGeneSet2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberExonBasedUserDefinedGeneSet2KMap,rightPermutationNumberExonBasedUserDefinedGeneSet2KMap);
				leftPermutationNumberExonBasedUserDefinedGeneSet2KMap = null;					
			}
			
			//REGULATION BASED USERDEFINED GENESET
			if (leftPermutationNumberRegulationBasedUserDefinedGeneSet2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberRegulationBasedUserDefinedGeneSet2KMap,rightPermutationNumberRegulationBasedUserDefinedGeneSet2KMap);
				leftPermutationNumberRegulationBasedUserDefinedGeneSet2KMap = null;
			}
			
			//ALL BASED USERDEFINED GENESET
			if (leftPermutationNumberAllBasedUserDefinedGeneSet2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberAllBasedUserDefinedGeneSet2KMap,rightPermutationNumberAllBasedUserDefinedGeneSet2KMap);
				leftPermutationNumberAllBasedUserDefinedGeneSet2KMap = null;
			}
			
			
			//EXON BASED KEGG PATHWAY
			if(leftPermutationNumberExonBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberExonBasedKeggPathway2KMap,rightPermutationNumberExonBasedKeggPathway2KMap);
				leftPermutationNumberExonBasedKeggPathway2KMap = null;					
			}
			
			//REGULATION BASED KEGG PATHWAY
			if (leftPermutationNumberRegulationBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberRegulationBasedKeggPathway2KMap,rightPermutationNumberRegulationBasedKeggPathway2KMap);
				leftPermutationNumberRegulationBasedKeggPathway2KMap = null;
			}
			
			//ALL BASED KEGG PATHWAY
			if (leftPermutationNumberAllBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberAllBasedKeggPathway2KMap,rightPermutationNumberAllBasedKeggPathway2KMap);
				leftPermutationNumberAllBasedKeggPathway2KMap = null;
			}
			
			//TF and EXON BASED KEGG PATHWAY
			if (leftPermutationNumberTfExonBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfExonBasedKeggPathway2KMap,rightPermutationNumberTfExonBasedKeggPathway2KMap);
				leftPermutationNumberTfExonBasedKeggPathway2KMap = null;
			}
			
			//TF and REGULATION BASED KEGG PATHWAY
			if (leftPermutationNumberTfRegulationBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfRegulationBasedKeggPathway2KMap,rightPermutationNumberTfRegulationBasedKeggPathway2KMap);
				leftPermutationNumberTfRegulationBasedKeggPathway2KMap = null;
			}
			
			//TF and ALL BASED KEGG PATHWAY
			if (leftPermutationNumberTfAllBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfAllBasedKeggPathway2KMap,rightPermutationNumberTfAllBasedKeggPathway2KMap);
				leftPermutationNumberTfAllBasedKeggPathway2KMap = null;
			}
			
			//TF and CellLine and EXON BASED KEGG PATHWAY
			if (leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap,rightPermutationNumberTfCellLineExonBasedKeggPathway2KMap);
				leftPermutationNumberTfCellLineExonBasedKeggPathway2KMap = null;
			}
			
			//TF and CellLine and REGULATION BASED KEGG PATHWAY
			if (leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap,rightPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap);
				leftPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap = null;
			}
			
			//TF and CellLine and ALL BASED KEGG PATHWAY
			if (leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap!=null){
				combineLeftMapandRightMap(leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap,rightPermutationNumberTfCellLineAllBasedKeggPathway2KMap);
				leftPermutationNumberTfCellLineAllBasedKeggPathway2KMap = null;
			}
		
					
			
			//delete AllMaps
			//deleteAllMaps(leftAllMaps);
			leftAllMapsWithNumbers = null;
					
		}//End of combineAllMaps
		
		
		//TIntIntMap version starts
		//Accumulate leftMapWithNumbers in the rightMapWithNumbers
		//Accumulate number of overlaps 
		//based on permutationNumber and ElementName
		protected void  combineLeftMapandRightMap(TIntIntMap leftMapWithNumbers, TIntIntMap rightMapWithNumbers){
						
			for(TIntIntIterator it =  leftMapWithNumbers.iterator(); it.hasNext(); ){
				
				it.advance();
				
				int permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();
				
				if (!(rightMapWithNumbers.containsKey(permutationNumberCellLineNumberOrKeggPathwayNumber))){
					rightMapWithNumbers.put(permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
				}else{
					rightMapWithNumbers.put(permutationNumberCellLineNumberOrKeggPathwayNumber, rightMapWithNumbers.get(permutationNumberCellLineNumberOrKeggPathwayNumber)+numberofOverlaps);
				}
			}
				
			
			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;
		
		}
		//TIntIntMap version ends
		  	
		//Accumulate leftMapWithNumbers in the rightMapWithNumbers
		//Accumulate number of overlaps 
		//based on permutationNumber and ElementName
		protected void  combineLeftMapandRightMap(TLongIntMap leftMapWithNumbers, TLongIntMap rightMapWithNumbers){
						
			for(TLongIntIterator it =  leftMapWithNumbers.iterator(); it.hasNext(); ){
				
				it.advance();
				
				long permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
				int numberofOverlaps = it.value();
				
				if (!(rightMapWithNumbers.containsKey(permutationNumberElementNumberCellLineNumberKeggPathwayNumber))){
					rightMapWithNumbers.put(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, numberofOverlaps);
				}else{
					rightMapWithNumbers.put(permutationNumberElementNumberCellLineNumberKeggPathwayNumber, rightMapWithNumbers.get(permutationNumberElementNumberCellLineNumberKeggPathwayNumber)+numberofOverlaps);
				}
			}
				
			
			leftMapWithNumbers.clear();
			leftMapWithNumbers = null;
		
		}
		
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
	
	
	public static void readOriginalInputDataLines(List<InputLine> originalInputLines, String inputFileName){
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
	
	
	public  static void partitionDataChromosomeBased(List<InputLine> originalInputLines, Map<ChromosomeName,List<InputLine>> chromosomeBasedOriginalInputLines){
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
	
	//TLongIntMap TIntObjectMap<TIntList> TIntIntMap starts
	public static void convert(TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,TIntObjectMap<TIntList> elementNumber2AllKMap, TIntIntMap elementNumber2OriginalKMap){
		
		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;		
		Integer elementNumberCellLineNumberOrKeggPathwayNumber;
		
		Integer numberofOverlaps;
				
		TIntList list;
		
		
		for(TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext(); ){
			
			 it.advance();
			  
			//example permutationAugmentedName PERMUTATION0_K562
			//@todo get permutationNumber from permutationAugmentedName
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
						
			elementNumberCellLineNumberOrKeggPathwayNumber = IntervalTree.getElementNumberCellLineNumberOrKeggPathwayNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			
			//example permutationNumber PERMUTATION0
				
			//@todo check this zero value for permutation of original data
			if (Commons.ZERO.equals(permutationNumber)){
				elementNumber2OriginalKMap.put(elementNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
			}else{
				list =elementNumber2AllKMap.get(elementNumberCellLineNumberOrKeggPathwayNumber);
				
				if(list ==null){
					list = new TIntArrayList();
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(elementNumberCellLineNumberOrKeggPathwayNumber, list);
				}else{
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(elementNumberCellLineNumberOrKeggPathwayNumber, list);					
				}
			}			
		}
		//yeni ends
		
	}
	//TLongIntMap TIntObjectMap<TIntList> TIntIntMap ends
	
	
	//TIntIntMap TIntObjectMap<TIntList>  TIntIntMap version starts
	public static void convert(TIntIntMap permutationNumberCellLineNumberOrGeneSetNumber2KMap,TIntObjectMap<TIntList> cellLineNumberorGeneSetNumber2AllKMap, TIntIntMap cellLineNumberorGeneSetNumber2OriginalKMap){
		
		int permutationNumberCellLineNumberOrGeneSetNumber;
		Integer permutationNumber;		
		Integer cellLineNumberOrGeneSetNumber;
		
		Integer numberofOverlaps;
				
		TIntList list;
				
		
		for(TIntIntIterator it = permutationNumberCellLineNumberOrGeneSetNumber2KMap.iterator(); it.hasNext(); ){
			
			 it.advance();
			  
			//example permutationAugmentedName PERMUTATION0_K562
			//@todo get permutationNumber from permutationAugmentedName
			permutationNumberCellLineNumberOrGeneSetNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberCellLineNumberOrGeneSetNumber);
						
			cellLineNumberOrGeneSetNumber = IntervalTree.getCellLineNumberOrGeneSetNumber(permutationNumberCellLineNumberOrGeneSetNumber);
			
			//example permutationNumber PERMUTATION0
				
			//@todo check this zero value for permutation of original data
			if (Commons.ZERO.equals(permutationNumber)){
				cellLineNumberorGeneSetNumber2OriginalKMap.put(cellLineNumberOrGeneSetNumber, numberofOverlaps);
			}else{
				list =cellLineNumberorGeneSetNumber2AllKMap.get(cellLineNumberOrGeneSetNumber);
				
				if(list ==null){
					list = new TIntArrayList();
					list.add(numberofOverlaps);
					cellLineNumberorGeneSetNumber2AllKMap.put(cellLineNumberOrGeneSetNumber, list);
				}else{
					list.add(numberofOverlaps);
					cellLineNumberorGeneSetNumber2AllKMap.put(cellLineNumberOrGeneSetNumber, list);					
				}
			}			
		}
				
	}	
	//TIntIntMap TIntObjectMap<TIntList>  TIntIntMap  version ends
	
	
	//TLongIntMap TLongObjectMap TLongIntMap version starts
	//@todo  I must get permutationNumber and elementNumber from combinedNumber
	//convert permutation augmented name to only name
	//Fill elementName2ALLMap and originalElementName2KMap in convert methods	
	public static void convert(TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap,TLongObjectMap<TIntList> elementNumber2AllKMap, TLongIntMap elementNumber2OriginalKMap){
		
		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;		
		Long otherNumber;
		
		Integer numberofOverlaps;
				
		TIntList list;
			
		for(TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext(); ){
			
			 it.advance();
			  
			//example permutationAugmentedName PERMUTATION0_K562
			//@todo get permutationNumber from permutationAugmentedName
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber = IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
						
			otherNumber = IntervalTree.getOtherNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			
			//example permutationNumber PERMUTATION0
				
			//@todo check this zero value for permutation of original data
			if (Commons.ZERO.equals(permutationNumber)){
				elementNumber2OriginalKMap.put(otherNumber, numberofOverlaps);
			}else{
				list =elementNumber2AllKMap.get(otherNumber);
				
				if(list ==null){
					list = new TIntArrayList();
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(otherNumber, list);
				}else{
					list.add(numberofOverlaps);
					elementNumber2AllKMap.put(otherNumber, list);					
				}
			}			
		}
		
		
	}
	//TLongIntMap TLongObjectMap TLongIntMap version ends

	
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
		
		
		
	
	public static void generateAnnotationTasks(ChromosomeName chromName, List<AnnotationTask> listofAnnotationTasks,int runNumber,int numberofPermutationsinThisRun,int numberofPermutationsinEachRun){
		
		
			for(int permutationNumber = 1; permutationNumber<= numberofPermutationsinThisRun; permutationNumber++){
				listofAnnotationTasks.add(new AnnotationTask(chromName, (runNumber-1)* numberofPermutationsinEachRun + permutationNumber));
			}
	}
	
	
	public static void generateAnnotationTaskforOriginalData(ChromosomeName chromName, List<AnnotationTask> listofAnnotationTasks,Integer originalDataPermutationNumber){
		listofAnnotationTasks.add(new AnnotationTask(chromName, 0));
	}
	
	public static IntervalTree generateDnaseIntervalTreeWithNumbers(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createDnaseIntervalTreeWithNumbers(dataFolder,chromName);	
	}
	
	public static IntervalTree generateTfbsIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createTfbsIntervalTree(dataFolder,chromName);	
	}
	
	public static IntervalTree generateTfbsIntervalTreeWithNumbers(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createTfbsIntervalTreeWithNumbers(dataFolder,chromName);	
	}
	
	public static IntervalTree generateHistoneIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createHistoneIntervalTree(dataFolder,chromName);	
	}
	
	
	public static IntervalTree generateHistoneIntervalTreeWithNumbers(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createHistoneIntervalTreeWithNumbers(dataFolder,chromName);	
	}
	
	public static IntervalTree generateUcscRefSeqGeneIntervalTree(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createUcscRefSeqGenesIntervalTree(dataFolder,chromName);	
	}
	
	public static IntervalTree generateUcscRefSeqGeneIntervalTreeWithNumbers(String dataFolder,ChromosomeName chromName){		
		return AnnotateGivenIntervalsWithNumbersWithChoices.createUcscRefSeqGenesIntervalTreeWithNumbers(dataFolder,chromName);	
	}
	
	public void generateIntervalTrees(String outputFolder,ChromosomeName chromName, List<IntervalTree> listofIntervalTrees){
		IntervalTree dnaseIntervalTree;
		IntervalTree tfbsIntervalTree ;
		IntervalTree histoneIntervalTree;
		IntervalTree ucscRefSeqGeneIntervalTree;
		
				
		dnaseIntervalTree			= AnnotateGivenIntervalsWithNumbersWithChoices.createDnaseIntervalTree(outputFolder,chromName);
		tfbsIntervalTree 			= AnnotateGivenIntervalsWithNumbersWithChoices.createTfbsIntervalTree(outputFolder,chromName);
		histoneIntervalTree  		= AnnotateGivenIntervalsWithNumbersWithChoices.createHistoneIntervalTree(outputFolder,chromName);
		ucscRefSeqGeneIntervalTree 	= AnnotateGivenIntervalsWithNumbersWithChoices.createUcscRefSeqGenesIntervalTree(outputFolder,chromName);
		
		//order is important
		listofIntervalTrees.add(dnaseIntervalTree);
		listofIntervalTrees.add(tfbsIntervalTree);
		listofIntervalTrees.add(histoneIntervalTree);
		listofIntervalTrees.add(ucscRefSeqGeneIntervalTree);
		
	}

	public static void closeBufferedWriters(Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap){
		
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
	
	
	//TIntIntMap version starts
	public static void writeAnnotationstoFiles(String outputFolder,TIntIntMap permutationNumberCellLineNumberorGeneSetNumber2KMap, Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName){
		
		int permutationNumberCellLineNumberorGeneSetNumber;
		Integer permutationNumber;
		Integer cellLineNumberorGeneSetNumber;
		
		
		
		Integer numberofOverlaps;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		
		for(TIntIntIterator it = permutationNumberCellLineNumberorGeneSetNumber2KMap.iterator(); it.hasNext();){
			
			it.advance();
			
			permutationNumberCellLineNumberorGeneSetNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber 	= IntervalTree.getPermutationNumber(permutationNumberCellLineNumberorGeneSetNumber);
			cellLineNumberorGeneSetNumber 		= IntervalTree.getCellLineNumberOrGeneSetNumber(permutationNumberCellLineNumberorGeneSetNumber);
				
					
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber) ;
			
			try {
				
				if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + folderName + permutationNumber +  "_" + extraFileName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						
						bufferedWriter.write("CellLineNumberOrKeggPathwayNumber" + "\t" + "NumberofOverlaps" +System.getProperty("line.separator"));					
						
						permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);							
				}
				
				
				
				if (cellLineNumberorGeneSetNumber>0){
					bufferedWriter.write(cellLineNumberorGeneSetNumber +"\t");					
				}
				
				
				
				bufferedWriter.write(numberofOverlaps +System.getProperty("line.separator"));
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of for
					
	}
	//TIntIntMap version ends
	
	
	public static void writeAnnotationstoFiles_ElementNumberCellLineNumber(String outputFolder,TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName){
		
		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer cellLineNumber;
		
		Integer numberofOverlaps;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		
		for(TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){
			
			it.advance();
			
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber 	= IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			elementNumber 		= IntervalTree.getTforHistoneNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			cellLineNumber 		= IntervalTree.getCellLineNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
				
					
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber) ;
			
			try {
				
				if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + folderName + permutationNumber +  "_" + extraFileName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						
						bufferedWriter.write("TforHistoneNumber" + "\t" + "CellLineNumber" + "\t" + "NumberofOverlaps" + System.getProperty("line.separator"));
						
						permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);							
				}
				
				if (elementNumber>0){
					bufferedWriter.write(elementNumber +"\t");
				}
				
				if (cellLineNumber>0){
					bufferedWriter.write(cellLineNumber +"\t");					
				}
				
						
				bufferedWriter.write(numberofOverlaps +System.getProperty("line.separator"));
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of for
					
	}
	
	
	public static void writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(String outputFolder,TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName){
		
		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer keggPathwayNumber;
		
		Integer numberofOverlaps;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		
		for(TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){
			
			it.advance();
			
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber 	= IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			elementNumber 		= IntervalTree.getTforHistoneNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			keggPathwayNumber 	= IntervalTree.getGeneSetNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			
					
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber) ;
			
			try {
				
				if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + folderName + permutationNumber +  "_" + extraFileName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						
						bufferedWriter.write("TfNumber" + "\t" + "KeggPathwayNumber" +"\t" + "NumberofOverlaps" + System.getProperty("line.separator"));
						
						permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);							
				}
				
				if (elementNumber>0){
					bufferedWriter.write(elementNumber +"\t");
				}
				
				if (keggPathwayNumber>0){
					bufferedWriter.write(keggPathwayNumber +"\t");		
				}
				
				bufferedWriter.write(numberofOverlaps +System.getProperty("line.separator"));
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of for
					
	}
	
	public static void writeAnnotationstoFiles(String outputFolder,TLongIntMap permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap, Map<Integer,BufferedWriter> permutationNumber2BufferedWriterHashMap, String folderName, String extraFileName){
		
		Long permutationNumberElementNumberCellLineNumberKeggPathwayNumber;
		Integer permutationNumber;
		Integer elementNumber;
		Integer cellLineNumber;
		Integer keggPathwayNumber;
		
		Integer numberofOverlaps;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		
		for(TLongIntIterator it = permutationNumberElementNumberCellLineNumberKeggPathwayNumber2KMap.iterator(); it.hasNext();){
			
			it.advance();
			
			permutationNumberElementNumberCellLineNumberKeggPathwayNumber = it.key();
			numberofOverlaps = it.value();
			
			permutationNumber 	= IntervalTree.getPermutationNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			elementNumber 		= IntervalTree.getTforHistoneNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			cellLineNumber 		= IntervalTree.getCellLineNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			keggPathwayNumber 	= IntervalTree.getGeneSetNumber(permutationNumberElementNumberCellLineNumberKeggPathwayNumber);
			
					
			bufferedWriter = permutationNumber2BufferedWriterHashMap.get(permutationNumber) ;
			
			try {
				
				if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + folderName + permutationNumber +  "_" + extraFileName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						
						bufferedWriter.write("TfNumber" + "\t" + "CellLineNumber" + "\t" + "KeggPathwayNumber" +"\t" + "NumberofOverlaps" + System.getProperty("line.separator"));
						
						permutationNumber2BufferedWriterHashMap.put(permutationNumber, bufferedWriter);							
				}
				
				if (elementNumber>0){
					bufferedWriter.write(elementNumber +"\t");
				}
				
				if (cellLineNumber>0){
					bufferedWriter.write(cellLineNumber +"\t");					
				}
				
				if (keggPathwayNumber>0){
					bufferedWriter.write(keggPathwayNumber +"\t");		
				}
				
				bufferedWriter.write(numberofOverlaps +System.getProperty("line.separator"));
				
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//End of for
					
	}
	
	//TIntIntMap version starts
	//Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	//Accumulate number of overlaps coming from each chromosome
	//based on permutationNumber and ElementName
	public static void accumulate(TIntIntMap chromosomeBasedName2KMap, TIntIntMap accumulatedName2KMap){
		int permutationNumberCellLineNumberOrKeggPathwayNumber;
		Integer numberofOverlaps;
		
		// accessing keys/values through an iterator:
		for ( TIntIntIterator it = chromosomeBasedName2KMap.iterator(); it.hasNext(); ) {
		   
			it.advance();
		    
			permutationNumberCellLineNumberOrKeggPathwayNumber = it.key();
		    numberofOverlaps = it.value();
		    
			if (!(accumulatedName2KMap.containsKey(permutationNumberCellLineNumberOrKeggPathwayNumber))){
				accumulatedName2KMap.put(permutationNumberCellLineNumberOrKeggPathwayNumber, numberofOverlaps);
			}else{
				accumulatedName2KMap.put(permutationNumberCellLineNumberOrKeggPathwayNumber, accumulatedName2KMap.get(permutationNumberCellLineNumberOrKeggPathwayNumber) + numberofOverlaps);			
			}		  
		    
		}
				
		
	}
	//TIntIntMap version ends
	
	//Accumulate chromosomeBasedName2KMap results in accumulatedName2KMap
	//Accumulate number of overlaps coming from each chromosome
	//based on permutationNumber and ElementName
	public static void accumulate(TLongIntMap chromosomeBasedName2KMap, TLongIntMap accumulatedName2KMap){
		Long permutationNumberElementNumber;
		Integer numberofOverlaps;
		
		
		// accessing keys/values through an iterator:
		for ( TLongIntIterator it = chromosomeBasedName2KMap.iterator(); it.hasNext(); ) {
		   
			it.advance();
		    
		    permutationNumberElementNumber = it.key();
		    numberofOverlaps = it.value();
		    
			if (!(accumulatedName2KMap.containsKey(permutationNumberElementNumber))){
				accumulatedName2KMap.put(permutationNumberElementNumber, numberofOverlaps);
			}else{
				accumulatedName2KMap.put(permutationNumberElementNumber, accumulatedName2KMap.get(permutationNumberElementNumber) + numberofOverlaps);
				
			}		  
		    
		}
				
		
	}
	
	
	
	public static void accumulate(AllMapsWithNumbers chromosomeBasedAllMaps, AllMapsWithNumbers accumulatedAllMaps){
		
		//Dnase
		accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap());
		
		//Tfbs
		accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap());
		
		//Histone
		accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap());
		
		//Exon Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap());
		
		//Regulation Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap());
	
		//All Based Kegg Pathway
		accumulate(chromosomeBasedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberAllBasedKeggPathway2KMap());
			
	}
	
	//Accumulate chromosomeBasedAllMaps in accumulatedAllMaps
	//Coming from each chromosome
	public static void accumulate(AllMapsWithNumbers chromosomeBasedAllMaps, AllMapsWithNumbers accumulatedAllMaps, AnnotationType annotationType){
		
		if (annotationType.isDnaseAnnotation()){
			//Dnase
			accumulate(chromosomeBasedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberDnaseCellLineNumber2KMap());
		}else if (annotationType.isTfAnnotation()){
			//Tfbs
			accumulate(chromosomeBasedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberTfNumberCellLineNumber2KMap());
		}else if (annotationType.isHistoneAnnotation()){
			//Histone
			accumulate(chromosomeBasedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap(), accumulatedAllMaps.getPermutationNumberHistoneNumberCellLineNumber2KMap());
		}else if (annotationType.isUserDefinedGeneSetAnnotation()){
			//Exon Based User Defined GeneSet
			accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedUserDefinedGeneSet2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedUserDefinedGeneSet2KMap());
			
			//Regulation Based User Defined GeneSet
			accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedUserDefinedGeneSet2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedUserDefinedGeneSet2KMap());
			
			//All Based User Defined GeneSet
			accumulate(chromosomeBasedAllMaps.getPermutationNumberAllBasedUserDefinedGeneSet2KMap(), accumulatedAllMaps.getPermutationNumberAllBasedUserDefinedGeneSet2KMap());
		}else if (annotationType.isKeggPathwayAnnotation()){
			//Exon Based KEGG Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberExonBasedKeggPathway2KMap());
			
			//Regulation Based KEGG Pathway
			accumulate(chromosomeBasedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap(), accumulatedAllMaps.getPermutationNumberRegulationBasedKeggPathway2KMap());
			
			//All Based KEGG Pathway
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
	
	
	public static void deleteIntervalTree(IntervalTree intervalTree){
		
		IntervalTree.deleteNodesofIntervalTree(intervalTree.getRoot());
		intervalTree.setRoot(null);
		intervalTree 	= null;
	}
	
	
	public static void deleteAnnotationTasks(List<AnnotationTask> listofAnnotationTasks){
		for(AnnotationTask annotationTask : listofAnnotationTasks){
			annotationTask.setChromName(null);
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
	public static void annotateAllPermutationsInThreads(
			String outputFolder,
			String dataFolder,
			int NUMBER_OF_AVAILABLE_PROCESSORS,
			int runNumber, 
			int numberofPermutationsinThisRun,
			int numberofPermutationsinEachRun,
			List<InputLine> allOriginalInputLines, 
			TIntObjectMap<TIntList> dnase2AllKMap,
			TIntObjectMap<TIntList> tfbs2AllKMap,
			TIntObjectMap<TIntList> histone2AllKMap,
			TIntObjectMap<TIntList> exonBasedUserDefinedGeneSet2AllKMap,
			TIntObjectMap<TIntList> regulationBasedUserDefinedGeneSet2AllKMap,
			TIntObjectMap<TIntList> allBasedUserDefinedGeneSet2AllKMap,
			TIntObjectMap<TIntList> exonBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> regulationBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> allBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> tfExonBasedKeggPathway2AllKMap, 
			TIntObjectMap<TIntList> tfRegulationBasedKeggPathway2AllKMap,
			TIntObjectMap<TIntList> tfAllBasedKeggPathway2AllKMap,
			TLongObjectMap<TIntList> tfCellLineExonBasedKeggPathway2AllKMap, 
			TLongObjectMap<TIntList> tfCellLineRegulationBasedKeggPathway2AllKMap,
			TLongObjectMap<TIntList> tfCellLineAllBasedKeggPathway2AllKMap, 
			GenerateRandomDataMode generateRandomDataMode, 
			WriteGeneratedRandomDataMode writeGeneratedRandomDataMode,
			WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode,
			WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode,
			TIntIntMap originalDnase2KMap,
			TIntIntMap originalTfbs2KMap,
			TIntIntMap originalHistone2KMap,
			TIntIntMap originalExonBasedUserDefinedGeneSet2KMap,
			TIntIntMap originalRegulationBasedUserDefinedGeneSet2KMap,
			TIntIntMap originalAllBasedUserDefinedGeneSet2KMap, 
			TIntIntMap originalExonBasedKeggPathway2KMap,
			TIntIntMap originalRegulationBasedKeggPathway2KMap,
			TIntIntMap originalAllBasedKeggPathway2KMap, 
			TIntIntMap originalTfExonBasedKeggPathway2KMap,
			TIntIntMap originalTfRegulationBasedKeggPathway2KMap,
			TIntIntMap originalTfAllBasedKeggPathway2KMap,
			TLongIntMap originalTfCellLineExonBasedKeggPathway2KMap,
			TLongIntMap originalTfCellLineRegulationBasedKeggPathway2KMap,
			TLongIntMap originalTfCellLineAllBasedKeggPathway2KMap, 
			EnrichmentType dnaseEnrichmentType, 
			EnrichmentType histoneEnrichmentType, 
			EnrichmentType tfEnrichmentType, 
			EnrichmentType userDefinedGeneSetEnrichmentType,
			EnrichmentType keggPathwayEnrichmentType, 
			EnrichmentType tfKeggPathwayEnrichmentType, 
			EnrichmentType tfCellLineKeggPathwayEnrichmentType,
			int overlapDefinition,
			TIntObjectMap<TShortList> geneId2ListofKeggPathwayNumberMap,
			TIntObjectMap<TShortList> geneId2ListofUserDefinedGeneSetNumberMap){
		
		String permutationBasedResultDirectory;
		
		//allMaps stores one chromosome based results
		AllMapsWithNumbers allMapsWithNumbers = new AllMapsWithNumbers();
		
		//accumulatedAllMaps stores all chromosome results
		//In other words, it contains accumulated results coming from each chromosome
		AllMapsWithNumbers accumulatedAllMapsWithNumbers = new AllMapsWithNumbers();

				
		/******************************************************************************************************/
		/****************************ACCUMULATED ALL MAPS******************************************************/
		//DNASE 
		//TF 
		//HISTONE
		accumulatedAllMapsWithNumbers.setPermutationNumberDnaseCellLineNumber2KMap(new TIntIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberTfNumberCellLineNumber2KMap(new TLongIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberHistoneNumberCellLineNumber2KMap(new TLongIntHashMap());
		
		//User Defined GeneSet
		accumulatedAllMapsWithNumbers.setPermutationNumberExonBasedUserDefinedGeneSet2KMap(new TIntIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberRegulationBasedUserDefinedGeneSet2KMap(new TIntIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberAllBasedUserDefinedGeneSet2KMap(new TIntIntHashMap());
	
		//KEGG Pathway
		accumulatedAllMapsWithNumbers.setPermutationNumberExonBasedKeggPathway2KMap(new TIntIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberRegulationBasedKeggPathway2KMap(new TIntIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberAllBasedKeggPathway2KMap(new TIntIntHashMap());
		
		//TF KEGGPathway Enrichment
		accumulatedAllMapsWithNumbers.setPermutationNumberTfExonBasedKeggPathway2KMap(new TLongIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberTfRegulationBasedKeggPathway2KMap(new TLongIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberTfAllBasedKeggPathway2KMap(new TLongIntHashMap());
			
		//TF CellLine KEGGPathway Enrichment
		accumulatedAllMapsWithNumbers.setPermutationNumberTfCellLineExonBasedKeggPathway2KMap(new TLongIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(new TLongIntHashMap());
		accumulatedAllMapsWithNumbers.setPermutationNumberTfCellLineAllBasedKeggPathway2KMap(new TLongIntHashMap());
		/****************************ACCUMULATED ALL MAPS******************************************************/
		/******************************************************************************************************/
		
		
		/******************************************************************************************************/		
		/**************************USED FOR WRITING PERMUTATION BASED RESULTS**********************************/		
		Map<Integer,BufferedWriter> permutationNumber2DnaseBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2TfbsBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2HistoneBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		
		//UserDefinedGeneSet
		Map<Integer,BufferedWriter> permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
				
		//KEGG Pathway
		Map<Integer,BufferedWriter> permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		
		//TF KEGGPathway Enrichment
		Map<Integer,BufferedWriter> permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		
		//TF CellLine KEGGPathway Enrichment
		Map<Integer,BufferedWriter> permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		Map<Integer,BufferedWriter> permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap = new HashMap<Integer,BufferedWriter>();
		/**************************USED FOR WRITING PERMUTATION BASED RESULTS**********************************/		
		/******************************************************************************************************/
		
		
		
		/******************************************************************************************************/		
		/*******************************ORIGINAL INPUT LINES***************************************************/		
		Map<ChromosomeName,List<InputLine>> originalInputLinesMap = new HashMap<ChromosomeName,List<InputLine>>();
		/*******************************ORIGINAL INPUT LINES***************************************************/		
		/******************************************************************************************************/		
		
		//todo test it 
		//SecureRandom myrandom = new SecureRandom();

		List<AnnotationTask> listofAnnotationTasks 	= null;
		IntervalTree intervalTree 					= null;
		
		//For NEW FUNCTIONALITY
		IntervalTree tfIntervalTree 				= null;
		IntervalTree ucscRefSeqGenesIntervalTree 	= null;
			
		GCCharArray gcCharArray						= null;
    	MapabilityFloatArray mapabilityFloatArray 	= null;
    	List<Integer> hg19ChromosomeSizes 			= new ArrayList<Integer>();
    	
		/******************************************************************************************************/		
    	/**************PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES STARTS*****************/		
		//Partition the original input data lines in a chromosome based manner
		partitionDataChromosomeBased(allOriginalInputLines,originalInputLinesMap);
	   	/**************PARTITION ORIGINAL INPUT LINES INTO CHROMOSOME BASED INPUT LINES ENDS*******************/		
		/******************************************************************************************************/		
				
		
		
		/******************************************************************************************************/		
    	/*******************************GET HG19 CHROMOSOME SIZES STARTS***************************************/		
		hg19.GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
    	//get the hg19 chromosome sizes
    	hg19.GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
    	/*******************************GET HG19 CHROMOSOME SIZES ENDS*****************************************/		
		/******************************************************************************************************/		
		
		ChromosomeName chromName;
    	int chromSize;
    	List<InputLine> chromosomeBaseOriginalInputLines;
    	Map<Integer,List<InputLine>> permutationNumber2RandomlyGeneratedDataHashMap = new HashMap<Integer,List<InputLine>>();
    	
    	AnnotateWithNumbers annotateWithNumbers;
    	GenerateRandomData generateRandomData;
    	ForkJoinPool pool = new ForkJoinPool(NUMBER_OF_AVAILABLE_PROCESSORS);
    	
    	long startTimeAllPermutations = System.currentTimeMillis();
    		       		
		GlanetRunner.appendLog("Run Number: " + runNumber);
		

		/******************************************************************************************************/		
    	/*********************************FOR EACH G19 CHROMOSOME STARTS***************************************/		
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
			
				/******************************************************************************************************/		
				/******************************GENERATE ANNOTATION TASKS STARTS****************************************/						
				GlanetRunner.appendLog("Generate annotation tasks has started.");
				generateAnnotationTasks(chromName,listofAnnotationTasks,runNumber,numberofPermutationsinThisRun,numberofPermutationsinEachRun);
				GlanetRunner.appendLog("Generate annotation tasks has ended.");
				/********************************GENERATE ANNOTATION TASKS ENDS****************************************/						
				/******************************************************************************************************/		

				/******************************************************************************************************/		
				/************************FILL GCCHARARRAY AND MAPABILITYFLOATARRAY STARTS******************************/						
				//Fill gcCharArray and 	mapabilityFloatArray		
				if (generateRandomDataMode.isGenerateRandomDataModeWithMapabilityandGc()){
					gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(dataFolder,chromName,chromSize);
					mapabilityFloatArray = ChromosomeBasedMapabilityArray.getChromosomeMapabilityArray(dataFolder,chromName,chromSize);
				}
				/************************FILL GCCHARARRAY AND MAPABILITYFLOATARRAY ENDS********************************/						
				/******************************************************************************************************/		

				
				
				/******************************************************************************************************/		
				/**********************************GENERATE RANDOM DATA STARTS*****************************************/						
				GlanetRunner.appendLog("Generate Random Data and Annotate has started.");	
			    long startTime = System.currentTimeMillis();
			    
			    GlanetRunner.appendLog("First Generate Random Data");
			    GlanetRunner.appendLog("Generate Random Data has started.");
 			    //First generate Random Data
			    generateRandomData = new GenerateRandomData(outputFolder,chromSize,chromName,chromosomeBaseOriginalInputLines,generateRandomDataMode,writeGeneratedRandomDataMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,gcCharArray,mapabilityFloatArray);
			    permutationNumber2RandomlyGeneratedDataHashMap = pool.invoke(generateRandomData);
			    GlanetRunner.appendLog("Generate Random Data has ended.");
			    /**********************************GENERATE RANDOM DATA ENDS*****************************************/						
			    /******************************************************************************************************/		
			    
			    
			    
			    /******************************************************************************************************/		
				/*****************************************FREE MEMORY STARTS*******************************************/
			    gcCharArray.setGcArray(null);
			    gcCharArray = null;		
			    mapabilityFloatArray.setMapabilityArray(null);
				mapabilityFloatArray = null;
				
				System.gc();
				System.runFinalization();				
				/*****************************************FREE MEMORY ENDS*******************************************/						
			    /******************************************************************************************************/		

							
			    
			    /******************************************************************************************************/		
				/**********************************GENERATE ANNOTATION TASK FOR GIVEN ORIGINAL DATA STARTS*************/						
			    //In each run
			    //generate one task for original data
		     	//After Random Data Generation has been ended
				//generate task for User Given Original Data(Genomic Variants)
			    //Since we do not need random data, there is  original data is given
				generateAnnotationTaskforOriginalData(chromName,listofAnnotationTasks,Commons.ORIGINAL_DATA_PERMUTATION_NUMBER);
				
				//Add the original data to permutationNumber2RandomlyGeneratedDataHashMap
				permutationNumber2RandomlyGeneratedDataHashMap.put(Commons.ORIGINAL_DATA_PERMUTATION_NUMBER, chromosomeBaseOriginalInputLines);			 	    
				/**********************************GENERATE ANNOTATION TASK FOR GIVEN ORIGINAL DATA ENDS***************/						
			    /******************************************************************************************************/		
			   
				
				
				/******************************************************************************************************/		
				/*****************************ANNOTATE PERMUTATIONS STARTS*********************************************/					
				GlanetRunner.appendLog("Annotate has started.");
				
				if (dnaseEnrichmentType.isDnaseEnrichment()){			
					//dnase
    			    //generate dnase interval tree
    			    intervalTree = generateDnaseIntervalTreeWithNumbers(dataFolder,chromName);
    			    annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.DNASE_ANNOTATION,null,null,overlapDefinition);
    				allMapsWithNumbers = pool.invoke(annotateWithNumbers);    			    
    			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers, AnnotationType.DNASE_ANNOTATION);
    			    allMapsWithNumbers = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;
    			    
    			    System.gc();
    				System.runFinalization();   				       	
				}
			 		
				if (histoneEnrichmentType.isHistoneEnrichment()){
				    //histone
    			    //generate histone interval tree
    			    intervalTree = generateHistoneIntervalTreeWithNumbers(dataFolder,chromName);
    			    annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.HISTONE_ANNOTATION,null,null,overlapDefinition);
    			    allMapsWithNumbers = pool.invoke(annotateWithNumbers);    			    
    			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.HISTONE_ANNOTATION);
    			    allMapsWithNumbers = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;
    			    
    			    System.gc();
    				System.runFinalization();			
				}
	    			    
				if ((tfEnrichmentType.isTfEnrichment()) && !(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
    			    //tf
    			    //generate tf interval tree
    			    intervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder,chromName);
    			    annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.TF_ANNOTATION,null,null,overlapDefinition);
    			    allMapsWithNumbers = pool.invoke(annotateWithNumbers);    			    
    			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_ANNOTATION);
    			    allMapsWithNumbers = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;
    			    
    			    System.gc();
    				System.runFinalization();	
				}
				
				
				//UserDefinedGeneSet
				if(userDefinedGeneSetEnrichmentType.isUserDefinedGeneSetEnrichment()){
					//ucsc RefSeq Genes
    			    //generate UCSC RefSeq Genes interval tree
    			    intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder,chromName);
    			    annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.USER_DEFINED_GENE_SET_ANNOTATION,null,geneId2ListofUserDefinedGeneSetNumberMap,overlapDefinition);
    			    allMapsWithNumbers = pool.invoke(annotateWithNumbers);    			    
    			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.USER_DEFINED_GENE_SET_ANNOTATION);
    			    allMapsWithNumbers = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;
    			    
    			    System.gc();
    				System.runFinalization();			
				}
				
				
				if (keggPathwayEnrichmentType.isKeggPathwayEnrichment() && !(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
					//ucsc RefSeq Genes
    			    //generate UCSC RefSeq Genes interval tree
    			    intervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder,chromName);
    			    annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,intervalTree,null,AnnotationType.KEGG_PATHWAY_ANNOTATION,null,geneId2ListofKeggPathwayNumberMap,overlapDefinition);
    			    allMapsWithNumbers = pool.invoke(annotateWithNumbers);    			    
    			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.KEGG_PATHWAY_ANNOTATION);
    			    allMapsWithNumbers = null;
    			    deleteIntervalTree(intervalTree);
    			    intervalTree = null;
    			    
    			    System.gc();
    				System.runFinalization();			
				}
	
				
				if (tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
					
					//New Functionality START
    				//tfbs 
    				//Kegg Pathway (exon Based, regulation Based, all Based)
    				//tfbs and Kegg Pathway (exon Based, regulation Based, all Based)
    				//generate tf interval tree and ucsc refseq genes interval tree
    				tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder,chromName);
    				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder,chromName);
    				annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,AnnotationType.TF_KEGG_PATHWAY_ANNOTATION,tfKeggPathwayEnrichmentType,geneId2ListofKeggPathwayNumberMap,overlapDefinition);
    				allMapsWithNumbers = pool.invoke(annotateWithNumbers);    
      				//Will be used 	for TF and KEGG Pathway Enrichment or
      				//				for TF and CellLine and KEGG Pathway Enrichment
					accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_KEGG_PATHWAY_ANNOTATION);	
      			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_ANNOTATION);
      			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.KEGG_PATHWAY_ANNOTATION);
      			  
      			    allMapsWithNumbers = null;
      			    deleteIntervalTree(tfIntervalTree);
      			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
      			    tfIntervalTree = null;
      			    ucscRefSeqGenesIntervalTree = null;	
      				//New Functionality END
      			    
      			  System.gc();
      			  System.runFinalization();
  				
    			
				}else if (!(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
    					
    					//New Functionality START
        				//tfbs 
        				//Kegg Pathway (exon Based, regulation Based, all Based)
        				//tfbs and Kegg Pathway (exon Based, regulation Based, all Based)
        				//generate tf interval tree and ucsc refseq genes interval tree
        				tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder,chromName);
        				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder,chromName);
        				annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION,tfCellLineKeggPathwayEnrichmentType,geneId2ListofKeggPathwayNumberMap,overlapDefinition);
        				allMapsWithNumbers = pool.invoke(annotateWithNumbers);    
          				//Will be used 	for Tf and KeggPathway Enrichment or
          				//				for Tf and CellLine and KeggPathway Enrichment
        				accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION);	
          			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_ANNOTATION);
          			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.KEGG_PATHWAY_ANNOTATION);
          			  
          			    allMapsWithNumbers = null;
          			    deleteIntervalTree(tfIntervalTree);
          			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
          			    tfIntervalTree = null;
          			    ucscRefSeqGenesIntervalTree = null;	
          				//New Functionality END
          			    
          			  System.gc();
          			  System.runFinalization();
      							
        			
    			} else if (tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
    				
    				tfIntervalTree = generateTfbsIntervalTreeWithNumbers(dataFolder,chromName);
    				ucscRefSeqGenesIntervalTree = generateUcscRefSeqGeneIntervalTreeWithNumbers(dataFolder,chromName);
    				
    				annotateWithNumbers = new AnnotateWithNumbers(outputFolder,chromSize,chromName,permutationNumber2RandomlyGeneratedDataHashMap,runNumber,numberofPermutationsinThisRun,writePermutationBasedandParametricBasedAnnotationResultMode,Commons.ZERO, listofAnnotationTasks.size(),listofAnnotationTasks,tfIntervalTree,ucscRefSeqGenesIntervalTree,AnnotationType.BOTH_TF_KEGG_PATHWAY_AND_TF_CELLLINE_KEGG_PATHWAY_ANNOTATION,EnrichmentType.BOTH_DO_TF_KEGGPATHWAY_AND_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT,geneId2ListofKeggPathwayNumberMap,overlapDefinition);
    				allMapsWithNumbers = pool.invoke(annotateWithNumbers);    
      				
    				accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_KEGG_PATHWAY_ANNOTATION);	
         			accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION);	
      			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.TF_ANNOTATION);
      			    accumulate(allMapsWithNumbers, accumulatedAllMapsWithNumbers,AnnotationType.KEGG_PATHWAY_ANNOTATION);
      			  
      			    allMapsWithNumbers = null;
      			    deleteIntervalTree(tfIntervalTree);
      			    deleteIntervalTree(ucscRefSeqGenesIntervalTree);
      			    tfIntervalTree = null;
      			    ucscRefSeqGenesIntervalTree = null;	
      				//New Functionality END
      			    
      			  System.gc();
      			  System.runFinalization();
    			}
				
				GlanetRunner.appendLog("Annotate has ended.");
				/*****************************ANNOTATE PERMUTATIONS ENDS***********************************************/					
				/******************************************************************************************************/		


			    long endTime = System.currentTimeMillis();
				GlanetRunner.appendLog("RunNumber: " + runNumber  + " For Chromosome: " + chromName + " Annotation of " + numberofPermutationsinThisRun + " permutations took  " + (endTime - startTime) + " milliseconds.");
				GlanetRunner.appendLog("Generate Random Data and Annotate has ended.");
			
				GlanetRunner.appendLog("Deletion of the tasks has started.");
				deleteAnnotationTasks(listofAnnotationTasks);
				GlanetRunner.appendLog("Deletion of the tasks has ended.");
		
			    permutationNumber2RandomlyGeneratedDataHashMap.clear();
			    permutationNumber2RandomlyGeneratedDataHashMap= null;
				listofAnnotationTasks = null;
				annotateWithNumbers = null;
				generateRandomData = null;
				chromosomeBaseOriginalInputLines =null;
				
				
			}//end of if: chromosome based input lines is not null
			
    	}//End of for: each chromosome
		/******************************************************************************************************/		
    	/*********************************FOR EACH G19 CHROMOSOME ENDS***************************************/		

    			
    	pool.shutdown();
		
		if (pool.isTerminated()){
			GlanetRunner.appendLog("ForkJoinPool is terminated ");
			
		}   	
		
		long endTimeAllPermutations = System.currentTimeMillis();
	
		GlanetRunner.appendLog("RUN_NUMBER: " + runNumber + " NUMBER_OF_PERMUTATIONS:  "+ numberofPermutationsinThisRun  + " took "  + (endTimeAllPermutations - startTimeAllPermutations) + " milliseconds.");
	
		/************************************************************************************************************************/
		/*****************************************CONVERT************************************************************************/
		//convert permutationNumber augmented number to only number
		//Fill elementNumber2ALLMap and originalElementNumber2KMap in convert methods
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(),dnase2AllKMap,originalDnase2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),tfbs2AllKMap,originalTfbs2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(),histone2AllKMap,originalHistone2KMap);
		
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSet2KMap(),exonBasedUserDefinedGeneSet2AllKMap,originalExonBasedUserDefinedGeneSet2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSet2KMap(),regulationBasedUserDefinedGeneSet2AllKMap,originalRegulationBasedUserDefinedGeneSet2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSet2KMap(),allBasedUserDefinedGeneSet2AllKMap,originalAllBasedUserDefinedGeneSet2KMap);
		
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap(),exonBasedKeggPathway2AllKMap,originalExonBasedKeggPathway2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap(),regulationBasedKeggPathway2AllKMap,originalRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap(),allBasedKeggPathway2AllKMap,originalAllBasedKeggPathway2KMap);
			
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfExonBasedKeggPathway2KMap(),tfExonBasedKeggPathway2AllKMap,originalTfExonBasedKeggPathway2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),tfRegulationBasedKeggPathway2AllKMap,originalTfRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfAllBasedKeggPathway2KMap(),tfAllBasedKeggPathway2AllKMap,originalTfAllBasedKeggPathway2KMap);
		
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),tfCellLineExonBasedKeggPathway2AllKMap,originalTfCellLineExonBasedKeggPathway2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),tfCellLineRegulationBasedKeggPathway2AllKMap,originalTfCellLineRegulationBasedKeggPathway2KMap);
		convert(accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),tfCellLineAllBasedKeggPathway2AllKMap,originalTfCellLineAllBasedKeggPathway2KMap);
		/*****************************************CONVERT************************************************************************/
		/************************************************************************************************************************/
		
				
		/************************************************************************************************************************/
		/**********************WRITE PERMUTATION BASED ANNOTATION RESULTS********************************************************/
		//Permutation Based Results
		if (writePermutationBasedAnnotationResultMode.isWritePermutationBasedAnnotationResultMode()){
			
			permutationBasedResultDirectory = outputFolder + Commons.ANNOTATION_FOR_PERMUTATIONS + System.getProperty("file.separator") + "results" + System.getProperty("file.separator");
			
			if(dnaseEnrichmentType.isDnaseEnrichment()){
				//Dnase
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberDnaseCellLineNumber2KMap(),permutationNumber2DnaseBufferedWriterHashMap, AnnotationType.DNASE_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator")  , Commons.DNASE);
				closeBufferedWriters(permutationNumber2DnaseBufferedWriterHashMap);
			}
			
			if(histoneEnrichmentType.isHistoneEnrichment()){
				//Histone
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberHistoneNumberCellLineNumber2KMap(),permutationNumber2HistoneBufferedWriterHashMap,AnnotationType.HISTONE_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") , Commons.HISTONE);
				closeBufferedWriters(permutationNumber2HistoneBufferedWriterHashMap);
			}
			
			
			if(tfEnrichmentType.isTfEnrichment()  && !(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){					
				//Transcription Factor 
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);					
			}
			
		
			if(userDefinedGeneSetEnrichmentType.isUserDefinedGeneSetEnrichment()){					
				//Exon Based User Defined GeneSet
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedUserDefinedGeneSet2KMap(),permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap, AnnotationType.USER_DEFINED_GENE_SET_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" +System.getProperty("file.separator") , Commons.EXON_BASED);
				closeBufferedWriters(permutationNumber2ExonBasedUserDefinedGeneSetBufferedWriterHashMap);
				
				//Regulation Based User Defined GeneSet
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedUserDefinedGeneSet2KMap(),permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap, AnnotationType.USER_DEFINED_GENE_SET_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator") , Commons.REGULATION_BASED);
				closeBufferedWriters(permutationNumber2RegulationBasedUserDefinedGeneSetBufferedWriterHashMap);
				
				//All Based User Defined GeneSet
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedUserDefinedGeneSet2KMap(),permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap, AnnotationType.USER_DEFINED_GENE_SET_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator") , Commons.ALL_BASED);
				closeBufferedWriters(permutationNumber2AllBasedUserDefinedGeneSetBufferedWriterHashMap);
			}
	
			if(keggPathwayEnrichmentType.isKeggPathwayEnrichment()  && !(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){					
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" +System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator") , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator") , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
			}
			

			
			if(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
				
				//Tfbs
				writeAnnotationstoFiles_ElementNumberCellLineNumber(outputFolder,accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);				
				
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" +System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator") , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator") , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
				
				//Tf and Exon Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfExonBasedKeggPathway2KMap(),permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator") , Commons.TF_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Regulation Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator") , Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and All Based Kegg Pathway
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfAllBasedKeggPathway2KMap(),permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator")+ "tfAllBased" + System.getProperty("file.separator") , Commons.TF_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);			
				
				
			}else if(!(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment())  &&   tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
										
				//Tfbs
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);
				
				//Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator"), Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator"), Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);			
				
				//Tf and Cell Line and Exon Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Cell Line and Regulation Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf and Cell Line and All Based Kegg Pathway
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);
				
		
			}else if (tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
				
				//TF
				writeAnnotationstoFiles_ElementNumberCellLineNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfNumberCellLineNumber2KMap(),permutationNumber2TfbsBufferedWriterHashMap, AnnotationType.TF_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") , Commons.TF);
				closeBufferedWriters(permutationNumber2TfbsBufferedWriterHashMap);				
				
				//ExonKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberExonBasedKeggPathway2KMap(),permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap,AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "exonBased" +System.getProperty("file.separator") , Commons.EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2ExonBasedKeggPathwayBufferedWriterHashMap);
				
				//RegulationKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberRegulationBasedKeggPathway2KMap(),permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator") , Commons.REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2RegulationBasedKeggPathwayBufferedWriterHashMap);
				
				//AllKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberAllBasedKeggPathway2KMap(),permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator") , Commons.ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2AllBasedKeggPathwayBufferedWriterHashMap);
				
				//TF ExonKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfExonBasedKeggPathway2KMap(),permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator") , Commons.TF_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfExonBasedKeggPathwayBufferedWriterHashMap);
		
				//TF RegulationKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfRegulationBasedKeggPathway2KMap(),permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator") , Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//TF AllKEGG
				writeAnnotationstoFiles_ElementNumberKeggPathwayNumber(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfAllBasedKeggPathway2KMap(),permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator")+ "tfAllBased" + System.getProperty("file.separator") , Commons.TF_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfAllBasedKeggPathwayBufferedWriterHashMap);			
		
				
				//TF CellLine ExonKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineExonBasedKeggPathway2KMap(),permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineExonBasedKeggPathwayBufferedWriterHashMap);
		
				//TF CellLine RegulationKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineRegulationBasedKeggPathway2KMap(),permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineRegulationBasedKeggPathwayBufferedWriterHashMap);
		
				//Tf CellLine AllKEGG
				writeAnnotationstoFiles(permutationBasedResultDirectory,accumulatedAllMapsWithNumbers.getPermutationNumberTfCellLineAllBasedKeggPathway2KMap(),permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap, AnnotationType.TF_CELLLINE_KEGG_PATHWAY_ANNOTATION.convertEnumtoString() + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator") , Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
				closeBufferedWriters(permutationNumber2TfCellLineAllBasedKeggPathwayBufferedWriterHashMap);
			
			}
			
			
		}//End of if: write permutation based results
		/**********************WRITE PERMUTATION BASED ANNOTATION RESULTS********************************************************/
		/************************************************************************************************************************/

			
	}
	//NEW FUNCIONALITY ADDED
	
	

	
	//TIntIntMap TIntObjectMap<TIntList> version starts
	public static void writeToBeCollectedNumberofOverlaps(String outputFolder,TIntIntMap  originalElementNumber2KMap, TIntObjectMap<TIntList> elementNumber2AllKMap,String toBePolledDirectoryName, String runNumber){
		int elementNumber;
		int originalNumberofOverlaps;
		
		TIntList permutationNumberofOverlapsList;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + toBePolledDirectoryName  +"_" + runNumber +".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for( TIntIntIterator it = originalElementNumber2KMap.iterator(); it.hasNext();){
				
				it.advance();
				elementNumber = it.key();
				originalNumberofOverlaps = it.value();
				
				bufferedWriter.write(elementNumber + "\t" + originalNumberofOverlaps + "|" );
				
				permutationNumberofOverlapsList = elementNumber2AllKMap.get(elementNumber);
				
				if (permutationNumberofOverlapsList!=null){
					
					//@todo check this
					for ( TIntIterator it2 = permutationNumberofOverlapsList.iterator(); it2.hasNext(); ) {
						
					    bufferedWriter.write(it2.next() + "," );	    
					}
																	
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
	//TIntIntMap TIntObjectMap<TIntList> version ends


	
	public static void writeToBeCollectedNumberofOverlaps(String outputFolder,TLongIntMap  originalElementNumber2KMap, TLongObjectMap<TIntList> elementNumber2AllKMap,String toBePolledDirectoryName, String runNumber){
		long elementNumber;
		int originalNumberofOverlaps;
		
		TIntList permutationNumberofOverlapsList;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + toBePolledDirectoryName + "_" + runNumber +".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for( TLongIntIterator it = originalElementNumber2KMap.iterator(); it.hasNext();){
				
				it.advance();
				elementNumber = it.key();
				originalNumberofOverlaps = it.value();
				
				bufferedWriter.write(elementNumber + "\t" + originalNumberofOverlaps + "|" );
				
				permutationNumberofOverlapsList = elementNumber2AllKMap.get(elementNumber);
				
				if (permutationNumberofOverlapsList!=null){
					
					for ( TIntIterator it2 = permutationNumberofOverlapsList.iterator(); it2.hasNext(); ) {
					    bufferedWriter.write(it2.next() + "," );	    
					}
																	
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
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = "noname";
		}
		//jobName ends
				
				
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
				
		int overlapDefinition = Integer.parseInt(args[3]);

		//Number of processors can be used in deciding on paralellism level
		int NUMBER_OF_AVAILABLE_PROCESSORS =  java.lang.Runtime.getRuntime().availableProcessors();
			
		//Set the number of total permutations
		int numberofTotalPermutations = Integer.parseInt(args[9]);
		
		//set the number of permutations in each run
		int numberofPermutationsInEachRun = Integer.parseInt(args[21]);
		
		
		//SET the Input Data File
		String inputDataFileName = outputFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE;
				
		//Set the Generate Random Data Mode
		GenerateRandomDataMode generateRandomDataMode = GenerateRandomDataMode.convertStringtoEnum(args[5]);
		
		//Set the Write Mode of Generated Random Data
		WriteGeneratedRandomDataMode writeGeneratedRandomDataMode = WriteGeneratedRandomDataMode.convertStringtoEnum(args[18]);
				
		//Set the Write Mode of Permutation Based and Parametric Based Annotation Result
		WritePermutationBasedandParametricBasedAnnotationResultMode writePermutationBasedandParametricBasedAnnotationResultMode = WritePermutationBasedandParametricBasedAnnotationResultMode.convertStringtoEnum(args[19]);

		//Set the Write Mode of the Permutation Based Annotation Result
		WritePermutationBasedAnnotationResultMode writePermutationBasedAnnotationResultMode = WritePermutationBasedAnnotationResultMode.convertStringtoEnum(args[20]);
		
		//ENRICHMENT
		//Dnase Enrichment, DO or DO_NOT
		EnrichmentType dnaseEnrichmentType = EnrichmentType.convertStringtoEnum(args[10]);
		
		//Histone Enrichment, DO or DO_NOT
//		String histoneEnrichment = args[11];
		EnrichmentType histoneEnrichmentType = EnrichmentType.convertStringtoEnum(args[11]);
		
		
		//Transcription Factor Enrichment, DO or DO_NOT
//		String tfEnrichment = args[12];
		EnrichmentType tfEnrichmentType = EnrichmentType.convertStringtoEnum(args[12]);
			
		//KEGG Pathway Enrichment, DO or DO_NOT
//		String keggPathwayEnrichment = args[13];
		EnrichmentType keggPathwayEnrichmentType = EnrichmentType.convertStringtoEnum(args[13]);
								
		//TfKeggPathway Enrichment, DO or DO_NOT
//		String tfKeggPathwayEnrichment = args[14];
		EnrichmentType tfKeggPathwayEnrichmentType = EnrichmentType.convertStringtoEnum(args[14]);
		
		//TfCellLineKeggPathway Enrichment, DO or DO_NOT
//		String tfCellLineKeggPathwayEnrichment = args[15];
		EnrichmentType tfCellLineKeggPathwayEnrichmentType = EnrichmentType.convertStringtoEnum(args[15]);
		
		
		//UserDefinedGeneSet Enrichement
		EnrichmentType userDefinedGeneSetEnrichmentType = EnrichmentType.convertStringtoEnum(args[22]);
		
		//@todo userDefinedGeneSetInputFile has to filled from GLANET GUI
		String userDefinedGeneSetInputFile = "E:\\DOKTORA_DATA\\GO\\GO_gene_associations_human_ref.txt";
		
		//@todo userDefinedGeneSetName has to filled from GLANET GUI
		String userDefinedGeneSetName = "GO";
		
		//@todo userDefinedGeneSetInputType has to filled from GLANET GUI
		UserDefinedGeneSetInputType  userDefinedGeneSetInputType = UserDefinedGeneSetInputType.GENE_SYMBOL;
				
		
		//UserDefinedLibrary Enrichement
		EnrichmentType userDefinedLibraryEnrichmentType = EnrichmentType.convertStringtoEnum(args[23]);
	
		
		writeInformation();
			
		//Random Class for generating small values instead of zero valued p values
		//Random random = new Random();
		
		/*********************************************************************************************/			
		/**************************READ ORIGINAL INPUT LINES STARTS***********************************/		
		List<InputLine> originalInputLines = new ArrayList<InputLine>();
		
		//Read original input data lines in to a list
		AnnotatePermutationsWithNumbersWithChoices.readOriginalInputDataLines(originalInputLines, inputDataFileName);
		/**************************READ ORIGINAL INPUT LINES ENDS*************************************/		
		/*********************************************************************************************/			
	
		
		
		/*********************************************************************************************/			
		/**********GET NUMBER OF COMPARISONS FOR BONFERRONI CORRECTION STARTS*************************/		
		//For Bonferroni Correction 
		//Set the number of comparisons for DNase, Tfbs, Histone
		//Set the number of comparisons for ExonBasedKeggPathway, RegulationBasedKeggPathway, AllBasedKeggPathway
		//Set the number of comparisons for TfCellLineExonBasedKeggPathway, TfCellLineRegulationBasedKeggPathway, TfCellLineAllBasedKeggPathway
		//Set the number of comparisons for TfExonBasedKeggPathway, TfRegulationBasedKeggPathway, TfAllBasedKeggPathway
		NumberofComparisons  numberofComparisons = new NumberofComparisons();
		NumberofComparisonsforBonferroniCorrectionCalculation.getNumberofComparisonsforBonferroniCorrection(dataFolder,numberofComparisons);
		/**********GET NUMBER OF COMPARISONS FOR BONFERRONI CORRECTION ENDS***************************/		
		/*********************************************************************************************/			
		
		
		/*********************************************************************************************/			
		/*********************DELETE OLD FILES STARTS*************************************************/		
		String annotationForPermutationsOutputDirectory = outputFolder + Commons.ANNOTATION_FOR_PERMUTATIONS;
		List<String> notToBeDeleted = new ArrayList<String>();
		//FileOperations.deleteDirectoriesandFilesUnderThisDirectory(annotateOutputBaseDirectoryName,notToBeDeleted);
		FileOperations.deleteOldFiles(annotationForPermutationsOutputDirectory,notToBeDeleted);
		
		String toBeDeletedDirectoryName = outputFolder + Commons.ENRICHMENT_DIRECTORY;
		FileOperations.deleteOldFiles(toBeDeletedDirectoryName);			
		/*********************DELETE OLD FILES ENDS***************************************************/		
		/*********************************************************************************************/	
		
		
		/*********************************************************************************************/			
		/*********************FILL GENEID 2 USER DEFINED GENESET NUMBER  MAP STARTS*******************/
		TShortObjectMap<String> userDefinedGeneSetNumber2UserDefinedGeneSetNameMap	= new TShortObjectHashMap<String>();	
	    //used in filling geneId2ListofUserDefinedGeneSetNumberMap
	    TObjectShortMap<String> userDefinedGeneSetName2UserDefinedGeneSetNumberMap 	= new TObjectShortHashMap<String>();

	    TIntObjectMap<TShortList> geneId2ListofUserDefinedGeneSetNumberMap = new TIntObjectHashMap<TShortList>();	
		
	    if (userDefinedGeneSetEnrichmentType.isUserDefinedGeneSetEnrichment()){
	    	UserDefinedGeneSetUtility.createNcbiGeneId2ListofUserDefinedGeneSetNumberMap(dataFolder,userDefinedGeneSetInputFile,userDefinedGeneSetInputType,userDefinedGeneSetName2UserDefinedGeneSetNumberMap,userDefinedGeneSetNumber2UserDefinedGeneSetNameMap,geneId2ListofUserDefinedGeneSetNumberMap);
	    }
		/*********************************************************************************************/			
		/*********************FILL GENEID 2 USER DEFINED GENESET NUMBER  MAP ENDS*******************/
		
		
		
		/*********************************************************************************************/			
		/*********************FILL GENEID 2 KEGG PATHWAY NUMBER  MAP STARTS***************************/
		//For efficiency
		//Fill this map only once.
		//NCBI Gene Id is Integer
		TIntObjectMap<TShortList> geneId2KeggPathwayNumberMap = new TIntObjectHashMap<TShortList>();		
		TObjectShortMap<String> keggPathwayName2KeggPathwayNumberMap = new TObjectShortHashMap<String>();

		if(keggPathwayEnrichmentType.isKeggPathwayEnrichment() ||
			tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() ||
			tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
					
			//all_possible_keggPathwayName_2_keggPathwayNumber_map.txt
			KeggPathwayUtility.fillKeggPathwayName2KeggPathwayNumberMap(dataFolder, Commons.BYGLANET + System.getProperty("file.separator") + Commons.ALL_POSSIBLE_NAMES+ System.getProperty("file.separator") ,  Commons.ALL_POSSIBLE_KEGGPATHWAYNAME_2_KEGGPATHWAYNUMBER_FILE, keggPathwayName2KeggPathwayNumberMap);
			KeggPathwayUtility.createNcbiGeneId2KeggPathwayNumberMap(dataFolder,Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE, geneId2KeggPathwayNumberMap,keggPathwayName2KeggPathwayNumberMap);

		}
		/*********************FILL GENEID 2 KEGG PATHWAY NUMBER  MAP  ENDS****************************/		
		/*********************************************************************************************/			
		
	
		/*********************************************************************************************/			
		/*******************************CALCULATE NUMBER OF RUNS  STARTS********************************/		
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
		/*******************************CALCULATE NUMBER OF RUNS  ENDS********************************/		
		/*********************************************************************************************/			
		
		
		/*********************************************************************************************/	
		/*********************FOR LOOP FOR RUN NUMBERS  STARTS****************************************/							
		for(int runNumber=1; runNumber<=numberofRuns;runNumber++){
			
			GlanetRunner.appendLog("**************	" + runNumber + ". Run" + "	******************	starts");
			
			runName = jobName + "_" +runNumber;
			
			/*********************************************************************************************/			
			/***********************NUMBER OF OVERLAPS FOR ORIGINAL DATA  STARTS**************************/						
			//annotation of original data with permutations	
			//annotation of original data has permutation number zero
			//number of overlaps for the original data: k out of n for the original data
			//ElementName to integer
			//DNASE TF HISTONE
			TIntIntMap originalDnase2KMap 			= new TIntIntHashMap();
			TIntIntMap originalTfbs2KMap 			= new TIntIntHashMap();
			TIntIntMap originalHistone2KMap 		= new TIntIntHashMap();
			
			//User Defined Gene Set
			TIntIntMap originalExonBasedUserDefinedGeneSet2KMap 		= new TIntIntHashMap();
			TIntIntMap originalRegulationBasedUserDefinedGeneSet2KMap 	= new TIntIntHashMap();
			TIntIntMap originalAllBasedUserDefinedGeneSet2KMap 			= new TIntIntHashMap();
			
			//KEGG Pathway
			TIntIntMap originalExonBasedKeggPathway2KMap 		= new TIntIntHashMap();
			TIntIntMap originalRegulationBasedKeggPathway2KMap 	= new TIntIntHashMap();
			TIntIntMap originalAllBasedKeggPathway2KMap 		= new TIntIntHashMap();
						
			//TF and KEGG Pathway Enrichment
			TIntIntMap originalTfExonBasedKeggPathway2KMap 			= new TIntIntHashMap();
			TIntIntMap originalTfRegulationBasedKeggPathway2KMap 	= new TIntIntHashMap();
			TIntIntMap originalTfAllBasedKeggPathway2KMap 			= new TIntIntHashMap();
		
			//TF and CellLine and KEGG Pathway Enrichment 
			TLongIntMap originalTfCellLineExonBasedKeggPathway2KMap 		= new TLongIntHashMap();
			TLongIntMap originalTfCellLineRegulationBasedKeggPathway2KMap 	= new TLongIntHashMap();
			TLongIntMap originalTfCellLineAllBasedKeggPathway2KMap 			= new TLongIntHashMap();
			/***********************NUMBER OF OVERLAPS FOR ORIGINAL DATA  ENDS****************************/						
			/*********************************************************************************************/			

			
			/*********************************************************************************************/			
			/***********************NUMBER OF OVERLAPS FOR ALL PERMUTATIONS  STARTS***********************/
			//Accumulated number of overlaps for all permutations
			//functionalElementNumber based
			//number of overlaps: k out of n for all permutations
			//ElementNumber has been mapped to a list of integers				
			TIntObjectMap<TIntList> dnase2AllKMap 		= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> histone2AllKMap 	= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> tfbs2AllKMap 		= new TIntObjectHashMap<TIntList>();
			
			//User Defined GeneSet
			TIntObjectMap<TIntList> exonBasedUserDefinedGeneSet2AllKMap 		= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> regulationBasedUserDefinedGeneSet2AllKMap 	= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> allBasedUserDefinedGeneSet2AllKMap 			= new TIntObjectHashMap<TIntList>();
			
			//KEGG Pathway
			TIntObjectMap<TIntList> exonBasedKeggPathway2AllKMap 			= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> regulationBasedKeggPathway2AllKMap 		= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> allBasedKeggPathway2AllKMap 			= new TIntObjectHashMap<TIntList>();
			
			//Tf and KEGG Pathway Enrichment
			TIntObjectMap<TIntList> tfExonBasedKeggPathway2AllKMap 			= new TIntObjectHashMap<TIntList>();
			TIntObjectMap<TIntList> tfRegulationBasedKeggPathway2AllKMap 	= new TIntObjectHashMap<TIntList>() ;
			TIntObjectMap<TIntList> tfAllBasedKeggPathway2AllKMap 			= new TIntObjectHashMap<TIntList>();
		
			//Tf and CellLine and KEGG Pathway Enrichment 
			TLongObjectMap<TIntList> tfCellLineExonBasedKeggPathway2AllKMap 		= new TLongObjectHashMap<TIntList>();
			TLongObjectMap<TIntList> tfCellLineRegulationBasedKeggPathway2AllKMap 	= new TLongObjectHashMap<TIntList>() ;
			TLongObjectMap<TIntList> tfCellLineAllBasedKeggPathway2AllKMap 			= new TLongObjectHashMap<TIntList>();
			/***********************NUMBER OF OVERLAPS FOR ALL PERMUTATIONS  ENDS*************************/						
			/*********************************************************************************************/			

							
			
			/*********************************************************************************************/			
			/**************************ANNOTATE PERMUTATIONS STARTS***************************************/		
			GlanetRunner.appendLog("Concurrent programming has been started.");				
			//concurrent programming
			//generate random data
			//then annotate permutations concurrently
			//elementName2AllKMap and originalElementName2KMap will be filled here
			if ((runNumber == numberofRuns) && (numberofRemainedPermutations >0)){
				AnnotatePermutationsWithNumbersWithChoices.annotateAllPermutationsInThreads(outputFolder,dataFolder,NUMBER_OF_AVAILABLE_PROCESSORS,runNumber,numberofRemainedPermutations,numberofPermutationsInEachRun,originalInputLines,dnase2AllKMap, tfbs2AllKMap, histone2AllKMap,exonBasedUserDefinedGeneSet2AllKMap,regulationBasedUserDefinedGeneSet2AllKMap,allBasedUserDefinedGeneSet2AllKMap, exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap,allBasedKeggPathway2AllKMap,tfExonBasedKeggPathway2AllKMap,tfRegulationBasedKeggPathway2AllKMap,tfAllBasedKeggPathway2AllKMap,tfCellLineExonBasedKeggPathway2AllKMap,tfCellLineRegulationBasedKeggPathway2AllKMap,tfCellLineAllBasedKeggPathway2AllKMap,generateRandomDataMode,writeGeneratedRandomDataMode,writePermutationBasedandParametricBasedAnnotationResultMode,writePermutationBasedAnnotationResultMode,originalDnase2KMap,originalTfbs2KMap,originalHistone2KMap,originalExonBasedUserDefinedGeneSet2KMap,originalRegulationBasedUserDefinedGeneSet2KMap,originalAllBasedUserDefinedGeneSet2KMap,originalExonBasedKeggPathway2KMap,originalRegulationBasedKeggPathway2KMap,originalAllBasedKeggPathway2KMap,originalTfExonBasedKeggPathway2KMap,originalTfRegulationBasedKeggPathway2KMap,originalTfAllBasedKeggPathway2KMap,originalTfCellLineExonBasedKeggPathway2KMap,originalTfCellLineRegulationBasedKeggPathway2KMap,originalTfCellLineAllBasedKeggPathway2KMap,dnaseEnrichmentType,histoneEnrichmentType,tfEnrichmentType,userDefinedGeneSetEnrichmentType,keggPathwayEnrichmentType,tfKeggPathwayEnrichmentType,tfCellLineKeggPathwayEnrichmentType,overlapDefinition,geneId2KeggPathwayNumberMap,geneId2ListofUserDefinedGeneSetNumberMap);						
			}else {
				AnnotatePermutationsWithNumbersWithChoices.annotateAllPermutationsInThreads(outputFolder,dataFolder,NUMBER_OF_AVAILABLE_PROCESSORS,runNumber,numberofPermutationsInEachRun,numberofPermutationsInEachRun,originalInputLines,dnase2AllKMap, tfbs2AllKMap, histone2AllKMap, exonBasedUserDefinedGeneSet2AllKMap,regulationBasedUserDefinedGeneSet2AllKMap,allBasedUserDefinedGeneSet2AllKMap,exonBasedKeggPathway2AllKMap, regulationBasedKeggPathway2AllKMap,allBasedKeggPathway2AllKMap,tfExonBasedKeggPathway2AllKMap,tfRegulationBasedKeggPathway2AllKMap,tfAllBasedKeggPathway2AllKMap,tfCellLineExonBasedKeggPathway2AllKMap,tfCellLineRegulationBasedKeggPathway2AllKMap,tfCellLineAllBasedKeggPathway2AllKMap,generateRandomDataMode,writeGeneratedRandomDataMode,writePermutationBasedandParametricBasedAnnotationResultMode,writePermutationBasedAnnotationResultMode,originalDnase2KMap,originalTfbs2KMap,originalHistone2KMap,originalExonBasedUserDefinedGeneSet2KMap,originalRegulationBasedUserDefinedGeneSet2KMap,originalAllBasedUserDefinedGeneSet2KMap,originalExonBasedKeggPathway2KMap,originalRegulationBasedKeggPathway2KMap,originalAllBasedKeggPathway2KMap,originalTfExonBasedKeggPathway2KMap,originalTfRegulationBasedKeggPathway2KMap,originalTfAllBasedKeggPathway2KMap,originalTfCellLineExonBasedKeggPathway2KMap,originalTfCellLineRegulationBasedKeggPathway2KMap,originalTfCellLineAllBasedKeggPathway2KMap,dnaseEnrichmentType,histoneEnrichmentType,tfEnrichmentType, userDefinedGeneSetEnrichmentType,keggPathwayEnrichmentType, tfKeggPathwayEnrichmentType,tfCellLineKeggPathwayEnrichmentType,overlapDefinition,geneId2KeggPathwayNumberMap,geneId2ListofUserDefinedGeneSetNumberMap);		
				
			}
			GlanetRunner.appendLog("Concurrent programming has been ended.");				
			/**************************ANNOTATE PERMUTATIONS ENDS*****************************************/
			/*********************************************************************************************/			
			
			
			/*********************************************************************************************/			
			/**************************WRITE TO BE COLLECTED RESULTS STARTS*******************************/
			if(dnaseEnrichmentType.isDnaseEnrichment()){				
				//Write to be collected files
				writeToBeCollectedNumberofOverlaps(outputFolder,originalDnase2KMap,dnase2AllKMap,Commons.TO_BE_COLLECTED_DNASE_NUMBER_OF_OVERLAPS,runName);
			}
			
			if (histoneEnrichmentType.isHistoneEnrichment()){
				
				//Write to be collected files
				writeToBeCollectedNumberofOverlaps(outputFolder,originalHistone2KMap,histone2AllKMap,Commons.TO_BE_COLLECTED_HISTONE_NUMBER_OF_OVERLAPS,runName);
			}
			
			if (tfEnrichmentType.isTfEnrichment() && !(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
				
				//Write to be collected files
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
			}
			
			if (userDefinedGeneSetEnrichmentType.isUserDefinedGeneSetEnrichment()){
				
				final String  TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.EXON_BASED + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.EXON_BASED + userDefinedGeneSetName ;
				final String  TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.REGULATION_BASED + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.REGULATION_BASED + userDefinedGeneSetName ;
				final String  TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS = Commons.ENRICHMENT_DIRECTORY + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ALL_BASED + userDefinedGeneSetName + System.getProperty("file.separator") + Commons.ALL_BASED + userDefinedGeneSetName ;

				//Write to be collected files
				writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,TO_BE_COLLECTED_EXON_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,TO_BE_COLLECTED_REGULATION_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,TO_BE_COLLECTED_ALL_BASED_USER_DEFINED_GENESET_NUMBER_OF_OVERLAPS,runName);
			
			}
				
			if (keggPathwayEnrichmentType.isKeggPathwayEnrichment() && !(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
				
				//Write to be collected files
				writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
			}

			if (tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && !(tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment())){
							
				//Write to be collected files
				
				//TF
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
				
				//KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				
				//TF KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfExonBasedKeggPathway2KMap,tfExonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfRegulationBasedKeggPathway2KMap,tfRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfAllBasedKeggPathway2KMap,tfAllBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
			
			}
			
			if(!(tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment()) && tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
				
				//Write to be collected files
				
				//TF
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
				
				//KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);		
											
				//TF CELLLINE KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineExonBasedKeggPathway2KMap,tfCellLineExonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineRegulationBasedKeggPathway2KMap,tfCellLineRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineAllBasedKeggPathway2KMap,tfCellLineAllBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				
			}
			
			if (tfKeggPathwayEnrichmentType.isTfKeggPathwayEnrichment() && tfCellLineKeggPathwayEnrichmentType.isTfCellLineKeggPathwayEnrichment()){
				
				//Write to be collected files
				
				//TF
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfbs2KMap,tfbs2AllKMap,Commons.TO_BE_COLLECTED_TF_NUMBER_OF_OVERLAPS,runName);
				
				//KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalAllBasedKeggPathway2KMap,allBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				
				//TF KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfExonBasedKeggPathway2KMap,tfExonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfRegulationBasedKeggPathway2KMap,tfRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfAllBasedKeggPathway2KMap,tfAllBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
			
				//TF CELLLINE KEGG
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineExonBasedKeggPathway2KMap,tfCellLineExonBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineRegulationBasedKeggPathway2KMap,tfCellLineRegulationBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
				writeToBeCollectedNumberofOverlaps(outputFolder,originalTfCellLineAllBasedKeggPathway2KMap,tfCellLineAllBasedKeggPathway2AllKMap,Commons.TO_BE_COLLECTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS,runName);
			
			}
			//Calculate Empirical P Values and Bonferroni Corrected Empirical P Values ends
			/**************************WRITE TO BE COLLECTED RESULTS ENDS*********************************/
			/*********************************************************************************************/			

			/*********************************************************************************************/							
			/**********************************FREE MEMORY STARTS*****************************************/
			originalDnase2KMap 		= null;
			originalTfbs2KMap 		= null;
			originalHistone2KMap 	= null;
			
			originalExonBasedUserDefinedGeneSet2KMap = null;
			originalRegulationBasedUserDefinedGeneSet2KMap = null;
			originalAllBasedUserDefinedGeneSet2KMap = null;
			
			originalExonBasedKeggPathway2KMap 		= null;
			originalRegulationBasedKeggPathway2KMap = null;
			originalAllBasedKeggPathway2KMap 		= null;
						
			originalTfExonBasedKeggPathway2KMap 		= null;
			originalTfRegulationBasedKeggPathway2KMap 	= null;
			originalTfAllBasedKeggPathway2KMap 			= null;
		
			originalTfCellLineExonBasedKeggPathway2KMap 		= null;
			originalTfCellLineRegulationBasedKeggPathway2KMap 	= null;
			originalTfCellLineAllBasedKeggPathway2KMap 			= null;
								
			//functionalElementName based
			//number of overlaps: k out of n for all permutations
			dnase2AllKMap = null;
			histone2AllKMap = null;
			tfbs2AllKMap = null;
			
			exonBasedUserDefinedGeneSet2AllKMap = null;
			regulationBasedUserDefinedGeneSet2AllKMap = null;
			allBasedUserDefinedGeneSet2AllKMap = null;
			
			exonBasedKeggPathway2AllKMap = null;
			regulationBasedKeggPathway2AllKMap = null;
			allBasedKeggPathway2AllKMap = null;
			
			//TF and KEGG Pathway Enrichment
			tfExonBasedKeggPathway2AllKMap = null;
			tfRegulationBasedKeggPathway2AllKMap = null ;
			tfAllBasedKeggPathway2AllKMap = null;
		
			//Tf and CellLine and KEGG Pathway Enrichment 
			tfCellLineExonBasedKeggPathway2AllKMap = null;
			tfCellLineRegulationBasedKeggPathway2AllKMap = null ;
			tfCellLineAllBasedKeggPathway2AllKMap = null;
			/***********************************FREE MEMORY ENDS******************************************/
			/*********************************************************************************************/							

			GlanetRunner.appendLog("**************	" + runNumber + ". Run" + "	******************	ends");
			
		}
		//end of for each run number						

		
		/*********************FOR LOOP FOR RUN NUMBERS  ENDS****************************************/				
		/*********************************************************************************************/	

								
	
	}//End of main function

}

