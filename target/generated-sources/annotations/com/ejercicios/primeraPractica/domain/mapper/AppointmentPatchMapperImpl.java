package com.ejercicios.primeraPractica.domain.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T08:04:02+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class AppointmentPatchMapperImpl implements AppointmentPatchMapper {

    @Override
    public void update(Appointment output, Appointment input) {
        if ( input == null ) {
            return;
        }

        if ( input.getDate() != null ) {
            output.setDate( input.getDate() );
        }
        if ( input.getId() != null ) {
            output.setId( input.getId() );
        }
        if ( input.getNutritionistDocument() != null ) {
            output.setNutritionistDocument( input.getNutritionistDocument() );
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
        if ( input.getPatientDocument() != null ) {
            output.setPatientDocument( input.getPatientDocument() );
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
    }
}
