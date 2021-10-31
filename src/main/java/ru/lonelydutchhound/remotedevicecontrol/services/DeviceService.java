package ru.lonelydutchhound.remotedevicecontrol.services;

import ru.lonelydutchhound.remotedevicecontrol.models.device.Device;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceActivity.DeviceActivity;

import java.util.List;
import java.util.UUID;

public interface DeviceService<T extends Device> {
    List<T> getAllActiveDevices();
    T createDevice(UUID id);
    T getDeviceById(UUID id);
    T deleteDeviceById(UUID id);
    DeviceActivity<T> startNewDeviceProgram(UUID deviceId, UUID programId);
}
