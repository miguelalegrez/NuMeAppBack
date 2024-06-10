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

@Slf4j
@Component
public class AppointmentRepositoryService implements AppointmentRepositoryOutputPort {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	AppointmentToAppointmentEntityMapper appointmentEntityMapper;

	@Override
	@Cacheable(value = "appointments", key = "#pageable")
	public Page<Appointment> getAppointments(@Valid Pageable pageable) {
		log.debug("getAppointments");

		Page<AppointmentEntity> appointments = appointmentRepository.findAll(pageable);

		return appointmentEntityMapper.fromOutputToInput(appointments);
	}

	// Get appointment by APPOINTMENT ID (NOT USER ID)
	@Override
	@Cacheable(value = "appointments", key = "#id")
	public Optional<Appointment> getAppointmentById(@Valid String id) {
		log.debug("getAppointmentById");

		Optional<AppointmentEntity> entityOpt = appointmentRepository.findById(id);
		return appointmentEntityMapper.fromOutputToInput(entityOpt);

	}

	// Get appointment by PERSON ID
	@Override
	@Cacheable(value = "appointments", key = "#id")
	public Page<Appointment> getAppointmentsByPersonId(String personId, Pageable pageable) throws BusinessException {
		log.debug("getAppointmentsByPersonId");

		// Primero busco a la persona por su id
		Optional<PersonEntity> personOpt = personRepository.findById(personId);
		if (personOpt.isPresent()) {
			PersonEntity person = personOpt.get();
			// Recopilo los ids de sus CITAS
			List<String> appointmentIds = person.getAppointmentId();

			// Las busco a través del repositorio con la lista
			Page<AppointmentEntity> appointmentEntities = appointmentRepository.findByIdIn(appointmentIds, pageable);
			return appointmentEntities.map(appointmentEntityMapper::fromOutputToInput);
		} else {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
	}

	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public Appointment addAppointment(@Valid Appointment appointment) {
		log.debug("addAppointment");

		// Convertir el objeto Appointment a AppointmentEntity
		AppointmentEntity appointmentEntity = appointmentEntityMapper.fromInputToOutput(appointment);

		// Guardar la entidad en el repositorio
		AppointmentEntity savedAppointmentEntity = appointmentRepository.save(appointmentEntity);

		// Convertir la entidad guardada de nuevo a Appointment
		return appointmentEntityMapper.fromOutputToInput(savedAppointmentEntity);
	}

	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public void modifyAppointment(@Valid Appointment appointment) {
		log.debug("modifyAppointment");

		AppointmentEntity appointmentEntity = appointmentEntityMapper.fromInputToOutput(appointment);
		appointmentRepository.save(appointmentEntity);
	}

	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public void deleteAppointment(@Valid String id) {
		log.debug("deleteAppointment");

		appointmentRepository.deleteById(id);
	}

}
