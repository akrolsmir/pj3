/* Kruskal.java */

import java.util.Hashtable;
import dict.*;
import graph.*;
import set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

	private static int heapLength = 0;
	private static int arrayLength = 0;

	/**
	 * minSpanTree() returns a WUGraph that represents the minimum spanning tree
	 * of the WUGraph g. The original WUGraph g is NOT changed.
	 */
	public static WUGraph minSpanTree(WUGraph g) {
		WUGraph minSpanTree = new WUGraph();
		Object[] copyVertices = g.getVertices();

		HashTableChained verticeHash = new HashTableChained();
		for (int i = 0; i < copyVertices.length; i++) {
			minSpanTree.addVertex(copyVertices[i]);
			verticeHash.insert(copyVertices[i], i);
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

		toHeap(arrayEdges);
		heapLength = arrayEdges.length;
		arrayLength = arrayEdges.length;
		Edge[] temp = new Edge[heapLength];
		for (int i = 0; i < arrayLength; i++) {
			temp[i] = removeMin(arrayEdges);
		}
		arrayEdges = temp;

		DisjointSets dSet = new DisjointSets(copyVertices.length);

		for (int i = 0; i < arrayLength; i++) {
			Edge edge = arrayEdges[i];
			int vertice1, vertice2;
			vertice1 = (Integer) verticeHash.find(edge.vertexPair.object1)
					.value();
			vertice2 = (Integer) verticeHash.find(edge.vertexPair.object2)
					.value();
			if (dSet.find(vertice1) != dSet.find(vertice2)) {
				minSpanTree.addEdge(edge.vertexPair.object1,
						edge.vertexPair.object2, edge.weight);
				dSet.union(dSet.find(vertice1), dSet.find(vertice2));
			}
		}

		return minSpanTree;
	}

	private static void toHeap(Edge[] array) {
		int i = arrayLength + 1;
		while (i != 1) {
			if (i % 2 == 0) {
				if (Math.min(array[i - 1].weight, array[i - 2].weight) < array[i / 2 - 1].weight) {
					Edge temp = array[i / 2 - 1];
					if (array[i - 1].weight <= array[i - 2].weight) {
						array[i / 2 - 1] = array[i - 1];
						array[i - 1] = temp;
					} else {
						array[i / 2 - 1] = array[i - 2];
						array[i - 2] = temp;
					}
				}
				i = i - 2;
			} else {
				if (array[i - 1].weight < array[(i - 1) / 2].weight) {
					Edge temp = array[(i - 1) / 2];
					array[(i - 1) / 2] = array[i - 1];
					array[i - 1] = temp;
				}
				i = i - 1;
			}
		}
	}

	private static Edge removeMin(Edge[] array) {
		Edge edge = array[0];
		array[0] = array[heapLength - 1];
		int i = 1;
		while (i <= heapLength / 2
				&& array[i - 1].weight < Math.min(array[2 * i].weight,
						array[2 * i + 1].weight)) {
			Edge temp = array[i - 1];
			if (array[2 * i].weight < array[2 * i + 1].weight) {
				array[i - 1] = array[2 * i];
				array[2 * i] = temp;
				i *= 2;
			} else {
				array[i - 1] = array[2 * i + 1];
				array[2 * i + 1] = temp;
				i = i * 2 + 1;
			}
		}
		heapLength--;
		return edge;
	}

}
