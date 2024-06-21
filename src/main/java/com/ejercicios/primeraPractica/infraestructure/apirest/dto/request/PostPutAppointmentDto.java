package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
public class PostPutAppointmentDto {
	@NotNull
	private LocalDateTime date;
	@NotNull
	private String patientId;
	private String patientName;
	private String patientSurname;
	private String patientDocument;
	@NotNull
	private String nutritionistId;
	private String nutritionistName;
	private String nutritionisSurname;
	private String nutritionistDocument;

}
