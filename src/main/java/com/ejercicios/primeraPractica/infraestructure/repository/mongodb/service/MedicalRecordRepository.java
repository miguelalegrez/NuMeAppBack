package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.MedicalRecordEntity;

@Repository
@EnableMongoRepositories
public interface MedicalRecordRepository extends MongoRepository<MedicalRecordEntity, String> {

}
