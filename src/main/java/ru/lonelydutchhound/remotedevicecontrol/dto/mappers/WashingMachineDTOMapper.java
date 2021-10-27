package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachineDevice;

@Component
public class WashingMachineDTOMapper implements DeviceDTOMapper<WashingMachineDeviceDTO, WashingMachineDevice> {
    @Override
    public WashingMachineDeviceDTO mapEntityToDto(WashingMachineDevice device) {
        return WashingMachineDeviceDTO.builder()
                .id(device.getId())
                .washingMachine(device.getWashingMachine())
                .powerStatus(device.getPowerStatus())
                .createdAt(device.getCreatedAt())
                .build();
    }
}
