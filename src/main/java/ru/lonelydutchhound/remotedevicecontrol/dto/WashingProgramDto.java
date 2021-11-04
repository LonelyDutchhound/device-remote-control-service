package ru.lonelydutchhound.remotedevicecontrol.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WashingProgramDto implements Dto {
  UUID id;
  String name;
  int temperature;
  Long duration;
  int spinSpeed;
}
