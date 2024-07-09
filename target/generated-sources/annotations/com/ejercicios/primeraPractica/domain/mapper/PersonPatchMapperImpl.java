package com.ejercicios.primeraPractica.domain.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonalInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-09T11:57:09+0200",
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
        }
        else {
            List<String> list = input.getAppointmentId();
            if ( list != null ) {
                output.setAppointmentId( new ArrayList<String>( list ) );
            }
        }
        if ( input.getId() != null ) {
            output.setId( input.getId() );
        }
        if ( output.getMedicalRecordId() != null ) {
            List<String> list1 = input.getMedicalRecordId();
            if ( list1 != null ) {
                output.getMedicalRecordId().clear();
                output.getMedicalRecordId().addAll( list1 );
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
                output.setPersoInfo( PersonalInfo.builder().build() );
            }
            update( output.getPersoInfo(), input.getPersoInfo() );
        }
        if ( input.getPersonType() != null ) {
            output.setPersonType( input.getPersonType() );
        }
    }

    @Override
    public void update(PersonalInfo output, PersonalInfo input) {
        if ( input == null ) {
            return;
        }

        if ( input.getDocument() != null ) {
            output.setDocument( input.getDocument() );
        }
        if ( input.getDocumentType() != null ) {
            output.setDocumentType( input.getDocumentType() );
        }
        if ( input.getName() != null ) {
            output.setName( input.getName() );
        }
        if ( input.getSurname() != null ) {
            output.setSurname( input.getSurname() );
        }
    }
}
