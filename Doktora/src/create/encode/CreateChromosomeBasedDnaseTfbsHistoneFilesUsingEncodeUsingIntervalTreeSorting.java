/**
 * @author Burcak Otlu
 *
 * 
 */


/*
 * This program created unsorted and sorted chromosome based dnase, tfbs and histone intervals using ENCODE data
 * For sorting it uses Interval Tree Infix Traversal.
 * 
 * It takes 6 minutes.
 * 
 */

package create.encode;

import intervaltree.DnaseIntervalTreeNode;
import intervaltree.IntervalTree;
import intervaltree.IntervalTreeNode;
import intervaltree.TforHistoneIntervalTreeNode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ui.GlanetRunner;

import common.Commons;

import create.ChromosomeBasedFilesandOperations;
import enumtypes.ChromosomeName;
import enumtypes.NodeType;

public class CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting {
	
   
   static NumberofDNAElements numberofDNAElements = new NumberofDNAElements();
   
// Write the dnase into corresponding chromosome file  
   public static void writetoUnsortedChrBaseDnaseFile(Dnase dnase, List<BufferedWriter> bufferedWriterList){
	   
	   BufferedWriter bufferedWriter = null;
	   try {
		   
			 bufferedWriter = ChromosomeBasedFilesandOperations.getBufferedWriter(dnase.getChromName(), bufferedWriterList);		 
			 bufferedWriter.write(dnase.getChromName().convertEnumtoString() + "\t" + dnase.getStartPos() + "\t" + dnase.getEndPos() + "\t" + dnase.getCellLineName()+ "\t"+ dnase.getFileName()+System.getProperty("line.separator"));

		   		   
			if(dnase.getChromName().isCHROMOSOME1()){
				numberofDNAElements.setNumberofDnaseinChr1(numberofDNAElements.getNumberofDnaseinChr1()+1);
			}else if(dnase.getChromName().isCHROMOSOME2()){
				numberofDNAElements.setNumberofDnaseinChr2(numberofDNAElements.getNumberofDnaseinChr2()+1);
			}else if(dnase.getChromName().isCHROMOSOME3()){
				numberofDNAElements.setNumberofDnaseinChr3(numberofDNAElements.getNumberofDnaseinChr3()+1);
			}else if(dnase.getChromName().isCHROMOSOME4()){
				numberofDNAElements.setNumberofDnaseinChr4(numberofDNAElements.getNumberofDnaseinChr4()+1);
			}else if(dnase.getChromName().isCHROMOSOME5()){
				numberofDNAElements.setNumberofDnaseinChr5(numberofDNAElements.getNumberofDnaseinChr5()+1);
			}else if(dnase.getChromName().isCHROMOSOME6()){
				numberofDNAElements.setNumberofDnaseinChr6(numberofDNAElements.getNumberofDnaseinChr6()+1);
			}else if(dnase.getChromName().isCHROMOSOME7()){
				numberofDNAElements.setNumberofDnaseinChr7(numberofDNAElements.getNumberofDnaseinChr7()+1);
			}else if(dnase.getChromName().isCHROMOSOME8()){
				numberofDNAElements.setNumberofDnaseinChr8(numberofDNAElements.getNumberofDnaseinChr8()+1);
			}else if(dnase.getChromName().isCHROMOSOME9()){
				numberofDNAElements.setNumberofDnaseinChr9(numberofDNAElements.getNumberofDnaseinChr9()+1);
			}else if(dnase.getChromName().isCHROMOSOME10()){
				numberofDNAElements.setNumberofDnaseinChr10(numberofDNAElements.getNumberofDnaseinChr10()+1);
			}else if(dnase.getChromName().isCHROMOSOME11()){
				numberofDNAElements.setNumberofDnaseinChr11(numberofDNAElements.getNumberofDnaseinChr11()+1);
			}else if(dnase.getChromName().isCHROMOSOME12()){
				numberofDNAElements.setNumberofDnaseinChr12(numberofDNAElements.getNumberofDnaseinChr12()+1);
			}else if(dnase.getChromName().isCHROMOSOME13()){
				numberofDNAElements.setNumberofDnaseinChr13(numberofDNAElements.getNumberofDnaseinChr13()+1);
			}else if(dnase.getChromName().isCHROMOSOME14()){
				numberofDNAElements.setNumberofDnaseinChr14(numberofDNAElements.getNumberofDnaseinChr14()+1);
			}else if(dnase.getChromName().isCHROMOSOME15()){
				numberofDNAElements.setNumberofDnaseinChr15(numberofDNAElements.getNumberofDnaseinChr15()+1);
			}else if(dnase.getChromName().isCHROMOSOME16()){
				numberofDNAElements.setNumberofDnaseinChr16(numberofDNAElements.getNumberofDnaseinChr16()+1);
			}else if(dnase.getChromName().isCHROMOSOME17()){
				numberofDNAElements.setNumberofDnaseinChr17(numberofDNAElements.getNumberofDnaseinChr17()+1);
			}else if(dnase.getChromName().isCHROMOSOME18()){
				numberofDNAElements.setNumberofDnaseinChr18(numberofDNAElements.getNumberofDnaseinChr18()+1);
			}else if(dnase.getChromName().isCHROMOSOME19()){
				numberofDNAElements.setNumberofDnaseinChr19(numberofDNAElements.getNumberofDnaseinChr19()+1);
			}else if(dnase.getChromName().isCHROMOSOME20()){
				numberofDNAElements.setNumberofDnaseinChr20(numberofDNAElements.getNumberofDnaseinChr20()+1);
			}else if(dnase.getChromName().isCHROMOSOME21()){
				numberofDNAElements.setNumberofDnaseinChr21(numberofDNAElements.getNumberofDnaseinChr21()+1);
			}else if(dnase.getChromName().isCHROMOSOME22()){
				numberofDNAElements.setNumberofDnaseinChr22(numberofDNAElements.getNumberofDnaseinChr22()+1);
			}else if(dnase.getChromName().isCHROMOSOMEX()){
				numberofDNAElements.setNumberofDnaseinChrX(numberofDNAElements.getNumberofDnaseinChrX()+1);
			}else if(dnase.getChromName().isCHROMOSOMEY()){
				numberofDNAElements.setNumberofDnaseinChrY(numberofDNAElements.getNumberofDnaseinChrY()+1);
			} 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


   }
	
   
 //Write the histone into corresponding chromosome file
	public static void writetoUnsortedChrBaseHistoneFile(Histone histone, List<BufferedWriter> bufferedWriterList){
		try {
			
			BufferedWriter bufferedWriter = ChromosomeBasedFilesandOperations.getBufferedWriter(histone.getChromName() , bufferedWriterList);
			bufferedWriter.write(histone.getChromName().convertEnumtoString() + "\t" + histone.getStartPos() + "\t" + histone.getEndPos() + "\t" + histone.getHistoneName()+ "\t" + histone.getCellLineName()+ "\t"+ histone.getFileName()+System.getProperty("line.separator"));

			if(histone.getChromName().isCHROMOSOME1()){
				numberofDNAElements.setNumberofHistoneinChr1(numberofDNAElements.getNumberofHistoneinChr1()+1);
			}else if (histone.getChromName().isCHROMOSOME2()){
				numberofDNAElements.setNumberofHistoneinChr2(numberofDNAElements.getNumberofHistoneinChr2()+1);				
			}else if (histone.getChromName().isCHROMOSOME3()){
				numberofDNAElements.setNumberofHistoneinChr3(numberofDNAElements.getNumberofHistoneinChr3()+1);				
			}else if (histone.getChromName().isCHROMOSOME4()){
				numberofDNAElements.setNumberofHistoneinChr4(numberofDNAElements.getNumberofHistoneinChr4()+1);				
			}else if (histone.getChromName().isCHROMOSOME5()){
				numberofDNAElements.setNumberofHistoneinChr5(numberofDNAElements.getNumberofHistoneinChr5()+1);				
			}else if (histone.getChromName().isCHROMOSOME6()){
				numberofDNAElements.setNumberofHistoneinChr6(numberofDNAElements.getNumberofHistoneinChr6()+1);				
			}else if (histone.getChromName().isCHROMOSOME7()){
				numberofDNAElements.setNumberofHistoneinChr7(numberofDNAElements.getNumberofHistoneinChr7()+1);				
			}else if (histone.getChromName().isCHROMOSOME8()){
				numberofDNAElements.setNumberofHistoneinChr8(numberofDNAElements.getNumberofHistoneinChr8()+1);				
			}else if (histone.getChromName().isCHROMOSOME9()){
				numberofDNAElements.setNumberofHistoneinChr9(numberofDNAElements.getNumberofHistoneinChr9()+1);				
			}else if (histone.getChromName().isCHROMOSOME10()){
				numberofDNAElements.setNumberofHistoneinChr10(numberofDNAElements.getNumberofHistoneinChr10()+1);				
			}else if (histone.getChromName().isCHROMOSOME11()){
				numberofDNAElements.setNumberofHistoneinChr11(numberofDNAElements.getNumberofHistoneinChr11()+1);				
			}else if (histone.getChromName().isCHROMOSOME12()){
				numberofDNAElements.setNumberofHistoneinChr12(numberofDNAElements.getNumberofHistoneinChr12()+1);				
			}else if (histone.getChromName().isCHROMOSOME13()){
				numberofDNAElements.setNumberofHistoneinChr13(numberofDNAElements.getNumberofHistoneinChr13()+1);				
			}else if (histone.getChromName().isCHROMOSOME14()){
				numberofDNAElements.setNumberofHistoneinChr14(numberofDNAElements.getNumberofHistoneinChr14()+1);				
			}else if (histone.getChromName().isCHROMOSOME15()){
				numberofDNAElements.setNumberofHistoneinChr15(numberofDNAElements.getNumberofHistoneinChr15()+1);				
			}else if (histone.getChromName().isCHROMOSOME16()){
				numberofDNAElements.setNumberofHistoneinChr16(numberofDNAElements.getNumberofHistoneinChr16()+1);				
			}else if (histone.getChromName().isCHROMOSOME17()){
				numberofDNAElements.setNumberofHistoneinChr17(numberofDNAElements.getNumberofHistoneinChr17()+1);				
			}else if (histone.getChromName().isCHROMOSOME18()){
				numberofDNAElements.setNumberofHistoneinChr18(numberofDNAElements.getNumberofHistoneinChr18()+1);				
			}else if (histone.getChromName().isCHROMOSOME19()){
				numberofDNAElements.setNumberofHistoneinChr19(numberofDNAElements.getNumberofHistoneinChr19()+1);				
			}else if (histone.getChromName().isCHROMOSOME20()){
				numberofDNAElements.setNumberofHistoneinChr20(numberofDNAElements.getNumberofHistoneinChr20()+1);				
			}else if (histone.getChromName().isCHROMOSOME21()){
				numberofDNAElements.setNumberofHistoneinChr21(numberofDNAElements.getNumberofHistoneinChr21()+1);				
			}else if (histone.getChromName().isCHROMOSOME22()){
				numberofDNAElements.setNumberofHistoneinChr22(numberofDNAElements.getNumberofHistoneinChr22()+1);				
			}else if (histone.getChromName().isCHROMOSOMEX()){
				numberofDNAElements.setNumberofHistoneinChrX(numberofDNAElements.getNumberofHistoneinChrX()+1);				
			}else if (histone.getChromName().isCHROMOSOMEY()){
				numberofDNAElements.setNumberofHistoneinChrY(numberofDNAElements.getNumberofHistoneinChrY()+1);				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

   
		
	//Write the tfbs into thecorresponding chromosome file
	public static void writetoUnsortedChrBaseTfbsFile(TranscriptionFactorBindingSite tfbs, List<BufferedWriter> bufferedWriterList){
		
		try {
			
			BufferedWriter bufferedWriter = ChromosomeBasedFilesandOperations.getBufferedWriter(tfbs.getChromName(), bufferedWriterList);
			bufferedWriter.write(tfbs.getChromName().convertEnumtoString() + "\t" + tfbs.getStartPos() + "\t" + tfbs.getEndPos() + "\t" + tfbs.getTranscriptionFactorName()+ "\t" + tfbs.getCellLineName()+ "\t"+ tfbs.getFileName()+ System.getProperty("line.separator"));
			
			if(tfbs.getChromName().isCHROMOSOME1()){
				numberofDNAElements.setNumberofTfbsinChr1(numberofDNAElements.getNumberofTfbsinChr1()+1);
			}else if (tfbs.getChromName().isCHROMOSOME2()){
				numberofDNAElements.setNumberofTfbsinChr2(numberofDNAElements.getNumberofTfbsinChr2()+1);			
			}else if (tfbs.getChromName().isCHROMOSOME3()){
				numberofDNAElements.setNumberofTfbsinChr3(numberofDNAElements.getNumberofTfbsinChr3()+1);
			}else if (tfbs.getChromName().isCHROMOSOME4()){
				numberofDNAElements.setNumberofTfbsinChr4(numberofDNAElements.getNumberofTfbsinChr4()+1);
			}else if (tfbs.getChromName().isCHROMOSOME5()){
				numberofDNAElements.setNumberofTfbsinChr5(numberofDNAElements.getNumberofTfbsinChr5()+1);
			}else if (tfbs.getChromName().isCHROMOSOME6()){
				numberofDNAElements.setNumberofTfbsinChr6(numberofDNAElements.getNumberofTfbsinChr6()+1);				
			}else if (tfbs.getChromName().isCHROMOSOME7()){
				numberofDNAElements.setNumberofTfbsinChr7(numberofDNAElements.getNumberofTfbsinChr7()+1);
			}else if (tfbs.getChromName().isCHROMOSOME8()){
				numberofDNAElements.setNumberofTfbsinChr8(numberofDNAElements.getNumberofTfbsinChr8()+1);			
			}else if (tfbs.getChromName().isCHROMOSOME9()){
				numberofDNAElements.setNumberofTfbsinChr9(numberofDNAElements.getNumberofTfbsinChr9()+1);
			}else if (tfbs.getChromName().isCHROMOSOME10()){
				numberofDNAElements.setNumberofTfbsinChr10(numberofDNAElements.getNumberofTfbsinChr10()+1);
			}else if (tfbs.getChromName().isCHROMOSOME11()){
				numberofDNAElements.setNumberofTfbsinChr11(numberofDNAElements.getNumberofTfbsinChr11()+1);
			}else if (tfbs.getChromName().isCHROMOSOME12()){
				numberofDNAElements.setNumberofTfbsinChr12(numberofDNAElements.getNumberofTfbsinChr12()+1);
			}else if (tfbs.getChromName().isCHROMOSOME13()){
				numberofDNAElements.setNumberofTfbsinChr13(numberofDNAElements.getNumberofTfbsinChr13()+1);				
			}else if (tfbs.getChromName().isCHROMOSOME14()){
				numberofDNAElements.setNumberofTfbsinChr14(numberofDNAElements.getNumberofTfbsinChr14()+1);
			}else if (tfbs.getChromName().isCHROMOSOME15()){
				numberofDNAElements.setNumberofTfbsinChr15(numberofDNAElements.getNumberofTfbsinChr15()+1);
			}else if (tfbs.getChromName().isCHROMOSOME16()){
				numberofDNAElements.setNumberofTfbsinChr16(numberofDNAElements.getNumberofTfbsinChr16()+1);
			}else if (tfbs.getChromName().isCHROMOSOME17()){
				numberofDNAElements.setNumberofTfbsinChr17(numberofDNAElements.getNumberofTfbsinChr17()+1);
			}else if (tfbs.getChromName().isCHROMOSOME18()){
				numberofDNAElements.setNumberofTfbsinChr18(numberofDNAElements.getNumberofTfbsinChr18()+1);
			}else if (tfbs.getChromName().isCHROMOSOME19()){
				numberofDNAElements.setNumberofTfbsinChr19(numberofDNAElements.getNumberofTfbsinChr19()+1);
			}else if (tfbs.getChromName().isCHROMOSOME20()){
				numberofDNAElements.setNumberofTfbsinChr20(numberofDNAElements.getNumberofTfbsinChr20()+1);
			}else if (tfbs.getChromName().isCHROMOSOME21()){
				numberofDNAElements.setNumberofTfbsinChr21(numberofDNAElements.getNumberofTfbsinChr21()+1);
			}else if (tfbs.getChromName().isCHROMOSOME22()){
				numberofDNAElements.setNumberofTfbsinChr22(numberofDNAElements.getNumberofTfbsinChr22()+1);
			}else if (tfbs.getChromName().isCHROMOSOMEX()){
				numberofDNAElements.setNumberofTfbsinChrX(numberofDNAElements.getNumberofTfbsinChrX()+1);
			}else if (tfbs.getChromName().isCHROMOSOMEY()){
				numberofDNAElements.setNumberofTfbsinChrY(numberofDNAElements.getNumberofTfbsinChrY()+1);
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	Get Dnase data from strLine and cellLineDnase and fill dnase
	public static void getDnaseData(String strLine, CellLineDnase cellLineDnase, Dnase dnase){
//		get these data from cellLineDnase
//		In dnase there is no dnase name 
		dnase.setCellLineName(cellLineDnase.getCellLineName());
		dnase.setFileName(cellLineDnase.getFileName());
		
//		get these data from strLine		
		int indexofFirstTab =0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		int indexofSixthTab=0;
		
		int start =0;
		int end = 0;
		
		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab= strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab= strLine.indexOf('\t',indexofFourthTab+1);
		indexofSixthTab= strLine.indexOf('\t',indexofFifthTab+1);
		
		start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
		end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
		
		//Encode data 
		//start 0-based inclusive
		//end exclusive
		
		//my convention is
		//start 0-based inclusive
		//end 0-based inclusive
		
		dnase.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));
		dnase.setStartPos(start);
		dnase.setEndPos(end-1);	
		
		// +/- or . if no orientation is assigned.
		dnase.setStrand(strLine.substring(indexofFifthTab+1, indexofSixthTab).charAt(0));
		
		
	}
	
	
//	Get Histone data from strLine and cellLineNameHistoneName and fill histone
		public static void getHistoneData(String strLine, CellLineHistone cellLineNameHistoneName, Histone histone){
		//get these data from cellLineNameHistoneName
		histone.setHistoneName(cellLineNameHistoneName.getHistoneName());
		histone.setCellLineName(cellLineNameHistoneName.getCellLineName());
		histone.setFileName(cellLineNameHistoneName.getFileName());
				
//		get these data from strLine		
		int indexofFirstTab =0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		int indexofSixthTab=0;
		
		int start =0;
		int end =0;
		
		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab= strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab= strLine.indexOf('\t',indexofFourthTab+1);
		indexofSixthTab= strLine.indexOf('\t',indexofFifthTab+1);
		
	
		start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
		end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
			
		//Encode data 
		//start 0-based inclusive
		//end exclusive
		
		//my convention is
		//start 0-based inclusive
		//end 0-based inclusive

	
		histone.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));
		histone.setStartPos(start);
		histone.setEndPos(end-1);
		
		// +/- or . if no orientation is assigned.
		histone.setStrand(strLine.substring(indexofFifthTab+1, indexofSixthTab).charAt(0));
			
		
			
	}
	
	
	//Get Tfbs data from strLine and cellLineTranscriptionFactor then fill tfbs
	// Get chrName, transcriptionFactorBindingSite start position and end position
	
	public static void getTranscriptionFactorBindingSiteData(String strLine, CellLineTranscriptionFactor cellLineTranscriptionFactor, TranscriptionFactorBindingSite tfbs){
		// get these data from cellLineTranscriptionFactor
		tfbs.setTranscriptionFactorName(cellLineTranscriptionFactor.getTranscriptionFactorName());
		tfbs.setCellLineName(cellLineTranscriptionFactor.getCellLineName());
		tfbs.setFileName(cellLineTranscriptionFactor.getFileName());
				
//		get these data from strLine
		
		int indexofFirstTab =0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		int indexofSixthTab=0;
		
		int start =0;
		int end =0;
	
		
		indexofFirstTab 	= strLine.indexOf('\t');
		indexofSecondTab 	= strLine.indexOf('\t', indexofFirstTab+1);
		indexofThirdTab 	= strLine.indexOf('\t',indexofSecondTab+1);
		indexofFourthTab= strLine.indexOf('\t',indexofThirdTab+1);
		indexofFifthTab= strLine.indexOf('\t',indexofFourthTab+1);
		indexofSixthTab= strLine.indexOf('\t',indexofFifthTab+1);
		
		start = Integer.parseInt(strLine.substring(indexofFirstTab+1, indexofSecondTab));
		end = Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab));
	
		//Encode data 
		//start 0-based inclusive
		//end exclusive
		
		//my convention is
		//start 0-based inclusive
		//end 0-based inclusive
	
		tfbs.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));
		tfbs.setStartPos(start);
		tfbs.setEndPos(end-1);
		
