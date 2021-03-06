/**
 * @author Burcak Otlu Saritas
 *
 * 
 */

/*
 * This program divides the input data into chromosome based files
 * Then for each chromosome based files it searches for the dnase, tfbs, histone and ucsc refseq genes 
 * whether the input line overlaps with any dnase, tfbs, histone and ucsc refseq genes.
 * 
 * Using unsorted chromosome based dnase, tfbs, histone and refseq genes files
 * interval tree are formed.
 * 
 * search is done through this interval trees.
 */

package search.encodeucscgenome;

import intervaltree.DnaseIntervalTreeNode;
import intervaltree.Interval;
import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;
import intervaltree.TforHistoneIntervalTreeNode;
import intervaltree.UcscRefSeqGeneIntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import create.ChromosomeBasedFilesandOperations;
import enumtypes.ChromosomeName;
import enumtypes.IntervalName;
import enumtypes.NodeType;
 

public class SearchChromosomeIntervalsUsingIntervalTree {

	public IntervalTree generateEncodeHistoneIntervalTree(BufferedReader bufferedReader) {
		IntervalTree histoneIntervalTree = new IntervalTree();
		String strLine;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
				
		String chromName;
		int startPosition = 0;
		int endPosition = 0;
		
		String histoneName;
		String cellLineName;
		String fileName;
		
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
//				example strLine
//				chr9	131533188	131535395	H2az	Gm12878	wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
					
				chromName = strLine.substring(0,indexofFirstTab);
				startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));				
				histoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				cellLineName = strLine.substring(indexofFourthTab+1,indexofFifthTab);
				fileName = strLine.substring(indexofFifthTab+1);
				
//				Creating millions of nodes with six attributes causes out of memory error
				IntervalTreeNode node = new TforHistoneIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName),startPosition,endPosition,histoneName,cellLineName,fileName,NodeType.ORIGINAL);
				histoneIntervalTree.intervalTreeInsert(histoneIntervalTree, node);
								
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return histoneIntervalTree;
	}
	
	public IntervalTree generateEncodeDnaseIntervalTree(BufferedReader bufferedReader) {
		IntervalTree dnaseIntervalTree = new IntervalTree();
		String strLine;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		
		int startPosition = 0;
		int endPosition = 0;
		
		String chromName;
		String cellLineName;
		String fileName;
		
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
//				example strLine
//				chr1	91852781	91853156	GM12878	idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
					
				chromName = strLine.substring(0,indexofFirstTab);
				startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				cellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				fileName = strLine.substring(indexofFourthTab+1);
				
//				Creating millions of nodes with six attributes causes out of memory error
				IntervalTreeNode node = new DnaseIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName),startPosition,endPosition,cellLineName,fileName,NodeType.ORIGINAL);
				dnaseIntervalTree.intervalTreeInsert(dnaseIntervalTree, node);
								
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dnaseIntervalTree;
	}
	
	public IntervalTree generateEncodeTfbsIntervalTree(BufferedReader bufferedReader){
		IntervalTree tfbsIntervalTree = new IntervalTree();
		String strLine;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		
		int startPosition = 0;
		int endPosition = 0;
		
		String chromName;
		String tfbsName;
		String cellLineName;
		String fileName;
		
	
		try {
			while((strLine = bufferedReader.readLine())!=null){
//				exampple strLine
//				chrY	2804079	2804213	Ctcf	H1hesc	spp.optimal.wgEncodeBroadHistoneH1hescCtcfStdAlnRep0_VS_wgEncodeBroadHistoneH1hescControlStdAlnRep0.narrowPeak
			
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
					
				chromName = strLine.substring(0,indexofFirstTab);
				startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				tfbsName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
				fileName = strLine.substring(indexofFifthTab+1);
				
//				Creating millions of nodes with six attributes causes out of memory error
				IntervalTreeNode node = new TforHistoneIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName),startPosition,endPosition,tfbsName,cellLineName,fileName,NodeType.ORIGINAL);
				tfbsIntervalTree.intervalTreeInsert(tfbsIntervalTree, node);
								
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tfbsIntervalTree;
	}
	
	public IntervalTree generateUcscRefSeqGenesIntervalTree(BufferedReader bufferedReader){
		IntervalTree tree = new IntervalTree();
		String strLine;
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;
		
		
		int startPosition = 0;
		int endPosition = 0;
		
		ChromosomeName chromName;
		String  refSeqGeneName;
		Integer geneEntrezId;
		IntervalName intervalName;
		int intervalNumber;
		String geneHugoSymbol;
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
//				example strLine
//				chr17	67074846	67075215	NM_080284	23460	Exon	1	-	ABCA6

				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab = strLine.indexOf('\t', indexofThirdTab+1);
				indexofFifthTab = strLine.indexOf('\t', indexofFourthTab+1);
				indexofSixthTab = strLine.indexOf('\t', indexofFifthTab+1);
				indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);	
				indexofEighthTab = strLine.indexOf('\t',indexofSeventhTab+1);
				
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
				startPosition = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
				endPosition = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				refSeqGeneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				geneEntrezId = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
				intervalName = IntervalName.convertStringtoEnum(strLine.substring(indexofFifthTab+1, indexofSixthTab));
				intervalNumber = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
				geneHugoSymbol = strLine.substring(indexofEighthTab+1);
				
