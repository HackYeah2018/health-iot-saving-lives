package au.gov.hack.health.iot.core.utils;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class HealthJacksonFactory {

	private ObjectMapper mapper;
	
	@PostConstruct
	public void buildMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		SimpleModule module = new SimpleModule("health-date-module");
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addDeserializer(DateTime.class, new DateTimeDeserializer());
		module.addSerializer(DateTime.class, new DateTimeSerializer());
		mapper.registerModule(module);
		
		//mapper.registerModule(new JavaTimeModule());
		//mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		this.mapper = mapper;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
	
	
	
}
