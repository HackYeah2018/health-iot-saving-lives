package au.gov.hack.health.iot.core.utils;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

public class DateTimeSerializer extends StdScalarSerializer<DateTime> {
	private static final long serialVersionUID = 1L;

	DateTimeFormatter format = ISODateTimeFormat.basicDateTime();

	public DateTimeSerializer() {
		super(DateTime.class);
	}

	@Override
	public void serialize(DateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
        	gen.writeString(format.print(value));
        }
		
	}

	
	
}
