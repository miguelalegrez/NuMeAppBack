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

import com.ejercicios.primeraPractica.application.service.PersonService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutPersonDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPersonDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPostPutPersonMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nutritionists")
public class NutritionistController {

	@Autowired
	PersonService personService;

	@Autowired
	PersonToPersonDtoMapper personToPersonDto;

	@Autowired
	PersonToPostPutPersonMapper personToPostPutDtoMapper;

	@GetMapping
	public ResponseEntity getNutritionists(Pageable pageable) {
		log.debug("getNutritionists");
		Page<Person> nutritionists;
		try {
			nutritionists = personService.getPersonsByType(PersonType.NUTRITIONIST, pageable);
		} catch (BusinessException e) {
			log.error("Error getting users", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(personToPersonDto.fromInputToOutput(nutritionists));
	}

	@GetMapping("/{id}")
	public ResponseEntity getPersonById(@PathVariable("id") String id) {
		Optional<Person> persoOpt = personService.getPersonById(id);
		if (persoOpt.isPresent()) {
			return ResponseEntity.ok(persoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity addNutritionist(@Valid @RequestBody PostPutPersonDto personDto) {

		try {
			Person nutritionist = personToPostPutDtoMapper.fromOutputToInput(personDto);
			String idNewNutritionist = personService.createNutritionist(nutritionist);
			URI locationHeader = createUri(idNewNutritionist);
			return ResponseEntity.created(locationHeader).build();

		} catch (BusinessException e) {
			log.error("Error creating nutritionist", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modifyPerson(@PathVariable String id, @Valid @RequestBody PostPutPersonDto personDto) {
		try {
			Person domain = personToPostPutDtoMapper.fromOutputToInput(personDto);
			domain.setId(id); // Asignar el ID al objeto de dominio

			personService.modifyPerson(domain);
			return ResponseEntity.ok().build();
		} catch (BusinessException e) {
			log.error("Error modifying user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteUser(@Valid @PathVariable("user-id") String id) {
		log.debug("deleteUser");

		try {
			personService.deletePerson(id);
		} catch (BusinessException e) {
			log.error("Error deleting user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();

	}

	private URI createUri(String id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
