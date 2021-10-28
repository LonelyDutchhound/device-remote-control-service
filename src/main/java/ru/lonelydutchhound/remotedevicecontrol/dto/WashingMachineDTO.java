package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class WashingMachineDTO implements DTO {
    UUID id;
    String model;
    List<WashingProgramDTO> programList;
}
