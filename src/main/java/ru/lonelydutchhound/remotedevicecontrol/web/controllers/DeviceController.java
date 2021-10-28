package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDeviceDTOMapper;
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
    private final WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper;

    @Autowired
    public DeviceController(WashingMachineDeviceService washingMachineDeviceService, WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper) {
        this.washingMachineDeviceService = washingMachineDeviceService;
        this.washingMachineDeviceDTOMapper = washingMachineDeviceDTOMapper;
    }

    @GetMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WashingMachineDeviceDTO>> getAllDevices() {
        var washingMachineDeviceDTOList = washingMachineDeviceService.getAllActiveDevices()
                .stream().map(washingMachineDeviceDTOMapper::mapEntityToDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(washingMachineDeviceDTOList);
    }

    @GetMapping(value = "/device/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WashingMachineDeviceDTO> getWashingMachineById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(washingMachineDeviceDTOMapper.mapEntityToDto(washingMachineDeviceService.getDeviceById(UUID.fromString(id))));
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

    @DeleteMapping(value = "/device/{id}")
    public ResponseEntity<WashingMachineDeviceDTO> deleteDevice(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(washingMachineDeviceDTOMapper.mapEntityToDto(washingMachineDeviceService.deleteDeviceById(UUID.fromString(id))));
    }
}
