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

@Slf4j
@Component
public class PersonRepositoryService implements PersonRepositoryOutputPort {

	@Autowired
	PersonRepository persoRepository;

	@Autowired
	PersonToPersonEntityMapper personToPersonEntityMapper;

	public Page<Person> getAllPerson(@Valid Pageable pageable) {
		log.debug("getAllPerson");
		Page<PersonEntity> personsList = persoRepository.findByEliminado(false, pageable);
		return personToPersonEntityMapper.fromOutputToInput(personsList);
	}

	@Override
	@Cacheable(value = "persons", key = "#pageable")
	public Page<Person> getPersonsByPersonType(@Valid PersonType type, Pageable pageable) {
		log.debug("getPersonsByType");

		Page<PersonEntity> personTypeList = persoRepository.findByEliminadoAndPersonType(false, type, pageable);
		return personToPersonEntityMapper.fromOutputToInput(personTypeList);
	}

	@Override
	@Cacheable(value = "persons", key = "#id")
	public Optional<Person> getPersonById(@Valid String id) {
		log.debug("getPersonsById");

		Optional<PersonEntity> personEntity = persoRepository.findByIdAndEliminado(id, false);
		return personToPersonEntityMapper.fromOutputToInput(personEntity);
	}

	// get user by personal document to validate if exists in db.
	@Override
	public Optional<Person> findByPersoInfoDocument(@Valid String document) {
		log.debug("findByPersoInfoDocument");

		Optional<PersonEntity> personEntityOpt = persoRepository.findByPersoInfoDocument(document);
		return personToPersonEntityMapper.fromOutputToInput(personEntityOpt);
	}

	@Override
	@CacheEvict(value = "persons", allEntries = true)
	public String createPerson(@Valid Person person) {
		log.debug("createPerson");

		PersonEntity personEntity = personToPersonEntityMapper.fromInputToOutput(person);
		personEntity.setEliminado(false);

		PersonEntity savedEntity = persoRepository.save(personEntity);
		return savedEntity.getId();
	}

	@Override
	@CacheEvict(value = "persons", allEntries = true)
	public void modifyPerson(@Valid Person person) {
		log.debug("modifyPerson");

		PersonEntity personEntity = personToPersonEntityMapper.fromInputToOutput(person);
		persoRepository.save(personEntity);
	}

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
