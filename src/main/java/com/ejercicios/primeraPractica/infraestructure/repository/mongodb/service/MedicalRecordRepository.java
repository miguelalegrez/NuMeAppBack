package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.MedicalRecordEntity;

@Repository
@EnableMongoRepositories
public interface MedicalRecordRepository extends MongoRepository<MedicalRecordEntity, String> {
	// Obtiene un listado de citas obtenidas del usuario
	Page<MedicalRecordEntity> findByIdInAndEliminado(List<String> ids, boolean eliminado, Pageable pageable);

	Optional<MedicalRecordEntity> findByIdAndEliminado(String id, boolean eliminado);

}
