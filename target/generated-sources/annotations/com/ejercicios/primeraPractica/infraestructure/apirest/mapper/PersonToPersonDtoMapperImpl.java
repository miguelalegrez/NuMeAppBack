package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.PersonDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-09T12:46:47+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PersonToPersonDtoMapperImpl implements PersonToPersonDtoMapper {

    @Override
    public PersonDto fromInputToOutput(Person input) {
        if ( input == null ) {
            return null;
        }

        PersonDto.PersonDtoBuilder personDto = PersonDto.builder();

        List<String> list = input.getAppointmentId();
        if ( list != null ) {
            personDto.appointmentId( new ArrayList<String>( list ) );
        }
        List<String> list1 = input.getMedicalRecordId();
        if ( list1 != null ) {
            personDto.medicalRecordId( new ArrayList<String>( list1 ) );
        }
        personDto.persoInfo( input.getPersoInfo() );
        personDto.personType( input.getPersonType() );

        return personDto.build();
    }

    @Override
    public Person fromOutputToInput(PersonDto output) {
        if ( output == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        List<String> list = output.getAppointmentId();
        if ( list != null ) {
            person.appointmentId( new ArrayList<String>( list ) );
        }
        List<String> list1 = output.getMedicalRecordId();
        if ( list1 != null ) {
            person.medicalRecordId( new ArrayList<String>( list1 ) );
        }
        person.persoInfo( output.getPersoInfo() );
        person.personType( output.getPersonType() );

        return person.build();
    }

    @Override
    public List<PersonDto> fromInputToOutput(List<Person> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PersonDto> list = new ArrayList<PersonDto>( inputList.size() );
        for ( Person person : inputList ) {
            list.add( fromInputToOutput( person ) );
        }

        return list;
    }

    @Override
    public List<Person> fromOutputToInput(List<PersonDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Person> list = new ArrayList<Person>( outputList.size() );
        for ( PersonDto personDto : outputList ) {
            list.add( fromOutputToInput( personDto ) );
        }

        return list;
    }
}
