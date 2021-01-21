package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/** A class that represents a node in a graph that represents a geographic location. 
 * 
 * @author ryanwilliamconnor
 */
public class MapIntersection extends GeographicPoint {
	// the adjacency list
	private List<MapEdge> neighbors;
	
	// a  mapintersection a 2d point takes latitude and longitude 
	public MapIntersection(double latitude, double longitude) {

		super(latitude, longitude);
		neighbors = new ArrayList<MapEdge>();
	}
	
	// create a  method  with list, to g
	public List<MapEdge> getNeighbors() {
		
		return neighbors;
	}
	
	// make immutable
	@Override
	public void setLocation(double x, double y) {
		
		throw new IllegalArgumentException("Under penalty of death, MapIntersections"
				+ " cannot change location.");
	}
	
}
