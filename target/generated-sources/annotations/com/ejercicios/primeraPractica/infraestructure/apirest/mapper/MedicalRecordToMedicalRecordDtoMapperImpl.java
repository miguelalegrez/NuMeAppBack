package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.response.MedicalRecordDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-25T09:21:57+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MedicalRecordToMedicalRecordDtoMapperImpl implements MedicalRecordToMedicalRecordDtoMapper {

    @Override
    public MedicalRecordDto fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        MedicalRecordDto.MedicalRecordDtoBuilder medicalRecordDto = MedicalRecordDto.builder();

        medicalRecordDto.date( input.getDate() );
        medicalRecordDto.id( input.getId() );
        medicalRecordDto.observations( input.getObservations() );
        medicalRecordDto.patientId( input.getPatientId() );
        medicalRecordDto.registryType( input.getRegistryType() );

        return medicalRecordDto.build();
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
    public MedicalRecord fromOutputToInput(MedicalRecordDto output) {
        if ( output == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setDate( output.getDate() );
        medicalRecord.setId( output.getId() );
        medicalRecord.setObservations( output.getObservations() );
        medicalRecord.setPatientId( output.getPatientId() );
        medicalRecord.setRegistryType( output.getRegistryType() );

        return medicalRecord;
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
