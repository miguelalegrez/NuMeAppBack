package com.ejercicios.primeraPractica.application.port.input;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;

public interface MedicalRecordServiceInputPort {

	public Page<MedicalRecord> getMedicalRecord(Pageable pageable) throws BusinessException;

	public String addMedicalRecord(MedicalRecord medicalRecord) throws BusinessException;

	public Optional<MedicalRecord> getMedicalRecordById(String id) throws BusinessException;

	public void modifyMedicalRecord(MedicalRecord medicalRecord) throws BusinessException;

	public void modifyPartialMedicalRecord(MedicalRecord medicalRecord) throws BusinessException;

	public void deleteMedicalRecord(String id) throws BusinessException;
}
