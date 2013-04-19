
public class KEdge {

	protected VertexPair vertexPair;
	protected int weight;

	// Implementation such that creating one edge creates its partner
	// private Edge(Object vertex1, Object vertex2, int weight, Edge partner) {
	// vertexPair = new VertexPair(vertex1, vertex2);
	// this.weight = weight;
	// if (partner == null) {
	// partner = new Edge(vertex1, vertex2, weight, this);
	// }
	// }
	//
	// public Edge(Object vertex1, Object vertex2, int weight) {
	// this(vertex1, vertex2, weight, null);
	// }

	/**
	 * KEdge() creates an instance of KEdge representing an edge in a 
	 * WUGraph.
	 * 
	 * @param vertex1 a vertex in the edge
	 * @param vertex2 the other vertex in the edge
	 * @param weight the weight of the edge
	 */
	public KEdge(Object vertex1, Object vertex2, int weight) {
		vertexPair = new VertexPair(vertex1, vertex2);
		this.weight = weight;
	}
	
	/**
	 * getVertex() is a getter function for the vertexPair, 
	 * the two vertices.
	 * 
	 * @return a vertexPair holding both vertices
	 */
	public VertexPair getVertex() {
		return vertexPair;
	}
	
	/**
	 * getWeight() is a getter function for the weight 
	 * of the edge.
	 * 
	 * @return the weight of the edge
	 */
	public int getWeight() {
		return weight;
	}

	public String toString() {
		return "" + weight;
	}

	
	public boolean equals(Object edge) {
		if (edge instanceof KEdge) {
			return vertexPair.equals(((KEdge) edge).vertexPair);
		} else {
			return false;
		}
	}

}
