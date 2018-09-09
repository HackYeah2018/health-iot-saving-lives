package au.gov.hack.health.iot.core.utils;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class LocalDateSerializer extends StdScalarSerializer<LocalDate> {
	private static final long serialVersionUID = 1L;

	DateTimeFormatter format = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd");

	public LocalDateSerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
        	gen.writeString(format.print(value));
        }
		
	}

	
	
}
