package com.ejercicios.primeraPractica.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
	private String id;
	private LocalDateTime date;
	private RegistryType registryType;
	private String observations;
	private String patientId;

}
