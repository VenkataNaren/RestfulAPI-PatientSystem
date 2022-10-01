package com.nz.orion.patient.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.nz.orion.patient.model.Patient;

/**
 * Service related to patients information system
 *
 * @author Venkata Narendra
 */

public interface PatientService {

	/**
	 * @see com.nz.orion.patient.service.PatientService#addPatient(Patient patient)
	 *      returns true if patient is not exits and false if exists
	 */
	public boolean addPatient(Patient patient);

	/**
	 * @see com.nz.orion.patient.service.PatientService#savePatient(Patient patient)
	 *      stores patient record
	 */
	public Patient savePatient(Patient patient);

	/**
	 * @see com.nz.orion.patient.service.PatientService#getAllPatient() returns all
	 *      patient records
	 */
	public List<Patient> getAllPatient();

	/**
	 * @see com.nz.orion.patient.service.PatientService#findByFirstNameAndLastNameAndDateOfBirth(String
	 *      firstName, String lastName, Date dateOfBirth) returns all patient
	 *      records whose firstName, lastName and date of birth matches this service
	 *      is used to find existing patient record
	 */
	List<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth);

	/**
	 * @see com.nz.orion.patient.service.PatientService#findByFirstNameStartsWith(String
	 *      firstName) returns all patient records whose first name start with the
	 *      given string
	 */
	List<Patient> findByFirstNameStartsWith(String firstName);

	/**
	 * @see com.nz.orion.patient.service.PatientService#findById(Long id) returns
	 *      all patient record with the given id
	 */
	public Patient findById(Long id);

	/**
	 * @see com.nz.orion.patient.service.PatientService#findBirthYearCount(int
	 *      startRange, int endRange) returns count for patients born in year range
	 *      in descending order
	 */
	public Map<Integer, Long> findBirthYearCount(int startRange, int endRange);
}
