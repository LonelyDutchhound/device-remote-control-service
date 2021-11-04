package ru.lonelydutchhound.remotedevicecontrol.services.device;

import java.util.List;
import java.util.UUID;
import ru.lonelydutchhound.remotedevicecontrol.models.device.Device;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceactivity.DeviceActivity;

public interface DeviceService<T extends Device> {
  List<T> getAllActiveDevices();

  T createDevice(UUID id);

  T getDeviceById(UUID id);

  void deleteDeviceById(UUID id);

  DeviceActivity<T> startNewDeviceProgram(UUID deviceId, UUID programId);
}
