package com.nz.orion.patient.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nz.orion.patient.exception.PatientNotFoundException;
import com.nz.orion.patient.model.Patient;
import com.nz.orion.patient.repo.PatientRepository;
import com.nz.orion.patient.service.PatientService;

/**
 * REST Controller which serves as an entry-point for requests for Patient
 * System information.
 *
 * @author Venkata Narendra
 */
@RequestMapping("/api")
@RestController
public class PatientController {

	Logger logger = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	PatientService patientService;

	@Autowired
	PatientRepository patientRepository;

	/**
	 * API Operation that returns all patients
	 *
	 */
	@RequestMapping(value = "/allPatients", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> getAll() {
		logger.info("All patients called...");
		try {
			List<Patient> patients = new ArrayList<Patient>();
			patients = patientService.getAllPatient();
			if (patients.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(patients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * API Operation that returns patient whose id is supplied
	 *
	 */
	@RequestMapping(value = "/getPatient/{id}", method = RequestMethod.GET)
	public Patient getPatientWithId(@PathVariable Long id) {
		logger.info("patient called with id : " + id);
		return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
	}

	/**
	 * API Operation that stores patients record and returns true, if patient record
	 * exits it returns false
	 *
	 */
	@RequestMapping(value = "/addPatient", method = RequestMethod.POST)
	public boolean insertPatient(@RequestBody Patient patient) {
		logger.info("patient adding...");
		List<Patient> patientData = patientService.findByFirstNameAndLastNameAndDateOfBirth(patient.getFirstName(),
				patient.getLastName(), patient.getDateOfBirth());
		for (Patient patientLoop : patientData) {
			if (patientLoop.getFirstName().equals(patient.getFirstName())
					&& patientLoop.getLastName().equals(patient.getLastName())
					&& patientLoop.getDateOfBirth().toString().equals(patient.getDateOfBirth().toString())) {
				return false;
			}

		}
		return patientService.addPatient(patient) ? true : false;
	}

	/**
	 * API Operation that returns all patients who's name starts with given string
	 *
	 */
	@RequestMapping(value = "/patientsStartsWith/{firstNameStartsWith}", method = RequestMethod.GET)
	public ResponseEntity<List<Patient>> getPatientStartsWith(@PathVariable String firstNameStartsWith) {
		logger.info("patient name start with..." + firstNameStartsWith);
		List<Patient> patientsList = new ArrayList<Patient>();
		try {
			patientsList = patientService.findByFirstNameStartsWith(firstNameStartsWith);
			if (patientsList == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} else if (patientsList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<List<Patient>>(patientsList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * API Operation that returns the number of patients born each year in given
	 * date range in years in descending order
	 *
	 */
	@RequestMapping(value = "/getPatient/{startYear}/{endYear}", method = RequestMethod.GET)
	public Map<Integer, Long> getPatientCountYearRange(@PathVariable int startYear, @PathVariable int endYear) {
		logger.info("patient count with year range startYear : " + startYear + "--endYear : " + endYear);
		return patientService.findBirthYearCount(startYear, endYear);
	}
}
