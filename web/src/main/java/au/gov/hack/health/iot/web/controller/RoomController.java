package au.gov.hack.health.iot.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import au.gov.hack.health.iot.core.domain.Room;
import au.gov.hack.health.iot.core.domain.RoomStatus;
import au.gov.hack.health.iot.core.services.RoomService;

@RestController
public class RoomController {

	private static final Logger logger = Logger.getLogger(RoomController.class);
	
	@Autowired
	private RoomService service;
	
	@GetMapping("/room/{id}")
	public Room getRoom(@PathVariable("id") String roomId) {
		return service.get(roomId);
	}
	
	@GetMapping("/rooms/{floorId}")
	public List<Room> getRooms(@PathVariable("floorId") String floorId) {
		return service.allForFloor(floorId);
	}
	
	@PostMapping("/room/{hospitalId}/{floorId}/{roomId}")
	public void updateRoom(@PathVariable("hospitalId") String hospitalId, @PathVariable("floorId") String floorId,@PathVariable("roomId") String roomId, @RequestBody RoomStatus update) {
		logger.info("Doing room update for hospital " + hospitalId);
		logger.info("Doing room update for floor " + floorId);
		logger.info("Doing room update for room " + roomId);
		
		logger.info("Patient ID: " + update.getPatientId());
		logger.info("ECG ID: " + update.getEcgId());
		logger.info("Heart Monitor ID: " + update.getHeartMonitorId());
		
		
	}
}
