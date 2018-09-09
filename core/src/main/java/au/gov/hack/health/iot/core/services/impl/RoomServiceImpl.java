package au.gov.hack.health.iot.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.dao.HBaseFloorDao;
import au.gov.hack.health.iot.core.dao.HBaseRoomDao;
import au.gov.hack.health.iot.core.domain.Floor;
import au.gov.hack.health.iot.core.domain.Room;
import au.gov.hack.health.iot.core.services.FloorService;
import au.gov.hack.health.iot.core.services.RoomService;

/**
 * Temporary service which will be deprecated in favour of the HospitalService.
 * 
 * @author davidlevy
 *
 */
@Component
public class RoomServiceImpl implements RoomService {

	
	@Autowired
	private HBaseRoomDao roomDao;
	
	@Override
	public Room get(String id) {
		return roomDao.get(id);
	}

	@Override
	public List<Room> allForFloor(String floorId) {
		return roomDao.allForFloor(floorId);
	}
	
}
