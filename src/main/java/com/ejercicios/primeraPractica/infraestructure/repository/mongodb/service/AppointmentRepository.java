package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;

@Repository
@EnableMongoRepositories
public interface AppointmentRepository extends MongoRepository<AppointmentEntity, String> {

	List<Appointment> findByPatientDocument(String patientDocument);

	List<Appointment> findByNutritionistId(String nutritionistId);

}
