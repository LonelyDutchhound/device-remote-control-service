package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

import java.util.UUID;

@Value
@Builder
public class DeviceActivityDTO {
    UUID id;
    UUID deviceId;
    WashingProgram program;
    ProgramStatus programStatus;
}
