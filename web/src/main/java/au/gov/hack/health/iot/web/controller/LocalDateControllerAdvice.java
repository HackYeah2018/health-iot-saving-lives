package au.gov.hack.health.iot.web.controller;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import au.gov.hack.health.iot.core.utils.HealthJacksonFactory;

@ControllerAdvice
public class LocalDateControllerAdvice {

	private static Logger logger = Logger.getLogger(LocalDateControllerAdvice.class);

	DateTimeFormatter format = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd");

	HealthJacksonFactory jacksonFactory = new HealthJacksonFactory();

	public void initialize() {
		jacksonFactory.buildMapper();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if (StringUtils.isNotBlank(text)) {

					setValue(format.parseLocalDate(text));

				}
			}
		});
	}
}
