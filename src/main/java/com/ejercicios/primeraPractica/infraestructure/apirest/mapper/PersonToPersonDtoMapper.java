package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import org.mapstruct.Mapper;

import com.ejercicios.primeraPractica.domain.mapper.BaseMapper;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.PersonDto;

@Mapper(componentModel = "spring")
public interface PersonToPersonDtoMapper extends BaseMapper<Person, PersonDto> {

}
