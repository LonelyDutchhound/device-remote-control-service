package ru.lonelydutchhound.remotedevicecontrol.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.models.DeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceActivityRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class WashingMachineDeviceService implements DeviceService<WashingMachineDevice> {
    private final Logger LOGGER = LoggerFactory.getLogger(WashingMachineDeviceService.class);

    private final DeviceRepository deviceRepository;
    private final DeviceActivityRepository deviceActivityRepository;
    private final WashingProgramRepository washingProgramRepository;

    @Autowired
    public WashingMachineDeviceService(DeviceRepository deviceRepository, DeviceActivityRepository deviceActivityRepository, WashingProgramRepository washingProgramRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceActivityRepository = deviceActivityRepository;
        this.washingProgramRepository = washingProgramRepository;
    }

    public List<WashingMachineDevice> getAllActiveDevices() {
        return deviceRepository.findAllByDeletedAtIsNull();
    }

    @Transactional
    public DeviceActivity startNewDeviceProgram(UUID deviceUuid, UUID programUuid) {
        var device = deviceRepository.findById(deviceUuid);
        var program = washingProgramRepository.findById(programUuid);

        AtomicReference<DeviceActivity> newDeviceActivity = new AtomicReference<>(null);
        device.ifPresentOrElse(
                existingDevice -> program.ifPresentOrElse(
                        existingProgram -> {
                            var activity = buildNewDeviceActivity(existingDevice, existingProgram);
                            newDeviceActivity.set(deviceActivityRepository.save(activity));
                        }, () -> LOGGER.error("No program with id {} found", programUuid)),
                () -> LOGGER.error("No device with id {} found", deviceUuid));
        return newDeviceActivity.get();
    }

    private DeviceActivity buildNewDeviceActivity(WashingMachineDevice washingMachineDevice, WashingProgram washingProgram) {
        return new DeviceActivity.DeviceActivityBuilder()
                .setWashingMachineDevice(washingMachineDevice)
                .setWashingProgram(washingProgram)
                .setProgramStatus(ProgramStatus.STARTING)
                .build();
    }
}
