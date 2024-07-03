package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PatchPersonDto;
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
public class PersonToPatchPersonMapperImpl implements PersonToPatchPersonMapper {

    @Override
    public PatchPersonDto fromInputToOutput(Person input) {
        if ( input == null ) {
            return null;
        }

        PatchPersonDto.PatchPersonDtoBuilder patchPersonDto = PatchPersonDto.builder();

        patchPersonDto.persoInfo( input.getPersoInfo() );

        return patchPersonDto.build();
    }

    @Override
    public Person fromOutputToInput(PatchPersonDto output) {
        if ( output == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.persoInfo( output.getPersoInfo() );

        return person.build();
    }

    @Override
    public List<PatchPersonDto> fromInputToOutput(List<Person> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PatchPersonDto> list = new ArrayList<PatchPersonDto>( inputList.size() );
        for ( Person person : inputList ) {
            list.add( fromInputToOutput( person ) );
        }

        return list;
    }

    @Override
    public List<Person> fromOutputToInput(List<PatchPersonDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Person> list = new ArrayList<Person>( outputList.size() );
        for ( PatchPersonDto patchPersonDto : outputList ) {
            list.add( fromOutputToInput( patchPersonDto ) );
        }

        return list;
    }
}
