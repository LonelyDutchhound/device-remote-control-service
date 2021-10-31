package ru.lonelydutchhound.remotedevicecontrol.web.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddWashingMachineRequest {
    private final String model;
    private final List<UUID> programIdList;
}
