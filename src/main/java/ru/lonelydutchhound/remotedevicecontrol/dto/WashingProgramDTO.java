package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class WashingProgramDTO implements DTO{
    UUID id;
    String name;
    int temperature;
    Long duration;
    int spinSpeed;
}
