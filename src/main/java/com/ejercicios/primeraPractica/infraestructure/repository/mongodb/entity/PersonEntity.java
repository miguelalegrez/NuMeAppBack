package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ejercicios.primeraPractica.domain.model.PersonalInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
@Document("PERSONS")
public class PersonEntity {

	@Id
	private String id;
	private PersonTypeEntity personType;
	private PersonalInfo persoInfo;
	private List<String> appointmentId;
	private List<String> medicalRecordId;
	boolean eliminado;

}
