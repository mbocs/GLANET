/**
 * @author Burcak Otlu
 * Mar 7, 2014
 * 4:21:05 PM
 * 2014
 *
 * 
 */
package processinputdata.augment;

public class SnpPosition {
	
	int startZeroBased;
	int endZeroBased;
	
	
	



	public int getStartZeroBased() {
		return startZeroBased;
	}



	public void setStartZeroBased(int startZeroBased) {
		this.startZeroBased = startZeroBased;
	}



	public int getEndZeroBased() {
		return endZeroBased;
	}



	public void setEndZeroBased(int endZeroBased) {
		this.endZeroBased = endZeroBased;
	}



	/**
	 * 
	 */
	public SnpPosition() {
		// TODO Auto-generated constructor stub
		
	}

	
	
	public SnpPosition(int startZeroBased, int endZeroBased) {
		super();
		this.startZeroBased = startZeroBased;
		this.endZeroBased = endZeroBased;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
