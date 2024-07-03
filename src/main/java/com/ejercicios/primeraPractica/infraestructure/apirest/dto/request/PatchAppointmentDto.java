package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

import java.time.LocalDateTime;

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
public class PatchAppointmentDto {

	private LocalDateTime date;
	private String nutritionistId;

}
