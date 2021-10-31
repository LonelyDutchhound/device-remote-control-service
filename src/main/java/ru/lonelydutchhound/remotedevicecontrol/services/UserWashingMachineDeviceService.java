package ru.lonelydutchhound.remotedevicecontrol.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.*;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceActivity.WashingMachineDeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.types.PowerStatus;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceActivityRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.DeviceRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserWashingMachineDeviceService implements DeviceService<WashingMachineDevice> {
    private final Logger LOGGER = LoggerFactory.getLogger(UserWashingMachineDeviceService.class);

    private final DeviceRepository deviceRepository;
    private final DeviceActivityRepository deviceActivityRepository;
    private final WashingProgramRepository washingProgramRepository;
    private final WashingMachineRepository washingMachineRepository;

    @Autowired
    public UserWashingMachineDeviceService(
            DeviceRepository deviceRepository,
            DeviceActivityRepository deviceActivityRepository,
            WashingProgramRepository washingProgramRepository,
            WashingMachineRepository washingMachineRepository) {
        this.deviceRepository = deviceRepository;
        this.deviceActivityRepository = deviceActivityRepository;
        this.washingProgramRepository = washingProgramRepository;
        this.washingMachineRepository = washingMachineRepository;
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

    @Transactional
    public WashingMachineDevice createDevice(UUID machineId) {
        var washingMachine = washingMachineRepository.findById(machineId).orElseThrow(() -> {
            LOGGER.error("No device with id {} found", machineId);
            throw new NotFoundException(String.format("No device with id %s found", machineId));
        });
        var device = buildWashingMachineDevice(washingMachine);
        return deviceRepository.save(device);
    }

    public void deleteDeviceById(UUID deviceId) {
        var device = getDeviceById(deviceId);
        var deviceActivityNotFinished = deviceActivityRepository.findOneByWashingMachineDeviceIdAndProgramStatusNot(deviceId, ProgramStatus.FINISHED);

        checkExistingDeviceActivity(deviceActivityNotFinished, deviceId);

        deviceRepository.delete(device);
    }

    @Transactional
    public WashingMachineDeviceActivity startNewDeviceProgram(UUID deviceId, UUID programId) {
        var device = findDevice(deviceId);
        if (device.getPowerStatus() == PowerStatus.OFF) {
            LOGGER.error("Device with id {} is switched off", deviceId);
            throw new IncorrectDeviceStateException(String.format("Device with id %s is switched off", deviceId));
        }

        var program = findProgram(programId, device.getWashingMachine()).orElseThrow(() -> {
            LOGGER.error("Program with id {} is not found in device with id {} set", programId, deviceId);
            throw new NotFoundException("No program with id {} exists on this device");
        });

        var deviceActivityNotFinished = deviceActivityRepository.findOneByWashingMachineDeviceIdAndProgramStatusNot(deviceId, ProgramStatus.FINISHED);
        checkExistingDeviceActivity(deviceActivityNotFinished, deviceId);

        var activity = buildNewDeviceActivity(device, program);
        return deviceActivityRepository.save(activity);

    }

    private void checkExistingDeviceActivity(Optional<WashingMachineDeviceActivity> deviceActivity, UUID deviceId) {
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

    private WashingMachineDevice findDevice(UUID deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow(() -> {
            LOGGER.error("No device with id {} found", deviceId);
            throw new NotFoundException(String.format("No device with %s found", deviceId));
        });
    }

    private Optional<WashingProgram> findProgram(UUID programId, WashingMachine machine) {
        var program = washingProgramRepository.findById(programId).orElseThrow(() -> {
            LOGGER.error("No program with id {} found", programId);
            throw new NotFoundException(String.format("No program with %s on this device", programId));
        });
        var isProgramOnDevice = program.getWashingMachineSet().contains(machine);
        if (isProgramOnDevice){
            return Optional.of(program);
        } else {
            return Optional.empty();
        }
    }

    private WashingMachineDevice buildWashingMachineDevice(WashingMachine washingMachine) {
        return new WashingMachineDevice(washingMachine);
    }

    private WashingMachineDeviceActivity buildNewDeviceActivity(WashingMachineDevice washingMachineDevice, WashingProgram washingProgram) {
        return new WashingMachineDeviceActivity.DeviceActivityBuilder()
                .setWashingMachineDevice(washingMachineDevice)
                .setWashingProgram(washingProgram)
                .setProgramStatus(ProgramStatus.STARTING)
                .build();
    }
}
