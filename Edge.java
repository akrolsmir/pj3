
public class Edge {

	protected VertexPair vertexPair;
	protected Edge partner;
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

	public Edge(Object vertex1, Object vertex2, int weight) {
		vertexPair = new VertexPair(vertex1, vertex2);
		this.weight = weight;
	}

	/**
	 * @param vertex1
	 * @param vertex2
	 * @param weight
	 * @return an array of size 2 containing the two newly formed Edges
	 */
	public static Edge[] makeEdges(Object vertex1, Object vertex2, int weight) {
		Edge edge1 = new Edge(vertex1, vertex2, weight);
		Edge edge2 = new Edge(vertex1, vertex2, weight);
		edge1.partner = edge2;
		edge2.partner = edge1;
		return new Edge[] { edge1, edge2 };
	}
	
	public VertexPair getVertex() {
		return vertexPair;
	}
	
	public int getWeight() {
		return weight;
	}

	public String toString() {
		return "" + weight;
	}

	
	public boolean equals(Object edge) {
		if (edge instanceof Edge) {
			return vertexPair.equals(((Edge) edge).vertexPair);
		} else {
			return false;
		}
	}

}
