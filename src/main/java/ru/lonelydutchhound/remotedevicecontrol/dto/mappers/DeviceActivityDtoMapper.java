package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.DeviceActivityDto;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceactivity.WashingMachineDeviceActivity;

@Component
public class DeviceActivityDtoMapper
    implements DtoMapper<DeviceActivityDto, WashingMachineDeviceActivity> {
  private final WashingProgramDtoMapper washingProgramDtoMapper;

  @Autowired
  public DeviceActivityDtoMapper(WashingProgramDtoMapper washingProgramDtoMapper) {
    this.washingProgramDtoMapper = washingProgramDtoMapper;
  }

  @Override
  public DeviceActivityDto mapEntityToDto(WashingMachineDeviceActivity entity) {
    return DeviceActivityDto.builder()
        .id(entity.getId())
        .deviceId(entity.getWashingMachineDevice().getId())
        .washingProgramDto(washingProgramDtoMapper.mapEntityToDto(entity.getProgram()))
        .programStatus(entity.getProgramStatus())
        .build();
  }
}
