package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PostPutMedicalRecordDto;
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
public class MedicalRecordToPostPutMedicalRecordMapperImpl implements MedicalRecordToPostPutMedicalRecordMapper {

    @Override
    public PostPutMedicalRecordDto fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        PostPutMedicalRecordDto.PostPutMedicalRecordDtoBuilder postPutMedicalRecordDto = PostPutMedicalRecordDto.builder();

        postPutMedicalRecordDto.registryType( input.getRegistryType() );
        postPutMedicalRecordDto.observations( input.getObservations() );
        postPutMedicalRecordDto.patientId( input.getPatientId() );

        return postPutMedicalRecordDto.build();
    }

    @Override
    public MedicalRecord fromOutputToInput(PostPutMedicalRecordDto output) {
        if ( output == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setRegistryType( output.getRegistryType() );
        medicalRecord.setObservations( output.getObservations() );
        medicalRecord.setPatientId( output.getPatientId() );

        return medicalRecord;
    }

    @Override
    public List<PostPutMedicalRecordDto> fromInputToOutput(List<MedicalRecord> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PostPutMedicalRecordDto> list = new ArrayList<PostPutMedicalRecordDto>( inputList.size() );
        for ( MedicalRecord medicalRecord : inputList ) {
            list.add( fromInputToOutput( medicalRecord ) );
        }

        return list;
    }

    @Override
    public List<MedicalRecord> fromOutputToInput(List<PostPutMedicalRecordDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<MedicalRecord> list = new ArrayList<MedicalRecord>( outputList.size() );
        for ( PostPutMedicalRecordDto postPutMedicalRecordDto : outputList ) {
            list.add( fromOutputToInput( postPutMedicalRecordDto ) );
        }

        return list;
    }
}
