package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lonelydutchhound.remotedevicecontrol.models.WashingProgram;
import ru.lonelydutchhound.remotedevicecontrol.services.AdminWashingMachineService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/washing-machine")
public class AdminDeviceController {
    private AdminWashingMachineService adminWashingMachineService;

    @Autowired
    public AdminDeviceController(AdminWashingMachineService adminWashingMachineService) {
        this.adminWashingMachineService = adminWashingMachineService;
    }

    @PostMapping(
            value = "/program",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UUID> createWashingProgram(@RequestBody WashingProgram washingProgram) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminWashingMachineService.createNewWashingProgram(washingProgram));
    }
}
