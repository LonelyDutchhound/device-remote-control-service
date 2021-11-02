package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;

import java.util.stream.Collectors;

@Component
public class WashingMachineDTOMapper implements DTOMapper<WashingMachineDTO, WashingMachine> {
  private WashingProgramDTOMapper washingProgramDTOMapper;

  @Autowired
  public WashingMachineDTOMapper (WashingProgramDTOMapper washingProgramDTOMapper) {
    this.washingProgramDTOMapper = washingProgramDTOMapper;
  }

  @Override
  public WashingMachineDTO mapEntityToDto (WashingMachine washingMachine) {
    return WashingMachineDTO.builder()
        .id(washingMachine.getId())
        .model(washingMachine.getModel())
        .programDTOSet(washingMachine.getProgramSet().stream().map(program -> washingProgramDTOMapper.mapEntityToDto(program)).collect(Collectors.toSet()))
        .build();
  }
}
