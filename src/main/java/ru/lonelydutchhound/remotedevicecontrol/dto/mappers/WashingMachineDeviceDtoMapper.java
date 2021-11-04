package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDto;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;

@Component
public class WashingMachineDeviceDtoMapper
    implements DtoMapper<WashingMachineDeviceDto, WashingMachineDevice> {
  private final WashingMachineDtoMapper washingMachineDtoMapper;

  @Autowired
  public WashingMachineDeviceDtoMapper(WashingMachineDtoMapper washingMachineDtoMapper) {
    this.washingMachineDtoMapper = washingMachineDtoMapper;
  }

  @Override
  public WashingMachineDeviceDto mapEntityToDto(WashingMachineDevice washingMachineDevice) {
    return WashingMachineDeviceDto.builder()
        .id(washingMachineDevice.getId())
        .washingMachine(
            washingMachineDtoMapper.mapEntityToDto(washingMachineDevice.getWashingMachine()))
        .powerStatus(washingMachineDevice.getPowerStatus())
        .createdAt(washingMachineDevice.getCreatedAt())
        .build();
  }
}
