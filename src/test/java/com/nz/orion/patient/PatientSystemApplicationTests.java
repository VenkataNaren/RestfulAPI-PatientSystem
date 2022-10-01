package com.nz.orion.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.nz.orion.patient.model.Patient;
import com.nz.orion.patient.service.PatientService;
import com.nz.orion.patient.utils.PatientUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ComponentScan("com.nz.orion.patient")
class PatientSystemApplicationTests {

	Logger logger = LoggerFactory.getLogger(PatientSystemApplicationTests.class);

	@Autowired
	private PatientService patientService;

	@Test
	void contextLoads() {
	}

	@Test
	public void should_find_patient_if_repo_is_not_empty() {
		logger.info("test case - should_find_patient_if_repo_is_not_empty started");
		Iterable<Patient> itrPatient = patientService.getAllPatient();

		assertThat(itrPatient).isNotEmpty();
	}

	@Test
	public void should_add_a_patient() {
		logger.info("test case - should_add_a_patient started");
		boolean storePatient = patientService
				.addPatient(new Patient("hello", "world", PatientUtils.convertStringToDate("1985-12-09")));
		assertTrue(storePatient);
	}

	@Test
	public void should_save_a_patient() {
		logger.info("test case - should_save_a_patient started");
		Patient patient = patientService
				.savePatient(new Patient("firstName", "lastName", PatientUtils.convertStringToDate("1985-12-09")));

		assertThat(patient).hasFieldOrPropertyWithValue("firstName", "firstName");
		assertThat(patient).hasFieldOrPropertyWithValue("lastName", "lastName");
		assertThat(patient).hasFieldOrPropertyWithValue("dateOfBirth", PatientUtils.convertStringToDate("1985-12-09"));
	}

	@Test
	public void should_find_patient_by_id() {
		logger.info("test case - should_find_patient_by_id started");
		Patient patient1 = new Patient("firstName", "lastName", PatientUtils.convertStringToDate("1985-12-09"));
		patientService.savePatient(patient1);

		Patient patient2 = new Patient("hello", "world", PatientUtils.convertStringToDate("1985-12-09"));
		patientService.savePatient(patient2);

		Patient foundPatient = patientService.findById(patient2.getId());

		assertThat(foundPatient.getFirstName()).isEqualTo(patient2.getFirstName());
		assertThat(foundPatient.getFirstName()).isNotEqualTo(patient1.getFirstName());
	}

	@Test
	public void should_find_with_patients_start_with_String() {
		logger.info("test case - should_find_with_patients_start_with_String started");

		patientService
				.savePatient(new Patient("startString1", "lastName", PatientUtils.convertStringToDate("1985-12-09")));

		patientService
				.savePatient(new Patient("startString2", "lastName", PatientUtils.convertStringToDate("1985-12-09")));

		patientService
				.savePatient(new Patient("startString3", "lastName", PatientUtils.convertStringToDate("1985-12-09")));

		List<Patient> patientWithStartString = patientService.findByFirstNameStartsWith("start");

		assertThat(patientWithStartString).hasSize(3);

	}

	@Test
	public void should_find_patient_count_by_year_range_desc() {

		logger.info("test case - should_find_patient_count_by_year_range_desc started");
		Map<Integer, Long> map = patientService.findBirthYearCount(1900, 2022);

		for (Map.Entry<Integer, Long> entry : map.entrySet()) {
			logger.info(entry.getKey() + "--" + entry.getValue());
		}

		assertEquals(map.get(1983), new Long(3));

	}

}
