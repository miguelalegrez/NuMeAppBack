package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document("APPOINTMENT")
public class AppointmentEntity {

	@Id
	private String id;
	private LocalDateTime date;
	private String patientDocument;
	private String nutritionistId;

}
