package ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class AddWashingMachineRequest implements Request{
    @NotNull
    @NotBlank
    private final String model;
    private final List<UUID> programIdList;
}
