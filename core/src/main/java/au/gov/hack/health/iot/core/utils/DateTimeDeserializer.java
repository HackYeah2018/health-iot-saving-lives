package au.gov.hack.health.iot.core.utils;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

public class DateTimeDeserializer extends StdScalarDeserializer<DateTime> {
	private static final long serialVersionUID = 1L;

	DateTimeFormatter format = ISODateTimeFormat.basicDateTime();

	public DateTimeDeserializer() {
		super(DateTime.class);
	}

	@Override
	public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		switch (p.getCurrentTokenId()) {
		case JsonTokenId.ID_STRING:
			String str = p.getText().trim();
			return (str.length() == 0) ? null : format.parseDateTime(str);

		}
		throw ctxt.wrongTokenException(p, JsonToken.START_ARRAY, "expected String");
	}
}
