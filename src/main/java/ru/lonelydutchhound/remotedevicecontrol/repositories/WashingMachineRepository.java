package ru.lonelydutchhound.remotedevicecontrol.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.smartdevice.WashingMachine;

public interface WashingMachineRepository extends JpaRepository<WashingMachine, UUID> {
}
