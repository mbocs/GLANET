/**
 * @author burcakotlu
 * @date Jun 25, 2014 
 * @time 11:47:15 AM
 */
package test;

import enumtypes.ChromosomeName;

/**
 * 
 */
public class EnumEqualTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChromosomeName chrName = ChromosomeName.CHROMOSOME1;
		String chrString = "chr2";
		
		if(chrName.equals(ChromosomeName.convertStringtoEnum(chrString))){
				System.out.println("It workss");
		}else{
			System.out.println("They are not equal");
		}

	}

}