package ru.lonelydutchhound.remotedevicecontrol.models.smartdevice;


import java.util.Set;
import java.util.UUID;
import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;

public interface AbstractSmartDevice<T extends Program> {
  UUID getId();

  String getModel();

  Set<T> getProgramSet();
}
