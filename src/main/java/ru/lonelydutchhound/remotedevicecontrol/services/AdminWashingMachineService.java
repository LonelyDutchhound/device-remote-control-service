package ru.lonelydutchhound.remotedevicecontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

@Service
public class AdminWashingMachineService {
    private final WashingProgramRepository washingProgramRepository;
    private final WashingMachineRepository washingMachineRepository;

    @Autowired
    public AdminWashingMachineService(WashingProgramRepository washingProgramRepository, WashingMachineRepository washingMachineRepository) {
        this.washingProgramRepository = washingProgramRepository;
        this.washingMachineRepository = washingMachineRepository;
    }

    public WashingProgram createNewWashingProgram(WashingProgram washingProgram) {
        return washingProgramRepository.save(washingProgram);
    }

    public WashingMachine createNewWashingMachine(WashingMachine washingMachine) {
        return washingMachineRepository.save(washingMachine);
    }
}
