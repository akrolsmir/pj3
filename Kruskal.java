/* Kruskal.java */

import java.util.Hashtable;
import graph.*;
import set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

	/**
	 * minSpanTree() returns a WUGraph that represents the minimum spanning tree
	 * of the WUGraph g. The original WUGraph g is NOT changed.
	 */
	public static WUGraph minSpanTree(WUGraph g) {
		WUGraph minSpanTree = new WUGraph();
		Object[] copyVertices = g.getVertices();
		for (int i = 0; i < copyVertices.length; i++) {
			minSpanTree.addVertex(copyVertices[i]);
		}

		int edgeCount = 0;
		Edge[] arrayEdges = new Edge[g.edgeCount()];
		for (int i = 0; i < copyVertices.length; i++) {
			Neighbors neighbor = g.getNeighbors(copyVertices[i]);
			for (int j = 0; j < neighbor.neighborList.length; j++) {
				boolean inArray = false;
				Edge edge = new Edge(copyVertices[i], neighbor.neighborList[j],
						neighbor.weightList[j]);
				for (int k = 0; k < edgeCount; k++) {
					if (arrayEdges[k] == edge) {
						inArray = true;
						break;
					}
				}
				if (!inArray) {
					arrayEdges[edgeCount] = new Edge(copyVertices[i],
							neighbor.neighborList[j], neighbor.weightList[j]);
					edgeCount++;
				}
			}
		}
		
		return minSpanTree;
	}

}
