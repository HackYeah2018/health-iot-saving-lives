package au.gov.hack.health.iot.core.domain;

import java.util.List;

public class Room {

	private String hospitalId;
	
	private String floorId;
	
	private String id;

	private String name;
	
	List<Point> corners;

	private Point center;
	
	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Point> getCorners() {
		return corners;
	}

	public void setCorners(List<Point> corners) {
		this.corners = corners;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	
	
	
}
