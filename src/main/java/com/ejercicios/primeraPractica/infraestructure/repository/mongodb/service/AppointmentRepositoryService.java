package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ejercicios.primeraPractica.application.port.output.AppointmentRepositoryOutputPort;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppointmentRepositoryService implements AppointmentRepositoryOutputPort {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public Page<Appointment> getAppointments(@Valid Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Appointment> getPatientAppointments(@Valid PersonType type, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Appointment> getNutritionistAppointments(@Valid PersonType type, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addAppointment(@Valid Appointment appointment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Appointment> getAppointmentById(@Valid String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void modifyAppointment(@Valid Appointment appointment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAppointment(@Valid String id) {
		// TODO Auto-generated method stub

	}

}
