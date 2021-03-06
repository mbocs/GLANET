/*
 * CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingIntervalTree.java 
 * 
 * calculates the number of non overlapping base pairs in exon based manner for each kegg pathway in whole genome and
 * calculates the number of non overlapping base pairs in regulation based manner for each kegg pathway in whole genome.
 * calculates the number of non overlapping base pairs in dnase intervals in whole genome 
 * calculates the number of non overlapping base pairs in tfbs intervals in whole genome 
 * calculates the number of non overlapping base pairs in histone intervals in whole genome 
 * 
 * exon based calculation means the number of non overlapping base pairs in exons of genes of a given kegg pathway
 * regulation based calculation means the number of non overlapping base pairs in introns, 5p1, 5p2, 3p1 and 3p2 of a given kegg pathway.
 * 
 * This class uses interval tree for the calculation of number of non overlapping base pairs.
 * 
 */
package wholegenome.nonoverlappingbasepairs.usingintervaltree;

import intervaltree.DnaseIntervalTreeNode;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import keggpathway.ncbigenes.KeggPathwayUtility;
import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.IntervalName;
import enumtypes.NodeType;

public class CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingIntervalTree {
	
	public void updateMergedNode(IntervalTreeNode mergedNode, IntervalTreeNode overlappedNode){
		if (overlappedNode.getLow()<mergedNode.getLow()){
			mergedNode.setLow(overlappedNode.getLow());			
		}
			
		
		if (overlappedNode.getHigh()>mergedNode.getHigh()){
			mergedNode.setHigh(overlappedNode.getHigh());
		}
		
		mergedNode.setNumberofBases(mergedNode.getHigh()-mergedNode.getLow()+1);
	}
	
	

	
	//Calculate the number of non overlapping base pairs in the introns, 5p1, 5p2, 3p1 and 3p2 of genes of kegg pathways
	//create keggPathway2IntervalTreeHashMap for each chromosome
	//accumulate the results in regulationBasedkeggPathway2NumberofBasePairsHashMap
	public void calculateforEachChromosomeRegulationBasedKeggPathway(String outputFolder,Map<String,Long> regulationBasedkeggPathway2NumberofBasePairsHashMap,Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap,String unsortedChromosomeBasedInputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;		
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		int indexofSixthTab =0;
		int indexofSeventhTab =0;
		int indexofEighthTab = 0;
		
		ChromosomeName chromName;
		int low;
		int high;
		String refSeqGeneName;		
		String ncbiGeneId;
		Integer geneEntrezId;		
		IntervalName intervalName;
		int intervalNumber;
		String geneHugoSymbol;
		
		//Initialize this for each chromosome
		Map<String,IntervalTree> keggPathway2IntervalTreeHashMap = new HashMap<String,IntervalTree>();
		IntervalTree existingIntervalTree = null;
		//update existingNumberofBasePairs after each insert whether there is any overlap or not
		
		try {
			fileReader = new FileReader(outputFolder + unsortedChromosomeBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example strLine
				//chrY	16636453	16636816	NR_028319	22829	Exon1	+	NLGN4Y
				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);				
				indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
				indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
				indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
				indexofEighthTab	= strLine.indexOf('\t',indexofSeventhTab+1);
					
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));	
				refSeqGeneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				ncbiGeneId = strLine.substring(indexofFourthTab+1, indexofFifthTab);
				geneEntrezId = Integer.parseInt(ncbiGeneId);
				intervalName = IntervalName.convertStringtoEnum(strLine.substring(indexofFifthTab+1, indexofSixthTab));
				intervalNumber = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
				geneHugoSymbol =strLine.substring(indexofEighthTab+1);
				
