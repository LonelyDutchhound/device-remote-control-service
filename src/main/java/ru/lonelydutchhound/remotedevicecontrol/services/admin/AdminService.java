package ru.lonelydutchhound.remotedevicecontrol.services.admin;

import ru.lonelydutchhound.remotedevicecontrol.models.program.Program;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.AbstractSmartDevice;

import java.util.List;

public interface AdminService<T extends Program, E extends AbstractSmartDevice<T>> {
    T createProgram(T program);
    List<T> getAllPrograms();
    E createNewSmartDevice(E smartDevice);
}