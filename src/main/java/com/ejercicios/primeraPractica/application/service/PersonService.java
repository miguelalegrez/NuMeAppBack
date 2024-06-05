package com.ejercicios.primeraPractica.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.application.port.input.PersonServiceInputPort;
import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.util.Constants;
import com.ejercicios.primeraPractica.application.util.DniValidator;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonService implements PersonServiceInputPort {

	@Autowired
	private PersonRepositoryOutputPort personRepository;

	@Autowired
	private DniValidator dniValidator;

	public Page<Person> getAllPerson(@Valid Pageable pageable) throws BusinessException {
		log.debug("getAllPerson");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}

		return personRepository.getAllPerson(pageable);
	}

	@Override
	public Page<Person> getPersonsByType(@Valid PersonType type, Pageable pageable) throws BusinessException {
		log.debug("getPersonByType");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}
		return personRepository.getPersonsByType(type, pageable);
	}

	@Override
	public Optional<Person> getPersonById(@Valid String id) {
		log.debug("getPersonById");

		return personRepository.getPersonById(id);
	}

	@Override
	public Optional<Person> findByPersoInfoDocument(String document) {
		log.debug("findPersoInfoDocument");

		return personRepository.findByPersoInfoDocument(document);

	}

	@Override
	public String createPatient(@Valid Person person) {
		log.debug("createPatient");

		if (person.getType() != PersonType.PATIENT) {
			throw new IllegalArgumentException("Invalid person type for creating a patient");
		}

		// Validate if a person with the same document already exists
		dniValidator.validatePersonExistsByDocument(person.getPersoInfo().getDocument());

		// Create the person
		String nuevoId = personRepository.createPerson(person);

		// Assign the new ID to the person object
		person.setId(nuevoId);

		return nuevoId;
	}

	@Override
	public String createNutritionist(@Valid Person person) {
		log.debug("createNutritionist");

		if (person.getType() != PersonType.NUTRITIONIST) {
			throw new IllegalArgumentException("Invalid person type for creating a nutritionist");
		}

		// Validate if a person with the same document already exists
		dniValidator.validatePersonExistsByDocument(person.getPersoInfo().getDocument());

		// Create the person
		String nuevoId = personRepository.createPerson(person);

		// Assign the new ID to the person object
		person.setId(nuevoId);

		return nuevoId;
	}

	@Override
	public void modifyPerson(@Valid Person person) throws BusinessException {
		log.debug("modifyPerson");

		Optional<Person> foundPerson = personRepository.getPersonById(person.getId());
		if (!foundPerson.isPresent()) {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		personRepository.modifyPerson(person);
	}

	@Override
	public void deletePerson(@Valid String id) throws BusinessException {
		log.debug("deletePerson");

		Optional<Person> foundPerson = personRepository.getPersonById(id);
		if (!foundPerson.isPresent()) {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		personRepository.deletePerson(id);
	}

}
