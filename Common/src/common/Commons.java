/*
 * This program takes human_gene2refseq.txt as input
 * and returns human_refseq2gene.txt as output
 *  for testing purposes
 * test burcak
 */

package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Commons {
	
	public static final String OUTPUT_DATA = System.getProperty("user.home") + System.getProperty("file.separator") + "GLANET" + System.getProperty("file.separator") + "Output" + System.getProperty("file.separator");
	public static final String LARGE_OUTPUT_DATA = System.getProperty("user.home") + System.getProperty("file.separator") + "GLANET" + System.getProperty("file.separator") + "LOutput" + System.getProperty("file.separator");
	public static final String INPUT_DATA = System.getProperty("user.home") + System.getProperty("file.separator") + "GLANET" + System.getProperty("file.separator") + "AnnotationData" + System.getProperty("file.separator");

	//FIX RSERVE (SR2C PARTS)
	public static final String RSERVE =  System.getProperty("user.home") + System.getProperty("file.separator") + "GLANET" + System.getProperty("file.separator") + "Rserve" + System.getProperty("file.separator");
	
	public static final Integer ORIGINAL_DATA_PERMUTATION_NUMBER = 0;
	public static final String PERMUTATION0 = "PERMUTATION0";

	public static Integer ZERO = new Integer(0); 
	public static Integer ONE = new Integer(1); 
	
	public static Float FLOAT_ZERO = 0.0f; 
	public static Float FLOAT_TEN_QUADRILLION = 10000000000000000f; 
	
	public static Long LONG_ZERO = new Long(0); 
	public static Long LONG_ONE = new Long(1); 
	
	public static final String GC = "GC";
	public static final String MAPABILITY = "MAPABILITY";
	
	public static final String RANDOM_DATA_GENERATION_LOG_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "generate" + System.getProperty("file.separator") + "randomdata" + System.getProperty("file.separator") + "GenerateRandomDataLog.txt";
	
	//Enrich Outputs
	public static final String ENRICH_OUTPUT_FOLDER = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator");
	
	
	public static final String AUGMENTED_TF_EXON_BASED_KEGG_PATHWAY_RESULTS 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "AugmentedTfExonBasedKeggPathwayResults.txt";
	public static final String AUGMENTED_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "AugmentedTfRegulationBasedKeggPathwayResults.txt";
	public static final String AUGMENTED_TF_ALL_BASED_KEGG_PATHWAY_RESULTS 			= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "AugmentedTfAllBasedKeggPathwayResults.txt";

	public static final String AUGMENTED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "AugmentedTfCellLineExonBasedKeggPathwayResults.txt";
	public static final String AUGMENTED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "AugmentedTfCellLineRegulationBasedKeggPathwayResults.txt";
	public static final String AUGMENTED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "augmentation" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "AugmentedTfCellLineAllBasedKeggPathwayResults.txt";
	
	//RSAT
	 public static final String RSAT_ORGANISM_Homo_sapiens_ensembl_74_GRCh37 = "Homo_sapiens_ensembl_74_GRCh37";
	 public static final String RSAT_BACKGROUND_upstream_noorf = "upstream-noorf";
	 public static final String RSAT_tmp_background_infile = "/home/rsat/rsat/public_html/data/genomes/Homo_sapiens_ensembl_74_GRCh37/oligo-frequencies/1nt_upstream-noorf_Homo_sapiens_ensembl_74_GRCh37-ovlp-1str.freq.gz";
	 
	 public static final String RSAT_OUTPUT_FILENAME= OUTPUT_DATA + "RSAT" + System.getProperty("file.separator") + "rsat" + System.getProperty("file.separator") + "RSAT_results.txt";
	    
	//Rserve
	public static final String RSERVE_OUTPUT_FOLDER = OUTPUT_DATA + "rserve" + System.getProperty("file.separator");
	
	/*************************************************************************************/
	//TF KEGGPATHWAY DIRECTORY BASES
	public static final String TF_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE 		= RSERVE_OUTPUT_FOLDER + "TfKeggPathway" + System.getProperty("file.separator") + "TfExonBasedKeggPathway" + System.getProperty("file.separator");
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE 	= RSERVE_OUTPUT_FOLDER + "TfKeggPathway" + System.getProperty("file.separator") + "TfRegulationBasedKeggPathway" + System.getProperty("file.separator");
	public static final String TF_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE 		= RSERVE_OUTPUT_FOLDER + "TfKeggPathway" + System.getProperty("file.separator") + "TfAllBasedKeggPathway" + System.getProperty("file.separator");
	
	
	//TF CELLLINE KEGGPATHWAY DIRECTORY BASES
	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE 		= RSERVE_OUTPUT_FOLDER + "TfCellLineKeggPathway" + System.getProperty("file.separator") + "TfCellLineExonBasedKeggPathway" + System.getProperty("file.separator");
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE = RSERVE_OUTPUT_FOLDER + "TfCellLineKeggPathway" + System.getProperty("file.separator") + "TfCellLineRegulationBasedKeggPathway" + System.getProperty("file.separator");
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_DIRECTORY_BASE 		= RSERVE_OUTPUT_FOLDER + "TfCellLineKeggPathway" + System.getProperty("file.separator") + "TfCellLineAllBasedKeggPathway" + System.getProperty("file.separator");

	/*************************************************************************************/

	
	
	//Encode-motifs file
	public static final String ENCODE_MOTIFS = INPUT_DATA + "encode_motifs" + System.getProperty("file.separator") + "motifs.txt";
	
	//Jaspar Core File
	public static final String JASPAR_CORE = INPUT_DATA + "jaspar_core" + System.getProperty("file.separator") + "pfm_all.txt";
	
	
	public static final String JASPAR_CORE_MATRICES_FOR_LOGO = RSERVE_OUTPUT_FOLDER + "jaspar_core_logo_matrices.txt";
	
	public static final int NUMBER_OF_BASES_BEFORE_SNP_POSITION= 14;
	public static final int NUMBER_OF_BASES_AFTER_SNP_POSITION= 14;
	public static final int SNP_POSITION= 15;
	
	public static final char SEQUENCE_DIRECTION_D = 'D';
	public static final char SEQUENCE_DIRECTION_R = 'R';
	
	
	
	
	//Case Study OCD_GWAS
	//Comparison of Binomial Test versus Permutation Test
	public static final String DNASE_ADJUSTED_P_VALUE_BINOMIAL_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "binomialdistribution" + System.getProperty("file.separator") + "dnase_adjusted_pvalues.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "dnase_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";
	
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_BINOMIAL_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "binomialdistribution" + System.getProperty("file.separator") + "tfbs_adjusted_pvalues.txt";;
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "tfbs_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";
	
	public static final String HISTONE_ADJUSTED_P_VALUE_BINOMIAL_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "binomialdistribution" + System.getProperty("file.separator") + "histone_adjusted_pvalues.txt";;
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "histone_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";
	
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_BINOMIAL_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "binomialdistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_adjusted_pvalues.txt";;
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "exonBasedKeggPathway_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";
	
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_BINOMIAL_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "binomialdistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_adjusted_pvalues.txt";;
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_OCD_GWAS_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_OCD_withoutGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_BINOMIAL_VERSUS_PERMUTATION_COMPARISON_OCD_GWAS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "regulationBasedKeggPathway_comparison_of_binomial_and_permutation_tests_OCD_GWAS.txt";
	
	
	
	//Case Study Positive Control K562 GATA1
	//Comparison of Permutation Tests
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST 		= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST 	= OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String DNASE_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "dnase_comparison_of_permutation_tests_K562_GATA1.txt";
	
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String TRANSCRIPTION_FACTOR_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "tfbs_comparison_of_permutation_tests_K562_GATA1.txt";
	
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String HISTONE_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "histone_comparison_of_permutation_tests_K562_GATA1.txt";
	
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "exonBasedKeggPathway_comparison_of_permutation_tests_K562_GATA1.txt";
	
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_10000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_10000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_5000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_5000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITH_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUE_1000_WITHOUT_POSITIVE_CONTROL_K562_GATA1_PERMUTATION_TEST = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues_K562_GATA1_withoutGCMap_1Rep_1000Perm.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_PERMUTATION_COMPARISON_POSITIVE_CONTROL_K562_GATA1 = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")  + "comparison" + System.getProperty("file.separator") + "binomialversuspermutation" + System.getProperty("file.separator") + "regulationBasedKeggPathway_comparison_of_permutation_tests_K562_GATA1.txt";
	

	
	//Mapability and GC
	public static final String WRITE_MEAN_VALUE_OF_EACH_FILE = "WRITE_MEAN_VALUE_OF_EACH_FILE";
	public static final String WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE = "WRITE_STANDARD_DEVIATION_VALUE_OF_EACH_FILE";
	
	//ALL FILES
	public static final String ALL_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_gc_files.txt";
	public static final String ALL_DNASE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_dnase_gc_files.txt";
	public static final String ALL_TFBS_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_tfbs_gc_files.txt";
	public static final String ALL_HISTONE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_histone_gc_files.txt";
	
	public static final String ALL_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_mapability_files.txt";
	public static final String ALL_DNASE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_dnase_mapability_files.txt";
	public static final String ALL_TFBS_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_tfbs_mapability_files.txt";
	public static final String ALL_HISTONE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "all_histone_mapability_files.txt";

	
	//Ten Different Mean Files
	public static final String TEN_DIFFERENT_MEAN_DNASE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_dnase_gc_files.txt";
	public static final String TEN_DIFFERENT_MEAN_TFBS_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_tfbs_gc_files.txt";
	public static final String TEN_DIFFERENT_MEAN_HISTONE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_histone_gc_files.txt";
	
	public static final String TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_dnase_mapability_files.txt";
	public static final String TEN_DIFFERENT_MEAN_TFBS_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_tfbs_mapability_files.txt";
	public static final String TEN_DIFFERENT_MEAN_HISTONE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "ten_different_mean_histone_mapability_files.txt";
	
	//TOP TEN MOST VARYING FILES
	public static final String TOP_TEN_MOST_VARYING_DNASE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_dnase_gc_files.txt";
	public static final String TOP_TEN_MOST_VARYING_TFBS_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_tfbs_gc_files.txt";
	public static final String TOP_TEN_MOST_VARYING_HISTONE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_histone_gc_files.txt";
	
	public static final String TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_dnase_mapability_files.txt";
	public static final String TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_tfbs_mapability_files.txt";
	public static final String TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "top_ten_histone_mapability_files.txt";
	
	//Data Files for R for Ten Different Mean Files
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_DNASE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanDnaseGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_TFBS_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanTfbsGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_HISTONE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanHistoneGCFiles.txt";
	
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_DNASE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanDnaseMAPABILITYFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_TFBS_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanTfbsMAPABILITYFiles.txt";
	public static final String DATA_FILE_FOR_R_TEN_DIFFERENT_MEAN_HISTONE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TenDifferentMeanHistoneMAPABILITYFiles.txt";
		
	//Top Ten Most Varying Dnase Tfbs Histone Mapability and GC files for Box Plot in R
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_DNASE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingDnaseGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_TFBS_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingTfbsGCFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_HISTONE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingHistoneGCFiles.txt";
	
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_DNASE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingDnaseMapabilityFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_TFBS_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingTfbsMapabilityFiles.txt";
	public static final String DATA_FILE_FOR_R_TOP_TEN_MOST_VARYING_HISTONE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "TopTenMostVaryingHistoneMapabilityFiles.txt";
	
	//All Dnase Tfbs Histone Mapability and GC files for Box Plot in R
	public static final String DATA_FILE_FOR_R_ALL_DNASE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllDnaseGCFiles.txt";
	public static final String DATA_FILE_FOR_R_ALL_TFBS_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllTfbsGCFiles.txt";
	public static final String DATA_FILE_FOR_R_ALL_HISTONE_GC_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllHistoneGCFiles.txt";

	public static final String DATA_FILE_FOR_R_ALL_DNASE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllDnaseMapabilityFiles.txt";;
	public static final String DATA_FILE_FOR_R_ALL_TFBS_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllTfbsMapabilityFiles.txt";
	public static final String DATA_FILE_FOR_R_ALL_HISTONE_MAPABILITY_FILES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "DataFilesForR" + System.getProperty("file.separator") + "AllHistoneMapabilityFiles.txt";

	public static final String ALL_DNASE_GC_FILES_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Dnase" + System.getProperty("file.separator") + "Gc" + System.getProperty("file.separator");
	public static final String ALL_TFBS_GC_FILES_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Tfbs" + System.getProperty("file.separator") + "Gc" + System.getProperty("file.separator");
	public static final String ALL_HISTONE_GC_FILES_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Histone" + System.getProperty("file.separator") + "Gc" + System.getProperty("file.separator");
	
	public static final String ALL_DNASE_MAPABILITY_FILES_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Dnase" + System.getProperty("file.separator") + "Mapability" + System.getProperty("file.separator");
	public static final String ALL_TFBS_MAPABILITY_FILES_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Tfbs" + System.getProperty("file.separator") + "Mapability" + System.getProperty("file.separator");
	public static final String ALL_HISTONE_MAPABILITY_FILES_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "mapabilityandgc" + System.getProperty("file.separator") + "Augmentation" + System.getProperty("file.separator") + "FunctionalElementFileBased" + System.getProperty("file.separator") + "Histone" + System.getProperty("file.separator") + "Mapability" + System.getProperty("file.separator");
	
	
	//MAPABILITY
	public static final String WG_ENCODE_CRG_MAPABILITY_ALIGN_100_MER_WIG = INPUT_DATA + "MAPABILITY" + System.getProperty("file.separator") + "wgEncodeCrgMapabilityAlign100mer.wig" ;
	public static final String WG_ENCODE_CRG_MAPABILITY_ALIGN_50_MER_WIG = INPUT_DATA + "MAPABILITY" + System.getProperty("file.separator") + "wgEncodeCrgMapabilityAlign50mer.wig" ;
	public static final String MAPABILITY_HG19_CHR1_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr1_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR2_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr2_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR3_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr3_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR4_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr4_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR5_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr5_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR6_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr6_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR7_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr7_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR8_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr8_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR9_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr9_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR10_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr10_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR11_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr11_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR12_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr12_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR13_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr13_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR14_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr14_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR15_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr15_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR16_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr16_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR17_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr17_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR18_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr18_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR19_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr19_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR20_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr20_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR21_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr21_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHR22_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chr22_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHRX_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chrX_hg19_mapability.txt" ;
	public static final String MAPABILITY_HG19_CHRY_FILE = INPUT_DATA +  "MAPABILITY" + System.getProperty("file.separator") + "chrY_hg19_mapability.txt" ;
	
	//GC
	public static final String GC_HG19_CHR1_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr1.fa" ;
	public static final String GC_HG19_CHR2_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr2.fa" ;
	public static final String GC_HG19_CHR3_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr3.fa" ;
	public static final String GC_HG19_CHR4_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr4.fa" ;
	public static final String GC_HG19_CHR5_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr5.fa" ;
	public static final String GC_HG19_CHR6_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr6.fa" ;
	public static final String GC_HG19_CHR7_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr7.fa" ;
	public static final String GC_HG19_CHR8_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr8.fa" ;
	public static final String GC_HG19_CHR9_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr9.fa" ;
	public static final String GC_HG19_CHR10_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr10.fa" ;
	public static final String GC_HG19_CHR11_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr11.fa" ;
	public static final String GC_HG19_CHR12_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr12.fa" ;
	public static final String GC_HG19_CHR13_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr13.fa" ;
	public static final String GC_HG19_CHR14_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr14.fa" ;
	public static final String GC_HG19_CHR15_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr15.fa" ;
	public static final String GC_HG19_CHR16_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr16.fa" ;
	public static final String GC_HG19_CHR17_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr17.fa" ;
	public static final String GC_HG19_CHR18_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr18.fa" ;
	public static final String GC_HG19_CHR19_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr19.fa" ;
	public static final String GC_HG19_CHR20_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr20.fa" ;
	public static final String GC_HG19_CHR21_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr21.fa" ;
	public static final String GC_HG19_CHR22_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chr22.fa" ;
	public static final String GC_HG19_CHRX_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chrX.fa" ;
	public static final String GC_HG19_CHRY_FASTA_FILE = INPUT_DATA +  "GC" + System.getProperty("file.separator") + "chrY.fa" ;
	
	public static final String GC_HG19_CHR1_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr1_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR2_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr2_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR3_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr3_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR4_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr4_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR5_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr5_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR6_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr6_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR7_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr7_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR8_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr8_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR9_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr9_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR10_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr10_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR11_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr11_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR12_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr12_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR13_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr13_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR14_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr14_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR15_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr15_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR16_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr16_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR17_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr17_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR18_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr18_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR19_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr19_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR20_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr20_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR21_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr21_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHR22_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chr22_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHRX_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chrX_GC_onezero_file.txt" ;
	public static final String GC_HG19_CHRY_ONEZERO_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator")+  "mappabilityandgc" + System.getProperty("file.separator") + "GC" + System.getProperty("file.separator") + "chrY_GC_onezero_file.txt" ;
	
	public static final char NUCLEIC_ACID_UPPER_CASE_A = 'A' ;
	public static final char NUCLEIC_ACID_LOWER_CASE_A = 'a' ;
	
	public static final char NUCLEIC_ACID_UPPER_CASE_G = 'G' ;
	public static final char NUCLEIC_ACID_LOWER_CASE_G = 'g' ;
	
	public static final char NUCLEIC_ACID_UPPER_CASE_C = 'C' ;
	public static final char NUCLEIC_ACID_LOWER_CASE_C = 'c' ;
	
	public static final char NUCLEIC_ACID_UPPER_CASE_T = 'T' ;
	public static final char NUCLEIC_ACID_LOWER_CASE_T = 't' ;
	
	public static final char NUCLEIC_ACID_UPPER_CASE_N = 'N' ;
	public static final char NUCLEIC_ACID_LOWER_CASE_N = 'n' ;
	
	
	
	
	//Empirical P Value	
	public static final float GC_THRESHOLD_LOWER_VALUE = (float) 0.01;
	public static final float GC_THRESHOLD_UPPER_VALUE = (float) 0.1;
	
	public static final float MAPABILITY_THRESHOLD_LOWER_VALUE = (float) 0.01;
	public static final float MAPABILITY_THRESHOLD_UPPER_VALUE = (float) 0.1;
	
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_001	= (float) 0.001;
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_002	= (float) 0.002;
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_003 	= (float) 0.003;
	public static final float THRESHOLD_INCREASE_VALUE_0_POINT_004	= (float) 0.004;
	
	public static final int NUMBER_OF_TRIAL_FIRST_LEVEL 	=	1000;
	public static final int NUMBER_OF_TRIAL_SECOND_LEVEL	=	2000;
	public static final int NUMBER_OF_TRIAL_THIRD_LEVEL	=	3000;
	public static final int NUMBER_OF_TRIAL_FOURTH_LEVEL	=	4000;
	
	public static final String ORIGINAL_INPUT_DATA_FILE = "ORIGINAL_INPUT_DATA_FILE";
	
	public static final String WRITE_GENERATED_RANDOM_DATA = "WRITE_GENERATED_RANDOM_DATA";
	public static final String DO_NOT_WRITE_GENERATED_RANDOM_DATA = "DO_NOT_WRITE_GENERATED_RANDOM_DATA";
	
	public static final String WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT = "WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT";
	public static final String DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT = "DO_NOT_WRITE_PERMUTATION_BASED_AND_PARAMETRIC_BASED_ANNOTATION_RESULT";
	
	public static final String WRITE_PERMUTATION_BASED_ANNOTATION_RESULT = "WRITE_PERMUTATION_BASED_ANNOTATION_RESULT";
	public static final String DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT = "DO_NOT_WRITE_PERMUTATION_BASED_ANNOTATION_RESULT";

	public static final String GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT = "GENERATE_RANDOM_DATA_WITHOUT_MAPPABILITY_AND_GC_CONTENT";
	public static final String GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT = "GENERATE_RANDOM_DATA_WITH_MAPPABILITY_AND_GC_CONTENT";
	public static final String PERMUTATION = "PERMUTATION";
	public static final String RANDOMLY_GENERATED_DATA_FOLDER = "RandomlyGeneratedData" + System.getProperty("file.separator");
	public static final String RANDOMLY_GENERATED_DATA = "RANDOMLY_GENERATED_DATA";
	
	
	//Positive Control
	public static final String POSITIVE_CONTROL_INPUT_FILE_NAME = INPUT_DATA +  "ENCODE" + System.getProperty("file.separator") + "transcription_factors" + System.getProperty("file.separator") + "spp.optimal.wgEncodeSydhTfbsK562bGata1UcdAlnRep0_VS_wgEncodeSydhTfbsK562bInputUcdAlnRep1.narrowPeak";
	public static final String POSITIVE_CONTROL_OUTPUT_FILE_NAME = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "prepare" + System.getProperty("file.separator") + "positive_control_K562_Gata1.txt";
	public static final String POSITIVE_CONTROL_OUTPUT_FILE_NAME_WITHOUT_OVERLAPS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "process" + System.getProperty("file.separator") + "positive_control_K562_Gata1_without_overlaps.txt";
	
	//Input Data Prepare
	public static final String CHROMOSOME_POSITION_TYPE_ZERO_BASED = "CHROMOSOME_POSITION_TYPE_ZERO_BASED"; 
	public static final String CHROMOSOME_POSITION_TYPE_ONE_BASED = "CHROMOSOME_POSITION_TYPE_ONE_BASED"; 
	
	public static final String NOT_AVAILABLE_SNP_ID = "#N/A";
	
	//OCD_GWAS_SIGNIFICANT_SNPS
	public static final String OCD_GWAS_SIGNIFICANT_SNPS_CHRNUMBER_BASEPAIRNUMBER = INPUT_DATA + "OCD_GWAS_SNP" + System.getProperty("file.separator") + "ocd_gwas_snp_chrNumber_basePairNumber.txt";
	public static final String OCD_GWAS_SIGNIFICANT_SNPS_PREPARED_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "prepare" + System.getProperty("file.separator") + "ocd_gwas_snp_chrNumber_basePairNumber_prepared_file.txt";
	public static final String OCD_GWAS_SIGNIFICANT_SNPS_WITHOUT_OVERLAPS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "process" + System.getProperty("file.separator") + "ocd_gwas_snp_chrNumber_basePairNumber_without_overlaps.txt";
	
	//HIV1 SNPS
	public static final String HIV1_SNPS_START_INCLUSIVE_END_EXCLUSIVE = INPUT_DATA + "HIV1_SNP" + System.getProperty("file.separator") + "hglft_www_5c79_8ab500.bed" ;
	public static final String HIV1_SNPS_START_INCLUSIVE_END_INCLUSIVE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "prepare" + System.getProperty("file.separator") + "HIV1_SNPs_hg19_start_inclusive_end_inclusive.txt" ;
	public static final String HIV1_SNPS_START_INCLUSIVE_END_INCLUSIVE_WITHOUT_OVERLAPS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "process" + System.getProperty("file.separator") + "HIV1_SNPs_hg19_start_inclusive_end_inclusive_without_overlaps.txt" ;
	
	public static final String RANDOMLY_GENERATED_DATA_FILE = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "RandomlyGeneratedData" + System.getProperty("file.separator") + "PERMUTATION4_RANDOMLY_GENERATED_DATA.txt";
	
	//Is an interval tree node an original node or a merged node?
	public static final String ORIGINAL_NODE = "ORIGINAL_NODE";
	public static final String MERGED_NODE = "MERGED_NODE";
	
	//For Whole Genome Sliding Window
	public static final String ORIGINAL_READ_LINE = "ORIGINAL_READ_LINE";
	public static final String DEGENERATED_LINE = "DEGENERATED_LINE";
	
	
	
	public static final String SENTINEL = "SENTINEL";
	public static final String NOT_SENTINEL = "NOT_SENTINEL";
	
	public static final char RED = 'r';
	public static final char BLACK = 'b';
	
	public static final String INSERT = "INSERT";
	public static final String DELETE = "DELETE";
	public static final String chr = "chr";
	
	public static final String PROCESS_INPUT_DATA_REMOVE_OVERLAPS = "PROCESS_INPUT_DATA_REMOVE_OVERLAPS";
	
		
	public static final String FTP_ENCODE_DNASE_DIRECTORY1 	= INPUT_DATA + "ENCODE" + System.getProperty("file.separator") + "dnase";
	public static final String FTP_ENCODE_DNASE_DIRECTORY2 	= INPUT_DATA + "ENCODE" + System.getProperty("file.separator") + "dnase_jul2010";
	public static final String FTP_ENCODE_TFBS_DIRECTORY 	= INPUT_DATA + "ENCODE" + System.getProperty("file.separator") + "transcription_factors";
	public static final String FTP_ENCODE_HISTONE_DIRECTORY = INPUT_DATA + "ENCODE" + System.getProperty("file.separator") + "histone_macs";
	
		
	public static final String HYPHEN = "HYPHEN";
	public static final String TEST_LINEAR_SEARCH_VERSUS_INTERVAL_TREE_SEARCH =OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "testlinearsearchversusintervaltreesearch" + System.getProperty("file.separator") + "Compare.txt";
	
	public static int NUMBER_OF_CHROMOSOMES_HG19 = 24;
	
//	Calculations
	public static final String HG19_CHROMOSOME_SIZES_INPUT_FILE = INPUT_DATA + "FTP" + System.getProperty("file.separator") + "HG19_CHROM_SIZES" + System.getProperty("file.separator") + "hg19.chrom.sizes.txt";
	
	
	//ANNOTATE
	public static final String ANNOTATE_INTERVALS_DNASE_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_dnase_results.txt";
	public static final String ANNOTATE_INTERVALS_TF_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_results.txt";
	public static final String ANNOTATE_INTERVALS_HISTONE_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_histone_results.txt";
	
	public static final String ANNOTATE_INTERVALS_EXON_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_exon_based_kegg_pathway_results.txt";
	public static final String ANNOTATE_INTERVALS_REGULATION_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_regulation_based_kegg_pathway_results.txt";
	public static final String ANNOTATE_INTERVALS_ALL_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_all_based_kegg_pathway_results.txt";
	
	public static final String ANNOTATE_INTERVALS_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_cellLine_exon_based_kegg_pathway_results.txt";
	public static final String ANNOTATE_INTERVALS_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_cellLine_regulation_based_kegg_pathway_results.txt";
	public static final String ANNOTATE_INTERVALS_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_cellLine_all_based_kegg_pathway_results.txt";
	
	public static final String ANNOTATE_INTERVALS_TF_EXON_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_exon_based_kegg_pathway_results.txt";
	public static final String ANNOTATE_INTERVALS_TF_REGULATION_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_regulation_based_kegg_pathway_results.txt";
	public static final String ANNOTATE_INTERVALS_TF_ALL_BASED_KEGG_PATHWAY_RESULTS_GIVEN_SEARCH_INPUT =  OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "results" + System.getProperty("file.separator") + "number_of_k_out_of_n_search_input_lines_tf_all_based_kegg_pathway_results.txt";
			
	//whole genome using interval tree
	public static final String DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "dnaseCellLine_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String DUMMY_DNASE_CELL_LINE_WHOLE_GENOME_USING_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "dummy_dnase_cell_line_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String TFBS_WHOLE_GENOME_USING_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "tfbs_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String HISTONE_WHOLE_GENOME_USING_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "histone_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "exon_based_kegg_pathway_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_intervaltree" + System.getProperty("file.separator") + "regulation_based_kegg_pathway_whole_genome_using_interval_tree_number_of_non_overlapping_base_pairs_.txt";

	//whole genome using sliding window
	public static final String DNASE_CELL_LINE_WHOLE_GENOME_USING_SLIDING_WINDOW = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_slidingwindow" + System.getProperty("file.separator") + "dnaseCellLine_whole_genome_using_sliding_window_number_of_non_overlapping_base_pairs.txt";
	public static final String TFBS_WHOLE_GENOME_USING_SLIDING_WINDOW = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_slidingwindow" + System.getProperty("file.separator") + "tfbs_whole_genome_sliding_window_number_of_non_overlapping_base_pairs_.txt";		
	public static final String HISTONE_WHOLE_GENOME_USING_SLIDING_WINDOW = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_slidingwindow" + System.getProperty("file.separator") + "histone_whole_genome_sliding_window_number_of_non_overlapping_base_pairs.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_SLIDING_WINDOW = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_slidingwindow" + System.getProperty("file.separator") + "exon_based_kegg_pathway_whole_genome_sliding_window_number_of_non_overlapping_base_pairs.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_WHOLE_GENOME_USING_SLIDING_WINDOW = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "wholegenome" + System.getProperty("file.separator") + "nonoverlappingbasepairs" + System.getProperty("file.separator") + "wholegenome_slidingwindow" + System.getProperty("file.separator") + "regulation_based_kegg_pathway_whole_genome_sliding_window_number_of_non_overlapping_base_pairs.txt";
	
	//empirical P values
	public static final String DNASE_CELL_LINE_NAME_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_EmpiricalPValues";
	public static final String TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_EmpiricalPValues";
	public static final String HISTONE_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_EmpiricalPValues";
	public static final String EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_EmpiricalPValues";
	public static final String REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_EmpiricalPValues";
	public static final String ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "allBasedKeggPathway_EmpiricalPValues";
	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineExonBasedKeggPathway_EmpiricalPValues";
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineRegulationBasedKeggPathway_EmpiricalPValues";
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineAllBasedKeggPathway_EmpiricalPValues";
	public static final String TF_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfExonBasedKeggPathway_EmpiricalPValues";
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfRegulationBasedKeggPathway_EmpiricalPValues";
	public static final String TF_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfAllBasedKeggPathway_EmpiricalPValues";

	//empirical P values using Bonferroni Correction
	public static final String DNASE_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BonfCorr_EmpiricalPValues";
	public static final String TFBS_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BonfCorr_EmpiricalPValues";
	public static final String HISTONE_NAME_CELL_LINE_NAME_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BonfCorr_EmpiricalPValues";
	public static final String EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "allBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineExonBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineRegulationBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineAllBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String TF_EXON_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfExonBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfRegulationBasedKeggPathway_BonfCorr_EmpiricalPValues";
	public static final String TF_ALL_BASED_KEGG_PATHWAY_EMPIRICAL_P_VALUES_USING_BONFERRONI_CORRECTION = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfAllBasedKeggPathway_BonfCorr_EmpiricalPValues";
	
	
	//Significant for FDR
	public static final String DNASE_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "dnase_BH_FDR";
	public static final String TFBS_NAME_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfbs_BH_FDR";
	public static final String HISTONE_NAME_CELL_LINE_NAME_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "histone_BH_FDR";
	public static final String EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "exonBasedKeggPathway_BH_FDR";
	public static final String REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "regulationBasedKeggPathway_BH_FDR";
	public static final String ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "allBasedKeggPathway_BH_FDR";
	public static final String TF_EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfExonBasedKeggPathway_BH_FDR";
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfRegulationBasedKeggPathway_BH_FDR";
	public static final String TF_ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfAllBasedKeggPathway_BH_FDR";
	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineExonBasedKeggPathway_BH_FDR";
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineRegulationBasedKeggPathway_BH_FDR";
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_BENJAMINI_HOCHBERG_FDR = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "empiricalpvalues" + System.getProperty("file.separator") + "tfCellLineAllBasedKeggPathway_BH_FDR";
	
	
	//Empirical P Value type
	public static final String BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE = "BONFERRONI_CORRECTED_EMPIRICAL_P_VALUE";
	public static final String EMPIRICAL_P_VALUE = "EMPIRICAL_P_VALUE";
	public static final String BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE = "BENJAMINI_HOCHBERG_FDR_ADJUSTED_P_VALUE";
	

	//empirical P values 
	//directories for annotation of permutations
	public static final String ANNOTATE_PERMUTATIONS_FOR_DNASE = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_TFBS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_HISTONE = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator");
	
	public static final String ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "keggPathway" + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "keggPathway" + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "keggPathway" + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator");
	
	public static final String ANNOTATE_PERMUTATIONS_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfCellLineKeggPathway" + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfCellLineKeggPathway" + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfCellLineKeggPathway" + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator");
	
	public static final String ANNOTATE_PERMUTATIONS_TF_EXON_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfKeggPathway" + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfKeggPathway" + System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_PERMUTATIONS_TF_ALL_BASED_KEGG_PATHWAY_ANALYSIS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfKeggPathway" + System.getProperty("file.separator") + "tfAllBased" + System.getProperty("file.separator");

	public static final String E_DOKTORA_ECLIPSE_WORKSPACE_ANNOTATE_PERMUTATIONS = LARGE_OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator");
	public static final String C_DOKTORA_ECLIPSE_WORKSPACE_ANNOTATE_INTERVALS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator");
	
	//Results to be polled 
	
	public static final String TO_BE_POLLED_DIRECTORY 	= "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator");
	
	public static final String TO_BE_POLLED_DNASE_NUMBER_OF_OVERLAPS 	= "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "Dnase" + System.getProperty("file.separator") + "Dnase";
	public static final String TO_BE_POLLED_HISTONE_NUMBER_OF_OVERLAPS 	= "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "Histone" + System.getProperty("file.separator") + "Histone";
	public static final String TO_BE_POLLED_TF_NUMBER_OF_OVERLAPS		= "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "Tf" + System.getProperty("file.separator") + "Tf";
	
	public static final String TO_BE_POLLED_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "KeggPathway" + System.getProperty("file.separator") + "ExonBased";
	public static final String TO_BE_POLLED_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "KeggPathway" + System.getProperty("file.separator") + "RegulationBased";
	public static final String TO_BE_POLLED_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "KeggPathway" + System.getProperty("file.separator") + "AllBased";
	
	public static final String TO_BE_POLLED_TF_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "TfKeggPathway" + System.getProperty("file.separator") + "TfExonBasedKeggPathway";
	public static final String TO_BE_POLLED_TF_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "TfKeggPathway" + System.getProperty("file.separator") + "TfRegulationBasedKeggPathway";
	public static final String TO_BE_POLLED_TF_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "TfKeggPathway" + System.getProperty("file.separator") + "TfAllBasedKeggPathway";
	
	public static final String TO_BE_POLLED_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "TfCellLineKeggPathway" + System.getProperty("file.separator") + "TfCellLineExonBasedKeggPathway";
	public static final String TO_BE_POLLED_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "TfCellLineKeggPathway" + System.getProperty("file.separator") + "TfCellLineRegulationBasedKeggPathway";;
	public static final String TO_BE_POLLED_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY_NUMBER_OF_OVERLAPS = "empiricalpvalues" + System.getProperty("file.separator") + "toBePolled" + System.getProperty("file.separator") + "TfCellLineKeggPathway" + System.getProperty("file.separator") + "TfCellLineAllBasedKeggPathway";
	
	
	//binomial distribution
	public static final String DNASE_CELLLINE_NAMES_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "dnase_pvalues.txt";
	public static final String DNASE_CELLLINE_NAMES_ADJUSTED_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "dnase_adjusted_pvalues.txt";
	public static final String DNASE_CELLLINE_NAMES_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "dnase_all_values.txt";
	public static final String DNASE_CELLLINE_NAMES_ADJUSTED_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "dnase_adjusted_all_values.txt";
	
	public static final String TFBS_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "tfbs_pvalues.txt";
	public static final String TFBS_ADJUSTED_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "tfbs_adjusted_pvalues.txt";
	public static final String TFBS_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "tfbs_all_values.txt";
	public static final String TFBS_ADJUSTED_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "tfbs_adjusted_all_values.txt";
	
	public static final String HISTONE_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "histone_pvalues.txt";
	public static final String HISTONE_ADJUSTED_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "histone_adjusted_pvalues.txt";
	public static final String HISTONE_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "histone_all_values.txt";
	public static final String HISTONE_ADJUSTED_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "histone_adjusted_all_values.txt";
	
	public static final String EXON_BASED_KEGG_PATHWAY_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_pvalues.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_adjusted_pvalues.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_all_values.txt";
	public static final String EXON_BASED_KEGG_PATHWAY_ADJUSTED_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "exonBased_KeggPathway_adjusted_all_values.txt";
	
	public static final String REGULATION_BASED_KEGG_PATHWAY_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_pvalues.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_P_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_adjusted_pvalues.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_all_values.txt";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ADJUSTED_ALL_VALUES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "binomialdistribution" + System.getProperty("file.separator") + "regulationBased_KeggPathway_adjusted_all_values.txt";
	
	public static final String CALCULATE_USING_BINOMIAL_DISTRIBUTION = "CALCULATE_USING_BINOMIAL_DISTRIBUTION";
	public static final String CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION = "CALCULATE_USING_BURCAK_BINOMIAL_DISTRIBUTION";
	
	public static final String EXON = "EXON";
	public static final String INTRON = "INTRON";
	public static final String FIVE_P_ONE = "5P1";
	public static final String FIVE_P_TWO = "5P2";
	public static final String THREE_P_ONE = "3P1";
	public static final String THREE_P_TWO = "3P2";
	
	public static final String EXON_BASED_KEGG_PATHWAY_ANALYSIS 		= "EXON_BASED_KEGG_PATHWAY_ANALYSIS";
	public static final String REGULATION_BASED_KEGG_PATHWAY_ANALYSIS 	= "REGULATION_BASED_KEGG_PATHWAY_ANALYSIS";
	public static final String ALL_BASED_KEGG_PATHWAY_ANALYSIS 	= "ALL_BASED_KEGG_PATHWAY_ANALYSIS";
	
	//Annotation Type
	public static final String DNASE_ANNOTATION 		= "DNASE_ANNOTATION";
	public static final String TFBS_ANNOTATION 			= "TFBS_ANNOTATION";
	public static final String HISTONE_ANNOTATION 		= "HISTONE_ANNOTATION";
	public static final String UCSC_REFSEQ_GENE_ANNOTATION = "UCSC_REFSEQ_GENE_ANNOTATION";
	public static final String TF_CELLLINE_KEGG_PATHWAY_ANNOTATION = "TF_CELLLINE_KEGG_PATHWAY_ANNOTATION";
	public static final String TF_KEGG_PATHWAY_ANNOTATION = "TF_KEGG_PATHWAY_ANNOTATION";

	//Enrichment Type
	public static final String DO_DNASE_ENRICHMENT 		= "DO_DNASE_ENRICHMENT";
	public static final String DO_NOT_DNASE_ENRICHMENT 	= "DO_NOT_DNASE_ENRICHMENT";
	
	public static final String DO_HISTONE_ENRICHMENT 		= "DO_HISTONE_ENRICHMENT";
	public static final String DO_NOT_HISTONE_ENRICHMENT 	= "DO_NOT_HISTONE_ENRICHMENT";

	//6 March 2014
	public static final String DO_TF_ENRICHMENT 		= "DO_TF_ENRICHMENT";
	public static final String DO_NOT_TF_ENRICHMENT 	= "DO_NOT_TF_ENRICHMENT";
	
	//6 March 2014
	public static final String DO_KEGGPATHWAY_ENRICHMENT 		= "DO_KEGGPATHWAY_ENRICHMENT";
	public static final String DO_NOT_KEGGPATHWAY_ENRICHMENT 	= "DO_NOT_KEGGPATHWAY_ENRICHMENT";
	
	//You can choose TF_CELLLINE_KEGGPATHWAY or TF_KEGGPATHWAY, not both
	//TF and KEGGPATHWAY Enrichment is done by default in case of any choice.
	public static final String DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 		= "DO_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT";
	public static final String DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT 	= "DO_NOT_TF_CELLLINE_KEGGPATHWAY_ENRICHMENT";
	
	public static final String DO_TF_KEGGPATHWAY_ENRICHMENT 			= "DO_TF_KEGGPATHWAY_ENRICHMENT";
	public static final String DO_NOT_TF_KEGGPATHWAY_ENRICHMENT 		= "DO_NOT_TF_KEGGPATHWAY_ENRICHMENT";

	
