package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.MedicalRecordEntity;
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
public class MedicalRecordToMedicalRecordEntityMapperImpl implements MedicalRecordToMedicalRecordEntityMapper {

    @Override
    public MedicalRecordEntity fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        MedicalRecordEntity.MedicalRecordEntityBuilder medicalRecordEntity = MedicalRecordEntity.builder();

        medicalRecordEntity.id( input.getId() );
        medicalRecordEntity.date( input.getDate() );
        medicalRecordEntity.registryType( input.getRegistryType() );
        medicalRecordEntity.observations( input.getObservations() );
        medicalRecordEntity.patientId( input.getPatientId() );

        return medicalRecordEntity.build();
    }

    @Override
    public MedicalRecord fromOutputToInput(MedicalRecordEntity output) {
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
    public List<MedicalRecordEntity> fromInputToOutput(List<MedicalRecord> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<MedicalRecordEntity> list = new ArrayList<MedicalRecordEntity>( inputList.size() );
        for ( MedicalRecord medicalRecord : inputList ) {
            list.add( fromInputToOutput( medicalRecord ) );
        }

        return list;
    }

    @Override
    public List<MedicalRecord> fromOutputToInput(List<MedicalRecordEntity> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<MedicalRecord> list = new ArrayList<MedicalRecord>( outputList.size() );
        for ( MedicalRecordEntity medicalRecordEntity : outputList ) {
            list.add( fromOutputToInput( medicalRecordEntity ) );
        }

        return list;
    }
}