		// +/- or . if no orientation is assigned.
		tfbs.setStrand(strLine.substring(indexofFifthTab+1, indexofSixthTab).charAt(0));

		
		
		
	}
	
	public static void getCellLineName(CellLineDnase cellLineDnase, String fileName){
//		example fileName
//		It seems that there are five different kinds of labs 
//SM_SFMDukeDNaseSeq.pk
//hESCT0-DS13133.peaks.fdr0.01.hg19.bed
//idrPool.GM12878-DS9432-DS10671.z_OV_GM12878-DS10671.z_VS_GM12878-DS9432.z.npk2.narrowPeak
//idrPool.GM12878_FAIRE_BP_TP_peaks_OV_GM12878_B1_peaks_VS_UncFAIRE_GM12878_B2_peaks.npk2.narrowPeak
//idrPool.GM12878_DNaseHS_BP_TP_P_peaks_OV_DukeDNase_GM12878_B4_peaks_VS_DukeDNase_GM12878_B5_peaks.npk2.narrowPeak		
		
		int start = 0;
		int indexofDukeDnase = fileName.indexOf("DukeDNase");
		int indexof_DS = fileName.indexOf("-DS");
		int indexof_Dnase = fileName.indexOf("_DNase");
		int indexof_FAIRE = fileName.indexOf("_FAIRE");
		int indexofIdrPool = fileName.indexOf("idrPool");
		
		if (indexofDukeDnase >=0){
			
			if (indexof_Dnase>=0){
				if (indexofIdrPool>=0){
					start = start + "idrPool.".length();
					cellLineDnase.setCellLineName(fileName.substring(start, indexof_Dnase).toUpperCase(Locale.ENGLISH));																
				}else{
					GlanetRunner.appendLog("Unknown Lab: " + fileName);
				}
			} else{			
				cellLineDnase.setCellLineName(fileName.substring(start, indexofDukeDnase).toUpperCase(Locale.ENGLISH));
			}
		}else if (indexof_DS>=0){
			if (indexofIdrPool < 0){
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_DS).toUpperCase(Locale.ENGLISH));			
			} else if (indexofIdrPool>=0){
				start = start + "idrPool.".length();
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_DS).toUpperCase(Locale.ENGLISH));								
			}else{
				GlanetRunner.appendLog("Unknown Lab: " + fileName);
			}
		}else if (indexof_Dnase>=0){
			if (indexofIdrPool>=0){
				start = start + "idrPool.".length();
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_Dnase).toUpperCase(Locale.ENGLISH));																
			}else{
				GlanetRunner.appendLog("Unknown Lab: " + fileName);
			}
		}else if (indexof_FAIRE>=0){
			if (indexofIdrPool>=0){
				start = start + "idrPool.".length();
				cellLineDnase.setCellLineName(fileName.substring(start, indexof_FAIRE).toUpperCase(Locale.ENGLISH));																				
			}else{
				GlanetRunner.appendLog("Unknown Lab: " + fileName);
			}
			
		}else{
			GlanetRunner.appendLog("Unknown Lab: " + fileName);
		}
		
		cellLineDnase.setFileName(fileName);
	}
	

	public static void getCellLineNameandHistoneName(CellLineHistone cellLineHistone, String fileName) {
//		example fileName
//		It seems that there are three different kinds of labs 
//		wgEncodeUwHistoneNhekH3k4me3StdAln.narrowPeak
//		wgEncodeSydhHistoneU2osH3k9me3UcdAln.narrowPeak
//		wgEncodeBroadHistoneOsteoblH3k9me3StdAln.narrowPeak
		
		int uwHistoneStart = fileName.indexOf("UwHistone");
		int sydhHistoneStart = fileName.indexOf("SydhHistone");
		int broadHistoneStart = fileName.indexOf("BroadHistone");		
		
		int start =0;
		int cellLineNameStart = 0;
		int histoneNameStart = 0;
		int laborProtocolNameStart =0;
		
		if (uwHistoneStart >=0){
			start = uwHistoneStart+"UwHistone".length();
		} else if (sydhHistoneStart>=0){
			start = sydhHistoneStart + "SydhHistone".length();
		}else if (broadHistoneStart>=0){
			start = broadHistoneStart+ "BroadHistone".length();
		}else{
			GlanetRunner.appendLog("Unknown Lab in  Histone Files");	
		}
		
		
//		Find the First,  Second and Third Upper Case Letters in filename after the lab name		
		for (int i = start; i<fileName.length(); i++){
			if (Character.isUpperCase(fileName.charAt(i))){
				cellLineNameStart = i;
				break;
			}				
		}
		
		for (int i = cellLineNameStart+1; i<fileName.length(); i++){
			if (Character.isUpperCase(fileName.charAt(i))){
				histoneNameStart = i;
				break;
			}				
		}
		
		for (int i = histoneNameStart+1; i<fileName.length(); i++){
			if (Character.isUpperCase(fileName.charAt(i))){
				laborProtocolNameStart = i;
				break;
			}								
		}
		
		cellLineHistone.setCellLineName(fileName.substring(cellLineNameStart, histoneNameStart).toUpperCase(Locale.ENGLISH));
		cellLineHistone.setHistoneName(fileName.substring(histoneNameStart,laborProtocolNameStart).toUpperCase(Locale.ENGLISH));
		cellLineHistone.setFileName(fileName);		
		
	}
	
	
	
	
	
	public static void getCellLineNameandTranscriptionFactorName(CellLineTranscriptionFactor cellLineandTranscriptionFactor ,String fileName){
//		example fileName
//		It seems that there are three kind of fileName classes
//		spp.optimal.wgEncodeBroadHistoneGm12878CtcfStdAlnRep0_VS_wgEncodeBroadHistoneGm12878ControlStdAlnRep0.narrowPeak
//		spp.optimal.wgEncodeOpenChromChipFibroblCtcfAlnRep0_VS_wgEncodeOpenChromChipFibroblInputAln.narrowPeak
//		spp.optimal.wgEncodeSydhTfbsK562Pol2IggmusAlnRep0_VS_wgEncodeSydhTfbsK562InputIggmusAlnRep0.narrowPeak
		
		int tfbsStart = fileName.indexOf("Tfbs");		
		int openChromChipStart = fileName.indexOf("OpenChromChip");
		int broadHistoneStart = fileName.indexOf("BroadHistone");
		
		int start = 0;
		
		int cellLineNameStart = 0;
		int transcriptionFactorNameStart = 0;
		int laborProtocolNameStart = 0;
		
		if (tfbsStart >= 0){
//			Tfbs is 4 char long
			start = tfbsStart+"Tfbs".length();
		} else if (openChromChipStart>=0){
//			OpenChromChip
			start = openChromChipStart+ "OpenChromChip".length();			
		}else if (broadHistoneStart >=0){
//			BroadHistone
			start = broadHistoneStart+"BroadHistone".length();			
		}else{
			GlanetRunner.appendLog("Unknown Lab in TFBS file");	
		}
			
		
	
//			Find the First,  Second and Third Upper Case Letters in filename after the lab name
			
			for (int i = start; i<fileName.length(); i++){
				if (Character.isUpperCase(fileName.charAt(i))){
					cellLineNameStart = i;
					break;
				}				
			}
			
			for (int i = cellLineNameStart+1; i<fileName.length(); i++){
				if (Character.isUpperCase(fileName.charAt(i))){
					transcriptionFactorNameStart = i;
					break;
				}				
			}
			
			for (int i = transcriptionFactorNameStart+1; i<fileName.length(); i++){
				if (Character.isUpperCase(fileName.charAt(i))){
					laborProtocolNameStart = i;
					break;
				}								
			}
			
			cellLineandTranscriptionFactor.setCellLineName(fileName.substring(cellLineNameStart, transcriptionFactorNameStart).toUpperCase(Locale.ENGLISH));
			cellLineandTranscriptionFactor.setTranscriptionFactorName(fileName.substring(transcriptionFactorNameStart,laborProtocolNameStart).toUpperCase(Locale.ENGLISH));
			cellLineandTranscriptionFactor.setFileName(fileName);
			
	}
	
	
	public static void readEncodeDnaseFilesandWriteUnsortedChromBaseDnaseFiles(File directory,List<BufferedWriter> bufferedWriterList){
//		Use same cellLineDnase object for each file
		CellLineDnase cellLineDnase = new CellLineDnase();
//		Use the same Dnase object for each read line
		Dnase dnase = new Dnase();
		
		long numberofIntervalsInDnaseFiles = 0;
		
		if(!directory.exists()){
			 GlanetRunner.appendLog("No File/Dir" + directory.getName()); 
		 }
		
		 // Reading directory contents
		 if(directory.isDirectory()){// a directory!
			 
			    File[] files = directory.listFiles();
			    int numberofDnaseFiles= files.length;
			    
			    System.out.printf("Number of Dnase Files %d in %s" + System.getProperty("line.separator"), files.length, directory.getAbsolutePath());
				
		        for (int i = 0; i < numberofDnaseFiles; i++) {
		        	FileReader fileReader =null;
		        	BufferedReader br = null;
		        	
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		File file = files[i];		        		
		        		
		        		String fileName = file.getName();		
		    			String filePath = file.getPath();
		    			
		    			 // Open the file that is the first 		    			  		    			  
		    			try {
		    				    fileReader = new FileReader(filePath);
		    					br = new BufferedReader(fileReader);
		    					
		    					String strLine;
		    					
//				        		Get the cell line name from file name
		    					getCellLineName(cellLineDnase,fileName);
		    				  
		    					try {
		    						while ((strLine = br.readLine()) != null)   {
		    							getDnaseData(strLine, cellLineDnase, dnase);
		    												
		    							writetoUnsortedChrBaseDnaseFile(dnase,bufferedWriterList);
		    							numberofIntervalsInDnaseFiles++;
		    						}
		    					} catch (IOException e) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}
		    						
//		    			Close the Buffered Reader
		    					br.close();
		    					
		    			} catch (FileNotFoundException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			 
		        	}//Check for each file and read each file		        	
//		            GlanetRunner.appendLog(files[i]);		            		         		           		            
		        }	// End of For -----reading each file in the directory	  
		        
				GlanetRunner.appendLog("number of Intervals In Dnase Files:  " + numberofIntervalsInDnaseFiles);
    			
	        } //End of if: For all files in this directory
		
	}
	
	
	
	public static void readEncodeHistoneFilesandWriteUnsortedChromBaseHistoneFiles(File mainDirectory, List<BufferedWriter> bufferedWriterList){
//		Use same cellLineHistone object for each file
		CellLineHistone cellLineNameHistoneName  = new CellLineHistone();
//		Use the same histone object for each read line
		Histone histone = new Histone();
		
		long numberofIntervalsInHistoneFiles = 0;
		
		
		 if(!mainDirectory.exists()){
			 GlanetRunner.appendLog("No File/Dir"); 
		 }
        
		 // Reading directory contents
		 if(mainDirectory.isDirectory()){// a directory!
			 
			    File[] files = mainDirectory.listFiles();
			    int numberofHistoneFiles= files.length;
			    
			    System.out.printf("Number of Histone Files %d in %s" + System.getProperty("line.separator"), files.length,mainDirectory.getAbsolutePath());
			    
		        for (int i = 0; i < numberofHistoneFiles; i++) {
		        	FileReader fileReader =null;
		        	BufferedReader br = null;
		        	
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		File file = files[i];		        		
		        		
		        		String fileName = file.getName();		
		    			String filePath = file.getPath();
		    			
		    			 // Open the file that is the first 		    			  		    			  
		    			try {
		    				    fileReader = new FileReader(filePath);
		    					br = new BufferedReader(fileReader);
		    					
		    					String strLine;
		    					
//				        		Get the cell line name and histone name from file name
		    					getCellLineNameandHistoneName(cellLineNameHistoneName,fileName);
		    				  
		    					try {
		    						while ((strLine = br.readLine()) != null)   {
		    							getHistoneData(strLine, cellLineNameHistoneName, histone);
		    							writetoUnsortedChrBaseHistoneFile(histone,bufferedWriterList);
		    							numberofIntervalsInHistoneFiles++;
		    						}
		    					} catch (IOException e) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}
		    						
//		    				close the Buffered Reader
//		    			The typically use is to close the outermost reader in the chain		
		    					br.close();
		    					
		    						
		    			} catch (FileNotFoundException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			 

		        	}//Check for each file and read each file
		        	
//		            GlanetRunner.appendLog(files[i]);		            
		         
		           		            
		        }	// End of For -----reading each file in the directory
	        	
				GlanetRunner.appendLog("number of Intervals In Histone Files: " + numberofIntervalsInHistoneFiles);
	    		
	        } //End of if: For all files in this directory
				
	}	
	
	public static void readEncodeTfbsFilesandWriteUnsortedChromBaseTfbsFiles(File mainDirectory, List<BufferedWriter> bufferedWriterList){
//		Use same CellLineTranscriptionFactor object for each file
		CellLineTranscriptionFactor cellLineandTranscriptionFactorName  = new CellLineTranscriptionFactor();
//		Use the same transcription factor binding site object for each read line
		TranscriptionFactorBindingSite tfbs = new TranscriptionFactorBindingSite();
		
		int numberofIntervalsInTranscriptionFactorFiles = 0;
		
		 if(!mainDirectory.exists()){
			 GlanetRunner.appendLog("No File/Dir"); 
		 }
        
		 // Reading directory contents
		 if(mainDirectory.isDirectory()){// a directory!
			 
			    File[] files = mainDirectory.listFiles();
			    int numberofTfbsFiles= files.length;
			    
			    System.out.printf("Number of Tfbs Files %d in %s" + System.getProperty("line.separator"), files.length, mainDirectory.getAbsolutePath());
			       
		        for (int i = 0; i < numberofTfbsFiles; i++) {
		        	FileReader fileReader =null;
		        	BufferedReader br = null;
		        	
		        	if (files[i].isFile()){

//		        		read the content of each file		        		    					
		        		File file = files[i];		        		
		        		
		        		String fileName = file.getName();		
		    			String filePath = file.getPath();
		    			
		    			 // Open the file that is the first 		    			  		    			  
		    			try {
		    				    fileReader = new FileReader(filePath);
		    					br = new BufferedReader(fileReader);
		    					
		    					String strLine;
		    					
//				        		Get the cell line name and transcription factor name from file name
		    					getCellLineNameandTranscriptionFactorName(cellLineandTranscriptionFactorName,fileName);
		    				  
		    					try {
		    						while ((strLine = br.readLine()) != null)   {
		    							getTranscriptionFactorBindingSiteData(strLine, cellLineandTranscriptionFactorName, tfbs);
		    							writetoUnsortedChrBaseTfbsFile(tfbs,bufferedWriterList);
		    							numberofIntervalsInTranscriptionFactorFiles++;
		    						}
		    					} catch (IOException e) {
		    						// TODO Auto-generated catch block
		    						e.printStackTrace();
		    					}
		    						
//		    				close the Buffered Reader
//		    			The typically use is to close the outermost reader in the chain		
		    					br.close();
		    					
		    					
		    			} catch (FileNotFoundException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			 

		        	}//Check for each file and read each file
		        	
//		            GlanetRunner.appendLog(files[i]);		            
		         
		           		            
		        }	// End of For -----reading each file in the directory
	        	
		        
				GlanetRunner.appendLog("number of Intervals In Transcription Factor Files: " + numberofIntervalsInTranscriptionFactorFiles);
			    
	        } //For all files in this directory
				
	}
		
	
//	Common function for tfbs, histone and dnase
//			Close the outermost Stream/Writer/Reader in the chain.
//			Which is BufferedWriter in this case
//			It will flush it buffers, close and propagate close to the next Writer in the chain.
//			There is no need to close the FileWriter explicitly
//			But if we want to close the FileWriter explicitly
//			First we should close the BufferedWriter
//			Then we should close the FileWriter
//			Otherwise we will lose the remaining chars in the buffers of BufferedWriter
	
//			After you close the BufferedReder (it flushes it buffers and then close and close propagate) you can also close FileWriter explicitly
//			no problem, but also no  need
//			commons.getChr10TfbsFw().close();
				

	

	
	public static void readUnsortedChromBaseHistoneFilesSortWriteSortedChromosomeBaseHistoneFiles(String dataFolder){
		
		int indexofFirstTab=0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		
		//Read the unsorted chromosome base histone file into a list
		for(int i=1; i<=24; i++){
			FileReader fileReader = null;
			BufferedReader br = null;	
			String strLine;
			
			FileWriter fileWriter = null;
			BufferedWriter bw = null;
			
			try {
				
					fileReader = ChromosomeBasedFilesandOperations.getUnsortedHistoneFileReader(i,dataFolder);
					fileWriter = ChromosomeBasedFilesandOperations.getSortedHistoneFileWriter(i,dataFolder);
													
					br = new BufferedReader(fileReader);					
					bw = new BufferedWriter(fileWriter);

					IntervalTree histoneIntervalTree = new IntervalTree();
					Histone histone = new Histone();
					
					while ((strLine = br.readLine()) != null)   {
						  // ADD the content to the ArrayList
						
						indexofFirstTab = strLine.indexOf('\t');
						indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
						indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
						indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
						indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
						
						if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || indexofFifthTab <0){
							GlanetRunner.appendLog("Unexpected histone format in Unsorted Histone File");
							GlanetRunner.appendLog("For chromosome " + i);
							GlanetRunner.appendLog(strLine);								
						}
						
						histone.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));							
						histone.setStartPos(Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab)));
						histone.setEndPos(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));							
						histone.setHistoneName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
						histone.setCellLineName(strLine.substring(indexofFourthTab+1, indexofFifthTab));
						histone.setFileName(strLine.substring(indexofFifthTab+1));
						
						IntervalTreeNode node = new TforHistoneIntervalTreeNode(histone.getChromName(), histone.getStartPos(), histone.getEndPos(), histone.getHistoneName(), histone.getCellLineName(), histone.getFileName(),NodeType.ORIGINAL);
						
						histoneIntervalTree.intervalTreeInsert(histoneIntervalTree,node);
					}//End of while
					
					