//	Write all possible names
	public static final String DNASE = "DNASE";
	public static final String TFBS = "TFBS";
	public static final String HISTONE = "HISTONE";
	public static final String UCSC_GENE = "UCSC_GENE";
	public static final String NCBI_GENE_ID = "NCBI_GENE_ID";
	public static final String NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION = "NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION";
	public static final String UCSC_GENE_ALTERNATE_NAME = "UCSC_GENE_ALTERNATE_NAME";
	public static final String KEGG_PATHWAY = "KEGG_PATHWAY";
	
	public static final String EXON_BASED_KEGG_PATHWAY = "EXON_BASED_KEGG_PATHWAY";
	public static final String REGULATION_BASED_KEGG_PATHWAY = "REGULATION_BASED_KEGG_PATHWAY";
	public static final String ALL_BASED_KEGG_PATHWAY = "ALL_BASED_KEGG_PATHWAY";
	
	public static final String CELLLINE = "CELLLINE";
	
	public static final String TF_CELLLINE_EXON_BASED_KEGG_PATHWAY = "TF_CELLLINE_EXON_BASED_KEGG_PATHWAY";
	public static final String TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY = "TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY";
	public static final String TF_CELLLINE_ALL_BASED_KEGG_PATHWAY = "TF_CELLLINE_ALL_BASED_KEGG_PATHWAY";
	
	public static final String TF_EXON_BASED_KEGG_PATHWAY = "TF_EXON_BASED_KEGG_PATHWAY";
	public static final String TF_REGULATION_BASED_KEGG_PATHWAY = "TF_REGULATION_BASED_KEGG_PATHWAY";
	public static final String TF_ALL_BASED_KEGG_PATHWAY = "TF_ALL_BASED_KEGG_PATHWAY";
	
	public static final String FIND_ALL = "FIND_ALL";
	public static final String FIND_FIRST = "FIND_FIRST";
	
	
