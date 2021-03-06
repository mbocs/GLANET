/**
 * @author burcakotlu
 * @date Aug 30, 2014 
 * @time 4:10:57 PM
 * 
 * AnnotationBinaryMatrixForOnePhenotype filled with 1/0s
 * Row: givenInterval
 * Column: elementName
 * 
 * 
 */
package collaboration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auxiliary.FileOperations;

import common.Commons;

/**
 * 
 */
public class GivenIntervalVersusElementAnnotationBinaryMatrixForOnePhenotype {
	
	public static void readGivenIntervals(Map<String,Short> givenIntervalName2ArrayXIndexMap, String[] givenIntervalNames,String outputFolder){
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine;
		
		int indexOfFirstTab;
		int indexOfSecondTab;
			
		String chrNumber;
		int start_0BasedInclusive;
		int end_0BasedInclusive;
				
		String givenIntervalKey = null;
		short givenIntervalIndex = 0;
		
		try {
			fileReader = FileOperations.createFileReader(outputFolder + Commons.REMOVED_OVERLAPS_INPUT_FILE);
			bufferedReader = new BufferedReader(fileReader);
			
			while ((strLine = bufferedReader.readLine())!=null){
//				chr1	11862777	11862777
				indexOfFirstTab = strLine.indexOf('\t');
				indexOfSecondTab = strLine.indexOf('\t',indexOfFirstTab+1);
					
				chrNumber = strLine.substring(0,indexOfFirstTab);
				start_0BasedInclusive = Integer.parseInt(strLine.substring(indexOfFirstTab+1, indexOfSecondTab));
				end_0BasedInclusive = Integer.parseInt(strLine.substring(indexOfSecondTab+1));
				
				givenIntervalKey = chrNumber + "_"  + start_0BasedInclusive + "_" + end_0BasedInclusive;
				givenIntervalName2ArrayXIndexMap.put(givenIntervalKey, givenIntervalIndex);
				givenIntervalNames[givenIntervalIndex] = givenIntervalKey;
				givenIntervalIndex++;
			}//end of while
			
			bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	public static  void readAnnotations(Map<String,Short>  givenIntervalName2ArrayXIndexMap,List<String> elementNameList,String[] elementNames,short[][] annotationBinaryMatrix, String outputFolder, String elementType){
	
		String folderName = null;
		File folder;
		String fileName;
		String fileAbsolutePath;
		
		int indexofDot;
		String elementName;
		short elementIndex = 0;
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String strLine; 
		
		int indexofFirstTab;
		int indexofSecondTab;
		int indexofThirdTab;
		
		String givenIntervalKey = null;
		
		String givenIntervalChrNumber;
		int givenIntervalStart;
		int givenIntervalEnd;
		
		short givenIntervalIndex;
		
		if (elementType.equals(Commons.DNASE)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_DNASE;
		}else if (elementType.equals(Commons.HISTONE)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HISTONE;
		} else if (elementType.equals(Commons.TF)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS;
		}
		
		else if (elementType.equals(Commons.EXON_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS;
		}else if (elementType.equals(Commons.REGULATION_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS;
		}else if (elementType.equals(Commons.ALL_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS;
		}
		
		
		else if (elementType.equals(Commons.TF_EXON_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_EXON_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_REGULATION_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_REGULATION_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_ALL_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_ALL_BASED_KEGG_PATHWAY;
		}
		
		else if (elementType.equals(Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY;
		}else if (elementType.equals(Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY)){
			folderName = outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY;
		}
		
		folder = new File(folderName);
		
		if (folder.exists() && folder.isDirectory()){
			File[] files = folder.listFiles();
			
			   for(File file: files){
				   if(file.isFile()){
						fileName = file.getName();
						indexofDot = fileName.indexOf('.');
						elementName = fileName.substring(0,indexofDot);
						
						elementNameList.add(elementName);
						elementNames[elementIndex] = elementName;
									
         				fileAbsolutePath = file.getAbsolutePath();
         				
         				try {
							fileReader = FileOperations.createFileReader(fileAbsolutePath);
							bufferedReader = new BufferedReader(fileReader);
							
							//read annotations
							while((strLine = bufferedReader.readLine())!=null){
								if(!strLine.contains("Search")){
//									Searched for chr	given interval low	given interval high	dnase overlap chrom name	node low	node high	node CellLineName	node FileName
//									chr1	109817589	109817589	chr1	109817060	109817913	MCF7	MCF7DukeDNaseSeq.pk
									indexofFirstTab = strLine.indexOf('\t');
									indexofSecondTab = strLine.indexOf('\t',indexofFirstTab+1);
									indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
									
									givenIntervalChrNumber = strLine.substring(0,indexofFirstTab);
									givenIntervalStart = Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab));
									givenIntervalEnd =  Integer.parseInt(strLine.substring(indexofSecondTab+1,indexofThirdTab));
									
									givenIntervalKey = givenIntervalChrNumber + "_" + givenIntervalStart + "_" + givenIntervalEnd;
									
									givenIntervalIndex = givenIntervalName2ArrayXIndexMap.get(givenIntervalKey);
													
									annotationBinaryMatrix[givenIntervalIndex][elementIndex] = 1;
								}//End of IF
							}//End of WHILE
							
							bufferedReader.close();
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}   
         					
         				elementIndex++;
				   }
				   
			   } 
				   
		}
		
	}
	
	public static void writeAnnotationBinaryMatrix(
			short[][] annotationBinaryMatrix,
			String[] givenIntervalNames,
			int numberofGivenIntervals,
			String[] elementNames,
			int numberofElements, 
			String outputFolder, 
			String elementType,
			Map<String,String> overlap2RSIDMap){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String outputFileName = null;
		
		String rsId = null;

		if (elementType.equals(Commons.DNASE)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_DNASE;
		}else if (elementType.equals(Commons.HISTONE)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_HISTONE;
		}else if (elementType.equals(Commons.TF)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TF;
		}
		
		 else if (elementType.equals(Commons.EXON_BASED_KEGG_PATHWAY)){
				outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_EXONBASEDKEGG;
		}else if (elementType.equals(Commons.REGULATION_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_REGULATIONBASEDKEGG;
		}else if (elementType.equals(Commons.ALL_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_ALLBASEDKEGG;
		}
		
		
		 else if (elementType.equals(Commons.TF_EXON_BASED_KEGG_PATHWAY)){
				outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TFEXONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_REGULATION_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TFREGULATIONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_ALL_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TFALLBASEDKEGG;
		}
		
		 else if (elementType.equals(Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY)){
				outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TFCELLLINEEXONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TFCELLLINEREGULATIONBASEDKEGG;
		}else if (elementType.equals(Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY)){
			outputFileName = outputFolder + Commons.ENCODE_COLLABORATION + System.getProperty("file.separator") + Commons.ANNOTATIONBINARYMATRIX_TFCELLLINEALLBASEDKEGG;
		}
		
		try {
			fileWriter = FileOperations.createFileWriter(outputFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			//Write header file starts
			if (overlap2RSIDMap!=null){
				bufferedWriter.write("rsId" + "\t" + "givenInterval_chrName_start0Basedhg19_end0Basedhg19" + "\t");
				for(int i = 0; i<numberofElements; i++){
					bufferedWriter.write(elementNames[i]+ "\t");
				}
				bufferedWriter.write(System.getProperty("line.separator"));
			}else {
				bufferedWriter.write("givenInterval_chrName_start0Basedhg19_end0Basedhg19" + "\t");
				for(int i = 0; i<numberofElements; i++){
					bufferedWriter.write(elementNames[i]+ "\t");
				}
				bufferedWriter.write(System.getProperty("line.separator"));
			}
			
			//Write header file ends
		
			//Write givenIntervalName and 1s and 0s starts
			for(int i= 0; i<numberofGivenIntervals; i++){
				
				if (overlap2RSIDMap!=null){
					//get rsID
					rsId = overlap2RSIDMap.get(givenIntervalNames[i]);
					
					if (rsId!= null){
						bufferedWriter.write(rsId + "\t");
						
					}
				}//End of IF overlap2RSIDMap is not null
				
				
				
				
				bufferedWriter.write(givenIntervalNames[i]+ "\t");
				
				for (int j= 0; j<numberofElements; j++){
					bufferedWriter.write(annotationBinaryMatrix[i][j] + "\t");
				}
				
				bufferedWriter.write(System.getProperty("line.separator"));
				
			}
			//Write givenIntervalName and 1s and 0s ends

						
				
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			jobName = Commons.NO_NAME;
		}
		//jobName ends
		
		
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") + jobName + System.getProperty("file.separator");
		
//		/************************************************************************************/
//		/********************************FILL givenInterval2rsID starts**********************/
//		/************************************************************************************/		
//		//File provided from Chen Yao
//		String inputFileName = "C:"+ System.getProperty("file.separator") +"Users" + System.getProperty("file.separator") + "burcakotlu" + System.getProperty("file.separator") + "Desktop" + System.getProperty("file.separator") + "ENCODE Collaboration" + System.getProperty("file.separator") +"eqtl-gene-anno-all.txt";
//			
		Map<String,String> overlap2RSIDMap = null;		
//		Map<String,String> overlap2RSIDMap = new HashMap<String,String>();		
//		
//		ElementVersusNumberofOverlapsWithRSIDsAndEnrichmentForOnePhenotype.readRSIDMap(inputFileName, overlap2RSIDMap);
//		/************************************************************************************/
//		/********************************FILL givenInterval2rsID ends************************/
//		/************************************************************************************/		
		
		
		/************************************************************************************/
		/********************************HASH MAPS starts************************************/
		/************************************************************************************/		
		//Given Intervals
		Map<String,Short> givenIntervalName2ArrayXIndexMap 		= new HashMap<String,Short>();
		
		//Dnase
		//Histone
		//TF
		List<String> dnaseCellLineNameList 			= new ArrayList<String>();
		List<String> histoneNameCellLineNameList 	= new ArrayList<String>();
		List<String> tfNameCellLineNameList 		= new ArrayList<String>();
		
		//EXON
		//REGULATION
		//ALL
		List<String> exonBasedKEGGPathwayList 		= new ArrayList<String>();
		List<String> regulationBasedKEGGPathwayList = new ArrayList<String>();
		List<String> allBasedKEGGPathwayList 		= new ArrayList<String>();
	
		//TF EXON
		//TF REGULATION
		//TF ALL		
		List<String> tfExonBasedKEGGPathwayList			= new ArrayList<String>();
		List<String> tfRegulationBasedKEGGPathwayList 	= new ArrayList<String>();
		List<String> tfAllBasedKEGGPathwayList 			= new ArrayList<String>();
	
		//TF CELLLINE EXON
		//TF CELLLINE REGULATION
		//TF CELLLINE ALL		
		List<String> tfCellLineExonBasedKEGGPathwayList 		= new ArrayList<String>();
		List<String> tfCellLineRegulationBasedKEGGPathwayList 	= new ArrayList<String>();
		List<String> tfCellLineAllBasedKEGGPathwayList 			= new ArrayList<String>();
		/************************************************************************************/
		/********************************HASH MAPS ends**************************************/
		/************************************************************************************/	
		
		/************************************************************************************/
		/*******************************My assumptions starts********************************/
		/************************************************************************************/	
		//number of given intervals at most 10000
		//number of elements at most 10000
		final Integer MAXIMUM_NUMBER_OF_GIVEN_INTERVALS = new Integer (10000);
		final Integer MAXIMUM_NUMBER_OF_ELEMENTS = new Integer (10000);
		/************************************************************************************/
		/********************************My assumptions ends*********************************/
		/************************************************************************************/	
		
		
	
		
		/************************************************************************************/
		/********************************INDEX TO NAME ARRAYS starts*************************/
		/************************************************************************************/			
		String[] givenIntervalNames = new String[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS];
		
		String[] dnaseCelllineNames 		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] tfNameCellLineNames		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] histoneNameCellLineNames	= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		
		String[] exonBasedKEGGPathwayNames 		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] regulationBasedKEGGPathwayNames= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] allBasedKEGGPathwayNames 		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		
		String[] tfExonBasedKEGGPathwayNames	 	= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] tfRegulationBasedKEGGPathwayNames 	= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] tfAllBasedKEGGPathwayNames 		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		
		String[] tfCellLineExonBasedKEGGPathwayNames 		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] tfCellLineRegulationBasedKEGGPathwayNames 	= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		String[] tfCellLineAllBasedKEGGPathwayNames 		= new String[MAXIMUM_NUMBER_OF_ELEMENTS];
		/************************************************************************************/
		/********************************INDEX TO NAME ARRAYS ends***************************/
		/************************************************************************************/			

	
		/************************************************************************************/
		/***************************ANNOTATION BINARY MATRICES starts************************/
		/************************************************************************************/	
		short[][] annotationBinaryMatrixforDnase 	= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforHistone 	= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforTf 		= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];

		short[][] annotationBinaryMatrixforExonBasedKEGG 		= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforRegulationBasedKEGG 	= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforAllBasedKEGG 		= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];

