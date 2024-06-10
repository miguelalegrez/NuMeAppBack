package com.ejercicios.primeraPractica.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.application.port.output.AppointmentRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.util.Constants;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppointmentService {

	@Autowired
	PersonRepositoryOutputPort personRepository;

	@Autowired
	AppointmentRepositoryOutputPort appointmentRepoOutputPort;

	// Get all appointments

	public Page<Appointment> getAppointments(@Valid Pageable pageable) throws BusinessException {
		log.debug("getAllAppointments");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}

		return appointmentRepoOutputPort.getAppointments(pageable);
	}

	// Add appointment
	public String addAppointment(Appointment appointment) throws BusinessException {

		if (appointment == null) {
			throw new IllegalArgumentException("El objeto appointment no puede ser null.");
		}
		String exitId = null;

		// Buscar al paciente por su id
		Optional<Person> patientOpt = personRepository.getPersonById(appointment.getPatientId());
		if (patientOpt.isPresent() && patientOpt.get().getPersonType() == PersonType.PATIENT) {
			Person patientPerson = patientOpt.get();

			// Validar que el nutricionista existe
			Optional<Person> nutritionistOpt = personRepository.getPersonById(appointment.getNutritionistId());
			if (nutritionistOpt.isPresent() && nutritionistOpt.get().getPersonType() == PersonType.NUTRITIONIST) {
				Person nutriPerson = nutritionistOpt.get();

				// Establecer los IDs del paciente y del nutricionista en la cita
				appointment.setPatientId(patientPerson.getId()); // Establecer el ID correcto del paciente
				appointment.setNutritionistId(nutriPerson.getId()); // Establecer el ID correcto del nutricionista

				// Guardar la cita
				Appointment savedAppointment = appointmentRepoOutputPort.addAppointment(appointment);
				exitId = savedAppointment.getId();

				// Añadir el ID de la cita a la lista de IDs de citas del paciente
				patientPerson.getAppointmentId().add(savedAppointment.getId());
				personRepository.modifyPerson(patientPerson);

				// Añadir el ID de la cita a la lista de IDs de citas del nutricionista
				nutriPerson.getAppointmentId().add(savedAppointment.getId());
				personRepository.modifyPerson(nutriPerson);
			} else {
				// Manejar el caso en el que el nutricionista no exista
				throw new BusinessException(Errors.PERSON_NOT_FOUND);
			}
		} else {
			// Manejar el caso en el que el paciente no exista
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		return exitId;
	}

	// Get appointments by Appointment id
	public Optional<Appointment> getAppointmentById(String id) throws BusinessException {
		log.debug("getAppointmentById");

		Optional<Appointment> appOpt = appointmentRepoOutputPort.getAppointmentById(id);
		if (appOpt.isPresent()) {
			return appOpt;
		} else {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);
		}
	}

	// Get appointments by PERSON ID
	public Page<Appointment> getAppointmentsByPersonId(String id, Pageable pageable) throws BusinessException {
		log.debug("getPatientAppointments");

		Optional<Person> personOpt = personRepository.getPersonById(id);
		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			return appointmentRepoOutputPort.getAppointmentsByPersonId(person.getId(), pageable);
		} else {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
	}

	// modify Appointment
	public void modifyAppointment(Appointment appointment) throws BusinessException {
		log.debug("modifyAppointment");

		Optional<Appointment> foundAppointment = appointmentRepoOutputPort.getAppointmentById(appointment.getId());
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);
		}
		appointmentRepoOutputPort.modifyAppointment(appointment);
	}

	public void deleteAppointment(String id) throws BusinessException {
		log.debug("deleteAppointment");
		Optional<Appointment> foundAppointment = appointmentRepoOutputPort.getAppointmentById(id);
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);

		}
		appointmentRepoOutputPort.deleteAppointment(id);

	}

}
