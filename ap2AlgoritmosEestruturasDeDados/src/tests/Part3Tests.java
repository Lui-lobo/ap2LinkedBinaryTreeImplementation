package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//import source.ArrayIndexList;
import source.LinkedBinaryTree;

class Part3Tests {

	@Test
	<E> void test() {
		LinkedBinaryTree<E> binaryTest = new LinkedBinaryTree<>();
		
		
		LinkedBinaryTree<E> binaryTree = (LinkedBinaryTree<E>) binaryTest.buildExpression("(((5+2)*(2-1))/((2+9)+(7-2)-1))*8))");
		
		String treeString = 
			    		"/\n" +
			    		"│   └── *\n" +
			    	    "│   │   └── └── +\n" +
			    	    "│   │   │   └── └── └── 5\n" +
			    	    "│   │   │   └── └── └── 2\n" +
			    	    "│   │   └── └── -\n" +
			    	    "│   │       └── └── ┌── 2\n" +
			    	    "│   │       └── └── ┌── 1\n" +
			    	    "│   └── *\n" +
			    	    "│       └── ┌── +\n" +
			    	    "│       │   └── ┌── └── +\n" +
			    	    "│       │   │   └── ┌── └── └── 2\n" +
			    	    "│       │   │   └── ┌── └── └── 9\n" +
			    	    "│       │   └── ┌── └── -\n" +
			    	    "│       │       └── ┌── └── ┌── -\n" +
			    	    "│       │       │   └── ┌── └── ┌── └── 7\n" +
			    	    "│       │       │   └── ┌── └── ┌── └── 2\n" +
			    	    "│       │       └── ┌── └── ┌── 1\n" +
			    	    "│       └── ┌── 8\n";
		
		assertEquals(treeString, binaryTest.drawExpressionTreeToString(binaryTree.root(), 0, new StringBuilder(), new StringBuilder(), true));
	}

}
