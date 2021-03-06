/**
 * @author Burcak Otlu
 * 
 * 28 FEB 2014 changed.
 *
 * 
 */



/*
 * This program creates unsorted and sorted chromosome based refseq genes files.
 * For sorting it uses Interval Tree Infix Traversal method.
 * 
 * Refseq genes data are read from hg19_refseq_genes.txt
 * Each refseq gene is augmented with gene id by using human_refseq2gene.txt
 * 
 * human_refseq2gene.txt is output of src\ncbi\HumanRefSeq2Gene.java
 * 
 * It takes less than one minute. 
 */

package create.ucscgenome;


import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;
import intervaltree.UcscRefSeqGeneIntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ui.GlanetRunner;
import auxiliary.FileOperations;

import common.Commons;

import create.ChromosomeBasedFilesandOperations;
import enumtypes.ChromosomeName;
import enumtypes.IntervalName;
import enumtypes.NodeType;


public class CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting {
	
//	global variables
	
	public static void getRefSeqGeneData(String strLine,RefSeqGene refSeqGene){
		
		String refSeqGeneName;
		ChromosomeName chromName;
		char strand;
		int txStart;
		int txEnd;
		int cdsStart;
		int cdsEnd;
		int exonCounts;
		String exonStarts;
		String exonEnds;
		String alternateGeneName;
		 
		
		int indexofFirstTab = 0;
		int indexofSecondTab = 0;
		int indexofThirdTab = 0;
		int indexofFourthTab = 0;
		int indexofFifthTab = 0;
		int indexofSixthTab = 0;
		int indexofSeventhTab = 0;
		int indexofEighthTab = 0;
		int indexofNinethTab = 0;
		int indexofTenthTab = 0;
		int indexofEleventhTab = 0;
		int indexofTwelfthTab = 0;
		int indexofThirteenthTab = 0;
		
		indexofFirstTab 		= strLine.indexOf('\t');
		indexofSecondTab 		= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 		= strLine.indexOf('\t', indexofSecondTab+1);
		indexofFourthTab 		= strLine.indexOf('\t', indexofThirdTab+1);
		indexofFifthTab 		= strLine.indexOf('\t', indexofFourthTab+1);
		indexofSixthTab 		= strLine.indexOf('\t', indexofFifthTab+1);
		indexofSeventhTab 		= strLine.indexOf('\t', indexofSixthTab+1);
		indexofEighthTab 		= strLine.indexOf('\t', indexofSeventhTab+1);
		indexofNinethTab 		= strLine.indexOf('\t', indexofEighthTab+1);
		indexofTenthTab 		= strLine.indexOf('\t', indexofNinethTab+1);
		indexofEleventhTab 		= strLine.indexOf('\t', indexofTenthTab+1);
		indexofTwelfthTab 		= strLine.indexOf('\t', indexofEleventhTab+1);
		indexofThirteenthTab 	= strLine.indexOf('\t', indexofTwelfthTab+1);
		
		
		refSeqGeneName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
		chromName = ChromosomeName.convertStringtoEnum(strLine.substring(indexofSecondTab+1, indexofThirdTab));
		
		//for debug purposes starts
		if (chromName == null){
			GlanetRunner.appendLog("There is a situation, chromName is null" + strLine);			
		}
		//for debug purposes ends
		
		strand = strLine.substring(indexofThirdTab+1, indexofFourthTab).charAt(0);
		
		
		txStart = Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab));
		//28FEB2014
		//Convert one based end to zero based end
		txEnd = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab))-1;
		
		cdsStart = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
		//28FEB2014
		//Convert one based end to zero based end
		cdsEnd = Integer.parseInt(strLine.substring(indexofSeventhTab+1, indexofEighthTab))-1;
		
		exonCounts = Integer.parseInt(strLine.substring(indexofEighthTab+1, indexofNinethTab));
		exonStarts = strLine.substring(indexofNinethTab+1, indexofTenthTab);
		exonEnds = strLine.substring(indexofTenthTab+1, indexofEleventhTab);
		alternateGeneName = strLine.substring(indexofTwelfthTab+1, indexofThirteenthTab);
		
		refSeqGene.setRefSeqGeneName(refSeqGeneName);
		refSeqGene.setAlternateGeneName(alternateGeneName);
		refSeqGene.setChromName(chromName); 
		refSeqGene.setStrand(strand);
		refSeqGene.setTranscriptionStartPosition(txStart);
		refSeqGene.setTranscriptionEndPosition(txEnd);
		refSeqGene.setCdsStart(cdsStart);
		refSeqGene.setCdsEnd(cdsEnd);
		refSeqGene.setExonCounts(exonCounts);
		
		List<Integer> exonStartList = new ArrayList<Integer>(exonCounts);
		List<Integer> exonEndList = new ArrayList<Integer>(exonCounts);
		
