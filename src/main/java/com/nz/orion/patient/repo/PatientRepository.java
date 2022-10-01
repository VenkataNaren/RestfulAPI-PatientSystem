package com.nz.orion.patient.repo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.nz.orion.patient.model.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	List<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName,String lastName, Date dateOfBirth);
	
	@Query("FROM Patient patient WHERE LOWER(patient.firstName) LIKE lower(concat(:firstName,'%'))")
	List<Patient> findByFirstNameStartsWith(@Param("firstName") String firstName);
	
//	@Query("SELECT EXTRACT(YEAR FROM P.DATE_OF_BIRTH) AS BIRTH_YEAR ,COUNT(*) AS COUNT FROM PATIENT P WHERE EXTRACT(YEAR FROM P.DATE_OF_BIRTH) BETWEEN :startRange AND :endRange GROUP BY BIRTH_YEAR HAVING COUNT(BIRTH_YEAR) >= 1")
//	List<BirthYearCount> findBirthYearCount(@Param("startRange") int startRange, @Param("endRange") int endRange);
	
	@Query("FROM Patient P WHERE EXTRACT(YEAR FROM P.dateOfBirth) BETWEEN :startRange AND :endRange")
	List<Patient> findBirthYearCount(@Param("startRange") int startRange, @Param("endRange") int endRange);
}
