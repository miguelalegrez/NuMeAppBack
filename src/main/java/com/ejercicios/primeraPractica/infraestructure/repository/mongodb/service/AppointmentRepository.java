package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;

@Repository
@EnableMongoRepositories
public interface AppointmentRepository extends MongoRepository<AppointmentEntity, String> {

	Optional<AppointmentEntity> findById(String id);

	Optional<AppointmentEntity> findByPatientDocumentAndEliminado(String document, boolean eliminado);

	// Obtiene un listado de citas obtenidas del usuario
	Page<AppointmentEntity> findByEliminadoAndIdIn(boolean eliminado, List<String> ids, Pageable pageable);

	Optional<AppointmentEntity> findByIdAndEliminado(String id, boolean eliminado);

	Page<AppointmentEntity> findAllByEliminado(boolean eliminado, Pageable pageable);

}
