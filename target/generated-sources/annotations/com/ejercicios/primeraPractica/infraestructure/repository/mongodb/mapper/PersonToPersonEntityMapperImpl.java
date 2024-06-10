package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;
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
public class PersonToPersonEntityMapperImpl implements PersonToPersonEntityMapper {

    @Override
    public PersonEntity fromInputToOutput(Person input) {
        if ( input == null ) {
            return null;
        }

        PersonEntity.PersonEntityBuilder personEntity = PersonEntity.builder();

        List<String> list = input.getAppointmentId();
        if ( list != null ) {
            personEntity.appointmentId( new ArrayList<String>( list ) );
        }
        personEntity.id( input.getId() );
        List<String> list1 = input.getMedicalRecordId();
        if ( list1 != null ) {
            personEntity.medicalRecordId( new ArrayList<String>( list1 ) );
        }
        personEntity.persoInfo( input.getPersoInfo() );
        personEntity.personType( input.getPersonType() );

        return personEntity.build();
    }

    @Override
    public Person fromOutputToInput(PersonEntity output) {
        if ( output == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        List<String> list = output.getAppointmentId();
        if ( list != null ) {
            person.appointmentId( new ArrayList<String>( list ) );
        }
        person.id( output.getId() );
        List<String> list1 = output.getMedicalRecordId();
        if ( list1 != null ) {
            person.medicalRecordId( new ArrayList<String>( list1 ) );
        }
        person.persoInfo( output.getPersoInfo() );
        person.personType( output.getPersonType() );

        return person.build();
    }

    @Override
    public List<PersonEntity> fromInputToOutput(List<Person> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PersonEntity> list = new ArrayList<PersonEntity>( inputList.size() );
        for ( Person person : inputList ) {
            list.add( fromInputToOutput( person ) );
        }

        return list;
    }

    @Override
    public List<Person> fromOutputToInput(List<PersonEntity> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Person> list = new ArrayList<Person>( outputList.size() );
        for ( PersonEntity personEntity : outputList ) {
            list.add( fromOutputToInput( personEntity ) );
        }

        return list;
    }
}
