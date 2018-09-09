package au.gov.hack.health.iot.stub;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.domain.Floor;
import au.gov.hack.health.iot.core.domain.Point;
import au.gov.hack.health.iot.core.domain.Room;
import au.gov.hack.health.iot.core.services.FloorService;
import au.gov.hack.health.iot.core.services.RoomService;

@Component
public class RoomServiceStub implements RoomService {

	@Override
	public Room get(String id) {
		Room f = buildRoom("1", 10);
		
		return f;
	}

	protected Room buildRoom(String id, int offset) {
		Room f = new Room();
		f.setFloorId("0");
		f.setHospitalId("aaa");
		f.setId(id);
		f.setName(id);
		
		List<Point> corners = new ArrayList<>();
		corners.add(new Point(30 + offset, 130, 0));
		corners.add(new Point(60 + offset, 130, 0));
		corners.add(new Point(60 + offset, 160, 0));
		corners.add(new Point(30 + offset, 130, 0));	
		f.setCorners(corners);
		f.setCenter(new Point(35 + offset, 135, 0));
		return f;
	}

	@Override
	public List<Room> allForFloor(String floorId) {
		List<Room> result = new ArrayList<Room>();
		
		result.add(buildRoom("1", 10));
		result.add(buildRoom("2", 50));
		result.add(buildRoom("3", 90));
		
		
		return result;
	}
	
}
