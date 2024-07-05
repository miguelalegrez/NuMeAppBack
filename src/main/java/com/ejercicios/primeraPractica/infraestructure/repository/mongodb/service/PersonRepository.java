package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;

@Repository
@EnableMongoRepositories
public interface PersonRepository extends MongoRepository<PersonEntity, String> {

	Optional<PersonEntity> findByPersoInfoDocumentIgnoreCaseAndEliminado(String document, boolean eliminado);

	Page<PersonEntity> findByEliminado(boolean eliminado, Pageable pageable);

	Optional<PersonEntity> findByIdAndEliminado(String id, boolean eliminado);

	Page<PersonEntity> findByPersonTypeAndEliminado(PersonType personType, boolean eliminado, Pageable pageable);

	@Query("{'personType': ?0, 'persoInfo.name': {$regex: ?1, $options: 'i'}, 'persoInfo.surname': {$regex: ?2, $options: 'i'}, 'eliminado': ?3}")
	Page<PersonEntity> findByPersonTypeAndPersoInfoNameAndPersoInfoSurnameAndEliminado(PersonType personType,
			String name, String surname, boolean eliminado, Pageable pageable);

}
