package ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private final String message;
    private final LocalDateTime happenedAt = LocalDateTime.now();
}
