package com.ejercicios.primeraPractica.infraestructure.apirest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
	/*
	 * @Autowired private MedicalRecordService medicalRecordService;
	 * 
	 * @GetMapping public ResponseEntity<List<MedicalRecord>> getMedicalRecords() {
	 * 
	 * List<MedicalRecord> AllMedicalRecords =
	 * medicalRecordService.getMedicalRecord(); if (AllMedicalRecords.isEmpty()) {
	 * return ResponseEntity.noContent().build(); }
	 * 
	 * return ResponseEntity.ok(AllMedicalRecords); }
	 * 
	 * @GetMapping("/{id}") public ResponseEntity<MedicalRecord>
	 * getMedicalRecordById(@PathVariable String id) { Optional<MedicalRecord>
	 * medicalRecord = medicalRecordService.getMedicalRecordById(id); return
	 * medicalRecord.map(ResponseEntity::ok) .orElseGet(() ->
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); }
	 * 
	 * @PostMapping("/{personId}") public ResponseEntity<Optional<String>>
	 * addMedicalRecord(@RequestBody MedicalRecordRequest medicalRecordRequest) {
	 * try { Optional<String> medicalRecordId =
	 * medicalRecordService.addMedicalRecord(medicalRecordRequest); return
	 * ResponseEntity.ok(medicalRecordId); } catch (RuntimeException e) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty()); } }
	 * 
	 */
}
