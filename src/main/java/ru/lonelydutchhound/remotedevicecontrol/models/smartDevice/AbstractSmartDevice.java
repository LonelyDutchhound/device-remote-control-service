package ru.lonelydutchhound.remotedevicecontrol.models.smartDevice;


import java.util.Set;
import java.util.UUID;

public interface AbstractSmartDevice<T> {
  UUID getId ();

  String getModel ();

  Set<T> getProgramSet ();
}
