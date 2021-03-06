/**
 * @author Burcak Otlu
 *
 * 
 */


/*
 * Interval Tree is a red black tree which is augmented with max attribute.
 * Red black tree is a variation of binary search tree + 1 bit per node: an attribute color, which is either red or black.
 * Red black tree is balanced, its height is O(log n), where is n is the number of nodes.
 * Operations will take O(log n) time in the worst case. 
 * 
 * Red black tree properties:
 * 1. Every node is either red or black.
 * 2. The root is black.
 * 3. Every leaf(nil[T]) is black.
 * 4. If a node is red, then both its children are black. (Hence no two reds in a row on a simple path from the root to a leaf.)
 * 5. For each node, all paths from the node to descendant leaves contain the same number of black nodes.
 * 
 * This Interval Tree implementation is based on Introduction to Algorithms book of Cormen et al. 2nd Edition
 * 
 * Please note that IntervalTreeDelete function does not handle erroneous inputs:
 * Like deleting a node which does not exists in the interval tree 
 * and deleting the root of tree when there is no NOT_SENTINEL node has left.
 * 
 * 
 */
package intervaltree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ui.GlanetRunner;
import annotate.intervals.parametric.PermutationNumberTfNameCellLineNameOverlap;
import annotate.intervals.parametric.PermutationNumberTfNumberCellLineNumberOverlap;
import annotate.intervals.parametric.PermutationNumberUcscRefSeqGeneNumberOverlap;
import annotate.intervals.parametric.PermutationNumberUcscRefSeqGeneOverlap;
import annotate.intervals.parametric.TfCellLineOverlapWithNumbers;
import annotate.intervals.parametric.TfNameandCellLineNameOverlap;
import annotate.intervals.parametric.UcscRefSeqGeneOverlap;
import annotate.intervals.parametric.UcscRefSeqGeneOverlapWithNumbers;
import auxiliary.FileOperations;

import common.Commons;

import enumtypes.ChromosomeName;
import enumtypes.GeneSetAnalysisType;
import enumtypes.KeggPathwayAnalysisType;
import enumtypes.KeyOrder;
import enumtypes.NodeName;
import gnu.trove.iterator.TShortIterator;
import gnu.trove.list.TShortList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TIntShortMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.TShortShortMap;

public class IntervalTree {

	IntervalTreeNode root;
	int numberofNodes;
	long numberofNonOverlappingBases;
	

	public long getNumberofNonOverlappingBases() {
		return numberofNonOverlappingBases;
	}

	public void setNumberofNonOverlappingBases(long numberofNonOverlappingBases) {
		this.numberofNonOverlappingBases = numberofNonOverlappingBases;
	}

	public int getNumberofNodes() {
		return numberofNodes;
	}

	public void setNumberofNodes(int numberofNodes) {
		this.numberofNodes = numberofNodes;
	}

	public IntervalTreeNode getRoot() {
		return root;
	}

	public void setRoot(IntervalTreeNode root) {
		this.root = root;
	}
	
	

	public IntervalTree() {
		super();
		root = new IntervalTreeNode();
		this.numberofNodes = 0;
		this.numberofNonOverlappingBases =0;
	}
	
	

	public static int maximum(int x, int y, int z){
		
		int max = x;
	
		if (y>max) 
			max = y;
		if (z>max ) 
			max = z;
		
		return max;
	}
	
	
	public static int minimum(int x, int y, int z){
		
		int min = x;
	
		if (y<min) 
			min = y;
		if (z<min) 
			min = z;
		
		return min;
	}
	

	
	public static int max(IntervalTreeNode node){
		
		int a = Integer.MIN_VALUE;
		int b = Integer.MIN_VALUE;
		
		if (node.getLeft().getNodeName().isNotSentinel())
			a= node.getLeft().getMax();
		
		if(node.getRight().getNodeName().isNotSentinel())
			b= node.getRight().getMax();
		
		return maximum(node.getHigh(), a, b);
					
	}
	
	public static int min(IntervalTreeNode node){
		
		int a = Integer.MAX_VALUE;
		int b = Integer.MAX_VALUE;
		
		if (node.getLeft().getNodeName().isNotSentinel())
			a= ((OtherIntervalTreeNode)(node.getLeft())).getMin();
		
		if(node.getRight().getNodeName().isNotSentinel())
			b= ((OtherIntervalTreeNode)(node.getRight())).getMin();
		
		return minimum(node.getLow(), a, b);
					
	}
	
	
	public void incrementHeightByOne(IntervalTreeNode node){
		
		((OtherIntervalTreeNode)node).setHeight( ((OtherIntervalTreeNode)node).getHeight() +1);
		
		if (node.getLeft().getNodeName().isNotSentinel()){
			incrementHeightByOne(node.getLeft());
		}
		if (node.getRight().getNodeName().isNotSentinel()){
			incrementHeightByOne(node.getRight());
		}
		
	}
	
	public void decrementHeightByOne(IntervalTreeNode node){
		((OtherIntervalTreeNode)node).setHeight(((OtherIntervalTreeNode)node).getHeight()-1);
		
		if (node.getLeft().getNodeName().isNotSentinel()){
			decrementHeightByOne(node.getLeft());
		}
		if (node.getRight().getNodeName().isNotSentinel()){
			decrementHeightByOne(node.getRight());
		}
		
	}
	
//	We change the pointer structure with Rotation  
//	When we do a left rotation on a node x,
//	we assume that its right child y is not null.
//	Left rotation pivots around the link from x to y.
//	It makes the y the new root of the subtree, with x as the new left child of y 
//	and y's left child becomes x's right child
//	burcak: left rotate edilen node x, right child'inin y diyelim, left child'i oluyor
	public void intervalTreeLeftRotate(IntervalTree tree, IntervalTreeNode x){
		
		IntervalTreeNode y = x.getRight();		
		
		x.setRight(y.getLeft());
		if(!NodeName.SENTINEL.equals(y.getLeft().getNodeName())){
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		
		if(NodeName.SENTINEL.equals(x.getParent().getNodeName())){
			tree.setRoot(y);
		} else{
			if(x==(x.getParent().getLeft())){
				x.getParent().setLeft(y);
			}else{
				x.getParent().setRight(y);
			}	
		}
		
		y.setLeft(x);
		x.setParent(y);
		
//		update max attributes
		x.setMax(max(x));
		y.setMax(max(y));
		
//		update min attributes
		if (x instanceof OtherIntervalTreeNode && y instanceof OtherIntervalTreeNode){
			((OtherIntervalTreeNode)x).setMin(min(x));
			((OtherIntervalTreeNode)y).setMin(min(y));
			
			//update height attributes
			((OtherIntervalTreeNode)x).setHeight(((OtherIntervalTreeNode)x).getHeight()+1);
			if(x.getLeft().getNodeName().isNotSentinel()){
				incrementHeightByOne(x.getLeft());
			}
			
			((OtherIntervalTreeNode)y).setHeight(((OtherIntervalTreeNode)y).getHeight()-1);
			if(y.getRight().getNodeName().isNotSentinel()){
				decrementHeightByOne(y.getRight());
			}

		}
		
		
		
					
	}
	
//	burcak: right rotate edilen node x left child'inin y diyelim, right child'i oluyor
	public void intervalTreeRightRotate(IntervalTree tree, IntervalTreeNode x){
		IntervalTreeNode y = x.getLeft();
		
		x.setLeft(y.getRight());
		if(!NodeName.SENTINEL.equals(y.getRight().getNodeName())){
			y.getRight().setParent(x);
		}
		y.setParent(x.getParent());
		
		if(NodeName.SENTINEL.equals(x.getParent().getNodeName())){
			tree.setRoot(y);
		} else{
			if(x==(x.getParent().getRight())){
				x.getParent().setRight(y);
			}else{
				x.getParent().setLeft(y);
			}	
		}
		
		y.setRight(x);
		x.setParent(y);
		
//		update max attributes
		x.setMax(max(x));
		y.setMax(max(y));		
		
		if (x instanceof OtherIntervalTreeNode && y instanceof OtherIntervalTreeNode){
			//update min attributes
			((OtherIntervalTreeNode)x).setMin(min(x));
			((OtherIntervalTreeNode)y).setMin(min(y));
			
			//update height attributes
			((OtherIntervalTreeNode)x).setHeight(((OtherIntervalTreeNode)x).getHeight()+1);
			if(x.getRight().getNodeName().isNotSentinel()){
				incrementHeightByOne(x.getRight());
			}
			
			((OtherIntervalTreeNode)y).setHeight(((OtherIntervalTreeNode)y).getHeight()-1);
			if(y.getLeft().getNodeName().isNotSentinel()){
				decrementHeightByOne(y.getLeft());
			}
		}
		
			
		
						
	}

	
	public void intervalTreeDeleteFixUp(IntervalTree tree, IntervalTreeNode x){
		IntervalTreeNode w;
		
		
		while(x!=tree.getRoot() && x.getColor()==Commons.BLACK){
			if (x== (x.getParent()).getLeft()){
				w = x.getParent().getRight();
				
				//case1: w is red.
				if(w.getColor()==Commons.RED){
					w.setColor(Commons.BLACK);								//Case1
					x.getParent().setColor(Commons.RED);					//Case1
					intervalTreeLeftRotate(tree, x.getParent());	//Case1
					w = x.getParent().getRight(); 					//Case1
				}
				
				//case2: w is black and both of w's children are black
				if(w.getLeft().getColor()==Commons.BLACK && w.getRight().getColor()==Commons.BLACK ){
					w.setColor(Commons.RED);								//Case2
					x= x.getParent();								//Case2
				}else{
					//case3: w is black, w's left child is red and w's right child is black
					if((w.getRight()).getColor()==Commons.BLACK){
						w.getLeft().setColor(Commons.BLACK);						//Case3
						w.setColor(Commons.RED);								//Case3
						intervalTreeRightRotate(tree, w);				//Case3
						w = x.getParent().getRight();					//Case3
					}
					//case4: w is black, w's left child is black and w's right child is red.
					w.setColor(x.getParent().getColor());				//Case4
					x.getParent().setColor(Commons.BLACK);						//Case4
					w.getRight().setColor(Commons.BLACK);							//Case4
					intervalTreeLeftRotate(tree, x.getParent());		//Case4
					x = tree.getRoot();									//Case4						
					
				}				
				
			}else{
				
				w = x.getParent().getLeft();
				
				//case1: w is red.
				if(w.getColor()==Commons.RED){
					w.setColor(Commons.BLACK);								//Case1
					x.getParent().setColor(Commons.RED);					//Case1
					intervalTreeRightRotate(tree, x.getParent());	//Case1
					w = x.getParent().getLeft(); 					//Case1
				}
				
				//case2: w is black and both of w's children are black
				if(w.getRight().getColor()==Commons.BLACK && w.getLeft().getColor()==Commons.BLACK ){
					w.setColor(Commons.RED);								//Case2
					x= x.getParent();								//Case2
				}else{
					//case3: w is black, w's right child is red and w's left child is black
					if((w.getLeft()).getColor()==Commons.BLACK){
						w.getRight().setColor(Commons.BLACK);						//Case3
						w.setColor(Commons.RED);								//Case3
						intervalTreeLeftRotate(tree, w);				//Case3
						w = x.getParent().getLeft();					//Case3
					}
					//case4: w is black, w's right child is black and w's left child is red.
					w.setColor(x.getParent().getColor());				//Case4
					x.getParent().setColor(Commons.BLACK);						//Case4
					w.getLeft().setColor(Commons.BLACK);							//Case4
					intervalTreeRightRotate(tree, x.getParent());		//Case4
					x = tree.getRoot();									//Case4						
					
				}
			
			}
			
		}//end of while
		
		x.setColor(Commons.BLACK);
	}
	
	public void intervalTreeInsertFixUp(IntervalTree tree, IntervalTreeNode z){
		IntervalTreeNode y;
		
			while(z.getParent().getColor()==Commons.RED){
				if ((z.getParent().getParent().getLeft()) == z.getParent()){
					y= z.getParent().getParent().getRight();
					if (y.getColor()==Commons.RED){
						//Case1 y is red
						z.getParent().setColor(Commons.BLACK); 				//Case1
						y.setColor(Commons.BLACK);             				//Case1
						z.getParent().getParent().setColor(Commons.RED); 	//Case1
						z = z.getParent().getParent(); 				//Case1
					}else {
						if (z==z.getParent().getRight()){
							//Case2 y is black, z is a right child 
							z = z.getParent();							//Case2
							intervalTreeLeftRotate(tree, z);			//Case2
						}
						//Case3 y is black, z is a left child
						z.getParent().setColor(Commons.BLACK);								//Case3
						z.getParent().getParent().setColor(Commons.RED);					//Case3 
						intervalTreeRightRotate(tree,z.getParent().getParent()); 	//Case3
						
					}
				}else{
					y= z.getParent().getParent().getLeft();
					if (y.getColor()==Commons.RED){
						z.getParent().setColor(Commons.BLACK); 				//Case1
						y.setColor(Commons.BLACK);             				//Case1
						z.getParent().getParent().setColor(Commons.RED); 	//Case1
						z = z.getParent().getParent(); 				//Case1
					}else{ 
						if (z==z.getParent().getLeft()){
							z = z.getParent();							//Case2
							intervalTreeRightRotate(tree, z);			//Case2
						}
						z.getParent().setColor(Commons.BLACK);								//Case3
						z.getParent().getParent().setColor(Commons.RED);					//Case3 
						intervalTreeLeftRotate(tree,z.getParent().getParent()); 	//Case3
						
					}
				}
								
			}
		tree.getRoot().setColor(Commons.BLACK);
	}
	
	public void updateMinAttribute(IntervalTreeNode x){
		int savedMin;
		
		if(x.getNodeName().isNotSentinel()){
			
			
			savedMin= ((OtherIntervalTreeNode)x).getMin(); 
			((OtherIntervalTreeNode)x).setMin(min(x));	
			
			if(savedMin!=((OtherIntervalTreeNode)x).getMin()){
				updateMinAttribute(x.getParent());
			}	
		}		
		
	}

	public void updateMaxAttribute(IntervalTreeNode x){
		
		
		boolean hasChanged = false;
		int savedMax;
		
		if(x.getNodeName().isNotSentinel()){
			savedMax= x.getMax(); 
			x.setMax(max(x));	
			
			if(savedMax!=x.getMax()){
				hasChanged = true;
			}
			
		}else{
			return;
		}
		
		if (hasChanged)
			updateMaxAttribute(x.getParent());
		
		
	}
	
	/*
	 * intervalTreeMinimum returns the minimum elementin the subtree rooted at a given node x
	 */
	public IntervalTreeNode intervalTreeMinimum(IntervalTreeNode x){
		while(!NodeName.SENTINEL.equals(x.getLeft().getNodeName())){
			x = x.getLeft();
		}
		
		return x;
	}
	
	/*
	 * intervalTreeSuccessor is broken into two cases.
	 * If the right subtree of node x is nonempty, then the successor of x is just the left-most node in the right subtree.
	 * 
	 * On the other hand, if the right subtree of node x is empty and x has a successor y, then is the lowest ancestor of x 
	 * whose left child is an ancestor of x. 
	 * To find y, we go up the tree from x until we encounter a node that is the left child of its parent.
	 */
	public IntervalTreeNode intervalTreeSuccessor(IntervalTreeNode x){
		IntervalTreeNode y;
		
		if (!NodeName.SENTINEL.equals(x.getRight().getNodeName())){
			return intervalTreeMinimum(x.getRight());			
		} 
		
		y = x.getParent();
		
		while(!NodeName.SENTINEL.equals(y.getNodeName()) && (x==y.getRight())){
			x = y;
			y = y.getParent();
		}		
		
		return y;
	}
	
	/*
	 *  If we delete, thus removing a node, what color was the node that was removed?
	 *  
	 *  RED? Ok, since we won't have changed any black-heights, nor will we have created two red nodes in a row.
	 *  Also, cannot cause a violation of property 2, since if the removed node was red, it could not have been the root.
	 *  
	 *  BLACK? Could cause there to be two reds in a row(violating property 4), and can also cause a violation of property 5.
	 *  Could also cause a violation of property 2, if the removed node was the root and its child-which becomes the new root -was red. 
	 */
	public IntervalTreeNode intervalTreeDelete(IntervalTree tree, IntervalTreeNode z){
		
        //since z might be changed, do the decrements before
		//Decrement the number of nodes by one
		tree.setNumberofNodes(tree.getNumberofNodes()-1);
		
		//Decrease the number of non overlapping bases by size of the deleted node z
		tree.setNumberofNonOverlappingBases(tree.getNumberofNonOverlappingBases()-z.getNumberofBases());
		
		
		//Start by doing regular binary search tree 
		IntervalTreeNode y;
		IntervalTreeNode x;
		
		//Determine a node y to splice out.
		//The node y is either the input node z (if z has at most 1 child) or
		//the successor of z if z has two children.
		if (NodeName.SENTINEL.equals(z.getLeft().getNodeName()) || NodeName.SENTINEL.equals(z.getRight().getNodeName())){
			y = z;			
		}else{
			y = intervalTreeSuccessor(z);
		}
		
		//x is set to the not null child of the y or
		//x is set to nil[T] if y has no children.
		if(!NodeName.SENTINEL.equals(y.getLeft().getNodeName())){
			x = y.getLeft();
		}else{
			x = y.getRight();
		}
		
		//The node y is spliced out here by modifying pointers in p[y] and x.
		//Splicing out y is somewhat complicated by the need for proper handling of the boundary conditions,
		//which occur when x = null or when y is the root.
		//

		x.setParent(y.getParent());
		
		if ((x instanceof OtherIntervalTreeNode) && (y instanceof OtherIntervalTreeNode)){
			((OtherIntervalTreeNode)x).setHeight(((OtherIntervalTreeNode)(y.getParent())).getHeight()+1);
		}
		
		if (NodeName.SENTINEL.equals(y.getParent().getNodeName())){
			tree.setRoot(x);
		}else{
			if(y==(y.getParent()).getLeft()){
				(y.getParent()).setLeft(x);
			}else{
				(y.getParent()).setRight(x);
			}
		}
		
		
		//set max value of parent of x		
		updateMaxAttribute(x.getParent());
		
		//set min value of parent of x	
		if (x instanceof OtherIntervalTreeNode){
			updateMinAttribute(x.getParent());			
		}
			
		//If the successor of z was the node spliced out, 
		//the contents of z are moved from y to z, overwriting the previous contents.
		//data fields of node y is copied into node z.
		//node y takes place of node z.
		if(y!=z){
			//copy y's satellite data into z
			z.setChromName(y.getChromName());
			z.setLow(y.getLow());
			z.setHigh(y.getHigh());
			z.setNumberofBases(y.getNumberofBases());
		
			
			if ((z instanceof DnaseIntervalTreeNode) && (y instanceof DnaseIntervalTreeNode)){
				((DnaseIntervalTreeNode)z).setCellLineName(((DnaseIntervalTreeNode)y).getCellLineName());			
				((DnaseIntervalTreeNode)z).setFileName(((DnaseIntervalTreeNode)y).getFileName());		
			}
			
			
			else if ((z instanceof TforHistoneIntervalTreeNode) && (y instanceof TforHistoneIntervalTreeNode)){
				((TforHistoneIntervalTreeNode)z).setTfbsorHistoneName(((TforHistoneIntervalTreeNode)y).getTfbsorHistoneName());			
				((TforHistoneIntervalTreeNode)z).setCellLineName(((TforHistoneIntervalTreeNode)y).getCellLineName());			
				((TforHistoneIntervalTreeNode)z).setFileName(((TforHistoneIntervalTreeNode)y).getFileName());		
			}
			
			
			else if ((z instanceof UcscRefSeqGeneIntervalTreeNode) && (y instanceof UcscRefSeqGeneIntervalTreeNode)){
				((UcscRefSeqGeneIntervalTreeNode)z).setRefSeqGeneName(((UcscRefSeqGeneIntervalTreeNode)y).getRefSeqGeneName());
				((UcscRefSeqGeneIntervalTreeNode)z).setGeneEntrezId(((UcscRefSeqGeneIntervalTreeNode)y).getGeneEntrezId());
				((UcscRefSeqGeneIntervalTreeNode)z).setGeneHugoSymbol(((UcscRefSeqGeneIntervalTreeNode)y).getGeneHugoSymbol());
				((UcscRefSeqGeneIntervalTreeNode)z).setIntervalName(((UcscRefSeqGeneIntervalTreeNode)y).getIntervalName());
				((UcscRefSeqGeneIntervalTreeNode)z).setStrand(((UcscRefSeqGeneIntervalTreeNode)y).getStrand());		
			}
			
			
				
			
			//Burcak commented only the data has been changed
			//Left, Right and Parent does not change.
			//height does not change
//			z.setLeft(y.getLeft());
//			z.setRight(y.getRight());
//			z.setParent(y.getParent());	
//			z.setColor(y.getColor());
			
			
			//set the max of node z
			updateMaxAttribute(z);
			
			
			if (z instanceof OtherIntervalTreeNode){
				//set the min of node z
				updateMinAttribute(z);
			}
			
			
		}
		
		//If y is black, we could have violations of red-black properties:
		//1. Every node is either black or red. Ok.
		//2. The root is black. If y is the root and x is red, then the  root has become red. 
		//3. Every leaf is black. Ok.
		//4. If a node is red, then both of its children are black. (Hence no two reds in a row on a simple path from the root to a leaf.) Violation if p[y] and x are both red.
		//5. For each node, all paths from the node to descendant leaves contain the same number of black nodes. Any path containing y now has 1 fewer black node.
		//5. 5.1 correct by giving x an "extra black".
		//5. 5.2 Add 1 to count of black nodes on paths containing x.
		//5. 5.3 Now property 5 is Ok, but property 1 is not.
		//5. 5.4 x is either doubly black (if color[x] = BLACK) or red&black (if color[x] = RED) .
		//5. 5.4 The attribute color [x] is still either RED or BLACK. No new values for color attribute.
		//5. 5.4 In other words, the extra blackness on a node is by virtue of x pointing to the node.		
		if (y.getColor()==Commons.BLACK){
			intervalTreeDeleteFixUp(tree,x);
		}
		
		
		
		//The node y is returned so that calling procedure can recyle it via the free list.
		return y;
		
	}
	
	public void  intervalTreeInsert(IntervalTree tree, IntervalTreeNode z){
		//Increment the number of nodes by one
		tree.setNumberofNodes(tree.getNumberofNodes()+1);
				
		//Increase the number of non overlapping bases by size of the inserted node z
		tree.setNumberofNonOverlappingBases(tree.getNumberofNonOverlappingBases()+z.getNumberofBases());
		
		
		//Set y to SENTINEL node
		IntervalTreeNode y = new IntervalTreeNode();
		//Set x to the root
		IntervalTreeNode x = tree.getRoot();
		
//		This while sets the parent for the new inserted node z
		while(!(NodeName.SENTINEL.equals(x.getNodeName()))){
			y = x;
			if (z.getLow()< x.getLow())
				x = x.getLeft();
			else 
				x = x.getRight();
		}
		
		z.setParent(y);
		
//		This part sets whether the new inserted node is the left or right child of parent
		if (NodeName.SENTINEL.equals(y.getNodeName())){//enters for the first insert
			tree.setRoot(z);
		} else{
			if (z.getLow()<y.getLow()){
				y.setLeft(z);
			}else{
				y.setRight(z);
			}			
		}
		
//		sets the left right color attributes of the new inserted node
		z.setLeft(new IntervalTreeNode());
		z.setRight(new IntervalTreeNode());
		z.setColor(Commons.RED);
		z.setMax(z.getHigh());
		
		if (z instanceof OtherIntervalTreeNode){
			((OtherIntervalTreeNode)z).setMin(z.getLow());
			((OtherIntervalTreeNode)z).setHeight(((OtherIntervalTreeNode)(z.getParent())).getHeight()+1);
			

		}
		
		
		updateMaxAttribute(z.getParent());
		
		if (z instanceof OtherIntervalTreeNode){
			updateMinAttribute(z.getParent());
		}
		
		intervalTreeInsertFixUp(tree,z);
		
		
		
	}
	
	
	public void intervalTreeInfixTraversal(IntervalTreeNode node){
		
		if (node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getLeft());
		
		if (node.getNodeName().isNotSentinel()){
			GlanetRunner.appendLog(node.getLow() + "\t"+ node.getHigh() + "\t" + node.getMax() + "\t" + node.getColor());
		}
		
		if (node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getRight());
				
	}
	
	
	public void intervalTreeInfixTraversal(IntervalTreeNode node,BufferedWriter bufferedWriter){
		
		if (node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getLeft(),bufferedWriter);
		
		try {
			
			if (node.getNodeName().isNotSentinel()){				
				bufferedWriter.write(node.getLow() + "\t"+ node.getHigh() + "\t" + node.getMax()+ System.getProperty("line.separator"));
				bufferedWriter.flush();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getRight(),bufferedWriter);
		
				
	}
	
	public void intervalTreeInfixTraversal(IntervalTreeNode node,BufferedWriter bufferedWriter, String type){
		
		DnaseIntervalTreeNode castedNodeDnase = null;
		TforHistoneIntervalTreeNode castedNodeTforHistone = null;
		UcscRefSeqGeneIntervalTreeNode castedNodeUcscRefSeqGene = null;
		
		
		if (node.getLeft().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getLeft(),bufferedWriter,type);
		
		try {
			if (node.getNodeName().isNotSentinel()){
				
				if (Commons.DNASE.equals(type)){
					castedNodeDnase = (DnaseIntervalTreeNode) node;
					bufferedWriter.write(castedNodeDnase.getChromName().convertEnumtoString()+ "\t" + castedNodeDnase.getLow()+"\t"+ castedNodeDnase.getHigh() +"\t" + castedNodeDnase.getCellLineName()+"\t" + castedNodeDnase.getFileName()+System.getProperty("line.separator"));																
				}else if (Commons.TF.equals(type)){
					castedNodeTforHistone = (TforHistoneIntervalTreeNode) node;					
					bufferedWriter.write(castedNodeTforHistone.getChromName().convertEnumtoString()+ "\t" + castedNodeTforHistone.getLow()+"\t"+ castedNodeTforHistone.getHigh()+"\t" + castedNodeTforHistone.getTfbsorHistoneName()+"\t" + castedNodeTforHistone.getCellLineName()+"\t" + castedNodeTforHistone.getFileName()+System.getProperty("line.separator"));												
				}else if (Commons.HISTONE.equals(type)){
					castedNodeTforHistone = (TforHistoneIntervalTreeNode) node;										
					bufferedWriter.write(castedNodeTforHistone.getChromName().convertEnumtoString()+ "\t" + castedNodeTforHistone.getLow()+"\t"+ castedNodeTforHistone.getHigh()+"\t" + castedNodeTforHistone.getTfbsorHistoneName()+"\t" + castedNodeTforHistone.getCellLineName()+"\t" + castedNodeTforHistone.getFileName()+System.getProperty("line.separator"));												
				}else if(Commons.HG19_REFSEQ_GENE.equals(type)){
					castedNodeUcscRefSeqGene = (UcscRefSeqGeneIntervalTreeNode) node;
					bufferedWriter.write(castedNodeUcscRefSeqGene.getChromName().convertEnumtoString()+ "\t" + castedNodeUcscRefSeqGene.getLow()+"\t"+ castedNodeUcscRefSeqGene.getHigh()+"\t" + castedNodeUcscRefSeqGene.getRefSeqGeneName()+ "\t" + castedNodeUcscRefSeqGene.getGeneEntrezId()+ "\t" + castedNodeUcscRefSeqGene.getIntervalName().convertEnumtoString()+ "\t" + castedNodeUcscRefSeqGene.getIntervalNumber() + "\t" + castedNodeUcscRefSeqGene.getStrand() + "\t" + castedNodeUcscRefSeqGene.getGeneHugoSymbol()+ System.getProperty("line.separator"));
//					bufferedWriter.write(refSeqGeneIntervalList.get(j).getChromName()+ "\t" +refSeqGeneIntervalList.get(j).getIntervalStart()+"\t"+ refSeqGeneIntervalList.get(j).getIntervalEnd()+"\t" +refSeqGeneIntervalList.get(j).getRefSeqGeneName()+ "\t" + refSeqGeneIntervalList.get(j).getGeneId()+ "\t" +refSeqGeneIntervalList.get(j).getIntervalName()+ "\t" + refSeqGeneIntervalList.get(j).getStrand() + "\t" + refSeqGeneIntervalList.get(j).getAlternateGeneName()+ System.getProperty("line.separator"));					
				}else if (Commons.PROCESS_INPUT_DATA_REMOVE_OVERLAPS.equals(type)){
					bufferedWriter.write(node.getChromName().convertEnumtoString()+ "\t" + node.getLow()+"\t"+ node.getHigh() +  System.getProperty("line.separator"));
				}
				bufferedWriter.flush();
			}
				
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (node.getRight().getNodeName().isNotSentinel())
			intervalTreeInfixTraversal(node.getRight(),bufferedWriter,type);
		
				
	}
	
	public static boolean overlaps(int low_x, int high_x, int low_y, int high_y ){
		if (( low_x <= high_y) && (low_y <= high_x))
			return true;
		else 
			return false;
	}
	
	//overlap definition: number of overlapping bases necessary for overlap
	public static boolean overlaps(int low_x, int high_x, int low_y, int high_y, int numberofOverlappingBases ){
		if (( low_x <= high_y) && (low_y <= high_x)){
			
			if ((Math.min(high_x,high_y)-Math.max(low_x,low_y)+1) >= numberofOverlappingBases){
				return true;				
			}else
				return false;
		}
		else 
			return false;
	}
	
	

	//For debug purposes
	public static void findGivenNode(IntervalTreeNode node, int low, int high, List<IntervalTreeNode> foundNodes){
		//exact node
		if (node.getLow() == low && node.getHigh() == high){
			//Found
			foundNodes.add(node);
		}
		
		//partial overlap possibility
		if(node.getLeft().getNodeName().isNotSentinel() && ((OtherIntervalTreeNode)(node.getLeft())).getMin()<= high && low <= node.getLeft().getMax()) {
			findGivenNode(node.getLeft(), low, high,foundNodes);
		}
		
		//partial overlap possibility
		if(node.getRight().getNodeName().isNotSentinel() && ((OtherIntervalTreeNode)(node.getRight())).getMin()<= high && low <= node.getRight().getMax()) {
			findGivenNode(node.getRight(), low, high,foundNodes);
		}
	 
		
	}
	
	
	//finds the left most node in the previous non empty node list
	public static IntervalTreeNode findLeftMostNodefromPreviousQuery(List<IntervalTreeNode> previousNonEmptyOverlappingNodeList){
			 IntervalTreeNode leftMostNode = previousNonEmptyOverlappingNodeList.get(0);
			  
			 for(IntervalTreeNode node: previousNonEmptyOverlappingNodeList){
				
				 if (node.getLow()< leftMostNode.getLow()){
					 leftMostNode = node;
				 }
			 }	 
			 
			return leftMostNode;
		 
	}
	
	//Go up in the interval tree for the new query 
	public static IntervalTreeNode findMostGeneralSearchStaringNodeforNewQuery(Interval interval, IntervalTreeNode leftMostNode){
			//For OCD GWAS taking right most node did not work
			//Test for POSITIVE CONTROL DATA
			
			IntervalTreeNode parent = leftMostNode;
			IntervalTreeNode previousParent;
			
			previousParent = parent;
			parent = parent.getParent();
			
			//Exit the loop when parent.getLow()>interval.getHigh()
			while( 	(parent.getNodeName().isNotSentinel()) &&
					(parent.getLow()<=interval.getHigh()) )
			    {
				previousParent = parent;
				parent = parent.getParent();
				
			}
			
			return previousParent;
		}
	
	
	//Go up in the interval tree for the new query 
	public static IntervalTreeNode findMostGeneralSearchStaringNodeforNewQuery(Interval interval, IntervalTreeNode leftMostNode, IntervalTreeNode rightMostNode){
		//For OCD GWAS taking right most node did not work
		//Test for POSITIVE CONTROL DATA
		
		IntervalTreeNode parent = leftMostNode;
		IntervalTreeNode previousParent;
		
		previousParent = parent;
		parent = parent.getParent();
		
		//Exit the loop when parent.getLow()>interval.getHigh()
		while( 	(parent.getNodeName().isNotSentinel()) &&
				(parent.getLow()<=interval.getHigh()) )
		    {
			previousParent = parent;
			parent = parent.getParent();
			
		}
		
		return previousParent;
	}
	
	//Go down in the interval tree for the new query
	public static IntervalTreeNode findMostSpecificSearchStaringNodeforNewQuery(Interval interval,IntervalTreeNode node){
		
		
		
		//if there is possibility of overlap between interval and node's children
		//or overlaps with the node
		if		(((node.getLeft().getNodeName().isNotSentinel()) && (node.getRight().getNodeName().isNotSentinel()) &&
				((((OtherIntervalTreeNode)(node.getLeft())).getMin() <= interval.getHigh())  && (interval.getLow()<= node.getLeft().getMax())) &&
				((((OtherIntervalTreeNode)(node.getRight())).getMin() <= interval.getHigh())  && (interval.getLow()<= node.getRight().getMax())))
				 || 
				 (node.getLow()<= interval.getHigh() && interval.getLow() <= node.getHigh()))
		{
				return node;
		}else if(node.getLeft().getNodeName().isNotSentinel() &&
				((((OtherIntervalTreeNode)(node.getLeft())).getMin() <= interval.getHigh())  && (interval.getLow()<= node.getLeft().getMax()))){
				return findMostSpecificSearchStaringNodeforNewQuery(interval,node.getLeft());
		}else if(node.getRight().getNodeName().isNotSentinel() &&
				((((OtherIntervalTreeNode)(node.getRight())).getMin() <= interval.getHigh())  && (interval.getLow()<= node.getRight().getMax()))){
				return findMostSpecificSearchStaringNodeforNewQuery(interval,node.getRight());
		}
		//no way out
		//does not overlap with the node and its children
		else 
			return new IntervalTreeNode();
		
	}
	
	public static void writeRouteFromRoottoThisNode(IntervalTreeNode node){
		IntervalTreeNode parent;
		
		if (node.getNodeName().isNotSentinel()){
			parent = node.getParent();
			
			if (parent.getNodeName().isNotSentinel()){
				if (parent.getLeft()==node){
					writeRouteFromRoottoThisNode(node.getParent());
					GlanetRunner.appendLog("Left");
				}else if (parent.getRight()==node){
					writeRouteFromRoottoThisNode(node.getParent());
					GlanetRunner.appendLog("Right");
				}
			}
		}
		
	}
	
	public static void printRouteFromRoottoThisNode(IntervalTreeNode root, int low, int high, List<IntervalTreeNode> foundNodes){
		
		OtherIntervalTreeNode castedNode = null;
		
		
		if (root.getNodeName().isNotSentinel()){
			findGivenNode(root,low, high,foundNodes);	
		}
		
		for(IntervalTreeNode foundNode: foundNodes){
			
			if (foundNode instanceof OtherIntervalTreeNode){
				castedNode = (OtherIntervalTreeNode) foundNode;
			}
			
			GlanetRunner.appendLog("Height: "+ castedNode.height);
			writeRouteFromRoottoThisNode(foundNode);
		}
		
	}
	
	//Normal
	public void findAllOverlappingIntervals(IntervalTreeNode node, Interval interval){
		if (node.getNodeName().isNotSentinel()){
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
					GlanetRunner.appendLog("overlap" + node.getLow() + "\t" + node.getHigh());
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingIntervals(node.getLeft(),interval);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingIntervals(node.getRight(),interval);	
				
			}
		}		
	}
	
	
	//Normal: for calculation of non overlapping base pairs in whole genome using interval tree
	public void findAllOverlappingIntervals(List<IntervalTreeNode> overlappedNodeList, IntervalTreeNode root, IntervalTreeNode newNode){
		
		if (root.getNodeName().isNotSentinel() && newNode.getNodeName().isNotSentinel()){
			if (overlaps(root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh())){
				overlappedNodeList.add(root);
//				GlanetRunner.appendLog("overlap " + root.getLow() + "\t" + root.getHigh());
			}
			
						
			if((root.getLeft().getNodeName().isNotSentinel()) && (newNode.getLow()<=root.getLeft().getMax())){
				findAllOverlappingIntervals(overlappedNodeList,root.getLeft(),newNode);	
			}
			
			if((root.getRight().getNodeName().isNotSentinel()) && (newNode.getLow()<=root.getRight().getMax()) && (root.getLow()<=newNode.getHigh())){
				findAllOverlappingIntervals(overlappedNodeList,root.getRight(),newNode);	
				
			}
		}
		
		
	}
	
		//Normal
		//First argument is the root of the interval tree
	    //Second argument is the node that is looked for whether it overlaps with any node in the interval tree
		public IntervalTreeNode findFirstOverlappingIntervals(IntervalTreeNode root, IntervalTreeNode newNode){
			
			IntervalTreeNode overlappedNode=null;
			
			if (root.getNodeName().isNotSentinel()){
				if (overlaps(root.getLow(), root.getHigh(), newNode.getLow(), newNode.getHigh())){
//						GlanetRunner.appendLog(root.getLow() + "\t" + root.getHigh());						
						return root;
						
				}else{
					if((root.getLeft().getNodeName().isNotSentinel()) && (newNode.getLow()<=root.getLeft().getMax())){
						overlappedNode = findFirstOverlappingIntervals(root.getLeft(),newNode);
						if (overlappedNode!=null)
							return overlappedNode;
					}
					
					if((root.getRight().getNodeName().isNotSentinel()) && (newNode.getLow()<=root.getRight().getMax()) && (root.getLow()<=newNode.getHigh())){
						overlappedNode = findFirstOverlappingIntervals(root.getRight(),newNode);	
						if (overlappedNode!=null)
							return overlappedNode;
					}
				}
								
			}
				
			return null;
			
			
			
	}
			
			
	
	//Search1
	public void findAllOverlappingHistoneIntervals(IntervalTreeNode node, Interval interval, BufferedWriter bufferedWriter, List<IntervalTreeNode> overlappingNodeList){
		
		TforHistoneIntervalTreeNode castedNode = null;
		
		if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
			
			if (node instanceof TforHistoneIntervalTreeNode){
				castedNode = (TforHistoneIntervalTreeNode) node;
			}
										
			overlappingNodeList.add(node);
			
			try {
				bufferedWriter.write("histone" + "\t" + castedNode.getChromName().toString()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName().toString()+ "\t" + castedNode.getCellLineName().toString() + "\t" + castedNode.getFileName().toString() +System.getProperty("line.separator"));
				bufferedWriter.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
		}
		
		
		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
			findAllOverlappingHistoneIntervals(node.getLeft(),interval,bufferedWriter,overlappingNodeList);	
		}
		
		if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
			findAllOverlappingHistoneIntervals(node.getRight(),interval,bufferedWriter,overlappingNodeList);	
			
		}
				
	}
	
