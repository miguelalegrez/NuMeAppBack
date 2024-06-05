package com.ejercicios.primeraPractica.application.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.domain.model.Person;

import jakarta.validation.Valid;

public class DniValidator {
	@Autowired
	PersonRepositoryOutputPort persoRepository;

	// Validation id
	public void validatePersonExistsByDocument(@Valid String document) {
		Optional<Person> existingPerson = persoRepository.findByPersoInfoDocument(document);
		if (existingPerson.isPresent()) {
			throw new RuntimeException("A user with this document already exists.");
		}
	}
}
