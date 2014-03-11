package ncbi;


public class RefSeq2Gene {
	
	Integer geneId;
	String refSeqName;
	String refSeqNamewithDot;
	
	
	
	public String getRefSeqNamewithDot() {
		return refSeqNamewithDot;
	}
	public void setRefSeqNamewithDot(String refSeqNamewithDot) {
		this.refSeqNamewithDot = refSeqNamewithDot;
	}
	public Integer getGeneId() {
		return geneId;
	}
	public void setGeneId(Integer geneId) {
		this.geneId = geneId;
	}
	public String getRefSeqName() {
		return refSeqName;
	}
	public void setRefSeqName(String refSeqName) {
		this.refSeqName = refSeqName;
	}
	
//	overridden equals method
	public boolean equals(Object obj){
		boolean isEqual = false;
		
		if(obj!=null){
			if (this.getClass() == obj.getClass()){
				RefSeq2Gene  refSeq2Gene = (RefSeq2Gene) obj;
				
				if ((refSeq2Gene.getGeneId().equals(this.getGeneId())) && 
				    (refSeq2Gene.getRefSeqName().equals(this.getRefSeqName())) &&
				    (refSeq2Gene.getRefSeqNamewithDot().equals(this.getRefSeqNamewithDot()))){			
					 	isEqual = true;
					 	return isEqual;
				}
			}			
		}
		
		return isEqual;		
	}
	
	public int hashCode(){
		return this.getRefSeqNamewithDot().hashCode();
	}
	

}