//		Initialise before for loop
		int indexofFormerComma =-1;
		int indexofLatterComma = -1;
		
		for(int i = 0 ; i<exonCounts; i++){
			indexofFormerComma = indexofLatterComma;
			indexofLatterComma = exonStarts.indexOf(',',indexofFormerComma+1);
			exonStartList.add(Integer.parseInt(exonStarts.substring(indexofFormerComma+1,indexofLatterComma)));						
		}
		
//		Initialize before for loop
		indexofFormerComma =-1;
		indexofLatterComma = -1;
		
		for(int i = 0 ; i<exonCounts; i++){
			indexofFormerComma = indexofLatterComma;
			indexofLatterComma = exonEnds.indexOf(',',indexofFormerComma+1);
			//28FEB2014
			//Convert one based end to zero based end
			exonEndList.add(Integer.parseInt(exonEnds.substring(indexofFormerComma+1,indexofLatterComma))-1);						
		}
		
		refSeqGene.setExonStarts(exonStartList);
		refSeqGene.setExonEnds(exonEndList);		
		
	}
	
	
	public static void writeInformation(Set<RefSeqGene> refSeqGenes, Set<String> refSeqGeneNames,BufferedWriter bufferedWriter){
		try {
				bufferedWriter.write("Size of the refseqGenes is " + refSeqGenes.size()+ System.getProperty("line.separator"));
				bufferedWriter.write("Size of the refseqGeneNamess is " + refSeqGeneNames.size()+System.getProperty("line.separator"));	

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	
	public static void addRefSeqGeneName(RefSeqGene refSeqGene, Set<RefSeqGene> refSeqGenes, Set<String> refSeqGeneNames,BufferedWriter bufferedWriter){
		try {
			
			//for debug purposes starts
			if (refSeqGene.getChromName() == null){
				GlanetRunner.appendLog("refSeqGene chromName is null");
			}
			//for debug purposes ends 
			
			if (!refSeqGenes.contains(refSeqGene))
				refSeqGenes.add(refSeqGene);
			else 
				bufferedWriter.write("Totally same RefSeq Gene "+ refSeqGene.getRefSeqGeneName() +System.getProperty("line.separator"));
			

			if (!refSeqGeneNames.contains(refSeqGene.getRefSeqGeneName()))
				refSeqGeneNames.add(refSeqGene.getRefSeqGeneName());
			else 
				bufferedWriter.write("This RefSeq Gene Name already exists " + refSeqGene.getRefSeqGeneName()+ System.getProperty("line.separator"));
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void analyzeTxCdsExons(RefSeqGene refSeqGene, BufferedWriter bufferedWriter){
		if (!((refSeqGene.getTranscriptionStartPosition()<= refSeqGene.getCdsStart()) &&
		    (refSeqGene.getTranscriptionEndPosition()>= refSeqGene.getCdsEnd()) &&
		    (refSeqGene.getExonStarts().get(0)==refSeqGene.getTranscriptionStartPosition()) &&
		    (refSeqGene.getExonEnds().get(refSeqGene.getExonCounts()-1)==refSeqGene.getTranscriptionEndPosition()))){
			  try {
				bufferedWriter.write("Unexpected refseq gene" + System.getProperty("line.separator"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
	public static void readInputFile(String fileName, List<RefSeqGene> refSeqGeneList, Map<String,Integer> refSeq2GeneHashMap, String dataFolder){
	    FileReader fileReader =null;
	    BufferedReader bufferedReader = null;
	    
	    FileWriter fileWriter = null;
	    BufferedWriter bufferedWriter = null;
	    
	    String strLine =null;
	    Set<RefSeqGene> refSeqGenes = new HashSet<RefSeqGene>();
	    Set<String> refSeqGeneNames = new HashSet<String>();
	    Integer geneId;
	    
	    
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			fileWriter = FileOperations.createFileWriter(dataFolder + Commons.ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES_DIRECTORYNAME,Commons.ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES_FILENAME);
			bufferedWriter = new BufferedWriter(fileWriter);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
//			Consume first line since first line contains column names
			strLine = bufferedReader.readLine();
			if (strLine!=null){
//				GlanetRunner.appendLog(strLine);				
			}
			
			while ((strLine = bufferedReader.readLine()) != null)   {	
				RefSeqGene refSeqGene = new RefSeqGene();	
				
				//My convention is zero based start and end.
				//Ucsc genome table browser convention: Our internal database representations of coordinates always have a zero-based start and a one-based end.
				//Convert one based end to zero based end in getRefSeqGeneData()	
				getRefSeqGeneData(strLine, refSeqGene);	
				geneId = refSeq2GeneHashMap.get(refSeqGene.getRefSeqGeneName());
				
				if (geneId!=null){
					refSeqGene.setGeneId(geneId);
				}else{
//					If rna nucleotide accession version has no corresponding gene id, zero is inserted.
					refSeqGene.setGeneId(Commons.ZERO);

				}
										
				addRefSeqGeneName(refSeqGene, refSeqGenes, refSeqGeneNames,bufferedWriter);
				refSeqGeneList.add(refSeqGene);
				
				analyzeTxCdsExons(refSeqGene,bufferedWriter);
			}
			
			
			
			writeInformation(refSeqGenes,refSeqGeneNames,bufferedWriter);

//			Close the bufferedReader
			bufferedReader.close();
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
				   
	}

	
	public static void checkforValidInterval(FivePrimeThreePrime primes){
//		Five primes
		if ((primes.get_5p1Start()).compareTo(0)<0)		
			primes.set_5p1Start(0);
		if ((primes.get_5p1End()).compareTo(0)<0)
			primes.set_5p1End(0);		
		
		if ((primes.get_5p2Start()).compareTo(0)<0)
			primes.set_5p2Start(0);
		if ((primes.get_5p2End()).compareTo(0)<0)
			primes.set_5p2End(0);
		
		if ((primes.get_5dStart()).compareTo(0)<0)
			primes.set_5dStart(0);
		if ((primes.get_5dEnd()).compareTo(0)<0)
			primes.set_5dEnd(0);
		
		
		
//		Three primes
		if ((primes.get_3p1Start()).compareTo(0)<0)
			primes.set_3p1Start(0);
		if ((primes.get_3p1End()).compareTo(0)<0)
			primes.set_3p1End(0);
		
		if ((primes.get_3p2Start()).compareTo(0)<0)
			primes.set_3p2Start(0);
		if ((primes.get_3p2End()).compareTo(0)<0)
			primes.set_3p2End(0);
		
		if ((primes.get_3dStart()).compareTo(0)<0)
			primes.set_3dStart(0);
		if ((primes.get_3dEnd()).compareTo(0)<0)
			primes.set_3dEnd(0);
	}
		
		
	
	public static void create5p3pIntervals(RefSeqGene refSeqGene,BufferedWriter bufferedWriter,RefSeqGeneIntervalsInformation information){
		char strand;
		int txStart,txEnd;
		
		FivePrimeThreePrime primes = new FivePrimeThreePrime();
				
		strand = refSeqGene.getStrand();
		txStart = refSeqGene.getTranscriptionStartPosition();
		txEnd = refSeqGene.getTranscriptionEndPosition();
		
		switch(strand){
		
		//Always from 5 prime to 3 prime
		//For + strand	gene starts at txStart, 5 prime is on the left hand side of txStart, 3 prime is on the right hand side of txEnd
		//For + strand: 5 prime ------- txStart----------(gene starts at txStart and goes to txEnd)-------------> txEnd------- 3 prime, always from 5 prime to 3 prime			
			
			case '+': 	
						//Write 5p1 [txStart-2000, txStart-1]			
						primes.set_5p1Start(txStart-2000);
						primes.set_5p1End(txStart-1);
						
						//Write 5p2 [txStart-10000,txStart-2001]
						primes.set_5p2Start(txStart-10000);
						primes.set_5p2End(txStart-2001);
						
						//Write 5d [txStart-100000,txStart-10001] 
						primes.set_5dStart(txStart-100000);
						primes.set_5dEnd(txStart-10001);
											
						//Write 3p1 [txEnd+1,txEnd+2000]
						primes.set_3p1Start(txEnd+1);
						primes.set_3p1End(txEnd+2000);
					
						//Write 3p2 [txEnd+2001,txEnd+10000]
						primes.set_3p2Start(txEnd+2001);
						primes.set_3p2End(txEnd+10000);
	
						//Write 3d [txEnd+10001,txEnd+100000]
						primes.set_3dStart(txEnd+10001);
						primes.set_3dEnd(txEnd +100000);				
						break;
			//Always from 5 prime to 3 prime
			//For - strand  gene starts at txEnd, 5 prime is on right hand side of txEnd, 3 prime is on the left hand side of txStart			
			//For - strand: 3 prime ------- txStart <------------(gene starts at txEnd and goes to txStart)---------------txEnd------- 5 prime, always from 5 prime to 3 prime			
			case '-': 
						//Write 5p1 [txEnd+1, txEnd+2000]
	                    primes.set_5p1Start(txEnd+1);				
	                    primes.set_5p1End(txEnd+2000);
	                    
						//Write 5p2 [txEnd+2001,txEnd+10000]
	                    primes.set_5p2Start(txEnd+2001);				
	                    primes.set_5p2End(txEnd+10000);
	                    
	                    //Write 5d [txEnd+10001,txEnd+100000] 
	                    primes.set_5dStart(txEnd+10001);				
	                    primes.set_5dEnd(txEnd+100000);
	                                        	
						//Write 3p1 [txStart-2000,txStart-1]
	                    primes.set_3p1Start(txStart-2000);				
	                    primes.set_3p1End(txStart-1);
	                    
						//Write 3p2 [txStart-10000,txStart-2001]
	                    primes.set_3p2Start(txStart-10000);				
	                    primes.set_3p2End(txStart-2001);
	                    
						//Write 3d [txStart-100000,txStart-10001]
	                    primes.set_3dStart(txStart-100000);				
	                    primes.set_3dEnd(txStart-10001);                    
						break;			
						
		}// End of Switch

//		We have to check for invalid intervals
//		Such as position number less than zero or position number greater than corresponding chromosome length
//		Position number greater than corresponding chromosome length might not be a problem
//		However position number less than zero must be avoided. 
		
		checkforValidInterval(primes);
		
		
		try {
						
				bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + primes.get_5p1Start() + "\t" + primes.get_5p1End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.FIVE_P_ONE.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t" + refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));
				bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + primes.get_5p2Start() + "\t" + primes.get_5p2End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.FIVE_P_TWO.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t" + refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));
				bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + primes.get_5dStart() + "\t" + primes.get_5dEnd() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t"   + IntervalName.FIVE_D.convertEnumtoString()   + "\t" + "0" + "\t" + strand + "\t" + refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));

				bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + primes.get_3p1Start() + "\t" + primes.get_3p1End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.THREE_P_ONE.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));
				bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + primes.get_3p2Start() + "\t" + primes.get_3p2End() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" + IntervalName.THREE_P_TWO.convertEnumtoString() + "\t" + "0" + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));
				bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + primes.get_3dStart()+ "\t" + primes.get_3dEnd() + "\t" + refSeqGene.getRefSeqGeneName() + "\t" + refSeqGene.getGeneId() + "\t" 	  + IntervalName.THREE_D.convertEnumtoString() +  "\t" + "0" + "\t" + strand + "\t"+ refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));

				bufferedWriter.flush();
		
				information.setNumberof5p1s(information.getNumberof5p1s()+1);
				information.setNumberof5p2s(information.getNumberof5p2s()+1);
				information.setNumberof5ds(information.getNumberof5ds()+1);
				
				information.setNumberof3p1s(information.getNumberof3p1s()+1);
				information.setNumberof3p2s(information.getNumberof3p2s()+1);
				information.setNumberof3ds(information.getNumberof3ds()+1);
				
				information.setNumberofRefSeqGeneIntervals(information.getNumberofRefSeqGeneIntervals()+6);
					
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		Let's free the space for primes
		primes = null;
	}
	
	public static void createExonIntronIntervals(RefSeqGene refSeqGene,int i, BufferedWriter bufferedWriter, RefSeqGeneIntervalsInformation information){
		int j;
				
		try {	
			//Write exon and intron intervals 			
			for( j =0; j< refSeqGene.getExonCounts()-1; j++){
									
					bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + refSeqGene.getExonStarts().get(j) + "\t" + refSeqGene.getExonEnds().get(j) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId()+ "\t" + IntervalName.EXON.convertEnumtoString() + "\t" +(j+1) + "\t"+ refSeqGene.getStrand() + "\t" +refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));			
					bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + (refSeqGene.getExonEnds().get(j)+1) + "\t" + (refSeqGene.getExonStarts().get(j+1)-1) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + IntervalName.INTRON.convertEnumtoString() + "\t" + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" + refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					information.setNumberofExons(information.getNumberofExons()+1);
					information.setNumberofIntrons(information.getNumberofIntrons()+1);
					information.setNumberofRefSeqGeneIntervals(information.getNumberofRefSeqGeneIntervals()+2);	
			}
			//Write the last exon which is not written in the for loop
			bufferedWriter.write(refSeqGene.getChromName().convertEnumtoString() + "\t" + refSeqGene.getExonStarts().get(j) + "\t" + refSeqGene.getExonEnds().get(j) + "\t" + refSeqGene.getRefSeqGeneName() + "\t"+ refSeqGene.getGeneId() + "\t" + IntervalName.EXON.convertEnumtoString() + "\t" + (j+1) + "\t"+ refSeqGene.getStrand() + "\t" +refSeqGene.getAlternateGeneName()+System.getProperty("line.separator"));
			bufferedWriter.flush();
			
			information.setNumberofExons(information.getNumberofExons()+1);
			information.setNumberofRefSeqGeneIntervals(information.getNumberofRefSeqGeneIntervals()+1);
				 
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	

	
	public static void fillUnsortedChromBaseRefSeqGeneIntervalFiles(List<RefSeqGene> refSeqGeneList, List<BufferedWriter> bufferedWriterList){
		RefSeqGene refSeqGene = null;
		BufferedWriter bufferedWriter = null;
		
		ChromosomeName chromName;
		
		RefSeqGeneIntervalsInformation information = new RefSeqGeneIntervalsInformation();
		
		
		for (int i =0; i<refSeqGeneList.size(); i++){
			refSeqGene = refSeqGeneList.get(i);
			chromName = refSeqGene.getChromName();
						
			bufferedWriter = ChromosomeBasedFilesandOperations.getBufferedWriter(chromName, bufferedWriterList);
			//Pay attention, bufferedWriter is null for such refseq genes
			//chr6_ssto_hap7	LY6G5B
			//GlanetRunner.appendLog(refSeqGene.getChromName() + "\t"+refSeqGene.getAlternateGeneName() );
		
			
			if (bufferedWriter!=null){
				createExonIntronIntervals(refSeqGene,i,bufferedWriter,information);			
				create5p3pIntervals(refSeqGene,bufferedWriter,information);
			}
		}//End of for
		
		GlanetRunner.appendLog("Number of intervals in exons of refseq genes: " + information.getNumberofExons());
		GlanetRunner.appendLog("Number of intervals in introns of refseq genes: " + information.getNumberofIntrons());
		
		GlanetRunner.appendLog("Number of intervals in 5p1s of refseq genes: " + information.getNumberof5p1s());
		GlanetRunner.appendLog("Number of intervals in 5p2s of refseq genes: " + information.getNumberof5p2s());
		GlanetRunner.appendLog("Number of intervals in 5ds of refseq genes: " + information.getNumberof5ds());
		
		GlanetRunner.appendLog("Number of intervals in 3p1s of refseq genes: " + information.getNumberof3p1s());
		GlanetRunner.appendLog("Number of intervals in 3p2s of refseq genes: " + information.getNumberof3p2s());
		GlanetRunner.appendLog("Number of intervals in 3ds of refseq genes: " + information.getNumberof3ds());
		
		GlanetRunner.appendLog("Number of intervals in refseq genes: " + information.getNumberofRefSeqGeneIntervals());
		
	}

	
	
	

	
	
	
	public static void readUnsortedChromBaseRefSeqGeneFilesSortWriteSortedChromBaseRefSeqGeneFiles(List<BufferedReader> unsortedBufferedReaderList, List<BufferedWriter> sortedBufferedWriterList){
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		String strLine =null;
		
		int indexofFirstTab =0;
		int indexofSecondTab =0;
		int indexofThirdTab =0;
		int indexofFourthTab =0;
		int indexofFifthTab =0;
		int indexofSixthTab =0;		
		int indexofSeventhTab = 0;
		int indexofEigthTab = 0;
		
		for(int i=0; i<24 ; i++){
			
			bufferedReader = unsortedBufferedReaderList.get(i);
			bufferedWriter = sortedBufferedWriterList.get(i);
						
			IntervalTree refSeqGeneIntervalTree = new IntervalTree();
			RefSeqGeneInterval refSeqGeneInterval = new RefSeqGeneInterval();
			
			try {
				while ((strLine = bufferedReader.readLine()) != null)   {
					  // ADD the content to the ArrayList
						
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
					indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
					indexofEigthTab 	= strLine.indexOf('\t',indexofSeventhTab+1);
					
//					example line
//					chrY	16636453	16636816	NR_028319	22829	Exon 1	+	NLGN4Y
//					chrY	16636453	16636816	NR_028319	22829	5p1  0 	+	NLGN4Y

					if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || (indexofFifthTab<0) || (indexofSixthTab<0) || indexofSeventhTab<0){
						GlanetRunner.appendLog("Unexpected format in Unsorted RefSeq Gene File");
						GlanetRunner.appendLog("For chromosome " + i);
						GlanetRunner.appendLog(strLine);								
					}
					
					refSeqGeneInterval.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));							
					refSeqGeneInterval.setIntervalStart(Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab)));
					refSeqGeneInterval.setIntervalEnd(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));
					refSeqGeneInterval.setRefSeqGeneName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
					
					refSeqGeneInterval.setGeneId(Integer.parseInt(strLine.substring(indexofFourthTab+1, indexofFifthTab)));
					
					refSeqGeneInterval.setIntervalName(IntervalName.convertStringtoEnum(strLine.substring(indexofFifthTab+1, indexofSixthTab)));				
					refSeqGeneInterval.setIntervalNumber(Integer.parseInt(strLine.substring(indexofSixthTab+1,indexofSeventhTab)));
					
					refSeqGeneInterval.setStrand(strLine.substring(indexofSeventhTab+1, indexofEigthTab).charAt(0));
					refSeqGeneInterval.setAlternateGeneName(strLine.substring(indexofEigthTab+1));
					
					IntervalTreeNode  node = new UcscRefSeqGeneIntervalTreeNode(refSeqGeneInterval.getChromName(), refSeqGeneInterval.getIntervalStart(), refSeqGeneInterval.getIntervalEnd(),refSeqGeneInterval.getRefSeqGeneName(), refSeqGeneInterval.getGeneId(), refSeqGeneInterval.getIntervalName(),refSeqGeneInterval.getIntervalNumber(), refSeqGeneInterval.getStrand(),refSeqGeneInterval.getAlternateGeneName(),NodeType.ORIGINAL);
					refSeqGeneIntervalTree.intervalTreeInsert(refSeqGeneIntervalTree, node);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
//			write sorted list to file
			refSeqGeneIntervalTree.intervalTreeInfixTraversal(refSeqGeneIntervalTree.getRoot(), bufferedWriter, Commons.HG19_REFSEQ_GENE);
						
			refSeqGeneIntervalTree = null;
			refSeqGeneInterval = null;
			
			
		}//End of for loop
		
	}
	
	public static void createRefSeq2GeneMap(String fileName,Map<String,Integer> refSeq2GeneHashMap){
		
		String strLine;
		int indexofFirstTab; 
		String refSeqName;
		int geneId;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((strLine=bufferedReader.readLine())!=null){
				indexofFirstTab = strLine.indexOf('\t');
				refSeqName = strLine.substring(0, indexofFirstTab);
				geneId = Integer.parseInt(strLine.substring(indexofFirstTab+1));
				
				if(!(refSeq2GeneHashMap.containsKey(refSeqName))){
					refSeq2GeneHashMap.put(refSeqName, geneId);
				}else{
					GlanetRunner.appendLog("RefSeqName already exists " + refSeqName);
				}
			}// End of while
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
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
		
//		TODO  might be done without using refSeqGeneList
		List<RefSeqGene> refSeqGeneList = new ArrayList<RefSeqGene>();
		List<BufferedWriter> unsortedBufferedWriterList = new ArrayList<BufferedWriter>(24);
		List<BufferedReader> unsortedBufferedReaderList = new ArrayList<BufferedReader>(24);		
		List<BufferedWriter> sortedBufferedWriterList = new ArrayList<BufferedWriter>(24);
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		
		Map<String,Integer> refSeq2GeneIdHashMap =  new HashMap<String,Integer>();
		String fileName = dataFolder + Commons.FTP_HG19_REFSEQ_GENES_DOWNLOADED_1_OCT_2014;
		String fileName2 = dataFolder + Commons.NCBI_HUMAN_GENE_TO_REF_SEQ_DIRECTORYNAME + Commons.NCBI_RNANUCLEOTIDEACCESSION_TO_GENEID_1_OCT_2014;
		
		
		//My convention is zero based start and end.
		//Ucsc genome table browser convention: Our internal database representations of coordinates always have a zero-based start and a one-based end.
		//Convert one based end to zero based end in readInputFile()
	
	    
//		ncbi output file will be read into a map
//		using this map for each gene in the refSeqGeneList  geneId will be added
		CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting.createRefSeq2GeneMap(fileName2,refSeq2GeneIdHashMap);
//		augmentation of refSeqGeneName (in other words RNA nucleotide accession version) with entrez gene id 
		CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting.readInputFile(fileName, refSeqGeneList,refSeq2GeneIdHashMap,dataFolder);	
		
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedRefSeqGeneFileWriters(dataFolder,unsortedBufferedWriterList);	    
		CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting.fillUnsortedChromBaseRefSeqGeneIntervalFiles(refSeqGeneList, unsortedBufferedWriterList);		
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(unsortedBufferedWriterList);		
		
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedRefSeqGeneFileReaders(dataFolder,unsortedBufferedReaderList);
		ChromosomeBasedFilesandOperations.openSortedChromosomeBasedRefSeqGeneFiles(dataFolder,sortedBufferedWriterList);		
		CreateIntervalFileUsingUCSCGenomeUsingIntervalTreeSorting.readUnsortedChromBaseRefSeqGeneFilesSortWriteSortedChromBaseRefSeqGeneFiles(unsortedBufferedReaderList,sortedBufferedWriterList);
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedReaders(unsortedBufferedReaderList);
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(sortedBufferedWriterList);
	}

}
