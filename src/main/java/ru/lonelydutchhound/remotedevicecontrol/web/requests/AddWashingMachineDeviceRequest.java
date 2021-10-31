package ru.lonelydutchhound.remotedevicecontrol.web.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class AddWashingMachineDeviceRequest {
    @JsonCreator
    public AddWashingMachineDeviceRequest(@JsonProperty("machineId") UUID machineId) {
        this.machineId = machineId;
    }
    private final UUID machineId;
}
