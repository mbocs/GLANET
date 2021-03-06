package wholegenome.nonoverlappingbasepairs.usingslidingwindow;

import java.util.ArrayList;
import java.util.List;

import common.Commons;


public class Window {
	
	List<Names> windowBases;
	int windowLow;
	int windowHigh;
	long numberofAllNonoverlappingBases;
	int windowNumber;
	
	
	

	public int getWindowNumber() {
		return windowNumber;
	}



	public void setWindowNumber(int windowNumber) {
		this.windowNumber = windowNumber;
	}



	public long getNumberofAllNonoverlappingBases() {
		return numberofAllNonoverlappingBases;
	}



	public void setNumberofAllNonoverlappingBases(long numberofAllNonoverlappingBases) {
		this.numberofAllNonoverlappingBases = numberofAllNonoverlappingBases;
	}



	public int getWindowLow() {
		return windowLow;
	}



	public void setWindowLow(int windowLow) {
		this.windowLow = windowLow;
	}



	public int getWindowHigh() {
		return windowHigh;
	}




	public void setWindowHigh(int windowHigh) {
		this.windowHigh = windowHigh;
	}




	public List<Names> getWindowBases() {
		return windowBases;
	}





	public void setWindowBases(List<Names> windowBases) {
		this.windowBases = windowBases;
	}




	public Window(int windowSize,int windowLow, int windowHigh,int windowNumber){
		this.windowBases =new ArrayList<Names>(windowSize);
		this.windowLow = windowLow;
		this.windowHigh = windowHigh;
		this.numberofAllNonoverlappingBases = Commons.LONG_ZERO;
		this.windowNumber = windowNumber;
	}









	
	
	

}
