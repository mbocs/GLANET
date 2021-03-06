package deprecated;
///**
// * @author Burcak Otlu
// * Jul 22, 2013
// * 1:44:13 PM
// * 2013
// *
// *	This class is deprecated.
// *  This class is not maintained any more.
// * 
// */
//package empiricalpvalues;
//
//import generate.randomdata.RandomDataGenerator;
//import hg19.GRCh37Hg19Chromosome;
//import intervaltree.IntervalTree;
//import ui.GlanetRunner;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import annotate.intervals.parametric.AnnotateGivenIntervalsWithGivenParameters;
//
//import common.Commons;
//
//class AnnotateCallable implements Callable<AllMaps>{
//	
//	Integer permutationNumber;
//	String chrName;
//	
//	List<InputLine> inputLines;
//	
//	IntervalTree dnaseIntervalTree;
//	IntervalTree tfbsIntervalTree;
//	IntervalTree histoneIntervalTree;
//	IntervalTree ucscRefSeqGeneIntervalTree;
//	
//	
//	public AnnotateCallable(Integer permutationNumber, String chrName, List<InputLine> inputLines,IntervalTree dnaseIntervalTree,IntervalTree tfbsIntervalTree,IntervalTree histoneIntervalTree,IntervalTree ucscRefSeqGeneIntervalTree) {
//		super();
//		this.permutationNumber = permutationNumber;
//		this.chrName = chrName;
//		this.inputLines = inputLines;
//		this.dnaseIntervalTree = dnaseIntervalTree;
//		this.tfbsIntervalTree = tfbsIntervalTree;
//		this.histoneIntervalTree = histoneIntervalTree;
//		this.ucscRefSeqGeneIntervalTree = ucscRefSeqGeneIntervalTree;
//	}
//
//
//	@Override
//	public AllMaps call() throws Exception {
//		
//		AllMaps allMaps ;
//		
//		GlanetRunner.appendLog("Chromosome Name: "+ this.chrName +" Annotate Permutation Number: " + this.permutationNumber);
//		
//		AnnotateGivenIntervalsWithGivenParameters annotateIntervals = new AnnotateGivenIntervalsWithGivenParameters();
//		allMaps = annotateIntervals.annotatPermutation(this.permutationNumber,this.chrName,this.inputLines,dnaseIntervalTree,tfbsIntervalTree,histoneIntervalTree,ucscRefSeqGeneIntervalTree);
//	
//		return allMaps;
//	}
//	
//}
//
//
//
//
//
//
//public class AnnotateOneThousandPermutationsUsingExecutorService {
//	Map<String,Long> name2NumberofOverlap = new HashMap<String,Long>();
//	
//	public void accumulateNumberofOverlaps(Map<String,List<Integer>> accumulatedMap, Map<String,Integer> name2KMap){
//		String name;
//		Integer numberofOverlap;
//		List<Integer> list;
//		
//		for(Map.Entry<String, Integer> entry: name2KMap.entrySet()){
//			name = entry.getKey();
//			numberofOverlap = entry.getValue();
//			
//			list = accumulatedMap.get(name);
//			
//			if (list == null){
//				list = new ArrayList<Integer>();
//				list.add(numberofOverlap);
//				accumulatedMap.put(name, list);
//			}else{
//				list.add(numberofOverlap);
//				accumulatedMap.put(name, list);
//			}
//		}
//		
//	}
//	
//	public void fillMapfromMap(Map<String,Integer> toMap, Map<String,Integer> fromMap){
//		String name;
//		Integer numberofOverlaps;
//		
//		for(Map.Entry<String, Integer> entry: fromMap.entrySet()){
//			name = entry.getKey();
//			numberofOverlaps = entry.getValue();
//			
//			toMap.put(name, numberofOverlaps);
//			
//			
//		}
//	}
//	
//	public void fillListfromList(List<InputLine> toList, List<InputLine> fromList){
//		
//		for(int i=0; i<fromList.size(); i++ ){
//			toList.add(fromList.get(i));
//		}
//	}
//
//	public void readOriginalInputDataLines(List<InputLine> originalInputLines, String inputFileName){
//		FileReader fileReader;
//		BufferedReader bufferedReader;
//		String strLine;
//		
//		int indexofFirstTab;
//		int indexofSecondTab;
//		
//		String chrName;
//		int low;
//		int high;
//		double mappability;
//		int gcContent;
//		
//		try {
//			fileReader = new FileReader(inputFileName);
//			bufferedReader = new BufferedReader(fileReader);
//			
//			while( (strLine= bufferedReader.readLine())!=null){
//				
//				indexofFirstTab = strLine.indexOf('\t');
//				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
//				
//				chrName = strLine.substring(0, indexofFirstTab);
//				
//				if (indexofSecondTab>0){
//					low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
//				}else{
//					low = Integer.parseInt(strLine.substring(indexofFirstTab+1));
//					high = low;
//				}
//
//				//todo
////				mappability = computeMappability(chrName,low,high);
////				gcContent = computeGCContent(chrName,low,high);
//				
//				InputLine originalInputLine = new InputLine(chrName, low, high);
//				originalInputLines.add(originalInputLine);
//			
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	public void annotateOriginalInputData(List<InputLine> originalInputLines, Map<String,Integer> originalDnase2KMap, Map<String,Integer> originalTfbs2KMap, Map<String,Integer> originalHistone2KMap, Map<String,Integer> originalExonBasedKeggPathway2KMap, Map<String,Integer> originalRegulationBasedKeggPathway2KMap){
//		AnnotateGivenIntervalsWithGivenParameters annotateIntervals = new AnnotateGivenIntervalsWithGivenParameters();
//		
//		AllName2KMaps name2KMap = annotateIntervals.annotateOriginalData(Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS);
//		
//		fillMapfromMap(originalDnase2KMap, name2KMap.getDnaseCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalTfbs2KMap, name2KMap.getTfbsNameandCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalHistone2KMap, name2KMap.getHistoneNameandCellLineName2NumberofOverlapsMap());
//		fillMapfromMap(originalExonBasedKeggPathway2KMap, name2KMap.getExonBasedKeggPathway2NumberofOverlapsMap());
//		fillMapfromMap(originalRegulationBasedKeggPathway2KMap, name2KMap.getRegulationBasedKeggPathway2NumberofOverlapsMap());
//		
//		
//	}
//	
//
//	
//	
//	//Accumulate the number of k out of n for each permutation for each chromosome
//	public void accumulate(Map<String,Integer> name2KMap, Map<String,Integer> name2AllKMap){
//		Integer numberofOverlaps;
//		Integer currentNumberofOverlaps;
//		
//		String permutationAugmentedName;
//		
//		for(Map.Entry<String, Integer> entry: name2KMap.entrySet()){
//			
//			permutationAugmentedName = entry.getKey();
//			numberofOverlaps = entry.getValue();
//			
//			
//			currentNumberofOverlaps = name2AllKMap.get(permutationAugmentedName);
//			
//			if (currentNumberofOverlaps==null){
//				name2AllKMap.put(permutationAugmentedName,numberofOverlaps);
//				
//			}else{
//				name2AllKMap.put(permutationAugmentedName, numberofOverlaps + currentNumberofOverlaps);
//			}
//			
//		}//end of for
//		
//	}
//	
//	
//	
//		public void generateRandomDataandAnnotate(Map<String,List<InputLine>> chromosomeBasedOriginalInputLines, Map<String,Integer> permutationDnase2KMap, Map<String,Integer> permutationTfbs2KMap,Map<String,Integer> permutationHistone2KMap,Map<String,Integer> permutationExonBasedKeggPathway2KMap,Map<String,Integer> permutationRegulationBasedKeggPathway2KMap, int NUMBER_OF_PERMUTATIONS){
//		
//		List<InputLine> originalInputLines;
//			
//		IntervalTree 	dnaseIntervalTree;
//		IntervalTree 	tfbsIntervalTree;
//		IntervalTree 	histoneIntervalTree;
//		IntervalTree 	ucscRefSeqGeneIntervalTree;
//		
//		Map<String, Integer> dnase2KMap;
//    	Map<String, Integer> tfbs2KMap;
//    	Map<String, Integer> histone2KMap;
//    	Map<String, Integer> exonBasedKeggPathway2KMap;
//    	Map<String, Integer> regulationBasedKeggPathway2KMap;
//    	
//    	int NUMBER_OF_AVAILABLE_PROCESSORS =  java.lang.Runtime.getRuntime().availableProcessors();
//    	
//    	Integer chromSize;
//    	String chromName;
//    	
//    	Random myrandom = new Random();
//    	
//    	List<Integer> hg19ChromosomeSizes = new ArrayList<Integer>();
//    	
//    	GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
//    	//get the hg19 chromosome sizes
//    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
//    	
//    	ExecutorService executor=null;
//    	
//		for(int i= 1; i<=Commons.NUMBER_OF_CHROMOSOMES_HG19; i++){
//			
//			chromName = GRCh37Hg19Chromosome.getChromosomeName(i);
//			chromSize = hg19ChromosomeSizes.get(i-1);
//			
//			GlanetRunner.appendLog("chromosome name:" + chromName);
//			
//			originalInputLines 	= chromosomeBasedOriginalInputLines.get(chromName);
//			
//			if(originalInputLines!=null){
//				
//				dnaseIntervalTree			= AnnotateGivenIntervalsWithGivenParameters.createDnaseIntervalTree(chromName);
//				tfbsIntervalTree 			= AnnotateGivenIntervalsWithGivenParameters.createTfbsIntervalTree(chromName);
//				histoneIntervalTree  		= AnnotateGivenIntervalsWithGivenParameters.createHistoneIntervalTree(chromName);
//				ucscRefSeqGeneIntervalTree 	= AnnotateGivenIntervalsWithGivenParameters.createUcscRefSeqGenesIntervalTree(chromName);
//											
//				List<Future<AllMaps>> list = new ArrayList<Future<AllMaps>>();
//			       		
//				for(int permutationNumber=1; permutationNumber<=NUMBER_OF_PERMUTATIONS ; permutationNumber++){
//					
//					List<InputLine> randomlyGeneratedData = new ArrayList<InputLine>();
//					
//					RandomDataGenerator.generateRandomData(randomlyGeneratedData,originalInputLines,myrandom,chromSize,chromName);
//					executor = Executors.newFixedThreadPool(NUMBER_OF_AVAILABLE_PROCESSORS+1);
//					
//					AnnotateCallable worker = new AnnotateCallable(permutationNumber, chromName,randomlyGeneratedData, dnaseIntervalTree, tfbsIntervalTree, histoneIntervalTree, ucscRefSeqGeneIntervalTree);
//					
//					Future<AllMaps> submit= executor.submit(worker);
//					list.add(submit);
//				}//end of for: each permutation
//						
//				// This will make the executor accept no new threads
//				// and finish all existing threads in the queue
//				executor.shutdown();
//				
//				// Wait until all threads are finish
//				while (!executor.isTerminated()) {
//				}
//			      
//		       for (Future<AllMaps> future : list) {
//		        try {
//		        	AllMaps allMaps = future.get();
//		        	
//		        	
//		        	dnase2KMap 						=  allMaps.getPermutationNumberDnaseCellLineName2KMap();
//		        	tfbs2KMap 						=  allMaps.getPermutationNumberTfNameCellLineName2KMap();
//		        	histone2KMap 					=  allMaps.getPermutationNumberHistoneNameCellLineName2KMap();
//		        	exonBasedKeggPathway2KMap 		=  allMaps.getPermutationNumberExonBasedKeggPathway2KMap();
//		        	regulationBasedKeggPathway2KMap =  allMaps.getPermutationNumberRegulationBasedKeggPathway2KMap();
//		        	
//		        	accumulate(dnase2KMap, permutationDnase2KMap);
//		        	accumulate(tfbs2KMap, permutationTfbs2KMap);
//		        	accumulate(histone2KMap, permutationHistone2KMap);
//		        	accumulate(exonBasedKeggPathway2KMap, permutationExonBasedKeggPathway2KMap);
//		        	accumulate(regulationBasedKeggPathway2KMap, permutationRegulationBasedKeggPathway2KMap);
//			        	
//		         } catch (InterruptedException e) {
//		          e.printStackTrace();
//		        } catch (ExecutionException e) {
//		          e.printStackTrace();
//		        }
//		      }//End of for: get of Future results
//
//			}//End of if: originalInputData is not null
//								      
//		}//End of for: each chromosome
//		
//	}
//	
//	public void calculateEmpricalPValue(Map<String, List<Integer>> name2AccumulatedKMap,Map<String, Integer> originalName2KMap, String outputFileName,int NUMBER_OF_PERMUTATIONS){
//		
//		FileWriter fileWriter;
//		BufferedWriter bufferedWriter;
//		
//		DecimalFormat df = new DecimalFormat("0.######E0");
//		
//		String  originalName;
//		Integer originalNumberofOverlaps;
//		List<Integer> listofNumberofOverlaps;
//		Integer numberofOverlaps;
//		double accumulatedSum = 0;
//		double empiricalPValue;
//		
//		try {
//			fileWriter = new FileWriter(outputFileName);
//			bufferedWriter = new BufferedWriter(fileWriter);
//			
//			
//			//only consider the names in the original name 2 k map
//			for(Map.Entry<String, Integer> entry: originalName2KMap.entrySet()){
//				originalName = entry.getKey();
//				originalNumberofOverlaps = entry.getValue();
//				
//				listofNumberofOverlaps = name2AccumulatedKMap.get(originalName);
//
//				//Initialise accumulatedSum to zero for each original name 
//				accumulatedSum = 0;
//				
//				if (listofNumberofOverlaps!=null){
//					for(int i =0; i<listofNumberofOverlaps.size(); i++){
//						
//						numberofOverlaps =listofNumberofOverlaps.get(i);
//						
//						if(numberofOverlaps >= originalNumberofOverlaps){
//							accumulatedSum++;
//						}
//					}//end of for
//				}//end of if
//				
//				empiricalPValue = accumulatedSum/NUMBER_OF_PERMUTATIONS;
//				
//				bufferedWriter.write(originalName + "\t" + df.format(empiricalPValue)+ "\n");
//					
//			}//end of for
//			
//			bufferedWriter.close();
//			
//		} catch (IOException e) {
//				e.printStackTrace();
//		}
//		
//		
//		
//	}
//	
//	
//
//	
//	
//	public  void partitionDataChromosomeBased(List<InputLine> originalInputLines, Map<String,List<InputLine>> chromosomeBasedOriginalInputLines){
//		InputLine line;
//		String chrName;
//		List<InputLine> list;
//		
//		
//		for(int i = 0; i<originalInputLines.size(); i++){
//			
//			line = originalInputLines.get(i);
//			chrName = line.getChrName();
//			list = chromosomeBasedOriginalInputLines.get(chrName);
//			
//			if (list == null){
//				list = new ArrayList<InputLine>();
//				list.add(line);
//				chromosomeBasedOriginalInputLines.put(chrName, list);
//			}else{
//				list.add(line);
//				chromosomeBasedOriginalInputLines.put(chrName,list);
//			}
//		}
//	}
//	
//	
//	public void convert(Map<String,Integer> permutationNumberName2KMap,Map<String,List<Integer>> name2AllKMap){
//		String permutationAugmentedName;
//		Integer numberofOverlaps;
//		int indexofFirstUnderscore;
//		String name;
//		
//		List<Integer> list;
//		
//		for(Map.Entry<String, Integer> entry: permutationNumberName2KMap.entrySet()){
//			
//			permutationAugmentedName = entry.getKey();
//			numberofOverlaps = entry.getValue();
//			
//			indexofFirstUnderscore = permutationAugmentedName.indexOf('_');
//			name = permutationAugmentedName.substring(indexofFirstUnderscore+1);
//			
//			list =name2AllKMap.get(name);
//			
//			if(list ==null){
//				list = new ArrayList<Integer>();
//				list.add(numberofOverlaps);
//				name2AllKMap.put(name, list);
//			}else{
//				list.add(numberofOverlaps);
//				name2AllKMap.put(name, list);
//				
//			}
//			
//		}
//	}
//	
//	public void deleteOldFiles(File folder){
//		 
//		File[] files = folder.listFiles();
//		 
//		    for(File file: files){
//		        if(file.isFile()){
//		            file.delete();
//		        }else if(file.isDirectory()) {
//		        	//GlanetRunner.appendLog("Folder Name: "+ file.getName() + " Absolute Path: " + file.getAbsolutePath());
//		        	//Do not delete Folder "C:\eclipse_juno_workspace\Doktora\src\annotate\intervals\parametric\output\all_possible_names"
//		        	if (!(file.getName().equals("all_possible_names"))){
//		        		deleteOldFiles(file);
//		        	}
//		        }
//		    }
//	}
//	
//	public static void main(String[] args) {
//		
//		GlanetRunner.appendLog("java runtime max memory: " + java.lang.Runtime.getRuntime().maxMemory()); 
//		//Number of processors can be paralellism level
//		GlanetRunner.appendLog("java runtime available processors: " + java.lang.Runtime.getRuntime().availableProcessors()); 
//		
//		AnnotateOneThousandPermutationsUsingExecutorService oneThousandPermutations = new AnnotateOneThousandPermutationsUsingExecutorService();
//
//		//functionalElementName based
//		//number of overlaps for the original data: k out of n for the original data
//		Map<String,Integer> originalDnase2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> originalTfbs2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> originalHistone2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> originalExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> originalRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
//		
//		//permutationName_functionalElementName based 
//		//number of overlaps: k out of n for randomly generated data
//		Map<String,Integer> permutationDnase2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> permutationTfbs2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> permutationHistone2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> permutationExonBasedKeggPathway2KMap = new HashMap<String,Integer>();
//		Map<String,Integer> permutationRegulationBasedKeggPathway2KMap = new HashMap<String,Integer>();
//			
//		//functionalElementName based
//		//number of overlaps: k out of n for all permutations
//		Map<String,List<Integer>> dnase2AllKMap = new HashMap<String,List<Integer>>();
//		Map<String,List<Integer>> tfbs2AllKMap = new HashMap<String,List<Integer>>();
//		Map<String,List<Integer>> histone2AllKMap = new HashMap<String,List<Integer>>();
//		Map<String,List<Integer>> exonBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
//		Map<String,List<Integer>> regulationBasedKeggPathway2AllKMap = new HashMap<String,List<Integer>>();
//	
//		List<InputLine> originalInputLines = new ArrayList<InputLine>();
//		Map<String,List<InputLine>> chromosomeBasedOriginalInputLines = new HashMap<String,List<InputLine>>();
//		
//		//Delete old files before run 
//		File folder = new File(Commons.E_DOKTORA_ECLIPSE_WORKSPACE_ANNOTATE_PERMUTATIONS);
//		oneThousandPermutations.deleteOldFiles(folder);
//		
//		int NUMBER_OF_PERMUTATIONS = 1000; 
//		
//		//Read original input data lines in to a list
//		oneThousandPermutations.readOriginalInputDataLines(originalInputLines, Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS);
//		
//		//Partition the original input data lines in a chromosome based manner
//		oneThousandPermutations.partitionDataChromosomeBased(originalInputLines,chromosomeBasedOriginalInputLines);
//			
//		//Annotate the original input data
//		//Read the original number of overlaps for each functional element.
//		oneThousandPermutations.annotateOriginalInputData(originalInputLines,originalDnase2KMap,originalTfbs2KMap,originalHistone2KMap,originalExonBasedKeggPathway2KMap,originalRegulationBasedKeggPathway2KMap);
//		
//		//generate random data and annotate for each permutation
//		oneThousandPermutations.generateRandomDataandAnnotate(chromosomeBasedOriginalInputLines, permutationDnase2KMap,permutationTfbs2KMap,permutationHistone2KMap,permutationExonBasedKeggPathway2KMap,permutationRegulationBasedKeggPathway2KMap,NUMBER_OF_PERMUTATIONS);
//		
//		//convert permutation augmented name to only name
//		oneThousandPermutations.convert(permutationDnase2KMap,dnase2AllKMap);
//		oneThousandPermutations.convert(permutationTfbs2KMap,tfbs2AllKMap);
//		oneThousandPermutations.convert(permutationHistone2KMap,histone2AllKMap);
//		oneThousandPermutations.convert(permutationExonBasedKeggPathway2KMap,exonBasedKeggPathway2AllKMap);
//		oneThousandPermutations.convert(permutationRegulationBasedKeggPathway2KMap,regulationBasedKeggPathway2AllKMap);
//		
//		//Calculate Empirical P Values and Write to output files
//		oneThousandPermutations.calculateEmpricalPValue(dnase2AllKMap, originalDnase2KMap, Commons.DNASE_CELL_LINE_NAME_EMPIRICAL_P_VALUES,NUMBER_OF_PERMUTATIONS);
//		oneThousandPermutations.calculateEmpricalPValue(tfbs2AllKMap, originalTfbs2KMap, Commons.TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES,NUMBER_OF_PERMUTATIONS);
//		oneThousandPermutations.calculateEmpricalPValue(histone2AllKMap, originalHistone2KMap, Commons.HISTONE_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES,NUMBER_OF_PERMUTATIONS);
//		oneThousandPermutations.calculateEmpricalPValue(exonBasedKeggPathway2AllKMap, originalExonBasedKeggPathway2KMap, Commons.EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,NUMBER_OF_PERMUTATIONS);
//		oneThousandPermutations.calculateEmpricalPValue(regulationBasedKeggPathway2AllKMap, originalRegulationBasedKeggPathway2KMap, Commons.REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES,NUMBER_OF_PERMUTATIONS);
//		
//		GlanetRunner.appendLog("stop at this debug point");
//		
//		}
//
//}
