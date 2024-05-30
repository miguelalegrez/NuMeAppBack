package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.negocio.model.Person;
import com.ejercicios.primeraPractica.negocio.model.PersonType;

@Repository
@EnableMongoRepositories

public interface PersonRepository extends MongoRepository<Person, String> {

	List<Person> findByType(PersonType type);

	Optional<Person> findByPersoInfoDocument(String document);

}
