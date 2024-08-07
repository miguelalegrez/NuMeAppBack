package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.MedicalRecordPatchDto;
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
public class MedicalToPatchMedicalMapperImpl implements MedicalToPatchMedicalMapper {

    @Override
    public MedicalRecordPatchDto fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        MedicalRecordPatchDto.MedicalRecordPatchDtoBuilder medicalRecordPatchDto = MedicalRecordPatchDto.builder();

        medicalRecordPatchDto.date( input.getDate() );
        medicalRecordPatchDto.observations( input.getObservations() );
        medicalRecordPatchDto.patientId( input.getPatientId() );
        medicalRecordPatchDto.registryType( input.getRegistryType() );

        return medicalRecordPatchDto.build();
    }

    @Override
    public MedicalRecord fromOutputToInput(MedicalRecordPatchDto output) {
        if ( output == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setDate( output.getDate() );
        medicalRecord.setObservations( output.getObservations() );
        medicalRecord.setPatientId( output.getPatientId() );
        medicalRecord.setRegistryType( output.getRegistryType() );

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
