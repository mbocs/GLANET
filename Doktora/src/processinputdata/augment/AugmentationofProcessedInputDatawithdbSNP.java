/**
 * @author Burcak Otlu
 * Mar 7, 2014
 * 4:04:32 PM
 * 2014
 *
 * 
 */

/*
 * 
 * It lasts for 25 minutes.
 * It uses dbSNP flat files for augmentation with observed alleles
 * 
 * This way will not be used it is very costly.
 * User needs to download 64 GB dbSNP asn1 flat files.
 */
package processinputdata.augment;

import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;
import intervaltree.OtherIntervalTreeNode;

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

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import dbSNP.CreationofChromosomeBasedSNPIntervalTrees;
import enumtypes.ChromosomeName;

public class AugmentationofProcessedInputDatawithdbSNP {

	/**
	 * 
	 */
	public AugmentationofProcessedInputDatawithdbSNP() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void fillChromosomeBasedSNPPositionsMap(String processedInputDataFileName, Map<String,List<SnpPosition>> chrName2SNPPositionsMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		String chrName;
		int zeroBasedStart;
		int zeroBasedEnd;
		
		List<SnpPosition> snpList;
		
		
		try {
			
			
			
			fileReader = new FileReader(processedInputDataFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				chrName = strLine.substring(0,indexofFirstTab);
				zeroBasedStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				zeroBasedEnd = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				
				
				snpList = chrName2SNPPositionsMap.get(chrName);
				
				if(snpList == null){
					
					snpList = new ArrayList<SnpPosition>();
					
					SnpPosition snpPosition = new SnpPosition(zeroBasedStart,zeroBasedEnd);
					
					snpList.add(snpPosition);
					
					chrName2SNPPositionsMap.put(chrName, snpList);
					
				}else{
					
					SnpPosition snpPosition = new SnpPosition(zeroBasedStart,zeroBasedEnd);
					
					snpList.add(snpPosition);
					
					chrName2SNPPositionsMap.put(chrName, snpList);
					
				}
				
				
			}//End of while
			
					
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	public static void augmentEachSNPwithDBSNP(String outputFolder,String dataFolder,Map<String,List<SnpPosition>> chrName2SNPPositionsMap,String augmentedwithdbSNPOutputFileName){
		
		IntervalTree dbSNPIntervalTree = null;
		ChromosomeName  chrName= null;
		List<SnpPosition> snpPositionList = null;
		List<IntervalTreeNode> overlappedNodeList = null;
		
		IntervalTreeNode root = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFolder + augmentedwithdbSNPOutputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			
			for(Map.Entry<String, List<SnpPosition>> entry:chrName2SNPPositionsMap.entrySet() ){

				//get the dbSNP interval tree
				chrName = ChromosomeName.convertStringtoEnum(entry.getKey());
				snpPositionList = entry.getValue();
				
				dbSNPIntervalTree = CreationofChromosomeBasedSNPIntervalTrees.readDBSNPFlatFileandCreateChromosomeBasedSNPIntervalTreeforGivenChromosome(dataFolder,chrName);
				root = dbSNPIntervalTree.getRoot();
								
				for(SnpPosition snpPosition : snpPositionList){
					//bufferedWriter.write(chrName+ "\t" + snpPosition.getStart() +"\n");
					
					
					IntervalTreeNode node = new IntervalTreeNode(snpPosition.getStartZeroBased(),snpPosition.getEndZeroBased());
					
					overlappedNodeList = new ArrayList<IntervalTreeNode>();
					
					//Overlaps
					dbSNPIntervalTree.findAllOverlappingIntervals(overlappedNodeList, root, node);
					
					if (overlappedNodeList.size()>0){
//						bufferedWriter.write("OverlapNodeListSize: " + overlappedNodeList.size() + "\n");
						
						for(IntervalTreeNode overlapNode:overlappedNodeList){
							bufferedWriter.write(((OtherIntervalTreeNode) overlapNode).getRsId() + "\t" + chrName.convertEnumtoString() + "\t" + snpPosition.getStartZeroBased() + "\t");
							
							for(String observedAllele :((OtherIntervalTreeNode)overlapNode).getObservedVariationAlleles()){
								bufferedWriter.write(observedAllele +"\t");
								
							}//for every observed allele
							
							bufferedWriter.write("\n");
						}//for every overlap
						
					}//End of if 
				}//for every snp
				
				GlanetRunner.appendLog(entry.getKey() +"\t" + entry.getValue().size());
				
				dbSNPIntervalTree = null;
			}//for every chromosome
			
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		

	}
	

	public static void augmentProcessedInputSNPswithdbSNP(String outputFolder,String dataFolder,String processedInputDataFileName,String augmentedwithdbSNPOutputFileName){
		
			
		Map<String,List<SnpPosition>> chrName2SNPPositionsMap = new HashMap<String,List<SnpPosition>>();
				
		fillChromosomeBasedSNPPositionsMap(processedInputDataFileName,chrName2SNPPositionsMap);
		
		augmentEachSNPwithDBSNP(outputFolder,dataFolder,chrName2SNPPositionsMap,augmentedwithdbSNPOutputFileName);
		
			
			
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
	//args[9] must have Dnase Enrichment example: DO_DNASE_ENRICHMENT or DO_NOT_DNASE_ENRICHMENT
	//args[10] must have Histone Enrichment example : DO_HISTONE_ENRICHMENT or DO_NOT_HISTONE_ENRICHMENT
	//args[11] must have Tf and KeggPathway Enrichment example: DO_TF_KEGGPATHWAY_ENRICHMENT or DO_NOT_TF_KEGGPATHWAY_ENRICHMENT
	//args[12] must have Tf and CellLine and KeggPathway Enrichment example: DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT or DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT
	//args[13] must have a job name exampe: any_string 
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;

		//ProcessedInput already contains 0-based coordinates.
		String processedInputDataFileName = outputFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE;
		
//		String augmentedwithdbSNPOutputFileName = Commons.OCD_GWAS_SIGNIFICANT_SNPS_AUGMENTED_WITH_DBSNP;
		
//		augmentProcessedInputSNPswithdbSNP(outputFolder,dataFolder,processedInputDataFileName,augmentedwithdbSNPOutputFileName);

	}

}
