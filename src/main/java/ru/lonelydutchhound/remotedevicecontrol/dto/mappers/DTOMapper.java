package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import ru.lonelydutchhound.remotedevicecontrol.dto.DTO;

public interface DTOMapper<T extends DTO, E> {
  T mapEntityToDto (E entity);
}
