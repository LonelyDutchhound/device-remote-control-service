package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class WashingMachineDTO {
    UUID id;
    String model;
    List<WashingProgramDTO> programList;
}
