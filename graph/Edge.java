package graph;

import list.InvalidNodeException;
import list.ListNode;

public class Edge {

	protected VertexPair vertexPair;
	protected int weight;
	protected ListNode<Edge> node1, node2; 
	
	public Edge(Vertex v1, Vertex v2, int weight){
		vertexPair = new VertexPair(v1.item, v2.item);
		this.weight = weight;
		node1 = v1.adjacencyList.insertBack(this);
		if (!v1.equals(v2)) {
			node2 = v2.adjacencyList.insertBack(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Edge
				&& vertexPair.equals(((Edge) obj).vertexPair);
	}

	@Override
	public int hashCode() {
		return vertexPair.hashCode();
	}

	@Override
	public String toString() {
		return "Edge between " + vertexPair.object1 + " and "
				+ vertexPair.object2 + ", weight: " + weight;
	}

	public void removefromLists() {
		try {
			node1.remove();
			node2.remove();
		} catch (NullPointerException npe) {
			// Null pointer? No problem!
		} catch (InvalidNodeException e) {
			// Should not occur
		}
	}

}
