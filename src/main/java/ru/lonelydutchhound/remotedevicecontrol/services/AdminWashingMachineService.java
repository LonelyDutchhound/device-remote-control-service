package ru.lonelydutchhound.remotedevicecontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

import java.util.UUID;

@Service
public class AdminWashingMachineService {
    private WashingProgramRepository washingProgramRepository;

    @Autowired
    public AdminWashingMachineService(WashingProgramRepository washingProgramRepository) {
        this.washingProgramRepository = washingProgramRepository;
    }

    public UUID createNewWashingProgram(WashingProgram washingProgram) {
        var newWashingProgram = washingProgramRepository.save(washingProgram);
        return newWashingProgram.getId();
    }
}
