package graph;

import list.*;

public class Vertex {
	
	protected DList<Edge> adjacencyList = new DList<Edge>();;
	protected Object item;
	protected ListNode<Vertex> node; 
	
	/**
	 * A Vertex contains references to its item, an adjacency list of Edges, and
	 * the node that contains it. The constructor takes in the item, as well as
	 * the list of vertices to which the Vertex will add itself.
	 * 
	 * @param item
	 * @param list
	 */
	public Vertex(Object item, List<Vertex> list){
		this.item = item;
		node = list.insertBack(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vertex
				&& item.equals(((Vertex) obj).item);
	}

	/**
	 * removeFromList() removes the node containing this Vertex from its list
	 */
	public void removeFromList() {
		try {
			node.remove();
		} catch (InvalidNodeException e) {
			// Shouldn't ever happen
		}
	}
}
