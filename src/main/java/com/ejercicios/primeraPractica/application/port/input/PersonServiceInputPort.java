package com.ejercicios.primeraPractica.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;

public interface PersonServiceInputPort {

	Page<Person> getAllPerson(@Valid Pageable pageable) throws BusinessException;

	Page<Person> getPersonsByType(@Valid PersonType type, Pageable pageable) throws BusinessException;

	Optional<Person> getPersonById(@Valid String id) throws BusinessException;

	String createPatient(@Valid Person person) throws BusinessException;

	String createNutritionist(@Valid Person person) throws BusinessException;

	void modifyPerson(@Valid Person person) throws BusinessException;

	void deletePerson(@Valid String id) throws BusinessException;

	Optional<Person> findByPersoInfoDocument(String document) throws BusinessException;
}
