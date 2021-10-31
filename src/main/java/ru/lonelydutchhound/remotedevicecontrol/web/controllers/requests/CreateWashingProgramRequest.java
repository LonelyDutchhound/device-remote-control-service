package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import lombok.Data;

@Data
public class CreateWashingProgramRequest implements Request {
    private final String name;
    private final int temperature;
    private final Long duration;
    private final int spinSpeed;
}
