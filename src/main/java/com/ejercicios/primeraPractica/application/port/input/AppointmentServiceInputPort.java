package com.ejercicios.primeraPractica.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;

public interface AppointmentServiceInputPort {

	Page<Appointment> getAppointments(@Valid Pageable pageable);

	Page<Appointment> getAppointmentsByType(@Valid PersonType type, Pageable pageable);

	String addAppointment(@Valid Appointment appointment);

	Optional<Appointment> getAppointmentById(@Valid String id);

	void modifyAppointment(@Valid Appointment appointment);

	void deleteAppointment(@Valid String id);

}
