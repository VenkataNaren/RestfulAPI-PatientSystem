package com.nz.orion.patient.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class PatientNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(PatientNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String patientNotFoundHandler(PatientNotFoundException ex){
		return ex.getMessage();
	}

}
