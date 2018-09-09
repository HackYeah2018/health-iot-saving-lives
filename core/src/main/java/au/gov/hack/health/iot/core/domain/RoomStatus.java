package au.gov.hack.health.iot.core.domain;

public class RoomStatus {

	private String roomId;
	
	private String patientId;
	
	private String ecgId;
	
	private String heartMonitorId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getEcgId() {
		return ecgId;
	}

	public void setEcgId(String ecgId) {
		this.ecgId = ecgId;
	}

	public String getHeartMonitorId() {
		return heartMonitorId;
	}

	public void setHeartMonitorId(String heartMonitorId) {
		this.heartMonitorId = heartMonitorId;
	}
	
	
	
}
