package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutAppointmentDto;
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
public class AppointmentToPostPutAppointmentMapperImpl implements AppointmentToPostPutAppointmentMapper {

    @Override
    public PostPutAppointmentDto fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        PostPutAppointmentDto postPutAppointmentDto = new PostPutAppointmentDto();

        postPutAppointmentDto.setDate( input.getDate() );
        postPutAppointmentDto.setNutritionistId( input.getNutritionistId() );
        postPutAppointmentDto.setPatientId( input.getPatientId() );

        return postPutAppointmentDto;
    }

    @Override
    public Appointment fromOutputToInput(PostPutAppointmentDto output) {
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
