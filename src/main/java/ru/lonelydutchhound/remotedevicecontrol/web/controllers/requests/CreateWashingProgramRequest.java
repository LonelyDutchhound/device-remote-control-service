package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateWashingProgramRequest implements Request {
  @NotBlank
  private final String name;
  @Min(value = 0)
  private final int temperature;
  @Positive
  private final Long duration;
  private final int spinSpeed;
}
