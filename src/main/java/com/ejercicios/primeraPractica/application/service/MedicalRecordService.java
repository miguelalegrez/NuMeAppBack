package com.ejercicios.primeraPractica.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ejercicios.primeraPractica.application.port.input.MedicalRecordServiceInputPort;
import com.ejercicios.primeraPractica.application.port.output.MedicalRecordRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.util.Constants;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.mapper.MedicalRecordPatchMapper;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalRecordService implements MedicalRecordServiceInputPort {

	@Autowired
	MedicalRecordRepositoryOutputPort medicalRecordRepositoryOutputPort;

	@Autowired
	PersonRepositoryOutputPort personRepository;

	@Autowired
	MedicalRecordPatchMapper medicalPatchMapper;

	// Get all medical records

	public Page<MedicalRecord> getMedicalRecord(Pageable pageable) throws BusinessException {
		log.debug("getMedicalRecords");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}

		return medicalRecordRepositoryOutputPort.getMedicalRecords(pageable);
	}

	// Add Medical Record

	public String addMedicalRecord(MedicalRecord medicalRecord) throws BusinessException {
		if (medicalRecord == null) {
			throw new IllegalArgumentException("El objeto appointment no puede ser null.");
		}
		String exitId = null;

		// Buscamos la persona en la que quieren guardar una ficha médica y validamos
		Optional<Person> patientOpt = personRepository.getPersonById(medicalRecord.getPatientId());
		if (patientOpt.isPresent() && patientOpt.get().getPersonType() == PersonType.PATIENT) {
			Person patientPerson = patientOpt.get();

			Optional<Person> nutritionistOpt = personRepository.getPersonById(medicalRecord.getNutritionistId());
			if (nutritionistOpt.isPresent() && nutritionistOpt.get().getPersonType() == PersonType.NUTRITIONIST) {
				Person nutriPerson = nutritionistOpt.get();

				// Obtenemos la ficha médica y seteamos los datos de la visita
				medicalRecord.setPatientId(patientPerson.getId());
				medicalRecord.setDate(LocalDateTime.now());
				medicalRecord.setNutritionistId(nutriPerson.getId());

				// Guardamos la nueva ficha médica en su repo
				MedicalRecord savedMedicalRecord = medicalRecordRepositoryOutputPort.addMedicalRecord(medicalRecord);
				exitId = savedMedicalRecord.getId();

				// Actualizamos la persona con el id generado de la ficha
				Person patientForSave = patientOpt.get();
				patientForSave.getMedicalRecordId().add(savedMedicalRecord.getId());
				personRepository.modifyPerson(patientPerson);

				Person nutriForSave = nutritionistOpt.get();
				nutriForSave.getMedicalRecordId().add(savedMedicalRecord.getId());
				personRepository.modifyPerson(nutriPerson);

			} else {

				// Manejar el caso en que no exista alguna persona
				throw new BusinessException(Errors.PERSON_NOT_FOUND);
			}
		} else {

			// Manejar el caso en el que el paciente no exista
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
		return exitId;
	}

	// Get Medical Record by ID

	public Optional<MedicalRecord> getMedicalRecordById(String id) throws BusinessException {
		log.debug("getMedicalRecordById");

		Optional<MedicalRecord> mmedicalRecordOpt = medicalRecordRepositoryOutputPort.getMedicalRecordsById(id);
		if (mmedicalRecordOpt.isPresent()) {
			return mmedicalRecordOpt;
		} else {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}
	}

	public void modifyMedicalRecord(MedicalRecord medicalRecord) throws BusinessException {
		log.debug("modifyMedicalRecord");

		Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepositoryOutputPort
				.getMedicalRecordsById(medicalRecord.getId());
		if (!foundMedicalRecord.isPresent()) {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}
		medicalRecordRepositoryOutputPort.modifyMedicalRecord(medicalRecord);
	}

	public void modifyPartialMedicalRecord(MedicalRecord medicalRecord) throws BusinessException {
		log.debug("modifyPartialMedicalRecord");

		Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepositoryOutputPort
				.getMedicalRecordsById(medicalRecord.getId());
		if (!foundMedicalRecord.isPresent()) {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}

		MedicalRecord updated = foundMedicalRecord.get();
		medicalPatchMapper.update(medicalRecord, updated);
		medicalRecordRepositoryOutputPort.modifyMedicalRecord(updated);
	}

	public void deleteMedicalRecord(String id) throws BusinessException {
		log.debug("deleteMedicalRecord");
		Optional<MedicalRecord> foundAppointment = medicalRecordRepositoryOutputPort.getMedicalRecordsById(id);
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);

		}
		medicalRecordRepositoryOutputPort.deleteMedicalRecord(id);

	}

}