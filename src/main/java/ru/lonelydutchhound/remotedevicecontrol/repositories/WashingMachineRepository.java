package ru.lonelydutchhound.remotedevicecontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;

import java.util.UUID;

public interface WashingMachineRepository extends JpaRepository<WashingMachine, UUID> {
}
