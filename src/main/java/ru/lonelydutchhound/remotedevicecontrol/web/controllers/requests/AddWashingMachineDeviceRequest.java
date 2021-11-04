package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddWashingMachineDeviceRequest {
  @NotNull
  private final UUID machineId;

  @JsonCreator
  public AddWashingMachineDeviceRequest(@JsonProperty("machineId") UUID machineId) {
    this.machineId = machineId;
  }
}
