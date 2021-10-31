package ru.lonelydutchhound.remotedevicecontrol.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.AddWashingMachineRequest;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.CreateWashingProgramRequest;

import java.util.HashSet;
import java.util.List;

@Service
public class WashingMachineAdminService implements AdminService<WashingProgram, WashingMachine> {
    private final WashingProgramRepository washingProgramRepository;
    private final WashingMachineRepository washingMachineRepository;

    @Autowired
    public WashingMachineAdminService(WashingProgramRepository washingProgramRepository, WashingMachineRepository washingMachineRepository) {
        this.washingProgramRepository = washingProgramRepository;
        this.washingMachineRepository = washingMachineRepository;
    }

    @Override
    public WashingProgram createProgram(WashingProgram washingProgram) {
        return washingProgramRepository.save(washingProgram);
    }

    @Override
    public List<WashingProgram> getAllPrograms() {
        return washingProgramRepository.findAll();
    }

    @Override
    @Transactional
    public WashingMachine createNewSmartDevice(WashingMachine washingMachine) {
        return washingMachineRepository.save(washingMachine);
    }

    public WashingProgram buildProgram(CreateWashingProgramRequest request){
        return new WashingProgram.WashingProgramBuilder()
                .setName(request.getName())
                .setDuration(request.getDuration())
                .setTemperature(request.getTemperature())
                .setSpinSpeed(request.getSpinSpeed())
                .build();
    }

    public WashingMachine buildSmartDevice(AddWashingMachineRequest addWashingMachineRequest) {
        var programList = washingProgramRepository.findAllById(addWashingMachineRequest.getProgramIdList());

        return new WashingMachine.WashingMachineBuilder()
                .setModel(addWashingMachineRequest.getModel())
                .setProgramSet(new HashSet<>(programList))
                .build();
    }
}
