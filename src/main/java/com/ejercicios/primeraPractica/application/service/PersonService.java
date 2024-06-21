package com.ejercicios.primeraPractica.application.service;

import java.util.ArrayList;
import java.util.List;
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
import com.ejercicios.primeraPractica.domain.mapper.PersonPatchMapper;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonService implements PersonServiceInputPort {

	@Autowired
	private PersonRepositoryOutputPort personRepository;

	@Autowired
	PersonPatchMapper personPatchMapper;

	@Autowired
	private DniValidator dniValidator;

	@Override
	@Transactional
	public Page<Person> getPersonsByType(@Valid PersonType personType, Pageable pageable) throws BusinessException {
		log.debug("getPersonByType");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}
		return personRepository.getPersonsByPersonType(personType, pageable);
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
	@Transactional
	public String createPatient(@Valid Person person) throws BusinessException {
		log.debug("createPatient");

		if (person.getPersonType() != PersonType.PATIENT) {
			throw new BusinessException(Errors.INVALID_PERSON_TYPE);
		}

		// Validate if a person with the same document already exists
		dniValidator.validatePersonExistsByDocument(person.getPersoInfo().getDocument());

		// Initialize appointments and Medical Record
		List<String> appointments = new ArrayList();
		person.setAppointmentId(appointments);

		List<String> medicalRecords = new ArrayList();
		person.setMedicalRecordId(medicalRecords);

		// Create the person
		String nuevoId = personRepository.createPerson(person);
		person.setId(nuevoId);

		return nuevoId;
	}

	@Override
	@Transactional
	public String createNutritionist(@Valid Person person) throws BusinessException {
		log.debug("createNutritionist");

		if (person.getPersonType() != PersonType.NUTRITIONIST) {
			throw new BusinessException(Errors.INVALID_PERSON_TYPE);
		}

		// Validate if a person with the same document already exists
		dniValidator.validatePersonExistsByDocument(person.getPersoInfo().getDocument());

		// Initialize appointments and Medical Record
		List<String> appointments = new ArrayList();
		person.setAppointmentId(appointments);

		List<String> medicalRecords = new ArrayList();
		person.setMedicalRecordId(medicalRecords);

		// Create the person
		String nuevoId = personRepository.createPerson(person);
		person.setId(nuevoId);

		return nuevoId;
	}

	@Override
	@Transactional
	public void modifyPerson(@Valid Person person) throws BusinessException {
		log.debug("modifyPerson");

		Optional<Person> foundPerson = personRepository.getPersonById(person.getId());
		if (!foundPerson.isPresent()) {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		personRepository.modifyPerson(person);
	}

	@Override
	@Transactional
	public void modifyPartialPerson(@Valid Person person) throws BusinessException {
		log.debug("modifyPartialPerson");

		Optional<Person> foundPerson = personRepository.getPersonById(person.getId());
		if (!foundPerson.isPresent()) {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		Person updated = foundPerson.get();
		personPatchMapper.update(updated, person);
		personRepository.modifyPerson(updated);
	}

	@Override
	@Transactional
	public void deletePerson(@Valid String id) throws BusinessException {
		log.debug("deletePerson");

		Optional<Person> foundPerson = personRepository.getPersonById(id);
		if (!foundPerson.isPresent()) {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		personRepository.deletePerson(id);
	}

}
