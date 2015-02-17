import java.awt.geom.Point2D;

public class Links {
	public Point2D p1;
	public Point2D p2;
	public int minutes;
	public double distance;
	public AmusementPark travelToLocation;

	public Links(AmusementPark first, AmusementPark second, int minutesToTravel) {
		p1 = first.getLocation();
		p2 = second.getLocation();
		this.distance = calcDistance();
		this.minutes = minutesToTravel;
		this.travelToLocation = second;
	}

	public double calcDistance() {
		return (double) p1.distance(p2);
	}
	
	public double getDistance(){
		return this.distance;
	}
	
	public int getTime(){
		return this.minutes;
	}
	
	public AmusementPark getLinkLocation() {
		return this.travelToLocation;
	}
}
