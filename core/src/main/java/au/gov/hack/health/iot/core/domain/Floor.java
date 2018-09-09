package au.gov.hack.health.iot.core.domain;

import java.util.List;

public class Floor {

	private String hospitalId;
	
	private String id;
	
	private String name;

	private List<Point> corners;
	
	private List<Room> rooms;
	
	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Point> getCorners() {
		return corners;
	}

	public void setCorners(List<Point> corners) {
		this.corners = corners;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	
	
}
