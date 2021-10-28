package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.models.DeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.services.WashingMachineDeviceService;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.StartProgramRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/washing-machine")
public class DeviceController {
    private final WashingMachineDeviceService washingMachineDeviceService;
    private final WashingMachineDTOMapper washingMachineDTOMapper;

    @Autowired
    public DeviceController(WashingMachineDeviceService washingMachineDeviceService, WashingMachineDTOMapper washingMachineDTOMapper) {
        this.washingMachineDeviceService = washingMachineDeviceService;
        this.washingMachineDTOMapper = washingMachineDTOMapper;
    }

    @GetMapping(value = "/devices", produces = "application/json")
    public ResponseEntity<List<WashingMachineDeviceDTO>> getAllDevices() {
        var washingMachineDeviceDTOList = washingMachineDeviceService.getAllActiveDevices()
                .stream().map(washingMachineDTOMapper::mapEntityToDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(washingMachineDeviceDTOList);
    }

    @PostMapping(
            value = "/start-program",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DeviceActivity> startProgram(@RequestBody StartProgramRequest request) {
        var activity = washingMachineDeviceService.startNewDeviceProgram(request.getDeviceUuid(), request.getProgramUuid());

        return ResponseEntity.status(HttpStatus.CREATED).body(activity);
    }
}
