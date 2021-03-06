package intervaltree;

import enumtypes.ChromosomeName;
import enumtypes.IntervalName;
import enumtypes.NodeType;

public class UcscRefSeqGeneIntervalTreeNodeWithNumbers extends IntervalTreeNode{

	char strand;
	
	int geneEntrezId;
	int refSeqGeneNumber;
	int geneHugoSymbolNumber;

	//Enum Type
	//EXON, INTRON, _5P1, _5P2, _3P1, _3P2
	IntervalName intervalName;
	//In case of interval is EXON or INTRON
	int intervalNumber;
	
	
	
	public char getStrand() {
		return strand;
	}
	public void setStrand(char strand) {
		this.strand = strand;
	}
	public int getRefSeqGeneNumber() {
		return refSeqGeneNumber;
	}
	public void setRefSeqGeneNumber(int refSeqGeneNumber) {
		this.refSeqGeneNumber = refSeqGeneNumber;
	}
	public int getGeneEntrezId() {
		return geneEntrezId;
	}
	public void setGeneEntrezId(int geneEntrezId) {
		this.geneEntrezId = geneEntrezId;
	}
	public int getGeneHugoSymbolNumber() {
		return geneHugoSymbolNumber;
	}
	public void setGeneHugoSymbolNumber(int geneHugoSymbolNumber) {
		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
	}
	public IntervalName getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(IntervalName intervalName) {
		this.intervalName = intervalName;
	}
	public int getIntervalNumber() {
		return intervalNumber;
	}
	public void setIntervalNumber(int intervalNumber) {
		this.intervalNumber = intervalNumber;
	}
	
	//generated from Source 
	public UcscRefSeqGeneIntervalTreeNodeWithNumbers(char strand,
			int refSeqGeneNumber, int geneEntrezId,
			int geneHugoSymbolNumber, IntervalName intervalName,
			int intervalNumber) {
		
		super();
		this.strand = strand;
		this.refSeqGeneNumber = refSeqGeneNumber;
		this.geneEntrezId = geneEntrezId;
		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;
	} 
	
	
	//For Exon Based Kegg Pathway Enrichment Analysis Ucsc gene
	public UcscRefSeqGeneIntervalTreeNodeWithNumbers(ChromosomeName chromName, int low, int high, Integer geneEntrezId, IntervalName intervalName,Integer intervalNumber,NodeType nodeType) {
			super(chromName,low,high,nodeType);
			
			this.geneEntrezId = geneEntrezId;
			this.intervalName = intervalName;
			
	}
		
		
	//For Ucsc gene without strand attribute
	public UcscRefSeqGeneIntervalTreeNodeWithNumbers(ChromosomeName chromName, int low, int high,  Integer geneEntrezId, Integer refSeqGeneNumber, Integer geneHugoSymbolNumber,IntervalName intervalName, Integer intervalNumber, NodeType nodeType) {
		super(chromName,low,high,nodeType);
		
		this.geneEntrezId = geneEntrezId;
		this.refSeqGeneNumber = refSeqGeneNumber;
		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
		
		this.intervalName = intervalName;
		this.intervalNumber = intervalNumber;
		
		
	}
			
			
	//For Ucsc gene with strand attribute
	public UcscRefSeqGeneIntervalTreeNodeWithNumbers(ChromosomeName chromName, int low, int high, Integer refSeqGeneNumber, Integer geneEntrezId, IntervalName intervalName,char strand, Integer geneHugoSymbolNumber,NodeType nodeType) {
		super(chromName,low,high,nodeType);
		
		this.refSeqGeneNumber = refSeqGeneNumber;
		this.geneEntrezId = geneEntrezId;
		this.intervalName = intervalName;
		this.strand = strand;
		this.geneHugoSymbolNumber = geneHugoSymbolNumber;
		
		
		
	}
	

	
}
