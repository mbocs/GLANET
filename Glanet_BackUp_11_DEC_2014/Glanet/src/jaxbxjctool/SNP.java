/**
 * @author burcakotlu
 * @date Apr 4, 2014 
 * @time 4:08:27 PM
 */
package jaxbxjctool;

import java.util.List;

/**
 * 
 */
public class SNP{
	
	String SNPKey;
	
	String chrNamewithoutPreceedingChr;
	
	
	int snpOneBasedStartCoordinate;
	int snpZeroBasedStartCoordinate;
	
	int snpOneBasedEndCoordinate;
	int snpZeroBasedEndCoordinate;
	
	int length;

	
	List<String> observedAlleles;
	List<String> alteredSequences;
	
	String referenceSequence;
	String fastaFile;
	
	
	public int getLength() {
		return length;
	}




	public void setLength(int length) {
		this.length = length;
	}




	public int getSnpOneBasedStartCoordinate() {
		return snpOneBasedStartCoordinate;
	}




	public void setSnpOneBasedStartCoordinate(int snpOneBasedStartCoordinate) {
		this.snpOneBasedStartCoordinate = snpOneBasedStartCoordinate;
	}




	public int getSnpZeroBasedStartCoordinate() {
		return snpZeroBasedStartCoordinate;
	}




	public void setSnpZeroBasedStartCoordinate(int snpZeroBasedStartCoordinate) {
		this.snpZeroBasedStartCoordinate = snpZeroBasedStartCoordinate;
	}




	public int getSnpOneBasedEndCoordinate() {
		return snpOneBasedEndCoordinate;
	}




	public void setSnpOneBasedEndCoordinate(int snpOneBasedEndCoordinate) {
		this.snpOneBasedEndCoordinate = snpOneBasedEndCoordinate;
	}




	public int getSnpZeroBasedEndCoordinate() {
		return snpZeroBasedEndCoordinate;
	}




	public void setSnpZeroBasedEndCoordinate(int snpZeroBasedEndCoordinate) {
		this.snpZeroBasedEndCoordinate = snpZeroBasedEndCoordinate;
	}




	public String getFastaFile() {
		return fastaFile;
	}




	public void setFastaFile(String fastaFile) {
		this.fastaFile = fastaFile;
	}




	public String getChrNamewithoutPreceedingChr() {
		return chrNamewithoutPreceedingChr;
	}




	public void setChrNamewithoutPreceedingChr(String chrNamewithoutPreceedingChr) {
		this.chrNamewithoutPreceedingChr = chrNamewithoutPreceedingChr;
	}




	



	public List<String> getObservedAlleles() {
		return observedAlleles;
	}




	public void setObservedAlleles(List<String> observedAlleles) {
		this.observedAlleles = observedAlleles;
	}




	public String getReferenceSequence() {
		return referenceSequence;
	}




	public void setReferenceSequence(String referenceSequence) {
		this.referenceSequence = referenceSequence;
	}




	public List<String> getAlteredSequences() {
		return alteredSequences;
	}




	public void setAlteredSequences(List<String> alteredSequences) {
		this.alteredSequences = alteredSequences;
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
