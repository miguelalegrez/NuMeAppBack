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
	private String nutritionistId;
}
