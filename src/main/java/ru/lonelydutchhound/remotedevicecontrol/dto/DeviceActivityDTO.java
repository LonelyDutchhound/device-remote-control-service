package ru.lonelydutchhound.remotedevicecontrol.dto;

import lombok.Builder;
import lombok.Value;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

import java.util.UUID;

@Value
@Builder
public class DeviceActivityDTO implements DTO{
    UUID id;
    UUID deviceId;
    WashingProgram program;
    ProgramStatus programStatus;
}