	//@todo
	//with Numbers
	//Empirical P Value Calculation
	//with IO
	public void findAllOverlappingHistoneIntervalsWithNumbers(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TLongObjectMap<BufferedWriter> bufferedWriterHashMap, TLongIntMap permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		long permutationNumberHistoneNumberCellLineNumber;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
					castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
				}
					
				
				try {			
					permutationNumberHistoneNumberCellLineNumber = generateCombinedNumber(permutationNumber, castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short) 0);
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberHistoneNumberCellLineNumber);
					
					if (bufferedWriter==null){
					
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_HISTONE + Commons.PERMUTATION + permutationNumber + System.getProperty("file.separator")+ permutationNumberHistoneNumberCellLineNumber + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "histone" + "\t" + "ChromName"+ "\t"  + "Low" + "\t" + "High" + "\t" + "HistoneNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" +System.getProperty("line.separator"));
						bufferedWriter.flush();
										

						bufferedWriterHashMap.put(permutationNumberHistoneNumberCellLineNumber,bufferedWriter);
					}
					
					if(!(permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.containsKey(permutationNumberHistoneNumberCellLineNumber))){
						permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.put(permutationNumberHistoneNumberCellLineNumber, 1);
					}
										
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "histone" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber()+ "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() +System.getProperty("line.separator"));
					bufferedWriter.flush();
									

				} catch (IOException e) {
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervalsWithNumbers(outputFolder,permutationNumber,node.getLeft(),interval,chromName,bufferedWriterHashMap,permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervalsWithNumbers(outputFolder,permutationNumber,node.getRight(),interval,chromName,bufferedWriterHashMap,permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap,overlapDefinition);	
				
			}
				
	}
	//with Numbers
	//@todo

	
	//Empirical P Value Calculation
	//with IO
	public void findAllOverlappingHistoneIntervals(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,Integer> permutationNumberHistoneNameCellLineName2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory = null;
		
		String permutationNumberHistoneNameaCellLineName;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof TforHistoneIntervalTreeNode){
					castedNode = (TforHistoneIntervalTreeNode) node;
				}
					
				
				try {			
					permutationNumberHistoneNameaCellLineName =  Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberHistoneNameaCellLineName);
					
					if (bufferedWriter==null){
					
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_HISTONE + Commons.PERMUTATION + permutationNumber + System.getProperty("file.separator")+ permutationNumberHistoneNameaCellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(permutationNumberHistoneNameaCellLineName,bufferedWriter);
					}
					
					if(permutationNumberHistoneNameCellLineName2ZeroorOneMap.get(permutationNumberHistoneNameaCellLineName)==null){
						permutationNumberHistoneNameCellLineName2ZeroorOneMap.put(permutationNumberHistoneNameaCellLineName, 1);
					}
										
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "histone" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName()+ "\t" + String.valueOf(castedNode.getCellLineName()) + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();
									

				} catch (IOException e) {
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervals(outputFolder,permutationNumber,node.getLeft(),interval,chromName,bufferedWriterHashMap,permutationNumberHistoneNameCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervals(outputFolder,permutationNumber,node.getRight(),interval,chromName,bufferedWriterHashMap,permutationNumberHistoneNameCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
				
	}

	//@todo
	//Empirical P Value Calculation
	//without IO
	//without overlappedNodeList
	//with Numbers
	public void findAllOverlappingHistoneIntervalsWithNumbers(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TLongIntMap permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap, int overlapDefinition){
		long permutationNumberHistoneNumberCellLineNumber;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
				
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
					castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
				}
										
				permutationNumberHistoneNumberCellLineNumber = generateCombinedNumber(permutationNumber, castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short)0);
				
				
				if(!(permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.containsKey(permutationNumberHistoneNumberCellLineNumber))){
					permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap.put(permutationNumberHistoneNumberCellLineNumber, 1);
				}																				
			}
						
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervalsWithNumbers(permutationNumber,node.getLeft(),interval,chromName,permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervalsWithNumbers(permutationNumber,node.getRight(),interval,chromName,permutationNumberHistoneNumberCellLineNumber2ZeroorOneMap,overlapDefinition);	
				
			}
			
	}
	//@todo
	

	//Empirical P Value Calculation
	//without IO
	//without overlappedNodeList
	public void findAllOverlappingHistoneIntervals(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,Integer> permutationNumberHistoneNameCellLineName2ZeroorOneMap, int overlapDefinition){
		String permutationNumberHistoneNameaCellLineName;
		
		TforHistoneIntervalTreeNode castedNode = null;
				
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof TforHistoneIntervalTreeNode){
					castedNode = (TforHistoneIntervalTreeNode) node;
				}
											
				permutationNumberHistoneNameaCellLineName =  Commons.PERMUTATION + permutationNumber + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
											
				if(permutationNumberHistoneNameCellLineName2ZeroorOneMap.get(permutationNumberHistoneNameaCellLineName)==null){
					permutationNumberHistoneNameCellLineName2ZeroorOneMap.put(permutationNumberHistoneNameaCellLineName, 1);
				}																				
			}
						
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervals(permutationNumber,node.getLeft(),interval,chromName,permutationNumberHistoneNameCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervals(permutationNumber,node.getRight(),interval,chromName,permutationNumberHistoneNameCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
			
	}
	
	
	//Empirical P Value Calculation
	//without IO
	//with overlappedNodeList
	public void findAllOverlappingHistoneIntervals(int repeatNumber,int permutationNumber,int NUMBER_OF_PERMUTATIONS,IntervalTreeNode node, Interval interval, String chromName, Map<String,Integer> permutationNumberHistoneNameCellLineName2ZeroorOneMap,List<IntervalTreeNode> overlappingNodeList){
		String permutationNumberHistoneNameaCellLineName;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
						
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				
					if (node instanceof TforHistoneIntervalTreeNode){
						castedNode = (TforHistoneIntervalTreeNode) node;
					}
				
					overlappingNodeList.add(node);
					
					permutationNumberHistoneNameaCellLineName =  Commons.PERMUTATION + (((repeatNumber-1)*NUMBER_OF_PERMUTATIONS) + permutationNumber) + "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
												
					if(permutationNumberHistoneNameCellLineName2ZeroorOneMap.get(permutationNumberHistoneNameaCellLineName)==null){
						permutationNumberHistoneNameCellLineName2ZeroorOneMap.put(permutationNumberHistoneNameaCellLineName, 1);
					}																				
			}
						
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervals(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getLeft(),interval,chromName,permutationNumberHistoneNameCellLineName2ZeroorOneMap,overlappingNodeList);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervals(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getRight(),interval,chromName,permutationNumberHistoneNameCellLineName2ZeroorOneMap,overlappingNodeList);	
				
			}
			
	}
	
	//@todo for Annotation with Numbers starts
	//Search2 For finding the number of each histoneNameandCellLineName: k for the given search input size: n
	//For each search input line, each histoneNameandCellLineName will have value 1 or 0
	//These 1 or 0's will be accumulated in histoneNameandCellLineName2KMap		
	public void findAllOverlappingHistoneIntervalsWithNumbers(String outputFolder, IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntObjectMap<BufferedWriter> bufferedWriterHashMap, TIntShortMap histoneNumberCellLineNumber2ZeroorOneMap,int overlapDefinition,TShortObjectMap<String> histoneNumber2HistoneNameMap,TShortObjectMap<String> cellLineNumber2CellLineNameMap,TShortObjectMap<String> fileNumber2FileNameMap){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		
		String histoneName;
		String cellLineName;
		String fileName;
				
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
		
		if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
			castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
		}
									
	
		int histoneNumberCellLineNumber = generateCombinedNumber(castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short)0);
		

			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {	
					
					histoneName = histoneNumber2HistoneNameMap.get(castedNode.getTforHistoneNumber());
					cellLineName = cellLineNumber2CellLineNameMap.get(castedNode.getCellLineNumber());
					fileName = fileNumber2FileNameMap.get(castedNode.getFileNumber());
				
					
					bufferedWriter = bufferedWriterHashMap.get(histoneNumberCellLineNumber);
					
					if (bufferedWriter==null){						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HISTONE +"_" + histoneName + "_" + cellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(histoneNumberCellLineNumber,bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t"  + "interval low" + "\t" + "interval high" + "\t" + "histone node chrom name" + "\t"  + "node Low" + "\t" + "node high" + "\t" + "node HistoneName" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));
						
					}
					
					if(!histoneNumberCellLineNumber2ZeroorOneMap.containsKey(histoneNumberCellLineNumber)){
						histoneNumberCellLineNumber2ZeroorOneMap.put(histoneNumberCellLineNumber, (short)1);
					}
										
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + histoneName + "\t" + cellLineName + "\t" + fileName +System.getProperty("line.separator"));
					bufferedWriter.flush();
									

				} catch (IOException e) {
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName,bufferedWriterHashMap,histoneNumberCellLineNumber2ZeroorOneMap,overlapDefinition,histoneNumber2HistoneNameMap,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName,bufferedWriterHashMap,histoneNumberCellLineNumber2ZeroorOneMap,overlapDefinition,histoneNumber2HistoneNameMap,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
				
			}
				
	}
	//@todo for Annotation with Numbers ends
	
	
	//Search2 For finding the number of each histoneNameandCellLineName: k for the given search input size: n
	//For each search input line, each histoneNameandCellLineName will have value 1 or 0
	//These 1 or 0's will be accumulated in histoneNameandCellLineName2KMap		
	public void findAllOverlappingHistoneIntervals(String outputFolder, IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, List<String> histoneNameList, Map<String,Integer> histoneNameandCellLineName2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
		if (node instanceof TforHistoneIntervalTreeNode){
			castedNode = (TforHistoneIntervalTreeNode) node;
		}
									
		
		String histoneNameandCellLine = castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
		

			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition) && histoneNameList.contains(castedNode.getTfbsorHistoneName())){
				try {			
					
					bufferedWriter = bufferedWriterHashMap.get(histoneNameandCellLine);
					
					if (bufferedWriter==null){						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HISTONE +"_" + histoneNameandCellLine + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(histoneNameandCellLine,bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t"  + "interval low" + "\t" + "interval high" + "\t" + "histone node chrom name" + "\t"  + "node Low" + "\t" + "node high" + "\t" + "node Histone Name" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));
						
					}
					
					if(histoneNameandCellLineName2ZeroorOneMap.get(histoneNameandCellLine)==null){
						histoneNameandCellLineName2ZeroorOneMap.put(histoneNameandCellLine, 1);
					}
										
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName()+ "\t" + String.valueOf(castedNode.getCellLineName()) + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();
									

				} catch (IOException e) {
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingHistoneIntervals(outputFolder,node.getLeft(),interval,chromName,bufferedWriterHashMap, histoneNameList,histoneNameandCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingHistoneIntervals(outputFolder,node.getRight(),interval,chromName,bufferedWriterHashMap,histoneNameList,histoneNameandCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
				
	}
	
	//@todo
	//with Numbers
	//Empirical P Value Calculation
	//with IO
	//with OverlapNodeList
	public void findAllOverlappingTfbsIntervalsWithNumbers(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TLongObjectMap<BufferedWriter> bufferedWriterHashMap, TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap,List<PermutationNumberTfNumberCellLineNumberOverlap> 	permutationNumberTfNumberCellLineNumberOverlapList,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		long permutationNumberTfNumberCellLineNumber;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
						castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
					}				
					
					permutationNumberTfNumberCellLineNumber = generateCombinedNumber(permutationNumber, castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short) 0);
					
					permutationNumberTfNumberCellLineNumberOverlapList.add(new PermutationNumberTfNumberCellLineNumberOverlap(permutationNumberTfNumberCellLineNumber,castedNode.getLow(),castedNode.getHigh()));
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberTfNumberCellLineNumber);
					
					if (bufferedWriter==null){	
												
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber+ System.getProperty("file.separator") + permutationNumberTfNumberCellLineNumber + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" +"\t" + "tfbs" + "\t" + "ChromName" + "\t"  + "Low" + "\t" + "High" + "\t" + "TfNumber" + "\t" + "CellLineNumber" + "\t" + "FileNumber" +System.getProperty("line.separator"));
						bufferedWriter.flush();
						
						bufferedWriterHashMap.put(permutationNumberTfNumberCellLineNumber,bufferedWriter);
					}
					
					if(!(permutationNumberTfNumberCellLineNumber2ZeroorOneMap.containsKey(permutationNumberTfNumberCellLineNumber))){
						permutationNumberTfNumberCellLineNumber2ZeroorOneMap.put(permutationNumberTfNumberCellLineNumber, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() +"\t" + "tfbs" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber()+ "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,permutationNumber,node.getLeft(),interval,chromName, bufferedWriterHashMap,permutationNumberTfNumberCellLineNumber2ZeroorOneMap,permutationNumberTfNumberCellLineNumberOverlapList,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,permutationNumber,node.getRight(),interval,chromName, bufferedWriterHashMap,permutationNumberTfNumberCellLineNumber2ZeroorOneMap,permutationNumberTfNumberCellLineNumberOverlapList,overlapDefinition);	
				
			}		
	}	
	//with Numbers
	//@todo
	
	
	//Empirical P Value Calculation
	//with IO
	//with OverlapNodeList
	public void findAllOverlappingTfbsIntervals(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,List<PermutationNumberTfNameCellLineNameOverlap> 	permutationNumberTfNameCellLineNameOverlapList,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String permutationNumberTfNameCellLineName;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					if (node instanceof TforHistoneIntervalTreeNode){
						castedNode = (TforHistoneIntervalTreeNode) node;
					}
						
					
					permutationNumberTfNameCellLineName = Commons.PERMUTATION + permutationNumber+ "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
					
					permutationNumberTfNameCellLineNameOverlapList.add(new PermutationNumberTfNameCellLineNameOverlap(permutationNumberTfNameCellLineName,castedNode.getLow(),castedNode.getHigh()));
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberTfNameCellLineName);
					
					if (bufferedWriter==null){	
												
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber+ System.getProperty("file.separator") + permutationNumberTfNameCellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(permutationNumberTfNameCellLineName,bufferedWriter);
					}
					
					if(permutationNumberTfbsNameCellLineName2ZeroorOneMap.get(permutationNumberTfNameCellLineName)==null){
						permutationNumberTfbsNameCellLineName2ZeroorOneMap.put(permutationNumberTfNameCellLineName, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() +"\t" + "tfbs" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName()+ "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingTfbsIntervals(outputFolder,permutationNumber,node.getLeft(),interval,chromName, bufferedWriterHashMap,permutationNumberTfbsNameCellLineName2ZeroorOneMap,permutationNumberTfNameCellLineNameOverlapList,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervals(outputFolder,permutationNumber,node.getRight(),interval,chromName, bufferedWriterHashMap,permutationNumberTfbsNameCellLineName2ZeroorOneMap,permutationNumberTfNameCellLineNameOverlapList,overlapDefinition);	
				
			}		
	}
		
	
	//@todo
	//with Numbers
	//Empirical P Value Calculation
	//with IO
	public void findAllOverlappingTfbsIntervalsWithNumbers(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TLongObjectMap<BufferedWriter> bufferedWriterHashMap, TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
				
		long permutationNumberTfNumberCellLineNumber;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
		
	
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
						
					if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
						castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
					}
					
					permutationNumberTfNumberCellLineNumber = generateCombinedNumber(permutationNumber, castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short) 0);
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberTfNumberCellLineNumber);
					
					if (bufferedWriter==null){	
											
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber+ System.getProperty("file.separator") + permutationNumberTfNumberCellLineNumber + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" +"\t" + "tfbs" + "\t" + "ChromName"+ "\t"  + "Low" + "\t" + "High" + "\t" + "TfNumber"+ "\t" + "CellLineNumber" + "\t" + "FileNumber" +System.getProperty("line.separator"));
						bufferedWriter.flush();
					
						bufferedWriterHashMap.put(permutationNumberTfNumberCellLineNumber,bufferedWriter);
					}
					
					if(!(permutationNumberTfNumberCellLineNumber2ZeroorOneMap.containsKey(permutationNumberTfNumberCellLineNumber))){
						permutationNumberTfNumberCellLineNumber2ZeroorOneMap.put(permutationNumberTfNumberCellLineNumber, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() +"\t" + "tfbs" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTforHistoneNumber()+ "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,permutationNumber,node.getLeft(),interval,chromName, bufferedWriterHashMap,permutationNumberTfNumberCellLineNumber2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,permutationNumber,node.getRight(),interval,chromName, bufferedWriterHashMap,permutationNumberTfNumberCellLineNumber2ZeroorOneMap,overlapDefinition);	
				
			}
		
	}
	//with Numbers
	//@todo
	
	//Empirical P Value Calculation
	//with IO
	public void findAllOverlappingTfbsIntervals(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory;
				
		String permutationNumberTfbsNameCellLineName;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
		

		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
						
					if (node instanceof TforHistoneIntervalTreeNode){
						castedNode = (TforHistoneIntervalTreeNode) node;
					}
					
					permutationNumberTfbsNameCellLineName = Commons.PERMUTATION + permutationNumber+ "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberTfbsNameCellLineName);
					
					if (bufferedWriter==null){	
											
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_TFBS + Commons.PERMUTATION + permutationNumber+ System.getProperty("file.separator") + permutationNumberTfbsNameCellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(permutationNumberTfbsNameCellLineName,bufferedWriter);
					}
					
					if(permutationNumberTfbsNameCellLineName2ZeroorOneMap.get(permutationNumberTfbsNameCellLineName)==null){
						permutationNumberTfbsNameCellLineName2ZeroorOneMap.put(permutationNumberTfbsNameCellLineName, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() +"\t" + "tfbs" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName()+ "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingTfbsIntervals(outputFolder,permutationNumber,node.getLeft(),interval,chromName, bufferedWriterHashMap,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervals(outputFolder,permutationNumber,node.getRight(),interval,chromName, bufferedWriterHashMap,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
		
	}
	
	
	
	
	//@todo
	//Empirical P Value Calculation
	//without IO
	//without overlappedNodeList
	//with numbers
	public void findAllOverlappingTfbsIntervalsWithNumbers(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TLongIntMap permutationNumberTfbsNameCellLineName2ZeroorOneMap,int overlapDefinition){
		long permutationNumberTfNumberCellLineNumber;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
							
		if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
			
			if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
				castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
			}
							
			permutationNumberTfNumberCellLineNumber = generateCombinedNumber(permutationNumber,castedNode.getTforHistoneNumber(),castedNode.getCellLineNumber(),(short)0);
			
			if(!(permutationNumberTfbsNameCellLineName2ZeroorOneMap.containsKey(permutationNumberTfNumberCellLineNumber))){
				permutationNumberTfbsNameCellLineName2ZeroorOneMap.put(permutationNumberTfNumberCellLineNumber, 1);
			}
		}
				
		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
			findAllOverlappingTfbsIntervalsWithNumbers(permutationNumber,node.getLeft(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlapDefinition);	
		}
		
		if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
			findAllOverlappingTfbsIntervalsWithNumbers(permutationNumber,node.getRight(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlapDefinition);				
		}
		
	}
	//@todo
	

    	//Empirical P Value Calculation
		//without IO
		//without overlappedNodeList
		public void findAllOverlappingTfbsIntervals(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,int overlapDefinition){
			String permutationNumberTfbsNameCellLineName;
			
			TforHistoneIntervalTreeNode castedNode = null;
								
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof TforHistoneIntervalTreeNode){
					castedNode = (TforHistoneIntervalTreeNode) node;
				}
	
				permutationNumberTfbsNameCellLineName = Commons.PERMUTATION + permutationNumber+ "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();			
					
				if(permutationNumberTfbsNameCellLineName2ZeroorOneMap.get(permutationNumberTfbsNameCellLineName)==null){
					permutationNumberTfbsNameCellLineName2ZeroorOneMap.put(permutationNumberTfbsNameCellLineName, 1);
				}
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingTfbsIntervals(permutationNumber,node.getLeft(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervals(permutationNumber,node.getRight(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
			
		}

//		//Empirical P Value Calculation
//		//without IO
//		//with overlappedNodeList
//		public void findAllOverlappingTfbsIntervals(int repeatNumber,int permutationNumber,int NUMBER_OF_PERMUTATIONS,IntervalTreeNode node, Interval interval, String chromName, Map<String,Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,List<IntervalTreeNode> overlappingNodeList){
//			String permutationNumberTfbsNameCellLineName;
//			
//				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
//					
//					overlappingNodeList.add(node);
//						
//					permutationNumberTfbsNameCellLineName = Commons.PERMUTATION + (((repeatNumber-1)*NUMBER_OF_PERMUTATIONS) + permutationNumber)+ "_" + node.getTfbsorHistoneName() + "_" + node.getCellLineName();			
//						
//					if(permutationNumberTfbsNameCellLineName2ZeroorOneMap.get(permutationNumberTfbsNameCellLineName)==null){
//						permutationNumberTfbsNameCellLineName2ZeroorOneMap.put(permutationNumberTfbsNameCellLineName, 1);
//					}
//				}
//				
//				
//				if((node.getLeft().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) && (node.getLeft().getMin()<= interval.getHigh())){
//					findAllOverlappingTfbsIntervals(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getLeft(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlappingNodeList);	
//				}
//				
//				if((node.getRight().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getRight().getMin()<=interval.getHigh())){
//					findAllOverlappingTfbsIntervals(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getRight(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,overlappingNodeList);	
//					
//				}
//				
//		}

		//@todo
		//with Numbers starts
		//Empirical P Value Calculation
		//without IO
		//with permutationNumberTfNameCellLineNameOverlapList
		public void findAllOverlappingTfbsIntervalsWithNumbers(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TLongIntMap permutationNumberTfNumberCellLineNumber2ZeroorOneMap,List<PermutationNumberTfNumberCellLineNumberOverlap> permutationNumberTfNumberCellLineNumberOverlapList,int overlapDefinition){
			
			long permutationNumberTfNumberCellLineNumber;
			
			TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
			
						
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
						castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
					}
							
					permutationNumberTfNumberCellLineNumber = generateCombinedNumber(permutationNumber, castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short)0);
									
					permutationNumberTfNumberCellLineNumberOverlapList.add(new PermutationNumberTfNumberCellLineNumberOverlap(permutationNumberTfNumberCellLineNumber,castedNode.getLow(),castedNode.getHigh()));
					
					if(!(permutationNumberTfNumberCellLineNumber2ZeroorOneMap.containsKey(permutationNumberTfNumberCellLineNumber))){
						permutationNumberTfNumberCellLineNumber2ZeroorOneMap.put(permutationNumberTfNumberCellLineNumber, 1);
					}
				}
				
				
				if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
					findAllOverlappingTfbsIntervalsWithNumbers(permutationNumber,node.getLeft(),interval,chromName,permutationNumberTfNumberCellLineNumber2ZeroorOneMap,permutationNumberTfNumberCellLineNumberOverlapList,overlapDefinition);	
				}
				
				if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
					findAllOverlappingTfbsIntervalsWithNumbers(permutationNumber,node.getRight(),interval,chromName,permutationNumberTfNumberCellLineNumber2ZeroorOneMap,permutationNumberTfNumberCellLineNumberOverlapList,overlapDefinition);	
					
				}
				
		}
		//with Numbers ends
		//@todo
		
		//Empirical P Value Calculation
		//without IO
		//with permutationNumberTfNameCellLineNameOverlapList
		public void findAllOverlappingTfbsIntervals(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,Integer> permutationNumberTfbsNameCellLineName2ZeroorOneMap,List<PermutationNumberTfNameCellLineNameOverlap> permutationNumberTfNameCellLineNameOverlapList,int overlapDefinition){
			
			String permutationNumberTfNameCellLineName;
			
			TforHistoneIntervalTreeNode castedNode = null;
			
						
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof TforHistoneIntervalTreeNode){
						castedNode = (TforHistoneIntervalTreeNode) node;
					}
							
					permutationNumberTfNameCellLineName = Commons.PERMUTATION + permutationNumber+ "_" + castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();			
					
					permutationNumberTfNameCellLineNameOverlapList.add(new PermutationNumberTfNameCellLineNameOverlap(permutationNumberTfNameCellLineName,castedNode.getLow(),castedNode.getHigh()));
					
					if(permutationNumberTfbsNameCellLineName2ZeroorOneMap.get(permutationNumberTfNameCellLineName)==null){
						permutationNumberTfbsNameCellLineName2ZeroorOneMap.put(permutationNumberTfNameCellLineName, 1);
					}
				}
				
				
				if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
					findAllOverlappingTfbsIntervals(permutationNumber,node.getLeft(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,permutationNumberTfNameCellLineNameOverlapList,overlapDefinition);	
				}
				
				if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
					findAllOverlappingTfbsIntervals(permutationNumber,node.getRight(),interval,chromName,permutationNumberTfbsNameCellLineName2ZeroorOneMap,permutationNumberTfNameCellLineNameOverlapList,overlapDefinition);	
					
				}
				
		}
	
	//Search2 For finding the number of each tfbs:k for the given search input size: n
	//For each search input line, each tfbs will have value 1 or 0
	//These 1 or 0's will be accumulated in tfbs2KMap
	public void findAllOverlappingTfbsIntervals(IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, List<String> tfbsNameList, Map<String,Integer> tfbsNameandCellLineName2ZeroorOneMap){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
		if (node instanceof TforHistoneIntervalTreeNode){
			castedNode = (TforHistoneIntervalTreeNode) node;
		}

		String tfbsNameandCellLineName = castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
		
			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && tfbsNameList.contains(castedNode.getTfbsorHistoneName())){
				try {
										
					bufferedWriter = bufferedWriterHashMap.get(tfbsNameandCellLineName);
					
					if (bufferedWriter==null){						
						fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS +"_" + tfbsNameandCellLineName + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(tfbsNameandCellLineName,bufferedWriter);
					}
					
					if(tfbsNameandCellLineName2ZeroorOneMap.get(tfbsNameandCellLineName)==null){
						tfbsNameandCellLineName2ZeroorOneMap.put(tfbsNameandCellLineName, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() +"\t" + "tfbs" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName()+ "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingTfbsIntervals(node.getLeft(),interval,chromName, bufferedWriterHashMap,tfbsNameList,tfbsNameandCellLineName2ZeroorOneMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervals(node.getRight(),interval,chromName, bufferedWriterHashMap,tfbsNameList,tfbsNameandCellLineName2ZeroorOneMap);	
				
			}		
	}

	//@todo Annotation with Numbers starts
	public void findAllOverlappingTfbsIntervalsWithNumbers(String outputFolder,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntObjectMap<BufferedWriter> bufferedWriterHashMap, TIntShortMap tfNumberCellLineNumber2ZeroorOneMap,int overlapDefinition,TShortObjectMap<String> tfNumber2TfNameMap,TShortObjectMap<String> cellLineNumber2CellLineNameMap,TShortObjectMap<String> fileNumber2FileNameMap){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
		if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
			castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
		}
		
		//KEGG Pathway number included
		int elementNumberCellLineNumber = IntervalTree.generateCombinedNumber(castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short)0);
		
		
			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					bufferedWriter = bufferedWriterHashMap.get(elementNumberCellLineNumber);
														
					if (bufferedWriter==null){						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS +"_" + tfNumber2TfNameMap.get(castedNode.getTforHistoneNumber()) + "_" + cellLineNumber2CellLineNameMap.get(castedNode.getCellLineNumber()) + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(elementNumberCellLineNumber,bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" +"\t" + "tfbs node Chrom Name"+ "\t"  + "node Low" + "\t" + "node High" + "\t" + "node Tfbs Name" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));
						bufferedWriter.flush();
					}
						
					if(!tfNumberCellLineNumber2ZeroorOneMap.containsKey(elementNumberCellLineNumber)){
						tfNumberCellLineNumber2ZeroorOneMap.put(elementNumberCellLineNumber, (short)1);
					}
					
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + tfNumber2TfNameMap.get(castedNode.getTforHistoneNumber())+ "\t" + cellLineNumber2CellLineNameMap.get(castedNode.getCellLineNumber()) + "\t" + fileNumber2FileNameMap.get(castedNode.getFileNumber()) +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName, bufferedWriterHashMap,tfNumberCellLineNumber2ZeroorOneMap,overlapDefinition,tfNumber2TfNameMap,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName, bufferedWriterHashMap,tfNumberCellLineNumber2ZeroorOneMap,overlapDefinition,tfNumber2TfNameMap,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
			}		
	}
	//@todo Annotation with Numbers ends
	
	
	//@todo Annotation with Numbers with OverlapList starts
	public void findAllOverlappingTfbsIntervalsWithNumbers(String outputFolder,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntObjectMap<BufferedWriter> bufferedWriterHashMap, TIntShortMap tfNumberCellLineNumber2ZeroorOneMap, List<TfCellLineOverlapWithNumbers> tfandCellLineOverlapList,int overlapDefinition,TShortObjectMap<String> tfNumber2TfNameMap,TShortObjectMap<String> cellLineNumber2CellLineNameMap,TShortObjectMap<String> fileNumber2FileNameMap){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		TforHistoneIntervalTreeNodeWithNumbers castedNode = null;
		
		if (node instanceof TforHistoneIntervalTreeNodeWithNumbers){
			castedNode = (TforHistoneIntervalTreeNodeWithNumbers) node;
		}
		
		//KEGG Pathway number included
		int elementNumberCellLineNumber = IntervalTree.generateCombinedNumber(castedNode.getTforHistoneNumber(), castedNode.getCellLineNumber(), (short)0);
		
		
			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					bufferedWriter = bufferedWriterHashMap.get(elementNumberCellLineNumber);
														
					if (bufferedWriter==null){						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS +"_" + tfNumber2TfNameMap.get(castedNode.getTforHistoneNumber()) + "_" + cellLineNumber2CellLineNameMap.get(castedNode.getCellLineNumber()) + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(elementNumberCellLineNumber,bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" +"\t" + "tfbs node Chrom Name"+ "\t"  + "node Low" + "\t" + "node High" + "\t" + "node Tfbs Name" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));
						bufferedWriter.flush();
					}
						
					if(!tfNumberCellLineNumber2ZeroorOneMap.containsKey(elementNumberCellLineNumber)){
						tfNumberCellLineNumber2ZeroorOneMap.put(elementNumberCellLineNumber, (short)1);
					}
					
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + tfNumber2TfNameMap.get(castedNode.getTforHistoneNumber())+ "\t" + cellLineNumber2CellLineNameMap.get(castedNode.getCellLineNumber()) + "\t" + fileNumber2FileNameMap.get(castedNode.getFileNumber()) +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					tfandCellLineOverlapList.add(new TfCellLineOverlapWithNumbers(elementNumberCellLineNumber,castedNode.getLow(),castedNode.getHigh()));

					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName, bufferedWriterHashMap,tfNumberCellLineNumber2ZeroorOneMap,tfandCellLineOverlapList,overlapDefinition,tfNumber2TfNameMap,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName, bufferedWriterHashMap,tfNumberCellLineNumber2ZeroorOneMap,tfandCellLineOverlapList,overlapDefinition,tfNumber2TfNameMap,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
			}		
	}
	//@todo  Annotation with Numbers with OverlapList ends
	
	//New Functionality added
	//Search2 For finding the number of each tfbs:k for the given search input size: n
	//For each search input line, each tfbs will have value 1 or 0
	//These 1 or 0's will be accumulated in tfbs2KMap
	public void findAllOverlappingTfbsIntervals(String outputFolder,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, List<String> tfbsNameList, Map<String,Integer> tfbsNameandCellLineName2ZeroorOneMap, List<TfNameandCellLineNameOverlap> tfandCellLineOverlapList,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		TforHistoneIntervalTreeNode castedNode = null;
		
		if (node instanceof TforHistoneIntervalTreeNode){
			castedNode = (TforHistoneIntervalTreeNode) node;
		}

		
		String tfbsNameandCellLineName = castedNode.getTfbsorHistoneName() + "_" + castedNode.getCellLineName();
		
			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition) && tfbsNameList.contains(castedNode.getTfbsorHistoneName())){
				try {
					
					bufferedWriter = bufferedWriterHashMap.get(tfbsNameandCellLineName);
														
					if (bufferedWriter==null){						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_TFBS +"_" + tfbsNameandCellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(tfbsNameandCellLineName,bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" +"\t" + "tfbs node Chrom Name"+ "\t"  + "node Low" + "\t" + "node High" + "\t" + "node Tfbs Name" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));
						bufferedWriter.flush();
					}
						
					if(tfbsNameandCellLineName2ZeroorOneMap.get(tfbsNameandCellLineName)==null){
						tfbsNameandCellLineName2ZeroorOneMap.put(tfbsNameandCellLineName, 1);
					}
					
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getTfbsorHistoneName()+ "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
					tfandCellLineOverlapList.add(new TfNameandCellLineNameOverlap(tfbsNameandCellLineName,castedNode.getLow(),castedNode.getHigh()));

					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingTfbsIntervals(outputFolder,node.getLeft(),interval,chromName, bufferedWriterHashMap,tfbsNameList,tfbsNameandCellLineName2ZeroorOneMap,tfandCellLineOverlapList,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervals(outputFolder,node.getRight(),interval,chromName, bufferedWriterHashMap,tfbsNameList,tfbsNameandCellLineName2ZeroorOneMap,tfandCellLineOverlapList,overlapDefinition);	
			}		
	}
	//New Functionality added
	

	
	
	//Search1
	public void findAllOverlappingTfbsIntervals(IntervalTreeNode node, Interval interval, BufferedWriter bufferedWriter,List<IntervalTreeNode> overlappedNodeList){
		
			TforHistoneIntervalTreeNode castedNode = null;
			
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				
				if (node instanceof TforHistoneIntervalTreeNode){
					castedNode = (TforHistoneIntervalTreeNode) node;
				}
				
				overlappedNodeList.add(node);
				
				try {
					bufferedWriter.write("tfbs" + "\t" + castedNode.getChromName().toString()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" +castedNode.getTfbsorHistoneName().toString()+ "\t" + castedNode.getCellLineName().toString() + "\t" + castedNode.getFileName().toString() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingTfbsIntervals(node.getLeft(),interval,bufferedWriter,overlappedNodeList);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingTfbsIntervals(node.getRight(),interval,bufferedWriter,overlappedNodeList);	
				
			}
				
	}
	
	//Search1
	public void findAllOverlappingDnaseIntervals(IntervalTreeNode node, Interval interval, BufferedWriter bufferedWriter, List<IntervalTreeNode> overlappedNodeList){
		
		DnaseIntervalTreeNode castedNode = null;
			
		if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
			
			castedNode = (DnaseIntervalTreeNode) node;
			
				
			overlappedNodeList.add(node);
				
				try {
					bufferedWriter.write("dnase" + "\t" + castedNode.getChromName().toString()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineName().toString() + "\t" + castedNode.getFileName().toString() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervals(node.getLeft(),interval,bufferedWriter,overlappedNodeList);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingDnaseIntervals(node.getRight(),interval,bufferedWriter,overlappedNodeList);		
			}
							
	}

	
	//@todo
	//with Numbers
	//Empirical P Value Calculation
	//with IO
	public void findAllOverlappingDnaseIntervalsWithNumbers(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntObjectMap<BufferedWriter> bufferedWriterHashMap, TIntIntMap permutationNumberDnaseCellLineNumber2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory = null;
		
		int permutationNumberDnaseCellLineNumber;
		
		DnaseIntervalTreeNodeWithNumbers castedNode = null;
		
		
			
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof DnaseIntervalTreeNodeWithNumbers){
					castedNode = (DnaseIntervalTreeNodeWithNumbers) node;
				}
				
				try {
					
					permutationNumberDnaseCellLineNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber, castedNode.getCellLineNumber());
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberDnaseCellLineNumber);
					
					if (bufferedWriter==null){
						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_DNASE + Commons.PERMUTATION +permutationNumber + System.getProperty("file.separator")+ permutationNumberDnaseCellLineNumber + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh"+ "\t" + "dnase" + "\t" + "dnaseChromName"+ "\t"  + "dnaseLow" + "\t" + "dnaseHigh" + "\t" + "dnaseCellLineNumber" + "\t" + "dnaseFileNumber" +System.getProperty("line.separator"));
						bufferedWriter.flush();	
						
						bufferedWriterHashMap.put(permutationNumberDnaseCellLineNumber,bufferedWriter);
					}										
					
					if(!(permutationNumberDnaseCellLineNumber2ZeroorOneMap.containsKey(permutationNumberDnaseCellLineNumber))){
						permutationNumberDnaseCellLineNumber2ZeroorOneMap.put(permutationNumberDnaseCellLineNumber, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh()+ "\t" + "dnase" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineNumber() + "\t" + castedNode.getFileNumber() +System.getProperty("line.separator"));
					bufferedWriter.flush();	
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervalsWithNumbers(outputFolder,permutationNumber,node.getLeft(),interval,chromName,bufferedWriterHashMap, permutationNumberDnaseCellLineNumber2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingDnaseIntervalsWithNumbers(outputFolder,permutationNumber,node.getRight(),interval,chromName,bufferedWriterHashMap,permutationNumberDnaseCellLineNumber2ZeroorOneMap,overlapDefinition);	
				
			}
						
	}
	//with Numbers
	//@todo
	
	//Empirical P Value Calculation
	//with IO
	public void findAllOverlappingDnaseIntervals(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,Integer> permutationNumberDnaseCellLineName2ZeroorOneMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory = null;
		String permutationNumberDnaseCellLineName;
		
		DnaseIntervalTreeNode castedNode = null;
		
		
			
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
				if (node instanceof DnaseIntervalTreeNode){
					castedNode = (DnaseIntervalTreeNode) node;
				}
				
				try {
					
					permutationNumberDnaseCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getCellLineName();
					
					bufferedWriter = bufferedWriterHashMap.get(permutationNumberDnaseCellLineName);
					
					if (bufferedWriter==null){
						
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_DNASE + Commons.PERMUTATION +permutationNumber + System.getProperty("file.separator")+ permutationNumberDnaseCellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(permutationNumberDnaseCellLineName,bufferedWriter);
					}										
					
					if(permutationNumberDnaseCellLineName2ZeroorOneMap.get(permutationNumberDnaseCellLineName)==null){
						permutationNumberDnaseCellLineName2ZeroorOneMap.put(permutationNumberDnaseCellLineName, 1);
					}
					
					bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh()+ "\t" + "dnase" + "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();	
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervals(outputFolder,permutationNumber,node.getLeft(),interval,chromName,bufferedWriterHashMap, permutationNumberDnaseCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingDnaseIntervals(outputFolder,permutationNumber,node.getRight(),interval,chromName,bufferedWriterHashMap,permutationNumberDnaseCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
						
	}
	

	
	//long starts
	public static int getGeneSetNumber(long PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber){
		
		int keggPathwayNumber = (int) (PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber %  10000L);
		
		return keggPathwayNumber;
	}
	//long ends
	
	//int starts
	public static int getCellLineNumberOrGeneSetNumber(int permutationNumberCellLineNumberOrKeggPathwayNumber){
		
		int cellLineNumberOrKeggPathwayNumber;
		
		cellLineNumberOrKeggPathwayNumber = permutationNumberCellLineNumberOrKeggPathwayNumber % 10000;
				
		return cellLineNumberOrKeggPathwayNumber;
	}
	//int ends
	
	//mixed starts
	public static int getCellLineNumber(long mixedNumber, KeyOrder keyOrder){
		
		int cellLineNumber = Integer.MIN_VALUE;
		
		switch(keyOrder){
			case DNASE_CELLLINENUMBER:{		cellLineNumber = (int) (mixedNumber % 10000L);
											break;										
										}
			case TFNUMBER_CELLLINENUMBER: {	 cellLineNumber = (int) (mixedNumber % 10000L);
											 break;																				
										}
												
			case HISTONENUMBER_CELLLINENUMBER:{	cellLineNumber = (int) (mixedNumber % 10000L);
			 										break;	
			 									}
			
			case KEGGPATHWAYNUMBER:{	break;
									}
				
			case TFNUMBER_KEGGPATHWAYNUMBER:{	break;
											}
			case TFNUMBER_CELLLINENUMBER_KEGGPATHWAYNUMBER:{	cellLineNumber = (int) ((mixedNumber / 10000L) % 10000L);
																break;
															}
										
		}
		
		return cellLineNumber;
	}
	//mixed ends
	
	//@todo For Annotation with Numbers starts
	public static int removeCellLineNumber(int elementNumberCellLineNumberKeggPathwayNumber){
		//1., 2. and 3. digits show  keggPathwayNumber
		//4., 5. and 6. digits show  cellLineNumber
		//7., 8., 9. and 10.  digits show elementNumber
		int elementNumber;
		int cellLineNumber;
		int keggPathwayNumber;
		
		int elmentNumberCellLineNumber;
		int elementNumberKeggPathwayNumber;
		
		
		
		
		//example 100_300_020 
		keggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000;
		elmentNumberCellLineNumber = elementNumberCellLineNumberKeggPathwayNumber-keggPathwayNumber;
		cellLineNumber = elmentNumberCellLineNumber %1000000;
		elementNumber = elmentNumberCellLineNumber-cellLineNumber;
		elementNumberKeggPathwayNumber = elementNumber + keggPathwayNumber;
		
		return elementNumberKeggPathwayNumber;
	}
	//@todo For Annotation with Numbers ends
	
	//@todo For Annotation with Numbers starts
	public static int removeElementNumberCellLineNumber(int elementNumberCellLineNumberKeggPathwayNumber){
		//1., 2. and 3. digits show  keggPathwayNumber
		//4., 5. and 6. digits show  cellLineNumber
		//7., 8., 9. and 10.  digits show elementNumber
		int keggPathwayNumber;
		
		//example 100_300_020 
		keggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000;
		
		return keggPathwayNumber;
	}
	//@todo For Annotation with Numbers ends
	
	//@todo For Annotation with Numbers starts
	public static int removeCellLineNumberKeggPathwayNumber(int elementNumberCellLineNumberKeggPathwayNumber){
		//1., 2. and 3. digits show  keggPathwayNumber
		//4., 5. and 6. digits show  cellLineNumber
		//7., 8., 9. and 10.  digits show elementNumber
		int elementNumber;
		int cellLineNumberKeggPathwayNumber;
		
		//example 100_300_020 
		cellLineNumberKeggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000000;
		
		elementNumber =  elementNumberCellLineNumberKeggPathwayNumber - cellLineNumberKeggPathwayNumber;
		
		return elementNumber;
	}
	//@todo For Annotation with Numbers ends
	
	//@todo For Annotation with Numbers starts
	public static short getKeggPathwayNumber(int elementNumberCellLineNumberKeggPathwayNumber){
		//1., 2. and 3. digits show  keggPathwayNumber
		//4., 5. and 6. digits show  cellLineNumber
		//7., 8., 9. and 10.  digits show elementNumber
		short keggPathwayNumber;
		
		//example 100_300_020 
		keggPathwayNumber = (short) (elementNumberCellLineNumberKeggPathwayNumber % 1000);
		
		return keggPathwayNumber;
	}
	//@todo For Annotation with Numbers ends
	
	//@todo For Annotation with Numbers starts
	public static short getCellLineNumber(int elementNumberCellLineNumberKeggPathwayNumber){
		//1., 2. and 3. digits show  keggPathwayNumber
		//4., 5. and 6. digits show  cellLineNumber
		//7., 8., 9. and 10.  digits show elementNumber
		short cellLineNumber;
		
		//example 100_300_020 
		cellLineNumber = (short) ((elementNumberCellLineNumberKeggPathwayNumber / 1000) % 1000);
		
		return cellLineNumber;
	}
	//@todo For Annotation with Numbers ends
	
	//@todo For Annotation with Numbers starts
	public static short getElementNumber(int elementNumberCellLineNumberKeggPathwayNumber){
		//1., 2. and 3. digits show  keggPathwayNumber
		//4., 5. and 6. digits show  cellLineNumber
		//7., 8., 9. and 10.  digits show elementNumber
		short elementNumber;
		int cellLineNumberKeggPathwayNumber;
		
		//example 100_300_020 
		cellLineNumberKeggPathwayNumber = elementNumberCellLineNumberKeggPathwayNumber % 1000000;
		
		elementNumber = (short) ((elementNumberCellLineNumberKeggPathwayNumber - cellLineNumberKeggPathwayNumber)/1000000);
		
		return elementNumber;
	}
	//@todo For Annotation with Numbers ends

	
	
	
	//long starts
	public static int getCellLineNumber(long permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber){
		
		int cellLineNumber;
		long cellLineNumberKeggPathwayNumber;
		
		cellLineNumberKeggPathwayNumber = permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber % 100000000L;
			
		cellLineNumber = (int)(cellLineNumberKeggPathwayNumber /10000L);
		return cellLineNumber;
	}
	//long ends
	
	
	
	public static int getTforHistoneNumber(long mixedNumber, KeyOrder keyOrder){
		
		int tforHistoneNumber = Integer.MIN_VALUE;
		
		switch(keyOrder){
			case DNASE_CELLLINENUMBER:{	break;										
										}
			case TFNUMBER_CELLLINENUMBER: {	 tforHistoneNumber = (int) (mixedNumber / 10000L);
											 break;																				
										}
												
			case HISTONENUMBER_CELLLINENUMBER:{	 	tforHistoneNumber = (int) (mixedNumber / 10000L);
			 										break;	
			 								}
			
			case KEGGPATHWAYNUMBER:{	break;
									}
				
			case TFNUMBER_KEGGPATHWAYNUMBER:{	tforHistoneNumber = (int) (mixedNumber / 10000L);
												break;
											}
			
			case TFNUMBER_CELLLINENUMBER_KEGGPATHWAYNUMBER:{	tforHistoneNumber = (int) ((mixedNumber / 100000000L) % 10000L);
																break;
															}
										
		}
	
		return tforHistoneNumber;
		
	}
	
	
	
	//Get last 9., 10., 11. and 12. digits
	public static int getTforHistoneNumber(long PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber){
		
		int tforHistoneNumber;
		long permutationNumberTforHistoneNumber;
		
		permutationNumberTforHistoneNumber = PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber / 100000000L;
		
		tforHistoneNumber = (int) (permutationNumberTforHistoneNumber % 10000L);
		return tforHistoneNumber;
	}
	
	
	public static int getElementNumberCellLineNumberOrKeggPathwayNumber(long permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber){
		int elementNumberCellLineNumberOrKeggPathwayNumber = 0;
		
		int elementNumber = getTforHistoneNumber(permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber);
		int cellLineNumber = getCellLineNumber(permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber);
		int keggPathwayNumber = getGeneSetNumber(permutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber);
		
		if (cellLineNumber>0){
			elementNumberCellLineNumberOrKeggPathwayNumber = elementNumber*10000 + cellLineNumber;
		}else if (keggPathwayNumber>0){
			elementNumberCellLineNumberOrKeggPathwayNumber = elementNumber*10000 + keggPathwayNumber;	
		}
		
		return elementNumberCellLineNumberOrKeggPathwayNumber;
	}
	
	
	//Long version starts
	public static long getOtherNumber(long PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber){
		long otherNumber;
		
		otherNumber = PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber % 1000000000000L;
		
		return otherNumber;
	}
	//Long version ends
	
	//int version starts
	public static int getPermutationNumber(int permutationNumberCellLineNumberOrKeggPathwayNumber){
		int permutationNumber;
		
		permutationNumber = (int) (permutationNumberCellLineNumberOrKeggPathwayNumber / 10000);
		
		return permutationNumber;
	}
	//int version ends
	
	//long version starts 
	public static int getPermutationNumber(long PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber){
		int permutationNumber;
		
		permutationNumber = (int) (PermutationNumberTforHistoneNumberCellLineNumberKeggPathwayNumber / 1000000000000L);
		
		return permutationNumber;
	}
	//long version ends
	
	//Last 4.th, 3.rd, 2.nd, and 1.st digits are the CellLineNumber and KeggPathwayNumber
	//Rest of the digits are PermutationNumber
	public static int generatePermutationNumberCellLineNumberorGeneSetNumber(int permutationNumber, int cellLineNumberorKeggPathwayNumber){
		//max integer	2147483647
		//min integer	-2147483648
		
		int permutationNumberCellLineNumberorKeggPathwayNumber;
		
		permutationNumberCellLineNumberorKeggPathwayNumber = permutationNumber*10000 + cellLineNumberorKeggPathwayNumber;
		
		return permutationNumberCellLineNumberorKeggPathwayNumber;
		
	}
	
	//For Annotation starts
	public static int generateCombinedNumber(short elementNumber, short cellLineNumber, short keggPathwayNumber){
		int combinedNumber;
		
		
		//Integer.MAX 2147_483_647
		//Integer.MIN -2147_483_648
		
		//1.,2.,3. digits show the KEGG Pathway number if any (last three digits)
		//4.,5.,6., digits show the cell line number (middle three digits)
		//7.,8.,9.,10. digits show the element number (first four digits)
		
		combinedNumber = elementNumber*1000000 + cellLineNumber*1000 + keggPathwayNumber;
		return combinedNumber;
	}	
	//For Annotation ends
	
	//long version
	public static long generateCombinedNumber(int permutationNumber,short elementNumber,short cellLineNumber,short keggPathwayNumber){
		
		//Long.MAX_VALUE	 9223372_0368_5477_5807
		//Long.MIN_VALUE	-9223372036854775808

		long combinedNumber;
		
		long _permutationNumber 	= permutationNumber	* 1000000000000L;	//Last 18. 17. 16. 15. 14. 13.  digits
		long _elementNumber 		= elementNumber		* 100000000L;		//Last 12. 11. 10. 9.  digits
		long _cellLineNumber		= cellLineNumber	* 10000L; 			//Last 8. 7. 6. 5.  digits
		long _keggPathwayNumber 	= keggPathwayNumber * 1L;				//Last 4. 3. 2. 1.  digits
		
		combinedNumber = _permutationNumber + _elementNumber + _cellLineNumber + _keggPathwayNumber;
		return combinedNumber;

	}
	
	public static long removeCellLineNumber(long temp1){
		
		//get only Cell Line Number and KEGG pathway number
		long cellLineNumberKeggPathwayNumber = temp1 % 100000000L;
		
		//get KEGG Pathway Number
		short keggPathwayNumber = (short) (cellLineNumberKeggPathwayNumber % 10000L);
		
		long cellLineNumber = cellLineNumberKeggPathwayNumber- keggPathwayNumber;	
		
		return temp1-cellLineNumber;
		
	} 
	
	
	public static long addKeggPathwayNumber(long temp1, short keggPathwayNumber){
		return temp1 + keggPathwayNumber;
	} 
	
	
	public static long removeCellLineNumberAddKeggPathwayNumber(long combinedNumber,short keggPathwayNumber){
		
		long temp1 = removeCellLineNumber(combinedNumber);
		return addKeggPathwayNumber(temp1,keggPathwayNumber);
		
	}
	
	//with Numbers starts
	//Empirical P Value Calculation
	//without IO
	//without overlappedNodeList
	public void findAllOverlappingDnaseIntervalsWithNumbers(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntIntMap permutationNumberDnaseCellLineName2ZeroorOneMap,int overlapDefinition){
		
		int permutationNumberDnaseCellLineNumber;
		DnaseIntervalTreeNodeWithNumbers castedNode = null;
		
	
		if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
			
				if (node instanceof DnaseIntervalTreeNodeWithNumbers){
					castedNode = (DnaseIntervalTreeNodeWithNumbers) node;
				}
					
				permutationNumberDnaseCellLineNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,castedNode.getCellLineNumber());
								
				if(!(permutationNumberDnaseCellLineName2ZeroorOneMap.containsKey(permutationNumberDnaseCellLineNumber))){
					permutationNumberDnaseCellLineName2ZeroorOneMap.put(permutationNumberDnaseCellLineNumber, 1);
				}
		}//End of IF OVERLAPS
		
		
		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
			findAllOverlappingDnaseIntervalsWithNumbers(permutationNumber,node.getLeft(),interval,chromName, permutationNumberDnaseCellLineName2ZeroorOneMap,overlapDefinition);	
		}
		
		if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
			findAllOverlappingDnaseIntervalsWithNumbers(permutationNumber,node.getRight(),interval,chromName,permutationNumberDnaseCellLineName2ZeroorOneMap,overlapDefinition);	
			
		}
						
	}
	//with Numbers ends
	
	

		//Empirical P Value Calculation
		//without IO
		//without overlappedNodeList
		public void findAllOverlappingDnaseIntervals(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,Integer> permutationNumberDnaseCellLineName2ZeroorOneMap,int overlapDefinition){
			String permutationNumberDnaseCellLineName;
			DnaseIntervalTreeNode castedNode = null;
			
		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				
					if (node instanceof DnaseIntervalTreeNode){
						castedNode = (DnaseIntervalTreeNode) node;
					}
						
					permutationNumberDnaseCellLineName = Commons.PERMUTATION + permutationNumber + "_" + castedNode.getCellLineName();
									
					if(permutationNumberDnaseCellLineName2ZeroorOneMap.get(permutationNumberDnaseCellLineName)==null){
						permutationNumberDnaseCellLineName2ZeroorOneMap.put(permutationNumberDnaseCellLineName, 1);
					}
			}//End of IF OVERLAPS
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervals(permutationNumber,node.getLeft(),interval,chromName, permutationNumberDnaseCellLineName2ZeroorOneMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingDnaseIntervals(permutationNumber,node.getRight(),interval,chromName,permutationNumberDnaseCellLineName2ZeroorOneMap,overlapDefinition);	
				
			}
							
		}
		//Empirical P Value Calculation
		//without IO
		//with overlappedNodeList
		public void findAllOverlappingDnaseIntervals(int repeatNumber,int permutationNumber,int NUMBER_OF_PERMUTATIONS,IntervalTreeNode node, Interval interval, String chromName, Map<String,Integer> permutationNumberDnaseCellLineName2ZeroorOneMap,List<IntervalTreeNode> overlappingNodeList){
			String permutationNumberDnaseCellLineName;
			
			DnaseIntervalTreeNode castedNode = null;
			
	
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				
					castedNode = (DnaseIntervalTreeNode) node;
				
					overlappingNodeList.add(node);
					
					permutationNumberDnaseCellLineName = Commons.PERMUTATION + ((repeatNumber-1)*NUMBER_OF_PERMUTATIONS+permutationNumber) + "_" + castedNode.getCellLineName();
					
					
					if(permutationNumberDnaseCellLineName2ZeroorOneMap.get(permutationNumberDnaseCellLineName)==null){
						permutationNumberDnaseCellLineName2ZeroorOneMap.put(permutationNumberDnaseCellLineName, 1);
					}
			}//End of IF OVERLAPS
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervals(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getLeft(),interval,chromName, permutationNumberDnaseCellLineName2ZeroorOneMap,overlappingNodeList);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow() <= interval.getHigh())){
				findAllOverlappingDnaseIntervals(repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getRight(),interval,chromName,permutationNumberDnaseCellLineName2ZeroorOneMap,overlappingNodeList);	
				
			}
							
		}

	//@todo Annotation with Numbers starts
	//Search2 For finding the number of each dnase cell line:k for the given search input size: n
	//For each search input line, each dnase cell line will have value 1 or 0
	//These 1 or 0's will be accumulated in dnaseCellLine2KMap		
	public void findAllOverlappingDnaseIntervalsWithNumbers(String outputFolder,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TShortObjectMap<BufferedWriter> dnaseCellLineNumber2bufferedWriterHashMap,TShortShortMap dnaseCellLineNumber2OneorZeroMap,int overlapDefinition,TShortObjectMap<String> cellLineNumber2CellLineNameMap,TShortObjectMap<String> fileNumber2FileNameMap){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String cellLineName;
		String fileName;
		
		
		DnaseIntervalTreeNodeWithNumbers castedNode = null;
		
			if (node instanceof DnaseIntervalTreeNodeWithNumbers){
				
				castedNode = (DnaseIntervalTreeNodeWithNumbers) node;
			}
		
			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					cellLineName = cellLineNumber2CellLineNameMap.get(castedNode.getCellLineNumber());
					fileName = fileNumber2FileNameMap.get(castedNode.getFileNumber());
					
					bufferedWriter = (BufferedWriter)dnaseCellLineNumber2bufferedWriterHashMap.get(castedNode.getCellLineNumber());
					
					if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_DNASE +"_" + cellLineName + ".txt",true);
						bufferedWriter = new BufferedWriter(fileWriter);
						dnaseCellLineNumber2bufferedWriterHashMap.put(castedNode.getCellLineNumber(),bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t" + "given interval low" + "\t" + 	"given interval high"+ "\t" + "dnase overlap chrom name" + "\t"  + "node low" + "\t" + "node high" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));												
					}										
					
					if(!dnaseCellLineNumber2OneorZeroMap.containsKey(castedNode.getCellLineNumber())){
						dnaseCellLineNumber2OneorZeroMap.put(castedNode.getCellLineNumber(), (short)1);
					}
					
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()+ "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + cellLineName + "\t" + fileName +System.getProperty("line.separator"));
					bufferedWriter.flush();	
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName,dnaseCellLineNumber2bufferedWriterHashMap, dnaseCellLineNumber2OneorZeroMap,overlapDefinition,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingDnaseIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName,dnaseCellLineNumber2bufferedWriterHashMap,dnaseCellLineNumber2OneorZeroMap,overlapDefinition,cellLineNumber2CellLineNameMap,fileNumber2FileNameMap);	
				
			}					
	}	
	//@todo Annotation with Numbers ends
		
	//Search2 For finding the number of each dnase cell line:k for the given search input size: n
	//For each search input line, each dnase cell line will have value 1 or 0
	//These 1 or 0's will be accumulated in dnaseCellLine2KMap		
	public void findAllOverlappingDnaseIntervals(String outputFolder,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, List<String> dnaseCellLineNameList,Map<String,Integer> dnaseCellLine2OneorZeroMap,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		DnaseIntervalTreeNode castedNode = null;
		
			if (node instanceof DnaseIntervalTreeNode){
				
				castedNode = (DnaseIntervalTreeNode) node;
			}
		
			if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition) && dnaseCellLineNameList.contains(castedNode.getCellLineName())){
				try {
					
					
					
					bufferedWriter = bufferedWriterHashMap.get(castedNode.getCellLineName());
					
					if (bufferedWriter==null){
						fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_DNASE +"_" + castedNode.getCellLineName() + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(castedNode.getCellLineName(),bufferedWriter);
						bufferedWriter.write("Searched for chr" + "\t" + "given interval low" + "\t" + 	"given interval high"+ "\t" + "dnase overlap chrom name" + "\t"  + "node low" + "\t" + "node high" + "\t" + "node CellLineName" + "\t" + "node FileName" +System.getProperty("line.separator"));
						
						
					}										
					
					if(dnaseCellLine2OneorZeroMap.get(castedNode.getCellLineName())==null){
						dnaseCellLine2OneorZeroMap.put(castedNode.getCellLineName(), 1);
					}
					
					bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()+ "\t" + castedNode.getChromName()+ "\t"  + castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getCellLineName() + "\t" + castedNode.getFileName() +System.getProperty("line.separator"));
					bufferedWriter.flush();	
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingDnaseIntervals(outputFolder,node.getLeft(),interval,chromName,bufferedWriterHashMap,dnaseCellLineNameList, dnaseCellLine2OneorZeroMap,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingDnaseIntervals(outputFolder,node.getRight(),interval,chromName,bufferedWriterHashMap,dnaseCellLineNameList,dnaseCellLine2OneorZeroMap,overlapDefinition);	
				
			}					
	}

	//@todo
	//with Numbers
	//Empirical P Value Calculation
	//Search2 KeggPathway
	//For finding the number of each keggpathway:k for the given search input size: n
	//For each search input line, each kegg pathway will have a value of 1 or 0
	//These 1 or 0's will be accumulated in keggPathway2KMap	
	//with IO
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(
			String outputFolder,
			int permutationNumber,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName, 
			TIntObjectMap<BufferedWriter> bufferedWriterHashMap, 
			TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap,
			TIntIntMap permutationNumberGeneSetNumber2OneorZeroMap, 
			String type, 
			GeneSetAnalysisType geneSetAnalysisType,
			int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
	
		int permutationNumberGeneSetNumber;
		Short geneSetNumber = null;
		TShortList listofGeneSetNumberContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;
					}
					
					try {
												
						//write exon based results
						if (geneSetAnalysisType.isExonBasedGeneSetAnalysis()){
							
							//exon based kegg pathway analysis
							if (castedNode.getIntervalName().isExon()){
								
								listofGeneSetNumberContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
								
								if(listofGeneSetNumberContainingThisGeneId!=null){
									for(int i= 0; i<listofGeneSetNumberContainingThisGeneId.size(); i++){
										
										geneSetNumber = listofGeneSetNumberContainingThisGeneId.get(i);
										permutationNumberGeneSetNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber, geneSetNumber);
											
										bufferedWriter = bufferedWriterHashMap.get(permutationNumberGeneSetNumber);
										
										if (bufferedWriter == null){
											
											fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_exonBased_" + geneSetNumber + ".txt",true);
											bufferedWriter = new BufferedWriter(fileWriter);
											bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName"+ "\t" +  "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" +  "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t"+ "GeneEntrezId" +System.getProperty("line.separator"));
											bufferedWriter.flush();	
										
											bufferedWriterHashMap.put(permutationNumberGeneSetNumber, bufferedWriter);
																					
										}
										
										if(!(permutationNumberGeneSetNumber2OneorZeroMap.containsKey(permutationNumberGeneSetNumber))){
											permutationNumberGeneSetNumber2OneorZeroMap.put(permutationNumberGeneSetNumber, 1);
										}
										
										bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() +  "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
									
										
									}// End of For: for all keggpathways having this gene in their gene list
								} //End of If: keggPathWayListContainingThisGeneId is not null								
							}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						}
						//write regulation based results
						else if (geneSetAnalysisType.isRegulationBasedGeneSetAnalysis()){
							//Regulation Based kegg pathway analysis
							if (castedNode.getIntervalName().isIntron() ||
								castedNode.getIntervalName().isFivePOne() ||
								castedNode.getIntervalName().isFivePTwo() ||
								castedNode.getIntervalName().isThreePOne()||
								castedNode.getIntervalName().isThreePTwo()){
								
								listofGeneSetNumberContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
								
								if(listofGeneSetNumberContainingThisGeneId!=null){
									for(int i= 0; i<listofGeneSetNumberContainingThisGeneId.size(); i++){
										geneSetNumber = listofGeneSetNumberContainingThisGeneId.get(i);
										permutationNumberGeneSetNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,geneSetNumber);
										
									
										bufferedWriter = bufferedWriterHashMap.get(permutationNumberGeneSetNumber);
										
										if (bufferedWriter == null){
											
											fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS  + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator")+ Commons.PERMUTATION + permutationNumber + "_regulationBased_" + geneSetNumber + ".txt",true);
											bufferedWriter = new BufferedWriter(fileWriter);
											bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName"+ "\t" +  "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" +  "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t"+ "GeneEntrezId" +System.getProperty("line.separator"));
											bufferedWriter.flush();	
										
											bufferedWriterHashMap.put(permutationNumberGeneSetNumber, bufferedWriter);									
											
										}
										
										if(!(permutationNumberGeneSetNumber2OneorZeroMap.containsKey(permutationNumberGeneSetNumber))){
											permutationNumberGeneSetNumber2OneorZeroMap.put(permutationNumberGeneSetNumber, 1);
										}
										
										bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" +  castedNode.getGeneHugoSymbolNumber()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
											
									}// End of For: for all kegg pathways having this gene in their gene list
								} // End of If:		keggPathWayListContainingThisGeneId is not null					
							}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						}
						//write all results
						else{
							listofGeneSetNumberContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
							
							if(listofGeneSetNumberContainingThisGeneId!=null){
								for(int i= 0; i<listofGeneSetNumberContainingThisGeneId.size(); i++){
									geneSetNumber = listofGeneSetNumberContainingThisGeneId.get(i);
									permutationNumberGeneSetNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber, geneSetNumber);
											
											
									bufferedWriter = bufferedWriterHashMap.get(permutationNumberGeneSetNumber);
									
									if (bufferedWriter == null){
										
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION + permutationNumber +"_all_" + geneSetNumber + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName"+ "\t" +  "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" +  "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t"+ "GeneEntrezId" +System.getProperty("line.separator"));
										bufferedWriter.flush();	
									
										bufferedWriterHashMap.put(permutationNumberGeneSetNumber, bufferedWriter);
										
									}
									
									if(!(permutationNumberGeneSetNumber2OneorZeroMap.containsKey(permutationNumberGeneSetNumber))){
										permutationNumberGeneSetNumber2OneorZeroMap.put(permutationNumberGeneSetNumber, 1);
									}
									
									bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
									
									
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
						}
												
						
																
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,permutationNumber,node.getLeft(),interval,chromName,bufferedWriterHashMap, geneId2ListofGeneSetNumberMap, permutationNumberGeneSetNumber2OneorZeroMap,type,geneSetAnalysisType,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,permutationNumber,node.getRight(),interval,chromName,bufferedWriterHashMap, geneId2ListofGeneSetNumberMap, permutationNumberGeneSetNumber2OneorZeroMap,type,geneSetAnalysisType,overlapDefinition);	
				
			}
						
	}
	//with Numbers
	//@todo

	
	
	//Empirical P Value Calculation
	//Search2 KeggPathway
	//For finding the number of each keggpathway:k for the given search input size: n
	//For each search input line, each kegg pathway will have a value of 1 or 0
	//These 1 or 0's will be accumulated in keggPathway2KMap	
	//with IO
	public void findAllOverlappingUcscRefSeqGenesIntervals(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,List<String>> geneId2KeggPathwayMap, Map<String,Integer> permutationNumberKeggPathway2OneorZeroMap, String type, KeggPathwayAnalysisType keggPathwayAnalysisType,int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
	
		String permutationNumberKeggPathwayName = null;
		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNode castedNode = null;
		
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNode){
						castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
					}
					
					try {
												
						//write exon based results
						if (keggPathwayAnalysisType.isExonBasedKeggPathwayAnalysis()){
							
							//exon based kegg pathway analysis
							if (castedNode.getIntervalName().isExon()){
								
								keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
								
								if(keggPathWayListContainingThisGeneId!=null){
									for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
										keggPathwayName = keggPathWayListContainingThisGeneId.get(i);
										permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;	
											
										bufferedWriter = bufferedWriterHashMap.get(permutationNumberKeggPathwayName);
										
										if (bufferedWriter == null){
											
											fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_exonBased_" + keggPathwayName + ".txt",true);
											bufferedWriter = new BufferedWriter(fileWriter);
											bufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
																					
										}
										
										if(permutationNumberKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
											permutationNumberKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
										}
										
										bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
									
										
									}// End of For: for all keggpathways having this gene in their gene list
								} //End of If: keggPathWayListContainingThisGeneId is not null								
							}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						}
						//write regulation based results
						else if (keggPathwayAnalysisType.isRegulationBasedKeggPathwayAnalysis()){
							//Regulation Based kegg pathway analysis
							if (castedNode.getIntervalName().isIntron() ||
								castedNode.getIntervalName().isFivePOne() ||
								castedNode.getIntervalName().isFivePTwo() ||
								castedNode.getIntervalName().isThreePOne()||
								castedNode.getIntervalName().isThreePTwo()){
								
								keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
								
								if(keggPathWayListContainingThisGeneId!=null){
									for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
										keggPathwayName = keggPathWayListContainingThisGeneId.get(i);
										permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;	
											
										bufferedWriter = bufferedWriterHashMap.get(permutationNumberKeggPathwayName);
										
										if (bufferedWriter == null){
											
											fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS  + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator")+ Commons.PERMUTATION + permutationNumber + "_regulationBased_" + keggPathwayName + ".txt",true);
											bufferedWriter = new BufferedWriter(fileWriter);
											bufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
										
											
										}
										
										if(permutationNumberKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
											permutationNumberKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
										}
										
										bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
											
									}// End of For: for all kegg pathways having this gene in their gene list
								} // End of If:		keggPathWayListContainingThisGeneId is not null					
							}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						}
						//write all results
						else{
							keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
							
							if(keggPathWayListContainingThisGeneId!=null){
								for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get(i);
									permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;	
											
									bufferedWriter = bufferedWriterHashMap.get(permutationNumberKeggPathwayName);
									
									if (bufferedWriter == null){
										
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION + permutationNumber +"_all_" + keggPathwayName + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										bufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
										
									}
									
									if(permutationNumberKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
										permutationNumberKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
									}
									
									bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
									
									
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
						}
												
						
																
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,permutationNumber,node.getLeft(),interval,chromName,bufferedWriterHashMap, geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap,type,keggPathwayAnalysisType,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,permutationNumber,node.getRight(),interval,chromName,bufferedWriterHashMap, geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap,type,keggPathwayAnalysisType,overlapDefinition);	
				
			}
						
	}

	//@todo
	//Empirical P Value Calculation
	//Search2 KeggPathway
	//For finding the number of each keggpathway:k for the given search input size: n
	//For each search input line, each kegg pathway will have a value of 1 or 0
	//These 1 or 0's will be accumulated in keggPathway2KMap	
	//without IO
	//with Numbers
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(
			int permutationNumber,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName, 
			TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap, 
			TIntIntMap permutationNumberKeggPathwayNumber2OneorZeroMap, 
			String type, 
			GeneSetAnalysisType geneSetAnalysisType,
			int overlapDefinition){
		
		int permutationNumberGeneSetNumber;
		
		Short geneSetNumber = null;
		TShortList ListofGeneSetNumberContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		
			
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
						if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
							castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;
						}
					
						//write exon based results
						if (geneSetAnalysisType.isExonBasedGeneSetAnalysis()){
							
							//exon based kegg pathway analysis
							if (castedNode.getIntervalName().isExon()){
								
								ListofGeneSetNumberContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
								
								if(ListofGeneSetNumberContainingThisGeneId!=null){
									for(int i= 0; i<ListofGeneSetNumberContainingThisGeneId.size(); i++){
										
										geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get(i);
										
										
										permutationNumberGeneSetNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,geneSetNumber);
																							
										
										if(!(permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey(permutationNumberGeneSetNumber))){
											permutationNumberKeggPathwayNumber2OneorZeroMap.put(permutationNumberGeneSetNumber, 1);
										}
										
									}// End of For: for all genesets having this gene in their gene list
								} //End of If: geneSetListContainingThisGeneId is not null								
							}// End of If: Exon Based GeneSet Analysis, Overlapped node is an exon

						}
						//write regulation based results
						else if (geneSetAnalysisType.isRegulationBasedGeneSetAnalysis()){
							//Regulation Based kegg pathway analysis
							if (castedNode.getIntervalName().isIntron() ||
								castedNode.getIntervalName().isFivePOne() ||
								castedNode.getIntervalName().isFivePTwo() ||
								castedNode.getIntervalName().isThreePOne()||
								castedNode.getIntervalName().isThreePTwo()){
								
								ListofGeneSetNumberContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
								
								if(ListofGeneSetNumberContainingThisGeneId!=null){
									for(int i= 0; i<ListofGeneSetNumberContainingThisGeneId.size(); i++){
										
										geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get(i);
										
										permutationNumberGeneSetNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,geneSetNumber);
											
										
										
										if(!(permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey(permutationNumberGeneSetNumber))){
											permutationNumberKeggPathwayNumber2OneorZeroMap.put(permutationNumberGeneSetNumber, 1);
										}
										
								
											
									}// End of For: for all gene sets having this gene in their gene list
								} // End of If:		geneSetListContainingThisGeneId is not null					
							}//End of If: Regulation Based gene set Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						}//regulation based geneset analysis
						//write all results
						else{
							ListofGeneSetNumberContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
							
							if(ListofGeneSetNumberContainingThisGeneId!=null){
								for(int i= 0; i<ListofGeneSetNumberContainingThisGeneId.size(); i++){
									
									geneSetNumber = ListofGeneSetNumberContainingThisGeneId.get(i);
									
									
									permutationNumberGeneSetNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,geneSetNumber);
									
									
									if(!(permutationNumberKeggPathwayNumber2OneorZeroMap.containsKey(permutationNumberGeneSetNumber))){
										permutationNumberKeggPathwayNumber2OneorZeroMap.put(permutationNumberGeneSetNumber, 1);
									}
									
								
									
								}// End of For: for all genesets having this gene in their gene list
							} // End of If:		geneSetListContainingThisGeneId is not null	
							
						}//all based geneset analysis
																																									
				}//End of if: there is overlap	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(permutationNumber,node.getLeft(),interval,chromName, geneId2ListofGeneSetNumberMap, permutationNumberKeggPathwayNumber2OneorZeroMap,type,geneSetAnalysisType,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(permutationNumber,node.getRight(),interval,chromName, geneId2ListofGeneSetNumberMap, permutationNumberKeggPathwayNumber2OneorZeroMap,type,geneSetAnalysisType,overlapDefinition);		
			}
						
	}
	//@todo
	
	
	//Empirical P Value Calculation
	//Search2 KeggPathway
	//For finding the number of each keggpathway:k for the given search input size: n
	//For each search input line, each kegg pathway will have a value of 1 or 0
	//These 1 or 0's will be accumulated in keggPathway2KMap	
	//without IO
	public void findAllOverlappingUcscRefSeqGenesIntervals(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,List<String>> geneId2KeggPathwayMap, Map<String,Integer> permutationNumberKeggPathway2OneorZeroMap, String type, KeggPathwayAnalysisType keggPathwayAnalysisType,int overlapDefinition){
		String permutationNumberKeggPathwayName = null;
		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNode castedNode = null;
		
			
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
						if (node instanceof UcscRefSeqGeneIntervalTreeNode){
							castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
						}
					
						//write exon based results
						if (keggPathwayAnalysisType.isExonBasedKeggPathwayAnalysis()){
							
							//exon based kegg pathway analysis
							if (castedNode.getIntervalName().isExon()){
								
								keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
								
								if(keggPathWayListContainingThisGeneId!=null){
									for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
										
										permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathWayListContainingThisGeneId.get(i);	
										keggPathwayName = keggPathWayListContainingThisGeneId.get(i);
											
										
										if(permutationNumberKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
											permutationNumberKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
										}
										
									}// End of For: for all keggpathways having this gene in their gene list
								} //End of If: keggPathWayListContainingThisGeneId is not null								
							}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						}
						//write regulation based results
						else if (keggPathwayAnalysisType.isRegulationBasedKeggPathwayAnalysis()){
							//Regulation Based kegg pathway analysis
							if (castedNode.getIntervalName().isIntron() ||
								castedNode.getIntervalName().isFivePOne() ||
								castedNode.getIntervalName().isFivePTwo() ||
								castedNode.getIntervalName().isThreePOne() ||
								castedNode.getIntervalName().isThreePTwo()){
								
								keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
								
								if(keggPathWayListContainingThisGeneId!=null){
									for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
										permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathWayListContainingThisGeneId.get(i);	
										keggPathwayName = keggPathWayListContainingThisGeneId.get(i);
											
										
										
										if(permutationNumberKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
											permutationNumberKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
										}
										
								
											
									}// End of For: for all kegg pathways having this gene in their gene list
								} // End of If:		keggPathWayListContainingThisGeneId is not null					
							}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						}//regulation based kegg pathway analysis
						//write all results
						else{
							keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
							
							if(keggPathWayListContainingThisGeneId!=null){
								for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
									permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber + "_" + keggPathWayListContainingThisGeneId.get(i);	
									keggPathwayName = keggPathWayListContainingThisGeneId.get(i);
											
									
									
									if(permutationNumberKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
										permutationNumberKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
									}
									
								
									
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
						}//all kegg pathway analysis
												
						
																
									
				}//End of if: there is overlap	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervals(permutationNumber,node.getLeft(),interval,chromName, geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap,type,keggPathwayAnalysisType,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals(permutationNumber,node.getRight(),interval,chromName, geneId2KeggPathwayMap, permutationNumberKeggPathway2OneorZeroMap,type,keggPathwayAnalysisType,overlapDefinition);		
			}
						
	}
	
	
	
	//@todo
	//with Numbers
	//NEW FUNCIONALITY
	//with IO
	//with Overlap Node List
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(
			String outputFolder,
			int permutationNumber,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName, 
			TIntObjectMap<TShortList> geneId2KeggPathwayNumberMap, 
			TIntObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap, 
			TIntObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap, 
			TIntObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap, 
			TIntIntMap permutationNumberExonBasedKeggPathway2OneorZeroMap, 
			TIntIntMap permutationNumberRegulationBasedKeggPathway2OneorZeroMap,
			TIntIntMap permutationNumberAllBasedKeggPathway2OneorZeroMap,
			String type,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberExonBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,
			List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberAllBasedKeggPathwayOverlapList,
			int overlapDefinition){
		
		int permutationNumberKeggPathwayNumber;
		Short keggPathwayNumber;
		TShortList keggPathwayNumberListContainingThisGeneId = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		
		if (Commons.NCBI_GENE_ID.equals(type)){
			//There is overlap
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;
					}
					
					keggPathwayNumberListContainingThisGeneId =  geneId2KeggPathwayNumberMap.get(castedNode.getGeneEntrezId());
				
					//write EXON based results
					if (castedNode.getIntervalName().isExon()){							
						if(keggPathwayNumberListContainingThisGeneId!=null){
							
//							public PermutationNumberUcscRefSeqGeneNumberOverlap(
//									int permutationNumber, 
//									int refSeqGeneNumber,
//									IntervalName intervalName, 
//									int intervalNumber,
//									int geneHugoSymbolNumber, 
//									int geneEntrezId, 
//									int low, 
//									int high,
//									List<Short> keggPathwayNameList) {

							permutationNumberExonBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneNumberOverlap(permutationNumber,castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayNumberListContainingThisGeneId));

							for(int i= 0; i<keggPathwayNumberListContainingThisGeneId.size(); i++){
								
								keggPathwayNumber = keggPathwayNumberListContainingThisGeneId.get(i);											
								
								permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,keggPathwayNumber);
										
								
								bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayNumber);
								
								if (bufferedWriter == null){
																	
									fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_exonBased_" + keggPathwayNumber + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName"+ "\t" +  "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t"+ "GeneEntrezId" +System.getProperty("line.separator"));
									bufferedWriter.flush();	
		
									exonBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayNumber, bufferedWriter);
								}
								
								
								if(!(permutationNumberExonBasedKeggPathway2OneorZeroMap.containsKey(permutationNumberKeggPathwayNumber))){
									permutationNumberExonBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayNumber, 1);
								}
								
								bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber() + "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
								bufferedWriter.flush();	
								
							}// End of For: for all keggpathways having this gene in their gene list
						} //End of If: keggPathWayListContainingThisGeneId is not null								
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

					
					//write regulation based results
					//Regulation Based kegg pathway analysis
					if (castedNode.getIntervalName().isIntron() ||
						castedNode.getIntervalName().isFivePOne() ||
						castedNode.getIntervalName().isFivePTwo() ||
						castedNode.getIntervalName().isThreePOne() ||
						castedNode.getIntervalName().isThreePTwo()){
													
						if(keggPathwayNumberListContainingThisGeneId!=null){
							
						
							permutationNumberRegulationBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneNumberOverlap(permutationNumber,castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayNumberListContainingThisGeneId));
							
							for(int i= 0; i<keggPathwayNumberListContainingThisGeneId.size(); i++){
								
								keggPathwayNumber = keggPathwayNumberListContainingThisGeneId.get(i);
								permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,keggPathwayNumber);
										
									
								bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayNumber);
								
								if (bufferedWriter == null){
									
									fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_regulationBased_" + keggPathwayNumber + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									
									bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName"+ "\t" +  "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t"+ "GeneEntrezId" +System.getProperty("line.separator"));
									bufferedWriter.flush();	
		
									regulationBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayNumber, bufferedWriter);
								}
									
								if(!(permutationNumberRegulationBasedKeggPathway2OneorZeroMap.containsKey(permutationNumberKeggPathwayNumber))){
									permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayNumber, 1);
								}
						
								bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber()+ "\t" + castedNode.getIntervalName() + "\t"  + castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
								bufferedWriter.flush();	
						
																		
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null					
					}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					
					
					//write all results							
					if(keggPathwayNumberListContainingThisGeneId!=null){
						
						permutationNumberAllBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneNumberOverlap(permutationNumber,castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayNumberListContainingThisGeneId));
						
						for(int i= 0; i<keggPathwayNumberListContainingThisGeneId.size(); i++){
							
							keggPathwayNumber = keggPathwayNumberListContainingThisGeneId.get(i);
							permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,keggPathwayNumber);
								
							bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayNumber);
							
							if (bufferedWriter == null){
								
								fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_allBased_" + keggPathwayNumber + ".txt",true);
								bufferedWriter = new BufferedWriter(fileWriter);
								
								bufferedWriter.write("Searched for" + "\t" + "chromName" + "\t" + "intervalLow" + "\t" + "intervalHigh" + "\t" + "ucscRefSeqGene" + "\t" + "ChromName"+ "\t" +  "Low" + "\t" + "High" + "\t" + "RefSeqGeneNumber" + "\t" + "IntervalName" + "\t" + "IntervalNumber" + "\t" + "GeneHugoSymbolNumber" + "\t"+ "GeneEntrezId" +System.getProperty("line.separator"));
								bufferedWriter.flush();	

								allBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayNumber, bufferedWriter);
							}
																		
							if(!(permutationNumberAllBasedKeggPathway2OneorZeroMap.containsKey(permutationNumberKeggPathwayNumber))){
								permutationNumberAllBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayNumber, 1);
							}
							
							bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneNumber()+ "\t" + castedNode.getIntervalName() + "\t" +  castedNode.getIntervalNumber() + "\t" + castedNode.getGeneHugoSymbolNumber()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
							bufferedWriter.flush();	
																
						}// End of For: for all kegg pathways having this gene in their gene list
					} // End of If:		keggPathWayListContainingThisGeneId is not null	
					
											
				} catch (IOException e) {
					e.printStackTrace();
				}
															
								
			}//End of if: there is overlap	
		} //End of If: type is NCBI_GENE_ID
			
		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
			findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,permutationNumber,node.getLeft(),interval,chromName, geneId2KeggPathwayNumberMap, exonBasedKeggPathwayBufferedWriterHashMap,regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type, permutationNumberExonBasedKeggPathwayOverlapList, permutationNumberRegulationBasedKeggPathwayOverlapList, permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);	
		}
		
		if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,permutationNumber,node.getRight(),interval,chromName, geneId2KeggPathwayNumberMap,exonBasedKeggPathwayBufferedWriterHashMap,regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type, permutationNumberExonBasedKeggPathwayOverlapList, permutationNumberRegulationBasedKeggPathwayOverlapList, permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);		
		}					
	}	
	//with Numbers
	//@todo

	//NEW FUNCIONALITY
	//with IO
	//with Overlap Node List
	public void findAllOverlappingUcscRefSeqGenesIntervals(String outputFolder,int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,List<String>> geneId2KeggPathwayMap, Map<String,BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap, Map<String,BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap, Map<String,BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap, Map<String,Integer> permutationNumberExonBasedKeggPathway2OneorZeroMap, Map<String,Integer> permutationNumberRegulationBasedKeggPathway2OneorZeroMap,Map<String,Integer> permutationNumberAllBasedKeggPathway2OneorZeroMap,String type,List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberExonBasedKeggPathwayOverlapList,List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberAllBasedKeggPathwayOverlapList,int overlapDefinition){
		String permutationNumberKeggPathwayName = null;
		String keggPathwayName;
		List<String> keggPathwayListContainingThisGeneId = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory;
		
		UcscRefSeqGeneIntervalTreeNode castedNode = null;
		
		if (Commons.NCBI_GENE_ID.equals(type)){
			//There is overlap
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
				try {
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNode){
						castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
					}
					
					keggPathwayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
				
					//write exon based results
					if (castedNode.getIntervalName().isExon()){							
						if(keggPathwayListContainingThisGeneId!=null){
							
							permutationNumberExonBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneOverlap(Commons.PERMUTATION + permutationNumber,castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));

							for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
								
								keggPathwayName = keggPathwayListContainingThisGeneId.get(i);											
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;	
								
								bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayName);
								
								if (bufferedWriter == null){
																	
									fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_exonBased_" + keggPathwayName + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									exonBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
								}
								
								
								if(permutationNumberExonBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
									permutationNumberExonBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
								}
								
								bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
								bufferedWriter.flush();	
								
							}// End of For: for all keggpathways having this gene in their gene list
						} //End of If: keggPathWayListContainingThisGeneId is not null								
					}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

					
					//write regulation based results
					//Regulation Based kegg pathway analysis
					if (castedNode.getIntervalName().isIntron() ||
						castedNode.getIntervalName().isFivePOne() ||
						castedNode.getIntervalName().isFivePTwo() ||
						castedNode.getIntervalName().isThreePOne()||
						castedNode.getIntervalName().isThreePTwo()){
													
						if(keggPathwayListContainingThisGeneId!=null){
							
							permutationNumberRegulationBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneOverlap(Commons.PERMUTATION + permutationNumber,castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));
							
							for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
								
								keggPathwayName = keggPathwayListContainingThisGeneId.get(i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;
								
								bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayName);
								
								if (bufferedWriter == null){
									
									fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_regulationBased_" + keggPathwayName + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									regulationBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
								}
									
								if(permutationNumberRegulationBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
									permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
								}
						
								bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
								bufferedWriter.flush();	
						
																		
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null					
					}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

					
					
					//write all results							
					if(keggPathwayListContainingThisGeneId!=null){
						
						permutationNumberAllBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneOverlap(Commons.PERMUTATION + permutationNumber,castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));
						
						for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
							
							keggPathwayName = keggPathwayListContainingThisGeneId.get(i);
							permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;
							
							bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayName);
							
							if (bufferedWriter == null){
								
								fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +permutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +permutationNumber +"_allBased_" + keggPathwayName + ".txt",true);
								bufferedWriter = new BufferedWriter(fileWriter);
								allBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
							}
																		
							if(permutationNumberAllBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
								permutationNumberAllBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
							}
							
							bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
							bufferedWriter.flush();	
																
						}// End of For: for all kegg pathways having this gene in their gene list
					} // End of If:		keggPathWayListContainingThisGeneId is not null	
					
											
				} catch (IOException e) {
					e.printStackTrace();
				}
															
								
			}//End of if: there is overlap	
		} //End of If: type is NCBI_GENE_ID
			
		if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
			findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,permutationNumber,node.getLeft(),interval,chromName, geneId2KeggPathwayMap, exonBasedKeggPathwayBufferedWriterHashMap,regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type, permutationNumberExonBasedKeggPathwayOverlapList, permutationNumberRegulationBasedKeggPathwayOverlapList, permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);	
		}
		
		if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
			findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,permutationNumber,node.getRight(),interval,chromName, geneId2KeggPathwayMap,exonBasedKeggPathwayBufferedWriterHashMap,regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type, permutationNumberExonBasedKeggPathwayOverlapList, permutationNumberRegulationBasedKeggPathwayOverlapList, permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);		
		}					
	}	
	
	
	
	//NEW FUNCIONALITY
	//with IO
	public void findAllOverlappingUcscRefSeqGenesIntervals(String outputFolder, int repeatNumber,int permutationNumber,int NUMBER_OF_PERMUTATIONS,IntervalTreeNode node, Interval interval, String chromName, Map<String,List<String>> geneId2KeggPathwayMap, Map<String,BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap, Map<String,BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap, Map<String,BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap, Map<String,Integer> permutationNumberExonBasedKeggPathway2OneorZeroMap, Map<String,Integer> permutationNumberRegulationBasedKeggPathway2OneorZeroMap,Map<String,Integer> permutationNumberAllBasedKeggPathway2OneorZeroMap,String type){
		String permutationNumberKeggPathwayName = null;
		String keggPathwayName;
		List<String> keggPathwayListContainingThisGeneId = null;
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		File directory;
		
		UcscRefSeqGeneIntervalTreeNode castedNode = null;
		
		int repeatNumberReflectedPermutationNumber = ((repeatNumber-1)*NUMBER_OF_PERMUTATIONS) + permutationNumber;

			
			if (Commons.NCBI_GENE_ID.equals(type)){
				//There is overlap
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
					try {
						
						if (node instanceof UcscRefSeqGeneIntervalTreeNode){
							castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
						}
						
						keggPathwayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
					
						//write exon based results
						if (castedNode.getIntervalName().isExon()){							
							if(keggPathwayListContainingThisGeneId!=null){
								for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
									
									keggPathwayName = keggPathwayListContainingThisGeneId.get(i);											
									permutationNumberKeggPathwayName = Commons.PERMUTATION + (((repeatNumber-1)*NUMBER_OF_PERMUTATIONS) + permutationNumber)+ "_" + keggPathwayName;	
									
									bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayName);
									
									if (bufferedWriter == null){
										
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_PERMUTATIONS_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +repeatNumberReflectedPermutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +repeatNumberReflectedPermutationNumber +"_exonBased_" + keggPathwayName + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										exonBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
									}
									
									
									if(permutationNumberExonBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
										permutationNumberExonBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
									}
									
									bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
									
								}// End of For: for all keggpathways having this gene in their gene list
							} //End of If: keggPathWayListContainingThisGeneId is not null								
						}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						
						//write regulation based results
						//Regulation Based kegg pathway analysis
						if (castedNode.getIntervalName().isIntron() ||
							castedNode.getIntervalName().isFivePOne() ||
							castedNode.getIntervalName().isFivePTwo() ||
							castedNode.getIntervalName().isThreePOne()||
							castedNode.getIntervalName().isThreePTwo()){
														
							if(keggPathwayListContainingThisGeneId!=null){
								for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
									
									keggPathwayName = keggPathwayListContainingThisGeneId.get(i);
									permutationNumberKeggPathwayName = Commons.PERMUTATION + (((repeatNumber-1)*NUMBER_OF_PERMUTATIONS) + permutationNumber)+ "_" + keggPathwayName;
									
									bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayName);
									
									if (bufferedWriter == null){
										
										fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_PERMUTATIONS_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +repeatNumberReflectedPermutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +repeatNumberReflectedPermutationNumber +"_regulationBased_" + keggPathwayName + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										regulationBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
									}
										
									if(permutationNumberRegulationBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
										permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
									}
							
									bufferedWriter.write("Searched for" + "\t" + chromName + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
							
																			
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null					
						}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						
						
						//write all results							
						if(keggPathwayListContainingThisGeneId!=null){
							for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
								
								keggPathwayName = keggPathwayListContainingThisGeneId.get(i);
								permutationNumberKeggPathwayName = Commons.PERMUTATION + (((repeatNumber-1)*NUMBER_OF_PERMUTATIONS) + permutationNumber)+ "_" + keggPathwayName;
								
								bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get(permutationNumberKeggPathwayName);
								
								if (bufferedWriter == null){
									
									fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_PERMUTATIONS_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + Commons.PERMUTATION +repeatNumberReflectedPermutationNumber+ System.getProperty("file.separator") + Commons.PERMUTATION +repeatNumberReflectedPermutationNumber +"_allBased_" + keggPathwayName + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									allBasedKeggPathwayBufferedWriterHashMap.put(permutationNumberKeggPathwayName, bufferedWriter);
								}
																			
								if(permutationNumberAllBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
									permutationNumberAllBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
								}
																
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null	
						
												
					} catch (IOException e) {
						e.printStackTrace();
					}
																
									
				}//End of if: there is overlap	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getLeft(),interval,chromName, geneId2KeggPathwayMap, exonBasedKeggPathwayBufferedWriterHashMap,regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,repeatNumber,permutationNumber,NUMBER_OF_PERMUTATIONS,node.getRight(),interval,chromName, geneId2KeggPathwayMap,exonBasedKeggPathwayBufferedWriterHashMap,regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type);		
			}					
		}	
	
	
	//@todo
	//with numbers starts
	//NEW FUNCIONALITY
	//EXON BASED KEGG PATHWAY
	//REGULATION BASED KEGG PATHWAY
	//ALL BASED KEGG PATHWAY
	//Empirical P Value Calculation
	//Search2 KeggPathway
	//For finding the number of each keggpathway:k for the given search input size: n
	//For each search input line, each kegg pathway will have a value of 1 or 0
	//These 1 or 0's will be accumulated in keggPathway2KMap	
	//without IO
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, TIntObjectMap<TShortList> geneId2KeggPathwayNumbersMap, TIntIntMap permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap, TIntIntMap permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap, TIntIntMap permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap,String type,List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberExonBasedKeggPathwayOverlapList,List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,List<PermutationNumberUcscRefSeqGeneNumberOverlap> permutationNumberAllBasedKeggPathwayOverlapList,int overlapDefinition){
		int permutationNumberKeggPathwayNumber;
		short keggPathwayNumber;
		TShortList keggPathwayListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
			
			if (Commons.NCBI_GENE_ID.equals(type)){
				//There is overlap
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;
					}
					
					keggPathwayListContainingThisGeneId =  geneId2KeggPathwayNumbersMap.get(castedNode.getGeneEntrezId());
					
					
						//write EXON based results
						if (castedNode.getIntervalName().isExon()){							
							if(keggPathwayListContainingThisGeneId!=null){
								
								permutationNumberExonBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneNumberOverlap(permutationNumber,castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(),castedNode.getIntervalNumber(),castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));
								
								for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
									
									keggPathwayNumber = keggPathwayListContainingThisGeneId.get(i);											
									permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,keggPathwayNumber);
									
									
									if(!(permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap.containsKey(permutationNumberKeggPathwayNumber))){
										permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap.put(permutationNumberKeggPathwayNumber, 1);
									}
									
								}// End of For: for all keggpathways having this gene in their gene list
							} //End of If: keggPathWayListContainingThisGeneId is not null								
						}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						
						//write REGULATION based results
						//Regulation Based kegg pathway analysis
						if (castedNode.getIntervalName().isIntron() ||
							castedNode.getIntervalName().isFivePOne() ||
							castedNode.getIntervalName().isFivePTwo() ||
							castedNode.getIntervalName().isThreePOne() ||
							castedNode.getIntervalName().isThreePTwo()){
														
							if(keggPathwayListContainingThisGeneId!=null){
								
								permutationNumberRegulationBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneNumberOverlap(permutationNumber ,castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(), castedNode.getIntervalNumber(),castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));

								for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
									
									keggPathwayNumber = keggPathwayListContainingThisGeneId.get(i);
									permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,keggPathwayNumber);
	
									if(!(permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.containsKey(permutationNumberKeggPathwayNumber))){
										permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap.put(permutationNumberKeggPathwayNumber, 1);
									}
																			
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null					
						}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						
						
						//write ALL results							
						if(keggPathwayListContainingThisGeneId!=null){
							
							permutationNumberAllBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneNumberOverlap(permutationNumber, castedNode.getRefSeqGeneNumber(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));

							for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
								
								keggPathwayNumber = keggPathwayListContainingThisGeneId.get(i);
								permutationNumberKeggPathwayNumber = generatePermutationNumberCellLineNumberorGeneSetNumber(permutationNumber,keggPathwayNumber);
											
								if(!(permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap.containsKey(permutationNumberKeggPathwayNumber))){
									permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap.put(permutationNumberKeggPathwayNumber, 1);
								}
																
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null	
						
												
						
																
									
				}//End of if: there is overlap	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(permutationNumber,node.getLeft(),interval,chromName, geneId2KeggPathwayNumbersMap, permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap,permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap,permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap,type,permutationNumberExonBasedKeggPathwayOverlapList,permutationNumberRegulationBasedKeggPathwayOverlapList,permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(permutationNumber,node.getRight(),interval,chromName, geneId2KeggPathwayNumbersMap, permutationNumberExonBasedKeggPathwayNumber2OneorZeroMap,permutationNumberRegulationBasedKeggPathwayNumber2OneorZeroMap,permutationNumberAllBasedKeggPathwayNumber2OneorZeroMap,type,permutationNumberExonBasedKeggPathwayOverlapList,permutationNumberRegulationBasedKeggPathwayOverlapList,permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);		
			}
						
	}

