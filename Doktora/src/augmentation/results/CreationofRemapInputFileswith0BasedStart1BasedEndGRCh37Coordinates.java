/**
 * @author burcakotlu
 * @date Aug 25, 2014 
 * @time 11:00:56 AM
 */
package augmentation.results;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.EnrichmentType;

/**
 * 
 */
public class CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates {
	
	public static void readResultsandWriteVersion2(String outputFolder, String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine= null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		int indexofTenthTab;
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalOneBasedEnd;		
	
		String tfCellLineChrName;
		int tfCellLineZeroBasedStart;
		int tfCellLineOneBasedEnd;	
		
		String refseqGeneChrName;
		int refseqGeneZeroBasedStart;
		int refseqGeneOneBasedEnd;	
			
		try {
			fileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(!strLine.contains("Search") && !strLine.startsWith("*")){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab	= strLine.indexOf('\t', indexofSixthTab+1);
					indexofEigthTab		= strLine.indexOf('\t', indexofSeventhTab+1);
					indexofNinethTab	= strLine.indexOf('\t', indexofEigthTab+1);
					indexofTenthTab		= strLine.indexOf('\t', indexofNinethTab+1);
					
					givenIntervalChrName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
					givenIntervalZeroBasedStart = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					givenIntervalOneBasedEnd = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
												
					tfCellLineChrName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					tfCellLineZeroBasedStart = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					tfCellLineOneBasedEnd = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
					refseqGeneChrName = strLine.substring(indexofSeventhTab+1, indexofEigthTab);
					refseqGeneZeroBasedStart = Integer.parseInt(strLine.substring(indexofEigthTab+1, indexofNinethTab));
					refseqGeneOneBasedEnd = Integer.parseInt(strLine.substring(indexofNinethTab+1, indexofTenthTab));
					
					bufferedWriter.write(givenIntervalChrName +"\t" + givenIntervalZeroBasedStart +"\t" + givenIntervalOneBasedEnd + System.getProperty("line.separator"));
					bufferedWriter.write(givenIntervalChrName +"\t" + tfCellLineZeroBasedStart +"\t" + tfCellLineOneBasedEnd + System.getProperty("line.separator"));
					bufferedWriter.write(givenIntervalChrName +"\t" + refseqGeneZeroBasedStart +"\t" + refseqGeneOneBasedEnd + System.getProperty("line.separator"));
					
				}//End of if			
			}//End of while
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void readResultsandWrite(String outputFolder, String inputFileName, String outputFileName){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String strLine= null;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		
		String givenIntervalChrName;		
		int givenIntervalZeroBasedStart;
		int givenIntervalOneBasedEnd;		
	
		String overlapChrName;
		int overlapZeroBasedStart;
		int overlapOneBasedEnd;	
		
		
		try {
			fileReader = new FileReader(outputFolder + inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			fileWriter = FileOperations.createFileWriter(outputFolder + outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			while((strLine = bufferedReader.readLine())!=null){
				if(!strLine.contains("Search") && !strLine.startsWith("*")){
					
					indexofFirstTab 	= strLine.indexOf('\t');
					indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
					indexofThirdTab 	= strLine.indexOf('\t', indexofSecondTab+1);
					indexofFourthTab 	= strLine.indexOf('\t', indexofThirdTab+1);
					indexofFifthTab 	= strLine.indexOf('\t', indexofFourthTab+1);
					indexofSixthTab 	= strLine.indexOf('\t', indexofFifthTab+1);
					indexofSeventhTab	= strLine.indexOf('\t', indexofSixthTab+1);
					
					givenIntervalChrName = strLine.substring(indexofFirstTab+1, indexofSecondTab);
					givenIntervalZeroBasedStart = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					givenIntervalOneBasedEnd = Integer.parseInt(strLine.substring(indexofThirdTab+1, indexofFourthTab));
												
					overlapChrName = strLine.substring(indexofFourthTab+1, indexofFifthTab);
					overlapZeroBasedStart = Integer.parseInt(strLine.substring(indexofFifthTab+1, indexofSixthTab));
					overlapOneBasedEnd = Integer.parseInt(strLine.substring(indexofSixthTab+1, indexofSeventhTab));
					
					bufferedWriter.write(givenIntervalChrName +"\t" + givenIntervalZeroBasedStart +"\t" + givenIntervalOneBasedEnd + System.getProperty("line.separator"));
					bufferedWriter.write(overlapChrName +"\t" + overlapZeroBasedStart +"\t" + overlapOneBasedEnd + System.getProperty("line.separator"));
					
				}//End of if			
			}//End of while
			
			
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	
	public static void readandCreateFiles(String outputFolder, EnrichmentType dnaseEnrichment, EnrichmentType histoneEnrichment, EnrichmentType tfEnrichment, EnrichmentType keggPathwayEnrichment, EnrichmentType tfKeggPathwayEnrichment, EnrichmentType tfCellLineKeggPathwayEnrichment){
		if (dnaseEnrichment.isDnaseEnrichment()){
			readResultsandWrite(outputFolder, Commons.AUGMENTED_DNASE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES,Commons.LINE_BY_LINE_AUGMENTED_DNASE_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 
		 if (histoneEnrichment.isHistoneEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_HISTONE_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_HISTONE_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 
		 if (tfEnrichment.isTfEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_TF_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
		 }
		 
		 if (keggPathwayEnrichment.isKeggPathwayEnrichment()){
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
			 readResultsandWrite(outputFolder, Commons.AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);					
		}
		
	     if (tfKeggPathwayEnrichment.isTfKeggPathwayEnrichment()){	   
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);					
	     }
		
	     if (tfCellLineKeggPathwayEnrichment.isTfCellLineKeggPathwayEnrichment()){
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);	
	    	 readResultsandWriteVersion2(outputFolder, Commons.AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES, Commons.LINE_BY_LINE_AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_CHRNUMBER_0BASEDSTART_1BASEDEND_GRCH37_COORDINATES);					

	     }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String glanetFolder = args[1];
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = "noname";
		}
		//jobName ends
				
				
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName +  System.getProperty("file.separator");
		
		EnrichmentType dnaseEnrichment 		= EnrichmentType.convertStringtoEnum(args[10]);
		EnrichmentType histoneEnrichment  	= EnrichmentType.convertStringtoEnum(args[11]);
		EnrichmentType tfEnrichment 		= EnrichmentType.convertStringtoEnum(args[12]);
		EnrichmentType keggPathwayEnrichment  			= EnrichmentType.convertStringtoEnum(args[13]);
		EnrichmentType tfKeggPathwayEnrichment 			= EnrichmentType.convertStringtoEnum(args[14]);
		EnrichmentType tfCellLineKeggPathwayEnrichment 	= EnrichmentType.convertStringtoEnum(args[15]);
		
		
		//delete old files starts 
		FileOperations.deleteOldFiles(outputFolder + Commons.AUGMENTED_ENRICHED_ELEMENTS_WITH_GIVEN_INPUT_DATA_REMAP_DIRECTORY);
		//delete old files ends
	
		CreationofRemapInputFileswith0BasedStart1BasedEndGRCh37Coordinates.readandCreateFiles(outputFolder,dnaseEnrichment,histoneEnrichment,tfEnrichment,keggPathwayEnrichment,tfKeggPathwayEnrichment,tfCellLineKeggPathwayEnrichment);
	}

}