//					write sorted histone list to file
					histoneIntervalTree.intervalTreeInfixTraversal(histoneIntervalTree.getRoot(), bw, Commons.HISTONE);
					
//					Remove memory allocation for histoneList
					histoneIntervalTree = null;
					histone = null;
					
					
					br.close();
					bw.close();
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
										
		} // open unsorted chrom base histone file one by one
	
	}
	
	public static void readUnsortedChromBaseDnaseFilesSortWriteSortedChromosomeBaseDnaseFiles(String dataFolder){
		int indexofFirstTab=0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		
		//Read the unsorted chromosome base dnase file into a list
		for(int i=1; i<=24; i++){
			FileReader fileReader = null;
			BufferedReader br = null;	
			String strLine;
			
			FileWriter fileWriter = null;
			BufferedWriter bw = null;
			
			try {			
					fileReader = ChromosomeBasedFilesandOperations.getUnsortedDnaseFileReader(i,dataFolder);
					fileWriter = ChromosomeBasedFilesandOperations.getSortedDnaseFileWriter(i,dataFolder);
									
					br = new BufferedReader(fileReader);					
					bw = new BufferedWriter(fileWriter);

					IntervalTree dnaseIntervalTree = new IntervalTree();					
					Dnase dnase = new Dnase();
					
					try {
						while ((strLine = br.readLine()) != null)   {
							  // ADD the content to the ArrayList
							
							indexofFirstTab = strLine.indexOf('\t');
							indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
							indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
							
							if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0)){
								GlanetRunner.appendLog("Unexpected tfbs format in Unsorted Dnase File");
								GlanetRunner.appendLog("For chromosome " + i);
								GlanetRunner.appendLog(strLine);								
							}
							
							dnase.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));							
							dnase.setStartPos(Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab)));
							dnase.setEndPos(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));							
							dnase.setCellLineName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							dnase.setFileName(strLine.substring(indexofFourthTab+1));

							IntervalTreeNode node = new DnaseIntervalTreeNode(dnase.getChromName(),dnase.getStartPos(), dnase.getEndPos(),dnase.getCellLineName(), dnase.getFileName(),NodeType.ORIGINAL);							
							
							dnaseIntervalTree.intervalTreeInsert(dnaseIntervalTree,node);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
