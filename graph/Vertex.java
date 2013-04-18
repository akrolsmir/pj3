package graph;

import list.*;

public class Vertex {
	
	protected DList<Edge> adjacencyList;
	protected Object item;
	protected ListNode<Vertex> node; 
	
	/**
	 * 
	 * @param o
	 */
	public Vertex(Object o, List<Vertex> l){
		adjacencyList = new DList<Edge>();
		item = o;
		node = l.insertBack(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vertex
				&& item.equals(((Vertex) obj).item);
	}

	public void removeFromList() {
		try {
			node.remove();
		} catch (InvalidNodeException e) {
			// Shouldn't ever happen
		}
		
	}
}
