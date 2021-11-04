package ru.lonelydutchhound.remotedevicecontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WashingMachineDto implements Dto {
  UUID id;
  String model;
  @JsonProperty("programSet")
  Set<WashingProgramDto> washingProgramDtos;
}