//NEW FUNCIONALITY
	
	//with numbers ends
	//@todo
	
		//NEW FUNCIONALITY
		//EXON BASED KEGG PATHWAY
		//REGULATION BASED KEGG PATHWAY
		//ALL BASED KEGG PATHWAY
		//Empirical P Value Calculation
		//Search2 KeggPathway
		//For finding the number of each keggpathway:k for the given search input size: n
		//For each search input line, each kegg pathway will have a value of 1 or 0
		//These 1 or 0's will be accumulated in keggPathway2KMap	
		//without IO
		public void findAllOverlappingUcscRefSeqGenesIntervals(int permutationNumber,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,List<String>> geneId2KeggPathwayMap, Map<String,Integer> permutationNumberExonBasedKeggPathway2OneorZeroMap, Map<String,Integer> permutationNumberRegulationBasedKeggPathway2OneorZeroMap,Map<String,Integer> permutationNumberAllBasedKeggPathway2OneorZeroMap,String type,List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberExonBasedKeggPathwayOverlapList,List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberRegulationBasedKeggPathwayOverlapList,List<PermutationNumberUcscRefSeqGeneOverlap> permutationNumberAllBasedKeggPathwayOverlapList,int overlapDefinition){
			String permutationNumberKeggPathwayName = null;
			String keggPathwayName;
			List<String> keggPathwayListContainingThisGeneId = null;
			
			UcscRefSeqGeneIntervalTreeNode castedNode = null;
				
				if (Commons.NCBI_GENE_ID.equals(type)){
					//There is overlap
					if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
						
						if (node instanceof UcscRefSeqGeneIntervalTreeNode){
							castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
						}
						
						keggPathwayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
						
						
							//write exon based results
							if (castedNode.getIntervalName().isExon()){							
								if(keggPathwayListContainingThisGeneId!=null){
									
									permutationNumberExonBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneOverlap(Commons.PERMUTATION + permutationNumber,castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));

									
									for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
										
										keggPathwayName = keggPathwayListContainingThisGeneId.get(i);											
										permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;
										
										
										if(permutationNumberExonBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
											permutationNumberExonBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
										}
										
									}// End of For: for all keggpathways having this gene in their gene list
								} //End of If: keggPathWayListContainingThisGeneId is not null								
							}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

							
							//write regulation based results
							//Regulation Based kegg pathway analysis
							if (castedNode.getIntervalName().isIntron() ||
								castedNode.getIntervalName().isFivePOne() ||
								castedNode.getIntervalName().isFivePTwo() ||
								castedNode.getIntervalName().isThreePOne()||
								castedNode.getIntervalName().isThreePTwo()){
															
								if(keggPathwayListContainingThisGeneId!=null){
									
									permutationNumberRegulationBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneOverlap(Commons.PERMUTATION + permutationNumber ,castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));

									for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
										
										keggPathwayName = keggPathwayListContainingThisGeneId.get(i);
										permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;	
											
										if(permutationNumberRegulationBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
											permutationNumberRegulationBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
										}
																				
									}// End of For: for all kegg pathways having this gene in their gene list
								} // End of If:		keggPathWayListContainingThisGeneId is not null					
							}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

							
							
							//write all results							
							if(keggPathwayListContainingThisGeneId!=null){
								
								permutationNumberAllBasedKeggPathwayOverlapList.add(new PermutationNumberUcscRefSeqGeneOverlap(Commons.PERMUTATION + permutationNumber, castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathwayListContainingThisGeneId));

								for(int i= 0; i<keggPathwayListContainingThisGeneId.size(); i++){
									
									keggPathwayName = keggPathwayListContainingThisGeneId.get(i);
									permutationNumberKeggPathwayName = Commons.PERMUTATION + permutationNumber+ "_" + keggPathwayName;	
																				
									if(permutationNumberAllBasedKeggPathway2OneorZeroMap.get(permutationNumberKeggPathwayName)==null){
										permutationNumberAllBasedKeggPathway2OneorZeroMap.put(permutationNumberKeggPathwayName, 1);
									}
																	
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
													
							
																	
										
					}//End of if: there is overlap	
				} //End of If: type is NCBI_GENE_ID
					
				if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax()) ){
					findAllOverlappingUcscRefSeqGenesIntervals(permutationNumber,node.getLeft(),interval,chromName, geneId2KeggPathwayMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type,permutationNumberExonBasedKeggPathwayOverlapList,permutationNumberRegulationBasedKeggPathwayOverlapList,permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);	
				}
				
				if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
					findAllOverlappingUcscRefSeqGenesIntervals(permutationNumber,node.getRight(),interval,chromName, geneId2KeggPathwayMap, permutationNumberExonBasedKeggPathway2OneorZeroMap,permutationNumberRegulationBasedKeggPathway2OneorZeroMap,permutationNumberAllBasedKeggPathway2OneorZeroMap,type,permutationNumberExonBasedKeggPathwayOverlapList,permutationNumberRegulationBasedKeggPathwayOverlapList,permutationNumberAllBasedKeggPathwayOverlapList,overlapDefinition);		
				}
							
		}

	//NEW FUNCIONALITY
		
		
	//@todo hg19 refseq Gene Annotation with numbers starts
	public void findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers(
			String outputFolder,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName, 
			TIntShortMap geneAlternateNumber2OneorZeroMap, 
			String type, 
			int overlapDefinition,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		int geneAlternateNumber;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;						
					}
					
					geneAlternateNumber = castedNode.getGeneHugoSymbolNumber();
					
					try {
							if (bufferedWriter == null){
								fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_HG19_REFSEQ_GENE +"_" + Commons.HG19_REFSEQ_GENE + ".txt",true);
								bufferedWriter = new BufferedWriter(fileWriter);
								bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
								bufferedWriter.flush();
							}
																		
							
							if(!geneAlternateNumber2OneorZeroMap.containsKey(geneAlternateNumber)){
								geneAlternateNumber2OneorZeroMap.put(geneAlternateNumber, (short)1);
							}
							
							bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName().convertEnumtoString() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
							bufferedWriter.flush();	
																			
							
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}//End of IF: overlaps
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName,geneAlternateNumber2OneorZeroMap,type,overlapDefinition,geneHugoSymbolNumber2GeneHugoSymbolNameMap,refSeqGeneNumber2RefSeqGeneNameMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllGeneOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName,geneAlternateNumber2OneorZeroMap,type,overlapDefinition,geneHugoSymbolNumber2GeneHugoSymbolNameMap,refSeqGeneNumber2RefSeqGeneNameMap);	
				
			}
	}
	//@todo Gene Annotation with numbers ends
		
	//@todo for Annotation with Numbers starts
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(
			String outputFolder,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName, 
			TShortObjectMap<BufferedWriter> exonBasedGeneSetBufferedWriterHashMap, 
			TShortObjectMap<BufferedWriter> regulationBasedGeneSetBufferedWriterHashMap, 
			TShortObjectMap<BufferedWriter> allBasedGeneSetBufferedWriterHashMap, 
			TShortShortMap exonBasedGeneSet2OneorZeroMap, 
			TShortShortMap regulationBasedGeneSet2OneorZeroMap, 
			TShortShortMap allBasedGeneSet2OneorZeroMap, 
			String type, 
			int overlapDefinition,
			TShortObjectMap<String> geneSetNumber2GeneSetNameMap,
			TIntObjectMap<TShortList> geneId2ListofGeneSetNumberMap, 
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap,
			String geneSetName){
		
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		short geneSetNumber;
		TShortList geneSetNumberListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;						
					}
					
					geneSetNumberListContainingThisGeneId =  geneId2ListofGeneSetNumberMap.get(castedNode.getGeneEntrezId());
					
					try {
												
						//write exon based kegg pathway results
						if (castedNode.getIntervalName().isExon()){
							
							if(geneSetNumberListContainingThisGeneId!=null){
															
								for(TShortIterator it =geneSetNumberListContainingThisGeneId.iterator(); it.hasNext();){
									geneSetNumber = it.next();	
																		
									bufferedWriter = exonBasedGeneSetBufferedWriterHashMap.get(geneSetNumber);										
									
									if (bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + geneSetName + System.getProperty("file.separator") + Commons.EXON_BASED + geneSetName + System.getProperty("file.separator") +"_" + geneSetNumber2GeneSetNameMap.get(geneSetNumber) + ".txt",true);
																				
										bufferedWriter = new BufferedWriter(fileWriter);
										exonBasedGeneSetBufferedWriterHashMap.put(geneSetNumber, bufferedWriter);
										bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
										bufferedWriter.flush();
									}
																				
									
									if(!exonBasedGeneSet2OneorZeroMap.containsKey(geneSetNumber)){
										exonBasedGeneSet2OneorZeroMap.put(geneSetNumber, (short)1);
									}
									
									bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
																			
									
								}// End of For: for all keggpathways having this gene in their gene list
							} //End of If: keggPathWayListContainingThisGeneId is not null								
						}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						
						//write regulation based kegg pathway results
						if (castedNode.getIntervalName().isIntron() ||
							castedNode.getIntervalName().isFivePOne() ||
							castedNode.getIntervalName().isFivePTwo() ||
							castedNode.getIntervalName().isThreePOne()||
							castedNode.getIntervalName().isThreePTwo()){
							
							
							if(geneSetNumberListContainingThisGeneId!=null){
								
								for(TShortIterator it = geneSetNumberListContainingThisGeneId.iterator(); it.hasNext();){
									geneSetNumber = it.next();	
									
									bufferedWriter = regulationBasedGeneSetBufferedWriterHashMap.get(geneSetNumber);
									
									if (bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + geneSetName + System.getProperty("file.separator") + Commons.REGULATION_BASED + geneSetName + System.getProperty("file.separator") +"_" + geneSetNumber2GeneSetNameMap.get(geneSetNumber) + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										regulationBasedGeneSetBufferedWriterHashMap.put(geneSetNumber, bufferedWriter);
										bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
										bufferedWriter.flush();
									}
								
									
									if(!regulationBasedGeneSet2OneorZeroMap.containsKey(geneSetNumber)){
										regulationBasedGeneSet2OneorZeroMap.put(geneSetNumber, (short)1);
									}
									
									bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
										
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null					
						}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						
						//write all results
						if(geneSetNumberListContainingThisGeneId!=null){
							
							
							for(TShortIterator it = geneSetNumberListContainingThisGeneId.iterator(); it.hasNext();){
								geneSetNumber = it.next();	
																
								bufferedWriter = allBasedGeneSetBufferedWriterHashMap.get(geneSetNumber);
																
								if (bufferedWriter==null){
									fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATION + System.getProperty("file.separator") + geneSetName + System.getProperty("file.separator") + Commons.ALL_BASED + geneSetName + System.getProperty("file.separator") +"_" + geneSetNumber2GeneSetNameMap.get(geneSetNumber) + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									allBasedGeneSetBufferedWriterHashMap.put(geneSetNumber, bufferedWriter);
									bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
									bufferedWriter.flush();					
								}								
								
								if(!allBasedGeneSet2OneorZeroMap.containsKey(geneSetNumber)){
									allBasedGeneSet2OneorZeroMap.put(geneSetNumber,(short) 1);
								}
								
								bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
								bufferedWriter.flush();	
																	
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName,exonBasedGeneSetBufferedWriterHashMap, regulationBasedGeneSetBufferedWriterHashMap,allBasedGeneSetBufferedWriterHashMap,exonBasedGeneSet2OneorZeroMap,regulationBasedGeneSet2OneorZeroMap,allBasedGeneSet2OneorZeroMap,type,overlapDefinition,geneSetNumber2GeneSetNameMap,geneId2ListofGeneSetNumberMap,geneHugoSymbolNumber2GeneHugoSymbolNameMap,refSeqGeneNumber2RefSeqGeneNameMap,geneSetName);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName,exonBasedGeneSetBufferedWriterHashMap, regulationBasedGeneSetBufferedWriterHashMap,allBasedGeneSetBufferedWriterHashMap, exonBasedGeneSet2OneorZeroMap,regulationBasedGeneSet2OneorZeroMap,allBasedGeneSet2OneorZeroMap,type,overlapDefinition,geneSetNumber2GeneSetNameMap,geneId2ListofGeneSetNumberMap,geneHugoSymbolNumber2GeneHugoSymbolNameMap,refSeqGeneNumber2RefSeqGeneNameMap,geneSetName);	
				
			}
	}
	//@todo for Annotation with Numbers ends
		
		
		
	//@todo for Annotation with Numbers with OverlapList starts
	public void findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(
			String outputFolder,
			IntervalTreeNode node, 
			Interval interval, 
			ChromosomeName chromName, 
			TShortObjectMap<BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap, 
			TShortObjectMap<BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap, 
			TShortObjectMap<BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap, 
			TIntObjectMap<TShortList> geneId2ListofKeggPathwayNumberMap, 
			TShortShortMap exonBasedKeggPathway2OneorZeroMap, 
			TShortShortMap regulationBasedKeggPathway2OneorZeroMap, 
			TShortShortMap allBasedKeggPathway2OneorZeroMap, 
			String type, 
			List<UcscRefSeqGeneOverlapWithNumbers> exonBasedKeggPathwayOverlapList, 
			List<UcscRefSeqGeneOverlapWithNumbers> regulationBasedKeggPathwayOverlapList, 
			List<UcscRefSeqGeneOverlapWithNumbers> allBasedKeggPathwayOverlapList, 
			int overlapDefinition,
			TShortObjectMap<String> keggPathwayNumber2KeggPathwayNameMap,
			TIntObjectMap<String> geneHugoSymbolNumber2GeneHugoSymbolNameMap,
			TIntObjectMap<String> refSeqGeneNumber2RefSeqGeneNameMap){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		short keggPathwayNumber;
		TShortList keggPathWayNumberListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNodeWithNumbers castedNode = null;
		
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNodeWithNumbers){
						castedNode = (UcscRefSeqGeneIntervalTreeNodeWithNumbers) node;						
					}
					
					keggPathWayNumberListContainingThisGeneId =  geneId2ListofKeggPathwayNumberMap.get(castedNode.getGeneEntrezId());
					
					try {
												
						//write exon based kegg pathway results
						if (castedNode.getIntervalName().isExon()){
							
							if(keggPathWayNumberListContainingThisGeneId!=null){
								
								exonBasedKeggPathwayOverlapList.add(new UcscRefSeqGeneOverlapWithNumbers(castedNode.getRefSeqGeneNumber(), castedNode.getGeneHugoSymbolNumber(), castedNode.getGeneEntrezId(),keggPathWayNumberListContainingThisGeneId,castedNode.getIntervalName(), castedNode.getIntervalNumber(), node.getLow(), node.getHigh()));

								for(TShortIterator it =keggPathWayNumberListContainingThisGeneId.iterator(); it.hasNext();){
									keggPathwayNumber = it.next();	
									
										
									bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get(keggPathwayNumber);										
									
									if (bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS +"_" + keggPathwayNumber2KeggPathwayNameMap.get(keggPathwayNumber) + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										exonBasedKeggPathwayBufferedWriterHashMap.put(keggPathwayNumber, bufferedWriter);
										bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
										bufferedWriter.flush();
									}
																				
									
									if(!exonBasedKeggPathway2OneorZeroMap.containsKey(keggPathwayNumber)){
										exonBasedKeggPathway2OneorZeroMap.put(keggPathwayNumber, (short)1);
									}
									
									bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
																			
									
								}// End of For: for all keggpathways having this gene in their gene list
							} //End of If: keggPathWayListContainingThisGeneId is not null								
						}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						
						//write regulation based kegg pathway results
						if (castedNode.getIntervalName().isIntron() ||
							castedNode.getIntervalName().isFivePOne() ||
							castedNode.getIntervalName().isFivePTwo() ||
							castedNode.getIntervalName().isThreePOne()||
							castedNode.getIntervalName().isThreePTwo()){
							
							
							if(keggPathWayNumberListContainingThisGeneId!=null){
								
								regulationBasedKeggPathwayOverlapList.add(new UcscRefSeqGeneOverlapWithNumbers(castedNode.getRefSeqGeneNumber(),castedNode.getGeneHugoSymbolNumber(),castedNode.getGeneEntrezId(), keggPathWayNumberListContainingThisGeneId, castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getLow(), castedNode.getHigh()));

								for(TShortIterator it = keggPathWayNumberListContainingThisGeneId.iterator(); it.hasNext();){
									keggPathwayNumber = it.next();	
									
									bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get(keggPathwayNumber);
									
									if (bufferedWriter == null){
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS +"_" + keggPathwayNumber2KeggPathwayNameMap.get(keggPathwayNumber) + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										regulationBasedKeggPathwayBufferedWriterHashMap.put(keggPathwayNumber, bufferedWriter);
										bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
										bufferedWriter.flush();
									}

									
									
									if(!regulationBasedKeggPathway2OneorZeroMap.containsKey(keggPathwayNumber)){
										regulationBasedKeggPathway2OneorZeroMap.put(keggPathwayNumber, (short)1);
									}
									
									bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
										
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null					
						}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						
						//write all results
						if(keggPathWayNumberListContainingThisGeneId!=null){
							
							allBasedKeggPathwayOverlapList.add(new UcscRefSeqGeneOverlapWithNumbers(castedNode.getRefSeqGeneNumber(),castedNode.getGeneHugoSymbolNumber(),castedNode.getGeneEntrezId(),keggPathWayNumberListContainingThisGeneId, castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getLow(), castedNode.getHigh()));
							
							for(TShortIterator it = keggPathWayNumberListContainingThisGeneId.iterator(); it.hasNext();){
								keggPathwayNumber = it.next();	
								
									
								bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get(keggPathwayNumber);
								
								
								if (bufferedWriter==null){
									fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS + "_" + keggPathwayNumber2KeggPathwayNameMap.get(keggPathwayNumber) + ".txt",true);
									bufferedWriter = new BufferedWriter(fileWriter);
									allBasedKeggPathwayBufferedWriterHashMap.put(keggPathwayNumber, bufferedWriter);
									bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
									bufferedWriter.flush();					
								}
								
								
								if(!allBasedKeggPathway2OneorZeroMap.containsKey(keggPathwayNumber)){
									allBasedKeggPathway2OneorZeroMap.put(keggPathwayNumber,(short) 1);
								}
								
								bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + ChromosomeName.convertEnumtoString(castedNode.getChromName())+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + refSeqGeneNumber2RefSeqGeneNameMap.get(castedNode.getRefSeqGeneNumber())+ "\t" + castedNode.getIntervalName() + "\t" + geneHugoSymbolNumber2GeneHugoSymbolNameMap.get(castedNode.getGeneHugoSymbolNumber())+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
								bufferedWriter.flush();	
																	
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,node.getLeft(),interval,chromName,exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,regulationBasedKeggPathway2OneorZeroMap,allBasedKeggPathway2OneorZeroMap,type,exonBasedKeggPathwayOverlapList,regulationBasedKeggPathwayOverlapList,allBasedKeggPathwayOverlapList,overlapDefinition,keggPathwayNumber2KeggPathwayNameMap,geneHugoSymbolNumber2GeneHugoSymbolNameMap,refSeqGeneNumber2RefSeqGeneNameMap);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervalsWithNumbers(outputFolder,node.getRight(),interval,chromName,exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap, geneId2ListofKeggPathwayNumberMap, exonBasedKeggPathway2OneorZeroMap,regulationBasedKeggPathway2OneorZeroMap,allBasedKeggPathway2OneorZeroMap,type,exonBasedKeggPathwayOverlapList,regulationBasedKeggPathwayOverlapList,allBasedKeggPathwayOverlapList,overlapDefinition,keggPathwayNumber2KeggPathwayNameMap,geneHugoSymbolNumber2GeneHugoSymbolNameMap,refSeqGeneNumber2RefSeqGeneNameMap);	
				
			}
	}
	//New Functionality ends	
	//@todo for Annotation with Numbers with OverlapList ends	
		
		
	//New Functionality starts
	//Search2 Kegg Pathway
	//Search for TF
	//Search for KEGG Pathways (exon based, regulation based, all based)
	//Search for TF and KEGG Pathways (tf and exonBased, tf and regulationBased,tf and allBased)	
	//will be modified	
	public void findAllOverlappingUcscRefSeqGenesIntervals(String outputFolder,IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> exonBasedKeggPathwayBufferedWriterHashMap, Map<String,BufferedWriter> regulationBasedKeggPathwayBufferedWriterHashMap, Map<String,BufferedWriter> allBasedKeggPathwayBufferedWriterHashMap, Map<String,List<String>> geneId2KeggPathwayMap, List<String> keggPathwayNameList, Map<String,Integer> exonBasedKeggPathway2OneorZeroMap,Map<String,Integer> regulationBasedKeggPathway2OneorZeroMap, Map<String,Integer> allBasedKeggPathway2OneorZeroMap, String type, List<UcscRefSeqGeneOverlap> exonBasedKeggPathwayOverlapList, List<UcscRefSeqGeneOverlap> regulationBasedKeggPathwayOverlapList, List<UcscRefSeqGeneOverlap> allBasedKeggPathwayOverlapList, int overlapDefinition){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNode castedNode = null;
		
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh(),overlapDefinition)){
					
					if (node instanceof UcscRefSeqGeneIntervalTreeNode){
						castedNode = (UcscRefSeqGeneIntervalTreeNode) node;						
					}
					
					keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
					
					try {
												
						//write exon based kegg pathway results
						if (castedNode.getIntervalName().isExon()){
							
							if(keggPathWayListContainingThisGeneId!=null){
								
								exonBasedKeggPathwayOverlapList.add(new UcscRefSeqGeneOverlap(castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), node.getLow(), node.getHigh(),keggPathWayListContainingThisGeneId));

								for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get(i);	
									
									if (keggPathwayNameList.contains(keggPathwayName)){
										
										bufferedWriter = exonBasedKeggPathwayBufferedWriterHashMap.get(keggPathwayName);										
										
										if (bufferedWriter == null){
											fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS +"_exonBased_" + keggPathwayName + ".txt",true);
											bufferedWriter = new BufferedWriter(fileWriter);
											exonBasedKeggPathwayBufferedWriterHashMap.put(keggPathwayName, bufferedWriter);
											bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
											bufferedWriter.flush();
										}
																					
										
										if(exonBasedKeggPathway2OneorZeroMap.get(keggPathwayName)==null){
											exonBasedKeggPathway2OneorZeroMap.put(keggPathwayName, 1);
										}
										
										bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
										
									
									}//If this keggPathwayName is in  keggPathwayNameList
									
									
								}// End of For: for all keggpathways having this gene in their gene list
							} //End of If: keggPathWayListContainingThisGeneId is not null								
						}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						
						//write regulation based kegg pathway results
						if (castedNode.getIntervalName().isIntron() ||
							castedNode.getIntervalName().isFivePOne() ||
							castedNode.getIntervalName().isFivePTwo() ||
							castedNode.getIntervalName().isThreePOne()||
							castedNode.getIntervalName().isThreePTwo()){
							
							
							if(keggPathWayListContainingThisGeneId!=null){
								
								regulationBasedKeggPathwayOverlapList.add(new UcscRefSeqGeneOverlap(castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathWayListContainingThisGeneId));

								for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get(i);	
									
									if (keggPathwayNameList.contains(keggPathwayName)){
										
										bufferedWriter = regulationBasedKeggPathwayBufferedWriterHashMap.get(keggPathwayName);
										
										if (bufferedWriter == null){
											fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS +"_regulationBased_" + keggPathwayName + ".txt",true);
											bufferedWriter = new BufferedWriter(fileWriter);
											regulationBasedKeggPathwayBufferedWriterHashMap.put(keggPathwayName, bufferedWriter);
											bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
											bufferedWriter.flush();
										}

										
										
										if(regulationBasedKeggPathway2OneorZeroMap.get(keggPathwayName)==null){
											regulationBasedKeggPathway2OneorZeroMap.put(keggPathwayName, 1);
										}
										
										bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
										

									}//If this keggPathwayName is in  keggPathwayNameList
									
									
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null					
						}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						
						//write all results
						if(keggPathWayListContainingThisGeneId!=null){
							
							allBasedKeggPathwayOverlapList.add(new UcscRefSeqGeneOverlap(castedNode.getRefSeqGeneName(), castedNode.getIntervalName(), castedNode.getIntervalNumber(), castedNode.getGeneHugoSymbol(), castedNode.getGeneEntrezId(), castedNode.getLow(), castedNode.getHigh(),keggPathWayListContainingThisGeneId));
							
							for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
								keggPathwayName = keggPathWayListContainingThisGeneId.get(i);	
								
								if (keggPathwayNameList.contains(keggPathwayName)){			
									
									bufferedWriter = allBasedKeggPathwayBufferedWriterHashMap.get(keggPathwayName);
									
									
									if (bufferedWriter==null){
										fileWriter = FileOperations.createFileWriter(outputFolder + Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS +"_allBased_" + keggPathwayName + ".txt",true);
										bufferedWriter = new BufferedWriter(fileWriter);
										allBasedKeggPathwayBufferedWriterHashMap.put(keggPathwayName, bufferedWriter);
										bufferedWriter.write("Searched for chr" + "\t" + "interval Low" + "\t" + "interval High" + "\t" + "ucscRefSeqGene node ChromName" + "\t" +  "node Low" + "\t" + "node High" + "\t" + "node RefSeqGeneName"+ "\t" + "node IntervalName" + "\t" + "node GeneHugoSymbol"+ "\t"+ "node GeneEntrezId" +System.getProperty("line.separator"));
										bufferedWriter.flush();					
									}
									
									
									if(allBasedKeggPathway2OneorZeroMap.get(keggPathwayName)==null){
										allBasedKeggPathway2OneorZeroMap.put(keggPathwayName, 1);
									}
									
									bufferedWriter.write(chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh()  + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
									bufferedWriter.flush();	
									

								}//If this keggPathwayName is in  keggPathwayNameList
								
								
							}// End of For: for all kegg pathways having this gene in their gene list
						} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,node.getLeft(),interval,chromName,exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap,geneId2KeggPathwayMap, keggPathwayNameList, exonBasedKeggPathway2OneorZeroMap,regulationBasedKeggPathway2OneorZeroMap,allBasedKeggPathway2OneorZeroMap,type,exonBasedKeggPathwayOverlapList,regulationBasedKeggPathwayOverlapList,allBasedKeggPathwayOverlapList,overlapDefinition);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals(outputFolder,node.getRight(),interval,chromName,exonBasedKeggPathwayBufferedWriterHashMap, regulationBasedKeggPathwayBufferedWriterHashMap,allBasedKeggPathwayBufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList, exonBasedKeggPathway2OneorZeroMap,regulationBasedKeggPathway2OneorZeroMap,allBasedKeggPathway2OneorZeroMap,type,exonBasedKeggPathwayOverlapList,regulationBasedKeggPathwayOverlapList,allBasedKeggPathwayOverlapList,overlapDefinition);	
				
			}
	}
	//New Functionality ends	
	//Search2 Kegg Pathway	
	
	
	//Search2 KeggPathway
	//For finding the number of each keggpathway:k for the given search input size: n
	//For each search input line, each kegg pathway will have a value of 1 or 0
	//These 1 or 0's will be accumulated in keggPathway2KMap		
	public void findAllOverlappingUcscRefSeqGenesIntervals(IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,List<String>> geneId2KeggPathwayMap, List<String> keggPathwayNameList, Map<String,Integer> keggPathway2OneorZeroMap, String type, KeggPathwayAnalysisType keggPathwayAnalysisType){
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		
		String keggPathwayName = null;
		List<String> keggPathWayListContainingThisGeneId = null;
		
		UcscRefSeqGeneIntervalTreeNode castedNode = null;
			
			if (Commons.NCBI_GENE_ID.equals(type)){
				
				if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
					try {
							
						if (node instanceof UcscRefSeqGeneIntervalTreeNode){
							castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
							
						}
								
						//write exon based results
						if (keggPathwayAnalysisType.isExonBasedKeggPathwayAnalysis()){
							
							//exon based kegg pathway analysis
							if (castedNode.getIntervalName().isExon()){
								
								keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
								
								if(keggPathWayListContainingThisGeneId!=null){
									for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
										keggPathwayName = keggPathWayListContainingThisGeneId.get(i);	
										
										if (keggPathwayNameList.contains(keggPathwayName)){
											
											bufferedWriter = bufferedWriterHashMap.get(keggPathwayName);
											
											if (bufferedWriter == null){
												fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_EXON_BASED_KEGG_PATHWAY_ANALYSIS +"_exonBased_" + keggPathwayName + ".txt");
												bufferedWriter = new BufferedWriter(fileWriter);
												bufferedWriterHashMap.put(keggPathwayName, bufferedWriter);
												
											}
											
											if(keggPathway2OneorZeroMap.get(keggPathwayName)==null){
												keggPathway2OneorZeroMap.put(keggPathwayName, 1);
											}
											
											bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
											bufferedWriter.flush();	
										}//If this keggPathwayName is in  keggPathwayNameList
										
										
									}// End of For: for all keggpathways having this gene in their gene list
								} //End of If: keggPathWayListContainingThisGeneId is not null								
							}// End of If: Exon Based Kegg Pathway Analysis, Overlapped node is an exon

						}
						//write regulation based results
						else if (keggPathwayAnalysisType.isRegulationBasedKeggPathwayAnalysis()){
							//Regulation Based kegg pathway analysis
							if (castedNode.getIntervalName().isIntron() ||
								castedNode.getIntervalName().isFivePOne() ||
								castedNode.getIntervalName().isFivePTwo() ||
								castedNode.getIntervalName().isThreePOne()||
								castedNode.getIntervalName().isThreePTwo()){
								
								keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
								
								if(keggPathWayListContainingThisGeneId!=null){
									for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
										keggPathwayName = keggPathWayListContainingThisGeneId.get(i);	
										
										if (keggPathwayNameList.contains(keggPathwayName)){
											
											bufferedWriter = bufferedWriterHashMap.get(keggPathwayName);
											
											if (bufferedWriter == null){
												fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_REGULATION_BASED_KEGG_PATHWAY_ANALYSIS +"_regulationBased_" + keggPathwayName + ".txt");
												bufferedWriter = new BufferedWriter(fileWriter);
												bufferedWriterHashMap.put(keggPathwayName, bufferedWriter);
												
											}
											
											if(keggPathway2OneorZeroMap.get(keggPathwayName)==null){
												keggPathway2OneorZeroMap.put(keggPathwayName, 1);
											}
											
											bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
											bufferedWriter.flush();	
										}//If this keggPathwayName is in  keggPathwayNameList
										
										
									}// End of For: for all kegg pathways having this gene in their gene list
								} // End of If:		keggPathWayListContainingThisGeneId is not null					
							}//End of If: Regulation Based kegg pathway Analysis, Overlapped node is an intron, 5P1, 5P2, 3P1, 3P2

						}
						//write all results
						else{
							keggPathWayListContainingThisGeneId =  geneId2KeggPathwayMap.get(castedNode.getGeneEntrezId().toString());
							
							if(keggPathWayListContainingThisGeneId!=null){
								for(int i= 0; i<keggPathWayListContainingThisGeneId.size(); i++){
									keggPathwayName = keggPathWayListContainingThisGeneId.get(i);	
									
									if (keggPathwayNameList.contains(keggPathwayName)){
										
										bufferedWriter = bufferedWriterHashMap.get(keggPathwayName);
										
										if (bufferedWriter == null){
											fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_ALL_BASED_KEGG_PATHWAY_ANALYSIS +"_all_" + keggPathwayName + ".txt");
											bufferedWriter = new BufferedWriter(fileWriter);
											bufferedWriterHashMap.put(keggPathwayName, bufferedWriter);
											
										}
										
										if(keggPathway2OneorZeroMap.get(keggPathwayName)==null){
											keggPathway2OneorZeroMap.put(keggPathwayName, 1);
										}
										
										bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
										bufferedWriter.flush();	
									}//If this keggPathwayName is in  keggPathwayNameList
									
									
								}// End of For: for all kegg pathways having this gene in their gene list
							} // End of If:		keggPathWayListContainingThisGeneId is not null	
							
						}
												
						
																
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}	
			} //End of If: type is NCBI_GENE_ID
				
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervals((UcscRefSeqGeneIntervalTreeNode)node.getLeft(),interval,chromName,bufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList, keggPathway2OneorZeroMap,type,keggPathwayAnalysisType);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals((UcscRefSeqGeneIntervalTreeNode)node.getRight(),interval,chromName,bufferedWriterHashMap, geneId2KeggPathwayMap, keggPathwayNameList, keggPathway2OneorZeroMap,type,keggPathwayAnalysisType);	
				
			}
	}
		

