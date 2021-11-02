package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddWashingMachineDeviceRequest {
  @JsonCreator
  public AddWashingMachineDeviceRequest (@JsonProperty("machineId") UUID machineId) {
    this.machineId = machineId;
  }

  @NotNull
  private final UUID machineId;
}
