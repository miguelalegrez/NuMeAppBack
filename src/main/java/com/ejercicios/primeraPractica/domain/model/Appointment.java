package com.ejercicios.primeraPractica.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
	private String id;
	private LocalDateTime date;
	private String patientId;
	private String patientName;
	private String patientSurname;
	private String patientDocument;
	private String nutritionistId;
	private String nutritionistName;
	private String nutritionistSurname;
	private String nutritionistDocument;
}
