package com.ejercicios.primeraPractica.domain.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-01T09:17:10+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AppointmentPatchMapperImpl implements AppointmentPatchMapper {

    @Override
    public void update(Appointment output, Appointment input) {
        if ( input == null ) {
            return;
        }

        if ( input.getId() != null ) {
            output.setId( input.getId() );
        }
        if ( input.getDate() != null ) {
            output.setDate( input.getDate() );
        }
        if ( input.getPatientId() != null ) {
            output.setPatientId( input.getPatientId() );
        }
        if ( input.getPatientName() != null ) {
            output.setPatientName( input.getPatientName() );
        }
        if ( input.getPatientSurname() != null ) {
            output.setPatientSurname( input.getPatientSurname() );
        }
        if ( input.getPatientDocument() != null ) {
            output.setPatientDocument( input.getPatientDocument() );
        }
        if ( input.getNutritionistId() != null ) {
            output.setNutritionistId( input.getNutritionistId() );
        }
        if ( input.getNutritionistName() != null ) {
            output.setNutritionistName( input.getNutritionistName() );
        }
        if ( input.getNutritionistSurname() != null ) {
            output.setNutritionistSurname( input.getNutritionistSurname() );
        }
        if ( input.getNutritionistDocument() != null ) {
            output.setNutritionistDocument( input.getNutritionistDocument() );
        }
    }
}
