package com.ejercicios.primeraPractica.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import jakarta.validation.Valid;

public interface AppointmentRepositoryOutputPort {

	Page<Appointment> getAppointments(@Valid Pageable pageable);

	Page<Appointment> getAppointmentsByType(@Valid PersonType type, Pageable pageable);

	Optional<Appointment> getAppointmentById(@Valid String id);

	String addAppointment(@Valid Appointment appointment);

	void modifyAppointment(@Valid Appointment appointment);

	void deleteAppointment(@Valid String id);

}
