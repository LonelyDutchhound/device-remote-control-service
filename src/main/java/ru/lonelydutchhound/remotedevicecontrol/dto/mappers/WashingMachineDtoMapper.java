package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDto;
import ru.lonelydutchhound.remotedevicecontrol.models.smartappliance.WashingMachine;

@Component
public class WashingMachineDtoMapper implements DtoMapper<WashingMachineDto, WashingMachine> {
  private final WashingProgramDtoMapper washingProgramDtoMapper;

  @Autowired
  public WashingMachineDtoMapper(WashingProgramDtoMapper washingProgramDtoMapper) {
    this.washingProgramDtoMapper = washingProgramDtoMapper;
  }

  @Override
  public WashingMachineDto mapEntityToDto(WashingMachine washingMachine) {
    return WashingMachineDto.builder()
        .id(washingMachine.getId())
        .model(washingMachine.getModel())
        .washingProgramDtos(washingMachine.getProgramSet().stream()
            .map(washingProgramDtoMapper::mapEntityToDto)
            .collect(Collectors.toSet()))
        .build();
  }
}
