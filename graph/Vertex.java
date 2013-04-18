package graph;

import list.*;

public class Vertex {
	
	private DList<Object> adjacencyList;
	private Object item;
	
	/**
	 * 
	 * @param o
	 */
	public Vertex(Object o){
		adjacencyList = new DList<Object>();
		item = o;
	}
	
	/**
	 * 
	 * @param e
	 */
	public void addEdge(Object e){
		adjacencyList.insertBack(e);
	}
	
	/**
	 * 
	 * @param e
	 */
	public void removeEdge(Object e){
		ListNode<Object> edge = getEdge(e);
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
	 * @return
	 */
	public Object getItem(){
		return item;
	}
	
	/**
	 * 
	 * @return
	 */
	public DList<Object> getAdjacencyList(){
		return adjacencyList;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	private ListNode<Object> getEdge(Object e){
		ListNode<Object> temp = adjacencyList.front();
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
