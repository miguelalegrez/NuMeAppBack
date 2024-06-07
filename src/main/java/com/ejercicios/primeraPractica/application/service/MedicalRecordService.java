package com.ejercicios.primeraPractica.application.service;

import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {
	/*
	 * @Autowired private MedicalRecordRepository medicalRecordRepository;
	 * 
	 * @Autowired private PersonRepository personRepository;
	 * 
	 * // Get all medical records
	 * 
	 * public List<MedicalRecord> getMedicalRecord() { return
	 * medicalRecordRepository.findAll(); }
	 * 
	 * // Add Medical Record
	 * 
	 * public Optional<String> addMedicalRecord(MedicalRecordRequest
	 * medicalRecordRequest) { Optional<String> exitId = Optional.empty();
	 * 
	 * // Buscamos la persona en la que quieren guardar una ficha médica
	 * Optional<Person> personOpt = personRepository
	 * .findByPersoInfoDocument(medicalRecordRequest.getPatientDocument());
	 * 
	 * // Validamos que la persona existe if (personOpt.isPresent()) { Person person
	 * = personOpt.get();
	 * 
	 * // Validar que el nutricionista existe Optional<Person> nutritionistOpt =
	 * personRepository
	 * .findByPersoInfoDocument(medicalRecordRequest.getNutritionistId()); if
	 * (nutritionistOpt.isPresent() && nutritionistOpt.get().getType() ==
	 * PersonType.NUTRITIONIST) { Person nutriPerson = nutritionistOpt.get();
	 * 
	 * // Obtenemos la ficha médica del request y seteamos los datos de la visita
	 * MedicalRecord medicalRecord = medicalRecordRequest.getMedicalRecord();
	 * medicalRecord.setPatientDocument(person.getPersoInfo().getDocument());
	 * medicalRecord.setDate(LocalDateTime.now());
	 * medicalRecord.setMedicalIdentification(nutriPerson.getPersoInfo().getDocument
	 * ()); medicalRecord.setObservations(medicalRecord.getObservations());
	 * medicalRecord.setRegistryType(medicalRecord.getRegistryType());
	 * 
	 * // Guardamos la nueva ficha médica en su repo MedicalRecord
	 * savedMedicalRecord = medicalRecordRepository.save(medicalRecord); exitId =
	 * Optional.ofNullable(savedMedicalRecord.getId());
	 * 
	 * // Actualizamos la persona con el id generado de la ficha Person
	 * personForSave = personOpt.get();
	 * personForSave.getMedicalRecordId().add(savedMedicalRecord.getId());
	 * personRepository.save(personForSave); } else {
	 * 
	 * // Manejar el caso en el que el nutricionista no exista o no sea un //
	 * nutricionista throw new
	 * RuntimeException("Nutricionista no encontrado o no válido"); } } else {
	 * 
	 * // Manejar el caso en el que el paciente no exista throw new
	 * RuntimeException("Paciente no encontrado"); } return exitId; }
	 * 
	 * // Get Medical Record by ID
	 * 
	 * public Optional<MedicalRecord> getMedicalRecordById(String id) { return
	 * medicalRecordRepository.findById(id); }
	 * 
	 */
}
