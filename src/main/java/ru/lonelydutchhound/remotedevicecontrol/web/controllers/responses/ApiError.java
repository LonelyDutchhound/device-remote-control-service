package ru.lonelydutchhound.remotedevicecontrol.web.controllers.responses;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ApiError {
  String message;
  LocalDateTime happenedAt = LocalDateTime.now();
}
