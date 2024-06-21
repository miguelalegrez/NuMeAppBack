package com.ejercicios.primeraPractica.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PatchPersonDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PersonPatchMapper {
	void update(@MappingTarget Person output, Person input);

	void update(@MappingTarget PersonalInfo output, PersonalInfo input);
}
