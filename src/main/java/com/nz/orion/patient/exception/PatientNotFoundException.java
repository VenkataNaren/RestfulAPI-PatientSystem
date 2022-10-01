package com.nz.orion.patient.exception;


public class PatientNotFoundException extends RuntimeException {

	public PatientNotFoundException(Long id) {
		super("Could not find patient " + id);
	}

}
