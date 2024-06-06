package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ejercicios.primeraPractica.domain.mapper.BaseMapper;
import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentToAppointmentEntityMapper extends BaseMapper<Appointment, AppointmentEntity> {

}
