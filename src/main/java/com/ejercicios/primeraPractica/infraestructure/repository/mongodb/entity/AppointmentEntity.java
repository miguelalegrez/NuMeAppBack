package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Document("APPOINTMENT")
public class AppointmentEntity {

	@Id
	private String id;
	private LocalDateTime date;
	private String patientId;
	private String patientName;
	private String patientSurname;
	private String patientDocument;
	private String nutritionistName;
	private String nutritionistSurname;
	private String nutritionistDocument;
	private String nutritionistId;
	private boolean eliminado;

}
