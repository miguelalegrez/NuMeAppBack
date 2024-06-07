package com.ejercicios.primeraPractica.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Appointment;

import jakarta.validation.Valid;

public interface AppointmentRepositoryOutputPort {

	Page<Appointment> getAppointments(@Valid Pageable pageable);

	Page<Appointment> getAppointmentsByPersonId(String personId, Pageable pageable) throws BusinessException;

	Optional<Appointment> getAppointmentById(@Valid String id);

	Appointment addAppointment(@Valid Appointment appointment);

	void modifyAppointment(@Valid Appointment appointment);

	void deleteAppointment(@Valid String id);

}
