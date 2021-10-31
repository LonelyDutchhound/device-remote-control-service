package ru.lonelydutchhound.remotedevicecontrol.web.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class StartProgramRequest {
    @JsonCreator
    public StartProgramRequest(@JsonProperty("deviceId") UUID deviceId,
                               @JsonProperty("programId") UUID programId){
        this.deviceId = deviceId;
        this.programId = programId;
    }
    private final UUID deviceId;
    private final UUID programId;
}
