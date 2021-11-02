package ru.lonelydutchhound.remotedevicecontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class WashingMachineDTO implements DTO {
  UUID id;
  String model;
  @JsonProperty("programSet")
  Set<WashingProgramDTO> programDTOSet;
}
