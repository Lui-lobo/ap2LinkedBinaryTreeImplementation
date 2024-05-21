package pilha;

import exceptions.EmptyStackException;

public interface Stack<E> {

	// Retorna o número de elementos na pilha.

	public int size();

	// @return True se a pilha é vazia, False em caso contrário.

	public boolean isEmpty();

	// @return o elemento que está no topo da pilha.

	// @exception EmptyStackException se a pilha estiver vazia.

	public E top() throws EmptyStackException;

	// @param elemento a ser inserido.

	public void push(E element);

	// @return elemento removido.

	// @exception EmptyStackException se a pilha estiver vazia.

	public E pop() throws EmptyStackException;
}
