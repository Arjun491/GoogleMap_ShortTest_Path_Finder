package basicgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* This class shows the another way 
 to implement the adding of edges by a better method 
 * that is by creating the adjacent list rather then matrix 
 * matrix consumes a lot of space and quiet slower as compared to list  
 * 
 * @author UCSD MOOC development team
 * @author ryanwilliamconnor
 *
 */
public class GraphAdjList extends Graph {

// here, using the map datastructure that has a key and value of the vertexs
	private static Map<Integer,ArrayList<Integer>> adjListMap;
	
	// Initializing the adjListmap map and creating a new map 
	public GraphAdjList () {
		adjListMap = new HashMap<Integer,ArrayList<Integer>>();
	}
	
	//generated the adj list as a String representation 
	public String adjacencyString() {
		String s = "The Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + " integer):";

		for (int v : adjListMap.keySet()) {
			s += "\n \t"+v+":: ";
			for (int w : adjListMap.get(v)) {
				s += w+", ";
			}
		}
		return s;
	}

	// this will add a new vertex
	public void implementAddVertex() {
		int v = getNumVertices();
		 System.out.println("Adding vertex "+v);
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		adjListMap.put(v,  neighbors);
	}
	// v and w are the start and end node 
	// this method implement the edges 
	public void implementAddEdge(int v, int w) {
		(adjListMap.get(v)).add(w);

	}
	
	// finding all 
	//  out-neighbors of a vertex.
	 // in case of out and in degree method if there are multiple edges the neighbor will apear one 	
	public List<Integer> getNeighbors(int v) {
		return new ArrayList<Integer>(adjListMap.get(v));
	}

	 // find all the in degree vertex 
	public List<Integer> getInNeighbors(int v) {
		List<Integer> inDegNeighbors = new ArrayList<Integer>();
		for (int u : adjListMap.keySet()) {
			//iterate through all edges in u's adjacency list and 
			//add u to the inNeighbor list of v whenever an edge
			//with startpoint u has endpoint v.
			for (int w : adjListMap.get(u)) {
				if (v == w) {
					inDegNeighbors.add(u);
				}
			}
		}
		return inDegNeighbors;
	}
	 
	// to find all the vertex the could be reach by 2 hops from v

	//where v--> vertex and  	
	 public List<Integer> getDistance2(int v) {
		 
		 List<Integer> twoHopPath = new ArrayList<Integer>(); // list of indices of vertex //
		 
		 List<Integer> outNeighborsFirst;
		 List<Integer> outNeighborsSecond;
		 
		 if (getNeighbors(v) != null){ // in case the node is not present, no new neighbors
			 
			 outNeighborsFirst = getNeighbors(v);
			 
		
			 for (int i = 0; i < outNeighborsFirst.size(); i++) {
				 
				 if (getNeighbors(outNeighborsFirst.get(i)) != null) {
					 
					 outNeighborsSecond = getNeighbors(outNeighborsFirst.get(i));
					 
					 for (int j = 0; j < outNeighborsSecond.size(); j++) {
					
						 twoHopPath.add(outNeighborsSecond.get(j));
					 } 
				 }
			 }
		 }
		 
		 return twoHopPath; // return 2 hop values 
	}
	





}
