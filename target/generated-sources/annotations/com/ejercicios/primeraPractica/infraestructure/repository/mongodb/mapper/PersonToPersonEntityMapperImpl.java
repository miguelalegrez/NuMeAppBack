package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.PersonEntity;
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
public class PersonToPersonEntityMapperImpl implements PersonToPersonEntityMapper {

    @Override
    public PersonEntity fromInputToOutput(Person input) {
        if ( input == null ) {
            return null;
        }

        PersonEntity.PersonEntityBuilder personEntity = PersonEntity.builder();

        personEntity.id( input.getId() );
        personEntity.personType( input.getPersonType() );
        personEntity.persoInfo( input.getPersoInfo() );
        List<String> list = input.getAppointmentId();
        if ( list != null ) {
            personEntity.appointmentId( new ArrayList<String>( list ) );
        }
        List<String> list1 = input.getMedicalRecordId();
        if ( list1 != null ) {
            personEntity.medicalRecordId( new ArrayList<String>( list1 ) );
        }

        return personEntity.build();
    }

    @Override
    public Person fromOutputToInput(PersonEntity output) {
        if ( output == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.id( output.getId() );
        person.personType( output.getPersonType() );
        person.persoInfo( output.getPersoInfo() );
        List<String> list = output.getAppointmentId();
        if ( list != null ) {
            person.appointmentId( new ArrayList<String>( list ) );
        }
        List<String> list1 = output.getMedicalRecordId();
        if ( list1 != null ) {
            person.medicalRecordId( new ArrayList<String>( list1 ) );
        }

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