// 	All possible names		
	public static final String WRITE_ALL_POSSIBLE_DNASE_CELL_NAMES_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_dnase_cell_lines_names.txt";
	public static final String WRITE_ALL_POSSIBLE_TFBS_NAMES_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_tfbs_names.txt";
	public static final String WRITE_ALL_POSSIBLE_HISTONE_NAMES_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_histone_names.txt";
	public static final String WRITE_ALL_POSSIBLE_GENE_IDS_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_gene_ids.txt";	
	public static final String WRITE_ALL_POSSIBLE_RNA_NUCLEUOTIDE_ACCESSION_VERSIONS_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_rna_nucleotide_accession_versions.txt";	
	public static final String WRITE_ALL_POSSIBLE_ALTERNATE_GENE_NAMES_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_alternate_gene_names.txt";	
	public static final String WRITE_ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "all_possible_names" + System.getProperty("file.separator") + "all_possible_kegg_pathway_names.txt";
	
//	Kegg Pathway to Ncbi Ref Seq Gene Ids
	public static final String KEGG_PATHWAY_ENTRY_2_NAME_INPUT_FILE = INPUT_DATA + "KEGG" + System.getProperty("file.separator") + "list_pathway_hsa.txt";
	public static final String KEGG_PATHWAY_2_NCBI_GENE_IDS_INPUT_FILE = INPUT_DATA + "KEGG" + System.getProperty("file.separator") + "pathway_hsa.list";
	public static final String ALL_POSSIBLE_KEGG_PATHWAY_NAMES_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "keggpathway" + System.getProperty("file.separator") + "ncbigenes" + System.getProperty("file.separator") + "all_possible_kegg_pathway_names.txt";
	public static final String KEGG_PATHWAY_2_NCBI_GENE_IDS_OUTPUT_FILE_PATH = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "keggpathway" + System.getProperty("file.separator") + "ncbigenes" + System.getProperty("file.separator");
	
	
