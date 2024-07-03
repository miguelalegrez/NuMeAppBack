package com.ejercicios.primeraPractica.domain.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-01T09:17:10+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class MedicalRecordPatchMapperImpl implements MedicalRecordPatchMapper {

    @Override
    public void update(MedicalRecord output, MedicalRecord input) {
        if ( input == null ) {
            return;
        }

        output.setId( input.getId() );
        output.setDate( input.getDate() );
        output.setRegistryType( input.getRegistryType() );
        output.setObservations( input.getObservations() );
        output.setPatientId( input.getPatientId() );
    }
}
