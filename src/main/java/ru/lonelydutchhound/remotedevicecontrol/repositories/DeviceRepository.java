package ru.lonelydutchhound.remotedevicecontrol.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;

public interface DeviceRepository extends JpaRepository<WashingMachineDevice, UUID> {
  List<WashingMachineDevice> findAllByDeletedAtIsNull();
}
