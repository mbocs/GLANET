package create.ucscgenome;

import java.util.List;

import enumtypes.ChromosomeName;


public class RefSeqGene {
 
 String refSeqGeneName;
 ChromosomeName chromName;
 char strand;
 int transcriptionStartPosition;
 int transcriptionEndPosition;
 int cdsStart;
 int cdsEnd;
 int exonCounts;
 List<Integer> exonStarts;
 List<Integer> exonEnds;
 String alternateGeneName;
 
 Integer geneId;
 
 
 
public Integer getGeneId() {
	return geneId;
}
public void setGeneId(Integer geneId) {
	this.geneId = geneId;
}
public int getCdsStart() {
	return cdsStart;
}
public void setCdsStart(int cdsStart) {
	this.cdsStart = cdsStart;
}

public int getCdsEnd() {
	return cdsEnd;
}
public void setCdsEnd(int cdsEnd) {
	this.cdsEnd = cdsEnd;
}
public String getRefSeqGeneName() {
	return refSeqGeneName;
}
public void setRefSeqGeneName(String refSeqGeneName) {
	this.refSeqGeneName = refSeqGeneName;
}


public ChromosomeName getChromName() {
	return chromName;
}
public void setChromName(ChromosomeName chromName) {
	this.chromName = chromName;
}

public char getStrand() {
	return strand;
}
public void setStrand(char strand) {
	this.strand = strand;
}
public int getTranscriptionStartPosition() {
	return transcriptionStartPosition;
}
public void setTranscriptionStartPosition(int transcriptionStartPosition) {
	this.transcriptionStartPosition = transcriptionStartPosition;
}
public int getTranscriptionEndPosition() {
	return transcriptionEndPosition;
}
public void setTranscriptionEndPosition(int transcriptionEndPosition) {
	this.transcriptionEndPosition = transcriptionEndPosition;
}
public int getExonCounts() {
	return exonCounts;
}
public void setExonCounts(int exonCounts) {
	this.exonCounts = exonCounts;
}
public List<Integer> getExonStarts() {
	return exonStarts;
}
public void setExonStarts(List<Integer> exonStarts) {
	this.exonStarts = exonStarts;
}
public List<Integer> getExonEnds() {
	return exonEnds;
}
public void setExonEnds(List<Integer> exonEnds) {
	this.exonEnds = exonEnds;
}
public String getAlternateGeneName() {
	return alternateGeneName;
}
public void setAlternateGeneName(String alternateGeneName) {
	this.alternateGeneName = alternateGeneName;
}
 
public boolean equalsExonEndList(List<Integer> exonEndList1, List<Integer> exonEndList2){
	
	int count = exonEndList1.size();
	
	boolean isEqual = true;
	
	for(int i = 0; i<count ;i++){
		if(!(exonEndList1.get(i).equals(exonEndList2.get(i)))){
			isEqual = false;
			break;
		}			
	}
	return isEqual;
}



public boolean equalsExonStartList(List<Integer> exonStartList1, List<Integer> exonStartList2){
	int count = exonStartList1.size();
	boolean isEqual = true;
	
	for(int i = 0; i<count ;i++){
		if(!(exonStartList1.get(i).equals(exonStartList2.get(i)))){
			isEqual = false;
			break;
		}			
	}
	return isEqual;
}

//Override hashCode method
public int hashCode(){
	return this.getRefSeqGeneName().hashCode();
}

//Override equals method
public boolean equals(Object obj){
	
	boolean isEqual = false;
	
	if(obj!=null){
		if (this.getClass() == obj.getClass()){
			RefSeqGene  refSeqGene = (RefSeqGene) obj;
			
			
			if (refSeqGene.getRefSeqGeneName().equals(this.getRefSeqGeneName()) && 
			    refSeqGene.getAlternateGeneName().equals(this.getAlternateGeneName()) &&
			    refSeqGene.getChromName().equals(this.getChromName()) &&			    
			    (refSeqGene.getExonCounts()==this.getExonCounts()) &&
			    (refSeqGene.getStrand()==this.getStrand()) &&
			    (refSeqGene.getTranscriptionStartPosition()== this.getTranscriptionStartPosition())&&
			    (refSeqGene.getTranscriptionEndPosition()== this.getTranscriptionEndPosition()) &&
			    (refSeqGene.getGeneId().equals(this.getGeneId()))){
			
				 	isEqual = equalsExonEndList(refSeqGene.getExonEnds(),this.getExonEnds()) && equalsExonStartList(refSeqGene.getExonStarts(), this.getExonStarts());

				 	return isEqual;
			}
		}		
	}
	
	return isEqual;
}

}
