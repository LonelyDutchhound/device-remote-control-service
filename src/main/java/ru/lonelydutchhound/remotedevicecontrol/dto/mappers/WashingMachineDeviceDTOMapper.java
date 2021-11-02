package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;

@Component
public class WashingMachineDeviceDTOMapper implements DTOMapper<WashingMachineDeviceDTO, WashingMachineDevice> {
  private WashingMachineDTOMapper washingMachineDTOMapper;

  @Autowired
  public WashingMachineDeviceDTOMapper (WashingMachineDTOMapper washingMachineDTOMapper) {
    this.washingMachineDTOMapper = washingMachineDTOMapper;
  }

  @Override
  public WashingMachineDeviceDTO mapEntityToDto (WashingMachineDevice washingMachineDevice) {
    return WashingMachineDeviceDTO.builder()
        .id(washingMachineDevice.getId())
        .washingMachine(washingMachineDTOMapper.mapEntityToDto(washingMachineDevice.getWashingMachine()))
        .powerStatus(washingMachineDevice.getPowerStatus())
        .createdAt(washingMachineDevice.getCreatedAt())
        .build();
  }
}
