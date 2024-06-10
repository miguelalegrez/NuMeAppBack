package com.ejercicios.primeraPractica.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;

public interface PersonRepositoryOutputPort {

	Page<Person> getPersonsByPersonType(@Valid PersonType personType, Pageable pageable);

	Optional<Person> getPersonById(@Valid String id);

	String createPerson(@Valid Person person);

	void modifyPerson(@Valid Person person);

	void deletePerson(@Valid String id);

	Optional<Person> findByPersoInfoDocument(String document);
}
