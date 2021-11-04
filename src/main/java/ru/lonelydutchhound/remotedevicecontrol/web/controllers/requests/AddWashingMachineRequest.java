package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddWashingMachineRequest implements Request {
  @NotBlank
  private final String model;
  @NotNull
  private final List<UUID> programIdList;
}
