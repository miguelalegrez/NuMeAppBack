package com.ejercicios.primeraPractica.infraestructure.apirest.dto.response;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
public class AppointmentDto {
	@NotNull
	private LocalDateTime date;
	@NotNull
	private String patientId;
	@NotNull
	private String nutritionistId;
}
