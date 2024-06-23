package com.ejercicios.primeraPractica.infraestructure.apirest.dto.response;

import java.util.List;

import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;

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
public class PersonDto {
	// Para poder usar el id en el front debo de incluir el id en la response
	private String id;
	private PersonType personType;
	private PersonalInfo persoInfo;
	private List<String> appointmentId;
	private List<String> medicalRecordId;

}
