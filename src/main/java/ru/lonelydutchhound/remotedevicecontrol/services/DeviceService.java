package ru.lonelydutchhound.remotedevicecontrol.services;

import ru.lonelydutchhound.remotedevicecontrol.models.Device;

import java.util.List;

public interface DeviceService<T extends Device> {
    List<T> getAllActiveDevices();
}
