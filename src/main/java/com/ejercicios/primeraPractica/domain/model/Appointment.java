package com.ejercicios.primeraPractica.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("APPOINTMENT")

public class Appointment {
	private String id;
	private LocalDateTime date;
	private String patientDocument;
	private String nutritionistId;
}
