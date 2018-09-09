package au.gov.hack.health.iot.stub;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.domain.Floor;
import au.gov.hack.health.iot.core.domain.Point;
import au.gov.hack.health.iot.core.services.FloorService;
import au.gov.hack.health.iot.core.services.RoomService;

@Component
public class FloorServiceStub implements FloorService {

	@Autowired
	RoomService roomService;
	
	@Override
	public Floor get(String id) {
		Floor f = new Floor();
		
		List<Point> corners = new ArrayList<>();
		corners.add(new Point(30, 30, 0));
		corners.add(new Point(60, 30, 0));
		corners.add(new Point(60, 60, 0));
		corners.add(new Point(30, 30, 0));		
		f.setCorners(corners);
		
		f.setRooms(roomService.allForFloor(id));
		
		return f;
	}
	
}
