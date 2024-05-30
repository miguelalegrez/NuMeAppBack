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

import com.ejercicios.primeraPractica.negocio.dto.MedicalRecordRequest;
import com.ejercicios.primeraPractica.negocio.model.MedicalRecord;
import com.ejercicios.primeraPractica.negocio.service.MedicalRecordService;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

	@Autowired
	private MedicalRecordService medicalRecordService;

	@GetMapping
	public ResponseEntity<List<MedicalRecord>> getMedicalRecords() {

		List<MedicalRecord> AllMedicalRecords = medicalRecordService.getMedicalRecord();
		if (AllMedicalRecords.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(AllMedicalRecords);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable String id) {
		Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordById(id);
		return medicalRecord.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@PostMapping("/{personId}")
	public ResponseEntity<Optional<String>> addMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest) {
		try {
			Optional<String> medicalRecordId = medicalRecordService.addMedicalRecord(medicalRecordRequest);
			return ResponseEntity.ok(medicalRecordId);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
		}
	}
}
