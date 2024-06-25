package com.ejercicios.primeraPractica.domain.mapper;

import com.ejercicios.primeraPractica.domain.model.MedicalRecord;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-25T09:21:57+0200",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class MedicalRecordPatchMapperImpl implements MedicalRecordPatchMapper {

    @Override
    public void update(MedicalRecord output, MedicalRecord input) {
        if ( input == null ) {
            return;
        }

        output.setDate( input.getDate() );
        output.setId( input.getId() );
        output.setObservations( input.getObservations() );
        output.setPatientId( input.getPatientId() );
        output.setRegistryType( input.getRegistryType() );
    }
}
