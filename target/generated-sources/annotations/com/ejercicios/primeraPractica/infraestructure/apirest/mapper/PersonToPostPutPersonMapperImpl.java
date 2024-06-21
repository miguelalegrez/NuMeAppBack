package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutPersonDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T08:04:02+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PersonToPostPutPersonMapperImpl implements PersonToPostPutPersonMapper {

    @Override
    public PostPutPersonDto fromInputToOutput(Person input) {
        if ( input == null ) {
            return null;
        }

        PostPutPersonDto.PostPutPersonDtoBuilder postPutPersonDto = PostPutPersonDto.builder();

        List<String> list = input.getAppointmentId();
        if ( list != null ) {
            postPutPersonDto.appointmentId( new ArrayList<String>( list ) );
        }
        List<String> list1 = input.getMedicalRecordId();
        if ( list1 != null ) {
            postPutPersonDto.medicalRecordId( new ArrayList<String>( list1 ) );
        }
        postPutPersonDto.persoInfo( input.getPersoInfo() );
        postPutPersonDto.personType( input.getPersonType() );

        return postPutPersonDto.build();
    }

    @Override
    public Person fromOutputToInput(PostPutPersonDto output) {
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
    public List<PostPutPersonDto> fromInputToOutput(List<Person> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PostPutPersonDto> list = new ArrayList<PostPutPersonDto>( inputList.size() );
        for ( Person person : inputList ) {
            list.add( fromInputToOutput( person ) );
        }

        return list;
    }

    @Override
    public List<Person> fromOutputToInput(List<PostPutPersonDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Person> list = new ArrayList<Person>( outputList.size() );
        for ( PostPutPersonDto postPutPersonDto : outputList ) {
            list.add( fromOutputToInput( postPutPersonDto ) );
        }

        return list;
    }
}
