package ru.lonelydutchhound.remotedevicecontrol.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

public interface WashingProgramRepository extends JpaRepository<WashingProgram, UUID> {
}
