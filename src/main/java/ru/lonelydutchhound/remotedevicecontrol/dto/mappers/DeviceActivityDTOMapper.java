package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.DeviceActivityDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceActivity.WashingMachineDeviceActivity;

@Component
public class DeviceActivityDTOMapper implements DTOMapper<DeviceActivityDTO, WashingMachineDeviceActivity> {
  @Override
  public DeviceActivityDTO mapEntityToDto (WashingMachineDeviceActivity entity) {
    return DeviceActivityDTO.builder()
        .id(entity.getId())
        .deviceId(entity.getWashingMachineDevice().getId())
        .program(entity.getProgram())
        .programStatus(entity.getProgramStatus())
        .build();
  }
}
