package graph;

import list.*;

public class Vertex {
	
	private DList<Object> adjacencyList;
	private Object item;
	
	public Vertex(Object o){
		adjacencyList = new DList<Object>();
		item = o;
	}
	
	public void addEdge(Object e){
		adjacencyList.insertBack(e);
	}
	
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
	
	public Object getItem(){
		return item;
	}
	
	public DList<Object> getAdjacencyList(){
		return adjacencyList;
	}
	
	private ListNode<Object> getEdge(Object e){
		ListNode<Object> temp = adjacencyList.front();
		try{
			while(temp.item() != e || !temp.isValidNode()){
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
}
