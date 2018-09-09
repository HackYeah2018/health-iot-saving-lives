package au.gov.hack.health.iot.stub;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import au.gov.hack.health.iot.core.domain.Gender;
import au.gov.hack.health.iot.core.domain.Person;
import au.gov.hack.health.iot.core.services.PersonService;

@Component
public class PersonServiceStub implements PersonService {

	@Override
	public Person getPerson(String id) {
		Person p = new Person();
		p.setId(id);
		p.setAdmin(true);
		p.setDateOfBirth(new LocalDate());
		p.setEmail("test@example.com");
		p.setGender(Gender.MALE);
		p.setName("Test");
		p.setUsername("test");
		
		
		return p;
	}

}
