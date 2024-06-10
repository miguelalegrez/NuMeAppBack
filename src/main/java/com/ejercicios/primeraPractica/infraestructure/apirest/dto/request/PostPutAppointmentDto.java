package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPutAppointmentDto {
	@NotNull
	private LocalDateTime date;
	@NotNull
	private String patientId;
	@NotNull
	private String nutritionistId;
}
