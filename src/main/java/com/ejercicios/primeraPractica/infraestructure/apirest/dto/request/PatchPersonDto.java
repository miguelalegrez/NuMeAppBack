package com.ejercicios.primeraPractica.infraestructure.apirest.dto.request;

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
public class PatchPersonDto {

	private PersonalInfo persoInfo;

}
