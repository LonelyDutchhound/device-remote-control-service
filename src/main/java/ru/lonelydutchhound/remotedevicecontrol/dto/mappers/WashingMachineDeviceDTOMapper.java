package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachineDevice;

@Component
public class WashingMachineDeviceDTOMapper implements DTOMapper<WashingMachineDeviceDTO, WashingMachineDevice> {
    @Override
    public WashingMachineDeviceDTO mapEntityToDto(WashingMachineDevice washingMachineDevice) {
        return WashingMachineDeviceDTO.builder()
                .id(washingMachineDevice.getId())
                .washingMachine(washingMachineDevice.getWashingMachine())
                .powerStatus(washingMachineDevice.getPowerStatus())
                .createdAt(washingMachineDevice.getCreatedAt())
                .build();
    }
}
