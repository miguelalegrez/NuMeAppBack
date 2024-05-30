package com.ejercicios.primeraPractica.application.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.database.repository.AppointmentRepository;
import com.ejercicios.primeraPractica.database.repository.MedicalRecordRepository;
import com.ejercicios.primeraPractica.database.repository.PersonRepository;
import com.ejercicios.primeraPractica.negocio.model.Person;
import com.ejercicios.primeraPractica.negocio.model.PersonType;
import com.ejercicios.primeraPractica.negocio.model.PersonalInfo;

@Service
public class NutritionistService {

	@Autowired
	PersonRepository persoRepository;

	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	public Optional<String> addNutritionist(PersonalInfo persoInfo) {
		Person savedPerson = persoRepository.save(Person.builder().type(PersonType.NUTRITIONIST).persoInfo(persoInfo)
				.appointmentId(Arrays.asList()).medicalRecordId(Arrays.asList()).build());
		return Optional.ofNullable(savedPerson.getId());
	}

	// get nutritionist List
	public List<Person> getNutritrionists() {
		return persoRepository.findByType(PersonType.NUTRITIONIST);
	}

}
