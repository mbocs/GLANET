/**
 * @author Burcak Otlu
 * Sep 20, 2013
 * 11:14:46 PM
 * 2013
 *
 * 
 */
package auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ui.GlanetRunner;

public class FunctionalElement implements Comparable<FunctionalElement> {
	
	Float empiricalPValue;
	Float bonferroniCorrectedEmpiricalPValue;
	

	//15 MAY 2014
	long number;
	String name;
	
	
	//18 FEB 2014 
	int originalNumberofOverlaps;
	int numberofPermutationsHavingOverlapsGreaterThanorEqualto;
	int numberofPermutations;
	int numberofComparisons;
	
	
	//4 MARCH 2014
	Float BH_FDR_adjustedPValue;
	boolean rejectNullHypothesis;
	
	
	//In case of Functional element contains a KEGG Pathway
	int keggPathwayNumber;   	//202	
	String keggPathwayEntry;	//hsa04974
	String keggPathwayName; 	//Protein digestion and absorption - Homo sapiens (human)
	List<Integer> keggPathwayGeneIdList;
	List<String> keggPathwayRefSeqGeneNameList;
	List<String> keggPathwayAlternateGeneNameList;
		
	
		

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getKeggPathwayEntry() {
		return keggPathwayEntry;
	}

	public void setKeggPathwayEntry(String keggPathwayEntry) {
		this.keggPathwayEntry = keggPathwayEntry;
	}

	public int getKeggPathwayNumber() {
		return keggPathwayNumber;
	}

	public void setKeggPathwayNumber(int keggPathwayNumber) {
		this.keggPathwayNumber = keggPathwayNumber;
	}


	public Float getBH_FDR_adjustedPValue() {
		return BH_FDR_adjustedPValue;
	}

	public void setBH_FDR_adjustedPValue(Float bH_FDR_adjustedPValue) {
		BH_FDR_adjustedPValue = bH_FDR_adjustedPValue;
	}
		
	public boolean isRejectNullHypothesis() {
		return rejectNullHypothesis;
	}

	public void setRejectNullHypothesis(boolean rejectNullHypothesis) {
		this.rejectNullHypothesis = rejectNullHypothesis;
	}

	

	public int getNumberofPermutationsHavingOverlapsGreaterThanorEqualto() {
		return numberofPermutationsHavingOverlapsGreaterThanorEqualto;
	}

	public void setNumberofPermutationsHavingOverlapsGreaterThanorEqualto(
			int numberofPermutationsHavingOverlapsGreaterThanorEqualto) {
		this.numberofPermutationsHavingOverlapsGreaterThanorEqualto = numberofPermutationsHavingOverlapsGreaterThanorEqualto;
	}

	
	public Float getEmpiricalPValue() {
		return empiricalPValue;
	}

	public void setEmpiricalPValue(Float empiricalPValue) {
		this.empiricalPValue = empiricalPValue;
	}

	public Float getBonferroniCorrectedEmpiricalPValue() {
		return bonferroniCorrectedEmpiricalPValue;
	}

	public void setBonferroniCorrectedEmpiricalPValue(
			Float bonferroniCorrectedEmpiricalPValue) {
		this.bonferroniCorrectedEmpiricalPValue = bonferroniCorrectedEmpiricalPValue;
	}

	public int getOriginalNumberofOverlaps() {
		return originalNumberofOverlaps;
	}

	public void setOriginalNumberofOverlaps(int originalNumberofOverlaps) {
		this.originalNumberofOverlaps = originalNumberofOverlaps;
	}

	

	public int getNumberofPermutations() {
		return numberofPermutations;
	}

	public void setNumberofPermutations(int numberofPermutations) {
		this.numberofPermutations = numberofPermutations;
	}

	public int getNumberofComparisons() {
		return numberofComparisons;
	}

	public void setNumberofComparisons(int numberofComparisons) {
		this.numberofComparisons = numberofComparisons;
	}

	

	

	public List<Integer> getKeggPathwayGeneIdList() {
		return keggPathwayGeneIdList;
	}

	public void setKeggPathwayGeneIdList(List<Integer> keggPathwayGeneIdList) {
		this.keggPathwayGeneIdList = keggPathwayGeneIdList;
	}

	public List<String> getKeggPathwayRefSeqGeneNameList() {
		return keggPathwayRefSeqGeneNameList;
	}

	public void setKeggPathwayRefSeqGeneNameList(
			List<String> keggPathwayRefSeqGeneNameList) {
		this.keggPathwayRefSeqGeneNameList = keggPathwayRefSeqGeneNameList;
	}

