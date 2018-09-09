package au.gov.hack.health.iot.core.utils;

import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

public class LocalDateDeserializer extends StdScalarDeserializer<LocalDate> {
	private static final long serialVersionUID = 1L;

	DateTimeFormatter format = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd");

	public LocalDateDeserializer() {
		super(LocalDate.class);
	}

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		switch (p.getCurrentTokenId()) {
		case JsonTokenId.ID_STRING:
			String str = p.getText().trim();
			return (str.length() == 0) ? null : format.parseLocalDate(str);

		}
		throw ctxt.wrongTokenException(p, JsonToken.START_ARRAY, "expected String");
	}
}
