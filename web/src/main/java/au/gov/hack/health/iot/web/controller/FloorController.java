package au.gov.hack.health.iot.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import au.gov.hack.health.iot.core.domain.Floor;
import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.services.FloorService;
import au.gov.hack.health.iot.core.services.PersonService;

@RestController
public class FloorController {

	private static final Logger logger = Logger.getLogger(FloorController.class);
	
	@Autowired
	private FloorService service;
	
	@CrossOrigin(origins = "*")
	@GetMapping("/floor/{id}")
	public Floor getPerson(@PathVariable("id") String person) {
		return service.get(person);
	}
	
}
