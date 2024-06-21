package com.ejercicios.primeraPractica.infraestructure.repository.mongodb.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import com.ejercicios.primeraPractica.infraestructure.repository.mongodb.entity.MedicalRecordEntity;
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
public class MedicalRecordToMedicalRecordEntityMapperImpl implements MedicalRecordToMedicalRecordEntityMapper {

    @Override
    public MedicalRecordEntity fromInputToOutput(MedicalRecord input) {
        if ( input == null ) {
            return null;
        }

        MedicalRecordEntity.MedicalRecordEntityBuilder medicalRecordEntity = MedicalRecordEntity.builder();

        medicalRecordEntity.date( input.getDate() );
        medicalRecordEntity.id( input.getId() );
        medicalRecordEntity.nutritionistId( input.getNutritionistId() );
        medicalRecordEntity.observations( input.getObservations() );
        medicalRecordEntity.patientId( input.getPatientId() );
        medicalRecordEntity.registryType( input.getRegistryType() );

        return medicalRecordEntity.build();
    }

    @Override
    public MedicalRecord fromOutputToInput(MedicalRecordEntity output) {
        if ( output == null ) {
            return null;
        }

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setDate( output.getDate() );
        medicalRecord.setId( output.getId() );
        medicalRecord.setNutritionistId( output.getNutritionistId() );
        medicalRecord.setObservations( output.getObservations() );
        medicalRecord.setPatientId( output.getPatientId() );
        medicalRecord.setRegistryType( output.getRegistryType() );

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
