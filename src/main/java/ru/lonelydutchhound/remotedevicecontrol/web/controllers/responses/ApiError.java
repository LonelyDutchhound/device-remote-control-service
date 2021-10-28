package ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ApiError {
    String message;
//    @JsonProperty("happened_at")
    LocalDateTime happenedAt = LocalDateTime.now();
}
