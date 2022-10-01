package com.nz.orion.patient.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientUtils {

	// method to count number of patients in a year within date range in years and
	// returned in map object with year and count of patients in key value pair
	public static <T> Map<T, Long> countByPatientBirthYear(List<T> inputList) {
		Map<T, Long> resultMap = new HashMap<>();
		for (T element : inputList) {
			if (resultMap.containsKey(element)) {
				resultMap.put(element, resultMap.get(element) + 1L);
			} else {
				resultMap.put(element, 1L);
			}
		}
		return resultMap;
	}

	// method to convert String year to sql date
	public static java.sql.Date convertStringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlDate = null;
		try {
			Date javaDate = sdf.parse(date);
			sqlDate = new java.sql.Date(javaDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}

}
