package au.gov.hack.health.iot.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.services.PersonService;

@RestController
public class PersonController {

	private static final Logger logger = Logger.getLogger(PersonController.class);
	
	@Autowired
	private PersonService service;
	
	@GetMapping("/person/{id}")
	public Person getPerson(@PathVariable("id") String person) {
		return service.getPerson(person);
	}
	
}
