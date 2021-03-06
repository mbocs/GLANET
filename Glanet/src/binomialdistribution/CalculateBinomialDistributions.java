/**
 * @author Burcak Otlu
 *
 * IMPORTANT NOTE
 * Before annotate.intervals.parametric.AnnotateGivenIntervalsWithGivenParameters has to be run
 * with the same input file used in the binomial distribution calculations.
 *
 * 
 */


package binomialdistribution;

import hg19.GRCh37Hg19Chromosome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.FastMath;

import common.Commons;




public class CalculateBinomialDistributions {

	
	
	public static void fillHashMapwithOccurences(Map<String,Integer> hashMap, String outputFolder,String inputFileName){
		String strLine;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		int indexofFirstTab = 0;
		String name;
		Integer occurrence;
		
		try {
			fileReader = new FileReader(outputFolder + inputFileName);			
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null) {
				
				indexofFirstTab = strLine.indexOf('\t');
				
				name = strLine.substring(0, indexofFirstTab);
				occurrence = Integer.valueOf(strLine.substring(indexofFirstTab+1));
				
				if (!(hashMap.containsKey(name))){
//					hashMap.put(strLine.toUpperCase(Locale.ENGLISH), Commons.LONG_ZERO);
					hashMap.put(name, occurrence);
				}
				
				strLine = null;
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public Long calculateWholeGenomeSize(List<Integer> chromosomeSizes, Long genomeSize){
		for(int i = 0; i<chromosomeSizes.size(); i++){
			genomeSize = genomeSize + chromosomeSizes.get(i);
		}
		return genomeSize;
	}
	
	
	public Integer calculateSearchInputSize(String inputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int count = 0;
		
		try {
			
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((bufferedReader.readLine())!=null){
				count++;
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public void fillFunctionalElementList(Map<String,Integer> funcElementOccurrencesSearchInputHashMap,Map<String,Integer> funcElementOccurrencesWholeGenomeHashMap,Integer searchInputDataSize,long genomeSize, List<FunctionalElement> functionalElementList){
		
		Set<Map.Entry<String,Integer>> setSearchInput = funcElementOccurrencesSearchInputHashMap.entrySet();
		Iterator<Map.Entry<String, Integer>> itr = setSearchInput.iterator();
		double probability;
		
		while(itr.hasNext()){
			
			Map.Entry<String, Integer> entry = (Map.Entry<String,Integer>) itr.next();
			String functionalElementName = entry.getKey();
			Integer numberofOccurrencesinSearchInput = entry.getValue();
			
			//Get the corresponding numberofOccurrences of this functional Element from funcElementOccurrencesWholeGenomeHashMap
			
			int numberofOccurrencesinWholeGenome = funcElementOccurrencesWholeGenomeHashMap.get(functionalElementName);
			
			probability = (numberofOccurrencesinWholeGenome*1.0)/genomeSize;
				
			FunctionalElement functionalElement = new FunctionalElement(numberofOccurrencesinSearchInput,numberofOccurrencesinWholeGenome,probability,genomeSize,searchInputDataSize,functionalElementName);
			
			functionalElementList.add(functionalElement);
		}
		
	}
	
	
	
	public double logCombination(int n, int k){	
 		return  ArithmeticUtils.factorialLog(n)-ArithmeticUtils.factorialLog(k) -ArithmeticUtils.factorialLog(n-k);
	
	}
	

	public double func (int k, int n, double p){
		
		return logCombination(n,k) + (k *FastMath.log(p)) + ((n-k) *FastMath.log(1-p));
	}
	
	
	public double computePvalue(FunctionalElement element){
		double probability = element.getProbability();
		int  numberofOccurrencesinSearchInputData = element.getNumberofOccurrencesinSearchInputData();
		int searchInputSize = element.getSearchInputSize();
		double pValue = 0.0;
		
		
		for(int k = numberofOccurrencesinSearchInputData ; k<= searchInputSize; k++){
			pValue = pValue +  FastMath.pow(FastMath.E, func(k,searchInputSize,probability));
		}
				
		return pValue;
		
	}
	
	public double calculatePvalueUsingBinomialDistribution(FunctionalElement element){
		double probability = element.getProbability();
		int  numberofOccurrencesinSearchInputData = element.getNumberofOccurrencesinSearchInputData();
		int searchInputSize = element.getSearchInputSize();
		double pValue = 0.0;

		 BinomialDistribution binomialDistribution = new BinomialDistribution(searchInputSize, probability);
		 pValue = 1- binomialDistribution.cumulativeProbability(numberofOccurrencesinSearchInputData-1);
					
		return pValue;
	}
	
	public void fillPvalues(List<FunctionalElement> functionalElementList){
		for (int i = 0; i< functionalElementList.size(); i++){
			FunctionalElement element = functionalElementList.get(i);
		
			double pValue = computePvalue(element);
			element.setPValue(pValue);
			if (pValue<0.00001){
//				GlanetRunner.appendLog(element.getName() + "\tp-value:\t" + pValue);
			}
		}
		
	}
	
	
	public void calculatePValuesUsingBinomialDistribution(List<FunctionalElement> functionalElementList){
		for (int i = 0; i< functionalElementList.size(); i++){
			FunctionalElement element = functionalElementList.get(i);
		
			double pValue = calculatePvalueUsingBinomialDistribution(element);
			element.setPValue(pValue);
		}
		
	}
	
	public void writeRawPValues(List<FunctionalElement> functionalElementList, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
//		DecimalFormat df = new DecimalFormat("#,###,##0.00000000000000000000");
		DecimalFormat df = new DecimalFormat("0.######E0");
		   
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i = 0; i< functionalElementList.size(); i++){
				
//				Both have the same results
				bufferedWriter.write(functionalElementList.get(i).getName() + "\t" + df.format(functionalElementList.get(i).getPValue())+ "\n");
				
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//Bonferroni's correction
	//multiply with numberofComparison
	public void writeAdjustedPValues(List<FunctionalElement> functionalElementList, String outputFileName, int numberofComparison){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		Double bonferroniCorrectedPValue;
		
//		DecimalFormat df = new DecimalFormat("#,###,##0.00000000000000000000");
		DecimalFormat df = new DecimalFormat("0.######E0");
		   
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i = 0; i< functionalElementList.size(); i++){
				
				bonferroniCorrectedPValue = functionalElementList.get(i).getPValue()*numberofComparison;
				
				if (bonferroniCorrectedPValue >1.0) {
					bonferroniCorrectedPValue = 1.0;
				}
//				Both have the same results
				bufferedWriter.write(functionalElementList.get(i).getName() + "\t" + df.format(bonferroniCorrectedPValue)+ "\n");
				
			}//End of For
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeRawAll(List<FunctionalElement> dnaseCellLineNameList, String outputFileName){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
//		DecimalFormat df = new DecimalFormat("#,###,##0.00000000000000000000");
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		   
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			FunctionalElement element;

			//Header Row
			bufferedWriter.write("Name" + "\t"   + "OccurrencesInSearchInputData" + "\t" + "SearchInputDataSize" + "\t" + "OccurencesInWholeGenome" + "\t"+ "WholeGenomeSize" + "\t" + "Raw PValue" + "\n");				

			for(int i = 0; i< dnaseCellLineNameList.size(); i++){
				element = dnaseCellLineNameList.get(i);
				bufferedWriter.write(element.getName() + "\t"   + element.getNumberofOccurrencesinSearchInputData() + "\t" +element.getSearchInputSize() + "\t" +element.getNumberofOccurrencesinWholeGenome() + "\t"+element.getGenomeSize() + "\t" + df.format(element.getPValue())+ "\n");				
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Bonferroni's correction
	//multiply with numberofComparison
	public void writeAdjustedAll(List<FunctionalElement> nameList, String outputFileName,int numberofComparison){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		Double bonferroniCorrectedPValue;
		
//		DecimalFormat df = new DecimalFormat("#,###,##0.00000000000000000000");
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		   
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			FunctionalElement element;

			//Header Row
			bufferedWriter.write("Name" + "\t"   + "OccurrencesInSearchInputData" + "\t" + "SearchInputDataSize" + "\t" + "OccurencesInWholeGenome" + "\t"+ "WholeGenomeSize" + "\t" + "Adjusted PValue" + "\n");				

			for(int i = 0; i< nameList.size(); i++){
				element = nameList.get(i);
				
				bonferroniCorrectedPValue = element.getPValue()*numberofComparison;
				
				if (bonferroniCorrectedPValue >1.0) {
					bonferroniCorrectedPValue = 1.0;
				}
				
				bufferedWriter.write(element.getName() + "\t"   + element.getNumberofOccurrencesinSearchInputData() + "\t" +element.getSearchInputSize() + "\t" +element.getNumberofOccurrencesinWholeGenome() + "\t"+element.getGenomeSize() + "\t" + df.format(bonferroniCorrectedPValue)+ "\n");				
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	/*
	 * IMPORTANT NOTE
	 * Before annotate.intervals.parametric.AnnotateGivenIntervalsWithGivenParameters has to be run
	 * with the same input file used in the binomial distribution calculations.
	 * 
	 */
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
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;

		
		String calculateMode;
		
		//Bonferroni's correction, number of comparison
		int numberofComparison;
		
		CalculateBinomialDistributions calculate = new CalculateBinomialDistributions();
		
		List<Integer> chromosomeSizes = new ArrayList<Integer>(24);
		Long genomeSize = Commons.LONG_ZERO;
		Integer searchInputDataSize = null;
		
		Map<String,Integer> dnaseCellLineNumberofNonoverlappingOccurrencesSearchInputHashMap = new HashMap<String,Integer>();
		Map<String,Integer> dnaseCellLineNumberofNonoverlappingOccurrencesWholeGenomeHashMap = new HashMap<String,Integer>();
		List<FunctionalElement> dnaseCellLineList = new ArrayList<FunctionalElement>();
		
		Map<String,Integer> tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesSearchInputHashMap = new HashMap<String,Integer>();
		Map<String,Integer> tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap = new HashMap<String,Integer>();
		List<FunctionalElement> tfbsList = new ArrayList<FunctionalElement>();
		
		Map<String,Integer> histoneNameandCellLineNameNumberofNonoverlappingOccurrencesSearchInputHashMap = new HashMap<String,Integer>();
		Map<String,Integer> histoneNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap = new HashMap<String,Integer>();
		List<FunctionalElement> histoneList = new ArrayList<FunctionalElement>();
		
		Map<String,Integer> exonBasedKeggPathwaySearchInputMap = new HashMap<String,Integer>();
		Map<String,Integer> exonBasedKeggPathwayWholeGenomeMap = new HashMap<String,Integer>();
		List<FunctionalElement> exonBasedKeggPathwayList = new ArrayList<FunctionalElement>();
		
		Map<String,Integer> regulationBasedKeggPathwaySearchInputMap = new HashMap<String,Integer>();
		Map<String,Integer> regulationBasedKeggPathwayWholeGenomeMap = new HashMap<String,Integer>();
		List<FunctionalElement> regulationBasedKeggPathwayList = new ArrayList<FunctionalElement>();
		
//		calculateMode = Commons.CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION;
		calculateMode = Commons.CALCULATE_USING_BINOMIAL_DISTRIBUTION;
		
		
		//Calculate whole genome size using hg19_chromosome_sizes input file
		GRCh37Hg19Chromosome.initializeChromosomeSizes(chromosomeSizes);
		GRCh37Hg19Chromosome.getHg19ChromosomeSizes(chromosomeSizes,dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		genomeSize = calculate.calculateWholeGenomeSize(chromosomeSizes, genomeSize);
		
		//calculate search input size using C:\eclipse_juno_workspace\Doktora\src\inputdata\process\output\TCGAInputDataWithNonBlankSNPsWithoutOverlaps.txt
//		searchInputDataSize = calculate.calculateSearchInputSize(Commons.TCGA_INPUT_DATA_WITH_NON_BLANKS_SNP_IDS_WITHOUT_OVERLAPS);
//		searchInputDataSize = calculate.calculateSearchInputSize(Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS);
//		searchInputDataSize = calculate.calculateSearchInputSize(Commons.POSITIVE_CONTROL_OUTPUT_FILE_NAME_WITHOUT_OVERLAPS);
		
		
		
		/*---------------------------------------------------------*/	
		//Dnase		
		fillHashMapwithOccurences(dnaseCellLineNumberofNonoverlappingOccurrencesSearchInputHashMap, outputFolder,Commons.ANNOTATION_RESULTS_FOR_DNASE);
		fillHashMapwithOccurences(dnaseCellLineNumberofNonoverlappingOccurrencesWholeGenomeHashMap, outputFolder,Commons.DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE);
		
		//set number of comparison
		numberofComparison = dnaseCellLineNumberofNonoverlappingOccurrencesWholeGenomeHashMap.size();
		
		calculate.fillFunctionalElementList(dnaseCellLineNumberofNonoverlappingOccurrencesSearchInputHashMap,dnaseCellLineNumberofNonoverlappingOccurrencesWholeGenomeHashMap,searchInputDataSize,genomeSize,dnaseCellLineList);
		if (Commons.CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.fillPvalues(dnaseCellLineList);				
		}else if (Commons.CALCULATE_USING_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.calculatePValuesUsingBinomialDistribution(dnaseCellLineList);			
		}
	
		//sort before writing
		Collections.sort(dnaseCellLineList,FunctionalElement.P_VALUE);
//		calculate.writeRawPValues(dnaseCellLineList, Commons.DNASE_CELLLINE_NAMES_P_VALUES);
//		calculate.writeRawAll(dnaseCellLineList, Commons.DNASE_CELLLINE_NAMES_ALL_VALUES);
		calculate.writeAdjustedPValues(dnaseCellLineList, Commons.DNASE_CELLLINE_NAMES_ADJUSTED_P_VALUES,numberofComparison);
		calculate.writeAdjustedAll(dnaseCellLineList, Commons.DNASE_CELLLINE_NAMES_ADJUSTED_ALL_VALUES,numberofComparison);
		
		/*---------------------------------------------------------*/	
		//Tfbs
		fillHashMapwithOccurences(tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesSearchInputHashMap, outputFolder,Commons.ANNOTATION_RESULTS_FOR_TF);
		fillHashMapwithOccurences(tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap, outputFolder,Commons.TFBS_WHOLE_GENOME_USING_INTERVAL_TREE);
		
		//set number of comparison
		numberofComparison = tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap.size();
				
		
		calculate.fillFunctionalElementList(tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesSearchInputHashMap,tfbsNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap,searchInputDataSize,genomeSize,tfbsList);
		if (Commons.CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.fillPvalues(tfbsList);				
		}else if (Commons.CALCULATE_USING_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.calculatePValuesUsingBinomialDistribution(tfbsList);			
		}

		//sort before writing
		Collections.sort(tfbsList,FunctionalElement.P_VALUE);
//		calculate.writeRawPValues(tfbsList, Commons.TFBS_P_VALUES);
//		calculate.writeRawAll(tfbsList, Commons.TFBS_ALL_VALUES);
		calculate.writeAdjustedPValues(tfbsList, Commons.TFBS_ADJUSTED_P_VALUES,numberofComparison);
		calculate.writeAdjustedAll(tfbsList, Commons.TFBS_ADJUSTED_ALL_VALUES,numberofComparison);
		
		/*---------------------------------------------------------*/	
		//histone
		fillHashMapwithOccurences(histoneNameandCellLineNameNumberofNonoverlappingOccurrencesSearchInputHashMap, outputFolder,Commons.ANNOTATION_RESULTS_FOR_HISTONE);
		fillHashMapwithOccurences(histoneNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap, outputFolder,Commons.HISTONE_WHOLE_GENOME_USING_INTERVAL_TREE);
		
		//set number of comparison
		numberofComparison = histoneNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap.size();
	
		
		calculate.fillFunctionalElementList(histoneNameandCellLineNameNumberofNonoverlappingOccurrencesSearchInputHashMap,histoneNameandCellLineNameNumberofNonoverlappingOccurrencesWholeGenomeHashMap,searchInputDataSize,genomeSize,histoneList);
		if (Commons.CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.fillPvalues(histoneList);				
		}else if (Commons.CALCULATE_USING_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.calculatePValuesUsingBinomialDistribution(histoneList);			
		}
		
		//sort before writing
		Collections.sort(histoneList,FunctionalElement.P_VALUE);
//		calculate.writeRawPValues(histoneList, Commons.HISTONE_P_VALUES);
//		calculate.writeRawAll(histoneList, Commons.HISTONE_ALL_VALUES);
		calculate.writeAdjustedPValues(histoneList, Commons.HISTONE_ADJUSTED_P_VALUES,numberofComparison);
		calculate.writeAdjustedAll(histoneList, Commons.HISTONE_ADJUSTED_ALL_VALUES,numberofComparison);
		
		/*---------------------------------------------------------*/	
		//exon based kegg pathway
		fillHashMapwithOccurences(exonBasedKeggPathwaySearchInputMap, outputFolder,Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_EXON_BASED_KEGGPATHWAY_FILE);
		fillHashMapwithOccurences(exonBasedKeggPathwayWholeGenomeMap, outputFolder,Commons.EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE);
		
		//set number of comparison
		numberofComparison = exonBasedKeggPathwayWholeGenomeMap.size();
			
		calculate.fillFunctionalElementList(exonBasedKeggPathwaySearchInputMap,exonBasedKeggPathwayWholeGenomeMap,searchInputDataSize,genomeSize,exonBasedKeggPathwayList);
		if (Commons.CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.fillPvalues(exonBasedKeggPathwayList);				
		}else if (Commons.CALCULATE_USING_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.calculatePValuesUsingBinomialDistribution(exonBasedKeggPathwayList);			
		}

		//sort before writing
		Collections.sort(exonBasedKeggPathwayList, FunctionalElement.P_VALUE);	
//		calculate.writeRawPValues(exonBasedKeggPathwayList, Commons.EXON_BASED_KEGG_PATHWAY_P_VALUES);
//		calculate.writeRawAll(exonBasedKeggPathwayList, Commons.EXON_BASED_KEGG_PATHWAY_ALL_VALUES);
		calculate.writeAdjustedPValues(exonBasedKeggPathwayList, Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUES,numberofComparison);
		calculate.writeAdjustedAll(exonBasedKeggPathwayList, Commons.EXON_BASED_KEGG_PATHWAY_ADJUSTED_ALL_VALUES,numberofComparison);
		
		/*---------------------------------------------------------*/	
		//regulation based kegg pathway
		fillHashMapwithOccurences(regulationBasedKeggPathwaySearchInputMap, outputFolder,Commons.ANNOTATION_RESULTS_FOR_KEGGPATHWAY + Commons.ANNOTATION_RESULTS_FOR_REGULATION_BASED_KEGGPATHWAY_FILE);
		fillHashMapwithOccurences(regulationBasedKeggPathwayWholeGenomeMap, outputFolder,Commons.REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE);
		
		//set number of comparison
		numberofComparison = regulationBasedKeggPathwayWholeGenomeMap.size();
				
		
		calculate.fillFunctionalElementList(regulationBasedKeggPathwaySearchInputMap,regulationBasedKeggPathwayWholeGenomeMap,searchInputDataSize,genomeSize,regulationBasedKeggPathwayList);
		if (Commons.CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.fillPvalues(regulationBasedKeggPathwayList);				
		}else if (Commons.CALCULATE_USING_BINOMIAL_DISTRIBUTION.equals(calculateMode)){
			calculate.calculatePValuesUsingBinomialDistribution(regulationBasedKeggPathwayList);			
		}

		//sort before writing
		Collections.sort(regulationBasedKeggPathwayList, FunctionalElement.P_VALUE);
//		calculate.writeRawPValues(regulationBasedKeggPathwayList, Commons.REGULATION_BASED_KEGG_PATHWAY_P_VALUES);
//		calculate.writeRawAll(regulationBasedKeggPathwayList, Commons.REGULATION_BASED_KEGG_PATHWAY_ALL_VALUES);
		calculate.writeAdjustedPValues(regulationBasedKeggPathwayList, Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUES,numberofComparison);
		calculate.writeAdjustedAll(regulationBasedKeggPathwayList, Commons.REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_ALL_VALUES,numberofComparison);

	}

}
