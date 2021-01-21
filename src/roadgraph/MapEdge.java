package roadgraph;

import javax.swing.JOptionPane;

/*
 * Arjun Singh and Royali
 * 
 * 
 * */
public class MapEdge {

		private MapIntersection intersectionFrom;
		private MapIntersection intersectionTo;
		private String roadName;
		private String roadType;
		private double length; // in miles
		private double speedLimit; // in miles per hour
		private double travelTime; // in seconds
		
		public MapEdge(MapIntersection fromIntersection,
				       MapIntersection toIntersection,
				       String roadName, String roadType, double length) {
			
			this.roadName = roadName;
			this.roadType = roadType;
			this.length = length*0.621; // assumes length is given in kilometers
			this.intersectionFrom = fromIntersection;
			this.intersectionTo = toIntersection;
			
			switch(roadType)
			{
			case "motorway":
				this.speedLimit = 70.0;
				
	case "trunk":
		this.speedLimit = 60.0;
				
	case "primary":
		this.speedLimit = 55.0;
		
	case "secondary":
		this.speedLimit = 45.0;
		
	case "tertiary":
		this.speedLimit = 40.0;
		
	case "unclassified":
		this.speedLimit = 35.0;
	
	case "residential":
		this.speedLimit = 25.0;
		
	case "service":
		this.speedLimit = 15.0;
		
	case "flight":
		this.speedLimit = 550.0;
		
		default:
			this.speedLimit=40.0;
			}
	
		
			
			setTravelTime(this.speedLimit);
			//System.out.println("Length of " + this.roadName + " is " + this.length);
			//System.out.println("Travel time " + this.roadName + " is " + travelTime);
		}
		
	
		public MapIntersection getFromIntersection() {
			
			return intersectionFrom;
		}
		
		public MapIntersection getToIntersection() {
			return intersectionTo;
		}
		// to support changing the speed limit on this road
		public void setTravelTime(double speedLimit) {
			
			this.travelTime = (length / speedLimit)*60; // gives time in minutes
		}
		
		public double getLength() {
			return length;
		}
		
		public double getTravelTime() {
			return travelTime;
		}
		
		public String getRoadName() {
			return roadName;
		}
		
		public String getRoadType() {
			return roadType;
		}
		
	
}
