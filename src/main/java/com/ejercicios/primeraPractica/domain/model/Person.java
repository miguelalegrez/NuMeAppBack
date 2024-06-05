package com.ejercicios.primeraPractica.domain.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Person {
	@Id
	private String id;
	private PersonType type;
	private PersonalInfo persoInfo;
	private List<String> appointmentId;
	private List<String> medicalRecordId;
	// Cargamos el ID y no la lista entera para que no pese tanto

}
