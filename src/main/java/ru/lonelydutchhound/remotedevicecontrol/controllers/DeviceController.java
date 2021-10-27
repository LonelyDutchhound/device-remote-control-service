package ru.lonelydutchhound.remotedevicecontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.services.WashingMachineDeviceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("washing-machine")
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
        List<WashingMachineDeviceDTO> washingMachineDeviceDTOList = washingMachineDeviceService.getAllActiveDevices()
                .stream().map(washingMachineDTOMapper::mapEntityToDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(washingMachineDeviceDTOList);
    }
}
