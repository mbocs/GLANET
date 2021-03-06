/**
 * @author burcakotlu
 * @date Sep 15, 2014 
 * @time 2:39:39 PM
 */
package collaboration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import auxiliary.FileOperations;

import common.Commons;

import enumtypes.MultipleTestingType;

/**
 * 
 */
public class ElementVersusNumberofOverlapsWithRSIDsAndEnrichmentForOnePhenotype {
	
	
	public static void readEnrichmentResults(String outputFolder, float bonferroniCorrectionSignificanceLevel,String elementType, Map<String,ElementEnrichment> elementMap){
		
		
		String enrichmentDirectory = outputFolder + Commons.ENRICHMENT + System.getProperty("file.separator") + elementType + System.getProperty("file.separator");
		String name = null;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine = null;
		
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
		
		String elementName = null;
		float empiricalPValue;
		float BonferroniCorrectedPValue;
		float BHFDRAdjustedPValue;
		boolean enriched_BH_FDR;
		boolean enriched_Bonferroni_Correction;
		
		boolean headerLine= true;
		
		ElementEnrichment element = null;
		
		
		//Read each file under enrichmentFolder	
		File enrichmentFolder = new File(enrichmentDirectory);
		
		if (enrichmentFolder.exists() && enrichmentFolder.isDirectory()){
			File[] files = enrichmentFolder.listFiles();
			
			 for(File elementEnrichmentFile: files){
				 	//example	ExonBased_KEGGPathway_test4_wrt_BH_FDR_adjusted_pValue.txt
			        if(elementEnrichmentFile.isFile()) {
			        		
			        	//get file whose name contain "wrt"
			        	name = elementEnrichmentFile.getName();
			        	
			        	if (name.contains(Commons.WRT)){
			        		try {
								fileReader 		= new FileReader(elementEnrichmentFile);
								bufferedReader 	= new BufferedReader(fileReader);
								
								//Skip header line
								if (headerLine){
									strLine = bufferedReader.readLine();
									headerLine= false;
								}
					        	
								while((strLine = bufferedReader.readLine())!=null){
									
									
										
										indexofFirstTab = strLine.indexOf('\t');
										indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
										indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
										indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
										indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
										indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
										indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
										indexofEigthTab = strLine.indexOf('\t',indexofSeventhTab+1);
										indexofNinethTab = strLine.indexOf('\t',indexofEigthTab+1);
										indexofTenthTab = strLine.indexOf('\t',indexofNinethTab+1);
										
//										3100170099	PU1_GM12878_hsa03060	1	0	10	109214	0.00E+00	0.00E+00	0.00E+00	TRUE	Protein export - Homo sapiens (human)	10952, 11231, 196294, 23478, 23480, 28972, 29927, 3309, 5018, 55176, 58477, 60559, 6726, 6727, 6728, 6729, 6730, 6731, 6734, 7095, 83943, 90701, 9789	SEC61B, SEC63, IMMP1L, SEC11A, SEC61G, SPCS1, SEC61A1, HSPA5, OXA1L, SEC61A2, SRPRB, SPCS3, SRP9, SRP14, SRP19, SRP54, SRP68, SRP72, SRPR, SEC62, IMMP2L, SEC11C, SPCS2

										//Get the element name
										elementName = strLine.substring(indexofFirstTab+1,indexofSecondTab);
										
										empiricalPValue = Float.parseFloat(strLine.substring(indexofSixthTab+1,indexofSeventhTab));		
										BonferroniCorrectedPValue = Float.parseFloat(strLine.substring(indexofSeventhTab+1,indexofEigthTab));
										BHFDRAdjustedPValue = Float.parseFloat(strLine.substring(indexofEigthTab+1,indexofNinethTab));
										
										if(indexofTenthTab!=-1){
											enriched_BH_FDR = Boolean.parseBoolean(strLine.substring(indexofNinethTab+1,indexofTenthTab));
											
										}else{
											enriched_BH_FDR = Boolean.parseBoolean(strLine.substring(indexofNinethTab+1));
											
										}
										
										if (BonferroniCorrectedPValue <= bonferroniCorrectionSignificanceLevel){
											enriched_Bonferroni_Correction= true;
										}else{
											enriched_Bonferroni_Correction = false;
													
										}
										
										element = new ElementEnrichment(elementName,enriched_Bonferroni_Correction,enriched_BH_FDR,empiricalPValue,BonferroniCorrectedPValue,BHFDRAdjustedPValue);
										
										elementMap.put(elementName, element);
										
									
									
								}//End of while
								
							bufferedReader.close();
							fileReader.close();
								
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	}//End of IF: file containing Commons.WRT
			        			        	
			        }// End of IF: it is a file
				 
			 }//End of for: each file in this directory
			    			    			
		}//End of IF: It is a directory
	}
	
	
	
	public static void readAnnotationResults(String outputFolder, String elementType, List<ElementAnnotationEnrichment> elementList){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine;
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		int indexofUnderScore;
		int indexofDot;
		
		String chrName;
		int startZeroBasedHg19Inclusive;
		int endZeroBasedHg19Inclusive;
		
		Set<String> overlappingGivenIntervals = null;
		String name = null;
		String elementName = null;
		ElementAnnotationEnrichment element = null;
			
		String annotationDirectory = outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + elementType + System.getProperty("file.separator");
		
		//Read each file under annotationFolder	
		File annotationFolder = new File(annotationDirectory);
		
		if (annotationFolder.exists() && annotationFolder.isDirectory()){
			File[] files = annotationFolder.listFiles();
			
			 for(File elementAnnotationFile: files){
				 	//example	BCL3_hsa03050
			        if(elementAnnotationFile.isFile()) {
			        	
			        	//We will start reading Element Annotation File
			        	overlappingGivenIntervals = new HashSet<String>();
			        	
			        	//get the ElementName from the fileName
			        	name = elementAnnotationFile.getName();
			        	indexofUnderScore = name.indexOf('_');
			        	indexofDot = name.indexOf('.');
			        	elementName = name.substring(indexofUnderScore+1, indexofDot);
			        	
			        	try {
							fileReader 		= new FileReader(elementAnnotationFile);
							bufferedReader 	= new BufferedReader(fileReader);
				        	
							while((strLine = bufferedReader.readLine())!=null){
								if (!strLine.startsWith("Search")){
									indexofFirstTab = strLine.indexOf('\t');
									indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
									indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
									
									chrName = strLine.substring(0,indexofFirstTab);
									startZeroBasedHg19Inclusive = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
									endZeroBasedHg19Inclusive = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
									
									overlappingGivenIntervals.add(chrName + "_" + startZeroBasedHg19Inclusive + "_" + endZeroBasedHg19Inclusive);							
								}
								
							}//End of while
							
							//We have read the overlaps for a certain element
							element = new ElementAnnotationEnrichment(elementName,overlappingGivenIntervals.size(),overlappingGivenIntervals);
							elementList.add(element);
							
							bufferedReader.close();
							fileReader.close();
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			        	
			        	
			        }
				 
			 }//End of for
			
		}//End of IF
			
	}
	
	//fill only overlap2RSIDMap starts
	public static void readRSIDMap(String inputFileName,Map<String,String> overlap2RSIDMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		
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
		int indexofEleventhTab;
		
		boolean headerLine = true;
		
		String rsID;
		String snpChrName;
		int snpOneBasedPositionHg19;
		
		String chrName;
		int startZeroBasedHg19Inclusive;
		int endZeroBasedHg19Inclusive;
		
//		String eQTL_geneName;
//		String eQTL_geneLocation;
		
		String key;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//skip headerLine
			if (headerLine){
				strLine= bufferedReader.readLine();
				headerLine = false;
			}
			
			while((strLine= bufferedReader.readLine())!=null){
//				SNPid	mesh_term	Expressed.gene.associated.with.the.SNP	Type	SNP_Fx_Allele	SNP_Non_Fx_Allele	SNP_Fx_Allele_Freq	SNP_MAF	SNP_Imputation_RSq	SNP_Chr.x	SNP_Pos_hg19.x	Transcript_Chr	Transcript_Start_hg19	RSq	log10P	IsValidated.in.Blood.eQTL.Broswer
//				rs10821415	Atrial Fibrillation	C9orf3	cis	C	A	0.57359	0.42641	0.96657	9	97713459	9	97488991	0.008	-10.45	No

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab 	= strLine.indexOf('\t',indexofEigthTab+1);
				indexofTenthTab 	= strLine.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab 	= strLine.indexOf('\t',indexofTenthTab+1);
				
				rsID = strLine.substring(0, indexofFirstTab);
				
				snpChrName = strLine.substring(indexofNinethTab+1, indexofTenthTab);
				snpOneBasedPositionHg19 = Integer.parseInt(strLine.substring(indexofTenthTab+1, indexofEleventhTab));
				
				chrName = "chr" + snpChrName;
				startZeroBasedHg19Inclusive = snpOneBasedPositionHg19 - 1;
				endZeroBasedHg19Inclusive = snpOneBasedPositionHg19 - 1;
				
				
				key = chrName+ "_" + startZeroBasedHg19Inclusive + "_" + endZeroBasedHg19Inclusive;
				
				if (!overlap2RSIDMap.containsKey(key)){
					overlap2RSIDMap.put(key , rsID);
				}
			
				
			}//End of while
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//fill only overlap2RSIDMap ends

	public static void readRSIDMap(String inputFileName,Map<String,String> overlap2RSIDMap, Map<String,String> overlap2EQTLMap){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String strLine;
		
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
		int indexofEleventhTab;
		
		boolean headerLine = true;
		
		String rsID;
		String snpChrName;
		int snpOneBasedPositionHg19;
		
		String chrName;
		int startZeroBasedHg19Inclusive;
		int endZeroBasedHg19Inclusive;
		
		String eQTL_geneName;
		String eQTL_geneLocation;
		
		String key;
		
		try {
			fileReader = new FileReader(inputFileName);
			bufferedReader = new BufferedReader(fileReader);
			
			//skip headerLine
			if (headerLine){
				strLine= bufferedReader.readLine();
				headerLine = false;
			}
			
			while((strLine= bufferedReader.readLine())!=null){
//				SNPid	mesh_term	Expressed.gene.associated.with.the.SNP	Type	SNP_Fx_Allele	SNP_Non_Fx_Allele	SNP_Fx_Allele_Freq	SNP_MAF	SNP_Imputation_RSq	SNP_Chr.x	SNP_Pos_hg19.x	Transcript_Chr	Transcript_Start_hg19	RSq	log10P	IsValidated.in.Blood.eQTL.Broswer
//				rs10821415	Atrial Fibrillation	C9orf3	cis	C	A	0.57359	0.42641	0.96657	9	97713459	9	97488991	0.008	-10.45	No

				indexofFirstTab 	= strLine.indexOf('\t');
				indexofSecondTab 	= strLine.indexOf('\t',indexofFirstTab+1);
				indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
				indexofFourthTab 	= strLine.indexOf('\t',indexofThirdTab+1);
				indexofFifthTab 	= strLine.indexOf('\t',indexofFourthTab+1);
				indexofSixthTab 	= strLine.indexOf('\t',indexofFifthTab+1);
				indexofSeventhTab	= strLine.indexOf('\t',indexofSixthTab+1);
				indexofEigthTab 	= strLine.indexOf('\t',indexofSeventhTab+1);
				indexofNinethTab 	= strLine.indexOf('\t',indexofEigthTab+1);
				indexofTenthTab 	= strLine.indexOf('\t',indexofNinethTab+1);
				indexofEleventhTab 	= strLine.indexOf('\t',indexofTenthTab+1);
				
				rsID = strLine.substring(0, indexofFirstTab);
				
				snpChrName = strLine.substring(indexofNinethTab+1, indexofTenthTab);
				snpOneBasedPositionHg19 = Integer.parseInt(strLine.substring(indexofTenthTab+1, indexofEleventhTab));
				
				chrName = "chr" + snpChrName;
				startZeroBasedHg19Inclusive = snpOneBasedPositionHg19 - 1;
				endZeroBasedHg19Inclusive = snpOneBasedPositionHg19 - 1;
				
				eQTL_geneName = strLine.substring(indexofSecondTab+1, indexofThirdTab);
				eQTL_geneLocation = strLine.substring(indexofThirdTab+1, indexofFourthTab);
				
				key = chrName+ "_" + startZeroBasedHg19Inclusive + "_" + endZeroBasedHg19Inclusive;
				
				if (!overlap2RSIDMap.containsKey(key)){
					overlap2RSIDMap.put(key , rsID);
				}
				
				
				if (!overlap2EQTLMap.containsKey(key)){
					overlap2EQTLMap.put(key , eQTL_geneName + "_" + eQTL_geneLocation);
				}else{
					overlap2EQTLMap.put(key , overlap2EQTLMap.get(key) + " " + eQTL_geneName+ "_" +eQTL_geneLocation);				
				}
			}//End of while
			
			bufferedReader.close();
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void readAnnotationResults(String outputFolder,
			List<ElementAnnotationEnrichment> dnaseElements,
			List<ElementAnnotationEnrichment> tfElements,
			List<ElementAnnotationEnrichment> histoneElements,
			List<ElementAnnotationEnrichment> exonBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> regulationBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> allBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfExonBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfRegulationBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfAllBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfCellLineExonBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfCellLineRegulationBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfCellLineAllBasedKEGGPathwayElements){
		
		
		/****************************************************************************/
		/**********************************DNASE starts******************************/
		/****************************************************************************/
		//Get the annotations per elementName which are under .../GLANET/Output/jobName/Annotation	
		readAnnotationResults(outputFolder, Commons.DNASE,dnaseElements);	
		/****************************************************************************/
		/**********************************DNASE  ends*******************************/
		/****************************************************************************/
		
		
		/****************************************************************************/
		/*************************************TF starts******************************/
		/****************************************************************************/
		//Get the annotations per elementName which are under .../GLANET/Output/jobName/Annotation	
		readAnnotationResults(outputFolder, Commons.TF,tfElements);
		/****************************************************************************/
		/*************************************TF  ends*******************************/
		/****************************************************************************/
		
		
		/****************************************************************************/
		/*************************************HISTONE starts*************************/
		/****************************************************************************/
		//Get the annotations per elementName which are under .../GLANET/Output/jobName/Annotation	
		readAnnotationResults(outputFolder, Commons.HISTONE,histoneElements);
		/****************************************************************************/
		/*************************************HISTONE  ends**************************/
		/****************************************************************************/
		
		/****************************************************************************/
		/*************************************KEGGPathway starts*********************/
		/****************************************************************************/
		//Get the annotations per elementName which are under .../GLANET/Output/jobName/Annotation	
		readAnnotationResults(outputFolder, Commons.KEGG_PATHWAY + System.getProperty("file.separator") +Commons.EXON_BASED_KEGG_PATHWAY,exonBasedKEGGPathwayElements);
		readAnnotationResults(outputFolder, Commons.KEGG_PATHWAY + System.getProperty("file.separator") +Commons.REGULATION_BASED_KEGG_PATHWAY ,regulationBasedKEGGPathwayElements);
		readAnnotationResults(outputFolder, Commons.KEGG_PATHWAY + System.getProperty("file.separator") +Commons.ALL_BASED_KEGG_PATHWAY,allBasedKEGGPathwayElements);
		/****************************************************************************/
		/*************************************KEGGPathway  ends**********************/
		/****************************************************************************/
		
		
		/****************************************************************************/
		/*************************************TF KEGGPathway starts******************/
		/****************************************************************************/
		//Get the annotations per elementName which are under .../GLANET/Output/jobName/Annotation	
		readAnnotationResults(outputFolder, Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") +Commons.TF_EXON_BASED_KEGG_PATHWAY,tfExonBasedKEGGPathwayElements);
		readAnnotationResults(outputFolder, Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") +Commons.TF_REGULATION_BASED_KEGG_PATHWAY ,tfRegulationBasedKEGGPathwayElements);
		readAnnotationResults(outputFolder, Commons.TF_KEGGPATHWAY + System.getProperty("file.separator") +Commons.TF_ALL_BASED_KEGG_PATHWAY,tfAllBasedKEGGPathwayElements);
		/****************************************************************************/
		/*************************************TF KEGGPathway  ends*******************/
		/****************************************************************************/
		
		
		/****************************************************************************/
		/*************************************TF CELLLINE KEGGPathway starts******************/
		/****************************************************************************/
		//Get the annotations per elementName which are under .../GLANET/Output/jobName/Annotation	
		readAnnotationResults(outputFolder, Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") +Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,tfCellLineExonBasedKEGGPathwayElements);
		readAnnotationResults(outputFolder, Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") +Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY ,tfCellLineRegulationBasedKEGGPathwayElements);
		readAnnotationResults(outputFolder, Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator") +Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,tfCellLineAllBasedKEGGPathwayElements);
		/****************************************************************************/
		/*************************************TF CELLLINE KEGGPathway  ends*******************/
		/****************************************************************************/

	}
	

	
	
	public static void readEnrichmentResults(String outputFolder,
			float bonferroniCorrectionSignificanceLevel,
			Map<String,ElementEnrichment> dnaseMap,
			Map<String,ElementEnrichment> tfMap,
			Map<String,ElementEnrichment> histoneMap,
			Map<String,ElementEnrichment> exonBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> regulationBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> allBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfExonBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfRegulationBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfAllBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfCellLineExonBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfCellLineRegulationBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfCellLineAllBasedKEGGPathwayMap){
		
		/****************************************************************************/
		/**********************************DNASE starts******************************/
		/****************************************************************************/
		//Get the enrichment results per elementName which are under .../GLANET/Output/jobName/Enrichment	
		readEnrichmentResults(outputFolder,bonferroniCorrectionSignificanceLevel, Commons.DNASE,dnaseMap);
		/****************************************************************************/
		/**********************************DNASE  ends*******************************/
		/****************************************************************************/
	
		
		/****************************************************************************/
		/**********************************TF starts*********************************/
		/****************************************************************************/
		//Get the enrichment results per elementName which are under .../GLANET/Output/jobName/Enrichment	
		readEnrichmentResults(outputFolder,bonferroniCorrectionSignificanceLevel, Commons.TF,tfMap);
		/****************************************************************************/
		/**********************************TF  ends**********************************/
		/****************************************************************************/

		/****************************************************************************/
		/**********************************HISTONE starts****************************/
		/****************************************************************************/
		//Get the enrichment results per elementName which are under .../GLANET/Output/jobName/Enrichment	
		readEnrichmentResults(outputFolder,bonferroniCorrectionSignificanceLevel, Commons.HISTONE,histoneMap);
		/****************************************************************************/
		/**********************************HISTONE  ends*****************************/
		/****************************************************************************/

		
		/****************************************************************************/
		/**********************************KEGGPathway starts************************/
		/****************************************************************************/
		//Get the enrichment results per elementName which are under .../GLANET/Output/jobName/Enrichment	
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel,Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.EXON_BASED_KEGG_PATHWAY,exonBasedKEGGPathwayMap);
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel, Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.REGULATION_BASED_KEGG_PATHWAY,regulationBasedKEGGPathwayMap);
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel,Commons.KEGG_PATHWAY + System.getProperty("file.separator") + Commons.ALL_BASED_KEGG_PATHWAY,allBasedKEGGPathwayMap);
		/****************************************************************************/
		/**********************************KEGGPathway  ends*************************/
		/****************************************************************************/

		
		/****************************************************************************/
		/**********************************TF KEGGPathway starts*********************/
		/****************************************************************************/
		//Get the enrichment results per elementName which are under .../GLANET/Output/jobName/Enrichment	
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel, Commons.TF_KEGGPATHWAY + System.getProperty("file.separator")+ Commons.TF_EXON_BASED_KEGG_PATHWAY,tfExonBasedKEGGPathwayMap);
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel,Commons.TF_KEGGPATHWAY + System.getProperty("file.separator")+ Commons.TF_REGULATION_BASED_KEGG_PATHWAY,tfRegulationBasedKEGGPathwayMap);
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel,Commons.TF_KEGGPATHWAY + System.getProperty("file.separator")+ Commons.TF_ALL_BASED_KEGG_PATHWAY,tfAllBasedKEGGPathwayMap);
		/****************************************************************************/
		/**********************************TF KEGGPathway  ends**********************/
		/****************************************************************************/

		
		/****************************************************************************/
		/**********************************TF CELLLINE KEGGPathway starts************/
		/****************************************************************************/
		//Get the enrichment results per elementName which are under .../GLANET/Output/jobName/Enrichment	
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel, Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator")+ Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,tfCellLineExonBasedKEGGPathwayMap);
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel, Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator")+ Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,tfCellLineRegulationBasedKEGGPathwayMap);
		readEnrichmentResults(outputFolder, bonferroniCorrectionSignificanceLevel, Commons.TF_CELLLINE_KEGGPATHWAY + System.getProperty("file.separator")+ Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,tfCellLineAllBasedKEGGPathwayMap);
		/****************************************************************************/
		/**********************************TF CELLLINE KEGGPathway  ends*************/
		/****************************************************************************/
	}
	
	
	public static  void augmentAnnotationandEnrichmentResultsWithRSIds(Map<String,String> overlap2RSIDMap,
			List<ElementAnnotationEnrichment> elementListFromAnnotation,
			Map<String,ElementEnrichment> elementMapFromEnrichment){
		
		ElementAnnotationEnrichment elementAnnotationEnrichment = null;
		ElementEnrichment elementFromEnrichment = null;
		
		Iterator<ElementAnnotationEnrichment> iterator = elementListFromAnnotation.iterator();
		
		String elementName;
		String rsId;
		
		List<String> rsIdList = null;
		
		
		while(iterator.hasNext()){
			
			//Get the rsIds from overlap2RSIDMap
			elementAnnotationEnrichment = (ElementAnnotationEnrichment) iterator.next();			
			elementName = elementAnnotationEnrichment.getElementName();
			
			rsIdList = new ArrayList<String>();			
			for(String chrName_start_end : elementAnnotationEnrichment.getOverlappingGivenIntervalSet()){
				
				rsId = overlap2RSIDMap.get(chrName_start_end);
				rsIdList.add(rsId);
				
			}			
			elementAnnotationEnrichment.setRsIdList(rsIdList);
			
			
			//Get enriched_Bonferroni_Correction and enriched_BH_FDR
			elementFromEnrichment = elementMapFromEnrichment.get(elementName);
			
			elementAnnotationEnrichment.setEnriched_Bonferroni_Correction(elementFromEnrichment.isEnriched_Bonferroni_Correction());
			elementAnnotationEnrichment.setEnriched_BH_FDR(elementFromEnrichment.isEnriched_BH_FDR());
			
			elementAnnotationEnrichment.setEmpiricalPValue(elementFromEnrichment.getEmpiricalPValue());
			elementAnnotationEnrichment.setBonferroniCorrectedPValue(elementFromEnrichment.getBonferroniCorrectedPValue());
			elementAnnotationEnrichment.setBHFDRAdjustedPValue(elementFromEnrichment.getBHFDRAdjustedPValue());
			
			
		}//End of while
		
	}
	
	
	public static void augmentAnnotationResultsWithRsIds(
			String annotationResultsInputFileName,
			String annotationResultsWithRsIdsOutputFileName,
			Map<String,String> overlap2RSIDMap,
			Map<String,String> overlap2EQTLMap){
		
		
		String strLine;
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		int indexofFourthTab;
		int indexofFifthTab;
		int indexofSixthTab;
		int indexofSeventhTab;
		int indexofEigthTab;
		int indexofNinethTab;
		
		String chrName;
		int startZeroBasedHg19Inclusive;
		int endZeroBasedHg19Inclusive;
		String annotatedGeneAlternateName;
		
		int startOneBasedHg19Inclusive;
		
		String concatenated= null;
		String rsID = null;
		String eQTLs = null;
		
		boolean isAnnotatedGeneIneQTLS = false;
		
		try {
			FileReader fileReader = FileOperations.createFileReader(annotationResultsInputFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			FileWriter fileWriter = FileOperations.createFileWriter(annotationResultsWithRsIdsOutputFileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//write header line
			bufferedWriter.write("Searched rsID" + "\t" + "eQTLs" + "\t" + "Annotated Gene in eQTLS" + "\t" +  "snp chrName"	+ "\t" + "snp Pos hg19 1based" + "\t" +	"ucscRefSeqGene ChromName"	+ "\t" + "ucscRefSeqGene Low"	+ "\t" + "ucscRefSeqGene High" + "\t" +	"ucscRefSeqGene RefSeqGeneName" + "\t" +	"ucscRefSeqGene IntervalName"	+ "\t" + "ucscRefSeqGene GeneHugoSymbol" + "\t" + "ucscRefSeqGene GeneEntrezId" +  System.getProperty("line.separator"));

//			example strLine		
//			chr1	11862777	11862777	chr1	11820828	11910827	NM_001040194	THREE_D	AGTRAP	57085

			while((strLine = bufferedReader.readLine())!=null){
				if (!strLine.startsWith("Search")){
					
					indexofFirstTab = strLine.indexOf('\t');
					indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
					indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
					indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
					indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
					indexofSixthTab = strLine.indexOf('\t',indexofFifthTab+1);
					indexofSeventhTab = strLine.indexOf('\t',indexofSixthTab+1);
					indexofEigthTab = strLine.indexOf('\t',indexofSeventhTab+1);
					indexofNinethTab = strLine.indexOf('\t',indexofEigthTab+1);
						
					chrName = strLine.substring(0,indexofFirstTab);
					startZeroBasedHg19Inclusive = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
					endZeroBasedHg19Inclusive = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
					annotatedGeneAlternateName = strLine.substring(indexofEigthTab+1, indexofNinethTab);
					
					startOneBasedHg19Inclusive = startZeroBasedHg19Inclusive+1;
					
					concatenated = chrName + "_" + startZeroBasedHg19Inclusive + "_" + endZeroBasedHg19Inclusive;							
			
					rsID = overlap2RSIDMap.get(concatenated);
					eQTLs =overlap2EQTLMap.get(concatenated);
					
					if (eQTLs.contains(annotatedGeneAlternateName)){
						isAnnotatedGeneIneQTLS =  true;
					}else{
						isAnnotatedGeneIneQTLS = false;
					}
					
					
							
					bufferedWriter.write(rsID + "\t" + eQTLs +  "\t" + isAnnotatedGeneIneQTLS + "\t" + chrName + "\t" +startOneBasedHg19Inclusive + "\t" + strLine.substring(indexofThirdTab+1) + System.getProperty("line.separator"));
				}
			}//End of While
			
			bufferedReader.close();
			fileReader.close();
			
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void augmentAnnotationandEnrichmentResultsWithRSIds(
			Map<String,String> overlap2RSIDMap,
			List<ElementAnnotationEnrichment> dnaseElements,
			List<ElementAnnotationEnrichment> tfElements,
			List<ElementAnnotationEnrichment> histoneElements,
			List<ElementAnnotationEnrichment> exonBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> regulationBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> allBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfExonBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfRegulationBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfAllBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfCellLineExonBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfCellLineRegulationBasedKEGGPathwayElements,
			List<ElementAnnotationEnrichment> tfCellLineAllBasedKEGGPathwayElements,
			Map<String,ElementEnrichment> dnaseMap,
			Map<String,ElementEnrichment> tfMap,
			Map<String,ElementEnrichment> histoneMap,
			Map<String,ElementEnrichment> exonBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> regulationBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> allBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfExonBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfRegulationBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfAllBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfCellLineExonBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfCellLineRegulationBasedKEGGPathwayMap,
			Map<String,ElementEnrichment> tfCellLineAllBasedKEGGPathwayMap){
		
		
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,dnaseElements,dnaseMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfElements,tfMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,histoneElements,histoneMap);
			
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,exonBasedKEGGPathwayElements,exonBasedKEGGPathwayMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,regulationBasedKEGGPathwayElements,regulationBasedKEGGPathwayMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,allBasedKEGGPathwayElements,allBasedKEGGPathwayMap);
			
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfExonBasedKEGGPathwayElements,tfExonBasedKEGGPathwayMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfRegulationBasedKEGGPathwayElements,tfRegulationBasedKEGGPathwayMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfAllBasedKEGGPathwayElements,tfAllBasedKEGGPathwayMap);
			
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfCellLineExonBasedKEGGPathwayElements,tfCellLineExonBasedKEGGPathwayMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfCellLineRegulationBasedKEGGPathwayElements,tfCellLineRegulationBasedKEGGPathwayMap);
			augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,tfCellLineAllBasedKEGGPathwayElements,tfCellLineAllBasedKEGGPathwayMap);
		
	}
	
	
	public static void writeAnnotationEnrichmentWithRSIDsSummaryTable(
		String encodeCollaborationDirectory, 
		String extraDirectoryName,
		String initialFileName,
		MultipleTestingType multipleTestingParameter,
		List<ElementAnnotationEnrichment> augmentedElementList){
		
		DecimalFormat df = new DecimalFormat("0.######E0");
		
		FileWriter fileWriter = null;		
		BufferedWriter bufferedWriter = null;
				
		//sort the elements w.r.t. its pValue from smallest to largest before traversing the list
		if (multipleTestingParameter.isBonferroniCorrection()){
			Collections.sort(augmentedElementList,ElementAnnotationEnrichment.BONFERRONI_CORRECTED_P_VALUE);
			
		}else if (multipleTestingParameter.isBenjaminiHochbergFDR()){
			Collections.sort(augmentedElementList,ElementAnnotationEnrichment.BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE);			
		}
			
				
		
		try {
			fileWriter = FileOperations.createFileWriter(encodeCollaborationDirectory + extraDirectoryName + System.getProperty("file.separator") + initialFileName + "_SummaryTable.txt");
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//write header line
			bufferedWriter.write("Element Name" + "\t" + "Number of overlaps: k out of n" + "\t" + "Overlapping rsIDs" + "\t" + "Empirical P Value" + "\t" + "Bonferroni Corrected P Value" + "\t" + "Bonferroni_Enriched"  + "\t" + "BH FDR Adjusted P Value (sorted w.r.t. this value)" + "\t" + "BH_Enriched"  + System.getProperty("line.separator"));			
		
			
			
			for(ElementAnnotationEnrichment e : augmentedElementList ){
				bufferedWriter.write(e.getElementName() + "\t" + e.getNumberofOverlaps() + "\t");
				
				//Write all of the rsIds
				for(String rsID : e.getRsIdList()){
					bufferedWriter.write(rsID + " ");
				}//End of for each rsID in the rsIDList
				
				bufferedWriter.write("\t");
								
				bufferedWriter.write( df.format(e.getEmpiricalPValue())+ "\t" + df.format(e.getBonferroniCorrectedPValue()) + "\t" + e.enriched_Bonferroni_Correction + "\t" + df.format(e.getBHFDRAdjustedPValue()) + "\t" + e.enriched_BH_FDR + System.getProperty("line.separator"));
					 
				
			}//End of for each element
			
			
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	
	}
	
	
	public static void writeAnnotationEnrichmentWithRSIDsSummaryTable(
		String outputFolder,
		MultipleTestingType multipleTestingParameter,
		List<ElementAnnotationEnrichment> dnaseElements,
		List<ElementAnnotationEnrichment> tfElements,
		List<ElementAnnotationEnrichment> histoneElements,
		List<ElementAnnotationEnrichment> exonBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> regulationBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> allBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> tfExonBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> tfRegulationBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> tfAllBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> tfCellLineExonBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> tfCellLineRegulationBasedKEGGPathwayElements,
		List<ElementAnnotationEnrichment> tfCellLineAllBasedKEGGPathwayElements){
		
		String encodeCollaborationSummaryTablesDirectory = outputFolder + System.getProperty("file.separator") + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATION_WITH_RS_IDS_ENRICHMENT_SUMMARY_TABLES_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") ;
		
		/****************************************************************************/
		/**********************************DNASE starts******************************/
		/****************************************************************************/
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.DNASE,Commons.DNASE, multipleTestingParameter,dnaseElements);
		/****************************************************************************/
		/**********************************DNASE  ends*******************************/
		/****************************************************************************/
	
		
		/****************************************************************************/
		/**********************************TF starts*********************************/
		/****************************************************************************/
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF,Commons.TF, multipleTestingParameter,tfElements);
		/****************************************************************************/
		/**********************************TF  ends**********************************/
		/****************************************************************************/

		
		/****************************************************************************/
		/**********************************HISTONE starts****************************/
		/****************************************************************************/
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.HISTONE,Commons.HISTONE, multipleTestingParameter,histoneElements);
		/****************************************************************************/
		/**********************************HISTONE  ends*****************************/
		/****************************************************************************/

		/****************************************************************************/
		/**********************************KEGGPathway starts************************/
		/****************************************************************************/
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.KEGG_PATHWAY,Commons.EXON_BASED_KEGG_PATHWAY, multipleTestingParameter,exonBasedKEGGPathwayElements);
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.KEGG_PATHWAY,Commons.REGULATION_BASED_KEGG_PATHWAY, multipleTestingParameter,regulationBasedKEGGPathwayElements);
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.KEGG_PATHWAY,Commons.ALL_BASED_KEGG_PATHWAY, multipleTestingParameter,allBasedKEGGPathwayElements);
		/****************************************************************************/
		/**********************************KEGGPathway  ends*************************/
		/****************************************************************************/

		
		/****************************************************************************/
		/**********************************TF KEGGPathway starts*********************/
		/****************************************************************************/
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF_KEGGPATHWAY,Commons.TF_EXON_BASED_KEGG_PATHWAY, multipleTestingParameter,tfExonBasedKEGGPathwayElements);
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF_KEGGPATHWAY,Commons.TF_REGULATION_BASED_KEGG_PATHWAY, multipleTestingParameter,tfRegulationBasedKEGGPathwayElements);
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF_KEGGPATHWAY,Commons.TF_ALL_BASED_KEGG_PATHWAY, multipleTestingParameter,tfAllBasedKEGGPathwayElements);
		/****************************************************************************/
		/**********************************TF KEGGPathway  ends**********************/
		/****************************************************************************/

		
		/****************************************************************************/
		/**********************************TF CellLine KEGGPathway starts************/
		/****************************************************************************/
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF_CELLLINE_KEGGPATHWAY,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY, multipleTestingParameter,tfCellLineExonBasedKEGGPathwayElements);
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF_CELLLINE_KEGGPATHWAY,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY, multipleTestingParameter,tfCellLineRegulationBasedKEGGPathwayElements);
		writeAnnotationEnrichmentWithRSIDsSummaryTable(encodeCollaborationSummaryTablesDirectory, Commons.TF_CELLLINE_KEGGPATHWAY,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY, multipleTestingParameter,tfCellLineAllBasedKEGGPathwayElements);
		/****************************************************************************/
		/**********************************TF CellLine KEGGPathway  ends*************/
		/****************************************************************************/

	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
			
		//Multiple Testing Parameter for selection of enriched elements
		MultipleTestingType multipleTestingParameter = MultipleTestingType.convertStringtoEnum(args[6]);
		
		float bonferroniCorrectionSignificanceLevel = Float.parseFloat(args[7]); 
		
		//jobName starts
		String jobName = args[17].trim();
		if (jobName.isEmpty()){
			jobName = "noname";
		}
		//jobName ends
		
		
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		
		//ANNOTATION Element ArrayLists starts
		List<ElementAnnotationEnrichment> dnaseElements 	= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> tfElements 		= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> histoneElements 	= new ArrayList<ElementAnnotationEnrichment>();
		
		List<ElementAnnotationEnrichment> exonBasedKEGGPathwayElements 		= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> regulationBasedKEGGPathwayElements= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> allBasedKEGGPathwayElements 		= new ArrayList<ElementAnnotationEnrichment>();

		List<ElementAnnotationEnrichment> tfExonBasedKEGGPathwayElements 		= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> tfRegulationBasedKEGGPathwayElements	= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> tfAllBasedKEGGPathwayElements 		= new ArrayList<ElementAnnotationEnrichment>();

		List<ElementAnnotationEnrichment> tfCellLineExonBasedKEGGPathwayElements 		= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> tfCellLineRegulationBasedKEGGPathwayElements	= new ArrayList<ElementAnnotationEnrichment>();
		List<ElementAnnotationEnrichment> tfCellLineAllBasedKEGGPathwayElements 		= new ArrayList<ElementAnnotationEnrichment>();
		//ANNOTATION Element ArrayLists ends
		
		//ENRICHMENT Element HashMaps starts
		Map<String,ElementEnrichment> dnaseMap 		= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> tfMap 		= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> histoneMap 	= new HashMap<String,ElementEnrichment>();
		
		Map<String,ElementEnrichment> exonBasedKEGGPathwayMap 			= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> regulationBasedKEGGPathwayMap		= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> allBasedKEGGPathwayMap 			= new HashMap<String,ElementEnrichment>();

		Map<String,ElementEnrichment> tfExonBasedKEGGPathwayMap 		= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> tfRegulationBasedKEGGPathwayMap	= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> tfAllBasedKEGGPathwayMap 			= new HashMap<String,ElementEnrichment>();

		Map<String,ElementEnrichment> tfCellLineExonBasedKEGGPathwayMap 		= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> tfCellLineRegulationBasedKEGGPathwayMap 	= new HashMap<String,ElementEnrichment>();
		Map<String,ElementEnrichment> tfCellLineAllBasedKEGGPathwayMap 			= new HashMap<String,ElementEnrichment>();
		//ENRICHMENT Element HashMaps ends

		Map<String,String> overlap2RSIDMap = new HashMap<String,String>();		
		Map<String,String> overlap2EQTLMap = new HashMap<String,String>();
		
		//File provided from Chen Yao
		String inputFileName = "C:"+ System.getProperty("file.separator") +"Users" + System.getProperty("file.separator") + "burcakotlu" + System.getProperty("file.separator") + "Desktop" + System.getProperty("file.separator") + "ENCODE Collaboration" + System.getProperty("file.separator") +"eqtl-gene-anno-all.txt";
		
		
		String annotationResultsInputFileName = outputFolder  + Commons.ANNOTATION + System.getProperty("file.separator") + Commons.HG19_REFSEQ_GENE + System.getProperty("file.separator") + "_" + Commons.HG19_REFSEQ_GENE + ".txt";		
		String annotationResultsWithRsIdsWitheQTLSOutputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATION_FOR_ONE_PHENOTYPE + System.getProperty("file.separator")  + "_" + Commons.HG19_REFSEQ_GENE + "_withRsIds_withEQTLs.txt";
		
