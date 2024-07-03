package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.application.service.PersonService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PatchPersonDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutPersonDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPatchPersonMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPersonDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPostPutPersonMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest Controller for managing patients.
 */
@CrossOrigin(origins = "http://localhost:4200")
@SuppressWarnings("rawtypes")
@Slf4j
@RestController
@RequestMapping("/patients")
public class PatientController {

	@Autowired
	PersonService personService;

	@Autowired
	PersonToPersonDtoMapper personToPersonDto;

	@Autowired
	PersonToPatchPersonMapper personToPatchDtoMapper;

	@Autowired
	PersonToPostPutPersonMapper personToPostPutDtoMapper;

	/**
	 * Retrieves a page of patients.
	 *
	 * @param pageable the pagination information
	 * @return a response entity containing the list of patients
	 */
	@GetMapping
	public ResponseEntity getPatients(Pageable pageable) {
		log.debug("getPatients - pageable: {}", pageable);

		try {
			Page<Person> patients;
			patients = personService.getPersonsByType(PersonType.PATIENT, pageable);
			log.debug("Retrieved patients: {}", patients.getContent());
			return ResponseEntity.ok(personToPersonDto.fromInputToOutput(patients));
		} catch (BusinessException e) {
			log.error("Error getting users", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Retrieves a patient by its ID.
	 *
	 * @param id the patient ID
	 * @return a response entity containing the patient
	 */
	@GetMapping("/{patientId}")
	public ResponseEntity getPatientById(@PathVariable("patientId") String id) {
		log.debug("getPatientById - {}", id);

		Optional<Person> persoOpt = personService.getPersonById(id);
		if (persoOpt.isPresent()) {
			return ResponseEntity.ok(persoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Retrieves patients by their name and surname.
	 *
	 * @param name     the patient's name
	 * @param surname  the patient's surname
	 * @param pageable the pagination information
	 * @return a response entity containing the list of patients
	 */
	@GetMapping("/search")
	public ResponseEntity getPatientByNameAndSurname(@RequestParam String name, @RequestParam String surname,
			Pageable pageable) {
		log.debug("getPatientByNameAndSurname: {} {}", name, surname);
		try {
			Page<Person> patients;
			patients = personService.getPersonByNameAndSurname(name, surname, pageable);
			log.debug("Retrieved patients: {}", patients.getContent());
			return ResponseEntity.ok(personToPersonDto.fromInputToOutput(patients));
		} catch (BusinessException e) {
			log.error("Error getting users", e);
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	/**
	 * Retrieves a patient by their document.
	 *
	 * @param document the patient's document
	 * @return a response entity containing the patient
	 */
	@GetMapping("/searchByPatientDocument")
	public ResponseEntity getPatientByDocument(@RequestParam String document) {
		log.debug("searchPatientByDocument");
		Optional<Person> personOpt = personService.findByPersoInfoDocument(document);
		if (personOpt.isPresent()) {
			return ResponseEntity.ok(personToPersonDto.fromInputToOutput(personOpt));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Adds a new patient.
	 *
	 * @param personDto the patient DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PostMapping
	public ResponseEntity addPatient(@Valid @RequestBody PostPutPersonDto personDto) {
		try {
			Person patient = personToPostPutDtoMapper.fromOutputToInput(personDto);
			String idNewPatient = personService.createPatient(patient);
			URI locationHeader = createUri(idNewPatient);
			return ResponseEntity.created(locationHeader).build();
		} catch (BusinessException e) {
			log.error("Error creating patient", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Modifies an existing patient.
	 *
	 * @param id        the patient ID
	 * @param personDto the patient DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PutMapping("/{patientId}")
	public ResponseEntity modifyPerson(@PathVariable("patientId") String id, @RequestBody PostPutPersonDto personDto) {
		log.debug("modifyPerson: {}", id);

		Person domain = personToPostPutDtoMapper.fromOutputToInput(personDto);
		domain.setId(id);

		try {
			personService.modifyPerson(domain);
		} catch (BusinessException e) {
			log.error("Error modifying user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(domain);
	}

	/**
	 * Partially modifies an existing patient.
	 *
	 * @param id        the patient ID
	 * @param personDto the patient DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PatchMapping("/{patientId}")
	public ResponseEntity modifyPartialPerson(@PathVariable("patientId") String id,
			@RequestBody PatchPersonDto personDto) {
		log.debug("modifyPartialPerson: {}", id);
		Person domain = personToPatchDtoMapper.fromOutputToInput(personDto);
		domain.setId(id);
		try {
			personService.modifyPartialPerson(domain);
		} catch (BusinessException e) {
			log.error("Error modifying patient", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Deletes a patient by its ID.
	 *
	 * @param id the patient ID
	 * @return a response entity indicating the result of the operation
	 */
	@DeleteMapping("/{patientId}")
	public ResponseEntity deleteUser(@Valid @PathVariable("patientId") String id) {
		log.debug("deleteUser");

		try {
			personService.deletePerson(id);
		} catch (BusinessException e) {
			log.error("Error deleting user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();

	}

	/**
	 * Creates a URI for the newly created patient.
	 *
	 * @param id the patient ID
	 * @return the URI of the newly created patient
	 */
	private URI createUri(String id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
