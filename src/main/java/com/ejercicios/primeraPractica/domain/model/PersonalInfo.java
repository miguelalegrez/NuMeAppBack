package com.ejercicios.primeraPractica.domain.model;

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
public class PersonalInfo {
	private String name;
	private String surname;
	private DocumentType documentType;
	private String document;
}