				//Fill the regulation based keggPathway2IntervalTreeHashMap
				if (intervalName.isIntron() ||
						intervalName.isFivePOne() ||
						intervalName.isFivePTwo()||
						intervalName.isThreePOne() ||
						intervalName.isThreePTwo()){
					
					//Get the list of kegg pathway names contain this ncbi gene id
					List<String> keggPathwayNameListContainingThisNcbiGeneId = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
					
					
					//Add this strLine as an intervalTreeNode to the corresponding interval tree of kegg Pathway
					if (keggPathwayNameListContainingThisNcbiGeneId!=null){
						for(int i=0; i<keggPathwayNameListContainingThisNcbiGeneId.size() ; i++){
							
							//create intervalTreeNode
							UcscRefSeqGeneIntervalTreeNode intervalTreeNode = new UcscRefSeqGeneIntervalTreeNode(chromName,low,high,refSeqGeneName,geneEntrezId,intervalName,intervalNumber,geneHugoSymbol,NodeType.ORIGINAL);
		
							String keggPathwayName = keggPathwayNameListContainingThisNcbiGeneId.get(i);
							//Get the interval tree for this Kegg Pathway Name

							//Get the interval tree for this kegg  pathway							
							//For first insert of each kegg pathway name and chromosome number
							//keggPathway2IntervalTreeHashMap does not contain this kegg pathway name
							if (keggPathway2IntervalTreeHashMap.get(keggPathwayName)==null){
								IntervalTree intervalTree = new IntervalTree();							
								intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);							
								keggPathway2IntervalTreeHashMap.put(keggPathwayName, intervalTree);
								
							}
							
							//For all inserts but the first of kegg pathway name and chromosome number
							//keggPathway2IntervalTreeHashMap already contains this kegg pathway name
							else{
								//get existing interval tree for this chromosome and kegg pathway name
								existingIntervalTree = keggPathway2IntervalTreeHashMap.get(keggPathwayName);
																			
								List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
								
								existingIntervalTree.findAllOverlappingIntervals(overlappedNodeList,existingIntervalTree.getRoot(), intervalTreeNode);
								
								//there is overlap
								if (overlappedNodeList!= null && overlappedNodeList.size()>0){
										
									IntervalTreeNode mergedNode = new UcscRefSeqGeneIntervalTreeNode(intervalTreeNode.getChromName(),intervalTreeNode.getLow(), intervalTreeNode.getHigh(),intervalTreeNode.getRefSeqGeneName(),intervalTreeNode.getGeneEntrezId(),intervalTreeNode.getIntervalName(),intervalTreeNode.getIntervalNumber(), intervalTreeNode.getGeneHugoSymbol(),NodeType.MERGED);
									IntervalTreeNode splicedoutNode = null;
									IntervalTreeNode nodetoBeDeleted =null;	
									//you may try to delete a node which is already spliced out by former deletions
									//therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
									Map<IntervalTreeNode,IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();
									
									for(int j= 0; j<overlappedNodeList.size(); j++){
											
										IntervalTreeNode overlappedNode = overlappedNodeList.get(j);
											
										updateMergedNode(mergedNode, overlappedNode);
										
										//if the to be deleted, intended interval tree node is an already spliced out node
										//in other words if it is copied into another interval tree node
										//then you have to delete that node
										//not the already spliced out node
										
										nodetoBeDeleted =compute(splicedoutNode2CopiedNodeMap,overlappedNode) ;
										
										if 	(nodetoBeDeleted!=null){
												//they are the same
												//current overlapped node has been copied to the previously deleted overlapped node
												//current overlapped node is spliced out by the previous delete operation
												//so delete that previously deleted overlapped node in order to delete the current overlapped node
												//since current overlapped node is copied to the previously deleted overlapped node
												//Now we can delete this overlappedNode
												splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, nodetoBeDeleted);
												
												if(splicedoutNode!=nodetoBeDeleted)
													splicedoutNode2CopiedNodeMap.put(splicedoutNode,nodetoBeDeleted);	
										}else{							
											//Now we can delete this overlappedNode
											splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, overlappedNode);
											
											if (splicedoutNode!=overlappedNode)
												splicedoutNode2CopiedNodeMap.put(splicedoutNode,overlappedNode);
									
										}
										
									}//end of for: each overlapped node in the list
									
									existingIntervalTree.intervalTreeInsert(existingIntervalTree, mergedNode);
									
								}
								//there is no overlap
								else{
															
									//insert interval
									existingIntervalTree.intervalTreeInsert(existingIntervalTree, intervalTreeNode);
								}
								
								keggPathway2IntervalTreeHashMap.put(keggPathwayName, existingIntervalTree);
								
							
							}//end of else:keggPathway2IntervalTreeHashMap contains this kegg pathway name

							
							
						}// End of For: add this interval to interval tree of each kegg pathway containing this intron, 5p1, 5p2, 3p1, 3p2 of gene
					}//End of If: keggPathwayNameListContainingThisNcbiGeneId is not null.
				
				}//End of If: read interval if it is intron, 5p1, 5p2, 3p1 or 3p2
			}//End of While: read each line 
			
			//for this chromosome and all kegg pathway names, the interval trees are constructed.
			//now accumulate the number of non overlapping base pairs in keggPathway2IntervalTreeHashMap into regulationBasedkeggPathway2NumberofBasePairsHashMap
			accumulateNumberofNonOverlappingBasePairs(keggPathway2IntervalTreeHashMap,regulationBasedkeggPathway2NumberofBasePairsHashMap);
		
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;

	}
	
	public IntervalTreeNode compute(Map<IntervalTreeNode,IntervalTreeNode> splicedNode2CopiedNodeMap,IntervalTreeNode overlappedNode){
		IntervalTreeNode node = splicedNode2CopiedNodeMap.get(overlappedNode);
		IntervalTreeNode savedPreviousNode = null;
		
		while(node!=null){
			savedPreviousNode = node;
			node = splicedNode2CopiedNodeMap.get(node);
		}
	
		return savedPreviousNode;
	}
	
	//for debugging purposes
	//do not delete
	public void checkforanyOverlapsintheIntervalTree(IntervalTree intervalTree,IntervalTreeNode node){
		List<IntervalTreeNode> overlappedNodeList = new ArrayList<IntervalTreeNode>();
		
		
		if (node.getNodeName().isNotSentinel()){
			
				intervalTree.findAllOverlappingIntervals(overlappedNodeList,intervalTree.getRoot(), node);
				if (overlappedNodeList.contains(node)){
					overlappedNodeList.remove(node);
				}
				
				if (overlappedNodeList.size()>0){
					GlanetRunner.appendLog("Overlap");
				}
			
		}
		
		if (node.getLeft().getNodeName().isNotSentinel())
			checkforanyOverlapsintheIntervalTree(intervalTree, node.getLeft());
		
		if(node.getRight().getNodeName().isNotSentinel())
			checkforanyOverlapsintheIntervalTree(intervalTree, node.getRight());
	}
	
	
	
	//for debugging purposes
	//do not delete
	public void checkforMaxAttributeofNodesintheIntervalTree(IntervalTree intervalTree,IntervalTreeNode node){
		
		
		if (node.getNodeName().isNotSentinel()){
			if (node.getMax()!=IntervalTree.max(node)){
				GlanetRunner.appendLog("There is a wrongly set max attribute!");
			}
			
			
		}
		
		if (node.getLeft().getNodeName().isNotSentinel())
			checkforMaxAttributeofNodesintheIntervalTree(intervalTree, node.getLeft());
		
		if(node.getRight().getNodeName().isNotSentinel())
			checkforMaxAttributeofNodesintheIntervalTree(intervalTree, node.getRight());
	}
	
	public void checkforanyOverlaps(Map<String,IntervalTree> name2IntervalTreeHashMap,String name){
		IntervalTree intervalTree = null;
		
		if (name!= null){
			intervalTree = name2IntervalTreeHashMap.get(name);
			
			if (intervalTree!=null){
				IntervalTreeNode root = intervalTree.getRoot();
				
				//for every node in the intervalTree
				//check if there is any overlap with any other node in the intervalTree 
				checkforanyOverlapsintheIntervalTree(intervalTree,root);
								
			}
			
		}
	}
	
	
	public void accumulateNumberofNonOverlappingBasePairs(Map<String,IntervalTree> name2IntervalTreeHashMap,Map<String,Long> name2NumberofNonOverlappingBasePairsHashMap){
		for (Map.Entry<String, IntervalTree> entry: name2IntervalTreeHashMap.entrySet()){
			String name = entry.getKey();
			IntervalTree intervalTree = entry.getValue();
			
			if (name2NumberofNonOverlappingBasePairsHashMap.get(name)==null){
				name2NumberofNonOverlappingBasePairsHashMap.put(name, intervalTree.getNumberofNonOverlappingBases());
			}else{
				name2NumberofNonOverlappingBasePairsHashMap.put(name, name2NumberofNonOverlappingBasePairsHashMap.get(name)+intervalTree.getNumberofNonOverlappingBases());
			}
		}
		
	}
	
	
	//Every read line's data as an interval will be inserted to the interval tree of related chromosome and dnase cell line name.
	//For each chromosome number and dnase cell line name dnaseCellLine2IntervalTreeHashMap will be initialized.
	//However dnaseCellLine2NumberofNonOverlappingBasePairsHashMap will be accumulation point for all chromosomes.	
	public void calculateforEachChromosomeDnaseCellLine(String outputFolder,Map<String,Long> dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, String unsortedChromosomeBasedInputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;		
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
					
		String chromName;
		int low;
		int high;
		String cellLineName;		
		String fileName;
		
		//Initialize this for each chromosome
		//So there will be an interval tree for each chromosome number and dnase cell line name 
		Map<String,IntervalTree> dnaseCellLine2IntervalTreeHashMap = new HashMap<String,IntervalTree>();
		IntervalTree existingIntervalTree = null;
		
		try {
			fileReader = new FileReader(outputFolder + unsortedChromosomeBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example strLine
				//chr9	179080	179230	AG04449	AG04449-DS12319.peaks.fdr0.01.hg19.bed

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);				
					
				chromName = strLine.substring(0,indexofFirstTab);
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));	
				cellLineName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				fileName = strLine.substring(indexofFourthTab+1);								
				
				//create intervalTreeNode
				DnaseIntervalTreeNode intervalTreeNode = new DnaseIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName),low,high,cellLineName,fileName,NodeType.ORIGINAL);
		
				//Get the interval tree for this dnase Cell Line Name							
				//For first insert of each dnase cell line name and chromosome number
				//dnaseCellLine2IntervalTreeHashMap does not contain this dnase cell line name
				if (dnaseCellLine2IntervalTreeHashMap.get(cellLineName)==null){
					IntervalTree intervalTree = new IntervalTree();							
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);							
					dnaseCellLine2IntervalTreeHashMap.put(cellLineName, intervalTree);
					
				}
				
				//For all inserts but the first of each dnase cell line name and each chromosome number
				//dnaseCellLine2IntervalTreeHashMap already contains this  dnase cell line name
				else{
					//get existing interval tree for this chromosome and dnase cell line name
					existingIntervalTree = dnaseCellLine2IntervalTreeHashMap.get(cellLineName);
																
					List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
					
					existingIntervalTree.findAllOverlappingIntervals(overlappedNodeList,existingIntervalTree.getRoot(), intervalTreeNode);
					
					//there is overlap
					if (overlappedNodeList!= null && overlappedNodeList.size()>0){
							
						IntervalTreeNode mergedNode = new DnaseIntervalTreeNode(intervalTreeNode.getChromName(),intervalTreeNode.getLow(), intervalTreeNode.getHigh(),intervalTreeNode.getCellLineName(), intervalTreeNode.getFileName(),NodeType.MERGED);
						IntervalTreeNode splicedoutNode = null;
						IntervalTreeNode nodetoBeDeleted =null;	
						//you may try to delete a node which is already spliced out by former deletions
						//therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
						Map<IntervalTreeNode,IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();
						
						for(int i= 0; i<overlappedNodeList.size(); i++){
								
							IntervalTreeNode overlappedNode = overlappedNodeList.get(i);
								
							updateMergedNode(mergedNode, overlappedNode);
							
							//if the to be deleted, intended interval tree node is an already spliced out node
							//in other words if it is copied into another interval tree node
							//then you have to delete that node
							//not the already spliced out node
							
							nodetoBeDeleted =compute(splicedoutNode2CopiedNodeMap,overlappedNode) ;
							
							if 	(nodetoBeDeleted!=null){
									//they are the same
									//current overlapped node has been copied to the previously deleted overlapped node
									//current overlapped node is spliced out by the previous delete operation
									//so delete that previously deleted overlapped node in order to delete the current overlapped node
									//since current overlapped node is copied to the previously deleted overlapped node
									//Now we can delete this overlappedNode
									splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, nodetoBeDeleted);
									
									if(splicedoutNode!=nodetoBeDeleted)
										splicedoutNode2CopiedNodeMap.put(splicedoutNode,nodetoBeDeleted);	
							}else{							
								//Now we can delete this overlappedNode
								splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, overlappedNode);
								
								if (splicedoutNode!=overlappedNode)
									splicedoutNode2CopiedNodeMap.put(splicedoutNode,overlappedNode);
						
							}
							
						}//end of for: each overlapped node in the list
						
						
						existingIntervalTree.intervalTreeInsert(existingIntervalTree, mergedNode);
						
					}
					//there is no overlap
					else{
												
						//insert interval
						existingIntervalTree.intervalTreeInsert(existingIntervalTree, intervalTreeNode);
					}
					
					dnaseCellLine2IntervalTreeHashMap.put(cellLineName, existingIntervalTree);	
				
				}//end of else:dnaseCellLine2IntervalTreeHashMap contains this dnase cell line name
				
			}//End of While: read each line 
			
			//for this chromosome and all dnase cell line names, the interval trees are constructed.
			//now accumulate the number of non overlapping base pairs in dnaseCellLine2IntervalTreeHashMap into dnaseCellLine2NumberofNonOverlappingBasePairsHashMap
			accumulateNumberofNonOverlappingBasePairs(dnaseCellLine2IntervalTreeHashMap,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap);
			
			
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;

	}
	
	//Every read line's data as an interval will be inserted to the interval tree of related chromosome and tfbs name.
	//For each chromosome tfbs2IntervalTreeHashMap will be initialized.
	public void calculateforEachChromosomeTfbs(String outputFolder, Map<String,Long> tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, String unsortedChromosomeBasedInputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;		
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		
		String chromName;
		int low;
		int high;
		String tfbsName;
		String cellLineName;		
		String fileName;
		
		String tfbsNameandCellLineName;
		
		//Initialize this for each chromosome 
		Map<String,IntervalTree> tfbsNameandCellLineName2IntervalTreeHashMap = new HashMap<String,IntervalTree>();
		IntervalTree existingIntervalTree = null;
		
		
		try {
			fileReader = new FileReader(outputFolder + unsortedChromosomeBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example strLine
				//chr18	42792773	42793022	CTCF	GM12878	spp.optimal.wgEncodeBroadHistoneGm12878CtcfStdAlnRep0_VS_wgEncodeBroadHistoneGm12878ControlStdAlnRep0.narrowPeak

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);				
				indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);				
					
				chromName = strLine.substring(0,indexofFirstTab);
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				tfbsName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
				fileName = strLine.substring(indexofFifthTab+1);
				
				tfbsNameandCellLineName = tfbsName + "_" +cellLineName;
											
				//create intervalTreeNode
				TforHistoneIntervalTreeNode intervalTreeNode = new TforHistoneIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName),low,high,tfbsName,cellLineName,fileName,NodeType.ORIGINAL);
		
				//Get the interval tree for this tfbs Nameand cell line name						
				//For first insert of each tfbsNameandCellLineName and chromosome number
				//tfbsNameandCellLineName2IntervalTreeHashMap does not contain this tfbsNameandCellLineName
				if (tfbsNameandCellLineName2IntervalTreeHashMap.get(tfbsNameandCellLineName)==null){
					IntervalTree intervalTree = new IntervalTree();							
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);							
					tfbsNameandCellLineName2IntervalTreeHashMap.put(tfbsNameandCellLineName, intervalTree);
					
				}
				
				//For all inserts but the first of tfbs name and each chromosome number
				//tfbs2IntervalTreeHashMap already contains this tfbs name
				else{
					//get existing interval tree for this chromosome and tfbsNameandCellLineName
					existingIntervalTree = tfbsNameandCellLineName2IntervalTreeHashMap.get(tfbsNameandCellLineName);
																
					List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
					
					existingIntervalTree.findAllOverlappingIntervals(overlappedNodeList,existingIntervalTree.getRoot(), intervalTreeNode);
					
					//there is overlap
					if (overlappedNodeList!= null && overlappedNodeList.size()>0){
							
						IntervalTreeNode mergedNode = new TforHistoneIntervalTreeNode(intervalTreeNode.getChromName(),intervalTreeNode.getLow(), intervalTreeNode.getHigh(),intervalTreeNode.getTfbsorHistoneName(),intervalTreeNode.getCellLineName(), intervalTreeNode.getFileName(),NodeType.MERGED);
						IntervalTreeNode splicedoutNode = null;
						IntervalTreeNode nodetoBeDeleted =null;	
						//you may try to delete a node which is already spliced out by former deletions
						//therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
						Map<IntervalTreeNode,IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();
						
						for(int i= 0; i<overlappedNodeList.size(); i++){
								
							IntervalTreeNode overlappedNode = overlappedNodeList.get(i);
								
							updateMergedNode(mergedNode, overlappedNode);
							
							//if the to be deleted, intended interval tree node is an already spliced out node
							//in other words if it is copied into another interval tree node
							//then you have to delete that node
							//not the already spliced out node
							
							nodetoBeDeleted =compute(splicedoutNode2CopiedNodeMap,overlappedNode) ;
							
							if 	(nodetoBeDeleted!=null){
									//they are the same
									//current overlapped node has been copied to the previously deleted overlapped node
									//current overlapped node is spliced out by the previous delete operation
									//so delete that previously deleted overlapped node in order to delete the current overlapped node
									//since current overlapped node is copied to the previously deleted overlapped node
									//Now we can delete this overlappedNode
									splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, nodetoBeDeleted);
									
									if(splicedoutNode!=nodetoBeDeleted)
										splicedoutNode2CopiedNodeMap.put(splicedoutNode,nodetoBeDeleted);	
							}else{							
								//Now we can delete this overlappedNode
								splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, overlappedNode);
								
								if (splicedoutNode!=overlappedNode)
									splicedoutNode2CopiedNodeMap.put(splicedoutNode,overlappedNode);
						
							}
							
						}//end of for: each overlapped node in the list
						
						
						existingIntervalTree.intervalTreeInsert(existingIntervalTree, mergedNode);
						
					}
					//there is no overlap
					else{
												
						//insert interval
						existingIntervalTree.intervalTreeInsert(existingIntervalTree, intervalTreeNode);
					}
					
					tfbsNameandCellLineName2IntervalTreeHashMap.put(tfbsNameandCellLineName, existingIntervalTree);	
				
				}//end of tfbsNameandCellLineName2IntervalTreeHashMap contains this tfbsNameandCellLineName
				
			}//End of While: read each line 
			
			//for this chromosome and all tfbsNameandCellLineName, the interval trees are constructed.
			//now accumulate the number of non overlapping base pairs in tfbsNameandCellLineName2IntervalTreeHashMap into tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap
			accumulateNumberofNonOverlappingBasePairs(tfbsNameandCellLineName2IntervalTreeHashMap,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap);
			
			bufferedReader.close();
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	//Every read line's data as an interval will be inserted to the interval tree of related chromosome and histone name.
	//For each chromosome histone2IntervalTreeHashMap will be initialized.
	public void calculateforEachChromosomeHistone(String outputFolder,Map<String,Long> histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, String unsortedChromosomeBasedInputFileName){
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;		
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		
		String chromName=null;
		int low;
		int high;
		String histoneName;
		String cellLineName;		
		String fileName;
		
		String histoneNameandCellLineName;
		
		//Initialize this for each chromosome
		Map<String,IntervalTree> histoneNameandCellLineName2IntervalTreeHashMap = new HashMap<String,IntervalTree>();
		IntervalTree existingIntervalTree = null;
		
		
		try {
			fileReader = new FileReader(outputFolder + unsortedChromosomeBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example strLine
				//chr16	22199367	22204654	H2AZ	GM12878	wgEncodeBroadHistoneGm12878H2azStdAln.narrowPeak

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);				
				indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);				
					
				chromName = strLine.substring(0,indexofFirstTab);
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
				histoneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				cellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
				fileName = strLine.substring(indexofFifthTab+1);
				
				histoneNameandCellLineName = histoneName + "_" +cellLineName;
															
				//create intervalTreeNode
				TforHistoneIntervalTreeNode intervalTreeNode = new TforHistoneIntervalTreeNode(ChromosomeName.convertStringtoEnum(chromName),low,high,histoneName,cellLineName,fileName,NodeType.ORIGINAL);
		
				//Get the interval tree for this histoneNameandCellLineName							
				//For first insert of each histoneNameandCellLineName and chromosome number
				//histoneNameandCellLineName2IntervalTreeHashMap does not contain this histoneNameandCellLineName
				if (histoneNameandCellLineName2IntervalTreeHashMap.get(histoneNameandCellLineName)==null){
					IntervalTree intervalTree = new IntervalTree();							
					intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);							
					histoneNameandCellLineName2IntervalTreeHashMap.put(histoneNameandCellLineName, intervalTree);
					
				}
				
				//For all inserts but the first of histoneNameandCellLineName and each chromosome number
				//histoneNameandCellLineName2IntervalTreeHashMap already contains this histoneNameandCellLineName
				else{
					//get existing interval tree for this chromosome and histoneNameandCellLineName
					existingIntervalTree = histoneNameandCellLineName2IntervalTreeHashMap.get(histoneNameandCellLineName);
																
					List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
					
					existingIntervalTree.findAllOverlappingIntervals(overlappedNodeList,existingIntervalTree.getRoot(), intervalTreeNode);
					
					//there is overlap
					if (overlappedNodeList!= null && overlappedNodeList.size()>0){
							
						IntervalTreeNode mergedNode = new TforHistoneIntervalTreeNode(intervalTreeNode.getChromName(),intervalTreeNode.getLow(), intervalTreeNode.getHigh(),intervalTreeNode.getTfbsorHistoneName(),intervalTreeNode.getCellLineName(), intervalTreeNode.getFileName(),NodeType.MERGED);
						IntervalTreeNode splicedoutNode = null;
						IntervalTreeNode nodetoBeDeleted =null;	
						//you may try to delete a node which is already spliced out by former deletions
						//therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
						Map<IntervalTreeNode,IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();
						
						for(int i= 0; i<overlappedNodeList.size(); i++){
								
							IntervalTreeNode overlappedNode = overlappedNodeList.get(i);
								
							updateMergedNode(mergedNode, overlappedNode);
							
							//if the to be deleted, intended interval tree node is an already spliced out node
							//in other words if it is copied into another interval tree node
							//then you have to delete that node
							//not the already spliced out node
							
							nodetoBeDeleted =compute(splicedoutNode2CopiedNodeMap,overlappedNode) ;
							
							if 	(nodetoBeDeleted!=null){
									//they are the same
									//current overlapped node has been copied to the previously deleted overlapped node
									//current overlapped node is spliced out by the previous delete operation
									//so delete that previously deleted overlapped node in order to delete the current overlapped node
									//since current overlapped node is copied to the previously deleted overlapped node
									//Now we can delete this overlappedNode
									splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, nodetoBeDeleted);
									
									if(splicedoutNode!=nodetoBeDeleted)
										splicedoutNode2CopiedNodeMap.put(splicedoutNode,nodetoBeDeleted);	
							}else{							
								//Now we can delete this overlappedNode
								splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, overlappedNode);
								
								if (splicedoutNode!=overlappedNode)
									splicedoutNode2CopiedNodeMap.put(splicedoutNode,overlappedNode);
						
							}
							
						}//end of for: each overlapped node in the list
						
						existingIntervalTree.intervalTreeInsert(existingIntervalTree, mergedNode);
						
					}
					//there is no overlap
					else{
												
						//insert interval
						existingIntervalTree.intervalTreeInsert(existingIntervalTree, intervalTreeNode);
					}
					
					histoneNameandCellLineName2IntervalTreeHashMap.put(histoneNameandCellLineName, existingIntervalTree);	
				
				}//end of histoneNameandCellLineName2IntervalTreeHashMap contains this histoneNameandCellLineName
				
			}//End of While: read each line 
			
			//for this chromosome and all histoneNameandCellLineName, the interval trees are constructed.
			//now accumulate the number of non overlapping base pairs in histoneNameandCellLineName2IntervalTreeHashMap into histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap
			accumulateNumberofNonOverlappingBasePairs(histoneNameandCellLineName2IntervalTreeHashMap,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap);
			
			
			bufferedReader.close();
		
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
	}
	
	public void calculateforEachChromosomeExonBasedKeggPathway(String outputFolder,Map<String,Long> exonBasedkeggPathway2NumberofBasePairsHashMap,Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap,String unsortedChromosomeBasedInputFileName){
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		String strLine = null;		
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		int indexofSixthTab =0;
		int indexofSeventhTab =0;
		int indexofEighth = 0;
		
		ChromosomeName chromName;
		int low;
		int high;
		String refSeqGeneName;		
		String ncbiGeneId;
		Integer geneEntrezId;		
		IntervalName intervalName;
		int intervalNumber;
		String geneHugoSymbol;
		
		//Initialize this for each chromosome
		Map<String,IntervalTree> keggPathway2IntervalTreeHashMap = new HashMap<String,IntervalTree>();
		IntervalTree existingIntervalTree = null;
		
		
		try {
			fileReader = new FileReader(outputFolder + unsortedChromosomeBasedInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
			while((strLine = bufferedReader.readLine())!=null){
				
				//example strLine
				//chrY	16636453	16636816	NR_028319	22829	Exon	1	+	NLGN4Y
				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);				
				indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
				indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
				indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
				indexofEighth		= strLine.indexOf('\t',indexofSeventhTab+1);
					
				chromName = ChromosomeName.convertStringtoEnum(strLine.substring(0,indexofFirstTab));
				low = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
				high = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));	
				refSeqGeneName = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				ncbiGeneId = strLine.substring(indexofFourthTab+1, indexofFifthTab);
				geneEntrezId = Integer.parseInt(ncbiGeneId);
				intervalName = IntervalName.convertStringtoEnum(strLine.substring(indexofFifthTab+1, indexofSixthTab));
				intervalNumber = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
				geneHugoSymbol =strLine.substring(indexofEighth+1);
				
				//Fill the exon based keggPathway2IntervalTreeHashMap
				if (intervalName.isExon()){
					//Get the list of kegg pathway names contain this ncbi gene id
					List<String> keggPathwayNameListContainingThisNcbiGeneId = ncbiGeneId2KeggPathwayHashMap.get(ncbiGeneId);
					
					
					//Add this strLine as an intervalTreeNode to the corresponding interval tree of kegg Pathway
					if (keggPathwayNameListContainingThisNcbiGeneId!=null){
						for(int i=0; i<keggPathwayNameListContainingThisNcbiGeneId.size() ; i++){
							
							//create intervalTreeNode
							UcscRefSeqGeneIntervalTreeNode intervalTreeNode = new UcscRefSeqGeneIntervalTreeNode(chromName,low,high,refSeqGeneName,geneEntrezId,intervalName,intervalNumber,geneHugoSymbol,NodeType.ORIGINAL);
		
							String keggPathwayName = keggPathwayNameListContainingThisNcbiGeneId.get(i);
							//Get the interval tree for this Kegg Pathway Name
							
							
							
							//Get the interval tree for this kegg  pathway							
							//For first insert of each kegg pathway name and chromosome number
							//keggPathway2IntervalTreeHashMap does not contain this kegg pathway name
							if (keggPathway2IntervalTreeHashMap.get(keggPathwayName)==null){
								IntervalTree intervalTree = new IntervalTree();							
								intervalTree.intervalTreeInsert(intervalTree, intervalTreeNode);							
								keggPathway2IntervalTreeHashMap.put(keggPathwayName, intervalTree);
								
	
							}
							
							//For all inserts but the first of kegg pathway name and chromosome number
							//keggPathway2IntervalTreeHashMap already contains this kegg pathway name
							else{
								//get existing interval tree for this chromosome and kegg pathway name
								existingIntervalTree = keggPathway2IntervalTreeHashMap.get(keggPathwayName);
																			
								List<IntervalTreeNode> overlappedNodeList= new ArrayList<IntervalTreeNode>();
								
								existingIntervalTree.findAllOverlappingIntervals(overlappedNodeList,existingIntervalTree.getRoot(), intervalTreeNode);
								
								//there is overlap
								if (overlappedNodeList!= null && overlappedNodeList.size()>0){
										
									IntervalTreeNode mergedNode = new UcscRefSeqGeneIntervalTreeNode(intervalTreeNode.getChromName(),intervalTreeNode.getLow(), intervalTreeNode.getHigh(),intervalTreeNode.getRefSeqGeneName(),intervalTreeNode.getGeneEntrezId(),intervalTreeNode.getIntervalName(),intervalTreeNode.getIntervalNumber(),intervalTreeNode.getGeneHugoSymbol(),NodeType.MERGED);
									IntervalTreeNode splicedoutNode = null;
									IntervalTreeNode nodetoBeDeleted =null;	
									//you may try to delete a node which is already spliced out by former deletions
									//therefore you must keep track of the real node to be deleted in case of trial of deletion of an already spliced out node.
									Map<IntervalTreeNode,IntervalTreeNode> splicedoutNode2CopiedNodeMap = new HashMap<IntervalTreeNode, IntervalTreeNode>();
									
									for(int j= 0; j<overlappedNodeList.size(); j++){
											
										IntervalTreeNode overlappedNode = overlappedNodeList.get(j);
											
										updateMergedNode(mergedNode, overlappedNode);
										
										//if the to be deleted, intended interval tree node is an already spliced out node
										//in other words if it is copied into another interval tree node
										//then you have to delete that node
										//not the already spliced out node
										
										nodetoBeDeleted =compute(splicedoutNode2CopiedNodeMap,overlappedNode) ;
										
										if 	(nodetoBeDeleted!=null){
												//they are the same
												//current overlapped node has been copied to the previously deleted overlapped node
												//current overlapped node is spliced out by the previous delete operation
												//so delete that previously deleted overlapped node in order to delete the current overlapped node
												//since current overlapped node is copied to the previously deleted overlapped node
												//Now we can delete this overlappedNode
												splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, nodetoBeDeleted);
												
												if(splicedoutNode!=nodetoBeDeleted)
													splicedoutNode2CopiedNodeMap.put(splicedoutNode,nodetoBeDeleted);	
										}else{							
											//Now we can delete this overlappedNode
											splicedoutNode = existingIntervalTree.intervalTreeDelete(existingIntervalTree, overlappedNode);
											
											if (splicedoutNode!=overlappedNode)
												splicedoutNode2CopiedNodeMap.put(splicedoutNode,overlappedNode);
									
										}
										
									}//end of for: each overlapped node in the list
									
									existingIntervalTree.intervalTreeInsert(existingIntervalTree, mergedNode);
									
								}
								//there is no overlap
								else{
															
									//insert interval
									existingIntervalTree.intervalTreeInsert(existingIntervalTree, intervalTreeNode);
								}
								
								keggPathway2IntervalTreeHashMap.put(keggPathwayName, existingIntervalTree);	
								
								
							
							}//end of else:keggPathway2IntervalTreeHashMap contains this kegg pathway name

														
							
							
						}// End of For: add this interval to interval tree of each kegg pathway containing this exon of gene
						
					}//End of If: keggPathwayNameListContainingThisNcbiGeneId is not null.
				}//End of If: read interval if it is exon
			}//End of While: read each line 
			
			//for this chromosome and all kegg pathway names, the interval trees are constructed.
			//now accumulate the number of non overlapping base pairs in keggPathway2IntervalTreeHashMap into keggPathway2NumberofNonOverlappingBasePairsHashMap
			accumulateNumberofNonOverlappingBasePairs(keggPathway2IntervalTreeHashMap,exonBasedkeggPathway2NumberofBasePairsHashMap);
				
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
		
		
	}
	

	
	
	public void writeNumberofNonoverlappingBasePairsResults(String outputFileName, Map<String,Long> functionalElement2NumberofNonOverlappingBasePairsHashMap){
		FileWriter fileWriter;
		BufferedWriter  bufferedWriter;
		try {
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
	
			Set<Map.Entry<String, Long>> functionalElement2NumberofBasePairsSet = functionalElement2NumberofNonOverlappingBasePairsHashMap.entrySet();
		
			Iterator<Map.Entry<String,Long>> itr = functionalElement2NumberofBasePairsSet.iterator();
		
			
			while(itr.hasNext()){
				Map.Entry<String, Long>	 entry = (Map.Entry<String, Long>) itr.next();
				bufferedWriter.write(entry.getKey() + "\t" + entry.getValue() +"\n");
			}
	
			bufferedWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/*
	 * This map will contain keggpathway versus number of non overlapping base pairs in the exons of the genes of the kegg pathway
	 * This list will contain the gene ids list for each kegg pathway
	 * 
	 * Unsorted chromosome based ucsc refseq gene files will be read only once and one by one.
	 * While reading each unsorted chromosome based ucsc refseq gene files, interval tree for each kegg pathway will be created.
	 * 
	 * If read line is a exon of a gene in the gene ids list of any kegg pathway
	 * that read line will be inserted to the interval tree of that kegg pathway.
	 * 
	 * But before inserting read line as an interval into interval tree, 
	 * it will be checked whether read line as an interval overlaps with any already existing intervals in the interval tree.
	 * If it overlaps, newly to be inserted node will be updated according to the already existing overlapped nodes.
	 * Already overlapped nodes will be deleted one by one.
	 * then updated to be inserted node will be inserted into interval tree.
	 * Otherwise read line will be inserted into the interval tree of that kegg pathway and chromosome.
	 */
	public void calculateNumberofNonoverlappingBasePairsforExonBasedKeggPathwayAnalysis(String dataFolder,Map<String,Long> exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap){
		
		Map<String,List<String>> keggPathway2NcbiGeneIdHashMap = new HashMap<String,List<String>>();
		Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap = new HashMap<String,List<String>>();
		String pathwayHsaListFileName = dataFolder+ Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE;
		
		KeggPathwayUtility.readKeggPathwayHsaListAndCreateHashMaps(pathwayHsaListFileName, keggPathway2NcbiGeneIdHashMap, ncbiGeneId2KeggPathwayHashMap);
		
		
		//For Each kegg pathway and For each chromosome, interval tree will be created.
		//Lengths of intervals in the interval tree will be accumulated in exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			switch(chromosomeNumber){
				case 1: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR1_REFSEQ_GENES);
					break;
				case 2: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME+ Commons.UNSORTED_CHR2_REFSEQ_GENES);
					break;
				case 3: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR3_REFSEQ_GENES);
					break;
				case 4: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR4_REFSEQ_GENES);
					break;
				case 5: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR5_REFSEQ_GENES);
					break;			
				case 6: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR6_REFSEQ_GENES);
					break;
				case 7: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR7_REFSEQ_GENES);
					break;
				case 8: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR8_REFSEQ_GENES);
					break;
				case 9: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR9_REFSEQ_GENES);
					break;
				case 10: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR10_REFSEQ_GENES);
					break;
				case 11: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR11_REFSEQ_GENES);
					break;
				case 12: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR12_REFSEQ_GENES);
					break;
				case 13: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR13_REFSEQ_GENES);
					break;
				case 14: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR14_REFSEQ_GENES);
					break;
				case 15: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR15_REFSEQ_GENES);
					break;
				case 16: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR16_REFSEQ_GENES);
					break;
				case 17: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR17_REFSEQ_GENES);
					break;
				case 18: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR18_REFSEQ_GENES);
					break;
				case 19: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR19_REFSEQ_GENES);
					break;
				case 20: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR20_REFSEQ_GENES);
					break;
				case 21: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR21_REFSEQ_GENES);
					break;
				case 22: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR22_REFSEQ_GENES);
					break;
				case 23: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRX_REFSEQ_GENES);
					break;
				case 24: calculateforEachChromosomeExonBasedKeggPathway(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRY_REFSEQ_GENES);
					break;

	
			}//End of switch
		} // For each chromosome
		
	
		
	}

	
	/*
	 * This map will contain keggpathway versus number of non overlapping base pairs in the intons, 5p1, 5p2, 3p1, 3p2 of the genes of the kegg pathway
	 * This list will contain the gene ids list for each kegg pathway
	 * 
	 * Unsorted chromosome based ucsc refseq gene files will be read only once and one by one.
	 * While reading each unsorted chromosome based ucsc refseq gene files, interval tree for each kegg pathway will be created.
	 * 
	 * If read line is an intron, 5p1, 5p2, 3p1, 3p2 of a gene in the gene ids list of any kegg pathway
	 * that read line will be inserted to the interval tree of that kegg pathway.
	 * 
	 * But before inserting read line as an interval into interval tree, 
	 * it will be checked whether read line as an interval overlaps with any already existing intervals in the interval tree.
	 * If it overlaps, newly to be inserted node will be updated according to the already existing overlapped nodes.
	 * Already overlapped nodes will be deleted one by one.
	 * then updated to be inserted node will be inserted into interval tree.
	 * Otherwise read line will be inserted into the interval tree of that kegg pathway and chromosome.
	 */
	public void calculateNumberofNonoverlappingBasePairsforRegulationBasedKeggPathwayAnalysis(String dataFolder,Map<String,Long> regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap){
		
		Map<String,List<String>> keggPathway2NcbiGeneIdHashMap = new HashMap<String,List<String>>();
		Map<String,List<String>> ncbiGeneId2KeggPathwayHashMap = new HashMap<String,List<String>>();
		String pathwayHsaListFileName = dataFolder + Commons.KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE;
		
		KeggPathwayUtility.readKeggPathwayHsaListAndCreateHashMaps(pathwayHsaListFileName, keggPathway2NcbiGeneIdHashMap, ncbiGeneId2KeggPathwayHashMap);
		
		
		//For each chromosome, interval tree will be created for each kegg pathway
		//Lengths of intervals in the interval tree will be accumulated in regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR1_REFSEQ_GENES);
						break;
				case 2: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR2_REFSEQ_GENES);
						break;
				case 3: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR3_REFSEQ_GENES);
						break;
				case 4: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR4_REFSEQ_GENES);
						break;
				case 5: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR5_REFSEQ_GENES);
						break;			
				case 6: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR6_REFSEQ_GENES);
						break;
				case 7: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR7_REFSEQ_GENES);
						break;
				case 8: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR8_REFSEQ_GENES);
						break;
				case 9: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR9_REFSEQ_GENES);
						break;
				case 10: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR10_REFSEQ_GENES);
						break;
				case 11: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR11_REFSEQ_GENES);
						break;
				case 12: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR12_REFSEQ_GENES);
						break;
				case 13: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR13_REFSEQ_GENES);
						break;
				case 14: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR14_REFSEQ_GENES);
						break;
				case 15: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR15_REFSEQ_GENES);
						break;
				case 16: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR16_REFSEQ_GENES);
						break;
				case 17: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR17_REFSEQ_GENES);
						break;
				case 18: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR18_REFSEQ_GENES);
						break;
				case 19: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR19_REFSEQ_GENES);
						break;
				case 20: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR20_REFSEQ_GENES);
						break;
				case 21: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR21_REFSEQ_GENES);
						break;
				case 22: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHR22_REFSEQ_GENES);
						break;
				case 23: calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRX_REFSEQ_GENES);
						break;
			case 24: 	calculateforEachChromosomeRegulationBasedKeggPathway(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap, ncbiGeneId2KeggPathwayHashMap, Commons.CREATE_UCSCGENOME_REFSEQ_GENES_DIRECTORYNAME + Commons.UNSORTED_CHRY_REFSEQ_GENES);
						break;

	
			}//End of switch
		} // For each chromosome
		
	}
	
	
	
	/*
	 * This dnaseCellLine2NumberofNonOverlappingBasePairsHashMap will contain dnase cell line name to  number of non overlapping base pairs in whole genome
	 *  
	 * Unsorted chromosome based encode dnase files will be read only once and one by one.
	 * While reading each unsorted chromosome based dnase files, for each chromosome and for each dnase cell line name an interval tree will be created.
	 * 
	  * that read line will be inserted to the interval tree of related chromosome and dnase cell line name.
	 * 
	 * But before inserting read line as an interval into interval tree, 
	 * it will be checked whether read line as an interval overlaps with any already existing intervals in the interval tree.
	 * If it overlaps, newly to be inserted node will be updated according to the already existing overlapped nodes.
	 * Already overlapped nodes will be deleted one by one.
	 * then updated to be inserted node will be inserted into interval tree.
	 * Otherwise read line will be inserted into the interval tree of that histone Name and chromosome.
	 */
	public void calculateNumberofNonoverlappingBasePairsforDnaseCellLineAnalysis(String dataFolder,Map<String,Long> dnaseCellLine2NumberofNonOverlappingBasePairsHashMap){
		
		
		//For each chromosome and for each dnase cell line name an interval tree will be created.
		//Lengths of intervals in the interval tree will be accumulated in dnaseCellLine2NumberofNonOverlappingBasePairsHashMap
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR1_DNASE_FILENAME);
						break;
				case 2: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR2_DNASE_FILENAME);
						break;
				case 3: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR3_DNASE_FILENAME);
						break;
				case 4: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR4_DNASE_FILENAME);
						break;
				case 5: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR5_DNASE_FILENAME);
						break;
				case 6: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR6_DNASE_FILENAME);
						break;
				case 7: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR7_DNASE_FILENAME);
						break;
				case 8: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR8_DNASE_FILENAME);
						break;
				case 9: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR9_DNASE_FILENAME);
						break;
				case 10: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR10_DNASE_FILENAME);
						break;
				case 11: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR11_DNASE_FILENAME);
						break;
				case 12: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR12_DNASE_FILENAME);
						break;
				case 13: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR13_DNASE_FILENAME);
						break;
				case 14: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR14_DNASE_FILENAME);
						break;
				case 15: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR15_DNASE_FILENAME);
						break;
				case 16: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR16_DNASE_FILENAME);
						break;
				case 17: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR17_DNASE_FILENAME);
						break;
				case 18: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR18_DNASE_FILENAME);
						break;
				case 19: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR19_DNASE_FILENAME);
						break;
				case 20: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR20_DNASE_FILENAME);
						break;
				case 21: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR21_DNASE_FILENAME);
						break;
				case 22: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHR22_DNASE_FILENAME);
						break;
				case 23: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHRX_DNASE_FILENAME);
						break;
				case 24: calculateforEachChromosomeDnaseCellLine(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_DNASE_DIRECTORY + Commons.UNSORTED_CHRY_DNASE_FILENAME);
						break;

			}//End of switch
		} // For each chromosome
		
	}
	
	
	/*
	 * This tfbs2NumberofNonOverlappingBasePairsHashMap will contain tfbs to  number of non overlapping base pairs in whole genome
	 *  
	 * Unsorted chromosome based encode tfbs files will be read only once and one by one.
	 * While reading each unsorted chromosome based tfbs files, interval tree of  each tfbs name and each chromosome will be created.
	 * 
	 * that read line will be inserted to the interval tree of that tfbs name and chromosome.
	 * 
	 * But before inserting read line as an interval into interval tree, 
	 * it will be checked whether read line as an interval overlaps with any already existing intervals in the interval tree.
	 * If it overlaps, newly to be inserted node will be updated according to the already existing overlapped nodes.
	 * Already overlapped nodes will be deleted one by one.
	 * then updated to be inserted node will be inserted into interval tree.
	 * Otherwise read line will be inserted into the interval tree of that histone Name and chromosome.
	 */
	public void calculateNumberofNonoverlappingBasePairsforTfbsAnalysis(String dataFolder,Map<String,Long> tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap){
		
		
		//For each chromosome and tfbs, interval tree will be created.
		//there will be no overlap in the interval tree
		//number of bases of all intervals in the interval tree will be accumulated in tfbs2NumberofNonOverlappingBasePairsHashMap
		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR1_TFBS_FILENAME);
					break;
				case 2: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR2_TFBS_FILENAME);
					break;
				case 3: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR3_TFBS_FILENAME);
					break;
				case 4: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR4_TFBS_FILENAME);
					break;
				case 5: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR5_TFBS_FILENAME);
					break;
				case 6: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR6_TFBS_FILENAME);
					break;
				case 7: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR7_TFBS_FILENAME);
					break;
				case 8: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR8_TFBS_FILENAME);
					break;
				case 9: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR9_TFBS_FILENAME);
					break;
				case 10: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR10_TFBS_FILENAME);
					break;
				case 11: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR11_TFBS_FILENAME);
					break;
				case 12: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR12_TFBS_FILENAME);
					break;
				case 13: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR13_TFBS_FILENAME);
					break;
				case 14: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR14_TFBS_FILENAME);
					break;
				case 15: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR15_TFBS_FILENAME);
					break;
				case 16: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR16_TFBS_FILENAME);
					break;
				case 17: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR17_TFBS_FILENAME);
					break;
				case 18: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR18_TFBS_FILENAME);
					break;
				case 19: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR19_TFBS_FILENAME);
					break;
				case 20: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR20_TFBS_FILENAME);
					break;
				case 21: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR21_TFBS_FILENAME);
					break;
				case 22: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHR22_TFBS_FILENAME);
					break;
				case 23: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHRX_TFBS_FILENAME);
					break;
				case 24: calculateforEachChromosomeTfbs(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_TFBS_DIRECTORY + Commons.UNSORTED_CHRY_TFBS_FILENAME);
					break;

			}//End of switch
		} // For each chromosome
		
	}
		
	
	/*
	 * This histone2NumberofNonOverlappingBasePairsHashMap will contain histone name to  number of non overlapping base pairs in whole genome
	 *  
	 * Unsorted chromosome based encode histone files will be read only once and one by one.
	 * While reading each unsorted chromosome based histone files, interval tree of  each hsitone name and each chromosome will be created.
	 * 
	 * that read line will be inserted to the interval tree of that histone name and chromosome.
	 * 
	 * But before inserting read line as an interval into interval tree, 
	 * it will be checked whether read line as an interval overlaps with any already existing intervals in the interval tree.
	 * If it overlaps, newly to be inserted node will be updated according to the already existing overlapped nodes.
	 * Already overlapped nodes will be deleted one by one.
	 * then updated to be inserted node will be inserted into interval tree.
	 * Otherwise read line will be inserted into the interval tree of that histone Name and chromosome.
	 */
	public void calculateNumberofNonoverlappingBasePairsforHistoneAnalysis(String dataFolder,Map<String,Long> histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap){
		
		
		//For each chromosome and histone name, interval tree will be created.
		//there will be no overlap in the interval tree.
		//Number of bases of intervals in the interval tree will be accumulated in histone2NumberofNonOverlappingBasePairsHashMap		
		for(int chromosomeNumber= 1; chromosomeNumber<=Commons.NUMBER_OF_CHROMOSOMES_HG19; chromosomeNumber++){
			
			switch(chromosomeNumber){
				case 1: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR1_HISTONE_FILENAME);
					break;
				case 2: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR2_HISTONE_FILENAME);
					break;
				case 3: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR3_HISTONE_FILENAME);
					break;
				case 4: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR4_HISTONE_FILENAME);
					break;
				case 5: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR5_HISTONE_FILENAME);
					break;
				case 6: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR6_HISTONE_FILENAME);
					break;
				case 7: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR7_HISTONE_FILENAME);
					break;
				case 8: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR8_HISTONE_FILENAME);
					break;
				case 9: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR9_HISTONE_FILENAME);
					break;
				case 10: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR10_HISTONE_FILENAME);
					break;
				case 11: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR11_HISTONE_FILENAME);
					break;
				case 12: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR12_HISTONE_FILENAME);
					break;
				case 13: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR13_HISTONE_FILENAME);
					break;
				case 14: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR14_HISTONE_FILENAME);
					break;
				case 15: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR15_HISTONE_FILENAME);
					break;
				case 16: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR16_HISTONE_FILENAME);
					break;
				case 17: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR17_HISTONE_FILENAME);
					break;
				case 18: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR18_HISTONE_FILENAME);
					break;
				case 19: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR19_HISTONE_FILENAME);
					break;
				case 20: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR20_HISTONE_FILENAME);
					break;
				case 21: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR21_HISTONE_FILENAME);
					break;
				case 22: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHR22_HISTONE_FILENAME);
					break;
				case 23: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHRX_HISTONE_FILENAME);
					break;
				case 24: calculateforEachChromosomeHistone(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap, Commons.CREATE_ENCODE_HISTONE_DIRECTORY + Commons.UNSORTED_CHRY_HISTONE_FILENAME);
					break;

			}//End of switch
		} // For each chromosome
		
	}


	
		//args[0]	--->	Input File Name with folder
		//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
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
		public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
	
			
		CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingIntervalTree calculate = new CalculateNumberofNonOverlappingBasePairsinWholeGenomeUsingIntervalTree();
		
		Map<String,Long> exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap = new HashMap<String,Long>();
		Map<String,Long> regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap = new HashMap<String,Long>();
		Map<String,Long> dnaseCellLine2NumberofNonOverlappingBasePairsHashMap = new HashMap<String,Long>();
		Map<String,Long> tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap = new HashMap<String,Long>();
		Map<String,Long> histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap = new HashMap<String,Long>();
		
		
		String dnaseCellLineOutputFileName = dataFolder + Commons.DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE;
		String tfbsOutputFileName = dataFolder + Commons.TFBS_WHOLE_GENOME_USING_INTERVAL_TREE;
		String histoneOutputFileName = dataFolder + Commons.HISTONE_WHOLE_GENOME_USING_INTERVAL_TREE;
		String exonBasedOutputFileName = dataFolder + Commons.EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE;
		String regulationBasedOutputFileName = dataFolder + Commons.REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE;
		
			
		//Dnase
		//Calculate the number of non overlapping base pairs in dnase cell Line in whole genome
		calculate.calculateNumberofNonoverlappingBasePairsforDnaseCellLineAnalysis(dataFolder,dnaseCellLine2NumberofNonOverlappingBasePairsHashMap);
		//Write dnase cell line whole genome results to a file
		calculate.writeNumberofNonoverlappingBasePairsResults(dnaseCellLineOutputFileName, dnaseCellLine2NumberofNonOverlappingBasePairsHashMap);
		
		//Tfbs
		//Calculate the number of non overlapping base pairs in tfbs in whole genome
		calculate.calculateNumberofNonoverlappingBasePairsforTfbsAnalysis(dataFolder,tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap);		
		//Write tfbs whole genome results to a file
		calculate.writeNumberofNonoverlappingBasePairsResults(tfbsOutputFileName, tfbsNameandCellLineName2NumberofNonOverlappingBasePairsHashMap);
		
		//Histone
		//Calculate the number of non overlapping base pairs in histone in whole genome
		calculate.calculateNumberofNonoverlappingBasePairsforHistoneAnalysis(dataFolder,histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap);				
		//Write histone whole genome results to a file
		calculate.writeNumberofNonoverlappingBasePairsResults(histoneOutputFileName, histoneNameandCellLineName2NumberofNonOverlappingBasePairsHashMap);
		
		
		//Exon Based Kegg Pathway
		//Calculate the number of non overlapping base pairs of exons of genes of each kegg pathway
		calculate.calculateNumberofNonoverlappingBasePairsforExonBasedKeggPathwayAnalysis(dataFolder,exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap);
		//Write exon based Results to a file
		calculate.writeNumberofNonoverlappingBasePairsResults(exonBasedOutputFileName, exonBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap);		
		
		//Regulation Based Kegg Pathway
		//Calculate the number of non overlapping base pairs of introns, 5p1, 5p2, 3p1 and 3p2 of genes of each kegg pathway
		calculate.calculateNumberofNonoverlappingBasePairsforRegulationBasedKeggPathwayAnalysis(dataFolder,regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap);
		//Write regulation based Results to a file
		calculate.writeNumberofNonoverlappingBasePairsResults(regulationBasedOutputFileName, regulationBasedKeggPathway2NumberofNonOverlappingBasePairsHashMap);
			
		return;
		

	}

}
