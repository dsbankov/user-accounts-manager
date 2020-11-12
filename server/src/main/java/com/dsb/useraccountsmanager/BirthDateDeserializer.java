package com.dsb.useraccountsmanager;

import java.io.IOException;
import java.sql.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class BirthDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
		String dateString = jsonParser.getText();
		// allow empty birth date
		if (dateString.isEmpty()) {
			return null;
		}
		try {
			// format should be yyyy-[m]m-[d]d
			// if not, throw custom exception so we can catch it in the REST controller
			return Date.valueOf(dateString);
		} catch (IllegalArgumentException e) {
			throw new BirthDateDeserializationException(dateString);
		}
	}

}
