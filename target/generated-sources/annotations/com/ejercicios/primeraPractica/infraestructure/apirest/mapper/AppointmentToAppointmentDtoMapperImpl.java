package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.AppointmentDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-09T12:46:48+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentToAppointmentDtoMapperImpl implements AppointmentToAppointmentDtoMapper {

    @Override
    public AppointmentDto fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        AppointmentDto.AppointmentDtoBuilder appointmentDto = AppointmentDto.builder();

        appointmentDto.date( input.getDate() );
        appointmentDto.nutritionistId( input.getNutritionistId() );
        appointmentDto.patientId( input.getPatientId() );

        return appointmentDto.build();
    }

    @Override
    public Appointment fromOutputToInput(AppointmentDto output) {
        if ( output == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setDate( output.getDate() );
        appointment.setNutritionistId( output.getNutritionistId() );
        appointment.setPatientId( output.getPatientId() );

        return appointment;
    }

    @Override
    public List<AppointmentDto> fromInputToOutput(List<Appointment> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<AppointmentDto> list = new ArrayList<AppointmentDto>( inputList.size() );
        for ( Appointment appointment : inputList ) {
            list.add( fromInputToOutput( appointment ) );
        }

        return list;
    }

    @Override
    public List<Appointment> fromOutputToInput(List<AppointmentDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( outputList.size() );
        for ( AppointmentDto appointmentDto : outputList ) {
            list.add( fromOutputToInput( appointmentDto ) );
        }

        return list;
    }
}