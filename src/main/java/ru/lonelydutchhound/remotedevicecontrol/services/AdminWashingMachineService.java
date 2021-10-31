package ru.lonelydutchhound.remotedevicecontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;
import ru.lonelydutchhound.remotedevicecontrol.web.requests.AddWashingMachineRequest;

import java.util.HashSet;
import java.util.List;

@Service
public class AdminWashingMachineService {
    private final WashingProgramRepository washingProgramRepository;
    private final WashingMachineRepository washingMachineRepository;

    @Autowired
    public AdminWashingMachineService(WashingProgramRepository washingProgramRepository, WashingMachineRepository washingMachineRepository) {
        this.washingProgramRepository = washingProgramRepository;
        this.washingMachineRepository = washingMachineRepository;
    }

    public WashingProgram createNewWashingProgram(WashingProgramDTO washingProgramDTO) {
        return washingProgramRepository.save(buildWashingProgram(washingProgramDTO));
    }

    @Transactional
    public WashingMachine createNewWashingMachine(AddWashingMachineRequest addWashingMachineRequest) {
        var programList = washingProgramRepository.findAllById(addWashingMachineRequest.getProgramIdList());
        return washingMachineRepository.save(buildWashingMachine(addWashingMachineRequest.getModel(), programList));
    }

    private WashingProgram buildWashingProgram(WashingProgramDTO washingProgramDTO){
        return new WashingProgram.WashingProgramBuilder()
                .setName(washingProgramDTO.getName())
                .setDuration(washingProgramDTO.getDuration())
                .setTemperature(washingProgramDTO.getTemperature())
                .setSpinSpeed(washingProgramDTO.getSpinSpeed())
                .build();
    }

    private WashingMachine buildWashingMachine(String model, List<WashingProgram> washingProgramList) {
        return new WashingMachine.WashingMachineBuilder()
                .setModel(model)
                .setProgramSet(new HashSet<>(washingProgramList))
                .build();
    }
}
