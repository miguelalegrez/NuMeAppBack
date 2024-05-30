package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.negocio.model.Person;
import com.ejercicios.primeraPractica.negocio.model.PersonalInfo;
import com.ejercicios.primeraPractica.negocio.service.PatientService;

@RestController
public class PatientController {

	@Autowired
	PatientService patientService;

	@GetMapping("/patients")
	public ResponseEntity<List<Person>> getPatients() {
		List<Person> patients = patientService.getPatients();
		if (patients.isEmpty()) {
			ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(patients);
	}

	// Obtener paciente por id
	@GetMapping("/patients/{id}") // el Id es la identificación personal: DNI o Passport
	public ResponseEntity<Person> getPatientById(@PathVariable("id") String id) {
		Optional<Person> persoOpt = patientService.getPatients().stream()
				.filter(p -> p.getPersoInfo().getDocument().equalsIgnoreCase(id)).findFirst();
		if (persoOpt.isPresent()) {
			return ResponseEntity.ok(persoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/patients")
	public ResponseEntity<String> addPatient(@RequestBody PersonalInfo personalInfo) {
		Optional<String> optionalId = patientService.addPatient(personalInfo);

		if (optionalId.isPresent()) {
			URI location = createUri(optionalId.get());
			return ResponseEntity.created(location).body("Usuario creado correctamente");
		} else {
			return ResponseEntity.status(500).body("Error al crear el usuario");

		}
	}

	private URI createUri(String id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
