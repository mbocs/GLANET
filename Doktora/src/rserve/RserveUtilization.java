package rserve;

import hg19.GRCh37Hg19Chromosome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;


/**
 * @author Burcak Otlu
 * Jan 30, 2014
 * 5:27:04 PM
 * 2014
 *
 * 
 */

public class RserveUtilization {

	/**
	 * 
	 */
	public RserveUtilization() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static String getDnaSequence(char[] dnaSequenceArray,int start, int end){
		String s = null;
		
		for(int i = start; i<=end; i++){
			s = s+ dnaSequenceArray[i];
		}
		
		return s;
		
	}
	
	
	public static void 	compareChromosomeDNASequences(char[] chromDNASequence){
		RConnection c = null;
		
		try {
			c = new RConnection();
			
			c.voidEval("library(Rserve)");
			c.voidEval("Rserve()");
			c.voidEval("library(BSgenome.Hsapiens.UCSC.hg19)");
			
			
			String chromNumber = "chr17";
			String _strand = "+";
			String s1;
			String s2;
			
			int chromSize = chromDNASequence.length;
			
			for(int i = 1; i< chromSize;){
				int[] _startandEnd={i,i+10};
				
				c.assign("intervalStrand", _strand);
				c.assign("chrom", chromNumber);
				c.assign("startandEnd",_startandEnd);
				
				s1 = c.eval("seq_sd1 <- getSeq( Hsapiens, chrom, start=startandEnd[1], end=startandEnd[2], strand = intervalStrand, as.character=TRUE)").asString();
				s2 = String.copyValueOf(chromDNASequence, i-1, 11);
				
				if ((s1.compareTo(s2))!=0){
					System.out.println("R\t" + s1);
					System.out.println("B\t" + s2);
					System.out.println("i\t"+ i);
					break;
					
				}
				i= i+11;
			}
		
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
	}
	
	public static void createPeakSequencesFile(String directoryBase,String sequenceFileDirectory, String fileName, String peakName, String peakSequence){

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(directoryBase + sequenceFileDirectory + "\\" + fileName + ".txt",true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(">" + peakName + "\n");
			bufferedWriter.write(peakSequence +"\n");
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void createMatrixFile(String directoryBase, String tfNameKeggPathwayNameBased_SnpDirectory, String matrixName,String matrix){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(directoryBase + tfNameKeggPathwayNameBased_SnpDirectory + "\\" +matrixName + ".txt",true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(matrix);
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void createObservedAllelesFile(String directoryBase, String observedAllelesFileDirectory, String fileName,List<String> observedAlleles){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(directoryBase + observedAllelesFileDirectory + "\\" + fileName + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(String observedAllele: observedAlleles){
				bufferedWriter.write(observedAllele + "\n");	
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createSequenceFile(String directoryBase, String sequenceFileDirectory, String fileName,String sequence){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(directoryBase + sequenceFileDirectory + "\\" + fileName + ".txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.write(">" + fileName + "\n");
			bufferedWriter.write(sequence +"\n");
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String   createDirectory(String directoryName,String enrichmentType){
		
		String directoryBase=null;
		
		
		switch(enrichmentType){
		
			case Commons.TF_EXON_BASED_KEGG_PATHWAY:{			
				directoryBase = Commons.TF_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;	
				break;
			}
			
			case Commons.TF_REGULATION_BASED_KEGG_PATHWAY:{				
				directoryBase = Commons.TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;			
				break;
			}
				
			case Commons.TF_ALL_BASED_KEGG_PATHWAY:{			
				directoryBase = Commons.TF_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;			
				break;
			}	
			
			case Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY:{
				directoryBase = Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;		
				break;
			}
			
			case Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY:{
				directoryBase = Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;					
				break;
			}
			
			case Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY:{
				directoryBase = Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE;					
				break;
			}
			
		} // End of switch
		
		File directory = new File(directoryBase + "\\"+ directoryName);

		
		// if the directory does not exist, create it
		  if (!directory.exists()) {
		    System.out.println("creating directory: " + directoryName);
		    boolean result = directory.mkdir();  
		
		     if(!result) {    
		       System.out.println(directoryName + " not created");  
		     }
		  }
		  
		  return directoryBase;
	}
	
	
	public static boolean sameAllele(char snp,String allele){
		if (allele.length()>1){
			return false;
		}else if (allele.equals(Commons.STRING_HYPHEN)){
			return false;
		}else if(allele.charAt(0)==snp){
			return true;
		}
		
		return false;
		
	}
		
	
	public static String getAlteredSequence(String precedingSNP,String allele,String followingSNP){
		
		if(!allele.equals(Commons.STRING_HYPHEN)){
			return precedingSNP + allele + followingSNP;
		}
		
		return null;
		
	}
	
	
	public static List<String>  getAlteredSnpSequences(char snp, String alleles,String precedingSNP,String followingSNP){
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		String alteredSnpSequence;
		List<String> alteredSnpSequences = new ArrayList<String>();
		
		indexofFormerTab = alleles.indexOf('\t');
				
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
		
		//check for this allele
		if (!sameAllele(snp,allele)){
			alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
			
			if (alteredSnpSequence!=null){
				alteredSnpSequences.add(alteredSnpSequence);
			}
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//check for this allele
			if (!sameAllele(snp,allele)){
				alteredSnpSequence = getAlteredSequence(precedingSNP,allele,followingSNP);
				
				if (alteredSnpSequence!=null){
					alteredSnpSequences.add(alteredSnpSequence);
				}
			}
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
		
		return alteredSnpSequences;
	}
	
	//if snp exists in any of tab delimited alleles useAlleles return true
	//else useAlleles return false
	public static boolean useAlleles(char snp,String alleles){
		//alleles is composed by allele each is seperated by tab
		//A\tC\tG\t
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		
		indexofFormerTab = alleles.indexOf('\t');
		
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
				
		//check for this allele
		if (sameAllele(snp,allele)){
			return true;
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//check for this allele
			if (sameAllele(snp,allele)){
				return true;
			}
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
				
		return false;
		
	}
	
	
	public static String takeComplementforeachAllele(String allele){
		
		String complementedAllele = "";
		
		for(char nucleotide: allele.toCharArray()){
			switch(nucleotide) {
				case 'A':
				case 'a': 	complementedAllele = complementedAllele + "T";
							break;
							
				case 'C':
				case 'c': 	complementedAllele = complementedAllele + "G";
							break;
							
				case 'G':
				case 'g': 	complementedAllele = complementedAllele + "C";
							break;
							
				case 'T':
				case 't': 	complementedAllele = complementedAllele + "A";
							break;
							
				default : return null;			
							
			}//End of switch
		}//End of for
		
		return complementedAllele;
	}
	
	public static String takeComplement(String alleles){
		int indexofFormerTab;
		int indexofLatterTab;
		
		String allele;
		String complementedAllele = null;
		String complementedAlleles =  "";
		
		indexofFormerTab = alleles.indexOf('\t');
		
		//get the first allele
		allele = alleles.substring(0,indexofFormerTab);
		
		//take the complement of this allele
		complementedAllele = takeComplementforeachAllele(allele);
		
		if(complementedAllele!=null){
			complementedAlleles = complementedAlleles + complementedAllele + "\t";
		}
		
		indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);

		while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
			allele = alleles.substring(indexofFormerTab+1, indexofLatterTab);
			
			//take the complement of this allele
			complementedAllele = takeComplementforeachAllele(allele);
			
			if(complementedAllele!=null){
				complementedAlleles = complementedAlleles + complementedAllele + "\t";
			}
			
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = alleles.indexOf('\t',indexofFormerTab+1);
		}
				
		return complementedAlleles;
		
	}
	
	//10 March 2014
	public static List<String> findOtherObservedAllelesandGetAltereSequences(char snp, String alleles,String precedingSNP,String followingSNP){
		
		String allele;
		List<String> alteredSnpSequences;
		
		String complementedAlleles;
		
			
		//We must decide whether we can use alleles 
		//or we must use the complement of the alleles
		//if snp is equal to the one of these alleles then use alleles
		//else use the complement of alleles
		if (useAlleles(snp,alleles)){
			alteredSnpSequences = getAlteredSnpSequences(snp,alleles,precedingSNP,followingSNP);
		}else {
			complementedAlleles = takeComplement(alleles);
			alteredSnpSequences = getAlteredSnpSequences(snp,complementedAlleles,precedingSNP,followingSNP);	
		}
			
		
		return alteredSnpSequences;
	}
	
	
	public static List<String> getAlteredSNPSequences(String snpSequence, List<String> observedAlleles,int oneBasedSnpPosition){
		
		String precedingSNP;
		String followingSNP;
		char snp;
		
		List<String> alteredSnpSequences;
		List<String> allAlteredSnpSequences = new ArrayList<String>();
				
		//snpPosition is at Commons.SNP_POSITION; (one-based)
				
		//precedingSNP is 14 characters long
		precedingSNP = snpSequence.substring(0, oneBasedSnpPosition-1);
		
		//followingSNP is 14 characters long
		followingSNP = snpSequence.substring(oneBasedSnpPosition);
		
		//snp
		snp = snpSequence.charAt(oneBasedSnpPosition-1);
		
		
		//take each possible observed alleles
		//C\tT\t
		
		for(String alleles: observedAlleles){
			
			//Find the other alleles other than normal nucleotide
			alteredSnpSequences = findOtherObservedAllelesandGetAltereSequences(snp,alleles,precedingSNP,followingSNP);
			
			allAlteredSnpSequences.addAll(alteredSnpSequences);
			
		}
	
		return allAlteredSnpSequences;
	}
	
	
	public static String getDNASequence(ChromosomeName chromNumber, String _strand, int[] _startandEnd,RConnection c){
		try {
			
			//todo use my own get sequence code for the software 
			c.assign("intervalStrand", _strand);
			c.assign("chrom", chromNumber.convertEnumtoString());
			c.assign("startandEnd",_startandEnd);
			return c.eval("seq_sd1 <- getSeq( Hsapiens, chrom, start=startandEnd[1], end=startandEnd[2], strand = intervalStrand, as.character=TRUE)").asString();

		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	
	}
	
	public static String getTfNamewithoutNumber(String tfName){
		
		int n = tfName.length();
		char c;
		int i;
		
		for (i = 0; i < n; i++) {
		    c = tfName.charAt(i);
		    if (Character.isDigit(c)){
		    	break;
		    }
		}
		
		return tfName.substring(0, i);
	}
	
	//TF KEGGPATHWAY starts
	public static void readAugmentedDataWriteSequencesMatrices(String outputFolder,String augmentedInputFileName, Map<String,String> tfName2PfmMatrices, Map<String,String> tfName2LogoMatrices,String enrichmentType,Map<String,List<String>> chrNameZeroBasedCoordinate2ObservedAlleles){
		
		FileReader augmentedFileReader;
		BufferedReader augmentedBufferedReader;
		
				
		RConnection c = null;
		
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
			
		int indexofUnderscore;
		int indexofSecondUnderScore;
			
		ChromosomeName chromNumber;
		
		int snpLocus_ZeroBased;
		int snpLocus_OneBased;
		
		int tfStart_ZeroBased;
		int tfStart_OneBased;
		
		int tfEnd_ZeroBased;
		int tfEnd_OneBased;
				
		String tfNameCellLineName;
		
		String tfName;
		String tfNamewithoutNumber;
		String cellLineName;
		String keggPathwayName;
		
		//These variables will be used jointly 
		//for Tf CellLine KeggPathway and
		//for Tf KeggPathway 
		String tfNameKeggPathwayName;
		String tfNameKeggPathwayNameSnpChromNumberSnpLocus;		
		String snpChromNumberSnpLocusTfNameKeggPathwayName;
		String tfNameKeggPathwayNameSnpPeak;
		String peaksFileName;
		
		String _strand;
		int[] _startandEnd;
		
		Map<String,Boolean> tf2FalseorTrueMap 			= new HashMap<String,Boolean>();
		Map<String,Boolean> snp2FalseorTrueMap 			= new HashMap<String,Boolean>();
		Map<String,Boolean> peak2FalseorTrueMap 		= new HashMap<String,Boolean>();
		Map<String,Boolean> pfmMatrices2FalseorTrueMap 	= new HashMap<String,Boolean>();
				
		String snpSequence;
		String peakSequence;
		String directoryBase = null;
		String tfNameKeggPathwayNameBased_SnpDirectory = null;
		String peakName ;
				
		Boolean isThereAnExactTfNamePfmMatrix = false;
		
		//10 March 2014
		//Each observedAlleles String contains observed alleles which are separated by tabs, pay attention, there can be more than two observed alleles such as A\tG\tT\t-\tACG
		//Pay attention, for the same chrName and ChrPosition there can be more than one observedAlleles String. It is rare but possible.
		List<String> observedAlleles; 
		List<String> alteredSnpSequences;
					
//		**************	hsa00380 Tryptophan metabolism - Homo sapiens (human)	**************											
//		NFKB_hsa00380	Search for chr	given interval low	given interval high	tfbs	tfbs low	tfbs high	refseq gene name	ucscRefSeqGene low	ucscRefSeqGene high	interval name 	hugo suymbol	entrez id	keggPathwayName
//		NFKB_hsa00380	chr1	89546803	89546803	NFKB_GM12878	89546683	89546992	NM_001008661	89468644	89558643	5D	CCBL2	56267	hsa00380

			try {
			augmentedFileReader = new FileReader(outputFolder + augmentedInputFileName);
			augmentedBufferedReader = new BufferedReader(augmentedFileReader);
							
			c = new RConnection();
			
			c.voidEval("library(Rserve)");
			c.voidEval("Rserve()");
			c.voidEval("library(BSgenome.Hsapiens.UCSC.hg19)");

			while((strLine = augmentedBufferedReader.readLine())!=null){
				
			
				//skip strLine starts with * or contains "Search for" which means it is a header line
				if (!(strLine.startsWith("*")) && !(strLine.contains("Search for"))){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
					indexofSeventhTab 	= strLine.indexOf('\t',indexofSixthTab+1);
					
					tfNameKeggPathwayName = strLine.substring(0, indexofFirstTab);
					chromNumber =  ChromosomeName.convertStringtoEnum(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					snpLocus_ZeroBased = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
						
					tfNameCellLineName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					tfStart_ZeroBased = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					tfEnd_ZeroBased =  Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
					//Get tfName and keggPathwayName
					indexofUnderscore = tfNameKeggPathwayName.indexOf('_');
					tfName = tfNameKeggPathwayName.substring(0, indexofUnderscore);
					
					tfNamewithoutNumber = getTfNamewithoutNumber(tfName);
					
					if (!(enrichmentType.contains(Commons.CELLLINE))){
						keggPathwayName = tfNameKeggPathwayName.substring(indexofUnderscore+1);	
					}else {
						indexofSecondUnderScore =  tfNameKeggPathwayName.indexOf('_', indexofUnderscore+1);
						keggPathwayName = tfNameKeggPathwayName.substring(indexofSecondUnderScore+1);
					}
					
					//Get cellLineName
					indexofUnderscore = tfNameCellLineName.indexOf('_');
					cellLineName = tfNameCellLineName.substring(indexofUnderscore+1);
					
					
					tfNameKeggPathwayNameSnpChromNumberSnpLocus = tfNameKeggPathwayName+ "_" + "snp" + "_" + chromNumber.convertEnumtoString() + "_" + snpLocus_ZeroBased;
					snpChromNumberSnpLocusTfNameKeggPathwayName = "snp" + "_" + chromNumber.convertEnumtoString() + "_" + snpLocus_ZeroBased + "_" + tfNameKeggPathwayName;
					
					peakName = "peak" + "_" +tfNameCellLineName + "_" +tfStart_ZeroBased + "_" +tfEnd_ZeroBased + "_" + tfNameKeggPathwayName;
					tfNameKeggPathwayNameSnpPeak = tfNameKeggPathwayName + chromNumber.convertEnumtoString() + snpLocus_ZeroBased + tfNameCellLineName + tfStart_ZeroBased + tfEnd_ZeroBased;
					peaksFileName = "peaks" +  "_" + chromNumber.convertEnumtoString() + "_" +snpLocus_ZeroBased + "_" + tfNameKeggPathwayName;
					
					
					//create tfNameKeggPathwayName based directory  if no directory has been already created
					if (tf2FalseorTrueMap.get(tfNameKeggPathwayName)==null){
						//create new directory for this tf
						createDirectory(tfNameKeggPathwayName,enrichmentType);
						tf2FalseorTrueMap.put(tfNameKeggPathwayName,true);
					}
						
					//create snp based directory if no directory has been already created
					//get snp sequence
					//write snp sequence to a file under snp directory
					if (snp2FalseorTrueMap.get(tfNameKeggPathwayNameSnpChromNumberSnpLocus)==null){
						tfNameKeggPathwayNameBased_SnpDirectory = tfNameKeggPathwayName + "\\snp_"+ chromNumber.convertEnumtoString() + "_" + snpLocus_ZeroBased;
						
						directoryBase = createDirectory(tfNameKeggPathwayNameBased_SnpDirectory, enrichmentType);
			
						//convert from zeroBased to oneBased
						snpLocus_OneBased = snpLocus_ZeroBased +1;
						
						//for debug purposes start
						if(chromNumber.isCHROMOSOME8() && snpLocus_ZeroBased == 143887956){
							System.out.println("stop here");
						}
						//for debug purposes end
						
						_strand = "+";
						
						_startandEnd = new int[]{snpLocus_OneBased-Commons.NUMBER_OF_BASES_BEFORE_SNP_POSITION,snpLocus_OneBased+Commons.NUMBER_OF_BASES_AFTER_SNP_POSITION};
												
						snpSequence = getDNASequence(chromNumber,_strand,_startandEnd,c);		
					
						//for snp sequence
						createSequenceFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory, snpChromNumberSnpLocusTfNameKeggPathwayName,snpSequence);
					
						
						//10 March 2014 starts
						observedAlleles = chrNameZeroBasedCoordinate2ObservedAlleles.get(chromNumber + "_" + snpLocus_ZeroBased);
						alteredSnpSequences = getAlteredSNPSequences(snpSequence,observedAlleles,Commons.ONE_BASED_SNP_POSITION);
									
						//create Altered SNP Sequences
						int alteredSNPSequenceCount = 0;
						for(String alteredSnpSequence : alteredSnpSequences){
							alteredSNPSequenceCount++;
							createSequenceFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory, "altered" + alteredSNPSequenceCount + "_" +snpChromNumberSnpLocusTfNameKeggPathwayName,alteredSnpSequence);	
						}
						
						//write observedAlleles
						createObservedAllelesFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory, "observedAllelesfor" + "_" +snpChromNumberSnpLocusTfNameKeggPathwayName,observedAlleles);
						//10 March 2014 ends
						
						
						snp2FalseorTrueMap.put(tfNameKeggPathwayNameSnpChromNumberSnpLocus, true);

					}
					
					//get peak sequence
					//write the peak sequence to a file under snp directory
					if(peak2FalseorTrueMap.get(tfNameKeggPathwayNameSnpPeak) == null){
						
						//convert from zeroBased to oneBased
						tfStart_OneBased =tfStart_ZeroBased+1;
						tfEnd_OneBased =tfEnd_ZeroBased+1;
						
						_strand = "+";
					
						_startandEnd = new int[]{tfStart_OneBased,tfEnd_OneBased};
					
						peakSequence = getDNASequence(chromNumber,_strand,_startandEnd,c);
						
						createPeakSequencesFile(directoryBase,tfNameKeggPathwayNameBased_SnpDirectory,peaksFileName,peakName,peakSequence);
						
						peak2FalseorTrueMap.put(tfNameKeggPathwayNameSnpPeak, true);
					}
						
					
					
					
					//pfmMatrices and logo Matrices
					if(pfmMatrices2FalseorTrueMap.get(tfNameKeggPathwayNameSnpChromNumberSnpLocus) == null){
						
						isThereAnExactTfNamePfmMatrix = false;
						
						//find pfm entry							
						for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
							if (pfmEntry.getKey().contains(tfName)){
								isThereAnExactTfNamePfmMatrix = true;
								createMatrixFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory,  "pfmMatrices_" + tfName,pfmEntry.getValue());
									
							}
						}//End of for
						
						
						//find logo entry
						for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
							if(logoEntry.getKey().contains(tfName)){
								createMatrixFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory, "logoMatrices_" +tfName,logoEntry.getValue());

							}
						}

						
						if (!isThereAnExactTfNamePfmMatrix){

							//find pfm entry							
							for(Map.Entry<String, String> pfmEntry:tfName2PfmMatrices.entrySet()){
								if (pfmEntry.getKey().contains(tfNamewithoutNumber)){
									createMatrixFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory, "pfmMatrices_" + tfName,pfmEntry.getValue());									
								}
							}//End of for
							
							//find logo entry
							for(Map.Entry<String, String> logoEntry:tfName2LogoMatrices.entrySet()){
								if(logoEntry.getKey().contains(tfNamewithoutNumber)){
									createMatrixFile(directoryBase, tfNameKeggPathwayNameBased_SnpDirectory, "logoMatrices_" +tfName,logoEntry.getValue());

								}
							}
							
						}
						
						pfmMatrices2FalseorTrueMap.put(tfNameKeggPathwayNameSnpChromNumberSnpLocus, true);
					} //End of if
					
											
				}//End of if: strLine does not start with "*"	and contains "Search for"	
				
				
			}//End of while 
			
			c.close();
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (REXPMismatchException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (REngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	//TF KEGGPATHWAY ends
	
	//Fill CountList using CountLine
	public static void fillCountList(String countLine,List<Integer> countList){
		//example Count line
		//4	19	0	0	0	0		
		int indexofFormerTab =0;
		int indexofLatterTab =0;
		
		int count =0;
		
		indexofFormerTab = 0;
		indexofLatterTab = countLine.indexOf('\t');
		
		
		//Insert the first count
		if (indexofLatterTab>=0){
			count = Integer.parseInt(countLine.substring(indexofFormerTab, indexofLatterTab));
			countList.add(count);			
		}
		
		
		indexofFormerTab = indexofLatterTab;
		indexofLatterTab = countLine.indexOf('\t',indexofFormerTab+1);

		
		//Insert the rest of the counts
		while(indexofLatterTab>=0){
			
			count = Integer.parseInt(countLine.substring(indexofFormerTab+1, indexofLatterTab));
			
			countList.add(count);
			
			indexofFormerTab = indexofLatterTab;
			indexofLatterTab = countLine.indexOf('\t',indexofFormerTab+1);
			
		}
		
		//Insert the last count
		if(indexofFormerTab>=0){
			count = Integer.parseInt(countLine.substring(indexofFormerTab+1));
			countList.add(count);
			
		}
	
	}
	
	
	public static void fillFrequencyListUsingCountList(List<Float> frequencyList, List<Integer> countList,Integer totalCount){
		
		Iterator<Integer> iterator = countList.iterator();
		
		Integer count;
		Float frequency;
		
		
		while(iterator.hasNext()){
			
			count = iterator.next();
			frequency = (count*1.0f)/totalCount;
			
			frequencyList.add(frequency);
			
			
		}
		
		
	}
	
	
	public static int getTotalCount(List<Integer> ACountList,List<Integer> CCountList,List<Integer> GCountList,List<Integer>TCountList){
		
		Iterator<Integer> iteratorA = ACountList.iterator();
		Integer countA;
		
		
		Iterator<Integer> iteratorC = CCountList.iterator();
		Integer countC;
		
		Iterator<Integer> iteratorG = GCountList.iterator();
		Integer countG;
		
		Iterator<Integer> iteratorT = TCountList.iterator();
		Integer countT;
	
		int totalCount = 0;;
		
		while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()  ){
			
			countA = iteratorA.next();
			countC = iteratorC.next();
			countG = iteratorG.next();
			countT = iteratorT.next();
			
			totalCount = countA + countC + countG + countT;
			return totalCount;
			
		
			
		}
		
		return totalCount;
		
		
	}
	
	
	public static void putLogoMatrix(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,Map<String,String> tfName2LogoMatrices){
		
//		Example logo matrix
//		G 0.008511 0.004255 0.987234 0.000000		
//		A 0.902127 0.012766 0.038298 0.046809		
//		R 0.455319 0.072340 0.344681 0.127660		
//		W 0.251064 0.085106 0.085106 0.578724		
//		T 0.000000 0.046809 0.012766 0.940425		
//		G 0.000000 0.000000 1.000000 0.000000		
//		T 0.038298 0.021277 0.029787 0.910638		
//		A 0.944681 0.004255 0.051064 0.000000		
//		G 0.000000 0.000000 1.000000 0.000000		
//		T 0.000000 0.000000 0.012766 0.987234		

		Iterator<Float> iteratorA = AFrequencyList.iterator();
		Iterator<Float> iteratorC = CFrequencyList.iterator();
		Iterator<Float> iteratorG = GFrequencyList.iterator();
		Iterator<Float> iteratorT = TFrequencyList.iterator();
		
		Float frequencyA;
		Float frequencyC;
		Float frequencyG;
		Float frequencyT;
	
		String strLine = null;
		
		if (tfName2LogoMatrices.get(tfName) == null){
			tfName2LogoMatrices.put(tfName, tfName + "\n");		
		}
		
		else{
			tfName2LogoMatrices.put(tfName,tfName2LogoMatrices.get(tfName)+ tfName + "\n");			
		}
			
			
			
		while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
			
			frequencyA = iteratorA.next();
			frequencyC = iteratorC.next();
			frequencyG = iteratorG.next();
			frequencyT = iteratorT.next();
			
			strLine = "X" + "\t" + frequencyA + "\t" + frequencyC + "\t" + frequencyG + "\t" + frequencyT +"\n";
			tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName) + strLine);
			
			
		}//end of while

	
	}
	
	
	
	public static void putPFM(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,Map<String,String> tfName2PfmMatrices){
		
		//example matrix in tab format
		//		; NFKB_known3	NFKB_1	NF-kappaB_transfac_M00054														
		//		A |	0	0	0.025	0.675	0.525	0.2	0.025	0.05	0.075	0						
		//		C |	0	0	0	0	0.325	0.025	0.05	0.45	0.9	0.95						
		//		G |	1	1	0.975	0.325	0.025	0.075	0.05	0	0	0						
		//		T |	0	0	0	0	0.125	0.7	0.875	0.5	0.025	0.05						
		//		//			
		
		Iterator<Float> iteratorA = AFrequencyList.iterator();
		Iterator<Float> iteratorC = CFrequencyList.iterator();
		Iterator<Float> iteratorG = GFrequencyList.iterator();
		Iterator<Float> iteratorT = TFrequencyList.iterator();
		
		Float frequencyA;
		Float frequencyC;
		Float frequencyG;
		Float frequencyT;
		
		String strLineA = "";
		String strLineC = "";
		String strLineG = "";
		String strLineT = "";
		
		while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
			
			frequencyA = iteratorA.next();
			frequencyC = iteratorC.next();
			frequencyG = iteratorG.next();
			frequencyT = iteratorT.next();
			
			strLineA = strLineA  + "\t"  + frequencyA;
			strLineC = strLineC  + "\t"  + frequencyC;
			strLineG = strLineG  + "\t"  + frequencyG;
			strLineT = strLineT  + "\t"  + frequencyT;
			
		}//end of while

		
		//this tfName has no previous position frequency matrix inserted
		if(tfName2PfmMatrices.get(tfName)==null){
			tfName2PfmMatrices.put(tfName, "; " + tfName + "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "A|"+ strLineA+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "C|"+ strLineC+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "G|"+ strLineG+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "T|"+ strLineT+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "//"+  "\n");
			
		}
		//this tfName already has position frequency matrices
		//append the new position frequency matrix
		else{
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "; " + tfName + "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "A|"+ strLineA+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "C|"+ strLineC+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "G|"+ strLineG+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "T|"+ strLineT+  "\n");
			tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "//"+  "\n");
				
		}

		
		
		
	}

	
	public static void writeLogoMatrix(String tfName,List<Float> AFrequencyList,List<Float> CFrequencyList,List<Float> GFrequencyList,List<Float> TFrequencyList,BufferedWriter bufferedWriter){
//		Example position frequency matrix for logo generation
//		G 0.008511 0.004255 0.987234 0.000000		
//		A 0.902127 0.012766 0.038298 0.046809		
//		R 0.455319 0.072340 0.344681 0.127660		
//		W 0.251064 0.085106 0.085106 0.578724		
//		T 0.000000 0.046809 0.012766 0.940425		
//		G 0.000000 0.000000 1.000000 0.000000		
//		T 0.038298 0.021277 0.029787 0.910638		
//		A 0.944681 0.004255 0.051064 0.000000		
//		G 0.000000 0.000000 1.000000 0.000000		
//		T 0.000000 0.000000 0.012766 0.987234		

		Iterator<Float> iteratorA = AFrequencyList.iterator();
		Iterator<Float> iteratorC = CFrequencyList.iterator();
		Iterator<Float> iteratorG = GFrequencyList.iterator();
		Iterator<Float> iteratorT = TFrequencyList.iterator();
		
		Float frequencyA;
		Float frequencyC;
		Float frequencyG;
		Float frequencyT;
	
		String strLine = null;
		
		try {
			
			bufferedWriter.write(tfName + "\n");
			
			while(iteratorA.hasNext() && iteratorC.hasNext() && iteratorG.hasNext() && iteratorT.hasNext()){
				
				frequencyA = iteratorA.next();
				frequencyC = iteratorC.next();
				frequencyG = iteratorG.next();
				frequencyT = iteratorT.next();
				
				strLine = "X" + "\t" + frequencyA + "\t" + frequencyC + "\t" + frequencyG + "\t" + frequencyT +"\n";
				bufferedWriter.write(strLine);
				
			}//end of while

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void constructObservedAllelesMap(String outputFolder,String observedAllelesInputFileName,Map<String,List<String>> chrNameChrCoordinate2ObservedAlleles){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String  strLine;
		
		String rsId;
		String chrName;
		int chrZeroBasedCoordinate;
		List<String> observedAlleles;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		int indexofFormerTab;
		int indexofLatterTab;
		
		String alleles;
		String key;
		
		try {
				fileReader = new FileReader(outputFolder + observedAllelesInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
					//example line
					//rs954980 	chr9	3761908	A	G
//					rs11777634 	chr8	143887956	C	T
//					rs199911610 chr8	143887956	-	GCT
//					rs1121707 	chr5	120293327	A	G	T

					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t', indexofSecondTab+1);
					
					rsId = strLine.substring(0,indexofFirstTab);
					chrName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
					chrZeroBasedCoordinate = Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
					
					indexofFormerTab = indexofThirdTab;
					indexofLatterTab = strLine.indexOf('\t', indexofFormerTab+1);
					
					//Initialise alleles for each read line
					alleles = "";
					
					while(indexofFormerTab!=-1 && indexofLatterTab!=-1){
						alleles = alleles + strLine.substring(indexofFormerTab+1, indexofLatterTab) +"\t" ;
												
						indexofFormerTab = indexofLatterTab;
						indexofLatterTab = strLine.indexOf('\t', indexofFormerTab+1);						
					}
					
						
					//Add readLine to to hash map					
					key = chrName + "_" + chrZeroBasedCoordinate;
					
					observedAlleles = chrNameChrCoordinate2ObservedAlleles.get(key);
					
					if (observedAlleles == null){
						observedAlleles = new ArrayList<String>();
						observedAlleles.add(alleles);	
						chrNameChrCoordinate2ObservedAlleles.put(key, observedAlleles);				
					}else {
//						rs2225363 	chr10	111787715	A	C	G	
//						rs370543953 	chr10	111787715	C	CG	T	
						observedAlleles.add(alleles);
						chrNameChrCoordinate2ObservedAlleles.put(key, observedAlleles);	
					}
					
					

					
				}
			
			
			bufferedReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	public static void constructPfmMatricesandLogoMatricesfromJasparCore(String dataFolder,String jasparCoreInputFileName,Map<String,String> tfName2PfmMatrices,Map<String,String>  tfName2LogoMatrices){
		//Attention
		//Order is ACGT
				
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
		
		String tfName = null;
		
		int whichLine = 0;
		
		final int headerLine= 0;
		final int ALine = 1;
		final int CLine = 2;
		final int GLine = 3;
		final int TLine = 4;
		
		List<Integer> ACountList = null;
		List<Float>	AFrequencyList = null;
		
		List<Integer> CCountList = null;
		List<Float>	CFrequencyList = null;
		
		List<Integer> GCountList = null;
		List<Float>	GFrequencyList = null;
	
		List<Integer> TCountList = null;
		List<Float>	TFrequencyList = null;
		
		int totalCount;
	
		try {
			fileReader = new FileReader(dataFolder + jasparCoreInputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			
//			Example matrix from jaspar core pfm_all.txt
//			>MA0004.1 Arnt																													
//			4	19	0	0	0	0																								
//			16	0	20	0	0	0																								
//			0	1	0	20	0	20																								
//			0	0	0	0	20	0																								

			
			while((strLine = bufferedReader.readLine())!=null){
				if (strLine.startsWith(">")){
					tfName = strLine.substring(1);
					
					//Initialize array lists
					//for the new coming position count matrix and position frequency matrix
					ACountList = new ArrayList<Integer>();
					AFrequencyList = new ArrayList<Float>();
					
					CCountList = new ArrayList<Integer>();
					CFrequencyList = new ArrayList<Float>();
					
					GCountList = new ArrayList<Integer>();
					GFrequencyList = new ArrayList<Float>();
				
					TCountList = new ArrayList<Integer>();
					TFrequencyList = new ArrayList<Float>();
					
					whichLine = ALine;
					continue;
				}
				
				switch(whichLine){			
					case ALine:	{	
									fillCountList(strLine,ACountList);
									whichLine = CLine;
									break;
								}
											
					case CLine:	{
									fillCountList(strLine,CCountList);							
									whichLine = GLine;
									break;
								}
										
					case GLine: {		
									fillCountList(strLine,GCountList);					
									whichLine = TLine;
									break;
								}
						
					case TLine:{
									fillCountList(strLine,TCountList);		
									whichLine = headerLine;
									
									//Since count lists are available
									//Then compute frequency lists
									totalCount = getTotalCount(ACountList,CCountList,GCountList,TCountList);
									fillFrequencyListUsingCountList(AFrequencyList,ACountList,totalCount);
									fillFrequencyListUsingCountList(CFrequencyList,CCountList,totalCount);
									fillFrequencyListUsingCountList(GFrequencyList,GCountList,totalCount);
									fillFrequencyListUsingCountList(TFrequencyList,TCountList,totalCount);
									
									//Now put the new matrix to the hashmap in tab format
									putPFM(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,tfName2PfmMatrices);
									
									//Put the transpose of the matrix for logo generation
									putLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,tfName2LogoMatrices);
//									writeLogoMatrix(tfName,AFrequencyList,CFrequencyList,GFrequencyList,TFrequencyList,bufferedWriter);
	
									break;
									
								}
										
				}//End of switch
							
			}//End of while
			
			bufferedReader.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public static void constructLogoMatricesfromEncodeMotifs(String dataFolder,String encodeMotifsInputFileName,Map<String,String>  tfName2LogoMatrices){
		
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
				
		//Attention
		//Order is ACGT
		String tfName = null;
		
		
		try {
				fileReader = new FileReader(dataFolder +  encodeMotifsInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
//					Encode-motif matrix
//					Order is ACGT					
//					>NFKB_disc1 NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
//					K 0.000000 0.000000 0.737500 0.262500
//					G 0.000000 0.000000 1.000000 0.000000
//					G 0.000000 0.000000 1.000000 0.000000
//					R 0.570833 0.000000 0.429167 0.000000
//					A 0.837500 0.158333 0.004167 0.000000
//					W 0.395833 0.000000 0.000000 0.604167
//					T 0.000000 0.004167 0.000000 0.995833
//					Y 0.000000 0.383333 0.000000 0.616667
//					C 0.000000 1.000000 0.000000 0.000000
//					C 0.000000 1.000000 0.000000 0.000000
					
					
					if (strLine.startsWith(">")){
						
						//start reading from first character, skip '>' character
						tfName = strLine.substring(1);
						
						if (tfName2LogoMatrices.get(tfName)==null){
							tfName2LogoMatrices.put(tfName, strLine+ "\n");
							
						}else{
							tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName)+ strLine + "\n");	
						}
							
					}//End of if
						
						
						
					else{
						tfName2LogoMatrices.put(tfName, tfName2LogoMatrices.get(tfName)+ strLine+ "\n");
					}
						
				}//end of while
				

				
				bufferedReader.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	public static void constructPfmMatricesfromEncodeMotifs(String dataFolder,String encodeMotifsInputFileName,Map<String,String> tfName2PfmMatrices){
		FileReader fileReader ;
		BufferedReader bufferedReader;
		String strLine;
		
		int indexofFirstBlank;
		int indexofSecondBlank;
		int indexofThirdBlank;
		int indexofFourthBlank;
		
		float _AFrequency;
		float _CFrequency;
		float _GFrequency;
		float _TFrequency;
		
		
		List<PositionFrequency> positionfrequencyList = new ArrayList<PositionFrequency>();;
		
		//Attention
		//Order is ACGT
		String ALine = "";
		String CLine = "";
		String GLine = "";
		String TLine = "";
		
		
		int indexofUnderscore;
		
		String tfName = null;
		String formerTfName = null;
		
		Iterator<PositionFrequency> iterator;
		
		try {
				fileReader = new FileReader(dataFolder + encodeMotifsInputFileName);
				bufferedReader = new BufferedReader(fileReader);
				
				while((strLine = bufferedReader.readLine())!=null){
					
//					Encode-motif matrix
//					Order is ACGT					
//					>NFKB_disc1 NFKB1_GM19193_encode-Snyder_seq_hsa_IgG-rab_r1:MDscan#1#Intergenic
//					K 0.000000 0.000000 0.737500 0.262500
//					G 0.000000 0.000000 1.000000 0.000000
//					G 0.000000 0.000000 1.000000 0.000000
//					R 0.570833 0.000000 0.429167 0.000000
//					A 0.837500 0.158333 0.004167 0.000000
//					W 0.395833 0.000000 0.000000 0.604167
//					T 0.000000 0.004167 0.000000 0.995833
//					Y 0.000000 0.383333 0.000000 0.616667
//					C 0.000000 1.000000 0.000000 0.000000
//					C 0.000000 1.000000 0.000000 0.000000
					
					
					if (strLine.startsWith(">")){
						
						indexofUnderscore = strLine.indexOf('_');
						
						//start reading from first character, skip '>' character
						tfName = strLine.substring(1);
							
						if(formerTfName!=null){
							//Write former positionfrequencyList to the output file starts
							//if it is full
							ALine ="A |\t";
							CLine ="C |\t";
							GLine ="G |\t";
							TLine ="T |\t";
									
							iterator = positionfrequencyList.iterator();
							
							while(iterator.hasNext()){
								PositionFrequency positionFrequency = (PositionFrequency) iterator.next();
								ALine = ALine + positionFrequency.get_AFrequency() + "\t";
								CLine = CLine + positionFrequency.get_CFrequency() + "\t";
								GLine = GLine + positionFrequency.get_GFrequency() + "\t";
								TLine = TLine + positionFrequency.get_TFrequency() + "\t";
							}
							
							
							//We must have the former tfName
							//We must have inserted the header line
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + ALine +"\n");
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + CLine +"\n");
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + GLine +"\n");
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + TLine +"\n");
							tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + "//"  +"\n");
							//Write former full pfList to the output file ends
						}//End of if
						
						
						//if tfName is inserted for the first time
						if (tfName2PfmMatrices.get(tfName)==null){
							tfName2PfmMatrices.put(tfName, "; " + strLine.substring(1) + "\n");
						}
						//else start appending the new coming matrix to the already existing matrices for this tfName
						else{
							tfName2PfmMatrices.put(tfName, tfName2PfmMatrices.get(tfName) + "; " + strLine.substring(1) + "\n");
						}
						
						//Initialize positionfrequencyList
						positionfrequencyList = new ArrayList<PositionFrequency>();
						
					}else{
						
						indexofFirstBlank 	= strLine.indexOf(' ');
						indexofSecondBlank 	= strLine.indexOf(' ',indexofFirstBlank+1);
						indexofThirdBlank 	= strLine.indexOf(' ',indexofSecondBlank+1);
						indexofFourthBlank 	= strLine.indexOf(' ',indexofThirdBlank+1);
						
						_AFrequency = Float.parseFloat(strLine.substring(indexofFirstBlank+1, indexofSecondBlank));
						_CFrequency = Float.parseFloat(strLine.substring(indexofSecondBlank+1, indexofThirdBlank));
						_GFrequency = Float.parseFloat(strLine.substring(indexofThirdBlank+1, indexofFourthBlank));
						_TFrequency	= Float.parseFloat(strLine.substring(indexofFourthBlank+1));
						
						PositionFrequency positionFrequency = new PositionFrequency(_AFrequency,_CFrequency,_GFrequency,_TFrequency);
						positionfrequencyList.add(positionFrequency);
						formerTfName = tfName;
					}
						
				}//end of while
				
				//Write the last positionFrequencyList starts
				//Order is ACGT
				ALine ="A |\t";
				CLine ="C |\t";
				GLine ="G |\t";
				TLine ="T |\t";
			
				iterator = positionfrequencyList.iterator();
				
				while(iterator.hasNext()){
					PositionFrequency positionFrequency = (PositionFrequency) iterator.next();
					ALine = ALine + positionFrequency.get_AFrequency() + "\t";
					CLine = CLine + positionFrequency.get_CFrequency() + "\t";
					GLine = GLine + positionFrequency.get_GFrequency() + "\t";
					TLine = TLine + positionFrequency.get_TFrequency() + "\t";
				}
				
				
				//We must use former tfName
				//We must have inserted the header line
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + ALine +"\n");
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + CLine +"\n");
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + GLine +"\n");
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + TLine +"\n");
				tfName2PfmMatrices.put(formerTfName, tfName2PfmMatrices.get(formerTfName)  + "//"  +"\n");
				//Write the last positionFrequencyList ends
				
				bufferedReader.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void connectToR(RConnection  c){
		
		try {
			c = new RConnection();
			c.voidEval("library(Rserve)");
			c.voidEval("Rserve()");
			c.voidEval("library(BSgenome.Hsapiens.UCSC.hg19)");	
		
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	
	public static char[] getHsapiensHg19ChromosomeDNASequence(ChromosomeName chromNumber){
		//for testing purposes
		Map<ChromosomeName, Integer> hg19ChromosomeSizes	= new HashMap<ChromosomeName,Integer>();
		
    	//get the hg19 chromosome sizes
    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
    	
    	int chromSize  = hg19ChromosomeSizes.get(chromNumber);
    	   	
    	return GRCh37Hg19Chromosome.fillHsapiensHg19Chromosome(chromNumber,chromSize);
		
			
	}
	
	public static void getHsapiensHg19DNASequences(Map<ChromosomeName,char[]> hg19ChromosomeBasedDNASequences){
		
		//for testing purposes
		Map<ChromosomeName, Integer> hg19ChromosomeSizes	= new HashMap<ChromosomeName,Integer>();
		
    	//get the hg19 chromosome sizes
    	GRCh37Hg19Chromosome.getHg19ChromosomeSizes(hg19ChromosomeSizes, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		
    	//fill hg 19 chromosome based dna sequence arrays
		GRCh37Hg19Chromosome.fillHsapiensHg19Chromosomes(hg19ChromosomeSizes,hg19ChromosomeBasedDNASequences);
		//for testing purposes
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

		//pssm matrices
		String encodeMotifsInputFileName 	= Commons.ENCODE_MOTIFS ;		
		String jasparCoreInputFileName = Commons.JASPAR_CORE;
	
		//TF and KeggPathway
		String augmentedTfExonBasedKeggPathwayInputFileName 		= Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES ;
		String augmentedTfRegulationBasedKeggPathwayInputFileName 	= Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES ;
		String augmentedTfAllBasedKeggPathwayInputFileName 			= Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES ;
		
				
		//TF and CellLine and KeggPathway
		String augmentedTfCellLineExonBasedKeggPathwayInputFileName 		= Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES ;
		String augmentedTfCellLineRegulationBasedKeggPathwayInputFileName 	= Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES ;
		String augmentedTfCellLineAllBasedKeggPathwayInputFileName 			= Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES ;
		
			
		
//		//Before each run
//		//delete directories and files under base directories
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(outputFolder,Commons.TF_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE);
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(outputFolder,Commons.TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE);
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(outputFolder,Commons.TF_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE);
		
		
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(outputFolder,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE);
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(outputFolder,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE);
		FileOperations.deleteDirectoriesandFilesUnderThisDirectory(outputFolder,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE);
		
		
		//Construct pfm matrices from encode-motif.txt file
		//A tf can have more than one pfm matrices
		//Take the transpose of given matrices in encode-motif.txt
		//Write the matrices in tab format for RSAT tool
		Map<String,String> tfName2PfmMatrices = new HashMap<String,String>();
		
		Map<String,String> tfName2LogoMatrices = new HashMap<String,String>();
		
		
		Map<String,List<String>> chrNameZeroBasedCoordinate2ObservedAlleles = new HashMap<String,List<String>>();
		
		//Construct position frequency matrices from Encode Motifs
		constructPfmMatricesfromEncodeMotifs(dataFolder,encodeMotifsInputFileName,tfName2PfmMatrices);
		
		//Construct logo matrices from Encode Motifs
		constructLogoMatricesfromEncodeMotifs(dataFolder,encodeMotifsInputFileName,tfName2LogoMatrices);
		
		//Construct position frequency matrices from Jaspar Core 
		//Construct logo matrices from Jaspar Core
		constructPfmMatricesandLogoMatricesfromJasparCore(dataFolder,jasparCoreInputFileName,tfName2PfmMatrices,tfName2LogoMatrices);
		
		//Construct chrNameZeroBasedCoordinate2ObservedAlleles HashMap
//		constructObservedAllelesMap(outputFolder,Commons.OCD_GWAS_SIGNIFICANT_SNPS_AUGMENTED_WITH_DBSNP,chrNameZeroBasedCoordinate2ObservedAlleles);
	
				
//		compareChromosomeDNASequences(chromDNASequence);
						
		//Using tfName2PfmMatrices
		//Using snps for Enriched TfandKeggPathway
		//Output dnaSequences for TfandKeggPathway
		//Output pfmMatrices for TfandKeggPathway
		readAugmentedDataWriteSequencesMatrices(outputFolder,augmentedTfExonBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_EXON_BASED_KEGG_PATHWAY,chrNameZeroBasedCoordinate2ObservedAlleles);
		readAugmentedDataWriteSequencesMatrices(outputFolder,augmentedTfRegulationBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_REGULATION_BASED_KEGG_PATHWAY,chrNameZeroBasedCoordinate2ObservedAlleles);
		readAugmentedDataWriteSequencesMatrices(outputFolder,augmentedTfAllBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_ALL_BASED_KEGG_PATHWAY,chrNameZeroBasedCoordinate2ObservedAlleles);	

		
		//Using tfName2PfmMatrices
		//Using snps for Enriched Tf CellLine KeggPathway
		//Output dnaSequences for Tf CellLine KeggPathway
		//Output pfmMatrices for Tf CellLine KeggPathway
		readAugmentedDataWriteSequencesMatrices(outputFolder,augmentedTfCellLineExonBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,chrNameZeroBasedCoordinate2ObservedAlleles);
		readAugmentedDataWriteSequencesMatrices(outputFolder,augmentedTfCellLineRegulationBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,chrNameZeroBasedCoordinate2ObservedAlleles);
		readAugmentedDataWriteSequencesMatrices(outputFolder,augmentedTfCellLineAllBasedKeggPathwayInputFileName,tfName2PfmMatrices,tfName2LogoMatrices,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,chrNameZeroBasedCoordinate2ObservedAlleles);
		
	}

}
