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

/**
 * Service class for managing persons.
 */
@Slf4j
@Service
public class PersonService implements PersonServiceInputPort {

	@Autowired
	private PersonRepositoryOutputPort personRepository;

	@Autowired
	private PersonPatchMapper personPatchMapper;

	@Autowired
	private DniValidator dniValidator;

	/**
	 * Retrieves persons by their type with pagination.
	 *
	 * @param personType the type of person (e.g., PATIENT, NUTRITIONIST)
	 * @param pageable   the pagination information
	 * @return a page of persons
	 * @throws BusinessException if there is a business exception
	 */
	@Override
	@Transactional
	public Page<Person> getPersonsByType(@Valid PersonType personType, Pageable pageable) throws BusinessException {
		log.debug("getPersonByType");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}
		return personRepository.getPersonsByPersonType(personType, pageable);
	}

	/**
	 * Retrieves a person by their ID.
	 *
	 * @param id the person ID
	 * @return the person
	 */
	@Override
	@Transactional
	public Optional<Person> getPersonById(@Valid String id) {
		log.debug("getPersonById");

		return personRepository.getPersonById(id);
	}

	/**
	 * Retrieves persons by their name and surname with pagination.
	 *
	 * @param name     the person's name
	 * @param surname  the person's surname
	 * @param pageable the pagination information
	 * @return a page of persons
	 * @throws BusinessException if there is a business exception
	 */
	@Override
	@Transactional
	public Page<Person> getPersonByNameAndSurname(@Valid String name, String surname, Pageable pageable)
			throws BusinessException {
		log.debug("getPersonByNameAndSurname");
		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}
		return personRepository.findByNameAndSurname(name, surname, pageable);
	}

	/**
	 * Retrieves a person by their document.
	 *
	 * @param document the person's document
	 * @return the person
	 */
	@Override
	@Transactional
	public Optional<Person> findByPersoInfoDocument(@Valid String document) {
		log.debug("findPersoInfoDocument");

		return personRepository.findByPersoInfoDocument(document);
	}

	/**
	 * Creates a new patient.
	 *
	 * @param person the patient to create
	 * @return the ID of the created patient
	 * @throws BusinessException if there is a business exception
	 */
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
		List<String> appointments = new ArrayList<>();
		person.setAppointmentId(appointments);

		List<String> medicalRecords = new ArrayList<>();
		person.setMedicalRecordId(medicalRecords);

		// Create the person
		String nuevoId = personRepository.createPerson(person);
		person.setId(nuevoId);

		return nuevoId;
	}

	/**
	 * Creates a new nutritionist.
	 *
	 * @param person the nutritionist to create
	 * @return the ID of the created nutritionist
	 * @throws BusinessException if there is a business exception
	 */
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
		List<String> appointments = new ArrayList<>();
		person.setAppointmentId(appointments);

		List<String> medicalRecords = new ArrayList<>();
		person.setMedicalRecordId(medicalRecords);

		// Create the person
		String nuevoId = personRepository.createPerson(person);
		person.setId(nuevoId);

		return nuevoId;
	}

	/**
	 * Modifies an existing person.
	 *
	 * @param person the person to modify
	 * @throws BusinessException if there is a business exception
	 */
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

	/**
	 * Partially modifies an existing person.
	 *
	 * @param person the person to modify
	 * @throws BusinessException if there is a business exception
	 */
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

	/**
	 * Deletes a person by their ID.
	 *
	 * @param id the person ID
	 * @throws BusinessException if there is a business exception
	 */
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
