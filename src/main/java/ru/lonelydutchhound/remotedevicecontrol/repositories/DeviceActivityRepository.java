package ru.lonelydutchhound.remotedevicecontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceActivity.WashingMachineDeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

import java.util.Optional;
import java.util.UUID;

public interface DeviceActivityRepository extends JpaRepository<WashingMachineDeviceActivity, UUID> {
  Optional<WashingMachineDeviceActivity> findOneByWashingMachineDeviceIdAndProgramStatusNot (UUID deviceId, ProgramStatus status);
}
