package com.ejercicios.primeraPractica.infraestructure.apirest.dto.response;

import java.time.LocalDateTime;

import com.ejercicios.primeraPractica.domain.model.RegistryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDto {
	private String id;
	private LocalDateTime date;
	private RegistryType registryType;
	private String observations;
	private String patientId;
}
