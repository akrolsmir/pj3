package graph;

import list.*;

public class Vertex {
	
	protected DList<Edge> adjacencyList;
	protected Object item;
	
	/**
	 * 
	 * @param o
	 */
	public Vertex(Object o){
		adjacencyList = new DList<Edge>();
		item = o;
	}
	
	/**
	 * 
	 * @param e
	 */
	public ListNode<Edge> addEdge(Edge e){
		return adjacencyList.insertBack(e);
	}
	
	/**
	 * 
	 * @param e
	 */
	public void removeEdge(Edge e){
		ListNode<Edge> edge = getEdge(e);
		if(edge != null){
			try{
				edge.remove();
			} catch (InvalidNodeException ex){
				System.err.println(ex);
			}
		}
	}
	
	/**
	 * 
<<<<<<< HEAD
=======
	 * @return
	 */
	public Object getItem(){
		return item;
	}
	
	/**
	 * 
	 * @return
	 */
	public DList<Edge> getAdjacencyList(){
		return adjacencyList;
	}
	
	/**
	 * 
>>>>>>> Implement add and removeEdge
	 * @param e
	 * @return
	 */
	private ListNode<Edge> getEdge(Edge e){
		ListNode<Edge> temp = adjacencyList.front();
		try{
			while(temp.item() != e || temp.isValidNode()){
				temp = temp.next();
			}
			if(!temp.isValidNode()){
				return temp;
			} else {
				return null;
			}
		} catch (InvalidNodeException ex){
			System.err.println(ex);
			return null;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vertex
				&& item.equals(((Vertex) obj).item);
	}
}