//Search2
public void findAllOverlappingUcscRefSeqGenesIntervals(IntervalTreeNode node, Interval interval, ChromosomeName chromName, Map<String,BufferedWriter> bufferedWriterHashMap, Map<String,Integer> nameorIdHashMap, String type){
	FileWriter fileWriter = null;
	BufferedWriter bufferedWriter = null;
	Integer count ;
	
	UcscRefSeqGeneIntervalTreeNode castedNode = null;
	
	try {
		
		if (node.getNodeName().isNotSentinel()){
			
			if (node instanceof UcscRefSeqGeneIntervalTreeNode){
				castedNode = (UcscRefSeqGeneIntervalTreeNode) node;				
			}
			
			
			if (Commons.NCBI_GENE_ID.equals(type)){
				if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && nameorIdHashMap.containsKey(castedNode.getGeneEntrezId().toString())){
						
					bufferedWriter = bufferedWriterHashMap.get(castedNode.getGeneEntrezId().toString());
					count =  nameorIdHashMap.get(castedNode.getGeneEntrezId().toString());
					
					if (bufferedWriter == null){
						fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_GENE_ID +"_" +String.valueOf(castedNode.getGeneEntrezId()) + ".txt");
						bufferedWriter = new BufferedWriter(fileWriter);
						bufferedWriterHashMap.put(castedNode.getGeneEntrezId().toString(), bufferedWriter);
					
					}							
					
					
					
					bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
					bufferedWriter.flush();					
										
					count++;
					nameorIdHashMap.put(castedNode.getGeneEntrezId().toString(), count);
				}	
			}else if (Commons.NCBI_RNA_NUCLEOTIDE_ACCESSION_VERSION.equals(type)){
				if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) &&nameorIdHashMap.containsKey(castedNode.getRefSeqGeneName())){

						bufferedWriter = bufferedWriterHashMap.get(castedNode.getRefSeqGeneName());
						count =  nameorIdHashMap.get(castedNode.getRefSeqGeneName());
						
						if (bufferedWriter == null){
							fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_NCBI_RNA +"_" + castedNode.getRefSeqGeneName() + ".txt");
							bufferedWriter = new BufferedWriter(fileWriter);
							bufferedWriterHashMap.put(castedNode.getRefSeqGeneName(), bufferedWriter);							
						}
													
						bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName() + "\t" + castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
						bufferedWriter.flush();		
						
						count++;
						nameorIdHashMap.put(castedNode.getRefSeqGeneName(), count);
										
				}	
			}else if(Commons.UCSC_GENE_ALTERNATE_NAME.equals(type)){
				if (overlaps(castedNode.getLow(), castedNode.getHigh(), interval.getLow(), interval.getHigh()) && nameorIdHashMap.containsKey(castedNode.getGeneHugoSymbol())){
				
						bufferedWriter = bufferedWriterHashMap.get(castedNode.getGeneHugoSymbol());
						count =  nameorIdHashMap.get(castedNode.getGeneHugoSymbol());
						
						if (bufferedWriter == null){
							fileWriter = FileOperations.createFileWriter(Commons.ANNOTATE_INTERVALS_USING_INTERVAL_TREE_OUTPUT_FILE_PATH_FOR_UCSC_GENE_ALTERNATE_NAME +"_" + castedNode.getGeneHugoSymbol() + ".txt");
							bufferedWriter = new BufferedWriter(fileWriter);
							bufferedWriterHashMap.put(castedNode.getGeneHugoSymbol(), bufferedWriter);							
						}
												
						bufferedWriter.write("Searched for" + "\t" + chromName.convertEnumtoString() + "\t" + interval.getLow() + "\t" + interval.getHigh() + "\t" + "ucscRefSeqGene" + "\t" + castedNode.getChromName()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName()+ "\t" +  castedNode.getIntervalName() + "\t" + castedNode.getGeneHugoSymbol()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
						bufferedWriter.flush();					
						
						count++;
						nameorIdHashMap.put(castedNode.getGeneHugoSymbol(), count);
				}	
			}

			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervals((UcscRefSeqGeneIntervalTreeNode)node.getLeft(),interval,chromName, bufferedWriterHashMap, nameorIdHashMap,type);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals((UcscRefSeqGeneIntervalTreeNode)node.getRight(),interval,chromName, bufferedWriterHashMap, nameorIdHashMap,type);	
				
			}
		} // If node is not null
					
		
	} catch (IOException e) {
		e.printStackTrace();
	}		
}	






	//Search1
	public void findAllOverlappingUcscRefSeqGenesIntervals(IntervalTreeNode node, Interval interval, BufferedWriter bufferedWriter,List<IntervalTreeNode> overlappingNodeList){
		
			UcscRefSeqGeneIntervalTreeNode castedNode = null;
		
			if (overlaps(node.getLow(), node.getHigh(), interval.getLow(), interval.getHigh())){
				
				overlappingNodeList.add(node);
				
				if (node instanceof UcscRefSeqGeneIntervalTreeNode){
					castedNode = (UcscRefSeqGeneIntervalTreeNode) node;
				}
				
				try {
					bufferedWriter.write("ucscRefSeqGene" + "\t" + castedNode.getChromName().toString()+ "\t" +  castedNode.getLow() + "\t" + castedNode.getHigh() + "\t" + castedNode.getRefSeqGeneName().toString()+ "\t" + castedNode.getIntervalName().toString() + "\t" + castedNode.getGeneHugoSymbol().toString()+ "\t"+ castedNode.getGeneEntrezId() +System.getProperty("line.separator"));
					bufferedWriter.flush();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
			
			
			if((node.getLeft().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getLeft().getMax())){
				findAllOverlappingUcscRefSeqGenesIntervals((UcscRefSeqGeneIntervalTreeNode)node.getLeft(),interval,bufferedWriter,overlappingNodeList);	
			}
			
			if((node.getRight().getNodeName().isNotSentinel()) && (interval.getLow()<=node.getRight().getMax()) && (node.getLow()<=interval.getHigh())){
				findAllOverlappingUcscRefSeqGenesIntervals((UcscRefSeqGeneIntervalTreeNode)node.getRight(),interval,bufferedWriter,overlappingNodeList);	
				
			}
						
	}
	
	public void printOverlappingIntervalsList(List<IntervalTreeNode> list){
		Iterator<IntervalTreeNode> itr = list.iterator();
		
		while(itr.hasNext()){
			IntervalTreeNode  node =(IntervalTreeNode) itr.next();			
			GlanetRunner.appendLog(node.getLow() + "\t" + node.getHigh());
			
		}
	}
	
	
	public static void printPath(Path path){
		IntervalTreeNode node;
		int numberofBlackNodes = 0;
		
		GlanetRunner.appendLog("One Path Starts");
		
		for(int i = 0; i<path.getNodeList().size(); i++){
			node = path.getNodeList().get(i);
			
			if (node.getColor() == Commons.BLACK)
				numberofBlackNodes++;
			
			GlanetRunner.appendLog("Node(" + node.getLow() + "," + node.getHigh() + ",Max: " + node.getMax() + ") Color: "+node.getColor() +" Name: "+ node.getNodeName());
			
		}
		
		
		GlanetRunner.appendLog("--- Number of black nodes: " + numberofBlackNodes);
		
	}
	
	public static void findAllPathsFromRootToLeavesUsingDepthFirstSearch(IntervalTreeNode node,IntervalTreeProperties properties,Path path , List<Path> allPathsFromRootToLeaves){
		
		//Null node
		if (node==null){
				return;
		}	
		// SENTINEL or NOT_SENTINEL_NODE
		else {
			//SENTINEL NODE
			if (node.getNodeName().isSentinel()){
		
				 if (node.getColor()!=Commons.BLACK){
					 properties.setEverySentinelLeafIsBlack(false);
						 return;
				 }
				 
				 path.getNodeList().add(node);
				  
			 	 if (node.getColor()==Commons.BLACK){
			 		 path.setNumberofBlackNodes(path.getNumberofBlackNodes()+1);
			 	 }
			 	 
			 	 //A leaf has been reached
			 	 //Create a new Path object
			 	 //Since path instance changes rapidly during recursion
			 	 
			 	 Path onePathFromRootToLeaf = new Path(path.getNumberofBlackNodes(),path.getNodeList());			 	 
			 	 //printPath(onePathFromRootToLeaf);				 		
		 		 allPathsFromRootToLeaves.add(onePathFromRootToLeaf);		 		 		
			}
			 
		 	//NOT SENTINEL NODE
			 else if (node.getNodeName().isNotSentinel()){
				 	if (node.getColor()!= Commons.RED && node.getColor()!= Commons.BLACK ){
				 			properties.setEveryNodeIsEitherRedorBlack(false);
				 			return;
				 	}
				 	
				 	if (node.getColor()==Commons.RED){
				 		if (node.getLeft().getColor()!=Commons.BLACK && node.getRight().getColor()!=Commons.BLACK){
				 			properties.setEveryRedNodeHasBlackChildren(false);
				 			return;
				 		}
				 	}
				 	
				 	if (node.getMax()!=max(node)){
				 		properties.setEveryNotSentinelNodeHasRightMaxValue(false);
				 		return;
				 		
				 	}
					
				 	 path.getNodeList().add(node);
				 	 
				 	 if (node.getColor()==Commons.BLACK){
				 		 path.setNumberofBlackNodes(path.getNumberofBlackNodes()+1);
				 	 }
				 	 
				 	
				 	findAllPathsFromRootToLeavesUsingDepthFirstSearch(node.getLeft(), properties,path,allPathsFromRootToLeaves);
				 	findAllPathsFromRootToLeavesUsingDepthFirstSearch(node.getRight(), properties,path,allPathsFromRootToLeaves);
				 	
			 }		 
		} //SENTINEL or NOT_SENTINEL node
		
		 path.getNodeList().remove(node);
	 	 
	 	if (node.getColor()==Commons.BLACK){
	 		 path.setNumberofBlackNodes(path.getNumberofBlackNodes()-1);
	 	 }
	}
	
	
	public static void checkNumberofBlackNodesinAllPathsFromRootToLeaves(IntervalTreeProperties properties, List<Path> allPaths){
		int numberofBlackNodes =0;
	
		if (allPaths.size()>0){
			numberofBlackNodes = allPaths.get(0).getNumberofBlackNodes();
		}
			
			
		for(int i=1; i<allPaths.size();i++){
			if (numberofBlackNodes!=allPaths.get(i).getNumberofBlackNodes()){
				properties.setAllPathsfromRoottoLeavesHasSameNumberofBlackNodes(false);
				break;
			}				
		}
	}
	/*
	 * Traverse the tree using depth first search 
	 * And check whether the properties hold or not
	 */
	
	public static void checkProperties(IntervalTree tree,IntervalTreeProperties properties){
		Path path = new Path();
		List<Path> allPathsFromRoottoLeaves = new ArrayList<Path>();
		
		findAllPathsFromRootToLeavesUsingDepthFirstSearch(tree.getRoot(),properties,path,allPathsFromRoottoLeaves);
		
		checkNumberofBlackNodesinAllPathsFromRootToLeaves(properties,allPathsFromRoottoLeaves);
	
		return;
	}
	
	/*
	 * Check for whether the given tree holds the red black tree properties
	 * 
	 *  Red black tree properties:
	 * 1. Every node is either red or black.
	 * 2. The root is black.
	 * 3. Every leaf(nil[T]) is black.
	 * 4. If a node is red, then both its children are black. (Hence no two reds in a row on a simple path from the root to a leaf.)
	 * 5. For each node, all paths from the node to descendant leaves contain the same number of black nodes.
	 * 
	 * 
	 */
	public static boolean checkIntervalTreePropertiesHolds(IntervalTree tree){
		
		boolean intevalTreePropertiesHold = true;
		
		IntervalTreeProperties properties = new IntervalTreeProperties();
		
		if (tree.getRoot().getColor()!=Commons.BLACK)
			properties.setRootIsBlack(false);
			
		checkProperties(tree,properties);
		
		intevalTreePropertiesHold = properties.isEveryNodeIsEitherRedorBlack() && 
									properties.isRootIsBlack() && 
									properties.isEverySentinelLeafIsBlack()  && 
									properties.isEveryRedNodeHasBlackChildren() && 
									properties.isAllPathsfromRoottoLeavesHasSameNumberofBlackNodes() && 
									properties.isEveryNotSentinelNodeHasRightMaxValue();
		
		return intevalTreePropertiesHold;
		
	}
	
	public static void intervalTreeInsertDelete(IntervalTree tree,IntervalTreeNode node, String operation){
		
		boolean isIntervalTreePropertiesHold;
		IntervalTreeNode splicedOutNode;
		
		if (Commons.INSERT.equals(operation)){
			GlanetRunner.appendLog("After insert node (" + node.getLow() +"," + node.getHigh() +")");
			tree.intervalTreeInsert(tree, node);
			GlanetRunner.appendLog("Tree Root color: " + tree.getRoot().getColor()+ " Tree Root Low: " + tree.getRoot().getLow() + " Tree Root High: " +tree.getRoot().getHigh() + " Tree Root Max: " + tree.getRoot().getMax() + " Tree Root's Parent's Name: " + tree.getRoot().getParent().getNodeName() );
			tree.intervalTreeInfixTraversal(tree.getRoot());
			isIntervalTreePropertiesHold = checkIntervalTreePropertiesHolds(tree);
			GlanetRunner.appendLog("Does the interval tree properties hold? " + isIntervalTreePropertiesHold);
			
			
		}else if(Commons.DELETE.equals(operation)){
			GlanetRunner.appendLog("After delete node (" + node.getLow() +"," + node.getHigh() +")");
			splicedOutNode = tree.intervalTreeDelete(tree, node);
			splicedOutNode = null;
			GlanetRunner.appendLog("Tree Root color: " + tree.getRoot().getColor()+ " Tree Root Low: " + tree.getRoot().getLow() + " Tree Root High: " +tree.getRoot().getHigh() + " Tree Root Max: " + tree.getRoot().getMax() + " Tree Root's Parent's Name: " + tree.getRoot().getParent().getNodeName() );
			tree.intervalTreeInfixTraversal(tree.getRoot());
			isIntervalTreePropertiesHold = checkIntervalTreePropertiesHolds(tree);
			GlanetRunner.appendLog("Does the interval tree properties hold? " + isIntervalTreePropertiesHold);
			
		}
		
		GlanetRunner.appendLog("-------------------------------------------------------------------------------");
		
		
	}
	
	
	/*
	 * IntervalTree is composed of sentinel and not sentinel nodes.
	 * Sentinel nodes are the leaf nodes.
	 * Sentinel nodes have left and right nodes null.
	 * 
	 * Not sentinel nodes are the not leaf nodes.
	 * Not sentinel nodes have not null left and  right nodes.
	 *
	 */
	public static void deleteNodesofIntervalTree(IntervalTreeNode node){
		if(node.getNodeName().isNotSentinel()){
			deleteNodesofIntervalTree(node.getLeft());
			deleteNodesofIntervalTree(node.getRight());
			node.setChromName(null);
			node.setLeft(null);
			node.setRight(null);
			node.setParent(null);
			node.setNodeName(null);
			node.setNodeType(null);
			node=null;
		}else if(node.getNodeName().isSentinel()){
			node.setParent(null);
			node =null;
		}
		
	}
	
	
	public static void main(String[] args) {
		
		IntervalTree tree = new IntervalTree();
		boolean isIntervalTreePropertiesHold;

		//Check whether interval tree properties hold when the tree is null?
		isIntervalTreePropertiesHold = checkIntervalTreePropertiesHolds(tree);
		
		
		List<IntervalTreeNode> resultList ;
		
		IntervalTreeNode node1 = new IntervalTreeNode(10,15);
		IntervalTreeNode node_1 = new IntervalTreeNode(10,15);
		IntervalTreeNode node_2 = new IntervalTreeNode(10,15);
		IntervalTreeNode node2 = new IntervalTreeNode(5,40);
		IntervalTreeNode node3 = new IntervalTreeNode(15,60);
		IntervalTreeNode node4 = new IntervalTreeNode(2,80);
		IntervalTreeNode node5 = new IntervalTreeNode(1,100);
		IntervalTreeNode node6 = new IntervalTreeNode(3,150);
		IntervalTreeNode node7 = new IntervalTreeNode(6,36);
		IntervalTreeNode node8 = new IntervalTreeNode(7,77);
		IntervalTreeNode node9 = new IntervalTreeNode(8,200);
		IntervalTreeNode node10 = new IntervalTreeNode(4,20);
		IntervalTreeNode node11 = new IntervalTreeNode(40,140);
		IntervalTreeNode node12 = new IntervalTreeNode(60,120);
		IntervalTreeNode node13 = new IntervalTreeNode(50,150);
		IntervalTreeNode node14 = new IntervalTreeNode(55,90);		
		IntervalTreeNode node15 = new IntervalTreeNode(30,40);
		
		intervalTreeInsertDelete(tree,node1, Commons.INSERT);
		intervalTreeInsertDelete(tree,node_1, Commons.INSERT);
		intervalTreeInsertDelete(tree,node_2, Commons.INSERT);
		intervalTreeInsertDelete(tree,node2, Commons.INSERT);
		intervalTreeInsertDelete(tree,node3, Commons.INSERT);
		intervalTreeInsertDelete(tree,node4, Commons.INSERT);
		intervalTreeInsertDelete(tree,node5, Commons.INSERT);
		intervalTreeInsertDelete(tree,node6, Commons.INSERT);
		intervalTreeInsertDelete(tree,node7, Commons.INSERT);
		intervalTreeInsertDelete(tree,node8, Commons.INSERT);
		intervalTreeInsertDelete(tree,node9, Commons.INSERT);
		intervalTreeInsertDelete(tree,node10, Commons.INSERT);
		intervalTreeInsertDelete(tree,node11, Commons.INSERT);
		intervalTreeInsertDelete(tree,node12, Commons.INSERT);
		intervalTreeInsertDelete(tree,node13, Commons.INSERT);
		intervalTreeInsertDelete(tree,node14, Commons.INSERT);
			
		
		intervalTreeInsertDelete(tree,node2, Commons.DELETE);
		intervalTreeInsertDelete(tree,node8, Commons.DELETE);
		intervalTreeInsertDelete(tree,node12, Commons.DELETE);
		intervalTreeInsertDelete(tree,node4, Commons.DELETE);
		intervalTreeInsertDelete(tree,node14, Commons.DELETE);
		intervalTreeInsertDelete(tree,node_1, Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,node2, Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		

		//Deletion of erroneous data: no NOT_SENTINEL node has been left
//		intervalTreeInsertDelete(tree,tree.getRoot(), Commons.DELETE);
		
		
		//Deletion of erroneous data: non existing node
//		GlanetRunner.appendLog("After delete node (" + node15.getLow() +"," + node15.getHigh() +")");
//		node = tree.intervalTreeDelete(tree, node15);
//		node = null;
//		GlanetRunner.appendLog("Tree root name: "+ tree.getRoot().getNodeName() + " Tree Root color: " + tree.getRoot().getColor()+ " Tree Root Low: " + tree.getRoot().getLow() + " Tree Root High: " +tree.getRoot().getHigh() + " Tree Root Max: " + tree.getRoot().getMax() + " Tree Root's Parent's Name: " + tree.getRoot().getParent().getNodeName() );
//		tree.intervalTreeInfixTraversal(tree.getRoot());
		
		
		
	
//		GlanetRunner.appendLog("Overlapping Intervals");
//		tree.findAllOverlappingIntervals(tree.getRoot(), new Interval(5,20));
		
//		tree.printOverlappingIntervalsList(resultList);
		
	}

	
}
