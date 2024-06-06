package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ejercicios.primeraPractica.application.port.output.AppointmentRepositoryOutputPort;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper.AppointmentToAppointmentEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppointmentRepositoryService implements AppointmentRepositoryOutputPort {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	AppointmentToAppointmentEntityMapper appointmentEntityMapper;

	@Override
	@Cacheable(value = "appointments", key = "#pageable")
	public Page<Appointment> getAppointments(@Valid Pageable pageable) {
		log.debug("getAppointments");

		Page<AppointmentEntity> appointments = appointmentRepository.findAll(pageable);

		return appointmentEntityMapper.fromOutputToInput(appointments);
	}

	@Override
	@Cacheable(value = "appointments", key = "#id")
	public Optional<Appointment> getAppointmentById(@Valid String id) {
		log.debug("getAppointmentById");

		Optional<AppointmentEntity> entityOpt = appointmentRepository.findByIdAndEliminado(id, false);
		return appointmentEntityMapper.fromOutputToInput(entityOpt);

	}

	@Override
	@Cacheable(value = "appointments", key = "#pageable")
	public Page<Appointment> getAppointmentsByType(@Valid PersonType type, Pageable pageable) {
		log.debug("getAppointmentsByType");

		Page<AppointmentEntity> entityOpt = appointmentRepository.findByEliminadoAndType(false, type, pageable);
		return appointmentEntityMapper.fromOutputToInput(entityOpt);

	}

	@Override
	@CacheEvict(value = "appointments", allEntries = true)
	public String addAppointment(@Valid Appointment appointment) {
		log.debug("addAppointment");

		AppointmentEntity appointmentEntity = appointmentEntityMapper.fromInputToOutput(appointment);
		AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);

		return savedAppointment.getId();
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
