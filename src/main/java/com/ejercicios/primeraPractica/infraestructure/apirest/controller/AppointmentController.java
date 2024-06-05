package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicios.primeraPractica.application.service.AppointmentService;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.AppointmentRequest;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service.PersonRepository;

@RestController
@RequestMapping("/appointments")

public class AppointmentController {

	@Autowired
	AppointmentService appService;

	@Autowired
	PersonRepository persoRepository;

	@PostMapping
	public ResponseEntity<Optional<String>> addAppointment(@RequestBody AppointmentRequest appointmentRequest) {
		try {
			Optional<String> appointmentId = appService.addAppointment(appointmentRequest);
			return ResponseEntity.ok(appointmentId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Optional.empty());
		}
	}

	@GetMapping
	public ResponseEntity<List<Appointment>> getAppointments() {

		List<Appointment> allAppointments = appService.getAppointments();
		if (allAppointments.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(allAppointments);
	}

	// Patient appointment's list
	@GetMapping("/{id}")
	public ResponseEntity<List<Appointment>> getAppointmentsById(@PathVariable("id") String id) {

		List<Appointment> apps = appService.getPatientAppointments(id);

		if (apps.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(apps);
	}
}