package au.gov.hack.health.iot.core.services;

import java.util.List;

import au.gov.hack.health.iot.core.domain.Room;

public interface RoomService {

	public Room get(String id);
	
	public List<Room> allForFloor(String floorId);
	
}
