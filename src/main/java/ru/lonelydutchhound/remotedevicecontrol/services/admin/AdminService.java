package ru.lonelydutchhound.remotedevicecontrol.services.admin;

import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.AbstractSmartDevice;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.Request;

import java.util.List;
import java.util.UUID;

public interface AdminService<T extends Program, E extends AbstractSmartDevice<T>> {
    T createProgram(T program);
    List<T> getAllPrograms();
    E createNewSmartDevice(String model, List<UUID> programList);
}
