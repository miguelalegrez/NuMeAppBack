package com.ejercicios.primeraPractica.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.database.repository.PersonRepository;
import com.ejercicios.primeraPractica.negocio.model.Person;
import com.ejercicios.primeraPractica.negocio.model.PersonType;
import com.ejercicios.primeraPractica.negocio.model.PersonalInfo;

@Service
public class PatientService {
	@Autowired
	PersonRepository persoRepository;

	// get patient List
	public List<Person> getPatients() {
		return persoRepository.findByType(PersonType.PATIENT);
	}

	// Validation id
	public void validatePersonExistsByDocument(String document) {
		Optional<Person> existingPerson = persoRepository.findByPersoInfoDocument(document);
		if (existingPerson.isPresent()) {
			throw new RuntimeException("A user with this document already exists.");
		}
	}

	// Add patient, returns and Id
	public Optional<String> addPatient(PersonalInfo persoInfo) {
		// Validamos previamente si existe la persona
		validatePersonExistsByDocument(persoInfo.getDocument());

		// Crear un nuevo paciente
		Person person = new Person();
		person.setType(PersonType.PATIENT);
		person.setPersoInfo(persoInfo);
		person.setAppointmentId(new ArrayList<>());
		person.setMedicalRecordId(new ArrayList<>());

		// Guardar el paciente en el repositorio
		Person savedPerson = persoRepository.save(person);

		// Devolver el ID del paciente creado
		return Optional.ofNullable(savedPerson.getId());
	}

}
