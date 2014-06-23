package empiricalpvalues;

import intervaltree.ChromosomeName;
import common.Commons;

public enum EnrichmentType {
	
	DO_DNASE_ENRICHMENT(1),
	DO_NOT_DNASE_ENRICHMENT(2),
	
	DO_TF_ENRICHMENT(3),
	DO_NOT_TF_ENRICHMENT(4),
	
	DO_HISTONE_ENRICHMENT(5),
	DO_NOT_HISTONE_ENRICHMENT(6),
	
	DO_KEGGPATHWAY_ENRICHMENT(7),
	DO_NOT_KEGGPATHWAY_ENRICHMENT(8),
	
	DO_TF_KEGGPATHWAY_ENRICHMENT(9),
	DO_NOT_TF_KEGGPATHWAY_ENRICHMENT(10),
	
	DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT(11),
	DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT(12);
	
	private final int enrichmentType;
	
	/* 
    * This constructor is private.
    * Legal to declare a non-private constructor, but not legal
    * to use such a constructor outside the enum.
    * Can never use "new" with any enum, even inside the enum 
    * class itself.
    */
    private EnrichmentType(int enrichmentType) {
    	this.enrichmentType = enrichmentType;
	}
	
    
    public static EnrichmentType convertStringtoEnum(String enrichmentType){
    	
    	if (Commons.DO_DNASE_ENRICHMENT.equals(enrichmentType)){
    		return DO_DNASE_ENRICHMENT;
    	}else if  (Commons.DO_NOT_DNASE_ENRICHMENT.equals(enrichmentType)){
    		return DO_NOT_DNASE_ENRICHMENT;
    	}else if  (Commons.DO_TF_ENRICHMENT.equals(enrichmentType)){
    		return DO_TF_ENRICHMENT;
    	}else if  (Commons.DO_NOT_TF_ENRICHMENT.equals(enrichmentType)){
    		return DO_NOT_TF_ENRICHMENT; 		
    	}else if  (Commons.DO_HISTONE_ENRICHMENT.equals(enrichmentType)){
    		return DO_HISTONE_ENRICHMENT;
    	}else if  (Commons.DO_NOT_HISTONE_ENRICHMENT.equals(enrichmentType)){
    		return DO_NOT_HISTONE_ENRICHMENT;
    	}else if  (Commons.DO_KEGGPATHWAY_ENRICHMENT.equals(enrichmentType)){
    		return DO_KEGGPATHWAY_ENRICHMENT;
    	}else if  (Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT.equals(enrichmentType)){
    		return DO_NOT_KEGGPATHWAY_ENRICHMENT;
    	}else if  (Commons.DO_TF_KEGGPATHWAY_ENRICHMENT.equals(enrichmentType)){
    		return DO_TF_KEGGPATHWAY_ENRICHMENT;
    	}else if  (Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT.equals(enrichmentType)){
    		return DO_NOT_TF_KEGGPATHWAY_ENRICHMENT;
    	}else if  (Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT.equals(enrichmentType)){
    		return DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
    	}else if  (Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT.equals(enrichmentType)){
    		return DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
    	}else
    		return null;
    }
    
    public String getEnrichmentType(){
    	if (this.equals(EnrichmentType.DO_DNASE_ENRICHMENT))
    		return Commons.DO_DNASE_ENRICHMENT;
    	else if (this.equals(EnrichmentType.DO_NOT_DNASE_ENRICHMENT))
    		return Commons.DO_NOT_DNASE_ENRICHMENT;
    	
    	else if (this.equals(EnrichmentType.DO_HISTONE_ENRICHMENT))
    		return Commons.DO_HISTONE_ENRICHMENT;
    	else if (this.equals(EnrichmentType.DO_NOT_HISTONE_ENRICHMENT))
    		return Commons.DO_NOT_HISTONE_ENRICHMENT;
    	
    	else if (this.equals(EnrichmentType.DO_TF_ENRICHMENT))
    		return Commons.DO_TF_ENRICHMENT;
    	else if (this.equals(EnrichmentType.DO_NOT_TF_ENRICHMENT))
    		return Commons.DO_NOT_TF_ENRICHMENT;
    	
    	else if (this.equals(EnrichmentType.DO_KEGGPATHWAY_ENRICHMENT))
    		return Commons.DO_KEGGPATHWAY_ENRICHMENT;
    	else if (this.equals(EnrichmentType.DO_NOT_KEGGPATHWAY_ENRICHMENT))
    		return Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT;
    	
    	else if (this.equals(EnrichmentType.DO_TF_KEGGPATHWAY_ENRICHMENT))
    		return Commons.DO_TF_KEGGPATHWAY_ENRICHMENT;
    	else if (this.equals(EnrichmentType.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT))
    		return Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT;
    	
    	else if (this.equals(EnrichmentType.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT))
    		return Commons.DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
    	else if (this.equals(EnrichmentType.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT))
    		return Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
    	
    	
    	else
    		return null;
    				
    		
    }
    
    /** An added method.  */
    public boolean isDnaseEnrichment() {
        return  this == DO_DNASE_ENRICHMENT;
    }
		
    /** An added method.  */
    public boolean isTfEnrichment() {
        return  this == DO_TF_ENRICHMENT;
    }
	
    
    /** An added method.  */
    public boolean isHistoneEnrichment() {
        return  this == DO_HISTONE_ENRICHMENT;
    }
    
    /** An added method.  */
    public boolean isKeggPathwayEnrichment() {
        return  this == DO_KEGGPATHWAY_ENRICHMENT;
    }
    
    /** An added method.  */
    public boolean isTfKeggPathwayEnrichment() {
        return  this == DO_TF_KEGGPATHWAY_ENRICHMENT;
    }
    
    /** An added method.  */
    public boolean isTfCellLineKeggPathwayEnrichment() {
        return  this == DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT;
    }

}