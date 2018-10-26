package com.training.webservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean() {
		SomeBean someBean = new SomeBean("value1","value2","value3");
		
		SimpleBeanPropertyFilter simpleFilter = 
				SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filter = 
				new SimpleFilterProvider().addFilter("someBeanFilter", simpleFilter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		
		mapping.setFilters(filter);
		
		return mapping;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue retrieveListOfSomeBean() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","value3"),
				new SomeBean("value1","value2","value3"));
		
		SimpleBeanPropertyFilter simpleFilter = SimpleBeanPropertyFilter.
				filterOutAllExcept("field2","field3");
		FilterProvider filter = new SimpleFilterProvider().
				addFilter("someBeanFilter", simpleFilter);
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		
		mapping.setFilters(filter);
		
		return mapping;
	}
	
	
}
