package au.gov.hack.health.iot.core.domain;

public class Bed {

	private String hospital;
	
	private String floor;
	
	private String room;
	
	private String publicId;
	
	private String occupiedByPersonId;
	
	public String getOccupiedByPersonId() {
		return occupiedByPersonId;
	}

	public void setOccupiedByPersonId(String occupiedByPersonId) {
		this.occupiedByPersonId = occupiedByPersonId;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}


	
	
	
}
