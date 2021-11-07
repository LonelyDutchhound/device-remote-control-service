package ru.lonelydutchhound.remotedevicecontrol.services.supply;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lonelydutchhound.remotedevicecontrol.exceptions.NotFoundException;
import ru.lonelydutchhound.remotedevicecontrol.models.smartappliance.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingMachineRepository;
import ru.lonelydutchhound.remotedevicecontrol.repositories.WashingProgramRepository;

@Service
public class WashingMachineService implements SmartApplianceService<WashingMachine> {
  private final WashingMachineRepository washingMachineRepository;
  private final WashingProgramRepository washingProgramRepository;
  private final Logger logger = LoggerFactory.getLogger(WashingMachineService.class);

  @Autowired
  public WashingMachineService(WashingMachineRepository washingMachineRepository,
                               WashingProgramRepository washingProgramRepository) {
    this.washingMachineRepository = washingMachineRepository;
    this.washingProgramRepository = washingProgramRepository;
  }

  @Override
  public List<WashingMachine> getAllAppliances() {
    return washingMachineRepository.findAll();
  }

  @Override
  public WashingMachine createAppliance(WashingMachine appliance) {
    return washingMachineRepository.save(appliance);
  }

  @Override
  public WashingMachine getApplianceById(UUID id) {
    return washingMachineRepository.findById(id).orElseThrow(() -> {
      logger.error("No washing machine with {} exists", id);
      throw new NotFoundException(String.format("No washing machine with id %s exists", id));
    });
  }

  @Override
  @Transactional
  public WashingMachine updateApplianceProgramSet(UUID id, List<UUID> programIds) {
    var washingMachine = getApplianceById(id);
    var newProgramSet = new HashSet<>(washingProgramRepository.findAllById(programIds));
    var existedProgramSet = washingMachine.getProgramSet();
    newProgramSet.addAll(existedProgramSet);

    washingMachine.updateProgramSet(newProgramSet);
    return washingMachineRepository.save(washingMachine);
  }

  @Override
  public void deleteAppliance(UUID id) {
    washingMachineRepository.deleteById(id);
  }
}