//		Commons.ANNOTATION_WITH_RS_IDS_ENRICHMENT_SUMMARY_TABLES
		
		/********************************************************************/
		/***********delete old files starts**********************************/
		String deleteDirectoryName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATION_WITH_RS_IDS_ENRICHMENT_SUMMARY_TABLES_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") ;
		FileOperations.deleteOldFiles(deleteDirectoryName);
		
		deleteDirectoryName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATION_FOR_ONE_PHENOTYPE + System.getProperty("file.separator") ;
		FileOperations.deleteOldFiles(deleteDirectoryName);
		/***********delete old files ends***********************************/
		/******************************************************************/
		
		
		//Fill the rsId 2 chrName_start_end HashMap
		//Fill chrName_start_end 2 rsId  HashMap
		readRSIDMap(inputFileName,overlap2RSIDMap,overlap2EQTLMap);
		
		//augment hg19RefSeqGene annotation results with rsIDs
		augmentAnnotationResultsWithRsIds(annotationResultsInputFileName,annotationResultsWithRsIdsWitheQTLSOutputFileName,overlap2RSIDMap,overlap2EQTLMap);
		
		//Read all annotation results except for hg19RefSeq Genes
		readAnnotationResults(outputFolder,dnaseElements,tfElements,histoneElements,exonBasedKEGGPathwayElements,regulationBasedKEGGPathwayElements,allBasedKEGGPathwayElements,tfExonBasedKEGGPathwayElements,tfRegulationBasedKEGGPathwayElements,tfAllBasedKEGGPathwayElements,tfCellLineExonBasedKEGGPathwayElements,tfCellLineRegulationBasedKEGGPathwayElements,tfCellLineAllBasedKEGGPathwayElements);
		
		//Read all enrichment results except for hg19RefSeq Genes
		readEnrichmentResults(outputFolder,bonferroniCorrectionSignificanceLevel,dnaseMap,tfMap,histoneMap,exonBasedKEGGPathwayMap,regulationBasedKEGGPathwayMap,allBasedKEGGPathwayMap,tfExonBasedKEGGPathwayMap,tfRegulationBasedKEGGPathwayMap,tfAllBasedKEGGPathwayMap,tfCellLineExonBasedKEGGPathwayMap,tfCellLineRegulationBasedKEGGPathwayMap,tfCellLineAllBasedKEGGPathwayMap);
		
		//Augment annotation results and enrichment results with rsIds
		augmentAnnotationandEnrichmentResultsWithRSIds(overlap2RSIDMap,dnaseElements,tfElements,histoneElements,exonBasedKEGGPathwayElements,regulationBasedKEGGPathwayElements,allBasedKEGGPathwayElements,tfExonBasedKEGGPathwayElements,tfRegulationBasedKEGGPathwayElements,tfAllBasedKEGGPathwayElements,tfCellLineExonBasedKEGGPathwayElements,tfCellLineRegulationBasedKEGGPathwayElements,tfCellLineAllBasedKEGGPathwayElements,dnaseMap,tfMap,histoneMap,exonBasedKEGGPathwayMap,regulationBasedKEGGPathwayMap,allBasedKEGGPathwayMap,tfExonBasedKEGGPathwayMap,tfRegulationBasedKEGGPathwayMap,tfAllBasedKEGGPathwayMap,tfCellLineExonBasedKEGGPathwayMap,tfCellLineRegulationBasedKEGGPathwayMap,tfCellLineAllBasedKEGGPathwayMap);
	
		//Write required summary tables
		writeAnnotationEnrichmentWithRSIDsSummaryTable(outputFolder,multipleTestingParameter,dnaseElements,tfElements,histoneElements,exonBasedKEGGPathwayElements,regulationBasedKEGGPathwayElements,allBasedKEGGPathwayElements,tfExonBasedKEGGPathwayElements,tfRegulationBasedKEGGPathwayElements,tfAllBasedKEGGPathwayElements,tfCellLineExonBasedKEGGPathwayElements,tfCellLineRegulationBasedKEGGPathwayElements,tfCellLineAllBasedKEGGPathwayElements);
		
		
	}

}
