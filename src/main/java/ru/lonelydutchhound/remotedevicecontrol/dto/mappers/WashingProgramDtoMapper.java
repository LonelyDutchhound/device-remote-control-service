package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDto;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

@Component
public class WashingProgramDtoMapper implements DtoMapper<WashingProgramDto, WashingProgram> {
  @Override
  public WashingProgramDto mapEntityToDto(WashingProgram program) {
    return WashingProgramDto.builder()
        .id(program.getId())
        .name(program.getName())
        .duration(program.getDuration())
        .temperature(program.getTemperature())
        .spinSpeed(program.getSpinSpeed())
        .build();
  }
}
