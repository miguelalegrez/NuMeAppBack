package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.mapper.BaseMapper;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutPersonDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonToPostPutPersonMapper extends BaseMapper<Person, PostPutPersonDto> {

}
