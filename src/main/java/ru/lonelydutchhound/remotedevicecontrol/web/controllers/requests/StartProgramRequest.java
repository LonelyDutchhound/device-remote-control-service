package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class StartProgramRequest {
    private final UUID deviceUuid;
    private final UUID programUuid;
}
