package roadgraph;

import java.util.List;

/** A class that represents information about a path in a geographic map. 
 * 
 * Useful for returning multiple data types from graph search methods.
 * 
 * @author ryanwilliamconnor
 */
import geography.GeographicPoint;

/** A class that represents a path in a graph that represents a geographic location. 
 * Useful for returning multiple data types from graph search methods (e.g., a list
 * of vertices on a path and the path's length).
 * @author UCSD Team 
 * @author ryanwilliamconnor
 * 
 */
public class Path {

	private List<GeographicPoint> path;

	private List<MapEdge> roadsTaken;
	private double length;
	private double travelTime;
	
	public Path(List<GeographicPoint> path, 
					  List<MapEdge> roadsTaken,
					  double length, 
					  double travelTime) {
		
		this.path = path;
		this.roadsTaken = roadsTaken;
		this.length = length;
		this.travelTime = travelTime;
	}
	
	public List<GeographicPoint> getPath() {
		
		return path;
	}
	
	public List<MapEdge> getRoadsTaken() {
		
		return roadsTaken;
	}
	
	public double getLength() {
		
		return length;
	}
	
	public void setLength(double length) {
		
		this.length = length;
	}
	
	public double getTravelTime() {
		
		return travelTime;
	}
	
	public void setTravelTime(double travelTime) {
		
		this.travelTime = travelTime;
	}
}
