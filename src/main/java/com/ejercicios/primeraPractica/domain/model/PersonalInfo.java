package com.ejercicios.primeraPractica.domain.model;

import lombok.Data;

@Data
public class PersonalInfo {
	private String name;
	private String surname;
	private DocumentType documentType;
	private String document;
}
