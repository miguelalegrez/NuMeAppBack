package com.ejercicios.primeraPractica.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordPatchMapper {

	void update(@MappingTarget MedicalRecord output, MedicalRecord input);

}
