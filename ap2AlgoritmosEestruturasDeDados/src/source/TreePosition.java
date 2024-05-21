package source;

import position.Position;

public interface TreePosition<E> extends Position<E> {

	// Define o elemento a ser armazenado nesta posição

	public void setElement(E o);

	// Retorna o elemento armazenado nesta posição

	public E getElement();

	// Retorna os filhos desta posição

	public PositionList<Position<E>> getChildren();

	// Define os filhos desta posição

	public void setChildren(PositionList<Position<E>> c);

	// Retorna o pai desta posição

	public TreePosition<E> getParent();

	// Define o pai desta posição

	public void setParent(TreePosition<E> v);
}
