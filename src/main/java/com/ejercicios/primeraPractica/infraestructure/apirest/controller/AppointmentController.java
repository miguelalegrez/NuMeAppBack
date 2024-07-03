package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.application.service.AppointmentService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PatchAppointmentDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutAppointmentDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.AppointmentToAppointmentDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.AppointmentToPatchAppointmentMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.AppointmentToPostPutAppointmentMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest Controller for managing appointments.
 */
@CrossOrigin(origins = "http://localhost:4200")
@SuppressWarnings("rawtypes")
@Slf4j
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	AppointmentService appService;

	@Autowired
	AppointmentToPostPutAppointmentMapper postPutMapper;

	@Autowired
	AppointmentToAppointmentDtoMapper appToDtoMapper;

	@Autowired
	AppointmentToPatchAppointmentMapper appToPatchMapper;

	/**
	 * Retrieves a page of appointments.
	 *
	 * @param pageable the pagination information
	 * @return a response entity containing the list of appointments
	 */
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

	/**
	 * Retrieves an appointment by its ID.
	 *
	 * @param id the appointment ID
	 * @return a response entity containing the appointment
	 * @throws BusinessException if there is a business exception
	 */
	@GetMapping("/{appointmentId}")
	public ResponseEntity getAppointmentById(@PathVariable("appointmentId") String id) throws BusinessException {
		Optional<Appointment> appointment = appService.getAppointmentById(id);
		if (appointment.isPresent()) {
			return ResponseEntity.ok(appointment.get());
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Retrieves appointments by person ID.
	 *
	 * @param id       the person ID
	 * @param pageable the pagination information
	 * @return a response entity containing the list of appointments
	 */
	@GetMapping("/person/{id}")
	public ResponseEntity getAppointmentsByPersonId(@PathVariable("id") String id, Pageable pageable) {
		try {
			Page<Appointment> personAppointments = appService.getAppointmentsByPersonId(id, pageable);
			log.debug("Retrieved appointments: {}", personAppointments.getContent());
			return ResponseEntity.ok(personAppointments);
		} catch (BusinessException e) {
			log.error("Error getting appointments", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Retrieves appointments by person document.
	 *
	 * @param document the person document
	 * @param pageable the pagination information
	 * @return a response entity containing the list of appointments
	 */
	@GetMapping("/person/searchByDocument")
	public ResponseEntity getAppointmentsByPersonDocument(@RequestParam String document, Pageable pageable) {
		try {
			Page<Appointment> personAppointments = appService.getAppointmentsByPersonDocument(document, pageable);
			log.debug("Retrieved appointments: {}", personAppointments.getContent());
			return ResponseEntity.ok(personAppointments);
		} catch (BusinessException e) {
			log.error("Error getting appointments", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Adds a new appointment.
	 *
	 * @param appointmentDto the appointment DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PostMapping
	public ResponseEntity addAppointment(@RequestBody PostPutAppointmentDto appointmentDto) {
		try {
			// Convertir el DTO a dominio
			Appointment appDomain = postPutMapper.fromOutputToInput(appointmentDto);

			// Llamar al servicio para agregar la cita
			String appointmentId = appService.addAppointment(appDomain);
			URI locationHeader = createUri(appointmentId);
			return ResponseEntity.created(locationHeader).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Modifies an existing appointment.
	 *
	 * @param id             the appointment ID
	 * @param appointmentDto the appointment DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PutMapping("/{appointmentId}")
	public ResponseEntity modifyAppointment(@PathVariable("appointmentId") String id,
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

	/**
	 * Partially modifies an existing appointment.
	 *
	 * @param id                  the appointment ID
	 * @param patchAppointmentDto the appointment DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PatchMapping("/{appointmentId}")
	public ResponseEntity modifyPartialAppointment(@PathVariable("appointmentId") String id,
			@Valid @RequestBody PatchAppointmentDto patchAppointmentDto) {
		log.debug("modifyAppointment");

		Appointment domain = appToPatchMapper.fromOutputToInput(patchAppointmentDto);
		domain.setId(id);

		try {
			appService.modifyPartialAppointment(domain);
			return ResponseEntity.ok(domain);
		} catch (BusinessException e) {
			log.error("Error modifying appointment", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Deletes an appointment by its ID.
	 *
	 * @param id the appointment ID
	 * @return a response entity indicating the result of the operation
	 */
	@DeleteMapping("/{appointmentId}")
	public ResponseEntity deleteAppointment(@Valid @PathVariable("appointmentId") String id) {
		log.debug("deleteAppointment");

		try {
			appService.deleteAppointment(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessException e) {
			log.error("Error deleting appointment", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Creates a URI for the newly created appointment.
	 *
	 * @param appointmentId the appointment ID
	 * @return the URI of the newly created appointment
	 */
	private URI createUri(String appointmentId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(appointmentId).toUri();
	}
}
