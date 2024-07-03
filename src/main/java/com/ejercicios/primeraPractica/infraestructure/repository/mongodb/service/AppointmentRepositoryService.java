package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ejercicios.primeraPractica.application.port.output.AppointmentRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper.AppointmentToAppointmentEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing appointments in a MongoDB repository.
 */
@Slf4j
@Component
public class AppointmentRepositoryService implements AppointmentRepositoryOutputPort {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	AppointmentToAppointmentEntityMapper appointmentEntityMapper;

	/**
	 * Retrieves all appointments with pagination.
	 *
	 * @param pageable the pagination information
	 * @return a page of appointments
	 */
	@Override
	@Cacheable(value = "appointments", key = "#pageable")
	public Page<Appointment> getAppointments(@Valid Pageable pageable) {
		log.debug("getAppointments");

		Page<AppointmentEntity> appointments = appointmentRepository.findAllByEliminado(false, pageable);

		return appointmentEntityMapper.fromOutputToInput(appointments);
	}

	/**
	 * Retrieves an appointment by its ID.
	 *
	 * @param id the appointment ID
	 * @return the appointment
	 */
	@Override
	@Cacheable(value = "appointments", key = "#id")
	public Optional<Appointment> getAppointmentById(@Valid String id) {
		log.debug("getAppointmentById");

		Optional<AppointmentEntity> entityOpt = appointmentRepository.findByIdAndEliminado(id, false);
		return appointmentEntityMapper.fromOutputToInput(entityOpt);
	}

	/**
	 * Retrieves appointments by person ID with pagination.
	 *
	 * @param personId the person ID
	 * @param pageable the pagination information
	 * @return a page of appointments
	 * @throws BusinessException if the person is not found
	 */
	@Override
	@Cacheable(value = "appointments", key = "#id")
	public Page<Appointment> getAppointmentsByPersonId(String personId, Pageable pageable) throws BusinessException {
		log.debug("getAppointmentsByPersonId");

		Optional<PersonEntity> personOpt = personRepository.findByIdAndEliminado(personId, false);
		if (personOpt.isPresent()) {
			PersonEntity person = personOpt.get();
			List<String> appointmentIds = person.getAppointmentId();
			Page<AppointmentEntity> appointmentEntities = appointmentRepository.findByEliminadoAndIdIn(false,
					appointmentIds, pageable);
			return appointmentEntities.map(appointmentEntityMapper::fromOutputToInput);
		} else {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
	}

	/**
	 * Retrieves appointments by person document with pagination.
	 *
	 * @param document the person document
	 * @param pageable the pagination information
	 * @return a page of appointments
	 */
	@Override
	@Cacheable(value = "appointments", key = "#document")
	public Page<Appointment> getAppointmentsByPersonDocument(String document, Pageable pageable) {
		log.debug("getAppointmentsByPersonDocument");

		Optional<PersonEntity> personOpt = personRepository.findByPersoInfoDocumentIgnoreCaseAndEliminado(document,
				false);
		PersonEntity person = personOpt.get();
		List<String> appointmentIds = person.getAppointmentId();
		Page<AppointmentEntity> appointmentEntities = appointmentRepository.findByEliminadoAndIdIn(false,
				appointmentIds, pageable);
		return appointmentEntities.map(appointmentEntityMapper::fromOutputToInput);
	}

	/**
	 * Adds a new appointment.
	 *
	 * @param appointment the appointment to add
	 * @return the added appointment
	 */
	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public Appointment addAppointment(@Valid Appointment appointment) {
		log.debug("addAppointment");

		AppointmentEntity appointmentEntity = appointmentEntityMapper.fromInputToOutput(appointment);
		appointmentEntity.setEliminado(false);
		AppointmentEntity savedAppointmentEntity = appointmentRepository.save(appointmentEntity);
		return appointmentEntityMapper.fromOutputToInput(savedAppointmentEntity);
	}

	/**
	 * Modifies an existing appointment.
	 *
	 * @param appointment the appointment to modify
	 */
	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public void modifyAppointment(@Valid Appointment appointment) {
		log.debug("modifyAppointment");

		AppointmentEntity appointmentEntity = appointmentEntityMapper.fromInputToOutput(appointment);
		appointmentRepository.save(appointmentEntity);
	}

	/**
	 * Deletes an appointment by its ID.
	 *
	 * @param id the appointment ID
	 */
	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public void deleteAppointment(@Valid String id) {
		log.debug("deleteAppointment");

		Optional<AppointmentEntity> appointmentEntityOpt = appointmentRepository.findByIdAndEliminado(id, false);
		if (appointmentEntityOpt.isPresent()) {
			AppointmentEntity appointmentEntity = appointmentEntityOpt.get();
			appointmentEntity.setEliminado(true);
			appointmentRepository.save(appointmentEntity);
		}
	}

}
