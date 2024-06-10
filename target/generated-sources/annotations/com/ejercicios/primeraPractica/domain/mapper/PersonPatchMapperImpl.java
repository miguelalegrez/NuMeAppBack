package com.ejercicios.primeraPractica.domain.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;
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
public class PersonPatchMapperImpl implements PersonPatchMapper {

    @Override
    public void update(Person output, Person input) {
        if ( input == null ) {
            return;
        }

        if ( output.getAppointmentId() != null ) {
            List<String> list = input.getAppointmentId();
            if ( list != null ) {
                output.getAppointmentId().clear();
                output.getAppointmentId().addAll( list );
            }
            else {
                output.setAppointmentId( null );
            }
        }
        else {
            List<String> list = input.getAppointmentId();
            if ( list != null ) {
                output.setAppointmentId( new ArrayList<String>( list ) );
            }
        }
        output.setId( input.getId() );
        if ( output.getMedicalRecordId() != null ) {
            List<String> list1 = input.getMedicalRecordId();
            if ( list1 != null ) {
                output.getMedicalRecordId().clear();
                output.getMedicalRecordId().addAll( list1 );
            }
            else {
                output.setMedicalRecordId( null );
            }
        }
        else {
            List<String> list1 = input.getMedicalRecordId();
            if ( list1 != null ) {
                output.setMedicalRecordId( new ArrayList<String>( list1 ) );
            }
        }
        if ( input.getPersoInfo() != null ) {
            if ( output.getPersoInfo() == null ) {
                output.setPersoInfo( new PersonalInfo() );
            }
            update( output.getPersoInfo(), input.getPersoInfo() );
        }
        else {
            output.setPersoInfo( null );
        }
        output.setPersonType( input.getPersonType() );
    }

    @Override
    public void update(PersonalInfo output, PersonalInfo input) {
        if ( input == null ) {
            return;
        }

        output.setDocument( input.getDocument() );
        output.setDocumentType( input.getDocumentType() );
        output.setName( input.getName() );
        output.setSurname( input.getSurname() );
    }
}
