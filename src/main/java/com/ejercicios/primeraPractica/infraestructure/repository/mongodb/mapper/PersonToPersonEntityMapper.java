package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.mapper.BaseMapper;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonToPersonEntityMapper extends BaseMapper<Person, PersonEntity> {

}
