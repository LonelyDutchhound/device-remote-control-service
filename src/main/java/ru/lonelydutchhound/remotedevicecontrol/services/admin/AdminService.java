package ru.lonelydutchhound.remotedevicecontrol.services.admin;

import java.util.List;
import java.util.UUID;
import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;
import ru.lonelydutchhound.remotedevicecontrol.models.smartdevice.AbstractSmartDevice;

public interface AdminService<P extends Program, D extends AbstractSmartDevice<P>> {
  P createProgram(P program);

  List<P> getAllPrograms();

  D createNewSmartDevice(String model, List<UUID> programList);
}
