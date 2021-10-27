package ru.lonelydutchhound.remotedevicecontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.DeviceActivity;

import java.util.UUID;

public interface DeviceActivityRepository extends JpaRepository<DeviceActivity, UUID> {
}
