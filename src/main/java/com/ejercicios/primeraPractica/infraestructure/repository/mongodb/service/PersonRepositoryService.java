package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper.PersonToPersonEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing persons in a MongoDB repository.
 */
@Slf4j
@Component
public class PersonRepositoryService implements PersonRepositoryOutputPort {

	@Autowired
	PersonRepository persoRepository;

	@Autowired
	PersonToPersonEntityMapper personToPersonEntityMapper;

	/**
	 * Retrieves persons by their type with pagination.
	 *
	 * @param personType the type of person (e.g., PATIENT, NUTRITIONIST)
	 * @param pageable   the pagination information
	 * @return a page of persons
	 */
	@Override
	@Cacheable(value = "persons", key = "#pageable")
	public Page<Person> getPersonsByPersonType(@Valid PersonType personType, Pageable pageable) {
		log.debug("getPersonsByPersonType - type: {}, pageable: {}", personType, pageable);

		Page<PersonEntity> personEntities = persoRepository.findByPersonTypeAndEliminado(personType, false, pageable);
		log.debug("Found personEntities: {}", personEntities.getContent());

		Page<Person> persons = personToPersonEntityMapper.fromOutputToInput(personEntities);
		log.debug("Mapped persons: {}", persons.getContent());

		return persons;
	}

	/**
	 * Retrieves a person by their ID.
	 *
	 * @param id the person ID
	 * @return the person
	 */
	@Override
	@Cacheable(value = "persons", key = "#id")
	public Optional<Person> getPersonById(@Valid String id) {
		log.debug("getPersonsById");

		Optional<PersonEntity> personEntity = persoRepository.findByIdAndEliminado(id, false);
		return personToPersonEntityMapper.fromOutputToInput(personEntity);
	}

	/**
	 * Retrieves persons by their name and surname with pagination.
	 *
	 * @param name     the person's name
	 * @param surname  the person's surname
	 * @param pageable the pagination information
	 * @return a page of persons
	 */
	@Override
	public Page<Person> findByNameAndSurnameAndPersonType(@Valid String name, String surname, PersonType personType,
			Pageable pageable) {
		log.debug("getPersonsByNameAndSurname");

		Page<PersonEntity> personEntity = persoRepository
				.findByPersonTypeAndPersoInfoNameAndPersoInfoSurnameAndEliminado(personType, name, surname, false,
						pageable);
		return personToPersonEntityMapper.fromOutputToInput(personEntity);
	}

	/**
	 * Retrieves a person by their document.
	 *
	 * @param document the person's document
	 * @return the person
	 */
	@Override
	public Optional<Person> findByPersoInfoDocument(@Valid String document) {
		log.debug("findByPersoInfoDocument");

		Optional<PersonEntity> personEntityOpt = persoRepository.findByPersoInfoDocumentIgnoreCaseAndEliminado(document,
				false);
		return personToPersonEntityMapper.fromOutputToInput(personEntityOpt);
	}

	/**
	 * Creates a new person.
	 *
	 * @param person the person to create
	 * @return the ID of the created person
	 */
	@Override
	@CacheEvict(value = "persons", allEntries = true)
	public String createPerson(@Valid Person person) {
		log.debug("createPerson");

		PersonEntity personEntity = personToPersonEntityMapper.fromInputToOutput(person);
		personEntity.setEliminado(false);

		PersonEntity savedEntity = persoRepository.save(personEntity);
		return savedEntity.getId();
	}

	/**
	 * Modifies an existing person.
	 *
	 * @param person the person to modify
	 */
	@Override
	@CacheEvict(value = "persons", allEntries = true)
	public void modifyPerson(@Valid Person person) {
		log.debug("modifyPerson");

		PersonEntity personEntity = personToPersonEntityMapper.fromInputToOutput(person);
		persoRepository.save(personEntity);
	}

	/**
	 * Deletes a person by their ID.
	 *
	 * @param id the person ID
	 */
	@Override
	@CacheEvict(value = "persons", allEntries = true)
	public void deletePerson(@Valid String id) {
		log.debug("deletePerson");

		Optional<PersonEntity> personEntityOpt = persoRepository.findByIdAndEliminado(id, false);
		if (personEntityOpt.isPresent()) {
			PersonEntity personEntity = personEntityOpt.get();
			personEntity.setEliminado(true);
			persoRepository.save(personEntity);
		}
	}

}
