package com.ejercicios.primeraPractica.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonPatchMapper {
	void update(@MappingTarget Person output, Person input);

	void update(@MappingTarget PersonalInfo output, PersonalInfo input);
}