		short[][] annotationBinaryMatrixforTFExonBasedKEGG 			= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforTFRegulationBasedKEGG 	= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforTFAllBasedKEGG 			= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];

		short[][] annotationBinaryMatrixforTFCellLineExonBasedKEGG 			= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforTFCellLineRegulationBasedKEGG 	= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];
		short[][] annotationBinaryMatrixforTFCellLineAllBasedKEGG 			= new short[MAXIMUM_NUMBER_OF_GIVEN_INTERVALS][MAXIMUM_NUMBER_OF_ELEMENTS];	
		/************************************************************************************/
		/***************************ANNOTATION BINARY MATRICES ends**************************/
		/************************************************************************************/		
	
		
		/***********************************************************************************************/
		/***************************READ GIVEN INPUT starts*********************************************/
		/***********************************************************************************************/		
		readGivenIntervals(givenIntervalName2ArrayXIndexMap,givenIntervalNames,outputFolder);
		/***********************************************************************************************/
		/***************************READ GIVEN INPUT ends***********************************************/
		/***********************************************************************************************/		
	
		
		
		/***********************************************************************************************/
		/***************************READ ANNOTATIONs FILL BINARY MATRICES starts************************/
		/***********************************************************************************************/		
		readAnnotations(givenIntervalName2ArrayXIndexMap,dnaseCellLineNameList,dnaseCelllineNames,annotationBinaryMatrixforDnase,outputFolder,Commons.DNASE);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfNameCellLineNameList,tfNameCellLineNames,annotationBinaryMatrixforTf,outputFolder,Commons.TF);
		readAnnotations(givenIntervalName2ArrayXIndexMap,histoneNameCellLineNameList,histoneNameCellLineNames,annotationBinaryMatrixforHistone,outputFolder,Commons.HISTONE);
		
		readAnnotations(givenIntervalName2ArrayXIndexMap,exonBasedKEGGPathwayList,exonBasedKEGGPathwayNames,annotationBinaryMatrixforExonBasedKEGG,outputFolder,Commons.EXON_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,regulationBasedKEGGPathwayList,regulationBasedKEGGPathwayNames,annotationBinaryMatrixforRegulationBasedKEGG,outputFolder,Commons.REGULATION_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,allBasedKEGGPathwayList,allBasedKEGGPathwayNames,annotationBinaryMatrixforAllBasedKEGG,outputFolder,Commons.ALL_BASED_KEGG_PATHWAY);
		
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfExonBasedKEGGPathwayList,tfExonBasedKEGGPathwayNames,annotationBinaryMatrixforTFExonBasedKEGG,outputFolder,Commons.TF_EXON_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfRegulationBasedKEGGPathwayList,tfRegulationBasedKEGGPathwayNames,annotationBinaryMatrixforTFRegulationBasedKEGG,outputFolder,Commons.TF_REGULATION_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfAllBasedKEGGPathwayList,tfAllBasedKEGGPathwayNames,annotationBinaryMatrixforTFAllBasedKEGG,outputFolder,Commons.TF_ALL_BASED_KEGG_PATHWAY);

		readAnnotations(givenIntervalName2ArrayXIndexMap,tfCellLineExonBasedKEGGPathwayList,tfCellLineExonBasedKEGGPathwayNames,annotationBinaryMatrixforTFCellLineExonBasedKEGG,outputFolder,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfCellLineRegulationBasedKEGGPathwayList,tfCellLineRegulationBasedKEGGPathwayNames,annotationBinaryMatrixforTFCellLineRegulationBasedKEGG,outputFolder,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY);
		readAnnotations(givenIntervalName2ArrayXIndexMap,tfCellLineAllBasedKEGGPathwayList,tfCellLineAllBasedKEGGPathwayNames,annotationBinaryMatrixforTFCellLineAllBasedKEGG,outputFolder,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY);
		/***********************************************************************************************/
		/***************************READ ANNOTATIONs FILL BINARY MATRICES ends**************************/
		/***********************************************************************************************/
		
		
		/***********************************************************************************************/
		/***************************WRITE ANNOTATION BINARY MATRICES starts*****************************/
		/***********************************************************************************************/	
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforDnase,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),dnaseCelllineNames,dnaseCellLineNameList.size(),outputFolder,Commons.DNASE,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTf,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfNameCellLineNames,tfNameCellLineNameList.size(),outputFolder,Commons.TF,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforHistone,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),histoneNameCellLineNames,histoneNameCellLineNameList.size(),outputFolder,Commons.HISTONE,overlap2RSIDMap);
		
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforExonBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),exonBasedKEGGPathwayNames,exonBasedKEGGPathwayList.size(),outputFolder,Commons.EXON_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforRegulationBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),regulationBasedKEGGPathwayNames,regulationBasedKEGGPathwayList.size(),outputFolder,Commons.REGULATION_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforAllBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),allBasedKEGGPathwayNames,allBasedKEGGPathwayList.size(),outputFolder,Commons.ALL_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFExonBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfExonBasedKEGGPathwayNames,tfExonBasedKEGGPathwayList.size(),outputFolder,Commons.TF_EXON_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFRegulationBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfRegulationBasedKEGGPathwayNames,tfRegulationBasedKEGGPathwayList.size(),outputFolder,Commons.TF_REGULATION_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFAllBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfAllBasedKEGGPathwayNames,tfAllBasedKEGGPathwayList.size(),outputFolder,Commons.TF_ALL_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFCellLineExonBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfCellLineExonBasedKEGGPathwayNames,tfCellLineExonBasedKEGGPathwayList.size(),outputFolder,Commons.TF_CELLLINE_EXON_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFCellLineRegulationBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfCellLineRegulationBasedKEGGPathwayNames,tfCellLineRegulationBasedKEGGPathwayList.size(),outputFolder,Commons.TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY,overlap2RSIDMap);
		writeAnnotationBinaryMatrix(annotationBinaryMatrixforTFCellLineAllBasedKEGG,givenIntervalNames,givenIntervalName2ArrayXIndexMap.size(),tfCellLineAllBasedKEGGPathwayNames,tfCellLineAllBasedKEGGPathwayList.size(),outputFolder,Commons.TF_CELLLINE_ALL_BASED_KEGG_PATHWAY,overlap2RSIDMap);		
		/***********************************************************************************************/
		/***************************WRITE ANNOTATION BINARY MATRICES ends*******************************/
		/***********************************************************************************************/		

	

	}

}
