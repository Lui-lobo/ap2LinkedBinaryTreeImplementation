package source;

import position.Position;
import java.util.Iterator;
import java.util.Comparator;
import pilha.ArrayStack;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;

public class LinkedBinaryTree<E> implements BinaryTree<E> {

	protected BTPosition<E> root; // referência para a raiz
	
	protected int size; // número de nodos
	
	// Cria uma árvore binária vazia.
	
	public LinkedBinaryTree() {
		
		root = null; // inicia com uma árvore vazia
		
		size = 0;
	
	}
	
	// Retorna o número de nodos da árvore.
	
	public int size() { return size; }
	
	// Retorna se um nodo é interno.
	
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
	
		checkPosition(v); // método auxiliar
		
		return (hasLeft(v) || hasRight(v));
	
	}
	
	// Retorna se um nodo é a raiz.
	
	public boolean isRoot(Position<E> v) throws EmptyTreeException, InvalidPositionException {
	
		checkPosition(v);
		
		return (v == root());
	
	}
	
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {

		BTPosition<E> vv = checkPosition(v);

		return (vv.getLeft() != null);

	}

		// Retorna a raiz da árvore.

	public Position<E> root() throws EmptyTreeException {
		if (root == null) throw new EmptyTreeException("The tree is empty");
		return root;
	}

		// Retorna o filho da esquerda de um nodo.

	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {

		BTPosition<E> vv = checkPosition(v);

		Position<E> leftPos = (Position<E>) vv.getLeft();

		if (leftPos == null) throw new BoundaryViolationException("No left child");

		return leftPos;

	}

	// Retorna o pai de um nodo.

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {

		BTPosition<E> vv = checkPosition(v);

		Position<E> parentPos = (Position<E>) vv.getParent();

		if (parentPos == null) throw new BoundaryViolationException("No parent");

		return parentPos;

	}

		// Retorna uma coleção iterável contendo os filhos de um nodo.

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {

		PositionList<Position<E>> children = new NodePositionList<Position<E>>();

		if (hasLeft(v)) children.addLast(left(v));

		if (hasRight(v)) children.addLast(right(v));

		return children;

	}
	
	// Retorna uma coleção iterável (inorder) contendo os nodos da árvore.

	public Iterable<Position<E>> positionsInorder() {
	
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
	
		if (size != 0) inorderPositions(root(), positions); // atribui as posições usando caminhamento prefixado
	
		return positions;

	}

	// Cria uma lista que armazena os nodos da subárvore de um nodo ordenados de acordo com o caminhamento inorder da subárvore.

	public void inorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {

		if (hasLeft(v)) inorderPositions(left(v), pos); // recursão sobre o filho da esquerda
	
		pos.addLast(v);
	
		if (hasRight(v)) inorderPositions(right(v), pos); // recursão sobre o filho da direita

	}

	// Retorna uma coleção iterável contendo os nodos da árvore.

	public Iterable<Position<E>> positions() {

		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
	
		if (size != 0) preorderPositions(root(), positions); // atribui as posições usando caminhamento prefixado
	
		return positions;

	}

	// Retorna um iterador sobre os elementos armazenados nos nodos

	public Iterator<E> iterator() {

		Iterable<Position<E>> positions = positions();
	
		PositionList<E> elements = new NodePositionList<E>();
	
		for (Position<E> pos : positions) elements.addLast(pos.element());
	
		return elements.iterator(); // Um iterador sobre os elementos

	}

	// Substitui o elemento armazenado no nodo.

	public E replace(Position<E> v, E o) throws InvalidPositionException {

		BTPosition<E> vv = checkPosition(v);
	
		E temp = v.element();
	
		vv.setElement(o);
	
		return temp;

	}
	
	// Método de acesso adicional

	// Retorna o irmão de um nodo

	public Position<E> sibling(Position<E> v) throws InvalidPositionException, BoundaryViolationException {

		BTPosition<E> vv = checkPosition(v);
	
		BTPosition<E> parentPos = vv.getParent();
	
		if (parentPos != null) {
	
		BTPosition<E> sibPos;
	
		BTPosition<E> leftPos = parentPos.getLeft();
	
		if (leftPos == vv) sibPos = parentPos.getRight();
	
		else sibPos = parentPos.getLeft();
	
		if (sibPos != null) return sibPos;
	
		}
	
		throw new BoundaryViolationException("No sibling");

	}

	// Métodos de acesso adicionais

	// Insere a raiz em uma árvore vazia

	public Position<E> addRoot(E e) throws NonEmptyTreeException {
	
		if (!isEmpty()) throw new NonEmptyTreeException("Tree already has a root");
	
		size = 1;
	
		root = createNode(e, null, null, null);
	
		return root;

	}

	// Insere o filho da esquerda em um nodo.

	public Position<E> insertLeft(Position<E> v, E e) throws InvalidPositionException {
	
		BTPosition<E> vv = checkPosition(v);
	
		Position<E> leftPos = (Position<E>) vv.getLeft();
	
		if (leftPos != null) throw new InvalidPositionException("Node already has a left child");
	
		BTPosition<E> ww = createNode(e, vv, null, null);
	
		vv.setLeft(ww);
	
		size++;
	
		return ww;

	}

	// Insere o filho a direita em um nodo.

	public Position<E> insertRight(Position<E> v, E e) throws InvalidPositionException {
	
		BTPosition<E> vv = checkPosition(v);
	
		Position<E> rightPos = (Position<E>) vv.getRight();
	
		if (rightPos != null) throw new InvalidPositionException("Node already has a right child");
	
		BTPosition<E> ww = createNode(e, vv, null, null);
	
		vv.setRight(ww);
	
		size++;
	
		return ww;

	}

	// Remove um nodo com zero ou um filho.

	public E remove(Position<E> v) throws InvalidPositionException {

		BTPosition<E> vv = checkPosition(v);
	
		BTPosition<E> leftPos = vv.getLeft();
	
		BTPosition<E> rightPos = vv.getRight();
	
		if (leftPos != null && rightPos != null) throw new InvalidPositionException("Cannot remove node with two children");
	
		BTPosition<E> ww; // o único filho de v, se houver
	
		if (leftPos != null) ww = leftPos;
	
		else if (rightPos != null) ww = rightPos;
	
		else // v é folha
	
		ww = null;
	
		if (vv == root) { // v é a raiz
	
		if (ww != null) ww.setParent(null);
	
		root = ww;
	
		} else { // v não é a raiz
	
		BTPosition<E> uu = vv.getParent();
	
		if (vv == uu.getLeft()) uu.setLeft(ww);
	
		else uu.setRight(ww);
	
		if (ww != null) ww.setParent(uu);
	
		}
	
		size--;
	
		return v.element();

	}
	
	// Conecta duas árvores para serem subárvores de um nodo externo.

	public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {

		BTPosition<E> vv = checkPosition(v);
	
		if (isInternal(v)) throw new InvalidPositionException("Cannot attach from internal node");
	
		if (!T1.isEmpty()) {
	
		BTPosition<E> r1 = checkPosition(T1.root());
	
		vv.setLeft(r1);
	
		r1.setParent(vv); // T1 deve ser invalidada
	
		}
	
		if (!T2.isEmpty()) {
	
		BTPosition<E> r2 = checkPosition(T2.root());
	
		vv.setRight(r2);
	
		r2.setParent(vv); // T2 deve ser invalidada
	
		}

	}

	// Se v é um nodo de árvore binária, converte para BTPosition, caso contrário lança exceção

	protected BTPosition<E> checkPosition(Position<E> v) throws InvalidPositionException {

		if (v == null || !(v instanceof BTPosition)) throw new InvalidPositionException("The position is invalid");
	
		return (BTPosition<E>) v;
	
		}
	
		// Cria um novo nodo de árvore binária
	
		protected BTPosition<E> createNode(E element, BTPosition<E> parent, BTPosition<E> left, BTPosition<E> right) {
	
		return new BTNode<E>(element, parent, left, right);

	}

	// Cria uma lista que armazena os nodos da subárvore de um nodo ordenados de acordo com o

	// caminhamento prefixado da subárvore.

	protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {

		pos.addLast(v);
	
		if (hasLeft(v)) preorderPositions(left(v), pos); // recursão sobre o filho da esquerda
	
		if (hasRight(v)) preorderPositions(right(v), pos); // recursão sobre o filho da direita

	}
	
	public boolean isEmpty() { return (size == 0); }

	public boolean isExternal(Position<E> v) throws InvalidPositionException { return !isInternal(v); }

	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {

		BTPosition<E> vv = checkPosition(v);
	
		Position<E> rightPos = (Position<E>) vv.getRight();
	
		if (rightPos == null) throw new BoundaryViolationException("No right child");
	
		return rightPos;

	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
	
		BTPosition<E> vv = checkPosition(v);
	
		return (vv.getRight() != null);

	}
	
	public BinaryTree<E> buildExpression(String expression) {
		ArrayStack<LinkedBinaryTree> s = new ArrayStack<LinkedBinaryTree>();
		
		char[] elements = expression.toCharArray();
		
		for (int i = 0; i < elements.length; i++) {
			char element = elements[i];
			  if (isVariableOrOperator(element)) {
	                LinkedBinaryTree t = new LinkedBinaryTree();
	                t.addRoot(element);
	                s.push(t);
	            } else if (element == '(') {
	                continue;
	            } else {
	            	LinkedBinaryTree t2 = s.pop();
	            	LinkedBinaryTree t = s.pop();
	            	LinkedBinaryTree t1 = s.pop();
	            	
	            	t.attach(t.root(), t1, t2);
	       
	                s.push(t);
	            }
		}
		
		return s.pop();
	}

	 public String binaryPostorder(Position<E> v) {
		 	BTPosition<E> vv = checkPosition(v);
		 	StringBuilder sb = new StringBuilder();
		 	
	        if (vv.getLeft() != null) {
	            sb.append(binaryPostorder(vv.getLeft())); // recursivamente percorre a subárvore esquerda
	        }
	        if (vv.getRight() != null) {
	            sb.append(binaryPostorder(vv.getRight())); // recursivamente percorre a subárvore direita
	        }
	        
		 	sb.append(vv.element()); // Adiciona o valor do nodo atual
	        return sb.toString();
	       
	 }
	 
	  // Método para avaliar a expressão
	  public double evaluateExpression(Position<E> v) {
		  	BTPosition<E> vv = checkPosition(v);
		  
	        if (isInternal(vv)) {
	            double x = evaluateExpression(vv.getLeft());
	            double y = evaluateExpression(vv.getRight());
	            //E<e> operator = vv.element();

	            switch (vv.element().toString()) {
	                case "+":
	                    return x + y;
	                case "-":
	                    return x - y;
	                case "*":
	                    return x * y;
	                case "/":
	                    return x / y;
	                default:
	                    throw new IllegalArgumentException("Operador inválido: " + vv.element().toString());
	            }
	        } else {
	            // Se v é uma folha, retorna o valor armazenado em v como um double
	        	return Double.parseDouble(vv.element().toString());
	            //return Double.parseDouble(vv.element());
	        }
	  }
	  
	  public String inorder(Position<E> v) {
	        StringBuilder sb = new StringBuilder();
	        inorderHelper(v, sb);
	        return sb.toString();
	 }
	  
	  public String makerBTSearch(Position<E> v) {
	       StringBuilder sbt = new StringBuilder();
	       inorderHelperToBtSearch(v, sbt);
	       formatString(sbt.toString());
	       return formatString(sbt.toString());
	 }
	  
	  public String printExpressionTree(Position<E> v) {
		  BTPosition<E> vv = checkPosition(v);
		  return expressionTreeToStringHelperInOrder(vv);
	  }
	  
	  public String drawExpressionTreeToString(Position<E> v, int depth, StringBuilder prefix, StringBuilder line, boolean isLeft) {
		    if (v == null) return "";

		    String elementString = v.element().toString();

		    StringBuilder result = new StringBuilder();
		    result.append(line).append(prefix).append(elementString).append("\n");

		    StringBuilder newLine = new StringBuilder(line.toString());
		    newLine.append(isLeft ? "│   " : "    ");

		    StringBuilder newPrefix = new StringBuilder(prefix.toString());
		    newPrefix.append(isLeft ? "└── " : "┌── ");

		    BTPosition<E> vv = checkPosition(v);
		    result.append(drawExpressionTreeToString(vv.getLeft(), depth + 1, newPrefix, newLine, true));
		    result.append(drawExpressionTreeToString(vv.getRight(), depth + 1, newPrefix, newLine, false));

		    return result.toString();
	  }
	  
	  public String eulerTour(Position<E> v) {
		    StringBuilder result = new StringBuilder();
			BTPosition<E> vv = checkPosition(v);

		    // Executar a ação prevista para o nodo v quando encontrado pela esquerda
		    // (vindo de cima)
		    result.append(visitLeft(v));

		    // Verificar se v tem um filho da esquerda u em T
		    if (vv.getLeft() != null) {
		        // Euler Tour recursivo para percorrer a subárvore esquerda de v
		        result.append(eulerTour(vv.getLeft()));
		    }

		    // Executar a ação de visita sobre v vindo de baixo
		    result.append(visitBottom(vv));

		    // Verificar se v tem um filho da direita w em T
		    if (vv.getRight() != null) {
		        // Euler Tour recursivo para percorrer a subárvore direita de v
		        result.append(eulerTour(vv.getRight()));
		    }

		    // Executar a ação prevista para o nodo v pela direita
		    result.append(visitRight(v));

		    return result.toString();
		}
	  
	  public int countLeftNodes(Position<E> v) {
		  	BTPosition<E> vv = checkPosition(v);
	        return countLeftNodesHelper(vv) + 1;
	  }
	  
	  public int countLeftExternalNodes(Position<E> v) {
		  	BTPosition<E> vv = checkPosition(v);
	        return countLeftExternalNodesHelper(vv);
	  }
	  
	  
	  public int countRightNodes(Position<E> v) {
		  	BTPosition<E> vv = checkPosition(v);
	        return countRightNodesHelper(vv)  + 1;
	  }
	  
	  public int countRightExternalNodes(Position<E> v) {
		  BTPosition<E> vv = checkPosition(v);
	      return countRightExternalNodesHelper(vv);
	  }
	  
	   // Funçöes auxiliares criadas para facilitar a leitura/uso das outras funçöes
	  private static boolean isVariableOrOperator(char c) {
		  return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '+' || c == '-' || c == '*' || c == '/';
	  }
	  
	   // Método auxiliar para percorrer a árvore em ordem e construir a string
	  private void inorderHelper(Position<E> v, StringBuilder sb) {
		   BTPosition<E> vv = checkPosition(v);
		   
	       if (vv.getLeft() != null) {
	           inorderHelper(vv.getLeft(), sb); // percorre recursivamente a subárvore esquerda
	       }
	       sb.append(vv.element()); // executa a ação "de visita" sobre o nodo v
	       if (vv.getRight() != null) {
	           inorderHelper(vv.getRight(), sb); // percorre recursivamente a subárvore direita
	       }
	   }
	  
	  private void inorderHelperToBtSearch(Position<E> v, StringBuilder sbt) {
		   BTPosition<E> vv = checkPosition(v);

	       if (vv.getLeft() != null) {
	           inorderHelper(vv.getLeft(), sbt); // percorre recursivamente a subárvore esquerda
	       }
	       sbt.append(vv.element()); // executa a ação "de visita" sobre o nodo v
	       if (vv.getRight() != null) {
	           inorderHelper(vv.getRight(), sbt); // percorre recursivamente a subárvore direita
	       }
	   }
	  
	  // Insere um elemento na árvore de busca binária
	  public Position<E> insert(E element) {
	        if (isEmpty()) {
	            return addRoot(element);
	        } else {
	            return insertRec(root, element);
	        }
	    }
	
	  private Position<E> insertRec(BTPosition<E> node, E element) {
		    if (node == null) {
		        BTPosition<E> newNode = createNode(element, null, null, null);
		        if (root == null) {
		            root = newNode;
		        }
		        size++;
		        return newNode;
		    } else {
		        // Aqui usamos uma verificação simples para decidir se o novo elemento vai para a esquerda ou direita
		        if (element.hashCode() < node.element().hashCode()) {
		            if (node.getLeft() == null) {
		                BTPosition<E> newNode = createNode(element, node, null, null);
		                node.setLeft(newNode);
		                size++;
		                return newNode;
		            } else {
		                return insertRec(node.getLeft(), element);
		            }
		        } else {
		            if (node.getRight() == null) {
		                BTPosition<E> newNode = createNode(element, node, null, null);
		                node.setRight(newNode);
		                size++;
		                return newNode;
		            } else {
		                return insertRec(node.getRight(), element);
		            }
		        }
		    }
		}
	  
	  private static String formatString(String original) {
	        StringBuilder formatted = new StringBuilder();

	        for (int i = 0; i < original.length(); i += 2) {
	            if (i != 0) {
	                formatted.append(", ");
	            }
	            formatted.append(original.substring(i, i + 2));
	        }

	        return formatted.toString();
	    }
	  
	  private String expressionTreeToStringHelperInOrder(BTPosition<E> v) {
		    StringBuilder sb = new StringBuilder();
		    if (v.getLeft() != null) {
		        sb.append("(");
		        sb.append(expressionTreeToStringHelperInOrder(v.getLeft())); // percorre recursivamente a subárvore esquerda
		    }
	    	System.out.println(v.element());
		    sb.append(v.element()); // adiciona o operador ou valor do nodo v
		    if (v.getRight() != null) {
		        sb.append(expressionTreeToStringHelperInOrder(v.getRight())); // percorre recursivamente a subárvore direita
		        sb.append(")");
		    }
		    return sb.toString();
		}
	  
	  
	// Método para ação prevista para o nodo v quando encontrado pela esquerda
	  public String visitLeft(Position<E> v) {
	      // Implemente a ação desejada e retorne o resultado como uma string
	      return v.element().toString();
	  }

	  // Método para ação prevista para o nodo v vindo de baixo
	  public String visitBottom(Position<E> v) {
	      // Implemente a ação desejada e retorne o resultado como uma string
	      return v.element().toString();
	  }

	  // Método para ação prevista para o nodo v pela direita
	  public String visitRight(Position<E> v) {
	      // Implemente a ação desejada e retorne o resultado como uma string
	      return v.element().toString();
	  }
	  
	  private int countLeftNodesHelper(BTPosition<E> node) {
	      	if (node == null) {
	            return 0;
	        }
	      	BTPosition<E> leftTree = node.getLeft();
	      		
	        int count = countLeftNodeHelperOfTheHelper(leftTree);

	        return count;
	   }
	  
	  private int countLeftNodeHelperOfTheHelper(BTPosition<E> node) {
			if (node == null) {
	            return 0;
	        }
			
	        int count = 0;
	        if(node.getRight() != null) {
	        	count += 1 + countLeftNodesHelper(node.getRight()) + countLeftNodesHelper(node.getRight());
	        }
	        if (node.getLeft() != null) {
	            count += 1 + countLeftNodesHelper(node.getLeft()) + countLeftNodesHelper(node.getRight());
	        }
	        
	        return count;
	  }
	  
	  private int countRightNodesHelper(BTPosition<E> node) {
	      	if (node == null) {
	            return 0;
	        }
	      	
	      	BTPosition<E> rightTree = node.getRight();
      		
	        int count = countRightNodesHelperOfTheHelper(rightTree);

	        return count;
	   }
	    
	  private int countRightNodesHelperOfTheHelper(BTPosition<E> node) {
		  if (node == null) {
	            return 0;
	        }
			
	        int count = 0;
	        if(node.getRight() != null) {
	        	count += 1 + countRightNodesHelperOfTheHelper(node.getRight()) + countLeftNodesHelper(node.getRight());
	        }
	        if (node.getLeft() != null) {
	            count += 1 + countRightNodesHelperOfTheHelper(node.getLeft()) + countLeftNodesHelper(node.getRight());
	        }
	        
	        return count;
	   }
	    
	   private int countLeftExternalNodesHelper(BTPosition<E> node) {
	        if (node == null) {
	            return 0;
	        }
	        int count = countLeftExternalNodesHelperOfTheHelper(node.getLeft());

	        return count;
	    }
	    
	    private int countLeftExternalNodesHelperOfTheHelper(BTPosition<E> node) {
	        if (node == null) {
	            return 0;
	        }
	        int count = 0;
	        if (node.getLeft() != null) {
	            BTPosition<E> leftNode = node.getLeft();
	            if (leftNode.getLeft() == null && leftNode.getRight() == null) {
	                count += 1; // O nó esquerdo é uma folha
	            }
	            count += countLeftExternalNodesHelperOfTheHelper(leftNode);
	        }
	        if(node.getRight() != null)  {
	        	  BTPosition<E> rightNode = node.getRight();
	        	 if (rightNode.getLeft() == null && rightNode.getRight() == null) {
		                count += 1; // O nó esquerdo é uma folha
		         }
	        	 count += countLeftExternalNodesHelperOfTheHelper(rightNode);
	        }
	        	
	        return count;
	    }
	    

	    // Método auxiliar recursivo para contar os nodos direitos externos
	    private int countRightExternalNodesHelper(BTPosition<E> node) {
	        if (node == null) {
	            return 0;
	        }
	   
	        int count = countRightExternalNodesHelperOfTheHelper(node.getRight());
	        return count;
	    }
	    
	    private int countRightExternalNodesHelperOfTheHelper(BTPosition<E> node) {
	    	 if (node == null) {
		            return 0;
		        }
		        int count = 0;
		        if (node.getLeft() != null) {
		            BTPosition<E> leftNode = node.getLeft();
		            if (leftNode.getLeft() == null && leftNode.getRight() == null) {
		                count += 1; // O nó esquerdo é uma folha
		            }
		            count += countRightExternalNodesHelperOfTheHelper(leftNode);
		        }
		        if(node.getRight() != null)  {
		        	  BTPosition<E> rightNode = node.getRight();
		        	 if (rightNode.getLeft() == null && rightNode.getRight() == null) {
			                count += 1; // O nó esquerdo é uma folha
			         }
		        	 count += countRightExternalNodesHelperOfTheHelper(rightNode);
		        }
		        	
		        return count;
	    }
}
