/* Kruskal.java */

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
   * 
   * @param g The WUGraph from which to run Kruskal's algorithm on
   * @return a WUGraph that represents the minimum spanning tree
   */
  public static WUGraph minSpanTree(WUGraph g) {
    WUGraph minSpanTree = new WUGraph();
    Object[] copyVertices = g.getVertices();

    HashTableChained<Object, Integer> verticeHash = new HashTableChained<Object, Integer>();
    for (int i = 0; i < copyVertices.length; i++) {
      minSpanTree.addVertex(copyVertices[i]);
      verticeHash.insert(copyVertices[i], i);
    }

    KEdge[] arrayEdges = getEdge(g);
    heapLength = arrayEdges.length;
    arrayLength = arrayEdges.length;
    arrayEdges = toHeap(arrayEdges);
    arrayEdges = heapSort(arrayEdges);

    DisjointSets dSet = new DisjointSets(copyVertices.length);

    for (int i = 0; i < arrayLength; i++) {
      KEdge edge = arrayEdges[i];
      int vertice1, vertice2;
      vertice1 = verticeHash.find(edge.getVertex().object1);
      vertice2 = verticeHash.find(edge.getVertex().object2);
      if (dSet.find(vertice1) != dSet.find(vertice2)) {
        minSpanTree.addEdge(edge.getVertex().object1, edge.getVertex().object2,
            edge.getWeight());
        dSet.union(dSet.find(vertice1), dSet.find(vertice2));
      }
    }
    return minSpanTree;
  }

  /**
   * getEdge() creates an array of KEdges that are representative 
   * of the edges in graph g and returns that array.
   * 
   * @param g
   * @return
   */
  private static KEdge[] getEdge(WUGraph g) {
    Object[] copyVertices = g.getVertices();
    int edgeCount = 0;
    KEdge[] arrayEdges = new KEdge[g.edgeCount()];
    for (int i = 0; i < copyVertices.length; i++) {
      Neighbors neighbor = g.getNeighbors(copyVertices[i]);
      for (int j = 0; j < neighbor.neighborList.length; j++) {
        boolean inArray = false;
        KEdge edge = new KEdge(copyVertices[i], neighbor.neighborList[j],
            neighbor.weightList[j]);
        for (int k = 0; k < edgeCount; k++) {
          if (arrayEdges[k].equals(edge)) {
            inArray = true;
            break;
          }
        }
        if (!inArray) {
          arrayEdges[edgeCount] = edge;
          edgeCount++;
        }
      }
    }
    return arrayEdges;
  }

  /**
   * toHeap() takes in an array of KEdges and re-orders it to satisfy 
   * the heap order invariant.
   * 
   * @param array an array of KEdges
   * @return an array of KEdges satisfying the heap order invariant
   */
  private static KEdge[] toHeap(KEdge[] array) {
    KEdge[] newArray = new KEdge[array.length];
    for (int i = 0; i < array.length; i++) {
      newArray[i] = array[i];
      heapUp(newArray, i);
    }
    return newArray;
  }

  /**
   * heapUp() takes in an array with the given length and checks from
   * the bottom if the heap order invariant is satisfied.
   * 
   * @param array an array of KEdges
   * @param length the length of the KEdge array
   */
  private static void heapUp(KEdge[] array, int length) {
    int i = length + 1;
    while (i != 1) {
      if (i % 2 == 1) {
        if (Math.min(array[i - 1].getWeight(), array[i - 2].getWeight()) < array[(i - 3) / 2]
            .getWeight()) {
          KEdge temp = array[(i - 3) / 2];
          if (array[i - 1].getWeight() < array[i - 2].getWeight()) {
            array[(i - 3) / 2] = array[i - 1];
            array[i - 1] = temp;
          }
          else {
            array[(i - 3) / 2] = array[i - 2];
            array[i - 2] = temp;
          }
        }
        i -= 2;
      }
      else {
        if (array[i - 1].getWeight() < array[i / 2 - 1].getWeight()) {
          KEdge temp = array[i / 2 - 1];
          array[i / 2 - 1] = array[i - 1];
          array[i - 1] = temp;
        }
        i -= 1;
      }
    }
    return;
  }

  /**
   * heapSort() takes in an array of KEdges and sorts it using
   * the heapSort method.
   * 
   * @param array an array of KEdges
   * @return an ordered array of KEdges by weight of each KEdge
   */
  private static KEdge[] heapSort(KEdge[] array) {
    KEdge[] temp = new KEdge[heapLength];
    for (int i = 0; i < arrayLength; i++) {
      temp[i] = removeMin(array);
    }
    array = temp;
    return temp;
  }

  /**
   * removeMin() takes in an array of KEdges and removes the minimum
   * KEdge weight.
   * 
   * @param array an array of KEdges
   * @return the KEdge that was removed
   */
  private static KEdge removeMin(KEdge[] array) {
    KEdge edge = array[0];
    array[0] = array[heapLength - 1];
    int i = 1;
    while (i <= heapLength / 2
        && array[i - 1].getWeight() > Math.min(array[2 * i - 1].getWeight(),
            array[2 * i].getWeight())) {
      KEdge temp = array[i - 1];
      if (array[2 * i - 1].getWeight() < array[2 * i].getWeight()) {
        array[i - 1] = array[2 * i - 1];
        array[2 * i - 1] = temp;
        i *= 2;
      }
      else {
        array[i - 1] = array[2 * i];
        array[2 * i] = temp;
        i = i * 2 + 1;
      }
    }
    heapLength--;
    return edge;
  }

}
