package com.ejercicios.primeraPractica.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.application.port.input.AppointmentServiceInputPort;
import com.ejercicios.primeraPractica.application.port.output.AppointmentRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.util.Constants;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.mapper.AppointmentPatchMapper;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing appointments.
 */
@Slf4j
@Service
public class AppointmentService implements AppointmentServiceInputPort {

	@Autowired
	PersonRepositoryOutputPort personRepository;

	@Autowired
	AppointmentRepositoryOutputPort appointmentRepoOutputPort;

	@Autowired
	AppointmentPatchMapper appointmentPatchMapper;

	/**
	 * Retrieves all appointments with pagination.
	 *
	 * @param pageable the pagination information
	 * @return a page of appointments
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public Page<Appointment> getAppointments(@Valid Pageable pageable) throws BusinessException {
		log.debug("getAllAppointments");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}

		return appointmentRepoOutputPort.getAppointments(pageable);
	}

	/**
	 * Adds a new appointment.
	 *
	 * @param appointment the appointment to add
	 * @return the ID of the added appointment
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public String addAppointment(Appointment appointment) throws BusinessException {

		if (appointment == null) {
			throw new IllegalArgumentException("El objeto appointment no puede ser null.");
		}

		String exitId = null;

		// Buscar al paciente por su documento
		Optional<Person> patientOpt = personRepository.getPersonById(appointment.getPatientId());
		if (patientOpt.isPresent() && patientOpt.get().getPersonType() == PersonType.PATIENT) {
			Person patientPerson = patientOpt.get();
			log.debug("Paciente encontrado: {}", patientPerson);

			// Buscar al nutricionista por su documento
			Optional<Person> nutritionistOpt = personRepository.getPersonById(appointment.getNutritionistId());
			if (nutritionistOpt.isPresent() && nutritionistOpt.get().getPersonType() == PersonType.NUTRITIONIST) {
				Person nutriPerson = nutritionistOpt.get();
				log.debug("Nutricionista encontrado: {}", nutriPerson);

				// Establecer los datos del paciente en la cita
				appointment.setPatientId(patientPerson.getId());
				appointment.setPatientName(patientPerson.getPersoInfo().getName());
				appointment.setPatientSurname(patientPerson.getPersoInfo().getSurname());
				appointment.setPatientDocument(patientPerson.getPersoInfo().getDocument());

				// Establecer los datos del nutricionista en la cita
				appointment.setNutritionistId(nutriPerson.getId());
				appointment.setNutritionistName(nutriPerson.getPersoInfo().getName());
				appointment.setNutritionistSurname(nutriPerson.getPersoInfo().getSurname());
				appointment.setNutritionistDocument(nutriPerson.getPersoInfo().getDocument());

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
				log.error("Nutricionista no encontrado o no es del tipo NUTRITIONIST");
				// Manejar el caso en el que el nutricionista no exista
				throw new BusinessException(Errors.PERSON_NOT_FOUND);
			}
		} else {
			log.error("Paciente no encontrado o no es del tipo PATIENT");
			// Manejar el caso en el que el paciente no exista
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		return exitId;
	}

	/**
	 * Retrieves an appointment by its ID.
	 *
	 * @param id the appointment ID
	 * @return the appointment
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public Optional<Appointment> getAppointmentById(String id) throws BusinessException {
		log.debug("getAppointmentById");

		Optional<Appointment> appOpt = appointmentRepoOutputPort.getAppointmentById(id);
		if (appOpt.isPresent()) {
			return appOpt;
		} else {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);
		}
	}

	/**
	 * Retrieves appointments by person ID.
	 *
	 * @param id       the person ID
	 * @param pageable the pagination information
	 * @return a page of appointments
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
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

	/**
	 * Retrieves appointments by person document.
	 *
	 * @param document the person document
	 * @param pageable the pagination information
	 * @return a page of appointments
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public Page<Appointment> getAppointmentsByPersonDocument(String document, Pageable pageable)
			throws BusinessException {
		log.debug("getPatientAppointmentsByDocument");

		Optional<Person> personOpt = personRepository.findByPersoInfoDocument(document);
		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			return appointmentRepoOutputPort.getAppointmentsByPersonDocument(person.getPersoInfo().getDocument(),
					pageable);
		} else {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
	}

	/**
	 * Modifies an existing appointment.
	 *
	 * @param appointment the appointment to modify
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public void modifyAppointment(Appointment appointment) throws BusinessException {
		log.debug("modifyAppointment");

		Optional<Appointment> foundAppointment = appointmentRepoOutputPort.getAppointmentById(appointment.getId());
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);
		}
		appointmentRepoOutputPort.modifyAppointment(appointment);
	}

	/**
	 * Partially modifies an existing appointment.
	 *
	 * @param appointment the appointment to modify
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public void modifyPartialAppointment(Appointment appointment) throws BusinessException {
		log.debug("modifyPartialAppointment");

		Optional<Appointment> foundAppointment = appointmentRepoOutputPort.getAppointmentById(appointment.getId());
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);
		}
		Appointment updated = foundAppointment.get();
		appointmentPatchMapper.update(updated, appointment);
		appointmentRepoOutputPort.modifyAppointment(updated);
	}

	/**
	 * Deletes an appointment by its ID.
	 *
	 * @param id the appointment ID
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public void deleteAppointment(String id) throws BusinessException {
		log.debug("deleteAppointment");
		Optional<Appointment> foundAppointment = appointmentRepoOutputPort.getAppointmentById(id);
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.APPOINTMENT_NOT_FOUND);
		}
		appointmentRepoOutputPort.deleteAppointment(id);
	}

}
