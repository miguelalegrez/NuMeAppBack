package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ejercicios.primeraPractica.application.port.output.MedicalRecordRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.MedicalRecordEntity;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper.MedicalRecordToMedicalRecordEntityMapper;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MedicalRecordRepositoryService implements MedicalRecordRepositoryOutputPort {

	@Autowired
	MedicalRecordRepository medicalRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	MedicalRecordToMedicalRecordEntityMapper medicalRecordEntityMapper;

	@Override
	@Cacheable(value = "medicalRecords", key = "#pageable")
	public Page<MedicalRecord> getMedicalRecords(@Valid Pageable pageable) {
		log.debug("getMedicalRecords");

		Page<MedicalRecordEntity> medicalRecords = medicalRepository.findAll(pageable);
		return medicalRecordEntityMapper.fromOutputToInput(medicalRecords);
	}

	@Override
	public Optional<MedicalRecord> getMedicalRecordsById(String id) {
		log.debug("getMedicalRecordById");
		Optional<MedicalRecordEntity> medicalRecordsbyId = medicalRepository.findByIdAndEliminado(id, false);

		return medicalRecordEntityMapper.fromOutputToInput(medicalRecordsbyId);

	}

	@Override
	@Cacheable(value = "medicalRecords", key = "#id")
	public Page<MedicalRecord> getMedicalRecordsByPersonId(@Valid String id, Pageable pageable)
			throws BusinessException {
		log.debug("getMedicalRecordByPersonId");
		Optional<PersonEntity> personOpt = personRepository.findByIdAndEliminado(id, false);
		if (personOpt.isPresent()) {
			PersonEntity person = personOpt.get();
			List<String> medicalRecordsIds = person.getMedicalRecordId();

			Page<MedicalRecordEntity> medicalRecordEntities = medicalRepository.findById(medicalRecordsIds, false,
					pageable);
			return medicalRecordEntities.map(medicalRecordEntityMapper::fromOutputToInput);
		}

		throw new BusinessException(Errors.PERSON_NOT_FOUND);
	}

	@Override
	@CacheEvict(value = "medicalRecords", allEntries = true)
	public MedicalRecord addMedicalRecord(@Valid MedicalRecord medicalRecord) {

		log.debug("addMedicalRecord");

		MedicalRecordEntity medicalRecordEntity = medicalRecordEntityMapper.fromInputToOutput(medicalRecord);
		medicalRecordEntity.setEliminado(false);

		MedicalRecordEntity savedMedicalRecordEntity = medicalRepository.save(medicalRecordEntity);

		// Convertir la entidad guardada de nuevo a Medical Record
		return medicalRecordEntityMapper.fromOutputToInput(savedMedicalRecordEntity);
	}

	@Override
	public void modifyMedicalRecord(@Valid MedicalRecord medicalRecord) {
		log.debug("modifyMedicalRecord");

		MedicalRecordEntity medicalRecordEntity = medicalRecordEntityMapper.fromInputToOutput(medicalRecord);
		medicalRepository.save(medicalRecordEntity);
	}

	@Override
	public void deleteMedicalRecord(@Valid String id) {
		log.debug("deleteMedicalRecord");

		Optional<MedicalRecordEntity> medicalEntityOpt = medicalRepository.findByIdAndEliminado(id, false);
		if (medicalEntityOpt.isPresent()) {
			MedicalRecordEntity medicalRecordEntity = medicalEntityOpt.get();
			medicalRecordEntity.setEliminado(true);
			medicalRepository.save(medicalRecordEntity);
		}
	}

}
