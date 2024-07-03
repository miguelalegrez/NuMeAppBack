package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import java.net.URI;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ejercicios.primeraPractica.application.service.MedicalRecordService;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.MedicalRecordPatchDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutMedicalRecordDto;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.MedicalRecordToMedicalRecordDtoMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.MedicalRecordToPostPutMedicalRecordMapper;
import com.ejercicios.primeraPractica.infraestructure.apirest.mapper.MedicalToPatchMedicalMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Rest Controller for managing medical records.
 */
@CrossOrigin(origins = "http://localhost:4200")
@SuppressWarnings("rawtypes")
@Slf4j
@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

	@Autowired
	MedicalRecordService medicalRecordService;

	@Autowired
	MedicalRecordToMedicalRecordDtoMapper medicalRecordDtoMapper;

	@Autowired
	MedicalRecordToPostPutMedicalRecordMapper medicalPostPutDtoMapper;

	@Autowired
	MedicalToPatchMedicalMapper medicalToPatchMapper;

	/**
	 * Retrieves a page of medical records.
	 *
	 * @param pageable the pagination information
	 * @return a response entity containing the list of medical records
	 */
	@GetMapping
	public ResponseEntity getMedicalRecords(Pageable pageable) {
		try {
			Page<MedicalRecord> AllMedicalRecords = medicalRecordService.getMedicalRecord(pageable);
			log.debug("Retrieved medical records: {}", AllMedicalRecords.getContent());
			return ResponseEntity.ok(medicalRecordDtoMapper.fromInputToOutput(AllMedicalRecords));
		} catch (BusinessException e) {
			log.error("Error getting medical records", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Retrieves a medical record by its ID.
	 *
	 * @param id the medical record ID
	 * @return a response entity containing the medical record
	 */
	@GetMapping("/{medicalRecordId}")
	public ResponseEntity getMedicalRecordById(@PathVariable("medicalRecordId") String id) {
		try {
			Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordById(id);
			log.debug("Retrieved medical Record: {}", medicalRecord.get());
			return ResponseEntity.ok(medicalRecord.get());
		} catch (BusinessException e) {
			log.error("Error getting medical record", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Retrieves medical records by person ID.
	 *
	 * @param personId the person ID
	 * @param pageable the pagination information
	 * @return a response entity containing the list of medical records
	 */
	@GetMapping("/person/{personId}")
	public ResponseEntity getMedicalRecordsByPersonId(@PathVariable String personId, Pageable pageable) {
		try {
			Page<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersonId(personId, pageable);
			return ResponseEntity.ok(medicalRecords);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Adds a new medical record.
	 *
	 * @param medicalRecordDto the medical record DTO
	 * @return a response entity indicating the result of the operation
	 * @throws BusinessException if there is a business exception
	 */
	@PostMapping
	public ResponseEntity addMedicalRecord(@RequestBody PostPutMedicalRecordDto medicalRecordDto)
			throws BusinessException {
		MedicalRecord medicalDomain = medicalPostPutDtoMapper.fromOutputToInput(medicalRecordDto);

		try {
			String medicalRecordId = medicalRecordService.addMedicalRecord(medicalDomain);
			URI locationHeader = createUri(medicalRecordId);
			return ResponseEntity.created(locationHeader).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Modifies an existing medical record.
	 *
	 * @param id               the medical record ID
	 * @param medicalRecordDto the medical record DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PutMapping("/{medicalRecordId}")
	public ResponseEntity modifyMedicalRecord(@PathVariable("medicalRecordId") String id,
			@RequestBody PostPutMedicalRecordDto medicalRecordDto) {
		log.debug("modifyMedicalRecord");

		MedicalRecord domain = medicalPostPutDtoMapper.fromOutputToInput(medicalRecordDto);
		domain.setId(id);

		try {
			medicalRecordService.modifyMedicalRecord(domain);
			return ResponseEntity.ok(domain);
		} catch (BusinessException e) {
			log.error("Error modifying medical record", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Partially modifies an existing medical record.
	 *
	 * @param id               the medical record ID
	 * @param medicalRecordDto the medical record DTO
	 * @return a response entity indicating the result of the operation
	 */
	@PatchMapping("/{medicalRecordId}")
	public ResponseEntity modifyPartialMedicalRecord(@PathVariable("medicalRecordId") String id,
			@RequestBody MedicalRecordPatchDto medicalRecordDto) {
		log.debug("modifyMedicalRecord");

		MedicalRecord domain = medicalToPatchMapper.fromOutputToInput(medicalRecordDto);
		domain.setId(id);
		domain.setDate(LocalDateTime.now());

		try {
			medicalRecordService.modifyMedicalRecord(domain);
			return ResponseEntity.ok(domain);
		} catch (BusinessException e) {
			log.error("Error modifying medical record", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Deletes a medical record by its ID.
	 *
	 * @param id the medical record ID
	 * @return a response entity indicating the result of the operation
	 */
	@DeleteMapping("/{medicalRecordId}")
	public ResponseEntity deleteMedicalRecord(@Valid @PathVariable("medicalRecordId") String id) {
		log.debug("deleteMedicalRecord");

		try {
			medicalRecordService.deleteMedicalRecord(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessException e) {
			log.error("Error deleting medical record", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	/**
	 * Creates a URI for the newly created medical record.
	 *
	 * @param medicalRecordId the medical record ID
	 * @return the URI of the newly created medical record
	 */
	private URI createUri(String medicalRecordId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(medicalRecordId).toUri();
	}

}
