package ru.lonelydutchhound.remotedevicecontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceRepository;

import java.util.List;

@Service
public class WashingMachineDeviceService implements DeviceService<WashingMachineDevice> {
    private DeviceRepository deviceRepository;

    @Autowired
    public WashingMachineDeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<WashingMachineDevice> getAllActiveDevices() {
        return deviceRepository.findAllByDeletedAtIsNull();
    }
}
