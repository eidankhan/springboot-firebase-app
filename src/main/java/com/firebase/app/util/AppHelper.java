package com.firebase.app.util;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.app.Employee;

public class AppHelper {

	public static Map<String, Object> convertObjectToMap(Employee employee) {
		System.out.println("AppHelper.convertObjectToMap(employee) --> is called");
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> convertedValue = objectMapper.convertValue(employee, Map.class);
		System.out.println("Converted Value --> " + convertedValue);
		return convertedValue;
	}

}