//					write sorted dnase Interval Tree to file
					dnaseIntervalTree.intervalTreeInfixTraversal(dnaseIntervalTree.getRoot(), bw, Commons.DNASE);
					
					
//					Remove memory allocation for dnaseList
					dnaseIntervalTree = null;
					dnase = null;
					
					br.close();
					bw.close();
					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
		} // open unsorted chrom base dnase file one by one
	
	}
	
	public static void readUnsortedChromBaseTfbsFilesSortWriteSortedChromosomeBaseTfbsFiles(String dataFolder){
		
		int indexofFirstTab=0;
		int indexofSecondTab=0;
		int indexofThirdTab=0;
		int indexofFourthTab=0;
		int indexofFifthTab=0;
		
		//Read the unsorted chromosome base tfbs file into a list
		for(int i=1; i<=24; i++){
			FileReader fileReader = null;
			BufferedReader br = null;	
			String strLine;
			
			FileWriter fileWriter = null;
			BufferedWriter bw = null;
			
			try {	
				
					fileReader = ChromosomeBasedFilesandOperations.getUnsortedTfbsFileReader(i,dataFolder);
					fileWriter = ChromosomeBasedFilesandOperations.getSortedTfbsFileWriter(i,dataFolder);					
									
					br = new BufferedReader(fileReader);					
					bw = new BufferedWriter(fileWriter);

					IntervalTree tfbsIntervalTree = new IntervalTree ();
					TranscriptionFactorBindingSite tfbs = new TranscriptionFactorBindingSite();
					
					try {
						while ((strLine = br.readLine()) != null)   {
							  // ADD the content to the ArrayList
							
							indexofFirstTab = strLine.indexOf('\t');
							indexofSecondTab = strLine.indexOf('\t', indexofFirstTab+1);
							indexofThirdTab = strLine.indexOf('\t',indexofSecondTab+1);
							indexofFourthTab = strLine.indexOf('\t',indexofThirdTab+1);
							indexofFifthTab = strLine.indexOf('\t',indexofFourthTab+1);
							
							if ((indexofFirstTab<0) || (indexofSecondTab<0) || (indexofThirdTab<0) || (indexofFourthTab<0) || indexofFifthTab <0){
								GlanetRunner.appendLog("Unexpected tfbs format in Unsorted Tfbs File");
								GlanetRunner.appendLog("For chromosome " + i);
								GlanetRunner.appendLog(strLine);								
							}
							
							tfbs.setChromName(ChromosomeName.convertStringtoEnum(strLine.substring(0, indexofFirstTab)));							
							tfbs.setStartPos(Integer.parseInt(strLine.substring(indexofFirstTab+1,indexofSecondTab)));
							tfbs.setEndPos(Integer.parseInt(strLine.substring(indexofSecondTab+1, indexofThirdTab)));							
							tfbs.setTranscriptionFactorName(strLine.substring(indexofThirdTab+1, indexofFourthTab));
							tfbs.setCellLineName(strLine.substring(indexofFourthTab+1, indexofFifthTab));
							tfbs.setFileName(strLine.substring(indexofFifthTab+1));
							
							IntervalTreeNode node = new TforHistoneIntervalTreeNode(tfbs.getChromName(), tfbs.getStartPos(), tfbs.getEndPos(), tfbs.getTranscriptionFactorName(), tfbs.getCellLineName(), tfbs.getFileName(),NodeType.ORIGINAL);
							tfbsIntervalTree.intervalTreeInsert(tfbsIntervalTree, node);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					

//					write sorted tfbs list to file
					tfbsIntervalTree.intervalTreeInfixTraversal(tfbsIntervalTree.getRoot(), bw, Commons.TF);
					
//					Remove memory allocation for tfbsList
					tfbsIntervalTree = null;
					tfbs = null;
					
					br.close();
					bw.close();
					
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
										
		} // open unsorted chrom base tfbs file one by one
		
		
		
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
	
		File dnaseDir1 	= new File(dataFolder + common.Commons.ENCODE_DNASE_DIRECTORY1);
		File dnaseDir2 	= new File(dataFolder + common.Commons.ENCODE_DNASE_DIRECTORY2);		
		File tfbsDir 	= new File(dataFolder + common.Commons.ENCODE_TFBS_DIRECTORY);		
		File histoneDir = new File(dataFolder + common.Commons.ENCODE_HISTONE_DIRECTORY);
		
		List<BufferedWriter> unsortedDnaseBufferedWriterList = new ArrayList<BufferedWriter>(24);
		List<BufferedWriter> unsortedHistoneBufferedWriterList = new ArrayList<BufferedWriter>(24);
		List<BufferedWriter> unsortedTfbsBufferedWriterList = new ArrayList<BufferedWriter>(24);
		
		//Attention
		//Encode data
		// The starting position of the feature in the chromosome. The first base in a chromosome is numbered 0.
		// The ending position of the feature in the chromosome or scaffold. 
		// The chromEnd base is not included in the display of the feature. 
		//For example, the first 100 bases of a chromosome are defined as chromStart=0, chromEnd=100, and span the bases numbered 0-99.
		
		//my convention design
		// start 0-based
		// start and end are inclusive
		// therefore length is equal to end-start+1.

//		DNASE
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedDnaseFileWriters(dataFolder,unsortedDnaseBufferedWriterList);	    
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readEncodeDnaseFilesandWriteUnsortedChromBaseDnaseFiles(dnaseDir2,unsortedDnaseBufferedWriterList);		
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readEncodeDnaseFilesandWriteUnsortedChromBaseDnaseFiles(dnaseDir1,unsortedDnaseBufferedWriterList);
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(unsortedDnaseBufferedWriterList);		
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readUnsortedChromBaseDnaseFilesSortWriteSortedChromosomeBaseDnaseFiles(dataFolder);
		ChromosomeBasedFilesandOperations.writeDnaseInformationtoConsole(numberofDNAElements);
		
		
//		HISTONE
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedHistoneFileWriters(dataFolder, unsortedHistoneBufferedWriterList);
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readEncodeHistoneFilesandWriteUnsortedChromBaseHistoneFiles(histoneDir,unsortedHistoneBufferedWriterList);	
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(unsortedHistoneBufferedWriterList);
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readUnsortedChromBaseHistoneFilesSortWriteSortedChromosomeBaseHistoneFiles(dataFolder);
		ChromosomeBasedFilesandOperations.writeHistoneInformationtoConsole(numberofDNAElements);
				
//		TFBS
		ChromosomeBasedFilesandOperations.openUnsortedChromosomeBasedTfbsFileWriters(dataFolder,unsortedTfbsBufferedWriterList);
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readEncodeTfbsFilesandWriteUnsortedChromBaseTfbsFiles(tfbsDir,unsortedTfbsBufferedWriterList);
		ChromosomeBasedFilesandOperations.closeChromosomeBasedBufferedWriters(unsortedTfbsBufferedWriterList);
		CreateChromosomeBasedDnaseTfbsHistoneFilesUsingEncodeUsingIntervalTreeSorting.readUnsortedChromBaseTfbsFilesSortWriteSortedChromosomeBaseTfbsFiles(dataFolder);
		ChromosomeBasedFilesandOperations.writeTfbsInformationtoConsole(numberofDNAElements);
				            
	}

}
