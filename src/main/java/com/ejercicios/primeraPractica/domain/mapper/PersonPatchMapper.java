package com.ejercicios.primeraPractica.domain.mapper;

import org.mapstruct.MappingTarget;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;

public interface PersonPatchMapper {
	void update(@MappingTarget Person output, Person input);

	void update(@MappingTarget PersonalInfo output, PersonalInfo input);
}
