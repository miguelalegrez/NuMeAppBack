package com.ejercicios.primeraPractica.infraestructure.apirest.mapper;

import com.ejercicios.primeraPractica.domain.model.Appointment;
import com.ejercicios.primeraPractica.infraestructure.apirest.dto.request.PatchAppointmentDto;
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
public class AppointmentToPatchAppointmentMapperImpl implements AppointmentToPatchAppointmentMapper {

    @Override
    public PatchAppointmentDto fromInputToOutput(Appointment input) {
        if ( input == null ) {
            return null;
        }

        PatchAppointmentDto patchAppointmentDto = new PatchAppointmentDto();

        return patchAppointmentDto;
    }

    @Override
    public Appointment fromOutputToInput(PatchAppointmentDto output) {
        if ( output == null ) {
            return null;
        }

        Appointment appointment = new Appointment();

        return appointment;
    }

    @Override
    public List<PatchAppointmentDto> fromInputToOutput(List<Appointment> inputList) {
        if ( inputList == null ) {
            return null;
        }

        List<PatchAppointmentDto> list = new ArrayList<PatchAppointmentDto>( inputList.size() );
        for ( Appointment appointment : inputList ) {
            list.add( fromInputToOutput( appointment ) );
        }

        return list;
    }

    @Override
    public List<Appointment> fromOutputToInput(List<PatchAppointmentDto> outputList) {
        if ( outputList == null ) {
            return null;
        }

        List<Appointment> list = new ArrayList<Appointment>( outputList.size() );
        for ( PatchAppointmentDto patchAppointmentDto : outputList ) {
            list.add( fromOutputToInput( patchAppointmentDto ) );
        }

        return list;
    }
}
