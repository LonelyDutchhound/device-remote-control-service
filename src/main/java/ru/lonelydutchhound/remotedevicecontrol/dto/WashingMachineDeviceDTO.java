package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class WashingMachineDeviceDTO implements DTO {
  UUID id;
  WashingMachineDTO washingMachine;
  PowerStatus powerStatus;
  LocalDateTime createdAt;
  LocalDateTime deletedAt;
}
