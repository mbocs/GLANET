package common;

	//args[0]	--->	Input File Name with folder
	//args[1]	--->	GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2]	--->	Given Interval Input File Data Format
	//			--->			Commons.INPUT_FILE_FORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_DBSNP_IDS_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_BED_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//			--->			Commons.INPUT_FILE_FORMAT_GFF3_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE	
	//args[3]	--->	Annotation, overlap definition, number of bases, default 1
	//args[4]	--->	Enrichment parameter
	//			--->	default	Commons.DO_NOT_ENRICH
	//			--->			Commons.DO_ENRICH	
	//args[5]	--->	Generate Random Data Mode
	//			--->	default	Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT
	//			--->			Commons.GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT	
	//args[6]	--->	multiple testing parameter, enriched elements will be decided and sorted with respect to this parameter
	//			--->	default Commons.BENJAMINI_HOCHBERG_FDR
	//			--->			Commons.BONFERRONI_CORRECTION
	//args[7]	--->	Bonferroni Correction Significance Criteria, default 0.05
	//args[8]	--->	Benjamini Hochberg False Discovery Rate, default 0.05
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
	//args[21]  --->    number of permutations in each run. Default is 2000
	//args[22]  --->	User Defined GeneSet Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT
	//							Commons.DO_USER_DEFINED_GENESET_ENRICHMENT
	//args[23]	--->	User Defined GeneSet InputFile 
	//args[24]	--->	User Defined GeneSet GeneInformationType
	//					default Commons.GENE_ID
	//							Commons.GENE_SYMBOL
	//							Commons.RNA_NUCLEOTIDE_ACCESSION
	//args[25]	--->	User Defined GeneSet Name such as "GO"
	//					default Commons.NO_DESCRIPTION
	//args[26]	--->	Optional UserDefinedGeneSet Description InputFile
	//					default Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED
	//args[27]  --->	User Defined Library Enrichment
	//					default Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT
	//						 	Commons.DO_USER_DEFINED_LIBRARY_ENRICHMENT
	//args[28]  --->	User Defined Library InputFile
	//args[29]	--->	User Defined Library DataFormat
	//					default	Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//							Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//							Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE
	//							Commons.USERDEFINEDLIBRARY_DATAFORMAT_1_BASED_COORDINATES_START_INCLUSIVE_END_INCLUSIVE
	//args[30] - args[args.length-1]  --->	Note that the selected cell lines are
	//					always inserted at the end of the args array because it's size
	//					is not fixed. So for not (until the next change on args array) the selected cell
	//					lines can be reached starting from 22th index up until (args.length-1)th index.
	//					If no cell line selected so the args.length-1 will be 22-1 = 21. So it will never
	//					give an out of boundry exception in a for loop with this approach.

public enum CommandLineArguments {
	
	InputFileNameWithFolder(0),
	GlanetFolder(1),
	InputFileDataFormat(2),
	NumberOfBases(3, Commons.NUMBER_BASES_DEFAULT),
	PerformEnrichment(4, Commons.DO_NOT_ENRICH),
	GenerateRandomDataMode(5, Commons.GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT),
	MultipleTesting(6, Commons.BENJAMINI_HOCHBERG_FDR),
	SignificanceCriteria(7, Commons.SIGNIFICANCE_CRITERIA_DEFAULT),
	FalseDiscoveryRate(8, Commons.FDR_DEFAULT),
	NumberOfPermutation(9, Commons.NUMBER_OF_PERMUTATIONS_DEFAULT),
	DnaseAnnotation(10, Commons.DO_NOT_DNASE_ENRICHMENT),
	HistoneAnnotation(11, Commons.DO_NOT_HISTONE_ENRICHMENT),
	TfAnnotation(12, Commons.DO_NOT_TF_ENRICHMENT),
	KeggPathwayAnnotation(13, Commons.DO_NOT_KEGGPATHWAY_ENRICHMENT ),
	TfAndKeggPathwayAnnotation(14, Commons.DO_NOT_TF_KEGGPATHWAY_ENRICHMENT),
	CellLineBasedTfAndKeggPathwayAnnotation(15, Commons.DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT),
	RegulatorySequenceAnalysisUsingRSAT(16, Commons.DO_NOT_REGULATORY_SEQUENCE_ANALYSIS_USING_RSAT),
	JobName(17, Commons.JOB_NAME_DEFAULT),
	WriteGeneratedRandomDataMode(18, Commons.DO_NOT_WRITE_GENERATED_RANDOM_DATA),
	WritePermutationBasedandParametricBasedAnnotationResultMode(19, Commons.DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT),
	WritePermutationBasedAnnotationResultMode(20, Commons.DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT),
	NumberOfPerInEachRun(21, Commons.NUMBER_OF_PERMUTATIONS_IN_EACH_RUN_DEFAULT),
	UserDefinedGeneSetAnnotation(22, Commons.DO_NOT_USER_DEFINED_GENESET_ENRICHMENT),
	UserDefinedGeneSetInput(23, Commons.NO_OPTIONAL_USERDEFINEDGENESET_FILE_PROVIDED),
	UserDefinedGeneSetGeneInformation(24, Commons.GENE_ID),
	UserDefinedGeneSetName(25, Commons.NO_NAME),
	UserDefinedGeneSetDescriptionFile(26, Commons.NO_OPTIONAL_USERDEFINEDGENESET_DESCRIPTION_FILE_PROVIDED),
	UserDefinedLibraryAnnotation(27, Commons.DO_NOT_USER_DEFINED_LIBRARY_ENRICHMENT),
	UserDefinedLibraryInput(28, Commons.NO_OPTIONAL_USERDEFINEDLIBRARY_FILE_PROVIDED),
	UserDefinedLibraryDataFormat(29, Commons.USERDEFINEDLIBRARY_DATAFORMAT_0_BASED_COORDINATES_START_INCLUSIVE_END_EXCLUSIVE),
	NumberOfArguments(30);
	
	private final int value;
	private final String defaultValue;
	
	private CommandLineArguments(int value, String defaultValue ) {
		
    	this.value = value;
    	this.defaultValue = defaultValue;
	}
	
	private CommandLineArguments(int value) {
		
    	this.value = value;
    	defaultValue = null;
	}
    
	public int value(){
		
		return value;
	}
	
	public String defaultValue(){
		
		return defaultValue;
	}
}
