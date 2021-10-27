package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class WashingProgramDTO {
    UUID id;
    String name;
    byte temperature;
    Long duration;
    int spinSpeed;
}
