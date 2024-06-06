package com.ejercicios.primeraPractica.domain.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("MEDICAL_RECORD")
public class MedicalRecord {
	private String id;
	private LocalDateTime date;
	private RegistryType registryType;
	private String observations;
	private String patientDocument;
	private String medicalIdentification; //

}
