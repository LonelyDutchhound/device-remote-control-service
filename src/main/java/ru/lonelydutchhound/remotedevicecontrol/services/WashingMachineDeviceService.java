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
        return deviceRepository.findById(deviceId).orElseThrow(() -> {
            LOGGER.error("No device with id {} found", deviceId);
            throw new NotFoundException(String.format("No device with %s found", deviceId));
        });
    }

    public WashingMachineDevice deleteDeviceById(UUID deviceId) {
        var device = getDeviceById(deviceId);
        var deviceActivity = deviceActivityRepository.findOneByWashingMachineDeviceId(deviceId);

        checkExistingDeviceActivity(deviceActivity, deviceId);

        deviceRepository.delete(device);
        return device;
    }

    @Transactional
    public DeviceActivity startNewDeviceProgram(UUID deviceId, UUID programId) {
        var device = deviceRepository.findById(deviceId).orElseThrow(() -> {
            LOGGER.error("No device with id {} found", deviceId);
            throw new NotFoundException(String.format("No device with %s found", deviceId));
        });

        if (device.getPowerStatus() == PowerStatus.OFF) {
            throw new IncorrectDeviceStateException(String.format("Device with %s is switched off", deviceId));
        }

        var program = washingProgramRepository.findById(programId).orElseThrow(() -> {
            LOGGER.error("No program with id {} found", programId);
            throw new NotFoundException(String.format("No program with %s on this device", programId));
        });
        var deviceActivity = deviceActivityRepository.findOneByWashingMachineDeviceId(deviceId);

        checkExistingDeviceActivity(deviceActivity, deviceId);

        var activity = buildNewDeviceActivity(device, program);

        return deviceActivityRepository.save(activity);
    }

    private void checkExistingDeviceActivity(Optional<DeviceActivity> deviceActivity, UUID deviceId) {
        deviceActivity.ifPresent(activity -> {
            ProgramStatus status = activity.getProgramStatus();
            if (status == ProgramStatus.RUNNING || status == ProgramStatus.STARTING) {
                LOGGER.error("Device with id {} is busy", deviceId);
                throw new IncorrectDeviceStateException(String.format("Device with id %s is running a program right now, wait until it is finished", deviceId));
            } else if (status == ProgramStatus.ERROR) {
                LOGGER.error("Device with id {} has error program status", deviceId);
                throw new IncorrectDeviceStateException(String.format("Device with id %s has error program status, make a reset", deviceId));
            }
        });
    }

    private DeviceActivity buildNewDeviceActivity(WashingMachineDevice washingMachineDevice, WashingProgram washingProgram) {
        return new DeviceActivity.DeviceActivityBuilder()
                .setWashingMachineDevice(washingMachineDevice)
                .setWashingProgram(washingProgram)
                .setProgramStatus(ProgramStatus.STARTING)
                .build();
    }
}
