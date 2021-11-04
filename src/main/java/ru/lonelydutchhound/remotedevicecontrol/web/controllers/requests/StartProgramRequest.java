package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartProgramRequest {
  @NotNull
  private final UUID deviceId;
  @NotNull
  private final UUID programId;

  @JsonCreator
  public StartProgramRequest(@JsonProperty("deviceId") UUID deviceId,
                             @JsonProperty("programId") UUID programId) {
    this.deviceId = deviceId;
    this.programId = programId;
  }
}
