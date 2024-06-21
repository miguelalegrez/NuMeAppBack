package com.ejercicios.primeraPractica.application.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;

import jakarta.validation.Valid;

@Component
public class DniValidator {
	@Autowired
	PersonRepositoryOutputPort persoRepository;

	// Validation id
	public void validatePersonExistsByDocument(@Valid String document) throws BusinessException {
		Optional<Person> existingPerson = persoRepository.findByPersoInfoDocument(document);
		if (existingPerson.isPresent()) {
			throw new BusinessException(Errors.USER_EXISTS);
		}
	}
}
