package com.nz.orion.patient.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nz.orion.patient.model.Patient;
import com.nz.orion.patient.service.PatientService;

/**
 * REST front end Controller which serves as an entry-point for requests Patient
 * System information.
 *
 * @author Venkata Narendra
 */
@Controller
public class PatientFrontEndController {

	Logger logger = LoggerFactory.getLogger(PatientFrontEndController.class);

	@Autowired
	PatientService patientService;

	/**
	 * API Operation that returns the loading page with all patients
	 *
	 */
	@RequestMapping("/")
	public String viewPatientPage(Model model) {

		logger.info("viewPatientPage called...");
		List<Patient> listPatient = patientService.getAllPatient();
		model.addAttribute("listPatient", listPatient);

		return "index";
	}

	/**
	 * API Operation to add patient
	 *
	 */
	@RequestMapping("/newPatient")
	public String showNewPatientPage(Model model) {
		logger.info("showNewPatientPage called...");
		Patient patient = new Patient();
		model.addAttribute("patient", patient);

		return "new_patient";
	}

	/**
	 * API Operation to search patient with given string
	 *
	 */
	@RequestMapping("/searchPatientPage")
	public String showSearchPatientPage(Model model) {
		logger.info("showNewPatientPage called...");
		Patient patient = new Patient();
		model.addAttribute("patient", patient);

		return "search_patient";
	}

	/**
	 * API Operation that stores patients record and returns true, if patient record
	 * exits it returns false
	 *
	 */
	@RequestMapping(value = "/addPatient", method = RequestMethod.POST)
	public String insertPatient(@ModelAttribute("product") Patient patient) {
		logger.info("patient adding...");
		List<Patient> patientData = patientService.findByFirstNameAndLastNameAndDateOfBirth(patient.getFirstName(),
				patient.getLastName(), patient.getDateOfBirth());
		for (Patient patientLoop : patientData) {
			if (patientLoop.getFirstName().equals(patient.getFirstName())
					&& patientLoop.getLastName().equals(patient.getLastName())
					&& patientLoop.getDateOfBirth().toString().equals(patient.getDateOfBirth().toString())) {
				// return false;
			}

		}
		patientService.addPatient(patient);
		return "redirect:/";
	}

	/**
	 * API Operation that returns all patients who's name starts with given string
	 *
	 */
	@RequestMapping(value = "/patientsStartsWith/{firstNameStartsWith}", method = RequestMethod.GET)
	public String getPatientStartsWith(Model model, String firstNameStartsWith) {
		logger.info("patient name start with..." + firstNameStartsWith);
		List<Patient> patientsList = new ArrayList<Patient>();
		patientsList = patientService.findByFirstNameStartsWith(firstNameStartsWith);
		model.addAttribute("patientsList", patientsList);
		return "search_patient";
	}

}
