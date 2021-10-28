package ru.lonelydutchhound.remotedevicecontrol.dto.mappers;

import org.springframework.stereotype.Component;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDTO;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingMachine;

@Component
public class WashingMachineDTOMapper implements DTOMapper<WashingMachineDTO, WashingMachine> {
    @Override
    public WashingMachineDTO mapEntityToDto(WashingMachine washingMachine) {
        return WashingMachineDTO.builder()
                .id(washingMachine.getId())
                .model(washingMachine.getModel())
                .build();
    }
}
