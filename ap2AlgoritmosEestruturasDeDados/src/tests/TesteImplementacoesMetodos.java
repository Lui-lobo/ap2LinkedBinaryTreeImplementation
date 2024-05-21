package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//import source.ArrayIndexList;
import source.LinkedBinaryTree;

class TesteImplementacoesMetodos {

	@Test
	<E> void test() {
		LinkedBinaryTree<E> binaryTest = new LinkedBinaryTree<>();
		
		LinkedBinaryTree<E> binaryTree = (LinkedBinaryTree<E>) binaryTest.buildExpression("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))");
		
		binaryTest.binaryPostorder(binaryTree.root());
		
		assertEquals("31+3*95-2+/374-*6+-", binaryTest.binaryPostorder(binaryTree.root()));
		
		assertEquals(-13.0, binaryTest.evaluateExpression(binaryTree.root()));
		
		assertEquals("3+1*3/9-5+2-3*7-4+6", binaryTest.inorder(binaryTree.root()));
		
		LinkedBinaryTree<Integer> searchTree = new LinkedBinaryTree<>();
		
		searchTree.insert(56);
		searchTree.insert(90);
		searchTree.insert(75);
		searchTree.insert(62);
		searchTree.insert(42);
		searchTree.insert(36);
		searchTree.insert(31);
		searchTree.insert(25);
		searchTree.insert(12);
		
		assertEquals("12, 25, 31, 36, 42, 56, 62, 75, 90", searchTree.makerBTSearch(searchTree.root()));
		
		 String treeString = 
			        "-\n" +
			        "│   └── /\n" +
			        "│   │   └── └── *\n" +
			        "│   │   │   └── └── └── +\n" +
			        "│   │   │   │   └── └── └── └── 3\n" +
			        "│   │   │   │   └── └── └── └── 1\n" +
			        "│   │   │   └── └── └── 3\n" +
			        "│   │   └── └── +\n" +
			        "│   │       └── └── ┌── -\n" +
			        "│   │       │   └── └── ┌── └── 9\n" +
			        "│   │       │   └── └── ┌── └── 5\n" +
			        "│   │       └── └── ┌── 2\n" +
			        "│   └── +\n" +
			        "│       └── ┌── *\n" +
			        "│       │   └── ┌── └── 3\n" +
			        "│       │   └── ┌── └── -\n" +
			        "│       │       └── ┌── └── ┌── 7\n" +
			        "│       │       └── ┌── └── ┌── 4\n" +
			        "│       └── ┌── 6\n";
		
		assertEquals(treeString, binaryTest.drawExpressionTreeToString(binaryTree.root(), 0, new StringBuilder(), new StringBuilder(), true));
		
		assertEquals("-/*+333+111+*333*/+-999-555-+222+/-+*333*-777-444-*+666+-", binaryTest.eulerTour(binaryTree.root()));
		
		assertEquals("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))", binaryTest.printExpressionTree(binaryTree.root()));
		
		assertEquals(11, binaryTest.countLeftNodes(binaryTree.root()));
		
		assertEquals(6, binaryTest.countLeftExternalNodes(binaryTree.root()));
		
		assertEquals(7, binaryTest.countRightNodes(binaryTree.root()));
		
		assertEquals(4, binaryTest.countRightExternalNodes(binaryTree.root()));
	}

}
