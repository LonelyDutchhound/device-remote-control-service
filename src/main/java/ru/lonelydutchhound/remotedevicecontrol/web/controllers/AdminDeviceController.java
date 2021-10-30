package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingProgramDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingProgramDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.models.smartDevice.WashingMachine;
import ru.lonelydutchhound.remotedevicecontrol.models.program.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.services.AdminWashingMachineService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/washing-machine")
@Tag(name = "Admin control panel", description = "Customise user devices")
public class AdminDeviceController {
    private final AdminWashingMachineService adminWashingMachineService;
    private final WashingProgramDTOMapper washingProgramDTOMapper;
    private final WashingMachineDTOMapper washingMachineDTOMapper;

    @Autowired
    public AdminDeviceController(
            AdminWashingMachineService adminWashingMachineService,
            WashingProgramDTOMapper washingProgramDTOMapper,
        WashingMachineDTOMapper washingMachineDTOMapper
    ) {
        this.adminWashingMachineService = adminWashingMachineService;
        this.washingProgramDTOMapper = washingProgramDTOMapper;
        this.washingMachineDTOMapper = washingMachineDTOMapper;
    }

    @PostMapping(
            value = "/program",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WashingProgramDTO> createWashingProgram(@RequestBody @Valid WashingProgram washingProgram) {
        return ResponseEntity.status(HttpStatus.CREATED).body(washingProgramDTOMapper.mapEntityToDto(adminWashingMachineService.createNewWashingProgram(washingProgram)));
    }

    @PostMapping(
            value = "/machine",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WashingMachineDTO> createWashingMachine(@RequestBody @Valid WashingMachine washingMachine) {
        return ResponseEntity.status(HttpStatus.CREATED).body(washingMachineDTOMapper.mapEntityToDto(adminWashingMachineService.createNewWashingMachine(washingMachine)));
    }

    @PostMapping(
            value = "/program-to-machine",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void addProgramToWashingMachine(UUID programId, UUID machineId) {
        // TODO: implement mapping program-to-machine
    }
}
