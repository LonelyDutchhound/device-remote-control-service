package ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ApiError {
  String message;
  LocalDateTime happenedAt = LocalDateTime.now();
}
