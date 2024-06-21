package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.mapper.BaseMapper;
import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.MedicalRecordDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordToMedicalRecordDtoMapper extends BaseMapper<MedicalRecord, MedicalRecordDto> {

}
