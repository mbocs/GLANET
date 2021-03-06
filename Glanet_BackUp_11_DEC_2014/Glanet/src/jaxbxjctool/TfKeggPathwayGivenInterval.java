/**
 * @author burcakotlu
 * @date Apr 7, 2014 
 * @time 11:55:40 AM
 */
package jaxbxjctool;

import java.util.List;



/**
 * 
 */
public class TfKeggPathwayGivenInterval {
	
	
	String chromNamewithoutPreceedingChr;
	int startOneBased;
	int endOneBased;
	
	List<String> snpKeyList;
	List<String> tfKeggPathwayBasedTfIntervalKeyList;
	
	//will be generated from the peaks in this given interval
	String extendedPeakSequence;
	
	String givenIntervalName;
	String tfNameKeggPathwayName;
	

	public String getTfNameKeggPathwayName() {
		return tfNameKeggPathwayName;
	}






	public void setTfNameKeggPathwayName(String tfNameKeggPathwayName) {
		this.tfNameKeggPathwayName = tfNameKeggPathwayName;
	}






	public String getGivenIntervalName() {
		return givenIntervalName;
	}






	public void setGivenIntervalName(String givenIntervalName) {
		this.givenIntervalName = givenIntervalName;
	}






	public String getChromNamewithoutPreceedingChr() {
		return chromNamewithoutPreceedingChr;
	}






	public void setChromNamewithoutPreceedingChr(
			String chromNamewithoutPreceedingChr) {
		this.chromNamewithoutPreceedingChr = chromNamewithoutPreceedingChr;
	}




	public int getStartOneBased() {
		return startOneBased;
	}




	public void setStartOneBased(int startOneBased) {
		this.startOneBased = startOneBased;
	}






	public int getEndOneBased() {
		return endOneBased;
	}






	public void setEndOneBased(int endOneBased) {
		this.endOneBased = endOneBased;
	}






	public List<String> getSnpKeyList() {
		return snpKeyList;
	}






	public void setSnpKeyList(List<String> snpKeyList) {
		this.snpKeyList = snpKeyList;
	}






	public List<String> getTfKeggPathwayBasedTfIntervalKeyList() {
		return tfKeggPathwayBasedTfIntervalKeyList;
	}






	public void setTfKeggPathwayBasedTfIntervalKeyList(
			List<String> tfKeggPathwayBasedTfIntervalKeyList) {
		this.tfKeggPathwayBasedTfIntervalKeyList = tfKeggPathwayBasedTfIntervalKeyList;
	}






	public String getExtendedPeakSequence() {
		return extendedPeakSequence;
	}






	public void setExtendedPeakSequence(String extendedPeakSequence) {
		this.extendedPeakSequence = extendedPeakSequence;
	}






	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
