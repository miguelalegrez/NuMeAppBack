package com.ejercicios.primeraPractica.application.service;

import java.time.LocalDateTime;
import java.util.List;
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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing medical records.
 */
@Slf4j
@Service
public class MedicalRecordService implements MedicalRecordServiceInputPort {

	@Autowired
	MedicalRecordRepositoryOutputPort medicalRecordRepositoryOutputPort;

	@Autowired
	PersonRepositoryOutputPort personRepository;

	@Autowired
	MedicalRecordPatchMapper medicalPatchMapper;

	/**
	 * Retrieves all medical records with pagination.
	 *
	 * @param pageable the pagination information
	 * @return a page of medical records
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public Page<MedicalRecord> getMedicalRecord(Pageable pageable) throws BusinessException {
		log.debug("getMedicalRecords");

		if (pageable.getPageSize() > Constants.MAXIMUM_PAGINATION) {
			throw new BusinessException(Errors.MAXIMUM_PAGINATION_EXCEEDED);
		}

		return medicalRecordRepositoryOutputPort.getMedicalRecords(pageable);
	}

	/**
	 * Adds a new medical record.
	 *
	 * @param medicalRecord the medical record to add
	 * @return the ID of the added medical record
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public String addMedicalRecord(MedicalRecord medicalRecord) throws BusinessException {
		if (medicalRecord == null) {
			throw new IllegalArgumentException("El objeto medicalRecord no puede ser null.");
		}
		String exitId = null;

		// Buscamos la persona en la que quieren guardar una ficha médica y validamos
		Optional<Person> patientOpt = personRepository.getPersonById(medicalRecord.getPatientId());
		if (patientOpt.isPresent() && patientOpt.get().getPersonType() == PersonType.PATIENT) {
			Person patientPerson = patientOpt.get();

			// Obtenemos la ficha médica y seteamos los datos de la visita
			medicalRecord.setPatientId(patientPerson.getId());
			medicalRecord.setDate(LocalDateTime.now());

			// Guardamos la nueva ficha médica en su repo
			MedicalRecord savedMedicalRecord = medicalRecordRepositoryOutputPort.addMedicalRecord(medicalRecord);
			exitId = savedMedicalRecord.getId();

			// Actualizamos la persona con el id generado de la ficha
			Person patientForSave = patientOpt.get();
			patientForSave.getMedicalRecordId().add(savedMedicalRecord.getId());
			personRepository.modifyPerson(patientPerson);

		} else {

			// Manejar el caso en que no exista alguna persona
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}

		return exitId;
	}

	/**
	 * Retrieves a medical record by its ID.
	 *
	 * @param id the medical record ID
	 * @return the medical record
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public Optional<MedicalRecord> getMedicalRecordById(String id) throws BusinessException {
		log.debug("getMedicalRecordById");

		Optional<MedicalRecord> mmedicalRecordOpt = medicalRecordRepositoryOutputPort.getMedicalRecordsById(id);
		if (mmedicalRecordOpt.isPresent()) {
			return mmedicalRecordOpt;
		} else {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}
	}

	/**
	 * Retrieves medical records by person ID.
	 *
	 * @param id       the person ID
	 * @param pageable the pagination information
	 * @return a page of medical records
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public Page<MedicalRecord> getMedicalRecordsByPersonId(String id, Pageable pageable) throws BusinessException {
		log.debug("getPatientReports");

		Optional<Person> personOpt = personRepository.getPersonById(id);
		if (personOpt.isPresent()) {
			Person person = personOpt.get();
			List<String> medicalRecordsIds = person.getMedicalRecordId();
			if (medicalRecordsIds.isEmpty()) {
				throw new BusinessException("No medical records found for the person.");
			}
			return medicalRecordRepositoryOutputPort.getMedicalRecordsByPersonId(person.getId(), pageable);
		} else {
			throw new BusinessException(Errors.PERSON_NOT_FOUND);
		}
	}

	/**
	 * Modifies an existing medical record.
	 *
	 * @param medicalRecord the medical record to modify
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public void modifyMedicalRecord(MedicalRecord medicalRecord) throws BusinessException {
		log.debug("modifyMedicalRecord");

		Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepositoryOutputPort
				.getMedicalRecordsById(medicalRecord.getId());
		if (!foundMedicalRecord.isPresent()) {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}
		medicalRecordRepositoryOutputPort.modifyMedicalRecord(medicalRecord);
	}

	/**
	 * Partially modifies an existing medical record.
	 *
	 * @param medicalRecord the medical record to modify
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public void modifyPartialMedicalRecord(MedicalRecord medicalRecord) throws BusinessException {
		log.debug("modifyPartialMedicalRecord");

		Optional<MedicalRecord> foundMedicalRecord = medicalRecordRepositoryOutputPort
				.getMedicalRecordsById(medicalRecord.getId());
		if (!foundMedicalRecord.isPresent()) {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}

		MedicalRecord updated = foundMedicalRecord.get();
		medicalPatchMapper.update(updated, medicalRecord);
		medicalRecordRepositoryOutputPort.modifyMedicalRecord(updated);
	}

	/**
	 * Deletes a medical record by its ID.
	 *
	 * @param id the medical record ID
	 * @throws BusinessException if there is a business exception
	 */
	@Transactional
	public void deleteMedicalRecord(String id) throws BusinessException {
		log.debug("deleteMedicalRecord");
		Optional<MedicalRecord> foundAppointment = medicalRecordRepositoryOutputPort.getMedicalRecordsById(id);
		if (!foundAppointment.isPresent()) {
			throw new BusinessException(Errors.MEDICAL_RECORD_NOT_FOUND);
		}
		medicalRecordRepositoryOutputPort.deleteMedicalRecord(id);
	}

}
