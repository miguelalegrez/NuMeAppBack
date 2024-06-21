package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ejercicios.primeraPractica.domain.model.RegistryType;

import jakarta.persistence.Id;
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
@Document("MEDICAL_RECORD")
public class MedicalRecordEntity {
	@Id
	private String id;
	private LocalDateTime date;
	private RegistryType registryType;
	private String observations;
	private String patientId;
	private String nutritionistId;
	private boolean eliminado;

}
