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

		
		ChromosomeName name1 = ChromosomeName.CHROMOSOME1;
		ChromosomeName  name2 = ChromosomeName.CHROMOSOME1;
		
		if(name1.equals(name2)){
			System.out.println("Equality check They are equal");
			
		}
		
		System.out.println("1" + name2.convertEnumtoString());
		System.out.println("2" + name2.toString());
		
		
	}

}
