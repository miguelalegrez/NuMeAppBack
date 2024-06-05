package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

import java.util.List;

import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;

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
public class PostPutPersonDto {

	private PersonType type;
	private PersonalInfo persoInfo;
	private List<String> appointmentId;
	private List<String> medicalRecordId;
}