//	Annotate intervals using interval tree
	public static final String TCGA_INPUT_DATA_WITH_NON_BLANKS_SNP_IDS_WITH_OVERLAPS = INPUT_DATA + "FTP" + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputTCGADataWithNonBlankSNPRows.txt";
	public static final String TCGA_INPUT_DATA_WITH_NON_BLANKS_SNP_IDS_WITHOUT_OVERLAPS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "inputdata" + System.getProperty("file.separator") + "process" + System.getProperty("file.separator") + "TCGAInputDataWithNonBlankSNPsWithoutOverlaps.txt";
	
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_GENE_ID = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "ncbiGeneId" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "ncbiRNA" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_UCSC_GENE_ALTERNATE_NAME = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "ucscGeneAlternateName" + System.getProperty("file.separator");
	
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "keggPathway" + System.getProperty("file.separator") + "exonBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "keggPathway" + System.getProperty("file.separator") + "regulationBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "keggPathway" + System.getProperty("file.separator") + "allBased" + System.getProperty("file.separator");
	
	
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_EXON_BASED_KEGG_PATHWAY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfCellLineKeggPathway" + System.getProperty("file.separator") + "tfCellLineExonBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_REGULATION_BASED_KEGG_PATHWAY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfCellLineKeggPathway" + System.getProperty("file.separator") + "tfCellLineRegulationBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_CELLLINE_ALL_BASED_KEGG_PATHWAY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfCellLineKeggPathway" + System.getProperty("file.separator") + "tfCellLineAllBased" + System.getProperty("file.separator");
	
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_EXON_BASED_KEGG_PATHWAY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfKeggPathway" + System.getProperty("file.separator") + "tfExonBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_REGULATION_BASED_KEGG_PATHWAY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfKeggPathway" + System.getProperty("file.separator") + "tfRegulationBased" + System.getProperty("file.separator");
	public static final String ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TF_ALL_BASED_KEGG_PATHWAY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator") + "tfKeggPathway" + System.getProperty("file.separator") + "tfAllBased" + System.getProperty("file.separator");
		
//	Search using Linear search
	public static final String SEARCH_USING_LINEAR_SEARCH_INPUT_FILE =INPUT_DATA + "FTP" + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputforTCGATestData_three_columns.txt";
	public static final String SEARCH_USING_LINEAR_SEARCH_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "search" + System.getProperty("file.separator") + "encodeucscgenome" + System.getProperty("file.separator") + "SearchOutput_Using_LinearSearch.txt";	
	
// 	Searching using IntervalTree 	
//	public static final String SEARCH_USING_INTERVAL_TREE_INPUT_FILE = INPUT_DATA + "FTP" + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputTCGADataWithNonBlankSNPRows.txt";
	public static final String SEARCH_USING_INTERVAL_TREE_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "search" + System.getProperty("file.separator") + "encodeucscgenome" + System.getProperty("file.separator") + "SearchOutput_Using_IntervalTreeSearch.txt";
	
	
	public static final String ANNOTATE_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "intervals" + System.getProperty("file.separator") + "parametric" + System.getProperty("file.separator");
	
	public static final String SEARCH_CHROMOSOME_BASED_INPUT_FILE_DIRECTORY = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "search" + System.getProperty("file.separator") + "encodeucscgenome" + System.getProperty("file.separator");
	
	
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR1_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr1_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR2_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr2_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR3_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr3_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR4_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr4_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR5_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr5_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR6_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr6_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR7_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr7_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR8_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr8_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR9_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr9_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR10_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr10_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR11_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr11_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR12_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr12_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR13_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr13_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR14_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr14_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR15_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr15_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR16_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr16_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR17_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr17_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR18_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr18_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR19_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr19_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR20_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr20_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR21_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr21_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR22_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chr22_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRX_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chrX_ucsc_refseq_genes_interval_tree.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRY_UCSC_REFSEQ_GENES_INTERVAL_TREE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_with_Interval_Tree_Infix_Traversal" + System.getProperty("file.separator") + "sorted_chrY_ucsc_refseq_genes_interval_tree.txt";
			
	//Downloaded from ncbi, gene2refseq.txt data
	public static final String NCBI_GENE_TO_REF_SEQ = INPUT_DATA + "FTP" + System.getProperty("file.separator") + "GENE_2_REFSEQ" + System.getProperty("file.separator") + "gene2refseq" + System.getProperty("file.separator") + "gene2refseq.txt";	
	public static final String NCBI_HUMAN_GENE_TO_REF_SEQ = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "ncbi" + System.getProperty("file.separator") + "human_gene2refseq.txt";
	public static final String NCBI_HUMAN_REF_SEQ_TO_GENE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "ncbi" + System.getProperty("file.separator") + "human_refseq2gene.txt";
	public static final String NCBI_HUMAN_REF_SEQ_TO_GENE_2 = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "ncbi" + System.getProperty("file.separator") + "human_refseq2gene2.txt";
		
