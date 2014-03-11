/**
 * @author Burcak Otlu
 * Jan 17, 2014
 * 4:29:29 PM
 * 2014
 *
 * 
 */
package annotate.intervals.parametric;

import java.util.List;

public class PermutationNumberUcscRefSeqGeneOverlap {
	
	//example
	//refSeqGeneName	NM_002979	
	//intervalName	INTRON15	
	//geneHugoSymbol	SCP2	
	//geneEntrezId	6342

	String permutationNumber;
	String refSeqGeneName;
	String intervalName;
	String geneHugoSymbol;
	int geneEntrezId;
	int low;
	int high;
	List<String> keggPathwayNameList;
	
		
	public PermutationNumberUcscRefSeqGeneOverlap(String permutationNumber,
			String refSeqGeneName, String intervalName, String geneHugoSymbol,
			int geneEntrezId, int low, int high,
			List<String> keggPathwayNameList) {
		super();
		this.permutationNumber = permutationNumber;
		this.refSeqGeneName = refSeqGeneName;
		this.intervalName = intervalName;
		this.geneHugoSymbol = geneHugoSymbol;
		this.geneEntrezId = geneEntrezId;
		this.low = low;
		this.high = high;
		this.keggPathwayNameList = keggPathwayNameList;
	}

	
	public String getRefSeqGeneName() {
		return refSeqGeneName;
	}

	public void setRefSeqGeneName(String refSeqGeneName) {
		this.refSeqGeneName = refSeqGeneName;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	public String getGeneHugoSymbol() {
		return geneHugoSymbol;
	}

	public void setGeneHugoSymbol(String geneHugoSymbol) {
		this.geneHugoSymbol = geneHugoSymbol;
	}

	public int getGeneEntrezId() {
		return geneEntrezId;
	}

	public void setGeneEntrezId(int geneEntrezId) {
		this.geneEntrezId = geneEntrezId;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public List<String> getKeggPathwayNameList() {
		return keggPathwayNameList;
	}

	public void setKeggPathwayNameList(List<String> keggPathwayNameList) {
		this.keggPathwayNameList = keggPathwayNameList;
	}

	public String getPermutationNumber() {
		return permutationNumber;
	}

	public void setPermutationNumber(String permutationNumber) {
		this.permutationNumber = permutationNumber;
	}

	/**
	 * 
	 */
	public PermutationNumberUcscRefSeqGeneOverlap() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}