package ru.lonelydutchhound.remotedevicecontrol.services.admin;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.models.smartappliance.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

@Service
public class WashingMachineAdminService implements AdminService<WashingProgram, WashingMachine> {
  private final WashingProgramRepository washingProgramRepository;
  private final WashingMachineRepository washingMachineRepository;

  @Autowired
  public WashingMachineAdminService(WashingProgramRepository washingProgramRepository,
                                    WashingMachineRepository washingMachineRepository) {
    this.washingProgramRepository = washingProgramRepository;
    this.washingMachineRepository = washingMachineRepository;
  }

  @Override
  public WashingProgram createProgram(WashingProgram washingProgram) {
    return washingProgramRepository.saveAndFlush(washingProgram);
  }

  @Override
  public List<WashingProgram> getAllPrograms() {
    return washingProgramRepository.findAll();
  }

  @Override
  @Transactional
  public WashingMachine createNewSmartDevice(String model, List<UUID> programIdList) {
    var programList = washingProgramRepository.findAllById(programIdList);
    var washingMachine = buildSmartDevice(model, programList);
    return washingMachineRepository.saveAndFlush(washingMachine);
  }

  public WashingMachine buildSmartDevice(String model, List<WashingProgram> programList) {
    return new WashingMachine.WashingMachineBuilder()
        .setModel(model)
        .setProgramSet(new HashSet<>(programList))
        .build();
  }
}
