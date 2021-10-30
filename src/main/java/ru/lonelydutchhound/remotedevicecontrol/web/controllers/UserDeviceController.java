package ru.lonelydutchhound.remotedevicecontrol.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lonelydutchhound.remotedevicecontrol.dto.DeviceActivityDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.WashingMachineDeviceDTO;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.DeviceActivityDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.dto.mappers.WashingMachineDeviceDTOMapper;
import ru.lonelydutchhound.remotedevicecontrol.models.deviceActivity.WashingMachineDeviceActivity;
import ru.lonelydutchhound.remotedevicecontrol.services.UserWashingMachineDeviceService;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.AddWashingMachineDevice;
import ru.lonelydutchhound.remotedevicecontrol.web.controllers.requests.StartProgramRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/washing-machine")
@Tag(name = "User remote control", description = "Remote control for devices from user side")
public class UserDeviceController {
    private final UserWashingMachineDeviceService userWashingMachineDeviceService;
    private final WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper;
    private final DeviceActivityDTOMapper deviceActivityDTOMapper;

    @Autowired
    public UserDeviceController(
            UserWashingMachineDeviceService userWashingMachineDeviceService,
            WashingMachineDeviceDTOMapper washingMachineDeviceDTOMapper,
            DeviceActivityDTOMapper deviceActivityDTOMapper) {
        this.userWashingMachineDeviceService = userWashingMachineDeviceService;
        this.washingMachineDeviceDTOMapper = washingMachineDeviceDTOMapper;
        this.deviceActivityDTOMapper = deviceActivityDTOMapper;
    }

    @Operation(
            summary = "Get information about devices added to remote control",
            description = "Full information about devices with program list and current power status",
            tags = {"devices"}
    )
    @GetMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WashingMachineDeviceDTO>> getAllDevices() {
        var washingMachineDeviceDTOList = userWashingMachineDeviceService.getAllActiveDevices()
                .stream().map(washingMachineDeviceDTOMapper::mapEntityToDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(washingMachineDeviceDTOList);
    }

    @Operation(
            summary = "Add new washing machine device to remote control",
            description = "Washing machine model is searched by id, then a new device is created in remote control list",
            tags = {"device"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created",
                    content = @Content(schema = @Schema(implementation = WashingMachineDeviceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent"),
            @ApiResponse(responseCode = "404", description = "Washing machine model wasn't found")
    })
    @PostMapping(
            value = "/devices",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WashingMachineDeviceDTO> addWashingMachineDevice(
            @Parameter(description = "Washing machine model to add. ID can't be null or empty.", required = true, schema = @Schema(implementation = AddWashingMachineDevice.class))
            @RequestBody AddWashingMachineDevice request) {
        var device = userWashingMachineDeviceService.createDevice(request.getMachineId());

        return ResponseEntity.status(HttpStatus.CREATED).body(washingMachineDeviceDTOMapper.mapEntityToDto(device));
    }

    @Operation(
            summary = "Get full information about concrete washing machine device by its id",
            description = "Full information about devices with program list and current power status",
            tags = {"device"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Full device info",
                    content = @Content(schema = @Schema(implementation = WashingMachineDeviceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent"),
            @ApiResponse(responseCode = "404", description = "Washing machine device wasn't found by provided id")
    })
    @GetMapping(value = "/devices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WashingMachineDeviceDTO> getWashingMachineById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(washingMachineDeviceDTOMapper.mapEntityToDto(userWashingMachineDeviceService.getDeviceById(UUID.fromString(id))));
    }

    @Operation(
            summary = "Delete concrete washing machine device from remote control by its id",
            description = "Washing machine device is searched by id, then a new device is deleted, endpoint returns info about deleted device",
            tags = {"device"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Washing machine device deleted from remote control list",
                    content = @Content(schema = @Schema(implementation = WashingMachineDeviceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Request parameter is not valid or absent"),
            @ApiResponse(responseCode = "404", description = "Washing machine device wasn't found by provided id")
    })
    @DeleteMapping(value = "/devices/{id}")
    public ResponseEntity<WashingMachineDeviceDTO> deleteDevice(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(washingMachineDeviceDTOMapper.mapEntityToDto(userWashingMachineDeviceService.deleteDeviceById(UUID.fromString(id))));
    }

    @Operation(
            summary = "Start program on device",
            description = "Washing machine device is searched by its id, program is searched by its id in device program set",
            tags = {"activity"}
    )
    @PostMapping(
            value = "/programs/start",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DeviceActivityDTO> startProgram(@RequestBody StartProgramRequest request) {
        var activity = userWashingMachineDeviceService.startNewDeviceProgram(request.getDeviceId(), request.getProgramId());

        return ResponseEntity.status(HttpStatus.CREATED).body(deviceActivityDTOMapper.mapEntityToDto(activity));
    }
}
