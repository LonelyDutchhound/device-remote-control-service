package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddWashingMachineRequest implements Request{
    private final String model;
    private final List<UUID> programIdList;
}
