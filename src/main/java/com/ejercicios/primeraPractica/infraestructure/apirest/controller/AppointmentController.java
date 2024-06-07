package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")

public class AppointmentController {

	/*
	 * @Autowired AppointmentService appService;
	 * 
	 * @Autowired PersonRepository persoRepository;
	 * 
	 * @PostMapping public ResponseEntity<Optional<String>>
	 * addAppointment(@RequestBody PostPutAppointmentDto appointmentRequest) { try {
	 * Optional<String> appointmentId =
	 * appService.addAppointment(appointmentRequest); return
	 * ResponseEntity.ok(appointmentId); } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Optional.empty()
	 * ); } }
	 * 
	 * @GetMapping public ResponseEntity<List<Appointment>> getAppointments() {
	 * 
	 * List<Appointment> allAppointments = appService.getAppointments(); if
	 * (allAppointments.isEmpty()) { return ResponseEntity.noContent().build(); }
	 * 
	 * return ResponseEntity.ok(allAppointments); }
	 * 
	 * // Patient appointment's list
	 * 
	 * @GetMapping("/{id}") public ResponseEntity<List<Appointment>>
	 * getAppointmentsById(@PathVariable("id") String id) {
	 * 
	 * List<Appointment> apps = appService.getPatientAppointments(id);
	 * 
	 * if (apps.isEmpty()) { return ResponseEntity.noContent().build(); } return
	 * ResponseEntity.ok(apps); }
	 * 
	 */
}