import java.awt.geom.Point2D;

public class Links {
	Point2D p1;
	Point2D p2;
	int minutes;
	int distance;

	public Links(AmusementPark first, AmusementPark second) {
		p1 = first.getLocation();
		p2 = second.getLocation();
		this.distance = clacDistance();
		this.minutes = calcTime();
	}

	public int clacDistance() {
		return (int) p1.distance(p2);
	}

	public int calcTime() {
		return (this.distance / 70) * 60;
	}
}
