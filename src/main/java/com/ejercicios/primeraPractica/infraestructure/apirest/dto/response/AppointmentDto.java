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
	private String id;
	@NotNull
	private LocalDateTime date;
	@NotNull
	private String patientId;
	private String patientName;
	private String patientSurname;
	@NotNull
	private String patientDocument;
	@NotNull
	private String nutritionistId;
	private String nutritionistName;
	private String nutritionistSurname;
	@NotNull
	private String nutritionistDocument;

}