//				Creating millions of nodes with seven attributes causes out of memory error
				IntervalTreeNode node = new UcscRefSeqGeneIntervalTreeNode(chromName,startPosition,endPosition,refSeqGeneName,geneEntrezId,intervalName,intervalNumber,geneHugoSymbol,NodeType.ORIGINAL);
				tree.intervalTreeInsert(tree, node);
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tree;
	}
		
		
	
	public void searchUcscRefSeqGenes(ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree ucscRefSeqGenesIntervalTree, BufferedWriter bufferedWriter){
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		//Keeps the overlapping node list for the current query
		List<IntervalTreeNode> overlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the latest non empty overlapping node list for the previous queries
		List<IntervalTreeNode> previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the left most node 
		IntervalTreeNode previousLeftMostNode = new IntervalTreeNode();

		IntervalTreeNode newSearchStartingNode= ucscRefSeqGenesIntervalTree.getRoot();

		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
				
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();	
				
				//Empty the overlapping node list for the new query
				overlappingNodeList.clear();	
				
				
				if (previousLeftMostNode.getNodeName().isNotSentinel()){
				
					//Go up in the interval tree for the new query
					newSearchStartingNode = IntervalTree.findMostGeneralSearchStaringNodeforNewQuery(interval,previousLeftMostNode);
					
					//Go down in the interval tree for the new query 
					newSearchStartingNode = IntervalTree.findMostSpecificSearchStaringNodeforNewQuery(interval,newSearchStartingNode);	
				}
				
				
				//If sentinel means that there is no need to search for this new query
				if(newSearchStartingNode.getNodeName().isNotSentinel()){
					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervals(newSearchStartingNode,interval,bufferedWriter,overlappingNodeList);
				}			
				
				if(!overlappingNodeList.isEmpty()){
					previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>(overlappingNodeList);
					previousLeftMostNode =IntervalTree.findLeftMostNodefromPreviousQuery(previousNonEmptyOverlappingNodeList);			
				}
	
				
//				if(ucscRefSeqGenesIntervalTree.getRoot().isNotSentinel()){
//					ucscRefSeqGenesIntervalTree.findAllOverlappingUcscRefSeqGenesIntervals(ucscRefSeqGenesIntervalTree.getRoot(),interval,bufferedWriter);
//				}
								
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
	}
	
	public void searchHistone(ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree histoneIntervalTree, BufferedWriter bufferedWriter){
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		//Keeps the overlapping node list for the current query
		List<IntervalTreeNode> overlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the latest non empty overlapping node list for the previous queries
		List<IntervalTreeNode> previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the left most node 
		IntervalTreeNode previousLeftMostNode = new IntervalTreeNode();

		IntervalTreeNode newSearchStartingNode= histoneIntervalTree.getRoot();
			
		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
				
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();		
				
				//Empty the overlapping node list for the new query
				overlappingNodeList.clear();	
				
				if (previousLeftMostNode.getNodeName().isNotSentinel()){
				
					//Go up in the interval tree for the new query
					newSearchStartingNode = IntervalTree.findMostGeneralSearchStaringNodeforNewQuery(interval,previousLeftMostNode);
					
					//Go down in the interval tree for the new query 
					newSearchStartingNode = IntervalTree.findMostSpecificSearchStaringNodeforNewQuery(interval,newSearchStartingNode);	
				}
				
				
				
				//If sentinel means that there is no need to search for this new query
				if(newSearchStartingNode.getNodeName().isNotSentinel()){
					histoneIntervalTree.findAllOverlappingHistoneIntervals(newSearchStartingNode,interval,bufferedWriter,overlappingNodeList);
				}			
				
				if(!overlappingNodeList.isEmpty()){
					previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>(overlappingNodeList);
					previousLeftMostNode = IntervalTree.findLeftMostNodefromPreviousQuery(previousNonEmptyOverlappingNodeList);			
				}
	
			
				
//				if(histoneIntervalTree.getRoot().isNotSentinel()){
//					histoneIntervalTree.findAllOverlappingHistoneIntervals(histoneIntervalTree.getRoot(),interval,bufferedWriter);
//				}
			
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
	}
	
	
	public void searchTfbs(ChromosomeName chromName, BufferedReader bufferedReader, IntervalTree tfbsIntervalTree, BufferedWriter bufferedWriter){
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		//Keeps the overlapping node list for the current query
		List<IntervalTreeNode> overlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the latest non empty overlapping node list for the previous queries
		List<IntervalTreeNode> previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the left most node 
		IntervalTreeNode previousLeftMostNode = new IntervalTreeNode();

		IntervalTreeNode newSearchStartingNode=tfbsIntervalTree.getRoot();
		
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;
				
				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();		
				
				//Empty the overlapping node list for the new query
				overlappingNodeList.clear();	
				
				
				if (previousLeftMostNode.getNodeName().isNotSentinel()){
				
					//Go up in the interval tree for the new query
					newSearchStartingNode = IntervalTree.findMostGeneralSearchStaringNodeforNewQuery(interval,previousLeftMostNode);
					
					//Go down in the interval tree for the new query 
					newSearchStartingNode = IntervalTree.findMostSpecificSearchStaringNodeforNewQuery(interval,newSearchStartingNode);	
				}
				
				
				
				//If sentinel means that there is no need to search for this new query
				if(newSearchStartingNode.getNodeName().isNotSentinel()){
					tfbsIntervalTree.findAllOverlappingTfbsIntervals(newSearchStartingNode,interval,bufferedWriter,overlappingNodeList);
				}			
				
				if(!overlappingNodeList.isEmpty()){
					previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>(overlappingNodeList);
					
					previousLeftMostNode=IntervalTree.findLeftMostNodefromPreviousQuery(previousNonEmptyOverlappingNodeList);			
				}
				
					
//				if(tfbsIntervalTree.getRoot().isNotSentinel()){
//					tfbsIntervalTree.findAllOverlappingTfbsIntervals(tfbsIntervalTree.getRoot(),interval,bufferedWriter);
//				}
		
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
	}
	
	
	public void searchDnase(ChromosomeName chromName,BufferedReader bufferedReader, IntervalTree dnaseIntervalTree, BufferedWriter bufferedWriter){
		String strLine = null;
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		
		int low;
		int high;
		
		//Keeps the overlapping node list for the current query
		List<IntervalTreeNode> overlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the latest non empty overlapping node list for the previous queries
		List<IntervalTreeNode> previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>();
		//Keeps the left most node 
		IntervalTreeNode previousLeftMostNode = new IntervalTreeNode();
		
			
		IntervalTreeNode newSearchStartingNode= dnaseIntervalTree.getRoot();
		
		try {
			while((strLine = bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
				
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
//				indexofSecondTab must be greater than zero if it exists since indexofFirstTab must exists and can be at least zero therefore indexofSecondTab can be at least one.
				if (indexofSecondTab>0)
					high = Integer.parseInt(strLine.substring(indexofSecondTab+1));
				else 
					high = low;

				Interval interval = new Interval(low,high);
				bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + low + "\t" + high + "\n");
				bufferedWriter.flush();	
				
				//Empty the overlapping node list for the new query
				overlappingNodeList.clear();	
				
				if (previousLeftMostNode.getNodeName().isNotSentinel()){
				
					//Go up in the interval tree for the new query
					newSearchStartingNode = IntervalTree.findMostGeneralSearchStaringNodeforNewQuery(interval,previousLeftMostNode);
					
					//Go down in the interval tree for the new query 
					newSearchStartingNode = IntervalTree.findMostSpecificSearchStaringNodeforNewQuery(interval,newSearchStartingNode);	
				}
				
				
				//If sentinel means that there is no need to search for this new query
				if(newSearchStartingNode.getNodeName().isNotSentinel()){
					dnaseIntervalTree.findAllOverlappingDnaseIntervals(newSearchStartingNode,interval,bufferedWriter,overlappingNodeList);
				}			
				
				if(!overlappingNodeList.isEmpty()){
					previousNonEmptyOverlappingNodeList = new ArrayList<IntervalTreeNode>(overlappingNodeList);
					previousLeftMostNode = IntervalTree.findLeftMostNodefromPreviousQuery(previousNonEmptyOverlappingNodeList);			
				}
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} // End of while 
			
	}
	


	
	
	public void writeChromBaseSearchInputFile(ChromosomeName chromName, String strLine, List<BufferedWriter> bufList){
		try {
			
			if (chromName.isCHROMOSOME1()){
					bufList.get(0).write(strLine + "\n");
					bufList.get(0).flush();		
			} else 	if (chromName.isCHROMOSOME2()){
				bufList.get(1).write(strLine + "\n");
				bufList.get(1).flush();		
			}else 	if (chromName.isCHROMOSOME3()){
				bufList.get(2).write(strLine + "\n");
				bufList.get(2).flush();		
			}else 	if (chromName.isCHROMOSOME4()){
				bufList.get(3).write(strLine + "\n");
				bufList.get(3).flush();		
			}else 	if (chromName.isCHROMOSOME5()){
				bufList.get(4).write(strLine + "\n");
				bufList.get(4).flush();		
			}else 	if (chromName.isCHROMOSOME6()){
				bufList.get(5).write(strLine + "\n");
				bufList.get(5).flush();		
			}else 	if (chromName.isCHROMOSOME7()){
				bufList.get(6).write(strLine + "\n");
				bufList.get(6).flush();		
			}else 	if (chromName.isCHROMOSOME8()){
				bufList.get(7).write(strLine + "\n");
				bufList.get(7).flush();		
			}else 	if (chromName.isCHROMOSOME9()){
				bufList.get(8).write(strLine + "\n");
				bufList.get(8).flush();		
			}else 	if (chromName.isCHROMOSOME10()){
				bufList.get(9).write(strLine + "\n");
				bufList.get(9).flush();		
			}else 	if (chromName.isCHROMOSOME11()){
				bufList.get(10).write(strLine + "\n");
				bufList.get(10).flush();		
			}else 	if (chromName.isCHROMOSOME12()){
				bufList.get(11).write(strLine + "\n");
				bufList.get(11).flush();		
			}else 	if (chromName.isCHROMOSOME13()){
				bufList.get(12).write(strLine + "\n");
				bufList.get(12).flush();		
			}else 	if (chromName.isCHROMOSOME14()){
				bufList.get(13).write(strLine + "\n");
				bufList.get(13).flush();		
			}else 	if (chromName.isCHROMOSOME15()){
				bufList.get(14).write(strLine + "\n");
				bufList.get(14).flush();		
			}else 	if (chromName.isCHROMOSOME16()){
				bufList.get(15).write(strLine + "\n");
				bufList.get(15).flush();		
			}else 	if (chromName.isCHROMOSOME17()){
				bufList.get(16).write(strLine + "\n");
				bufList.get(16).flush();		
			}else 	if (chromName.isCHROMOSOME18()){
				bufList.get(17).write(strLine + "\n");
				bufList.get(17).flush();		
			}else 	if (chromName.isCHROMOSOME19()){
				bufList.get(18).write(strLine + "\n");
				bufList.get(18).flush();		
			}else 	if (chromName.isCHROMOSOME20()){
				bufList.get(19).write(strLine + "\n");
				bufList.get(19).flush();		
			}else 	if (chromName.isCHROMOSOME21()){
				bufList.get(20).write(strLine + "\n");
				bufList.get(20).flush();		
			}else 	if (chromName.isCHROMOSOME22()){
				bufList.get(21).write(strLine + "\n");
				bufList.get(21).flush();		
			}else 	if (chromName.isCHROMOSOMEX()){
				bufList.get(22).write(strLine + "\n");
				bufList.get(22).flush();		
			}else 	if (chromName.isCHROMOSOMEY()){
				bufList.get(23).write(strLine + "\n");
				bufList.get(23).flush();		
			}else{
				GlanetRunner.appendLog("Unknown chromosome");
			}

		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void partitionSearchInputFilePerChromName(String inputFileName, List<BufferedWriter> bufferedWriterList){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		int indexofFirstTab;
		ChromosomeName chromName;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				
				indexofFirstTab = strLine.indexOf('\t');
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
				writeChromBaseSearchInputFile(chromName,strLine,bufferedWriterList);
				
			} // End of While
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public void createChromBaseSeachInputFilesBufferedWriters(List<BufferedWriter> bufList){
		
		try {
			FileWriter fileWriter1 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt" );
			FileWriter fileWriter2 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt" );
			FileWriter fileWriter3 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt" );
			FileWriter fileWriter4 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt" );
			FileWriter fileWriter5 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt" );
			FileWriter fileWriter6 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt" );
			FileWriter fileWriter7 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt" );
			FileWriter fileWriter8 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt" );
			FileWriter fileWriter9 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt" );
			FileWriter fileWriter10 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt" );
			FileWriter fileWriter11 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt" );
			FileWriter fileWriter12 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt" );
			FileWriter fileWriter13 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt" );
			FileWriter fileWriter14 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt" );
			FileWriter fileWriter15 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt" );
			FileWriter fileWriter16 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt" );
			FileWriter fileWriter17 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt" );
			FileWriter fileWriter18 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt" );
			FileWriter fileWriter19 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt" );
			FileWriter fileWriter20 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt" );
			FileWriter fileWriter21 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt" );
			FileWriter fileWriter22 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt" );
			FileWriter fileWriter23 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt" );
			FileWriter fileWriter24 = new FileWriter(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt" );
			
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
			BufferedWriter bufferedWriter4 = new BufferedWriter(fileWriter4);
			BufferedWriter bufferedWriter5 = new BufferedWriter(fileWriter5);
			BufferedWriter bufferedWriter6 = new BufferedWriter(fileWriter6);
			BufferedWriter bufferedWriter7 = new BufferedWriter(fileWriter7);
			BufferedWriter bufferedWriter8 = new BufferedWriter(fileWriter8);
			BufferedWriter bufferedWriter9 = new BufferedWriter(fileWriter9);
			BufferedWriter bufferedWriter10 = new BufferedWriter(fileWriter10);
			BufferedWriter bufferedWriter11 = new BufferedWriter(fileWriter11);
			BufferedWriter bufferedWriter12 = new BufferedWriter(fileWriter12);
			BufferedWriter bufferedWriter13 = new BufferedWriter(fileWriter13);
			BufferedWriter bufferedWriter14 = new BufferedWriter(fileWriter14);
			BufferedWriter bufferedWriter15 = new BufferedWriter(fileWriter15);
			BufferedWriter bufferedWriter16 = new BufferedWriter(fileWriter16);
			BufferedWriter bufferedWriter17 = new BufferedWriter(fileWriter17);
			BufferedWriter bufferedWriter18 = new BufferedWriter(fileWriter18);
			BufferedWriter bufferedWriter19 = new BufferedWriter(fileWriter19);
			BufferedWriter bufferedWriter20 = new BufferedWriter(fileWriter20);
			BufferedWriter bufferedWriter21 = new BufferedWriter(fileWriter21);
			BufferedWriter bufferedWriter22 = new BufferedWriter(fileWriter22);
			BufferedWriter bufferedWriter23 = new BufferedWriter(fileWriter23);
			BufferedWriter bufferedWriter24 = new BufferedWriter(fileWriter24);
			
			bufList.add(bufferedWriter1);
			bufList.add(bufferedWriter2);
			bufList.add(bufferedWriter3);
			bufList.add(bufferedWriter4);
			bufList.add(bufferedWriter5);
			bufList.add(bufferedWriter6);
			bufList.add(bufferedWriter7);
			bufList.add(bufferedWriter8);
			bufList.add(bufferedWriter9);
			bufList.add(bufferedWriter10);
			bufList.add(bufferedWriter11);
			bufList.add(bufferedWriter12);
			bufList.add(bufferedWriter13);
			bufList.add(bufferedWriter14);
			bufList.add(bufferedWriter15);
			bufList.add(bufferedWriter16);
			bufList.add(bufferedWriter17);
			bufList.add(bufferedWriter18);
			bufList.add(bufferedWriter19);
			bufList.add(bufferedWriter20);
			bufList.add(bufferedWriter21);
			bufList.add(bufferedWriter22);
			bufList.add(bufferedWriter23);
			bufList.add(bufferedWriter24);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public IntervalTree createUcscRefSeqGenesIntervalTree(String outputFolder,ChromosomeName chromName){
		IntervalTree  ucscRefSeqGenesIntervalTree =null;
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		
		fileReader = ChromosomeBasedFilesandOperations.getUnsortedRefSeqGenesFileReader(outputFolder,chromName);
		bufferedReader = new BufferedReader(fileReader);
		ucscRefSeqGenesIntervalTree = generateUcscRefSeqGenesIntervalTree(bufferedReader);
			
			
				
		
		return ucscRefSeqGenesIntervalTree;	
	
	}
	
	public IntervalTree createHistoneIntervalTree(ChromosomeName chromName){
		IntervalTree  histoneIntervalTree =null;
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		
		try {			
			if (ChromosomeName.CHROMOSOME1.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR1_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME2.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR2_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME3.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR3_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME4.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR4_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME5.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR5_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME6.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR6_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME7.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR7_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME8.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR8_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME9.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR9_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME10.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR10_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME11.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR11_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME12.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR12_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME13.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR13_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME14.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR14_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME15.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR15_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME16.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR16_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME17.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR17_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME18.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR18_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME19.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR19_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME20.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR20_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME21.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR21_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME22.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHR22_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOMEX.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHRX_HISTONE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOMEY.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_HISTONE_DIRECTORY, Commons.UNSORTED_CHRY_HISTONE_FILENAME);				
			} 
		
			bufferedReader = new BufferedReader(fileReader);
			histoneIntervalTree = generateEncodeHistoneIntervalTree(bufferedReader);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return histoneIntervalTree;	
	}
	
	public IntervalTree createTfbsIntervalTree(ChromosomeName chromName){
		IntervalTree  tfbsIntervalTree =null;
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		
		try {			
			if (ChromosomeName.CHROMOSOME1.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR1_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME2.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR2_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME3.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR3_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME4.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR4_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME5.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR5_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME6.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR6_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME7.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR7_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME8.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR8_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME9.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR9_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME10.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR10_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME11.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR11_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME12.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR12_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME13.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR13_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME14.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR14_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME15.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR15_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME16.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR16_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME17.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR17_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME18.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR18_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME19.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR19_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME20.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR20_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME21.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR21_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME22.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHR22_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOMEX.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRX_TFBS_FILENAME);				
			} else if (ChromosomeName.CHROMOSOMEY.equals(chromName)){
					fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_TFBS_DIRECTORY,Commons.UNSORTED_CHRY_TFBS_FILENAME);				
			} 
		
			bufferedReader = new BufferedReader(fileReader);
			tfbsIntervalTree = generateEncodeTfbsIntervalTree(bufferedReader);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return tfbsIntervalTree;	
	}
	
	public IntervalTree createDnaseIntervalTree(ChromosomeName chromName){
		IntervalTree  dnaseIntervalTree =null;
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		
		try {			
			if (ChromosomeName.CHROMOSOME1.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR1_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME2.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR2_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME3.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR3_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME4.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR4_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME5.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR5_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME6.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR6_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME7.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR7_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME8.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR8_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME9.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR9_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME10.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR10_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME11.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR11_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME12.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR12_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME13.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR13_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME14.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR14_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME15.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR15_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME16.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR16_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME17.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR17_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME18.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR18_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME19.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR19_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME20.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR20_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME21.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR21_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOME22.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHR22_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOMEX.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRX_DNASE_FILENAME);				
			} else if (ChromosomeName.CHROMOSOMEY.equals(chromName)){
				fileReader = FileOperations.createFileReader(Commons.CREATE_ENCODE_DNASE_DIRECTORY,Commons.UNSORTED_CHRY_DNASE_FILENAME);				
			} 
		
			bufferedReader = new BufferedReader(fileReader);
			dnaseIntervalTree = generateEncodeDnaseIntervalTree(bufferedReader);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dnaseIntervalTree;
	}
	
	
	public BufferedReader createBufferedReader(String fileName){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bufferedReader;
	}
	
	public void searchEachChromBaseInputFile( String outputFolder,String outputFileName){
		
		BufferedReader bufferedReader =null ;
		
		IntervalTree dnaseIntervalTree;
		IntervalTree histoneIntervalTree;
		IntervalTree tfbsIntervalTree;
		IntervalTree ucscRefSeqGenesIntervalTree;				
		
		FileWriter fileWriter;
		BufferedWriter bufferedWriter = null;
		
		try {
			fileWriter = new FileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 1; i<=24 ; i++ ){
			
			switch(i){
			case 1: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME1);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME1,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME1);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME1,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;

					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME1);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME1,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME1);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr1_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME1,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
			case 2: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME2);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME2,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME2);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME2,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
	
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME2);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME2,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME2);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr2_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME2,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
					
			case 3: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME3);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME3,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME3);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME3,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
	
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME3);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME3,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME3);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr3_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME3,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
					
			case 4: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME4);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME4,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME4);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME4,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
	
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME4);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME4,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME4);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr4_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME4,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
				
			case 5: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME5);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME5,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME5);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME5,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
	
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME5);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME5,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME5);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr5_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME5,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
		
		case 6: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME6);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME6,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME6);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME6,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
	
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME6);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME6,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME6);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr6_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME6,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
					
		case 7: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME7);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME7,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME7);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME7,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME7);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME7,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME7);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr7_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME7,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;


		case 8: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME8);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME8,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME8);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME8,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME8);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME8,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME8);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr8_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME8,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;

		case 9: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME9);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME9,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME9);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME9,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME9);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME9,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME9);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr9_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME9,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
					
		case 10: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME10);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME10,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME10);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME10,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME10);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME10,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME10);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr10_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME10,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
					
		case 11: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME11);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME11,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME11);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME11,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME11);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME11,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME11);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr11_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME11,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;

		case 12: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME12);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME12,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME12);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME12,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME12);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME12,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME12);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr12_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME12,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
		case 13: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME13);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME13,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME13);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME13,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME13);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME13,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME13);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr13_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME13,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
				
		case 14: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME14);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME14,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME14);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME14,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME14);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME14,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME14);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr14_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME14,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
					
		case 15: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME15);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME15,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME15);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME15,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME15);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME15,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME15);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr15_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME15,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
						
		case 16: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME16);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME16,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME16);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME16,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME16);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME16,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME16);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr16_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME16,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
							
		case 17: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME17);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME17,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME17);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME17,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME17);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME17,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME17);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr17_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME17,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
								
		case 18: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME18);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME18,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME18);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME18,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME18);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME18,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME18);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr18_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME18,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
									
		case 19: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME19);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME19,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME19);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME19,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME19);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME19,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME19);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr19_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME19,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
										
		case 20: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME20);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME20,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME20);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME20,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME20);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME20,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME20);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr20_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME20,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
											
		case 21: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME21);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME21,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME21);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME21,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME21);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME21,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME21);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr21_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME21,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
												
		case 22: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOME22);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOME22,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOME22);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOME22,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOME22);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOME22,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOME22);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chr22_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOME22,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;		
													
		case 23: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOMEX);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOMEX,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOMEX);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOMEX,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOMEX);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOMEX,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOMEX);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrX_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOMEX,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;
					
		case 24: 			
					dnaseIntervalTree = createDnaseIntervalTree(ChromosomeName.CHROMOSOMEY);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
					searchDnase(ChromosomeName.CHROMOSOMEY,bufferedReader, dnaseIntervalTree, bufferedWriter);
					dnaseIntervalTree = null;					
					
					tfbsIntervalTree = createTfbsIntervalTree(ChromosomeName.CHROMOSOMEY);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
					searchTfbs(ChromosomeName.CHROMOSOMEY,bufferedReader, tfbsIntervalTree, bufferedWriter);
					tfbsIntervalTree = null;
		
					histoneIntervalTree = createHistoneIntervalTree(ChromosomeName.CHROMOSOMEY);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
					searchHistone(ChromosomeName.CHROMOSOMEY,bufferedReader, histoneIntervalTree, bufferedWriter);
					histoneIntervalTree = null;
					
					ucscRefSeqGenesIntervalTree = createUcscRefSeqGenesIntervalTree(outputFolder,ChromosomeName.CHROMOSOMEY);
					bufferedReader = createBufferedReader(Commons.SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY + "search_chrY_input_file.txt");
					searchUcscRefSeqGenes(ChromosomeName.CHROMOSOMEY,bufferedReader, ucscRefSeqGenesIntervalTree, bufferedWriter);
					ucscRefSeqGenesIntervalTree = null;					
					break;					

		}// End of Switch 
						
	}// End of For
		
	try {
		bufferedReader.close();
		bufferedWriter.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}
	
	
	public void closeBufferedWriterList(List<BufferedWriter> bufList){
		for(int i = 0; i<bufList.size(); i++){
			try {
				bufList.get(i).close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
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
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
	
		
		List<BufferedWriter> bufferedWriterList = new ArrayList<BufferedWriter>();
		
		
		SearchChromosomeIntervalsUsingIntervalTree searchInterval = new SearchChromosomeIntervalsUsingIntervalTree();		
		
		searchInterval.createChromBaseSeachInputFilesBufferedWriters(bufferedWriterList);		
//		searchInterval.partitionSearchInputFilePerChromName(Commons.OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS,bufferedWriterList);
//		searchInterval.partitionSearchInputFilePerChromName(Commons.POSITIVE_CONTROL_OUTPUT_FILE_NAME_WITHOUT_OVERLAPS,bufferedWriterList);
		
		
		searchInterval.closeBufferedWriterList(bufferedWriterList);
		
		searchInterval.searchEachChromBaseInputFile(outputFolder,Commons.SEARCH_USING_INTERVAL_TREE_OUTPUT_FILE);
	}
	
	
}
