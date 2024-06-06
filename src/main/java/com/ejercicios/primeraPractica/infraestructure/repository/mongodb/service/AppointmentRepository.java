package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;

@Repository
@EnableMongoRepositories
public interface AppointmentRepository extends MongoRepository<AppointmentEntity, String> {

	List<AppointmentEntity> findByPatientDocument(String patientDocument);

	List<AppointmentEntity> findByNutritionistDocument(String nutritionistId);

	Page<AppointmentEntity> findByEliminado(boolean eliminado, Pageable pageable);

	Optional<AppointmentEntity> findByIdAndEliminado(String id, boolean eliminado);

	Page<AppointmentEntity> findByEliminadoAndType(boolean eliminado, PersonType type, Pageable pageable);
}
