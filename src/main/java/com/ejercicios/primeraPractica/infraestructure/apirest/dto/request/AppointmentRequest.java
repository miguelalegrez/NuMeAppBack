package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

import com.ejercicios.primeraPractica.domain.model.Appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
	@NotNull
	@NotBlank
	private String patientDocument;
	@NotNull
	@NotBlank
	private String nutritionistId;
	@NotNull
	private Appointment appointment;

}