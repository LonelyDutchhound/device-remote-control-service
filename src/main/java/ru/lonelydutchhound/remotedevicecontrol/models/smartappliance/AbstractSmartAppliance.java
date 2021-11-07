package ru.lonelydutchhound.remotedevicecontrol.models.smartappliance;


import java.util.Set;
import java.util.UUID;
import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;

public interface AbstractSmartAppliance<T extends Program> {
  UUID getId();

  String getModel();

  Set<T> getProgramSet();
}
