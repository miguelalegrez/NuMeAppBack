package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.AppointmentEntity;
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
public class AppointmentToAppointmentEntityMapperImpl implements AppointmentToAppointmentEntityMapper {

    @Override
    public AppointmentEntity fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        AppointmentEntity.AppointmentEntityBuilder appointmentEntity = AppointmentEntity.builder();

        appointmentEntity.id( input.getId() );
        appointmentEntity.date( input.getDate() );
        appointmentEntity.patientId( input.getPatientId() );
        appointmentEntity.patientName( input.getPatientName() );
        appointmentEntity.patientSurname( input.getPatientSurname() );
        appointmentEntity.patientDocument( input.getPatientDocument() );
        appointmentEntity.nutritionistName( input.getNutritionistName() );
        appointmentEntity.nutritionistSurname( input.getNutritionistSurname() );
        appointmentEntity.nutritionistDocument( input.getNutritionistDocument() );
        appointmentEntity.nutritionistId( input.getNutritionistId() );

        return appointmentEntity.build();
    }

    @Override
    public Appointment fromOutputToInput(AppointmentEntity output) {
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