	public List<String> getKeggPathwayAlternateGeneNameList() {
		return keggPathwayAlternateGeneNameList;
	}

	public void setKeggPathwayAlternateGeneNameList(
			List<String> keggPathwayAlternateGeneNameList) {
		this.keggPathwayAlternateGeneNameList = keggPathwayAlternateGeneNameList;
	}

	public String getKeggPathwayName() {
		return keggPathwayName;
	}

	public void setKeggPathwayName(String keggPathwayName) {
		this.keggPathwayName = keggPathwayName;
	}

	
	
	
	
	

	/**
	 * 
	 */
	public FunctionalElement() {
		// TODO Auto-generated constructor stub
	}

	
	public static void traverseList(List<FunctionalElement> list){
		for (FunctionalElement ele: list){
			GlanetRunner.appendLog("empirical p value" +"\t" + ele.getEmpiricalPValue() + "\t" + "bonferroni corrected empirical p value" +"\t" + ele.getBonferroniCorrectedEmpiricalPValue());
			
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//For testing purposes
		List<FunctionalElement> list = new ArrayList<FunctionalElement>();
		
		FunctionalElement element1 = new FunctionalElement();
		FunctionalElement element2 = new FunctionalElement();
		FunctionalElement element3= new FunctionalElement();
		FunctionalElement element4 = new FunctionalElement();
		
		element1.setEmpiricalPValue((float)0.3);
		element1.setBonferroniCorrectedEmpiricalPValue((float) 0.05);
		
		element2.setEmpiricalPValue((float)0.5);
		element2.setBonferroniCorrectedEmpiricalPValue((float) 0.01);
		
		element3.setEmpiricalPValue((float)0.2);
		element3.setBonferroniCorrectedEmpiricalPValue((float) 0.04);
		
		element4.setEmpiricalPValue((float)0.1);
		element4.setBonferroniCorrectedEmpiricalPValue((float) 0.02);
		
		list.add(element1);
		list.add(element2);
		list.add(element3);
		list.add(element4);
		
		GlanetRunner.appendLog("Before any sort");
		traverseList(list);
		
		
		Collections.sort(list,FunctionalElement.EMPIRICAL_P_VALUE);
		GlanetRunner.appendLog("After sort empirical p value");
		traverseList(list);
		
		Collections.sort(list,FunctionalElement.BONFERRONI_CORRECTED_P_VALUE);
		GlanetRunner.appendLog("After sort bonferroni corrected empirical p value");
		traverseList(list);
		
		
		// TODO Auto-generated method stub

	}
	
	public static Comparator<FunctionalElement> EMPIRICAL_P_VALUE = 
    		new Comparator<FunctionalElement>() {
				public int compare(FunctionalElement element1, FunctionalElement element2) {
					
					return element1.getEmpiricalPValue().compareTo(element2.getEmpiricalPValue());
					
//					if (element1.getEmpiricalPValue() < element2.getEmpiricalPValue())
//						return -1;
//					else if (element1.getEmpiricalPValue() > element2.getEmpiricalPValue())
//						return 1;
//					else 
//						return 0;
				}
    };
	
    public static Comparator<FunctionalElement> BONFERRONI_CORRECTED_P_VALUE = 
    		new Comparator<FunctionalElement>() {
				public int compare(FunctionalElement element1, FunctionalElement element2) {
					
					return element1.getBonferroniCorrectedEmpiricalPValue().compareTo(element2.getBonferroniCorrectedEmpiricalPValue());
					
//					if (element1.getBonferroniCorrectedEmpiricalPValue() < element2.getBonferroniCorrectedEmpiricalPValue())
//						return -1;
//					else if (element1.getBonferroniCorrectedEmpiricalPValue() > element2.getBonferroniCorrectedEmpiricalPValue())
//						return 1;
//					else 
//						return 0;
				}
    };
	
	  
    public static Comparator<FunctionalElement> BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE = 
    		new Comparator<FunctionalElement>() {
				public int compare(FunctionalElement element1, FunctionalElement element2) {
					
					return element1.getBH_FDR_adjustedPValue().compareTo(element2.getBH_FDR_adjustedPValue());
					
				}
    };
	
	
	

	@Override
	public int compareTo(FunctionalElement element) {
		// TODO Auto-generated method stub
		if (this.empiricalPValue < element.getEmpiricalPValue())
			return -1;
		else if (this.empiricalPValue == element.getEmpiricalPValue())
			return 0;
		else 
			return 1;
	}
	
	
	
	

}
