package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.application.service.AppointmentService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutAppointmentDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.AppointmentToAppointmentDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.AppointmentToPostPutAppointmentMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appService;

	@Autowired
	private AppointmentToPostPutAppointmentMapper postPutMapper;

	@Autowired
	private AppointmentToAppointmentDtoMapper appToDtoMapper;

	@GetMapping
	public ResponseEntity getAppointments(Pageable pageable) {
		log.debug("getAppointments");

		try {
			Page<Appointment> allAppointments = appService.getAppointments(pageable);
			log.debug("Retrieved appointments: {}", allAppointments.getContent());
			return ResponseEntity.ok(appToDtoMapper.fromInputToOutput(allAppointments));
		} catch (BusinessException e) {
			log.error("Error getting appointments", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// appointment by APPOINTMENT id
	@GetMapping("/{id}")
	public ResponseEntity getAppointmentById(@PathVariable("id") String id) throws BusinessException {
		Optional<Appointment> appointment = appService.getAppointmentById(id);
		if (appointment.isPresent()) {
			return ResponseEntity.ok(appointment.get());
		}
		return ResponseEntity.notFound().build();
	}

	// appointment by PERSON id
	@GetMapping("/person/{id}")
	public ResponseEntity getAppontmentsByPersonId(@PathVariable("id") String id, Pageable pageable) {

		try {
			Page<Appointment> personAppointments = appService.getAppointmentsByPersonId(id, pageable);
			log.debug("Retrieved appointments: {}", personAppointments.getContent());
			return ResponseEntity.ok(personAppointments);
		} catch (BusinessException e) {
			log.error("Error getting appointments", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity addAppointment(@RequestBody PostPutAppointmentDto appointmentDto) {
		try {
			Appointment appDomain = postPutMapper.fromOutputToInput(appointmentDto);

			// Debemos setear los ids ya que el appointmentDto contiene ambos.
			appDomain.setNutritionistId(appointmentDto.getNutritionistId());
			appDomain.setPatientId(appointmentDto.getPatientId());

			String appointmentId = appService.addAppointment(appDomain);
			URI locationHeader = createUri(appointmentId);
			return ResponseEntity.created(locationHeader).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity modifyAppointment(@PathVariable("id") String id,
			@Valid @RequestBody PostPutAppointmentDto appointmentDto) {
		log.debug("modifyAppointment");

		Appointment domain = postPutMapper.fromOutputToInput(appointmentDto);
		domain.setId(id);

		try {
			appService.modifyAppointment(domain);
			return ResponseEntity.ok(domain);
		} catch (BusinessException e) {
			log.error("Error modifying appointment", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteAppointment(@Valid @PathVariable("id") String id) {
		log.debug("deleteAppointment");

		try {
			appService.deleteAppointment(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessException e) {
			log.error("Error deleting appointment", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private URI createUri(String appointmentId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(appointmentId).toUri();
	}
}
