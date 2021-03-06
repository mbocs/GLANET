/**
 * @author Burcak Otlu
 * Aug 19, 2013
 * 11:30:45 PM
 * 2013
 *
 * 
 */
package mapabilityandgc;

import hg19.GRCh37Hg19Chromosome;
import intervaltree.IntervalTree;

import java.util.ArrayList;
import java.util.List;

import ui.GlanetRunner;

import common.Commons;

import enumtypes.ChromosomeName;

public class SingletonChromosomeBasedMapabilityIntervalTree {
	
	private static IntervalTree SingletonChromosome1MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome2MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome3MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome4MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome5MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome6MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome7MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome8MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome9MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome10MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome11MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome12MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome13MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome14MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome15MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome16MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome17MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome18MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome19MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome20MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome21MapabilityIntervalTree;
	private static IntervalTree SingletonChromosome22MapabilityIntervalTree;
	private static IntervalTree SingletonChromosomeXMapabilityIntervalTree;
	private static IntervalTree SingletonChromosomeYMapabilityIntervalTree;
	
	// Note that the constructor is private
	private SingletonChromosomeBasedMapabilityIntervalTree() {
				// Optional Code
	}
	
	
	public static IntervalTree getSingletonChromosomeBasedMapabilityIntervalTree(ChromosomeName chromName, int chromSize){
		switch(chromName){
			case CHROMOSOME1 :  return getSingletonChromosome1MapabilityIntervalTree(chromSize); 
			case CHROMOSOME2 :  return getSingletonChromosome2MapabilityIntervalTree(chromSize); 
			case CHROMOSOME3 :  return getSingletonChromosome3MapabilityIntervalTree(chromSize); 
			case CHROMOSOME4 :  return getSingletonChromosome4MapabilityIntervalTree(chromSize); 
			case CHROMOSOME5 :  return getSingletonChromosome5MapabilityIntervalTree(chromSize); 
			case CHROMOSOME6 :  return getSingletonChromosome6MapabilityIntervalTree(chromSize); 
			case CHROMOSOME7 :  return getSingletonChromosome7MapabilityIntervalTree(chromSize); 
			case CHROMOSOME8 :  return getSingletonChromosome8MapabilityIntervalTree(chromSize); 
			case CHROMOSOME9 :  return getSingletonChromosome9MapabilityIntervalTree(chromSize); 
			case CHROMOSOME10 :  return getSingletonChromosome10MapabilityIntervalTree(chromSize); 
			case CHROMOSOME11 :  return getSingletonChromosome11MapabilityIntervalTree(chromSize); 
			case CHROMOSOME12 :  return getSingletonChromosome12MapabilityIntervalTree(chromSize); 
			case CHROMOSOME13 :  return getSingletonChromosome13MapabilityIntervalTree(chromSize); 
			case CHROMOSOME14 :  return getSingletonChromosome14MapabilityIntervalTree(chromSize); 
			case CHROMOSOME15 :  return getSingletonChromosome15MapabilityIntervalTree(chromSize); 
			case CHROMOSOME16 :  return getSingletonChromosome16MapabilityIntervalTree(chromSize); 
			case CHROMOSOME17 :  return getSingletonChromosome17MapabilityIntervalTree(chromSize); 
			case CHROMOSOME18 :  return getSingletonChromosome18MapabilityIntervalTree(chromSize); 
			case CHROMOSOME19 :  return getSingletonChromosome19MapabilityIntervalTree(chromSize); 
			case CHROMOSOME20:  return getSingletonChromosome20MapabilityIntervalTree(chromSize); 
			case CHROMOSOME21 :  return getSingletonChromosome21MapabilityIntervalTree(chromSize); 
			case CHROMOSOME22 :  return getSingletonChromosome22MapabilityIntervalTree(chromSize); 
			case CHROMOSOMEX :  return getSingletonChromosomeXMapabilityIntervalTree(chromSize); 
			case CHROMOSOMEY :  return getSingletonChromosomeYMapabilityIntervalTree(chromSize); 
				
		}//End of switch
		
		return null;
		
	}
	
