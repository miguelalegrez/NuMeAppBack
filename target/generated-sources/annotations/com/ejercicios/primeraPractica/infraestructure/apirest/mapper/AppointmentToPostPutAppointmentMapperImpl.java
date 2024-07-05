package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutAppointmentDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-05T09:17:41+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentToPostPutAppointmentMapperImpl implements AppointmentToPostPutAppointmentMapper {

    @Override
    public PostPutAppointmentDto fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        PostPutAppointmentDto.PostPutAppointmentDtoBuilder postPutAppointmentDto = PostPutAppointmentDto.builder();

        postPutAppointmentDto.date( input.getDate() );
        postPutAppointmentDto.nutritionistDocument( input.getNutritionistDocument() );
        postPutAppointmentDto.nutritionistId( input.getNutritionistId() );
        postPutAppointmentDto.nutritionistName( input.getNutritionistName() );
        postPutAppointmentDto.patientDocument( input.getPatientDocument() );
        postPutAppointmentDto.patientId( input.getPatientId() );
        postPutAppointmentDto.patientName( input.getPatientName() );
        postPutAppointmentDto.patientSurname( input.getPatientSurname() );

        return postPutAppointmentDto.build();
    }

    @Override
    public List<PostPutAppointmentDto> fromInputToOutput(List<Appointment> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PostPutAppointmentDto> list = new ArrayList<PostPutAppointmentDto>( inputList.size() );
        for ( Appointment appointment : inputList ) {
            list.add( fromInputToOutput( appointment ) );
        }

        return list;
    }

    @Override
    public Appointment fromOutputToInput(PostPutAppointmentDto output) {
        if ( output == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setDate( output.getDate() );
        appointment.setNutritionistDocument( output.getNutritionistDocument() );
        appointment.setNutritionistId( output.getNutritionistId() );
        appointment.setNutritionistName( output.getNutritionistName() );
        appointment.setPatientDocument( output.getPatientDocument() );
        appointment.setPatientId( output.getPatientId() );
        appointment.setPatientName( output.getPatientName() );
        appointment.setPatientSurname( output.getPatientSurname() );

        return appointment;
    }

    @Override
    public List<Appointment> fromOutputToInput(List<PostPutAppointmentDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( outputList.size() );
        for ( PostPutAppointmentDto postPutAppointmentDto : outputList ) {
            list.add( fromOutputToInput( postPutAppointmentDto ) );
        }

        return list;
    }
}
