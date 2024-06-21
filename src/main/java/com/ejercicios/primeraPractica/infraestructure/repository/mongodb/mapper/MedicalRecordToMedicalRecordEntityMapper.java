package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.mapper.BaseMapper;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.MedicalRecordEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordToMedicalRecordEntityMapper extends BaseMapper<MedicalRecord, MedicalRecordEntity> {

}
