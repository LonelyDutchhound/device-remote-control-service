package ru.lonelydutchhound.remotedevicecontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

@Value
@Builder
public class DeviceActivityDto implements Dto {
  UUID id;
  UUID deviceId;
  @JsonProperty(value = "washingProgram")
  WashingProgramDto washingProgramDto;
  ProgramStatus programStatus;
}
