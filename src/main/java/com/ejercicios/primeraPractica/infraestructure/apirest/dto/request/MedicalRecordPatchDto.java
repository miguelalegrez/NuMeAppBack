package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

import java.time.LocalDateTime;

import com.ejercicios.primeraPractica.domain.model.RegistryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class MedicalRecordPatchDto {
	private LocalDateTime date;
	private String patientId;
	private RegistryType registryType;
	private String observations;

}
