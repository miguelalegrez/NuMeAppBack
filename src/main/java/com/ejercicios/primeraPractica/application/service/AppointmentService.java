package com.ejercicios.primeraPractica.application.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.AppointmentRequest;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service.AppointmentRepository;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service.PersonRepository;

@Service
public class AppointmentService {

	@Autowired
	PersonRepository persoRepository;

	@Autowired
	AppointmentRepository appointmentRepository;

	// Get all appointments

	public Page<Appointment> getAppointments() {
		return appointmentRepository.findAll();
	}

	// Add appointment

	public Optional<String> addAppointment(AppointmentRequest appointmentRequest) {
		Optional<String> exitId = Optional.empty();

		// Buscar al paciente por su documento
		Optional<Person> personOpt = persoRepository.findByPersoInfoDocument(appointmentRequest.getPatientDocument());
		if (personOpt.isPresent()) {
			Person person = personOpt.get();

			// Validar que el nutricionista existe
			Optional<Person> nutritionistOpt = persoRepository
					.findByPersoInfoDocument(appointmentRequest.getNutritionistId());
			if (nutritionistOpt.isPresent() && nutritionistOpt.get().getType() == PersonType.NUTRITIONIST) {
				Person nutriPerson = nutritionistOpt.get();

				// Establecer los IDs del paciente y del nutricionista en la cita
				Appointment appointment = appointmentRequest.getAppointment();
				appointment.setPatientDocument(appointmentRequest.getPatientDocument());

				// Guardar la cita
				Appointment savedAppointment = appointmentRepository.save(appointment);
				exitId = Optional.ofNullable(savedAppointment.getId());

				// Añadir el ID de la cita a la lista de IDs de citas del paciente
				person.getAppointmentId().add(savedAppointment.getId());
				persoRepository.save(person);

				// Añadir el ID de la cita a la lista de IDs de citas del nutricionista
				nutriPerson.getAppointmentId().add(savedAppointment.getId());
				persoRepository.save(nutriPerson);
			} else {
				// Manejar el caso en el que el nutricionista no exista o no sea un
				// nutricionista
				throw new RuntimeException("Nutricionista no encontrado o no válido");
			}
		} else {
			// Manejar el caso en el que el paciente no exista
			throw new RuntimeException("Paciente no encontrado");
		}
		return exitId;
	}

	// get patient appointments

	public List<Appointment> getPatientAppointments(String persoId) {
		List<Appointment> exitApp = new ArrayList<>();
		Optional<Person> personOpt = persoRepository.findById(persoId);
		if (personOpt.isPresent()) {
			personOpt.get().getAppointmentId().forEach(appId -> {
				Optional<Appointment> appointmentOpt = appointmentRepository.findById(appId);
				appointmentOpt.ifPresent(exitApp::add);
			});
		}
		return exitApp;
	}

	// get nutritionist appointments

	public List<Appointment> getNutrinionistsAppointments(String nutritionistId) {
		Optional<Person> opt = persoRepository.findById(nutritionistId);
		if (opt.isPresent()) {
			return appointmentRepository.findByNutritionistId(nutritionistId);
		}
		return Arrays.asList();
	}
}
