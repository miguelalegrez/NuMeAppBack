package com.ejercicios.primeraPractica.application.port.output;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;

import jakarta.validation.Valid;

public interface MedicalRecordRepositoryOutputPort {

	Page<MedicalRecord> getMedicalRecords(@Valid Pageable pageable);

	Page<MedicalRecord> getMedicalRecordsByPersonId(String personId, Pageable pageable) throws BusinessException;

	Optional<MedicalRecord> getMedicalRecordsById(@Valid String id);

	MedicalRecord addMedicalRecord(@Valid MedicalRecord medicalRecord);

	void modifyMedicalRecord(@Valid MedicalRecord medicalRecord);

	void deleteMedicalRecord(@Valid String id);
}
