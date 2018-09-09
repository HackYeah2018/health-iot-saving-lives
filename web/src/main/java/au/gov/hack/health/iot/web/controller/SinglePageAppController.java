package au.gov.hack.health.iot.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SinglePageAppController {

	   @RequestMapping(path={"/app/main.html"},method=RequestMethod.GET)
	   public String buildPage(Model model, HttpServletRequest request) {
	      model.addAttribute("basePath",request.getContextPath());
	      
	      //Java 8 LocalDate
	      DateTimeFormatter formatter=DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
	      LocalDate date=LocalDate.now();
	      model.addAttribute("date", date.format(formatter));
	      
	      return "main";
	   }
	
}
