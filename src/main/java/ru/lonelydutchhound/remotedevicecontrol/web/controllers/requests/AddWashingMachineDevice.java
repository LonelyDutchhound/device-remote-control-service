package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class AddWashingMachineDevice {
    @JsonCreator
    public AddWashingMachineDevice(@JsonProperty("machineId") UUID machineId) {
        this.machineId = machineId;
    }
    private final UUID machineId;
}
