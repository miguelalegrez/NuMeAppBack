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

@CrossOrigin(origins = "http://localhost:4200")
@SuppressWarnings("rawtypes")
@Slf4j
@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

	@Autowired
	MedicalRecordService medicalRecordService;

	@Autowired
	MedicalRecordToMedicalRecordDtoMapper medicalRecordDtoMapper;

	@Autowired
	MedicalRecordToPostPutMedicalRecordMapper medicalPostPutDtoMapper;

	@Autowired
	MedicalToPatchMedicalMapper medicalToPatchMapper;

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

	@GetMapping("/{medicalrecord-id}")
	public ResponseEntity getMedicalRecordById(@PathVariable String id) {
		try {
			Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordById(id);
			log.debug("Retrieved medical Record: {}", medicalRecord.get());
			return ResponseEntity.ok(medicalRecord.get());
		} catch (BusinessException e) {
			log.error("Error getting medicalrecord", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity addMedicalRecord(@RequestBody PostPutMedicalRecordDto medicalRecordDto)
			throws BusinessException {
		MedicalRecord medicalDomain = medicalPostPutDtoMapper.fromOutputToInput(medicalRecordDto);

		try {
			medicalDomain.setNutritionistId(medicalRecordDto.getNutritionistId());
			medicalDomain.setPatientId(medicalRecordDto.getPatientId());

			String medicalRecordId = medicalRecordService.addMedicalRecord(medicalDomain);
			URI locationHeader = createUri(medicalRecordId);
			return ResponseEntity.created(locationHeader).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{medicalrecord-id}")
	public ResponseEntity modifyMedicalRecord(@PathVariable("id") String id,
			@Valid @RequestBody PostPutMedicalRecordDto medicalRecordDto) {
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

	@PatchMapping("/{medicalrecord-id}")
	public ResponseEntity modifyPartialMedicalRecord(@PathVariable("id") String id,
			@Valid @RequestBody MedicalRecordPatchDto medicalRecordDto) {
		log.debug("modifyMedicalRecord");

		MedicalRecord domain = medicalToPatchMapper.fromOutputToInput(medicalRecordDto);
		domain.setId(id);

		try {
			medicalRecordService.modifyMedicalRecord(domain);
			return ResponseEntity.ok(domain);
		} catch (BusinessException e) {
			log.error("Error modifying medical record", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{medicalrecord-id}")
	public ResponseEntity deleteMedicalRecord(@Valid @PathVariable("id") String id) {
		log.debug("deleteMedicalRecord");

		try {
			medicalRecordService.deleteMedicalRecord(id);
			return ResponseEntity.noContent().build();
		} catch (BusinessException e) {
			log.error("Error deleting medical record", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private URI createUri(String medicalRecordId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(medicalRecordId).toUri();
	}

}
