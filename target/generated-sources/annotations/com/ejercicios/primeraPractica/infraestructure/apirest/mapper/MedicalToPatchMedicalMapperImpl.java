package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.MedicalRecordPatchDto;
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
public class MedicalToPatchMedicalMapperImpl implements MedicalToPatchMedicalMapper {

    @Override
    public MedicalRecordPatchDto fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        MedicalRecordPatchDto.MedicalRecordPatchDtoBuilder medicalRecordPatchDto = MedicalRecordPatchDto.builder();

        medicalRecordPatchDto.date( input.getDate() );
        medicalRecordPatchDto.patientId( input.getPatientId() );
        medicalRecordPatchDto.registryType( input.getRegistryType() );
        medicalRecordPatchDto.observations( input.getObservations() );

        return medicalRecordPatchDto.build();
    }

    @Override
    public MedicalRecord fromOutputToInput(MedicalRecordPatchDto output) {
        if ( output == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setDate( output.getDate() );
        medicalRecord.setRegistryType( output.getRegistryType() );
        medicalRecord.setObservations( output.getObservations() );
        medicalRecord.setPatientId( output.getPatientId() );

        return medicalRecord;
    }

    @Override
    public List<MedicalRecordPatchDto> fromInputToOutput(List<MedicalRecord> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<MedicalRecordPatchDto> list = new ArrayList<MedicalRecordPatchDto>( inputList.size() );
        for ( MedicalRecord medicalRecord : inputList ) {
            list.add( fromInputToOutput( medicalRecord ) );
        }

        return list;
    }

    @Override
    public List<MedicalRecord> fromOutputToInput(List<MedicalRecordPatchDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<MedicalRecord> list = new ArrayList<MedicalRecord>( outputList.size() );
        for ( MedicalRecordPatchDto medicalRecordPatchDto : outputList ) {
            list.add( fromOutputToInput( medicalRecordPatchDto ) );
        }

        return list;
    }
}
