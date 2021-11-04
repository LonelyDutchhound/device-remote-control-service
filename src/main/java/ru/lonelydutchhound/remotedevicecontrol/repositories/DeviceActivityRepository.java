package ru.lonelydutchhound.remotedevicecontrol.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceactivity.WashingMachineDeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.models.types.ProgramStatus;

public interface DeviceActivityRepository
    extends JpaRepository<WashingMachineDeviceActivity, UUID> {
  Optional<WashingMachineDeviceActivity> findOneByWashingMachineDeviceIdAndProgramStatusNot(
      UUID deviceId, ProgramStatus status);
}
