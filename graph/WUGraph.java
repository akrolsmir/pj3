/* WUGraph.java */

package graph;

import list.*;
import dict.HashTableChained;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	
	private int vertexCount, edgeCount;
	
	HashTableChained<Object, Vertex> vertexTable = new HashTableChained<Object, Vertex>();
	HashTableChained<VertexPair, ListNode<Edge>> edgeTable = new HashTableChained<VertexPair, ListNode<Edge>>();
	
	DList<Vertex> vertexList = new DList<Vertex>();

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  vertexCount = 0;
	  edgeCount = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return vertexCount;
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return edgeCount;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  Object[] result = new Object[vertexCount];
	  ListNode<Vertex> temp = vertexList.front();
	  for(int i = 0; i < vertexCount; i++){
		  try{
			  result[i] = temp.item().item;
			  temp = temp.next();
		  } catch (InvalidNodeException e){
			  System.err.println(e);
			  return null;
		  }
	  }
	  return result;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  if(vertexTable.find(vertex) == null){
		  Vertex newVertex = new Vertex(vertex);
		  vertexTable.insert(vertex, newVertex);
		  vertexList.insertBack(newVertex);
		  vertexCount++;
	  }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  Vertex v = vertexTable.find(vertex);
	  if(v != null){
		  DList<Edge> edges = v.adjacencyList;
		  for(Edge edge : edges){
			  removeEdge(edge.vertexPair.object1, edge.vertexPair.object2);
		  }
		  try{
			  ListNode<Vertex> temp = vertexList.front();
			  while(temp.isValidNode()){
				  if(temp.item() == v){
					  temp.remove();
					  break;
				  }
				  temp = temp.next();
			  }
		  } catch (InvalidNodeException e){
			  System.err.println(e);
		  }
		  vertexTable.remove(vertex);
		  vertexCount--;
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  if(vertexTable.find(vertex) != null){
		  return true;
	  } else {
		  return false;
	  }
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  if(isVertex(vertex)){
		  Vertex v = vertexTable.find(vertex);
		  return v.adjacencyList.length();
	  } else {
		  return 0;
	  }
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  Neighbors result = new Neighbors();
	  if(isVertex(vertex)){
		  Vertex v = vertexTable.find(vertex);
		  if(v.adjacencyList.length() == 0){
			  return null;
		  }
		  Object[] neighborList = new Object[v.adjacencyList.length()];
		  int[] weightList = new int[v.adjacencyList.length()];
		  ListNode<Edge> temp = v.adjacencyList.front();
		  for(int i = 0; i < neighborList.length; i++){
			  try{
				  weightList[i] = temp.item().weight;
				  Edge tempEdge = temp.item();
				  if(tempEdge.vertexPair.object1 == v){
					  neighborList[i] = tempEdge.vertexPair.object2;
				  } else {
					  neighborList[i] = tempEdge.vertexPair.object1;
				  }
				  temp = temp.next();
			  } catch (InvalidNodeException e){
				  System.err.println(e);
			  }
		  }
		  result.neighborList = neighborList;
		  result.weightList = weightList;
		  return result;
	  } else {
		  return null;
	  }
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *t
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  VertexPair key = new VertexPair(u, v);
		if (isEdge(u, v)) {
			// Edge already present, update the weights
			try {
				edgeTable.find(key).item().weight = weight;
			} catch (InvalidNodeException e) {
				//Shouldn't ever hit this
			}
		}
		else {
			//Add a new edge
			Edge edge = new Edge(u, v, weight);
			ListNode<Edge> node1 = vertexTable.find(u).addEdge(edge);
			edgeTable.insert(key, node1);
			//If u and v are not the same
			if (!u.equals(v)) {
				ListNode<Edge> node2 = vertexTable.find(v).addEdge(edge);
				edgeTable.insert(key, node2);
			}
			edgeCount++;
		}
	}

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
		if (!isEdge(u, v)) {
			return;
		}
		VertexPair key = new VertexPair(u, v);
		try {
			edgeTable.remove(key).remove();
			if(u != v){
				edgeTable.remove(key).remove();
			}
		} catch (Exception e) {
			// Means that the second remove was unneeded.
		}
		vertexTable.find(v);
		// remove edge from the lists
		edgeCount--;
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  return edgeTable.find(new VertexPair(u, v)) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
	public int weight(Object u, Object v) {
		if (isEdge(u, v)) {
			try {
				return edgeTable.find(new VertexPair(u, v)).item().weight;
			} catch (InvalidNodeException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}
