package ru.lonelydutchhound.remotedevicecontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.device.WashingMachineDevice;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<WashingMachineDevice, UUID> {
    List<WashingMachineDevice> findAllByDeletedAtIsNull();
}
