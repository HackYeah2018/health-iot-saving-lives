package au.gov.hack.health.iot.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.dao.HBasePersonDao;
import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.services.PersonService;

@Component
public class PersonServiceImpl implements PersonService {

	@Autowired
	private HBasePersonDao personDao;
	
	public Person getPerson(String id) {
		return personDao.get(id);
		
	}
	
}
