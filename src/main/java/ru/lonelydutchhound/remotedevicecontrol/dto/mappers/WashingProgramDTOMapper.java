package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

@Component
public class WashingProgramDTOMapper implements DTOMapper<WashingProgramDTO, WashingProgram> {
    @Override
    public WashingProgramDTO mapEntityToDto(WashingProgram program) {
        return WashingProgramDTO.builder()
                .id(program.getId())
                .name(program.getName())
                .duration(program.getDuration())
                .temperature(program.getTemperature())
                .spinSpeed(program.getSpinSpeed())
                .build();
    }
}
