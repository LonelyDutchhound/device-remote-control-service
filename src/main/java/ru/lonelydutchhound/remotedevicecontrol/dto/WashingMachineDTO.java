package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class WashingMachineDTO implements DTO {
    UUID id;
    String model;
    Set<WashingProgramDTO> programSet;
}
