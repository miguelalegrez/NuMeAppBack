package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.AppointmentDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-01T09:17:10+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AppointmentToAppointmentDtoMapperImpl implements AppointmentToAppointmentDtoMapper {

    @Override
    public AppointmentDto fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        AppointmentDto.AppointmentDtoBuilder appointmentDto = AppointmentDto.builder();

        appointmentDto.id( input.getId() );
        appointmentDto.date( input.getDate() );
        appointmentDto.patientId( input.getPatientId() );
        appointmentDto.patientName( input.getPatientName() );
        appointmentDto.patientSurname( input.getPatientSurname() );
        appointmentDto.patientDocument( input.getPatientDocument() );
        appointmentDto.nutritionistId( input.getNutritionistId() );
        appointmentDto.nutritionistName( input.getNutritionistName() );
        appointmentDto.nutritionistSurname( input.getNutritionistSurname() );
        appointmentDto.nutritionistDocument( input.getNutritionistDocument() );

        return appointmentDto.build();
    }

    @Override
    public Appointment fromOutputToInput(AppointmentDto output) {
        if ( output == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setId( output.getId() );
        appointment.setDate( output.getDate() );
        appointment.setPatientId( output.getPatientId() );
        appointment.setPatientName( output.getPatientName() );
        appointment.setPatientSurname( output.getPatientSurname() );
        appointment.setPatientDocument( output.getPatientDocument() );
        appointment.setNutritionistId( output.getNutritionistId() );
        appointment.setNutritionistName( output.getNutritionistName() );
        appointment.setNutritionistSurname( output.getNutritionistSurname() );
        appointment.setNutritionistDocument( output.getNutritionistDocument() );

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
