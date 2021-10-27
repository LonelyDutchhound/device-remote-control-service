package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import ru.lonelydutchhound.remotedevicecontrol.dto.DeviceDTO;

public interface DeviceDTOMapper<T extends DeviceDTO, E> {
    T mapEntityToDto(E entity);
}
