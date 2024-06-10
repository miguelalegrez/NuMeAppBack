package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.application.service.PersonService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutPersonDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPersonDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPostPutPersonMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/patients")
public class PatientController {

	@Autowired
	PersonService personService;

	@Autowired
	PersonToPersonDtoMapper personToPersonDto;

	@Autowired
	PersonToPostPutPersonMapper personToPostPutDtoMapper;

	@GetMapping
	public ResponseEntity getPatients(Pageable pageable) {
		log.debug("getPatients - pageable: {}", pageable);
		Page<Person> patients;
		try {
			patients = personService.getPersonsByType(PersonType.PATIENT, pageable);
			log.debug("Retrieved patients: {}", patients.getContent());
			return ResponseEntity.ok(personToPersonDto.fromInputToOutput(patients));
		} catch (BusinessException e) {
			log.error("Error getting users", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity getPatientById(@PathVariable("id") String id) {
		Optional<Person> persoOpt = personService.getPersonById(id);
		if (persoOpt.isPresent()) {
			return ResponseEntity.ok(persoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

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

	@PutMapping("/{id}")
	public ResponseEntity modifyPerson(@PathVariable("id") String id, @RequestBody PostPutPersonDto personDto) {

		Person domain = personToPostPutDtoMapper.fromOutputToInput(personDto);
		domain.setId(id);

		try {
			personService.modifyPerson(domain);
		} catch (BusinessException e) {
			log.error("Error modifyng user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(domain);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@Valid @PathVariable("user-id") String id) {
		log.debug("deleteUser");

		try {
			personService.deletePerson(id);
		} catch (BusinessException e) {
			log.error("Error deleting user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();

	}

	private URI createUri(String id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
