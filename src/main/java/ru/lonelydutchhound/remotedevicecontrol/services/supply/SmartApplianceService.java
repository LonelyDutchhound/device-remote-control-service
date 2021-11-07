package ru.lonelydutchhound.remotedevicecontrol.services.supply;

import java.util.List;
import java.util.UUID;
import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;
import ru.lonelydutchhound.remotedevicecontrol.models.smartappliance.AbstractSmartAppliance;

public interface SmartApplianceService<T extends AbstractSmartAppliance<? extends Program>> {
  List<T> getAllAppliances();

  T createAppliance(T appliance);

  T getApplianceById(UUID id);

  T updateApplianceProgramSet(UUID id, List<UUID> programIds);

  void deleteAppliance(UUID id);
}
