package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.MedicalRecordDto;
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
public class MedicalRecordToMedicalRecordDtoMapperImpl implements MedicalRecordToMedicalRecordDtoMapper {

    @Override
    public MedicalRecordDto fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        MedicalRecordDto.MedicalRecordDtoBuilder medicalRecordDto = MedicalRecordDto.builder();

        medicalRecordDto.id( input.getId() );
        medicalRecordDto.date( input.getDate() );
        medicalRecordDto.registryType( input.getRegistryType() );
        medicalRecordDto.observations( input.getObservations() );
        medicalRecordDto.patientId( input.getPatientId() );

        return medicalRecordDto.build();
    }

    @Override
    public MedicalRecord fromOutputToInput(MedicalRecordDto output) {
        if ( output == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setId( output.getId() );
        medicalRecord.setDate( output.getDate() );
        medicalRecord.setRegistryType( output.getRegistryType() );
        medicalRecord.setObservations( output.getObservations() );
        medicalRecord.setPatientId( output.getPatientId() );

        return medicalRecord;
    }

    @Override
    public List<MedicalRecordDto> fromInputToOutput(List<MedicalRecord> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<MedicalRecordDto> list = new ArrayList<MedicalRecordDto>( inputList.size() );
        for ( MedicalRecord medicalRecord : inputList ) {
            list.add( fromInputToOutput( medicalRecord ) );
        }

        return list;
    }

    @Override
    public List<MedicalRecord> fromOutputToInput(List<MedicalRecordDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<MedicalRecord> list = new ArrayList<MedicalRecord>( outputList.size() );
        for ( MedicalRecordDto medicalRecordDto : outputList ) {
            list.add( fromOutputToInput( medicalRecordDto ) );
        }

        return list;
    }
}
