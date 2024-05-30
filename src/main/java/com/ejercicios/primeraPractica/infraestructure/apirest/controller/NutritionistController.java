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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.negocio.model.Person;
import com.ejercicios.primeraPractica.negocio.model.PersonalInfo;
import com.ejercicios.primeraPractica.negocio.service.NutritionistService;

@RestController
@RequestMapping("/nutritionists")
public class NutritionistController {

	@Autowired
	NutritionistService nutriService;

	@GetMapping
	public ResponseEntity<List<Person>> getPatients() {
		List<Person> nutritionists = nutriService.getNutritrionists();
		if (nutritionists.isEmpty()) {
			ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(nutritionists);
	}

	// Obtener paciente por id
	@GetMapping("/{id}") // el Id es la identificación personal: DNI o Passport
	public ResponseEntity<Person> getNutritionistById(@PathVariable("id") String id) {
		Optional<Person> persoOpt = nutriService.getNutritrionists().stream()
				.filter(p -> p.getPersoInfo().getDocument().equals(id)).findFirst();
		if (persoOpt.isPresent()) {
			return ResponseEntity.ok(persoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<String> addNutritionist(@RequestBody PersonalInfo personalInfo) {
		Optional<String> optionalId = nutriService.addNutritionist(personalInfo);

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