//	These files have been downloaded from ftp.ebi.ac.uk
//	They have been unzipped.
//	These files are yet unprocessed. 
//	They will be processed and will be accumated in corresponding chromosome base files first in unsorted manner then in sorted manner.
	public static final String TRANSCRIPTION_FACTOR_FILES_DIRECTORY = "C:" + System.getProperty("file.separator") + "eclipse_ganymede" + System.getProperty("file.separator") + "workspace" + System.getProperty("file.separator") + "ftp_encode" + System.getProperty("file.separator") + "transcription_factors";
	public static final String HISTONE_MARK_FILES_DIRECTORY = "C:" + System.getProperty("file.separator") + "eclipse_ganymede" + System.getProperty("file.separator") + "workspace" + System.getProperty("file.separator") + "ftp_encode" + System.getProperty("file.separator") + "histone_macs";
	public static final String DNASE_JUL2010_FILES_DIRECTORY = "C:" + System.getProperty("file.separator") + "eclipse_ganymede" + System.getProperty("file.separator") + "workspace" + System.getProperty("file.separator") + "ftp_encode" + System.getProperty("file.separator") + "dnase_jul2010";
	public static final String DNASE_FILES_DIRECTORY = "C:" + System.getProperty("file.separator") + "eclipse_ganymede" + System.getProperty("file.separator") + "workspace" + System.getProperty("file.separator") + "ftp_encode" + System.getProperty("file.separator") + "dnase";
	
	
	public static final String SEARCH_INPUT_FILE_WITH_NON_BLANK_SNP_IDS= INPUT_DATA + "FTP" + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputWithNonBlankSNPIDs.txt";
	public static final String SEARCH_INPUT_FILE_FOR_TCGA_TEST_DATA = INPUT_DATA + "FTP" + System.getProperty("file.separator") + "TCGA" + System.getProperty("file.separator") + "SearchInputforTCGATestData.txt";
	public static final String SEARCH_INPUT_FILE_FOR_TCGA_DATA_WITH_NON_BLANK_SNP_ROWS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "testtcgadata" + System.getProperty("file.separator") + "SearchInputTCGADataWithNonBlankSNPRows.txt";
	public static final String SEARCH_OUTPUT_FILE_FOR_TCGA_TEST_DATA = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "testtcgadata" + System.getProperty("file.separator") + "SearchOutputforTCGATestData.txt";
	
	
	public static final String SEARCH_OUTPUT_FILE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "annotate" + System.getProperty("file.separator") + "using" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "SearchOutput.txt";
	
	
	public static final String FTP_HG19_REFSEQ_GENES = INPUT_DATA + "FTP" + System.getProperty("file.separator") + "HG19_REFSEQ_GENES" + System.getProperty("file.separator") + "hg19_refseq_genes.txt"; 
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_ANNOTATE_UCSC_ANALYZE_HG19_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "analyze_hg19_refseq_genes.txt";
	
	public static final String CHROMOSOME1 = "chr1";
	public static final String CHROMOSOME2 = "chr2";
	public static final String CHROMOSOME3 = "chr3";
	public static final String CHROMOSOME4 = "chr4";
	public static final String CHROMOSOME5 = "chr5";
	public static final String CHROMOSOME6 = "chr6";
	public static final String CHROMOSOME7 = "chr7";
	public static final String CHROMOSOME8 = "chr8";
	public static final String CHROMOSOME9 = "chr9";
	public static final String CHROMOSOME10 = "chr10";
	public static final String CHROMOSOME11 = "chr11";
	public static final String CHROMOSOME12 = "chr12";
	public static final String CHROMOSOME13 = "chr13";
	public static final String CHROMOSOME14 = "chr14";
	public static final String CHROMOSOME15 = "chr15";
	public static final String CHROMOSOME16 = "chr16";
	public static final String CHROMOSOME17 = "chr17";
	public static final String CHROMOSOME18 = "chr18";
	public static final String CHROMOSOME19 = "chr19";
	public static final String CHROMOSOME20 = "chr20";
	public static final String CHROMOSOME21 = "chr21";
	public static final String CHROMOSOME22 = "chr22";
	public static final String CHROMOSOMEX = "chrX";
	public static final String CHROMOSOMEY = "chrY";
	
	
	//Unsorted Chromosome Base RefSeq Gene Files
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR1_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr1_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR2_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr2_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR3_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr3_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR4_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr4_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR5_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr5_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR6_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr6_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR7_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr7_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR8_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr8_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR9_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr9_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR10_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_ch10_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR11_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr11_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR12_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr12_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR13_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr13_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR14_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr14_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR15_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr15_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR16_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr16_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR17_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr17_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR18_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr18_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR19_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr19_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR20_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr20_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR21_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr21_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHR22_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chr22_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHRX_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chrX_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_UNSORTED_CHRY_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "unsorted_chrY_refseq_genes.txt";
	
	//Sorted Chromosome Base RefSeq Gene Files
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR1_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr1_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR2_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr2_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR3_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr3_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR4_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr4_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR5_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr5_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR6_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr6_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR7_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr7_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR8_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr8_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR9_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr9_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR10_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr10_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR11_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr11_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR12_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr12_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR13_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr13_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR14_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr14_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR15_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr15_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR16_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr16_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR17_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr17_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR18_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr18_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR19_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr19_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR20_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr20_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR21_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr21_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHR22_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chr22_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRX_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chrX_refseq_genes.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_UCSCGENOME_SORTED_CHRY_REFSEQ_GENES = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "ucscgenome" + System.getProperty("file.separator") + "sorted_chrY_refseq_genes.txt";
	
	
	// Unsorted Chromosome Base DNASE File names
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr1_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr2_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr3_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr4_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr5_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr6_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr7_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr8_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr9_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr10_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr11_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr12_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr13_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr14_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr15_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr16_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr17_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr18_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr19_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr20_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr21_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chr22_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chrX_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "unsorted_chrY_dnase.txt";	
	
	// Sorted Chromosome Base DNASE File names
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR1_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr1_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR2_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr2_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR3_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr3_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR4_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr4_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR5_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr5_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR6_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr6_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR7_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr7_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR8_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr8_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR9_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr9_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR10_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr10_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR11_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr11_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR12_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr12_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR13_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr13_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR14_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr14_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR15_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr15_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR16_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr16_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR17_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr17_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR18_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr18_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR19_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr19_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR20_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr20_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR21_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr21_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR22_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chr22_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRX_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chrX_dnase.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRY_DNASE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "dnase" + System.getProperty("file.separator") + "sorted_chrY_dnase.txt";

	
	//for debug sliding window versus interval tree
	public static final String BURCAK_DEBUG_ENCODE_SORTED_CHR1_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "burcak_debug_sorted_chr1_histone.txt";
		
	// Unsorted Chromosome Base HISTONE File names
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr1_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr2_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr3_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr4_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr5_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr6_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr7_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr8_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr9_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr10_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr11_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr12_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr13_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr14_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr15_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr16_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr17_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr18_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr19_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr20_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr21_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chr22_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chrX_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "unsorted_chrY_histone.txt";	
	
	// Sorted Chromosome Base HISTONE File names
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR1_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr1_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR2_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr2_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR3_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr3_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR4_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr4_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR5_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr5_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR6_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr6_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR7_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr7_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR8_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr8_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR9_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr9_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR10_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr10_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR11_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr11_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR12_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr12_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR13_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr13_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR14_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr14_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR15_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr15_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR16_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr16_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR17_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr17_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR18_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr18_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR19_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr19_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR20_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr20_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR21_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr21_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR22_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chr22_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRX_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chrX_histone.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRY_HISTONE = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "histone" + System.getProperty("file.separator") + "sorted_chrY_histone.txt";

			
	// Unsorted Chromosome Base TFBS File names
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR1_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr1_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR2_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr2_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR3_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr3_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR4_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr4_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR5_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr5_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR6_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr6_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR7_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr7_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR8_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr8_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR9_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr9_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR10_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr10_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR11_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr11_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR12_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr12_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR13_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr13_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR14_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr14_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR15_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr15_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR16_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr16_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR17_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr17_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR18_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr18_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR19_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr19_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR20_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr20_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR21_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr21_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHR22_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chr22_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRX_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chrX_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_UNSORTED_CHRY_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "unsorted_chrY_tfbs.txt";	
	
	// Sorted Chromosome Base TFBS File names
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR1_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr1_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR2_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr2_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR3_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr3_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR4_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr4_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR5_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr5_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR6_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr6_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR7_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr7_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR8_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr8_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR9_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr9_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR10_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr10_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR11_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr11_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR12_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr12_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR13_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr13_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR14_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr14_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR15_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr15_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR16_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr16_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR17_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr17_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR18_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr18_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR19_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr19_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR20_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr20_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR21_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr21_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHR22_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chr22_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRX_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chrX_tfbs.txt";
	public static final String C_ECLIPSE_WORKSPACE_DOKTORA_CREATE_ENCODE_SORTED_CHRY_TFBS = OUTPUT_DATA + "Doktora" + System.getProperty("file.separator") + "create" + System.getProperty("file.separator") + "encode" + System.getProperty("file.separator") + "tfbs" + System.getProperty("file.separator") + "sorted_chrY_tbs.txt";
		
//	Common FileWriters for Tfbs, Histone and Dnase Files
	FileWriter chr1FileWriter; 
	FileWriter chr2FileWriter; 
	FileWriter chr3FileWriter; 
	FileWriter chr4FileWriter; 
	FileWriter chr5FileWriter; 
	FileWriter chr6FileWriter; 
	FileWriter chr7FileWriter; 
	FileWriter chr8FileWriter; 
	FileWriter chr9FileWriter; 
	FileWriter chr10FileWriter; 
	FileWriter chr11FileWriter; 
	FileWriter chr12FileWriter; 
	FileWriter chr13FileWriter; 
	FileWriter chr14FileWriter; 
	FileWriter chr15FileWriter; 
	FileWriter chr16FileWriter; 
	FileWriter chr17FileWriter; 
	FileWriter chr18FileWriter; 
	FileWriter chr19FileWriter; 
	FileWriter chr20FileWriter; 
	FileWriter chr21FileWriter; 
	FileWriter chr22FileWriter; 
	FileWriter chrXFileWriter; 
	FileWriter chrYFileWriter; 
	
