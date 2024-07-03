package com.ejercicios.primeraPractica.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Appointment;

import jakarta.validation.Valid;

public interface AppointmentServiceInputPort {

	Page<Appointment> getAppointments(@Valid Pageable pageable) throws BusinessException;

	String addAppointment(@Valid Appointment appointment) throws BusinessException;

	Optional<Appointment> getAppointmentById(@Valid String id) throws BusinessException;

	void modifyAppointment(@Valid Appointment appointment) throws BusinessException;

	public void modifyPartialAppointment(Appointment appointment) throws BusinessException;

	void deleteAppointment(@Valid String id) throws BusinessException;

}
