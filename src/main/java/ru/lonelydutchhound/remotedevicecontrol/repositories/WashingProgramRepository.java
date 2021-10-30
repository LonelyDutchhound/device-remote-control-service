package ru.lonelydutchhound.remotedevicecontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;

import java.util.UUID;

public interface WashingProgramRepository extends JpaRepository<WashingProgram, UUID> {
}