	public static IntervalTree getSingletonChromosome1MapabilityIntervalTree(int chromSize) {
		if (SingletonChromosome1MapabilityIntervalTree == null) {
			SingletonChromosome1MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME1,SingletonChromosome1MapabilityIntervalTree);	
		}
		return SingletonChromosome1MapabilityIntervalTree;
	}

	
	public static IntervalTree getSingletonChromosome2MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome2MapabilityIntervalTree == null) {
			SingletonChromosome2MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME2,SingletonChromosome2MapabilityIntervalTree);	
		}
		return SingletonChromosome2MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome3MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome3MapabilityIntervalTree == null) {
			SingletonChromosome3MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME3,SingletonChromosome3MapabilityIntervalTree);	
		}
		return SingletonChromosome3MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome4MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome4MapabilityIntervalTree == null) {
			SingletonChromosome4MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME4,SingletonChromosome4MapabilityIntervalTree);	
		}
		return SingletonChromosome4MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome5MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome5MapabilityIntervalTree == null) {
			SingletonChromosome5MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME5,SingletonChromosome5MapabilityIntervalTree);	
		}
		return SingletonChromosome5MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome6MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome6MapabilityIntervalTree == null) {
			SingletonChromosome6MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME6,SingletonChromosome6MapabilityIntervalTree);	
		}
		return SingletonChromosome6MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome7MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome7MapabilityIntervalTree == null) {
			SingletonChromosome7MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME7,SingletonChromosome7MapabilityIntervalTree);	
		}
		return SingletonChromosome7MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome8MapabilityIntervalTree(int chromSize) {
		if (SingletonChromosome8MapabilityIntervalTree == null) {
			SingletonChromosome8MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME8,SingletonChromosome8MapabilityIntervalTree);	
		}
		return SingletonChromosome8MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome9MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome9MapabilityIntervalTree == null) {
			SingletonChromosome9MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME9,SingletonChromosome9MapabilityIntervalTree);	
		}
		return SingletonChromosome9MapabilityIntervalTree;
	}
	public static IntervalTree getSingletonChromosome10MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome10MapabilityIntervalTree == null) {
			SingletonChromosome10MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME10,SingletonChromosome10MapabilityIntervalTree);	
		}
		return SingletonChromosome10MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome11MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome11MapabilityIntervalTree == null) {
			SingletonChromosome11MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME11,SingletonChromosome11MapabilityIntervalTree);	
		}
		return SingletonChromosome11MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome12MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome12MapabilityIntervalTree == null) {
			SingletonChromosome12MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME12,SingletonChromosome12MapabilityIntervalTree);	
		}
		return SingletonChromosome12MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome13MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome13MapabilityIntervalTree == null) {
			SingletonChromosome13MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME13,SingletonChromosome13MapabilityIntervalTree);	
		}
		return SingletonChromosome13MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome14MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome14MapabilityIntervalTree == null) {
			SingletonChromosome14MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME14,SingletonChromosome14MapabilityIntervalTree);	
		}
		return SingletonChromosome14MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome15MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome15MapabilityIntervalTree == null) {
			SingletonChromosome15MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME15,SingletonChromosome15MapabilityIntervalTree);	
		}
		return SingletonChromosome15MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome16MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome16MapabilityIntervalTree == null) {
			SingletonChromosome16MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME16,SingletonChromosome16MapabilityIntervalTree);	
		}
		return SingletonChromosome16MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome17MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome17MapabilityIntervalTree == null) {
			SingletonChromosome17MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME17,SingletonChromosome17MapabilityIntervalTree);	
		}
		return SingletonChromosome17MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome18MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome18MapabilityIntervalTree == null) {
			SingletonChromosome18MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME18,SingletonChromosome18MapabilityIntervalTree);	
		}
		return SingletonChromosome18MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome19MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome19MapabilityIntervalTree == null) {
			SingletonChromosome19MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME19,SingletonChromosome19MapabilityIntervalTree);	
		}
		return SingletonChromosome19MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome20MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome20MapabilityIntervalTree == null) {
			SingletonChromosome20MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME20,SingletonChromosome20MapabilityIntervalTree);	
		}
		return SingletonChromosome20MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome21MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome21MapabilityIntervalTree == null) {
			SingletonChromosome21MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME21,SingletonChromosome21MapabilityIntervalTree);	
		}
		return SingletonChromosome21MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosome22MapabilityIntervalTree( int chromSize) {
		if (SingletonChromosome22MapabilityIntervalTree == null) {
			SingletonChromosome22MapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOME22,SingletonChromosome22MapabilityIntervalTree);	
		}
		return SingletonChromosome22MapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosomeXMapabilityIntervalTree( int chromSize) {
		if (SingletonChromosomeXMapabilityIntervalTree == null) {
			SingletonChromosomeXMapabilityIntervalTree = new IntervalTree();
				Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOMEX,SingletonChromosomeXMapabilityIntervalTree);	
		}
		return SingletonChromosomeXMapabilityIntervalTree;
	}
	
	public static IntervalTree getSingletonChromosomeYMapabilityIntervalTree( int chromSize) {
		if (SingletonChromosomeYMapabilityIntervalTree == null) {
			SingletonChromosomeYMapabilityIntervalTree = new IntervalTree();
			Mapability.fillChromosomeBasedMapabilityIntervalTree(chromSize,ChromosomeName.CHROMOSOMEY,SingletonChromosomeYMapabilityIntervalTree);	
		}
		return SingletonChromosomeYMapabilityIntervalTree;
	}
	
	
	//args[0] must have input file name with folder
	//args[1] must have GLANET installation folder with "\\" at the end. This folder will be used for outputFolder and dataFolder.
	//args[2] must have Input File Format		
	//args[3] must have Number of Permutations	
	//args[4] must have False Discovery Rate (ex: 0.05)
	//args[5] must have Generate Random Data Mode (with GC and Mapability/without GC and Mapability)
	//args[6] must have writeGeneratedRandomDataMode checkBox
	//args[7] must have writePermutationBasedandParametricBasedAnnotationResultMode checkBox
	//args[8] must have writePermutationBasedAnnotationResultMode checkBox
	public static void main(String[] args) {
		
		String glanetFolder = args[1];
		String dataFolder 	= glanetFolder + System.getProperty("file.separator") + Commons.DATA + System.getProperty("file.separator") ;
		String outputFolder = glanetFolder + System.getProperty("file.separator") + Commons.OUTPUT + System.getProperty("file.separator") ;
	
		
		List<Integer> chromosomeSizes = new ArrayList<Integer>();
		int chromSize;
		IntervalTree intervalTree;
	 	
		GRCh37Hg19Chromosome.initializeChromosomeSizes(chromosomeSizes);
	    
		GRCh37Hg19Chromosome.getHg19ChromosomeSizes(chromosomeSizes, dataFolder, Commons.HG19_CHROMOSOME_SIZES_INPUT_FILE);
		chromSize = GRCh37Hg19Chromosome.getHg19ChromsomeSize(chromosomeSizes, ChromosomeName.CHROMOSOME1);
		//for testing purposes
//		intervalTree = getSingletonChromosomeBasedMapabilityIntervalTree(Commons.CHROMOSOME1, chromSize);

		GlanetRunner.appendLog("Look at interval tree");
	}

}
