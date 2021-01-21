package basicgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** this class uses the directed graph that  add vertex
 *  and edges by uding the adjacent matrix method 
 * 
 * @author UCSD MOOC development team 
 * @author Arjun Singh and Royali Bahri
 *
 */
public class GraphAdjMatrix extends Graph {

	private static  final int defaultNumberOfVertices = 5; 
	private int[][] adjMatrix;
	// here we created a empty graph with default vertes 5 
	public GraphAdjMatrix () {
		adjMatrix = new int[defaultNumberOfVertices][defaultNumberOfVertices];
	}
	
	// increasing the abstarct method by 2 , a simple way to add more vertex by 2 for while 
	public void implementAddVertex() {
		int v = getNumVertices();
		if (v >= adjMatrix.length) {
			int[][] newAdjMatrix = new int[v*2][v*2];
			for (int i = 0; i < adjMatrix.length; i ++) {
				
				for (int j = 0; j < adjMatrix.length; j ++) {
					newAdjMatrix[i][j] = adjMatrix[i][j];
				}
			} 
			adjMatrix = newAdjMatrix;
		}
		for (int i=0; i < adjMatrix[v].length; i++) {
			adjMatrix[v][i] = 0;
		}
	}
	
	// adding the edge by adding the 1's in the matrix 
	// here is a way, if there is no edge in between the two node  mark 0 in the matrix else 1 
	// in case road is two way add 1 to the existing edge that has a value 1 so make 1+1=2 
	
	public void implementAddEdge(int v, int w) {
		adjMatrix[v][w] += 1;
	}
	
	// This is method is to find out the all neighbors 
	 
	// // in case there are multiple edges between two vertes,out degree- neighbors will appear once !//
	 
	public List<Integer> getNeighbors(int v) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[v][i]; j ++) {
				neighbors.add(i);
			}
		}
		return neighbors;
	}
	
	// This is method is to find out the all neighbors 
	 
	// // in case there are multiple edges between two vertes,in degree- neighbors will appear once !//
	 
	public List<Integer> getInNeighbors(int v) {
		List<Integer> inNeighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[i][v]; j++) {
				inNeighbors.add(i);
			}
		}
		return inNeighbors;
	}
	
// this method implement the 2-hop trick to reach the edge after visiting the two edge
	 public List<Integer> getDistance2(int v) {
		 
		 List<Integer> twoHopPath = new ArrayList<Integer>();
		 
		 List<Integer> outNeighborsFirst;
		 List<Integer> outNeighborsSecond;
		 
		 if (getNeighbors(v) != null){
			 
			 outNeighborsFirst = getNeighbors(v);
			 
			 // iterate through the vertex's immediate neighbors to get THEIR
			 // immediate neighbors
			 // then add all of 2nd immediate neighbors to the return list
			 for (int i = 0; i < outNeighborsFirst.size(); i++) {
				 
				 if (getNeighbors(outNeighborsFirst.get(i)) != null) {
					 
					 outNeighborsSecond = getNeighbors(outNeighborsFirst.get(i));
					 
					 for (int j = 0; j < outNeighborsSecond.size(); j++) {
					
						 twoHopPath.add(outNeighborsSecond.get(j));
					 } 
				 }
			 }
		 }
		 
		 return twoHopPath;
	}
	
	
	 // to get matrix as a string representation ......//
	 
	public String adjacencyString() {
		int dim = adjMatrix.length;
		String s = "Adjacency matrix";
		s += " (size " + dim + "x" + dim + " = " + dim* dim + " integers):";
		for (int i = 0; i < dim; i ++) {
			s += "\n\t"+i+": ";
			for (int j = 0; j < adjMatrix[i].length; j++) {
			s += adjMatrix[i][j] + ", ";
			}
		}
		return s;
	}


}
