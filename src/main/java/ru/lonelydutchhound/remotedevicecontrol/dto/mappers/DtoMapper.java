package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import ru.lonelydutchhound.remotedevicecontrol.dto.Dto;

public interface DtoMapper<T extends Dto, E> {
  T mapEntityToDto(E entity);
}
