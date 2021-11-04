package ru.lonelydutchhound.remotedevicecontrol.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;

@Value
@Builder
public class WashingMachineDeviceDto implements Dto {
  UUID id;
  WashingMachineDto washingMachine;
  PowerStatus powerStatus;
  LocalDateTime createdAt;
  LocalDateTime deletedAt;
}
