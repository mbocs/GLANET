/**
 * @author Burcak Otlu
 * Aug 19, 2013
 * 11:37:56 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import hg19.GRCh37Hg19Chromosome;
import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;
import intervaltree.MapabilityIntervalTreeNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ui.GlanetRunner;

import common.Commons;

import enrichment.GCCharArray;
import enrichment.InputLine;
import enrichment.MapabilityFloatArray;
import enumtypes.ChromosomeName;

public class Mapability {
	
	static IntervalTree mapabilityIntervalTree;

	public static IntervalTree getMapabilityIntervalTree() {
		return mapabilityIntervalTree;
	}


	public static void setMapabilityIntervalTree(IntervalTree mapabilityIntervalTree) {
		Mapability.mapabilityIntervalTree = mapabilityIntervalTree;
	}


	
	public static void fillChromBasedMapabilityArrayfromFile(String dataFolder,int chromSize,String inputFileName, MapabilityFloatArray mapabilityFloatArray){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		//chrY	10000	10153	0.5

		String chromName = null;
		int low;
		int high;
		float mapability;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		try {
			fileReader = new FileReader(dataFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				
				chromName = strLine.substring(0, indexofFirstTab);
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
				mapability = Float.parseFloat(strLine.substring(indexofThirdTab+1));
				
				//high is 1-based therefore it can be equal to chromSize
				if (low>=chromSize || high > chromSize ){
					GlanetRunner.appendLog("Unexpected situation: There exists a line in mapability file of " + chromName + " which exceeds chromsize " + chromSize + " low: " + low + " high: "+ high);
				}
				
				//High-1 is done here
				for(int i= low; i<high; i++){
					mapabilityFloatArray.getMapabilityArray()[i] = mapability;
				}
							
			}
			
			GlanetRunner.appendLog("This file must be read only once " + inputFileName + " chromName: " + chromName + " Mapability Double Array construction has ended.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	//todo
	public static  void fillChromBasedMapabilityArray(String dataFolder,int chromSize, ChromosomeName chromName, MapabilityFloatArray mapabilityFloatArray){		
		switch(chromName){
			case CHROMOSOME1:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR1_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME2:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR2_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME3:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR3_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME4:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR4_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME5:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR5_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME6:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR6_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME7:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR7_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME8:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR8_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME9:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR9_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME10:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR10_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME11:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR11_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME12:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR12_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME13:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR13_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME14:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR14_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME15:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR15_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME16:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR16_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME17:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR17_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME18:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR18_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME19:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR19_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME20:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR20_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME21:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR21_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOME22:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHR22_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOMEX:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHRX_FILE,mapabilityFloatArray);
										break;
			case CHROMOSOMEY:   fillChromBasedMapabilityArrayfromFile(dataFolder,chromSize,Commons.MAPABILITY_HG19_CHRY_FILE,mapabilityFloatArray);
										break;
							
					
		}
	}

	
	
	//todo
	//for variance calculation among functional elements' mapability values
	public static float calculateMapability(int low, int high, IntervalTree mapabilityIntervalTree){
		
		
		float accumulatedMapability=0;
		
		int numberofOverlappingBases;
		List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
		
		IntervalTreeNode node = new IntervalTreeNode(low, high);
		MapabilityIntervalTreeNode overlappedNode;
		
		
		mapabilityIntervalTree.findAllOverlappingIntervals(overlappedNodeList,mapabilityIntervalTree.getRoot(), node);
		
		
		//there is overlap
		//calculate mapability
		if (overlappedNodeList!= null && overlappedNodeList.size()>0){
			
			//Base assumption is that nodes in the overlappingNodeList do not overlap with each other
			for(int i=0; i<overlappedNodeList.size(); i++){
				
				overlappedNode = (MapabilityIntervalTreeNode) overlappedNodeList.get(i);
				
				numberofOverlappingBases = calculateTheNumberofOverlappingBases(node,overlappedNode);
				accumulatedMapability = accumulatedMapability + (overlappedNode.getMapability()* numberofOverlappingBases);
			}//end of FOR
			
		} // End of IF
		
		accumulatedMapability = accumulatedMapability/node.getNumberofBases();
				
		return accumulatedMapability;
		
	}
	
	
	public static float differenceofMapabilities(InputLine inputLine1, InputLine inputLine2){
		return Math.abs(inputLine1.getMapability() - inputLine2.getMapability());
	}
	
	
	public static int calculateTheNumberofOverlappingBases(IntervalTreeNode  node1, IntervalTreeNode node2){

		// Base Assumption is that these nodes overlaps
		//We know that node1 and node2 overlaps
		int start = Math.max(node1.getLow(), node2.getLow());
		int end = Math.min(node1.getHigh(), node2.getHigh()); 
		
		int numberofOverlappingBases = end-start+1;
		
		return numberofOverlappingBases;
	}
	
	//check it, test it
	public static void calculateMapabilityofInterval(InputLine givenInputLine,IntervalTree mapabilityIntervalTree){
		int numberofOverlappingBases;
		
		float accumulatedMapability=0;
		
		int low = givenInputLine.getLow();
		int high = givenInputLine.getHigh();
		
		List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
		
		IntervalTreeNode node = new IntervalTreeNode(low, high);
		MapabilityIntervalTreeNode overlappedNode;
		
		mapabilityIntervalTree.findAllOverlappingIntervals(overlappedNodeList,mapabilityIntervalTree.getRoot(), node);
		
		//there is overlap
		//calculate mapability
		if (overlappedNodeList!= null && overlappedNodeList.size()>0){
			
			//Base assumption is that nodes in the overlappingNodeList do not overlap with each other
			for(int i=0; i<overlappedNodeList.size(); i++){
				
				overlappedNode = (MapabilityIntervalTreeNode) overlappedNodeList.get(i);
						
				numberofOverlappingBases = calculateTheNumberofOverlappingBases(node,overlappedNode);
				accumulatedMapability = accumulatedMapability + (overlappedNode.getMapability()* numberofOverlappingBases);
			}//end of FOR
			
		} // End of IF
		
		accumulatedMapability = accumulatedMapability/node.getNumberofBases();
		
		givenInputLine.setMapability(accumulatedMapability);
	}
	

	public static void calculateMapabilityofIntervalUsingArray(InputLine givenInputLine,MapabilityFloatArray mapabilityDoubleArray){
		
		float accumulatedMapability=0;
		
		int low = givenInputLine.getLow();
		int high = givenInputLine.getHigh();
		
		int length=high-low+1;
		
	
		for(int i = low; i<=high; i++){
			accumulatedMapability = accumulatedMapability + mapabilityDoubleArray.getMapabilityArray()[i];
		}
		
		accumulatedMapability = accumulatedMapability/length;
			
		givenInputLine.setMapability(accumulatedMapability);
	}
	
	
	public static void fillChromosomeBasedMapabilityIntervalTreefromFile(int chromSize,String inputFileName, IntervalTree chromBasedMapabilityIntervalTree){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine;
		//chrY	10000	10153	0.5

		String chromName = null;
		int low;
		int high;
		float mapability;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				
				chromName = strLine.substring(0, indexofFirstTab);
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
				mapability = Float.parseFloat(strLine.substring(indexofThirdTab+1));
				
				//high is 1-based therefore it can be equal to chromSize
				if (low>=chromSize|| high > chromSize ){
					GlanetRunner.appendLog("Unexpected situation: There exists a line in mapability file of " + chromName + " which exceeds chromsize " + chromSize + " low: " + low + " high: "+ high);
				}
				
				
				//High-1 is done here
				IntervalTreeNode node = new MapabilityIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName), low, high-1, mapability);
				
				//Assumption there will be no overlaps
				chromBasedMapabilityIntervalTree.intervalTreeInsert(chromBasedMapabilityIntervalTree, node);
				
				
//				//Check for overlaps each time before you insert a node
//				List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
//				
//				chromBasedMapabilityIntervalTree.findAllOverlappingIntervals(overlappedNodeList,chromBasedMapabilityIntervalTree.getRoot(), node);
//				
//				//there is overlap
//				if (overlappedNodeList!= null && overlappedNodeList.size()>0){
//					GlanetRunner.appendLog("Unexpected situation: there is overlap in interval tree for mapability");
//				}else{
//					chromBasedMapabilityIntervalTree.intervalTreeInsert(chromBasedMapabilityIntervalTree, node);
//					
//				}
							
			}
			
			GlanetRunner.appendLog("This file must be read only once " + inputFileName + " chromName: " + chromName + " Mapability Interval Tree construction has ended.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public static void fillChromosomeBasedMapabilityIntervalTree(int chromSize,ChromosomeName chromName,IntervalTree chromBasedMapabilityIntervalTree){	
		
		switch(chromName){
			case CHROMOSOME1:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR1_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME2:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR2_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME3:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR3_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME4:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR4_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME5:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR5_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME6:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR6_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME7:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR7_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME8:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR8_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME9:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR9_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME10:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR10_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME11:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR11_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME12: 	fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR12_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME13:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR13_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME14:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR14_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME15:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR15_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME16:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR16_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME17:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR17_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME18:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR18_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME19:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR19_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME20:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR20_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME21:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR21_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOME22:  fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHR22_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOMEX:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHRX_FILE,chromBasedMapabilityIntervalTree);
										break;
			case CHROMOSOMEY:   fillChromosomeBasedMapabilityIntervalTreefromFile(chromSize,Commons.MAPABILITY_HG19_CHRY_FILE,chromBasedMapabilityIntervalTree);
										break;
	
		}//End of Switch
		
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
		
		
		//for testing purposes
		List<Integer> hg19ChromosomeSizes	= new ArrayList<Integer>();
    			
    	GRCh37Hg19Chromosome.initializeChromosomeSizes(hg19ChromosomeSizes);
    	//get the hg19 chromosome sizes
    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, dataFolder,Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		
		ChromosomeName chromName = ChromosomeName.CHROMOSOME17;
    	int chromSize = hg19ChromosomeSizes.get(16);
    	
    	GCCharArray gcCharArray = null;
    	MapabilityFloatArray mapabilityFloatArray = null;
    	IntervalTree mapabilityIntervalTree=null;
    	
		gcCharArray = ChromosomeBasedGCArray.getChromosomeGCArray(dataFolder,chromName,chromSize);
		mapabilityFloatArray = ChromosomeBasedMapabilityArray.getChromosomeMapabilityArray(dataFolder,chromName,chromSize);
		mapabilityIntervalTree = ChromosomeBasedMapabilityIntervalTree.getChromosomeBasedMapabilityIntervalTree(chromName, chromSize);
		
		int low= 35100000;
		int high = 35100999;
		
		InputLine inputLine = new InputLine(chromName, low, high);
		
		Mapability.calculateMapabilityofInterval(inputLine, mapabilityIntervalTree);
		GlanetRunner.appendLog("Using Interval Tree "+ inputLine.getMapability());
		Mapability.calculateMapabilityofIntervalUsingArray(inputLine, mapabilityFloatArray);
		GlanetRunner.appendLog("Using Double Array: "+ inputLine.getMapability());
		
		
    	
    }

}
