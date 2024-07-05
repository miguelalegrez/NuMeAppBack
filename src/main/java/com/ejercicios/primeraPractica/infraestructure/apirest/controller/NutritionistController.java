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

import com.ejercicios.primeraPractica.application.service.PersonService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PatchPersonDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutPersonDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPatchPersonMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPersonDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.PersonToPostPutPersonMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest Controller for managing nutritionists.
 */
@CrossOrigin(origins = "http://localhost:4200")
@SuppressWarnings("rawtypes")
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

	@Autowired
	PersonToPatchPersonMapper personToPatchDtoMapper;

	/**
	 * Retrieves a page of nutritionists.
	 *
	 * @param pageable the pagination information
	 * @return a response entity containing the list of nutritionists
	 */
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

	/**
	 * Retrieves a nutritionist by its ID.
	 *
	 * @param id the nutritionist ID
	 * @return a response entity containing the nutritionist
	 */
	@GetMapping("/{nutritionistId}")
	public ResponseEntity getPersonById(@PathVariable("nutritionistId") String id) {
		Optional<Person> persoOpt = personService.getPersonById(id);
		if (persoOpt.isPresent()) {
			return ResponseEntity.ok(persoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Retrieves nutritionists by their name and surname.
	 *
	 * @param name     the nutritionist's name
	 * @param surname  the nutritionist's surname
	 * @param pageable the pagination information
	 * @return a response entity containing the list of nutritionists
	 */
	@GetMapping("/searchNutritionists")
	public ResponseEntity getNutritionistByNameAndSurnameAndType(@RequestParam String name,
			@RequestParam String surname, Pageable pageable) {
		log.debug("getNutritionistByNameAndSurname", name, surname);
		try {
			Page<Person> nutritionists;
			nutritionists = personService.getPersonByNameAndSurnameAndType(name, surname, PersonType.NUTRITIONIST,
					pageable);
			log.debug("Retrieved nutritionists", nutritionists.getContent());
			return ResponseEntity.ok(personToPersonDto.fromInputToOutput(nutritionists));
		} catch (BusinessException e) {
			log.error("Error getting users", e);
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	/**
	 * Retrieves a nutritionist by their document.
	 *
	 * @param document the nutritionist's document
	 * @return a response entity containing the nutritionist
	 */
	@GetMapping("/searchByNutritionistDocument")
	public ResponseEntity getNutritionistByDocument(@RequestParam String document) {
		log.debug("searchNutritionistByDocument");
		Optional<Person> personOpt = personService.findByPersoInfoDocument(document);
		if (personOpt.isPresent()) {
			return ResponseEntity.ok(personToPersonDto.fromInputToOutput(personOpt));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Adds a new nutritionist.
	 *
	 * @param personDto the nutritionist DTO
	 * @return a response entity indicating the result of the operation
	 */
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

	/**
	 * Modifies an existing nutritionist.
	 *
	 * @param id        the nutritionist ID
	 * @param personDto the nutritionist DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PutMapping("/{id}")
	public ResponseEntity modifyPerson(@PathVariable String id, @Valid @RequestBody PostPutPersonDto personDto) {
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

	/**
	 * Partially modifies an existing nutritionist.
	 *
	 * @param id        the nutritionist ID
	 * @param personDto the nutritionist DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PatchMapping("/{nutritionistId}")
	public ResponseEntity modifyPartialPerson(@PathVariable("nutritionistId") String id,
			@RequestBody PatchPersonDto personDto) {
		log.debug("modifyPartialPerson: {}", id);
		Person domain = personToPatchDtoMapper.fromOutputToInput(personDto);
		domain.setId(id);
		try {
			personService.modifyPartialPerson(domain);
		} catch (BusinessException e) {
			log.error("Error modifying nutritionist");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Deletes a nutritionist by its ID.
	 *
	 * @param id the nutritionist ID
	 * @return a response entity indicating the result of the operation
	 */
	@DeleteMapping("/{nutritionistId}")
	public ResponseEntity deleteUser(@Valid @PathVariable("nutritionistId") String id) {
		log.debug("deleteUser");

		try {
			personService.deletePerson(id);
		} catch (BusinessException e) {
			log.error("Error deleting user", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.noContent().build();

	}

	/**
	 * Creates a URI for the newly created nutritionist.
	 *
	 * @param id the nutritionist ID
	 * @return the URI of the newly created nutritionist
	 */
	private URI createUri(String id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

}
