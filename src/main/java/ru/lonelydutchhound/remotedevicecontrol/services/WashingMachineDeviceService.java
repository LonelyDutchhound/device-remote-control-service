package ru.lonelydutchhound.remotedevicecontrol.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.*;
import ru.lonelydutchhound.remotedevicecontrol.models.DeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;
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

    public WashingMachineDevice getDeviceById(UUID deviceId) {
        Optional<WashingMachineDevice> washingMachineDevice = deviceRepository.findById(deviceId);
        AtomicReference<WashingMachineDevice> existingDevice = new AtomicReference<>(null);
        washingMachineDevice.ifPresentOrElse(existingDevice::set, () -> {
            LOGGER.error("No device with id {} found", deviceId);
            throw new DeviceNotFoundException(String.format("No device with %s found", deviceId));
        });
        return existingDevice.get();
    }

    @Transactional
    public DeviceActivity startNewDeviceProgram(UUID deviceId, UUID programId) {
        var device = deviceRepository.findById(deviceId);
        var program = washingProgramRepository.findById(programId);
        var deviceActivity = deviceActivityRepository.findOneByWashingMachineDeviceId(deviceId);

        deviceActivity.ifPresent(activity -> {
            ProgramStatus status = activity.getProgramStatus();
            if (status == ProgramStatus.RUNNING || status == ProgramStatus.STARTING) {
                LOGGER.error("Device with id {} is busy", deviceId);
                throw new DeviceBusyException(String.format("Device with id %s is running another program right now, wait until it is finished", deviceId));
            } else if (status == ProgramStatus.ERROR) {
                LOGGER.error("Device with id {} has error program status", deviceId);
                throw new DeviceErrorProgramStatusException(String.format("Device with id %s has error program status, can't start program until status is reset", deviceId));
            }
        });

        AtomicReference<DeviceActivity> newDeviceActivity = new AtomicReference<>(null);
        device.ifPresentOrElse(
                existingDevice -> {
                    if (existingDevice.getPowerStatus() == PowerStatus.OFF) {
                        throw new DevicePowerOffException(String.format("Device with %s is switched off", deviceId));
                    }
                    program.ifPresentOrElse(
                            existingProgram -> {
                                var activity = buildNewDeviceActivity(existingDevice, existingProgram);
                                newDeviceActivity.set(deviceActivityRepository.save(activity));
                            }, () -> {
                                LOGGER.error("No program with id {} found", programId);
                                throw new ProgramNotFoundException(String.format("No program with %s on this device", programId));
                            });
                },
                () -> {
                    LOGGER.error("No device with id {} found", deviceId);
                    throw new DeviceNotFoundException(String.format("No device with %s found", deviceId));
                });
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
