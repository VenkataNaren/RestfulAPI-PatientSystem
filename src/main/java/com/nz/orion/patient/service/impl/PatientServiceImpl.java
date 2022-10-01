package com.nz.orion.patient.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nz.orion.patient.exception.PatientNotFoundException;
import com.nz.orion.patient.model.Patient;
import com.nz.orion.patient.repo.PatientRepository;
import com.nz.orion.patient.service.PatientService;
import com.nz.orion.patient.utils.PatientUtils;

@Service
public class PatientServiceImpl implements PatientService {

	Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	PatientRepository patientRepository;

	@Override
	public boolean addPatient(Patient patient) {
		return patientRepository.save(patient) != null;
	}

	@Override
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}

	@Override
	public List<Patient> getAllPatient() {
		return (List<Patient>) patientRepository.findAll();
	}

	@Override
	public List<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth) {
		return patientRepository.findByFirstNameAndLastNameAndDateOfBirth(firstName, lastName, dateOfBirth);
	}

	@Override
	public List<Patient> findByFirstNameStartsWith(String firstName) {
		return patientRepository.findByFirstNameStartsWith(firstName);
	}

	@Override
	public Patient findById(Long id) {
		return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
	}

	@Override
	public Map<Integer, Long> findBirthYearCount(int startRange, int endRange) {

		List<Patient> listPatient = patientRepository.findBirthYearCount(startRange, endRange);
		List<Integer> listDob = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		for (Patient p : listPatient) {
			calendar.setTime(p.getDateOfBirth());
			logger.info(p.getFirstName() + "--" + (calendar.get(Calendar.YEAR)));
			listDob.add((calendar.get(Calendar.YEAR)));
		}
		Map<Integer, Long> unSortedMap = PatientUtils.countByPatientBirthYear(listDob);

		LinkedHashMap<Integer, Long> desendingSortedMap = new LinkedHashMap<>();

		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> desendingSortedMap.put(x.getKey(), x.getValue()));

		return desendingSortedMap;

	}

}
