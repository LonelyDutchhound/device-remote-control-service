package ru.lonelydutchhound.remotedevicecontrol.models;


import java.util.List;
import java.util.UUID;

public interface AbstractSmartDevice {
    UUID getId();
    String getModel();
    List<Program> getProgramList();
}