//	Common BuffredWriters for Tfbs, Histone and Dnase Files
	BufferedWriter chr1BufferedWriter;
	BufferedWriter chr2BufferedWriter;
	BufferedWriter chr3BufferedWriter;
	BufferedWriter chr4BufferedWriter;
	BufferedWriter chr5BufferedWriter;
	BufferedWriter chr6BufferedWriter;
	BufferedWriter chr7BufferedWriter;
	BufferedWriter chr8BufferedWriter;
	BufferedWriter chr9BufferedWriter;
	BufferedWriter chr10BufferedWriter;
	BufferedWriter chr11BufferedWriter;
	BufferedWriter chr12BufferedWriter;
	BufferedWriter chr13BufferedWriter;
	BufferedWriter chr14BufferedWriter;
	BufferedWriter chr15BufferedWriter;
	BufferedWriter chr16BufferedWriter;
	BufferedWriter chr17BufferedWriter;
	BufferedWriter chr18BufferedWriter;
	BufferedWriter chr19BufferedWriter;
	BufferedWriter chr20BufferedWriter;
	BufferedWriter chr21BufferedWriter;
	BufferedWriter chr22BufferedWriter;
	BufferedWriter chrXBufferedWriter;
	BufferedWriter chrYBufferedWriter;

	// Number of tfbs in each chromosome	
	int numberofTfbsinChr1 = 0;
	int numberofTfbsinChr2 = 0;
	int numberofTfbsinChr3 = 0;
	int numberofTfbsinChr4 = 0;
	int numberofTfbsinChr5 = 0;
	int numberofTfbsinChr6 = 0;
	int numberofTfbsinChr7 = 0;
	int numberofTfbsinChr8 = 0;
	int numberofTfbsinChr9 = 0;
	int numberofTfbsinChr10 = 0;
	int numberofTfbsinChr11 = 0;
	int numberofTfbsinChr12 = 0;
	int numberofTfbsinChr13 = 0;
	int numberofTfbsinChr14 = 0;
	int numberofTfbsinChr15 = 0;
	int numberofTfbsinChr16 = 0;
	int numberofTfbsinChr17 = 0;
	int numberofTfbsinChr18 = 0;
	int numberofTfbsinChr19 = 0;
	int numberofTfbsinChr20 = 0;
	int numberofTfbsinChr21 = 0;
	int numberofTfbsinChr22 = 0;
	int numberofTfbsinChrX = 0;
	int numberofTfbsinChrY = 0;

	// Number of histone in each chromosome	
	int numberofHistoneinChr1 = 0;
	int numberofHistoneinChr2 = 0;
	int numberofHistoneinChr3 = 0;
	int numberofHistoneinChr4 = 0;
	int numberofHistoneinChr5 = 0;
	int numberofHistoneinChr6 = 0;
	int numberofHistoneinChr7 = 0;
	int numberofHistoneinChr8 = 0;
	int numberofHistoneinChr9 = 0;
	int numberofHistoneinChr10 = 0;
	int numberofHistoneinChr11 = 0;
	int numberofHistoneinChr12 = 0;
	int numberofHistoneinChr13 = 0;
	int numberofHistoneinChr14 = 0;
	int numberofHistoneinChr15 = 0;
	int numberofHistoneinChr16 = 0;
	int numberofHistoneinChr17 = 0;
	int numberofHistoneinChr18 = 0;
	int numberofHistoneinChr19 = 0;
	int numberofHistoneinChr20 = 0;
	int numberofHistoneinChr21 = 0;
	int numberofHistoneinChr22 = 0;
	int numberofHistoneinChrX = 0;
	int numberofHistoneinChrY = 0;
	
	
	// Number of dnase in each chromosome	
	int numberofDnaseinChr1 = 0;
	int numberofDnaseinChr2 = 0;
	int numberofDnaseinChr3 = 0;
	int numberofDnaseinChr4 = 0;
	int numberofDnaseinChr5 = 0;
	int numberofDnaseinChr6 = 0;
	int numberofDnaseinChr7 = 0;
	int numberofDnaseinChr8 = 0;
	int numberofDnaseinChr9 = 0;
	int numberofDnaseinChr10 = 0;
	int numberofDnaseinChr11 = 0;
	int numberofDnaseinChr12 = 0;
	int numberofDnaseinChr13 = 0;
	int numberofDnaseinChr14 = 0;
	int numberofDnaseinChr15 = 0;
	int numberofDnaseinChr16 = 0;
	int numberofDnaseinChr17 = 0;
	int numberofDnaseinChr18 = 0;
	int numberofDnaseinChr19 = 0;
	int numberofDnaseinChr20 = 0;
	int numberofDnaseinChr21 = 0;
	int numberofDnaseinChr22 = 0;
	int numberofDnaseinChrX = 0;
	int numberofDnaseinChrY = 0;
	
	
	
	
	public int getNumberofDnaseinChr1() {
		return numberofDnaseinChr1;
	}

	public void setNumberofDnaseinChr1(int numberofDnaseinChr1) {
		this.numberofDnaseinChr1 = numberofDnaseinChr1;
	}

	public int getNumberofDnaseinChr2() {
		return numberofDnaseinChr2;
	}

	public void setNumberofDnaseinChr2(int numberofDnaseinChr2) {
		this.numberofDnaseinChr2 = numberofDnaseinChr2;
	}

	public int getNumberofDnaseinChr3() {
		return numberofDnaseinChr3;
	}

	public void setNumberofDnaseinChr3(int numberofDnaseinChr3) {
		this.numberofDnaseinChr3 = numberofDnaseinChr3;
	}

	public int getNumberofDnaseinChr4() {
		return numberofDnaseinChr4;
	}

	public void setNumberofDnaseinChr4(int numberofDnaseinChr4) {
		this.numberofDnaseinChr4 = numberofDnaseinChr4;
	}

	public int getNumberofDnaseinChr5() {
		return numberofDnaseinChr5;
	}

	public void setNumberofDnaseinChr5(int numberofDnaseinChr5) {
		this.numberofDnaseinChr5 = numberofDnaseinChr5;
	}

	public int getNumberofDnaseinChr6() {
		return numberofDnaseinChr6;
	}

	public void setNumberofDnaseinChr6(int numberofDnaseinChr6) {
		this.numberofDnaseinChr6 = numberofDnaseinChr6;
	}

	public int getNumberofDnaseinChr7() {
		return numberofDnaseinChr7;
	}

	public void setNumberofDnaseinChr7(int numberofDnaseinChr7) {
		this.numberofDnaseinChr7 = numberofDnaseinChr7;
	}

	public int getNumberofDnaseinChr8() {
		return numberofDnaseinChr8;
	}

	public void setNumberofDnaseinChr8(int numberofDnaseinChr8) {
		this.numberofDnaseinChr8 = numberofDnaseinChr8;
	}

	public int getNumberofDnaseinChr9() {
		return numberofDnaseinChr9;
	}

	public void setNumberofDnaseinChr9(int numberofDnaseinChr9) {
		this.numberofDnaseinChr9 = numberofDnaseinChr9;
	}

	public int getNumberofDnaseinChr10() {
		return numberofDnaseinChr10;
	}

	public void setNumberofDnaseinChr10(int numberofDnaseinChr10) {
		this.numberofDnaseinChr10 = numberofDnaseinChr10;
	}

	public int getNumberofDnaseinChr11() {
		return numberofDnaseinChr11;
	}

	public void setNumberofDnaseinChr11(int numberofDnaseinChr11) {
		this.numberofDnaseinChr11 = numberofDnaseinChr11;
	}

	public int getNumberofDnaseinChr12() {
		return numberofDnaseinChr12;
	}

	public void setNumberofDnaseinChr12(int numberofDnaseinChr12) {
		this.numberofDnaseinChr12 = numberofDnaseinChr12;
	}

	public int getNumberofDnaseinChr13() {
		return numberofDnaseinChr13;
	}

	public void setNumberofDnaseinChr13(int numberofDnaseinChr13) {
		this.numberofDnaseinChr13 = numberofDnaseinChr13;
	}

	public int getNumberofDnaseinChr14() {
		return numberofDnaseinChr14;
	}

	public void setNumberofDnaseinChr14(int numberofDnaseinChr14) {
		this.numberofDnaseinChr14 = numberofDnaseinChr14;
	}

	public int getNumberofDnaseinChr15() {
		return numberofDnaseinChr15;
	}

	public void setNumberofDnaseinChr15(int numberofDnaseinChr15) {
		this.numberofDnaseinChr15 = numberofDnaseinChr15;
	}

	public int getNumberofDnaseinChr16() {
		return numberofDnaseinChr16;
	}

	public void setNumberofDnaseinChr16(int numberofDnaseinChr16) {
		this.numberofDnaseinChr16 = numberofDnaseinChr16;
	}

	public int getNumberofDnaseinChr17() {
		return numberofDnaseinChr17;
	}

	public void setNumberofDnaseinChr17(int numberofDnaseinChr17) {
		this.numberofDnaseinChr17 = numberofDnaseinChr17;
	}

	public int getNumberofDnaseinChr18() {
		return numberofDnaseinChr18;
	}

	public void setNumberofDnaseinChr18(int numberofDnaseinChr18) {
		this.numberofDnaseinChr18 = numberofDnaseinChr18;
	}

	public int getNumberofDnaseinChr19() {
		return numberofDnaseinChr19;
	}

	public void setNumberofDnaseinChr19(int numberofDnaseinChr19) {
		this.numberofDnaseinChr19 = numberofDnaseinChr19;
	}

	public int getNumberofDnaseinChr20() {
		return numberofDnaseinChr20;
	}

	public void setNumberofDnaseinChr20(int numberofDnaseinChr20) {
		this.numberofDnaseinChr20 = numberofDnaseinChr20;
	}

	public int getNumberofDnaseinChr21() {
		return numberofDnaseinChr21;
	}

	public void setNumberofDnaseinChr21(int numberofDnaseinChr21) {
		this.numberofDnaseinChr21 = numberofDnaseinChr21;
	}

	public int getNumberofDnaseinChr22() {
		return numberofDnaseinChr22;
	}

	public void setNumberofDnaseinChr22(int numberofDnaseinChr22) {
		this.numberofDnaseinChr22 = numberofDnaseinChr22;
	}

	public int getNumberofDnaseinChrX() {
		return numberofDnaseinChrX;
	}

	public void setNumberofDnaseinChrX(int numberofDnaseinChrX) {
		this.numberofDnaseinChrX = numberofDnaseinChrX;
	}

	public int getNumberofDnaseinChrY() {
		return numberofDnaseinChrY;
	}

	public void setNumberofDnaseinChrY(int numberofDnaseinChrY) {
		this.numberofDnaseinChrY = numberofDnaseinChrY;
	}

	public int getNumberofHistoneinChr1() {
		return numberofHistoneinChr1;
	}

	public void setNumberofHistoneinChr1(int numberofHistoneinChr1) {
		this.numberofHistoneinChr1 = numberofHistoneinChr1;
	}

	public int getNumberofHistoneinChr2() {
		return numberofHistoneinChr2;
	}

	public void setNumberofHistoneinChr2(int numberofHistoneinChr2) {
		this.numberofHistoneinChr2 = numberofHistoneinChr2;
	}

	public int getNumberofHistoneinChr3() {
		return numberofHistoneinChr3;
	}

	public void setNumberofHistoneinChr3(int numberofHistoneinChr3) {
		this.numberofHistoneinChr3 = numberofHistoneinChr3;
	}

	public int getNumberofHistoneinChr4() {
		return numberofHistoneinChr4;
	}

	public void setNumberofHistoneinChr4(int numberofHistoneinChr4) {
		this.numberofHistoneinChr4 = numberofHistoneinChr4;
	}

	public int getNumberofHistoneinChr5() {
		return numberofHistoneinChr5;
	}

	public void setNumberofHistoneinChr5(int numberofHistoneinChr5) {
		this.numberofHistoneinChr5 = numberofHistoneinChr5;
	}

	public int getNumberofHistoneinChr6() {
		return numberofHistoneinChr6;
	}

	public void setNumberofHistoneinChr6(int numberofHistoneinChr6) {
		this.numberofHistoneinChr6 = numberofHistoneinChr6;
	}

	public int getNumberofHistoneinChr7() {
		return numberofHistoneinChr7;
	}

	public void setNumberofHistoneinChr7(int numberofHistoneinChr7) {
		this.numberofHistoneinChr7 = numberofHistoneinChr7;
	}

	public int getNumberofHistoneinChr8() {
		return numberofHistoneinChr8;
	}

	public void setNumberofHistoneinChr8(int numberofHistoneinChr8) {
		this.numberofHistoneinChr8 = numberofHistoneinChr8;
	}

	public int getNumberofHistoneinChr9() {
		return numberofHistoneinChr9;
	}

	public void setNumberofHistoneinChr9(int numberofHistoneinChr9) {
		this.numberofHistoneinChr9 = numberofHistoneinChr9;
	}

	public int getNumberofHistoneinChr10() {
		return numberofHistoneinChr10;
	}

	public void setNumberofHistoneinChr10(int numberofHistoneinChr10) {
		this.numberofHistoneinChr10 = numberofHistoneinChr10;
	}

	public int getNumberofHistoneinChr11() {
		return numberofHistoneinChr11;
	}

	public void setNumberofHistoneinChr11(int numberofHistoneinChr11) {
		this.numberofHistoneinChr11 = numberofHistoneinChr11;
	}

	public int getNumberofHistoneinChr12() {
		return numberofHistoneinChr12;
	}

	public void setNumberofHistoneinChr12(int numberofHistoneinChr12) {
		this.numberofHistoneinChr12 = numberofHistoneinChr12;
	}

	public int getNumberofHistoneinChr13() {
		return numberofHistoneinChr13;
	}

	public void setNumberofHistoneinChr13(int numberofHistoneinChr13) {
		this.numberofHistoneinChr13 = numberofHistoneinChr13;
	}

	public int getNumberofHistoneinChr14() {
		return numberofHistoneinChr14;
	}

	public void setNumberofHistoneinChr14(int numberofHistoneinChr14) {
		this.numberofHistoneinChr14 = numberofHistoneinChr14;
	}

	public int getNumberofHistoneinChr15() {
		return numberofHistoneinChr15;
	}

	public void setNumberofHistoneinChr15(int numberofHistoneinChr15) {
		this.numberofHistoneinChr15 = numberofHistoneinChr15;
	}

	public int getNumberofHistoneinChr16() {
		return numberofHistoneinChr16;
	}

	public void setNumberofHistoneinChr16(int numberofHistoneinChr16) {
		this.numberofHistoneinChr16 = numberofHistoneinChr16;
	}

	public int getNumberofHistoneinChr17() {
		return numberofHistoneinChr17;
	}

	public void setNumberofHistoneinChr17(int numberofHistoneinChr17) {
		this.numberofHistoneinChr17 = numberofHistoneinChr17;
	}

	public int getNumberofHistoneinChr18() {
		return numberofHistoneinChr18;
	}

	public void setNumberofHistoneinChr18(int numberofHistoneinChr18) {
		this.numberofHistoneinChr18 = numberofHistoneinChr18;
	}

	public int getNumberofHistoneinChr19() {
		return numberofHistoneinChr19;
	}

	public void setNumberofHistoneinChr19(int numberofHistoneinChr19) {
		this.numberofHistoneinChr19 = numberofHistoneinChr19;
	}

	public int getNumberofHistoneinChr20() {
		return numberofHistoneinChr20;
	}

	public void setNumberofHistoneinChr20(int numberofHistoneinChr20) {
		this.numberofHistoneinChr20 = numberofHistoneinChr20;
	}

	public int getNumberofHistoneinChr21() {
		return numberofHistoneinChr21;
	}

	public void setNumberofHistoneinChr21(int numberofHistoneinChr21) {
		this.numberofHistoneinChr21 = numberofHistoneinChr21;
	}

	public int getNumberofHistoneinChr22() {
		return numberofHistoneinChr22;
	}

	public void setNumberofHistoneinChr22(int numberofHistoneinChr22) {
		this.numberofHistoneinChr22 = numberofHistoneinChr22;
	}

	public int getNumberofHistoneinChrX() {
		return numberofHistoneinChrX;
	}

	public void setNumberofHistoneinChrX(int numberofHistoneinChrX) {
		this.numberofHistoneinChrX = numberofHistoneinChrX;
	}

	public int getNumberofHistoneinChrY() {
		return numberofHistoneinChrY;
	}

	public void setNumberofHistoneinChrY(int numberofHistoneinChrY) {
		this.numberofHistoneinChrY = numberofHistoneinChrY;
	}

	public int getNumberofTfbsinChr1() {
		return numberofTfbsinChr1;
	}

	public void setNumberofTfbsinChr1(int numberofTfbsinChr1) {
		this.numberofTfbsinChr1 = numberofTfbsinChr1;
	}

	public int getNumberofTfbsinChr2() {
		return numberofTfbsinChr2;
	}

	public void setNumberofTfbsinChr2(int numberofTfbsinChr2) {
		this.numberofTfbsinChr2 = numberofTfbsinChr2;
	}

	public int getNumberofTfbsinChr3() {
		return numberofTfbsinChr3;
	}

	public void setNumberofTfbsinChr3(int numberofTfbsinChr3) {
		this.numberofTfbsinChr3 = numberofTfbsinChr3;
	}

	public int getNumberofTfbsinChr4() {
		return numberofTfbsinChr4;
	}

	public void setNumberofTfbsinChr4(int numberofTfbsinChr4) {
		this.numberofTfbsinChr4 = numberofTfbsinChr4;
	}

	public int getNumberofTfbsinChr5() {
		return numberofTfbsinChr5;
	}

	public void setNumberofTfbsinChr5(int numberofTfbsinChr5) {
		this.numberofTfbsinChr5 = numberofTfbsinChr5;
	}

	public int getNumberofTfbsinChr6() {
		return numberofTfbsinChr6;
	}

	public void setNumberofTfbsinChr6(int numberofTfbsinChr6) {
		this.numberofTfbsinChr6 = numberofTfbsinChr6;
	}

	public int getNumberofTfbsinChr7() {
		return numberofTfbsinChr7;
	}

	public void setNumberofTfbsinChr7(int numberofTfbsinChr7) {
		this.numberofTfbsinChr7 = numberofTfbsinChr7;
	}

	public int getNumberofTfbsinChr8() {
		return numberofTfbsinChr8;
	}

	public void setNumberofTfbsinChr8(int numberofTfbsinChr8) {
		this.numberofTfbsinChr8 = numberofTfbsinChr8;
	}

	public int getNumberofTfbsinChr9() {
		return numberofTfbsinChr9;
	}

	public void setNumberofTfbsinChr9(int numberofTfbsinChr9) {
		this.numberofTfbsinChr9 = numberofTfbsinChr9;
	}

	public int getNumberofTfbsinChr10() {
		return numberofTfbsinChr10;
	}

	public void setNumberofTfbsinChr10(int numberofTfbsinChr10) {
		this.numberofTfbsinChr10 = numberofTfbsinChr10;
	}

	public int getNumberofTfbsinChr11() {
		return numberofTfbsinChr11;
	}

	public void setNumberofTfbsinChr11(int numberofTfbsinChr11) {
		this.numberofTfbsinChr11 = numberofTfbsinChr11;
	}

	public int getNumberofTfbsinChr12() {
		return numberofTfbsinChr12;
	}

	public void setNumberofTfbsinChr12(int numberofTfbsinChr12) {
		this.numberofTfbsinChr12 = numberofTfbsinChr12;
	}

	public int getNumberofTfbsinChr13() {
		return numberofTfbsinChr13;
	}

	public void setNumberofTfbsinChr13(int numberofTfbsinChr13) {
		this.numberofTfbsinChr13 = numberofTfbsinChr13;
	}

	public int getNumberofTfbsinChr14() {
		return numberofTfbsinChr14;
	}

	public void setNumberofTfbsinChr14(int numberofTfbsinChr14) {
		this.numberofTfbsinChr14 = numberofTfbsinChr14;
	}

	public int getNumberofTfbsinChr15() {
		return numberofTfbsinChr15;
	}

	public void setNumberofTfbsinChr15(int numberofTfbsinChr15) {
		this.numberofTfbsinChr15 = numberofTfbsinChr15;
	}

	public int getNumberofTfbsinChr16() {
		return numberofTfbsinChr16;
	}

	public void setNumberofTfbsinChr16(int numberofTfbsinChr16) {
		this.numberofTfbsinChr16 = numberofTfbsinChr16;
	}

	public int getNumberofTfbsinChr17() {
		return numberofTfbsinChr17;
	}

	public void setNumberofTfbsinChr17(int numberofTfbsinChr17) {
		this.numberofTfbsinChr17 = numberofTfbsinChr17;
	}

	public int getNumberofTfbsinChr18() {
		return numberofTfbsinChr18;
	}

	public void setNumberofTfbsinChr18(int numberofTfbsinChr18) {
		this.numberofTfbsinChr18 = numberofTfbsinChr18;
	}

	public int getNumberofTfbsinChr19() {
		return numberofTfbsinChr19;
	}

	public void setNumberofTfbsinChr19(int numberofTfbsinChr19) {
		this.numberofTfbsinChr19 = numberofTfbsinChr19;
	}

	public int getNumberofTfbsinChr20() {
		return numberofTfbsinChr20;
	}

	public void setNumberofTfbsinChr20(int numberofTfbsinChr20) {
		this.numberofTfbsinChr20 = numberofTfbsinChr20;
	}

	public int getNumberofTfbsinChr21() {
		return numberofTfbsinChr21;
	}

	public void setNumberofTfbsinChr21(int numberofTfbsinChr21) {
		this.numberofTfbsinChr21 = numberofTfbsinChr21;
	}

	public int getNumberofTfbsinChr22() {
		return numberofTfbsinChr22;
	}

	public void setNumberofTfbsinChr22(int numberofTfbsinChr22) {
		this.numberofTfbsinChr22 = numberofTfbsinChr22;
	}

	public int getNumberofTfbsinChrX() {
		return numberofTfbsinChrX;
	}

	public void setNumberofTfbsinChrX(int numberofTfbsinChrX) {
		this.numberofTfbsinChrX = numberofTfbsinChrX;
	}

	public int getNumberofTfbsinChrY() {
		return numberofTfbsinChrY;
	}

	public void setNumberofTfbsinChrY(int numberofTfbsinChrY) {
		this.numberofTfbsinChrY = numberofTfbsinChrY;
	}

	public FileWriter getChr1FileWriter() {
		return chr1FileWriter;
	}

	public void setChr1FileWriter(FileWriter chr1FileWriter) {
		this.chr1FileWriter = chr1FileWriter;
	}

	public FileWriter getChr2FileWriter() {
		return chr2FileWriter;
	}

	public void setChr2FileWriter(FileWriter chr2FileWriter) {
		this.chr2FileWriter = chr2FileWriter;
	}

	public FileWriter getChr3FileWriter() {
		return chr3FileWriter;
	}

	public void setChr3FileWriter(FileWriter chr3FileWriter) {
		this.chr3FileWriter = chr3FileWriter;
	}

	public FileWriter getChr4FileWriter() {
		return chr4FileWriter;
	}

	public void setChr4FileWriter(FileWriter chr4FileWriter) {
		this.chr4FileWriter = chr4FileWriter;
	}

	public FileWriter getChr5FileWriter() {
		return chr5FileWriter;
	}

	public void setChr5FileWriter(FileWriter chr5FileWriter) {
		this.chr5FileWriter = chr5FileWriter;
	}

	public FileWriter getChr6FileWriter() {
		return chr6FileWriter;
	}

	public void setChr6FileWriter(FileWriter chr6FileWriter) {
		this.chr6FileWriter = chr6FileWriter;
	}

	public FileWriter getChr7FileWriter() {
		return chr7FileWriter;
	}

	public void setChr7FileWriter(FileWriter chr7FileWriter) {
		this.chr7FileWriter = chr7FileWriter;
	}

	public FileWriter getChr8FileWriter() {
		return chr8FileWriter;
	}

	public void setChr8FileWriter(FileWriter chr8FileWriter) {
		this.chr8FileWriter = chr8FileWriter;
	}

	public FileWriter getChr9FileWriter() {
		return chr9FileWriter;
	}

	public void setChr9FileWriter(FileWriter chr9FileWriter) {
		this.chr9FileWriter = chr9FileWriter;
	}

	public FileWriter getChr10FileWriter() {
		return chr10FileWriter;
	}

	public void setChr10FileWriter(FileWriter chr10FileWriter) {
		this.chr10FileWriter = chr10FileWriter;
	}

	public FileWriter getChr11FileWriter() {
		return chr11FileWriter;
	}

	public void setChr11FileWriter(FileWriter chr11FileWriter) {
		this.chr11FileWriter = chr11FileWriter;
	}

	public FileWriter getChr12FileWriter() {
		return chr12FileWriter;
	}

	public void setChr12FileWriter(FileWriter chr12FileWriter) {
		this.chr12FileWriter = chr12FileWriter;
	}

	public FileWriter getChr13FileWriter() {
		return chr13FileWriter;
	}

	public void setChr13FileWriter(FileWriter chr13FileWriter) {
		this.chr13FileWriter = chr13FileWriter;
	}

	public FileWriter getChr14FileWriter() {
		return chr14FileWriter;
	}

	public void setChr14FileWriter(FileWriter chr14FileWriter) {
		this.chr14FileWriter = chr14FileWriter;
	}

	public FileWriter getChr15FileWriter() {
		return chr15FileWriter;
	}

	public void setChr15FileWriter(FileWriter chr15FileWriter) {
		this.chr15FileWriter = chr15FileWriter;
	}

	public FileWriter getChr16FileWriter() {
		return chr16FileWriter;
	}

	public void setChr16FileWriter(FileWriter chr16FileWriter) {
		this.chr16FileWriter = chr16FileWriter;
	}

	public FileWriter getChr17FileWriter() {
		return chr17FileWriter;
	}

	public void setChr17FileWriter(FileWriter chr17FileWriter) {
		this.chr17FileWriter = chr17FileWriter;
	}

	public FileWriter getChr18FileWriter() {
		return chr18FileWriter;
	}

	public void setChr18FileWriter(FileWriter chr18FileWriter) {
		this.chr18FileWriter = chr18FileWriter;
	}

	public FileWriter getChr19FileWriter() {
		return chr19FileWriter;
	}

	public void setChr19FileWriter(FileWriter chr19FileWriter) {
		this.chr19FileWriter = chr19FileWriter;
	}

	public FileWriter getChr20FileWriter() {
		return chr20FileWriter;
	}

	public void setChr20FileWriter(FileWriter chr20FileWriter) {
		this.chr20FileWriter = chr20FileWriter;
	}

	public FileWriter getChr21FileWriter() {
		return chr21FileWriter;
	}

	public void setChr21FileWriter(FileWriter chr21FileWriter) {
		this.chr21FileWriter = chr21FileWriter;
	}

	public FileWriter getChr22FileWriter() {
		return chr22FileWriter;
	}

	public void setChr22FileWriter(FileWriter chr22FileWriter) {
		this.chr22FileWriter = chr22FileWriter;
	}

	public FileWriter getChrXFileWriter() {
		return chrXFileWriter;
	}

	public void setChrXFileWriter(FileWriter chrXFileWriter) {
		this.chrXFileWriter = chrXFileWriter;
	}

	public FileWriter getChrYFileWriter() {
		return chrYFileWriter;
	}

	public void setChrYFileWriter(FileWriter chrYFileWriter) {
		this.chrYFileWriter = chrYFileWriter;
	}

	public BufferedWriter getChr1BufferedWriter() {
		return chr1BufferedWriter;
	}

	public void setChr1BufferedWriter(BufferedWriter chr1BufferedWriter) {
		this.chr1BufferedWriter = chr1BufferedWriter;
	}

	public BufferedWriter getChr2BufferedWriter() {
		return chr2BufferedWriter;
	}

	public void setChr2BufferedWriter(BufferedWriter chr2BufferedWriter) {
		this.chr2BufferedWriter = chr2BufferedWriter;
	}

	public BufferedWriter getChr3BufferedWriter() {
		return chr3BufferedWriter;
	}

	public void setChr3BufferedWriter(BufferedWriter chr3BufferedWriter) {
		this.chr3BufferedWriter = chr3BufferedWriter;
	}

	public BufferedWriter getChr4BufferedWriter() {
		return chr4BufferedWriter;
	}

	public void setChr4BufferedWriter(BufferedWriter chr4BufferedWriter) {
		this.chr4BufferedWriter = chr4BufferedWriter;
	}

	public BufferedWriter getChr5BufferedWriter() {
		return chr5BufferedWriter;
	}

	public void setChr5BufferedWriter(BufferedWriter chr5BufferedWriter) {
		this.chr5BufferedWriter = chr5BufferedWriter;
	}

	public BufferedWriter getChr6BufferedWriter() {
		return chr6BufferedWriter;
	}

	public void setChr6BufferedWriter(BufferedWriter chr6BufferedWriter) {
		this.chr6BufferedWriter = chr6BufferedWriter;
	}

	public BufferedWriter getChr7BufferedWriter() {
		return chr7BufferedWriter;
	}

	public void setChr7BufferedWriter(BufferedWriter chr7BufferedWriter) {
		this.chr7BufferedWriter = chr7BufferedWriter;
	}

	public BufferedWriter getChr8BufferedWriter() {
		return chr8BufferedWriter;
	}

	public void setChr8BufferedWriter(BufferedWriter chr8BufferedWriter) {
		this.chr8BufferedWriter = chr8BufferedWriter;
	}

	public BufferedWriter getChr9BufferedWriter() {
		return chr9BufferedWriter;
	}

	public void setChr9BufferedWriter(BufferedWriter chr9BufferedWriter) {
		this.chr9BufferedWriter = chr9BufferedWriter;
	}

	public BufferedWriter getChr10BufferedWriter() {
		return chr10BufferedWriter;
	}

	public void setChr10BufferedWriter(BufferedWriter chr10BufferedWriter) {
		this.chr10BufferedWriter = chr10BufferedWriter;
	}

	public BufferedWriter getChr11BufferedWriter() {
		return chr11BufferedWriter;
	}

	public void setChr11BufferedWriter(BufferedWriter chr11BufferedWriter) {
		this.chr11BufferedWriter = chr11BufferedWriter;
	}

	public BufferedWriter getChr12BufferedWriter() {
		return chr12BufferedWriter;
	}

	public void setChr12BufferedWriter(BufferedWriter chr12BufferedWriter) {
		this.chr12BufferedWriter = chr12BufferedWriter;
	}

	public BufferedWriter getChr13BufferedWriter() {
		return chr13BufferedWriter;
	}

	public void setChr13BufferedWriter(BufferedWriter chr13BufferedWriter) {
		this.chr13BufferedWriter = chr13BufferedWriter;
	}

	public BufferedWriter getChr14BufferedWriter() {
		return chr14BufferedWriter;
	}

	public void setChr14BufferedWriter(BufferedWriter chr14BufferedWriter) {
		this.chr14BufferedWriter = chr14BufferedWriter;
	}

	public BufferedWriter getChr15BufferedWriter() {
		return chr15BufferedWriter;
	}

	public void setChr15BufferedWriter(BufferedWriter chr15BufferedWriter) {
		this.chr15BufferedWriter = chr15BufferedWriter;
	}

	public BufferedWriter getChr16BufferedWriter() {
		return chr16BufferedWriter;
	}

	public void setChr16BufferedWriter(BufferedWriter chr16BufferedWriter) {
		this.chr16BufferedWriter = chr16BufferedWriter;
	}

	public BufferedWriter getChr17BufferedWriter() {
		return chr17BufferedWriter;
	}

	public void setChr17BufferedWriter(BufferedWriter chr17BufferedWriter) {
		this.chr17BufferedWriter = chr17BufferedWriter;
	}

	public BufferedWriter getChr18BufferedWriter() {
		return chr18BufferedWriter;
	}

	public void setChr18BufferedWriter(BufferedWriter chr18BufferedWriter) {
		this.chr18BufferedWriter = chr18BufferedWriter;
	}

	public BufferedWriter getChr19BufferedWriter() {
		return chr19BufferedWriter;
	}

	public void setChr19BufferedWriter(BufferedWriter chr19BufferedWriter) {
		this.chr19BufferedWriter = chr19BufferedWriter;
	}

	public BufferedWriter getChr20BufferedWriter() {
		return chr20BufferedWriter;
	}

	public void setChr20BufferedWriter(BufferedWriter chr20BufferedWriter) {
		this.chr20BufferedWriter = chr20BufferedWriter;
	}

	public BufferedWriter getChr21BufferedWriter() {
		return chr21BufferedWriter;
	}

	public void setChr21BufferedWriter(BufferedWriter chr21BufferedWriter) {
		this.chr21BufferedWriter = chr21BufferedWriter;
	}

	public BufferedWriter getChr22BufferedWriter() {
		return chr22BufferedWriter;
	}

	public void setChr22BufferedWriter(BufferedWriter chr22BufferedWriter) {
		this.chr22BufferedWriter = chr22BufferedWriter;
	}

	public BufferedWriter getChrXBufferedWriter() {
		return chrXBufferedWriter;
	}

	public void setChrXBufferedWriter(BufferedWriter chrXBufferedWriter) {
		this.chrXBufferedWriter = chrXBufferedWriter;
	}

	public BufferedWriter getChrYBufferedWriter() {
		return chrYBufferedWriter;
	}

	public void setChrYBufferedWriter(BufferedWriter chrYBufferedWriter) {
		this.chrYBufferedWriter = chrYBufferedWriter;
	}

	int totalNumberofTfbs;
	int totalNumberofHistone;
	int totalNumberofDnase;
	
	public int getTotalNumberofTfbs(){
		return numberofTfbsinChr1 + numberofTfbsinChr2 + numberofTfbsinChr3 + numberofTfbsinChr4 + numberofTfbsinChr5 + numberofTfbsinChr6 + numberofTfbsinChr7 + numberofTfbsinChr8 + numberofTfbsinChr9 + numberofTfbsinChr10 + numberofTfbsinChr11 + numberofTfbsinChr12 + numberofTfbsinChr13 + numberofTfbsinChr14 + numberofTfbsinChr15 + numberofTfbsinChr16 + numberofTfbsinChr17 + numberofTfbsinChr18 + numberofTfbsinChr19 + numberofTfbsinChr20 + numberofTfbsinChr21 + numberofTfbsinChr22 + numberofTfbsinChrX + numberofTfbsinChrY;  
	}
	
	public int getTotalNumberofHistone(){
		return numberofHistoneinChr1 + numberofHistoneinChr2 + numberofHistoneinChr3 + numberofHistoneinChr4 + numberofHistoneinChr5 + numberofHistoneinChr6 + numberofHistoneinChr7 + numberofHistoneinChr8 + numberofHistoneinChr9 + numberofHistoneinChr10 + numberofHistoneinChr11 + numberofHistoneinChr12 + numberofHistoneinChr13 + numberofHistoneinChr14 + numberofHistoneinChr15 + numberofHistoneinChr16 + numberofHistoneinChr17 + numberofHistoneinChr18 + numberofHistoneinChr19 + numberofHistoneinChr20 + numberofHistoneinChr21 + numberofHistoneinChr22 + numberofHistoneinChrX + numberofHistoneinChrY;  
	}
	
	public int getTotalNumberofDnase(){
		return numberofDnaseinChr1 + numberofDnaseinChr2 + numberofDnaseinChr3 + numberofDnaseinChr4 + numberofDnaseinChr5 + numberofDnaseinChr6 + numberofDnaseinChr7 + numberofDnaseinChr8 + numberofDnaseinChr9 + numberofDnaseinChr10 + numberofDnaseinChr11 + numberofDnaseinChr12 + numberofDnaseinChr13 + numberofDnaseinChr14 + numberofDnaseinChr15 + numberofDnaseinChr16 + numberofDnaseinChr17 + numberofDnaseinChr18 + numberofDnaseinChr19 + numberofDnaseinChr20 + numberofDnaseinChr21 + numberofDnaseinChr22 + numberofDnaseinChrX + numberofDnaseinChrY;  
	}
	
	//path should only include directory names, without any document name (e.g. C:/Users/)
	public File createFile( String path){
		
		File f = new File( path);
		
		if(f.isDirectory() && !f.exists())
			f.mkdirs();
		else if( !f.isDirectory() && !f.getParentFile().exists())
			f.getParentFile().mkdirs();
		
		return f;
	}
}