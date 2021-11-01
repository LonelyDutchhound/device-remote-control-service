package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class StartProgramRequest {
    @JsonCreator
    public StartProgramRequest(@JsonProperty("deviceId") UUID deviceId,
                               @JsonProperty("programId") UUID programId){
        this.deviceId = deviceId;
        this.programId = programId;
    }
    @NotNull
    private final UUID deviceId;
    @NotNull
    private final UUID programId;
}
