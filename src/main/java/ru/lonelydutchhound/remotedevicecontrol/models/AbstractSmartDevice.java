package ru.lonelydutchhound.remotedevicecontrol.models;


import java.util.List;
import java.util.UUID;

public interface AbstractSmartDevice<T> {
    UUID getId();
    String getModel();
    List<T> getProgramList();
}
