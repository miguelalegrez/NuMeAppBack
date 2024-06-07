package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-06T20:25:15+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentToAppointmentEntityMapperImpl implements AppointmentToAppointmentEntityMapper {

    @Override
    public AppointmentEntity fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        AppointmentEntity.AppointmentEntityBuilder appointmentEntity = AppointmentEntity.builder();

        appointmentEntity.date( input.getDate() );
        appointmentEntity.id( input.getId() );
        appointmentEntity.nutritionistId( input.getNutritionistId() );
        appointmentEntity.patientId( input.getPatientId() );

        return appointmentEntity.build();
    }

    @Override
    public Appointment fromOutputToInput(AppointmentEntity output) {
        if ( output == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        appointment.setDate( output.getDate() );
        appointment.setId( output.getId() );
        appointment.setNutritionistId( output.getNutritionistId() );
        appointment.setPatientId( output.getPatientId() );

        return appointment;
    }

    @Override
    public List<AppointmentEntity> fromInputToOutput(List<Appointment> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<AppointmentEntity> list = new ArrayList<AppointmentEntity>( inputList.size() );
        for ( Appointment appointment : inputList ) {
            list.add( fromInputToOutput( appointment ) );
        }

        return list;
    }

    @Override
    public List<Appointment> fromOutputToInput(List<AppointmentEntity> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( outputList.size() );
        for ( AppointmentEntity appointmentEntity : outputList ) {
            list.add( fromOutputToInput( appointmentEntity ) );
        }

        return list;
    }
}
